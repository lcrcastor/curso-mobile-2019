package android.support.design.widget;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.StringRes;
import android.support.design.R;
import android.support.v4.util.Pools.Pool;
import android.support.v4.util.Pools.SimplePool;
import android.support.v4.util.Pools.SynchronizedPool;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PointerIconCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.DecorView;
import android.support.v4.view.ViewPager.OnAdapterChangeListener;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.TooltipCompat;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import cz.msebera.android.httpclient.HttpStatus;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

@DecorView
public class TabLayout extends HorizontalScrollView {
    public static final int GRAVITY_CENTER = 1;
    public static final int GRAVITY_FILL = 0;
    public static final int MODE_FIXED = 1;
    public static final int MODE_SCROLLABLE = 0;
    private static final Pool<Tab> n = new SynchronizedPool(16);
    private DataSetObserver A;
    private TabLayoutOnPageChangeListener B;
    private AdapterChangeListener C;
    private boolean D;
    private final Pool<TabView> E;
    int a;
    int b;
    int c;
    int d;
    int e;
    ColorStateList f;
    float g;
    float h;
    final int i;
    int j;
    int k;
    int l;
    ViewPager m;
    private final ArrayList<Tab> o;
    private Tab p;
    private final SlidingTabStrip q;
    private final int r;
    private final int s;
    private final int t;
    private int u;
    private OnTabSelectedListener v;
    private final ArrayList<OnTabSelectedListener> w;
    private OnTabSelectedListener x;
    private ValueAnimator y;
    private PagerAdapter z;

    class AdapterChangeListener implements OnAdapterChangeListener {
        private boolean b;

        AdapterChangeListener() {
        }

        public void onAdapterChanged(@NonNull ViewPager viewPager, @Nullable PagerAdapter pagerAdapter, @Nullable PagerAdapter pagerAdapter2) {
            if (TabLayout.this.m == viewPager) {
                TabLayout.this.a(pagerAdapter2, this.b);
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(boolean z) {
            this.b = z;
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    public interface OnTabSelectedListener {
        void onTabReselected(Tab tab);

        void onTabSelected(Tab tab);

        void onTabUnselected(Tab tab);
    }

    class PagerAdapterObserver extends DataSetObserver {
        PagerAdapterObserver() {
        }

        public void onChanged() {
            TabLayout.this.a();
        }

        public void onInvalidated() {
            TabLayout.this.a();
        }
    }

    class SlidingTabStrip extends LinearLayout {
        int a = -1;
        float b;
        private int d;
        private final Paint e;
        private int f = -1;
        private int g = -1;
        private int h = -1;
        private ValueAnimator i;

        SlidingTabStrip(Context context) {
            super(context);
            setWillNotDraw(false);
            this.e = new Paint();
        }

        /* access modifiers changed from: 0000 */
        public void a(int i2) {
            if (this.e.getColor() != i2) {
                this.e.setColor(i2);
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }

        /* access modifiers changed from: 0000 */
        public void b(int i2) {
            if (this.d != i2) {
                this.d = i2;
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean a() {
            int childCount = getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                if (getChildAt(i2).getWidth() <= 0) {
                    return true;
                }
            }
            return false;
        }

        /* access modifiers changed from: 0000 */
        public void a(int i2, float f2) {
            if (this.i != null && this.i.isRunning()) {
                this.i.cancel();
            }
            this.a = i2;
            this.b = f2;
            c();
        }

        /* access modifiers changed from: 0000 */
        public float b() {
            return ((float) this.a) + this.b;
        }

        public void onRtlPropertiesChanged(int i2) {
            super.onRtlPropertiesChanged(i2);
            if (VERSION.SDK_INT < 23 && this.f != i2) {
                requestLayout();
                this.f = i2;
            }
        }

        /* access modifiers changed from: protected */
        public void onMeasure(int i2, int i3) {
            super.onMeasure(i2, i3);
            if (MeasureSpec.getMode(i2) == 1073741824) {
                boolean z = true;
                if (TabLayout.this.l == 1 && TabLayout.this.k == 1) {
                    int childCount = getChildCount();
                    int i4 = 0;
                    for (int i5 = 0; i5 < childCount; i5++) {
                        View childAt = getChildAt(i5);
                        if (childAt.getVisibility() == 0) {
                            i4 = Math.max(i4, childAt.getMeasuredWidth());
                        }
                    }
                    if (i4 > 0) {
                        if (i4 * childCount <= getMeasuredWidth() - (TabLayout.this.a(16) * 2)) {
                            boolean z2 = false;
                            for (int i6 = 0; i6 < childCount; i6++) {
                                LayoutParams layoutParams = (LayoutParams) getChildAt(i6).getLayoutParams();
                                if (layoutParams.width != i4 || layoutParams.weight != BitmapDescriptorFactory.HUE_RED) {
                                    layoutParams.width = i4;
                                    layoutParams.weight = BitmapDescriptorFactory.HUE_RED;
                                    z2 = true;
                                }
                            }
                            z = z2;
                        } else {
                            TabLayout.this.k = 0;
                            TabLayout.this.a(false);
                        }
                        if (z) {
                            super.onMeasure(i2, i3);
                        }
                    }
                }
            }
        }

        /* access modifiers changed from: protected */
        public void onLayout(boolean z, int i2, int i3, int i4, int i5) {
            super.onLayout(z, i2, i3, i4, i5);
            if (this.i == null || !this.i.isRunning()) {
                c();
                return;
            }
            this.i.cancel();
            b(this.a, Math.round((1.0f - this.i.getAnimatedFraction()) * ((float) this.i.getDuration())));
        }

        private void c() {
            int i2;
            int i3;
            View childAt = getChildAt(this.a);
            if (childAt == null || childAt.getWidth() <= 0) {
                i2 = -1;
                i3 = -1;
            } else {
                i2 = childAt.getLeft();
                i3 = childAt.getRight();
                if (this.b > BitmapDescriptorFactory.HUE_RED && this.a < getChildCount() - 1) {
                    View childAt2 = getChildAt(this.a + 1);
                    i2 = (int) ((this.b * ((float) childAt2.getLeft())) + ((1.0f - this.b) * ((float) i2)));
                    i3 = (int) ((this.b * ((float) childAt2.getRight())) + ((1.0f - this.b) * ((float) i3)));
                }
            }
            a(i2, i3);
        }

        /* access modifiers changed from: 0000 */
        public void a(int i2, int i3) {
            if (i2 != this.g || i3 != this.h) {
                this.g = i2;
                this.h = i3;
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }

        /* access modifiers changed from: 0000 */
        public void b(final int i2, int i3) {
            final int i4;
            final int i5;
            int i6;
            int i7;
            if (this.i != null && this.i.isRunning()) {
                this.i.cancel();
            }
            boolean z = ViewCompat.getLayoutDirection(this) == 1;
            View childAt = getChildAt(i2);
            if (childAt == null) {
                c();
                return;
            }
            final int left = childAt.getLeft();
            final int right = childAt.getRight();
            if (Math.abs(i2 - this.a) <= 1) {
                i5 = this.g;
                i4 = this.h;
            } else {
                int a2 = TabLayout.this.a(24);
                if (i2 < this.a) {
                    if (z) {
                        i6 = left - a2;
                    } else {
                        i7 = a2 + right;
                        i5 = i7;
                        i4 = i5;
                    }
                } else if (z) {
                    i7 = a2 + right;
                    i5 = i7;
                    i4 = i5;
                } else {
                    i6 = left - a2;
                }
                i5 = i6;
                i4 = i5;
            }
            if (!(i5 == left && i4 == right)) {
                ValueAnimator valueAnimator = new ValueAnimator();
                this.i = valueAnimator;
                valueAnimator.setInterpolator(AnimationUtils.b);
                valueAnimator.setDuration((long) i3);
                valueAnimator.setFloatValues(new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f});
                AnonymousClass1 r3 = new AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float animatedFraction = valueAnimator.getAnimatedFraction();
                        SlidingTabStrip.this.a(AnimationUtils.a(i5, left, animatedFraction), AnimationUtils.a(i4, right, animatedFraction));
                    }
                };
                valueAnimator.addUpdateListener(r3);
                valueAnimator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animator) {
                        SlidingTabStrip.this.a = i2;
                        SlidingTabStrip.this.b = BitmapDescriptorFactory.HUE_RED;
                    }
                });
                valueAnimator.start();
            }
        }

        public void draw(Canvas canvas) {
            super.draw(canvas);
            if (this.g >= 0 && this.h > this.g) {
                canvas.drawRect((float) this.g, (float) (getHeight() - this.d), (float) this.h, (float) getHeight(), this.e);
            }
        }
    }

