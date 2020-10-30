package android.support.v4.widget;

import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.PopupWindow;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class PopupWindowCompat {
    static final PopupWindowCompatBaseImpl a;

    @RequiresApi(19)
    static class PopupWindowCompatApi19Impl extends PopupWindowCompatBaseImpl {
        PopupWindowCompatApi19Impl() {
        }

        public void a(PopupWindow popupWindow, View view, int i, int i2, int i3) {
            popupWindow.showAsDropDown(view, i, i2, i3);
        }
    }

    @RequiresApi(21)
    static class PopupWindowCompatApi21Impl extends PopupWindowCompatApi19Impl {
        private static Field a;

        PopupWindowCompatApi21Impl() {
        }

        static {
            try {
                a = PopupWindow.class.getDeclaredField("mOverlapAnchor");
                a.setAccessible(true);
            } catch (NoSuchFieldException e) {
                Log.i("PopupWindowCompatApi21", "Could not fetch mOverlapAnchor field from PopupWindow", e);
            }
        }

        public void a(PopupWindow popupWindow, boolean z) {
            if (a != null) {
                try {
                    a.set(popupWindow, Boolean.valueOf(z));
                } catch (IllegalAccessException e) {
                    Log.i("PopupWindowCompatApi21", "Could not set overlap anchor field in PopupWindow", e);
                }
            }
        }

        public boolean a(PopupWindow popupWindow) {
            if (a != null) {
                try {
                    return ((Boolean) a.get(popupWindow)).booleanValue();
                } catch (IllegalAccessException e) {
                    Log.i("PopupWindowCompatApi21", "Could not get overlap anchor field in PopupWindow", e);
                }
            }
            return false;
        }
    }

    @RequiresApi(23)
    static class PopupWindowCompatApi23Impl extends PopupWindowCompatApi21Impl {
        PopupWindowCompatApi23Impl() {
        }

        public void a(PopupWindow popupWindow, boolean z) {
            popupWindow.setOverlapAnchor(z);
        }

        public boolean a(PopupWindow popupWindow) {
            return popupWindow.getOverlapAnchor();
        }

        public void a(PopupWindow popupWindow, int i) {
            popupWindow.setWindowLayoutType(i);
        }

        public int b(PopupWindow popupWindow) {
            return popupWindow.getWindowLayoutType();
        }
    }

    static class PopupWindowCompatBaseImpl {
        private static Method a;
        private static boolean b;
        private static Method c;
        private static boolean d;

        public void a(PopupWindow popupWindow, boolean z) {
        }

        public boolean a(PopupWindow popupWindow) {
            return false;
        }

        PopupWindowCompatBaseImpl() {
        }

        public void a(PopupWindow popupWindow, View view, int i, int i2, int i3) {
            if ((GravityCompat.getAbsoluteGravity(i3, ViewCompat.getLayoutDirection(view)) & 7) == 5) {
                i -= popupWindow.getWidth() - view.getWidth();
            }
            popupWindow.showAsDropDown(view, i, i2);
        }

        public void a(PopupWindow popupWindow, int i) {
            if (!b) {
                try {
                    a = PopupWindow.class.getDeclaredMethod("setWindowLayoutType", new Class[]{Integer.TYPE});
                    a.setAccessible(true);
                } catch (Exception unused) {
                }
                b = true;
            }
            if (a != null) {
                try {
                    a.invoke(popupWindow, new Object[]{Integer.valueOf(i)});
                } catch (Exception unused2) {
                }
            }
        }

        public int b(PopupWindow popupWindow) {
            if (!d) {
                try {
                    c = PopupWindow.class.getDeclaredMethod("getWindowLayoutType", new Class[0]);
                    c.setAccessible(true);
                } catch (Exception unused) {
                }
                d = true;
            }
            if (c != null) {
                try {
                    return ((Integer) c.invoke(popupWindow, new Object[0])).intValue();
                } catch (Exception unused2) {
                }
            }
            return 0;
        }
    }

    static {
        if (VERSION.SDK_INT >= 23) {
            a = new PopupWindowCompatApi23Impl();
        } else if (VERSION.SDK_INT >= 21) {
            a = new PopupWindowCompatApi21Impl();
        } else if (VERSION.SDK_INT >= 19) {
            a = new PopupWindowCompatApi19Impl();
        } else {
            a = new PopupWindowCompatBaseImpl();
        }
    }

    private PopupWindowCompat() {
    }

    public static void showAsDropDown(@NonNull PopupWindow popupWindow, @NonNull View view, int i, int i2, int i3) {
        a.a(popupWindow, view, i, i2, i3);
    }

    public static void setOverlapAnchor(@NonNull PopupWindow popupWindow, boolean z) {
        a.a(popupWindow, z);
    }

    public static boolean getOverlapAnchor(@NonNull PopupWindow popupWindow) {
        return a.a(popupWindow);
    }

    public static void setWindowLayoutType(@NonNull PopupWindow popupWindow, int i) {
        a.a(popupWindow, i);
    }

    public static int getWindowLayoutType(@NonNull PopupWindow popupWindow) {
        return a.b(popupWindow);
    }
}
