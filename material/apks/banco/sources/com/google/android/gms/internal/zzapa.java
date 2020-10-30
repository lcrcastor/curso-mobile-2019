package com.google.android.gms.internal;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;

public final class zzapa {
    static final Type[] a = new Type[0];

    static final class zza implements Serializable, GenericArrayType {
        private final Type a;

        public zza(Type type) {
            this.a = zzapa.zze(type);
        }

        public boolean equals(Object obj) {
            return (obj instanceof GenericArrayType) && zzapa.zza((Type) this, (Type) (GenericArrayType) obj);
        }

        public Type getGenericComponentType() {
            return this.a;
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        public String toString() {
            return String.valueOf(zzapa.zzg(this.a)).concat("[]");
        }
    }

    static final class zzb implements Serializable, ParameterizedType {
        private final Type a;
        private final Type b;
        private final Type[] c;

        public zzb(Type type, Type type2, Type... typeArr) {
            if (type2 instanceof Class) {
                Class cls = (Class) type2;
                boolean z = true;
                boolean z2 = Modifier.isStatic(cls.getModifiers()) || cls.getEnclosingClass() == null;
                if (type == null && !z2) {
                    z = false;
                }
                zzaoz.zzbs(z);
            }
            this.a = type == null ? null : zzapa.zze(type);
            this.b = zzapa.zze(type2);
            this.c = (Type[]) typeArr.clone();
            for (int i = 0; i < this.c.length; i++) {
                zzaoz.zzy(this.c[i]);
                zzapa.b(this.c[i]);
                this.c[i] = zzapa.zze(this.c[i]);
            }
        }

        public boolean equals(Object obj) {
            return (obj instanceof ParameterizedType) && zzapa.zza((Type) this, (Type) (ParameterizedType) obj);
        }

        public Type[] getActualTypeArguments() {
            return (Type[]) this.c.clone();
        }

        public Type getOwnerType() {
            return this.a;
        }

        public Type getRawType() {
            return this.b;
        }

        public int hashCode() {
            return (Arrays.hashCode(this.c) ^ this.b.hashCode()) ^ zzapa.b((Object) this.a);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder((this.c.length + 1) * 30);
            sb.append(zzapa.zzg(this.b));
            if (this.c.length == 0) {
                return sb.toString();
            }
            sb.append("<");
            sb.append(zzapa.zzg(this.c[0]));
            for (int i = 1; i < this.c.length; i++) {
                sb.append(", ");
                sb.append(zzapa.zzg(this.c[i]));
            }
            sb.append(">");
            return sb.toString();
        }
    }

    static final class zzc implements Serializable, WildcardType {
        private final Type a;
        private final Type b;

        public zzc(Type[] typeArr, Type[] typeArr2) {
            Type type;
            boolean z = true;
            zzaoz.zzbs(typeArr2.length <= 1);
            zzaoz.zzbs(typeArr.length == 1);
            if (typeArr2.length == 1) {
                zzaoz.zzy(typeArr2[0]);
                zzapa.b(typeArr2[0]);
                if (typeArr[0] != Object.class) {
                    z = false;
                }
                zzaoz.zzbs(z);
                this.b = zzapa.zze(typeArr2[0]);
                type = Object.class;
            } else {
                zzaoz.zzy(typeArr[0]);
                zzapa.b(typeArr[0]);
                this.b = null;
                type = zzapa.zze(typeArr[0]);
            }
            this.a = type;
        }

        public boolean equals(Object obj) {
            return (obj instanceof WildcardType) && zzapa.zza((Type) this, (Type) (WildcardType) obj);
        }

        public Type[] getLowerBounds() {
            if (this.b == null) {
                return zzapa.a;
            }
            return new Type[]{this.b};
        }

        public Type[] getUpperBounds() {
            return new Type[]{this.a};
        }

        public int hashCode() {
            return (this.b != null ? this.b.hashCode() + 31 : 1) ^ (this.a.hashCode() + 31);
        }

