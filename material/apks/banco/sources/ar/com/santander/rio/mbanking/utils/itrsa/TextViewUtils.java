package ar.com.santander.rio.mbanking.utils.itrsa;

import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.TextView;

public class TextViewUtils {
    public static void setTextValue(TextView textView, String str, int i) {
        if (i != 8) {
            i = 4;
        }
        if (TextUtils.isEmpty(str)) {
            ((ViewGroup) textView.getParent()).setVisibility(i);
            return;
        }
        ((ViewGroup) textView.getParent()).setVisibility(0);
        textView.setText(str);
    }

    public static void setTextValue(TextView textView, String str) {
        setTextValue(textView, str, 8);
    }
}
