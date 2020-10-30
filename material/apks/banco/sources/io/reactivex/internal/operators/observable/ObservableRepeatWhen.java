package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.HalfSerializer;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableRepeatWhen<T> extends AbstractObservableWithUpstream<T, T> {
    final Function<? super Observable<Object>, ? extends ObservableSource<?>> a;

    static final class RepeatWhenObserver<T> extends AtomicInteger implements Observer<T>, Disposable {
        private static final long serialVersionUID = 802743776666017014L;
        final Observer<? super T> a;
        final AtomicInteger b = new AtomicInteger();
        final AtomicThrowable c = new AtomicThrowable();
        final Subject<Object> d;
        final InnerRepeatObserver e = new InnerRepeatObserver<>();
        final AtomicReference<Disposable> f = new AtomicReference<>();
        final ObservableSource<T> g;
        volatile boolean h;

        final class InnerRepeatObserver extends AtomicReference<Disposable> implements Observer<Object> {
            private static final long serialVersionUID = 3254781284376480842L;

            InnerRepeatObserver() {
            }

            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            public void onNext(Object obj) {
                RepeatWhenObserver.this.a();
            }

            public void onError(Throwable th) {
                RepeatWhenObserver.this.a(th);
            }

            public void onComplete() {
                RepeatWhenObserver.this.b();
            }
        }

        RepeatWhenObserver(Observer<? super T> observer, Subject<Object> subject, ObservableSource<T> observableSource) {
            this.a = observer;
            this.d = subject;
            this.g = observableSource;
        }

        public void onSubscribe(Disposable disposable) {
            DisposableHelper.replace(this.f, disposable);
        }

        public void onNext(T t) {
            HalfSerializer.onNext(this.a, t, (AtomicInteger) this, this.c);
        }

        public void onError(Throwable th) {
            DisposableHelper.dispose(this.e);
            HalfSerializer.onError(this.a, th, (AtomicInteger) this, this.c);
        }

        public void onComplete() {
            this.h = false;
            this.d.onNext(Integer.valueOf(0));
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) this.f.get());
        }

        public void dispose() {
            DisposableHelper.dispose(this.f);
            DisposableHelper.dispose(this.e);
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            c();
        }

        /* access modifiers changed from: 0000 */
        public void a(Throwable th) {
            DisposableHelper.dispose(this.f);
            HalfSerializer.onError(this.a, th, (AtomicInteger) this, this.c);
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            DisposableHelper.dispose(this.f);
            HalfSerializer.onComplete(this.a, (AtomicInteger) this, this.c);
        }

        /* access modifiers changed from: 0000 */
        public void c() {
            if (this.b.getAndIncrement() == 0) {
                while (!isDisposed()) {
                    if (!this.h) {
                        this.h = true;
                        this.g.subscribe(this);
                    }
                    if (this.b.decrementAndGet() == 0) {
                    }
                }
            }
        }
    }

    public ObservableRepeatWhen(ObservableSource<T> observableSource, Function<? super Observable<Object>, ? extends ObservableSource<?>> function) {
        super(observableSource);
        this.a = function;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super T> observer) {
        Subject serialized = PublishSubject.create().toSerialized();
        try {
            ObservableSource observableSource = (ObservableSource) ObjectHelper.requireNonNull(this.a.apply(serialized), "The handler returned a null ObservableSource");
            RepeatWhenObserver repeatWhenObserver = new RepeatWhenObserver(observer, serialized, this.source);
            observer.onSubscribe(repeatWhenObserver);
            observableSource.subscribe(repeatWhenObserver.e);
            repeatWhenObserver.c();
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, observer);
        }
    }
}