        public String toString() {
            if (this.b != null) {
                String str = "? super ";
                String valueOf = String.valueOf(zzapa.zzg(this.b));
                return valueOf.length() != 0 ? str.concat(valueOf) : new String(str);
            } else if (this.a == Object.class) {
                return "?";
            } else {
                String str2 = "? extends ";
                String valueOf2 = String.valueOf(zzapa.zzg(this.a));
                return valueOf2.length() != 0 ? str2.concat(valueOf2) : new String(str2);
            }
        }
    }

    private static int a(Object[] objArr, Object obj) {
        for (int i = 0; i < objArr.length; i++) {
            if (obj.equals(objArr[i])) {
                return i;
            }
        }
        throw new NoSuchElementException();
    }

    private static Class<?> a(TypeVariable<?> typeVariable) {
        GenericDeclaration genericDeclaration = typeVariable.getGenericDeclaration();
        if (genericDeclaration instanceof Class) {
            return (Class) genericDeclaration;
        }
        return null;
    }

    static Type a(Type type, Class<?> cls, Class<?> cls2) {
        if (cls2 == cls) {
            return type;
        }
        if (cls2.isInterface()) {
            Class<?>[] interfaces = cls.getInterfaces();
            int length = interfaces.length;
            for (int i = 0; i < length; i++) {
                if (interfaces[i] == cls2) {
                    return cls.getGenericInterfaces()[i];
                }
                if (cls2.isAssignableFrom(interfaces[i])) {
                    return a(cls.getGenericInterfaces()[i], interfaces[i], cls2);
                }
            }
        }
        if (!cls.isInterface()) {
            while (cls != Object.class) {
                Class<?> superclass = cls.getSuperclass();
                if (superclass == cls2) {
                    return cls.getGenericSuperclass();
                }
                if (cls2.isAssignableFrom(superclass)) {
                    return a(cls.getGenericSuperclass(), superclass, cls2);
                }
                cls = superclass;
            }
        }
        return cls2;
    }

    static Type a(Type type, Class<?> cls, TypeVariable<?> typeVariable) {
        Class a2 = a(typeVariable);
        if (a2 == null) {
            return typeVariable;
        }
        Type a3 = a(type, cls, a2);
        if (!(a3 instanceof ParameterizedType)) {
            return typeVariable;
        }
        return ((ParameterizedType) a3).getActualTypeArguments()[a((Object[]) a2.getTypeParameters(), (Object) typeVariable)];
    }

    static boolean a(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    /* access modifiers changed from: private */
    public static int b(Object obj) {
        if (obj != null) {
            return obj.hashCode();
        }
        return 0;
    }

    static Type b(Type type, Class<?> cls, Class<?> cls2) {
        zzaoz.zzbs(cls2.isAssignableFrom(cls));
        return zza(type, cls, a(type, cls, cls2));
    }

    /* access modifiers changed from: private */
    public static void b(Type type) {
        zzaoz.zzbs(!(type instanceof Class) || !((Class) type).isPrimitive());
    }

    public static ParameterizedType zza(Type type, Type type2, Type... typeArr) {
        return new zzb(type, type2, typeArr);
    }

    public static Type zza(Type type, Class<?> cls) {
        Type b = b(type, cls, Collection.class);
        if (b instanceof WildcardType) {
            b = ((WildcardType) b).getUpperBounds()[0];
        }
        return b instanceof ParameterizedType ? ((ParameterizedType) b).getActualTypeArguments()[0] : Object.class;
    }

    public static Type zza(Type type, Class<?> cls, Type type2) {
        while (true) {
            boolean z = r10 instanceof TypeVariable;
            r10 = type2;
            if (z) {
                TypeVariable typeVariable = (TypeVariable) r10;
                Type a2 = a(type, cls, typeVariable);
                if (a2 == typeVariable) {
                    return a2;
                }
                r10 = a2;
            } else {
                if (r10 instanceof Class) {
                    Class cls2 = (Class) r10;
                    if (cls2.isArray()) {
                        Class componentType = cls2.getComponentType();
                        Type zza2 = zza(type, cls, (Type) componentType);
                        return componentType == zza2 ? cls2 : zzb(zza2);
                    }
                }
                if (r10 instanceof GenericArrayType) {
                    GenericArrayType genericArrayType = (GenericArrayType) r10;
                    Type genericComponentType = genericArrayType.getGenericComponentType();
                    Type zza3 = zza(type, cls, genericComponentType);
                    return genericComponentType == zza3 ? genericArrayType : zzb(zza3);
                }
                if (r10 instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) r10;
                    Type ownerType = parameterizedType.getOwnerType();
                    Type zza4 = zza(type, cls, ownerType);
                    boolean z2 = zza4 != ownerType;
                    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                    int length = actualTypeArguments.length;
                    for (int i = 0; i < length; i++) {
                        Type zza5 = zza(type, cls, actualTypeArguments[i]);
                        if (zza5 != actualTypeArguments[i]) {
                            if (!z2) {
                                actualTypeArguments = (Type[]) actualTypeArguments.clone();
                                z2 = true;
                            }
                            actualTypeArguments[i] = zza5;
                        }
                    }
                    if (z2) {
                        parameterizedType = zza(zza4, parameterizedType.getRawType(), actualTypeArguments);
                    }
                    return parameterizedType;
                }
                if (r10 instanceof WildcardType) {
                    r10 = r10;
                    Type[] lowerBounds = r10.getLowerBounds();
                    Type[] upperBounds = r10.getUpperBounds();
                    if (lowerBounds.length == 1) {
                        Type zza6 = zza(type, cls, lowerBounds[0]);
                        if (zza6 != lowerBounds[0]) {
                            return zzd(zza6);
                        }
                    } else if (upperBounds.length == 1) {
                        Type zza7 = zza(type, cls, upperBounds[0]);
                        if (zza7 != upperBounds[0]) {
                            return zzc(zza7);
                        }
                    }
                }
                return r10;
            }
        }
    }

