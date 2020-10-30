package ar.com.santander.rio.mbanking.view.tables;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;

public class RowTwoColumnView extends LinearLayout implements OnClickListener {
    public static String STYLE_1 = "style_1";
    public static String STYLE_2 = "style_2";
    public static String STYLE_3 = "style_3";
    public static String TYPE_AMOUNT = "amount";
    public static String TYPE_MAIL = "mail";
    private LayoutInflater a;
    private Context b;
    private String c;
    private String d;
    private String e = STYLE_1;
    private boolean f;
    private boolean g;
    private String h;
    private String i;
    private RowTwoColumnViewListener j;

    public interface RowTwoColumnViewListener {
        void onItemSelected(String str);
    }

    public RowTwoColumnView(Context context) {
        super(context);
        a(context);
    }

    /* JADX INFO: finally extract failed */
    public RowTwoColumnView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.TwoRowColumn, 0, 0);
        this.b = context;
        try {
            this.c = obtainStyledAttributes.getString(3);
            this.d = obtainStyledAttributes.getString(7);
            this.e = obtainStyledAttributes.getString(6);
            this.g = obtainStyledAttributes.getBoolean(2, false);
            this.h = obtainStyledAttributes.getString(1);
            this.i = obtainStyledAttributes.getString(0);
            if (this.e == null) {
                this.e = STYLE_1;
            }
            this.f = obtainStyledAttributes.getBoolean(5, false);
            obtainStyledAttributes.recycle();
            a(context);
            if (this.f) {
                TextView textView = (TextView) findViewById(R.id.rowContent);
                textView.setOnClickListener(this);
                textView.setTextColor(context.getResources().getColor(R.color.red_light));
            }
            if (this.c != null && this.d != null) {
                setLabel(this.c);
                setContent(this.d);
            }
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }

    public RowTwoColumnView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        a(context);
    }

    private void a(Context context) {
        this.a = (LayoutInflater) context.getSystemService("layout_inflater");
        if (this.g) {
            if (this.h == null || !this.h.equalsIgnoreCase(TYPE_MAIL)) {
                addView(this.a.inflate(R.layout.list_row_two_column_3, null));
            } else {
                addView(this.a.inflate(R.layout.list_row_two_column_mail, null));
            }
            if (this.i != null) {
                setHint(this.i);
            }
        } else if (this.e.equalsIgnoreCase(STYLE_2)) {
            addView(this.a.inflate(R.layout.list_row_tow_column_2, null));
        } else {
            addView(this.a.inflate(R.layout.list_row_two_column, null));
        }
    }

    public void setRow(String str, String str2) {
        setLabel(str);
        setContent(str2);
    }

    private void setLabel(String str) {
        TextView textView = (TextView) findViewById(R.id.rowLabel);
        if (textView != null) {
            textView.setText(str);
        }
    }

    public void setContent(String str) {
        TextView textView = (TextView) findViewById(R.id.rowContent);
        if (textView != null) {
            textView.setText(str);
            this.d = str;
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

    public RowTwoColumnViewListener getListener() {
        return this.j;
    }

    public void setListener(RowTwoColumnViewListener rowTwoColumnViewListener) {
        this.j = rowTwoColumnViewListener;
    }

    public void onClick(View view) {
        this.j.onItemSelected(this.d);
    }
}
