package android.support.graphics.drawable;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

public interface Animatable2Compat extends Animatable {

    public static abstract class AnimationCallback {
        android.graphics.drawable.Animatable2.AnimationCallback a;

        public void onAnimationEnd(Drawable drawable) {
        }

        public void onAnimationStart(Drawable drawable) {
        }

        /* access modifiers changed from: 0000 */
        @RequiresApi(23)
        public android.graphics.drawable.Animatable2.AnimationCallback a() {
            if (this.a == null) {
                this.a = new android.graphics.drawable.Animatable2.AnimationCallback() {
                    public void onAnimationStart(Drawable drawable) {
                        AnimationCallback.this.onAnimationStart(drawable);
                    }

                    public void onAnimationEnd(Drawable drawable) {
                        AnimationCallback.this.onAnimationEnd(drawable);
                    }
                };
            }
            return this.a;
        }
    }

    void clearAnimationCallbacks();

    void registerAnimationCallback(@NonNull AnimationCallback animationCallback);

    boolean unregisterAnimationCallback(@NonNull AnimationCallback animationCallback);
}
