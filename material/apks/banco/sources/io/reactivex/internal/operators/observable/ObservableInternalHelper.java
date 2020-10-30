package io.reactivex.internal.operators.observable;

import io.reactivex.Emitter;
import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.SingleSource;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.operators.single.SingleToObservable;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public final class ObservableInternalHelper {

    static final class BufferedReplayCallable<T> implements Callable<ConnectableObservable<T>> {
        private final Observable<T> a;
        private final int b;

        BufferedReplayCallable(Observable<T> observable, int i) {
            this.a = observable;
            this.b = i;
        }

        /* renamed from: a */
        public ConnectableObservable<T> call() {
            return this.a.replay(this.b);
        }
    }

    static final class BufferedTimedReplayCallable<T> implements Callable<ConnectableObservable<T>> {
        private final Observable<T> a;
        private final int b;
        private final long c;
        private final TimeUnit d;
        private final Scheduler e;

        BufferedTimedReplayCallable(Observable<T> observable, int i, long j, TimeUnit timeUnit, Scheduler scheduler) {
            this.a = observable;
            this.b = i;
            this.c = j;
            this.d = timeUnit;
            this.e = scheduler;
        }

        /* renamed from: a */
        public ConnectableObservable<T> call() {
            return this.a.replay(this.b, this.c, this.d, this.e);
        }
    }

    enum ErrorMapperFilter implements Function<Notification<Object>, Throwable>, Predicate<Notification<Object>> {
        INSTANCE;

        /* renamed from: a */
        public Throwable apply(Notification<Object> notification) {
            return notification.getError();
        }

        /* renamed from: b */
        public boolean test(Notification<Object> notification) {
            return notification.isOnError();
        }
    }

    static final class FlatMapIntoIterable<T, U> implements Function<T, ObservableSource<U>> {
        private final Function<? super T, ? extends Iterable<? extends U>> a;

        FlatMapIntoIterable(Function<? super T, ? extends Iterable<? extends U>> function) {
            this.a = function;
        }

        /* renamed from: a */
        public ObservableSource<U> apply(T t) {
            return new ObservableFromIterable((Iterable) this.a.apply(t));
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

    static final class FlatMapWithCombinerOuter<T, R, U> implements Function<T, ObservableSource<R>> {
        private final BiFunction<? super T, ? super U, ? extends R> a;
        private final Function<? super T, ? extends ObservableSource<? extends U>> b;

        FlatMapWithCombinerOuter(BiFunction<? super T, ? super U, ? extends R> biFunction, Function<? super T, ? extends ObservableSource<? extends U>> function) {
            this.a = biFunction;
            this.b = function;
        }

        /* renamed from: a */
        public ObservableSource<R> apply(T t) {
            return new ObservableMap((ObservableSource) this.b.apply(t), new FlatMapWithCombinerInner(this.a, t));
        }
    }

    static final class ItemDelayFunction<T, U> implements Function<T, ObservableSource<T>> {
        final Function<? super T, ? extends ObservableSource<U>> a;

        ItemDelayFunction(Function<? super T, ? extends ObservableSource<U>> function) {
            this.a = function;
        }

        /* renamed from: a */
        public ObservableSource<T> apply(T t) {
            return new ObservableTake((ObservableSource) this.a.apply(t), 1).map(Functions.justFunction(t)).defaultIfEmpty(t);
        }
    }

    enum MapToInt implements Function<Object, Object> {
        INSTANCE;

        public Object apply(Object obj) {
            return Integer.valueOf(0);
        }
    }

    static final class ObservableMapper<T, R> implements Function<T, Observable<R>> {
        final Function<? super T, ? extends SingleSource<? extends R>> a;

        ObservableMapper(Function<? super T, ? extends SingleSource<? extends R>> function) {
            this.a = function;
        }

        /* renamed from: a */
        public Observable<R> apply(T t) {
            return RxJavaPlugins.onAssembly((Observable<T>) new SingleToObservable<T>((SingleSource) ObjectHelper.requireNonNull(this.a.apply(t), "The mapper returned a null value")));
        }
    }

    static final class ObserverOnComplete<T> implements Action {
        final Observer<T> a;

        ObserverOnComplete(Observer<T> observer) {
            this.a = observer;
        }

        public void run() {
            this.a.onComplete();
        }
    }

    static final class ObserverOnError<T> implements Consumer<Throwable> {
        final Observer<T> a;

        ObserverOnError(Observer<T> observer) {
            this.a = observer;
        }

        /* renamed from: a */
        public void accept(Throwable th) {
            this.a.onError(th);
        }
    }

    static final class ObserverOnNext<T> implements Consumer<T> {
        final Observer<T> a;

        ObserverOnNext(Observer<T> observer) {
            this.a = observer;
        }

        public void accept(T t) {
            this.a.onNext(t);
        }
    }

    static final class RepeatWhenOuterHandler implements Function<Observable<Notification<Object>>, ObservableSource<?>> {
        private final Function<? super Observable<Object>, ? extends ObservableSource<?>> a;

        RepeatWhenOuterHandler(Function<? super Observable<Object>, ? extends ObservableSource<?>> function) {
            this.a = function;
        }

        /* renamed from: a */
        public ObservableSource<?> apply(Observable<Notification<Object>> observable) {
            return (ObservableSource) this.a.apply(observable.map(MapToInt.INSTANCE));
        }
    }

    static final class ReplayCallable<T> implements Callable<ConnectableObservable<T>> {
        private final Observable<T> a;

        ReplayCallable(Observable<T> observable) {
            this.a = observable;
        }

        /* renamed from: a */
        public ConnectableObservable<T> call() {
            return this.a.replay();
        }
    }

    static final class ReplayFunction<T, R> implements Function<Observable<T>, ObservableSource<R>> {
        private final Function<? super Observable<T>, ? extends ObservableSource<R>> a;
        private final Scheduler b;

        ReplayFunction(Function<? super Observable<T>, ? extends ObservableSource<R>> function, Scheduler scheduler) {
            this.a = function;
            this.b = scheduler;
        }

        /* renamed from: a */
        public ObservableSource<R> apply(Observable<T> observable) {
            return Observable.wrap((ObservableSource) this.a.apply(observable)).observeOn(this.b);
        }
    }

    static final class RetryWhenInner implements Function<Observable<Notification<Object>>, ObservableSource<?>> {
        private final Function<? super Observable<Throwable>, ? extends ObservableSource<?>> a;

        RetryWhenInner(Function<? super Observable<Throwable>, ? extends ObservableSource<?>> function) {
            this.a = function;
        }

        /* renamed from: a */
        public ObservableSource<?> apply(Observable<Notification<Object>> observable) {
            return (ObservableSource) this.a.apply(observable.takeWhile(ErrorMapperFilter.INSTANCE).map(ErrorMapperFilter.INSTANCE));
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

    static final class TimedReplayCallable<T> implements Callable<ConnectableObservable<T>> {
        private final Observable<T> a;
        private final long b;
        private final TimeUnit c;
        private final Scheduler d;

        TimedReplayCallable(Observable<T> observable, long j, TimeUnit timeUnit, Scheduler scheduler) {
            this.a = observable;
            this.b = j;
            this.c = timeUnit;
            this.d = scheduler;
        }

        /* renamed from: a */
        public ConnectableObservable<T> call() {
            return this.a.replay(this.b, this.c, this.d);
        }
    }

    static final class ZipIterableFunction<T, R> implements Function<List<ObservableSource<? extends T>>, ObservableSource<? extends R>> {
        private final Function<? super Object[], ? extends R> a;

        ZipIterableFunction(Function<? super Object[], ? extends R> function) {
            this.a = function;
        }

        /* renamed from: a */
        public ObservableSource<? extends R> apply(List<ObservableSource<? extends T>> list) {
            return Observable.zipIterable(list, this.a, false, Observable.bufferSize());
        }
    }

    private ObservableInternalHelper() {
        throw new IllegalStateException("No instances!");
    }

    public static <T, S> BiFunction<S, Emitter<T>, S> simpleGenerator(Consumer<Emitter<T>> consumer) {
        return new SimpleGenerator(consumer);
    }

    public static <T, S> BiFunction<S, Emitter<T>, S> simpleBiGenerator(BiConsumer<S, Emitter<T>> biConsumer) {
        return new SimpleBiGenerator(biConsumer);
    }

    public static <T, U> Function<T, ObservableSource<T>> itemDelay(Function<? super T, ? extends ObservableSource<U>> function) {
        return new ItemDelayFunction(function);
    }

    public static <T> Consumer<T> observerOnNext(Observer<T> observer) {
        return new ObserverOnNext(observer);
    }

    public static <T> Consumer<Throwable> observerOnError(Observer<T> observer) {
        return new ObserverOnError(observer);
    }

    public static <T> Action observerOnComplete(Observer<T> observer) {
        return new ObserverOnComplete(observer);
    }

    public static <T, U, R> Function<T, ObservableSource<R>> flatMapWithCombiner(Function<? super T, ? extends ObservableSource<? extends U>> function, BiFunction<? super T, ? super U, ? extends R> biFunction) {
        return new FlatMapWithCombinerOuter(biFunction, function);
    }

    public static <T, U> Function<T, ObservableSource<U>> flatMapIntoIterable(Function<? super T, ? extends Iterable<? extends U>> function) {
        return new FlatMapIntoIterable(function);
    }

    public static Function<Observable<Notification<Object>>, ObservableSource<?>> repeatWhenHandler(Function<? super Observable<Object>, ? extends ObservableSource<?>> function) {
        return new RepeatWhenOuterHandler(function);
    }

    public static <T> Callable<ConnectableObservable<T>> replayCallable(Observable<T> observable) {
        return new ReplayCallable(observable);
    }

    public static <T> Callable<ConnectableObservable<T>> replayCallable(Observable<T> observable, int i) {
        return new BufferedReplayCallable(observable, i);
    }

    public static <T> Callable<ConnectableObservable<T>> replayCallable(Observable<T> observable, int i, long j, TimeUnit timeUnit, Scheduler scheduler) {
        BufferedTimedReplayCallable bufferedTimedReplayCallable = new BufferedTimedReplayCallable(observable, i, j, timeUnit, scheduler);
        return bufferedTimedReplayCallable;
    }

    public static <T> Callable<ConnectableObservable<T>> replayCallable(Observable<T> observable, long j, TimeUnit timeUnit, Scheduler scheduler) {
        TimedReplayCallable timedReplayCallable = new TimedReplayCallable(observable, j, timeUnit, scheduler);
        return timedReplayCallable;
    }

    public static <T, R> Function<Observable<T>, ObservableSource<R>> replayFunction(Function<? super Observable<T>, ? extends ObservableSource<R>> function, Scheduler scheduler) {
        return new ReplayFunction(function, scheduler);
    }

    public static <T> Function<Observable<Notification<Object>>, ObservableSource<?>> retryWhenHandler(Function<? super Observable<Throwable>, ? extends ObservableSource<?>> function) {
        return new RetryWhenInner(function);
    }

    public static <T, R> Function<List<ObservableSource<? extends T>>, ObservableSource<? extends R>> zipIterable(Function<? super Object[], ? extends R> function) {
        return new ZipIterableFunction(function);
    }

    public static <T, R> Observable<R> switchMapSingle(Observable<T> observable, Function<? super T, ? extends SingleSource<? extends R>> function) {
        return observable.switchMap(a(function), 1);
    }

    public static <T, R> Observable<R> switchMapSingleDelayError(Observable<T> observable, Function<? super T, ? extends SingleSource<? extends R>> function) {
        return observable.switchMapDelayError(a(function), 1);
    }

    private static <T, R> Function<T, Observable<R>> a(Function<? super T, ? extends SingleSource<? extends R>> function) {
        ObjectHelper.requireNonNull(function, "mapper is null");
        return new ObservableMapper(function);
    }
}
