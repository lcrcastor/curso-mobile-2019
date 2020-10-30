package ar.com.santander.rio.mbanking.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import ar.com.santander.rio.mbanking.R;
import java.util.ArrayList;

public class CirclePagerIndicator extends LinearLayout implements OnClickListener {
    private LayoutInflater a;
    private Context b;
    private LinearLayout c = ((LinearLayout) this.a.inflate(R.layout.circle_indicator, this));
    private CirclePagerListener d;
    private ArrayList<ImageView> e;

    public interface CirclePagerListener {
        void onDotSelected(int i);
    }

    public CirclePagerIndicator(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.b = context;
        this.a = LayoutInflater.from(context);
    }

    public void setNumDots(int i) {
        this.e = new ArrayList<>();
        LinearLayout linearLayout = (LinearLayout) this.c.getChildAt(0);
        for (int i2 = 0; i2 < linearLayout.getChildCount(); i2++) {
            ImageView imageView = (ImageView) linearLayout.getChildAt(i2);
            if (i2 >= i) {
                imageView.setVisibility(8);
            }
            if (i2 == 0) {
                imageView.setContentDescription(this.b.getString(R.string.ACCESSIBILITY_HOME_BUTTON_PROMOCIONES_DOTS_SELECCIONADO, new Object[]{String.valueOf(i2 + 1)}));
            } else {
                imageView.setContentDescription(this.b.getString(R.string.ACCESSIBILITY_HOME_BUTTON_PROMOCIONES_DOTS, new Object[]{String.valueOf(i2 + 1)}));
            }
            imageView.setOnClickListener(this);
            this.e.add(imageView);
        }
    }

    public void updateDotsState(int i) {
        for (int i2 = 0; i2 < this.e.size(); i2++) {
            if (i2 == i) {
                ((ImageView) this.e.get(i2)).setBackground(this.b.getResources().getDrawable(R.drawable.selector_signup_circle));
                ((ImageView) this.e.get(i2)).setContentDescription(this.b.getString(R.string.ACCESSIBILITY_HOME_BUTTON_PROMOCIONES_DOTS_SELECCIONADO, new Object[]{String.valueOf(i2 + 1)}));
            } else {
                ((ImageView) this.e.get(i2)).setBackground(this.b.getResources().getDrawable(R.drawable.selector_signup_circle_inverse));
                ((ImageView) this.e.get(i2)).setContentDescription(this.b.getString(R.string.ACCESSIBILITY_HOME_BUTTON_PROMOCIONES_DOTS, new Object[]{String.valueOf(i2 + 1)}));
            }
        }
    }

    public void onClick(View view) {
        if (this.d != null) {
            LinearLayout linearLayout = (LinearLayout) this.c.getChildAt(0);
            for (int i = 0; i < linearLayout.getChildCount(); i++) {
                if (view.getId() == ((ImageView) linearLayout.getChildAt(i)).getId()) {
                    this.d.onDotSelected(i);
                    return;
                }
            }
        }
    }

    public CirclePagerListener getmListener() {
        return this.d;
    }

    public void setmListener(CirclePagerListener circlePagerListener) {
        this.d = circlePagerListener;
    }
}
