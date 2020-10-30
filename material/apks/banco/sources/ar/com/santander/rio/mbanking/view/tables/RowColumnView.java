package ar.com.santander.rio.mbanking.view.tables;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import ar.com.santander.rio.mbanking.R;

public class RowColumnView extends LinearLayout {
    private BaseColumn a;
    private BaseColumn b;
    private View c;
    private ViewGroup d;
    private ViewGroup e;
    private LayoutInflater f;

    public RowColumnView(Context context) {
        super(context);
        a(context);
    }

    public RowColumnView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    public RowColumnView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context);
    }

    private void a(Context context) {
        this.f = (LayoutInflater) context.getSystemService("layout_inflater");
        addView(this.f.inflate(R.layout.list_row_two_views, null));
        this.d = (ViewGroup) findViewById(R.id.vgLabel);
        this.e = (ViewGroup) findViewById(R.id.vgValue);
        this.c = findViewById(R.id.vSeparator);
    }

    public void setRow(BaseColumn baseColumn, BaseColumn baseColumn2) {
        setLabel(baseColumn.getViewColumn());
        setContent(baseColumn2.getViewColumn());
        this.a = baseColumn;
        this.b = baseColumn2;
    }

    public void setRow(BaseColumn baseColumn, BaseColumn baseColumn2, Integer num) {
        setRow(baseColumn, baseColumn2);
        if (num != null) {
            setId(num.intValue());
            setClickable(true);
        }
    }

    private void setLabel(View view) {
        if (this.d != null) {
            this.d.addView(view);
        }
    }

    private void setContent(View view) {
        if (this.e != null) {
            this.e.addView(view);
        }
    }

    public RowColumnView setContentDesc(String str) {
        setContentDescription(str);
        return this;
    }

    public View getLabel() {
        if (this.d == null || this.d.getChildCount() <= 0) {
            return null;
        }
        return this.d.getChildAt(0);
    }

    public View getValue() {
        if (this.e == null || this.e.getChildCount() <= 0) {
            return null;
        }
        return this.e.getChildAt(0);
    }

    public BaseColumn getColLabel() {
        return this.a;
    }

    public BaseColumn getColValue() {
        return this.b;
    }

    public void setBoldBottomSeparator() {
        if (this.c != null) {
            this.c.setBackgroundColor(getResources().getColor(R.color.black));
        }
    }
}