    public static final class Tab {
        public static final int INVALID_POSITION = -1;
        TabLayout a;
        TabView b;
        private Object c;
        private Drawable d;
        private CharSequence e;
        private CharSequence f;
        private int g = -1;
        private View h;

        Tab() {
        }

        @Nullable
        public Object getTag() {
            return this.c;
        }

        @NonNull
        public Tab setTag(@Nullable Object obj) {
            this.c = obj;
            return this;
        }

        @Nullable
        public View getCustomView() {
            return this.h;
        }

        @NonNull
        public Tab setCustomView(@Nullable View view) {
            this.h = view;
            a();
            return this;
        }

        @NonNull
        public Tab setCustomView(@LayoutRes int i) {
            return setCustomView(LayoutInflater.from(this.b.getContext()).inflate(i, this.b, false));
        }

        @Nullable
        public Drawable getIcon() {
            return this.d;
        }

        public int getPosition() {
            return this.g;
        }

        /* access modifiers changed from: 0000 */
        public void a(int i) {
            this.g = i;
        }

        @Nullable
        public CharSequence getText() {
            return this.e;
        }

        @NonNull
        public Tab setIcon(@Nullable Drawable drawable) {
            this.d = drawable;
            a();
            return this;
        }

        @NonNull
        public Tab setIcon(@DrawableRes int i) {
            if (this.a != null) {
                return setIcon(AppCompatResources.getDrawable(this.a.getContext(), i));
            }
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }

        @NonNull
        public Tab setText(@Nullable CharSequence charSequence) {
            this.e = charSequence;
            a();
            return this;
        }

        @NonNull
        public Tab setText(@StringRes int i) {
            if (this.a != null) {
                return setText(this.a.getResources().getText(i));
            }
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }

        public void select() {
            if (this.a == null) {
                throw new IllegalArgumentException("Tab not attached to a TabLayout");
            }
            this.a.a(this);
        }

