package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.xmlpull.v1.XmlPullParser;

public class ChangeTransform extends Transition {
    private static final String[] g = {"android:changeTransform:matrix", "android:changeTransform:transforms", "android:changeTransform:parentMatrix"};
    private static final Property<PathAnimatorMatrix, float[]> h = new Property<PathAnimatorMatrix, float[]>(float[].class, "nonTranslations") {
        /* renamed from: a */
        public float[] get(PathAnimatorMatrix pathAnimatorMatrix) {
            return null;
        }

        /* renamed from: a */
        public void set(PathAnimatorMatrix pathAnimatorMatrix, float[] fArr) {
            pathAnimatorMatrix.a(fArr);
        }
    };
    private static final Property<PathAnimatorMatrix, PointF> i = new Property<PathAnimatorMatrix, PointF>(PointF.class, "translations") {
        /* renamed from: a */
        public PointF get(PathAnimatorMatrix pathAnimatorMatrix) {
            return null;
        }

        /* renamed from: a */
        public void set(PathAnimatorMatrix pathAnimatorMatrix, PointF pointF) {
            pathAnimatorMatrix.a(pointF);
        }
    };
    private static final boolean j;
    /* access modifiers changed from: private */
    public boolean k = true;
    private boolean l = true;
    private Matrix m = new Matrix();

    static class GhostListener extends TransitionListenerAdapter {
        private View a;
        private GhostViewImpl b;

        GhostListener(View view, GhostViewImpl ghostViewImpl) {
            this.a = view;
            this.b = ghostViewImpl;
        }

        public void onTransitionEnd(@NonNull Transition transition) {
            transition.removeListener(this);
            GhostViewUtils.a(this.a);
            this.a.setTag(R.id.transition_transform, null);
            this.a.setTag(R.id.parent_matrix, null);
        }

        public void onTransitionPause(@NonNull Transition transition) {
            this.b.setVisibility(4);
        }

        public void onTransitionResume(@NonNull Transition transition) {
            this.b.setVisibility(0);
        }
    }

    static class PathAnimatorMatrix {
        private final Matrix a = new Matrix();
        private final View b;
        private final float[] c;
        private float d;
        private float e;

        PathAnimatorMatrix(View view, float[] fArr) {
            this.b = view;
            this.c = (float[]) fArr.clone();
            this.d = this.c[2];
            this.e = this.c[5];
            b();
        }

        /* access modifiers changed from: 0000 */
        public void a(float[] fArr) {
            System.arraycopy(fArr, 0, this.c, 0, fArr.length);
            b();
        }

        /* access modifiers changed from: 0000 */
        public void a(PointF pointF) {
            this.d = pointF.x;
            this.e = pointF.y;
            b();
        }

        private void b() {
            this.c[2] = this.d;
            this.c[5] = this.e;
            this.a.setValues(this.c);
            ViewUtils.c(this.b, this.a);
        }

        /* access modifiers changed from: 0000 */
        public Matrix a() {
            return this.a;
        }
    }

    static class Transforms {
        final float a;
        final float b;
        final float c;
        final float d;
        final float e;
        final float f;
        final float g;
        final float h;

        Transforms(View view) {
            this.a = view.getTranslationX();
            this.b = view.getTranslationY();
            this.c = ViewCompat.getTranslationZ(view);
            this.d = view.getScaleX();
            this.e = view.getScaleY();
            this.f = view.getRotationX();
            this.g = view.getRotationY();
            this.h = view.getRotation();
        }

        public void a(View view) {
            ChangeTransform.b(view, this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h);
        }

        public boolean equals(Object obj) {
            boolean z = false;
            if (!(obj instanceof Transforms)) {
                return false;
            }
            Transforms transforms = (Transforms) obj;
            if (transforms.a == this.a && transforms.b == this.b && transforms.c == this.c && transforms.d == this.d && transforms.e == this.e && transforms.f == this.f && transforms.g == this.g && transforms.h == this.h) {
                z = true;
            }
            return z;
        }

