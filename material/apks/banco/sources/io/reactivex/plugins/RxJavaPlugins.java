package io.reactivex.plugins;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.Beta;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.exceptions.UndeliverableException;
import io.reactivex.flowables.ConnectableFlowable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.schedulers.ComputationScheduler;
import io.reactivex.internal.schedulers.IoScheduler;
import io.reactivex.internal.schedulers.NewThreadScheduler;
import io.reactivex.internal.schedulers.SingleScheduler;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.parallel.ParallelFlowable;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadFactory;
import org.reactivestreams.Subscriber;

public final class RxJavaPlugins {
    @Nullable
    static volatile Consumer<? super Throwable> a;
    @Nullable
    static volatile Function<? super Runnable, ? extends Runnable> b;
    @Nullable
    static volatile Function<? super Callable<Scheduler>, ? extends Scheduler> c;
    @Nullable
    static volatile Function<? super Callable<Scheduler>, ? extends Scheduler> d;
    @Nullable
    static volatile Function<? super Callable<Scheduler>, ? extends Scheduler> e;
    @Nullable
    static volatile Function<? super Callable<Scheduler>, ? extends Scheduler> f;
    @Nullable
    static volatile Function<? super Scheduler, ? extends Scheduler> g;
    @Nullable
    static volatile Function<? super Scheduler, ? extends Scheduler> h;
    @Nullable
    static volatile Function<? super Scheduler, ? extends Scheduler> i;
    @Nullable
    static volatile Function<? super Scheduler, ? extends Scheduler> j;
    @Nullable
    static volatile Function<? super Flowable, ? extends Flowable> k;
    @Nullable
    static volatile Function<? super ConnectableFlowable, ? extends ConnectableFlowable> l;
    @Nullable
    static volatile Function<? super Observable, ? extends Observable> m;
    @Nullable
    static volatile Function<? super ConnectableObservable, ? extends ConnectableObservable> n;
    @Nullable
    static volatile Function<? super Maybe, ? extends Maybe> o;
    @Nullable
    static volatile Function<? super Single, ? extends Single> p;
    static volatile Function<? super Completable, ? extends Completable> q;
    @Nullable
    static volatile Function<? super ParallelFlowable, ? extends ParallelFlowable> r;
    @Nullable
    static volatile BiFunction<? super Flowable, ? super Subscriber, ? extends Subscriber> s;
    @Nullable
    static volatile BiFunction<? super Maybe, ? super MaybeObserver, ? extends MaybeObserver> t;
    @Nullable
    static volatile BiFunction<? super Observable, ? super Observer, ? extends Observer> u;
    @Nullable
    static volatile BiFunction<? super Single, ? super SingleObserver, ? extends SingleObserver> v;
    @Nullable
    static volatile BiFunction<? super Completable, ? super CompletableObserver, ? extends CompletableObserver> w;
    @Nullable
    static volatile BooleanSupplier x;
    static volatile boolean y;
    static volatile boolean z;

    public static void lockdown() {
        y = true;
    }

    public static boolean isLockdown() {
        return y;
    }

    public static void setFailOnNonBlockingScheduler(boolean z2) {
        if (y) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        z = z2;
    }

    public static boolean isFailOnNonBlockingScheduler() {
        return z;
    }

    @Nullable
    public static Function<? super Scheduler, ? extends Scheduler> getComputationSchedulerHandler() {
        return g;
    }

    @Nullable
    public static Consumer<? super Throwable> getErrorHandler() {
        return a;
    }

    @Nullable
    public static Function<? super Callable<Scheduler>, ? extends Scheduler> getInitComputationSchedulerHandler() {
        return c;
    }

    @Nullable
    public static Function<? super Callable<Scheduler>, ? extends Scheduler> getInitIoSchedulerHandler() {
        return e;
    }

    @Nullable
    public static Function<? super Callable<Scheduler>, ? extends Scheduler> getInitNewThreadSchedulerHandler() {
        return f;
    }

    @Nullable
    public static Function<? super Callable<Scheduler>, ? extends Scheduler> getInitSingleSchedulerHandler() {
        return d;
    }

    @Nullable
    public static Function<? super Scheduler, ? extends Scheduler> getIoSchedulerHandler() {
        return i;
    }

    @Nullable
    public static Function<? super Scheduler, ? extends Scheduler> getNewThreadSchedulerHandler() {
        return j;
    }