        public boolean isSelected() {
            if (this.a != null) {
                return this.a.getSelectedTabPosition() == this.g;
            }
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }

        @NonNull
        public Tab setContentDescription(@StringRes int i) {
            if (this.a != null) {
                return setContentDescription(this.a.getResources().getText(i));
            }
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }

        @NonNull
        public Tab setContentDescription(@Nullable CharSequence charSequence) {
            this.f = charSequence;
            a();
            return this;
        }

        @Nullable
        public CharSequence getContentDescription() {
            return this.f;
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (this.b != null) {
                this.b.b();
            }
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            this.a = null;
            this.b = null;
            this.c = null;
            this.d = null;
            this.e = null;
            this.f = null;
            this.g = -1;
            this.h = null;
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TabGravity {
    }

    public static class TabLayoutOnPageChangeListener implements OnPageChangeListener {
        private final WeakReference<TabLayout> a;
        private int b;
        private int c;

        public TabLayoutOnPageChangeListener(TabLayout tabLayout) {
            this.a = new WeakReference<>(tabLayout);
        }

        public void onPageScrollStateChanged(int i) {
            this.b = this.c;
            this.c = i;
        }

        public void onPageScrolled(int i, float f, int i2) {
            TabLayout tabLayout = (TabLayout) this.a.get();
            if (tabLayout != null) {
                boolean z = false;
                boolean z2 = this.c != 2 || this.b == 1;
                if (!(this.c == 2 && this.b == 0)) {
                    z = true;
                }
                tabLayout.a(i, f, z2, z);
            }
        }

        public void onPageSelected(int i) {
            TabLayout tabLayout = (TabLayout) this.a.get();
            if (tabLayout != null && tabLayout.getSelectedTabPosition() != i && i < tabLayout.getTabCount()) {
                tabLayout.a(tabLayout.getTabAt(i), this.c == 0 || (this.c == 2 && this.b == 0));
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            this.c = 0;
            this.b = 0;
        }
    }

    class TabView extends LinearLayout {
        private Tab b;
        private TextView c;
        private ImageView d;
        private View e;
        private TextView f;
        private ImageView g;
        private int h = 2;

        public TabView(Context context) {
            super(context);
            if (TabLayout.this.i != 0) {
                ViewCompat.setBackground(this, AppCompatResources.getDrawable(context, TabLayout.this.i));
            }
            ViewCompat.setPaddingRelative(this, TabLayout.this.a, TabLayout.this.b, TabLayout.this.c, TabLayout.this.d);
            setGravity(17);
            setOrientation(1);
            setClickable(true);
            ViewCompat.setPointerIcon(this, PointerIconCompat.getSystemIcon(getContext(), 1002));
        }

        public boolean performClick() {
            boolean performClick = super.performClick();
            if (this.b == null) {
                return performClick;
            }
            if (!performClick) {
                playSoundEffect(0);
            }
            this.b.select();
            return true;
        }

        public void setSelected(boolean z) {
            boolean z2 = isSelected() != z;
            super.setSelected(z);
            if (z2 && z && VERSION.SDK_INT < 16) {
                sendAccessibilityEvent(4);
            }
            if (this.c != null) {
                this.c.setSelected(z);
            }
            if (this.d != null) {
                this.d.setSelected(z);
            }
            if (this.e != null) {
                this.e.setSelected(z);
            }
        }

        public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(accessibilityEvent);
            accessibilityEvent.setClassName(android.support.v7.app.ActionBar.Tab.class.getName());
        }

        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            accessibilityNodeInfo.setClassName(android.support.v7.app.ActionBar.Tab.class.getName());
        }

        public void onMeasure(int i, int i2) {
            int size = MeasureSpec.getSize(i);
            int mode = MeasureSpec.getMode(i);
            int tabMaxWidth = TabLayout.this.getTabMaxWidth();
            if (tabMaxWidth > 0 && (mode == 0 || size > tabMaxWidth)) {
                i = MeasureSpec.makeMeasureSpec(TabLayout.this.j, Integer.MIN_VALUE);
            }
            super.onMeasure(i, i2);
            if (this.c != null) {
                getResources();
                float f2 = TabLayout.this.g;
                int i3 = this.h;
                boolean z = true;
                if (this.d != null && this.d.getVisibility() == 0) {
                    i3 = 1;
                } else if (this.c != null && this.c.getLineCount() > 1) {
                    f2 = TabLayout.this.h;
                }
                float textSize = this.c.getTextSize();
                int lineCount = this.c.getLineCount();
                int maxLines = TextViewCompat.getMaxLines(this.c);
                if (f2 != textSize || (maxLines >= 0 && i3 != maxLines)) {
                    if (TabLayout.this.l == 1 && f2 > textSize && lineCount == 1) {
                        Layout layout = this.c.getLayout();
                        if (layout == null || a(layout, 0, f2) > ((float) ((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight()))) {
                            z = false;
                        }
                    }
                    if (z) {
                        this.c.setTextSize(0, f2);
                        this.c.setMaxLines(i3);
                        super.onMeasure(i, i2);
                    }
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(@Nullable Tab tab) {
            if (tab != this.b) {
                this.b = tab;
                b();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            a(null);
            setSelected(false);
        }

        /* access modifiers changed from: 0000 */
        public final void b() {
            Tab tab = this.b;
            View customView = tab != null ? tab.getCustomView() : null;
            if (customView != null) {
                ViewParent parent = customView.getParent();
                if (parent != this) {
                    if (parent != null) {
                        ((ViewGroup) parent).removeView(customView);
                    }
                    addView(customView);
                }
                this.e = customView;
                if (this.c != null) {
                    this.c.setVisibility(8);
                }
                if (this.d != null) {
                    this.d.setVisibility(8);
                    this.d.setImageDrawable(null);
                }
                this.f = (TextView) customView.findViewById(16908308);
                if (this.f != null) {
                    this.h = TextViewCompat.getMaxLines(this.f);
                }
                this.g = (ImageView) customView.findViewById(16908294);
            } else {
                if (this.e != null) {
                    removeView(this.e);
                    this.e = null;
                }
                this.f = null;
                this.g = null;
            }
            boolean z = false;
            if (this.e == null) {
                if (this.d == null) {
                    ImageView imageView = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.design_layout_tab_icon, this, false);
                    addView(imageView, 0);
                    this.d = imageView;
                }
                if (this.c == null) {
                    TextView textView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.design_layout_tab_text, this, false);
                    addView(textView);
                    this.c = textView;
                    this.h = TextViewCompat.getMaxLines(this.c);
                }
                TextViewCompat.setTextAppearance(this.c, TabLayout.this.e);
                if (TabLayout.this.f != null) {
                    this.c.setTextColor(TabLayout.this.f);
                }
                a(this.c, this.d);
            } else if (!(this.f == null && this.g == null)) {
                a(this.f, this.g);
            }
            if (tab != null && tab.isSelected()) {
                z = true;
            }
            setSelected(z);
        }

        private void a(@Nullable TextView textView, @Nullable ImageView imageView) {
            CharSequence charSequence = null;
            Drawable icon = this.b != null ? this.b.getIcon() : null;
            CharSequence text = this.b != null ? this.b.getText() : null;
            CharSequence contentDescription = this.b != null ? this.b.getContentDescription() : null;
            int i = 0;
            if (imageView != null) {
                if (icon != null) {
                    imageView.setImageDrawable(icon);
                    imageView.setVisibility(0);
                    setVisibility(0);
                } else {
                    imageView.setVisibility(8);
                    imageView.setImageDrawable(null);
                }
                imageView.setContentDescription(contentDescription);
            }
            boolean z = !TextUtils.isEmpty(text);
            if (textView != null) {
                if (z) {
                    textView.setText(text);
                    textView.setVisibility(0);
                    setVisibility(0);
                } else {
                    textView.setVisibility(8);
                    textView.setText(null);
                }
                textView.setContentDescription(contentDescription);
            }
            if (imageView != null) {
                MarginLayoutParams marginLayoutParams = (MarginLayoutParams) imageView.getLayoutParams();
                if (z && imageView.getVisibility() == 0) {
                    i = TabLayout.this.a(8);
                }
                if (i != marginLayoutParams.bottomMargin) {
                    marginLayoutParams.bottomMargin = i;
                    imageView.requestLayout();
                }
            }
            if (!z) {
                charSequence = contentDescription;
            }
            TooltipCompat.setTooltipText(this, charSequence);
        }

        private float a(Layout layout, int i, float f2) {
            return layout.getLineWidth(i) * (f2 / layout.getPaint().getTextSize());
        }
    }

    public static class ViewPagerOnTabSelectedListener implements OnTabSelectedListener {
        private final ViewPager a;

        public void onTabReselected(Tab tab) {
        }

        public void onTabUnselected(Tab tab) {
        }

        public ViewPagerOnTabSelectedListener(ViewPager viewPager) {
            this.a = viewPager;
        }

        public void onTabSelected(Tab tab) {
            this.a.setCurrentItem(tab.getPosition());
        }
    }

    public TabLayout(Context context) {
        this(context, null);
    }

    public TabLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /* JADX INFO: finally extract failed */
    public TabLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.o = new ArrayList<>();
        this.j = SubsamplingScaleImageView.TILE_SIZE_AUTO;
        this.w = new ArrayList<>();
        this.E = new SimplePool(12);
        ThemeUtils.a(context);
        setHorizontalScrollBarEnabled(false);
        this.q = new SlidingTabStrip(context);
        super.addView(this.q, 0, new FrameLayout.LayoutParams(-2, -1));
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.TabLayout, i2, R.style.Widget_Design_TabLayout);
        this.q.b(obtainStyledAttributes.getDimensionPixelSize(R.styleable.TabLayout_tabIndicatorHeight, 0));
        this.q.a(obtainStyledAttributes.getColor(R.styleable.TabLayout_tabIndicatorColor, 0));
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.TabLayout_tabPadding, 0);
        this.d = dimensionPixelSize;
        this.c = dimensionPixelSize;
        this.b = dimensionPixelSize;
        this.a = dimensionPixelSize;
        this.a = obtainStyledAttributes.getDimensionPixelSize(R.styleable.TabLayout_tabPaddingStart, this.a);
        this.b = obtainStyledAttributes.getDimensionPixelSize(R.styleable.TabLayout_tabPaddingTop, this.b);
        this.c = obtainStyledAttributes.getDimensionPixelSize(R.styleable.TabLayout_tabPaddingEnd, this.c);
        this.d = obtainStyledAttributes.getDimensionPixelSize(R.styleable.TabLayout_tabPaddingBottom, this.d);
        this.e = obtainStyledAttributes.getResourceId(R.styleable.TabLayout_tabTextAppearance, R.style.TextAppearance_Design_Tab);
        TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(this.e, android.support.v7.appcompat.R.styleable.TextAppearance);
        try {
            this.g = (float) obtainStyledAttributes2.getDimensionPixelSize(android.support.v7.appcompat.R.styleable.TextAppearance_android_textSize, 0);
            this.f = obtainStyledAttributes2.getColorStateList(android.support.v7.appcompat.R.styleable.TextAppearance_android_textColor);
            obtainStyledAttributes2.recycle();
            if (obtainStyledAttributes.hasValue(R.styleable.TabLayout_tabTextColor)) {
                this.f = obtainStyledAttributes.getColorStateList(R.styleable.TabLayout_tabTextColor);
            }
            if (obtainStyledAttributes.hasValue(R.styleable.TabLayout_tabSelectedTextColor)) {
                this.f = a(this.f.getDefaultColor(), obtainStyledAttributes.getColor(R.styleable.TabLayout_tabSelectedTextColor, 0));
            }
            this.r = obtainStyledAttributes.getDimensionPixelSize(R.styleable.TabLayout_tabMinWidth, -1);
            this.s = obtainStyledAttributes.getDimensionPixelSize(R.styleable.TabLayout_tabMaxWidth, -1);
            this.i = obtainStyledAttributes.getResourceId(R.styleable.TabLayout_tabBackground, 0);
            this.u = obtainStyledAttributes.getDimensionPixelSize(R.styleable.TabLayout_tabContentStart, 0);
            this.l = obtainStyledAttributes.getInt(R.styleable.TabLayout_tabMode, 1);
            this.k = obtainStyledAttributes.getInt(R.styleable.TabLayout_tabGravity, 0);
            obtainStyledAttributes.recycle();
            Resources resources = getResources();
            this.h = (float) resources.getDimensionPixelSize(R.dimen.design_tab_text_size_2line);
            this.t = resources.getDimensionPixelSize(R.dimen.design_tab_scrollable_min_width);
            e();
        } catch (Throwable th) {
            obtainStyledAttributes2.recycle();
            throw th;
        }
    }

