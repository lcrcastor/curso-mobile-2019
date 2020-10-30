package ar.com.santander.rio.mbanking.app.commons;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import java.text.NumberFormat;
import java.util.Locale;

public class MoneyTextWatcher implements TextWatcher {
    boolean a = false;
    EditText b;
    boolean c = false;

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public MoneyTextWatcher(EditText editText, boolean z) {
        this.b = editText;
        this.c = z;
    }

    public void afterTextChanged(Editable editable) {
        if (!this.a) {
            this.a = true;
            Locale locale = new Locale("es", "AR");
            String replaceAll = editable.toString().replaceAll("\\D", "");
            if (this.c) {
                try {
                    editable.replace(0, editable.length(), NumberFormat.getNumberInstance(locale).format((long) Integer.parseInt(replaceAll)));
                } catch (NumberFormatException unused) {
                    editable.clear();
                }
            } else {
                try {
                    editable.replace(0, editable.length(), NumberFormat.getCurrencyInstance(locale).format(Double.parseDouble(replaceAll) / 100.0d));
                } catch (NumberFormatException unused2) {
                    editable.clear();
                }
            }
            this.a = false;
        }
    }
}
