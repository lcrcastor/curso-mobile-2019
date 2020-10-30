package com.squareup.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.widget.ImageView;
import com.squareup.picasso.Picasso.LoadedFrom;

final class PicassoDrawable extends BitmapDrawable {
    private static final Paint e = new Paint();
    Drawable a;
    long b;
    boolean c;
    int d = 255;
    private final boolean f;
    private final float g;
    private final LoadedFrom h;

    static void a(ImageView imageView, Context context, Bitmap bitmap, LoadedFrom loadedFrom, boolean z, boolean z2) {
        Drawable drawable = imageView.getDrawable();
        if (drawable instanceof AnimationDrawable) {
            ((AnimationDrawable) drawable).stop();
        }
        PicassoDrawable picassoDrawable = new PicassoDrawable(context, bitmap, drawable, loadedFrom, z, z2);
        imageView.setImageDrawable(picassoDrawable);
    }

    static void a(ImageView imageView, Drawable drawable) {
        imageView.setImageDrawable(drawable);
        if (imageView.getDrawable() instanceof AnimationDrawable) {
            ((AnimationDrawable) imageView.getDrawable()).start();
        }
    }

    PicassoDrawable(Context context, Bitmap bitmap, Drawable drawable, LoadedFrom loadedFrom, boolean z, boolean z2) {
        super(context.getResources(), bitmap);
        this.f = z2;
        this.g = context.getResources().getDisplayMetrics().density;
        this.h = loadedFrom;
        if (loadedFrom != LoadedFrom.MEMORY && !z) {
            this.a = drawable;
            this.c = true;
            this.b = SystemClock.uptimeMillis();
        }
    }

    public void draw(Canvas canvas) {
        if (!this.c) {
            super.draw(canvas);
        } else {
            float uptimeMillis = ((float) (SystemClock.uptimeMillis() - this.b)) / 200.0f;
            if (uptimeMillis >= 1.0f) {
                this.c = false;
                this.a = null;
                super.draw(canvas);
            } else {
                if (this.a != null) {
                    this.a.draw(canvas);
                }
                super.setAlpha((int) (((float) this.d) * uptimeMillis));
                super.draw(canvas);
                super.setAlpha(this.d);
                if (VERSION.SDK_INT <= 10) {
                    invalidateSelf();
                }
            }
        }
        if (this.f) {
            a(canvas);
        }
    }

    public void setAlpha(int i) {
        this.d = i;
        if (this.a != null) {
            this.a.setAlpha(i);
        }
        super.setAlpha(i);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        if (this.a != null) {
            this.a.setColorFilter(colorFilter);
        }
        super.setColorFilter(colorFilter);
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        if (this.a != null) {
            this.a.setBounds(rect);
        }
        super.onBoundsChange(rect);
    }

    private void a(Canvas canvas) {
        e.setColor(-1);
        canvas.drawPath(a(new Point(0, 0), (int) (this.g * 16.0f)), e);
        e.setColor(this.h.a);
        canvas.drawPath(a(new Point(0, 0), (int) (this.g * 15.0f)), e);
    }

    private static Path a(Point point, int i) {
        Point point2 = new Point(point.x + i, point.y);
        Point point3 = new Point(point.x, point.y + i);
        Path path = new Path();
        path.moveTo((float) point.x, (float) point.y);
        path.lineTo((float) point2.x, (float) point2.y);
        path.lineTo((float) point3.x, (float) point3.y);
        return path;
    }
}
