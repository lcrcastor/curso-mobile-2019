package com.google.common.reflect;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import javax.annotation.Nullable;

@Beta
public abstract class Invokable<T, R> extends Element implements GenericDeclaration {

    static class ConstructorInvokable<T> extends Invokable<T, T> {
        final Constructor<?> a;

        public final boolean isOverridable() {
            return false;
        }

        ConstructorInvokable(Constructor<?> constructor) {
            super(constructor);
            this.a = constructor;
        }

        /* access modifiers changed from: 0000 */
        public final Object a(@Nullable Object obj, Object[] objArr) {
            try {
                return this.a.newInstance(objArr);
            } catch (InstantiationException e) {
                StringBuilder sb = new StringBuilder();
                sb.append(this.a);
                sb.append(" failed.");
                throw new RuntimeException(sb.toString(), e);
            }
        }

        /* access modifiers changed from: 0000 */
        public Type d() {
            Class declaringClass = getDeclaringClass();
            TypeVariable[] typeParameters = declaringClass.getTypeParameters();
            return typeParameters.length > 0 ? Types.a(declaringClass, (Type[]) typeParameters) : declaringClass;
        }

        /* access modifiers changed from: 0000 */
        public Type[] a() {
            Type[] genericParameterTypes = this.a.getGenericParameterTypes();
            if (genericParameterTypes.length > 0 && e()) {
                Class[] parameterTypes = this.a.getParameterTypes();
                if (genericParameterTypes.length == parameterTypes.length && parameterTypes[0] == getDeclaringClass().getEnclosingClass()) {
                    return (Type[]) Arrays.copyOfRange(genericParameterTypes, 1, genericParameterTypes.length);
                }
            }
            return genericParameterTypes;
        }

        /* access modifiers changed from: 0000 */
        public Type[] b() {
            return this.a.getGenericExceptionTypes();
        }

        /* access modifiers changed from: 0000 */
        public final Annotation[][] c() {
            return this.a.getParameterAnnotations();
        }

        public final TypeVariable<?>[] getTypeParameters() {
            TypeVariable[] typeParameters = getDeclaringClass().getTypeParameters();
            TypeVariable[] typeParameters2 = this.a.getTypeParameters();
            TypeVariable<?>[] typeVariableArr = new TypeVariable[(typeParameters.length + typeParameters2.length)];
            System.arraycopy(typeParameters, 0, typeVariableArr, 0, typeParameters.length);
            System.arraycopy(typeParameters2, 0, typeVariableArr, typeParameters.length, typeParameters2.length);
            return typeVariableArr;
        }

        public final boolean isVarArgs() {
            return this.a.isVarArgs();
        }

        private boolean e() {
            Class declaringClass = this.a.getDeclaringClass();
            boolean z = true;
            if (declaringClass.getEnclosingConstructor() != null) {
                return true;
            }
            Method enclosingMethod = declaringClass.getEnclosingMethod();
            if (enclosingMethod != null) {
                return !Modifier.isStatic(enclosingMethod.getModifiers());
            }
            if (declaringClass.getEnclosingClass() == null || Modifier.isStatic(declaringClass.getModifiers())) {
                z = false;
            }
            return z;
        }
    }

    static class MethodInvokable<T> extends Invokable<T, Object> {
        final Method a;

        MethodInvokable(Method method) {
            super(method);
            this.a = method;
        }

        /* access modifiers changed from: 0000 */
        public final Object a(@Nullable Object obj, Object[] objArr) {
            return this.a.invoke(obj, objArr);
        }

        /* access modifiers changed from: 0000 */
        public Type d() {
            return this.a.getGenericReturnType();
        }

        /* access modifiers changed from: 0000 */
        public Type[] a() {
            return this.a.getGenericParameterTypes();
        }

        /* access modifiers changed from: 0000 */
        public Type[] b() {
            return this.a.getGenericExceptionTypes();
        }

        /* access modifiers changed from: 0000 */
        public final Annotation[][] c() {
            return this.a.getParameterAnnotations();
        }

        public final TypeVariable<?>[] getTypeParameters() {
            return this.a.getTypeParameters();
        }

        public final boolean isOverridable() {
            return !isFinal() && !isPrivate() && !isStatic() && !Modifier.isFinal(getDeclaringClass().getModifiers());
        }

        public final boolean isVarArgs() {
            return this.a.isVarArgs();
        }
    }

    /* access modifiers changed from: 0000 */
    public abstract Object a(@Nullable Object obj, Object[] objArr);

    /* access modifiers changed from: 0000 */
    public abstract Type[] a();

    /* access modifiers changed from: 0000 */
    public abstract Type[] b();

    /* access modifiers changed from: 0000 */
    public abstract Annotation[][] c();

    /* access modifiers changed from: 0000 */
    public abstract Type d();

    public abstract boolean isOverridable();

    public abstract boolean isVarArgs();

    public /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    <M extends AccessibleObject & Member> Invokable(M m) {
        super(m);
    }

    public static Invokable<?, Object> from(Method method) {
        return new MethodInvokable(method);
    }

    public static <T> Invokable<T, T> from(Constructor<T> constructor) {
        return new ConstructorInvokable(constructor);
    }

    @CanIgnoreReturnValue
    public final R invoke(@Nullable T t, Object... objArr) {
        return a(t, (Object[]) Preconditions.checkNotNull(objArr));
    }

    public final TypeToken<? extends R> getReturnType() {
        return TypeToken.of(d());
    }

    public final ImmutableList<Parameter> getParameters() {
        Type[] a = a();
        Annotation[][] c = c();
        Builder builder = ImmutableList.builder();
        for (int i = 0; i < a.length; i++) {
            builder.add((Object) new Parameter(this, i, TypeToken.of(a[i]), c[i]));
        }
        return builder.build();
    }

    public final ImmutableList<TypeToken<? extends Throwable>> getExceptionTypes() {
        Builder builder = ImmutableList.builder();
        for (Type of : b()) {
            builder.add((Object) TypeToken.of(of));
        }
        return builder.build();
    }

    public final <R1 extends R> Invokable<T, R1> returning(Class<R1> cls) {
        return returning(TypeToken.of(cls));
    }

    public final <R1 extends R> Invokable<T, R1> returning(TypeToken<R1> typeToken) {
        if (typeToken.isSupertypeOf(getReturnType())) {
            return this;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Invokable is known to return ");
        sb.append(getReturnType());
        sb.append(", not ");
        sb.append(typeToken);
        throw new IllegalArgumentException(sb.toString());
    }

    public final Class<? super T> getDeclaringClass() {
        return super.getDeclaringClass();
    }

    public TypeToken<T> getOwnerType() {
        return TypeToken.of(getDeclaringClass());
    }
}
