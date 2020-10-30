package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.observers.SerializedObserver;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableSampleWithObservable<T> extends AbstractObservableWithUpstream<T, T> {
    final ObservableSource<?> a;
    final boolean b;

    static final class SampleMainEmitLast<T> extends SampleMainObserver<T> {
        private static final long serialVersionUID = -3029755663834015785L;
        final AtomicInteger a = new AtomicInteger();
        volatile boolean b;

        SampleMainEmitLast(Observer<? super T> observer, ObservableSource<?> observableSource) {
            super(observer, observableSource);
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            this.b = true;
            if (this.a.getAndIncrement() == 0) {
                e();
                this.c.onComplete();
            }
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            this.b = true;
            if (this.a.getAndIncrement() == 0) {
                e();
                this.c.onComplete();
            }
        }

        /* access modifiers changed from: 0000 */
        public void c() {
            if (this.a.getAndIncrement() == 0) {
                do {
                    boolean z = this.b;
                    e();
                    if (z) {
                        this.c.onComplete();
                        return;
                    }
                } while (this.a.decrementAndGet() != 0);
            }
        }
    }

    static final class SampleMainNoLast<T> extends SampleMainObserver<T> {
        private static final long serialVersionUID = -3029755663834015785L;

        SampleMainNoLast(Observer<? super T> observer, ObservableSource<?> observableSource) {
            super(observer, observableSource);
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            this.c.onComplete();
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            this.c.onComplete();
        }

        /* access modifiers changed from: 0000 */
        public void c() {
            e();
        }
    }

    static abstract class SampleMainObserver<T> extends AtomicReference<T> implements Observer<T>, Disposable {
        private static final long serialVersionUID = -3517602651313910099L;
        final Observer<? super T> c;
        final ObservableSource<?> d;
        final AtomicReference<Disposable> e = new AtomicReference<>();
        Disposable f;

        /* access modifiers changed from: 0000 */
        public abstract void a();

        /* access modifiers changed from: 0000 */
        public abstract void b();

        /* access modifiers changed from: 0000 */
        public abstract void c();

        SampleMainObserver(Observer<? super T> observer, ObservableSource<?> observableSource) {
            this.c = observer;
            this.d = observableSource;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f, disposable)) {
                this.f = disposable;
                this.c.onSubscribe(this);
                if (this.e.get() == null) {
                    this.d.subscribe(new SamplerObserver(this));
                }
            }
        }

        public void onNext(T t) {
            lazySet(t);
        }

        public void onError(Throwable th) {
            DisposableHelper.dispose(this.e);
            this.c.onError(th);
        }

        public void onComplete() {
            DisposableHelper.dispose(this.e);
            a();
        }

        /* access modifiers changed from: 0000 */
        public boolean a(Disposable disposable) {
            return DisposableHelper.setOnce(this.e, disposable);
        }

        public void dispose() {
            DisposableHelper.dispose(this.e);
            this.f.dispose();
        }

        public boolean isDisposed() {
            return this.e.get() == DisposableHelper.DISPOSED;
        }

        public void a(Throwable th) {
            this.f.dispose();
            this.c.onError(th);
        }

        public void d() {
            this.f.dispose();
            b();
        }

        /* access modifiers changed from: 0000 */
        public void e() {
            Object andSet = getAndSet(null);
            if (andSet != null) {
                this.c.onNext(andSet);
            }
        }
    }

    static final class SamplerObserver<T> implements Observer<Object> {
        final SampleMainObserver<T> a;

        SamplerObserver(SampleMainObserver<T> sampleMainObserver) {
            this.a = sampleMainObserver;
        }

        public void onSubscribe(Disposable disposable) {
            this.a.a(disposable);
        }

        public void onNext(Object obj) {
            this.a.c();
        }

        public void onError(Throwable th) {
            this.a.a(th);
        }

        public void onComplete() {
            this.a.d();
        }
    }

    public ObservableSampleWithObservable(ObservableSource<T> observableSource, ObservableSource<?> observableSource2, boolean z) {
        super(observableSource);
        this.a = observableSource2;
        this.b = z;
    }

    public void subscribeActual(Observer<? super T> observer) {
        SerializedObserver serializedObserver = new SerializedObserver(observer);
        if (this.b) {
            this.source.subscribe(new SampleMainEmitLast(serializedObserver, this.a));
        } else {
            this.source.subscribe(new SampleMainNoLast(serializedObserver, this.a));
        }
    }
}