        public int hashCode() {
            int i = 0;
            int floatToIntBits = (((((((((((((this.a != BitmapDescriptorFactory.HUE_RED ? Float.floatToIntBits(this.a) : 0) * 31) + (this.b != BitmapDescriptorFactory.HUE_RED ? Float.floatToIntBits(this.b) : 0)) * 31) + (this.c != BitmapDescriptorFactory.HUE_RED ? Float.floatToIntBits(this.c) : 0)) * 31) + (this.d != BitmapDescriptorFactory.HUE_RED ? Float.floatToIntBits(this.d) : 0)) * 31) + (this.e != BitmapDescriptorFactory.HUE_RED ? Float.floatToIntBits(this.e) : 0)) * 31) + (this.f != BitmapDescriptorFactory.HUE_RED ? Float.floatToIntBits(this.f) : 0)) * 31) + (this.g != BitmapDescriptorFactory.HUE_RED ? Float.floatToIntBits(this.g) : 0)) * 31;
            if (this.h != BitmapDescriptorFactory.HUE_RED) {
                i = Float.floatToIntBits(this.h);
            }
            return floatToIntBits + i;
        }
    }

    static {
        boolean z = false;
        if (VERSION.SDK_INT >= 21) {
            z = true;
        }
        j = z;
    }

    public ChangeTransform() {
    }

