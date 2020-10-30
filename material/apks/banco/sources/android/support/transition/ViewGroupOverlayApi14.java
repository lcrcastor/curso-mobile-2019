package android.support.transition;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;

@RequiresApi(14)
class ViewGroupOverlayApi14 extends ViewOverlayApi14 implements ViewGroupOverlayImpl {
    ViewGroupOverlayApi14(Context context, ViewGroup viewGroup, View view) {
        super(context, viewGroup, view);
    }

    static ViewGroupOverlayApi14 a(ViewGroup viewGroup) {
        return (ViewGroupOverlayApi14) ViewOverlayApi14.d(viewGroup);
    }

    public void a(@NonNull View view) {
        this.a.a(view);
    }

    public void b(@NonNull View view) {
        this.a.b(view);
    }
}
