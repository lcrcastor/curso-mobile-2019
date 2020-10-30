package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.subscribers.QueueDrainSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.QueueDrainHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subscribers.DisposableSubscriber;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableBufferBoundary<T, U extends Collection<? super T>, Open, Close> extends AbstractFlowableWithUpstream<T, U> {
    final Callable<U> b;
    final Publisher<? extends Open> c;
    final Function<? super Open, ? extends Publisher<? extends Close>> d;

    static final class BufferBoundarySubscriber<T, U extends Collection<? super T>, Open, Close> extends QueueDrainSubscriber<T, U, U> implements Disposable, Subscription {
        final Publisher<? extends Open> a;
        final Function<? super Open, ? extends Publisher<? extends Close>> b;
        final Callable<U> c;
        final CompositeDisposable d;
        Subscription e;
        final List<U> f;
        final AtomicInteger g = new AtomicInteger();

        BufferBoundarySubscriber(Subscriber<? super U> subscriber, Publisher<? extends Open> publisher, Function<? super Open, ? extends Publisher<? extends Close>> function, Callable<U> callable) {
            super(subscriber, new MpscLinkedQueue());
            this.a = publisher;
            this.b = function;
            this.c = callable;
            this.f = new LinkedList();
            this.d = new CompositeDisposable();
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.e, subscription)) {
                this.e = subscription;
                BufferOpenSubscriber bufferOpenSubscriber = new BufferOpenSubscriber(this);
                this.d.add(bufferOpenSubscriber);
                this.actual.onSubscribe(this);
                this.g.lazySet(1);
                this.a.subscribe(bufferOpenSubscriber);
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            synchronized (this) {
                for (U add : this.f) {
                    add.add(t);
                }
            }
        }

        public void onError(Throwable th) {
            cancel();
            this.cancelled = true;
            synchronized (this) {
                this.f.clear();
            }
            this.actual.onError(th);
        }

        public void onComplete() {
            if (this.g.decrementAndGet() == 0) {
                a();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            ArrayList<Collection> arrayList;
            synchronized (this) {
                arrayList = new ArrayList<>(this.f);
                this.f.clear();
            }
            SimplePlainQueue simplePlainQueue = this.queue;
            for (Collection offer : arrayList) {
                simplePlainQueue.offer(offer);
            }
            this.done = true;
            if (enter()) {
                QueueDrainHelper.drainMaxLoop(simplePlainQueue, this.actual, false, this, this);
            }
        }

        public void request(long j) {
            requested(j);
        }

        public void dispose() {
            this.d.dispose();
        }

        public boolean isDisposed() {
            return this.d.isDisposed();
        }

        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                dispose();
            }
        }

        /* renamed from: a */
        public boolean accept(Subscriber<? super U> subscriber, U u) {
            subscriber.onNext(u);
            return true;
        }

        /* access modifiers changed from: 0000 */
        public void a(Open open) {
            if (!this.cancelled) {
                try {
                    Collection collection = (Collection) ObjectHelper.requireNonNull(this.c.call(), "The buffer supplied is null");
                    try {
                        Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.b.apply(open), "The buffer closing publisher is null");
                        if (!this.cancelled) {
                            synchronized (this) {
                                if (!this.cancelled) {
                                    this.f.add(collection);
                                    BufferCloseSubscriber bufferCloseSubscriber = new BufferCloseSubscriber(collection, this);
                                    this.d.add(bufferCloseSubscriber);
                                    this.g.getAndIncrement();
                                    publisher.subscribe(bufferCloseSubscriber);
                                }
                            }
                        }
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        onError(th);
                    }
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    onError(th2);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(Disposable disposable) {
            if (this.d.remove(disposable) && this.g.decrementAndGet() == 0) {
                a();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(U u, Disposable disposable) {
            boolean remove;
            synchronized (this) {
                remove = this.f.remove(u);
            }
            if (remove) {
                fastPathOrderedEmitMax(u, false, this);
            }
            if (this.d.remove(disposable) && this.g.decrementAndGet() == 0) {
                a();
            }
        }
    }

    static final class BufferCloseSubscriber<T, U extends Collection<? super T>, Open, Close> extends DisposableSubscriber<Close> {
        final BufferBoundarySubscriber<T, U, Open, Close> a;
        final U b;
        boolean c;

        BufferCloseSubscriber(U u, BufferBoundarySubscriber<T, U, Open, Close> bufferBoundarySubscriber) {
            this.a = bufferBoundarySubscriber;
            this.b = u;
        }

        public void onNext(Close close) {
            onComplete();
        }

        public void onError(Throwable th) {
            if (this.c) {
                RxJavaPlugins.onError(th);
            } else {
                this.a.onError(th);
            }
        }

        public void onComplete() {
            if (!this.c) {
                this.c = true;
                this.a.a(this.b, (Disposable) this);
            }
        }
    }

    static final class BufferOpenSubscriber<T, U extends Collection<? super T>, Open, Close> extends DisposableSubscriber<Open> {
        final BufferBoundarySubscriber<T, U, Open, Close> a;
        boolean b;

        BufferOpenSubscriber(BufferBoundarySubscriber<T, U, Open, Close> bufferBoundarySubscriber) {
            this.a = bufferBoundarySubscriber;
        }

        public void onNext(Open open) {
            if (!this.b) {
                this.a.a(open);
            }
        }

        public void onError(Throwable th) {
            if (this.b) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.b = true;
            this.a.onError(th);
        }

        public void onComplete() {
            if (!this.b) {
                this.b = true;
                this.a.a((Disposable) this);
            }
        }
    }

    public FlowableBufferBoundary(Flowable<T> flowable, Publisher<? extends Open> publisher, Function<? super Open, ? extends Publisher<? extends Close>> function, Callable<U> callable) {
        super(flowable);
        this.c = publisher;
        this.d = function;
        this.b = callable;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super U> subscriber) {
        this.source.subscribe((FlowableSubscriber<? super T>) new BufferBoundarySubscriber<Object>(new SerializedSubscriber(subscriber), this.c, this.d, this.b));
    }
}
