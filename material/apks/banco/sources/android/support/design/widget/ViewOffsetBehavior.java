package android.support.design.widget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout.Behavior;
import android.util.AttributeSet;
import android.view.View;

class ViewOffsetBehavior<V extends View> extends Behavior<V> {
    private ViewOffsetHelper a;
    private int b = 0;
    private int c = 0;

    public ViewOffsetBehavior() {
    }

    public ViewOffsetBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, V v, int i) {
        layoutChild(coordinatorLayout, v, i);
        if (this.a == null) {
            this.a = new ViewOffsetHelper(v);
        }
        this.a.a();
        if (this.b != 0) {
            this.a.a(this.b);
            this.b = 0;
        }
        if (this.c != 0) {
            this.a.b(this.c);
            this.c = 0;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void layoutChild(CoordinatorLayout coordinatorLayout, V v, int i) {
        coordinatorLayout.onLayoutChild(v, i);
    }

    public boolean setTopAndBottomOffset(int i) {
        if (this.a != null) {
            return this.a.a(i);
        }
        this.b = i;
        return false;
    }

    public boolean setLeftAndRightOffset(int i) {
        if (this.a != null) {
            return this.a.b(i);
        }
        this.c = i;
        return false;
    }

    public int getTopAndBottomOffset() {
        if (this.a != null) {
            return this.a.b();
        }
        return 0;
    }

    public int getLeftAndRightOffset() {
        if (this.a != null) {
            return this.a.c();
        }
        return 0;
    }
}
