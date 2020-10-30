package ar.com.santander.rio.mbanking.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;

public class CustomSpinner extends LinearLayout {
    private LayoutInflater a;
    private TextView b;
    private String c;
    private View d;
    private ImageView e;

    public CustomSpinner(Context context) {
        super(context);
        a(context);
    }

    public CustomSpinner(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
        setLabel(a(attributeSet, 0));
    }

    public CustomSpinner(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context);
        setLabel(a(attributeSet, 0));
    }

    private String a(AttributeSet attributeSet, int i) {
        return getContext().obtainStyledAttributes(attributeSet, R.styleable.CustomSpinner).getString(i);
    }

    public String getLabel() {
        return this.b != null ? this.b.getText().toString() : "";
    }

    public void setLabel(String str) {
        if (this.b != null) {
            this.b.setText(str);
            TextView textView = this.b;
            StringBuilder sb = new StringBuilder();
            sb.append("Botón ");
            sb.append(str);
            textView.setContentDescription(sb.toString());
        }
    }

    private void a(Context context) {
        this.a = (LayoutInflater) context.getSystemService("layout_inflater");
        addView(this.a.inflate(R.layout.layout_custom_spinner, null));
        this.b = (TextView) findViewById(R.id.vLabelSelector);
        this.c = this.b.getText().toString();
        this.d = findViewById(R.id.wrapperArrow);
        this.e = (ImageView) findViewById(R.id.ivArrow);
        setClickable(true);
    }

    public void setValueSelected(String str) {
        setLabel(str);
        if (this.d != null) {
            this.d.setVisibility(8);
        }
    }

    public void restoreSelector() {
        setLabel(this.c);
        this.d.setVisibility(0);
    }

    public void setWrapperArrow(int i) {
        this.e.setImageResource(i);
    }
}
