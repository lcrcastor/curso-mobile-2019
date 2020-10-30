package ar.com.santander.rio.mbanking.app.commons;

import android.widget.EditText;
import ar.com.santander.rio.mbanking.R;

public class CEditTextUtils {
    public static void cleanHintFocus(EditText editText, boolean z) {
        if (z) {
            if (editText.getTag(R.id.tag_focus_hint) == null || editText.getTag(R.id.tag_focus_hint).toString().isEmpty()) {
                editText.setTag(R.id.tag_focus_hint, editText.getHint().toString());
            }
            editText.setHint("");
        } else if (editText.getText().toString().isEmpty()) {
            editText.setHint((String) editText.getTag(R.id.tag_focus_hint));
        }
    }
}
