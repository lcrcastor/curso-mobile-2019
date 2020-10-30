package ar.com.santander.rio.mbanking.view.tables;

import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CElementAcc;
import ar.com.santander.rio.mbanking.view.tables.BaseColumn.TypeStyle;

public class ColumValueView extends LinearLayout implements BaseColumn {
    public CElementAcc mCElementAcc;
    public TextView mViewValue;

    public View getViewColumn() {
        return this;
    }

    public ColumValueView(Context context) {
        super(context);
        initView();
    }

    public void setAlignment(int i) {
        if (VERSION.SDK_INT >= 17) {
            this.mViewValue.setTextAlignment(i);
        }
    }

    public ColumValueView(Context context, String str) {
        super(context);
        initView();
        setValue(str);
    }

    public ColumValueView(Context context, String str, TypeStyle typeStyle) {
        this(context, str);
        setTypeStyle(typeStyle);
    }

    public ColumValueView(Context context, String str, Integer num) {
        this(context, str);
        if (num != null) {
            this.mViewValue.setId(num.intValue());
        }
    }

    public ColumValueView(Context context, String str, Integer num, TypeStyle typeStyle) {
        this(context, str, num);
        setTypeStyle(typeStyle);
    }

    public ColumValueView(Context context, String str, Integer num, Integer num2) {
        this(context, str, num);
        if (num2 != null) {
            this.mViewValue.setTextColor(getResources().getColor(num2.intValue()));
        }
    }

    public ColumValueView(Context context, String str, Integer num, Integer num2, TypeStyle typeStyle) {
        this(context, str, num, num2);
        setTypeStyle(typeStyle);
    }

    public ColumValueView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView();
    }

    public ColumValueView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView();
    }

    /* access modifiers changed from: protected */
    public void setTypeStyle(TypeStyle typeStyle) {
        if (TypeStyle.TYPE_STYLE_DATA_VALUE.equals(typeStyle)) {
            this.mViewValue.setTextColor(getResources().getColor(R.color.generic_black));
        } else if (TypeStyle.TYPE_STYLE_LABEL.equals(typeStyle)) {
            this.mViewValue.setTextColor(getResources().getColor(R.color.generic_black));
        }
    }

    /* access modifiers changed from: protected */
    public void initView() {
        addView(((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.layout_column_value, null));
        this.mViewValue = (TextView) findViewById(R.id.rowContent);
    }

    public void setValue(String str) {
        if (this.mViewValue != null) {
            this.mViewValue.setText(str);
        }
        if (this.mCElementAcc != null) {
            this.mCElementAcc.applyFilter(this.mViewValue, str);
        }
    }

    public void setColorValue(int i) {
        this.mViewValue.setTextColor(getResources().getColor(i));
    }

    public void setTextAccesibility(String str) {
        if (this.mViewValue != null) {
            this.mViewValue.setContentDescription(str);
        }
    }

    public CElementAcc getCElementAcc() {
        return this.mCElementAcc;
    }

    public void setCElementAcc(CElementAcc cElementAcc) {
        this.mCElementAcc = cElementAcc;
        if (this.mViewValue != null) {
            setValue(this.mViewValue.getText().toString());
        }
    }
}
