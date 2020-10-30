package ar.com.santander.rio.mbanking.components.asymmetricgridview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;

public class IcsLinearLayout extends NineLinearLayout {
    public static final int SHOW_DIVIDER_BEGINNING = 1;
    public static final int SHOW_DIVIDER_END = 4;
    public static final int SHOW_DIVIDER_MIDDLE = 2;
    public static final int SHOW_DIVIDER_NONE = 0;
    private static final int[] a = {16843049, 16843561, 16843562};
    private Drawable b;
    private int c;
    private int d;
    private int e;
    private int f;

    public IcsLinearLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, a);
        setDividerDrawable(obtainStyledAttributes.getDrawable(0));
        this.e = obtainStyledAttributes.getInt(1, 0);
        this.f = obtainStyledAttributes.getDimensionPixelSize(2, 0);
        obtainStyledAttributes.recycle();
    }

    public void setShowDividers(int i) {
        if (i != this.e) {
            requestLayout();
            invalidate();
        }
        this.e = i;
    }

    public int getShowDividers() {
        return this.e;
    }

    public void setDividerDrawable(Drawable drawable) {
        if (drawable != this.b) {
            if ((drawable instanceof ColorDrawable) && VERSION.SDK_INT < 11) {
                drawable = new IcsColorDrawable((ColorDrawable) drawable);
            }
            this.b = drawable;
            boolean z = false;
            if (drawable != null) {
                this.c = drawable.getIntrinsicWidth();
                this.d = drawable.getIntrinsicHeight();
            } else {
                this.c = 0;
                this.d = 0;
            }
            if (drawable == null) {
                z = true;
            }
            setWillNotDraw(z);
            requestLayout();
        }
    }

    public void setDividerPadding(int i) {
        this.f = i;
    }

    public int getDividerPadding() {
        return this.f;
    }

    public int getDividerWidth() {
        return this.c;
    }

    /* access modifiers changed from: protected */
    public void measureChildWithMargins(View view, int i, int i2, int i3, int i4) {
        int indexOfChild = indexOfChild(view);
        int orientation = getOrientation();
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (hasDividerBeforeChildAt(indexOfChild)) {
            if (orientation == 1) {
                layoutParams.topMargin = this.d;
            } else {
                layoutParams.leftMargin = this.c;
            }
        }
        int childCount = getChildCount();
        if (indexOfChild == childCount - 1 && hasDividerBeforeChildAt(childCount)) {
            if (orientation == 1) {
                layoutParams.bottomMargin = this.d;
            } else {
                layoutParams.rightMargin = this.c;
            }
        }
        super.measureChildWithMargins(view, i, i2, i3, i4);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (this.b != null) {
            if (getOrientation() == 1) {
                a(canvas);
            } else {
                b(canvas);
            }
        }
        super.onDraw(canvas);
    }

    /* access modifiers changed from: 0000 */
    public void a(Canvas canvas) {
        int i;
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (!(childAt == null || childAt.getVisibility() == 8 || !hasDividerBeforeChildAt(i2))) {
                a(canvas, childAt.getTop() - ((LayoutParams) childAt.getLayoutParams()).topMargin);
            }
        }
        if (hasDividerBeforeChildAt(childCount)) {
            View childAt2 = getChildAt(childCount - 1);
            if (childAt2 == null) {
                i = (getHeight() - getPaddingBottom()) - this.d;
            } else {
                i = childAt2.getBottom();
            }
            a(canvas, i);
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(Canvas canvas) {
        int i;
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (!(childAt == null || childAt.getVisibility() == 8 || !hasDividerBeforeChildAt(i2))) {
                b(canvas, childAt.getLeft() - ((LayoutParams) childAt.getLayoutParams()).leftMargin);
            }
        }
        if (hasDividerBeforeChildAt(childCount)) {
            View childAt2 = getChildAt(childCount - 1);
            if (childAt2 == null) {
                i = (getWidth() - getPaddingRight()) - this.c;
            } else {
                i = childAt2.getRight();
            }
            b(canvas, i);
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(Canvas canvas, int i) {
        this.b.setBounds(getPaddingLeft() + this.f, i, (getWidth() - getPaddingRight()) - this.f, this.d + i);
        this.b.draw(canvas);
    }

    /* access modifiers changed from: 0000 */
    public void b(Canvas canvas, int i) {
        this.b.setBounds(i, getPaddingTop() + this.f, this.c + i, (getHeight() - getPaddingBottom()) - this.f);
        this.b.draw(canvas);
    }

    /* access modifiers changed from: protected */
    public boolean hasDividerBeforeChildAt(int i) {
        boolean z = false;
        if (i == 0) {
            if ((this.e & 1) != 0) {
                z = true;
            }
            return z;
        } else if (i == getChildCount()) {
            if ((this.e & 4) != 0) {
                z = true;
            }
            return z;
        } else if ((this.e & 2) == 0) {
            return false;
        } else {
            int i2 = i - 1;
            while (true) {
                if (i2 < 0) {
                    break;
                } else if (getChildAt(i2).getVisibility() != 8) {
                    z = true;
                    break;
                } else {
                    i2--;
                }
            }
            return z;
        }
    }
}
