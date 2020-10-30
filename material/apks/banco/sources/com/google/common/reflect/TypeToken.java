package com.google.common.reflect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ForwardingSet;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Primitives;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

@Beta
public abstract class TypeToken<T> extends TypeCapture<T> implements Serializable {
    /* access modifiers changed from: private */
    public final Type a;
    private transient TypeResolver b;

    static abstract class TypeCollector<K> {
        static final TypeCollector<TypeToken<?>> a = new TypeCollector<TypeToken<?>>() {
            /* access modifiers changed from: 0000 */
            /* renamed from: a */
            public Class<?> b(TypeToken<?> typeToken) {
                return typeToken.getRawType();
            }

            /* access modifiers changed from: 0000 */
            /* renamed from: b */
            public Iterable<? extends TypeToken<?>> c(TypeToken<?> typeToken) {
                return typeToken.c();
            }

            /* access modifiers changed from: 0000 */
            @Nullable
            /* renamed from: c */
            public TypeToken<?> d(TypeToken<?> typeToken) {
                return typeToken.b();
            }
        };
        static final TypeCollector<Class<?>> b = new TypeCollector<Class<?>>() {
            /* access modifiers changed from: 0000 */
            /* renamed from: a */
            public Class<?> b(Class<?> cls) {
                return cls;
            }

            /* access modifiers changed from: 0000 */
            /* renamed from: b */
            public Iterable<? extends Class<?>> c(Class<?> cls) {
                return Arrays.asList(cls.getInterfaces());
            }

            /* access modifiers changed from: 0000 */
            @Nullable
            /* renamed from: c */
            public Class<?> d(Class<?> cls) {
                return cls.getSuperclass();
            }
        };

        static class ForwardingTypeCollector<K> extends TypeCollector<K> {
            private final TypeCollector<K> c;

            ForwardingTypeCollector(TypeCollector<K> typeCollector) {
                super();
                this.c = typeCollector;
            }

            /* access modifiers changed from: 0000 */
            public Class<?> b(K k) {
                return this.c.b(k);
            }

            /* access modifiers changed from: 0000 */
            public Iterable<? extends K> c(K k) {
                return this.c.c(k);
            }

            /* access modifiers changed from: 0000 */
            public K d(K k) {
                return this.c.d(k);
            }
        }

        /* access modifiers changed from: 0000 */
        public abstract Class<?> b(K k);

        /* access modifiers changed from: 0000 */
        public abstract Iterable<? extends K> c(K k);

        /* access modifiers changed from: 0000 */
        @Nullable
        public abstract K d(K k);

        private TypeCollector() {
        }

        /* access modifiers changed from: 0000 */
        public final TypeCollector<K> a() {
            return new ForwardingTypeCollector<K>(this) {
                /* access modifiers changed from: 0000 */
                public Iterable<? extends K> c(K k) {
                    return ImmutableSet.of();
                }

                /* access modifiers changed from: 0000 */
                public ImmutableList<K> a(Iterable<? extends K> iterable) {
                    Builder builder = ImmutableList.builder();
                    for (Object next : iterable) {
                        if (!b(next).isInterface()) {
                            builder.add(next);
                        }
                    }
                    return super.a((Iterable<? extends K>) builder.build());
                }
            };
        }

        /* access modifiers changed from: 0000 */
        public final ImmutableList<K> a(K k) {
            return a((Iterable<? extends K>) ImmutableList.of(k));
        }

        /* access modifiers changed from: 0000 */
        public ImmutableList<K> a(Iterable<? extends K> iterable) {
            HashMap newHashMap = Maps.newHashMap();
            for (Object a2 : iterable) {
                a((K) a2, (Map<? super K, Integer>) newHashMap);
            }
            return a((Map<K, V>) newHashMap, (Comparator<? super V>) Ordering.natural().reverse());
        }

        @CanIgnoreReturnValue
        private int a(K k, Map<? super K, Integer> map) {
            Integer num = (Integer) map.get(k);
            if (num != null) {
                return num.intValue();
            }
            int isInterface = b(k).isInterface();
            for (Object a2 : c(k)) {
                isInterface = Math.max(isInterface, a((K) a2, map));
            }
            Object d = d(k);
            if (d != null) {
                isInterface = Math.max(isInterface, a((K) d, map));
            }
            int i = isInterface + 1;
            map.put(k, Integer.valueOf(i));
            return i;
        }