    @Nullable
    public static Function<? super Runnable, ? extends Runnable> getScheduleHandler() {
        return b;
    }

    @Nullable
    public static Function<? super Scheduler, ? extends Scheduler> getSingleSchedulerHandler() {
        return h;
    }

    @NonNull
    public static Scheduler initComputationScheduler(@NonNull Callable<Scheduler> callable) {
        ObjectHelper.requireNonNull(callable, "Scheduler Callable can't be null");
        Function<? super Callable<Scheduler>, ? extends Scheduler> function = c;
        if (function == null) {
            return a(callable);
        }
        return a(function, callable);
    }

    @NonNull
    public static Scheduler initIoScheduler(@NonNull Callable<Scheduler> callable) {
        ObjectHelper.requireNonNull(callable, "Scheduler Callable can't be null");
        Function<? super Callable<Scheduler>, ? extends Scheduler> function = e;
        if (function == null) {
            return a(callable);
        }
        return a(function, callable);
    }

    @NonNull
    public static Scheduler initNewThreadScheduler(@NonNull Callable<Scheduler> callable) {
        ObjectHelper.requireNonNull(callable, "Scheduler Callable can't be null");
        Function<? super Callable<Scheduler>, ? extends Scheduler> function = f;
        if (function == null) {
            return a(callable);
        }
        return a(function, callable);
    }

    @NonNull
    public static Scheduler initSingleScheduler(@NonNull Callable<Scheduler> callable) {
        ObjectHelper.requireNonNull(callable, "Scheduler Callable can't be null");
        Function<? super Callable<Scheduler>, ? extends Scheduler> function = d;
        if (function == null) {
            return a(callable);
        }
        return a(function, callable);
    }

    @NonNull
    public static Scheduler onComputationScheduler(@NonNull Scheduler scheduler) {
        Function<? super Scheduler, ? extends Scheduler> function = g;
        if (function == null) {
            return scheduler;
        }
        return (Scheduler) a(function, (T) scheduler);
    }

    public static void onError(@NonNull Throwable th) {
        Consumer<? super Throwable> consumer = a;
        if (th == null) {
            th = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
        } else if (!a(th)) {
            th = new UndeliverableException(th);
        }
        if (consumer != null) {
            try {
                consumer.accept(th);
                return;
            } catch (Throwable th2) {
                th2.printStackTrace();
                b(th2);
            }
        }
        th.printStackTrace();
        b(th);
    }

    static boolean a(Throwable th) {
        if (!(th instanceof OnErrorNotImplementedException) && !(th instanceof MissingBackpressureException) && !(th instanceof IllegalStateException) && !(th instanceof NullPointerException) && !(th instanceof IllegalArgumentException) && !(th instanceof CompositeException)) {
            return false;
        }
        return true;
    }

    static void b(@NonNull Throwable th) {
        Thread currentThread = Thread.currentThread();
        currentThread.getUncaughtExceptionHandler().uncaughtException(currentThread, th);
    }

    @NonNull
    public static Scheduler onIoScheduler(@NonNull Scheduler scheduler) {
        Function<? super Scheduler, ? extends Scheduler> function = i;
        if (function == null) {
            return scheduler;
        }
        return (Scheduler) a(function, (T) scheduler);
    }

    @NonNull
    public static Scheduler onNewThreadScheduler(@NonNull Scheduler scheduler) {
        Function<? super Scheduler, ? extends Scheduler> function = j;
        if (function == null) {
            return scheduler;
        }
        return (Scheduler) a(function, (T) scheduler);
    }

    @NonNull
    public static Runnable onSchedule(@NonNull Runnable runnable) {
        Function<? super Runnable, ? extends Runnable> function = b;
        if (function == null) {
            return runnable;
        }
        return (Runnable) a(function, (T) runnable);
    }

    @NonNull
    public static Scheduler onSingleScheduler(@NonNull Scheduler scheduler) {
        Function<? super Scheduler, ? extends Scheduler> function = h;
        if (function == null) {
            return scheduler;
        }
        return (Scheduler) a(function, (T) scheduler);
    }

