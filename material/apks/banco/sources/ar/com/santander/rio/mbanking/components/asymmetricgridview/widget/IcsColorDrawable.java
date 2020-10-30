package ar.com.santander.rio.mbanking.components.asymmetricgridview.widget;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;

public class IcsColorDrawable extends Drawable {
    private int a;
    private final Paint b = new Paint();

    public void setColorFilter(ColorFilter colorFilter) {
    }

    public IcsColorDrawable(ColorDrawable colorDrawable) {
        Bitmap createBitmap = Bitmap.createBitmap(1, 1, Config.ARGB_8888);
        colorDrawable.draw(new Canvas(createBitmap));
        this.a = createBitmap.getPixel(0, 0);
        createBitmap.recycle();
    }

    public IcsColorDrawable(int i) {
        this.a = i;
    }

    public void draw(Canvas canvas) {
        if ((this.a >>> 24) != 0) {
            this.b.setColor(this.a);
            canvas.drawRect(getBounds(), this.b);
        }
    }

    public void setAlpha(int i) {
        if (i != (this.a >>> 24)) {
            this.a = (i << 24) | (this.a & ViewCompat.MEASURED_SIZE_MASK);
            invalidateSelf();
        }
    }

    public int getOpacity() {
        return this.a >>> 24;
    }
}
