package com.nineoldandroids.view;

import android.support.v4.app.FrameMetricsAggregator;
import android.view.View;
import android.view.animation.Interpolator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;
import com.nineoldandroids.view.animation.AnimatorProxy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

class ViewPropertyAnimatorPreHC extends ViewPropertyAnimator {
    ArrayList<NameValuesHolder> a = new ArrayList<>();
    private final AnimatorProxy b;
    /* access modifiers changed from: private */
    public final WeakReference<View> c;
    private long d;
    private boolean e = false;
    private long f = 0;
    private boolean g = false;
    private Interpolator h;
    private boolean i = false;
    /* access modifiers changed from: private */
    public AnimatorListener j = null;
    private AnimatorEventListener k = new AnimatorEventListener();
    private Runnable l = new Runnable() {
        public void run() {
            ViewPropertyAnimatorPreHC.this.a();
        }
    };
    /* access modifiers changed from: private */
    public HashMap<Animator, PropertyBundle> m = new HashMap<>();

    class AnimatorEventListener implements AnimatorListener, AnimatorUpdateListener {
        private AnimatorEventListener() {
        }

        public void onAnimationStart(Animator animator) {
            if (ViewPropertyAnimatorPreHC.this.j != null) {
                ViewPropertyAnimatorPreHC.this.j.onAnimationStart(animator);
            }
        }

        public void onAnimationCancel(Animator animator) {
            if (ViewPropertyAnimatorPreHC.this.j != null) {
                ViewPropertyAnimatorPreHC.this.j.onAnimationCancel(animator);
            }
        }

        public void onAnimationRepeat(Animator animator) {
            if (ViewPropertyAnimatorPreHC.this.j != null) {
                ViewPropertyAnimatorPreHC.this.j.onAnimationRepeat(animator);
            }
        }