    public static void reset() {
        setErrorHandler(null);
        setScheduleHandler(null);
        setComputationSchedulerHandler(null);
        setInitComputationSchedulerHandler(null);
        setIoSchedulerHandler(null);
        setInitIoSchedulerHandler(null);
        setSingleSchedulerHandler(null);
        setInitSingleSchedulerHandler(null);
        setNewThreadSchedulerHandler(null);
        setInitNewThreadSchedulerHandler(null);
        setOnFlowableAssembly(null);
        setOnFlowableSubscribe(null);
        setOnObservableAssembly(null);
        setOnObservableSubscribe(null);
        setOnSingleAssembly(null);
        setOnSingleSubscribe(null);
        setOnCompletableAssembly(null);
        setOnCompletableSubscribe(null);
        setOnConnectableFlowableAssembly(null);
        setOnConnectableObservableAssembly(null);
        setOnMaybeAssembly(null);
        setOnMaybeSubscribe(null);
        setOnParallelAssembly(null);
        setFailOnNonBlockingScheduler(false);
        setOnBeforeBlocking(null);
    }

    public static void setComputationSchedulerHandler(@Nullable Function<? super Scheduler, ? extends Scheduler> function) {
        if (y) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        g = function;
    }

    public static void setErrorHandler(@Nullable Consumer<? super Throwable> consumer) {
        if (y) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        a = consumer;
    }

    public static void setInitComputationSchedulerHandler(@Nullable Function<? super Callable<Scheduler>, ? extends Scheduler> function) {
        if (y) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        c = function;
    }

    public static void setInitIoSchedulerHandler(@Nullable Function<? super Callable<Scheduler>, ? extends Scheduler> function) {
        if (y) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        e = function;
    }

    public static void setInitNewThreadSchedulerHandler(@Nullable Function<? super Callable<Scheduler>, ? extends Scheduler> function) {
        if (y) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        f = function;
    }

    public static void setInitSingleSchedulerHandler(@Nullable Function<? super Callable<Scheduler>, ? extends Scheduler> function) {
        if (y) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        d = function;
    }

    public static void setIoSchedulerHandler(@Nullable Function<? super Scheduler, ? extends Scheduler> function) {
        if (y) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        i = function;
    }

    public static void setNewThreadSchedulerHandler(@Nullable Function<? super Scheduler, ? extends Scheduler> function) {
        if (y) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        j = function;
    }

    public static void setScheduleHandler(@Nullable Function<? super Runnable, ? extends Runnable> function) {
        if (y) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        b = function;
    }

    public static void setSingleSchedulerHandler(@Nullable Function<? super Scheduler, ? extends Scheduler> function) {
        if (y) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        h = function;
    }

    @Nullable
    public static Function<? super Completable, ? extends Completable> getOnCompletableAssembly() {
        return q;
    }

    @Nullable
    public static BiFunction<? super Completable, ? super CompletableObserver, ? extends CompletableObserver> getOnCompletableSubscribe() {
        return w;
    }

    @Nullable
    public static Function<? super Flowable, ? extends Flowable> getOnFlowableAssembly() {
        return k;
    }

    @Nullable
    public static Function<? super ConnectableFlowable, ? extends ConnectableFlowable> getOnConnectableFlowableAssembly() {
        return l;
    }

    @Nullable
    public static BiFunction<? super Flowable, ? super Subscriber, ? extends Subscriber> getOnFlowableSubscribe() {
        return s;
    }

    @Nullable
    public static BiFunction<? super Maybe, ? super MaybeObserver, ? extends MaybeObserver> getOnMaybeSubscribe() {
        return t;
    }

    @Nullable
    public static Function<? super Maybe, ? extends Maybe> getOnMaybeAssembly() {
        return o;
    }

    @Nullable
    public static Function<? super Single, ? extends Single> getOnSingleAssembly() {
        return p;
    }

    @Nullable
    public static BiFunction<? super Single, ? super SingleObserver, ? extends SingleObserver> getOnSingleSubscribe() {
        return v;
    }

    @Nullable
    public static Function<? super Observable, ? extends Observable> getOnObservableAssembly() {
        return m;
    }

    @Nullable
    public static Function<? super ConnectableObservable, ? extends ConnectableObservable> getOnConnectableObservableAssembly() {
        return n;
    }

    @Nullable
    public static BiFunction<? super Observable, ? super Observer, ? extends Observer> getOnObservableSubscribe() {
        return u;
    }

    public static void setOnCompletableAssembly(@Nullable Function<? super Completable, ? extends Completable> function) {
        if (y) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        q = function;
    }

    public static void setOnCompletableSubscribe(@Nullable BiFunction<? super Completable, ? super CompletableObserver, ? extends CompletableObserver> biFunction) {
        if (y) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        w = biFunction;
    }

