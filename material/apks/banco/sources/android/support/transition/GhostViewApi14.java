package android.support.transition;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.FrameLayout;

@SuppressLint({"ViewConstructor"})
@RequiresApi(14)
class GhostViewApi14 extends View implements GhostViewImpl {
    final View a;
    ViewGroup b;
    View c;
    int d;
    Matrix e;
    private int f;
    private int g;
    private final Matrix h = new Matrix();
    private final OnPreDrawListener i = new OnPreDrawListener() {
        public boolean onPreDraw() {
            GhostViewApi14.this.e = GhostViewApi14.this.a.getMatrix();
            ViewCompat.postInvalidateOnAnimation(GhostViewApi14.this);
            if (!(GhostViewApi14.this.b == null || GhostViewApi14.this.c == null)) {
                GhostViewApi14.this.b.endViewTransition(GhostViewApi14.this.c);
                ViewCompat.postInvalidateOnAnimation(GhostViewApi14.this.b);
                GhostViewApi14.this.b = null;
                GhostViewApi14.this.c = null;
            }
            return true;
        }
    };

    static class Creator implements android.support.transition.GhostViewImpl.Creator {
        Creator() {
        }

        public GhostViewImpl addGhost(View view, ViewGroup viewGroup, Matrix matrix) {
            GhostViewApi14 a = GhostViewApi14.a(view);
            if (a == null) {
                FrameLayout a2 = a(viewGroup);
                if (a2 == null) {
                    return null;
                }
                a = new GhostViewApi14(view);
                a2.addView(a);
            }
            a.d++;
            return a;
        }

        public void removeGhost(View view) {
            GhostViewApi14 a = GhostViewApi14.a(view);
            if (a != null) {
                a.d--;
                if (a.d <= 0) {
                    ViewParent parent = a.getParent();
                    if (parent instanceof ViewGroup) {
                        ViewGroup viewGroup = (ViewGroup) parent;
                        viewGroup.endViewTransition(a);
                        viewGroup.removeView(a);
                    }
                }
            }
        }

        private static FrameLayout a(ViewGroup viewGroup) {
            while (!(viewGroup instanceof FrameLayout)) {
                ViewParent parent = viewGroup.getParent();
                if (!(parent instanceof ViewGroup)) {
                    return null;
                }
                viewGroup = (ViewGroup) parent;
            }
            return (FrameLayout) viewGroup;
        }
    }

    GhostViewApi14(View view) {
        super(view.getContext());
        this.a = view;
        setLayerType(2, null);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        a(this.a, this);
        int[] iArr = new int[2];
        int[] iArr2 = new int[2];
        getLocationOnScreen(iArr);
        this.a.getLocationOnScreen(iArr2);
        iArr2[0] = (int) (((float) iArr2[0]) - this.a.getTranslationX());
        iArr2[1] = (int) (((float) iArr2[1]) - this.a.getTranslationY());
        this.f = iArr2[0] - iArr[0];
        this.g = iArr2[1] - iArr[1];
        this.a.getViewTreeObserver().addOnPreDrawListener(this.i);
        this.a.setVisibility(4);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        this.a.getViewTreeObserver().removeOnPreDrawListener(this.i);
        this.a.setVisibility(0);
        a(this.a, (GhostViewApi14) null);
        super.onDetachedFromWindow();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        this.h.set(this.e);
        this.h.postTranslate((float) this.f, (float) this.g);
        canvas.setMatrix(this.h);
        this.a.draw(canvas);
    }

    public void setVisibility(int i2) {
        super.setVisibility(i2);
        this.a.setVisibility(i2 == 0 ? 4 : 0);
    }

    public void a(ViewGroup viewGroup, View view) {
        this.b = viewGroup;
        this.c = view;
    }

    private static void a(@NonNull View view, GhostViewApi14 ghostViewApi14) {
        view.setTag(R.id.ghost_view, ghostViewApi14);
    }

    static GhostViewApi14 a(@NonNull View view) {
        return (GhostViewApi14) view.getTag(R.id.ghost_view);
    }
}
