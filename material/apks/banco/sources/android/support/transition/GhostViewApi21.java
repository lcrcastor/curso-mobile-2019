package android.support.transition;

import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RequiresApi(21)
class GhostViewApi21 implements GhostViewImpl {
    private static Class<?> a;
    private static boolean b;
    /* access modifiers changed from: private */
    public static Method c;
    private static boolean d;
    /* access modifiers changed from: private */
    public static Method e;
    private static boolean f;
    private final View g;

    static class Creator implements android.support.transition.GhostViewImpl.Creator {
        Creator() {
        }

        public GhostViewImpl addGhost(View view, ViewGroup viewGroup, Matrix matrix) {
            GhostViewApi21.f();
            if (GhostViewApi21.c != null) {
                try {
                    return new GhostViewApi21((View) GhostViewApi21.c.invoke(null, new Object[]{view, viewGroup, matrix}));
                } catch (IllegalAccessException unused) {
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e.getCause());
                }
            }
            return null;
        }

        public void removeGhost(View view) {
            GhostViewApi21.g();
            if (GhostViewApi21.e != null) {
                try {
                    GhostViewApi21.e.invoke(null, new Object[]{view});
                } catch (IllegalAccessException unused) {
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e.getCause());
                }
            }
        }
    }

    public void a(ViewGroup viewGroup, View view) {
    }

    private GhostViewApi21(@NonNull View view) {
        this.g = view;
    }

    public void setVisibility(int i) {
        this.g.setVisibility(i);
    }

    private static void e() {
        if (!b) {
            try {
                a = Class.forName("android.view.GhostView");
            } catch (ClassNotFoundException e2) {
                Log.i("GhostViewApi21", "Failed to retrieve GhostView class", e2);
            }
            b = true;
        }
    }

    /* access modifiers changed from: private */
    public static void f() {
        if (!d) {
            try {
                e();
                c = a.getDeclaredMethod("addGhost", new Class[]{View.class, ViewGroup.class, Matrix.class});
                c.setAccessible(true);
            } catch (NoSuchMethodException e2) {
                Log.i("GhostViewApi21", "Failed to retrieve addGhost method", e2);
            }
            d = true;
        }
    }

    /* access modifiers changed from: private */
    public static void g() {
        if (!f) {
            try {
                e();
                e = a.getDeclaredMethod("removeGhost", new Class[]{View.class});
                e.setAccessible(true);
            } catch (NoSuchMethodException e2) {
                Log.i("GhostViewApi21", "Failed to retrieve removeGhost method", e2);
            }
            f = true;
        }
    }
}
