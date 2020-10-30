package android.support.v7.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.FrameLayout;

@RestrictTo({Scope.LIBRARY})
public class ContentFrameLayout extends FrameLayout {
    private TypedValue a;
    private TypedValue b;
    private TypedValue c;
    private TypedValue d;
    private TypedValue e;
    private TypedValue f;
    private final Rect g;
    private OnAttachListener h;

    public interface OnAttachListener {
        void onAttachedFromWindow();

        void onDetachedFromWindow();
    }

    public ContentFrameLayout(Context context) {
        this(context, null);
    }

    public ContentFrameLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ContentFrameLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.g = new Rect();
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void dispatchFitSystemWindows(Rect rect) {
        fitSystemWindows(rect);
    }

    public void setAttachListener(OnAttachListener onAttachListener) {
        this.h = onAttachListener;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setDecorPadding(int i, int i2, int i3, int i4) {
        this.g.set(i, i2, i3, i4);
        if (ViewCompat.isLaidOut(this)) {
            requestLayout();
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x006e  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00bf  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00c2  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00ce  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00d4  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00e7  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00f3  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x00fb  */
    /* JADX WARNING: Removed duplicated region for block: B:62:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onMeasure(int r14, int r15) {
        /*
            r13 = this;
            android.content.Context r0 = r13.getContext()
            android.content.res.Resources r0 = r0.getResources()
            android.util.DisplayMetrics r0 = r0.getDisplayMetrics()
            int r1 = r0.widthPixels
            int r2 = r0.heightPixels
            r3 = 1
            r4 = 0
            if (r1 >= r2) goto L_0x0016
            r1 = 1
            goto L_0x0017
        L_0x0016:
            r1 = 0
        L_0x0017:
            int r2 = android.view.View.MeasureSpec.getMode(r14)
            int r5 = android.view.View.MeasureSpec.getMode(r15)
            r6 = 6
            r7 = 5
            r8 = -2147483648(0xffffffff80000000, float:-0.0)
            r9 = 1073741824(0x40000000, float:2.0)
            if (r2 != r8) goto L_0x006a
            if (r1 == 0) goto L_0x002c
            android.util.TypedValue r10 = r13.d
            goto L_0x002e
        L_0x002c:
            android.util.TypedValue r10 = r13.c
        L_0x002e:
            if (r10 == 0) goto L_0x006a
            int r11 = r10.type
            if (r11 == 0) goto L_0x006a
            int r11 = r10.type
            if (r11 != r7) goto L_0x003e
            float r10 = r10.getDimension(r0)
            int r10 = (int) r10
            goto L_0x004f
        L_0x003e:
            int r11 = r10.type
            if (r11 != r6) goto L_0x004e
            int r11 = r0.widthPixels
            float r11 = (float) r11
            int r12 = r0.widthPixels
            float r12 = (float) r12
            float r10 = r10.getFraction(r11, r12)
            int r10 = (int) r10
            goto L_0x004f
        L_0x004e:
            r10 = 0
        L_0x004f:
            if (r10 <= 0) goto L_0x006a
            android.graphics.Rect r11 = r13.g
            int r11 = r11.left
            android.graphics.Rect r12 = r13.g
            int r12 = r12.right
            int r11 = r11 + r12
            int r10 = r10 - r11
            int r14 = android.view.View.MeasureSpec.getSize(r14)
            int r14 = java.lang.Math.min(r10, r14)
            int r14 = android.view.View.MeasureSpec.makeMeasureSpec(r14, r9)
            r10 = r14
            r14 = 1
            goto L_0x006c
        L_0x006a:
            r10 = r14
            r14 = 0
        L_0x006c:
            if (r5 != r8) goto L_0x00ae
            if (r1 == 0) goto L_0x0073
            android.util.TypedValue r5 = r13.e
            goto L_0x0075
        L_0x0073:
            android.util.TypedValue r5 = r13.f
        L_0x0075:
            if (r5 == 0) goto L_0x00ae
            int r11 = r5.type
            if (r11 == 0) goto L_0x00ae
            int r11 = r5.type
            if (r11 != r7) goto L_0x0085
            float r5 = r5.getDimension(r0)
            int r5 = (int) r5
            goto L_0x0096
        L_0x0085:
            int r11 = r5.type
            if (r11 != r6) goto L_0x0095
            int r11 = r0.heightPixels
            float r11 = (float) r11
            int r12 = r0.heightPixels
            float r12 = (float) r12
            float r5 = r5.getFraction(r11, r12)
            int r5 = (int) r5
            goto L_0x0096
        L_0x0095:
            r5 = 0
        L_0x0096:
            if (r5 <= 0) goto L_0x00ae
            android.graphics.Rect r11 = r13.g
            int r11 = r11.top
            android.graphics.Rect r12 = r13.g
            int r12 = r12.bottom
            int r11 = r11 + r12
            int r5 = r5 - r11
            int r15 = android.view.View.MeasureSpec.getSize(r15)
            int r15 = java.lang.Math.min(r5, r15)
            int r15 = android.view.View.MeasureSpec.makeMeasureSpec(r15, r9)
        L_0x00ae:
            super.onMeasure(r10, r15)
            int r5 = r13.getMeasuredWidth()
            int r10 = android.view.View.MeasureSpec.makeMeasureSpec(r5, r9)
            if (r14 != 0) goto L_0x00f8
            if (r2 != r8) goto L_0x00f8
            if (r1 == 0) goto L_0x00c2
            android.util.TypedValue r14 = r13.b
            goto L_0x00c4
        L_0x00c2:
            android.util.TypedValue r14 = r13.a
        L_0x00c4:
            if (r14 == 0) goto L_0x00f8
            int r1 = r14.type
            if (r1 == 0) goto L_0x00f8
            int r1 = r14.type
            if (r1 != r7) goto L_0x00d4
            float r14 = r14.getDimension(r0)
            int r14 = (int) r14
            goto L_0x00e5
        L_0x00d4:
            int r1 = r14.type
            if (r1 != r6) goto L_0x00e4
            int r1 = r0.widthPixels
            float r1 = (float) r1
            int r0 = r0.widthPixels
            float r0 = (float) r0
            float r14 = r14.getFraction(r1, r0)
            int r14 = (int) r14
            goto L_0x00e5
        L_0x00e4:
            r14 = 0
        L_0x00e5:
            if (r14 <= 0) goto L_0x00f1
            android.graphics.Rect r0 = r13.g
            int r0 = r0.left
            android.graphics.Rect r1 = r13.g
            int r1 = r1.right
            int r0 = r0 + r1
            int r14 = r14 - r0
        L_0x00f1:
            if (r5 >= r14) goto L_0x00f8
            int r10 = android.view.View.MeasureSpec.makeMeasureSpec(r14, r9)
            goto L_0x00f9
        L_0x00f8:
            r3 = 0
        L_0x00f9:
            if (r3 == 0) goto L_0x00fe
            super.onMeasure(r10, r15)
        L_0x00fe:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.ContentFrameLayout.onMeasure(int, int):void");
    }

    public TypedValue getMinWidthMajor() {
        if (this.a == null) {
            this.a = new TypedValue();
        }
        return this.a;
    }

    public TypedValue getMinWidthMinor() {
        if (this.b == null) {
            this.b = new TypedValue();
        }
        return this.b;
    }

    public TypedValue getFixedWidthMajor() {
        if (this.c == null) {
            this.c = new TypedValue();
        }
        return this.c;
    }

    public TypedValue getFixedWidthMinor() {
        if (this.d == null) {
            this.d = new TypedValue();
        }
        return this.d;
    }

    public TypedValue getFixedHeightMajor() {
        if (this.e == null) {
            this.e = new TypedValue();
        }
        return this.e;
    }

    public TypedValue getFixedHeightMinor() {
        if (this.f == null) {
            this.f = new TypedValue();
        }
        return this.f;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.h != null) {
            this.h.onAttachedFromWindow();
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.h != null) {
            this.h.onDetachedFromWindow();
        }
    }
}
