package android.support.v4.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;

public class PagerTabStrip extends PagerTitleStrip {
    private int g;
    private int h;
    private int i;
    private int j;
    private int k;
    private int l;
    private final Paint m;
    private final Rect n;
    private int o;
    private boolean p;
    private boolean q;
    private int r;
    private boolean s;
    private float t;
    private float u;
    private int v;

    public PagerTabStrip(@NonNull Context context) {
        this(context, null);
    }

    public PagerTabStrip(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.m = new Paint();
        this.n = new Rect();
        this.o = 255;
        this.p = false;
        this.q = false;
        this.g = this.f;
        this.m.setColor(this.g);
        float f = context.getResources().getDisplayMetrics().density;
        this.h = (int) ((3.0f * f) + 0.5f);
        this.i = (int) ((6.0f * f) + 0.5f);
        this.j = (int) (64.0f * f);
        this.l = (int) ((16.0f * f) + 0.5f);
        this.r = (int) ((1.0f * f) + 0.5f);
        this.k = (int) ((f * 32.0f) + 0.5f);
        this.v = ViewConfiguration.get(context).getScaledTouchSlop();
        setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
        setTextSpacing(getTextSpacing());
        setWillNotDraw(false);
        this.b.setFocusable(true);
        this.b.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                PagerTabStrip.this.a.setCurrentItem(PagerTabStrip.this.a.getCurrentItem() - 1);
            }
        });
        this.d.setFocusable(true);
        this.d.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                PagerTabStrip.this.a.setCurrentItem(PagerTabStrip.this.a.getCurrentItem() + 1);
            }
        });
        if (getBackground() == null) {
            this.p = true;
        }
    }

    public void setTabIndicatorColor(@ColorInt int i2) {
        this.g = i2;
        this.m.setColor(this.g);
        invalidate();
    }

    public void setTabIndicatorColorResource(@ColorRes int i2) {
        setTabIndicatorColor(ContextCompat.getColor(getContext(), i2));
    }

    @ColorInt
    public int getTabIndicatorColor() {
        return this.g;
    }

    public void setPadding(int i2, int i3, int i4, int i5) {
        if (i5 < this.i) {
            i5 = this.i;
        }
        super.setPadding(i2, i3, i4, i5);
    }

    public void setTextSpacing(int i2) {
        if (i2 < this.j) {
            i2 = this.j;
        }
        super.setTextSpacing(i2);
    }

    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        if (!this.q) {
            this.p = drawable == null;
        }
    }

    public void setBackgroundColor(@ColorInt int i2) {
        super.setBackgroundColor(i2);
        if (!this.q) {
            this.p = (i2 & ViewCompat.MEASURED_STATE_MASK) == 0;
        }
    }

    public void setBackgroundResource(@DrawableRes int i2) {
        super.setBackgroundResource(i2);
        if (!this.q) {
            this.p = i2 == 0;
        }
    }

    public void setDrawFullUnderline(boolean z) {
        this.p = z;
        this.q = true;
        invalidate();
    }

    public boolean getDrawFullUnderline() {
        return this.p;
    }

    /* access modifiers changed from: 0000 */
    public int getMinHeight() {
        return Math.max(super.getMinHeight(), this.k);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action != 0 && this.s) {
            return false;
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        switch (action) {
            case 0:
                this.t = x;
                this.u = y;
                this.s = false;
                break;
            case 1:
                if (x >= ((float) (this.c.getLeft() - this.l))) {
                    if (x > ((float) (this.c.getRight() + this.l))) {
                        this.a.setCurrentItem(this.a.getCurrentItem() + 1);
                        break;
                    }
                } else {
                    this.a.setCurrentItem(this.a.getCurrentItem() - 1);
                    break;
                }
                break;
            case 2:
                if (Math.abs(x - this.t) > ((float) this.v) || Math.abs(y - this.u) > ((float) this.v)) {
                    this.s = true;
                    break;
                }
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int left = this.c.getLeft() - this.l;
        int right = this.c.getRight() + this.l;
        int i2 = height - this.h;
        this.m.setColor((this.o << 24) | (this.g & ViewCompat.MEASURED_SIZE_MASK));
        float f = (float) height;
        canvas.drawRect((float) left, (float) i2, (float) right, f, this.m);
        if (this.p) {
            this.m.setColor(-16777216 | (this.g & ViewCompat.MEASURED_SIZE_MASK));
            canvas.drawRect((float) getPaddingLeft(), (float) (height - this.r), (float) (getWidth() - getPaddingRight()), f, this.m);
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2, float f, boolean z) {
        Rect rect = this.n;
        int height = getHeight();
        int i3 = height - this.h;
        rect.set(this.c.getLeft() - this.l, i3, this.c.getRight() + this.l, height);
        super.a(i2, f, z);
        this.o = (int) (Math.abs(f - 0.5f) * 2.0f * 255.0f);
        rect.union(this.c.getLeft() - this.l, i3, this.c.getRight() + this.l, height);
        invalidate(rect);
    }
}
