package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.observables.GroupedObservable;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableGroupBy<T, K, V> extends AbstractObservableWithUpstream<T, GroupedObservable<K, V>> {
    final Function<? super T, ? extends K> a;
    final Function<? super T, ? extends V> b;
    final int c;
    final boolean d;

    public static final class GroupByObserver<T, K, V> extends AtomicInteger implements Observer<T>, Disposable {
        static final Object g = new Object();
        private static final long serialVersionUID = -3688291656102519502L;
        final Observer<? super GroupedObservable<K, V>> a;
        final Function<? super T, ? extends K> b;
        final Function<? super T, ? extends V> c;
        final int d;
        final boolean e;
        final Map<Object, GroupedUnicast<K, V>> f;
        Disposable h;
        final AtomicBoolean i = new AtomicBoolean();

        public GroupByObserver(Observer<? super GroupedObservable<K, V>> observer, Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2, int i2, boolean z) {
            this.a = observer;
            this.b = function;
            this.c = function2;
            this.d = i2;
            this.e = z;
            this.f = new ConcurrentHashMap();
            lazySet(1);
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.h, disposable)) {
                this.h = disposable;
                this.a.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            Object obj;
            try {
                Object apply = this.b.apply(t);
                if (apply != null) {
                    obj = apply;
                } else {
                    obj = g;
                }
                GroupedUnicast groupedUnicast = (GroupedUnicast) this.f.get(obj);
                if (groupedUnicast == null) {
                    if (!this.i.get()) {
                        groupedUnicast = GroupedUnicast.a(apply, this.d, this, this.e);
                        this.f.put(obj, groupedUnicast);
                        getAndIncrement();
                        this.a.onNext(groupedUnicast);
                    } else {
                        return;
                    }
                }
                try {
                    groupedUnicast.a(ObjectHelper.requireNonNull(this.c.apply(t), "The value supplied is null"));
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.h.dispose();
                    onError(th);
                }
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                this.h.dispose();
                onError(th2);
            }
        }

        public void onError(Throwable th) {
            ArrayList<GroupedUnicast> arrayList = new ArrayList<>(this.f.values());
            this.f.clear();
            for (GroupedUnicast a2 : arrayList) {
                a2.a(th);
            }
            this.a.onError(th);
        }

        public void onComplete() {
            ArrayList<GroupedUnicast> arrayList = new ArrayList<>(this.f.values());
            this.f.clear();
            for (GroupedUnicast a2 : arrayList) {
                a2.a();
            }
            this.a.onComplete();
        }

        public void dispose() {
            if (this.i.compareAndSet(false, true) && decrementAndGet() == 0) {
                this.h.dispose();
            }
        }

        public boolean isDisposed() {
            return this.i.get();
        }

        public void cancel(K k) {
            if (k == null) {
                k = g;
            }
            this.f.remove(k);
            if (decrementAndGet() == 0) {
                this.h.dispose();
            }
        }
    }

    static final class GroupedUnicast<K, T> extends GroupedObservable<K, T> {
        final State<T, K> a;

        public static <T, K> GroupedUnicast<K, T> a(K k, int i, GroupByObserver<?, K, T> groupByObserver, boolean z) {
            return new GroupedUnicast<>(k, new State(i, groupByObserver, k, z));
        }

        protected GroupedUnicast(K k, State<T, K> state) {
            super(k);
            this.a = state;
        }

        /* access modifiers changed from: protected */
        public void subscribeActual(Observer<? super T> observer) {
            this.a.subscribe(observer);
        }

        public void a(T t) {
            this.a.a(t);
        }

        public void a(Throwable th) {
            this.a.a(th);
        }

        public void a() {
            this.a.a();
        }
    }

    static final class State<T, K> extends AtomicInteger implements ObservableSource<T>, Disposable {
        private static final long serialVersionUID = -3852313036005250360L;
        final K a;
        final SpscLinkedArrayQueue<T> b;
        final GroupByObserver<?, K, T> c;
        final boolean d;
        volatile boolean e;
        Throwable f;
        final AtomicBoolean g = new AtomicBoolean();
        final AtomicBoolean h = new AtomicBoolean();
        final AtomicReference<Observer<? super T>> i = new AtomicReference<>();

        State(int i2, GroupByObserver<?, K, T> groupByObserver, K k, boolean z) {
            this.b = new SpscLinkedArrayQueue<>(i2);
            this.c = groupByObserver;
            this.a = k;
            this.d = z;
        }

        public void dispose() {
            if (this.g.compareAndSet(false, true) && getAndIncrement() == 0) {
                this.i.lazySet(null);
                this.c.cancel(this.a);
            }
        }

        public boolean isDisposed() {
            return this.g.get();
        }

        public void subscribe(Observer<? super T> observer) {
            if (this.h.compareAndSet(false, true)) {
                observer.onSubscribe(this);
                this.i.lazySet(observer);
                if (this.g.get()) {
                    this.i.lazySet(null);
                } else {
                    b();
                }
            } else {
                EmptyDisposable.error((Throwable) new IllegalStateException("Only one Observer allowed!"), observer);
            }
        }

        public void a(T t) {
            this.b.offer(t);
            b();
        }

        public void a(Throwable th) {
            this.f = th;
            this.e = true;
            b();
        }

        public void a() {
            this.e = true;
            b();
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            if (getAndIncrement() == 0) {
                SpscLinkedArrayQueue<T> spscLinkedArrayQueue = this.b;
                boolean z = this.d;
                Observer observer = (Observer) this.i.get();
                int i2 = 1;
                while (true) {
                    if (observer != null) {
                        while (true) {
                            boolean z2 = this.e;
                            Object poll = spscLinkedArrayQueue.poll();
                            boolean z3 = poll == null;
                            if (!a(z2, z3, observer, z)) {
                                if (z3) {
                                    break;
                                }
                                observer.onNext(poll);
                            } else {
                                return;
                            }
                        }
                    }
                    i2 = addAndGet(-i2);
                    if (i2 != 0) {
                        if (observer == null) {
                            observer = (Observer) this.i.get();
                        }
                    } else {
                        return;
                    }
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean a(boolean z, boolean z2, Observer<? super T> observer, boolean z3) {
            if (this.g.get()) {
                this.b.clear();
                this.c.cancel(this.a);
                this.i.lazySet(null);
                return true;
            }
            if (z) {
                if (!z3) {
                    Throwable th = this.f;
                    if (th != null) {
                        this.b.clear();
                        this.i.lazySet(null);
                        observer.onError(th);
                        return true;
                    } else if (z2) {
                        this.i.lazySet(null);
                        observer.onComplete();
                        return true;
                    }
                } else if (z2) {
                    Throwable th2 = this.f;
                    this.i.lazySet(null);
                    if (th2 != null) {
                        observer.onError(th2);
                    } else {
                        observer.onComplete();
                    }
                    return true;
                }
            }
            return false;
        }
    }

    public ObservableGroupBy(ObservableSource<T> observableSource, Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2, int i, boolean z) {
        super(observableSource);
        this.a = function;
        this.b = function2;
        this.c = i;
        this.d = z;
    }

    public void subscribeActual(Observer<? super GroupedObservable<K, V>> observer) {
        ObservableSource observableSource = this.source;
        GroupByObserver groupByObserver = new GroupByObserver(observer, this.a, this.b, this.c, this.d);
        observableSource.subscribe(groupByObserver);
    }
}
