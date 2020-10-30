package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subjects.UnicastSubject;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableGroupJoin<TLeft, TRight, TLeftEnd, TRightEnd, R> extends AbstractObservableWithUpstream<TLeft, R> {
    final ObservableSource<? extends TRight> a;
    final Function<? super TLeft, ? extends ObservableSource<TLeftEnd>> b;
    final Function<? super TRight, ? extends ObservableSource<TRightEnd>> c;
    final BiFunction<? super TLeft, ? super Observable<TRight>, ? extends R> d;

    static final class GroupJoinDisposable<TLeft, TRight, TLeftEnd, TRightEnd, R> extends AtomicInteger implements Disposable, JoinSupport {
        static final Integer n = Integer.valueOf(1);
        static final Integer o = Integer.valueOf(2);
        static final Integer p = Integer.valueOf(3);
        static final Integer q = Integer.valueOf(4);
        private static final long serialVersionUID = -6071216598687999801L;
        final Observer<? super R> a;
        final SpscLinkedArrayQueue<Object> b = new SpscLinkedArrayQueue<>(Observable.bufferSize());
        final CompositeDisposable c = new CompositeDisposable();
        final Map<Integer, UnicastSubject<TRight>> d = new LinkedHashMap();
        final Map<Integer, TRight> e = new LinkedHashMap();
        final AtomicReference<Throwable> f = new AtomicReference<>();
        final Function<? super TLeft, ? extends ObservableSource<TLeftEnd>> g;
        final Function<? super TRight, ? extends ObservableSource<TRightEnd>> h;
        final BiFunction<? super TLeft, ? super Observable<TRight>, ? extends R> i;
        final AtomicInteger j;
        int k;
        int l;
        volatile boolean m;

        GroupJoinDisposable(Observer<? super R> observer, Function<? super TLeft, ? extends ObservableSource<TLeftEnd>> function, Function<? super TRight, ? extends ObservableSource<TRightEnd>> function2, BiFunction<? super TLeft, ? super Observable<TRight>, ? extends R> biFunction) {
            this.a = observer;
            this.g = function;
            this.h = function2;
            this.i = biFunction;
            this.j = new AtomicInteger(2);
        }

        public void dispose() {
            if (!this.m) {
                this.m = true;
                a();
                if (getAndIncrement() == 0) {
                    this.b.clear();
                }
            }
        }

        public boolean isDisposed() {
            return this.m;
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            this.c.dispose();
        }

        /* access modifiers changed from: 0000 */
        public void a(Observer<?> observer) {
            Throwable terminate = ExceptionHelper.terminate(this.f);
            for (UnicastSubject onError : this.d.values()) {
                onError.onError(terminate);
            }
            this.d.clear();
            this.e.clear();
            observer.onError(terminate);
        }

        /* access modifiers changed from: 0000 */
        public void a(Throwable th, Observer<?> observer, SpscLinkedArrayQueue<?> spscLinkedArrayQueue) {
            Exceptions.throwIfFatal(th);
            ExceptionHelper.addThrowable(this.f, th);
            spscLinkedArrayQueue.clear();
            a();
            a(observer);
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            if (getAndIncrement() == 0) {
                SpscLinkedArrayQueue<Object> spscLinkedArrayQueue = this.b;
                Observer<? super R> observer = this.a;
                int i2 = 1;
                while (!this.m) {
                    if (((Throwable) this.f.get()) != null) {
                        spscLinkedArrayQueue.clear();
                        a();
                        a(observer);
                        return;
                    }
                    boolean z = this.j.get() == 0;
                    Integer num = (Integer) spscLinkedArrayQueue.poll();
                    boolean z2 = num == null;
                    if (z && z2) {
                        for (UnicastSubject onComplete : this.d.values()) {
                            onComplete.onComplete();
                        }
                        this.d.clear();
                        this.e.clear();
                        this.c.dispose();
                        observer.onComplete();
                        return;
                    } else if (z2) {
                        i2 = addAndGet(-i2);
                        if (i2 == 0) {
                            return;
                        }
                    } else {
                        Object poll = spscLinkedArrayQueue.poll();
                        if (num == n) {
                            UnicastSubject create = UnicastSubject.create();
                            int i3 = this.k;
                            this.k = i3 + 1;
                            this.d.put(Integer.valueOf(i3), create);
                            try {
                                ObservableSource observableSource = (ObservableSource) ObjectHelper.requireNonNull(this.g.apply(poll), "The leftEnd returned a null ObservableSource");
                                LeftRightEndObserver leftRightEndObserver = new LeftRightEndObserver(this, true, i3);
                                this.c.add(leftRightEndObserver);
                                observableSource.subscribe(leftRightEndObserver);
                                if (((Throwable) this.f.get()) != null) {
                                    spscLinkedArrayQueue.clear();
                                    a();
                                    a(observer);
                                    return;
                                }
                                try {
                                    observer.onNext(ObjectHelper.requireNonNull(this.i.apply(poll, create), "The resultSelector returned a null value"));
                                    for (Object onNext : this.e.values()) {
                                        create.onNext(onNext);
                                    }
                                } catch (Throwable th) {
                                    a(th, observer, spscLinkedArrayQueue);
                                    return;
                                }
                            } catch (Throwable th2) {
                                a(th2, observer, spscLinkedArrayQueue);
                                return;
                            }
                        } else if (num == o) {
                            int i4 = this.l;
                            this.l = i4 + 1;
                            this.e.put(Integer.valueOf(i4), poll);
                            try {
                                ObservableSource observableSource2 = (ObservableSource) ObjectHelper.requireNonNull(this.h.apply(poll), "The rightEnd returned a null ObservableSource");
                                LeftRightEndObserver leftRightEndObserver2 = new LeftRightEndObserver(this, false, i4);
                                this.c.add(leftRightEndObserver2);
                                observableSource2.subscribe(leftRightEndObserver2);
                                if (((Throwable) this.f.get()) != null) {
                                    spscLinkedArrayQueue.clear();
                                    a();
                                    a(observer);
                                    return;
                                }
                                for (UnicastSubject onNext2 : this.d.values()) {
                                    onNext2.onNext(poll);
                                }
                            } catch (Throwable th3) {
                                a(th3, observer, spscLinkedArrayQueue);
                                return;
                            }
                        } else if (num == p) {
                            LeftRightEndObserver leftRightEndObserver3 = (LeftRightEndObserver) poll;
                            UnicastSubject unicastSubject = (UnicastSubject) this.d.remove(Integer.valueOf(leftRightEndObserver3.c));
                            this.c.remove(leftRightEndObserver3);
                            if (unicastSubject != null) {
                                unicastSubject.onComplete();
                            }
                        } else if (num == q) {
                            LeftRightEndObserver leftRightEndObserver4 = (LeftRightEndObserver) poll;
                            this.e.remove(Integer.valueOf(leftRightEndObserver4.c));
                            this.c.remove(leftRightEndObserver4);
                        }
                    }
                }
                spscLinkedArrayQueue.clear();
            }
        }

        public void a(Throwable th) {
            if (ExceptionHelper.addThrowable(this.f, th)) {
                this.j.decrementAndGet();
                b();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        public void a(LeftRightObserver leftRightObserver) {
            this.c.delete(leftRightObserver);
            this.j.decrementAndGet();
            b();
        }

        public void a(boolean z, Object obj) {
            synchronized (this) {
                this.b.offer(z ? n : o, obj);
            }
            b();
        }

        public void a(boolean z, LeftRightEndObserver leftRightEndObserver) {
            synchronized (this) {
                this.b.offer(z ? p : q, leftRightEndObserver);
            }
            b();
        }

        public void b(Throwable th) {
            if (ExceptionHelper.addThrowable(this.f, th)) {
                b();
            } else {
                RxJavaPlugins.onError(th);
            }
        }
    }

    interface JoinSupport {
        void a(LeftRightObserver leftRightObserver);

        void a(Throwable th);

        void a(boolean z, LeftRightEndObserver leftRightEndObserver);

        void a(boolean z, Object obj);

        void b(Throwable th);
    }

    static final class LeftRightEndObserver extends AtomicReference<Disposable> implements Observer<Object>, Disposable {
        private static final long serialVersionUID = 1883890389173668373L;
        final JoinSupport a;
        final boolean b;
        final int c;

        LeftRightEndObserver(JoinSupport joinSupport, boolean z, int i) {
            this.a = joinSupport;
            this.b = z;
            this.c = i;
        }

        public void dispose() {
            DisposableHelper.dispose(this);
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) get());
        }

        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }

        public void onNext(Object obj) {
            if (DisposableHelper.dispose(this)) {
                this.a.a(this.b, this);
            }
        }

        public void onError(Throwable th) {
            this.a.b(th);
        }

        public void onComplete() {
            this.a.a(this.b, this);
        }
    }

    static final class LeftRightObserver extends AtomicReference<Disposable> implements Observer<Object>, Disposable {
        private static final long serialVersionUID = 1883890389173668373L;
        final JoinSupport a;
        final boolean b;

        LeftRightObserver(JoinSupport joinSupport, boolean z) {
            this.a = joinSupport;
            this.b = z;
        }

        public void dispose() {
            DisposableHelper.dispose(this);
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) get());
        }

        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }

        public void onNext(Object obj) {
            this.a.a(this.b, obj);
        }

        public void onError(Throwable th) {
            this.a.a(th);
        }

        public void onComplete() {
            this.a.a(this);
        }
    }

    public ObservableGroupJoin(ObservableSource<TLeft> observableSource, ObservableSource<? extends TRight> observableSource2, Function<? super TLeft, ? extends ObservableSource<TLeftEnd>> function, Function<? super TRight, ? extends ObservableSource<TRightEnd>> function2, BiFunction<? super TLeft, ? super Observable<TRight>, ? extends R> biFunction) {
        super(observableSource);
        this.a = observableSource2;
        this.b = function;
        this.c = function2;
        this.d = biFunction;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super R> observer) {
        GroupJoinDisposable groupJoinDisposable = new GroupJoinDisposable(observer, this.b, this.c, this.d);
        observer.onSubscribe(groupJoinDisposable);
        LeftRightObserver leftRightObserver = new LeftRightObserver(groupJoinDisposable, true);
        groupJoinDisposable.c.add(leftRightObserver);
        LeftRightObserver leftRightObserver2 = new LeftRightObserver(groupJoinDisposable, false);
        groupJoinDisposable.c.add(leftRightObserver2);
        this.source.subscribe(leftRightObserver);
        this.a.subscribe(leftRightObserver2);
    }
}
