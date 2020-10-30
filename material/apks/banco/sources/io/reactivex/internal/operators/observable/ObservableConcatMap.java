package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.ErrorMode;
import io.reactivex.observers.SerializedObserver;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public final class ObservableConcatMap<T, U> extends AbstractObservableWithUpstream<T, U> {
    final Function<? super T, ? extends ObservableSource<? extends U>> a;
    final int b;
    final ErrorMode c;

    static final class ConcatMapDelayErrorObserver<T, R> extends AtomicInteger implements Observer<T>, Disposable {
        private static final long serialVersionUID = -6951100001833242599L;
        final Observer<? super R> a;
        final Function<? super T, ? extends ObservableSource<? extends R>> b;
        final int c;
        final AtomicThrowable d = new AtomicThrowable();
        final DelayErrorInnerObserver<R> e;
        final SequentialDisposable f;
        final boolean g;
        SimpleQueue<T> h;
        Disposable i;
        volatile boolean j;
        volatile boolean k;
        volatile boolean l;
        int m;

        static final class DelayErrorInnerObserver<R> implements Observer<R> {
            final Observer<? super R> a;
            final ConcatMapDelayErrorObserver<?, R> b;

            DelayErrorInnerObserver(Observer<? super R> observer, ConcatMapDelayErrorObserver<?, R> concatMapDelayErrorObserver) {
                this.a = observer;
                this.b = concatMapDelayErrorObserver;
            }

            public void onSubscribe(Disposable disposable) {
                this.b.f.replace(disposable);
            }

            public void onNext(R r) {
                this.a.onNext(r);
            }

            public void onError(Throwable th) {
                ConcatMapDelayErrorObserver<?, R> concatMapDelayErrorObserver = this.b;
                if (concatMapDelayErrorObserver.d.addThrowable(th)) {
                    if (!concatMapDelayErrorObserver.g) {
                        concatMapDelayErrorObserver.i.dispose();
                    }
                    concatMapDelayErrorObserver.j = false;
                    concatMapDelayErrorObserver.a();
                    return;
                }
                RxJavaPlugins.onError(th);
            }

            public void onComplete() {
                ConcatMapDelayErrorObserver<?, R> concatMapDelayErrorObserver = this.b;
                concatMapDelayErrorObserver.j = false;
                concatMapDelayErrorObserver.a();
            }
        }

        ConcatMapDelayErrorObserver(Observer<? super R> observer, Function<? super T, ? extends ObservableSource<? extends R>> function, int i2, boolean z) {
            this.a = observer;
            this.b = function;
            this.c = i2;
            this.g = z;
            this.e = new DelayErrorInnerObserver<>(observer, this);
            this.f = new SequentialDisposable();
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.i, disposable)) {
                this.i = disposable;
                if (disposable instanceof QueueDisposable) {
                    QueueDisposable queueDisposable = (QueueDisposable) disposable;
                    int requestFusion = queueDisposable.requestFusion(3);
                    if (requestFusion == 1) {
                        this.m = requestFusion;
                        this.h = queueDisposable;
                        this.k = true;
                        this.a.onSubscribe(this);
                        a();
                        return;
                    } else if (requestFusion == 2) {
                        this.m = requestFusion;
                        this.h = queueDisposable;
                        this.a.onSubscribe(this);
                        return;
                    }
                }
                this.h = new SpscLinkedArrayQueue(this.c);
                this.a.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (this.m == 0) {
                this.h.offer(t);
            }
            a();
        }

        public void onError(Throwable th) {
            if (this.d.addThrowable(th)) {
                this.k = true;
                a();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        public void onComplete() {
            this.k = true;
            a();
        }

        public boolean isDisposed() {
            return this.i.isDisposed();
        }

        public void dispose() {
            this.l = true;
            this.i.dispose();
            this.f.dispose();
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (getAndIncrement() == 0) {
                Observer<? super R> observer = this.a;
                SimpleQueue<T> simpleQueue = this.h;
                AtomicThrowable atomicThrowable = this.d;
                while (true) {
                    if (!this.j) {
                        if (this.l) {
                            simpleQueue.clear();
                            return;
                        } else if (this.g || ((Throwable) atomicThrowable.get()) == null) {
                            boolean z = this.k;
                            try {
                                Object poll = simpleQueue.poll();
                                boolean z2 = poll == null;
                                if (z && z2) {
                                    Throwable terminate = atomicThrowable.terminate();
                                    if (terminate != null) {
                                        observer.onError(terminate);
                                    } else {
                                        observer.onComplete();
                                    }
                                    return;
                                } else if (!z2) {
                                    try {
                                        ObservableSource observableSource = (ObservableSource) ObjectHelper.requireNonNull(this.b.apply(poll), "The mapper returned a null ObservableSource");
                                        if (observableSource instanceof Callable) {
                                            try {
                                                Object call = ((Callable) observableSource).call();
                                                if (call != null && !this.l) {
                                                    observer.onNext(call);
                                                }
                                            } catch (Throwable th) {
                                                Exceptions.throwIfFatal(th);
                                                atomicThrowable.addThrowable(th);
                                            }
                                        } else {
                                            this.j = true;
                                            observableSource.subscribe(this.e);
                                        }
                                    } catch (Throwable th2) {
                                        Exceptions.throwIfFatal(th2);
                                        this.i.dispose();
                                        simpleQueue.clear();
                                        atomicThrowable.addThrowable(th2);
                                        observer.onError(atomicThrowable.terminate());
                                        return;
                                    }
                                }
                            } catch (Throwable th3) {
                                Exceptions.throwIfFatal(th3);
                                this.i.dispose();
                                atomicThrowable.addThrowable(th3);
                                observer.onError(atomicThrowable.terminate());
                                return;
                            }
                        } else {
                            simpleQueue.clear();
                            observer.onError(atomicThrowable.terminate());
                            return;
                        }
                    }
                    if (decrementAndGet() == 0) {
                        return;
                    }
                }
            }
        }
    }

    static final class SourceObserver<T, U> extends AtomicInteger implements Observer<T>, Disposable {
        private static final long serialVersionUID = 8828587559905699186L;
        final Observer<? super U> a;
        final SequentialDisposable b = new SequentialDisposable();
        final Function<? super T, ? extends ObservableSource<? extends U>> c;
        final Observer<U> d;
        final int e;
        SimpleQueue<T> f;
        Disposable g;
        volatile boolean h;
        volatile boolean i;
        volatile boolean j;
        int k;

        static final class InnerObserver<U> implements Observer<U> {
            final Observer<? super U> a;
            final SourceObserver<?, ?> b;

            InnerObserver(Observer<? super U> observer, SourceObserver<?, ?> sourceObserver) {
                this.a = observer;
                this.b = sourceObserver;
            }

            public void onSubscribe(Disposable disposable) {
                this.b.a(disposable);
            }

            public void onNext(U u) {
                this.a.onNext(u);
            }

            public void onError(Throwable th) {
                this.b.dispose();
                this.a.onError(th);
            }

            public void onComplete() {
                this.b.a();
            }
        }

        SourceObserver(Observer<? super U> observer, Function<? super T, ? extends ObservableSource<? extends U>> function, int i2) {
            this.a = observer;
            this.c = function;
            this.e = i2;
            this.d = new InnerObserver(observer, this);
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.g, disposable)) {
                this.g = disposable;
                if (disposable instanceof QueueDisposable) {
                    QueueDisposable queueDisposable = (QueueDisposable) disposable;
                    int requestFusion = queueDisposable.requestFusion(3);
                    if (requestFusion == 1) {
                        this.k = requestFusion;
                        this.f = queueDisposable;
                        this.j = true;
                        this.a.onSubscribe(this);
                        b();
                        return;
                    } else if (requestFusion == 2) {
                        this.k = requestFusion;
                        this.f = queueDisposable;
                        this.a.onSubscribe(this);
                        return;
                    }
                }
                this.f = new SpscLinkedArrayQueue(this.e);
                this.a.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (!this.j) {
                if (this.k == 0) {
                    this.f.offer(t);
                }
                b();
            }
        }

        public void onError(Throwable th) {
            if (this.j) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.j = true;
            dispose();
            this.a.onError(th);
        }

        public void onComplete() {
            if (!this.j) {
                this.j = true;
                b();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            this.h = false;
            b();
        }

        public boolean isDisposed() {
            return this.i;
        }

        public void dispose() {
            this.i = true;
            this.b.dispose();
            this.g.dispose();
            if (getAndIncrement() == 0) {
                this.f.clear();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(Disposable disposable) {
            this.b.update(disposable);
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            if (getAndIncrement() == 0) {
                while (!this.i) {
                    if (!this.h) {
                        boolean z = this.j;
                        try {
                            Object poll = this.f.poll();
                            boolean z2 = poll == null;
                            if (z && z2) {
                                this.a.onComplete();
                                return;
                            } else if (!z2) {
                                try {
                                    ObservableSource observableSource = (ObservableSource) ObjectHelper.requireNonNull(this.c.apply(poll), "The mapper returned a null ObservableSource");
                                    this.h = true;
                                    observableSource.subscribe(this.d);
                                } catch (Throwable th) {
                                    Exceptions.throwIfFatal(th);
                                    dispose();
                                    this.f.clear();
                                    this.a.onError(th);
                                    return;
                                }
                            }
                        } catch (Throwable th2) {
                            Exceptions.throwIfFatal(th2);
                            dispose();
                            this.f.clear();
                            this.a.onError(th2);
                            return;
                        }
                    }
                    if (decrementAndGet() == 0) {
                        return;
                    }
                }
                this.f.clear();
            }
        }
    }

    public ObservableConcatMap(ObservableSource<T> observableSource, Function<? super T, ? extends ObservableSource<? extends U>> function, int i, ErrorMode errorMode) {
        super(observableSource);
        this.a = function;
        this.c = errorMode;
        this.b = Math.max(8, i);
    }

    public void subscribeActual(Observer<? super U> observer) {
        if (!ObservableScalarXMap.tryScalarXMapSubscribe(this.source, observer, this.a)) {
            if (this.c == ErrorMode.IMMEDIATE) {
                this.source.subscribe(new SourceObserver(new SerializedObserver(observer), this.a, this.b));
            } else {
                this.source.subscribe(new ConcatMapDelayErrorObserver(observer, this.a, this.b, this.c == ErrorMode.END));
            }
        }
    }
}
