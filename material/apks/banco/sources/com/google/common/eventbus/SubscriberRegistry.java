package com.google.common.eventbus;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.reflect.TypeToken;
import com.google.common.util.concurrent.UncheckedExecutionException;
import com.google.j2objc.annotations.Weak;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.annotation.Nullable;

final class SubscriberRegistry {
    private static final LoadingCache<Class<?>, ImmutableList<Method>> c = CacheBuilder.newBuilder().weakKeys().build(new CacheLoader<Class<?>, ImmutableList<Method>>() {
        /* renamed from: a */
        public ImmutableList<Method> load(Class<?> cls) {
            return SubscriberRegistry.d(cls);
        }
    });
    private static final LoadingCache<Class<?>, ImmutableSet<Class<?>>> d = CacheBuilder.newBuilder().weakKeys().build(new CacheLoader<Class<?>, ImmutableSet<Class<?>>>() {
        /* renamed from: a */
        public ImmutableSet<Class<?>> load(Class<?> cls) {
            return ImmutableSet.copyOf((Collection<? extends E>) TypeToken.of(cls).getTypes().rawTypes());
        }
    });
    private final ConcurrentMap<Class<?>, CopyOnWriteArraySet<Subscriber>> a = Maps.newConcurrentMap();
    @Weak
    private final EventBus b;

    static final class MethodIdentifier {
        private final String a;
        private final List<Class<?>> b;

        MethodIdentifier(Method method) {
            this.a = method.getName();
            this.b = Arrays.asList(method.getParameterTypes());
        }

        public int hashCode() {
            return Objects.hashCode(this.a, this.b);
        }

        public boolean equals(@Nullable Object obj) {
            boolean z = false;
            if (!(obj instanceof MethodIdentifier)) {
                return false;
            }
            MethodIdentifier methodIdentifier = (MethodIdentifier) obj;
            if (this.a.equals(methodIdentifier.a) && this.b.equals(methodIdentifier.b)) {
                z = true;
            }
            return z;
        }
    }

    SubscriberRegistry(EventBus eventBus) {
        this.b = (EventBus) Preconditions.checkNotNull(eventBus);
    }

    /* access modifiers changed from: 0000 */
    public void a(Object obj) {
        for (Entry entry : d(obj).asMap().entrySet()) {
            Class cls = (Class) entry.getKey();
            Collection collection = (Collection) entry.getValue();
            CopyOnWriteArraySet copyOnWriteArraySet = (CopyOnWriteArraySet) this.a.get(cls);
            if (copyOnWriteArraySet == null) {
                CopyOnWriteArraySet copyOnWriteArraySet2 = new CopyOnWriteArraySet();
                copyOnWriteArraySet = (CopyOnWriteArraySet) MoreObjects.firstNonNull(this.a.putIfAbsent(cls, copyOnWriteArraySet2), copyOnWriteArraySet2);
            }
            copyOnWriteArraySet.addAll(collection);
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(Object obj) {
        for (Entry entry : d(obj).asMap().entrySet()) {
            Class cls = (Class) entry.getKey();
            Collection collection = (Collection) entry.getValue();
            CopyOnWriteArraySet copyOnWriteArraySet = (CopyOnWriteArraySet) this.a.get(cls);
            if (copyOnWriteArraySet != null) {
                if (!copyOnWriteArraySet.removeAll(collection)) {
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append("missing event subscriber for an annotated method. Is ");
            sb.append(obj);
            sb.append(" registered?");
            throw new IllegalArgumentException(sb.toString());
        }
    }

    /* access modifiers changed from: 0000 */
    public Iterator<Subscriber> c(Object obj) {
        ImmutableSet a2 = a(obj.getClass());
        ArrayList newArrayListWithCapacity = Lists.newArrayListWithCapacity(a2.size());
        Iterator it = a2.iterator();
        while (it.hasNext()) {
            CopyOnWriteArraySet copyOnWriteArraySet = (CopyOnWriteArraySet) this.a.get((Class) it.next());
            if (copyOnWriteArraySet != null) {
                newArrayListWithCapacity.add(copyOnWriteArraySet.iterator());
            }
        }
        return Iterators.concat(newArrayListWithCapacity.iterator());
    }

    private Multimap<Class<?>, Subscriber> d(Object obj) {
        HashMultimap create = HashMultimap.create();
        Iterator it = c(obj.getClass()).iterator();
        while (it.hasNext()) {
            Method method = (Method) it.next();
            create.put(method.getParameterTypes()[0], Subscriber.a(this.b, obj, method));
        }
        return create;
    }

    private static ImmutableList<Method> c(Class<?> cls) {
        return (ImmutableList) c.getUnchecked(cls);
    }

    /* access modifiers changed from: private */
    public static ImmutableList<Method> d(Class<?> cls) {
        Method[] declaredMethods;
        Set<Class> rawTypes = TypeToken.of(cls).getTypes().rawTypes();
        HashMap newHashMap = Maps.newHashMap();
        for (Class declaredMethods2 : rawTypes) {
            for (Method method : declaredMethods2.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Subscribe.class) && !method.isSynthetic()) {
                    Class[] parameterTypes = method.getParameterTypes();
                    boolean z = true;
                    if (parameterTypes.length != 1) {
                        z = false;
                    }
                    Preconditions.checkArgument(z, "Method %s has @Subscribe annotation but has %s parameters.Subscriber methods must have exactly 1 parameter.", (Object) method, parameterTypes.length);
                    MethodIdentifier methodIdentifier = new MethodIdentifier(method);
                    if (!newHashMap.containsKey(methodIdentifier)) {
                        newHashMap.put(methodIdentifier, method);
                    }
                }
            }
        }
        return ImmutableList.copyOf(newHashMap.values());
    }

    @VisibleForTesting
    static ImmutableSet<Class<?>> a(Class<?> cls) {
        try {
            return (ImmutableSet) d.getUnchecked(cls);
        } catch (UncheckedExecutionException e) {
            throw Throwables.propagate(e.getCause());
        }
    }
}
