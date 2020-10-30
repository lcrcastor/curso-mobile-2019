package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.Experimental;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.observers.BasicIntQueueDisposable;
import io.reactivex.plugins.RxJavaPlugins;

@Experimental
public final class ObservableDoFinally<T> extends AbstractObservableWithUpstream<T, T> {
    final Action a;

    static final class DoFinallyObserver<T> extends BasicIntQueueDisposable<T> implements Observer<T> {
        private static final long serialVersionUID = 4109457741734051389L;
        final Observer<? super T> a;
        final Action b;
        Disposable c;
        QueueDisposable<T> d;
        boolean e;

        DoFinallyObserver(Observer<? super T> observer, Action action) {
            this.a = observer;
            this.b = action;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.c, disposable)) {
                this.c = disposable;
                if (disposable instanceof QueueDisposable) {
                    this.d = (QueueDisposable) disposable;
                }
                this.a.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            this.a.onNext(t);
        }

        public void onError(Throwable th) {
            this.a.onError(th);
            a();
        }

        public void onComplete() {
            this.a.onComplete();
            a();
        }

        public void dispose() {
            this.c.dispose();
            a();
        }

        public boolean isDisposed() {
            return this.c.isDisposed();
        }

        public int requestFusion(int i) {
            QueueDisposable<T> queueDisposable = this.d;
            if (queueDisposable == null || (i & 4) != 0) {
                return 0;
            }
            int requestFusion = queueDisposable.requestFusion(i);
            if (requestFusion != 0) {
                boolean z = true;
                if (requestFusion != 1) {
                    z = false;
                }
                this.e = z;
            }
            return requestFusion;
        }

        public void clear() {
            this.d.clear();
        }

        public boolean isEmpty() {
            return this.d.isEmpty();
        }

        @Nullable
        public T poll() {
            T poll = this.d.poll();
            if (poll == null && this.e) {
                a();
            }
            return poll;
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (compareAndSet(0, 1)) {
                try {
                    this.b.run();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    RxJavaPlugins.onError(th);
                }
            }
        }
    }

    public ObservableDoFinally(ObservableSource<T> observableSource, Action action) {
        super(observableSource);
        this.a = action;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new DoFinallyObserver(observer, this.a));
    }
}
