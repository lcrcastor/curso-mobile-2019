package ar.com.santander.rio.mbanking.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CBaseAccesibility;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CElementAcc;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;

public class NumericEditTextWithPrefixAccesibility extends LinearLayout implements OnFocusChangeListener {
    private Context a;
    private AttributeSet b;
    private CAccessibility c;
    private CElementAcc d;
    private LayoutInflater e;
    private String f = "Cuadro de edición ";
    private String g;
    private String h;
    private int i = 1;
    /* access modifiers changed from: private */
    public int j;
    /* access modifiers changed from: private */
    public int k;
    private int l;
    private int m = 1;
    private int n = 1;
    private boolean o = true;
    private String p;
    /* access modifiers changed from: private */
    public TextView q;
    /* access modifiers changed from: private */
    public NumericEditText r;
    private View s;

    /* access modifiers changed from: private */
    public void d() {
    }

    public NumericEditTextWithPrefixAccesibility(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.a = context;
        this.b = attributeSet;
        this.d = new CBaseAccesibility();
        this.c = CAccessibility.getInstance(context);
        a(context, attributeSet);
    }

    public String getPrefix() {
        return this.g;
    }

    public void setPrefix(String str) {
        this.g = str;
        b();
    }

    private void a(Context context, AttributeSet attributeSet) {
        this.e = (LayoutInflater) context.getSystemService("layout_inflater");
        addView(this.e.inflate(R.layout.numeric_edittext_with_prefix_accensibility, null), -1, -2);
        this.s = findViewById(R.id.rll_input);
        this.r = (NumericEditText) findViewById(R.id.inp_number);
        this.q = (TextView) findViewById(R.id.lbl_prefix);
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.NumericEditTextWithPrefixAccesibility);
        this.g = obtainStyledAttributes.getString(5);
        if (this.g == null) {
            this.g = "";
        }
        this.h = obtainStyledAttributes.getString(1);
        if (this.h == null) {
            this.h = "";
        }
        this.i = obtainStyledAttributes.getDimensionPixelSize(9, (int) this.r.getTextSize());
        this.j = obtainStyledAttributes.getColor(8, this.r.getCurrentTextColor());
        this.k = obtainStyledAttributes.getColor(2, this.q.getCurrentTextColor());
        this.l = obtainStyledAttributes.getInt(0, this.l);
        this.m = obtainStyledAttributes.getInt(3, this.m);
        this.n = obtainStyledAttributes.getInt(4, this.n);
        this.o = obtainStyledAttributes.getBoolean(6, this.o);
        this.p = obtainStyledAttributes.getString(7);
        if (this.p == null) {
            this.p = "";
        }
        setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                NumericEditTextWithPrefixAccesibility.this.e();
            }
        });
        setFocusable(true);
        setFocusableInTouchMode(true);
        setOnFocusChangeListener(this);
        setLayoutParams(new LayoutParams(-1, -2));
        setBackground(getResources().getDrawable(R.drawable.edittext_complete));
        this.r.setText(this.p);
        this.r.setTextColor(this.j);
        this.r.setTextSize(0, (float) this.i);
        this.r.setMaxPlaceDecimal(this.m);
        this.r.setMaxPlaceInteger(this.n);
        this.r.setImeOptions(6);
        c();
        if (this.m <= 0) {
            this.r.setRawInputType(0);
            this.r.setKeyListener(DigitsKeyListener.getInstance("1234567890"));
        }
        this.r.setSingleLine(this.o);
        this.r.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                if (NumericEditTextWithPrefixAccesibility.this.r.getText().length() > 0) {
                    NumericEditTextWithPrefixAccesibility.this.q.setTextColor(NumericEditTextWithPrefixAccesibility.this.j);
                    NumericEditTextWithPrefixAccesibility.this.r.setTextColor(NumericEditTextWithPrefixAccesibility.this.j);
                    NumericEditTextWithPrefixAccesibility.this.r.setHint("");
                } else {
                    NumericEditTextWithPrefixAccesibility.this.q.setTextColor(NumericEditTextWithPrefixAccesibility.this.k);
                    NumericEditTextWithPrefixAccesibility.this.r.setHintTextColor(NumericEditTextWithPrefixAccesibility.this.k);
                    NumericEditTextWithPrefixAccesibility.this.c();
                }
                NumericEditTextWithPrefixAccesibility.this.d();
                NumericEditTextWithPrefixAccesibility.this.a();
            }
        });
        b();
        this.q.setTextColor(this.k);
        d();
        a();
    }

    /* access modifiers changed from: private */
    public void a() {
        try {
            if (this.r.getText().toString().isEmpty()) {
                View view = this.s;
                CAccessibility cAccessibility = this.c;
                StringBuilder sb = new StringBuilder();
                sb.append(this.g);
                sb.append(UtilsCuentas.SEPARAOR2);
                sb.append(this.h);
                view.setContentDescription(cAccessibility.applyFilterAmount(sb.toString()));
                return;
            }
            View view2 = this.s;
            CAccessibility cAccessibility2 = this.c;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.g);
            sb2.append(UtilsCuentas.SEPARAOR2);
            sb2.append(this.r.getText().toString());
            view2.setContentDescription(cAccessibility2.applyFilterAmount(sb2.toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void b() {
        this.q.setText(this.g);
        if (this.r.getText().length() > 0) {
            this.q.setTextColor(this.j);
        } else {
            this.q.setTextColor(this.k);
        }
        this.q.setTextSize(0, (float) this.i);
        try {
            this.q.setContentDescription(this.c.applyFilterAmount(this.q.getText().toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        a();
    }

    /* access modifiers changed from: private */
    public void c() {
        this.r.setHint(this.h);
    }

    /* access modifiers changed from: private */
    public void e() {
        if (this.r.isEnabled()) {
            this.r.setFocusableInTouchMode(true);
            this.r.requestFocus();
            ((InputMethodManager) getContext().getSystemService("input_method")).showSoftInput(this.r, 1);
            d();
        }
    }

    public Editable getText() {
        return this.r.getText();
    }

    public void setText(String str) {
        this.r.setText(str);
    }

    public Editable getFormatedText() {
        Editable text = this.r.getText();
        if (this.m > 0) {
            if (text.toString().contains(",")) {
                String[] split = text.toString().split(",");
                String str = split.length > 1 ? split[1] : "";
                if (str.length() < this.m) {
                    text.append(new String(new char[(this.m - str.length())]).replace("\u0000", "0"));
                }
            } else if (TextUtils.isEmpty(text)) {
                text.append(TarjetasConstants.FORMATTED_ZERO);
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(",");
                sb.append(new String(new char[this.m]).replace("\u0000", "0"));
                text.append(sb.toString());
            }
        }
        return text;
    }

    public void addTextChangedListener(TextWatcher textWatcher) {
        this.r.addTextChangedListener(textWatcher);
    }

    public void setEnabled(boolean z) {
        this.r.setEnabled(z);
    }

    public void onFocusChange(View view, boolean z) {
        if (z) {
            e();
        }
    }
}
