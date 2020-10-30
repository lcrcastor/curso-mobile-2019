package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.transition.Transition.TransitionListener;
import android.support.v4.content.res.TypedArrayUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class Visibility extends Transition {
    public static final int MODE_IN = 1;
    public static final int MODE_OUT = 2;
    private static final String[] g = {"android:visibility:visibility", "android:visibility:parent"};
    private int h = 3;

    static class DisappearListener extends AnimatorListenerAdapter implements AnimatorPauseListenerCompat, TransitionListener {
        boolean a = false;
        private final View b;
        private final int c;
        private final ViewGroup d;
        private final boolean e;
        private boolean f;

        public void onAnimationRepeat(Animator animator) {
        }

        public void onAnimationStart(Animator animator) {
        }

        public void onTransitionCancel(@NonNull Transition transition) {
        }

        public void onTransitionStart(@NonNull Transition transition) {
        }

        DisappearListener(View view, int i, boolean z) {
            this.b = view;
            this.c = i;
            this.d = (ViewGroup) view.getParent();
            this.e = z;
            a(true);
        }

        public void onAnimationPause(Animator animator) {
            if (!this.a) {
                ViewUtils.a(this.b, this.c);
            }
        }

        public void onAnimationResume(Animator animator) {
            if (!this.a) {
                ViewUtils.a(this.b, 0);
            }
        }

        public void onAnimationCancel(Animator animator) {
            this.a = true;
        }

        public void onAnimationEnd(Animator animator) {
            a();
        }

        public void onTransitionEnd(@NonNull Transition transition) {
            a();
            transition.removeListener(this);
        }

        public void onTransitionPause(@NonNull Transition transition) {
            a(false);
        }

        public void onTransitionResume(@NonNull Transition transition) {
            a(true);
        }

        private void a() {
            if (!this.a) {
                ViewUtils.a(this.b, this.c);
                if (this.d != null) {
                    this.d.invalidate();
                }
            }
            a(false);
        }

        private void a(boolean z) {
            if (this.e && this.f != z && this.d != null) {
                this.f = z;
                ViewGroupUtils.a(this.d, z);
            }
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    static class VisibilityInfo {
        boolean a;
        boolean b;
        int c;
        int d;
        ViewGroup e;
        ViewGroup f;

        private VisibilityInfo() {
        }
    }

    public Animator onAppear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        return null;
    }

    public Animator onDisappear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        return null;
    }

    public Visibility() {
    }

    public Visibility(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Styleable.e);
        int namedInt = TypedArrayUtils.getNamedInt(obtainStyledAttributes, (XmlResourceParser) attributeSet, "transitionVisibilityMode", 0, 0);
        obtainStyledAttributes.recycle();
        if (namedInt != 0) {
            setMode(namedInt);
        }
    }

    public void setMode(int i) {
        if ((i & -4) != 0) {
            throw new IllegalArgumentException("Only MODE_IN and MODE_OUT flags are allowed");
        }
        this.h = i;
    }

    public int getMode() {
        return this.h;
    }

    @Nullable
    public String[] getTransitionProperties() {
        return g;
    }

    private void b(TransitionValues transitionValues) {
        transitionValues.values.put("android:visibility:visibility", Integer.valueOf(transitionValues.view.getVisibility()));
        transitionValues.values.put("android:visibility:parent", transitionValues.view.getParent());
        int[] iArr = new int[2];
        transitionValues.view.getLocationOnScreen(iArr);
        transitionValues.values.put("android:visibility:screenLocation", iArr);
    }

    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        b(transitionValues);
    }

    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        b(transitionValues);
    }

    public boolean isVisible(TransitionValues transitionValues) {
        boolean z = false;
        if (transitionValues == null) {
            return false;
        }
        int intValue = ((Integer) transitionValues.values.get("android:visibility:visibility")).intValue();
        View view = (View) transitionValues.values.get("android:visibility:parent");
        if (intValue == 0 && view != null) {
            z = true;
        }
        return z;
    }

    private VisibilityInfo a(TransitionValues transitionValues, TransitionValues transitionValues2) {
        VisibilityInfo visibilityInfo = new VisibilityInfo();
        visibilityInfo.a = false;
        visibilityInfo.b = false;
        if (transitionValues == null || !transitionValues.values.containsKey("android:visibility:visibility")) {
            visibilityInfo.c = -1;
            visibilityInfo.e = null;
        } else {
            visibilityInfo.c = ((Integer) transitionValues.values.get("android:visibility:visibility")).intValue();
            visibilityInfo.e = (ViewGroup) transitionValues.values.get("android:visibility:parent");
        }
        if (transitionValues2 == null || !transitionValues2.values.containsKey("android:visibility:visibility")) {
            visibilityInfo.d = -1;
            visibilityInfo.f = null;
        } else {
            visibilityInfo.d = ((Integer) transitionValues2.values.get("android:visibility:visibility")).intValue();
            visibilityInfo.f = (ViewGroup) transitionValues2.values.get("android:visibility:parent");
        }
        if (transitionValues == null || transitionValues2 == null) {
            if (transitionValues == null && visibilityInfo.d == 0) {
                visibilityInfo.b = true;
                visibilityInfo.a = true;
            } else if (transitionValues2 == null && visibilityInfo.c == 0) {
                visibilityInfo.b = false;
                visibilityInfo.a = true;
            }
        } else if (visibilityInfo.c == visibilityInfo.d && visibilityInfo.e == visibilityInfo.f) {
            return visibilityInfo;
        } else {
            if (visibilityInfo.c != visibilityInfo.d) {
                if (visibilityInfo.c == 0) {
                    visibilityInfo.b = false;
                    visibilityInfo.a = true;
                } else if (visibilityInfo.d == 0) {
                    visibilityInfo.b = true;
                    visibilityInfo.a = true;
                }
            } else if (visibilityInfo.f == null) {
                visibilityInfo.b = false;
                visibilityInfo.a = true;
            } else if (visibilityInfo.e == null) {
                visibilityInfo.b = true;
                visibilityInfo.a = true;
            }
        }
        return visibilityInfo;
    }

    @Nullable
    public Animator createAnimator(@NonNull ViewGroup viewGroup, @Nullable TransitionValues transitionValues, @Nullable TransitionValues transitionValues2) {
        VisibilityInfo a = a(transitionValues, transitionValues2);
        if (!a.a || (a.e == null && a.f == null)) {
            return null;
        }
        if (a.b) {
            return onAppear(viewGroup, transitionValues, a.c, transitionValues2, a.d);
        }
        return onDisappear(viewGroup, transitionValues, a.c, transitionValues2, a.d);
    }

    public Animator onAppear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
        if ((this.h & 1) != 1 || transitionValues2 == null) {
            return null;
        }
        if (transitionValues == null) {
            View view = (View) transitionValues2.view.getParent();
            if (a(a(view, false), getTransitionValues(view, false)).a) {
                return null;
            }
        }
        return onAppear(viewGroup, transitionValues2.view, transitionValues, transitionValues2);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:35:0x006c, code lost:
        if (r6.e != false) goto L_0x0029;
     */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0076 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00bd  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00dd A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.animation.Animator onDisappear(android.view.ViewGroup r7, android.support.transition.TransitionValues r8, int r9, android.support.transition.TransitionValues r10, int r11) {
        /*
            r6 = this;
            int r9 = r6.h
            r0 = 2
            r9 = r9 & r0
            r1 = 0
            if (r9 == r0) goto L_0x0008
            return r1
        L_0x0008:
            if (r8 == 0) goto L_0x000d
            android.view.View r9 = r8.view
            goto L_0x000e
        L_0x000d:
            r9 = r1
        L_0x000e:
            if (r10 == 0) goto L_0x0013
            android.view.View r2 = r10.view
            goto L_0x0014
        L_0x0013:
            r2 = r1
        L_0x0014:
            r3 = 1
            if (r2 == 0) goto L_0x0026
            android.view.ViewParent r4 = r2.getParent()
            if (r4 != 0) goto L_0x001e
            goto L_0x0026
        L_0x001e:
            r4 = 4
            if (r11 != r4) goto L_0x0022
            goto L_0x0024
        L_0x0022:
            if (r9 != r2) goto L_0x0029
        L_0x0024:
            r9 = r1
            goto L_0x0073
        L_0x0026:
            if (r2 == 0) goto L_0x002b
            r9 = r2
        L_0x0029:
            r2 = r1
            goto L_0x0073
        L_0x002b:
            if (r9 == 0) goto L_0x0071
            android.view.ViewParent r2 = r9.getParent()
            if (r2 != 0) goto L_0x0034
            goto L_0x0029
        L_0x0034:
            android.view.ViewParent r2 = r9.getParent()
            boolean r2 = r2 instanceof android.view.View
            if (r2 == 0) goto L_0x0071
            android.view.ViewParent r2 = r9.getParent()
            android.view.View r2 = (android.view.View) r2
            android.support.transition.TransitionValues r4 = r6.getTransitionValues(r2, r3)
            android.support.transition.TransitionValues r5 = r6.a(r2, r3)
            android.support.transition.Visibility$VisibilityInfo r4 = r6.a(r4, r5)
            boolean r4 = r4.a
            if (r4 != 0) goto L_0x0057
            android.view.View r9 = android.support.transition.TransitionUtils.a(r7, r9, r2)
            goto L_0x0029
        L_0x0057:
            android.view.ViewParent r4 = r2.getParent()
            if (r4 != 0) goto L_0x006f
            int r2 = r2.getId()
            r4 = -1
            if (r2 == r4) goto L_0x006f
            android.view.View r2 = r7.findViewById(r2)
            if (r2 == 0) goto L_0x006f
            boolean r2 = r6.e
            if (r2 == 0) goto L_0x006f
            goto L_0x0029
        L_0x006f:
            r9 = r1
            goto L_0x0029
        L_0x0071:
            r9 = r1
            r2 = r9
        L_0x0073:
            r4 = 0
            if (r9 == 0) goto L_0x00bb
            if (r8 == 0) goto L_0x00bb
            java.util.Map<java.lang.String, java.lang.Object> r11 = r8.values
            java.lang.String r1 = "android:visibility:screenLocation"
            java.lang.Object r11 = r11.get(r1)
            int[] r11 = (int[]) r11
            r1 = r11[r4]
            r11 = r11[r3]
            int[] r0 = new int[r0]
            r7.getLocationOnScreen(r0)
            r2 = r0[r4]
            int r1 = r1 - r2
            int r2 = r9.getLeft()
            int r1 = r1 - r2
            r9.offsetLeftAndRight(r1)
            r0 = r0[r3]
            int r11 = r11 - r0
            int r0 = r9.getTop()
            int r11 = r11 - r0
            r9.offsetTopAndBottom(r11)
            android.support.transition.ViewGroupOverlayImpl r11 = android.support.transition.ViewGroupUtils.a(r7)
            r11.a(r9)
            android.animation.Animator r7 = r6.onDisappear(r7, r9, r8, r10)
            if (r7 != 0) goto L_0x00b2
            r11.b(r9)
            goto L_0x00ba
        L_0x00b2:
            android.support.transition.Visibility$1 r8 = new android.support.transition.Visibility$1
            r8.<init>(r11, r9)
            r7.addListener(r8)
        L_0x00ba:
            return r7
        L_0x00bb:
            if (r2 == 0) goto L_0x00dd
            int r9 = r2.getVisibility()
            android.support.transition.ViewUtils.a(r2, r4)
            android.animation.Animator r7 = r6.onDisappear(r7, r2, r8, r10)
            if (r7 == 0) goto L_0x00d9
            android.support.transition.Visibility$DisappearListener r8 = new android.support.transition.Visibility$DisappearListener
            r8.<init>(r2, r11, r3)
            r7.addListener(r8)
            android.support.transition.AnimatorUtils.a(r7, r8)
            r6.addListener(r8)
            goto L_0x00dc
        L_0x00d9:
            android.support.transition.ViewUtils.a(r2, r9)
        L_0x00dc:
            return r7
        L_0x00dd:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.transition.Visibility.onDisappear(android.view.ViewGroup, android.support.transition.TransitionValues, int, android.support.transition.TransitionValues, int):android.animation.Animator");
    }

    public boolean isTransitionRequired(TransitionValues transitionValues, TransitionValues transitionValues2) {
        boolean z = false;
        if (transitionValues == null && transitionValues2 == null) {
            return false;
        }
        if (transitionValues != null && transitionValues2 != null && transitionValues2.values.containsKey("android:visibility:visibility") != transitionValues.values.containsKey("android:visibility:visibility")) {
            return false;
        }
        VisibilityInfo a = a(transitionValues, transitionValues2);
        if (a.a && (a.c == 0 || a.d == 0)) {
            z = true;
        }
        return z;
    }
}