    public void setSelectedTabIndicatorColor(@ColorInt int i2) {
        this.q.a(i2);
    }

    public void setSelectedTabIndicatorHeight(int i2) {
        this.q.b(i2);
    }

    public void setScrollPosition(int i2, float f2, boolean z2) {
        a(i2, f2, z2, true);
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2, float f2, boolean z2, boolean z3) {
        int round = Math.round(((float) i2) + f2);
        if (round >= 0 && round < this.q.getChildCount()) {
            if (z3) {
                this.q.a(i2, f2);
            }
            if (this.y != null && this.y.isRunning()) {
                this.y.cancel();
            }
            scrollTo(a(i2, f2), 0);
            if (z2) {
                setSelectedTabView(round);
            }
        }
    }

    private float getScrollPosition() {
        return this.q.b();
    }

    public void addTab(@NonNull Tab tab) {
        addTab(tab, this.o.isEmpty());
    }

    public void addTab(@NonNull Tab tab, int i2) {
        addTab(tab, i2, this.o.isEmpty());
    }

    public void addTab(@NonNull Tab tab, boolean z2) {
        addTab(tab, this.o.size(), z2);
    }

    public void addTab(@NonNull Tab tab, int i2, boolean z2) {
        if (tab.a != this) {
            throw new IllegalArgumentException("Tab belongs to a different TabLayout.");
        }
        a(tab, i2);
        c(tab);
        if (z2) {
            tab.select();
        }
    }

