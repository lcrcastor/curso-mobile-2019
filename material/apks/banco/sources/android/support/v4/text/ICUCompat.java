package android.support.v4.text;

import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

public final class ICUCompat {
    private static Method a;
    private static Method b;

    static {
        if (VERSION.SDK_INT >= 21) {
            try {
                b = Class.forName("libcore.icu.ICU").getMethod("addLikelySubtags", new Class[]{Locale.class});
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        } else {
            try {
                Class cls = Class.forName("libcore.icu.ICU");
                if (cls != null) {
                    a = cls.getMethod("getScript", new Class[]{String.class});
                    b = cls.getMethod("addLikelySubtags", new Class[]{String.class});
                }
            } catch (Exception e2) {
                a = null;
                b = null;
                Log.w("ICUCompat", e2);
            }
        }
    }

    @Nullable
    public static String maximizeAndGetScript(Locale locale) {
        if (VERSION.SDK_INT >= 21) {
            try {
                return ((Locale) b.invoke(null, new Object[]{locale})).getScript();
            } catch (InvocationTargetException e) {
                Log.w("ICUCompat", e);
                return locale.getScript();
            } catch (IllegalAccessException e2) {
                Log.w("ICUCompat", e2);
                return locale.getScript();
            }
        } else {
            String a2 = a(locale);
            if (a2 != null) {
                return a(a2);
            }
            return null;
        }
    }

    private static String a(String str) {
        try {
            if (a != null) {
                return (String) a.invoke(null, new Object[]{str});
            }
        } catch (IllegalAccessException e) {
            Log.w("ICUCompat", e);
        } catch (InvocationTargetException e2) {
            Log.w("ICUCompat", e2);
        }
        return null;
    }

    private static String a(Locale locale) {
        String locale2 = locale.toString();
        try {
            if (b != null) {
                return (String) b.invoke(null, new Object[]{locale2});
            }
        } catch (IllegalAccessException e) {
            Log.w("ICUCompat", e);
        } catch (InvocationTargetException e2) {
            Log.w("ICUCompat", e2);
        }
        return locale2;
    }

    private ICUCompat() {
    }
}
