package com.google.android.gms.internal;

import android.graphics.Canvas;
import android.net.Uri;
import android.widget.ImageView;

public final class zzru extends ImageView {
    private Uri a;
    private int b;

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }

    public int zzatp() {
        return this.b;
    }

    public void zzgj(int i) {
        this.b = i;
    }

    public void zzq(Uri uri) {
        this.a = uri;
    }
}
