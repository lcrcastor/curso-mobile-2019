package ar.com.santander.rio.mbanking.app.ui.forms;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import ar.com.santander.rio.mbanking.R;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class FormConstraintLayout extends ConstraintLayout implements IFormViewGroup {
    private boolean g = false;

    public FormConstraintLayout(Context context) {
        super(context);
    }

    public FormConstraintLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public FormConstraintLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public boolean isFooter() {
        return this.g;
    }

    public void setFooter(boolean z) {
        this.g = z;
    }

    public int getFooterHeight() {
        return BitmapFactory.decodeResource(getResources(), R.drawable.logo_santander_seg).getHeight();
    }

    public int getTotalHeight() {
        int i;
        int i2 = 0;
        for (int i3 = 0; i3 < getChildCount(); i3++) {
            View childAt = getChildAt(i3);
            if (childAt instanceof IFormViewGroup) {
                i = ((IFormViewGroup) childAt).getTotalHeight();
            } else {
                i = childAt.getHeight();
            }
            i2 += i;
        }
        return i2;
    }

    public void drawCanvas(Canvas canvas) {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof IFormViewGroup) {
                IFormViewGroup iFormViewGroup = (IFormViewGroup) childAt;
                iFormViewGroup.drawCanvas(canvas);
                canvas.translate(BitmapDescriptorFactory.HUE_RED, (float) iFormViewGroup.getTotalHeight());
            } else if (childAt instanceof ViewGroup) {
                ((ViewGroup) childAt).draw(canvas);
                canvas.translate(BitmapDescriptorFactory.HUE_RED, (float) childAt.getHeight());
            } else {
                childAt.draw(canvas);
                canvas.translate(BitmapDescriptorFactory.HUE_RED, (float) childAt.getHeight());
            }
        }
    }
}
