package android.support.transition;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.support.v4.content.res.TypedArrayUtils;
import android.util.AttributeSet;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.xmlpull.v1.XmlPullParser;

public class ArcMotion extends PathMotion {
    private static final float a = ((float) Math.tan(Math.toRadians(35.0d)));
    private float b = BitmapDescriptorFactory.HUE_RED;
    private float c = BitmapDescriptorFactory.HUE_RED;
    private float d = 70.0f;
    private float e = BitmapDescriptorFactory.HUE_RED;
    private float f = BitmapDescriptorFactory.HUE_RED;
    private float g = a;

    public ArcMotion() {
    }

    public ArcMotion(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Styleable.j);
        XmlPullParser xmlPullParser = (XmlPullParser) attributeSet;
        setMinimumVerticalAngle(TypedArrayUtils.getNamedFloat(obtainStyledAttributes, xmlPullParser, "minimumVerticalAngle", 1, BitmapDescriptorFactory.HUE_RED));
        setMinimumHorizontalAngle(TypedArrayUtils.getNamedFloat(obtainStyledAttributes, xmlPullParser, "minimumHorizontalAngle", 0, BitmapDescriptorFactory.HUE_RED));
        setMaximumAngle(TypedArrayUtils.getNamedFloat(obtainStyledAttributes, xmlPullParser, "maximumAngle", 2, 70.0f));
        obtainStyledAttributes.recycle();
    }

    public void setMinimumHorizontalAngle(float f2) {
        this.b = f2;
        this.e = a(f2);
    }

    public float getMinimumHorizontalAngle() {
        return this.b;
    }

    public void setMinimumVerticalAngle(float f2) {
        this.c = f2;
        this.f = a(f2);
    }

    public float getMinimumVerticalAngle() {
        return this.c;
    }

    public void setMaximumAngle(float f2) {
        this.d = f2;
        this.g = a(f2);
    }

    public float getMaximumAngle() {
        return this.d;
    }

    private static float a(float f2) {
        if (f2 >= BitmapDescriptorFactory.HUE_RED && f2 <= 90.0f) {
            return (float) Math.tan(Math.toRadians((double) (f2 / 2.0f)));
        }
        throw new IllegalArgumentException("Arc must be between 0 and 90 degrees");
    }

    public Path getPath(float f2, float f3, float f4, float f5) {
        float f6;
        float f7;
        float f8;
        float f9;
        float f10;
        Path path = new Path();
        path.moveTo(f2, f3);
        float f11 = f4 - f2;
        float f12 = f5 - f3;
        float f13 = (f11 * f11) + (f12 * f12);
        float f14 = (f2 + f4) / 2.0f;
        float f15 = (f3 + f5) / 2.0f;
        float f16 = 0.25f * f13;
        boolean z = f3 > f5;
        if (Math.abs(f11) < Math.abs(f12)) {
            float abs = Math.abs(f13 / (f12 * 2.0f));
            if (z) {
                f7 = abs + f5;
                f8 = f4;
            } else {
                f7 = abs + f3;
                f8 = f2;
            }
            f6 = this.f * f16 * this.f;
        } else {
            float f17 = f13 / (f11 * 2.0f);
            if (z) {
                f10 = f3;
                f9 = f17 + f2;
            } else {
                f9 = f4 - f17;
                f10 = f5;
            }
            f6 = this.e * f16 * this.e;
        }
        float f18 = f14 - f8;
        float f19 = f15 - f7;
        float f20 = (f18 * f18) + (f19 * f19);
        float f21 = f16 * this.g * this.g;
        if (f20 >= f6) {
            f6 = f20 > f21 ? f21 : BitmapDescriptorFactory.HUE_RED;
        }
        if (f6 != BitmapDescriptorFactory.HUE_RED) {
            float sqrt = (float) Math.sqrt((double) (f6 / f20));
            f8 = ((f8 - f14) * sqrt) + f14;
            f7 = f15 + (sqrt * (f7 - f15));
        }
        path.cubicTo((f2 + f8) / 2.0f, (f3 + f7) / 2.0f, (f8 + f4) / 2.0f, (f7 + f5) / 2.0f, f4, f5);
        return path;
    }
}