    private void a(@NonNull TabItem tabItem) {
        Tab newTab = newTab();
        if (tabItem.a != null) {
            newTab.setText(tabItem.a);
        }
        if (tabItem.b != null) {
            newTab.setIcon(tabItem.b);
        }
        if (tabItem.c != 0) {
            newTab.setCustomView(tabItem.c);
        }
        if (!TextUtils.isEmpty(tabItem.getContentDescription())) {
            newTab.setContentDescription(tabItem.getContentDescription());
        }
        addTab(newTab);
    }

    @Deprecated
    public void setOnTabSelectedListener(@Nullable OnTabSelectedListener onTabSelectedListener) {
        if (this.v != null) {
            removeOnTabSelectedListener(this.v);
        }
        this.v = onTabSelectedListener;
        if (onTabSelectedListener != null) {
            addOnTabSelectedListener(onTabSelectedListener);
        }
    }

    public void addOnTabSelectedListener(@NonNull OnTabSelectedListener onTabSelectedListener) {
        if (!this.w.contains(onTabSelectedListener)) {
            this.w.add(onTabSelectedListener);
        }
    }

    public void removeOnTabSelectedListener(@NonNull OnTabSelectedListener onTabSelectedListener) {
        this.w.remove(onTabSelectedListener);
    }

    public void clearOnTabSelectedListeners() {
        this.w.clear();
    }

    @NonNull
    public Tab newTab() {
        Tab tab = (Tab) n.acquire();
        if (tab == null) {
            tab = new Tab();
        }
        tab.a = this;
        tab.b = b(tab);
        return tab;
    }

