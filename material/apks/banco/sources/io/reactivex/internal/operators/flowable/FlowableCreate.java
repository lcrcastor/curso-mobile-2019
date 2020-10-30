package io.reactivex.internal.operators.flowable;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Cancellable;
import io.reactivex.internal.disposables.CancellableDisposable;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableCreate<T> extends Flowable<T> {
    final FlowableOnSubscribe<T> b;
    final BackpressureStrategy c;

    static abstract class BaseEmitter<T> extends AtomicLong implements FlowableEmitter<T>, Subscription {
        private static final long serialVersionUID = 7326289992464377023L;
        final Subscriber<? super T> a;
        final SequentialDisposable b = new SequentialDisposable();

        /* access modifiers changed from: 0000 */
        public void a() {
        }

        /* access modifiers changed from: 0000 */
        public void b() {
        }

        BaseEmitter(Subscriber<? super T> subscriber) {
            this.a = subscriber;
        }

        public void onComplete() {
            if (!isCancelled()) {
                try {
                    this.a.onComplete();
                } finally {
                    this.b.dispose();
                }
            }
        }

        public void onError(Throwable th) {
            if (th == null) {
                th = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
            }
            if (isCancelled()) {
                RxJavaPlugins.onError(th);
                return;
            }
            try {
                this.a.onError(th);
            } finally {
                this.b.dispose();
            }
        }

        public final void cancel() {
            this.b.dispose();
            a();
        }

        public final boolean isCancelled() {
            return this.b.isDisposed();
        }

        public final void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this, j);
                b();
            }
        }

        public final void setDisposable(Disposable disposable) {
            this.b.update(disposable);
        }

        public final void setCancellable(Cancellable cancellable) {
            setDisposable(new CancellableDisposable(cancellable));
        }

        public final long requested() {
            return get();
        }

        public final FlowableEmitter<T> serialize() {
            return new SerializedEmitter(this);
        }
    }

    static final class BufferAsyncEmitter<T> extends BaseEmitter<T> {
        private static final long serialVersionUID = 2427151001689639875L;
        final SpscLinkedArrayQueue<T> c;
        Throwable d;
        volatile boolean e;
        final AtomicInteger f = new AtomicInteger();

        BufferAsyncEmitter(Subscriber<? super T> subscriber, int i) {
            super(subscriber);
            this.c = new SpscLinkedArrayQueue<>(i);
        }

        public void onNext(T t) {
            if (!this.e && !isCancelled()) {
                if (t == null) {
                    onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
                    return;
                }
                this.c.offer(t);
                c();
            }
        }

        public void onError(Throwable th) {
            if (this.e || isCancelled()) {
                RxJavaPlugins.onError(th);
                return;
            }
            if (th == null) {
                th = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
            }
            this.d = th;
            this.e = true;
            c();
        }

        public void onComplete() {
            this.e = true;
            c();
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            c();
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (this.f.getAndIncrement() == 0) {
                this.c.clear();
            }
        }

        /* access modifiers changed from: 0000 */
        public void c() {
            if (this.f.getAndIncrement() == 0) {
                Subscriber subscriber = this.a;
                SpscLinkedArrayQueue<T> spscLinkedArrayQueue = this.c;
                int i = 1;
                do {
                    long j = get();
                    long j2 = 0;
                    while (j2 != j) {
                        if (isCancelled()) {
                            spscLinkedArrayQueue.clear();
                            return;
                        }
                        boolean z = this.e;
                        Object poll = spscLinkedArrayQueue.poll();
                        boolean z2 = poll == null;
                        if (z && z2) {
                            Throwable th = this.d;
                            if (th != null) {
                                super.onError(th);
                            } else {
                                super.onComplete();
                            }
                            return;
                        } else if (z2) {
                            break;
                        } else {
                            subscriber.onNext(poll);
                            j2++;
                        }
                    }
                    if (j2 == j) {
                        if (isCancelled()) {
                            spscLinkedArrayQueue.clear();
                            return;
                        }
                        boolean z3 = this.e;
                        boolean isEmpty = spscLinkedArrayQueue.isEmpty();
                        if (z3 && isEmpty) {
                            Throwable th2 = this.d;
                            if (th2 != null) {
                                super.onError(th2);
                            } else {
                                super.onComplete();
                            }
                            return;
                        }
                    }
                    if (j2 != 0) {
                        BackpressureHelper.produced(this, j2);
                    }
                    i = this.f.addAndGet(-i);
                } while (i != 0);
            }
        }
    }

    static final class DropAsyncEmitter<T> extends NoOverflowBaseAsyncEmitter<T> {
        private static final long serialVersionUID = 8360058422307496563L;

        /* access modifiers changed from: 0000 */
        public void c() {
        }

        DropAsyncEmitter(Subscriber<? super T> subscriber) {
            super(subscriber);
        }
    }

    static final class ErrorAsyncEmitter<T> extends NoOverflowBaseAsyncEmitter<T> {
        private static final long serialVersionUID = 338953216916120960L;

        ErrorAsyncEmitter(Subscriber<? super T> subscriber) {
            super(subscriber);
        }

        /* access modifiers changed from: 0000 */
        public void c() {
            onError(new MissingBackpressureException("create: could not emit value due to lack of requests"));
        }
    }

    static final class LatestAsyncEmitter<T> extends BaseEmitter<T> {
        private static final long serialVersionUID = 4023437720691792495L;
        final AtomicReference<T> c = new AtomicReference<>();
        Throwable d;
        volatile boolean e;
        final AtomicInteger f = new AtomicInteger();

        LatestAsyncEmitter(Subscriber<? super T> subscriber) {
            super(subscriber);
        }

        public void onNext(T t) {
            if (!this.e && !isCancelled()) {
                if (t == null) {
                    onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
                    return;
                }
                this.c.set(t);
                c();
            }
        }

        public void onError(Throwable th) {
            if (this.e || isCancelled()) {
                RxJavaPlugins.onError(th);
                return;
            }
            if (th == null) {
                onError(new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources."));
            }
            this.d = th;
            this.e = true;
            c();
        }

        public void onComplete() {
            this.e = true;
            c();
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            c();
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (this.f.getAndIncrement() == 0) {
                this.c.lazySet(null);
            }
        }

        /* access modifiers changed from: 0000 */
        public void c() {
            boolean z;
            if (this.f.getAndIncrement() == 0) {
                Subscriber subscriber = this.a;
                AtomicReference<T> atomicReference = this.c;
                int i = 1;
                do {
                    long j = get();
                    long j2 = 0;
                    while (true) {
                        z = false;
                        if (j2 == j) {
                            break;
                        } else if (isCancelled()) {
                            atomicReference.lazySet(null);
                            return;
                        } else {
                            boolean z2 = this.e;
                            Object andSet = atomicReference.getAndSet(null);
                            boolean z3 = andSet == null;
                            if (z2 && z3) {
                                Throwable th = this.d;
                                if (th != null) {
                                    super.onError(th);
                                } else {
                                    super.onComplete();
                                }
                                return;
                            } else if (z3) {
                                break;
                            } else {
                                subscriber.onNext(andSet);
                                j2++;
                            }
                        }
                    }
                    if (j2 == j) {
                        if (isCancelled()) {
                            atomicReference.lazySet(null);
                            return;
                        }
                        boolean z4 = this.e;
                        if (atomicReference.get() == null) {
                            z = true;
                        }
                        if (z4 && z) {
                            Throwable th2 = this.d;
                            if (th2 != null) {
                                super.onError(th2);
                            } else {
                                super.onComplete();
                            }
                            return;
                        }
                    }
                    if (j2 != 0) {
                        BackpressureHelper.produced(this, j2);
                    }
                    i = this.f.addAndGet(-i);
                } while (i != 0);
            }
        }
    }

    static final class MissingEmitter<T> extends BaseEmitter<T> {
        private static final long serialVersionUID = 3776720187248809713L;

        MissingEmitter(Subscriber<? super T> subscriber) {
            super(subscriber);
        }

        public void onNext(T t) {
            long j;
            if (!isCancelled()) {
                if (t != null) {
                    this.a.onNext(t);
                    do {
                        j = get();
                        if (j == 0) {
                            break;
                        }
                    } while (!compareAndSet(j, j - 1));
                    return;
                }
                onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
            }
        }
    }

    static abstract class NoOverflowBaseAsyncEmitter<T> extends BaseEmitter<T> {
        private static final long serialVersionUID = 4127754106204442833L;

        /* access modifiers changed from: 0000 */
        public abstract void c();

        NoOverflowBaseAsyncEmitter(Subscriber<? super T> subscriber) {
            super(subscriber);
        }

        public final void onNext(T t) {
            if (!isCancelled()) {
                if (t == null) {
                    onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
                    return;
                }
                if (get() != 0) {
                    this.a.onNext(t);
                    BackpressureHelper.produced(this, 1);
                } else {
                    c();
                }
            }
        }
    }

    static final class SerializedEmitter<T> extends AtomicInteger implements FlowableEmitter<T> {
        private static final long serialVersionUID = 4883307006032401862L;
        final BaseEmitter<T> a;
        final AtomicThrowable b = new AtomicThrowable();
        final SimplePlainQueue<T> c = new SpscLinkedArrayQueue(16);
        volatile boolean d;

        public FlowableEmitter<T> serialize() {
            return this;
        }

        SerializedEmitter(BaseEmitter<T> baseEmitter) {
            this.a = baseEmitter;
        }

        public void onNext(T t) {
            if (!this.a.isCancelled() && !this.d) {
                if (t == null) {
                    onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
                    return;
                }
                if (get() != 0 || !compareAndSet(0, 1)) {
                    SimplePlainQueue<T> simplePlainQueue = this.c;
                    synchronized (simplePlainQueue) {
                        simplePlainQueue.offer(t);
                    }
                    if (getAndIncrement() != 0) {
                        return;
                    }
                } else {
                    this.a.onNext(t);
                    if (decrementAndGet() == 0) {
                        return;
                    }
                }
                b();
            }
        }

        public void onError(Throwable th) {
            if (this.a.isCancelled() || this.d) {
                RxJavaPlugins.onError(th);
                return;
            }
            if (th == null) {
                th = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
            }
            if (this.b.addThrowable(th)) {
                this.d = true;
                a();
            } else {
                RxJavaPlugins.onError(th);
            }
        }

        public void onComplete() {
            if (!this.a.isCancelled() && !this.d) {
                this.d = true;
                a();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (getAndIncrement() == 0) {
                b();
            }
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            BaseEmitter<T> baseEmitter = this.a;
            SimplePlainQueue<T> simplePlainQueue = this.c;
            AtomicThrowable atomicThrowable = this.b;
            int i = 1;
            while (!baseEmitter.isCancelled()) {
                if (atomicThrowable.get() != null) {
                    simplePlainQueue.clear();
                    baseEmitter.onError(atomicThrowable.terminate());
                    return;
                }
                boolean z = this.d;
                Object poll = simplePlainQueue.poll();
                boolean z2 = poll == null;
                if (z && z2) {
                    baseEmitter.onComplete();
                    return;
                } else if (z2) {
                    i = addAndGet(-i);
                    if (i == 0) {
                        return;
                    }
                } else {
                    baseEmitter.onNext(poll);
                }
            }
            simplePlainQueue.clear();
        }

        public void setDisposable(Disposable disposable) {
            this.a.setDisposable(disposable);
        }

        public void setCancellable(Cancellable cancellable) {
            this.a.setCancellable(cancellable);
        }

        public long requested() {
            return this.a.requested();
        }

        public boolean isCancelled() {
            return this.a.isCancelled();
        }
    }

    public FlowableCreate(FlowableOnSubscribe<T> flowableOnSubscribe, BackpressureStrategy backpressureStrategy) {
        this.b = flowableOnSubscribe;
        this.c = backpressureStrategy;
    }

    public void subscribeActual(Subscriber<? super T> subscriber) {
        BaseEmitter baseEmitter;
        switch (this.c) {
            case MISSING:
                baseEmitter = new MissingEmitter(subscriber);
                break;
            case ERROR:
                baseEmitter = new ErrorAsyncEmitter(subscriber);
                break;
            case DROP:
                baseEmitter = new DropAsyncEmitter(subscriber);
                break;
            case LATEST:
                baseEmitter = new LatestAsyncEmitter(subscriber);
                break;
            default:
                baseEmitter = new BufferAsyncEmitter(subscriber, bufferSize());
                break;
        }
        subscriber.onSubscribe(baseEmitter);
        try {
            this.b.subscribe(baseEmitter);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            baseEmitter.onError(th);
        }
    }
}