        private static <K, V> ImmutableList<K> a(final Map<K, V> map, final Comparator<? super V> comparator) {
            return new Ordering<K>() {
                public int compare(K k, K k2) {
                    return comparator.compare(map.get(k), map.get(k2));
                }
            }.immutableSortedCopy(map.keySet());
        }
    }

    static class Bounds {
        private final Type[] a;
        private final boolean b;

        Bounds(Type[] typeArr, boolean z) {
            this.a = typeArr;
            this.b = z;
        }

        /* access modifiers changed from: 0000 */
        public boolean a(Type type) {
            for (Type of : this.a) {
                if (TypeToken.of(of).isSubtypeOf(type) == this.b) {
                    return this.b;
                }
            }
            return !this.b;
        }

        /* access modifiers changed from: 0000 */
        public boolean b(Type type) {
            TypeToken of = TypeToken.of(type);
            for (Type isSubtypeOf : this.a) {
                if (of.isSubtypeOf(isSubtypeOf) == this.b) {
                    return this.b;
                }
            }
            return !this.b;
        }
    }

    final class ClassSet extends TypeSet {
        private static final long serialVersionUID = 0;
        private transient ImmutableSet<TypeToken<? super T>> c;

        public TypeSet classes() {
            return this;
        }

        private ClassSet() {
            super();
        }

        /* access modifiers changed from: protected */
        public Set<TypeToken<? super T>> delegate() {
            ImmutableSet<TypeToken<? super T>> immutableSet = this.c;
            if (immutableSet != null) {
                return immutableSet;
            }
            ImmutableSet<TypeToken<? super T>> set = FluentIterable.from((Iterable<E>) TypeCollector.a.a().a(TypeToken.this)).filter((Predicate<? super E>) TypeFilter.IGNORE_TYPE_VARIABLE_OR_WILDCARD).toSet();
            this.c = set;
            return set;
        }

        public Set<Class<? super T>> rawTypes() {
            return ImmutableSet.copyOf((Collection<? extends E>) TypeCollector.b.a().a((Iterable<? extends K>) TypeToken.this.f()));
        }

        public TypeSet interfaces() {
            throw new UnsupportedOperationException("classes().interfaces() not supported.");
        }

        private Object readResolve() {
            return TypeToken.this.getTypes().classes();
        }
    }

    final class InterfaceSet extends TypeSet {
        private static final long serialVersionUID = 0;
        private final transient TypeSet c;
        private transient ImmutableSet<TypeToken<? super T>> d;

        public TypeSet interfaces() {
            return this;
        }

        InterfaceSet(TypeSet typeSet) {
            super();
            this.c = typeSet;
        }

        /* access modifiers changed from: protected */
        public Set<TypeToken<? super T>> delegate() {
            ImmutableSet<TypeToken<? super T>> immutableSet = this.d;
            if (immutableSet != null) {
                return immutableSet;
            }
            ImmutableSet<TypeToken<? super T>> set = FluentIterable.from((Iterable<E>) this.c).filter((Predicate<? super E>) TypeFilter.INTERFACE_ONLY).toSet();
            this.d = set;
            return set;
        }

        public Set<Class<? super T>> rawTypes() {
            return FluentIterable.from((Iterable<E>) TypeCollector.b.a((Iterable<? extends K>) TypeToken.this.f())).filter((Predicate<? super E>) new Predicate<Class<?>>() {
                /* renamed from: a */
                public boolean apply(Class<?> cls) {
                    return cls.isInterface();
                }
            }).toSet();
        }

        public TypeSet classes() {
            throw new UnsupportedOperationException("interfaces().classes() not supported.");
        }

        private Object readResolve() {
            return TypeToken.this.getTypes().interfaces();
        }
    }

    static final class SimpleTypeToken<T> extends TypeToken<T> {
        private static final long serialVersionUID = 0;

        SimpleTypeToken(Type type) {
            super(type);
        }
    }

    enum TypeFilter implements Predicate<TypeToken<?>> {
        IGNORE_TYPE_VARIABLE_OR_WILDCARD {
            /* renamed from: a */
            public boolean apply(TypeToken<?> typeToken) {
                return !(typeToken.a instanceof TypeVariable) && !(typeToken.a instanceof WildcardType);
            }
        },
        INTERFACE_ONLY {
            /* renamed from: a */
            public boolean apply(TypeToken<?> typeToken) {
                return typeToken.getRawType().isInterface();
            }
        }
    }

