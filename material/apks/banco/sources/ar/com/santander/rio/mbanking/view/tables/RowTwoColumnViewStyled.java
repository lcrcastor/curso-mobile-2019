package ar.com.santander.rio.mbanking.view.tables;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.accessibility.AccessibilityManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CBaseAccesibility;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CElementAcc;

public class RowTwoColumnViewStyled extends LinearLayout implements OnClickListener {
    public static String STYLE_1 = "style_1";
    public static String STYLE_2 = "style_2";
    public static String STYLE_3 = "style_3";
    public static String STYLE_4 = "style_4";
    public static String TYPE_AMOUNT = "amount";
    public static String TYPE_MAIL = "mail";
    private LayoutInflater a;
    private Context b;
    private String c;
    private String d;
    private String e;
    private String f = STYLE_1;
    private boolean g;
    private boolean h;
    private String i;
    private String j;
    private int k = 0;
    private CElementAcc l;
    private CElementAcc m;
    private RowTwoColumnViewStyledListener n;

    public interface RowTwoColumnViewStyledListener {
        void onItemSelected(String str);
    }

    public RowTwoColumnViewStyled(Context context) {
        super(context);
        a(context);
    }

    /* JADX INFO: finally extract failed */
    public RowTwoColumnViewStyled(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.TwoRowColumn, 0, 0);
        this.b = context;
        try {
            this.c = obtainStyledAttributes.getString(3);
            this.d = obtainStyledAttributes.getString(7);
            this.f = obtainStyledAttributes.getString(6);
            this.h = obtainStyledAttributes.getBoolean(2, false);
            this.i = obtainStyledAttributes.getString(1);
            this.j = obtainStyledAttributes.getString(0);
            this.k = obtainStyledAttributes.getInteger(4, 0);
            if (this.f == null) {
                this.f = STYLE_1;
            }
            this.g = obtainStyledAttributes.getBoolean(5, false);
            obtainStyledAttributes.recycle();
            a(context);
            if (this.g) {
                TextView textView = (TextView) findViewById(R.id.rowContent);
                textView.setOnClickListener(this);
                textView.setTextColor(context.getResources().getColor(R.color.generic_black));
            }
            if (this.c != null) {
                setLabel(this.c);
            }
            if (this.d != null) {
                setContent(this.d);
            }
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }

    public RowTwoColumnViewStyled(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        a(context);
    }

    private void a(Context context) {
        this.a = (LayoutInflater) context.getSystemService("layout_inflater");
        if (this.h) {
            if (this.i == null || !this.i.equalsIgnoreCase(TYPE_MAIL)) {
                addView(this.a.inflate(R.layout.list_row_two_column_3, null));
            } else {
                addView(this.a.inflate(R.layout.list_row_two_column_mail, null));
            }
            if (this.j != null) {
                setHint(this.j);
            }
            if (this.k != 0) {
                ((EditText) findViewById(R.id.rowContent)).setFilters(new InputFilter[]{new LengthFilter(this.k)});
            }
        } else if (this.f.equalsIgnoreCase(STYLE_2)) {
            addView(this.a.inflate(R.layout.list_row_tow_column_2, null));
        } else if (this.f.equalsIgnoreCase(STYLE_4)) {
            addView(this.a.inflate(R.layout.list_row_two_column_4, null));
        } else {
            addView(this.a.inflate(R.layout.list_row_two_column, null));
        }
        this.m = new CBaseAccesibility();
        this.l = new CBaseAccesibility();
        setClickable(true);
    }

    public void setRow(String str, String str2) {
        setLabel(str);
        setContent(str2);
    }

    public void setLabel(String str) {
        setClickable(true);
        TextView textView = (TextView) findViewById(R.id.rowLabel);
        if (textView != null) {
            textView.setText(str);
            textView.setImportantForAccessibility(2);
            if (this.l != null) {
                this.l.applyFilter(textView, str);
            }
        }
    }

    public void setHint(String str) {
        EditText editText = (EditText) findViewById(R.id.rowContent);
        if (editText != null) {
            editText.setHint(str);
        }
    }

    public String getContent() {
        TextView textView = (TextView) findViewById(R.id.rowContent);
        return textView != null ? textView.getText().toString() : "";
    }

    public String getLabel() {
        return this.c;
    }

    public String getValue() {
        return this.d;
    }

    public void setContent(String str) {
        String str2;
        setClickable(false);
        TextView textView = (TextView) findViewById(R.id.rowContent);
        if (textView != null) {
            textView.setText(str);
            textView.setImportantForAccessibility(2);
            if (this.m != null) {
                CElementAcc cElementAcc = this.m;
                if (this.e != null) {
                    str2 = this.e;
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append(this.c);
                    sb.append(", ");
                    sb.append(str);
                    str2 = sb.toString();
                }
                cElementAcc.applyFilter(this, str2);
            }
        }
    }

    public void setLabelFilter(String str) {
        this.e = str;
    }

    public RowTwoColumnViewStyledListener getListener() {
        return this.n;
    }

    public void setListener(RowTwoColumnViewStyledListener rowTwoColumnViewStyledListener) {
        this.n = rowTwoColumnViewStyledListener;
    }

    public void onClick(View view) {
        this.n.onItemSelected(this.d);
    }

    public void performClickOnValue() {
        ((TextView) findViewById(R.id.rowContent)).performClick();
    }

    public boolean performClick() {
        return super.performClick();
    }

    public boolean isAccessibilityEnabled() {
        Context context = getContext();
        getContext();
        return ((AccessibilityManager) context.getSystemService("accessibility")).isEnabled();
    }

    public int getMaximumInput() {
        return this.k;
    }

    public void setMaximumInput(int i2) {
        this.k = i2;
    }

    public CElementAcc getmCElementAccLabel() {
        return this.l;
    }

    public void setmCElementAccLabel(CElementAcc cElementAcc) {
        this.l = cElementAcc;
        this.l.applyFilter(findViewById(R.id.rowLabel), this.c);
    }

    public CElementAcc getmCElementAccValue() {
        return this.m;
    }

    public void setmCElementAccValue(CElementAcc cElementAcc) {
        this.m = cElementAcc;
    }

    public View getLabelView() {
        return findViewById(R.id.rowLabel);
    }

    public View getValueView() {
        return findViewById(R.id.rowContent);
    }
}
