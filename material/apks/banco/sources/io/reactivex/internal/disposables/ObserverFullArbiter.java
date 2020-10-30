package io.reactivex.internal.disposables;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObserverFullArbiter<T> extends FullArbiterPad1 implements Disposable {
    final Observer<? super T> b;
    final SpscLinkedArrayQueue<Object> c;
    volatile Disposable d = EmptyDisposable.INSTANCE;
    Disposable e;
    volatile boolean f;

    public ObserverFullArbiter(Observer<? super T> observer, Disposable disposable, int i) {
        this.b = observer;
        this.e = disposable;
        this.c = new SpscLinkedArrayQueue<>(i);
    }

    public void dispose() {
        if (!this.f) {
            this.f = true;
            a();
        }
    }

    public boolean isDisposed() {
        Disposable disposable = this.e;
        return disposable != null ? disposable.isDisposed() : this.f;
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        Disposable disposable = this.e;
        this.e = null;
        if (disposable != null) {
            disposable.dispose();
        }
    }

    public boolean setDisposable(Disposable disposable) {
        if (this.f) {
            return false;
        }
        this.c.offer(this.d, NotificationLite.disposable(disposable));
        b();
        return true;
    }

    public boolean onNext(T t, Disposable disposable) {
        if (this.f) {
            return false;
        }
        this.c.offer(disposable, NotificationLite.next(t));
        b();
        return true;
    }

    public void onError(Throwable th, Disposable disposable) {
        if (this.f) {
            RxJavaPlugins.onError(th);
            return;
        }
        this.c.offer(disposable, NotificationLite.error(th));
        b();
    }

    public void onComplete(Disposable disposable) {
        this.c.offer(disposable, NotificationLite.complete());
        b();
    }

    /* access modifiers changed from: 0000 */
    public void b() {
        if (this.a.getAndIncrement() == 0) {
            SpscLinkedArrayQueue<Object> spscLinkedArrayQueue = this.c;
            Observer<? super T> observer = this.b;
            int i = 1;
            while (true) {
                Object poll = spscLinkedArrayQueue.poll();
                if (poll == null) {
                    i = this.a.addAndGet(-i);
                    if (i == 0) {
                        return;
                    }
                } else {
                    Object poll2 = spscLinkedArrayQueue.poll();
                    if (poll == this.d) {
                        if (NotificationLite.isDisposable(poll2)) {
                            Disposable disposable = NotificationLite.getDisposable(poll2);
                            this.d.dispose();
                            if (!this.f) {
                                this.d = disposable;
                            } else {
                                disposable.dispose();
                            }
                        } else if (NotificationLite.isError(poll2)) {
                            spscLinkedArrayQueue.clear();
                            a();
                            Throwable error = NotificationLite.getError(poll2);
                            if (!this.f) {
                                this.f = true;
                                observer.onError(error);
                            } else {
                                RxJavaPlugins.onError(error);
                            }
                        } else if (NotificationLite.isComplete(poll2)) {
                            spscLinkedArrayQueue.clear();
                            a();
                            if (!this.f) {
                                this.f = true;
                                observer.onComplete();
                            }
                        } else {
                            observer.onNext(NotificationLite.getValue(poll2));
                        }
                    }
                }
            }
        }
    }
}
