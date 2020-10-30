package com.squareup.picasso;

import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.ImageView;
import java.lang.ref.WeakReference;

class DeferredRequestCreator implements OnPreDrawListener {
    final RequestCreator a;
    final WeakReference<ImageView> b;
    Callback c;

    DeferredRequestCreator(RequestCreator requestCreator, ImageView imageView, Callback callback) {
        this.a = requestCreator;
        this.b = new WeakReference<>(imageView);
        this.c = callback;
        imageView.getViewTreeObserver().addOnPreDrawListener(this);
    }

    public boolean onPreDraw() {
        ImageView imageView = (ImageView) this.b.get();
        if (imageView == null) {
            return true;
        }
        ViewTreeObserver viewTreeObserver = imageView.getViewTreeObserver();
        if (!viewTreeObserver.isAlive()) {
            return true;
        }
        int width = imageView.getWidth();
        int height = imageView.getHeight();
        if (width <= 0 || height <= 0) {
            return true;
        }
        viewTreeObserver.removeOnPreDrawListener(this);
        this.a.a().resize(width, height).into(imageView, this.c);
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        this.c = null;
        ImageView imageView = (ImageView) this.b.get();
        if (imageView != null) {
            ViewTreeObserver viewTreeObserver = imageView.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.removeOnPreDrawListener(this);
            }
        }
    }
}
