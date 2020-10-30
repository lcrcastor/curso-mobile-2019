package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Cancellable;
import io.reactivex.internal.disposables.CancellableDisposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableCreate<T> extends Observable<T> {
    final ObservableOnSubscribe<T> a;

    static final class CreateEmitter<T> extends AtomicReference<Disposable> implements ObservableEmitter<T>, Disposable {
        private static final long serialVersionUID = -3434801548987643227L;
        final Observer<? super T> a;

        CreateEmitter(Observer<? super T> observer) {
            this.a = observer;
        }

        public void onNext(T t) {
            if (t == null) {
                onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
                return;
            }
            if (!isDisposed()) {
                this.a.onNext(t);
            }
        }

        public void onError(Throwable th) {
            if (th == null) {
                th = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
            }
            if (!isDisposed()) {
                try {
                    this.a.onError(th);
                } finally {
                    dispose();
                }
            } else {
                RxJavaPlugins.onError(th);
            }
        }

        public void onComplete() {
            if (!isDisposed()) {
                try {
                    this.a.onComplete();
                } finally {
                    dispose();
                }
            }
        }

        public void setDisposable(Disposable disposable) {
            DisposableHelper.set(this, disposable);
        }

        public void setCancellable(Cancellable cancellable) {
            setDisposable(new CancellableDisposable(cancellable));
        }

        public ObservableEmitter<T> serialize() {
            return new SerializedEmitter(this);
        }

        public void dispose() {
            DisposableHelper.dispose(this);
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) get());
        }
    }

    static final class SerializedEmitter<T> extends AtomicInteger implements ObservableEmitter<T> {
        private static final long serialVersionUID = 4883307006032401862L;
        final ObservableEmitter<T> a;
        final AtomicThrowable b = new AtomicThrowable();
        final SpscLinkedArrayQueue<T> c = new SpscLinkedArrayQueue<>(16);
        volatile boolean d;

        public ObservableEmitter<T> serialize() {
            return this;
        }

        SerializedEmitter(ObservableEmitter<T> observableEmitter) {
            this.a = observableEmitter;
        }

        public void onNext(T t) {
            if (!this.a.isDisposed() && !this.d) {
                if (t == null) {
                    onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
                    return;
                }
                if (get() != 0 || !compareAndSet(0, 1)) {
                    SpscLinkedArrayQueue<T> spscLinkedArrayQueue = this.c;
                    synchronized (spscLinkedArrayQueue) {
                        spscLinkedArrayQueue.offer(t);
                    }
                    if (getAndIncrement() != 0) {
                        return;
                    }
                } else {
                    this.a.onNext(t);
                    if (decrementAndGet() == 0) {
                        return;
                    }
                }
                b();
            }
        }

        public void onError(Throwable th) {
            if (this.a.isDisposed() || this.d) {
                RxJavaPlugins.onError(th);
                return;
            }
            if (th == null) {
                th = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
            }
            if (this.b.addThrowable(th)) {
                this.d = true;
                a();
            } else {
                RxJavaPlugins.onError(th);
            }
        }

        public void onComplete() {
            if (!this.a.isDisposed() && !this.d) {
                this.d = true;
                a();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (getAndIncrement() == 0) {
                b();
            }
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            ObservableEmitter<T> observableEmitter = this.a;
            SpscLinkedArrayQueue<T> spscLinkedArrayQueue = this.c;
            AtomicThrowable atomicThrowable = this.b;
            int i = 1;
            while (!observableEmitter.isDisposed()) {
                if (atomicThrowable.get() != null) {
                    spscLinkedArrayQueue.clear();
                    observableEmitter.onError(atomicThrowable.terminate());
                    return;
                }
                boolean z = this.d;
                Object poll = spscLinkedArrayQueue.poll();
                boolean z2 = poll == null;
                if (z && z2) {
                    observableEmitter.onComplete();
                    return;
                } else if (z2) {
                    i = addAndGet(-i);
                    if (i == 0) {
                        return;
                    }
                } else {
                    observableEmitter.onNext(poll);
                }
            }
            spscLinkedArrayQueue.clear();
        }

        public void setDisposable(Disposable disposable) {
            this.a.setDisposable(disposable);
        }

        public void setCancellable(Cancellable cancellable) {
            this.a.setCancellable(cancellable);
        }

        public boolean isDisposed() {
            return this.a.isDisposed();
        }
    }

    public ObservableCreate(ObservableOnSubscribe<T> observableOnSubscribe) {
        this.a = observableOnSubscribe;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super T> observer) {
        CreateEmitter createEmitter = new CreateEmitter(observer);
        observer.onSubscribe(createEmitter);
        try {
            this.a.subscribe(createEmitter);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            createEmitter.onError(th);
        }
    }
}
