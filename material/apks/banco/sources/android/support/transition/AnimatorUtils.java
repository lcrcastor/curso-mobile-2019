package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;

class AnimatorUtils {
    private static final AnimatorUtilsImpl a;

    AnimatorUtils() {
    }

    static {
        if (VERSION.SDK_INT >= 19) {
            a = new AnimatorUtilsApi19();
        } else {
            a = new AnimatorUtilsApi14();
        }
    }

    static void a(@NonNull Animator animator, @NonNull AnimatorListenerAdapter animatorListenerAdapter) {
        a.a(animator, animatorListenerAdapter);
    }

    static void a(@NonNull Animator animator) {
        a.a(animator);
    }

    static void b(@NonNull Animator animator) {
        a.b(animator);
    }
}
