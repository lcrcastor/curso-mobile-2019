package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.observers.BasicIntQueueDisposable;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.schedulers.TrampolineScheduler;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableObserveOn<T> extends AbstractObservableWithUpstream<T, T> {
    final Scheduler a;
    final boolean b;
    final int c;

    static final class ObserveOnObserver<T> extends BasicIntQueueDisposable<T> implements Observer<T>, Runnable {
        private static final long serialVersionUID = 6576896619930983584L;
        final Observer<? super T> a;
        final Worker b;
        final boolean c;
        final int d;
        SimpleQueue<T> e;
        Disposable f;
        Throwable g;
        volatile boolean h;
        volatile boolean i;
        int j;
        boolean k;

        ObserveOnObserver(Observer<? super T> observer, Worker worker, boolean z, int i2) {
            this.a = observer;
            this.b = worker;
            this.c = z;
            this.d = i2;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f, disposable)) {
                this.f = disposable;
                if (disposable instanceof QueueDisposable) {
                    QueueDisposable queueDisposable = (QueueDisposable) disposable;
                    int requestFusion = queueDisposable.requestFusion(7);
                    if (requestFusion == 1) {
                        this.j = requestFusion;
                        this.e = queueDisposable;
                        this.h = true;
                        this.a.onSubscribe(this);
                        a();
                        return;
                    } else if (requestFusion == 2) {
                        this.j = requestFusion;
                        this.e = queueDisposable;
                        this.a.onSubscribe(this);
                        return;
                    }
                }
                this.e = new SpscLinkedArrayQueue(this.d);
                this.a.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (!this.h) {
                if (this.j != 2) {
                    this.e.offer(t);
                }
                a();
            }
        }

        public void onError(Throwable th) {
            if (this.h) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.g = th;
            this.h = true;
            a();
        }

        public void onComplete() {
            if (!this.h) {
                this.h = true;
                a();
            }
        }

        public void dispose() {
            if (!this.i) {
                this.i = true;
                this.f.dispose();
                this.b.dispose();
                if (getAndIncrement() == 0) {
                    this.e.clear();
                }
            }
        }

        public boolean isDisposed() {
            return this.i;
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (getAndIncrement() == 0) {
                this.b.schedule(this);
            }
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            SimpleQueue<T> simpleQueue = this.e;
            Observer<? super T> observer = this.a;
            int i2 = 1;
            while (!a(this.h, simpleQueue.isEmpty(), observer)) {
                while (true) {
                    boolean z = this.h;
                    try {
                        Object poll = simpleQueue.poll();
                        boolean z2 = poll == null;
                        if (!a(z, z2, observer)) {
                            if (z2) {
                                i2 = addAndGet(-i2);
                                if (i2 == 0) {
                                    return;
                                }
                            } else {
                                observer.onNext(poll);
                            }
                        } else {
                            return;
                        }
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.f.dispose();
                        simpleQueue.clear();
                        observer.onError(th);
                        this.b.dispose();
                        return;
                    }
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void c() {
            int i2 = 1;
            while (!this.i) {
                boolean z = this.h;
                Throwable th = this.g;
                if (this.c || !z || th == null) {
                    this.a.onNext(null);
                    if (z) {
                        Throwable th2 = this.g;
                        if (th2 != null) {
                            this.a.onError(th2);
                        } else {
                            this.a.onComplete();
                        }
                        this.b.dispose();
                        return;
                    }
                    i2 = addAndGet(-i2);
                    if (i2 == 0) {
                        return;
                    }
                } else {
                    this.a.onError(this.g);
                    this.b.dispose();
                    return;
                }
            }
        }

        public void run() {
            if (this.k) {
                c();
            } else {
                b();
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean a(boolean z, boolean z2, Observer<? super T> observer) {
            if (this.i) {
                this.e.clear();
                return true;
            }
            if (z) {
                Throwable th = this.g;
                if (this.c) {
                    if (z2) {
                        if (th != null) {
                            observer.onError(th);
                        } else {
                            observer.onComplete();
                        }
                        this.b.dispose();
                        return true;
                    }
                } else if (th != null) {
                    this.e.clear();
                    observer.onError(th);
                    this.b.dispose();
                    return true;
                } else if (z2) {
                    observer.onComplete();
                    this.b.dispose();
                    return true;
                }
            }
            return false;
        }

        public int requestFusion(int i2) {
            if ((i2 & 2) == 0) {
                return 0;
            }
            this.k = true;
            return 2;
        }

        @Nullable
        public T poll() {
            return this.e.poll();
        }

        public void clear() {
            this.e.clear();
        }

        public boolean isEmpty() {
            return this.e.isEmpty();
        }
    }

    public ObservableObserveOn(ObservableSource<T> observableSource, Scheduler scheduler, boolean z, int i) {
        super(observableSource);
        this.a = scheduler;
        this.b = z;
        this.c = i;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super T> observer) {
        if (this.a instanceof TrampolineScheduler) {
            this.source.subscribe(observer);
            return;
        }
        this.source.subscribe(new ObserveOnObserver(observer, this.a.createWorker(), this.b, this.c));
    }
}
