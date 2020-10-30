package dagger.internal.loaders;

import dagger.internal.Binding;
import dagger.internal.Linker;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Set;

public final class ReflectiveAtInjectBinding<T> extends Binding<T> {
    private final Field[] a;
    private final ClassLoader b;
    private final Constructor<T> c;
    private final Class<?> d;
    private final String[] e;
    private final Binding<?>[] f;
    private final Binding<?>[] g;
    private Binding<? super T> h;

    private ReflectiveAtInjectBinding(String str, String str2, boolean z, Class<?> cls, Field[] fieldArr, Constructor<T> constructor, int i, Class<?> cls2, String[] strArr) {
        super(str, str2, z, cls);
        this.c = constructor;
        this.a = fieldArr;
        this.d = cls2;
        this.e = strArr;
        this.g = new Binding[i];
        this.f = new Binding[fieldArr.length];
        this.b = cls.getClassLoader();
    }

    public void attach(Linker linker) {
        int i = 0;
        for (int i2 = 0; i2 < this.a.length; i2++) {
            if (this.f[i2] == null) {
                this.f[i2] = linker.requestBinding(this.e[i], this.a[i2], this.b);
            }
            i++;
        }
        if (this.c != null) {
            for (int i3 = 0; i3 < this.g.length; i3++) {
                if (this.g[i3] == null) {
                    this.g[i3] = linker.requestBinding(this.e[i], this.c, this.b);
                }
                i++;
            }
        }
        if (this.d != null && this.h == null) {
            this.h = linker.requestBinding(this.e[i], this.membersKey, this.b, false, true);
        }
    }

    public T get() {
        if (this.c == null) {
            throw new UnsupportedOperationException();
        }
        Object[] objArr = new Object[this.g.length];
        for (int i = 0; i < this.g.length; i++) {
            objArr[i] = this.g[i].get();
        }
        try {
            T newInstance = this.c.newInstance(objArr);
            injectMembers(newInstance);
            return newInstance;
        } catch (InvocationTargetException e2) {
            Throwable cause = e2.getCause();
            throw (cause instanceof RuntimeException ? (RuntimeException) cause : new RuntimeException(cause));
        } catch (IllegalAccessException e3) {
            throw new AssertionError(e3);
        } catch (InstantiationException e4) {
            throw new RuntimeException(e4);
        }
    }

    public void injectMembers(T t) {
        int i = 0;
        while (i < this.a.length) {
            try {
                this.a[i].set(t, this.f[i].get());
                i++;
            } catch (IllegalAccessException e2) {
                throw new AssertionError(e2);
            }
        }
        if (this.h != null) {
            this.h.injectMembers(t);
        }
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        if (this.g != null) {
            Collections.addAll(set, this.g);
        }
        Collections.addAll(set2, this.f);
        if (this.h != null) {
            set2.add(this.h);
        }
    }

    public String toString() {
        return this.provideKey != null ? this.provideKey : this.membersKey;
    }

