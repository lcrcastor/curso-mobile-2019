package com.nineoldandroids.animation;

import android.view.View;
import com.nineoldandroids.util.Property;
import com.nineoldandroids.view.animation.AnimatorProxy;
import java.util.HashMap;
import java.util.Map;

public final class ObjectAnimator extends ValueAnimator {
    private static final Map<String, Property> h = new HashMap();
    private Object i;
    private String j;
    private Property k;

    static {
        h.put("alpha", PreHoneycombCompat.a);
        h.put("pivotX", PreHoneycombCompat.b);
        h.put("pivotY", PreHoneycombCompat.c);
        h.put("translationX", PreHoneycombCompat.d);
        h.put("translationY", PreHoneycombCompat.e);
        h.put("rotation", PreHoneycombCompat.f);
        h.put("rotationX", PreHoneycombCompat.g);
        h.put("rotationY", PreHoneycombCompat.h);
        h.put("scaleX", PreHoneycombCompat.i);
        h.put("scaleY", PreHoneycombCompat.j);
        h.put("scrollX", PreHoneycombCompat.k);
        h.put("scrollY", PreHoneycombCompat.l);
        h.put("x", PreHoneycombCompat.m);
        h.put("y", PreHoneycombCompat.n);
    }

    public void setPropertyName(String str) {
        if (this.f != null) {
            PropertyValuesHolder propertyValuesHolder = this.f[0];
            String propertyName = propertyValuesHolder.getPropertyName();
            propertyValuesHolder.setPropertyName(str);
            this.g.remove(propertyName);
            this.g.put(str, propertyValuesHolder);
        }
        this.j = str;
        this.e = false;
    }

    public void setProperty(Property property) {
        if (this.f != null) {
            PropertyValuesHolder propertyValuesHolder = this.f[0];
            String propertyName = propertyValuesHolder.getPropertyName();
            propertyValuesHolder.setProperty(property);
            this.g.remove(propertyName);
            this.g.put(this.j, propertyValuesHolder);
        }
        if (this.k != null) {
            this.j = property.getName();
        }
        this.k = property;
        this.e = false;
    }

    public String getPropertyName() {
        return this.j;
    }

    public ObjectAnimator() {
    }

    private ObjectAnimator(Object obj, String str) {
        this.i = obj;
        setPropertyName(str);
    }

    private <T> ObjectAnimator(T t, Property<T, ?> property) {
        this.i = t;
        setProperty(property);
    }

    public static ObjectAnimator ofInt(Object obj, String str, int... iArr) {
        ObjectAnimator objectAnimator = new ObjectAnimator(obj, str);
        objectAnimator.setIntValues(iArr);
        return objectAnimator;
    }

    public static <T> ObjectAnimator ofInt(T t, Property<T, Integer> property, int... iArr) {
        ObjectAnimator objectAnimator = new ObjectAnimator(t, property);
        objectAnimator.setIntValues(iArr);
        return objectAnimator;
    }

    public static ObjectAnimator ofFloat(Object obj, String str, float... fArr) {
        ObjectAnimator objectAnimator = new ObjectAnimator(obj, str);
        objectAnimator.setFloatValues(fArr);
        return objectAnimator;
    }

    public static <T> ObjectAnimator ofFloat(T t, Property<T, Float> property, float... fArr) {
        ObjectAnimator objectAnimator = new ObjectAnimator(t, property);
        objectAnimator.setFloatValues(fArr);
        return objectAnimator;
    }

    public static ObjectAnimator ofObject(Object obj, String str, TypeEvaluator typeEvaluator, Object... objArr) {
        ObjectAnimator objectAnimator = new ObjectAnimator(obj, str);
        objectAnimator.setObjectValues(objArr);
        objectAnimator.setEvaluator(typeEvaluator);
        return objectAnimator;
    }

    public static <T, V> ObjectAnimator ofObject(T t, Property<T, V> property, TypeEvaluator<V> typeEvaluator, V... vArr) {
        ObjectAnimator objectAnimator = new ObjectAnimator(t, property);
        objectAnimator.setObjectValues(vArr);
        objectAnimator.setEvaluator(typeEvaluator);
        return objectAnimator;
    }

    public static ObjectAnimator ofPropertyValuesHolder(Object obj, PropertyValuesHolder... propertyValuesHolderArr) {
        ObjectAnimator objectAnimator = new ObjectAnimator();
        objectAnimator.i = obj;
        objectAnimator.setValues(propertyValuesHolderArr);
        return objectAnimator;
    }

    public void setIntValues(int... iArr) {
        if (this.f != null && this.f.length != 0) {
            super.setIntValues(iArr);
        } else if (this.k != null) {
            setValues(PropertyValuesHolder.ofInt(this.k, iArr));
        } else {
            setValues(PropertyValuesHolder.ofInt(this.j, iArr));
        }
    }

    public void setFloatValues(float... fArr) {
        if (this.f != null && this.f.length != 0) {
            super.setFloatValues(fArr);
        } else if (this.k != null) {
            setValues(PropertyValuesHolder.ofFloat(this.k, fArr));
        } else {
            setValues(PropertyValuesHolder.ofFloat(this.j, fArr));
        }
    }

    public void setObjectValues(Object... objArr) {
        if (this.f != null && this.f.length != 0) {
            super.setObjectValues(objArr);
        } else if (this.k != null) {
            setValues(PropertyValuesHolder.ofObject(this.k, null, (V[]) objArr));
        } else {
            setValues(PropertyValuesHolder.ofObject(this.j, (TypeEvaluator) null, objArr));
        }
    }

    public void start() {
        super.start();
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        if (!this.e) {
            if (this.k == null && AnimatorProxy.NEEDS_PROXY && (this.i instanceof View) && h.containsKey(this.j)) {
                setProperty((Property) h.get(this.j));
            }
            for (PropertyValuesHolder a : this.f) {
                a.a(this.i);
            }
            super.a();
        }
    }

    public ObjectAnimator setDuration(long j2) {
        super.setDuration(j2);
        return this;
    }

    public Object getTarget() {
        return this.i;
    }

    public void setTarget(Object obj) {
        if (this.i != obj) {
            Object obj2 = this.i;
            this.i = obj;
            if (obj2 == null || obj == null || obj2.getClass() != obj.getClass()) {
                this.e = false;
            }
        }
    }

    public void setupStartValues() {
        a();
        for (PropertyValuesHolder b : this.f) {
            b.b(this.i);
        }
    }

    public void setupEndValues() {
        a();
        for (PropertyValuesHolder c : this.f) {
            c.c(this.i);
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(float f) {
        super.a(f);
        for (PropertyValuesHolder d : this.f) {
            d.d(this.i);
        }
    }

    public ObjectAnimator clone() {
        return (ObjectAnimator) super.clone();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ObjectAnimator@");
        sb.append(Integer.toHexString(hashCode()));
        sb.append(", target ");
        sb.append(this.i);
        String sb2 = sb.toString();
        if (this.f != null) {
            for (PropertyValuesHolder propertyValuesHolder : this.f) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(sb2);
                sb3.append("\n    ");
                sb3.append(propertyValuesHolder.toString());
                sb2 = sb3.toString();
            }
        }
        return sb2;
    }
}