    public class TypeSet extends ForwardingSet<TypeToken<? super T>> implements Serializable {
        private static final long serialVersionUID = 0;
        private transient ImmutableSet<TypeToken<? super T>> a;

        TypeSet() {
        }

        public TypeSet interfaces() {
            return new InterfaceSet(this);
        }

        public TypeSet classes() {
            return new ClassSet();
        }

        /* access modifiers changed from: protected */
        public Set<TypeToken<? super T>> delegate() {
            ImmutableSet<TypeToken<? super T>> immutableSet = this.a;
            if (immutableSet != null) {
                return immutableSet;
            }
            ImmutableSet<TypeToken<? super T>> set = FluentIterable.from((Iterable<E>) TypeCollector.a.a(TypeToken.this)).filter((Predicate<? super E>) TypeFilter.IGNORE_TYPE_VARIABLE_OR_WILDCARD).toSet();
            this.a = set;
            return set;
        }

        public Set<Class<? super T>> rawTypes() {
            return ImmutableSet.copyOf((Collection<? extends E>) TypeCollector.b.a((Iterable<? extends K>) TypeToken.this.f()));
        }
    }

    protected TypeToken() {
        this.a = a();
        Preconditions.checkState(!(this.a instanceof TypeVariable), "Cannot construct a TypeToken for a type variable.\nYou probably meant to call new TypeToken<%s>(getClass()) that can resolve the type variable for you.\nIf you do need to create a TypeToken of a type variable, please use TypeToken.of() instead.", (Object) this.a);
    }

    protected TypeToken(Class<?> cls) {
        Type a2 = super.a();
        if (a2 instanceof Class) {
            this.a = a2;
        } else {
            this.a = of(cls).resolveType(a2).a;
        }
    }

    private TypeToken(Type type) {
        this.a = (Type) Preconditions.checkNotNull(type);
    }

    public static <T> TypeToken<T> of(Class<T> cls) {
        return new SimpleTypeToken(cls);
    }

    public static TypeToken<?> of(Type type) {
        return new SimpleTypeToken(type);
    }

    public final Class<? super T> getRawType() {
        return (Class) f().iterator().next();
    }

    public final Type getType() {
        return this.a;
    }

    public final <X> TypeToken<T> where(TypeParameter<X> typeParameter, TypeToken<X> typeToken) {
        return new SimpleTypeToken(new TypeResolver().a((Map<TypeVariableKey, ? extends Type>) ImmutableMap.of(new TypeVariableKey(typeParameter.a), typeToken.a)).resolveType(this.a));
    }

    public final <X> TypeToken<T> where(TypeParameter<X> typeParameter, Class<X> cls) {
        return where(typeParameter, of(cls));
    }

    public final TypeToken<?> resolveType(Type type) {
        Preconditions.checkNotNull(type);
        TypeResolver typeResolver = this.b;
        if (typeResolver == null) {
            typeResolver = TypeResolver.a(this.a);
            this.b = typeResolver;
        }
        return of(typeResolver.resolveType(type));
    }

    /* access modifiers changed from: private */
    public Type[] a(Type[] typeArr) {
        for (int i = 0; i < typeArr.length; i++) {
            typeArr[i] = resolveType(typeArr[i]).getType();
        }
        return typeArr;
    }

