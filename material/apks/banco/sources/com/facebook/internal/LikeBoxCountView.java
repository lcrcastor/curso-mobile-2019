package com.facebook.internal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import com.facebook.android.R;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class LikeBoxCountView extends FrameLayout {
    private TextView a;
    private LikeBoxCountViewCaretPosition b = LikeBoxCountViewCaretPosition.LEFT;
    private float c;
    private float d;
    private float e;
    private Paint f;
    private int g;
    private int h;

    public enum LikeBoxCountViewCaretPosition {
        LEFT,
        TOP,
        RIGHT,
        BOTTOM
    }

    public LikeBoxCountView(Context context) {
        super(context);
        a(context);
    }

    public void setText(String str) {
        this.a.setText(str);
    }

    public void setCaretPosition(LikeBoxCountViewCaretPosition likeBoxCountViewCaretPosition) {
        this.b = likeBoxCountViewCaretPosition;
        switch (likeBoxCountViewCaretPosition) {
            case LEFT:
                a(this.h, 0, 0, 0);
                return;
            case TOP:
                a(0, this.h, 0, 0);
                return;
            case RIGHT:
                a(0, 0, this.h, 0);
                return;
            case BOTTOM:
                a(0, 0, 0, this.h);
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int paddingTop = getPaddingTop();
        int paddingLeft = getPaddingLeft();
        int width = getWidth() - getPaddingRight();
        int height = getHeight() - getPaddingBottom();
        switch (this.b) {
            case LEFT:
                paddingLeft = (int) (((float) paddingLeft) + this.c);
                break;
            case TOP:
                paddingTop = (int) (((float) paddingTop) + this.c);
                break;
            case RIGHT:
                width = (int) (((float) width) - this.c);
                break;
            case BOTTOM:
                height = (int) (((float) height) - this.c);
                break;
        }
        a(canvas, (float) paddingLeft, (float) paddingTop, (float) width, (float) height);
    }

    private void a(Context context) {
        setWillNotDraw(false);
        this.c = getResources().getDimension(R.dimen.com_facebook_likeboxcountview_caret_height);
        this.d = getResources().getDimension(R.dimen.com_facebook_likeboxcountview_caret_width);
        this.e = getResources().getDimension(R.dimen.com_facebook_likeboxcountview_border_radius);
        this.f = new Paint();
        this.f.setColor(getResources().getColor(R.color.com_facebook_likeboxcountview_border_color));
        this.f.setStrokeWidth(getResources().getDimension(R.dimen.com_facebook_likeboxcountview_border_width));
        this.f.setStyle(Style.STROKE);
        b(context);
        addView(this.a);
        setCaretPosition(this.b);
    }

    private void b(Context context) {
        this.a = new TextView(context);
        this.a.setLayoutParams(new LayoutParams(-1, -1));
        this.a.setGravity(17);
        this.a.setTextSize(0, getResources().getDimension(R.dimen.com_facebook_likeboxcountview_text_size));
        this.a.setTextColor(getResources().getColor(R.color.com_facebook_likeboxcountview_text_color));
        this.g = getResources().getDimensionPixelSize(R.dimen.com_facebook_likeboxcountview_text_padding);
        this.h = getResources().getDimensionPixelSize(R.dimen.com_facebook_likeboxcountview_caret_height);
    }

    private void a(int i, int i2, int i3, int i4) {
        this.a.setPadding(this.g + i, this.g + i2, this.g + i3, this.g + i4);
    }

    private void a(Canvas canvas, float f2, float f3, float f4, float f5) {
        Path path = new Path();
        float f6 = this.e * 2.0f;
        float f7 = f2 + f6;
        float f8 = f3 + f6;
        path.addArc(new RectF(f2, f3, f7, f8), -180.0f, 90.0f);
        if (this.b == LikeBoxCountViewCaretPosition.TOP) {
            float f9 = f4 - f2;
            path.lineTo(((f9 - this.d) / 2.0f) + f2, f3);
            path.lineTo((f9 / 2.0f) + f2, f3 - this.c);
            path.lineTo(((f9 + this.d) / 2.0f) + f2, f3);
        }
        path.lineTo(f4 - this.e, f3);
        float f10 = f4 - f6;
        path.addArc(new RectF(f10, f3, f4, f8), -90.0f, 90.0f);
        if (this.b == LikeBoxCountViewCaretPosition.RIGHT) {
            float f11 = f5 - f3;
            path.lineTo(f4, ((f11 - this.d) / 2.0f) + f3);
            path.lineTo(this.c + f4, (f11 / 2.0f) + f3);
            path.lineTo(f4, ((f11 + this.d) / 2.0f) + f3);
        }
        path.lineTo(f4, f5 - this.e);
        float f12 = f5 - f6;
        path.addArc(new RectF(f10, f12, f4, f5), BitmapDescriptorFactory.HUE_RED, 90.0f);
        if (this.b == LikeBoxCountViewCaretPosition.BOTTOM) {
            float f13 = f4 - f2;
            path.lineTo(((this.d + f13) / 2.0f) + f2, f5);
            path.lineTo((f13 / 2.0f) + f2, this.c + f5);
            path.lineTo(((f13 - this.d) / 2.0f) + f2, f5);
        }
        path.lineTo(this.e + f2, f5);
        path.addArc(new RectF(f2, f12, f7, f5), 90.0f, 90.0f);
        if (this.b == LikeBoxCountViewCaretPosition.LEFT) {
            float f14 = f5 - f3;
            path.lineTo(f2, ((this.d + f14) / 2.0f) + f3);
            path.lineTo(f2 - this.c, (f14 / 2.0f) + f3);
            path.lineTo(f2, ((f14 - this.d) / 2.0f) + f3);
        }
        path.lineTo(f2, f3 + this.e);
        canvas.drawPath(path, this.f);
    }
}
