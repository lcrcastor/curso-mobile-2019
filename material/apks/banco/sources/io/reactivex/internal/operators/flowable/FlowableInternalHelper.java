package io.reactivex.internal.operators.flowable;

import io.reactivex.Emitter;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.flowables.ConnectableFlowable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.Functions;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableInternalHelper {

    static final class BufferedReplayCallable<T> implements Callable<ConnectableFlowable<T>> {
        private final Flowable<T> a;
        private final int b;

        BufferedReplayCallable(Flowable<T> flowable, int i) {
            this.a = flowable;
            this.b = i;
        }

        /* renamed from: a */
        public ConnectableFlowable<T> call() {
            return this.a.replay(this.b);
        }
    }

    static final class BufferedTimedReplay<T> implements Callable<ConnectableFlowable<T>> {
        private final Flowable<T> a;
        private final int b;
        private final long c;
        private final TimeUnit d;
        private final Scheduler e;

        BufferedTimedReplay(Flowable<T> flowable, int i, long j, TimeUnit timeUnit, Scheduler scheduler) {
            this.a = flowable;
            this.b = i;
            this.c = j;
            this.d = timeUnit;
            this.e = scheduler;
        }

        /* renamed from: a */
        public ConnectableFlowable<T> call() {
            return this.a.replay(this.b, this.c, this.d, this.e);
        }
    }

    static final class FlatMapIntoIterable<T, U> implements Function<T, Publisher<U>> {
        private final Function<? super T, ? extends Iterable<? extends U>> a;

        FlatMapIntoIterable(Function<? super T, ? extends Iterable<? extends U>> function) {
            this.a = function;
        }

        /* renamed from: a */
        public Publisher<U> apply(T t) {
            return new FlowableFromIterable((Iterable) this.a.apply(t));
        }
    }

    static final class FlatMapWithCombinerInner<U, R, T> implements Function<U, R> {
        private final BiFunction<? super T, ? super U, ? extends R> a;
        private final T b;

        FlatMapWithCombinerInner(BiFunction<? super T, ? super U, ? extends R> biFunction, T t) {
            this.a = biFunction;
            this.b = t;
        }

        public R apply(U u) {
            return this.a.apply(this.b, u);
        }
    }

    static final class FlatMapWithCombinerOuter<T, R, U> implements Function<T, Publisher<R>> {
        private final BiFunction<? super T, ? super U, ? extends R> a;
        private final Function<? super T, ? extends Publisher<? extends U>> b;

        FlatMapWithCombinerOuter(BiFunction<? super T, ? super U, ? extends R> biFunction, Function<? super T, ? extends Publisher<? extends U>> function) {
            this.a = biFunction;
            this.b = function;
        }

        /* renamed from: a */
        public Publisher<R> apply(T t) {
            return new FlowableMapPublisher((Publisher) this.b.apply(t), new FlatMapWithCombinerInner(this.a, t));
        }
    }

    static final class ItemDelayFunction<T, U> implements Function<T, Publisher<T>> {
        final Function<? super T, ? extends Publisher<U>> a;

        ItemDelayFunction(Function<? super T, ? extends Publisher<U>> function) {
            this.a = function;
        }

        /* renamed from: a */
        public Publisher<T> apply(T t) {
            return new FlowableTakePublisher((Publisher) this.a.apply(t), 1).map(Functions.justFunction(t)).defaultIfEmpty(t);
        }
    }

    static final class ReplayCallable<T> implements Callable<ConnectableFlowable<T>> {
        private final Flowable<T> a;

        ReplayCallable(Flowable<T> flowable) {
            this.a = flowable;
        }

        /* renamed from: a */
        public ConnectableFlowable<T> call() {
            return this.a.replay();
        }
    }

    static final class ReplayFunction<T, R> implements Function<Flowable<T>, Publisher<R>> {
        private final Function<? super Flowable<T>, ? extends Publisher<R>> a;
        private final Scheduler b;

        ReplayFunction(Function<? super Flowable<T>, ? extends Publisher<R>> function, Scheduler scheduler) {
            this.a = function;
            this.b = scheduler;
        }

        /* renamed from: a */
        public Publisher<R> apply(Flowable<T> flowable) {
            return Flowable.fromPublisher((Publisher) this.a.apply(flowable)).observeOn(this.b);
        }
    }

    public enum RequestMax implements Consumer<Subscription> {
        INSTANCE;

        public void accept(Subscription subscription) {
            subscription.request(Long.MAX_VALUE);
        }
    }

    static final class SimpleBiGenerator<T, S> implements BiFunction<S, Emitter<T>, S> {
        final BiConsumer<S, Emitter<T>> a;

        SimpleBiGenerator(BiConsumer<S, Emitter<T>> biConsumer) {
            this.a = biConsumer;
        }

        /* renamed from: a */
        public S apply(S s, Emitter<T> emitter) {
            this.a.accept(s, emitter);
            return s;
        }
    }

    static final class SimpleGenerator<T, S> implements BiFunction<S, Emitter<T>, S> {
        final Consumer<Emitter<T>> a;

        SimpleGenerator(Consumer<Emitter<T>> consumer) {
            this.a = consumer;
        }

        /* renamed from: a */
        public S apply(S s, Emitter<T> emitter) {
            this.a.accept(emitter);
            return s;
        }
    }

    static final class SubscriberOnComplete<T> implements Action {
        final Subscriber<T> a;

        SubscriberOnComplete(Subscriber<T> subscriber) {
            this.a = subscriber;
        }

        public void run() {
            this.a.onComplete();
        }
    }

    static final class SubscriberOnError<T> implements Consumer<Throwable> {
        final Subscriber<T> a;

        SubscriberOnError(Subscriber<T> subscriber) {
            this.a = subscriber;
        }

        /* renamed from: a */
        public void accept(Throwable th) {
            this.a.onError(th);
        }
    }

    static final class SubscriberOnNext<T> implements Consumer<T> {
        final Subscriber<T> a;

        SubscriberOnNext(Subscriber<T> subscriber) {
            this.a = subscriber;
        }

        public void accept(T t) {
            this.a.onNext(t);
        }
    }

    static final class TimedReplay<T> implements Callable<ConnectableFlowable<T>> {
        private final Flowable<T> a;
        private final long b;
        private final TimeUnit c;
        private final Scheduler d;

        TimedReplay(Flowable<T> flowable, long j, TimeUnit timeUnit, Scheduler scheduler) {
            this.a = flowable;
            this.b = j;
            this.c = timeUnit;
            this.d = scheduler;
        }

        /* renamed from: a */
        public ConnectableFlowable<T> call() {
            return this.a.replay(this.b, this.c, this.d);
        }
    }

    static final class ZipIterableFunction<T, R> implements Function<List<Publisher<? extends T>>, Publisher<? extends R>> {
        private final Function<? super Object[], ? extends R> a;

        ZipIterableFunction(Function<? super Object[], ? extends R> function) {
            this.a = function;
        }

        /* renamed from: a */
        public Publisher<? extends R> apply(List<Publisher<? extends T>> list) {
            return Flowable.zipIterable(list, this.a, false, Flowable.bufferSize());
        }
    }

    private FlowableInternalHelper() {
        throw new IllegalStateException("No instances!");
    }

    public static <T, S> BiFunction<S, Emitter<T>, S> simpleGenerator(Consumer<Emitter<T>> consumer) {
        return new SimpleGenerator(consumer);
    }

    public static <T, S> BiFunction<S, Emitter<T>, S> simpleBiGenerator(BiConsumer<S, Emitter<T>> biConsumer) {
        return new SimpleBiGenerator(biConsumer);
    }

    public static <T, U> Function<T, Publisher<T>> itemDelay(Function<? super T, ? extends Publisher<U>> function) {
        return new ItemDelayFunction(function);
    }

    public static <T> Consumer<T> subscriberOnNext(Subscriber<T> subscriber) {
        return new SubscriberOnNext(subscriber);
    }

    public static <T> Consumer<Throwable> subscriberOnError(Subscriber<T> subscriber) {
        return new SubscriberOnError(subscriber);
    }

    public static <T> Action subscriberOnComplete(Subscriber<T> subscriber) {
        return new SubscriberOnComplete(subscriber);
    }

    public static <T, U, R> Function<T, Publisher<R>> flatMapWithCombiner(Function<? super T, ? extends Publisher<? extends U>> function, BiFunction<? super T, ? super U, ? extends R> biFunction) {
        return new FlatMapWithCombinerOuter(biFunction, function);
    }

    public static <T, U> Function<T, Publisher<U>> flatMapIntoIterable(Function<? super T, ? extends Iterable<? extends U>> function) {
        return new FlatMapIntoIterable(function);
    }

    public static <T> Callable<ConnectableFlowable<T>> replayCallable(Flowable<T> flowable) {
        return new ReplayCallable(flowable);
    }

    public static <T> Callable<ConnectableFlowable<T>> replayCallable(Flowable<T> flowable, int i) {
        return new BufferedReplayCallable(flowable, i);
    }

    public static <T> Callable<ConnectableFlowable<T>> replayCallable(Flowable<T> flowable, int i, long j, TimeUnit timeUnit, Scheduler scheduler) {
        BufferedTimedReplay bufferedTimedReplay = new BufferedTimedReplay(flowable, i, j, timeUnit, scheduler);
        return bufferedTimedReplay;
    }

    public static <T> Callable<ConnectableFlowable<T>> replayCallable(Flowable<T> flowable, long j, TimeUnit timeUnit, Scheduler scheduler) {
        TimedReplay timedReplay = new TimedReplay(flowable, j, timeUnit, scheduler);
        return timedReplay;
    }

    public static <T, R> Function<Flowable<T>, Publisher<R>> replayFunction(Function<? super Flowable<T>, ? extends Publisher<R>> function, Scheduler scheduler) {
        return new ReplayFunction(function, scheduler);
    }

    public static <T, R> Function<List<Publisher<? extends T>>, Publisher<? extends R>> zipIterable(Function<? super Object[], ? extends R> function) {
        return new ZipIterableFunction(function);
    }
}
