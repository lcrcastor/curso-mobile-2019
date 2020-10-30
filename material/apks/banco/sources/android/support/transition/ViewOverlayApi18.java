package android.support.transition;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewOverlay;

@RequiresApi(18)
class ViewOverlayApi18 implements ViewOverlayImpl {
    private final ViewOverlay a;

    ViewOverlayApi18(@NonNull View view) {
        this.a = view.getOverlay();
    }

    public void a(@NonNull Drawable drawable) {
        this.a.add(drawable);
    }

    public void b(@NonNull Drawable drawable) {
        this.a.remove(drawable);
    }
}
