package android.support.v4.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class ContentLoadingProgressBar extends ProgressBar {
    long a;
    boolean b;
    boolean c;
    boolean d;
    private final Runnable e;
    private final Runnable f;

    public ContentLoadingProgressBar(@NonNull Context context) {
        this(context, null);
    }

    public ContentLoadingProgressBar(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        this.a = -1;
        this.b = false;
        this.c = false;
        this.d = false;
        this.e = new Runnable() {
            public void run() {
                ContentLoadingProgressBar.this.b = false;
                ContentLoadingProgressBar.this.a = -1;
                ContentLoadingProgressBar.this.setVisibility(8);
            }
        };
        this.f = new Runnable() {
            public void run() {
                ContentLoadingProgressBar.this.c = false;
                if (!ContentLoadingProgressBar.this.d) {
                    ContentLoadingProgressBar.this.a = System.currentTimeMillis();
                    ContentLoadingProgressBar.this.setVisibility(0);
                }
            }
        };
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        a();
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        a();
    }

    private void a() {
        removeCallbacks(this.e);
        removeCallbacks(this.f);
    }

    public synchronized void hide() {
        this.d = true;
        removeCallbacks(this.f);
        this.c = false;
        long currentTimeMillis = System.currentTimeMillis() - this.a;
        if (currentTimeMillis < 500) {
            if (this.a != -1) {
                if (!this.b) {
                    postDelayed(this.e, 500 - currentTimeMillis);
                    this.b = true;
                }
            }
        }
        setVisibility(8);
    }

    public synchronized void show() {
        this.a = -1;
        this.d = false;
        removeCallbacks(this.e);
        this.b = false;
        if (!this.c) {
            postDelayed(this.f, 500);
            this.c = true;
        }
    }
}