    public static boolean zza(Type type, Type type2) {
        if (type == type2) {
            return true;
        }
        if (type instanceof Class) {
            return type.equals(type2);
        }
        if (type instanceof ParameterizedType) {
            if (!(type2 instanceof ParameterizedType)) {
                return false;
            }
            ParameterizedType parameterizedType = (ParameterizedType) type;
            ParameterizedType parameterizedType2 = (ParameterizedType) type2;
            return a((Object) parameterizedType.getOwnerType(), (Object) parameterizedType2.getOwnerType()) && parameterizedType.getRawType().equals(parameterizedType2.getRawType()) && Arrays.equals(parameterizedType.getActualTypeArguments(), parameterizedType2.getActualTypeArguments());
        } else if (type instanceof GenericArrayType) {
            if (!(type2 instanceof GenericArrayType)) {
                return false;
            }
            return zza(((GenericArrayType) type).getGenericComponentType(), ((GenericArrayType) type2).getGenericComponentType());
        } else if (type instanceof WildcardType) {
            if (!(type2 instanceof WildcardType)) {
                return false;
            }
            WildcardType wildcardType = (WildcardType) type;
            WildcardType wildcardType2 = (WildcardType) type2;
            return Arrays.equals(wildcardType.getUpperBounds(), wildcardType2.getUpperBounds()) && Arrays.equals(wildcardType.getLowerBounds(), wildcardType2.getLowerBounds());
        } else if (!(type instanceof TypeVariable) || !(type2 instanceof TypeVariable)) {
            return false;
        } else {
            TypeVariable typeVariable = (TypeVariable) type;
            TypeVariable typeVariable2 = (TypeVariable) type2;
            return typeVariable.getGenericDeclaration() == typeVariable2.getGenericDeclaration() && typeVariable.getName().equals(typeVariable2.getName());
        }
    }

    public static GenericArrayType zzb(Type type) {
        return new zza(type);
    }

    public static Type[] zzb(Type type, Class<?> cls) {
        if (type == Properties.class) {
            return new Type[]{String.class, String.class};
        }
        Type b = b(type, cls, Map.class);
        if (b instanceof ParameterizedType) {
            return ((ParameterizedType) b).getActualTypeArguments();
        }
        return new Type[]{Object.class, Object.class};
    }

    public static WildcardType zzc(Type type) {
        return new zzc(new Type[]{type}, a);
    }

