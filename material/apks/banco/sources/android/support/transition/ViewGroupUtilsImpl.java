package android.support.transition;

import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.ViewGroup;

@RequiresApi(14)
interface ViewGroupUtilsImpl {
    ViewGroupOverlayImpl a(@NonNull ViewGroup viewGroup);

    void a(@NonNull ViewGroup viewGroup, boolean z);
}
