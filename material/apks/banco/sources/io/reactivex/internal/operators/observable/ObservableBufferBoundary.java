package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.observers.QueueDrainObserver;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.util.QueueDrainHelper;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.SerializedObserver;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public final class ObservableBufferBoundary<T, U extends Collection<? super T>, Open, Close> extends AbstractObservableWithUpstream<T, U> {
    final Callable<U> a;
    final ObservableSource<? extends Open> b;
    final Function<? super Open, ? extends ObservableSource<? extends Close>> c;

    static final class BufferBoundaryObserver<T, U extends Collection<? super T>, Open, Close> extends QueueDrainObserver<T, U, U> implements Disposable {
        final ObservableSource<? extends Open> b;
        final Function<? super Open, ? extends ObservableSource<? extends Close>> c;
        final Callable<U> d;
        final CompositeDisposable e;
        Disposable f;
        final List<U> g;
        final AtomicInteger h = new AtomicInteger();

        BufferBoundaryObserver(Observer<? super U> observer, ObservableSource<? extends Open> observableSource, Function<? super Open, ? extends ObservableSource<? extends Close>> function, Callable<U> callable) {
            super(observer, new MpscLinkedQueue());
            this.b = observableSource;
            this.c = function;
            this.d = callable;
            this.g = new LinkedList();
            this.e = new CompositeDisposable();
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f, disposable)) {
                this.f = disposable;
                BufferOpenObserver bufferOpenObserver = new BufferOpenObserver(this);
                this.e.add(bufferOpenObserver);
                this.actual.onSubscribe(this);
                this.h.lazySet(1);
                this.b.subscribe(bufferOpenObserver);
            }
        }

        public void onNext(T t) {
            synchronized (this) {
                for (U add : this.g) {
                    add.add(t);
                }
            }
        }

        public void onError(Throwable th) {
            dispose();
            this.cancelled = true;
            synchronized (this) {
                this.g.clear();
            }
            this.actual.onError(th);
        }

        public void onComplete() {
            if (this.h.decrementAndGet() == 0) {
                a();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            ArrayList<Collection> arrayList;
            synchronized (this) {
                arrayList = new ArrayList<>(this.g);
                this.g.clear();
            }
            SimplePlainQueue simplePlainQueue = this.queue;
            for (Collection offer : arrayList) {
                simplePlainQueue.offer(offer);
            }
            this.done = true;
            if (enter()) {
                QueueDrainHelper.drainLoop(simplePlainQueue, this.actual, false, this, this);
            }
        }

        public void dispose() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.e.dispose();
            }
        }

        public boolean isDisposed() {
            return this.cancelled;
        }

        /* renamed from: a */
        public void accept(Observer<? super U> observer, U u) {
            observer.onNext(u);
        }

        /* access modifiers changed from: 0000 */
        public void a(Open open) {
            if (!this.cancelled) {
                try {
                    Collection collection = (Collection) ObjectHelper.requireNonNull(this.d.call(), "The buffer supplied is null");
                    try {
                        ObservableSource observableSource = (ObservableSource) ObjectHelper.requireNonNull(this.c.apply(open), "The buffer closing Observable is null");
                        if (!this.cancelled) {
                            synchronized (this) {
                                if (!this.cancelled) {
                                    this.g.add(collection);
                                    BufferCloseObserver bufferCloseObserver = new BufferCloseObserver(collection, this);
                                    this.e.add(bufferCloseObserver);
                                    this.h.getAndIncrement();
                                    observableSource.subscribe(bufferCloseObserver);
                                }
                            }
                        }
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        onError(th);
                    }
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    onError(th2);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(Disposable disposable) {
            if (this.e.remove(disposable) && this.h.decrementAndGet() == 0) {
                a();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(U u, Disposable disposable) {
            boolean remove;
            synchronized (this) {
                remove = this.g.remove(u);
            }
            if (remove) {
                fastPathOrderedEmit(u, false, this);
            }
            if (this.e.remove(disposable) && this.h.decrementAndGet() == 0) {
                a();
            }
        }
    }

    static final class BufferCloseObserver<T, U extends Collection<? super T>, Open, Close> extends DisposableObserver<Close> {
        final BufferBoundaryObserver<T, U, Open, Close> a;
        final U b;
        boolean c;

        BufferCloseObserver(U u, BufferBoundaryObserver<T, U, Open, Close> bufferBoundaryObserver) {
            this.a = bufferBoundaryObserver;
            this.b = u;
        }

        public void onNext(Close close) {
            onComplete();
        }

        public void onError(Throwable th) {
            if (this.c) {
                RxJavaPlugins.onError(th);
            } else {
                this.a.onError(th);
            }
        }

        public void onComplete() {
            if (!this.c) {
                this.c = true;
                this.a.a(this.b, (Disposable) this);
            }
        }
    }

    static final class BufferOpenObserver<T, U extends Collection<? super T>, Open, Close> extends DisposableObserver<Open> {
        final BufferBoundaryObserver<T, U, Open, Close> a;
        boolean b;

        BufferOpenObserver(BufferBoundaryObserver<T, U, Open, Close> bufferBoundaryObserver) {
            this.a = bufferBoundaryObserver;
        }

        public void onNext(Open open) {
            if (!this.b) {
                this.a.a(open);
            }
        }

        public void onError(Throwable th) {
            if (this.b) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.b = true;
            this.a.onError(th);
        }

        public void onComplete() {
            if (!this.b) {
                this.b = true;
                this.a.a((Disposable) this);
            }
        }
    }

    public ObservableBufferBoundary(ObservableSource<T> observableSource, ObservableSource<? extends Open> observableSource2, Function<? super Open, ? extends ObservableSource<? extends Close>> function, Callable<U> callable) {
        super(observableSource);
        this.b = observableSource2;
        this.c = function;
        this.a = callable;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super U> observer) {
        this.source.subscribe(new BufferBoundaryObserver(new SerializedObserver(observer), this.b, this.c, this.a));
    }
}
