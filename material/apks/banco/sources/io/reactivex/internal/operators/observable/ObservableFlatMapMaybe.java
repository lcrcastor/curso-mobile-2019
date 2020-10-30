package io.reactivex.internal.operators.observable;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableFlatMapMaybe<T, R> extends AbstractObservableWithUpstream<T, R> {
    final Function<? super T, ? extends MaybeSource<? extends R>> a;
    final boolean b;

    static final class FlatMapMaybeObserver<T, R> extends AtomicInteger implements Observer<T>, Disposable {
        private static final long serialVersionUID = 8600231336733376951L;
        final Observer<? super R> a;
        final boolean b;
        final CompositeDisposable c = new CompositeDisposable();
        final AtomicInteger d = new AtomicInteger(1);
        final AtomicThrowable e = new AtomicThrowable();
        final Function<? super T, ? extends MaybeSource<? extends R>> f;
        final AtomicReference<SpscLinkedArrayQueue<R>> g = new AtomicReference<>();
        Disposable h;
        volatile boolean i;

        final class InnerObserver extends AtomicReference<Disposable> implements MaybeObserver<R>, Disposable {
            private static final long serialVersionUID = -502562646270949838L;

            InnerObserver() {
            }

            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            public void onSuccess(R r) {
                FlatMapMaybeObserver.this.a(this, r);
            }

            public void onError(Throwable th) {
                FlatMapMaybeObserver.this.a(this, th);
            }

            public void onComplete() {
                FlatMapMaybeObserver.this.a(this);
            }

            public boolean isDisposed() {
                return DisposableHelper.isDisposed((Disposable) get());
            }

            public void dispose() {
                DisposableHelper.dispose(this);
            }
        }

        FlatMapMaybeObserver(Observer<? super R> observer, Function<? super T, ? extends MaybeSource<? extends R>> function, boolean z) {
            this.a = observer;
            this.f = function;
            this.b = z;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.h, disposable)) {
                this.h = disposable;
                this.a.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            try {
                MaybeSource maybeSource = (MaybeSource) ObjectHelper.requireNonNull(this.f.apply(t), "The mapper returned a null MaybeSource");
                this.d.getAndIncrement();
                InnerObserver innerObserver = new InnerObserver();
                if (this.c.add(innerObserver)) {
                    maybeSource.subscribe(innerObserver);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.h.dispose();
                onError(th);
            }
        }

        public void onError(Throwable th) {
            this.d.decrementAndGet();
            if (this.e.addThrowable(th)) {
                if (!this.b) {
                    this.c.dispose();
                }
                b();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        public void onComplete() {
            this.d.decrementAndGet();
            b();
        }

        public void dispose() {
            this.i = true;
            this.h.dispose();
            this.c.dispose();
        }

        public boolean isDisposed() {
            return this.i;
        }

        /* access modifiers changed from: 0000 */
        public void a(InnerObserver innerObserver, R r) {
            this.c.delete(innerObserver);
            if (get() == 0) {
                boolean z = true;
                if (compareAndSet(0, 1)) {
                    this.a.onNext(r);
                    if (this.d.decrementAndGet() != 0) {
                        z = false;
                    }
                    SpscLinkedArrayQueue spscLinkedArrayQueue = (SpscLinkedArrayQueue) this.g.get();
                    if (!z || (spscLinkedArrayQueue != null && !spscLinkedArrayQueue.isEmpty())) {
                        if (decrementAndGet() == 0) {
                            return;
                        }
                        d();
                    }
                    Throwable terminate = this.e.terminate();
                    if (terminate != null) {
                        this.a.onError(terminate);
                    } else {
                        this.a.onComplete();
                    }
                    return;
                }
            }
            SpscLinkedArrayQueue a2 = a();
            synchronized (a2) {
                a2.offer(r);
            }
            this.d.decrementAndGet();
            if (getAndIncrement() != 0) {
                return;
            }
            d();
        }

        /* access modifiers changed from: 0000 */
        public SpscLinkedArrayQueue<R> a() {
            SpscLinkedArrayQueue<R> spscLinkedArrayQueue;
            do {
                SpscLinkedArrayQueue<R> spscLinkedArrayQueue2 = (SpscLinkedArrayQueue) this.g.get();
                if (spscLinkedArrayQueue2 != null) {
                    return spscLinkedArrayQueue2;
                }
                spscLinkedArrayQueue = new SpscLinkedArrayQueue<>(Observable.bufferSize());
            } while (!this.g.compareAndSet(null, spscLinkedArrayQueue));
            return spscLinkedArrayQueue;
        }

        /* access modifiers changed from: 0000 */
        public void a(InnerObserver innerObserver, Throwable th) {
            this.c.delete(innerObserver);
            if (this.e.addThrowable(th)) {
                if (!this.b) {
                    this.h.dispose();
                    this.c.dispose();
                }
                this.d.decrementAndGet();
                b();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        /* access modifiers changed from: 0000 */
        public void a(InnerObserver innerObserver) {
            this.c.delete(innerObserver);
            if (get() == 0) {
                boolean z = true;
                if (compareAndSet(0, 1)) {
                    if (this.d.decrementAndGet() != 0) {
                        z = false;
                    }
                    SpscLinkedArrayQueue spscLinkedArrayQueue = (SpscLinkedArrayQueue) this.g.get();
                    if (z && (spscLinkedArrayQueue == null || spscLinkedArrayQueue.isEmpty())) {
                        Throwable terminate = this.e.terminate();
                        if (terminate != null) {
                            this.a.onError(terminate);
                        } else {
                            this.a.onComplete();
                        }
                        return;
                    } else if (decrementAndGet() != 0) {
                        d();
                    } else {
                        return;
                    }
                }
            }
            this.d.decrementAndGet();
            b();
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            if (getAndIncrement() == 0) {
                d();
            }
        }

        /* access modifiers changed from: 0000 */
        public void c() {
            SpscLinkedArrayQueue spscLinkedArrayQueue = (SpscLinkedArrayQueue) this.g.get();
            if (spscLinkedArrayQueue != null) {
                spscLinkedArrayQueue.clear();
            }
        }

        /* access modifiers changed from: 0000 */
        public void d() {
            Observer<? super R> observer = this.a;
            AtomicInteger atomicInteger = this.d;
            AtomicReference<SpscLinkedArrayQueue<R>> atomicReference = this.g;
            int i2 = 1;
            while (!this.i) {
                if (this.b || ((Throwable) this.e.get()) == null) {
                    boolean z = false;
                    boolean z2 = atomicInteger.get() == 0;
                    SpscLinkedArrayQueue spscLinkedArrayQueue = (SpscLinkedArrayQueue) atomicReference.get();
                    Object poll = spscLinkedArrayQueue != null ? spscLinkedArrayQueue.poll() : null;
                    if (poll == null) {
                        z = true;
                    }
                    if (z2 && z) {
                        Throwable terminate = this.e.terminate();
                        if (terminate != null) {
                            observer.onError(terminate);
                        } else {
                            observer.onComplete();
                        }
                        return;
                    } else if (z) {
                        i2 = addAndGet(-i2);
                        if (i2 == 0) {
                            return;
                        }
                    } else {
                        observer.onNext(poll);
                    }
                } else {
                    Throwable terminate2 = this.e.terminate();
                    c();
                    observer.onError(terminate2);
                    return;
                }
            }
            c();
        }
    }

    public ObservableFlatMapMaybe(ObservableSource<T> observableSource, Function<? super T, ? extends MaybeSource<? extends R>> function, boolean z) {
        super(observableSource);
        this.a = function;
        this.b = z;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super R> observer) {
        this.source.subscribe(new FlatMapMaybeObserver(observer, this.a, this.b));
    }
}
