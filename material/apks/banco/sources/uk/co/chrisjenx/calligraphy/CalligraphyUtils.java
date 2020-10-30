package uk.co.chrisjenx.calligraphy;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;
import android.widget.TextView.BufferType;

public final class CalligraphyUtils {
    public static final int[] ANDROID_ATTR_TEXT_APPEARANCE = {16842804};
    private static Boolean sAppCompatViewCheck;
    private static Boolean sToolbarCheck;

    public static CharSequence applyTypefaceSpan(CharSequence charSequence, Typeface typeface) {
        if (charSequence != null && charSequence.length() > 0) {
            if (!(charSequence instanceof Spannable)) {
                charSequence = new SpannableString(charSequence);
            }
            ((Spannable) charSequence).setSpan(TypefaceUtils.getSpan(typeface), 0, charSequence.length(), 33);
        }
        return charSequence;
    }

    public static boolean applyFontToTextView(TextView textView, Typeface typeface) {
        return applyFontToTextView(textView, typeface, false);
    }

    public static boolean applyFontToTextView(TextView textView, final Typeface typeface, boolean z) {
        if (textView == null || typeface == null) {
            return false;
        }
        textView.setPaintFlags(textView.getPaintFlags() | 128 | 1);
        textView.setTypeface(typeface);
        if (z) {
            textView.setText(applyTypefaceSpan(textView.getText(), typeface), BufferType.SPANNABLE);
            textView.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                public void afterTextChanged(Editable editable) {
                    CalligraphyUtils.applyTypefaceSpan(editable, typeface);
                }
            });
        }
        return true;
    }

    public static boolean applyFontToTextView(Context context, TextView textView, String str) {
        return applyFontToTextView(context, textView, str, false);
    }

    static boolean applyFontToTextView(Context context, TextView textView, String str, boolean z) {
        if (textView == null || context == null) {
            return false;
        }
        return applyFontToTextView(textView, TypefaceUtils.load(context.getAssets(), str), z);
    }

    static void applyFontToTextView(Context context, TextView textView, CalligraphyConfig calligraphyConfig) {
        applyFontToTextView(context, textView, calligraphyConfig, false);
    }

    static void applyFontToTextView(Context context, TextView textView, CalligraphyConfig calligraphyConfig, boolean z) {
        if (context != null && textView != null && calligraphyConfig != null && calligraphyConfig.isFontSet()) {
            applyFontToTextView(context, textView, calligraphyConfig.getFontPath(), z);
        }
    }

    public static void applyFontToTextView(Context context, TextView textView, CalligraphyConfig calligraphyConfig, String str) {
        applyFontToTextView(context, textView, calligraphyConfig, str, false);
    }

    static void applyFontToTextView(Context context, TextView textView, CalligraphyConfig calligraphyConfig, String str, boolean z) {
        if (context != null && textView != null && calligraphyConfig != null) {
            if (TextUtils.isEmpty(str) || !applyFontToTextView(context, textView, str, z)) {
                applyFontToTextView(context, textView, calligraphyConfig, z);
            }
        }
    }

    static String pullFontPathFromView(Context context, AttributeSet attributeSet, int[] iArr) {
        String str;
        if (iArr == null || attributeSet == null) {
            return null;
        }
        try {
            String resourceEntryName = context.getResources().getResourceEntryName(iArr[0]);
            int attributeResourceValue = attributeSet.getAttributeResourceValue(null, resourceEntryName, -1);
            if (attributeResourceValue > 0) {
                str = context.getString(attributeResourceValue);
            } else {
                str = attributeSet.getAttributeValue(null, resourceEntryName);
            }
            return str;
        } catch (NotFoundException unused) {
            return null;
        }
    }

    static String pullFontPathFromStyle(Context context, AttributeSet attributeSet, int[] iArr) {
        if (iArr == null || attributeSet == null) {
            return null;
        }
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr);
        if (obtainStyledAttributes != null) {
            try {
                String string = obtainStyledAttributes.getString(0);
                if (!TextUtils.isEmpty(string)) {
                    obtainStyledAttributes.recycle();
                    return string;
                }
            } catch (Exception unused) {
            } catch (Throwable th) {
                obtainStyledAttributes.recycle();
                throw th;
            }
            obtainStyledAttributes.recycle();
        }
        return null;
    }

    static String pullFontPathFromTextAppearance(Context context, AttributeSet attributeSet, int[] iArr) {
        if (iArr == null || attributeSet == null) {
            return null;
        }
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, ANDROID_ATTR_TEXT_APPEARANCE);
        int i = -1;
        if (obtainStyledAttributes != null) {
            try {
                i = obtainStyledAttributes.getResourceId(0, -1);
            } catch (Exception unused) {
                return null;
            } finally {
                obtainStyledAttributes.recycle();
            }
        }
        TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(i, iArr);
        if (obtainStyledAttributes2 == null) {
            return null;
        }
        try {
            return obtainStyledAttributes2.getString(0);
        } catch (Exception unused2) {
            return null;
        } finally {
            obtainStyledAttributes2.recycle();
        }
    }

    static String pullFontPathFromTheme(Context context, int i, int[] iArr) {
        if (i == -1 || iArr == null) {
            return null;
        }
        Theme theme = context.getTheme();
        TypedValue typedValue = new TypedValue();
        theme.resolveAttribute(i, typedValue, true);
        TypedArray obtainStyledAttributes = theme.obtainStyledAttributes(typedValue.resourceId, iArr);
        try {
            return obtainStyledAttributes.getString(0);
        } catch (Exception unused) {
            return null;
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    static String pullFontPathFromTheme(Context context, int i, int i2, int[] iArr) {
        if (i == -1 || iArr == null) {
            return null;
        }
        Theme theme = context.getTheme();
        TypedValue typedValue = new TypedValue();
        theme.resolveAttribute(i, typedValue, true);
        TypedArray obtainStyledAttributes = theme.obtainStyledAttributes(typedValue.resourceId, new int[]{i2});
        try {
            int resourceId = obtainStyledAttributes.getResourceId(0, -1);
            if (resourceId == -1) {
                return null;
            }
            TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(resourceId, iArr);
            if (obtainStyledAttributes2 == null) {
                return null;
            }
            try {
                return obtainStyledAttributes2.getString(0);
            } catch (Exception unused) {
                return null;
            } finally {
                obtainStyledAttributes2.recycle();
            }
        } catch (Exception unused2) {
            return null;
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    static boolean canCheckForV7Toolbar() {
        if (sToolbarCheck == null) {
            try {
                Class.forName("android.support.v7.widget.Toolbar");
                sToolbarCheck = Boolean.TRUE;
            } catch (ClassNotFoundException unused) {
                sToolbarCheck = Boolean.FALSE;
            }
        }
        return sToolbarCheck.booleanValue();
    }

    static boolean canAddV7AppCompatViews() {
        if (sAppCompatViewCheck == null) {
            try {
                Class.forName("android.support.v7.widget.AppCompatTextView");
                sAppCompatViewCheck = Boolean.TRUE;
            } catch (ClassNotFoundException unused) {
                sAppCompatViewCheck = Boolean.FALSE;
            }
        }
        return sAppCompatViewCheck.booleanValue();
    }

    private CalligraphyUtils() {
    }
}
