package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class LinearLayoutCompat extends ViewGroup {
    public static final int HORIZONTAL = 0;
    public static final int SHOW_DIVIDER_BEGINNING = 1;
    public static final int SHOW_DIVIDER_END = 4;
    public static final int SHOW_DIVIDER_MIDDLE = 2;
    public static final int SHOW_DIVIDER_NONE = 0;
    public static final int VERTICAL = 1;
    private boolean a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;
    private float g;
    private boolean h;
    private int[] i;
    private int[] j;
    private Drawable k;
    private int l;
    private int m;
    private int n;
    private int o;

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DividerMode {
    }

    public static class LayoutParams extends MarginLayoutParams {
        public int gravity;
        public float weight;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.gravity = -1;
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.LinearLayoutCompat_Layout);
            this.weight = obtainStyledAttributes.getFloat(R.styleable.LinearLayoutCompat_Layout_android_layout_weight, BitmapDescriptorFactory.HUE_RED);
            this.gravity = obtainStyledAttributes.getInt(R.styleable.LinearLayoutCompat_Layout_android_layout_gravity, -1);
            obtainStyledAttributes.recycle();
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.gravity = -1;
            this.weight = BitmapDescriptorFactory.HUE_RED;
        }

        public LayoutParams(int i, int i2, float f) {
            super(i, i2);
            this.gravity = -1;
            this.weight = f;
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.gravity = -1;
        }

        public LayoutParams(MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.gravity = -1;
        }

        public LayoutParams(LayoutParams layoutParams) {
            super(layoutParams);
            this.gravity = -1;
            this.weight = layoutParams.weight;
            this.gravity = layoutParams.gravity;
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface OrientationMode {
    }

    /* access modifiers changed from: 0000 */
    public int a(View view) {
        return 0;
    }

    /* access modifiers changed from: 0000 */
    public int a(View view, int i2) {
        return 0;
    }

    /* access modifiers changed from: 0000 */
    public int b(int i2) {
        return 0;
    }

    /* access modifiers changed from: 0000 */
    public int b(View view) {
        return 0;
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public LinearLayoutCompat(Context context) {
        this(context, null);
    }

    public LinearLayoutCompat(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public LinearLayoutCompat(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.a = true;
        this.b = -1;
        this.c = 0;
        this.e = 8388659;
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, R.styleable.LinearLayoutCompat, i2, 0);
        int i3 = obtainStyledAttributes.getInt(R.styleable.LinearLayoutCompat_android_orientation, -1);
        if (i3 >= 0) {
            setOrientation(i3);
        }
        int i4 = obtainStyledAttributes.getInt(R.styleable.LinearLayoutCompat_android_gravity, -1);
        if (i4 >= 0) {
            setGravity(i4);
        }
        boolean z = obtainStyledAttributes.getBoolean(R.styleable.LinearLayoutCompat_android_baselineAligned, true);
        if (!z) {
            setBaselineAligned(z);
        }
        this.g = obtainStyledAttributes.getFloat(R.styleable.LinearLayoutCompat_android_weightSum, -1.0f);
        this.b = obtainStyledAttributes.getInt(R.styleable.LinearLayoutCompat_android_baselineAlignedChildIndex, -1);
        this.h = obtainStyledAttributes.getBoolean(R.styleable.LinearLayoutCompat_measureWithLargestChild, false);
        setDividerDrawable(obtainStyledAttributes.getDrawable(R.styleable.LinearLayoutCompat_divider));
        this.n = obtainStyledAttributes.getInt(R.styleable.LinearLayoutCompat_showDividers, 0);
        this.o = obtainStyledAttributes.getDimensionPixelSize(R.styleable.LinearLayoutCompat_dividerPadding, 0);
        obtainStyledAttributes.recycle();
    }

    public void setShowDividers(int i2) {
        if (i2 != this.n) {
            requestLayout();
        }
        this.n = i2;
    }

    public int getShowDividers() {
        return this.n;
    }

    public Drawable getDividerDrawable() {
        return this.k;
    }

    public void setDividerDrawable(Drawable drawable) {
        if (drawable != this.k) {
            this.k = drawable;
            boolean z = false;
            if (drawable != null) {
                this.l = drawable.getIntrinsicWidth();
                this.m = drawable.getIntrinsicHeight();
            } else {
                this.l = 0;
                this.m = 0;
            }
            if (drawable == null) {
                z = true;
            }
            setWillNotDraw(z);
            requestLayout();
        }
    }

    public void setDividerPadding(int i2) {
        this.o = i2;
    }

    public int getDividerPadding() {
        return this.o;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public int getDividerWidth() {
        return this.l;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (this.k != null) {
            if (this.d == 1) {
                a(canvas);
            } else {
                b(canvas);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(Canvas canvas) {
        int i2;
        int virtualChildCount = getVirtualChildCount();
        for (int i3 = 0; i3 < virtualChildCount; i3++) {
            View a2 = a(i3);
            if (!(a2 == null || a2.getVisibility() == 8 || !hasDividerBeforeChildAt(i3))) {
                a(canvas, (a2.getTop() - ((LayoutParams) a2.getLayoutParams()).topMargin) - this.m);
            }
        }
        if (hasDividerBeforeChildAt(virtualChildCount)) {
            View a3 = a(virtualChildCount - 1);
            if (a3 == null) {
                i2 = (getHeight() - getPaddingBottom()) - this.m;
            } else {
                i2 = a3.getBottom() + ((LayoutParams) a3.getLayoutParams()).bottomMargin;
            }
            a(canvas, i2);
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(Canvas canvas) {
        int i2;
        int i3;
        int virtualChildCount = getVirtualChildCount();
        boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
        for (int i4 = 0; i4 < virtualChildCount; i4++) {
            View a2 = a(i4);
            if (!(a2 == null || a2.getVisibility() == 8 || !hasDividerBeforeChildAt(i4))) {
                LayoutParams layoutParams = (LayoutParams) a2.getLayoutParams();
                if (isLayoutRtl) {
                    i3 = a2.getRight() + layoutParams.rightMargin;
                } else {
                    i3 = (a2.getLeft() - layoutParams.leftMargin) - this.l;
                }
                b(canvas, i3);
            }
        }
        if (hasDividerBeforeChildAt(virtualChildCount)) {
            View a3 = a(virtualChildCount - 1);
            if (a3 != null) {
                LayoutParams layoutParams2 = (LayoutParams) a3.getLayoutParams();
                if (isLayoutRtl) {
                    i2 = (a3.getLeft() - layoutParams2.leftMargin) - this.l;
                } else {
                    i2 = a3.getRight() + layoutParams2.rightMargin;
                }
            } else if (isLayoutRtl) {
                i2 = getPaddingLeft();
            } else {
                i2 = (getWidth() - getPaddingRight()) - this.l;
            }
            b(canvas, i2);
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(Canvas canvas, int i2) {
        this.k.setBounds(getPaddingLeft() + this.o, i2, (getWidth() - getPaddingRight()) - this.o, this.m + i2);
        this.k.draw(canvas);
    }

    /* access modifiers changed from: 0000 */
    public void b(Canvas canvas, int i2) {
        this.k.setBounds(i2, getPaddingTop() + this.o, this.l + i2, (getHeight() - getPaddingBottom()) - this.o);
        this.k.draw(canvas);
    }

    public boolean isBaselineAligned() {
        return this.a;
    }

    public void setBaselineAligned(boolean z) {
        this.a = z;
    }

    public boolean isMeasureWithLargestChildEnabled() {
        return this.h;
    }

    public void setMeasureWithLargestChildEnabled(boolean z) {
        this.h = z;
    }

    public int getBaseline() {
        if (this.b < 0) {
            return super.getBaseline();
        }
        if (getChildCount() <= this.b) {
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout set to an index that is out of bounds.");
        }
        View childAt = getChildAt(this.b);
        int baseline = childAt.getBaseline();
        if (baseline != -1) {
            int i2 = this.c;
            if (this.d == 1) {
                int i3 = this.e & 112;
                if (i3 != 48) {
                    if (i3 == 16) {
                        i2 += ((((getBottom() - getTop()) - getPaddingTop()) - getPaddingBottom()) - this.f) / 2;
                    } else if (i3 == 80) {
                        i2 = ((getBottom() - getTop()) - getPaddingBottom()) - this.f;
                    }
                }
            }
            return i2 + ((LayoutParams) childAt.getLayoutParams()).topMargin + baseline;
        } else if (this.b == 0) {
            return -1;
        } else {
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout points to a View that doesn't know how to get its baseline.");
        }
    }

    public int getBaselineAlignedChildIndex() {
        return this.b;
    }

    public void setBaselineAlignedChildIndex(int i2) {
        if (i2 < 0 || i2 >= getChildCount()) {
            StringBuilder sb = new StringBuilder();
            sb.append("base aligned child index out of range (0, ");
            sb.append(getChildCount());
            sb.append(")");
            throw new IllegalArgumentException(sb.toString());
        }
        this.b = i2;
    }

    /* access modifiers changed from: 0000 */
    public View a(int i2) {
        return getChildAt(i2);
    }

    /* access modifiers changed from: 0000 */
    public int getVirtualChildCount() {
        return getChildCount();
    }

    public float getWeightSum() {
        return this.g;
    }

    public void setWeightSum(float f2) {
        this.g = Math.max(BitmapDescriptorFactory.HUE_RED, f2);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        if (this.d == 1) {
            a(i2, i3);
        } else {
            b(i2, i3);
        }
    }

    /* access modifiers changed from: protected */
    @RestrictTo({Scope.LIBRARY})
    public boolean hasDividerBeforeChildAt(int i2) {
        boolean z = false;
        if (i2 == 0) {
            if ((this.n & 1) != 0) {
                z = true;
            }
            return z;
        } else if (i2 == getChildCount()) {
            if ((this.n & 4) != 0) {
                z = true;
            }
            return z;
        } else if ((this.n & 2) == 0) {
            return false;
        } else {
            int i3 = i2 - 1;
            while (true) {
                if (i3 < 0) {
                    break;
                } else if (getChildAt(i3).getVisibility() != 8) {
                    z = true;
                    break;
                } else {
                    i3--;
                }
            }
            return z;
        }
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:144:0x0331  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(int r41, int r42) {
        /*
            r40 = this;
            r7 = r40
            r8 = r41
            r9 = r42
            r10 = 0
            r7.f = r10
            int r11 = r40.getVirtualChildCount()
            int r12 = android.view.View.MeasureSpec.getMode(r41)
            int r13 = android.view.View.MeasureSpec.getMode(r42)
            int r14 = r7.b
            boolean r15 = r7.h
            r16 = 0
            r17 = 1
            r0 = 0
            r1 = 0
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r18 = 0
            r19 = 1
            r20 = 0
        L_0x002a:
            r10 = 8
            r22 = r4
            if (r6 >= r11) goto L_0x0196
            android.view.View r4 = r7.a(r6)
            if (r4 != 0) goto L_0x0047
            int r4 = r7.f
            int r10 = r7.b(r6)
            int r4 = r4 + r10
            r7.f = r4
            r31 = r11
            r28 = r13
            r4 = r22
            goto L_0x018c
        L_0x0047:
            r24 = r1
            int r1 = r4.getVisibility()
            if (r1 != r10) goto L_0x005e
            int r1 = r7.a(r4, r6)
            int r6 = r6 + r1
            r31 = r11
            r28 = r13
            r4 = r22
            r1 = r24
            goto L_0x018c
        L_0x005e:
            boolean r1 = r7.hasDividerBeforeChildAt(r6)
            if (r1 == 0) goto L_0x006b
            int r1 = r7.f
            int r10 = r7.m
            int r1 = r1 + r10
            r7.f = r1
        L_0x006b:
            android.view.ViewGroup$LayoutParams r1 = r4.getLayoutParams()
            r10 = r1
            android.support.v7.widget.LinearLayoutCompat$LayoutParams r10 = (android.support.v7.widget.LinearLayoutCompat.LayoutParams) r10
            float r1 = r10.weight
            float r25 = r0 + r1
            r1 = 1073741824(0x40000000, float:2.0)
            if (r13 != r1) goto L_0x00a6
            int r0 = r10.height
            if (r0 != 0) goto L_0x00a6
            float r0 = r10.weight
            int r0 = (r0 > r16 ? 1 : (r0 == r16 ? 0 : -1))
            if (r0 <= 0) goto L_0x00a6
            int r0 = r7.f
            int r1 = r10.topMargin
            int r1 = r1 + r0
            r26 = r2
            int r2 = r10.bottomMargin
            int r1 = r1 + r2
            int r0 = java.lang.Math.max(r0, r1)
            r7.f = r0
            r8 = r4
            r34 = r5
            r31 = r11
            r28 = r13
            r32 = r22
            r33 = r24
            r30 = r26
            r18 = 1
            r13 = r6
            goto L_0x0112
        L_0x00a6:
            r26 = r2
            int r0 = r10.height
            if (r0 != 0) goto L_0x00b7
            float r0 = r10.weight
            int r0 = (r0 > r16 ? 1 : (r0 == r16 ? 0 : -1))
            if (r0 <= 0) goto L_0x00b7
            r0 = -2
            r10.height = r0
            r2 = 0
            goto L_0x00b9
        L_0x00b7:
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
        L_0x00b9:
            r23 = 0
            int r0 = (r25 > r16 ? 1 : (r25 == r16 ? 0 : -1))
            if (r0 != 0) goto L_0x00c4
            int r0 = r7.f
            r27 = r0
            goto L_0x00c6
        L_0x00c4:
            r27 = 0
        L_0x00c6:
            r0 = r7
            r28 = r13
            r13 = r24
            r24 = 1073741824(0x40000000, float:2.0)
            r1 = r4
            r29 = r2
            r30 = r26
            r2 = r6
            r31 = r11
            r11 = r3
            r3 = r8
            r8 = r4
            r33 = r13
            r32 = r22
            r13 = 1073741824(0x40000000, float:2.0)
            r4 = r23
            r34 = r5
            r5 = r9
            r13 = r6
            r6 = r27
            r0.a(r1, r2, r3, r4, r5, r6)
            r0 = r29
            r1 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r0 == r1) goto L_0x00f1
            r10.height = r0
        L_0x00f1:
            int r0 = r8.getMeasuredHeight()
            int r1 = r7.f
            int r2 = r1 + r0
            int r3 = r10.topMargin
            int r2 = r2 + r3
            int r3 = r10.bottomMargin
            int r2 = r2 + r3
            int r3 = r7.b(r8)
            int r2 = r2 + r3
            int r1 = java.lang.Math.max(r1, r2)
            r7.f = r1
            if (r15 == 0) goto L_0x0111
            int r3 = java.lang.Math.max(r0, r11)
            goto L_0x0112
        L_0x0111:
            r3 = r11
        L_0x0112:
            if (r14 < 0) goto L_0x011c
            int r6 = r13 + 1
            if (r14 != r6) goto L_0x011c
            int r0 = r7.f
            r7.c = r0
        L_0x011c:
            if (r13 >= r14) goto L_0x012c
            float r0 = r10.weight
            int r0 = (r0 > r16 ? 1 : (r0 == r16 ? 0 : -1))
            if (r0 <= 0) goto L_0x012c
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.String r1 = "A child of LinearLayout with index less than mBaselineAlignedChildIndex has weight > 0, which won't work.  Either remove the weight, or don't set mBaselineAlignedChildIndex."
            r0.<init>(r1)
            throw r0
        L_0x012c:
            r0 = 1073741824(0x40000000, float:2.0)
            if (r12 == r0) goto L_0x0139
            int r0 = r10.width
            r1 = -1
            if (r0 != r1) goto L_0x0139
            r0 = 1
            r20 = 1
            goto L_0x013a
        L_0x0139:
            r0 = 0
        L_0x013a:
            int r1 = r10.leftMargin
            int r2 = r10.rightMargin
            int r1 = r1 + r2
            int r2 = r8.getMeasuredWidth()
            int r2 = r2 + r1
            r4 = r30
            int r4 = java.lang.Math.max(r4, r2)
            int r5 = r8.getMeasuredState()
            r6 = r33
            int r5 = android.view.View.combineMeasuredStates(r6, r5)
            if (r19 == 0) goto L_0x015d
            int r6 = r10.width
            r11 = -1
            if (r6 != r11) goto L_0x015d
            r6 = 1
            goto L_0x015e
        L_0x015d:
            r6 = 0
        L_0x015e:
            float r10 = r10.weight
            int r10 = (r10 > r16 ? 1 : (r10 == r16 ? 0 : -1))
            if (r10 <= 0) goto L_0x0173
            if (r0 == 0) goto L_0x0169
        L_0x0166:
            r10 = r32
            goto L_0x016b
        L_0x0169:
            r1 = r2
            goto L_0x0166
        L_0x016b:
            int r0 = java.lang.Math.max(r10, r1)
            r10 = r0
            r0 = r34
            goto L_0x017e
        L_0x0173:
            r10 = r32
            if (r0 == 0) goto L_0x0178
            r2 = r1
        L_0x0178:
            r1 = r34
            int r0 = java.lang.Math.max(r1, r2)
        L_0x017e:
            int r1 = r7.a(r8, r13)
            int r1 = r1 + r13
            r2 = r4
            r19 = r6
            r4 = r10
            r6 = r1
            r1 = r5
            r5 = r0
            r0 = r25
        L_0x018c:
            int r6 = r6 + 1
            r13 = r28
            r11 = r31
            r8 = r41
            goto L_0x002a
        L_0x0196:
            r6 = r1
            r4 = r2
            r1 = r5
            r31 = r11
            r28 = r13
            r2 = r22
            r11 = r3
            int r3 = r7.f
            if (r3 <= 0) goto L_0x01b4
            r3 = r31
            boolean r5 = r7.hasDividerBeforeChildAt(r3)
            if (r5 == 0) goto L_0x01b6
            int r5 = r7.f
            int r8 = r7.m
            int r5 = r5 + r8
            r7.f = r5
            goto L_0x01b6
        L_0x01b4:
            r3 = r31
        L_0x01b6:
            if (r15 == 0) goto L_0x020f
            r5 = r28
            r8 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r5 == r8) goto L_0x01c4
            if (r5 != 0) goto L_0x01c1
            goto L_0x01c4
        L_0x01c1:
            r35 = r4
            goto L_0x0213
        L_0x01c4:
            r8 = 0
            r7.f = r8
            r8 = 0
        L_0x01c8:
            if (r8 >= r3) goto L_0x01c1
            android.view.View r13 = r7.a(r8)
            if (r13 != 0) goto L_0x01da
            int r13 = r7.f
            int r14 = r7.b(r8)
            int r13 = r13 + r14
            r7.f = r13
            goto L_0x01e5
        L_0x01da:
            int r14 = r13.getVisibility()
            if (r14 != r10) goto L_0x01e8
            int r13 = r7.a(r13, r8)
            int r8 = r8 + r13
        L_0x01e5:
            r35 = r4
            goto L_0x0208
        L_0x01e8:
            android.view.ViewGroup$LayoutParams r14 = r13.getLayoutParams()
            android.support.v7.widget.LinearLayoutCompat$LayoutParams r14 = (android.support.v7.widget.LinearLayoutCompat.LayoutParams) r14
            int r10 = r7.f
            int r21 = r10 + r11
            r35 = r4
            int r4 = r14.topMargin
            int r21 = r21 + r4
            int r4 = r14.bottomMargin
            int r21 = r21 + r4
            int r4 = r7.b(r13)
            int r4 = r21 + r4
            int r4 = java.lang.Math.max(r10, r4)
            r7.f = r4
        L_0x0208:
            int r8 = r8 + 1
            r4 = r35
            r10 = 8
            goto L_0x01c8
        L_0x020f:
            r35 = r4
            r5 = r28
        L_0x0213:
            int r4 = r7.f
            int r8 = r40.getPaddingTop()
            int r10 = r40.getPaddingBottom()
            int r8 = r8 + r10
            int r4 = r4 + r8
            r7.f = r4
            int r4 = r7.f
            int r8 = r40.getSuggestedMinimumHeight()
            int r4 = java.lang.Math.max(r4, r8)
            r8 = 0
            int r4 = android.view.View.resolveSizeAndState(r4, r9, r8)
            r8 = 16777215(0xffffff, float:2.3509886E-38)
            r8 = r8 & r4
            int r10 = r7.f
            int r8 = r8 - r10
            if (r18 != 0) goto L_0x0282
            if (r8 == 0) goto L_0x0240
            int r10 = (r0 > r16 ? 1 : (r0 == r16 ? 0 : -1))
            if (r10 <= 0) goto L_0x0240
            goto L_0x0282
        L_0x0240:
            int r0 = java.lang.Math.max(r1, r2)
            if (r15 == 0) goto L_0x027c
            r1 = 1073741824(0x40000000, float:2.0)
            if (r5 == r1) goto L_0x027c
            r1 = 0
        L_0x024b:
            if (r1 >= r3) goto L_0x027c
            android.view.View r2 = r7.a(r1)
            if (r2 == 0) goto L_0x0279
            int r5 = r2.getVisibility()
            r8 = 8
            if (r5 != r8) goto L_0x025c
            goto L_0x0279
        L_0x025c:
            android.view.ViewGroup$LayoutParams r5 = r2.getLayoutParams()
            android.support.v7.widget.LinearLayoutCompat$LayoutParams r5 = (android.support.v7.widget.LinearLayoutCompat.LayoutParams) r5
            float r5 = r5.weight
            int r5 = (r5 > r16 ? 1 : (r5 == r16 ? 0 : -1))
            if (r5 <= 0) goto L_0x0279
            int r5 = r2.getMeasuredWidth()
            r8 = 1073741824(0x40000000, float:2.0)
            int r5 = android.view.View.MeasureSpec.makeMeasureSpec(r5, r8)
            int r10 = android.view.View.MeasureSpec.makeMeasureSpec(r11, r8)
            r2.measure(r5, r10)
        L_0x0279:
            int r1 = r1 + 1
            goto L_0x024b
        L_0x027c:
            r1 = r35
            r11 = r41
            goto L_0x0376
        L_0x0282:
            float r2 = r7.g
            int r2 = (r2 > r16 ? 1 : (r2 == r16 ? 0 : -1))
            if (r2 <= 0) goto L_0x028a
            float r0 = r7.g
        L_0x028a:
            r2 = 0
            r7.f = r2
            r11 = r0
            r10 = r8
            r0 = 0
            r8 = r1
            r1 = r35
        L_0x0293:
            if (r0 >= r3) goto L_0x0365
            android.view.View r13 = r7.a(r0)
            int r14 = r13.getVisibility()
            r15 = 8
            if (r14 != r15) goto L_0x02a7
            r37 = r11
            r11 = r41
            goto L_0x035e
        L_0x02a7:
            android.view.ViewGroup$LayoutParams r14 = r13.getLayoutParams()
            android.support.v7.widget.LinearLayoutCompat$LayoutParams r14 = (android.support.v7.widget.LinearLayoutCompat.LayoutParams) r14
            float r2 = r14.weight
            int r18 = (r2 > r16 ? 1 : (r2 == r16 ? 0 : -1))
            if (r18 <= 0) goto L_0x0308
            float r15 = (float) r10
            float r15 = r15 * r2
            float r15 = r15 / r11
            int r15 = (int) r15
            float r11 = r11 - r2
            int r10 = r10 - r15
            int r2 = r40.getPaddingLeft()
            int r18 = r40.getPaddingRight()
            int r2 = r2 + r18
            r36 = r10
            int r10 = r14.leftMargin
            int r2 = r2 + r10
            int r10 = r14.rightMargin
            int r2 = r2 + r10
            int r10 = r14.width
            r37 = r11
            r11 = r41
            int r2 = getChildMeasureSpec(r11, r2, r10)
            int r10 = r14.height
            if (r10 != 0) goto L_0x02eb
            r10 = 1073741824(0x40000000, float:2.0)
            if (r5 == r10) goto L_0x02df
            goto L_0x02ed
        L_0x02df:
            if (r15 <= 0) goto L_0x02e2
            goto L_0x02e3
        L_0x02e2:
            r15 = 0
        L_0x02e3:
            int r15 = android.view.View.MeasureSpec.makeMeasureSpec(r15, r10)
            r13.measure(r2, r15)
            goto L_0x02fd
        L_0x02eb:
            r10 = 1073741824(0x40000000, float:2.0)
        L_0x02ed:
            int r18 = r13.getMeasuredHeight()
            int r15 = r18 + r15
            if (r15 >= 0) goto L_0x02f6
            r15 = 0
        L_0x02f6:
            int r15 = android.view.View.MeasureSpec.makeMeasureSpec(r15, r10)
            r13.measure(r2, r15)
        L_0x02fd:
            int r2 = r13.getMeasuredState()
            r2 = r2 & -256(0xffffffffffffff00, float:NaN)
            int r6 = android.view.View.combineMeasuredStates(r6, r2)
            goto L_0x030f
        L_0x0308:
            r2 = r11
            r11 = r41
            r37 = r2
            r36 = r10
        L_0x030f:
            int r2 = r14.leftMargin
            int r10 = r14.rightMargin
            int r2 = r2 + r10
            int r10 = r13.getMeasuredWidth()
            int r10 = r10 + r2
            int r1 = java.lang.Math.max(r1, r10)
            r15 = 1073741824(0x40000000, float:2.0)
            if (r12 == r15) goto L_0x032a
            int r15 = r14.width
            r38 = r1
            r1 = -1
            if (r15 != r1) goto L_0x032d
            r15 = 1
            goto L_0x032e
        L_0x032a:
            r38 = r1
            r1 = -1
        L_0x032d:
            r15 = 0
        L_0x032e:
            if (r15 == 0) goto L_0x0331
            goto L_0x0332
        L_0x0331:
            r2 = r10
        L_0x0332:
            int r2 = java.lang.Math.max(r8, r2)
            if (r19 == 0) goto L_0x033e
            int r8 = r14.width
            if (r8 != r1) goto L_0x033e
            r8 = 1
            goto L_0x033f
        L_0x033e:
            r8 = 0
        L_0x033f:
            int r10 = r7.f
            int r15 = r13.getMeasuredHeight()
            int r15 = r15 + r10
            int r1 = r14.topMargin
            int r15 = r15 + r1
            int r1 = r14.bottomMargin
            int r15 = r15 + r1
            int r1 = r7.b(r13)
            int r15 = r15 + r1
            int r1 = java.lang.Math.max(r10, r15)
            r7.f = r1
            r19 = r8
            r10 = r36
            r1 = r38
            r8 = r2
        L_0x035e:
            int r0 = r0 + 1
            r11 = r37
            r2 = 0
            goto L_0x0293
        L_0x0365:
            r11 = r41
            int r0 = r7.f
            int r2 = r40.getPaddingTop()
            int r5 = r40.getPaddingBottom()
            int r2 = r2 + r5
            int r0 = r0 + r2
            r7.f = r0
            r0 = r8
        L_0x0376:
            if (r19 != 0) goto L_0x037d
            r2 = 1073741824(0x40000000, float:2.0)
            if (r12 == r2) goto L_0x037d
            goto L_0x037e
        L_0x037d:
            r0 = r1
        L_0x037e:
            int r1 = r40.getPaddingLeft()
            int r2 = r40.getPaddingRight()
            int r1 = r1 + r2
            int r0 = r0 + r1
            int r1 = r40.getSuggestedMinimumWidth()
            int r0 = java.lang.Math.max(r0, r1)
            int r0 = android.view.View.resolveSizeAndState(r0, r11, r6)
            r7.setMeasuredDimension(r0, r4)
            if (r20 == 0) goto L_0x039c
            r7.c(r3, r9)
        L_0x039c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.LinearLayoutCompat.a(int, int):void");
    }

    private void c(int i2, int i3) {
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
        for (int i4 = 0; i4 < i2; i4++) {
            View a2 = a(i4);
            if (a2.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) a2.getLayoutParams();
                if (layoutParams.width == -1) {
                    int i5 = layoutParams.height;
                    layoutParams.height = a2.getMeasuredHeight();
                    measureChildWithMargins(a2, makeMeasureSpec, 0, i3, 0);
                    layoutParams.height = i5;
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:184:0x043d  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0186  */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x01c6  */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x01d4  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void b(int r42, int r43) {
        /*
            r41 = this;
            r7 = r41
            r8 = r42
            r9 = r43
            r10 = 0
            r7.f = r10
            int r11 = r41.getVirtualChildCount()
            int r12 = android.view.View.MeasureSpec.getMode(r42)
            int r13 = android.view.View.MeasureSpec.getMode(r43)
            int[] r0 = r7.i
            r14 = 4
            if (r0 == 0) goto L_0x001e
            int[] r0 = r7.j
            if (r0 != 0) goto L_0x0026
        L_0x001e:
            int[] r0 = new int[r14]
            r7.i = r0
            int[] r0 = new int[r14]
            r7.j = r0
        L_0x0026:
            int[] r15 = r7.i
            int[] r6 = r7.j
            r16 = 3
            r5 = -1
            r15[r16] = r5
            r17 = 2
            r15[r17] = r5
            r18 = 1
            r15[r18] = r5
            r15[r10] = r5
            r6[r16] = r5
            r6[r17] = r5
            r6[r18] = r5
            r6[r10] = r5
            boolean r4 = r7.a
            boolean r3 = r7.h
            r2 = 1073741824(0x40000000, float:2.0)
            if (r12 != r2) goto L_0x004c
            r19 = 1
            goto L_0x004e
        L_0x004c:
            r19 = 0
        L_0x004e:
            r20 = 0
            r0 = 0
            r1 = 0
            r14 = 0
            r21 = 0
            r22 = 0
            r23 = 0
            r24 = 0
            r25 = 0
            r26 = 1
            r27 = 0
        L_0x0061:
            r28 = r6
            r5 = 8
            if (r1 >= r11) goto L_0x0202
            android.view.View r6 = r7.a(r1)
            if (r6 != 0) goto L_0x0081
            int r5 = r7.f
            int r6 = r7.b(r1)
            int r5 = r5 + r6
            r7.f = r5
        L_0x0076:
            r30 = r0
            r0 = r1
            r31 = r3
            r35 = r4
            r1 = 1073741824(0x40000000, float:2.0)
            goto L_0x01ed
        L_0x0081:
            int r10 = r6.getVisibility()
            if (r10 != r5) goto L_0x008d
            int r5 = r7.a(r6, r1)
            int r1 = r1 + r5
            goto L_0x0076
        L_0x008d:
            boolean r5 = r7.hasDividerBeforeChildAt(r1)
            if (r5 == 0) goto L_0x009a
            int r5 = r7.f
            int r10 = r7.l
            int r5 = r5 + r10
            r7.f = r5
        L_0x009a:
            android.view.ViewGroup$LayoutParams r5 = r6.getLayoutParams()
            r10 = r5
            android.support.v7.widget.LinearLayoutCompat$LayoutParams r10 = (android.support.v7.widget.LinearLayoutCompat.LayoutParams) r10
            float r5 = r10.weight
            float r30 = r0 + r5
            if (r12 != r2) goto L_0x00f0
            int r0 = r10.width
            if (r0 != 0) goto L_0x00f0
            float r0 = r10.weight
            int r0 = (r0 > r20 ? 1 : (r0 == r20 ? 0 : -1))
            if (r0 <= 0) goto L_0x00f0
            if (r19 == 0) goto L_0x00be
            int r0 = r7.f
            int r5 = r10.leftMargin
            int r2 = r10.rightMargin
            int r5 = r5 + r2
            int r0 = r0 + r5
            r7.f = r0
            goto L_0x00cc
        L_0x00be:
            int r0 = r7.f
            int r2 = r10.leftMargin
            int r2 = r2 + r0
            int r5 = r10.rightMargin
            int r2 = r2 + r5
            int r0 = java.lang.Math.max(r0, r2)
            r7.f = r0
        L_0x00cc:
            if (r4 == 0) goto L_0x00e1
            r0 = 0
            int r2 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r0)
            r6.measure(r2, r2)
            r33 = r1
            r31 = r3
            r35 = r4
            r8 = r6
            r29 = -2
            goto L_0x0162
        L_0x00e1:
            r33 = r1
            r31 = r3
            r35 = r4
            r8 = r6
            r1 = 1073741824(0x40000000, float:2.0)
            r22 = 1
            r29 = -2
            goto L_0x0164
        L_0x00f0:
            int r0 = r10.width
            if (r0 != 0) goto L_0x00ff
            float r0 = r10.weight
            int r0 = (r0 > r20 ? 1 : (r0 == r20 ? 0 : -1))
            if (r0 <= 0) goto L_0x00ff
            r5 = -2
            r10.width = r5
            r2 = 0
            goto L_0x0102
        L_0x00ff:
            r5 = -2
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
        L_0x0102:
            int r0 = (r30 > r20 ? 1 : (r30 == r20 ? 0 : -1))
            if (r0 != 0) goto L_0x010b
            int r0 = r7.f
            r29 = r0
            goto L_0x010d
        L_0x010b:
            r29 = 0
        L_0x010d:
            r32 = 0
            r0 = r7
            r33 = r1
            r1 = r6
            r34 = r2
            r2 = r33
            r31 = r3
            r3 = r8
            r35 = r4
            r4 = r29
            r8 = -1
            r29 = -2
            r5 = r9
            r8 = r6
            r9 = -2147483648(0xffffffff80000000, float:-0.0)
            r6 = r32
            r0.a(r1, r2, r3, r4, r5, r6)
            r0 = r34
            if (r0 == r9) goto L_0x0130
            r10.width = r0
        L_0x0130:
            int r0 = r8.getMeasuredWidth()
            if (r19 == 0) goto L_0x0147
            int r1 = r7.f
            int r2 = r10.leftMargin
            int r2 = r2 + r0
            int r3 = r10.rightMargin
            int r2 = r2 + r3
            int r3 = r7.b(r8)
            int r2 = r2 + r3
            int r1 = r1 + r2
            r7.f = r1
            goto L_0x015c
        L_0x0147:
            int r1 = r7.f
            int r2 = r1 + r0
            int r3 = r10.leftMargin
            int r2 = r2 + r3
            int r3 = r10.rightMargin
            int r2 = r2 + r3
            int r3 = r7.b(r8)
            int r2 = r2 + r3
            int r1 = java.lang.Math.max(r1, r2)
            r7.f = r1
        L_0x015c:
            if (r31 == 0) goto L_0x0162
            int r14 = java.lang.Math.max(r0, r14)
        L_0x0162:
            r1 = 1073741824(0x40000000, float:2.0)
        L_0x0164:
            if (r13 == r1) goto L_0x016f
            int r0 = r10.height
            r2 = -1
            if (r0 != r2) goto L_0x016f
            r0 = 1
            r27 = 1
            goto L_0x0170
        L_0x016f:
            r0 = 0
        L_0x0170:
            int r2 = r10.topMargin
            int r3 = r10.bottomMargin
            int r2 = r2 + r3
            int r3 = r8.getMeasuredHeight()
            int r3 = r3 + r2
            int r4 = r8.getMeasuredState()
            r6 = r25
            int r4 = android.view.View.combineMeasuredStates(r6, r4)
            if (r35 == 0) goto L_0x01b0
            int r5 = r8.getBaseline()
            r6 = -1
            if (r5 == r6) goto L_0x01b0
            int r6 = r10.gravity
            if (r6 >= 0) goto L_0x0194
            int r6 = r7.e
            goto L_0x0196
        L_0x0194:
            int r6 = r10.gravity
        L_0x0196:
            r6 = r6 & 112(0x70, float:1.57E-43)
            r9 = 4
            int r6 = r6 >> r9
            r6 = r6 & -2
            int r6 = r6 >> 1
            r9 = r15[r6]
            int r9 = java.lang.Math.max(r9, r5)
            r15[r6] = r9
            r9 = r28[r6]
            int r5 = r3 - r5
            int r5 = java.lang.Math.max(r9, r5)
            r28[r6] = r5
        L_0x01b0:
            r5 = r21
            int r5 = java.lang.Math.max(r5, r3)
            if (r26 == 0) goto L_0x01bf
            int r6 = r10.height
            r9 = -1
            if (r6 != r9) goto L_0x01bf
            r6 = 1
            goto L_0x01c0
        L_0x01bf:
            r6 = 0
        L_0x01c0:
            float r9 = r10.weight
            int r9 = (r9 > r20 ? 1 : (r9 == r20 ? 0 : -1))
            if (r9 <= 0) goto L_0x01d4
            if (r0 == 0) goto L_0x01cb
        L_0x01c8:
            r10 = r24
            goto L_0x01cd
        L_0x01cb:
            r2 = r3
            goto L_0x01c8
        L_0x01cd:
            int r24 = java.lang.Math.max(r10, r2)
        L_0x01d1:
            r10 = r33
            goto L_0x01e2
        L_0x01d4:
            r10 = r24
            if (r0 == 0) goto L_0x01d9
            r3 = r2
        L_0x01d9:
            r2 = r23
            int r23 = java.lang.Math.max(r2, r3)
            r24 = r10
            goto L_0x01d1
        L_0x01e2:
            int r0 = r7.a(r8, r10)
            int r0 = r0 + r10
            r25 = r4
            r21 = r5
            r26 = r6
        L_0x01ed:
            int r0 = r0 + 1
            r1 = r0
            r6 = r28
            r0 = r30
            r3 = r31
            r4 = r35
            r2 = 1073741824(0x40000000, float:2.0)
            r5 = -1
            r8 = r42
            r9 = r43
            r10 = 0
            goto L_0x0061
        L_0x0202:
            r31 = r3
            r35 = r4
            r3 = r21
            r2 = r23
            r10 = r24
            r6 = r25
            r1 = 1073741824(0x40000000, float:2.0)
            r9 = -2147483648(0xffffffff80000000, float:-0.0)
            r29 = -2
            int r4 = r7.f
            if (r4 <= 0) goto L_0x0225
            boolean r4 = r7.hasDividerBeforeChildAt(r11)
            if (r4 == 0) goto L_0x0225
            int r4 = r7.f
            int r8 = r7.l
            int r4 = r4 + r8
            r7.f = r4
        L_0x0225:
            r4 = r15[r18]
            r8 = -1
            if (r4 != r8) goto L_0x0237
            r4 = 0
            r1 = r15[r4]
            if (r1 != r8) goto L_0x0237
            r1 = r15[r17]
            if (r1 != r8) goto L_0x0237
            r1 = r15[r16]
            if (r1 == r8) goto L_0x0267
        L_0x0237:
            r1 = r15[r16]
            r4 = 0
            r8 = r15[r4]
            r5 = r15[r18]
            r9 = r15[r17]
            int r5 = java.lang.Math.max(r5, r9)
            int r5 = java.lang.Math.max(r8, r5)
            int r1 = java.lang.Math.max(r1, r5)
            r5 = r28[r16]
            r8 = r28[r4]
            r4 = r28[r18]
            r9 = r28[r17]
            int r4 = java.lang.Math.max(r4, r9)
            int r4 = java.lang.Math.max(r8, r4)
            int r4 = java.lang.Math.max(r5, r4)
            int r1 = r1 + r4
            int r21 = java.lang.Math.max(r3, r1)
            r3 = r21
        L_0x0267:
            if (r31 == 0) goto L_0x02c8
            r1 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r12 == r1) goto L_0x026f
            if (r12 != 0) goto L_0x02c8
        L_0x026f:
            r1 = 0
            r7.f = r1
            r1 = 0
        L_0x0273:
            if (r1 >= r11) goto L_0x02c8
            android.view.View r4 = r7.a(r1)
            if (r4 != 0) goto L_0x0285
            int r4 = r7.f
            int r5 = r7.b(r1)
            int r4 = r4 + r5
            r7.f = r4
            goto L_0x0292
        L_0x0285:
            int r5 = r4.getVisibility()
            r8 = 8
            if (r5 != r8) goto L_0x0295
            int r4 = r7.a(r4, r1)
            int r1 = r1 + r4
        L_0x0292:
            r36 = r1
            goto L_0x02c5
        L_0x0295:
            android.view.ViewGroup$LayoutParams r5 = r4.getLayoutParams()
            android.support.v7.widget.LinearLayoutCompat$LayoutParams r5 = (android.support.v7.widget.LinearLayoutCompat.LayoutParams) r5
            if (r19 == 0) goto L_0x02ae
            int r8 = r7.f
            int r9 = r5.leftMargin
            int r9 = r9 + r14
            int r5 = r5.rightMargin
            int r9 = r9 + r5
            int r4 = r7.b(r4)
            int r9 = r9 + r4
            int r8 = r8 + r9
            r7.f = r8
            goto L_0x0292
        L_0x02ae:
            int r8 = r7.f
            int r9 = r8 + r14
            r36 = r1
            int r1 = r5.leftMargin
            int r9 = r9 + r1
            int r1 = r5.rightMargin
            int r9 = r9 + r1
            int r1 = r7.b(r4)
            int r9 = r9 + r1
            int r1 = java.lang.Math.max(r8, r9)
            r7.f = r1
        L_0x02c5:
            int r1 = r36 + 1
            goto L_0x0273
        L_0x02c8:
            int r1 = r7.f
            int r4 = r41.getPaddingLeft()
            int r5 = r41.getPaddingRight()
            int r4 = r4 + r5
            int r1 = r1 + r4
            r7.f = r1
            int r1 = r7.f
            int r4 = r41.getSuggestedMinimumWidth()
            int r1 = java.lang.Math.max(r1, r4)
            r4 = r42
            r5 = 0
            r8 = -1
            int r1 = android.view.View.resolveSizeAndState(r1, r4, r5)
            r5 = 16777215(0xffffff, float:2.3509886E-38)
            r5 = r5 & r1
            int r9 = r7.f
            int r5 = r5 - r9
            if (r22 != 0) goto L_0x0338
            if (r5 == 0) goto L_0x02f8
            int r21 = (r0 > r20 ? 1 : (r0 == r20 ? 0 : -1))
            if (r21 <= 0) goto L_0x02f8
            goto L_0x0338
        L_0x02f8:
            int r0 = java.lang.Math.max(r2, r10)
            if (r31 == 0) goto L_0x0334
            r2 = 1073741824(0x40000000, float:2.0)
            if (r12 == r2) goto L_0x0334
            r2 = 0
        L_0x0303:
            if (r2 >= r11) goto L_0x0334
            android.view.View r5 = r7.a(r2)
            if (r5 == 0) goto L_0x0331
            int r8 = r5.getVisibility()
            r10 = 8
            if (r8 != r10) goto L_0x0314
            goto L_0x0331
        L_0x0314:
            android.view.ViewGroup$LayoutParams r8 = r5.getLayoutParams()
            android.support.v7.widget.LinearLayoutCompat$LayoutParams r8 = (android.support.v7.widget.LinearLayoutCompat.LayoutParams) r8
            float r8 = r8.weight
            int r8 = (r8 > r20 ? 1 : (r8 == r20 ? 0 : -1))
            if (r8 <= 0) goto L_0x0331
            r8 = 1073741824(0x40000000, float:2.0)
            int r10 = android.view.View.MeasureSpec.makeMeasureSpec(r14, r8)
            int r12 = r5.getMeasuredHeight()
            int r12 = android.view.View.MeasureSpec.makeMeasureSpec(r12, r8)
            r5.measure(r10, r12)
        L_0x0331:
            int r2 = r2 + 1
            goto L_0x0303
        L_0x0334:
            r5 = r43
            goto L_0x04d1
        L_0x0338:
            float r3 = r7.g
            int r3 = (r3 > r20 ? 1 : (r3 == r20 ? 0 : -1))
            if (r3 <= 0) goto L_0x0340
            float r0 = r7.g
        L_0x0340:
            r15[r16] = r8
            r15[r17] = r8
            r15[r18] = r8
            r3 = 0
            r15[r3] = r8
            r28[r16] = r8
            r28[r17] = r8
            r28[r18] = r8
            r28[r3] = r8
            r7.f = r3
            r10 = r2
            r3 = -1
            r2 = r0
            r0 = 0
        L_0x0357:
            if (r0 >= r11) goto L_0x047f
            android.view.View r14 = r7.a(r0)
            if (r14 == 0) goto L_0x0472
            int r8 = r14.getVisibility()
            r9 = 8
            if (r8 != r9) goto L_0x0369
            goto L_0x0472
        L_0x0369:
            android.view.ViewGroup$LayoutParams r8 = r14.getLayoutParams()
            android.support.v7.widget.LinearLayoutCompat$LayoutParams r8 = (android.support.v7.widget.LinearLayoutCompat.LayoutParams) r8
            float r9 = r8.weight
            int r21 = (r9 > r20 ? 1 : (r9 == r20 ? 0 : -1))
            if (r21 <= 0) goto L_0x03cf
            float r4 = (float) r5
            float r4 = r4 * r9
            float r4 = r4 / r2
            int r4 = (int) r4
            float r2 = r2 - r9
            int r5 = r5 - r4
            int r9 = r41.getPaddingTop()
            int r21 = r41.getPaddingBottom()
            int r9 = r9 + r21
            r37 = r2
            int r2 = r8.topMargin
            int r9 = r9 + r2
            int r2 = r8.bottomMargin
            int r9 = r9 + r2
            int r2 = r8.height
            r38 = r5
            r5 = r43
            int r2 = getChildMeasureSpec(r5, r9, r2)
            int r9 = r8.width
            if (r9 != 0) goto L_0x03ad
            r9 = 1073741824(0x40000000, float:2.0)
            if (r12 == r9) goto L_0x03a1
            goto L_0x03af
        L_0x03a1:
            if (r4 <= 0) goto L_0x03a4
            goto L_0x03a5
        L_0x03a4:
            r4 = 0
        L_0x03a5:
            int r4 = android.view.View.MeasureSpec.makeMeasureSpec(r4, r9)
            r14.measure(r4, r2)
            goto L_0x03bf
        L_0x03ad:
            r9 = 1073741824(0x40000000, float:2.0)
        L_0x03af:
            int r21 = r14.getMeasuredWidth()
            int r4 = r21 + r4
            if (r4 >= 0) goto L_0x03b8
            r4 = 0
        L_0x03b8:
            int r4 = android.view.View.MeasureSpec.makeMeasureSpec(r4, r9)
            r14.measure(r4, r2)
        L_0x03bf:
            int r2 = r14.getMeasuredState()
            r4 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            r2 = r2 & r4
            int r6 = android.view.View.combineMeasuredStates(r6, r2)
            r2 = r37
            r4 = r38
            goto L_0x03d2
        L_0x03cf:
            r4 = r5
            r5 = r43
        L_0x03d2:
            if (r19 == 0) goto L_0x03f3
            int r9 = r7.f
            int r21 = r14.getMeasuredWidth()
            r39 = r2
            int r2 = r8.leftMargin
            int r21 = r21 + r2
            int r2 = r8.rightMargin
            int r21 = r21 + r2
            int r2 = r7.b(r14)
            int r21 = r21 + r2
            int r9 = r9 + r21
            r7.f = r9
            r40 = r4
        L_0x03f0:
            r2 = 1073741824(0x40000000, float:2.0)
            goto L_0x0410
        L_0x03f3:
            r39 = r2
            int r2 = r7.f
            int r9 = r14.getMeasuredWidth()
            int r9 = r9 + r2
            r40 = r4
            int r4 = r8.leftMargin
            int r9 = r9 + r4
            int r4 = r8.rightMargin
            int r9 = r9 + r4
            int r4 = r7.b(r14)
            int r9 = r9 + r4
            int r2 = java.lang.Math.max(r2, r9)
            r7.f = r2
            goto L_0x03f0
        L_0x0410:
            if (r13 == r2) goto L_0x0419
            int r2 = r8.height
            r4 = -1
            if (r2 != r4) goto L_0x0419
            r2 = 1
            goto L_0x041a
        L_0x0419:
            r2 = 0
        L_0x041a:
            int r4 = r8.topMargin
            int r9 = r8.bottomMargin
            int r4 = r4 + r9
            int r9 = r14.getMeasuredHeight()
            int r9 = r9 + r4
            int r3 = java.lang.Math.max(r3, r9)
            if (r2 == 0) goto L_0x042b
            goto L_0x042c
        L_0x042b:
            r4 = r9
        L_0x042c:
            int r2 = java.lang.Math.max(r10, r4)
            if (r26 == 0) goto L_0x0439
            int r4 = r8.height
            r10 = -1
            if (r4 != r10) goto L_0x043a
            r4 = 1
            goto L_0x043b
        L_0x0439:
            r10 = -1
        L_0x043a:
            r4 = 0
        L_0x043b:
            if (r35 == 0) goto L_0x0468
            int r14 = r14.getBaseline()
            if (r14 == r10) goto L_0x0468
            int r10 = r8.gravity
            if (r10 >= 0) goto L_0x044a
            int r8 = r7.e
            goto L_0x044c
        L_0x044a:
            int r8 = r8.gravity
        L_0x044c:
            r8 = r8 & 112(0x70, float:1.57E-43)
            r21 = 4
            int r8 = r8 >> 4
            r8 = r8 & -2
            int r8 = r8 >> 1
            r10 = r15[r8]
            int r10 = java.lang.Math.max(r10, r14)
            r15[r8] = r10
            r10 = r28[r8]
            int r9 = r9 - r14
            int r9 = java.lang.Math.max(r10, r9)
            r28[r8] = r9
            goto L_0x046a
        L_0x0468:
            r21 = 4
        L_0x046a:
            r10 = r2
            r26 = r4
            r2 = r39
            r4 = r40
            goto L_0x0477
        L_0x0472:
            r4 = r5
            r5 = r43
            r21 = 4
        L_0x0477:
            int r0 = r0 + 1
            r5 = r4
            r4 = r42
            r8 = -1
            goto L_0x0357
        L_0x047f:
            r5 = r43
            int r0 = r7.f
            int r2 = r41.getPaddingLeft()
            int r4 = r41.getPaddingRight()
            int r2 = r2 + r4
            int r0 = r0 + r2
            r7.f = r0
            r0 = r15[r18]
            r2 = -1
            if (r0 != r2) goto L_0x04a1
            r0 = 0
            r4 = r15[r0]
            if (r4 != r2) goto L_0x04a1
            r0 = r15[r17]
            if (r0 != r2) goto L_0x04a1
            r0 = r15[r16]
            if (r0 == r2) goto L_0x04d0
        L_0x04a1:
            r0 = r15[r16]
            r2 = 0
            r4 = r15[r2]
            r8 = r15[r18]
            r9 = r15[r17]
            int r8 = java.lang.Math.max(r8, r9)
            int r4 = java.lang.Math.max(r4, r8)
            int r0 = java.lang.Math.max(r0, r4)
            r4 = r28[r16]
            r2 = r28[r2]
            r8 = r28[r18]
            r9 = r28[r17]
            int r8 = java.lang.Math.max(r8, r9)
            int r2 = java.lang.Math.max(r2, r8)
            int r2 = java.lang.Math.max(r4, r2)
            int r0 = r0 + r2
            int r0 = java.lang.Math.max(r3, r0)
            r3 = r0
        L_0x04d0:
            r0 = r10
        L_0x04d1:
            if (r26 != 0) goto L_0x04d8
            r2 = 1073741824(0x40000000, float:2.0)
            if (r13 == r2) goto L_0x04d8
            goto L_0x04d9
        L_0x04d8:
            r0 = r3
        L_0x04d9:
            int r2 = r41.getPaddingTop()
            int r3 = r41.getPaddingBottom()
            int r2 = r2 + r3
            int r0 = r0 + r2
            int r2 = r41.getSuggestedMinimumHeight()
            int r0 = java.lang.Math.max(r0, r2)
            r2 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            r2 = r2 & r6
            r1 = r1 | r2
            int r2 = r6 << 16
            int r0 = android.view.View.resolveSizeAndState(r0, r5, r2)
            r7.setMeasuredDimension(r1, r0)
            if (r27 == 0) goto L_0x04ff
            r0 = r42
            r7.d(r11, r0)
        L_0x04ff:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.LinearLayoutCompat.b(int, int):void");
    }

    private void d(int i2, int i3) {
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824);
        for (int i4 = 0; i4 < i2; i4++) {
            View a2 = a(i4);
            if (a2.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) a2.getLayoutParams();
                if (layoutParams.height == -1) {
                    int i5 = layoutParams.width;
                    layoutParams.width = a2.getMeasuredWidth();
                    measureChildWithMargins(a2, i3, 0, makeMeasureSpec, 0);
                    layoutParams.width = i5;
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(View view, int i2, int i3, int i4, int i5, int i6) {
        measureChildWithMargins(view, i3, i4, i5, i6);
    }

    public void onLayout(boolean z, int i2, int i3, int i4, int i5) {
        if (this.d == 1) {
            a(i2, i3, i4, i5);
        } else {
            b(i2, i3, i4, i5);
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2, int i3, int i4, int i5) {
        int i6;
        int i7;
        int paddingLeft = getPaddingLeft();
        int i8 = i4 - i2;
        int paddingRight = i8 - getPaddingRight();
        int paddingRight2 = (i8 - paddingLeft) - getPaddingRight();
        int virtualChildCount = getVirtualChildCount();
        int i9 = this.e & 112;
        int i10 = this.e & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        if (i9 == 16) {
            i6 = (((i5 - i3) - this.f) / 2) + getPaddingTop();
        } else if (i9 != 80) {
            i6 = getPaddingTop();
        } else {
            i6 = ((getPaddingTop() + i5) - i3) - this.f;
        }
        int i11 = 0;
        while (i11 < virtualChildCount) {
            View a2 = a(i11);
            if (a2 == null) {
                i6 += b(i11);
            } else if (a2.getVisibility() != 8) {
                int measuredWidth = a2.getMeasuredWidth();
                int measuredHeight = a2.getMeasuredHeight();
                LayoutParams layoutParams = (LayoutParams) a2.getLayoutParams();
                int i12 = layoutParams.gravity;
                if (i12 < 0) {
                    i12 = i10;
                }
                int absoluteGravity = GravityCompat.getAbsoluteGravity(i12, ViewCompat.getLayoutDirection(this)) & 7;
                if (absoluteGravity == 1) {
                    i7 = ((((paddingRight2 - measuredWidth) / 2) + paddingLeft) + layoutParams.leftMargin) - layoutParams.rightMargin;
                } else if (absoluteGravity != 5) {
                    i7 = layoutParams.leftMargin + paddingLeft;
                } else {
                    i7 = (paddingRight - measuredWidth) - layoutParams.rightMargin;
                }
                int i13 = i7;
                if (hasDividerBeforeChildAt(i11)) {
                    i6 += this.m;
                }
                int i14 = i6 + layoutParams.topMargin;
                LayoutParams layoutParams2 = layoutParams;
                a(a2, i13, i14 + a(a2), measuredWidth, measuredHeight);
                i11 += a(a2, i11);
                i6 = i14 + measuredHeight + layoutParams2.bottomMargin + b(a2);
            }
            i11++;
        }
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00b6  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00bf  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00f6  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x010a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void b(int r28, int r29, int r30, int r31) {
        /*
            r27 = this;
            r6 = r27
            boolean r2 = android.support.v7.widget.ViewUtils.isLayoutRtl(r27)
            int r7 = r27.getPaddingTop()
            int r3 = r31 - r29
            int r4 = r27.getPaddingBottom()
            int r8 = r3 - r4
            int r3 = r3 - r7
            int r4 = r27.getPaddingBottom()
            int r9 = r3 - r4
            int r10 = r27.getVirtualChildCount()
            int r3 = r6.e
            r4 = 8388615(0x800007, float:1.1754953E-38)
            r3 = r3 & r4
            int r4 = r6.e
            r11 = r4 & 112(0x70, float:1.57E-43)
            boolean r12 = r6.a
            int[] r13 = r6.i
            int[] r14 = r6.j
            int r4 = android.support.v4.view.ViewCompat.getLayoutDirection(r27)
            int r3 = android.support.v4.view.GravityCompat.getAbsoluteGravity(r3, r4)
            r15 = 2
            r5 = 1
            if (r3 == r5) goto L_0x004e
            r4 = 5
            if (r3 == r4) goto L_0x0041
            int r0 = r27.getPaddingLeft()
            goto L_0x0059
        L_0x0041:
            int r3 = r27.getPaddingLeft()
            int r3 = r3 + r30
            int r3 = r3 - r28
            int r0 = r6.f
            int r0 = r3 - r0
            goto L_0x0059
        L_0x004e:
            int r3 = r27.getPaddingLeft()
            int r0 = r30 - r28
            int r1 = r6.f
            int r0 = r0 - r1
            int r0 = r0 / r15
            int r0 = r0 + r3
        L_0x0059:
            r1 = 0
            if (r2 == 0) goto L_0x0063
            int r2 = r10 + -1
            r16 = r2
            r17 = -1
            goto L_0x0067
        L_0x0063:
            r16 = 0
            r17 = 1
        L_0x0067:
            r3 = 0
        L_0x0068:
            if (r3 >= r10) goto L_0x014a
            int r1 = r17 * r3
            int r2 = r16 + r1
            android.view.View r1 = r6.a(r2)
            if (r1 != 0) goto L_0x0085
            int r1 = r6.b(r2)
            int r0 = r0 + r1
        L_0x0079:
            r26 = r7
            r23 = r10
            r24 = r11
            r18 = 1
            r20 = -1
            goto L_0x013e
        L_0x0085:
            int r5 = r1.getVisibility()
            r15 = 8
            if (r5 == r15) goto L_0x013a
            int r15 = r1.getMeasuredWidth()
            int r5 = r1.getMeasuredHeight()
            android.view.ViewGroup$LayoutParams r20 = r1.getLayoutParams()
            r4 = r20
            android.support.v7.widget.LinearLayoutCompat$LayoutParams r4 = (android.support.v7.widget.LinearLayoutCompat.LayoutParams) r4
            if (r12 == 0) goto L_0x00ad
            r22 = r3
            int r3 = r4.height
            r23 = r10
            r10 = -1
            if (r3 == r10) goto L_0x00b1
            int r3 = r1.getBaseline()
            goto L_0x00b2
        L_0x00ad:
            r22 = r3
            r23 = r10
        L_0x00b1:
            r3 = -1
        L_0x00b2:
            int r10 = r4.gravity
            if (r10 >= 0) goto L_0x00b7
            r10 = r11
        L_0x00b7:
            r10 = r10 & 112(0x70, float:1.57E-43)
            r24 = r11
            r11 = 16
            if (r10 == r11) goto L_0x00f6
            r11 = 48
            if (r10 == r11) goto L_0x00e3
            r11 = 80
            if (r10 == r11) goto L_0x00cc
            r3 = r7
            r11 = -1
        L_0x00c9:
            r18 = 1
            goto L_0x0104
        L_0x00cc:
            int r10 = r8 - r5
            int r11 = r4.bottomMargin
            int r10 = r10 - r11
            r11 = -1
            if (r3 == r11) goto L_0x00e1
            int r20 = r1.getMeasuredHeight()
            int r20 = r20 - r3
            r3 = 2
            r21 = r14[r3]
            int r21 = r21 - r20
            int r10 = r10 - r21
        L_0x00e1:
            r3 = r10
            goto L_0x00c9
        L_0x00e3:
            r11 = -1
            int r10 = r4.topMargin
            int r10 = r10 + r7
            if (r3 == r11) goto L_0x00f2
            r18 = 1
            r20 = r13[r18]
            int r20 = r20 - r3
            int r10 = r10 + r20
            goto L_0x00f4
        L_0x00f2:
            r18 = 1
        L_0x00f4:
            r3 = r10
            goto L_0x0104
        L_0x00f6:
            r11 = -1
            r18 = 1
            int r3 = r9 - r5
            r10 = 2
            int r3 = r3 / r10
            int r3 = r3 + r7
            int r10 = r4.topMargin
            int r3 = r3 + r10
            int r10 = r4.bottomMargin
            int r3 = r3 - r10
        L_0x0104:
            boolean r10 = r6.hasDividerBeforeChildAt(r2)
            if (r10 == 0) goto L_0x010d
            int r10 = r6.l
            int r0 = r0 + r10
        L_0x010d:
            int r10 = r4.leftMargin
            int r10 = r10 + r0
            int r0 = r6.a(r1)
            int r19 = r10 + r0
            r0 = r6
            r25 = r1
            r11 = r2
            r2 = r19
            r19 = r22
            r26 = r7
            r20 = -1
            r7 = r4
            r4 = r15
            r0.a(r1, r2, r3, r4, r5)
            int r0 = r7.rightMargin
            int r15 = r15 + r0
            r0 = r25
            int r1 = r6.b(r0)
            int r15 = r15 + r1
            int r10 = r10 + r15
            int r0 = r6.a(r0, r11)
            int r3 = r19 + r0
            r0 = r10
            goto L_0x013e
        L_0x013a:
            r19 = r3
            goto L_0x0079
        L_0x013e:
            int r3 = r3 + 1
            r10 = r23
            r11 = r24
            r7 = r26
            r5 = 1
            r15 = 2
            goto L_0x0068
        L_0x014a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.LinearLayoutCompat.b(int, int, int, int):void");
    }

    private void a(View view, int i2, int i3, int i4, int i5) {
        view.layout(i2, i3, i4 + i2, i5 + i3);
    }

    public void setOrientation(int i2) {
        if (this.d != i2) {
            this.d = i2;
            requestLayout();
        }
    }

    public int getOrientation() {
        return this.d;
    }

    public void setGravity(int i2) {
        if (this.e != i2) {
            if ((8388615 & i2) == 0) {
                i2 |= GravityCompat.START;
            }
            if ((i2 & 112) == 0) {
                i2 |= 48;
            }
            this.e = i2;
            requestLayout();
        }
    }

    public int getGravity() {
        return this.e;
    }

    public void setHorizontalGravity(int i2) {
        int i3 = i2 & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        if ((8388615 & this.e) != i3) {
            this.e = i3 | (this.e & -8388616);
            requestLayout();
        }
    }

    public void setVerticalGravity(int i2) {
        int i3 = i2 & 112;
        if ((this.e & 112) != i3) {
            this.e = i3 | (this.e & -113);
            requestLayout();
        }
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateDefaultLayoutParams() {
        if (this.d == 0) {
            return new LayoutParams(-2, -2);
        }
        if (this.d == 1) {
            return new LayoutParams(-1, -2);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(LinearLayoutCompat.class.getName());
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(LinearLayoutCompat.class.getName());
    }
}
