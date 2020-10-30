package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;
import com.google.android.gms.R;

public final class zzah extends Button {
    public zzah(Context context) {
        this(context, null);
    }

    public zzah(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 16842824);
    }

    private int a(int i, int i2, int i3) {
        switch (i) {
            case 0:
            case 1:
                return i3;
            case 2:
                return i2;
            default:
                StringBuilder sb = new StringBuilder(32);
                sb.append("Unknown button size: ");
                sb.append(i);
                throw new IllegalStateException(sb.toString());
        }
    }

    private int a(int i, int i2, int i3, int i4) {
        switch (i) {
            case 0:
                return i2;
            case 1:
                return i3;
            case 2:
                return i4;
            default:
                StringBuilder sb = new StringBuilder(33);
                sb.append("Unknown color scheme: ");
                sb.append(i);
                throw new IllegalStateException(sb.toString());
        }
    }

    private void a(Resources resources) {
        setTypeface(Typeface.DEFAULT_BOLD);
        setTextSize(14.0f);
        int i = (int) ((resources.getDisplayMetrics().density * 48.0f) + 0.5f);
        setMinHeight(i);
        setMinWidth(i);
    }

    private void a(Resources resources, int i, int i2) {
        setBackgroundDrawable(resources.getDrawable(a(i, a(i2, R.drawable.common_google_signin_btn_icon_dark, R.drawable.common_google_signin_btn_icon_light, R.drawable.common_google_signin_btn_icon_light), a(i2, R.drawable.common_google_signin_btn_text_dark, R.drawable.common_google_signin_btn_text_light, R.drawable.common_google_signin_btn_text_light))));
    }

    private void b(Resources resources, int i, int i2) {
        int i3;
        setTextColor((ColorStateList) zzac.zzy(resources.getColorStateList(a(i2, R.color.common_google_signin_btn_text_dark, R.color.common_google_signin_btn_text_light, R.color.common_google_signin_btn_text_light))));
        switch (i) {
            case 0:
                i3 = R.string.common_signin_button_text;
                break;
            case 1:
                i3 = R.string.common_signin_button_text_long;
                break;
            case 2:
                setText(null);
                break;
            default:
                StringBuilder sb = new StringBuilder(32);
                sb.append("Unknown button size: ");
                sb.append(i);
                throw new IllegalStateException(sb.toString());
        }
        setText(resources.getString(i3));
        setTransformationMethod(null);
    }

    public void zza(Resources resources, int i, int i2) {
        a(resources);
        a(resources, i, i2);
        b(resources, i, i2);
    }
}
