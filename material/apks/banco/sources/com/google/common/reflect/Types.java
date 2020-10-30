package com.google.common.reflect;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.security.AccessControlException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.Nullable;

final class Types {
    /* access modifiers changed from: private */
    public static final Function<Type, String> a = new Function<Type, String>() {
        /* renamed from: a */
        public String apply(Type type) {
            return JavaVersion.d.c(type);
        }
    };
    /* access modifiers changed from: private */
    public static final Joiner b = Joiner.on(", ").useForNull("null");

    enum ClassOwnership {
        OWNED_BY_ENCLOSING_CLASS {
            /* access modifiers changed from: 0000 */
            @Nullable
            public Class<?> a(Class<?> cls) {
                return cls.getEnclosingClass();
            }
        },
        LOCAL_CLASS_HAS_NO_OWNER {
            /* access modifiers changed from: 0000 */
            @Nullable
            public Class<?> a(Class<?> cls) {
                if (cls.isLocalClass()) {
                    return null;
                }
                return cls.getEnclosingClass();
            }
        };
        
        static final ClassOwnership c = null;

        /* access modifiers changed from: 0000 */
        @Nullable
        public abstract Class<?> a(Class<?> cls);

        static {
            c = a();
        }

        private static ClassOwnership a() {
            ClassOwnership[] values;
            ParameterizedType parameterizedType = (ParameterizedType) new AnonymousClass1LocalClass<String>() {
            }.getClass().getGenericSuperclass();
            for (ClassOwnership classOwnership : values()) {
                if (classOwnership.a(AnonymousClass1LocalClass.class) == parameterizedType.getOwnerType()) {
                    return classOwnership;
                }
            }
            throw new AssertionError();
        }
    }

    static final class ParameterizedTypeImpl implements Serializable, ParameterizedType {
        private static final long serialVersionUID = 0;
        private final Type a;
        private final ImmutableList<Type> b;
        private final Class<?> c;

        ParameterizedTypeImpl(@Nullable Type type, Class<?> cls, Type[] typeArr) {
            Preconditions.checkNotNull(cls);
            Preconditions.checkArgument(typeArr.length == cls.getTypeParameters().length);
            Types.b(typeArr, "type parameter");
            this.a = type;
            this.c = cls;
            this.b = JavaVersion.d.a(typeArr);
        }

        public Type[] getActualTypeArguments() {
            return Types.b((Collection<Type>) this.b);
        }

        public Type getRawType() {
            return this.c;
        }

        public Type getOwnerType() {
            return this.a;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (this.a != null) {
                sb.append(JavaVersion.d.c(this.a));
                sb.append('.');
            }
            sb.append(this.c.getName());
            sb.append('<');
            sb.append(Types.b.join(Iterables.transform(this.b, Types.a)));
            sb.append('>');
            return sb.toString();
        }

        public int hashCode() {
            return ((this.a == null ? 0 : this.a.hashCode()) ^ this.b.hashCode()) ^ this.c.hashCode();
        }

        public boolean equals(Object obj) {
            boolean z = false;
            if (!(obj instanceof ParameterizedType)) {
                return false;
            }
            ParameterizedType parameterizedType = (ParameterizedType) obj;
            if (getRawType().equals(parameterizedType.getRawType()) && Objects.equal(getOwnerType(), parameterizedType.getOwnerType()) && Arrays.equals(getActualTypeArguments(), parameterizedType.getActualTypeArguments())) {
                z = true;
            }
            return z;
        }
    }

    static final class GenericArrayTypeImpl implements Serializable, GenericArrayType {
        private static final long serialVersionUID = 0;
        private final Type a;

        GenericArrayTypeImpl(Type type) {
            this.a = JavaVersion.d.b(type);
        }