    public int getTabCount() {
        return this.o.size();
    }

    @Nullable
    public Tab getTabAt(int i2) {
        if (i2 < 0 || i2 >= getTabCount()) {
            return null;
        }
        return (Tab) this.o.get(i2);
    }

    public int getSelectedTabPosition() {
        if (this.p != null) {
            return this.p.getPosition();
        }
        return -1;
    }

    public void removeTab(Tab tab) {
        if (tab.a != this) {
            throw new IllegalArgumentException("Tab does not belong to this TabLayout.");
        }
        removeTabAt(tab.getPosition());
    }

    public void removeTabAt(int i2) {
        int position = this.p != null ? this.p.getPosition() : 0;
        b(i2);
        Tab tab = (Tab) this.o.remove(i2);
        if (tab != null) {
            tab.b();
            n.release(tab);
        }
        int size = this.o.size();
        for (int i3 = i2; i3 < size; i3++) {
            ((Tab) this.o.get(i3)).a(i3);
        }
        if (position == i2) {
            a(this.o.isEmpty() ? null : (Tab) this.o.get(Math.max(0, i2 - 1)));
        }
    }

    public void removeAllTabs() {
        for (int childCount = this.q.getChildCount() - 1; childCount >= 0; childCount--) {
            b(childCount);
        }
        Iterator it = this.o.iterator();
        while (it.hasNext()) {
            Tab tab = (Tab) it.next();
            it.remove();
            tab.b();
            n.release(tab);
        }
        this.p = null;
    }

    public void setTabMode(int i2) {
        if (i2 != this.l) {
            this.l = i2;
            e();
        }
    }

    public int getTabMode() {
        return this.l;
    }

    public void setTabGravity(int i2) {
        if (this.k != i2) {
            this.k = i2;
            e();
        }
    }

    public int getTabGravity() {
        return this.k;
    }

    public void setTabTextColors(@Nullable ColorStateList colorStateList) {
        if (this.f != colorStateList) {
            this.f = colorStateList;
            b();
        }
    }

    @Nullable
    public ColorStateList getTabTextColors() {
        return this.f;
    }

    public void setTabTextColors(int i2, int i3) {
        setTabTextColors(a(i2, i3));
    }

    public void setupWithViewPager(@Nullable ViewPager viewPager) {
        setupWithViewPager(viewPager, true);
    }

    public void setupWithViewPager(@Nullable ViewPager viewPager, boolean z2) {
        a(viewPager, z2, false);
    }

    private void a(@Nullable ViewPager viewPager, boolean z2, boolean z3) {
        if (this.m != null) {
            if (this.B != null) {
                this.m.removeOnPageChangeListener(this.B);
            }
            if (this.C != null) {
                this.m.removeOnAdapterChangeListener(this.C);
            }
        }
        if (this.x != null) {
            removeOnTabSelectedListener(this.x);
            this.x = null;
        }
        if (viewPager != null) {
            this.m = viewPager;
            if (this.B == null) {
                this.B = new TabLayoutOnPageChangeListener(this);
            }
            this.B.a();
            viewPager.addOnPageChangeListener(this.B);
            this.x = new ViewPagerOnTabSelectedListener(viewPager);
            addOnTabSelectedListener(this.x);
            PagerAdapter adapter = viewPager.getAdapter();
            if (adapter != null) {
                a(adapter, z2);
            }
            if (this.C == null) {
                this.C = new AdapterChangeListener();
            }
            this.C.a(z2);
            viewPager.addOnAdapterChangeListener(this.C);
            setScrollPosition(viewPager.getCurrentItem(), BitmapDescriptorFactory.HUE_RED, true);
        } else {
            this.m = null;
            a((PagerAdapter) null, false);
        }
        this.D = z3;
    }

    @Deprecated
    public void setTabsFromPagerAdapter(@Nullable PagerAdapter pagerAdapter) {
        a(pagerAdapter, false);
    }

    public boolean shouldDelayChildPressedState() {
        return getTabScrollRange() > 0;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.m == null) {
            ViewParent parent = getParent();
            if (parent instanceof ViewPager) {
                a((ViewPager) parent, true, true);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.D) {
            setupWithViewPager(null);
            this.D = false;
        }
    }

    private int getTabScrollRange() {
        return Math.max(0, ((this.q.getWidth() - getWidth()) - getPaddingLeft()) - getPaddingRight());
    }

    /* access modifiers changed from: 0000 */
    public void a(@Nullable PagerAdapter pagerAdapter, boolean z2) {
        if (!(this.z == null || this.A == null)) {
            this.z.unregisterDataSetObserver(this.A);
        }
        this.z = pagerAdapter;
        if (z2 && pagerAdapter != null) {
            if (this.A == null) {
                this.A = new PagerAdapterObserver();
            }
            pagerAdapter.registerDataSetObserver(this.A);
        }
        a();
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        removeAllTabs();
        if (this.z != null) {
            int count = this.z.getCount();
            for (int i2 = 0; i2 < count; i2++) {
                addTab(newTab().setText(this.z.getPageTitle(i2)), false);
            }
            if (this.m != null && count > 0) {
                int currentItem = this.m.getCurrentItem();
                if (currentItem != getSelectedTabPosition() && currentItem < getTabCount()) {
                    a(getTabAt(currentItem));
                }
            }
        }
    }

