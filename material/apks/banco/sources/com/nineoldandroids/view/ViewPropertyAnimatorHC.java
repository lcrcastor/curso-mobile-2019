package com.nineoldandroids.view;

import android.support.v4.app.FrameMetricsAggregator;
import android.view.View;
import android.view.animation.Interpolator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

class ViewPropertyAnimatorHC extends ViewPropertyAnimator {
    ArrayList<NameValuesHolder> a = new ArrayList<>();
    /* access modifiers changed from: private */
    public final WeakReference<View> b;
    private long c;
    private boolean d = false;
    private long e = 0;
    private boolean f = false;
    private Interpolator g;
    private boolean h = false;
    /* access modifiers changed from: private */
    public AnimatorListener i = null;
    private AnimatorEventListener j = new AnimatorEventListener();
    private Runnable k = new Runnable() {
        public void run() {
            ViewPropertyAnimatorHC.this.a();
        }
    };
    /* access modifiers changed from: private */
    public HashMap<Animator, PropertyBundle> l = new HashMap<>();

    class AnimatorEventListener implements AnimatorListener, AnimatorUpdateListener {
        private AnimatorEventListener() {
        }

        public void onAnimationStart(Animator animator) {
            if (ViewPropertyAnimatorHC.this.i != null) {
                ViewPropertyAnimatorHC.this.i.onAnimationStart(animator);
            }
        }

        public void onAnimationCancel(Animator animator) {
            if (ViewPropertyAnimatorHC.this.i != null) {
                ViewPropertyAnimatorHC.this.i.onAnimationCancel(animator);
            }
        }

        public void onAnimationRepeat(Animator animator) {
            if (ViewPropertyAnimatorHC.this.i != null) {
                ViewPropertyAnimatorHC.this.i.onAnimationRepeat(animator);
            }
        }

        public void onAnimationEnd(Animator animator) {
            if (ViewPropertyAnimatorHC.this.i != null) {
                ViewPropertyAnimatorHC.this.i.onAnimationEnd(animator);
            }
            ViewPropertyAnimatorHC.this.l.remove(animator);
            if (ViewPropertyAnimatorHC.this.l.isEmpty()) {
                ViewPropertyAnimatorHC.this.i = null;
            }
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            float animatedFraction = valueAnimator.getAnimatedFraction();
            PropertyBundle propertyBundle = (PropertyBundle) ViewPropertyAnimatorHC.this.l.get(valueAnimator);
            if ((propertyBundle.a & FrameMetricsAggregator.EVERY_DURATION) != 0) {
                View view = (View) ViewPropertyAnimatorHC.this.b.get();
                if (view != null) {
                    view.invalidate();
                }
            }
            ArrayList<NameValuesHolder> arrayList = propertyBundle.b;
            if (arrayList != null) {
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    NameValuesHolder nameValuesHolder = (NameValuesHolder) arrayList.get(i);
                    ViewPropertyAnimatorHC.this.c(nameValuesHolder.a, nameValuesHolder.b + (nameValuesHolder.c * animatedFraction));
                }
            }
            View view2 = (View) ViewPropertyAnimatorHC.this.b.get();
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

    ViewPropertyAnimatorHC(View view) {
        this.b = new WeakReference<>(view);
    }

    public ViewPropertyAnimator setDuration(long j2) {
        if (j2 < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Animators cannot have negative duration: ");
            sb.append(j2);
            throw new IllegalArgumentException(sb.toString());
        }
        this.d = true;
        this.c = j2;
        return this;
    }

    public long getDuration() {
        if (this.d) {
            return this.c;
        }
        return new ValueAnimator().getDuration();
    }

    public long getStartDelay() {
        if (this.f) {
            return this.e;
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
        this.f = true;
        this.e = j2;
        return this;
    }

    public ViewPropertyAnimator setInterpolator(Interpolator interpolator) {
        this.h = true;
        this.g = interpolator;
        return this;
    }

    public ViewPropertyAnimator setListener(AnimatorListener animatorListener) {
        this.i = animatorListener;
        return this;
    }

    public void start() {
        a();
    }

    public void cancel() {
        if (this.l.size() > 0) {
            for (Animator cancel : ((HashMap) this.l.clone()).keySet()) {
                cancel.cancel();
            }
        }
        this.a.clear();
        View view = (View) this.b.get();
        if (view != null) {
            view.removeCallbacks(this.k);
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
        this.l.put(ofFloat, new PropertyBundle(i2, arrayList));
        ofFloat.addUpdateListener(this.j);
        ofFloat.addListener(this.j);
        if (this.f) {
            ofFloat.setStartDelay(this.e);
        }
        if (this.d) {
            ofFloat.setDuration(this.c);
        }
        if (this.h) {
            ofFloat.setInterpolator(this.g);
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
        if (this.l.size() > 0) {
            Animator animator = null;
            Iterator it = this.l.keySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Animator animator2 = (Animator) it.next();
                PropertyBundle propertyBundle = (PropertyBundle) this.l.get(animator2);
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
        View view = (View) this.b.get();
        if (view != null) {
            view.removeCallbacks(this.k);
            view.post(this.k);
        }
    }

    /* access modifiers changed from: private */
    public void c(int i2, float f2) {
        View view = (View) this.b.get();
        if (view == null) {
            return;
        }
        if (i2 == 4) {
            view.setScaleX(f2);
        } else if (i2 == 8) {
            view.setScaleY(f2);
        } else if (i2 == 16) {
            view.setRotation(f2);
        } else if (i2 == 32) {
            view.setRotationX(f2);
        } else if (i2 == 64) {
            view.setRotationY(f2);
        } else if (i2 == 128) {
            view.setX(f2);
        } else if (i2 == 256) {
            view.setY(f2);
        } else if (i2 != 512) {
            switch (i2) {
                case 1:
                    view.setTranslationX(f2);
                    return;
                case 2:
                    view.setTranslationY(f2);
                    return;
                default:
                    return;
            }
        } else {
            view.setAlpha(f2);
        }
    }

    private float a(int i2) {
        View view = (View) this.b.get();
        if (view != null) {
            if (i2 == 4) {
                return view.getScaleX();
            }
            if (i2 == 8) {
                return view.getScaleY();
            }
            if (i2 == 16) {
                return view.getRotation();
            }
            if (i2 == 32) {
                return view.getRotationX();
            }
            if (i2 == 64) {
                return view.getRotationY();
            }
            if (i2 == 128) {
                return view.getX();
            }
            if (i2 == 256) {
                return view.getY();
            }
            if (i2 == 512) {
                return view.getAlpha();
            }
            switch (i2) {
                case 1:
                    return view.getTranslationX();
                case 2:
                    return view.getTranslationY();
            }
        }
        return BitmapDescriptorFactory.HUE_RED;
    }
}
