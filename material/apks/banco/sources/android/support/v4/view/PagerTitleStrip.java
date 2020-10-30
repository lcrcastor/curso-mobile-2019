package android.support.v4.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager.DecorView;
import android.support.v4.view.ViewPager.OnAdapterChangeListener;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.TextViewCompat;
import android.text.TextUtils.TruncateAt;
import android.text.method.SingleLineTransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.lang.ref.WeakReference;
import java.util.Locale;

@DecorView
public class PagerTitleStrip extends ViewGroup {
    private static final int[] n = {16842804, 16842901, 16842904, 16842927};
    private static final int[] o = {16843660};
    ViewPager a;
    TextView b;
    TextView c;
    TextView d;
    float e;
    int f;
    private int g;
    private int h;
    private int i;
    private boolean j;
    private boolean k;
    private final PageListener l;
    private WeakReference<PagerAdapter> m;
    private int p;

    class PageListener extends DataSetObserver implements OnAdapterChangeListener, OnPageChangeListener {
        private int b;

        PageListener() {
        }

        public void onPageScrolled(int i, float f, int i2) {
            if (f > 0.5f) {
                i++;
            }
            PagerTitleStrip.this.a(i, f, false);
        }

        public void onPageSelected(int i) {
            if (this.b == 0) {
                PagerTitleStrip.this.a(PagerTitleStrip.this.a.getCurrentItem(), PagerTitleStrip.this.a.getAdapter());
                float f = PagerTitleStrip.this.e;
                float f2 = BitmapDescriptorFactory.HUE_RED;
                if (f >= BitmapDescriptorFactory.HUE_RED) {
                    f2 = PagerTitleStrip.this.e;
                }
                PagerTitleStrip.this.a(PagerTitleStrip.this.a.getCurrentItem(), f2, true);
            }
        }

        public void onPageScrollStateChanged(int i) {
            this.b = i;
        }

        public void onAdapterChanged(ViewPager viewPager, PagerAdapter pagerAdapter, PagerAdapter pagerAdapter2) {
            PagerTitleStrip.this.a(pagerAdapter, pagerAdapter2);
        }

        public void onChanged() {
            PagerTitleStrip.this.a(PagerTitleStrip.this.a.getCurrentItem(), PagerTitleStrip.this.a.getAdapter());
            float f = PagerTitleStrip.this.e;
            float f2 = BitmapDescriptorFactory.HUE_RED;
            if (f >= BitmapDescriptorFactory.HUE_RED) {
                f2 = PagerTitleStrip.this.e;
            }
            PagerTitleStrip.this.a(PagerTitleStrip.this.a.getCurrentItem(), f2, true);
        }
    }

    static class SingleLineAllCapsTransform extends SingleLineTransformationMethod {
        private Locale a;

        SingleLineAllCapsTransform(Context context) {
            this.a = context.getResources().getConfiguration().locale;
        }

        public CharSequence getTransformation(CharSequence charSequence, View view) {
            CharSequence transformation = super.getTransformation(charSequence, view);
            if (transformation != null) {
                return transformation.toString().toUpperCase(this.a);
            }
            return null;
        }
    }

    private static void setSingleLineAllCaps(TextView textView) {
        textView.setTransformationMethod(new SingleLineAllCapsTransform(textView.getContext()));
    }

    public PagerTitleStrip(@NonNull Context context) {
        this(context, null);
    }

