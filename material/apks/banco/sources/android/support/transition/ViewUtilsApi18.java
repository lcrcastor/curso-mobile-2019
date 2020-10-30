package android.support.transition;

import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;

@RequiresApi(18)
class ViewUtilsApi18 extends ViewUtilsApi14 {
    ViewUtilsApi18() {
    }

    public ViewOverlayImpl a(@NonNull View view) {
        return new ViewOverlayApi18(view);
    }

    public WindowIdImpl b(@NonNull View view) {
        return new WindowIdApi18(view);
    }
}