    private void b() {
        int size = this.o.size();
        for (int i2 = 0; i2 < size; i2++) {
            ((Tab) this.o.get(i2)).a();
        }
    }

    private TabView b(@NonNull Tab tab) {
        TabView tabView = this.E != null ? (TabView) this.E.acquire() : null;
        if (tabView == null) {
            tabView = new TabView(getContext());
        }
        tabView.a(tab);
        tabView.setFocusable(true);
        tabView.setMinimumWidth(getTabMinWidth());
        return tabView;
    }

    private void a(Tab tab, int i2) {
        tab.a(i2);
        this.o.add(i2, tab);
        int size = this.o.size();
        while (true) {
            i2++;
            if (i2 < size) {
                ((Tab) this.o.get(i2)).a(i2);
            } else {
                return;
            }
        }
    }

    private void c(Tab tab) {
        this.q.addView(tab.b, tab.getPosition(), c());
    }

    public void addView(View view) {
        a(view);
    }

    public void addView(View view, int i2) {
        a(view);
    }

    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        a(view);
    }

    public void addView(View view, int i2, ViewGroup.LayoutParams layoutParams) {
        a(view);
    }

    private void a(View view) {
        if (view instanceof TabItem) {
            a((TabItem) view);
            return;
        }
        throw new IllegalArgumentException("Only TabItem instances can be added to TabLayout");
    }

    private LayoutParams c() {
        LayoutParams layoutParams = new LayoutParams(-2, -1);
        a(layoutParams);
        return layoutParams;
    }

    private void a(LayoutParams layoutParams) {
        if (this.l == 1 && this.k == 0) {
            layoutParams.width = 0;
            layoutParams.weight = 1.0f;
            return;
        }
        layoutParams.width = -2;
        layoutParams.weight = BitmapDescriptorFactory.HUE_RED;
    }