    /* JADX WARNING: Removed duplicated region for block: B:36:0x00b6  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00fa  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x011f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <T> dagger.internal.Binding<T> create(java.lang.Class<T> r12, boolean r13) {
        /*
            java.lang.Class<javax.inject.Singleton> r0 = javax.inject.Singleton.class
            boolean r4 = r12.isAnnotationPresent(r0)
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r2 = r12
        L_0x0011:
            java.lang.Class<java.lang.Object> r3 = java.lang.Object.class
            r5 = 1
            r6 = 0
            if (r2 == r3) goto L_0x006f
            java.lang.reflect.Field[] r3 = r2.getDeclaredFields()
            int r7 = r3.length
        L_0x001c:
            if (r6 >= r7) goto L_0x006a
            r8 = r3[r6]
            java.lang.Class<javax.inject.Inject> r9 = javax.inject.Inject.class
            boolean r9 = r8.isAnnotationPresent(r9)
            if (r9 == 0) goto L_0x0067
            int r9 = r8.getModifiers()
            boolean r9 = java.lang.reflect.Modifier.isStatic(r9)
            if (r9 == 0) goto L_0x0033
            goto L_0x0067
        L_0x0033:
            int r9 = r8.getModifiers()
            r9 = r9 & 2
            if (r9 == 0) goto L_0x0052
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            java.lang.String r0 = "Can't inject private field: "
            r13.append(r0)
            r13.append(r8)
            java.lang.String r13 = r13.toString()
            r12.<init>(r13)
            throw r12
        L_0x0052:
            r8.setAccessible(r5)
            r1.add(r8)
            java.lang.reflect.Type r9 = r8.getGenericType()
            java.lang.annotation.Annotation[] r10 = r8.getAnnotations()
            java.lang.String r8 = dagger.internal.Keys.get(r9, r10, r8)
            r0.add(r8)
        L_0x0067:
            int r6 = r6 + 1
            goto L_0x001c
        L_0x006a:
            java.lang.Class r2 = r2.getSuperclass()
            goto L_0x0011
        L_0x006f:
            java.lang.reflect.Constructor[] r2 = a(r12)
            int r3 = r2.length
            r7 = 0
            r9 = r7
            r8 = 0
        L_0x0077:
            if (r8 >= r3) goto L_0x0096
            r10 = r2[r8]
            java.lang.Class<javax.inject.Inject> r11 = javax.inject.Inject.class
            boolean r11 = r10.isAnnotationPresent(r11)
            if (r11 != 0) goto L_0x0084
            goto L_0x0093
        L_0x0084:
            if (r9 == 0) goto L_0x0092
            dagger.internal.Binding$InvalidBindingException r13 = new dagger.internal.Binding$InvalidBindingException
            java.lang.String r12 = r12.getName()
            java.lang.String r0 = "has too many injectable constructors"
            r13.<init>(r12, r0)
            throw r13
        L_0x0092:
            r9 = r10
        L_0x0093:
            int r8 = r8 + 1
            goto L_0x0077
        L_0x0096:
            if (r9 != 0) goto L_0x00b3
            boolean r2 = r1.isEmpty()
            if (r2 != 0) goto L_0x00a5
            java.lang.Class[] r13 = new java.lang.Class[r6]     // Catch:{ NoSuchMethodException -> 0x00b3 }
            java.lang.reflect.Constructor r13 = r12.getDeclaredConstructor(r13)     // Catch:{ NoSuchMethodException -> 0x00b3 }
            goto L_0x00b4
        L_0x00a5:
            if (r13 == 0) goto L_0x00b3
            dagger.internal.Binding$InvalidBindingException r13 = new dagger.internal.Binding$InvalidBindingException
            java.lang.String r12 = r12.getName()
            java.lang.String r0 = "has no injectable members. Do you want to add an injectable constructor?"
            r13.<init>(r12, r0)
            throw r13
        L_0x00b3:
            r13 = r9
        L_0x00b4:
            if (r13 == 0) goto L_0x00fa
            int r2 = r13.getModifiers()
            r2 = r2 & 2
            if (r2 == 0) goto L_0x00d5
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Can't inject private constructor: "
            r0.append(r1)
            r0.append(r13)
            java.lang.String r13 = r0.toString()
            r12.<init>(r13)
            throw r12
        L_0x00d5:
            java.lang.String r2 = dagger.internal.Keys.get(r12)
            r13.setAccessible(r5)
            java.lang.reflect.Type[] r3 = r13.getGenericParameterTypes()
            int r5 = r3.length
            if (r5 == 0) goto L_0x00f8
            java.lang.annotation.Annotation[][] r8 = r13.getParameterAnnotations()
        L_0x00e7:
            int r9 = r3.length
            if (r6 >= r9) goto L_0x00f8
            r9 = r3[r6]
            r10 = r8[r6]
            java.lang.String r9 = dagger.internal.Keys.get(r9, r10, r13)
            r0.add(r9)
            int r6 = r6 + 1
            goto L_0x00e7
        L_0x00f8:
            r8 = r5
            goto L_0x0119
        L_0x00fa:
            if (r4 == 0) goto L_0x0117
            java.lang.IllegalArgumentException r13 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "No injectable constructor on @Singleton "
            r0.append(r1)
            java.lang.String r12 = r12.getName()
            r0.append(r12)
            java.lang.String r12 = r0.toString()
            r13.<init>(r12)
            throw r13
        L_0x0117:
            r2 = r7
            r8 = 0
        L_0x0119:
            java.lang.Class r3 = r12.getSuperclass()
            if (r3 == 0) goto L_0x0132
            java.lang.String r5 = r3.getName()
            boolean r5 = dagger.internal.Keys.isPlatformType(r5)
            if (r5 == 0) goto L_0x012b
            r9 = r7
            goto L_0x0133
        L_0x012b:
            java.lang.String r5 = dagger.internal.Keys.getMembersKey(r3)
            r0.add(r5)
        L_0x0132:
            r9 = r3
        L_0x0133:
            java.lang.String r3 = dagger.internal.Keys.getMembersKey(r12)
            dagger.internal.loaders.ReflectiveAtInjectBinding r11 = new dagger.internal.loaders.ReflectiveAtInjectBinding
            int r5 = r1.size()
            java.lang.reflect.Field[] r5 = new java.lang.reflect.Field[r5]
            java.lang.Object[] r1 = r1.toArray(r5)
            r6 = r1
            java.lang.reflect.Field[] r6 = (java.lang.reflect.Field[]) r6
            int r1 = r0.size()
            java.lang.String[] r1 = new java.lang.String[r1]
            java.lang.Object[] r0 = r0.toArray(r1)
            r10 = r0
            java.lang.String[] r10 = (java.lang.String[]) r10
            r1 = r11
            r5 = r12
            r7 = r13
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9, r10)
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: dagger.internal.loaders.ReflectiveAtInjectBinding.create(java.lang.Class, boolean):dagger.internal.Binding");
    }

    private static <T> Constructor<T>[] a(Class<T> cls) {
        return cls.getDeclaredConstructors();
    }
}
