package android.support.transition;

import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.WindowId;

@RequiresApi(18)
class WindowIdApi18 implements WindowIdImpl {
    private final WindowId a;

    WindowIdApi18(@NonNull View view) {
        this.a = view.getWindowId();
    }

    public boolean equals(Object obj) {
        return (obj instanceof WindowIdApi18) && ((WindowIdApi18) obj).a.equals(this.a);
    }

    public int hashCode() {
        return this.a.hashCode();
    }
}
