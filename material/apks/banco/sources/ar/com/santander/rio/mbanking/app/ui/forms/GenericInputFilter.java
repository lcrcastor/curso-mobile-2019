package ar.com.santander.rio.mbanking.app.ui.forms;

import android.text.InputFilter;
import android.text.Spanned;

public class GenericInputFilter implements InputFilter {
    private String a = "abcdefghijklmnopqrstuvwxyzQWERTYUIOPASDFGHJKLÑZXCVBNMÁÉÍÓÚ1234567890áéíóú ";

    public GenericInputFilter setCharsetArray(String str) {
        if (str != null) {
            this.a = str;
        }
        return this;
    }

    public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
        StringBuilder sb = new StringBuilder();
        for (int i5 = i; i5 < i2; i5++) {
            char charAt = charSequence.charAt(i5);
            String str = this.a;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("");
            sb2.append(charAt);
            if (str.contains(sb2.toString())) {
                sb.append(charAt);
            }
        }
        if (sb.length() == i2 - i) {
            return null;
        }
        return sb.toString();
    }
}