    public static WildcardType zzd(Type type) {
        return new zzc(new Type[]{Object.class}, new Type[]{type});
    }

    /* JADX WARNING: type inference failed for: r3v8 */
    /* JADX WARNING: type inference failed for: r0v8, types: [com.google.android.gms.internal.zzapa$zza] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.reflect.Type zze(java.lang.reflect.Type r3) {
        /*
            boolean r0 = r3 instanceof java.lang.Class
            if (r0 == 0) goto L_0x001d
            java.lang.Class r3 = (java.lang.Class) r3
            boolean r0 = r3.isArray()
            if (r0 == 0) goto L_0x001a
            com.google.android.gms.internal.zzapa$zza r0 = new com.google.android.gms.internal.zzapa$zza
            java.lang.Class r3 = r3.getComponentType()
            java.lang.reflect.Type r3 = zze(r3)
            r0.<init>(r3)
            r3 = r0
        L_0x001a:
            java.lang.reflect.Type r3 = (java.lang.reflect.Type) r3
            return r3
        L_0x001d:
            boolean r0 = r3 instanceof java.lang.reflect.ParameterizedType
            if (r0 == 0) goto L_0x0035
            java.lang.reflect.ParameterizedType r3 = (java.lang.reflect.ParameterizedType) r3
            com.google.android.gms.internal.zzapa$zzb r0 = new com.google.android.gms.internal.zzapa$zzb
            java.lang.reflect.Type r1 = r3.getOwnerType()
            java.lang.reflect.Type r2 = r3.getRawType()
            java.lang.reflect.Type[] r3 = r3.getActualTypeArguments()
            r0.<init>(r1, r2, r3)
            return r0
        L_0x0035:
            boolean r0 = r3 instanceof java.lang.reflect.GenericArrayType
            if (r0 == 0) goto L_0x0045
            java.lang.reflect.GenericArrayType r3 = (java.lang.reflect.GenericArrayType) r3
            com.google.android.gms.internal.zzapa$zza r0 = new com.google.android.gms.internal.zzapa$zza
            java.lang.reflect.Type r3 = r3.getGenericComponentType()
            r0.<init>(r3)
            return r0
        L_0x0045:
            boolean r0 = r3 instanceof java.lang.reflect.WildcardType
            if (r0 == 0) goto L_0x0059
            java.lang.reflect.WildcardType r3 = (java.lang.reflect.WildcardType) r3
            com.google.android.gms.internal.zzapa$zzc r0 = new com.google.android.gms.internal.zzapa$zzc
            java.lang.reflect.Type[] r1 = r3.getUpperBounds()
            java.lang.reflect.Type[] r3 = r3.getLowerBounds()
            r0.<init>(r1, r3)
            return r0
        L_0x0059:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzapa.zze(java.lang.reflect.Type):java.lang.reflect.Type");
    }

    public static Class<?> zzf(Type type) {
        if (type instanceof Class) {
            return (Class) type;
        }
        if (type instanceof ParameterizedType) {
            Type rawType = ((ParameterizedType) type).getRawType();
            zzaoz.zzbs(rawType instanceof Class);
            return (Class) rawType;
        } else if (type instanceof GenericArrayType) {
            return Array.newInstance(zzf(((GenericArrayType) type).getGenericComponentType()), 0).getClass();
        } else {
            if (type instanceof TypeVariable) {
                return Object.class;
            }
            if (type instanceof WildcardType) {
                return zzf(((WildcardType) type).getUpperBounds()[0]);
            }
            String name = type == null ? "null" : type.getClass().getName();
            String valueOf = String.valueOf("Expected a Class, ParameterizedType, or GenericArrayType, but <");
            String valueOf2 = String.valueOf(type);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 13 + String.valueOf(valueOf2).length() + String.valueOf(name).length());
            sb.append(valueOf);
            sb.append(valueOf2);
            sb.append("> is of type ");
            sb.append(name);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public static String zzg(Type type) {
        return type instanceof Class ? ((Class) type).getName() : type.toString();
    }

    public static Type zzh(Type type) {
        return type instanceof GenericArrayType ? ((GenericArrayType) type).getGenericComponentType() : ((Class) type).getComponentType();
    }
}
