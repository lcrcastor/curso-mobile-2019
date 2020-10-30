package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

@GwtCompatible(emulated = true)
public abstract class CacheLoader<K, V> {

    static final class FunctionToCacheLoader<K, V> extends CacheLoader<K, V> implements Serializable {
        private static final long serialVersionUID = 0;
        private final Function<K, V> a;

        public FunctionToCacheLoader(Function<K, V> function) {
            this.a = (Function) Preconditions.checkNotNull(function);
        }

        public V load(K k) {
            return this.a.apply(Preconditions.checkNotNull(k));
        }
    }

    public static final class InvalidCacheLoadException extends RuntimeException {
        public InvalidCacheLoadException(String str) {
            super(str);
        }
    }

    static final class SupplierToCacheLoader<V> extends CacheLoader<Object, V> implements Serializable {
        private static final long serialVersionUID = 0;
        private final Supplier<V> a;

        public SupplierToCacheLoader(Supplier<V> supplier) {
            this.a = (Supplier) Preconditions.checkNotNull(supplier);
        }

        public V load(Object obj) {
            Preconditions.checkNotNull(obj);
            return this.a.get();
        }
    }

    public static final class UnsupportedLoadingOperationException extends UnsupportedOperationException {
        UnsupportedLoadingOperationException() {
        }
    }

    public abstract V load(K k);

    protected CacheLoader() {
    }

    @GwtIncompatible
    public ListenableFuture<V> reload(K k, V v) {
        Preconditions.checkNotNull(k);
        Preconditions.checkNotNull(v);
        return Futures.immediateFuture(load(k));
    }

    public Map<K, V> loadAll(Iterable<? extends K> iterable) {
        throw new UnsupportedLoadingOperationException();
    }

    public static <K, V> CacheLoader<K, V> from(Function<K, V> function) {
        return new FunctionToCacheLoader(function);
    }

    public static <V> CacheLoader<Object, V> from(Supplier<V> supplier) {
        return new SupplierToCacheLoader(supplier);
    }

    @GwtIncompatible
    public static <K, V> CacheLoader<K, V> asyncReloading(CacheLoader<K, V> cacheLoader, final Executor executor) {
        Preconditions.checkNotNull(cacheLoader);
        Preconditions.checkNotNull(executor);
        return new CacheLoader<K, V>(cacheLoader) {
            final /* synthetic */ CacheLoader a;

            {
                this.a = r1;
            }

            public V load(K k) {
                return this.a.load(k);
            }

            public ListenableFuture<V> reload(final K k, final V v) {
                ListenableFutureTask create = ListenableFutureTask.create(new Callable<V>() {
                    public V call() {
                        return AnonymousClass1.this.a.reload(k, v).get();
                    }
                });
                executor.execute(create);
                return create;
            }

            public Map<K, V> loadAll(Iterable<? extends K> iterable) {
                return this.a.loadAll(iterable);
            }
        };
    }
}
