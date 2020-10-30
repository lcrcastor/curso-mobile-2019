package android.support.design.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.design.widget.CoordinatorLayout.LayoutParams;
import android.support.v4.math.MathUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import java.util.List;

abstract class HeaderScrollingViewBehavior extends ViewOffsetBehavior<View> {
    final Rect a = new Rect();
    final Rect b = new Rect();
    private int c = 0;
    private int d;

    private static int a(int i) {
        if (i == 0) {
            return 8388659;
        }
        return i;
    }

    /* access modifiers changed from: 0000 */
    public float a(View view) {
        return 1.0f;
    }

    /* access modifiers changed from: 0000 */
    public abstract View b(List<View> list);

    public HeaderScrollingViewBehavior() {
    }

    public HeaderScrollingViewBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public boolean onMeasureChild(CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3, int i4) {
        View view2;
        int i5 = view.getLayoutParams().height;
        if (i5 == -1 || i5 == -2) {
            View b2 = b(coordinatorLayout.getDependencies(view));
            if (b2 != null) {
                if (!ViewCompat.getFitsSystemWindows(b2) || ViewCompat.getFitsSystemWindows(view)) {
                    view2 = view;
                } else {
                    view2 = view;
                    ViewCompat.setFitsSystemWindows(view2, true);
                    if (ViewCompat.getFitsSystemWindows(view2)) {
                        view2.requestLayout();
                        return true;
                    }
                }
                int size = MeasureSpec.getSize(i3);
                if (size == 0) {
                    size = coordinatorLayout.getHeight();
                }
                coordinatorLayout.onMeasureChild(view2, i, i2, MeasureSpec.makeMeasureSpec((size - b2.getMeasuredHeight()) + b(b2), i5 == -1 ? 1073741824 : Integer.MIN_VALUE), i4);
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void layoutChild(CoordinatorLayout coordinatorLayout, View view, int i) {
        View b2 = b(coordinatorLayout.getDependencies(view));
        if (b2 != null) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            Rect rect = this.a;
            rect.set(coordinatorLayout.getPaddingLeft() + layoutParams.leftMargin, b2.getBottom() + layoutParams.topMargin, (coordinatorLayout.getWidth() - coordinatorLayout.getPaddingRight()) - layoutParams.rightMargin, ((coordinatorLayout.getHeight() + b2.getBottom()) - coordinatorLayout.getPaddingBottom()) - layoutParams.bottomMargin);
            WindowInsetsCompat lastWindowInsets = coordinatorLayout.getLastWindowInsets();
            if (lastWindowInsets != null && ViewCompat.getFitsSystemWindows(coordinatorLayout) && !ViewCompat.getFitsSystemWindows(view)) {
                rect.left += lastWindowInsets.getSystemWindowInsetLeft();
                rect.right -= lastWindowInsets.getSystemWindowInsetRight();
            }
            Rect rect2 = this.b;
            GravityCompat.apply(a(layoutParams.gravity), view.getMeasuredWidth(), view.getMeasuredHeight(), rect, rect2, i);
            int c2 = c(b2);
            view.layout(rect2.left, rect2.top - c2, rect2.right, rect2.bottom - c2);
            this.c = rect2.top - b2.getBottom();
            return;
        }
        super.layoutChild(coordinatorLayout, view, i);
        this.c = 0;
    }

    /* access modifiers changed from: 0000 */
    public final int c(View view) {
        if (this.d == 0) {
            return 0;
        }
        return MathUtils.clamp((int) (a(view) * ((float) this.d)), 0, this.d);
    }

    /* access modifiers changed from: 0000 */
    public int b(View view) {
        return view.getMeasuredHeight();
    }

    /* access modifiers changed from: 0000 */
    public final int a() {
        return this.c;
    }

    public final void setOverlayTop(int i) {
        this.d = i;
    }

    public final int getOverlayTop() {
        return this.d;
    }
}
