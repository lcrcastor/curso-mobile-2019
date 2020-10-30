package android.support.v4.graphics.drawable;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build.VERSION;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.VisibleForTesting;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class IconCompat {
    private final int a;
    private Object b;
    private int c;
    private int d;

    public static IconCompat createWithResource(Context context, @DrawableRes int i) {
        if (context == null) {
            throw new IllegalArgumentException("Context must not be null.");
        }
        IconCompat iconCompat = new IconCompat(2);
        iconCompat.c = i;
        iconCompat.b = context;
        return iconCompat;
    }

    public static IconCompat createWithBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            throw new IllegalArgumentException("Bitmap must not be null.");
        }
        IconCompat iconCompat = new IconCompat(1);
        iconCompat.b = bitmap;
        return iconCompat;
    }

    public static IconCompat createWithAdaptiveBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            throw new IllegalArgumentException("Bitmap must not be null.");
        }
        IconCompat iconCompat = new IconCompat(5);
        iconCompat.b = bitmap;
        return iconCompat;
    }

    public static IconCompat createWithData(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            throw new IllegalArgumentException("Data must not be null.");
        }
        IconCompat iconCompat = new IconCompat(3);
        iconCompat.b = bArr;
        iconCompat.c = i;
        iconCompat.d = i2;
        return iconCompat;
    }

    public static IconCompat createWithContentUri(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Uri must not be null.");
        }
        IconCompat iconCompat = new IconCompat(4);
        iconCompat.b = str;
        return iconCompat;
    }

    public static IconCompat createWithContentUri(Uri uri) {
        if (uri != null) {
            return createWithContentUri(uri.toString());
        }
        throw new IllegalArgumentException("Uri must not be null.");
    }

    private IconCompat(int i) {
        this.a = i;
    }

    @RequiresApi(23)
    public Icon toIcon() {
        switch (this.a) {
            case 1:
                return Icon.createWithBitmap((Bitmap) this.b);
            case 2:
                return Icon.createWithResource((Context) this.b, this.c);
            case 3:
                return Icon.createWithData((byte[]) this.b, this.c, this.d);
            case 4:
                return Icon.createWithContentUri((String) this.b);
            case 5:
                if (VERSION.SDK_INT >= 26) {
                    return Icon.createWithAdaptiveBitmap((Bitmap) this.b);
                }
                return Icon.createWithBitmap(a((Bitmap) this.b, false));
            default:
                throw new IllegalArgumentException("Unknown type");
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Deprecated
    public void addToShortcutIntent(@NonNull Intent intent) {
        addToShortcutIntent(intent, null);
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void addToShortcutIntent(@NonNull Intent intent, @Nullable Drawable drawable) {
        Bitmap bitmap;
        int i = this.a;
        if (i != 5) {
            switch (i) {
                case 1:
                    bitmap = (Bitmap) this.b;
                    if (drawable != null) {
                        bitmap = bitmap.copy(bitmap.getConfig(), true);
                        break;
                    }
                    break;
                case 2:
                    if (drawable != null) {
                        Context context = (Context) this.b;
                        Drawable drawable2 = ContextCompat.getDrawable(context, this.c);
                        if (drawable2.getIntrinsicWidth() <= 0 || drawable2.getIntrinsicHeight() <= 0) {
                            int launcherLargeIconSize = ((ActivityManager) context.getSystemService("activity")).getLauncherLargeIconSize();
                            bitmap = Bitmap.createBitmap(launcherLargeIconSize, launcherLargeIconSize, Config.ARGB_8888);
                        } else {
                            bitmap = Bitmap.createBitmap(drawable2.getIntrinsicWidth(), drawable2.getIntrinsicHeight(), Config.ARGB_8888);
                        }
                        drawable2.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                        drawable2.draw(new Canvas(bitmap));
                        break;
                    } else {
                        intent.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", ShortcutIconResource.fromContext((Context) this.b, this.c));
                        return;
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Icon type not supported for intent shortcuts");
            }
        } else {
            bitmap = a((Bitmap) this.b, true);
        }
        if (drawable != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            drawable.setBounds(width / 2, height / 2, width, height);
            drawable.draw(new Canvas(bitmap));
        }
        intent.putExtra("android.intent.extra.shortcut.ICON", bitmap);
    }

    @VisibleForTesting
    static Bitmap a(Bitmap bitmap, boolean z) {
        int min = (int) (((float) Math.min(bitmap.getWidth(), bitmap.getHeight())) * 0.6666667f);
        Bitmap createBitmap = Bitmap.createBitmap(min, min, Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint(3);
        float f = (float) min;
        float f2 = 0.5f * f;
        float f3 = 0.9166667f * f2;
        if (z) {
            float f4 = 0.010416667f * f;
            paint.setColor(0);
            paint.setShadowLayer(f4, BitmapDescriptorFactory.HUE_RED, f * 0.020833334f, 1023410176);
            canvas.drawCircle(f2, f2, f3, paint);
            paint.setShadowLayer(f4, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 503316480);
            canvas.drawCircle(f2, f2, f3, paint);
            paint.clearShadowLayer();
        }
        paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        BitmapShader bitmapShader = new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP);
        Matrix matrix = new Matrix();
        matrix.setTranslate((float) ((-(bitmap.getWidth() - min)) / 2), (float) ((-(bitmap.getHeight() - min)) / 2));
        bitmapShader.setLocalMatrix(matrix);
        paint.setShader(bitmapShader);
        canvas.drawCircle(f2, f2, f3, paint);
        canvas.setBitmap(null);
        return createBitmap;
    }
}