    public ChangeTransform(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Styleable.g);
        XmlPullParser xmlPullParser = (XmlPullParser) attributeSet;
        this.k = TypedArrayUtils.getNamedBoolean(obtainStyledAttributes, xmlPullParser, "reparentWithOverlay", 1, true);
        this.l = TypedArrayUtils.getNamedBoolean(obtainStyledAttributes, xmlPullParser, "reparent", 0, true);
        obtainStyledAttributes.recycle();
    }

    public boolean getReparentWithOverlay() {
        return this.k;
    }

    public void setReparentWithOverlay(boolean z) {
        this.k = z;
    }

    public boolean getReparent() {
        return this.l;
    }

    public void setReparent(boolean z) {
        this.l = z;
    }

    public String[] getTransitionProperties() {
        return g;
    }

    private void b(TransitionValues transitionValues) {
        View view = transitionValues.view;
        if (view.getVisibility() != 8) {
            transitionValues.values.put("android:changeTransform:parent", view.getParent());
            transitionValues.values.put("android:changeTransform:transforms", new Transforms(view));
            Matrix matrix = view.getMatrix();
            transitionValues.values.put("android:changeTransform:matrix", (matrix == null || matrix.isIdentity()) ? null : new Matrix(matrix));
            if (this.l) {
                Matrix matrix2 = new Matrix();
                ViewGroup viewGroup = (ViewGroup) view.getParent();
                ViewUtils.a((View) viewGroup, matrix2);
                matrix2.preTranslate((float) (-viewGroup.getScrollX()), (float) (-viewGroup.getScrollY()));
                transitionValues.values.put("android:changeTransform:parentMatrix", matrix2);
                transitionValues.values.put("android:changeTransform:intermediateMatrix", view.getTag(R.id.transition_transform));
                transitionValues.values.put("android:changeTransform:intermediateParentMatrix", view.getTag(R.id.parent_matrix));
            }
        }
    }

    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        b(transitionValues);
        if (!j) {
            ((ViewGroup) transitionValues.view.getParent()).startViewTransition(transitionValues.view);
        }
    }

    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        b(transitionValues);
    }

    public Animator createAnimator(@NonNull ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues == null || transitionValues2 == null || !transitionValues.values.containsKey("android:changeTransform:parent") || !transitionValues2.values.containsKey("android:changeTransform:parent")) {
            return null;
        }
        ViewGroup viewGroup2 = (ViewGroup) transitionValues.values.get("android:changeTransform:parent");
        boolean z = this.l && !a(viewGroup2, (ViewGroup) transitionValues2.values.get("android:changeTransform:parent"));
        Matrix matrix = (Matrix) transitionValues.values.get("android:changeTransform:intermediateMatrix");
        if (matrix != null) {
            transitionValues.values.put("android:changeTransform:matrix", matrix);
        }
        Matrix matrix2 = (Matrix) transitionValues.values.get("android:changeTransform:intermediateParentMatrix");
        if (matrix2 != null) {
            transitionValues.values.put("android:changeTransform:parentMatrix", matrix2);
        }
        if (z) {
            a(transitionValues, transitionValues2);
        }
        ObjectAnimator a = a(transitionValues, transitionValues2, z);
        if (z && a != null && this.k) {
            a(viewGroup, transitionValues, transitionValues2);
        } else if (!j) {
            viewGroup2.endViewTransition(transitionValues.view);
        }
        return a;
    }

    private ObjectAnimator a(TransitionValues transitionValues, TransitionValues transitionValues2, boolean z) {
        Matrix matrix = (Matrix) transitionValues.values.get("android:changeTransform:matrix");
        Matrix matrix2 = (Matrix) transitionValues2.values.get("android:changeTransform:matrix");
        if (matrix == null) {
            matrix = MatrixUtils.a;
        }
        if (matrix2 == null) {
            matrix2 = MatrixUtils.a;
        }
        final Matrix matrix3 = matrix2;
        if (matrix.equals(matrix3)) {
            return null;
        }
        final Transforms transforms = (Transforms) transitionValues2.values.get("android:changeTransform:transforms");
        final View view = transitionValues2.view;
        c(view);
        float[] fArr = new float[9];
        matrix.getValues(fArr);
        float[] fArr2 = new float[9];
        matrix3.getValues(fArr2);
        final PathAnimatorMatrix pathAnimatorMatrix = new PathAnimatorMatrix(view, fArr);
        ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(pathAnimatorMatrix, new PropertyValuesHolder[]{PropertyValuesHolder.ofObject(h, new FloatArrayEvaluator(new float[9]), new float[][]{fArr, fArr2}), PropertyValuesHolderUtils.a(i, getPathMotion().getPath(fArr[2], fArr[5], fArr2[2], fArr2[5]))});
        final boolean z2 = z;
        AnonymousClass3 r1 = new AnimatorListenerAdapter() {
            private boolean g;
            private Matrix h = new Matrix();

            public void onAnimationCancel(Animator animator) {
                this.g = true;
            }

            public void onAnimationEnd(Animator animator) {
                if (!this.g) {
                    if (!z2 || !ChangeTransform.this.k) {
                        view.setTag(R.id.transition_transform, null);
                        view.setTag(R.id.parent_matrix, null);
                    } else {
                        a(matrix3);
                    }
                }
                ViewUtils.c(view, null);
                transforms.a(view);
            }

            public void onAnimationPause(Animator animator) {
                a(pathAnimatorMatrix.a());
            }

            public void onAnimationResume(Animator animator) {
                ChangeTransform.c(view);
            }

            private void a(Matrix matrix) {
                this.h.set(matrix);
                view.setTag(R.id.transition_transform, this.h);
                transforms.a(view);
            }
        };
        ofPropertyValuesHolder.addListener(r1);
        AnimatorUtils.a(ofPropertyValuesHolder, r1);
        return ofPropertyValuesHolder;
    }

    private boolean a(ViewGroup viewGroup, ViewGroup viewGroup2) {
        if (b((View) viewGroup) && b((View) viewGroup2)) {
            TransitionValues a = a((View) viewGroup, true);
            if (a == null || viewGroup2 != a.view) {
                return false;
            }
        } else if (viewGroup != viewGroup2) {
            return false;
        }
        return true;
    }

    private void a(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        View view = transitionValues2.view;
        Matrix matrix = new Matrix((Matrix) transitionValues2.values.get("android:changeTransform:parentMatrix"));
        ViewUtils.b(viewGroup, matrix);
        GhostViewImpl a = GhostViewUtils.a(view, viewGroup, matrix);
        if (a != null) {
            a.a((ViewGroup) transitionValues.values.get("android:changeTransform:parent"), transitionValues.view);
            Transition transition = this;
            while (transition.d != null) {
                transition = transition.d;
            }
            transition.addListener(new GhostListener(view, a));
            if (j) {
                if (transitionValues.view != transitionValues2.view) {
                    ViewUtils.a(transitionValues.view, (float) BitmapDescriptorFactory.HUE_RED);
                }
                ViewUtils.a(view, 1.0f);
            }
        }
    }

    private void a(TransitionValues transitionValues, TransitionValues transitionValues2) {
        Matrix matrix = (Matrix) transitionValues2.values.get("android:changeTransform:parentMatrix");
        transitionValues2.view.setTag(R.id.parent_matrix, matrix);
        Matrix matrix2 = this.m;
        matrix2.reset();
        matrix.invert(matrix2);
        Matrix matrix3 = (Matrix) transitionValues.values.get("android:changeTransform:matrix");
        if (matrix3 == null) {
            matrix3 = new Matrix();
            transitionValues.values.put("android:changeTransform:matrix", matrix3);
        }
        matrix3.postConcat((Matrix) transitionValues.values.get("android:changeTransform:parentMatrix"));
        matrix3.postConcat(matrix2);
    }

    /* access modifiers changed from: private */
    public static void c(View view) {
        b(view, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 1.0f, 1.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
    }

    /* access modifiers changed from: private */
    public static void b(View view, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        view.setTranslationX(f);
        view.setTranslationY(f2);
        ViewCompat.setTranslationZ(view, f3);
        view.setScaleX(f4);
        view.setScaleY(f5);
        view.setRotationX(f6);
        view.setRotationY(f7);
        view.setRotation(f8);
    }
}
