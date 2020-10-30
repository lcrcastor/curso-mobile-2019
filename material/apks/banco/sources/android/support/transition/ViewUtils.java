package android.support.transition;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.util.Property;
import android.view.View;
import java.lang.reflect.Field;

class ViewUtils {
    static final Property<View, Float> a = new Property<View, Float>(Float.class, "translationAlpha") {
        /* renamed from: a */
        public Float get(View view) {
            return Float.valueOf(ViewUtils.c(view));
        }

        /* renamed from: a */
        public void set(View view, Float f) {
            ViewUtils.a(view, f.floatValue());
        }
    };
    static final Property<View, Rect> b = new Property<View, Rect>(Rect.class, "clipBounds") {
        /* renamed from: a */
        public Rect get(View view) {
            return ViewCompat.getClipBounds(view);
        }

        /* renamed from: a */
        public void set(View view, Rect rect) {
            ViewCompat.setClipBounds(view, rect);
        }
    };
    private static final ViewUtilsImpl c;
    private static Field d;
    private static boolean e;

    ViewUtils() {
    }

    static {
        if (VERSION.SDK_INT >= 22) {
            c = new ViewUtilsApi22();
        } else if (VERSION.SDK_INT >= 21) {
            c = new ViewUtilsApi21();
        } else if (VERSION.SDK_INT >= 19) {
            c = new ViewUtilsApi19();
        } else if (VERSION.SDK_INT >= 18) {
            c = new ViewUtilsApi18();
        } else {
            c = new ViewUtilsApi14();
        }
    }

    static ViewOverlayImpl a(@NonNull View view) {
        return c.a(view);
    }

    static WindowIdImpl b(@NonNull View view) {
        return c.b(view);
    }

    static void a(@NonNull View view, float f) {
        c.a(view, f);
    }

    static float c(@NonNull View view) {
        return c.c(view);
    }

    static void d(@NonNull View view) {
        c.d(view);
    }

    static void e(@NonNull View view) {
        c.e(view);
    }

    static void a(@NonNull View view, int i) {
        a();
        if (d != null) {
            try {
                d.setInt(view, i | (d.getInt(view) & -13));
            } catch (IllegalAccessException unused) {
            }
        }
    }

    static void a(@NonNull View view, @NonNull Matrix matrix) {
        c.a(view, matrix);
    }

    static void b(@NonNull View view, @NonNull Matrix matrix) {
        c.b(view, matrix);
    }

    static void c(@NonNull View view, @Nullable Matrix matrix) {
        c.c(view, matrix);
    }

    static void a(@NonNull View view, int i, int i2, int i3, int i4) {
        c.a(view, i, i2, i3, i4);
    }

    private static void a() {
        if (!e) {
            try {
                d = View.class.getDeclaredField("mViewFlags");
                d.setAccessible(true);
            } catch (NoSuchFieldException unused) {
                Log.i("ViewUtils", "fetchViewFlagsField: ");
            }
            e = true;
        }
    }
}
