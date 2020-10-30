package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.subjects.UnicastSubject;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public final class ObservableWindow<T> extends AbstractObservableWithUpstream<T, Observable<T>> {
    final long a;
    final long b;
    final int c;

    static final class WindowExactObserver<T> extends AtomicInteger implements Observer<T>, Disposable, Runnable {
        private static final long serialVersionUID = -7481782523886138128L;
        final Observer<? super Observable<T>> a;
        final long b;
        final int c;
        long d;
        Disposable e;
        UnicastSubject<T> f;
        volatile boolean g;

        WindowExactObserver(Observer<? super Observable<T>> observer, long j, int i) {
            this.a = observer;
            this.b = j;
            this.c = i;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.e, disposable)) {
                this.e = disposable;
                this.a.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            UnicastSubject<T> unicastSubject = this.f;
            if (unicastSubject == null && !this.g) {
                unicastSubject = UnicastSubject.create(this.c, this);
                this.f = unicastSubject;
                this.a.onNext(unicastSubject);
            }
            if (unicastSubject != null) {
                unicastSubject.onNext(t);
                long j = this.d + 1;
                this.d = j;
                if (j >= this.b) {
                    this.d = 0;
                    this.f = null;
                    unicastSubject.onComplete();
                    if (this.g) {
                        this.e.dispose();
                    }
                }
            }
        }

        public void onError(Throwable th) {
            UnicastSubject<T> unicastSubject = this.f;
            if (unicastSubject != null) {
                this.f = null;
                unicastSubject.onError(th);
            }
            this.a.onError(th);
        }

        public void onComplete() {
            UnicastSubject<T> unicastSubject = this.f;
            if (unicastSubject != null) {
                this.f = null;
                unicastSubject.onComplete();
            }
            this.a.onComplete();
        }

        public void dispose() {
            this.g = true;
        }

        public boolean isDisposed() {
            return this.g;
        }

        public void run() {
            if (this.g) {
                this.e.dispose();
            }
        }
    }

    static final class WindowSkipObserver<T> extends AtomicBoolean implements Observer<T>, Disposable, Runnable {
        private static final long serialVersionUID = 3366976432059579510L;
        final Observer<? super Observable<T>> a;
        final long b;
        final long c;
        final int d;
        final ArrayDeque<UnicastSubject<T>> e;
        long f;
        volatile boolean g;
        long h;
        Disposable i;
        final AtomicInteger j = new AtomicInteger();

        WindowSkipObserver(Observer<? super Observable<T>> observer, long j2, long j3, int i2) {
            this.a = observer;
            this.b = j2;
            this.c = j3;
            this.d = i2;
            this.e = new ArrayDeque<>();
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.i, disposable)) {
                this.i = disposable;
                this.a.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            ArrayDeque<UnicastSubject<T>> arrayDeque = this.e;
            long j2 = this.f;
            long j3 = this.c;
            if (j2 % j3 == 0 && !this.g) {
                this.j.getAndIncrement();
                UnicastSubject create = UnicastSubject.create(this.d, this);
                arrayDeque.offer(create);
                this.a.onNext(create);
            }
            long j4 = this.h + 1;
            Iterator it = arrayDeque.iterator();
            while (it.hasNext()) {
                ((UnicastSubject) it.next()).onNext(t);
            }
            if (j4 >= this.b) {
                ((UnicastSubject) arrayDeque.poll()).onComplete();
                if (!arrayDeque.isEmpty() || !this.g) {
                    this.h = j4 - j3;
                } else {
                    this.i.dispose();
                    return;
                }
            } else {
                this.h = j4;
            }
            this.f = j2 + 1;
        }

        public void onError(Throwable th) {
            ArrayDeque<UnicastSubject<T>> arrayDeque = this.e;
            while (!arrayDeque.isEmpty()) {
                ((UnicastSubject) arrayDeque.poll()).onError(th);
            }
            this.a.onError(th);
        }

        public void onComplete() {
            ArrayDeque<UnicastSubject<T>> arrayDeque = this.e;
            while (!arrayDeque.isEmpty()) {
                ((UnicastSubject) arrayDeque.poll()).onComplete();
            }
            this.a.onComplete();
        }

        public void dispose() {
            this.g = true;
        }

        public boolean isDisposed() {
            return this.g;
        }

        public void run() {
            if (this.j.decrementAndGet() == 0 && this.g) {
                this.i.dispose();
            }
        }
    }

    public ObservableWindow(ObservableSource<T> observableSource, long j, long j2, int i) {
        super(observableSource);
        this.a = j;
        this.b = j2;
        this.c = i;
    }

    public void subscribeActual(Observer<? super Observable<T>> observer) {
        if (this.a == this.b) {
            this.source.subscribe(new WindowExactObserver(observer, this.a, this.c));
            return;
        }
        ObservableSource observableSource = this.source;
        WindowSkipObserver windowSkipObserver = new WindowSkipObserver(observer, this.a, this.b, this.c);
        observableSource.subscribe(windowSkipObserver);
    }
}
