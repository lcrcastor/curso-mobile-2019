package com.google.common.reflect;

import com.google.common.base.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import javax.annotation.Nullable;

class Element extends AccessibleObject implements Member {
    private final AccessibleObject a;
    private final Member b;

    <M extends AccessibleObject & Member> Element(M m) {
        Preconditions.checkNotNull(m);
        this.a = m;
        this.b = (Member) m;
    }

    public TypeToken<?> getOwnerType() {
        return TypeToken.of(getDeclaringClass());
    }

    public final boolean isAnnotationPresent(Class<? extends Annotation> cls) {
        return this.a.isAnnotationPresent(cls);
    }

    public final <A extends Annotation> A getAnnotation(Class<A> cls) {
        return this.a.getAnnotation(cls);
    }

    public final Annotation[] getAnnotations() {
        return this.a.getAnnotations();
    }

    public final Annotation[] getDeclaredAnnotations() {
        return this.a.getDeclaredAnnotations();
    }

    public final void setAccessible(boolean z) {
        this.a.setAccessible(z);
    }

    public final boolean isAccessible() {
        return this.a.isAccessible();
    }

    public Class<?> getDeclaringClass() {
        return this.b.getDeclaringClass();
    }

    public final String getName() {
        return this.b.getName();
    }

    public final int getModifiers() {
        return this.b.getModifiers();
    }

    public final boolean isSynthetic() {
        return this.b.isSynthetic();
    }

    public final boolean isPublic() {
        return Modifier.isPublic(getModifiers());
    }

    public final boolean isProtected() {
        return Modifier.isProtected(getModifiers());
    }

    public final boolean isPackagePrivate() {
        return !isPrivate() && !isPublic() && !isProtected();
    }

    public final boolean isPrivate() {
        return Modifier.isPrivate(getModifiers());
    }

    public final boolean isStatic() {
        return Modifier.isStatic(getModifiers());
    }

    public final boolean isFinal() {
        return Modifier.isFinal(getModifiers());
    }

    public final boolean isAbstract() {
        return Modifier.isAbstract(getModifiers());
    }

    public final boolean isNative() {
        return Modifier.isNative(getModifiers());
    }

    public final boolean isSynchronized() {
        return Modifier.isSynchronized(getModifiers());
    }

    public boolean equals(@Nullable Object obj) {
        boolean z = false;
        if (!(obj instanceof Element)) {
            return false;
        }
        Element element = (Element) obj;
        if (getOwnerType().equals(element.getOwnerType()) && this.b.equals(element.b)) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        return this.b.hashCode();
    }

    public String toString() {
        return this.b.toString();
    }
}
