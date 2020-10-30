package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableJoin<TLeft, TRight, TLeftEnd, TRightEnd, R> extends AbstractObservableWithUpstream<TLeft, R> {
    final ObservableSource<? extends TRight> a;
    final Function<? super TLeft, ? extends ObservableSource<TLeftEnd>> b;
    final Function<? super TRight, ? extends ObservableSource<TRightEnd>> c;
    final BiFunction<? super TLeft, ? super TRight, ? extends R> d;

    static final class JoinDisposable<TLeft, TRight, TLeftEnd, TRightEnd, R> extends AtomicInteger implements Disposable, JoinSupport {
        static final Integer n = Integer.valueOf(1);
        static final Integer o = Integer.valueOf(2);
        static final Integer p = Integer.valueOf(3);
        static final Integer q = Integer.valueOf(4);
        private static final long serialVersionUID = -6071216598687999801L;
        final Observer<? super R> a;
        final SpscLinkedArrayQueue<Object> b = new SpscLinkedArrayQueue<>(Observable.bufferSize());
        final CompositeDisposable c = new CompositeDisposable();
        final Map<Integer, TLeft> d = new LinkedHashMap();
        final Map<Integer, TRight> e = new LinkedHashMap();
        final AtomicReference<Throwable> f = new AtomicReference<>();
        final Function<? super TLeft, ? extends ObservableSource<TLeftEnd>> g;
        final Function<? super TRight, ? extends ObservableSource<TRightEnd>> h;
        final BiFunction<? super TLeft, ? super TRight, ? extends R> i;
        final AtomicInteger j;
        int k;
        int l;
        volatile boolean m;

        JoinDisposable(Observer<? super R> observer, Function<? super TLeft, ? extends ObservableSource<TLeftEnd>> function, Function<? super TRight, ? extends ObservableSource<TRightEnd>> function2, BiFunction<? super TLeft, ? super TRight, ? extends R> biFunction) {
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
                            int i3 = this.k;
                            this.k = i3 + 1;
                            this.d.put(Integer.valueOf(i3), poll);
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
                                for (Object apply : this.e.values()) {
                                    try {
                                        observer.onNext(ObjectHelper.requireNonNull(this.i.apply(poll, apply), "The resultSelector returned a null value"));
                                    } catch (Throwable th) {
                                        a(th, observer, spscLinkedArrayQueue);
                                        return;
                                    }
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
                                for (Object apply2 : this.d.values()) {
                                    try {
                                        observer.onNext(ObjectHelper.requireNonNull(this.i.apply(apply2, poll), "The resultSelector returned a null value"));
                                    } catch (Throwable th3) {
                                        a(th3, observer, spscLinkedArrayQueue);
                                        return;
                                    }
                                }
                            } catch (Throwable th4) {
                                a(th4, observer, spscLinkedArrayQueue);
                                return;
                            }
                        } else if (num == p) {
                            LeftRightEndObserver leftRightEndObserver3 = (LeftRightEndObserver) poll;
                            this.d.remove(Integer.valueOf(leftRightEndObserver3.c));
                            this.c.remove(leftRightEndObserver3);
                        } else {
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

    public ObservableJoin(ObservableSource<TLeft> observableSource, ObservableSource<? extends TRight> observableSource2, Function<? super TLeft, ? extends ObservableSource<TLeftEnd>> function, Function<? super TRight, ? extends ObservableSource<TRightEnd>> function2, BiFunction<? super TLeft, ? super TRight, ? extends R> biFunction) {
        super(observableSource);
        this.a = observableSource2;
        this.b = function;
        this.c = function2;
        this.d = biFunction;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super R> observer) {
        JoinDisposable joinDisposable = new JoinDisposable(observer, this.b, this.c, this.d);
        observer.onSubscribe(joinDisposable);
        LeftRightObserver leftRightObserver = new LeftRightObserver(joinDisposable, true);
        joinDisposable.c.add(leftRightObserver);
        LeftRightObserver leftRightObserver2 = new LeftRightObserver(joinDisposable, false);
        joinDisposable.c.add(leftRightObserver2);
        this.source.subscribe(leftRightObserver);
        this.a.subscribe(leftRightObserver2);
    }
}
