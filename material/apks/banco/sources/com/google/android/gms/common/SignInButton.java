package com.google.android.gms.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import com.google.android.gms.R;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzag;
import com.google.android.gms.common.internal.zzah;
import com.google.android.gms.dynamic.zzg.zza;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class SignInButton extends FrameLayout implements OnClickListener {
    public static final int COLOR_AUTO = 2;
    public static final int COLOR_DARK = 0;
    public static final int COLOR_LIGHT = 1;
    public static final int SIZE_ICON_ONLY = 2;
    public static final int SIZE_STANDARD = 0;
    public static final int SIZE_WIDE = 1;
    private int a;
    private int b;
    private View c;
    private OnClickListener d;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ButtonSize {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ColorScheme {
    }

    public SignInButton(Context context) {
        this(context, null);
    }

    public SignInButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SignInButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.d = null;
        a(context, attributeSet);
        setStyle(this.a, this.b);
    }

    private static Button a(Context context, int i, int i2) {
        zzah zzah = new zzah(context);
        zzah.zza(context.getResources(), i, i2);
        return zzah;
    }

    private void a(Context context) {
        if (this.c != null) {
            removeView(this.c);
        }
        try {
            this.c = zzag.zzb(context, this.a, this.b);
        } catch (zza unused) {
            Log.w("SignInButton", "Sign in button not found, using placeholder instead");
            this.c = a(context, this.a, this.b);
        }
        addView(this.c);
        this.c.setEnabled(isEnabled());
        this.c.setOnClickListener(this);
    }

    private void a(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.SignInButton, 0, 0);
        try {
            this.a = obtainStyledAttributes.getInt(R.styleable.SignInButton_buttonSize, 0);
            this.b = obtainStyledAttributes.getInt(R.styleable.SignInButton_colorScheme, 2);
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    public void onClick(View view) {
        if (this.d != null && view == this.c) {
            this.d.onClick(this);
        }
    }

    public void setColorScheme(int i) {
        setStyle(this.a, i);
    }

    public void setEnabled(boolean z) {
        super.setEnabled(z);
        this.c.setEnabled(z);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.d = onClickListener;
        if (this.c != null) {
            this.c.setOnClickListener(this);
        }
    }

    @Deprecated
    public void setScopes(Scope[] scopeArr) {
        setStyle(this.a, this.b);
    }

    public void setSize(int i) {
        setStyle(i, this.b);
    }

    public void setStyle(int i, int i2) {
        this.a = i;
        this.b = i2;
        a(getContext());
    }

    @Deprecated
    public void setStyle(int i, int i2, Scope[] scopeArr) {
        setStyle(i, i2);
    }
}
