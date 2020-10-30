package android.support.v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.StyleRes;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuBuilder.Callback;
import android.support.v7.view.menu.MenuBuilder.ItemInvoker;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.view.menu.MenuView;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewDebug.ExportedProperty;
import android.view.accessibility.AccessibilityEvent;

public class ActionMenuView extends LinearLayoutCompat implements ItemInvoker, MenuView {
    Callback a;
    OnMenuItemClickListener b;
    private MenuBuilder c;
    private Context d;
    private int e;
    private boolean f;
    private ActionMenuPresenter g;
    private MenuPresenter.Callback h;
    private boolean i;
    private int j;
    private int k;
    private int l;

    @RestrictTo({Scope.LIBRARY_GROUP})
    public interface ActionMenuChildView {
        boolean needsDividerAfter();

        boolean needsDividerBefore();
    }

    static class ActionMenuPresenterCallback implements MenuPresenter.Callback {
        public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        }

        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            return false;
        }

        ActionMenuPresenterCallback() {
        }
    }

    public static class LayoutParams extends android.support.v7.widget.LinearLayoutCompat.LayoutParams {
        boolean a;
        @ExportedProperty
        public int cellsUsed;
        @ExportedProperty
        public boolean expandable;
        @ExportedProperty
        public int extraPixels;
        @ExportedProperty
        public boolean isOverflowButton;
        @ExportedProperty
        public boolean preventEdgeOffset;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((android.view.ViewGroup.LayoutParams) layoutParams);
            this.isOverflowButton = layoutParams.isOverflowButton;
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.isOverflowButton = false;
        }
    }

    class MenuBuilderCallback implements Callback {
        MenuBuilderCallback() {
        }

        public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
            return ActionMenuView.this.b != null && ActionMenuView.this.b.onMenuItemClick(menuItem);
        }

        public void onMenuModeChange(MenuBuilder menuBuilder) {
            if (ActionMenuView.this.a != null) {
                ActionMenuView.this.a.onMenuModeChange(menuBuilder);
            }
        }
    }

    public interface OnMenuItemClickListener {
        boolean onMenuItemClick(MenuItem menuItem);
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        return false;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public int getWindowAnimations() {
        return 0;
    }

    public ActionMenuView(Context context) {
        this(context, null);
    }

    public ActionMenuView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setBaselineAligned(false);
        float f2 = context.getResources().getDisplayMetrics().density;
        this.k = (int) (56.0f * f2);
        this.l = (int) (f2 * 4.0f);
        this.d = context;
        this.e = 0;
    }

    public void setPopupTheme(@StyleRes int i2) {
        if (this.e != i2) {
            this.e = i2;
            if (i2 == 0) {
                this.d = getContext();
            } else {
                this.d = new ContextThemeWrapper(getContext(), i2);
            }
        }
    }

    public int getPopupTheme() {
        return this.e;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setPresenter(ActionMenuPresenter actionMenuPresenter) {
        this.g = actionMenuPresenter;
        this.g.a(this);
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.g != null) {
            this.g.updateMenuView(false);
            if (this.g.f()) {
                this.g.c();
                this.g.b();
            }
        }
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.b = onMenuItemClickListener;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        boolean z = this.i;
        this.i = MeasureSpec.getMode(i2) == 1073741824;
        if (z != this.i) {
            this.j = 0;
        }
        int size = MeasureSpec.getSize(i2);
        if (!(!this.i || this.c == null || size == this.j)) {
            this.j = size;
            this.c.onItemsChanged(true);
        }
        int childCount = getChildCount();
        if (!this.i || childCount <= 0) {
            for (int i4 = 0; i4 < childCount; i4++) {
                LayoutParams layoutParams = (LayoutParams) getChildAt(i4).getLayoutParams();
                layoutParams.rightMargin = 0;
                layoutParams.leftMargin = 0;
            }
            super.onMeasure(i2, i3);
            return;
        }
        c(i2, i3);
    }

    /* JADX WARNING: Removed duplicated region for block: B:134:0x025d A[LOOP:5: B:134:0x025d->B:139:0x0280, LOOP_START, PHI: r3 r34 
      PHI: (r3v5 int) = (r3v4 int), (r3v6 int) binds: [B:133:0x025b, B:139:0x0280] A[DONT_GENERATE, DONT_INLINE]
      PHI: (r34v1 int) = (r34v0 int), (r34v2 int) binds: [B:133:0x025b, B:139:0x0280] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARNING: Removed duplicated region for block: B:141:0x0287  */
    /* JADX WARNING: Removed duplicated region for block: B:142:0x028c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void c(int r37, int r38) {
        /*
            r36 = this;
            r0 = r36
            int r1 = android.view.View.MeasureSpec.getMode(r38)
            int r2 = android.view.View.MeasureSpec.getSize(r37)
            int r3 = android.view.View.MeasureSpec.getSize(r38)
            int r4 = r36.getPaddingLeft()
            int r5 = r36.getPaddingRight()
            int r4 = r4 + r5
            int r5 = r36.getPaddingTop()
            int r6 = r36.getPaddingBottom()
            int r5 = r5 + r6
            r6 = -2
            r7 = r38
            int r6 = getChildMeasureSpec(r7, r5, r6)
            int r2 = r2 - r4
            int r4 = r0.k
            int r4 = r2 / r4
            int r7 = r0.k
            int r7 = r2 % r7
            r8 = 0
            if (r4 != 0) goto L_0x0037
            r0.setMeasuredDimension(r2, r8)
            return
        L_0x0037:
            int r9 = r0.k
            int r7 = r7 / r4
            int r9 = r9 + r7
            int r7 = r36.getChildCount()
            r14 = r4
            r4 = 0
            r10 = 0
            r12 = 0
            r13 = 0
            r15 = 0
            r16 = 0
            r19 = 0
        L_0x0049:
            if (r4 >= r7) goto L_0x00da
            android.view.View r11 = r0.getChildAt(r4)
            int r8 = r11.getVisibility()
            r21 = r3
            r3 = 8
            if (r8 != r3) goto L_0x005d
            r23 = r2
            goto L_0x00d1
        L_0x005d:
            boolean r3 = r11 instanceof android.support.v7.view.menu.ActionMenuItemView
            int r13 = r13 + 1
            if (r3 == 0) goto L_0x0070
            int r8 = r0.l
            r22 = r13
            int r13 = r0.l
            r23 = r2
            r2 = 0
            r11.setPadding(r8, r2, r13, r2)
            goto L_0x0075
        L_0x0070:
            r23 = r2
            r22 = r13
            r2 = 0
        L_0x0075:
            android.view.ViewGroup$LayoutParams r8 = r11.getLayoutParams()
            android.support.v7.widget.ActionMenuView$LayoutParams r8 = (android.support.v7.widget.ActionMenuView.LayoutParams) r8
            r8.a = r2
            r8.extraPixels = r2
            r8.cellsUsed = r2
            r8.expandable = r2
            r8.leftMargin = r2
            r8.rightMargin = r2
            if (r3 == 0) goto L_0x0094
            r2 = r11
            android.support.v7.view.menu.ActionMenuItemView r2 = (android.support.v7.view.menu.ActionMenuItemView) r2
            boolean r2 = r2.hasText()
            if (r2 == 0) goto L_0x0094
            r2 = 1
            goto L_0x0095
        L_0x0094:
            r2 = 0
        L_0x0095:
            r8.preventEdgeOffset = r2
            boolean r2 = r8.isOverflowButton
            if (r2 == 0) goto L_0x009d
            r2 = 1
            goto L_0x009e
        L_0x009d:
            r2 = r14
        L_0x009e:
            int r2 = a(r11, r9, r2, r6, r5)
            int r3 = java.lang.Math.max(r15, r2)
            boolean r13 = r8.expandable
            if (r13 == 0) goto L_0x00ac
            int r16 = r16 + 1
        L_0x00ac:
            boolean r8 = r8.isOverflowButton
            if (r8 == 0) goto L_0x00b1
            r12 = 1
        L_0x00b1:
            int r14 = r14 - r2
            int r8 = r11.getMeasuredHeight()
            int r10 = java.lang.Math.max(r10, r8)
            r8 = 1
            if (r2 != r8) goto L_0x00cb
            int r2 = r8 << r4
            r24 = r3
            long r2 = (long) r2
            long r25 = r19 | r2
            r13 = r22
            r15 = r24
            r19 = r25
            goto L_0x00d1
        L_0x00cb:
            r24 = r3
            r13 = r22
            r15 = r24
        L_0x00d1:
            int r4 = r4 + 1
            r3 = r21
            r2 = r23
            r8 = 0
            goto L_0x0049
        L_0x00da:
            r23 = r2
            r21 = r3
            r2 = 2
            if (r12 == 0) goto L_0x00e5
            if (r13 != r2) goto L_0x00e5
            r3 = 1
            goto L_0x00e6
        L_0x00e5:
            r3 = 0
        L_0x00e6:
            r4 = 0
        L_0x00e7:
            r24 = 1
            if (r16 <= 0) goto L_0x018a
            if (r14 <= 0) goto L_0x018a
            r5 = 2147483647(0x7fffffff, float:NaN)
            r5 = 0
            r8 = 0
            r11 = 2147483647(0x7fffffff, float:NaN)
            r26 = 0
        L_0x00f7:
            if (r5 >= r7) goto L_0x0127
            android.view.View r2 = r0.getChildAt(r5)
            android.view.ViewGroup$LayoutParams r2 = r2.getLayoutParams()
            android.support.v7.widget.ActionMenuView$LayoutParams r2 = (android.support.v7.widget.ActionMenuView.LayoutParams) r2
            r28 = r4
            boolean r4 = r2.expandable
            if (r4 != 0) goto L_0x010a
            goto L_0x0121
        L_0x010a:
            int r4 = r2.cellsUsed
            if (r4 >= r11) goto L_0x0115
            int r2 = r2.cellsUsed
            long r26 = r24 << r5
            r11 = r2
            r8 = 1
            goto L_0x0121
        L_0x0115:
            int r2 = r2.cellsUsed
            if (r2 != r11) goto L_0x0121
            long r29 = r24 << r5
            long r31 = r26 | r29
            int r8 = r8 + 1
            r26 = r31
        L_0x0121:
            int r5 = r5 + 1
            r4 = r28
            r2 = 2
            goto L_0x00f7
        L_0x0127:
            r28 = r4
            long r4 = r19 | r26
            if (r8 <= r14) goto L_0x0134
            r34 = r6
            r35 = r7
            r33 = r10
            goto L_0x0194
        L_0x0134:
            int r11 = r11 + 1
            r19 = r4
            r2 = 0
        L_0x0139:
            if (r2 >= r7) goto L_0x0186
            android.view.View r4 = r0.getChildAt(r2)
            android.view.ViewGroup$LayoutParams r5 = r4.getLayoutParams()
            android.support.v7.widget.ActionMenuView$LayoutParams r5 = (android.support.v7.widget.ActionMenuView.LayoutParams) r5
            r33 = r10
            r8 = 1
            int r10 = r8 << r2
            r34 = r6
            r35 = r7
            long r6 = (long) r10
            long r24 = r26 & r6
            r17 = 0
            int r8 = (r24 > r17 ? 1 : (r24 == r17 ? 0 : -1))
            if (r8 != 0) goto L_0x0160
            int r4 = r5.cellsUsed
            if (r4 != r11) goto L_0x017d
            long r4 = r19 | r6
            r19 = r4
            goto L_0x017d
        L_0x0160:
            if (r3 == 0) goto L_0x0173
            boolean r6 = r5.preventEdgeOffset
            if (r6 == 0) goto L_0x0173
            r6 = 1
            if (r14 != r6) goto L_0x0174
            int r7 = r0.l
            int r7 = r7 + r9
            int r8 = r0.l
            r10 = 0
            r4.setPadding(r7, r10, r8, r10)
            goto L_0x0174
        L_0x0173:
            r6 = 1
        L_0x0174:
            int r4 = r5.cellsUsed
            int r4 = r4 + r6
            r5.cellsUsed = r4
            r5.a = r6
            int r14 = r14 + -1
        L_0x017d:
            int r2 = r2 + 1
            r10 = r33
            r6 = r34
            r7 = r35
            goto L_0x0139
        L_0x0186:
            r2 = 2
            r4 = 1
            goto L_0x00e7
        L_0x018a:
            r28 = r4
            r34 = r6
            r35 = r7
            r33 = r10
            r4 = r19
        L_0x0194:
            if (r12 != 0) goto L_0x019b
            r2 = 1
            if (r13 != r2) goto L_0x019c
            r3 = 1
            goto L_0x019d
        L_0x019b:
            r2 = 1
        L_0x019c:
            r3 = 0
        L_0x019d:
            if (r14 <= 0) goto L_0x0254
            r6 = 0
            int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r8 == 0) goto L_0x0254
            int r13 = r13 - r2
            if (r14 < r13) goto L_0x01ac
            if (r3 != 0) goto L_0x01ac
            if (r15 <= r2) goto L_0x0254
        L_0x01ac:
            int r2 = java.lang.Long.bitCount(r4)
            float r2 = (float) r2
            if (r3 != 0) goto L_0x01ed
            long r6 = r4 & r24
            r10 = 0
            int r3 = (r6 > r10 ? 1 : (r6 == r10 ? 0 : -1))
            r6 = 1056964608(0x3f000000, float:0.5)
            if (r3 == 0) goto L_0x01ce
            r3 = 0
            android.view.View r7 = r0.getChildAt(r3)
            android.view.ViewGroup$LayoutParams r7 = r7.getLayoutParams()
            android.support.v7.widget.ActionMenuView$LayoutParams r7 = (android.support.v7.widget.ActionMenuView.LayoutParams) r7
            boolean r7 = r7.preventEdgeOffset
            if (r7 != 0) goto L_0x01cf
            float r2 = r2 - r6
            goto L_0x01cf
        L_0x01ce:
            r3 = 0
        L_0x01cf:
            int r7 = r35 + -1
            r8 = 1
            int r10 = r8 << r7
            long r10 = (long) r10
            long r12 = r4 & r10
            r10 = 0
            int r8 = (r12 > r10 ? 1 : (r12 == r10 ? 0 : -1))
            if (r8 == 0) goto L_0x01ee
            android.view.View r7 = r0.getChildAt(r7)
            android.view.ViewGroup$LayoutParams r7 = r7.getLayoutParams()
            android.support.v7.widget.ActionMenuView$LayoutParams r7 = (android.support.v7.widget.ActionMenuView.LayoutParams) r7
            boolean r7 = r7.preventEdgeOffset
            if (r7 != 0) goto L_0x01ee
            float r2 = r2 - r6
            goto L_0x01ee
        L_0x01ed:
            r3 = 0
        L_0x01ee:
            r6 = 0
            int r6 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r6 <= 0) goto L_0x01f9
            int r14 = r14 * r9
            float r6 = (float) r14
            float r6 = r6 / r2
            int r8 = (int) r6
            goto L_0x01fa
        L_0x01f9:
            r8 = 0
        L_0x01fa:
            r11 = r28
            r2 = r35
            r6 = 0
        L_0x01ff:
            if (r6 >= r2) goto L_0x0259
            r7 = 1
            int r10 = r7 << r6
            long r12 = (long) r10
            long r14 = r4 & r12
            r12 = 0
            int r7 = (r14 > r12 ? 1 : (r14 == r12 ? 0 : -1))
            if (r7 != 0) goto L_0x0210
            r7 = 1
            r14 = 2
            goto L_0x0251
        L_0x0210:
            android.view.View r7 = r0.getChildAt(r6)
            android.view.ViewGroup$LayoutParams r10 = r7.getLayoutParams()
            android.support.v7.widget.ActionMenuView$LayoutParams r10 = (android.support.v7.widget.ActionMenuView.LayoutParams) r10
            boolean r7 = r7 instanceof android.support.v7.view.menu.ActionMenuItemView
            if (r7 == 0) goto L_0x0233
            r10.extraPixels = r8
            r7 = 1
            r10.a = r7
            if (r6 != 0) goto L_0x022f
            boolean r7 = r10.preventEdgeOffset
            if (r7 != 0) goto L_0x022f
            int r7 = -r8
            r14 = 2
            int r7 = r7 / r14
            r10.leftMargin = r7
            goto L_0x0230
        L_0x022f:
            r14 = 2
        L_0x0230:
            r7 = 1
        L_0x0231:
            r11 = 1
            goto L_0x0251
        L_0x0233:
            r14 = 2
            boolean r7 = r10.isOverflowButton
            if (r7 == 0) goto L_0x0242
            r10.extraPixels = r8
            r7 = 1
            r10.a = r7
            int r11 = -r8
            int r11 = r11 / r14
            r10.rightMargin = r11
            goto L_0x0231
        L_0x0242:
            r7 = 1
            if (r6 == 0) goto L_0x0249
            int r15 = r8 / 2
            r10.leftMargin = r15
        L_0x0249:
            int r15 = r2 + -1
            if (r6 == r15) goto L_0x0251
            int r15 = r8 / 2
            r10.rightMargin = r15
        L_0x0251:
            int r6 = r6 + 1
            goto L_0x01ff
        L_0x0254:
            r2 = r35
            r3 = 0
            r11 = r28
        L_0x0259:
            r4 = 1073741824(0x40000000, float:2.0)
            if (r11 == 0) goto L_0x0285
        L_0x025d:
            if (r3 >= r2) goto L_0x0285
            android.view.View r5 = r0.getChildAt(r3)
            android.view.ViewGroup$LayoutParams r6 = r5.getLayoutParams()
            android.support.v7.widget.ActionMenuView$LayoutParams r6 = (android.support.v7.widget.ActionMenuView.LayoutParams) r6
            boolean r7 = r6.a
            if (r7 != 0) goto L_0x0270
            r7 = r34
            goto L_0x0280
        L_0x0270:
            int r7 = r6.cellsUsed
            int r7 = r7 * r9
            int r6 = r6.extraPixels
            int r7 = r7 + r6
            int r6 = android.view.View.MeasureSpec.makeMeasureSpec(r7, r4)
            r7 = r34
            r5.measure(r6, r7)
        L_0x0280:
            int r3 = r3 + 1
            r34 = r7
            goto L_0x025d
        L_0x0285:
            if (r1 == r4) goto L_0x028c
            r2 = r23
            r1 = r33
            goto L_0x0290
        L_0x028c:
            r1 = r21
            r2 = r23
        L_0x0290:
            r0.setMeasuredDimension(r2, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.ActionMenuView.c(int, int):void");
    }

    static int a(View view, int i2, int i3, int i4, int i5) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i4) - i5, MeasureSpec.getMode(i4));
        ActionMenuItemView actionMenuItemView = view instanceof ActionMenuItemView ? (ActionMenuItemView) view : null;
        boolean z = false;
        boolean z2 = actionMenuItemView != null && actionMenuItemView.hasText();
        int i6 = 2;
        if (i3 <= 0 || (z2 && i3 < 2)) {
            i6 = 0;
        } else {
            view.measure(MeasureSpec.makeMeasureSpec(i3 * i2, Integer.MIN_VALUE), makeMeasureSpec);
            int measuredWidth = view.getMeasuredWidth();
            int i7 = measuredWidth / i2;
            if (measuredWidth % i2 != 0) {
                i7++;
            }
            if (!z2 || i7 >= 2) {
                i6 = i7;
            }
        }
        if (!layoutParams.isOverflowButton && z2) {
            z = true;
        }
        layoutParams.expandable = z;
        layoutParams.cellsUsed = i6;
        view.measure(MeasureSpec.makeMeasureSpec(i2 * i6, 1073741824), makeMeasureSpec);
        return i6;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i2, int i3, int i4, int i5) {
        int i6;
        int i7;
        int i8;
        int i9;
        if (!this.i) {
            super.onLayout(z, i2, i3, i4, i5);
            return;
        }
        int childCount = getChildCount();
        int i10 = (i5 - i3) / 2;
        int dividerWidth = getDividerWidth();
        int i11 = i4 - i2;
        int paddingRight = (i11 - getPaddingRight()) - getPaddingLeft();
        boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
        int i12 = paddingRight;
        int i13 = 0;
        int i14 = 0;
        for (int i15 = 0; i15 < childCount; i15++) {
            View childAt = getChildAt(i15);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.isOverflowButton) {
                    int measuredWidth = childAt.getMeasuredWidth();
                    if (hasSupportDividerBeforeChildAt(i15)) {
                        measuredWidth += dividerWidth;
                    }
                    int measuredHeight = childAt.getMeasuredHeight();
                    if (isLayoutRtl) {
                        i8 = getPaddingLeft() + layoutParams.leftMargin;
                        i9 = i8 + measuredWidth;
                    } else {
                        i9 = (getWidth() - getPaddingRight()) - layoutParams.rightMargin;
                        i8 = i9 - measuredWidth;
                    }
                    int i16 = i10 - (measuredHeight / 2);
                    childAt.layout(i8, i16, i9, measuredHeight + i16);
                    i12 -= measuredWidth;
                    i13 = 1;
                } else {
                    i12 -= (childAt.getMeasuredWidth() + layoutParams.leftMargin) + layoutParams.rightMargin;
                    boolean hasSupportDividerBeforeChildAt = hasSupportDividerBeforeChildAt(i15);
                    i14++;
                }
            }
        }
        if (childCount == 1 && i13 == 0) {
            View childAt2 = getChildAt(0);
            int measuredWidth2 = childAt2.getMeasuredWidth();
            int measuredHeight2 = childAt2.getMeasuredHeight();
            int i17 = (i11 / 2) - (measuredWidth2 / 2);
            int i18 = i10 - (measuredHeight2 / 2);
            childAt2.layout(i17, i18, measuredWidth2 + i17, measuredHeight2 + i18);
            return;
        }
        int i19 = i14 - (i13 ^ 1);
        if (i19 > 0) {
            i6 = i12 / i19;
            i7 = 0;
        } else {
            i7 = 0;
            i6 = 0;
        }
        int max = Math.max(i7, i6);
        if (isLayoutRtl) {
            int width = getWidth() - getPaddingRight();
            while (i7 < childCount) {
                View childAt3 = getChildAt(i7);
                LayoutParams layoutParams2 = (LayoutParams) childAt3.getLayoutParams();
                if (childAt3.getVisibility() != 8 && !layoutParams2.isOverflowButton) {
                    int i20 = width - layoutParams2.rightMargin;
                    int measuredWidth3 = childAt3.getMeasuredWidth();
                    int measuredHeight3 = childAt3.getMeasuredHeight();
                    int i21 = i10 - (measuredHeight3 / 2);
                    childAt3.layout(i20 - measuredWidth3, i21, i20, measuredHeight3 + i21);
                    width = i20 - ((measuredWidth3 + layoutParams2.leftMargin) + max);
                }
                i7++;
            }
        } else {
            int paddingLeft = getPaddingLeft();
            while (i7 < childCount) {
                View childAt4 = getChildAt(i7);
                LayoutParams layoutParams3 = (LayoutParams) childAt4.getLayoutParams();
                if (childAt4.getVisibility() != 8 && !layoutParams3.isOverflowButton) {
                    int i22 = paddingLeft + layoutParams3.leftMargin;
                    int measuredWidth4 = childAt4.getMeasuredWidth();
                    int measuredHeight4 = childAt4.getMeasuredHeight();
                    int i23 = i10 - (measuredHeight4 / 2);
                    childAt4.layout(i22, i23, i22 + measuredWidth4, measuredHeight4 + i23);
                    paddingLeft = i22 + measuredWidth4 + layoutParams3.rightMargin + max;
                }
                i7++;
            }
        }
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        dismissPopupMenus();
    }

    public void setOverflowIcon(@Nullable Drawable drawable) {
        getMenu();
        this.g.a(drawable);
    }

    @Nullable
    public Drawable getOverflowIcon() {
        getMenu();
        return this.g.a();
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public boolean isOverflowReserved() {
        return this.f;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setOverflowReserved(boolean z) {
        this.f = z;
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateDefaultLayoutParams() {
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.gravity = 16;
        return layoutParams;
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        if (layoutParams == null) {
            return generateDefaultLayoutParams();
        }
        LayoutParams layoutParams2 = layoutParams instanceof LayoutParams ? new LayoutParams((LayoutParams) layoutParams) : new LayoutParams(layoutParams);
        if (layoutParams2.gravity <= 0) {
            layoutParams2.gravity = 16;
        }
        return layoutParams2;
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return layoutParams != null && (layoutParams instanceof LayoutParams);
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public LayoutParams generateOverflowButtonLayoutParams() {
        LayoutParams generateDefaultLayoutParams = generateDefaultLayoutParams();
        generateDefaultLayoutParams.isOverflowButton = true;
        return generateDefaultLayoutParams;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public boolean invokeItem(MenuItemImpl menuItemImpl) {
        return this.c.performItemAction(menuItemImpl, 0);
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void initialize(MenuBuilder menuBuilder) {
        this.c = menuBuilder;
    }

    public Menu getMenu() {
        if (this.c == null) {
            Context context = getContext();
            this.c = new MenuBuilder(context);
            this.c.setCallback(new MenuBuilderCallback());
            this.g = new ActionMenuPresenter(context);
            this.g.a(true);
            this.g.setCallback(this.h != null ? this.h : new ActionMenuPresenterCallback());
            this.c.addMenuPresenter(this.g, this.d);
            this.g.a(this);
        }
        return this.c;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setMenuCallbacks(MenuPresenter.Callback callback, Callback callback2) {
        this.h = callback;
        this.a = callback2;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public MenuBuilder peekMenu() {
        return this.c;
    }

    public boolean showOverflowMenu() {
        return this.g != null && this.g.b();
    }

    public boolean hideOverflowMenu() {
        return this.g != null && this.g.c();
    }

    public boolean isOverflowMenuShowing() {
        return this.g != null && this.g.f();
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public boolean isOverflowMenuShowPending() {
        return this.g != null && this.g.g();
    }

    public void dismissPopupMenus() {
        if (this.g != null) {
            this.g.d();
        }
    }

    /* access modifiers changed from: protected */
    @RestrictTo({Scope.LIBRARY_GROUP})
    public boolean hasSupportDividerBeforeChildAt(int i2) {
        boolean z = false;
        if (i2 == 0) {
            return false;
        }
        View childAt = getChildAt(i2 - 1);
        View childAt2 = getChildAt(i2);
        if (i2 < getChildCount() && (childAt instanceof ActionMenuChildView)) {
            z = false | ((ActionMenuChildView) childAt).needsDividerAfter();
        }
        if (i2 > 0 && (childAt2 instanceof ActionMenuChildView)) {
            z |= ((ActionMenuChildView) childAt2).needsDividerBefore();
        }
        return z;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setExpandedActionViewsExclusive(boolean z) {
        this.g.b(z);
    }
}
