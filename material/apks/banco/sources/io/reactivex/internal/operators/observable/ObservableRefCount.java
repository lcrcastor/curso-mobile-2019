package io.reactivex.internal.operators.observable;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.observables.ConnectableObservable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

public final class ObservableRefCount<T> extends AbstractObservableWithUpstream<T, T> {
    final ConnectableObservable<? extends T> a;
    volatile CompositeDisposable b = new CompositeDisposable();
    final AtomicInteger c = new AtomicInteger();
    final ReentrantLock d = new ReentrantLock();

    final class ConnectionObserver extends AtomicReference<Disposable> implements Observer<T>, Disposable {
        private static final long serialVersionUID = 3813126992133394324L;
        final Observer<? super T> a;
        final CompositeDisposable b;
        final Disposable c;

        ConnectionObserver(Observer<? super T> observer, CompositeDisposable compositeDisposable, Disposable disposable) {
            this.a = observer;
            this.b = compositeDisposable;
            this.c = disposable;
        }

        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }

        public void onError(Throwable th) {
            a();
            this.a.onError(th);
        }

        public void onNext(T t) {
            this.a.onNext(t);
        }

        public void onComplete() {
            a();
            this.a.onComplete();
        }

        public void dispose() {
            DisposableHelper.dispose(this);
            this.c.dispose();
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) get());
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            ObservableRefCount.this.d.lock();
            try {
                if (ObservableRefCount.this.b == this.b) {
                    if (ObservableRefCount.this.a instanceof Disposable) {
                        ((Disposable) ObservableRefCount.this.a).dispose();
                    }
                    ObservableRefCount.this.b.dispose();
                    ObservableRefCount.this.b = new CompositeDisposable();
                    ObservableRefCount.this.c.set(0);
                }
            } finally {
                ObservableRefCount.this.d.unlock();
            }
        }
    }

    final class DisposeConsumer implements Consumer<Disposable> {
        private final Observer<? super T> b;
        private final AtomicBoolean c;

        DisposeConsumer(Observer<? super T> observer, AtomicBoolean atomicBoolean) {
            this.b = observer;
            this.c = atomicBoolean;
        }

        /* renamed from: a */
        public void accept(Disposable disposable) {
            try {
                ObservableRefCount.this.b.add(disposable);
                ObservableRefCount.this.a(this.b, ObservableRefCount.this.b);
            } finally {
                ObservableRefCount.this.d.unlock();
                this.c.set(false);
            }
        }
    }

    final class DisposeTask implements Runnable {
        private final CompositeDisposable b;

        DisposeTask(CompositeDisposable compositeDisposable) {
            this.b = compositeDisposable;
        }

        public void run() {
            ObservableRefCount.this.d.lock();
            try {
                if (ObservableRefCount.this.b == this.b && ObservableRefCount.this.c.decrementAndGet() == 0) {
                    if (ObservableRefCount.this.a instanceof Disposable) {
                        ((Disposable) ObservableRefCount.this.a).dispose();
                    }
                    ObservableRefCount.this.b.dispose();
                    ObservableRefCount.this.b = new CompositeDisposable();
                }
            } finally {
                ObservableRefCount.this.d.unlock();
            }
        }
    }

    public ObservableRefCount(ConnectableObservable<T> connectableObservable) {
        super(connectableObservable);
        this.a = connectableObservable;
    }

    public void subscribeActual(Observer<? super T> observer) {
        this.d.lock();
        if (this.c.incrementAndGet() == 1) {
            AtomicBoolean atomicBoolean = new AtomicBoolean(true);
            try {
                this.a.connect(a(observer, atomicBoolean));
            } finally {
                if (atomicBoolean.get()) {
                    this.d.unlock();
                }
            }
        } else {
            try {
                a(observer, this.b);
            } finally {
                this.d.unlock();
            }
        }
    }

    private Consumer<Disposable> a(Observer<? super T> observer, AtomicBoolean atomicBoolean) {
        return new DisposeConsumer(observer, atomicBoolean);
    }

    /* access modifiers changed from: 0000 */
    public void a(Observer<? super T> observer, CompositeDisposable compositeDisposable) {
        ConnectionObserver connectionObserver = new ConnectionObserver(observer, compositeDisposable, a(compositeDisposable));
        observer.onSubscribe(connectionObserver);
        this.a.subscribe((Observer<? super T>) connectionObserver);
    }

    private Disposable a(CompositeDisposable compositeDisposable) {
        return Disposables.fromRunnable(new DisposeTask(compositeDisposable));
    }
}
