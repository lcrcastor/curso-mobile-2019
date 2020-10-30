package com.nineoldandroids.animation;

import android.util.Log;
import com.nineoldandroids.util.FloatProperty;
import com.nineoldandroids.util.IntProperty;
import com.nineoldandroids.util.Property;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class PropertyValuesHolder implements Cloneable {
    private static final TypeEvaluator h = new IntEvaluator();
    private static final TypeEvaluator i = new FloatEvaluator();
    private static Class[] j = {Float.TYPE, Float.class, Double.TYPE, Integer.TYPE, Double.class, Integer.class};
    private static Class[] k = {Integer.TYPE, Integer.class, Float.TYPE, Double.TYPE, Float.class, Double.class};
    private static Class[] l = {Double.TYPE, Double.class, Float.TYPE, Integer.TYPE, Float.class, Integer.class};
    private static final HashMap<Class, HashMap<String, Method>> m = new HashMap<>();
    private static final HashMap<Class, HashMap<String, Method>> n = new HashMap<>();
    String a;
    Method b;
    Class c;
    KeyframeSet d;
    final ReentrantReadWriteLock e;
    final Object[] f;
    private Method g;
    protected Property mProperty;
    private TypeEvaluator o;
    private Object p;

    static class FloatPropertyValuesHolder extends PropertyValuesHolder {
        FloatKeyframeSet g;
        float h;
        private FloatProperty i;

        public FloatPropertyValuesHolder(String str, FloatKeyframeSet floatKeyframeSet) {
            super(str);
            this.c = Float.TYPE;
            this.d = floatKeyframeSet;
            this.g = (FloatKeyframeSet) this.d;
        }

        public FloatPropertyValuesHolder(Property property, FloatKeyframeSet floatKeyframeSet) {
            super(property);
            this.c = Float.TYPE;
            this.d = floatKeyframeSet;
            this.g = (FloatKeyframeSet) this.d;
            if (property instanceof FloatProperty) {
                this.i = (FloatProperty) this.mProperty;
            }
        }

        public FloatPropertyValuesHolder(String str, float... fArr) {
            super(str);
            setFloatValues(fArr);
        }

        public FloatPropertyValuesHolder(Property property, float... fArr) {
            super(property);
            setFloatValues(fArr);
            if (property instanceof FloatProperty) {
                this.i = (FloatProperty) this.mProperty;
            }
        }

        public void setFloatValues(float... fArr) {
            PropertyValuesHolder.super.setFloatValues(fArr);
            this.g = (FloatKeyframeSet) this.d;
        }

        /* access modifiers changed from: 0000 */
        public void a(float f) {
            this.h = this.g.b(f);
        }

        /* access modifiers changed from: 0000 */
        public Object b() {
            return Float.valueOf(this.h);
        }

        /* renamed from: c */
        public FloatPropertyValuesHolder clone() {
            FloatPropertyValuesHolder floatPropertyValuesHolder = (FloatPropertyValuesHolder) PropertyValuesHolder.super.clone();
            floatPropertyValuesHolder.g = (FloatKeyframeSet) floatPropertyValuesHolder.d;
            return floatPropertyValuesHolder;
        }

        /* access modifiers changed from: 0000 */
        public void d(Object obj) {
            if (this.i != null) {
                this.i.setValue(obj, this.h);
            } else if (this.mProperty != null) {
                this.mProperty.set(obj, Float.valueOf(this.h));
            } else {
                if (this.b != null) {
                    try {
                        this.f[0] = Float.valueOf(this.h);
                        this.b.invoke(obj, this.f);
                    } catch (InvocationTargetException e) {
                        Log.e("PropertyValuesHolder", e.toString());
                    } catch (IllegalAccessException e2) {
                        Log.e("PropertyValuesHolder", e2.toString());
                    }
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(Class cls) {
            if (this.mProperty == null) {
                PropertyValuesHolder.super.a(cls);
            }
        }
    }

    static class IntPropertyValuesHolder extends PropertyValuesHolder {
        IntKeyframeSet g;
        int h;
        private IntProperty i;

        public IntPropertyValuesHolder(String str, IntKeyframeSet intKeyframeSet) {
            super(str);
            this.c = Integer.TYPE;
            this.d = intKeyframeSet;
            this.g = (IntKeyframeSet) this.d;
        }

        public IntPropertyValuesHolder(Property property, IntKeyframeSet intKeyframeSet) {
            super(property);
            this.c = Integer.TYPE;
            this.d = intKeyframeSet;
            this.g = (IntKeyframeSet) this.d;
            if (property instanceof IntProperty) {
                this.i = (IntProperty) this.mProperty;
            }
        }

        public IntPropertyValuesHolder(String str, int... iArr) {
            super(str);
            setIntValues(iArr);
        }

        public IntPropertyValuesHolder(Property property, int... iArr) {
            super(property);
            setIntValues(iArr);
            if (property instanceof IntProperty) {
                this.i = (IntProperty) this.mProperty;
            }
        }

        public void setIntValues(int... iArr) {
            PropertyValuesHolder.super.setIntValues(iArr);
            this.g = (IntKeyframeSet) this.d;
        }

        /* access modifiers changed from: 0000 */
        public void a(float f) {
            this.h = this.g.b(f);
        }

        /* access modifiers changed from: 0000 */
        public Object b() {
            return Integer.valueOf(this.h);
        }

        /* renamed from: c */
        public IntPropertyValuesHolder clone() {
            IntPropertyValuesHolder intPropertyValuesHolder = (IntPropertyValuesHolder) PropertyValuesHolder.super.clone();
            intPropertyValuesHolder.g = (IntKeyframeSet) intPropertyValuesHolder.d;
            return intPropertyValuesHolder;
        }

        /* access modifiers changed from: 0000 */
        public void d(Object obj) {
            if (this.i != null) {
                this.i.setValue(obj, this.h);
            } else if (this.mProperty != null) {
                this.mProperty.set(obj, Integer.valueOf(this.h));
            } else {
                if (this.b != null) {
                    try {
                        this.f[0] = Integer.valueOf(this.h);
                        this.b.invoke(obj, this.f);
                    } catch (InvocationTargetException e) {
                        Log.e("PropertyValuesHolder", e.toString());
                    } catch (IllegalAccessException e2) {
                        Log.e("PropertyValuesHolder", e2.toString());
                    }
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(Class cls) {
            if (this.mProperty == null) {
                PropertyValuesHolder.super.a(cls);
            }
        }
    }

    private PropertyValuesHolder(String str) {
        this.b = null;
        this.g = null;
        this.d = null;
        this.e = new ReentrantReadWriteLock();
        this.f = new Object[1];
        this.a = str;
    }

    private PropertyValuesHolder(Property property) {
        this.b = null;
        this.g = null;
        this.d = null;
        this.e = new ReentrantReadWriteLock();
        this.f = new Object[1];
        this.mProperty = property;
        if (property != null) {
            this.a = property.getName();
        }
    }

    public static PropertyValuesHolder ofInt(String str, int... iArr) {
        return new IntPropertyValuesHolder(str, iArr);
    }

    public static PropertyValuesHolder ofInt(Property<?, Integer> property, int... iArr) {
        return new IntPropertyValuesHolder((Property) property, iArr);
    }

    public static PropertyValuesHolder ofFloat(String str, float... fArr) {
        return new FloatPropertyValuesHolder(str, fArr);
    }

    public static PropertyValuesHolder ofFloat(Property<?, Float> property, float... fArr) {
        return new FloatPropertyValuesHolder((Property) property, fArr);
    }

    public static PropertyValuesHolder ofObject(String str, TypeEvaluator typeEvaluator, Object... objArr) {
        PropertyValuesHolder propertyValuesHolder = new PropertyValuesHolder(str);
        propertyValuesHolder.setObjectValues(objArr);
        propertyValuesHolder.setEvaluator(typeEvaluator);
        return propertyValuesHolder;
    }

    public static <V> PropertyValuesHolder ofObject(Property property, TypeEvaluator<V> typeEvaluator, V... vArr) {
        PropertyValuesHolder propertyValuesHolder = new PropertyValuesHolder(property);
        propertyValuesHolder.setObjectValues(vArr);
        propertyValuesHolder.setEvaluator(typeEvaluator);
        return propertyValuesHolder;
    }

    public static PropertyValuesHolder ofKeyframe(String str, Keyframe... keyframeArr) {
        KeyframeSet a2 = KeyframeSet.a(keyframeArr);
        if (a2 instanceof IntKeyframeSet) {
            return new IntPropertyValuesHolder(str, (IntKeyframeSet) a2);
        }
        if (a2 instanceof FloatKeyframeSet) {
            return new FloatPropertyValuesHolder(str, (FloatKeyframeSet) a2);
        }
        PropertyValuesHolder propertyValuesHolder = new PropertyValuesHolder(str);
        propertyValuesHolder.d = a2;
        propertyValuesHolder.c = keyframeArr[0].getType();
        return propertyValuesHolder;
    }

    public static PropertyValuesHolder ofKeyframe(Property property, Keyframe... keyframeArr) {
        KeyframeSet a2 = KeyframeSet.a(keyframeArr);
        if (a2 instanceof IntKeyframeSet) {
            return new IntPropertyValuesHolder(property, (IntKeyframeSet) a2);
        }
        if (a2 instanceof FloatKeyframeSet) {
            return new FloatPropertyValuesHolder(property, (FloatKeyframeSet) a2);
        }
        PropertyValuesHolder propertyValuesHolder = new PropertyValuesHolder(property);
        propertyValuesHolder.d = a2;
        propertyValuesHolder.c = keyframeArr[0].getType();
        return propertyValuesHolder;
    }

    public void setIntValues(int... iArr) {
        this.c = Integer.TYPE;
        this.d = KeyframeSet.a(iArr);
    }

    public void setFloatValues(float... fArr) {
        this.c = Float.TYPE;
        this.d = KeyframeSet.a(fArr);
    }

    public void setKeyframes(Keyframe... keyframeArr) {
        int length = keyframeArr.length;
        Keyframe[] keyframeArr2 = new Keyframe[Math.max(length, 2)];
        this.c = keyframeArr[0].getType();
        for (int i2 = 0; i2 < length; i2++) {
            keyframeArr2[i2] = keyframeArr[i2];
        }
        this.d = new KeyframeSet(keyframeArr2);
    }

    public void setObjectValues(Object... objArr) {
        this.c = objArr[0].getClass();
        this.d = KeyframeSet.a(objArr);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:35:?, code lost:
        r7 = r9.getDeclaredMethod(r10, r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:?, code lost:
        r7.setAccessible(true);
        r8.c = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0087, code lost:
        return r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0088, code lost:
        r5 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0089, code lost:
        r0 = r0 + 1;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:34:0x007e */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.reflect.Method a(java.lang.Class r9, java.lang.String r10, java.lang.Class r11) {
        /*
            r8 = this;
            java.lang.String r0 = r8.a
            java.lang.String r10 = a(r10, r0)
            r0 = 0
            r1 = 1
            if (r11 != 0) goto L_0x003d
            java.lang.reflect.Method r11 = r9.getMethod(r10, r0)     // Catch:{ NoSuchMethodException -> 0x0010 }
            goto L_0x00af
        L_0x0010:
            r11 = move-exception
            java.lang.reflect.Method r9 = r9.getDeclaredMethod(r10, r0)     // Catch:{ NoSuchMethodException -> 0x0019 }
            r9.setAccessible(r1)     // Catch:{ NoSuchMethodException -> 0x001a }
            goto L_0x003a
        L_0x0019:
            r9 = r0
        L_0x001a:
            java.lang.String r10 = "PropertyValuesHolder"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Couldn't find no-arg method for property "
            r0.append(r1)
            java.lang.String r1 = r8.a
            r0.append(r1)
            java.lang.String r1 = ": "
            r0.append(r1)
            r0.append(r11)
            java.lang.String r11 = r0.toString()
            android.util.Log.e(r10, r11)
        L_0x003a:
            r11 = r9
            goto L_0x00af
        L_0x003d:
            java.lang.Class[] r11 = new java.lang.Class[r1]
            java.lang.Class r2 = r8.c
            java.lang.Class<java.lang.Float> r3 = java.lang.Float.class
            boolean r2 = r2.equals(r3)
            r3 = 0
            if (r2 == 0) goto L_0x004d
            java.lang.Class[] r2 = j
            goto L_0x006d
        L_0x004d:
            java.lang.Class r2 = r8.c
            java.lang.Class<java.lang.Integer> r4 = java.lang.Integer.class
            boolean r2 = r2.equals(r4)
            if (r2 == 0) goto L_0x005a
            java.lang.Class[] r2 = k
            goto L_0x006d
        L_0x005a:
            java.lang.Class r2 = r8.c
            java.lang.Class<java.lang.Double> r4 = java.lang.Double.class
            boolean r2 = r2.equals(r4)
            if (r2 == 0) goto L_0x0067
            java.lang.Class[] r2 = l
            goto L_0x006d
        L_0x0067:
            java.lang.Class[] r2 = new java.lang.Class[r1]
            java.lang.Class r4 = r8.c
            r2[r3] = r4
        L_0x006d:
            int r4 = r2.length
            r5 = r0
            r0 = 0
        L_0x0070:
            if (r0 >= r4) goto L_0x008c
            r6 = r2[r0]
            r11[r3] = r6
            java.lang.reflect.Method r7 = r9.getMethod(r10, r11)     // Catch:{ NoSuchMethodException -> 0x007e }
            r8.c = r6     // Catch:{ NoSuchMethodException -> 0x007d }
            return r7
        L_0x007d:
            r5 = r7
        L_0x007e:
            java.lang.reflect.Method r7 = r9.getDeclaredMethod(r10, r11)     // Catch:{ NoSuchMethodException -> 0x0089 }
            r7.setAccessible(r1)     // Catch:{ NoSuchMethodException -> 0x0088 }
            r8.c = r6     // Catch:{ NoSuchMethodException -> 0x0088 }
            return r7
        L_0x0088:
            r5 = r7
        L_0x0089:
            int r0 = r0 + 1
            goto L_0x0070
        L_0x008c:
            java.lang.String r9 = "PropertyValuesHolder"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "Couldn't find setter/getter for property "
            r10.append(r11)
            java.lang.String r11 = r8.a
            r10.append(r11)
            java.lang.String r11 = " with value type "
            r10.append(r11)
            java.lang.Class r11 = r8.c
            r10.append(r11)
            java.lang.String r10 = r10.toString()
            android.util.Log.e(r9, r10)
            r11 = r5
        L_0x00af:
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.nineoldandroids.animation.PropertyValuesHolder.a(java.lang.Class, java.lang.String, java.lang.Class):java.lang.reflect.Method");
    }

    private Method a(Class cls, HashMap<Class, HashMap<String, Method>> hashMap, String str, Class cls2) {
        try {
            this.e.writeLock().lock();
            HashMap hashMap2 = (HashMap) hashMap.get(cls);
            Method method = hashMap2 != null ? (Method) hashMap2.get(this.a) : null;
            if (method == null) {
                method = a(cls, str, cls2);
                if (hashMap2 == null) {
                    hashMap2 = new HashMap();
                    hashMap.put(cls, hashMap2);
                }
                hashMap2.put(this.a, method);
            }
            return method;
        } finally {
            this.e.writeLock().unlock();
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(Class cls) {
        this.b = a(cls, m, "set", this.c);
    }

    private void b(Class cls) {
        this.g = a(cls, n, "get", null);
    }

    /* access modifiers changed from: 0000 */
    public void a(Object obj) {
        if (this.mProperty != null) {
            try {
                this.mProperty.get(obj);
                Iterator it = this.d.e.iterator();
                while (it.hasNext()) {
                    Keyframe keyframe = (Keyframe) it.next();
                    if (!keyframe.hasValue()) {
                        keyframe.setValue(this.mProperty.get(obj));
                    }
                }
                return;
            } catch (ClassCastException unused) {
                StringBuilder sb = new StringBuilder();
                sb.append("No such property (");
                sb.append(this.mProperty.getName());
                sb.append(") on target object ");
                sb.append(obj);
                sb.append(". Trying reflection instead");
                Log.e("PropertyValuesHolder", sb.toString());
                this.mProperty = null;
            }
        }
        Class cls = obj.getClass();
        if (this.b == null) {
            a(cls);
        }
        Iterator it2 = this.d.e.iterator();
        while (it2.hasNext()) {
            Keyframe keyframe2 = (Keyframe) it2.next();
            if (!keyframe2.hasValue()) {
                if (this.g == null) {
                    b(cls);
                }
                try {
                    keyframe2.setValue(this.g.invoke(obj, new Object[0]));
                } catch (InvocationTargetException e2) {
                    Log.e("PropertyValuesHolder", e2.toString());
                } catch (IllegalAccessException e3) {
                    Log.e("PropertyValuesHolder", e3.toString());
                }
            }
        }
    }

    private void a(Object obj, Keyframe keyframe) {
        if (this.mProperty != null) {
            keyframe.setValue(this.mProperty.get(obj));
        }
        try {
            if (this.g == null) {
                b(obj.getClass());
            }
            keyframe.setValue(this.g.invoke(obj, new Object[0]));
        } catch (InvocationTargetException e2) {
            Log.e("PropertyValuesHolder", e2.toString());
        } catch (IllegalAccessException e3) {
            Log.e("PropertyValuesHolder", e3.toString());
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(Object obj) {
        a(obj, (Keyframe) this.d.e.get(0));
    }

    /* access modifiers changed from: 0000 */
    public void c(Object obj) {
        a(obj, (Keyframe) this.d.e.get(this.d.e.size() - 1));
    }

    public PropertyValuesHolder clone() {
        try {
            PropertyValuesHolder propertyValuesHolder = (PropertyValuesHolder) super.clone();
            propertyValuesHolder.a = this.a;
            propertyValuesHolder.mProperty = this.mProperty;
            propertyValuesHolder.d = this.d.clone();
            propertyValuesHolder.o = this.o;
            return propertyValuesHolder;
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }

    /* access modifiers changed from: 0000 */
    public void d(Object obj) {
        if (this.mProperty != null) {
            this.mProperty.set(obj, b());
        }
        if (this.b != null) {
            try {
                this.f[0] = b();
                this.b.invoke(obj, this.f);
            } catch (InvocationTargetException e2) {
                Log.e("PropertyValuesHolder", e2.toString());
            } catch (IllegalAccessException e3) {
                Log.e("PropertyValuesHolder", e3.toString());
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        if (this.o == null) {
            TypeEvaluator typeEvaluator = this.c == Integer.class ? h : this.c == Float.class ? i : null;
            this.o = typeEvaluator;
        }
        if (this.o != null) {
            this.d.a(this.o);
        }
    }

    public void setEvaluator(TypeEvaluator typeEvaluator) {
        this.o = typeEvaluator;
        this.d.a(typeEvaluator);
    }

    /* access modifiers changed from: 0000 */
    public void a(float f2) {
        this.p = this.d.a(f2);
    }

    public void setPropertyName(String str) {
        this.a = str;
    }

    public void setProperty(Property property) {
        this.mProperty = property;
    }

    public String getPropertyName() {
        return this.a;
    }

    /* access modifiers changed from: 0000 */
    public Object b() {
        return this.p;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.a);
        sb.append(": ");
        sb.append(this.d.toString());
        return sb.toString();
    }

    static String a(String str, String str2) {
        if (str2 == null || str2.length() == 0) {
            return str;
        }
        char upperCase = Character.toUpperCase(str2.charAt(0));
        String substring = str2.substring(1);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(upperCase);
        sb.append(substring);
        return sb.toString();
    }
}
