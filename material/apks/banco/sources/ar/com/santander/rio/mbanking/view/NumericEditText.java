package ar.com.santander.rio.mbanking.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.commons.CAmountIU;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NumericEditText extends EditText implements OnClickListener, OnEditorActionListener {
    private final char a = '.';
    private final char b = ',';
    private final String c = "^0+(?!$)";
    private String d = null;
    /* access modifiers changed from: private */
    public String e = "";
    private String f;
    private String g = "[^\\d\\,]";
    private int h = 2;
    private int i = 12;
    /* access modifiers changed from: private */
    public StateTextChange j;
    private List<NumericValueWatcher> k = new ArrayList();
    private final TextWatcher l = new TextWatcher() {
        private boolean b = false;

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void afterTextChanged(Editable editable) {
            boolean z;
            if (!this.b) {
                try {
                    String obj = editable.toString();
                    int selectionStart = NumericEditText.this.getSelectionStart();
                    if (NumericEditText.this.j == null || !NumericEditText.this.j.isNewCharacterASymbolDecimalOrGroup()) {
                        z = false;
                    } else {
                        obj = NumericEditText.this.j.f;
                        z = true;
                    }
                    if (obj.endsWith(String.valueOf('.'))) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(obj.substring(0, obj.length() - 1));
                        sb.append(',');
                        obj = sb.toString();
                    }
                    if (NumericEditText.this.b(obj, String.valueOf(',')) <= 1 && !obj.endsWith(String.valueOf('.')) && !obj.equals(String.valueOf(',')) && !NumericEditText.this.c(obj)) {
                        if (!NumericEditText.this.b(obj)) {
                            if (obj.length() == 0) {
                                NumericEditText.this.a();
                                return;
                            }
                            NumericEditText.this.setTextInternal(NumericEditText.this.format(obj.toString()));
                            if (z) {
                                NumericEditText.this.setSelection(selectionStart);
                            } else {
                                int length = NumericEditText.this.getText().length() - NumericEditText.this.j.a.length();
                                if (selectionStart == NumericEditText.this.getText().length()) {
                                    NumericEditText.this.setSelection(NumericEditText.this.getText().length());
                                } else if (selectionStart == 0) {
                                    NumericEditText.this.setSelection(0);
                                } else if (selectionStart + length > NumericEditText.this.getText().length()) {
                                    NumericEditText.this.setSelection(NumericEditText.this.getText().length());
                                } else if (length > 0) {
                                    NumericEditText.this.setSelection(selectionStart + (length - 1));
                                } else if (length < 0) {
                                    NumericEditText.this.setSelection(selectionStart + length + 1);
                                } else if (length == 0) {
                                    NumericEditText numericEditText = NumericEditText.this;
                                    if (selectionStart > 0) {
                                        selectionStart--;
                                    }
                                    numericEditText.setSelection(selectionStart);
                                }
                            }
                            NumericEditText.this.b();
                            return;
                        }
                    }
                    this.b = true;
                    NumericEditText.this.setText(NumericEditText.this.e);
                    if (selectionStart > NumericEditText.this.getText().length()) {
                        NumericEditText.this.setSelection(NumericEditText.this.getText().length());
                    } else {
                        NumericEditText numericEditText2 = NumericEditText.this;
                        if (selectionStart > 0) {
                            selectionStart--;
                        }
                        numericEditText2.setSelection(selectionStart);
                    }
                    this.b = false;
                } catch (Exception e) {
                    Log.e("@dev", "Error text changed", e);
                    NumericEditText.this.setText("");
                }
            }
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            try {
                NumericEditText.this.j = new StateTextChange();
                NumericEditText.this.j.a = charSequence.toString();
                NumericEditText.this.j.b = i2;
                NumericEditText.this.j.c = i;
                NumericEditText.this.j.d = i3;
                NumericEditText.this.j.e = charSequence.toString().substring(i, i2 + i);
                NumericEditText.this.j.f = NumericEditText.this.a(charSequence.toString(), i);
            } catch (Exception e) {
                Log.e("@dev", "Error before text changed", e);
            }
        }
    };

    public interface NumericValueWatcher {
        void onChanged(double d);

        void onCleared();
    }

    public class StateTextChange {
        String a;
        int b;
        int c;
        int d;
        String e;
        String f;

        public StateTextChange() {
        }

        public boolean isNewCharacterASymbolDecimalOrGroup() {
            return String.valueOf(',').equals(this.e) || String.valueOf('.').equals(this.e);
        }
    }

    public void onClick(View view) {
    }

    public NumericEditText(Context context) {
        super(context);
        c();
    }

    public NumericEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setCustomAttributes(attributeSet);
        c();
        setOnEditorActionListener(this);
    }

    public void onFocusChangeExtend(View view, boolean z) {
        EditText editText = (EditText) view;
        String obj = editText.getText().toString();
        if (z) {
            try {
                if (CAmountIU.getInstance().getDoubleFromInputUser(obj).doubleValue() == 0.0d) {
                    editText.setText("");
                }
            } catch (Exception unused) {
                Log.e("@dev", "Error al borrar la caja");
            }
        } else if (obj.endsWith(String.valueOf(','))) {
            editText.setText(d(obj));
        } else if (obj == null || obj.toString().isEmpty() || "".equals(obj.toString().replace("\\s", ""))) {
            editText.setText("0");
        }
    }

    public boolean onEditorAction(TextView textView, int i2, KeyEvent keyEvent) {
        if (i2 != 6 || !(getContext() instanceof BaseActivity)) {
            return false;
        }
        ((BaseActivity) getContext()).hideKeyboard();
        return true;
    }

    /* access modifiers changed from: private */
    public void a() {
        this.e = "";
        for (NumericValueWatcher onCleared : this.k) {
            onCleared.onCleared();
        }
    }

    /* access modifiers changed from: private */
    public void b() {
        this.e = getText().toString();
        for (NumericValueWatcher onChanged : this.k) {
            onChanged.onChanged(getNumericValue());
        }
    }

    private void c() {
        setLongClickable(false);
        addTextChangedListener(this.l);
        setOnClickListener(this);
    }

    public void addNumericValueChangedListener(NumericValueWatcher numericValueWatcher) {
        this.k.add(numericValueWatcher);
    }

    public void removeAllNumericValueChangedListeners() {
        while (!this.k.isEmpty()) {
            this.k.remove(0);
        }
    }

    public void setDefaultNumericValue(double d2, String str) {
        this.d = String.format(str, new Object[]{Double.valueOf(d2)});
        setTextInternal(this.d);
    }

    public void clear() {
        setTextInternal(this.d != null ? this.d : "");
        if (this.d != null) {
            b();
        }
    }

    public double getNumericValue(Locale locale) {
        try {
            return NumberFormat.getNumberInstance(locale).parse(getText().toString().replaceAll(this.g, "")).doubleValue();
        } catch (ParseException unused) {
            return Double.NaN;
        }
    }

    public double getNumericValue() {
        try {
            return NumberFormat.getNumberInstance(new Locale("es", "ES")).parse(getText().toString().replaceAll(this.g, "")).doubleValue();
        } catch (ParseException unused) {
            return Double.NaN;
        }
    }

    /* access modifiers changed from: protected */
    public String format(String str) {
        String[] split = str.split("\\,", -1);
        String a2 = a(a(a(split[0].replaceAll(this.g, "").replaceFirst("^0+(?!$)", "")).replaceAll("(.{3})", "$1.")), String.valueOf('.'));
        if (split.length <= 1) {
            return a2;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(a2);
        sb.append(',');
        sb.append(split[1]);
        return sb.toString();
    }

    /* access modifiers changed from: private */
    public String a(String str, int i2) {
        this.f = "";
        if (i2 > 0) {
            int i3 = i2 + 1;
            if (str.length() > i3 && (str.substring(i2, i3).equals(String.valueOf('.')) || str.substring(i2, i3).equals(String.valueOf(',')))) {
                this.f = str.substring(0, i2 - 1);
                if (str.length() >= i3) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(this.f);
                    sb.append(str.substring(i3, str.length()));
                    this.f = sb.toString();
                }
            }
        }
        return this.f;
    }

    /* access modifiers changed from: private */
    public void setTextInternal(String str) {
        removeTextChangedListener(this.l);
        setText(str);
        addTextChangedListener(this.l);
    }

    private String a(String str) {
        return (str == null || str.length() <= 1) ? str : TextUtils.getReverse(str, 0, str.length()).toString();
    }

    private String a(String str, String str2) {
        return (!TextUtils.isEmpty(str) && str.startsWith(str2)) ? str.substring(str2.length()) : str;
    }

    /* access modifiers changed from: private */
    public int b(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        int lastIndexOf = str.lastIndexOf(str2);
        if (lastIndexOf < 0) {
            return 0;
        }
        return b(str.substring(0, lastIndexOf), str2) + 1;
    }

    /* access modifiers changed from: private */
    public boolean b(String str) {
        boolean z = false;
        if (str.contains(String.valueOf(','))) {
            if (this.h <= 0) {
                return true;
            }
            String[] split = str.split(String.valueOf(','));
            if (split.length > 1) {
                if (split[1].length() > this.h) {
                    z = true;
                }
                return z;
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public boolean c(String str) {
        String[] split = str.split(String.valueOf(','));
        boolean z = false;
        if (split.length != 0) {
            str = split[0];
        }
        if (this.i <= 0) {
            return false;
        }
        if (str.replace(String.valueOf('.'), "").length() > this.i) {
            z = true;
        }
        return z;
    }

    private String d(String str) {
        String[] split = str.split(String.valueOf(','));
        return split.length > 0 ? split[0] : str;
    }

    private void setCustomAttributes(AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.NumericEditText);
        this.h = obtainStyledAttributes.getInt(0, this.h);
        this.i = obtainStyledAttributes.getInt(1, this.i);
    }

    public void setMaxPlaceInteger(int i2) {
        this.i = i2;
    }

    public void setMaxPlaceDecimal(int i2) {
        this.h = i2;
    }

    public void setInputType(int i2) {
        super.setInputType(i2);
    }

    public Editable getFormatedText() {
        Editable text = getText();
        if (this.h > 0) {
            if (text.toString().contains(",")) {
                String str = text.toString().split(",")[1];
                if (str.length() < this.h) {
                    text.append(new String(new char[(this.h - str.length())]).replace("\u0000", "0"));
                }
            } else if (TextUtils.isEmpty(text)) {
                text.append(TarjetasConstants.FORMATTED_ZERO);
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(",");
                sb.append(new String(new char[this.h]).replace("\u0000", "0"));
                text.append(sb.toString());
            }
        }
        return text;
    }
}
