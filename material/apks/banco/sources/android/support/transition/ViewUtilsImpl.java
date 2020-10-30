package android.support.transition;

import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;

@RequiresApi(14)
interface ViewUtilsImpl {
    ViewOverlayImpl a(@NonNull View view);

    void a(@NonNull View view, float f);

    void a(View view, int i, int i2, int i3, int i4);

    void a(@NonNull View view, @NonNull Matrix matrix);

    WindowIdImpl b(@NonNull View view);

    void b(@NonNull View view, @NonNull Matrix matrix);

    float c(@NonNull View view);

    void c(@NonNull View view, Matrix matrix);

    void d(@NonNull View view);

    void e(@NonNull View view);
}
