package ar.com.santander.rio.mbanking.utils;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ScaleXSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import ar.com.santander.rio.mbanking.view.tables.BaseColumn.TypeStyle;

public class UtilStyleCommons {
    public static final float letterSpacing25 = -1.5f;
    public static final float letterSpacing35 = -1.5f;

    public static TypeStyle getTypeStyleValue(TypeStyle typeStyle) {
        if (TypeStyle.TYPE_STYLE_DATA_VALUE.equals(typeStyle)) {
            return TypeStyle.TYPE_STYLE_LABEL;
        }
        if (TypeStyle.TYPE_STYLE_LABEL.equals(typeStyle)) {
            return TypeStyle.TYPE_STYLE_DATA_VALUE;
        }
        return null;
    }

    public static void setLetterSpacing(View view, float f) {
        setLetterSpacing(view, f, true);
    }

    public static void setLetterSpacing(View view, float f, boolean z) {
        CharSequence charSequence;
        boolean z2 = view instanceof TextView;
        if (z2) {
            charSequence = ((TextView) view).getText();
            if (z) {
                view.setContentDescription(charSequence);
            }
        } else if (view instanceof Button) {
            charSequence = ((Button) view).getText();
            if (z) {
                view.setContentDescription(charSequence);
            }
        } else if (view instanceof EditText) {
            charSequence = ((EditText) view).getText();
            if (z) {
                view.setContentDescription(charSequence);
            }
        } else {
            charSequence = null;
        }
        if (charSequence != null) {
            StringBuilder sb = new StringBuilder();
            int i = 0;
            while (i < charSequence.length()) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("");
                sb2.append(charSequence.charAt(i));
                sb.append(sb2.toString());
                i++;
                if (i < charSequence.length()) {
                    sb.append(" ");
                }
            }
            SpannableString spannableString = new SpannableString(sb.toString());
            if (sb.toString().length() > 1) {
                for (int i2 = 1; i2 < sb.toString().length(); i2 += 2) {
                    spannableString.setSpan(new ScaleXSpan((1.0f + f) / 10.0f), i2, i2 + 1, 33);
                }
            }
            if (z2) {
                ((TextView) view).setText(spannableString, BufferType.SPANNABLE);
            } else if (view instanceof Button) {
                ((Button) view).setText(spannableString, BufferType.SPANNABLE);
            } else if (view instanceof EditText) {
                ((EditText) view).setText(spannableString, BufferType.SPANNABLE);
            }
        }
    }

    public static SpannableStringBuilder getBuilderForLetterSpacing(String str) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        if (str.length() > 0) {
            int i = 0;
            while (i < str.length()) {
                StringBuilder sb = new StringBuilder();
                sb.append("");
                sb.append(str.charAt(i));
                spannableStringBuilder.append(sb.toString());
                i++;
                if (i < str.length()) {
                    spannableStringBuilder.append(" ");
                }
            }
        }
        return spannableStringBuilder;
    }

    public static SpannableStringBuilder getBuilderLetterSpaced(SpannableStringBuilder spannableStringBuilder, float f) {
        if (spannableStringBuilder.toString().length() > 1) {
            for (int i = 1; i < spannableStringBuilder.toString().length(); i += 2) {
                spannableStringBuilder.setSpan(new ScaleXSpan((1.0f + f) / 10.0f), i, i + 1, 33);
            }
        }
        return spannableStringBuilder;
    }
}
