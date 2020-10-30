package android.support.v7.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.appcompat.R;
import android.support.v7.view.ActionBarPolicy;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewPropertyAnimator;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

@RestrictTo({Scope.LIBRARY_GROUP})
public class ScrollingTabContainerView extends HorizontalScrollView implements OnItemSelectedListener {
    private static final Interpolator j = new DecelerateInterpolator();
    Runnable a;
    LinearLayoutCompat b;
    int c;
    int d;
    private TabClickListener e;
    private Spinner f;
    private boolean g;
    private int h;
    private int i;
    protected final VisibilityAnimListener mVisAnimListener = new VisibilityAnimListener();
    protected ViewPropertyAnimator mVisibilityAnim;

    class TabAdapter extends BaseAdapter {
        public long getItemId(int i) {
            return (long) i;
        }

        TabAdapter() {
        }

        public int getCount() {
            return ScrollingTabContainerView.this.b.getChildCount();
        }

        public Object getItem(int i) {
            return ((TabView) ScrollingTabContainerView.this.b.getChildAt(i)).b();
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                return ScrollingTabContainerView.this.a((Tab) getItem(i), true);
            }
            ((TabView) view).a((Tab) getItem(i));
            return view;
        }
    }

    class TabClickListener implements OnClickListener {
        TabClickListener() {
        }

        public void onClick(View view) {
            ((TabView) view).b().select();
            int childCount = ScrollingTabContainerView.this.b.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = ScrollingTabContainerView.this.b.getChildAt(i);
                childAt.setSelected(childAt == view);
            }
        }
    }

    class TabView extends LinearLayout {
        private final int[] b = {16842964};
        private Tab c;
        private TextView d;
        private ImageView e;
        private View f;

        public TabView(Context context, Tab tab, boolean z) {
            super(context, null, R.attr.actionBarTabStyle);
            this.c = tab;
            TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, null, this.b, R.attr.actionBarTabStyle, 0);
            if (obtainStyledAttributes.hasValue(0)) {
                setBackgroundDrawable(obtainStyledAttributes.getDrawable(0));
            }
            obtainStyledAttributes.recycle();
            if (z) {
                setGravity(8388627);
            }
            a();
        }

        public void a(Tab tab) {
            this.c = tab;
            a();
        }

        public void setSelected(boolean z) {
            boolean z2 = isSelected() != z;
            super.setSelected(z);
            if (z2 && z) {
                sendAccessibilityEvent(4);
            }
        }

        public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(accessibilityEvent);
            accessibilityEvent.setClassName(Tab.class.getName());
        }

        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            accessibilityNodeInfo.setClassName(Tab.class.getName());
        }

        public void onMeasure(int i, int i2) {
            super.onMeasure(i, i2);
            if (ScrollingTabContainerView.this.c > 0 && getMeasuredWidth() > ScrollingTabContainerView.this.c) {
                super.onMeasure(MeasureSpec.makeMeasureSpec(ScrollingTabContainerView.this.c, 1073741824), i2);
            }
        }

        public void a() {
            Tab tab = this.c;
            View customView = tab.getCustomView();
            CharSequence charSequence = null;
            if (customView != null) {
                ViewParent parent = customView.getParent();
                if (parent != this) {
                    if (parent != null) {
                        ((ViewGroup) parent).removeView(customView);
                    }
                    addView(customView);
                }
                this.f = customView;
                if (this.d != null) {
                    this.d.setVisibility(8);
                }
                if (this.e != null) {
                    this.e.setVisibility(8);
                    this.e.setImageDrawable(null);
                    return;
                }
                return;
            }
            if (this.f != null) {
                removeView(this.f);
                this.f = null;
            }
            Drawable icon = tab.getIcon();
            CharSequence text = tab.getText();
            if (icon != null) {
                if (this.e == null) {
                    AppCompatImageView appCompatImageView = new AppCompatImageView(getContext());
                    LayoutParams layoutParams = new LayoutParams(-2, -2);
                    layoutParams.gravity = 16;
                    appCompatImageView.setLayoutParams(layoutParams);
                    addView(appCompatImageView, 0);
                    this.e = appCompatImageView;
                }
                this.e.setImageDrawable(icon);
                this.e.setVisibility(0);
            } else if (this.e != null) {
                this.e.setVisibility(8);
                this.e.setImageDrawable(null);
            }
            boolean z = !TextUtils.isEmpty(text);
            if (z) {
                if (this.d == null) {
                    AppCompatTextView appCompatTextView = new AppCompatTextView(getContext(), null, R.attr.actionBarTabTextStyle);
                    appCompatTextView.setEllipsize(TruncateAt.END);
                    LayoutParams layoutParams2 = new LayoutParams(-2, -2);
                    layoutParams2.gravity = 16;
                    appCompatTextView.setLayoutParams(layoutParams2);
                    addView(appCompatTextView);
                    this.d = appCompatTextView;
                }
                this.d.setText(text);
                this.d.setVisibility(0);
            } else if (this.d != null) {
                this.d.setVisibility(8);
                this.d.setText(null);
            }
            if (this.e != null) {
                this.e.setContentDescription(tab.getContentDescription());
            }
            if (!z) {
                charSequence = tab.getContentDescription();
            }
            TooltipCompat.setTooltipText(this, charSequence);
        }

        public Tab b() {
            return this.c;
        }
    }

    public class VisibilityAnimListener extends AnimatorListenerAdapter {
        private boolean b = false;
        private int c;

        protected VisibilityAnimListener() {
        }

        public VisibilityAnimListener withFinalVisibility(ViewPropertyAnimator viewPropertyAnimator, int i) {
            this.c = i;
            ScrollingTabContainerView.this.mVisibilityAnim = viewPropertyAnimator;
            return this;
        }

        public void onAnimationStart(Animator animator) {
            ScrollingTabContainerView.this.setVisibility(0);
            this.b = false;
        }

        public void onAnimationEnd(Animator animator) {
            if (!this.b) {
                ScrollingTabContainerView.this.mVisibilityAnim = null;
                ScrollingTabContainerView.this.setVisibility(this.c);
            }
        }

        public void onAnimationCancel(Animator animator) {
            this.b = true;
        }
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public ScrollingTabContainerView(Context context) {
        super(context);
        setHorizontalScrollBarEnabled(false);
        ActionBarPolicy actionBarPolicy = ActionBarPolicy.get(context);
        setContentHeight(actionBarPolicy.getTabContainerHeight());
        this.d = actionBarPolicy.getStackedTabMaxWidth();
        this.b = d();
        addView(this.b, new ViewGroup.LayoutParams(-2, -1));
    }

    public void onMeasure(int i2, int i3) {
        int mode = MeasureSpec.getMode(i2);
        boolean z = true;
        boolean z2 = mode == 1073741824;
        setFillViewport(z2);
        int childCount = this.b.getChildCount();
        if (childCount <= 1 || !(mode == 1073741824 || mode == Integer.MIN_VALUE)) {
            this.c = -1;
        } else {
            if (childCount > 2) {
                this.c = (int) (((float) MeasureSpec.getSize(i2)) * 0.4f);
            } else {
                this.c = MeasureSpec.getSize(i2) / 2;
            }
            this.c = Math.min(this.c, this.d);
        }
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(this.h, 1073741824);
        if (z2 || !this.g) {
            z = false;
        }
        if (z) {
            this.b.measure(0, makeMeasureSpec);
            if (this.b.getMeasuredWidth() > MeasureSpec.getSize(i2)) {
                b();
            } else {
                c();
            }
        } else {
            c();
        }
        int measuredWidth = getMeasuredWidth();
        super.onMeasure(i2, makeMeasureSpec);
        int measuredWidth2 = getMeasuredWidth();
        if (z2 && measuredWidth != measuredWidth2) {
            setTabSelected(this.i);
        }
    }

    private boolean a() {
        return this.f != null && this.f.getParent() == this;
    }

    public void setAllowCollapse(boolean z) {
        this.g = z;
    }

    private void b() {
        if (!a()) {
            if (this.f == null) {
                this.f = e();
            }
            removeView(this.b);
            addView(this.f, new ViewGroup.LayoutParams(-2, -1));
            if (this.f.getAdapter() == null) {
                this.f.setAdapter(new TabAdapter());
            }
            if (this.a != null) {
                removeCallbacks(this.a);
                this.a = null;
            }
            this.f.setSelection(this.i);
        }
    }

    private boolean c() {
        if (!a()) {
            return false;
        }
        removeView(this.f);
        addView(this.b, new ViewGroup.LayoutParams(-2, -1));
        setTabSelected(this.f.getSelectedItemPosition());
        return false;
    }

    public void setTabSelected(int i2) {
        this.i = i2;
        int childCount = this.b.getChildCount();
        int i3 = 0;
        while (i3 < childCount) {
            View childAt = this.b.getChildAt(i3);
            boolean z = i3 == i2;
            childAt.setSelected(z);
            if (z) {
                animateToTab(i2);
            }
            i3++;
        }
        if (this.f != null && i2 >= 0) {
            this.f.setSelection(i2);
        }
    }

    public void setContentHeight(int i2) {
        this.h = i2;
        requestLayout();
    }

    private LinearLayoutCompat d() {
        LinearLayoutCompat linearLayoutCompat = new LinearLayoutCompat(getContext(), null, R.attr.actionBarTabBarStyle);
        linearLayoutCompat.setMeasureWithLargestChildEnabled(true);
        linearLayoutCompat.setGravity(17);
        linearLayoutCompat.setLayoutParams(new LinearLayoutCompat.LayoutParams(-2, -1));
        return linearLayoutCompat;
    }

    private Spinner e() {
        AppCompatSpinner appCompatSpinner = new AppCompatSpinner(getContext(), null, R.attr.actionDropDownStyle);
        appCompatSpinner.setLayoutParams(new LinearLayoutCompat.LayoutParams(-2, -1));
        appCompatSpinner.setOnItemSelectedListener(this);
        return appCompatSpinner;
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ActionBarPolicy actionBarPolicy = ActionBarPolicy.get(getContext());
        setContentHeight(actionBarPolicy.getTabContainerHeight());
        this.d = actionBarPolicy.getStackedTabMaxWidth();
    }

    public void animateToVisibility(int i2) {
        if (this.mVisibilityAnim != null) {
            this.mVisibilityAnim.cancel();
        }
        if (i2 == 0) {
            if (getVisibility() != 0) {
                setAlpha(BitmapDescriptorFactory.HUE_RED);
            }
            ViewPropertyAnimator alpha = animate().alpha(1.0f);
            alpha.setDuration(200);
            alpha.setInterpolator(j);
            alpha.setListener(this.mVisAnimListener.withFinalVisibility(alpha, i2));
            alpha.start();
            return;
        }
        ViewPropertyAnimator alpha2 = animate().alpha(BitmapDescriptorFactory.HUE_RED);
        alpha2.setDuration(200);
        alpha2.setInterpolator(j);
        alpha2.setListener(this.mVisAnimListener.withFinalVisibility(alpha2, i2));
        alpha2.start();
    }

    public void animateToTab(int i2) {
        final View childAt = this.b.getChildAt(i2);
        if (this.a != null) {
            removeCallbacks(this.a);
        }
        this.a = new Runnable() {
            public void run() {
                ScrollingTabContainerView.this.smoothScrollTo(childAt.getLeft() - ((ScrollingTabContainerView.this.getWidth() - childAt.getWidth()) / 2), 0);
                ScrollingTabContainerView.this.a = null;
            }
        };
        post(this.a);
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.a != null) {
            post(this.a);
        }
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.a != null) {
            removeCallbacks(this.a);
        }
    }

    /* access modifiers changed from: 0000 */
    public TabView a(Tab tab, boolean z) {
        TabView tabView = new TabView(getContext(), tab, z);
        if (z) {
            tabView.setBackgroundDrawable(null);
            tabView.setLayoutParams(new AbsListView.LayoutParams(-1, this.h));
        } else {
            tabView.setFocusable(true);
            if (this.e == null) {
                this.e = new TabClickListener();
            }
            tabView.setOnClickListener(this.e);
        }
        return tabView;
    }

    public void addTab(Tab tab, boolean z) {
        TabView a2 = a(tab, false);
        this.b.addView(a2, new LinearLayoutCompat.LayoutParams(0, -1, 1.0f));
        if (this.f != null) {
            ((TabAdapter) this.f.getAdapter()).notifyDataSetChanged();
        }
        if (z) {
            a2.setSelected(true);
        }
        if (this.g) {
            requestLayout();
        }
    }

    public void addTab(Tab tab, int i2, boolean z) {
        TabView a2 = a(tab, false);
        this.b.addView(a2, i2, new LinearLayoutCompat.LayoutParams(0, -1, 1.0f));
        if (this.f != null) {
            ((TabAdapter) this.f.getAdapter()).notifyDataSetChanged();
        }
        if (z) {
            a2.setSelected(true);
        }
        if (this.g) {
            requestLayout();
        }
    }

    public void updateTab(int i2) {
        ((TabView) this.b.getChildAt(i2)).a();
        if (this.f != null) {
            ((TabAdapter) this.f.getAdapter()).notifyDataSetChanged();
        }
        if (this.g) {
            requestLayout();
        }
    }

    public void removeTabAt(int i2) {
        this.b.removeViewAt(i2);
        if (this.f != null) {
            ((TabAdapter) this.f.getAdapter()).notifyDataSetChanged();
        }
        if (this.g) {
            requestLayout();
        }
    }

    public void removeAllTabs() {
        this.b.removeAllViews();
        if (this.f != null) {
            ((TabAdapter) this.f.getAdapter()).notifyDataSetChanged();
        }
        if (this.g) {
            requestLayout();
        }
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int i2, long j2) {
        ((TabView) view).b().select();
    }
}
