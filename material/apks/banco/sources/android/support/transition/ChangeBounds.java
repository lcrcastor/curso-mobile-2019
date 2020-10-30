package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class ChangeBounds extends Transition {
    private static final String[] g = {"android:changeBounds:bounds", "android:changeBounds:clip", "android:changeBounds:parent", "android:changeBounds:windowX", "android:changeBounds:windowY"};
    private static final Property<Drawable, PointF> h = new Property<Drawable, PointF>(PointF.class, "boundsOrigin") {
        private Rect a = new Rect();

        /* renamed from: a */
        public void set(Drawable drawable, PointF pointF) {
            drawable.copyBounds(this.a);
            this.a.offsetTo(Math.round(pointF.x), Math.round(pointF.y));
            drawable.setBounds(this.a);
        }

        /* renamed from: a */
        public PointF get(Drawable drawable) {
            drawable.copyBounds(this.a);
            return new PointF((float) this.a.left, (float) this.a.top);
        }
    };
    private static final Property<ViewBounds, PointF> i = new Property<ViewBounds, PointF>(PointF.class, "topLeft") {
        /* renamed from: a */
        public PointF get(ViewBounds viewBounds) {
            return null;
        }

        /* renamed from: a */
        public void set(ViewBounds viewBounds, PointF pointF) {
            viewBounds.a(pointF);
        }
    };
    private static final Property<ViewBounds, PointF> j = new Property<ViewBounds, PointF>(PointF.class, "bottomRight") {
        /* renamed from: a */
        public PointF get(ViewBounds viewBounds) {
            return null;
        }

        /* renamed from: a */
        public void set(ViewBounds viewBounds, PointF pointF) {
            viewBounds.b(pointF);
        }
    };
    private static final Property<View, PointF> k = new Property<View, PointF>(PointF.class, "bottomRight") {
        /* renamed from: a */
        public PointF get(View view) {
            return null;
        }

        /* renamed from: a */
        public void set(View view, PointF pointF) {
            ViewUtils.a(view, view.getLeft(), view.getTop(), Math.round(pointF.x), Math.round(pointF.y));
        }
    };
    private static final Property<View, PointF> l = new Property<View, PointF>(PointF.class, "topLeft") {
        /* renamed from: a */
        public PointF get(View view) {
            return null;
        }

        /* renamed from: a */
        public void set(View view, PointF pointF) {
            ViewUtils.a(view, Math.round(pointF.x), Math.round(pointF.y), view.getRight(), view.getBottom());
        }
    };
    private static final Property<View, PointF> m = new Property<View, PointF>(PointF.class, "position") {
        /* renamed from: a */
        public PointF get(View view) {
            return null;
        }

        /* renamed from: a */
        public void set(View view, PointF pointF) {
            int round = Math.round(pointF.x);
            int round2 = Math.round(pointF.y);
            ViewUtils.a(view, round, round2, view.getWidth() + round, view.getHeight() + round2);
        }
    };
    private static RectEvaluator q = new RectEvaluator();
    private int[] n = new int[2];
    private boolean o = false;
    private boolean p = false;

    static class ViewBounds {
        private int a;
        private int b;
        private int c;
        private int d;
        private View e;
        private int f;
        private int g;

        ViewBounds(View view) {
            this.e = view;
        }

        /* access modifiers changed from: 0000 */
        public void a(PointF pointF) {
            this.a = Math.round(pointF.x);
            this.b = Math.round(pointF.y);
            this.f++;
            if (this.f == this.g) {
                a();
            }
        }

        /* access modifiers changed from: 0000 */
        public void b(PointF pointF) {
            this.c = Math.round(pointF.x);
            this.d = Math.round(pointF.y);
            this.g++;
            if (this.f == this.g) {
                a();
            }
        }

        private void a() {
            ViewUtils.a(this.e, this.a, this.b, this.c, this.d);
            this.f = 0;
            this.g = 0;
        }
    }

    public ChangeBounds() {
    }

    public ChangeBounds(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Styleable.d);
        boolean namedBoolean = TypedArrayUtils.getNamedBoolean(obtainStyledAttributes, (XmlResourceParser) attributeSet, "resizeClip", 0, false);
        obtainStyledAttributes.recycle();
        setResizeClip(namedBoolean);
    }

    @Nullable
    public String[] getTransitionProperties() {
        return g;
    }

    public void setResizeClip(boolean z) {
        this.o = z;
    }

    public boolean getResizeClip() {
        return this.o;
    }

    private void b(TransitionValues transitionValues) {
        View view = transitionValues.view;
        if (ViewCompat.isLaidOut(view) || view.getWidth() != 0 || view.getHeight() != 0) {
            transitionValues.values.put("android:changeBounds:bounds", new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
            transitionValues.values.put("android:changeBounds:parent", transitionValues.view.getParent());
            if (this.p) {
                transitionValues.view.getLocationInWindow(this.n);
                transitionValues.values.put("android:changeBounds:windowX", Integer.valueOf(this.n[0]));
                transitionValues.values.put("android:changeBounds:windowY", Integer.valueOf(this.n[1]));
            }
            if (this.o) {
                transitionValues.values.put("android:changeBounds:clip", ViewCompat.getClipBounds(view));
            }
        }
    }

    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        b(transitionValues);
    }

    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        b(transitionValues);
    }

    private boolean a(View view, View view2) {
        if (!this.p) {
            return true;
        }
        TransitionValues a = a(view, true);
        if (a == null) {
            if (view == view2) {
                return true;
            }
        } else if (view2 == a.view) {
            return true;
        }
        return false;
    }

    @Nullable
    public Animator createAnimator(@NonNull ViewGroup viewGroup, @Nullable TransitionValues transitionValues, @Nullable TransitionValues transitionValues2) {
        int i2;
        View view;
        Animator animator;
        Animator animator2;
        int i3;
        Rect rect;
        ObjectAnimator objectAnimator;
        TransitionValues transitionValues3 = transitionValues;
        TransitionValues transitionValues4 = transitionValues2;
        if (transitionValues3 == null || transitionValues4 == null) {
            return null;
        }
        ViewGroup viewGroup2 = (ViewGroup) transitionValues3.values.get("android:changeBounds:parent");
        ViewGroup viewGroup3 = (ViewGroup) transitionValues4.values.get("android:changeBounds:parent");
        if (viewGroup2 == null || viewGroup3 == null) {
            return null;
        }
        View view2 = transitionValues4.view;
        if (a(viewGroup2, viewGroup3)) {
            Rect rect2 = (Rect) transitionValues3.values.get("android:changeBounds:bounds");
            Rect rect3 = (Rect) transitionValues4.values.get("android:changeBounds:bounds");
            int i4 = rect2.left;
            int i5 = rect3.left;
            int i6 = rect2.top;
            int i7 = rect3.top;
            int i8 = rect2.right;
            int i9 = rect3.right;
            int i10 = rect2.bottom;
            int i11 = rect3.bottom;
            int i12 = i8 - i4;
            int i13 = i10 - i6;
            int i14 = i9 - i5;
            int i15 = i11 - i7;
            View view3 = view2;
            Rect rect4 = (Rect) transitionValues3.values.get("android:changeBounds:clip");
            Rect rect5 = (Rect) transitionValues4.values.get("android:changeBounds:clip");
            if ((i12 == 0 || i13 == 0) && (i14 == 0 || i15 == 0)) {
                i2 = 0;
            } else {
                i2 = (i4 == i5 && i6 == i7) ? 0 : 1;
                if (!(i8 == i9 && i10 == i11)) {
                    i2++;
                }
            }
            if ((rect4 != null && !rect4.equals(rect5)) || (rect4 == null && rect5 != null)) {
                i2++;
            }
            if (i2 > 0) {
                Rect rect6 = rect5;
                Rect rect7 = rect4;
                if (!this.o) {
                    view = view3;
                    ViewUtils.a(view, i4, i6, i8, i10);
                    if (i2 == 2) {
                        if (i12 == i14 && i13 == i15) {
                            animator = ObjectAnimatorUtils.a(view, m, getPathMotion().getPath((float) i4, (float) i6, (float) i5, (float) i7));
                        } else {
                            final ViewBounds viewBounds = new ViewBounds(view);
                            ObjectAnimator a = ObjectAnimatorUtils.a(viewBounds, i, getPathMotion().getPath((float) i4, (float) i6, (float) i5, (float) i7));
                            ObjectAnimator a2 = ObjectAnimatorUtils.a(viewBounds, j, getPathMotion().getPath((float) i8, (float) i10, (float) i9, (float) i11));
                            AnimatorSet animatorSet = new AnimatorSet();
                            animatorSet.playTogether(new Animator[]{a, a2});
                            animatorSet.addListener(new AnimatorListenerAdapter() {
                                private ViewBounds mViewBounds = viewBounds;
                            });
                            animator = animatorSet;
                        }
                    } else if (i4 == i5 && i6 == i7) {
                        animator = ObjectAnimatorUtils.a(view, k, getPathMotion().getPath((float) i8, (float) i10, (float) i9, (float) i11));
                    } else {
                        animator = ObjectAnimatorUtils.a(view, l, getPathMotion().getPath((float) i4, (float) i6, (float) i5, (float) i7));
                    }
                } else {
                    view = view3;
                    ViewUtils.a(view, i4, i6, Math.max(i12, i14) + i4, Math.max(i13, i15) + i6);
                    if (i4 == i5 && i6 == i7) {
                        animator2 = null;
                    } else {
                        animator2 = ObjectAnimatorUtils.a(view, m, getPathMotion().getPath((float) i4, (float) i6, (float) i5, (float) i7));
                    }
                    if (rect7 == null) {
                        i3 = 0;
                        rect = new Rect(0, 0, i12, i13);
                    } else {
                        i3 = 0;
                        rect = rect7;
                    }
                    Rect rect8 = rect6 == null ? new Rect(i3, i3, i14, i15) : rect6;
                    if (!rect.equals(rect8)) {
                        ViewCompat.setClipBounds(view, rect);
                        RectEvaluator rectEvaluator = q;
                        Object[] objArr = new Object[2];
                        objArr[i3] = rect;
                        objArr[1] = rect8;
                        objectAnimator = ObjectAnimator.ofObject(view, "clipBounds", rectEvaluator, objArr);
                        final View view4 = view;
                        final Rect rect9 = rect6;
                        final int i16 = i5;
                        final int i17 = i7;
                        final int i18 = i9;
                        final int i19 = i11;
                        AnonymousClass8 r0 = new AnimatorListenerAdapter() {
                            private boolean h;

                            public void onAnimationCancel(Animator animator) {
                                this.h = true;
                            }

                            public void onAnimationEnd(Animator animator) {
                                if (!this.h) {
                                    ViewCompat.setClipBounds(view4, rect9);
                                    ViewUtils.a(view4, i16, i17, i18, i19);
                                }
                            }
                        };
                        objectAnimator.addListener(r0);
                    } else {
                        objectAnimator = null;
                    }
                    animator = TransitionUtils.a(animator2, objectAnimator);
                }
                if (view.getParent() instanceof ViewGroup) {
                    final ViewGroup viewGroup4 = (ViewGroup) view.getParent();
                    ViewGroupUtils.a(viewGroup4, true);
                    addListener(new TransitionListenerAdapter() {
                        boolean a = false;

                        public void onTransitionCancel(@NonNull Transition transition) {
                            ViewGroupUtils.a(viewGroup4, false);
                            this.a = true;
                        }

                        public void onTransitionEnd(@NonNull Transition transition) {
                            if (!this.a) {
                                ViewGroupUtils.a(viewGroup4, false);
                            }
                            transition.removeListener(this);
                        }

                        public void onTransitionPause(@NonNull Transition transition) {
                            ViewGroupUtils.a(viewGroup4, false);
                        }

                        public void onTransitionResume(@NonNull Transition transition) {
                            ViewGroupUtils.a(viewGroup4, true);
                        }
                    });
                }
                return animator;
            }
        } else {
            int intValue = ((Integer) transitionValues3.values.get("android:changeBounds:windowX")).intValue();
            int intValue2 = ((Integer) transitionValues3.values.get("android:changeBounds:windowY")).intValue();
            int intValue3 = ((Integer) transitionValues4.values.get("android:changeBounds:windowX")).intValue();
            int intValue4 = ((Integer) transitionValues4.values.get("android:changeBounds:windowY")).intValue();
            if (!(intValue == intValue3 && intValue2 == intValue4)) {
                ViewGroup viewGroup5 = viewGroup;
                viewGroup5.getLocationInWindow(this.n);
                Bitmap createBitmap = Bitmap.createBitmap(view2.getWidth(), view2.getHeight(), Config.ARGB_8888);
                view2.draw(new Canvas(createBitmap));
                BitmapDrawable bitmapDrawable = new BitmapDrawable(createBitmap);
                float c = ViewUtils.c(view2);
                ViewUtils.a(view2, (float) BitmapDescriptorFactory.HUE_RED);
                ViewUtils.a(viewGroup).a(bitmapDrawable);
                ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(bitmapDrawable, new PropertyValuesHolder[]{PropertyValuesHolderUtils.a(h, getPathMotion().getPath((float) (intValue - this.n[0]), (float) (intValue2 - this.n[1]), (float) (intValue3 - this.n[0]), (float) (intValue4 - this.n[1])))});
                final ViewGroup viewGroup6 = viewGroup5;
                final BitmapDrawable bitmapDrawable2 = bitmapDrawable;
                final View view5 = view2;
                final float f = c;
                AnonymousClass10 r02 = new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animator) {
                        ViewUtils.a(viewGroup6).b(bitmapDrawable2);
                        ViewUtils.a(view5, f);
                    }
                };
                ofPropertyValuesHolder.addListener(r02);
                return ofPropertyValuesHolder;
            }
        }
        return null;
    }
}