    private TypeToken<?> a(Type type) {
        TypeToken<?> resolveType = resolveType(type);
        resolveType.b = this.b;
        return resolveType;
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public final TypeToken<? super T> b() {
        if (this.a instanceof TypeVariable) {
            return b(((TypeVariable) this.a).getBounds()[0]);
        }
        if (this.a instanceof WildcardType) {
            return b(((WildcardType) this.a).getUpperBounds()[0]);
        }
        Type genericSuperclass = getRawType().getGenericSuperclass();
        if (genericSuperclass == null) {
            return null;
        }
        return a(genericSuperclass);
    }

    @Nullable
    private TypeToken<? super T> b(Type type) {
        TypeToken<? super T> of = of(type);
        if (of.getRawType().isInterface()) {
            return null;
        }
        return of;
    }

    /* access modifiers changed from: 0000 */
    public final ImmutableList<TypeToken<? super T>> c() {
        if (this.a instanceof TypeVariable) {
            return b(((TypeVariable) this.a).getBounds());
        }
        if (this.a instanceof WildcardType) {
            return b(((WildcardType) this.a).getUpperBounds());
        }
        Builder builder = ImmutableList.builder();
        for (Type a2 : getRawType().getGenericInterfaces()) {
            builder.add((Object) a(a2));
        }
        return builder.build();
    }

    private ImmutableList<TypeToken<? super T>> b(Type[] typeArr) {
        Builder builder = ImmutableList.builder();
        for (Type of : typeArr) {
            TypeToken of2 = of(of);
            if (of2.getRawType().isInterface()) {
                builder.add((Object) of2);
            }
        }
        return builder.build();
    }

    public final TypeSet getTypes() {
        return new TypeSet<>();
    }

    public final TypeToken<? super T> getSupertype(Class<? super T> cls) {
        Preconditions.checkArgument(b(cls), "%s is not a super class of %s", (Object) cls, (Object) this);
        if (this.a instanceof TypeVariable) {
            return a(cls, ((TypeVariable) this.a).getBounds());
        }
        if (this.a instanceof WildcardType) {
            return a(cls, ((WildcardType) this.a).getUpperBounds());
        }
        if (cls.isArray()) {
            return c(cls);
        }
        return a(a(cls).a);
    }

    public final TypeToken<? extends T> getSubtype(Class<?> cls) {
        Preconditions.checkArgument(!(this.a instanceof TypeVariable), "Cannot get subtype of type variable <%s>", (Object) this);
        if (this.a instanceof WildcardType) {
            return b(cls, ((WildcardType) this.a).getLowerBounds());
        }
        if (isArray()) {
            return d(cls);
        }
        Preconditions.checkArgument(getRawType().isAssignableFrom(cls), "%s isn't a subclass of %s", (Object) cls, (Object) this);
        return of(e(cls));
    }

    public final boolean isSupertypeOf(TypeToken<?> typeToken) {
        return typeToken.isSubtypeOf(getType());
    }

    public final boolean isSupertypeOf(Type type) {
        return of(type).isSubtypeOf(getType());
    }

    public final boolean isSubtypeOf(TypeToken<?> typeToken) {
        return isSubtypeOf(typeToken.getType());
    }

    public final boolean isSubtypeOf(Type type) {
        Preconditions.checkNotNull(type);
        if (type instanceof WildcardType) {
            return d(((WildcardType) type).getLowerBounds()).b(this.a);
        }
        if (this.a instanceof WildcardType) {
            return d(((WildcardType) this.a).getUpperBounds()).a(type);
        }
        boolean z = false;
        if (this.a instanceof TypeVariable) {
            if (this.a.equals(type) || d(((TypeVariable) this.a).getBounds()).a(type)) {
                z = true;
            }
            return z;
        } else if (this.a instanceof GenericArrayType) {
            return of(type).b((GenericArrayType) this.a);
        } else {
            if (type instanceof Class) {
                return b((Class) type);
            }
            if (type instanceof ParameterizedType) {
                return a((ParameterizedType) type);
            }
            if (type instanceof GenericArrayType) {
                return a((GenericArrayType) type);
            }
            return false;
        }
    }

    public final boolean isArray() {
        return getComponentType() != null;
    }

    public final boolean isPrimitive() {
        return (this.a instanceof Class) && ((Class) this.a).isPrimitive();
    }

    public final TypeToken<T> wrap() {
        return isPrimitive() ? of(Primitives.wrap((Class) this.a)) : this;
    }

    private boolean e() {
        return Primitives.allWrapperTypes().contains(this.a);
    }

    public final TypeToken<T> unwrap() {
        return e() ? of(Primitives.unwrap((Class) this.a)) : this;
    }

    @Nullable
    public final TypeToken<?> getComponentType() {
        Type e = Types.e(this.a);
        if (e == null) {
            return null;
        }
        return of(e);
    }

    public final Invokable<T, Object> method(Method method) {
        Preconditions.checkArgument(b(method.getDeclaringClass()), "%s not declared by %s", (Object) method, (Object) this);
        return new MethodInvokable<T>(method) {
            /* access modifiers changed from: 0000 */
            public Type d() {
                return TypeToken.this.resolveType(super.d()).getType();
            }

            /* access modifiers changed from: 0000 */
            public Type[] a() {
                return TypeToken.this.a(super.a());
            }

            /* access modifiers changed from: 0000 */
            public Type[] b() {
                return TypeToken.this.a(super.b());
            }

            public TypeToken<T> getOwnerType() {
                return TypeToken.this;
            }

            public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append(getOwnerType());
                sb.append(".");
                sb.append(super.toString());
                return sb.toString();
            }
        };
    }