        public void onAnimationEnd(Animator animator) {
            if (ViewPropertyAnimatorPreHC.this.j != null) {
                ViewPropertyAnimatorPreHC.this.j.onAnimationEnd(animator);
            }
            ViewPropertyAnimatorPreHC.this.m.remove(animator);
            if (ViewPropertyAnimatorPreHC.this.m.isEmpty()) {
                ViewPropertyAnimatorPreHC.this.j = null;
            }
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            float animatedFraction = valueAnimator.getAnimatedFraction();
            PropertyBundle propertyBundle = (PropertyBundle) ViewPropertyAnimatorPreHC.this.m.get(valueAnimator);
            if ((propertyBundle.a & FrameMetricsAggregator.EVERY_DURATION) != 0) {
                View view = (View) ViewPropertyAnimatorPreHC.this.c.get();
                if (view != null) {
                    view.invalidate();
                }
            }
            ArrayList<NameValuesHolder> arrayList = propertyBundle.b;
            if (arrayList != null) {
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    NameValuesHolder nameValuesHolder = (NameValuesHolder) arrayList.get(i);
                    ViewPropertyAnimatorPreHC.this.c(nameValuesHolder.a, nameValuesHolder.b + (nameValuesHolder.c * animatedFraction));
                }
            }
            View view2 = (View) ViewPropertyAnimatorPreHC.this.c.get();
            if (view2 != null) {
                view2.invalidate();
            }
        }
    }

    static class NameValuesHolder {
        int a;
        float b;
        float c;

        NameValuesHolder(int i, float f, float f2) {
            this.a = i;
            this.b = f;
            this.c = f2;
        }
    }

    static class PropertyBundle {
        int a;
        ArrayList<NameValuesHolder> b;

        PropertyBundle(int i, ArrayList<NameValuesHolder> arrayList) {
            this.a = i;
            this.b = arrayList;
        }

        /* access modifiers changed from: 0000 */
        public boolean a(int i) {
            if (!((this.a & i) == 0 || this.b == null)) {
                int size = this.b.size();
                for (int i2 = 0; i2 < size; i2++) {
                    if (((NameValuesHolder) this.b.get(i2)).a == i) {
                        this.b.remove(i2);
                        this.a = (i ^ -1) & this.a;
                        return true;
                    }
                }
            }
            return false;
        }
    }

    ViewPropertyAnimatorPreHC(View view) {
        this.c = new WeakReference<>(view);
        this.b = AnimatorProxy.wrap(view);
    }

    public ViewPropertyAnimator setDuration(long j2) {
        if (j2 < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Animators cannot have negative duration: ");
            sb.append(j2);
            throw new IllegalArgumentException(sb.toString());
        }
        this.e = true;
        this.d = j2;
        return this;
    }

    public long getDuration() {
        if (this.e) {
            return this.d;
        }
        return new ValueAnimator().getDuration();
    }

    public long getStartDelay() {
        if (this.g) {
            return this.f;
        }
        return 0;
    }

    public ViewPropertyAnimator setStartDelay(long j2) {
        if (j2 < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Animators cannot have negative duration: ");
            sb.append(j2);
            throw new IllegalArgumentException(sb.toString());
        }
        this.g = true;
        this.f = j2;
        return this;
    }

    public ViewPropertyAnimator setInterpolator(Interpolator interpolator) {
        this.i = true;
        this.h = interpolator;
        return this;
    }

    public ViewPropertyAnimator setListener(AnimatorListener animatorListener) {
        this.j = animatorListener;
        return this;
    }

    public void start() {
        a();
    }

    public void cancel() {
        if (this.m.size() > 0) {
            for (Animator cancel : ((HashMap) this.m.clone()).keySet()) {
                cancel.cancel();
            }
        }
        this.a.clear();
        View view = (View) this.c.get();
        if (view != null) {
            view.removeCallbacks(this.l);
        }
    }

    public ViewPropertyAnimator x(float f2) {
        a(128, f2);
        return this;
    }

    public ViewPropertyAnimator xBy(float f2) {
        b(128, f2);
        return this;
    }

    public ViewPropertyAnimator y(float f2) {
        a(256, f2);
        return this;
    }

    public ViewPropertyAnimator yBy(float f2) {
        b(256, f2);
        return this;
    }

    public ViewPropertyAnimator rotation(float f2) {
        a(16, f2);
        return this;
    }

    public ViewPropertyAnimator rotationBy(float f2) {
        b(16, f2);
        return this;
    }

    public ViewPropertyAnimator rotationX(float f2) {
        a(32, f2);
        return this;
    }

    public ViewPropertyAnimator rotationXBy(float f2) {
        b(32, f2);
        return this;
    }

    public ViewPropertyAnimator rotationY(float f2) {
        a(64, f2);
        return this;
    }

    public ViewPropertyAnimator rotationYBy(float f2) {
        b(64, f2);
        return this;
    }

    public ViewPropertyAnimator translationX(float f2) {
        a(1, f2);
        return this;
    }

    public ViewPropertyAnimator translationXBy(float f2) {
        b(1, f2);
        return this;
    }

    public ViewPropertyAnimator translationY(float f2) {
        a(2, f2);
        return this;
    }

    public ViewPropertyAnimator translationYBy(float f2) {
        b(2, f2);
        return this;
    }

    public ViewPropertyAnimator scaleX(float f2) {
        a(4, f2);
        return this;
    }

    public ViewPropertyAnimator scaleXBy(float f2) {
        b(4, f2);
        return this;
    }

    public ViewPropertyAnimator scaleY(float f2) {
        a(8, f2);
        return this;
    }

    public ViewPropertyAnimator scaleYBy(float f2) {
        b(8, f2);
        return this;
    }

    public ViewPropertyAnimator alpha(float f2) {
        a(512, f2);
        return this;
    }

    public ViewPropertyAnimator alphaBy(float f2) {
        b(512, f2);
        return this;
    }

    /* access modifiers changed from: private */
    public void a() {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f);
        ArrayList arrayList = (ArrayList) this.a.clone();
        this.a.clear();
        int i2 = 0;
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            i2 |= ((NameValuesHolder) arrayList.get(i3)).a;
        }
        this.m.put(ofFloat, new PropertyBundle(i2, arrayList));
        ofFloat.addUpdateListener(this.k);
        ofFloat.addListener(this.k);
        if (this.g) {
            ofFloat.setStartDelay(this.f);
        }
        if (this.e) {
            ofFloat.setDuration(this.d);
        }
        if (this.i) {
            ofFloat.setInterpolator(this.h);
        }
        ofFloat.start();
    }

    private void a(int i2, float f2) {
        float a2 = a(i2);
        a(i2, a2, f2 - a2);
    }

    private void b(int i2, float f2) {
        a(i2, a(i2), f2);
    }

    private void a(int i2, float f2, float f3) {
        if (this.m.size() > 0) {
            Animator animator = null;
            Iterator it = this.m.keySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Animator animator2 = (Animator) it.next();
                PropertyBundle propertyBundle = (PropertyBundle) this.m.get(animator2);
                if (propertyBundle.a(i2) && propertyBundle.a == 0) {
                    animator = animator2;
                    break;
                }
            }
            if (animator != null) {
                animator.cancel();
            }
        }
        this.a.add(new NameValuesHolder(i2, f2, f3));
        View view = (View) this.c.get();
        if (view != null) {
            view.removeCallbacks(this.l);
            view.post(this.l);
        }
    }

    /* access modifiers changed from: private */
    public void c(int i2, float f2) {
        if (i2 == 4) {
            this.b.setScaleX(f2);
        } else if (i2 == 8) {
            this.b.setScaleY(f2);
        } else if (i2 == 16) {
            this.b.setRotation(f2);
        } else if (i2 == 32) {
            this.b.setRotationX(f2);
        } else if (i2 == 64) {
            this.b.setRotationY(f2);
        } else if (i2 == 128) {
            this.b.setX(f2);
        } else if (i2 == 256) {
            this.b.setY(f2);
        } else if (i2 != 512) {
            switch (i2) {
                case 1:
                    this.b.setTranslationX(f2);
                    return;
                case 2:
                    this.b.setTranslationY(f2);
                    return;
                default:
                    return;
            }
        } else {
            this.b.setAlpha(f2);
        }
    }

    private float a(int i2) {
        if (i2 == 4) {
            return this.b.getScaleX();
        }
        if (i2 == 8) {
            return this.b.getScaleY();
        }
        if (i2 == 16) {
            return this.b.getRotation();
        }
        if (i2 == 32) {
            return this.b.getRotationX();
        }
        if (i2 == 64) {
            return this.b.getRotationY();
        }
        if (i2 == 128) {
            return this.b.getX();
        }
        if (i2 == 256) {
            return this.b.getY();
        }
        if (i2 == 512) {
            return this.b.getAlpha();
        }
        switch (i2) {
            case 1:
                return this.b.getTranslationX();
            case 2:
                return this.b.getTranslationY();
            default:
                return BitmapDescriptorFactory.HUE_RED;
        }
    }
}
