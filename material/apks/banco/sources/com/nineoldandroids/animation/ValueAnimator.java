package com.nineoldandroids.animation;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AndroidRuntimeException;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ValueAnimator extends Animator {
    public static final int INFINITE = -1;
    public static final int RESTART = 1;
    public static final int REVERSE = 2;
    private static ThreadLocal<AnimationHandler> h = new ThreadLocal<>();
    /* access modifiers changed from: private */
    public static final ThreadLocal<ArrayList<ValueAnimator>> i = new ThreadLocal<ArrayList<ValueAnimator>>() {
        /* access modifiers changed from: protected */
        /* renamed from: a */
        public ArrayList<ValueAnimator> initialValue() {
            return new ArrayList<>();
        }
    };
    /* access modifiers changed from: private */
    public static final ThreadLocal<ArrayList<ValueAnimator>> j = new ThreadLocal<ArrayList<ValueAnimator>>() {
        /* access modifiers changed from: protected */
        /* renamed from: a */
        public ArrayList<ValueAnimator> initialValue() {
            return new ArrayList<>();
        }
    };
    /* access modifiers changed from: private */
    public static final ThreadLocal<ArrayList<ValueAnimator>> k = new ThreadLocal<ArrayList<ValueAnimator>>() {
        /* access modifiers changed from: protected */
        /* renamed from: a */
        public ArrayList<ValueAnimator> initialValue() {
            return new ArrayList<>();
        }
    };
    /* access modifiers changed from: private */
    public static final ThreadLocal<ArrayList<ValueAnimator>> l = new ThreadLocal<ArrayList<ValueAnimator>>() {
        /* access modifiers changed from: protected */
        /* renamed from: a */
        public ArrayList<ValueAnimator> initialValue() {
            return new ArrayList<>();
        }
    };
    /* access modifiers changed from: private */
    public static final ThreadLocal<ArrayList<ValueAnimator>> m = new ThreadLocal<ArrayList<ValueAnimator>>() {
        /* access modifiers changed from: protected */
        /* renamed from: a */
        public ArrayList<ValueAnimator> initialValue() {
            return new ArrayList<>();
        }
    };
    private static final Interpolator n = new AccelerateDecelerateInterpolator();
    private static final TypeEvaluator o = new IntEvaluator();
    private static final TypeEvaluator p = new FloatEvaluator();
    /* access modifiers changed from: private */
    public static long z = 10;
    private int A = 0;
    private int B = 1;
    private Interpolator C = n;
    private ArrayList<AnimatorUpdateListener> D = null;
    long b;
    long c = -1;
    int d = 0;
    boolean e = false;
    PropertyValuesHolder[] f;
    HashMap<String, PropertyValuesHolder> g;
    private boolean q = false;
    private int r = 0;
    private float s = BitmapDescriptorFactory.HUE_RED;
    private boolean t = false;
    private long u;
    /* access modifiers changed from: private */
    public boolean v = false;
    private boolean w = false;
    private long x = 300;
    /* access modifiers changed from: private */
    public long y = 0;

    static class AnimationHandler extends Handler {
        private AnimationHandler() {
        }

        public void handleMessage(Message message) {
            boolean z;
            ArrayList arrayList = (ArrayList) ValueAnimator.i.get();
            ArrayList arrayList2 = (ArrayList) ValueAnimator.k.get();
            switch (message.what) {
                case 0:
                    ArrayList arrayList3 = (ArrayList) ValueAnimator.j.get();
                    z = arrayList.size() <= 0 && arrayList2.size() <= 0;
                    while (arrayList3.size() > 0) {
                        ArrayList arrayList4 = (ArrayList) arrayList3.clone();
                        arrayList3.clear();
                        int size = arrayList4.size();
                        for (int i = 0; i < size; i++) {
                            ValueAnimator valueAnimator = (ValueAnimator) arrayList4.get(i);
                            if (valueAnimator.y == 0) {
                                valueAnimator.i();
                            } else {
                                arrayList2.add(valueAnimator);
                            }
                        }
                    }
                    break;
                case 1:
                    z = true;
                    break;
                default:
                    return;
            }
            long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
            ArrayList arrayList5 = (ArrayList) ValueAnimator.m.get();
            ArrayList arrayList6 = (ArrayList) ValueAnimator.l.get();
            int size2 = arrayList2.size();
            for (int i2 = 0; i2 < size2; i2++) {
                ValueAnimator valueAnimator2 = (ValueAnimator) arrayList2.get(i2);
                if (valueAnimator2.b(currentAnimationTimeMillis)) {
                    arrayList5.add(valueAnimator2);
                }
            }
            int size3 = arrayList5.size();
            if (size3 > 0) {
                for (int i3 = 0; i3 < size3; i3++) {
                    ValueAnimator valueAnimator3 = (ValueAnimator) arrayList5.get(i3);
                    valueAnimator3.i();
                    valueAnimator3.v = true;
                    arrayList2.remove(valueAnimator3);
                }
                arrayList5.clear();
            }
            int size4 = arrayList.size();
            int i4 = 0;
            while (i4 < size4) {
                ValueAnimator valueAnimator4 = (ValueAnimator) arrayList.get(i4);
                if (valueAnimator4.a(currentAnimationTimeMillis)) {
                    arrayList6.add(valueAnimator4);
                }
                if (arrayList.size() == size4) {
                    i4++;
                } else {
                    size4--;
                    arrayList6.remove(valueAnimator4);
                }
            }
            if (arrayList6.size() > 0) {
                for (int i5 = 0; i5 < arrayList6.size(); i5++) {
                    ((ValueAnimator) arrayList6.get(i5)).h();
                }
                arrayList6.clear();
            }
            if (!z) {
                return;
            }
            if (!arrayList.isEmpty() || !arrayList2.isEmpty()) {
                sendEmptyMessageDelayed(1, Math.max(0, ValueAnimator.z - (AnimationUtils.currentAnimationTimeMillis() - currentAnimationTimeMillis)));
            }
        }
    }

    public interface AnimatorUpdateListener {
        void onAnimationUpdate(ValueAnimator valueAnimator);
    }

    public static ValueAnimator ofInt(int... iArr) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setIntValues(iArr);
        return valueAnimator;
    }

    public static ValueAnimator ofFloat(float... fArr) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setFloatValues(fArr);
        return valueAnimator;
    }

    public static ValueAnimator ofPropertyValuesHolder(PropertyValuesHolder... propertyValuesHolderArr) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setValues(propertyValuesHolderArr);
        return valueAnimator;
    }

    public static ValueAnimator ofObject(TypeEvaluator typeEvaluator, Object... objArr) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setObjectValues(objArr);
        valueAnimator.setEvaluator(typeEvaluator);
        return valueAnimator;
    }

    public void setIntValues(int... iArr) {
        if (iArr != null && iArr.length != 0) {
            if (this.f == null || this.f.length == 0) {
                setValues(PropertyValuesHolder.ofInt("", iArr));
            } else {
                this.f[0].setIntValues(iArr);
            }
            this.e = false;
        }
    }

    public void setFloatValues(float... fArr) {
        if (fArr != null && fArr.length != 0) {
            if (this.f == null || this.f.length == 0) {
                setValues(PropertyValuesHolder.ofFloat("", fArr));
            } else {
                this.f[0].setFloatValues(fArr);
            }
            this.e = false;
        }
    }

    public void setObjectValues(Object... objArr) {
        if (objArr != null && objArr.length != 0) {
            if (this.f == null || this.f.length == 0) {
                setValues(PropertyValuesHolder.ofObject("", (TypeEvaluator) null, objArr));
            } else {
                this.f[0].setObjectValues(objArr);
            }
            this.e = false;
        }
    }

    public void setValues(PropertyValuesHolder... propertyValuesHolderArr) {
        this.f = propertyValuesHolderArr;
        this.g = new HashMap<>(r0);
        for (PropertyValuesHolder propertyValuesHolder : propertyValuesHolderArr) {
            this.g.put(propertyValuesHolder.getPropertyName(), propertyValuesHolder);
        }
        this.e = false;
    }

    public PropertyValuesHolder[] getValues() {
        return this.f;
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        if (!this.e) {
            for (PropertyValuesHolder a : this.f) {
                a.a();
            }
            this.e = true;
        }
    }

    public ValueAnimator setDuration(long j2) {
        if (j2 < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Animators cannot have negative duration: ");
            sb.append(j2);
            throw new IllegalArgumentException(sb.toString());
        }
        this.x = j2;
        return this;
    }

    public long getDuration() {
        return this.x;
    }

    public void setCurrentPlayTime(long j2) {
        a();
        long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
        if (this.d != 1) {
            this.c = j2;
            this.d = 2;
        }
        this.b = currentAnimationTimeMillis - j2;
        a(currentAnimationTimeMillis);
    }

    public long getCurrentPlayTime() {
        if (!this.e || this.d == 0) {
            return 0;
        }
        return AnimationUtils.currentAnimationTimeMillis() - this.b;
    }

    public long getStartDelay() {
        return this.y;
    }

    public void setStartDelay(long j2) {
        this.y = j2;
    }

    public static long getFrameDelay() {
        return z;
    }

    public static void setFrameDelay(long j2) {
        z = j2;
    }

    public Object getAnimatedValue() {
        if (this.f == null || this.f.length <= 0) {
            return null;
        }
        return this.f[0].b();
    }

    public Object getAnimatedValue(String str) {
        PropertyValuesHolder propertyValuesHolder = (PropertyValuesHolder) this.g.get(str);
        if (propertyValuesHolder != null) {
            return propertyValuesHolder.b();
        }
        return null;
    }

    public void setRepeatCount(int i2) {
        this.A = i2;
    }

    public int getRepeatCount() {
        return this.A;
    }

    public void setRepeatMode(int i2) {
        this.B = i2;
    }

    public int getRepeatMode() {
        return this.B;
    }

    public void addUpdateListener(AnimatorUpdateListener animatorUpdateListener) {
        if (this.D == null) {
            this.D = new ArrayList<>();
        }
        this.D.add(animatorUpdateListener);
    }

    public void removeAllUpdateListeners() {
        if (this.D != null) {
            this.D.clear();
            this.D = null;
        }
    }

    public void removeUpdateListener(AnimatorUpdateListener animatorUpdateListener) {
        if (this.D != null) {
            this.D.remove(animatorUpdateListener);
            if (this.D.size() == 0) {
                this.D = null;
            }
        }
    }

    public void setInterpolator(Interpolator interpolator) {
        if (interpolator != null) {
            this.C = interpolator;
        } else {
            this.C = new LinearInterpolator();
        }
    }

    public Interpolator getInterpolator() {
        return this.C;
    }

    public void setEvaluator(TypeEvaluator typeEvaluator) {
        if (typeEvaluator != null && this.f != null && this.f.length > 0) {
            this.f[0].setEvaluator(typeEvaluator);
        }
    }

    private void a(boolean z2) {
        if (Looper.myLooper() == null) {
            throw new AndroidRuntimeException("Animators may only be run on Looper threads");
        }
        this.q = z2;
        this.r = 0;
        this.d = 0;
        this.w = true;
        this.t = false;
        ((ArrayList) j.get()).add(this);
        if (this.y == 0) {
            setCurrentPlayTime(getCurrentPlayTime());
            this.d = 0;
            this.v = true;
            if (this.a != null) {
                ArrayList arrayList = (ArrayList) this.a.clone();
                int size = arrayList.size();
                for (int i2 = 0; i2 < size; i2++) {
                    ((AnimatorListener) arrayList.get(i2)).onAnimationStart(this);
                }
            }
        }
        AnimationHandler animationHandler = (AnimationHandler) h.get();
        if (animationHandler == null) {
            animationHandler = new AnimationHandler();
            h.set(animationHandler);
        }
        animationHandler.sendEmptyMessage(0);
    }

    public void start() {
        a(false);
    }

    public void cancel() {
        if (this.d != 0 || ((ArrayList) j.get()).contains(this) || ((ArrayList) k.get()).contains(this)) {
            if (this.v && this.a != null) {
                Iterator it = ((ArrayList) this.a.clone()).iterator();
                while (it.hasNext()) {
                    ((AnimatorListener) it.next()).onAnimationCancel(this);
                }
            }
            h();
        }
    }

    public void end() {
        if (!((ArrayList) i.get()).contains(this) && !((ArrayList) j.get()).contains(this)) {
            this.t = false;
            i();
        } else if (!this.e) {
            a();
        }
        if (this.A <= 0 || (this.A & 1) != 1) {
            a(1.0f);
        } else {
            a((float) BitmapDescriptorFactory.HUE_RED);
        }
        h();
    }

    public boolean isRunning() {
        return this.d == 1 || this.v;
    }

    public boolean isStarted() {
        return this.w;
    }

    public void reverse() {
        this.q = !this.q;
        if (this.d == 1) {
            long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
            this.b = currentAnimationTimeMillis - (this.x - (currentAnimationTimeMillis - this.b));
            return;
        }
        a(true);
    }

    /* access modifiers changed from: private */
    public void h() {
        ((ArrayList) i.get()).remove(this);
        ((ArrayList) j.get()).remove(this);
        ((ArrayList) k.get()).remove(this);
        this.d = 0;
        if (this.v && this.a != null) {
            ArrayList arrayList = (ArrayList) this.a.clone();
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                ((AnimatorListener) arrayList.get(i2)).onAnimationEnd(this);
            }
        }
        this.v = false;
        this.w = false;
    }

    /* access modifiers changed from: private */
    public void i() {
        a();
        ((ArrayList) i.get()).add(this);
        if (this.y > 0 && this.a != null) {
            ArrayList arrayList = (ArrayList) this.a.clone();
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                ((AnimatorListener) arrayList.get(i2)).onAnimationStart(this);
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean b(long j2) {
        if (!this.t) {
            this.t = true;
            this.u = j2;
        } else {
            long j3 = j2 - this.u;
            if (j3 > this.y) {
                this.b = j2 - (j3 - this.y);
                this.d = 1;
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public boolean a(long j2) {
        if (this.d == 0) {
            this.d = 1;
            if (this.c < 0) {
                this.b = j2;
            } else {
                this.b = j2 - this.c;
                this.c = -1;
            }
        }
        boolean z2 = false;
        switch (this.d) {
            case 1:
            case 2:
                float f2 = this.x > 0 ? ((float) (j2 - this.b)) / ((float) this.x) : 1.0f;
                if (f2 >= 1.0f) {
                    if (this.r < this.A || this.A == -1) {
                        if (this.a != null) {
                            int size = this.a.size();
                            for (int i2 = 0; i2 < size; i2++) {
                                ((AnimatorListener) this.a.get(i2)).onAnimationRepeat(this);
                            }
                        }
                        if (this.B == 2) {
                            this.q = !this.q;
                        }
                        this.r += (int) f2;
                        f2 %= 1.0f;
                        this.b += this.x;
                    } else {
                        f2 = Math.min(f2, 1.0f);
                        z2 = true;
                    }
                }
                if (this.q) {
                    f2 = 1.0f - f2;
                }
                a(f2);
                break;
        }
        return z2;
    }

    public float getAnimatedFraction() {
        return this.s;
    }

    /* access modifiers changed from: 0000 */
    public void a(float f2) {
        float interpolation = this.C.getInterpolation(f2);
        this.s = interpolation;
        for (PropertyValuesHolder a : this.f) {
            a.a(interpolation);
        }
        if (this.D != null) {
            int size = this.D.size();
            for (int i2 = 0; i2 < size; i2++) {
                ((AnimatorUpdateListener) this.D.get(i2)).onAnimationUpdate(this);
            }
        }
    }

    public ValueAnimator clone() {
        ValueAnimator valueAnimator = (ValueAnimator) super.clone();
        if (this.D != null) {
            ArrayList<AnimatorUpdateListener> arrayList = this.D;
            valueAnimator.D = new ArrayList<>();
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                valueAnimator.D.add(arrayList.get(i2));
            }
        }
        valueAnimator.c = -1;
        valueAnimator.q = false;
        valueAnimator.r = 0;
        valueAnimator.e = false;
        valueAnimator.d = 0;
        valueAnimator.t = false;
        PropertyValuesHolder[] propertyValuesHolderArr = this.f;
        if (propertyValuesHolderArr != null) {
            int length = propertyValuesHolderArr.length;
            valueAnimator.f = new PropertyValuesHolder[length];
            valueAnimator.g = new HashMap<>(length);
            for (int i3 = 0; i3 < length; i3++) {
                PropertyValuesHolder clone = propertyValuesHolderArr[i3].clone();
                valueAnimator.f[i3] = clone;
                valueAnimator.g.put(clone.getPropertyName(), clone);
            }
        }
        return valueAnimator;
    }

    public static int getCurrentAnimationsCount() {
        return ((ArrayList) i.get()).size();
    }

    public static void clearAllAnimations() {
        ((ArrayList) i.get()).clear();
        ((ArrayList) j.get()).clear();
        ((ArrayList) k.get()).clear();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ValueAnimator@");
        sb.append(Integer.toHexString(hashCode()));
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
