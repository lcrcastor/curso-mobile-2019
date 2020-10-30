package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiPredicate;
import io.reactivex.internal.disposables.ArrayCompositeDisposable;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import java.util.concurrent.atomic.AtomicInteger;

public final class ObservableSequenceEqual<T> extends Observable<Boolean> {
    final ObservableSource<? extends T> a;
    final ObservableSource<? extends T> b;
    final BiPredicate<? super T, ? super T> c;
    final int d;

    static final class EqualCoordinator<T> extends AtomicInteger implements Disposable {
        private static final long serialVersionUID = -6178010334400373240L;
        final Observer<? super Boolean> a;
        final BiPredicate<? super T, ? super T> b;
        final ArrayCompositeDisposable c = new ArrayCompositeDisposable(2);
        final ObservableSource<? extends T> d;
        final ObservableSource<? extends T> e;
        final EqualObserver<T>[] f;
        volatile boolean g;
        T h;
        T i;

        EqualCoordinator(Observer<? super Boolean> observer, int i2, ObservableSource<? extends T> observableSource, ObservableSource<? extends T> observableSource2, BiPredicate<? super T, ? super T> biPredicate) {
            this.a = observer;
            this.d = observableSource;
            this.e = observableSource2;
            this.b = biPredicate;
            EqualObserver<T>[] equalObserverArr = new EqualObserver[2];
            this.f = equalObserverArr;
            equalObserverArr[0] = new EqualObserver<>(this, 0, i2);
            equalObserverArr[1] = new EqualObserver<>(this, 1, i2);
        }

        /* access modifiers changed from: 0000 */
        public boolean a(Disposable disposable, int i2) {
            return this.c.setResource(i2, disposable);
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            EqualObserver<T>[] equalObserverArr = this.f;
            this.d.subscribe(equalObserverArr[0]);
            this.e.subscribe(equalObserverArr[1]);
        }

        public void dispose() {
            if (!this.g) {
                this.g = true;
                this.c.dispose();
                if (getAndIncrement() == 0) {
                    EqualObserver<T>[] equalObserverArr = this.f;
                    equalObserverArr[0].b.clear();
                    equalObserverArr[1].b.clear();
                }
            }
        }

        public boolean isDisposed() {
            return this.g;
        }

        /* access modifiers changed from: 0000 */
        public void a(SpscLinkedArrayQueue<T> spscLinkedArrayQueue, SpscLinkedArrayQueue<T> spscLinkedArrayQueue2) {
            this.g = true;
            spscLinkedArrayQueue.clear();
            spscLinkedArrayQueue2.clear();
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            if (getAndIncrement() == 0) {
                EqualObserver<T>[] equalObserverArr = this.f;
                EqualObserver<T> equalObserver = equalObserverArr[0];
                SpscLinkedArrayQueue<T> spscLinkedArrayQueue = equalObserver.b;
                EqualObserver<T> equalObserver2 = equalObserverArr[1];
                SpscLinkedArrayQueue<T> spscLinkedArrayQueue2 = equalObserver2.b;
                int i2 = 1;
                while (!this.g) {
                    boolean z = equalObserver.d;
                    if (z) {
                        Throwable th = equalObserver.e;
                        if (th != null) {
                            a(spscLinkedArrayQueue, spscLinkedArrayQueue2);
                            this.a.onError(th);
                            return;
                        }
                    }
                    boolean z2 = equalObserver2.d;
                    if (z2) {
                        Throwable th2 = equalObserver2.e;
                        if (th2 != null) {
                            a(spscLinkedArrayQueue, spscLinkedArrayQueue2);
                            this.a.onError(th2);
                            return;
                        }
                    }
                    if (this.h == null) {
                        this.h = spscLinkedArrayQueue.poll();
                    }
                    boolean z3 = this.h == null;
                    if (this.i == null) {
                        this.i = spscLinkedArrayQueue2.poll();
                    }
                    boolean z4 = this.i == null;
                    if (z && z2 && z3 && z4) {
                        this.a.onNext(Boolean.valueOf(true));
                        this.a.onComplete();
                        return;
                    } else if (!z || !z2 || z3 == z4) {
                        if (!z3 && !z4) {
                            try {
                                if (!this.b.test(this.h, this.i)) {
                                    a(spscLinkedArrayQueue, spscLinkedArrayQueue2);
                                    this.a.onNext(Boolean.valueOf(false));
                                    this.a.onComplete();
                                    return;
                                }
                                this.h = null;
                                this.i = null;
                            } catch (Throwable th3) {
                                Exceptions.throwIfFatal(th3);
                                a(spscLinkedArrayQueue, spscLinkedArrayQueue2);
                                this.a.onError(th3);
                                return;
                            }
                        }
                        if (z3 || z4) {
                            i2 = addAndGet(-i2);
                            if (i2 == 0) {
                                return;
                            }
                        }
                    } else {
                        a(spscLinkedArrayQueue, spscLinkedArrayQueue2);
                        this.a.onNext(Boolean.valueOf(false));
                        this.a.onComplete();
                        return;
                    }
                }
                spscLinkedArrayQueue.clear();
                spscLinkedArrayQueue2.clear();
            }
        }
    }

    static final class EqualObserver<T> implements Observer<T> {
        final EqualCoordinator<T> a;
        final SpscLinkedArrayQueue<T> b;
        final int c;
        volatile boolean d;
        Throwable e;

        EqualObserver(EqualCoordinator<T> equalCoordinator, int i, int i2) {
            this.a = equalCoordinator;
            this.c = i;
            this.b = new SpscLinkedArrayQueue<>(i2);
        }

        public void onSubscribe(Disposable disposable) {
            this.a.a(disposable, this.c);
        }

        public void onNext(T t) {
            this.b.offer(t);
            this.a.b();
        }

        public void onError(Throwable th) {
            this.e = th;
            this.d = true;
            this.a.b();
        }

        public void onComplete() {
            this.d = true;
            this.a.b();
        }
    }

    public ObservableSequenceEqual(ObservableSource<? extends T> observableSource, ObservableSource<? extends T> observableSource2, BiPredicate<? super T, ? super T> biPredicate, int i) {
        this.a = observableSource;
        this.b = observableSource2;
        this.c = biPredicate;
        this.d = i;
    }

    public void subscribeActual(Observer<? super Boolean> observer) {
        EqualCoordinator equalCoordinator = new EqualCoordinator(observer, this.d, this.a, this.b, this.c);
        observer.onSubscribe(equalCoordinator);
        equalCoordinator.a();
    }
}
