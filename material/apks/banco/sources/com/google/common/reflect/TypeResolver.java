package com.google.common.reflect;

import com.google.common.annotations.Beta;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Maps;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;

@Beta
public final class TypeResolver {
    private final TypeTable a;

    static final class WildcardCapturer {
        private final AtomicInteger a;

        private WildcardCapturer() {
            this.a = new AtomicInteger();
        }

        /* access modifiers changed from: 0000 */
        public Type a(Type type) {
            Preconditions.checkNotNull(type);
            if ((type instanceof Class) || (type instanceof TypeVariable)) {
                return type;
            }
            if (type instanceof GenericArrayType) {
                return Types.a(a(((GenericArrayType) type).getGenericComponentType()));
            }
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                return Types.a(b(parameterizedType.getOwnerType()), (Class) parameterizedType.getRawType(), a(parameterizedType.getActualTypeArguments()));
            } else if (type instanceof WildcardType) {
                WildcardType wildcardType = (WildcardType) type;
                if (wildcardType.getLowerBounds().length != 0) {
                    return type;
                }
                Type[] upperBounds = wildcardType.getUpperBounds();
                StringBuilder sb = new StringBuilder();
                sb.append("capture#");
                sb.append(this.a.incrementAndGet());
                sb.append("-of ? extends ");
                sb.append(Joiner.on('&').join((Object[]) upperBounds));
                return Types.a(WildcardCapturer.class, sb.toString(), wildcardType.getUpperBounds());
            } else {
                throw new AssertionError("must have been one of the known types");
            }
        }

        private Type b(@Nullable Type type) {
            if (type == null) {
                return null;
            }
            return a(type);
        }