    public final Invokable<T, T> constructor(Constructor<?> constructor) {
        Preconditions.checkArgument(constructor.getDeclaringClass() == getRawType(), "%s not declared by %s", (Object) constructor, (Object) getRawType());
        return new ConstructorInvokable<T>(constructor) {
            /* access modifiers changed from: 0000 */
            public Type d() {
                return TypeToken.this.resolveType(super.d()).getType();
            }

            /* access modifiers changed from: 0000 */
            public Type[] a() {
                return TypeToken.this.a(super.a());
            }

            /* access modifiers changed from: 0000 */
            public Type[] b() {
                return TypeToken.this.a(super.b());
            }

            public TypeToken<T> getOwnerType() {
                return TypeToken.this;
            }

            public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append(getOwnerType());
                sb.append("(");
                sb.append(Joiner.on(", ").join((Object[]) a()));
                sb.append(")");
                return sb.toString();
            }
        };
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof TypeToken)) {
            return false;
        }
        return this.a.equals(((TypeToken) obj).a);
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    public String toString() {
        return Types.d(this.a);
    }

    /* access modifiers changed from: protected */
    public Object writeReplace() {
        return of(new TypeResolver().resolveType(this.a));
    }

    /* access modifiers changed from: 0000 */
    @CanIgnoreReturnValue
    public final TypeToken<T> d() {
        new TypeVisitor() {
            /* access modifiers changed from: 0000 */
            public void a(TypeVariable<?> typeVariable) {
                StringBuilder sb = new StringBuilder();
                sb.append(TypeToken.this.a);
                sb.append("contains a type variable and is not safe for the operation");
                throw new IllegalArgumentException(sb.toString());
            }

            /* access modifiers changed from: 0000 */
            public void a(WildcardType wildcardType) {
                a(wildcardType.getLowerBounds());
                a(wildcardType.getUpperBounds());
            }

            /* access modifiers changed from: 0000 */
            public void a(ParameterizedType parameterizedType) {
                a(parameterizedType.getActualTypeArguments());
                a(parameterizedType.getOwnerType());
            }

            /* access modifiers changed from: 0000 */
            public void a(GenericArrayType genericArrayType) {
                a(genericArrayType.getGenericComponentType());
            }
        }.a(this.a);
        return this;
    }

    private boolean b(Class<?> cls) {
        Iterator it = f().iterator();
        while (it.hasNext()) {
            if (cls.isAssignableFrom((Class) it.next())) {
                return true;
            }
        }
        return false;
    }

    private boolean a(ParameterizedType parameterizedType) {
        Class rawType = of((Type) parameterizedType).getRawType();
        boolean z = false;
        if (!b(rawType)) {
            return false;
        }
        TypeVariable[] typeParameters = rawType.getTypeParameters();
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        for (int i = 0; i < typeParameters.length; i++) {
            if (!resolveType(typeParameters[i]).c(actualTypeArguments[i])) {
                return false;
            }
        }
        if (Modifier.isStatic(((Class) parameterizedType.getRawType()).getModifiers()) || parameterizedType.getOwnerType() == null || d(parameterizedType.getOwnerType())) {
            z = true;
        }
        return z;
    }

    private boolean a(GenericArrayType genericArrayType) {
        if (this.a instanceof Class) {
            Class cls = (Class) this.a;
            if (!cls.isArray()) {
                return false;
            }
            return of(cls.getComponentType()).isSubtypeOf(genericArrayType.getGenericComponentType());
        } else if (this.a instanceof GenericArrayType) {
            return of(((GenericArrayType) this.a).getGenericComponentType()).isSubtypeOf(genericArrayType.getGenericComponentType());
        } else {
            return false;
        }
    }

    private boolean b(GenericArrayType genericArrayType) {
        if (this.a instanceof Class) {
            Class cls = (Class) this.a;
            if (!cls.isArray()) {
                return cls.isAssignableFrom(Object[].class);
            }
            return of(genericArrayType.getGenericComponentType()).isSubtypeOf((Type) cls.getComponentType());
        } else if (this.a instanceof GenericArrayType) {
            return of(genericArrayType.getGenericComponentType()).isSubtypeOf(((GenericArrayType) this.a).getGenericComponentType());
        } else {
            return false;
        }
    }

    private boolean c(Type type) {
        boolean z = true;
        if (this.a.equals(type)) {
            return true;
        }
        if (!(type instanceof WildcardType)) {
            return false;
        }
        WildcardType wildcardType = (WildcardType) type;
        if (!c(wildcardType.getUpperBounds()).b(this.a) || !c(wildcardType.getLowerBounds()).a(this.a)) {
            z = false;
        }
        return z;
    }

    private static Bounds c(Type[] typeArr) {
        return new Bounds(typeArr, false);
    }

    private static Bounds d(Type[] typeArr) {
        return new Bounds(typeArr, true);
    }

    /* access modifiers changed from: private */
    public ImmutableSet<Class<? super T>> f() {
        final ImmutableSet.Builder builder = ImmutableSet.builder();
        new TypeVisitor() {
            /* access modifiers changed from: 0000 */
            public void a(TypeVariable<?> typeVariable) {
                a(typeVariable.getBounds());
            }

            /* access modifiers changed from: 0000 */
            public void a(WildcardType wildcardType) {
                a(wildcardType.getUpperBounds());
            }

            /* access modifiers changed from: 0000 */
            public void a(ParameterizedType parameterizedType) {
                builder.add((Object) (Class) parameterizedType.getRawType());
            }

            /* access modifiers changed from: 0000 */
            public void a(Class<?> cls) {
                builder.add((Object) cls);
            }

            /* access modifiers changed from: 0000 */
            public void a(GenericArrayType genericArrayType) {
                builder.add((Object) Types.a(TypeToken.of(genericArrayType.getGenericComponentType()).getRawType()));
            }
        }.a(this.a);
        return builder.build();
    }

    private boolean d(Type type) {
        Iterator it = getTypes().iterator();
        while (it.hasNext()) {
            Type g = ((TypeToken) it.next()).g();
            if (g != null && of(g).isSubtypeOf(type)) {
                return true;
            }
        }
        return false;
    }

    @Nullable
    private Type g() {
        if (this.a instanceof ParameterizedType) {
            return ((ParameterizedType) this.a).getOwnerType();
        }
        if (this.a instanceof Class) {
            return ((Class) this.a).getEnclosingClass();
        }
        return null;
    }

    @VisibleForTesting
    static <T> TypeToken<? extends T> a(Class<T> cls) {
        if (cls.isArray()) {
            return of(Types.a(a(cls.getComponentType()).a));
        }
        TypeVariable[] typeParameters = cls.getTypeParameters();
        Type type = (!cls.isMemberClass() || Modifier.isStatic(cls.getModifiers())) ? null : a(cls.getEnclosingClass()).a;
        if (typeParameters.length > 0 || (type != null && type != cls.getEnclosingClass())) {
            return of((Type) Types.a(type, cls, (Type[]) typeParameters));
        }
        return of(cls);
    }

    private TypeToken<? super T> a(Class<? super T> cls, Type[] typeArr) {
        for (Type of : typeArr) {
            TypeToken of2 = of(of);
            if (of2.isSubtypeOf((Type) cls)) {
                return of2.getSupertype(cls);
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(cls);
        sb.append(" isn't a super type of ");
        sb.append(this);
        throw new IllegalArgumentException(sb.toString());
    }

    private TypeToken<? extends T> b(Class<?> cls, Type[] typeArr) {
        if (typeArr.length > 0) {
            return of(typeArr[0]).getSubtype(cls);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(cls);
        sb.append(" isn't a subclass of ");
        sb.append(this);
        throw new IllegalArgumentException(sb.toString());
    }

    private TypeToken<? super T> c(Class<? super T> cls) {
        return of(e(((TypeToken) Preconditions.checkNotNull(getComponentType(), "%s isn't a super type of %s", (Object) cls, (Object) this)).getSupertype(cls.getComponentType()).a));
    }

    private TypeToken<? extends T> d(Class<?> cls) {
        return of(e(getComponentType().getSubtype(cls.getComponentType()).a));
    }

    private Type e(Class<?> cls) {
        if ((this.a instanceof Class) && (cls.getTypeParameters().length == 0 || getRawType().getTypeParameters().length != 0)) {
            return cls;
        }
        TypeToken a2 = a(cls);
        return new TypeResolver().where(a2.getSupertype(getRawType()).a, this.a).resolveType(a2.a);
    }

    private static Type e(Type type) {
        return JavaVersion.JAVA7.a(type);
    }
}
