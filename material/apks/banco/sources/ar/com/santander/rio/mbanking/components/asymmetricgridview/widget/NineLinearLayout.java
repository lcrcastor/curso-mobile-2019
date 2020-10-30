package ar.com.santander.rio.mbanking.components.asymmetricgridview.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

@TargetApi(11)
public class NineLinearLayout extends LinearLayout {
    private final AnimatorProxy a;

    public NineLinearLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.a = AnimatorProxy.NEEDS_PROXY ? AnimatorProxy.wrap(this) : null;
    }

    public void setVisibility(int i) {
        if (this.a != null) {
            if (i == 8) {
                clearAnimation();
            } else if (i == 0) {
                setAnimation(this.a);
            }
        }
        super.setVisibility(i);
    }

    public float getAlpha() {
        if (AnimatorProxy.NEEDS_PROXY) {
            return this.a.getAlpha();
        }
        return super.getAlpha();
    }

    public void setAlpha(float f) {
        if (AnimatorProxy.NEEDS_PROXY) {
            this.a.setAlpha(f);
        } else {
            super.setAlpha(f);
        }
    }

    public float getTranslationX() {
        if (AnimatorProxy.NEEDS_PROXY) {
            return this.a.getTranslationX();
        }
        return super.getTranslationX();
    }

    public void setTranslationX(float f) {
        if (AnimatorProxy.NEEDS_PROXY) {
            this.a.setTranslationX(f);
        } else {
            super.setTranslationX(f);
        }
    }
}