    public static void setOnFlowableAssembly(@Nullable Function<? super Flowable, ? extends Flowable> function) {
        if (y) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        k = function;
    }

    public static void setOnMaybeAssembly(@Nullable Function<? super Maybe, ? extends Maybe> function) {
        if (y) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        o = function;
    }

    public static void setOnConnectableFlowableAssembly(@Nullable Function<? super ConnectableFlowable, ? extends ConnectableFlowable> function) {
        if (y) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        l = function;
    }

    public static void setOnFlowableSubscribe(@Nullable BiFunction<? super Flowable, ? super Subscriber, ? extends Subscriber> biFunction) {
        if (y) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        s = biFunction;
    }

    public static void setOnMaybeSubscribe(@Nullable BiFunction<? super Maybe, MaybeObserver, ? extends MaybeObserver> biFunction) {
        if (y) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        t = biFunction;
    }

    public static void setOnObservableAssembly(@Nullable Function<? super Observable, ? extends Observable> function) {
        if (y) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        m = function;
    }

    public static void setOnConnectableObservableAssembly(@Nullable Function<? super ConnectableObservable, ? extends ConnectableObservable> function) {
        if (y) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        n = function;
    }

    public static void setOnObservableSubscribe(@Nullable BiFunction<? super Observable, ? super Observer, ? extends Observer> biFunction) {
        if (y) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        u = biFunction;
    }

    public static void setOnSingleAssembly(@Nullable Function<? super Single, ? extends Single> function) {
        if (y) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        p = function;
    }

    public static void setOnSingleSubscribe(@Nullable BiFunction<? super Single, ? super SingleObserver, ? extends SingleObserver> biFunction) {
        if (y) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        v = biFunction;
    }

    @NonNull
    public static <T> Subscriber<? super T> onSubscribe(@NonNull Flowable<T> flowable, @NonNull Subscriber<? super T> subscriber) {
        BiFunction<? super Flowable, ? super Subscriber, ? extends Subscriber> biFunction = s;
        return biFunction != null ? (Subscriber) a(biFunction, flowable, subscriber) : subscriber;
    }

    @NonNull
    public static <T> Observer<? super T> onSubscribe(@NonNull Observable<T> observable, @NonNull Observer<? super T> observer) {
        BiFunction<? super Observable, ? super Observer, ? extends Observer> biFunction = u;
        return biFunction != null ? (Observer) a(biFunction, observable, observer) : observer;
    }

    @NonNull
    public static <T> SingleObserver<? super T> onSubscribe(@NonNull Single<T> single, @NonNull SingleObserver<? super T> singleObserver) {
        BiFunction<? super Single, ? super SingleObserver, ? extends SingleObserver> biFunction = v;
        return biFunction != null ? (SingleObserver) a(biFunction, single, singleObserver) : singleObserver;
    }

    @NonNull
    public static CompletableObserver onSubscribe(@NonNull Completable completable, @NonNull CompletableObserver completableObserver) {
        BiFunction<? super Completable, ? super CompletableObserver, ? extends CompletableObserver> biFunction = w;
        return biFunction != null ? (CompletableObserver) a(biFunction, completable, completableObserver) : completableObserver;
    }

    @NonNull
    public static <T> MaybeObserver<? super T> onSubscribe(@NonNull Maybe<T> maybe, @NonNull MaybeObserver<? super T> maybeObserver) {
        BiFunction<? super Maybe, ? super MaybeObserver, ? extends MaybeObserver> biFunction = t;
        return biFunction != null ? (MaybeObserver) a(biFunction, maybe, maybeObserver) : maybeObserver;
    }

    @NonNull
    public static <T> Maybe<T> onAssembly(@NonNull Maybe<T> maybe) {
        Function<? super Maybe, ? extends Maybe> function = o;
        return function != null ? (Maybe) a(function, (T) maybe) : maybe;
    }

    @NonNull
    public static <T> Flowable<T> onAssembly(@NonNull Flowable<T> flowable) {
        Function<? super Flowable, ? extends Flowable> function = k;
        return function != null ? (Flowable) a(function, (T) flowable) : flowable;
    }

    @NonNull
    public static <T> ConnectableFlowable<T> onAssembly(@NonNull ConnectableFlowable<T> connectableFlowable) {
        Function<? super ConnectableFlowable, ? extends ConnectableFlowable> function = l;
        return function != null ? (ConnectableFlowable) a(function, (T) connectableFlowable) : connectableFlowable;
    }