    /* access modifiers changed from: 0000 */
    public int a(int i2) {
        return Math.round(getResources().getDisplayMetrics().density * ((float) i2));
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0067, code lost:
        if (r1.getMeasuredWidth() != getMeasuredWidth()) goto L_0x0069;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0069, code lost:
        r6 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0073, code lost:
        if (r1.getMeasuredWidth() < getMeasuredWidth()) goto L_0x0069;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onMeasure(int r6, int r7) {
        /*
            r5 = this;
            int r0 = r5.getDefaultHeight()
            int r0 = r5.a(r0)
            int r1 = r5.getPaddingTop()
            int r0 = r0 + r1
            int r1 = r5.getPaddingBottom()
            int r0 = r0 + r1
            int r1 = android.view.View.MeasureSpec.getMode(r7)
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = 1073741824(0x40000000, float:2.0)
            if (r1 == r2) goto L_0x0024
            if (r1 == 0) goto L_0x001f
            goto L_0x0030
        L_0x001f:
            int r7 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r3)
            goto L_0x0030
        L_0x0024:
            int r7 = android.view.View.MeasureSpec.getSize(r7)
            int r7 = java.lang.Math.min(r0, r7)
            int r7 = android.view.View.MeasureSpec.makeMeasureSpec(r7, r3)
        L_0x0030:
            int r0 = android.view.View.MeasureSpec.getSize(r6)
            int r1 = android.view.View.MeasureSpec.getMode(r6)
            if (r1 == 0) goto L_0x004a
            int r1 = r5.s
            if (r1 <= 0) goto L_0x0041
            int r0 = r5.s
            goto L_0x0048
        L_0x0041:
            r1 = 56
            int r1 = r5.a(r1)
            int r0 = r0 - r1
        L_0x0048:
            r5.j = r0
        L_0x004a:
            super.onMeasure(r6, r7)
            int r6 = r5.getChildCount()
            r0 = 1
            if (r6 != r0) goto L_0x0096
            r6 = 0
            android.view.View r1 = r5.getChildAt(r6)
            int r2 = r5.l
            switch(r2) {
                case 0: goto L_0x006b;
                case 1: goto L_0x005f;
                default: goto L_0x005e;
            }
        L_0x005e:
            goto L_0x0076
        L_0x005f:
            int r2 = r1.getMeasuredWidth()
            int r4 = r5.getMeasuredWidth()
            if (r2 == r4) goto L_0x0076
        L_0x0069:
            r6 = 1
            goto L_0x0076
        L_0x006b:
            int r2 = r1.getMeasuredWidth()
            int r4 = r5.getMeasuredWidth()
            if (r2 >= r4) goto L_0x0076
            goto L_0x0069
        L_0x0076:
            if (r6 == 0) goto L_0x0096
            int r6 = r5.getPaddingTop()
            int r0 = r5.getPaddingBottom()
            int r6 = r6 + r0
            android.view.ViewGroup$LayoutParams r0 = r1.getLayoutParams()
            int r0 = r0.height
            int r6 = getChildMeasureSpec(r7, r6, r0)
            int r7 = r5.getMeasuredWidth()
            int r7 = android.view.View.MeasureSpec.makeMeasureSpec(r7, r3)
            r1.measure(r7, r6)
        L_0x0096:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.design.widget.TabLayout.onMeasure(int, int):void");
    }

    private void b(int i2) {
        TabView tabView = (TabView) this.q.getChildAt(i2);
        this.q.removeViewAt(i2);
        if (tabView != null) {
            tabView.a();
            this.E.release(tabView);
        }
        requestLayout();
    }

    private void c(int i2) {
        if (i2 != -1) {
            if (getWindowToken() == null || !ViewCompat.isLaidOut(this) || this.q.a()) {
                setScrollPosition(i2, BitmapDescriptorFactory.HUE_RED, true);
                return;
            }
            int scrollX = getScrollX();
            int a2 = a(i2, (float) BitmapDescriptorFactory.HUE_RED);
            if (scrollX != a2) {
                d();
                this.y.setIntValues(new int[]{scrollX, a2});
                this.y.start();
            }
            this.q.b(i2, HttpStatus.SC_MULTIPLE_CHOICES);
        }
    }

    private void d() {
        if (this.y == null) {
            this.y = new ValueAnimator();
            this.y.setInterpolator(AnimationUtils.b);
            this.y.setDuration(300);
            this.y.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    TabLayout.this.scrollTo(((Integer) valueAnimator.getAnimatedValue()).intValue(), 0);
                }
            });
        }
    }

    /* access modifiers changed from: 0000 */
    public void setScrollAnimatorListener(AnimatorListener animatorListener) {
        d();
        this.y.addListener(animatorListener);
    }

    private void setSelectedTabView(int i2) {
        int childCount = this.q.getChildCount();
        if (i2 < childCount) {
            int i3 = 0;
            while (i3 < childCount) {
                this.q.getChildAt(i3).setSelected(i3 == i2);
                i3++;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(Tab tab) {
        a(tab, true);
    }

    /* access modifiers changed from: 0000 */
    public void a(Tab tab, boolean z2) {
        Tab tab2 = this.p;
        if (tab2 != tab) {
            int position = tab != null ? tab.getPosition() : -1;
            if (z2) {
                if ((tab2 == null || tab2.getPosition() == -1) && position != -1) {
                    setScrollPosition(position, BitmapDescriptorFactory.HUE_RED, true);
                } else {
                    c(position);
                }
                if (position != -1) {
                    setSelectedTabView(position);
                }
            }
            if (tab2 != null) {
                e(tab2);
            }
            this.p = tab;
            if (tab != null) {
                d(tab);
            }
        } else if (tab2 != null) {
            f(tab);
            c(tab.getPosition());
        }
    }

    private void d(@NonNull Tab tab) {
        for (int size = this.w.size() - 1; size >= 0; size--) {
            ((OnTabSelectedListener) this.w.get(size)).onTabSelected(tab);
        }
    }

    private void e(@NonNull Tab tab) {
        for (int size = this.w.size() - 1; size >= 0; size--) {
            ((OnTabSelectedListener) this.w.get(size)).onTabUnselected(tab);
        }
    }

    private void f(@NonNull Tab tab) {
        for (int size = this.w.size() - 1; size >= 0; size--) {
            ((OnTabSelectedListener) this.w.get(size)).onTabReselected(tab);
        }
    }

    private int a(int i2, float f2) {
        int i3 = 0;
        if (this.l != 0) {
            return 0;
        }
        View childAt = this.q.getChildAt(i2);
        int i4 = i2 + 1;
        View childAt2 = i4 < this.q.getChildCount() ? this.q.getChildAt(i4) : null;
        int width = childAt != null ? childAt.getWidth() : 0;
        if (childAt2 != null) {
            i3 = childAt2.getWidth();
        }
        int left = (childAt.getLeft() + (width / 2)) - (getWidth() / 2);
        int i5 = (int) (((float) (width + i3)) * 0.5f * f2);
        return ViewCompat.getLayoutDirection(this) == 0 ? left + i5 : left - i5;
    }

    private void e() {
        ViewCompat.setPaddingRelative(this.q, this.l == 0 ? Math.max(0, this.u - this.a) : 0, 0, 0, 0);
        switch (this.l) {
            case 0:
                this.q.setGravity(GravityCompat.START);
                break;
            case 1:
                this.q.setGravity(1);
                break;
        }
        a(true);
    }

    /* access modifiers changed from: 0000 */
    public void a(boolean z2) {
        for (int i2 = 0; i2 < this.q.getChildCount(); i2++) {
            View childAt = this.q.getChildAt(i2);
            childAt.setMinimumWidth(getTabMinWidth());
            a((LayoutParams) childAt.getLayoutParams());
            if (z2) {
                childAt.requestLayout();
            }
        }
    }

    private static ColorStateList a(int i2, int i3) {
        return new ColorStateList(new int[][]{SELECTED_STATE_SET, EMPTY_STATE_SET}, new int[]{i3, i2});
    }

    private int getDefaultHeight() {
        int size = this.o.size();
        boolean z2 = false;
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                break;
            }
            Tab tab = (Tab) this.o.get(i2);
            if (tab != null && tab.getIcon() != null && !TextUtils.isEmpty(tab.getText())) {
                z2 = true;
                break;
            }
            i2++;
        }
        return z2 ? 72 : 48;
    }

    private int getTabMinWidth() {
        if (this.r != -1) {
            return this.r;
        }
        return this.l == 0 ? this.t : 0;
    }

    public FrameLayout.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return generateDefaultLayoutParams();
    }

    /* access modifiers changed from: 0000 */
    public int getTabMaxWidth() {
        return this.j;
    }
}