        private Type[] a(Type[] typeArr) {
            Type[] typeArr2 = new Type[typeArr.length];
            for (int i = 0; i < typeArr.length; i++) {
                typeArr2[i] = a(typeArr[i]);
            }
            return typeArr2;
        }
    }

    static final class TypeMappingIntrospector extends TypeVisitor {
        private static final WildcardCapturer a = new WildcardCapturer();
        private final Map<TypeVariableKey, Type> b = Maps.newHashMap();

        private TypeMappingIntrospector() {
        }

        static ImmutableMap<TypeVariableKey, Type> a(Type type) {
            TypeMappingIntrospector typeMappingIntrospector = new TypeMappingIntrospector();
            typeMappingIntrospector.a(a.a(type));
            return ImmutableMap.copyOf(typeMappingIntrospector.b);
        }

        /* access modifiers changed from: 0000 */
        public void a(Class<?> cls) {
            a(cls.getGenericSuperclass());
            a(cls.getGenericInterfaces());
        }

        /* access modifiers changed from: 0000 */
        public void a(ParameterizedType parameterizedType) {
            Class cls = (Class) parameterizedType.getRawType();
            TypeVariable[] typeParameters = cls.getTypeParameters();
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            Preconditions.checkState(typeParameters.length == actualTypeArguments.length);
            for (int i = 0; i < typeParameters.length; i++) {
                a(new TypeVariableKey(typeParameters[i]), actualTypeArguments[i]);
            }
            a(cls);
            a(parameterizedType.getOwnerType());
        }

        /* access modifiers changed from: 0000 */
        public void a(TypeVariable<?> typeVariable) {
            a(typeVariable.getBounds());
        }

        /* access modifiers changed from: 0000 */
        public void a(WildcardType wildcardType) {
            a(wildcardType.getUpperBounds());
        }

        private void a(TypeVariableKey typeVariableKey, Type type) {
            if (!this.b.containsKey(typeVariableKey)) {
                Type type2 = type;
                while (type2 != null) {
                    if (typeVariableKey.b(type2)) {
                        while (type != null) {
                            type = (Type) this.b.remove(TypeVariableKey.a(type));
                        }
                        return;
                    }
                    type2 = (Type) this.b.get(TypeVariableKey.a(type2));
                }
                this.b.put(typeVariableKey, type);
            }
        }
    }

    static class TypeTable {
        private final ImmutableMap<TypeVariableKey, Type> a;

        TypeTable() {
            this.a = ImmutableMap.of();
        }

        private TypeTable(ImmutableMap<TypeVariableKey, Type> immutableMap) {
            this.a = immutableMap;
        }

        /* access modifiers changed from: 0000 */
        public final TypeTable a(Map<TypeVariableKey, ? extends Type> map) {
            Builder builder = ImmutableMap.builder();
            builder.putAll((Map<? extends K, ? extends V>) this.a);
            for (Entry entry : map.entrySet()) {
                TypeVariableKey typeVariableKey = (TypeVariableKey) entry.getKey();
                Type type = (Type) entry.getValue();
                Preconditions.checkArgument(!typeVariableKey.b(type), "Type variable %s bound to itself", (Object) typeVariableKey);
                builder.put(typeVariableKey, type);
            }
            return new TypeTable(builder.build());
        }

        /* access modifiers changed from: 0000 */
        public final Type a(final TypeVariable<?> typeVariable) {
            return a(typeVariable, new TypeTable() {
                public Type a(TypeVariable<?> typeVariable, TypeTable typeTable) {
                    if (typeVariable.getGenericDeclaration().equals(typeVariable.getGenericDeclaration())) {
                        return typeVariable;
                    }
                    return this.a(typeVariable, typeTable);
                }
            });
        }

        /* access modifiers changed from: 0000 */
        public Type a(TypeVariable<?> typeVariable, TypeTable typeTable) {
            Type type = (Type) this.a.get(new TypeVariableKey(typeVariable));
            if (type != null) {
                return new TypeResolver(typeTable).resolveType(type);
            }
            Type[] bounds = typeVariable.getBounds();
            if (bounds.length == 0) {
                return typeVariable;
            }
            Type[] a2 = new TypeResolver(typeTable).a(bounds);
            if (!NativeTypeVariableEquals.a || !Arrays.equals(bounds, a2)) {
                return Types.a(typeVariable.getGenericDeclaration(), typeVariable.getName(), a2);
            }
            return typeVariable;
        }
    }

    static final class TypeVariableKey {
        private final TypeVariable<?> a;

        TypeVariableKey(TypeVariable<?> typeVariable) {
            this.a = (TypeVariable) Preconditions.checkNotNull(typeVariable);
        }

        public int hashCode() {
            return Objects.hashCode(this.a.getGenericDeclaration(), this.a.getName());
        }

        public boolean equals(Object obj) {
            if (obj instanceof TypeVariableKey) {
                return a(((TypeVariableKey) obj).a);
            }
            return false;
        }

        public String toString() {
            return this.a.toString();
        }

        static TypeVariableKey a(Type type) {
            if (type instanceof TypeVariable) {
                return new TypeVariableKey((TypeVariable) type);
            }
            return null;
        }

        /* access modifiers changed from: 0000 */
        public boolean b(Type type) {
            if (type instanceof TypeVariable) {
                return a((TypeVariable) type);
            }
            return false;
        }

        private boolean a(TypeVariable<?> typeVariable) {
            return this.a.getGenericDeclaration().equals(typeVariable.getGenericDeclaration()) && this.a.getName().equals(typeVariable.getName());
        }
    }

    public TypeResolver() {
        this.a = new TypeTable();
    }

    private TypeResolver(TypeTable typeTable) {
        this.a = typeTable;
    }

    static TypeResolver a(Type type) {
        return new TypeResolver().a((Map<TypeVariableKey, ? extends Type>) TypeMappingIntrospector.a(type));
    }

    public TypeResolver where(Type type, Type type2) {
        HashMap newHashMap = Maps.newHashMap();
        b(newHashMap, (Type) Preconditions.checkNotNull(type), (Type) Preconditions.checkNotNull(type2));
        return a((Map<TypeVariableKey, ? extends Type>) newHashMap);
    }

    /* access modifiers changed from: 0000 */
    public TypeResolver a(Map<TypeVariableKey, ? extends Type> map) {
        return new TypeResolver(this.a.a(map));
    }

    /* access modifiers changed from: private */
    public static void b(final Map<TypeVariableKey, Type> map, Type type, final Type type2) {
        if (!type.equals(type2)) {
            new TypeVisitor() {
                /* access modifiers changed from: 0000 */
                public void a(TypeVariable<?> typeVariable) {
                    map.put(new TypeVariableKey(typeVariable), type2);
                }

                /* access modifiers changed from: 0000 */
                public void a(WildcardType wildcardType) {
                    if (type2 instanceof WildcardType) {
                        WildcardType wildcardType2 = (WildcardType) type2;
                        Type[] upperBounds = wildcardType.getUpperBounds();
                        Type[] upperBounds2 = wildcardType2.getUpperBounds();
                        Type[] lowerBounds = wildcardType.getLowerBounds();
                        Type[] lowerBounds2 = wildcardType2.getLowerBounds();
                        Preconditions.checkArgument(upperBounds.length == upperBounds2.length && lowerBounds.length == lowerBounds2.length, "Incompatible type: %s vs. %s", (Object) wildcardType, (Object) type2);
                        for (int i = 0; i < upperBounds.length; i++) {
                            TypeResolver.b(map, upperBounds[i], upperBounds2[i]);
                        }
                        for (int i2 = 0; i2 < lowerBounds.length; i2++) {
                            TypeResolver.b(map, lowerBounds[i2], lowerBounds2[i2]);
                        }
                    }
                }

                /* access modifiers changed from: 0000 */
                public void a(ParameterizedType parameterizedType) {
                    if (!(type2 instanceof WildcardType)) {
                        ParameterizedType parameterizedType2 = (ParameterizedType) TypeResolver.b(ParameterizedType.class, type2);
                        if (!(parameterizedType.getOwnerType() == null || parameterizedType2.getOwnerType() == null)) {
                            TypeResolver.b(map, parameterizedType.getOwnerType(), parameterizedType2.getOwnerType());
                        }
                        Preconditions.checkArgument(parameterizedType.getRawType().equals(parameterizedType2.getRawType()), "Inconsistent raw type: %s vs. %s", (Object) parameterizedType, (Object) type2);
                        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                        Type[] actualTypeArguments2 = parameterizedType2.getActualTypeArguments();
                        Preconditions.checkArgument(actualTypeArguments.length == actualTypeArguments2.length, "%s not compatible with %s", (Object) parameterizedType, (Object) parameterizedType2);
                        for (int i = 0; i < actualTypeArguments.length; i++) {
                            TypeResolver.b(map, actualTypeArguments[i], actualTypeArguments2[i]);
                        }
                    }
                }

                /* access modifiers changed from: 0000 */
                public void a(GenericArrayType genericArrayType) {
                    if (!(type2 instanceof WildcardType)) {
                        Type e = Types.e(type2);
                        Preconditions.checkArgument(e != null, "%s is not an array type.", (Object) type2);
                        TypeResolver.b(map, genericArrayType.getGenericComponentType(), e);
                    }
                }

                /* access modifiers changed from: 0000 */
                public void a(Class<?> cls) {
                    if (!(type2 instanceof WildcardType)) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("No type mapping from ");
                        sb.append(cls);
                        sb.append(" to ");
                        sb.append(type2);
                        throw new IllegalArgumentException(sb.toString());
                    }
                }
            }.a(type);
        }
    }

    public Type resolveType(Type type) {
        Preconditions.checkNotNull(type);
        if (type instanceof TypeVariable) {
            return this.a.a((TypeVariable) type);
        }
        if (type instanceof ParameterizedType) {
            return a((ParameterizedType) type);
        }
        if (type instanceof GenericArrayType) {
            return a((GenericArrayType) type);
        }
        return type instanceof WildcardType ? a((WildcardType) type) : type;
    }

    /* access modifiers changed from: private */
    public Type[] a(Type[] typeArr) {
        Type[] typeArr2 = new Type[typeArr.length];
        for (int i = 0; i < typeArr.length; i++) {
            typeArr2[i] = resolveType(typeArr[i]);
        }
        return typeArr2;
    }

    private WildcardType a(WildcardType wildcardType) {
        return new WildcardTypeImpl(a(wildcardType.getLowerBounds()), a(wildcardType.getUpperBounds()));
    }

    private Type a(GenericArrayType genericArrayType) {
        return Types.a(resolveType(genericArrayType.getGenericComponentType()));
    }

    private ParameterizedType a(ParameterizedType parameterizedType) {
        Type type;
        Type ownerType = parameterizedType.getOwnerType();
        if (ownerType == null) {
            type = null;
        } else {
            type = resolveType(ownerType);
        }
        return Types.a(type, (Class) resolveType(parameterizedType.getRawType()), a(parameterizedType.getActualTypeArguments()));
    }

    /* access modifiers changed from: private */
    public static <T> T b(Class<T> cls, Object obj) {
        try {
            return cls.cast(obj);
        } catch (ClassCastException unused) {
            StringBuilder sb = new StringBuilder();
            sb.append(obj);
            sb.append(" is not a ");
            sb.append(cls.getSimpleName());
            throw new IllegalArgumentException(sb.toString());
        }
    }
}