    @NonNull
    public static <T> Observable<T> onAssembly(@NonNull Observable<T> observable) {
        Function<? super Observable, ? extends Observable> function = m;
        return function != null ? (Observable) a(function, (T) observable) : observable;
    }

    @NonNull
    public static <T> ConnectableObservable<T> onAssembly(@NonNull ConnectableObservable<T> connectableObservable) {
        Function<? super ConnectableObservable, ? extends ConnectableObservable> function = n;
        return function != null ? (ConnectableObservable) a(function, (T) connectableObservable) : connectableObservable;
    }

    @NonNull
    public static <T> Single<T> onAssembly(@NonNull Single<T> single) {
        Function<? super Single, ? extends Single> function = p;
        return function != null ? (Single) a(function, (T) single) : single;
    }

    @NonNull
    public static Completable onAssembly(@NonNull Completable completable) {
        Function<? super Completable, ? extends Completable> function = q;
        return function != null ? (Completable) a(function, (T) completable) : completable;
    }

    @Beta
    public static void setOnParallelAssembly(@Nullable Function<? super ParallelFlowable, ? extends ParallelFlowable> function) {
        if (y) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        r = function;
    }

    @Beta
    @Nullable
    public static Function<? super ParallelFlowable, ? extends ParallelFlowable> getOnParallelAssembly() {
        return r;
    }

    @NonNull
    @Beta
    public static <T> ParallelFlowable<T> onAssembly(@NonNull ParallelFlowable<T> parallelFlowable) {
        Function<? super ParallelFlowable, ? extends ParallelFlowable> function = r;
        return function != null ? (ParallelFlowable) a(function, (T) parallelFlowable) : parallelFlowable;
    }

    public static boolean onBeforeBlocking() {
        BooleanSupplier booleanSupplier = x;
        if (booleanSupplier == null) {
            return false;
        }
        try {
            return booleanSupplier.getAsBoolean();
        } catch (Throwable th) {
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }

    public static void setOnBeforeBlocking(@Nullable BooleanSupplier booleanSupplier) {
        if (y) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        x = booleanSupplier;
    }

    @Nullable
    public static BooleanSupplier getOnBeforeBlocking() {
        return x;
    }

    @NonNull
    public static Scheduler createComputationScheduler(@NonNull ThreadFactory threadFactory) {
        return new ComputationScheduler((ThreadFactory) ObjectHelper.requireNonNull(threadFactory, "threadFactory is null"));
    }

    @NonNull
    public static Scheduler createIoScheduler(@NonNull ThreadFactory threadFactory) {
        return new IoScheduler((ThreadFactory) ObjectHelper.requireNonNull(threadFactory, "threadFactory is null"));
    }

    @NonNull
    public static Scheduler createNewThreadScheduler(@NonNull ThreadFactory threadFactory) {
        return new NewThreadScheduler((ThreadFactory) ObjectHelper.requireNonNull(threadFactory, "threadFactory is null"));
    }

    @NonNull
    public static Scheduler createSingleScheduler(@NonNull ThreadFactory threadFactory) {
        return new SingleScheduler((ThreadFactory) ObjectHelper.requireNonNull(threadFactory, "threadFactory is null"));
    }

    @NonNull
    static <T, R> R a(@NonNull Function<T, R> function, @NonNull T t2) {
        try {
            return function.apply(t2);
        } catch (Throwable th) {
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }

    @NonNull
    static <T, U, R> R a(@NonNull BiFunction<T, U, R> biFunction, @NonNull T t2, @NonNull U u2) {
        try {
            return biFunction.apply(t2, u2);
        } catch (Throwable th) {
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }

    @NonNull
    static Scheduler a(@NonNull Callable<Scheduler> callable) {
        try {
            return (Scheduler) ObjectHelper.requireNonNull(callable.call(), "Scheduler Callable result can't be null");
        } catch (Throwable th) {
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }

    @NonNull
    static Scheduler a(@NonNull Function<? super Callable<Scheduler>, ? extends Scheduler> function, Callable<Scheduler> callable) {
        return (Scheduler) ObjectHelper.requireNonNull(a(function, (T) callable), "Scheduler Callable result can't be null");
    }

    private RxJavaPlugins() {
        throw new IllegalStateException("No instances!");
    }
}
