package android.support.transition;

import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

class ViewGroupUtils {
    private static final ViewGroupUtilsImpl a;

    ViewGroupUtils() {
    }

    static {
        if (VERSION.SDK_INT >= 18) {
            a = new ViewGroupUtilsApi18();
        } else {
            a = new ViewGroupUtilsApi14();
        }
    }

    static ViewGroupOverlayImpl a(@NonNull ViewGroup viewGroup) {
        return a.a(viewGroup);
    }

    static void a(@NonNull ViewGroup viewGroup, boolean z) {
        a.a(viewGroup, z);
    }
}
