package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.annotation.VisibleForTesting;
import android.support.design.R;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.Space;
import android.support.v4.widget.TextViewCompat;
import android.support.v4.widget.ViewGroupUtils;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DrawableUtils;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.WithHint;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewStructure;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class TextInputLayout extends LinearLayout implements WithHint {
    private boolean A;
    private Drawable B;
    private Drawable C;
    private ColorStateList D;
    private boolean E;
    private Mode F;
    private boolean G;
    private ColorStateList H;
    private ColorStateList I;
    private boolean J;
    private boolean K;
    private ValueAnimator L;
    private boolean M;
    private boolean N;
    /* access modifiers changed from: private */
    public boolean O;
    EditText a;
    TextView b;
    boolean c;
    final CollapsingTextHelper d;
    private final FrameLayout e;
    private CharSequence f;
    private boolean g;
    private CharSequence h;
    private Paint i;
    private final Rect j;
    private LinearLayout k;
    private int l;
    private Typeface m;
    private boolean n;
    private int o;
    private boolean p;
    private CharSequence q;
    private TextView r;
    private int s;
    private int t;
    private int u;
    private boolean v;
    private boolean w;
    private Drawable x;
    private CharSequence y;
    private CheckableImageButton z;

    static class SavedState extends AbsSavedState {
        public static final Creator<SavedState> CREATOR = new ClassLoaderCreator<SavedState>() {
            /* renamed from: a */
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            /* renamed from: a */
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }

            /* renamed from: a */
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        CharSequence a;
        boolean b;

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.a = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            boolean z = true;
            if (parcel.readInt() != 1) {
                z = false;
            }
            this.b = z;
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            TextUtils.writeToParcel(this.a, parcel, i);
            parcel.writeInt(this.b ? 1 : 0);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("TextInputLayout.SavedState{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(" error=");
            sb.append(this.a);
            sb.append("}");
            return sb.toString();
        }
    }

    class TextInputAccessibilityDelegate extends AccessibilityDelegateCompat {
        TextInputAccessibilityDelegate() {
        }

        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            accessibilityEvent.setClassName(TextInputLayout.class.getSimpleName());
        }

        public void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onPopulateAccessibilityEvent(view, accessibilityEvent);
            CharSequence j = TextInputLayout.this.d.j();
            if (!TextUtils.isEmpty(j)) {
                accessibilityEvent.getText().add(j);
            }
        }

        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            accessibilityNodeInfoCompat.setClassName(TextInputLayout.class.getSimpleName());
            CharSequence j = TextInputLayout.this.d.j();
            if (!TextUtils.isEmpty(j)) {
                accessibilityNodeInfoCompat.setText(j);
            }
            if (TextInputLayout.this.a != null) {
                accessibilityNodeInfoCompat.setLabelFor(TextInputLayout.this.a);
            }
            CharSequence text = TextInputLayout.this.b != null ? TextInputLayout.this.b.getText() : null;
            if (!TextUtils.isEmpty(text)) {
                accessibilityNodeInfoCompat.setContentInvalid(true);
                accessibilityNodeInfoCompat.setError(text);
            }
        }
    }

    public TextInputLayout(Context context) {
        this(context, null);
    }

    public TextInputLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TextInputLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet);
        this.j = new Rect();
        this.d = new CollapsingTextHelper(this);
        ThemeUtils.a(context);
        setOrientation(1);
        setWillNotDraw(false);
        setAddStatesFromChildren(true);
        this.e = new FrameLayout(context);
        this.e.setAddStatesFromChildren(true);
        addView(this.e);
        this.d.a(AnimationUtils.b);
        this.d.b((Interpolator) new AccelerateInterpolator());
        this.d.b(8388659);
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, R.styleable.TextInputLayout, i2, R.style.Widget_Design_TextInputLayout);
        this.g = obtainStyledAttributes.getBoolean(R.styleable.TextInputLayout_hintEnabled, true);
        setHint(obtainStyledAttributes.getText(R.styleable.TextInputLayout_android_hint));
        this.K = obtainStyledAttributes.getBoolean(R.styleable.TextInputLayout_hintAnimationEnabled, true);
        if (obtainStyledAttributes.hasValue(R.styleable.TextInputLayout_android_textColorHint)) {
            ColorStateList colorStateList = obtainStyledAttributes.getColorStateList(R.styleable.TextInputLayout_android_textColorHint);
            this.I = colorStateList;
            this.H = colorStateList;
        }
        if (obtainStyledAttributes.getResourceId(R.styleable.TextInputLayout_hintTextAppearance, -1) != -1) {
            setHintTextAppearance(obtainStyledAttributes.getResourceId(R.styleable.TextInputLayout_hintTextAppearance, 0));
        }
        this.o = obtainStyledAttributes.getResourceId(R.styleable.TextInputLayout_errorTextAppearance, 0);
        boolean z2 = obtainStyledAttributes.getBoolean(R.styleable.TextInputLayout_errorEnabled, false);
        boolean z3 = obtainStyledAttributes.getBoolean(R.styleable.TextInputLayout_counterEnabled, false);
        setCounterMaxLength(obtainStyledAttributes.getInt(R.styleable.TextInputLayout_counterMaxLength, -1));
        this.t = obtainStyledAttributes.getResourceId(R.styleable.TextInputLayout_counterTextAppearance, 0);
        this.u = obtainStyledAttributes.getResourceId(R.styleable.TextInputLayout_counterOverflowTextAppearance, 0);
        this.w = obtainStyledAttributes.getBoolean(R.styleable.TextInputLayout_passwordToggleEnabled, false);
        this.x = obtainStyledAttributes.getDrawable(R.styleable.TextInputLayout_passwordToggleDrawable);
        this.y = obtainStyledAttributes.getText(R.styleable.TextInputLayout_passwordToggleContentDescription);
        if (obtainStyledAttributes.hasValue(R.styleable.TextInputLayout_passwordToggleTint)) {
            this.E = true;
            this.D = obtainStyledAttributes.getColorStateList(R.styleable.TextInputLayout_passwordToggleTint);
        }
        if (obtainStyledAttributes.hasValue(R.styleable.TextInputLayout_passwordToggleTintMode)) {
            this.G = true;
            this.F = ViewUtils.a(obtainStyledAttributes.getInt(R.styleable.TextInputLayout_passwordToggleTintMode, -1), null);
        }
        obtainStyledAttributes.recycle();
        setErrorEnabled(z2);
        setCounterEnabled(z3);
        h();
        if (ViewCompat.getImportantForAccessibility(this) == 0) {
            ViewCompat.setImportantForAccessibility(this, 1);
        }
        ViewCompat.setAccessibilityDelegate(this, new TextInputAccessibilityDelegate());
    }

    public void addView(View view, int i2, LayoutParams layoutParams) {
        if (view instanceof EditText) {
            FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(layoutParams);
            layoutParams2.gravity = (layoutParams2.gravity & -113) | 16;
            this.e.addView(view, layoutParams2);
            this.e.setLayoutParams(layoutParams);
            a();
            setEditText((EditText) view);
            return;
        }
        super.addView(view, i2, layoutParams);
    }

    public void setTypeface(@Nullable Typeface typeface) {
        if ((this.m != null && !this.m.equals(typeface)) || (this.m == null && typeface != null)) {
            this.m = typeface;
            this.d.c(typeface);
            if (this.r != null) {
                this.r.setTypeface(typeface);
            }
            if (this.b != null) {
                this.b.setTypeface(typeface);
            }
        }
    }

    @NonNull
    public Typeface getTypeface() {
        return this.m;
    }

    public void dispatchProvideAutofillStructure(ViewStructure viewStructure, int i2) {
        if (this.f == null || this.a == null) {
            super.dispatchProvideAutofillStructure(viewStructure, i2);
            return;
        }
        CharSequence hint = this.a.getHint();
        this.a.setHint(this.f);
        try {
            super.dispatchProvideAutofillStructure(viewStructure, i2);
        } finally {
            this.a.setHint(hint);
        }
    }

    private void setEditText(EditText editText) {
        if (this.a != null) {
            throw new IllegalArgumentException("We already have an EditText, can only have one");
        }
        if (!(editText instanceof TextInputEditText)) {
            Log.i("TextInputLayout", "EditText added is not a TextInputEditText. Please switch to using that class instead.");
        }
        this.a = editText;
        if (!f()) {
            this.d.c(this.a.getTypeface());
        }
        this.d.a(this.a.getTextSize());
        int gravity = this.a.getGravity();
        this.d.b((gravity & -113) | 48);
        this.d.a(gravity);
        this.a.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                TextInputLayout.this.a(!TextInputLayout.this.O);
                if (TextInputLayout.this.c) {
                    TextInputLayout.this.a(editable.length());
                }
            }
        });
        if (this.H == null) {
            this.H = this.a.getHintTextColors();
        }
        if (this.g && TextUtils.isEmpty(this.h)) {
            this.f = this.a.getHint();
            setHint(this.f);
            this.a.setHint(null);
        }
        if (this.r != null) {
            a(this.a.getText().length());
        }
        if (this.k != null) {
            b();
        }
        e();
        a(false, true);
    }

    private void a() {
        int i2;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.e.getLayoutParams();
        if (this.g) {
            if (this.i == null) {
                this.i = new Paint();
            }
            this.i.setTypeface(this.d.d());
            this.i.setTextSize(this.d.h());
            i2 = (int) (-this.i.ascent());
        } else {
            i2 = 0;
        }
        if (i2 != layoutParams.topMargin) {
            layoutParams.topMargin = i2;
            this.e.requestLayout();
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(boolean z2) {
        a(z2, false);
    }

    /* access modifiers changed from: 0000 */
    public void a(boolean z2, boolean z3) {
        boolean isEnabled = isEnabled();
        boolean z4 = this.a != null && !TextUtils.isEmpty(this.a.getText());
        boolean a2 = a(getDrawableState(), 16842908);
        boolean isEmpty = true ^ TextUtils.isEmpty(getError());
        if (this.H != null) {
            this.d.b(this.H);
        }
        if (isEnabled && this.v && this.r != null) {
            this.d.a(this.r.getTextColors());
        } else if (isEnabled && a2 && this.I != null) {
            this.d.a(this.I);
        } else if (this.H != null) {
            this.d.a(this.H);
        }
        if (z4 || (isEnabled() && (a2 || isEmpty))) {
            if (z3 || this.J) {
                c(z2);
            }
        } else if (z3 || !this.J) {
            d(z2);
        }
    }

    @Nullable
    public EditText getEditText() {
        return this.a;
    }

    public void setHint(@Nullable CharSequence charSequence) {
        if (this.g) {
            setHintInternal(charSequence);
            sendAccessibilityEvent(2048);
        }
    }

    private void setHintInternal(CharSequence charSequence) {
        this.h = charSequence;
        this.d.a(charSequence);
    }

    @Nullable
    public CharSequence getHint() {
        if (this.g) {
            return this.h;
        }
        return null;
    }

    public void setHintEnabled(boolean z2) {
        if (z2 != this.g) {
            this.g = z2;
            CharSequence hint = this.a.getHint();
            if (!this.g) {
                if (!TextUtils.isEmpty(this.h) && TextUtils.isEmpty(hint)) {
                    this.a.setHint(this.h);
                }
                setHintInternal(null);
            } else if (!TextUtils.isEmpty(hint)) {
                if (TextUtils.isEmpty(this.h)) {
                    setHint(hint);
                }
                this.a.setHint(null);
            }
            if (this.a != null) {
                a();
            }
        }
    }

    public boolean isHintEnabled() {
        return this.g;
    }

    public void setHintTextAppearance(@StyleRes int i2) {
        this.d.c(i2);
        this.I = this.d.k();
        if (this.a != null) {
            a(false);
            a();
        }
    }

    private void a(TextView textView, int i2) {
        if (this.k == null) {
            this.k = new LinearLayout(getContext());
            this.k.setOrientation(0);
            addView(this.k, -1, -2);
            this.k.addView(new Space(getContext()), new LinearLayout.LayoutParams(0, 0, 1.0f));
            if (this.a != null) {
                b();
            }
        }
        this.k.setVisibility(0);
        this.k.addView(textView, i2);
        this.l++;
    }

    private void b() {
        ViewCompat.setPaddingRelative(this.k, ViewCompat.getPaddingStart(this.a), 0, ViewCompat.getPaddingEnd(this.a), this.a.getPaddingBottom());
    }

    private void a(TextView textView) {
        if (this.k != null) {
            this.k.removeView(textView);
            int i2 = this.l - 1;
            this.l = i2;
            if (i2 == 0) {
                this.k.setVisibility(8);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0054  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setErrorEnabled(boolean r6) {
        /*
            r5 = this;
            boolean r0 = r5.n
            if (r0 == r6) goto L_0x008a
            android.widget.TextView r0 = r5.b
            if (r0 == 0) goto L_0x0011
            android.widget.TextView r0 = r5.b
            android.view.ViewPropertyAnimator r0 = r0.animate()
            r0.cancel()
        L_0x0011:
            r0 = 0
            if (r6 == 0) goto L_0x007b
            android.support.v7.widget.AppCompatTextView r1 = new android.support.v7.widget.AppCompatTextView
            android.content.Context r2 = r5.getContext()
            r1.<init>(r2)
            r5.b = r1
            android.widget.TextView r1 = r5.b
            int r2 = android.support.design.R.id.textinput_error
            r1.setId(r2)
            android.graphics.Typeface r1 = r5.m
            if (r1 == 0) goto L_0x0031
            android.widget.TextView r1 = r5.b
            android.graphics.Typeface r2 = r5.m
            r1.setTypeface(r2)
        L_0x0031:
            r1 = 1
            android.widget.TextView r2 = r5.b     // Catch:{ Exception -> 0x0051 }
            int r3 = r5.o     // Catch:{ Exception -> 0x0051 }
            android.support.v4.widget.TextViewCompat.setTextAppearance(r2, r3)     // Catch:{ Exception -> 0x0051 }
            int r2 = android.os.Build.VERSION.SDK_INT     // Catch:{ Exception -> 0x0051 }
            r3 = 23
            if (r2 < r3) goto L_0x004f
            android.widget.TextView r2 = r5.b     // Catch:{ Exception -> 0x0051 }
            android.content.res.ColorStateList r2 = r2.getTextColors()     // Catch:{ Exception -> 0x0051 }
            int r2 = r2.getDefaultColor()     // Catch:{ Exception -> 0x0051 }
            r3 = -65281(0xffffffffffff00ff, float:NaN)
            if (r2 != r3) goto L_0x004f
            goto L_0x0051
        L_0x004f:
            r2 = 0
            goto L_0x0052
        L_0x0051:
            r2 = 1
        L_0x0052:
            if (r2 == 0) goto L_0x006a
            android.widget.TextView r2 = r5.b
            int r3 = android.support.v7.appcompat.R.style.TextAppearance_AppCompat_Caption
            android.support.v4.widget.TextViewCompat.setTextAppearance(r2, r3)
            android.widget.TextView r2 = r5.b
            android.content.Context r3 = r5.getContext()
            int r4 = android.support.v7.appcompat.R.color.error_color_material
            int r3 = android.support.v4.content.ContextCompat.getColor(r3, r4)
            r2.setTextColor(r3)
        L_0x006a:
            android.widget.TextView r2 = r5.b
            r3 = 4
            r2.setVisibility(r3)
            android.widget.TextView r2 = r5.b
            android.support.v4.view.ViewCompat.setAccessibilityLiveRegion(r2, r1)
            android.widget.TextView r1 = r5.b
            r5.a(r1, r0)
            goto L_0x0088
        L_0x007b:
            r5.p = r0
            r5.c()
            android.widget.TextView r0 = r5.b
            r5.a(r0)
            r0 = 0
            r5.b = r0
        L_0x0088:
            r5.n = r6
        L_0x008a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.design.widget.TextInputLayout.setErrorEnabled(boolean):void");
    }

    public void setErrorTextAppearance(@StyleRes int i2) {
        this.o = i2;
        if (this.b != null) {
            TextViewCompat.setTextAppearance(this.b, i2);
        }
    }

    public boolean isErrorEnabled() {
        return this.n;
    }

    public void setError(@Nullable CharSequence charSequence) {
        a(charSequence, ViewCompat.isLaidOut(this) && isEnabled() && (this.b == null || !TextUtils.equals(this.b.getText(), charSequence)));
    }

    private void a(@Nullable final CharSequence charSequence, boolean z2) {
        this.q = charSequence;
        if (!this.n) {
            if (!TextUtils.isEmpty(charSequence)) {
                setErrorEnabled(true);
            } else {
                return;
            }
        }
        this.p = !TextUtils.isEmpty(charSequence);
        this.b.animate().cancel();
        if (this.p) {
            this.b.setText(charSequence);
            this.b.setVisibility(0);
            if (z2) {
                if (this.b.getAlpha() == 1.0f) {
                    this.b.setAlpha(BitmapDescriptorFactory.HUE_RED);
                }
                this.b.animate().alpha(1.0f).setDuration(200).setInterpolator(AnimationUtils.d).setListener(new AnimatorListenerAdapter() {
                    public void onAnimationStart(Animator animator) {
                        TextInputLayout.this.b.setVisibility(0);
                    }
                }).start();
            } else {
                this.b.setAlpha(1.0f);
            }
        } else if (this.b.getVisibility() == 0) {
            if (z2) {
                this.b.animate().alpha(BitmapDescriptorFactory.HUE_RED).setDuration(200).setInterpolator(AnimationUtils.c).setListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animator) {
                        TextInputLayout.this.b.setText(charSequence);
                        TextInputLayout.this.b.setVisibility(4);
                    }
                }).start();
            } else {
                this.b.setText(charSequence);
                this.b.setVisibility(4);
            }
        }
        c();
        a(z2);
    }

    public void setCounterEnabled(boolean z2) {
        if (this.c != z2) {
            if (z2) {
                this.r = new AppCompatTextView(getContext());
                this.r.setId(R.id.textinput_counter);
                if (this.m != null) {
                    this.r.setTypeface(this.m);
                }
                this.r.setMaxLines(1);
                try {
                    TextViewCompat.setTextAppearance(this.r, this.t);
                } catch (Exception unused) {
                    TextViewCompat.setTextAppearance(this.r, android.support.v7.appcompat.R.style.TextAppearance_AppCompat_Caption);
                    this.r.setTextColor(ContextCompat.getColor(getContext(), android.support.v7.appcompat.R.color.error_color_material));
                }
                a(this.r, -1);
                if (this.a == null) {
                    a(0);
                } else {
                    a(this.a.getText().length());
                }
            } else {
                a(this.r);
                this.r = null;
            }
            this.c = z2;
        }
    }

    public boolean isCounterEnabled() {
        return this.c;
    }

    public void setCounterMaxLength(int i2) {
        if (this.s != i2) {
            if (i2 > 0) {
                this.s = i2;
            } else {
                this.s = -1;
            }
            if (this.c) {
                a(this.a == null ? 0 : this.a.getText().length());
            }
        }
    }

    public void setEnabled(boolean z2) {
        a((ViewGroup) this, z2);
        super.setEnabled(z2);
    }

    private static void a(ViewGroup viewGroup, boolean z2) {
        int childCount = viewGroup.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = viewGroup.getChildAt(i2);
            childAt.setEnabled(z2);
            if (childAt instanceof ViewGroup) {
                a((ViewGroup) childAt, z2);
            }
        }
    }

    public int getCounterMaxLength() {
        return this.s;
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2) {
        boolean z2 = this.v;
        if (this.s == -1) {
            this.r.setText(String.valueOf(i2));
            this.v = false;
        } else {
            this.v = i2 > this.s;
            if (z2 != this.v) {
                TextViewCompat.setTextAppearance(this.r, this.v ? this.u : this.t);
            }
            this.r.setText(getContext().getString(R.string.character_counter_pattern, new Object[]{Integer.valueOf(i2), Integer.valueOf(this.s)}));
        }
        if (this.a != null && z2 != this.v) {
            a(false);
            c();
        }
    }

    private void c() {
        if (this.a != null) {
            Drawable background = this.a.getBackground();
            if (background != null) {
                d();
                if (DrawableUtils.canSafelyMutateDrawable(background)) {
                    background = background.mutate();
                }
                if (this.p && this.b != null) {
                    background.setColorFilter(AppCompatDrawableManager.getPorterDuffColorFilter(this.b.getCurrentTextColor(), Mode.SRC_IN));
                } else if (!this.v || this.r == null) {
                    DrawableCompat.clearColorFilter(background);
                    this.a.refreshDrawableState();
                } else {
                    background.setColorFilter(AppCompatDrawableManager.getPorterDuffColorFilter(this.r.getCurrentTextColor(), Mode.SRC_IN));
                }
            }
        }
    }

    private void d() {
        int i2 = VERSION.SDK_INT;
        if (i2 == 21 || i2 == 22) {
            Drawable background = this.a.getBackground();
            if (background != null && !this.M) {
                Drawable newDrawable = background.getConstantState().newDrawable();
                if (background instanceof DrawableContainer) {
                    this.M = DrawableUtils.a((DrawableContainer) background, newDrawable.getConstantState());
                }
                if (!this.M) {
                    ViewCompat.setBackground(this.a, newDrawable);
                    this.M = true;
                }
            }
        }
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        if (this.p) {
            savedState.a = getError();
        }
        savedState.b = this.A;
        return savedState;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setError(savedState.a);
        if (savedState.b) {
            b(true);
        }
        requestLayout();
    }

    /* access modifiers changed from: protected */
    public void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        this.O = true;
        super.dispatchRestoreInstanceState(sparseArray);
        this.O = false;
    }

    @Nullable
    public CharSequence getError() {
        if (this.n) {
            return this.q;
        }
        return null;
    }

    public boolean isHintAnimationEnabled() {
        return this.K;
    }

    public void setHintAnimationEnabled(boolean z2) {
        this.K = z2;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.g) {
            this.d.a(canvas);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        e();
        super.onMeasure(i2, i3);
    }

    private void e() {
        if (this.a != null) {
            if (g()) {
                if (this.z == null) {
                    this.z = (CheckableImageButton) LayoutInflater.from(getContext()).inflate(R.layout.design_text_input_password_icon, this.e, false);
                    this.z.setImageDrawable(this.x);
                    this.z.setContentDescription(this.y);
                    this.e.addView(this.z);
                    this.z.setOnClickListener(new OnClickListener() {
                        public void onClick(View view) {
                            TextInputLayout.this.b(false);
                        }
                    });
                }
                if (this.a != null && ViewCompat.getMinimumHeight(this.a) <= 0) {
                    this.a.setMinimumHeight(ViewCompat.getMinimumHeight(this.z));
                }
                this.z.setVisibility(0);
                this.z.setChecked(this.A);
                if (this.B == null) {
                    this.B = new ColorDrawable();
                }
                this.B.setBounds(0, 0, this.z.getMeasuredWidth(), 1);
                Drawable[] compoundDrawablesRelative = TextViewCompat.getCompoundDrawablesRelative(this.a);
                if (compoundDrawablesRelative[2] != this.B) {
                    this.C = compoundDrawablesRelative[2];
                }
                TextViewCompat.setCompoundDrawablesRelative(this.a, compoundDrawablesRelative[0], compoundDrawablesRelative[1], this.B, compoundDrawablesRelative[3]);
                this.z.setPadding(this.a.getPaddingLeft(), this.a.getPaddingTop(), this.a.getPaddingRight(), this.a.getPaddingBottom());
            } else {
                if (this.z != null && this.z.getVisibility() == 0) {
                    this.z.setVisibility(8);
                }
                if (this.B != null) {
                    Drawable[] compoundDrawablesRelative2 = TextViewCompat.getCompoundDrawablesRelative(this.a);
                    if (compoundDrawablesRelative2[2] == this.B) {
                        TextViewCompat.setCompoundDrawablesRelative(this.a, compoundDrawablesRelative2[0], compoundDrawablesRelative2[1], this.C, compoundDrawablesRelative2[3]);
                        this.B = null;
                    }
                }
            }
        }
    }

    public void setPasswordVisibilityToggleDrawable(@DrawableRes int i2) {
        setPasswordVisibilityToggleDrawable(i2 != 0 ? AppCompatResources.getDrawable(getContext(), i2) : null);
    }

    public void setPasswordVisibilityToggleDrawable(@Nullable Drawable drawable) {
        this.x = drawable;
        if (this.z != null) {
            this.z.setImageDrawable(drawable);
        }
    }

    public void setPasswordVisibilityToggleContentDescription(@StringRes int i2) {
        setPasswordVisibilityToggleContentDescription(i2 != 0 ? getResources().getText(i2) : null);
    }

    public void setPasswordVisibilityToggleContentDescription(@Nullable CharSequence charSequence) {
        this.y = charSequence;
        if (this.z != null) {
            this.z.setContentDescription(charSequence);
        }
    }

    @Nullable
    public Drawable getPasswordVisibilityToggleDrawable() {
        return this.x;
    }

    @Nullable
    public CharSequence getPasswordVisibilityToggleContentDescription() {
        return this.y;
    }

    public boolean isPasswordVisibilityToggleEnabled() {
        return this.w;
    }

    public void setPasswordVisibilityToggleEnabled(boolean z2) {
        if (this.w != z2) {
            this.w = z2;
            if (!z2 && this.A && this.a != null) {
                this.a.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            this.A = false;
            e();
        }
    }

    public void setPasswordVisibilityToggleTintList(@Nullable ColorStateList colorStateList) {
        this.D = colorStateList;
        this.E = true;
        h();
    }

    public void setPasswordVisibilityToggleTintMode(@Nullable Mode mode) {
        this.F = mode;
        this.G = true;
        h();
    }

    /* access modifiers changed from: private */
    public void b(boolean z2) {
        if (this.w) {
            int selectionEnd = this.a.getSelectionEnd();
            if (f()) {
                this.a.setTransformationMethod(null);
                this.A = true;
            } else {
                this.a.setTransformationMethod(PasswordTransformationMethod.getInstance());
                this.A = false;
            }
            this.z.setChecked(this.A);
            if (z2) {
                this.z.jumpDrawablesToCurrentState();
            }
            this.a.setSelection(selectionEnd);
        }
    }

    private boolean f() {
        return this.a != null && (this.a.getTransformationMethod() instanceof PasswordTransformationMethod);
    }

    private boolean g() {
        return this.w && (f() || this.A);
    }

    private void h() {
        if (this.x == null) {
            return;
        }
        if (this.E || this.G) {
            this.x = DrawableCompat.wrap(this.x).mutate();
            if (this.E) {
                DrawableCompat.setTintList(this.x, this.D);
            }
            if (this.G) {
                DrawableCompat.setTintMode(this.x, this.F);
            }
            if (this.z != null && this.z.getDrawable() != this.x) {
                this.z.setImageDrawable(this.x);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
        if (this.g && this.a != null) {
            Rect rect = this.j;
            ViewGroupUtils.getDescendantRect(this, this.a, rect);
            int compoundPaddingLeft = rect.left + this.a.getCompoundPaddingLeft();
            int compoundPaddingRight = rect.right - this.a.getCompoundPaddingRight();
            this.d.a(compoundPaddingLeft, rect.top + this.a.getCompoundPaddingTop(), compoundPaddingRight, rect.bottom - this.a.getCompoundPaddingBottom());
            this.d.b(compoundPaddingLeft, getPaddingTop(), compoundPaddingRight, (i5 - i3) - getPaddingBottom());
            this.d.i();
        }
    }

    private void c(boolean z2) {
        if (this.L != null && this.L.isRunning()) {
            this.L.cancel();
        }
        if (!z2 || !this.K) {
            this.d.b(1.0f);
        } else {
            a(1.0f);
        }
        this.J = false;
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        if (!this.N) {
            boolean z2 = true;
            this.N = true;
            super.drawableStateChanged();
            int[] drawableState = getDrawableState();
            if (!ViewCompat.isLaidOut(this) || !isEnabled()) {
                z2 = false;
            }
            a(z2);
            c();
            if (this.d != null ? this.d.a(drawableState) | false : false) {
                invalidate();
            }
            this.N = false;
        }
    }

    private void d(boolean z2) {
        if (this.L != null && this.L.isRunning()) {
            this.L.cancel();
        }
        if (!z2 || !this.K) {
            this.d.b((float) BitmapDescriptorFactory.HUE_RED);
        } else {
            a((float) BitmapDescriptorFactory.HUE_RED);
        }
        this.J = true;
    }

    /* access modifiers changed from: 0000 */
    @VisibleForTesting
    public void a(float f2) {
        if (this.d.g() != f2) {
            if (this.L == null) {
                this.L = new ValueAnimator();
                this.L.setInterpolator(AnimationUtils.a);
                this.L.setDuration(200);
                this.L.addUpdateListener(new AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        TextInputLayout.this.d.b(((Float) valueAnimator.getAnimatedValue()).floatValue());
                    }
                });
            }
            this.L.setFloatValues(new float[]{this.d.g(), f2});
            this.L.start();
        }
    }

    private static boolean a(int[] iArr, int i2) {
        for (int i3 : iArr) {
            if (i3 == i2) {
                return true;
            }
        }
        return false;
    }
}
