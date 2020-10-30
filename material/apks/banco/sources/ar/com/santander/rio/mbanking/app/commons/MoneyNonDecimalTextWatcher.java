package ar.com.santander.rio.mbanking.app.commons;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import java.text.DecimalFormat;
import java.text.ParseException;

public class MoneyNonDecimalTextWatcher implements TextWatcher {
    private DecimalFormat a = new DecimalFormat("#,###.##");
    private DecimalFormat b;
    private boolean c;
    /* access modifiers changed from: private */
    public EditText d;

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public MoneyNonDecimalTextWatcher(EditText editText) {
        this.a.setDecimalSeparatorAlwaysShown(true);
        this.b = new DecimalFormat("#,###");
        this.d = editText;
        this.d.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View view, boolean z) {
                if (z) {
                    MoneyNonDecimalTextWatcher.this.d.setText("");
                }
            }
        });
        this.c = false;
    }

    public void afterTextChanged(Editable editable) {
        this.d.removeTextChangedListener(this);
        try {
            int length = this.d.getText().length();
            Number parse = this.a.parse(editable.toString().replace(String.valueOf(this.a.getDecimalFormatSymbols().getGroupingSeparator()), ""));
            int selectionStart = this.d.getSelectionStart();
            if (this.c) {
                this.d.setText(this.a.format(parse));
            } else {
                this.d.setText(this.b.format(parse));
            }
            int length2 = selectionStart + (this.d.getText().length() - length);
            if (length2 <= 0 || length2 > this.d.getText().length()) {
                this.d.setSelection(this.d.getText().length() - 1);
            } else {
                this.d.setSelection(length2);
            }
        } catch (NumberFormatException | ParseException unused) {
        }
        this.d.addTextChangedListener(this);
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        this.c = charSequence.toString().contains(String.valueOf(this.a.getDecimalFormatSymbols().getDecimalSeparator()));
    }
}
