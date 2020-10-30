package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ObservableBuffer<T, U extends Collection<? super T>> extends AbstractObservableWithUpstream<T, U> {
    final int a;
    final int b;
    final Callable<U> c;

    static final class BufferExactObserver<T, U extends Collection<? super T>> implements Observer<T>, Disposable {
        final Observer<? super U> a;
        final int b;
        final Callable<U> c;
        U d;
        int e;
        Disposable f;

        BufferExactObserver(Observer<? super U> observer, int i, Callable<U> callable) {
            this.a = observer;
            this.b = i;
            this.c = callable;
        }

        /* access modifiers changed from: 0000 */
        public boolean a() {
            try {
                this.d = (Collection) ObjectHelper.requireNonNull(this.c.call(), "Empty buffer supplied");
                return true;
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.d = null;
                if (this.f == null) {
                    EmptyDisposable.error(th, this.a);
                } else {
                    this.f.dispose();
                    this.a.onError(th);
                }
                return false;
            }
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f, disposable)) {
                this.f = disposable;
                this.a.onSubscribe(this);
            }
        }

        public void dispose() {
            this.f.dispose();
        }

        public boolean isDisposed() {
            return this.f.isDisposed();
        }

        public void onNext(T t) {
            U u = this.d;
            if (u != null) {
                u.add(t);
                int i = this.e + 1;
                this.e = i;
                if (i >= this.b) {
                    this.a.onNext(u);
                    this.e = 0;
                    a();
                }
            }
        }

        public void onError(Throwable th) {
            this.d = null;
            this.a.onError(th);
        }

        public void onComplete() {
            U u = this.d;
            this.d = null;
            if (u != null && !u.isEmpty()) {
                this.a.onNext(u);
            }
            this.a.onComplete();
        }
    }

    static final class BufferSkipObserver<T, U extends Collection<? super T>> extends AtomicBoolean implements Observer<T>, Disposable {
        private static final long serialVersionUID = -8223395059921494546L;
        final Observer<? super U> a;
        final int b;
        final int c;
        final Callable<U> d;
        Disposable e;
        final ArrayDeque<U> f = new ArrayDeque<>();
        long g;

        BufferSkipObserver(Observer<? super U> observer, int i, int i2, Callable<U> callable) {
            this.a = observer;
            this.b = i;
            this.c = i2;
            this.d = callable;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.e, disposable)) {
                this.e = disposable;
                this.a.onSubscribe(this);
            }
        }

        public void dispose() {
            this.e.dispose();
        }

        public boolean isDisposed() {
            return this.e.isDisposed();
        }

        public void onNext(T t) {
            long j = this.g;
            this.g = j + 1;
            if (j % ((long) this.c) == 0) {
                try {
                    this.f.offer((Collection) ObjectHelper.requireNonNull(this.d.call(), "The bufferSupplier returned a null collection. Null values are generally not allowed in 2.x operators and sources."));
                } catch (Throwable th) {
                    this.f.clear();
                    this.e.dispose();
                    this.a.onError(th);
                    return;
                }
            }
            Iterator it = this.f.iterator();
            while (it.hasNext()) {
                Collection collection = (Collection) it.next();
                collection.add(t);
                if (this.b <= collection.size()) {
                    it.remove();
                    this.a.onNext(collection);
                }
            }
        }

        public void onError(Throwable th) {
            this.f.clear();
            this.a.onError(th);
        }

        public void onComplete() {
            while (!this.f.isEmpty()) {
                this.a.onNext(this.f.poll());
            }
            this.a.onComplete();
        }
    }

    public ObservableBuffer(ObservableSource<T> observableSource, int i, int i2, Callable<U> callable) {
        super(observableSource);
        this.a = i;
        this.b = i2;
        this.c = callable;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super U> observer) {
        if (this.b == this.a) {
            BufferExactObserver bufferExactObserver = new BufferExactObserver(observer, this.a, this.c);
            if (bufferExactObserver.a()) {
                this.source.subscribe(bufferExactObserver);
                return;
            }
            return;
        }
        this.source.subscribe(new BufferSkipObserver(observer, this.a, this.b, this.c));
    }
}
