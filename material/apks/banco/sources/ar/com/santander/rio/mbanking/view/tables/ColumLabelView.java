package ar.com.santander.rio.mbanking.view.tables;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Spanned;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CBaseAccesibility;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CElementAcc;
import ar.com.santander.rio.mbanking.view.tables.BaseColumn.TypeStyle;

public class ColumLabelView extends LinearLayout implements BaseColumn {
    private boolean a;
    private String b;
    private CElementAcc c;
    public TextView mViewLabel;

    public View getViewColumn() {
        return this;
    }

    public ColumLabelView(Context context) {
        super(context);
        a();
    }

    public ColumLabelView(Context context, String str) {
        this(context, str, TypeStyle.TYPE_STYLE_LABEL);
    }

    public ColumLabelView(Context context, Spanned spanned) {
        super(context);
        this.a = true;
        a();
        this.mViewLabel.setText(spanned);
    }

    public ColumLabelView(Context context, String str, TypeStyle typeStyle) {
        super(context);
        a();
        setLabelAccessibility(str);
        setTypeStyle(typeStyle);
    }

    /* JADX INFO: finally extract failed */
    public ColumLabelView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.TwoRowColumn, 0, 0);
        try {
            this.b = obtainStyledAttributes.getString(3);
            obtainStyledAttributes.recycle();
            if (this.b != null) {
                setLabelAccessibility(this.b);
            }
            a();
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }

    private void a() {
        addView(((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.layout_column_label, null));
        this.mViewLabel = (TextView) findViewById(R.id.rowLabel);
    }

    public void setLabelAccessibility(String str) {
        setLabel(str);
    }

    public void setLabel(String str) {
        if (this.mViewLabel != null) {
            this.mViewLabel.setText(str);
            if (this.c != null) {
                this.c.applyFilter(this.mViewLabel, str);
            }
        }
    }

    private void setTypeStyle(TypeStyle typeStyle) {
        if (TypeStyle.TYPE_STYLE_DATA_VALUE.equals(typeStyle)) {
            this.mViewLabel.setTextColor(getResources().getColor(R.color.generic_grey_dark));
        } else if (TypeStyle.TYPE_STYLE_LABEL.equals(typeStyle)) {
            this.mViewLabel.setTextColor(getResources().getColor(R.color.generic_grey_dark));
        }
    }

    public void setTextAccesibility(String str) {
        if (this.mViewLabel != null) {
            this.mViewLabel.setContentDescription(str);
        }
    }

    public CElementAcc getCElementAcc() {
        return this.c;
    }

    public void setCElementAcc(CElementAcc cElementAcc) {
        this.c = cElementAcc;
        if (this.a) {
            this.mViewLabel.setContentDescription(((CBaseAccesibility) cElementAcc).getCustomString());
        } else if (this.mViewLabel != null) {
            setLabel(this.mViewLabel.getText().toString());
        }
    }
}