        public Type getGenericComponentType() {
            return this.a;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(Types.d(this.a));
            sb.append("[]");
            return sb.toString();
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof GenericArrayType)) {
                return false;
            }
            return Objects.equal(getGenericComponentType(), ((GenericArrayType) obj).getGenericComponentType());
        }
    }

    enum JavaVersion {
        JAVA6 {
            /* access modifiers changed from: 0000 */
            /* renamed from: d */
            public GenericArrayType a(Type type) {
                return new GenericArrayTypeImpl(type);
            }

            /* access modifiers changed from: 0000 */
            public Type b(Type type) {
                Preconditions.checkNotNull(type);
                if (type instanceof Class) {
                    Class cls = (Class) type;
                    if (cls.isArray()) {
                        return new GenericArrayTypeImpl(cls.getComponentType());
                    }
                }
                return type;
            }
        },
        JAVA7 {
            /* access modifiers changed from: 0000 */
            public Type a(Type type) {
                if (type instanceof Class) {
                    return Types.a((Class) type);
                }
                return new GenericArrayTypeImpl(type);
            }

            /* access modifiers changed from: 0000 */
            public Type b(Type type) {
                return (Type) Preconditions.checkNotNull(type);
            }
        },
        JAVA8 {
            /* access modifiers changed from: 0000 */
            public Type a(Type type) {
                return JAVA7.a(type);
            }

            /* access modifiers changed from: 0000 */
            public Type b(Type type) {
                return JAVA7.b(type);
            }

            /* access modifiers changed from: 0000 */
            public String c(Type type) {
                try {
                    return (String) Type.class.getMethod("getTypeName", new Class[0]).invoke(type, new Object[0]);
                } catch (NoSuchMethodException unused) {
                    throw new AssertionError("Type.getTypeName should be available in Java 8");
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e2) {
                    throw new RuntimeException(e2);
                }
            }
        };
        
        static final JavaVersion d = null;

        /* access modifiers changed from: 0000 */
        public abstract Type a(Type type);

        /* access modifiers changed from: 0000 */
        public abstract Type b(Type type);

        /* access modifiers changed from: 0000 */
        public String c(Type type) {
            return Types.d(type);
        }

        /* access modifiers changed from: 0000 */
        public final ImmutableList<Type> a(Type[] typeArr) {
            Builder builder = ImmutableList.builder();
            for (Type b : typeArr) {
                builder.add((Object) b(b));
            }
            return builder.build();
        }
    }

    static final class NativeTypeVariableEquals<X> {
        static final boolean a = (!NativeTypeVariableEquals.class.getTypeParameters()[0].equals(Types.a(NativeTypeVariableEquals.class, "X", new Type[0])));

        NativeTypeVariableEquals() {
        }
    }

    static final class TypeVariableImpl<D extends GenericDeclaration> {
        private final D a;
        private final String b;
        private final ImmutableList<Type> c;

        TypeVariableImpl(D d, String str, Type[] typeArr) {
            Types.b(typeArr, "bound for type variable");
            this.a = (GenericDeclaration) Preconditions.checkNotNull(d);
            this.b = (String) Preconditions.checkNotNull(str);
            this.c = ImmutableList.copyOf((E[]) typeArr);
        }

        public D a() {
            return this.a;
        }

        public String b() {
            return this.b;
        }

        public String toString() {
            return this.b;
        }

        public int hashCode() {
            return this.a.hashCode() ^ this.b.hashCode();
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (NativeTypeVariableEquals.a) {
                if (obj == null || !Proxy.isProxyClass(obj.getClass()) || !(Proxy.getInvocationHandler(obj) instanceof TypeVariableInvocationHandler)) {
                    return false;
                }
                TypeVariableImpl a2 = ((TypeVariableInvocationHandler) Proxy.getInvocationHandler(obj)).b;
                if (!this.b.equals(a2.b()) || !this.a.equals(a2.a()) || !this.c.equals(a2.c)) {
                    z = false;
                }
                return z;
            } else if (!(obj instanceof TypeVariable)) {
                return false;
            } else {
                TypeVariable typeVariable = (TypeVariable) obj;
                if (!this.b.equals(typeVariable.getName()) || !this.a.equals(typeVariable.getGenericDeclaration())) {
                    z = false;
                }
                return z;
            }
        }
    }

    static final class TypeVariableInvocationHandler implements InvocationHandler {
        private static final ImmutableMap<String, Method> a;
        /* access modifiers changed from: private */
        public final TypeVariableImpl<?> b;

        static {
            Method[] methods;
            ImmutableMap.Builder builder = ImmutableMap.builder();
            for (Method method : TypeVariableImpl.class.getMethods()) {
                if (method.getDeclaringClass().equals(TypeVariableImpl.class)) {
                    try {
                        method.setAccessible(true);
                    } catch (AccessControlException unused) {
                    }
                    builder.put(method.getName(), method);
                }
            }
            a = builder.build();
        }

        TypeVariableInvocationHandler(TypeVariableImpl<?> typeVariableImpl) {
            this.b = typeVariableImpl;
        }

        public Object invoke(Object obj, Method method, Object[] objArr) {
            String name = method.getName();
            Method method2 = (Method) a.get(name);
            if (method2 == null) {
                throw new UnsupportedOperationException(name);
            }
            try {
                return method2.invoke(this.b, objArr);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        }
    }

    static final class WildcardTypeImpl implements Serializable, WildcardType {
        private static final long serialVersionUID = 0;
        private final ImmutableList<Type> a;
        private final ImmutableList<Type> b;

        WildcardTypeImpl(Type[] typeArr, Type[] typeArr2) {
            Types.b(typeArr, "lower bound for wildcard");
            Types.b(typeArr2, "upper bound for wildcard");
            this.a = JavaVersion.d.a(typeArr);
            this.b = JavaVersion.d.a(typeArr2);
        }

        public Type[] getLowerBounds() {
            return Types.b((Collection<Type>) this.a);
        }

        public Type[] getUpperBounds() {
            return Types.b((Collection<Type>) this.b);
        }

        public boolean equals(Object obj) {
            boolean z = false;
            if (!(obj instanceof WildcardType)) {
                return false;
            }
            WildcardType wildcardType = (WildcardType) obj;
            if (this.a.equals(Arrays.asList(wildcardType.getLowerBounds())) && this.b.equals(Arrays.asList(wildcardType.getUpperBounds()))) {
                z = true;
            }
            return z;
        }

        public int hashCode() {
            return this.a.hashCode() ^ this.b.hashCode();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("?");
            Iterator it = this.a.iterator();
            while (it.hasNext()) {
                Type type = (Type) it.next();
                sb.append(" super ");
                sb.append(JavaVersion.d.c(type));
            }
            for (Type type2 : Types.b((Iterable<Type>) this.b)) {
                sb.append(" extends ");
                sb.append(JavaVersion.d.c(type2));
            }
            return sb.toString();
        }
    }

    static Type a(Type type) {
        if (!(type instanceof WildcardType)) {
            return JavaVersion.d.a(type);
        }
        WildcardType wildcardType = (WildcardType) type;
        Type[] lowerBounds = wildcardType.getLowerBounds();
        boolean z = true;
        Preconditions.checkArgument(lowerBounds.length <= 1, "Wildcard cannot have more than one lower bounds.");
        if (lowerBounds.length == 1) {
            return c(a(lowerBounds[0]));
        }
        Type[] upperBounds = wildcardType.getUpperBounds();
        if (upperBounds.length != 1) {
            z = false;
        }
        Preconditions.checkArgument(z, "Wildcard should have only one upper bound.");
        return b(a(upperBounds[0]));
    }

    static ParameterizedType a(@Nullable Type type, Class<?> cls, Type... typeArr) {
        if (type == null) {
            return a(cls, typeArr);
        }
        Preconditions.checkNotNull(typeArr);
        Preconditions.checkArgument(cls.getEnclosingClass() != null, "Owner type for unenclosed %s", (Object) cls);
        return new ParameterizedTypeImpl(type, cls, typeArr);
    }

    static ParameterizedType a(Class<?> cls, Type... typeArr) {
        return new ParameterizedTypeImpl(ClassOwnership.c.a(cls), cls, typeArr);
    }

    static <D extends GenericDeclaration> TypeVariable<D> a(D d, String str, Type... typeArr) {
        if (typeArr.length == 0) {
            typeArr = new Type[]{Object.class};
        }
        return b(d, str, typeArr);
    }

    @VisibleForTesting
    static WildcardType b(Type type) {
        return new WildcardTypeImpl(new Type[0], new Type[]{type});
    }

    @VisibleForTesting
    static WildcardType c(Type type) {
        return new WildcardTypeImpl(new Type[]{type}, new Type[]{Object.class});
    }

    static String d(Type type) {
        return type instanceof Class ? ((Class) type).getName() : type.toString();
    }

    @Nullable
    static Type e(Type type) {
        Preconditions.checkNotNull(type);
        final AtomicReference atomicReference = new AtomicReference();
        new TypeVisitor() {
            /* access modifiers changed from: 0000 */
            public void a(TypeVariable<?> typeVariable) {
                atomicReference.set(Types.b(typeVariable.getBounds()));
            }

            /* access modifiers changed from: 0000 */
            public void a(WildcardType wildcardType) {
                atomicReference.set(Types.b(wildcardType.getUpperBounds()));
            }

            /* access modifiers changed from: 0000 */
            public void a(GenericArrayType genericArrayType) {
                atomicReference.set(genericArrayType.getGenericComponentType());
            }

            /* access modifiers changed from: 0000 */
            public void a(Class<?> cls) {
                atomicReference.set(cls.getComponentType());
            }
        }.a(type);
        return (Type) atomicReference.get();
    }

    /* access modifiers changed from: private */
    @Nullable
    public static Type b(Type[] typeArr) {
        for (Type e : typeArr) {
            Type e2 = e(e);
            if (e2 != null) {
                if (e2 instanceof Class) {
                    Class cls = (Class) e2;
                    if (cls.isPrimitive()) {
                        return cls;
                    }
                }
                return b(e2);
            }
        }
        return null;
    }

    private static <D extends GenericDeclaration> TypeVariable<D> b(D d, String str, Type[] typeArr) {
        return (TypeVariable) Reflection.newProxy(TypeVariable.class, new TypeVariableInvocationHandler(new TypeVariableImpl(d, str, typeArr)));
    }

    /* access modifiers changed from: private */
    public static Type[] b(Collection<Type> collection) {
        return (Type[]) collection.toArray(new Type[collection.size()]);
    }

    /* access modifiers changed from: private */
    public static Iterable<Type> b(Iterable<Type> iterable) {
        return Iterables.filter(iterable, Predicates.not(Predicates.equalTo(Object.class)));
    }

    /* access modifiers changed from: private */
    public static void b(Type[] typeArr, String str) {
        for (Class cls : typeArr) {
            if (cls instanceof Class) {
                Class cls2 = cls;
                Preconditions.checkArgument(!cls2.isPrimitive(), "Primitive type '%s' used as %s", (Object) cls2, (Object) str);
            }
        }
    }

    static Class<?> a(Class<?> cls) {
        return Array.newInstance(cls, 0).getClass();
    }

    private Types() {
    }
}
