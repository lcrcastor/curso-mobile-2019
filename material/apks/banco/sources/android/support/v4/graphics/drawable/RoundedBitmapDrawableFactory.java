package android.support.v4.graphics.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.BitmapCompat;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import java.io.InputStream;

public final class RoundedBitmapDrawableFactory {

    static class DefaultRoundedBitmapDrawable extends RoundedBitmapDrawable {
        DefaultRoundedBitmapDrawable(Resources resources, Bitmap bitmap) {
            super(resources, bitmap);
        }

        public void setMipMap(boolean z) {
            if (this.a != null) {
                BitmapCompat.setHasMipMap(this.a, z);
                invalidateSelf();
            }
        }

        public boolean hasMipMap() {
            return this.a != null && BitmapCompat.hasMipMap(this.a);
        }

        /* access modifiers changed from: 0000 */
        public void a(int i, int i2, int i3, Rect rect, Rect rect2) {
            GravityCompat.apply(i, i2, i3, rect, rect2, 0);
        }
    }

    @NonNull
    public static RoundedBitmapDrawable create(@NonNull Resources resources, @Nullable Bitmap bitmap) {
        if (VERSION.SDK_INT >= 21) {
            return new RoundedBitmapDrawable21(resources, bitmap);
        }
        return new DefaultRoundedBitmapDrawable(resources, bitmap);
    }

    @NonNull
    public static RoundedBitmapDrawable create(@NonNull Resources resources, @NonNull String str) {
        RoundedBitmapDrawable create = create(resources, BitmapFactory.decodeFile(str));
        if (create.getBitmap() == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("RoundedBitmapDrawable cannot decode ");
            sb.append(str);
            Log.w("RoundedBitmapDrawableFa", sb.toString());
        }
        return create;
    }

    @NonNull
    public static RoundedBitmapDrawable create(@NonNull Resources resources, @NonNull InputStream inputStream) {
        RoundedBitmapDrawable create = create(resources, BitmapFactory.decodeStream(inputStream));
        if (create.getBitmap() == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("RoundedBitmapDrawable cannot decode ");
            sb.append(inputStream);
            Log.w("RoundedBitmapDrawableFa", sb.toString());
        }
        return create;
    }

    private RoundedBitmapDrawableFactory() {
    }
}
