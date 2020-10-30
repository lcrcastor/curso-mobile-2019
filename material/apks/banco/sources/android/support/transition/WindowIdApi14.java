package android.support.transition;

import android.os.IBinder;
import android.support.annotation.RequiresApi;

@RequiresApi(14)
class WindowIdApi14 implements WindowIdImpl {
    private final IBinder a;

    WindowIdApi14(IBinder iBinder) {
        this.a = iBinder;
    }

    public boolean equals(Object obj) {
        return (obj instanceof WindowIdApi14) && ((WindowIdApi14) obj).a.equals(this.a);
    }

    public int hashCode() {
        return this.a.hashCode();
    }
}