    public PagerTitleStrip(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.g = -1;
        this.e = -1.0f;
        this.l = new PageListener();
        TextView textView = new TextView(context);
        this.b = textView;
        addView(textView);
        TextView textView2 = new TextView(context);
        this.c = textView2;
        addView(textView2);
        TextView textView3 = new TextView(context);
        this.d = textView3;
        addView(textView3);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, n);
        boolean z = false;
        int resourceId = obtainStyledAttributes.getResourceId(0, 0);
        if (resourceId != 0) {
            TextViewCompat.setTextAppearance(this.b, resourceId);
            TextViewCompat.setTextAppearance(this.c, resourceId);
            TextViewCompat.setTextAppearance(this.d, resourceId);
        }
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(1, 0);
        if (dimensionPixelSize != 0) {
            setTextSize(0, (float) dimensionPixelSize);
        }
        if (obtainStyledAttributes.hasValue(2)) {
            int color = obtainStyledAttributes.getColor(2, 0);
            this.b.setTextColor(color);
            this.c.setTextColor(color);
            this.d.setTextColor(color);
        }
        this.i = obtainStyledAttributes.getInteger(3, 80);
        obtainStyledAttributes.recycle();
        this.f = this.c.getTextColors().getDefaultColor();
        setNonPrimaryAlpha(0.6f);
        this.b.setEllipsize(TruncateAt.END);
        this.c.setEllipsize(TruncateAt.END);
        this.d.setEllipsize(TruncateAt.END);
        if (resourceId != 0) {
            TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(resourceId, o);
            z = obtainStyledAttributes2.getBoolean(0, false);
            obtainStyledAttributes2.recycle();
        }
        if (z) {
            setSingleLineAllCaps(this.b);
            setSingleLineAllCaps(this.c);
            setSingleLineAllCaps(this.d);
        } else {
            this.b.setSingleLine();
            this.c.setSingleLine();
            this.d.setSingleLine();
        }
        this.h = (int) (context.getResources().getDisplayMetrics().density * 16.0f);
    }

    public void setTextSpacing(int i2) {
        this.h = i2;
        requestLayout();
    }

    public int getTextSpacing() {
        return this.h;
    }

    public void setNonPrimaryAlpha(@FloatRange(from = 0.0d, to = 1.0d) float f2) {
        this.p = ((int) (f2 * 255.0f)) & 255;
        int i2 = (this.p << 24) | (this.f & ViewCompat.MEASURED_SIZE_MASK);
        this.b.setTextColor(i2);
        this.d.setTextColor(i2);
    }

    public void setTextColor(@ColorInt int i2) {
        this.f = i2;
        this.c.setTextColor(i2);
        int i3 = (this.p << 24) | (this.f & ViewCompat.MEASURED_SIZE_MASK);
        this.b.setTextColor(i3);
        this.d.setTextColor(i3);
    }

    public void setTextSize(int i2, float f2) {
        this.b.setTextSize(i2, f2);
        this.c.setTextSize(i2, f2);
        this.d.setTextSize(i2, f2);
    }

    public void setGravity(int i2) {
        this.i = i2;
        requestLayout();
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewParent parent = getParent();
        if (!(parent instanceof ViewPager)) {
            throw new IllegalStateException("PagerTitleStrip must be a direct child of a ViewPager.");
        }
        ViewPager viewPager = (ViewPager) parent;
        PagerAdapter adapter = viewPager.getAdapter();
        viewPager.a((OnPageChangeListener) this.l);
        viewPager.addOnAdapterChangeListener(this.l);
        this.a = viewPager;
        a(this.m != null ? (PagerAdapter) this.m.get() : null, adapter);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.a != null) {
            a(this.a.getAdapter(), (PagerAdapter) null);
            this.a.a((OnPageChangeListener) null);
            this.a.removeOnAdapterChangeListener(this.l);
            this.a = null;
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2, PagerAdapter pagerAdapter) {
        int count = pagerAdapter != null ? pagerAdapter.getCount() : 0;
        this.j = true;
        CharSequence charSequence = null;
        this.b.setText((i2 < 1 || pagerAdapter == null) ? null : pagerAdapter.getPageTitle(i2 - 1));
        this.c.setText((pagerAdapter == null || i2 >= count) ? null : pagerAdapter.getPageTitle(i2));
        int i3 = i2 + 1;
        if (i3 < count && pagerAdapter != null) {
            charSequence = pagerAdapter.getPageTitle(i3);
        }
        this.d.setText(charSequence);
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(Math.max(0, (int) (((float) ((getWidth() - getPaddingLeft()) - getPaddingRight())) * 0.8f)), Integer.MIN_VALUE);
        int makeMeasureSpec2 = MeasureSpec.makeMeasureSpec(Math.max(0, (getHeight() - getPaddingTop()) - getPaddingBottom()), Integer.MIN_VALUE);
        this.b.measure(makeMeasureSpec, makeMeasureSpec2);
        this.c.measure(makeMeasureSpec, makeMeasureSpec2);
        this.d.measure(makeMeasureSpec, makeMeasureSpec2);
        this.g = i2;
        if (!this.k) {
            a(i2, this.e, false);
        }
        this.j = false;
    }

    public void requestLayout() {
        if (!this.j) {
            super.requestLayout();
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(PagerAdapter pagerAdapter, PagerAdapter pagerAdapter2) {
        if (pagerAdapter != null) {
            pagerAdapter.unregisterDataSetObserver(this.l);
            this.m = null;
        }
        if (pagerAdapter2 != null) {
            pagerAdapter2.registerDataSetObserver(this.l);
            this.m = new WeakReference<>(pagerAdapter2);
        }
        if (this.a != null) {
            this.g = -1;
            this.e = -1.0f;
            a(this.a.getCurrentItem(), pagerAdapter2);
            requestLayout();
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2, float f2, boolean z) {
        int i3;
        int i4;
        int i5;
        int i6 = i2;
        float f3 = f2;
        if (i6 != this.g) {
            a(i6, this.a.getAdapter());
        } else if (!z && f3 == this.e) {
            return;
        }
        this.k = true;
        int measuredWidth = this.b.getMeasuredWidth();
        int measuredWidth2 = this.c.getMeasuredWidth();
        int measuredWidth3 = this.d.getMeasuredWidth();
        int i7 = measuredWidth2 / 2;
        int width = getWidth();
        int height = getHeight();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int i8 = paddingRight + i7;
        int i9 = (width - (paddingLeft + i7)) - i8;
        float f4 = 0.5f + f3;
        if (f4 > 1.0f) {
            f4 -= 1.0f;
        }
        int i10 = ((width - i8) - ((int) (((float) i9) * f4))) - i7;
        int i11 = measuredWidth2 + i10;
        int baseline = this.b.getBaseline();
        int baseline2 = this.c.getBaseline();
        int baseline3 = this.d.getBaseline();
        int max = Math.max(Math.max(baseline, baseline2), baseline3);
        int i12 = max - baseline;
        int i13 = max - baseline2;
        int i14 = max - baseline3;
        int i15 = measuredWidth3;
        int measuredHeight = this.d.getMeasuredHeight() + i14;
        int max2 = Math.max(Math.max(this.b.getMeasuredHeight() + i12, this.c.getMeasuredHeight() + i13), measuredHeight);
        int i16 = this.i & 112;
        if (i16 == 16) {
            int i17 = (((height - paddingTop) - paddingBottom) - max2) / 2;
            i5 = i12 + i17;
            i3 = i13 + i17;
            i4 = i17 + i14;
        } else if (i16 != 80) {
            i5 = i12 + paddingTop;
            i3 = i13 + paddingTop;
            i4 = paddingTop + i14;
        } else {
            int i18 = (height - paddingBottom) - max2;
            i5 = i12 + i18;
            i3 = i13 + i18;
            i4 = i18 + i14;
        }
        this.c.layout(i10, i3, i11, this.c.getMeasuredHeight() + i3);
        int min = Math.min(paddingLeft, (i10 - this.h) - measuredWidth);
        this.b.layout(min, i5, measuredWidth + min, this.b.getMeasuredHeight() + i5);
        int max3 = Math.max((width - paddingRight) - i15, i11 + this.h);
        this.d.layout(max3, i4, max3 + i15, this.d.getMeasuredHeight() + i4);
        this.e = f2;
        this.k = false;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        int i4;
        if (MeasureSpec.getMode(i2) != 1073741824) {
            throw new IllegalStateException("Must measure with an exact width");
        }
        int paddingTop = getPaddingTop() + getPaddingBottom();
        int childMeasureSpec = getChildMeasureSpec(i3, paddingTop, -2);
        int size = MeasureSpec.getSize(i2);
        int childMeasureSpec2 = getChildMeasureSpec(i2, (int) (((float) size) * 0.2f), -2);
        this.b.measure(childMeasureSpec2, childMeasureSpec);
        this.c.measure(childMeasureSpec2, childMeasureSpec);
        this.d.measure(childMeasureSpec2, childMeasureSpec);
        if (MeasureSpec.getMode(i3) == 1073741824) {
            i4 = MeasureSpec.getSize(i3);
        } else {
            i4 = Math.max(getMinHeight(), this.c.getMeasuredHeight() + paddingTop);
        }
        setMeasuredDimension(size, View.resolveSizeAndState(i4, i3, this.c.getMeasuredState() << 16));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i2, int i3, int i4, int i5) {
        if (this.a != null) {
            float f2 = this.e;
            float f3 = BitmapDescriptorFactory.HUE_RED;
            if (f2 >= BitmapDescriptorFactory.HUE_RED) {
                f3 = this.e;
            }
            a(this.g, f3, true);
        }
    }

    /* access modifiers changed from: 0000 */
    public int getMinHeight() {
        Drawable background = getBackground();
        if (background != null) {
            return background.getIntrinsicHeight();
        }
        return 0;
    }
}
