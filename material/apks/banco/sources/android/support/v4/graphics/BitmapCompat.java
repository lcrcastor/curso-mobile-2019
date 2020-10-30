package android.support.v4.graphics;

import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

public final class BitmapCompat {
    static final BitmapCompatBaseImpl a;

    @RequiresApi(18)
    static class BitmapCompatApi18Impl extends BitmapCompatBaseImpl {
        BitmapCompatApi18Impl() {
        }

        public boolean a(Bitmap bitmap) {
            return bitmap.hasMipMap();
        }

        public void a(Bitmap bitmap, boolean z) {
            bitmap.setHasMipMap(z);
        }
    }

    @RequiresApi(19)
    static class BitmapCompatApi19Impl extends BitmapCompatApi18Impl {
        BitmapCompatApi19Impl() {
        }

        public int b(Bitmap bitmap) {
            return bitmap.getAllocationByteCount();
        }
    }

    static class BitmapCompatBaseImpl {
        public void a(Bitmap bitmap, boolean z) {
        }

        public boolean a(Bitmap bitmap) {
            return false;
        }

        BitmapCompatBaseImpl() {
        }

        public int b(Bitmap bitmap) {
            return bitmap.getByteCount();
        }
    }

    static {
        if (VERSION.SDK_INT >= 19) {
            a = new BitmapCompatApi19Impl();
        } else if (VERSION.SDK_INT >= 18) {
            a = new BitmapCompatApi18Impl();
        } else {
            a = new BitmapCompatBaseImpl();
        }
    }

    public static boolean hasMipMap(@NonNull Bitmap bitmap) {
        return a.a(bitmap);
    }

    public static void setHasMipMap(@NonNull Bitmap bitmap, boolean z) {
        a.a(bitmap, z);
    }

    public static int getAllocationByteCount(@NonNull Bitmap bitmap) {
        return a.b(bitmap);
    }

    private BitmapCompat() {
    }
}
