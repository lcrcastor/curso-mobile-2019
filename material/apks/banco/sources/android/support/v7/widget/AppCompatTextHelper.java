package android.support.v7.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources.NotFoundException;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.content.res.ResourcesCompat.FontCallback;
import android.support.v4.widget.AutoSizeableTextView;
import android.support.v7.appcompat.R;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.widget.TextView;
import java.lang.ref.WeakReference;

@RequiresApi(9)
class AppCompatTextHelper {
    final TextView a;
    private TintInfo b;
    private TintInfo c;
    private TintInfo d;
    private TintInfo e;
    @NonNull
    private final AppCompatTextViewAutoSizeHelper f;
    private int g = 0;
    private Typeface h;
    private boolean i;

    static AppCompatTextHelper a(TextView textView) {
        if (VERSION.SDK_INT >= 17) {
            return new AppCompatTextHelperV17(textView);
        }
        return new AppCompatTextHelper(textView);
    }

    AppCompatTextHelper(TextView textView) {
        this.a = textView;
        this.f = new AppCompatTextViewAutoSizeHelper(this.a);
    }

    /* access modifiers changed from: 0000 */
    @SuppressLint({"NewApi"})
    public void a(AttributeSet attributeSet, int i2) {
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        boolean z;
        boolean z2;
        Context context = this.a.getContext();
        AppCompatDrawableManager appCompatDrawableManager = AppCompatDrawableManager.get();
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, R.styleable.AppCompatTextHelper, i2, 0);
        int resourceId = obtainStyledAttributes.getResourceId(R.styleable.AppCompatTextHelper_android_textAppearance, -1);
        if (obtainStyledAttributes.hasValue(R.styleable.AppCompatTextHelper_android_drawableLeft)) {
            this.b = a(context, appCompatDrawableManager, obtainStyledAttributes.getResourceId(R.styleable.AppCompatTextHelper_android_drawableLeft, 0));
        }
        if (obtainStyledAttributes.hasValue(R.styleable.AppCompatTextHelper_android_drawableTop)) {
            this.c = a(context, appCompatDrawableManager, obtainStyledAttributes.getResourceId(R.styleable.AppCompatTextHelper_android_drawableTop, 0));
        }
        if (obtainStyledAttributes.hasValue(R.styleable.AppCompatTextHelper_android_drawableRight)) {
            this.d = a(context, appCompatDrawableManager, obtainStyledAttributes.getResourceId(R.styleable.AppCompatTextHelper_android_drawableRight, 0));
        }
        if (obtainStyledAttributes.hasValue(R.styleable.AppCompatTextHelper_android_drawableBottom)) {
            this.e = a(context, appCompatDrawableManager, obtainStyledAttributes.getResourceId(R.styleable.AppCompatTextHelper_android_drawableBottom, 0));
        }
        obtainStyledAttributes.recycle();
        boolean z3 = this.a.getTransformationMethod() instanceof PasswordTransformationMethod;
        ColorStateList colorStateList3 = null;
        if (resourceId != -1) {
            TintTypedArray obtainStyledAttributes2 = TintTypedArray.obtainStyledAttributes(context, resourceId, R.styleable.TextAppearance);
            if (z3 || !obtainStyledAttributes2.hasValue(R.styleable.TextAppearance_textAllCaps)) {
                z2 = false;
                z = false;
            } else {
                z = obtainStyledAttributes2.getBoolean(R.styleable.TextAppearance_textAllCaps, false);
                z2 = true;
            }
            a(context, obtainStyledAttributes2);
            if (VERSION.SDK_INT < 23) {
                ColorStateList colorStateList4 = obtainStyledAttributes2.hasValue(R.styleable.TextAppearance_android_textColor) ? obtainStyledAttributes2.getColorStateList(R.styleable.TextAppearance_android_textColor) : null;
                colorStateList = obtainStyledAttributes2.hasValue(R.styleable.TextAppearance_android_textColorHint) ? obtainStyledAttributes2.getColorStateList(R.styleable.TextAppearance_android_textColorHint) : null;
                if (obtainStyledAttributes2.hasValue(R.styleable.TextAppearance_android_textColorLink)) {
                    colorStateList3 = obtainStyledAttributes2.getColorStateList(R.styleable.TextAppearance_android_textColorLink);
                }
                ColorStateList colorStateList5 = colorStateList4;
                colorStateList2 = colorStateList3;
                colorStateList3 = colorStateList5;
            } else {
                colorStateList2 = null;
                colorStateList = null;
            }
            obtainStyledAttributes2.recycle();
        } else {
            colorStateList2 = null;
            colorStateList = null;
            z2 = false;
            z = false;
        }
        TintTypedArray obtainStyledAttributes3 = TintTypedArray.obtainStyledAttributes(context, attributeSet, R.styleable.TextAppearance, i2, 0);
        if (!z3 && obtainStyledAttributes3.hasValue(R.styleable.TextAppearance_textAllCaps)) {
            z = obtainStyledAttributes3.getBoolean(R.styleable.TextAppearance_textAllCaps, false);
            z2 = true;
        }
        if (VERSION.SDK_INT < 23) {
            if (obtainStyledAttributes3.hasValue(R.styleable.TextAppearance_android_textColor)) {
                colorStateList3 = obtainStyledAttributes3.getColorStateList(R.styleable.TextAppearance_android_textColor);
            }
            if (obtainStyledAttributes3.hasValue(R.styleable.TextAppearance_android_textColorHint)) {
                colorStateList = obtainStyledAttributes3.getColorStateList(R.styleable.TextAppearance_android_textColorHint);
            }
            if (obtainStyledAttributes3.hasValue(R.styleable.TextAppearance_android_textColorLink)) {
                colorStateList2 = obtainStyledAttributes3.getColorStateList(R.styleable.TextAppearance_android_textColorLink);
            }
        }
        a(context, obtainStyledAttributes3);
        obtainStyledAttributes3.recycle();
        if (colorStateList3 != null) {
            this.a.setTextColor(colorStateList3);
        }
        if (colorStateList != null) {
            this.a.setHintTextColor(colorStateList);
        }
        if (colorStateList2 != null) {
            this.a.setLinkTextColor(colorStateList2);
        }
        if (!z3 && z2) {
            a(z);
        }
        if (this.h != null) {
            this.a.setTypeface(this.h, this.g);
        }
        this.f.a(attributeSet, i2);
        if (AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE && this.f.a() != 0) {
            int[] e2 = this.f.e();
            if (e2.length <= 0) {
                return;
            }
            if (((float) this.a.getAutoSizeStepGranularity()) != -1.0f) {
                this.a.setAutoSizeTextTypeUniformWithConfiguration(this.f.c(), this.f.d(), this.f.b(), 0);
            } else {
                this.a.setAutoSizeTextTypeUniformWithPresetSizes(e2, 0);
            }
        }
    }

    private void a(Context context, TintTypedArray tintTypedArray) {
        this.g = tintTypedArray.getInt(R.styleable.TextAppearance_android_textStyle, this.g);
        boolean z = true;
        if (tintTypedArray.hasValue(R.styleable.TextAppearance_android_fontFamily) || tintTypedArray.hasValue(R.styleable.TextAppearance_fontFamily)) {
            this.h = null;
            int i2 = tintTypedArray.hasValue(R.styleable.TextAppearance_fontFamily) ? R.styleable.TextAppearance_fontFamily : R.styleable.TextAppearance_android_fontFamily;
            if (!context.isRestricted()) {
                final WeakReference weakReference = new WeakReference(this.a);
                try {
                    this.h = tintTypedArray.getFont(i2, this.g, new FontCallback() {
                        public void onFontRetrievalFailed(int i) {
                        }

                        public void onFontRetrieved(@NonNull Typeface typeface) {
                            AppCompatTextHelper.this.a(weakReference, typeface);
                        }
                    });
                    if (this.h != null) {
                        z = false;
                    }
                    this.i = z;
                } catch (NotFoundException | UnsupportedOperationException unused) {
                }
            }
            if (this.h == null) {
                String string = tintTypedArray.getString(i2);
                if (string != null) {
                    this.h = Typeface.create(string, this.g);
                }
            }
            return;
        }
        if (tintTypedArray.hasValue(R.styleable.TextAppearance_android_typeface)) {
            this.i = false;
            switch (tintTypedArray.getInt(R.styleable.TextAppearance_android_typeface, 1)) {
                case 1:
                    this.h = Typeface.SANS_SERIF;
                    break;
                case 2:
                    this.h = Typeface.SERIF;
                    break;
                case 3:
                    this.h = Typeface.MONOSPACE;
                    break;
            }
        }
    }

    /* access modifiers changed from: private */
    public void a(WeakReference<TextView> weakReference, Typeface typeface) {
        if (this.i) {
            this.h = typeface;
            TextView textView = (TextView) weakReference.get();
            if (textView != null) {
                textView.setTypeface(typeface, this.g);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(Context context, int i2) {
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, i2, R.styleable.TextAppearance);
        if (obtainStyledAttributes.hasValue(R.styleable.TextAppearance_textAllCaps)) {
            a(obtainStyledAttributes.getBoolean(R.styleable.TextAppearance_textAllCaps, false));
        }
        if (VERSION.SDK_INT < 23 && obtainStyledAttributes.hasValue(R.styleable.TextAppearance_android_textColor)) {
            ColorStateList colorStateList = obtainStyledAttributes.getColorStateList(R.styleable.TextAppearance_android_textColor);
            if (colorStateList != null) {
                this.a.setTextColor(colorStateList);
            }
        }
        a(context, obtainStyledAttributes);
        obtainStyledAttributes.recycle();
        if (this.h != null) {
            this.a.setTypeface(this.h, this.g);
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(boolean z) {
        this.a.setAllCaps(z);
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        if (this.b != null || this.c != null || this.d != null || this.e != null) {
            Drawable[] compoundDrawables = this.a.getCompoundDrawables();
            a(compoundDrawables[0], this.b);
            a(compoundDrawables[1], this.c);
            a(compoundDrawables[2], this.d);
            a(compoundDrawables[3], this.e);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(Drawable drawable, TintInfo tintInfo) {
        if (drawable != null && tintInfo != null) {
            AppCompatDrawableManager.a(drawable, tintInfo, this.a.getDrawableState());
        }
    }

    protected static TintInfo a(Context context, AppCompatDrawableManager appCompatDrawableManager, int i2) {
        ColorStateList a2 = appCompatDrawableManager.a(context, i2);
        if (a2 == null) {
            return null;
        }
        TintInfo tintInfo = new TintInfo();
        tintInfo.d = true;
        tintInfo.a = a2;
        return tintInfo;
    }

    /* access modifiers changed from: 0000 */
    @RestrictTo({Scope.LIBRARY_GROUP})
    public void a(boolean z, int i2, int i3, int i4, int i5) {
        if (!AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE) {
            b();
        }
    }

    /* access modifiers changed from: 0000 */
    @RestrictTo({Scope.LIBRARY_GROUP})
    public void a(int i2, float f2) {
        if (!AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE && !c()) {
            b(i2, f2);
        }
    }

    /* access modifiers changed from: 0000 */
    @RestrictTo({Scope.LIBRARY_GROUP})
    public void b() {
        this.f.f();
    }

    /* access modifiers changed from: 0000 */
    @RestrictTo({Scope.LIBRARY_GROUP})
    public boolean c() {
        return this.f.g();
    }

    private void b(int i2, float f2) {
        this.f.a(i2, f2);
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2) {
        this.f.a(i2);
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2, int i3, int i4, int i5) {
        this.f.a(i2, i3, i4, i5);
    }

    /* access modifiers changed from: 0000 */
    public void a(@NonNull int[] iArr, int i2) {
        this.f.a(iArr, i2);
    }

    /* access modifiers changed from: 0000 */
    public int d() {
        return this.f.a();
    }

    /* access modifiers changed from: 0000 */
    public int e() {
        return this.f.b();
    }

    /* access modifiers changed from: 0000 */
    public int f() {
        return this.f.c();
    }

    /* access modifiers changed from: 0000 */
    public int g() {
        return this.f.d();
    }

    /* access modifiers changed from: 0000 */
    public int[] h() {
        return this.f.e();
    }
}
