package ar.com.santander.rio.mbanking.app.commons;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;

public class TableViewRowCreator {
    public static View createRowLabelValue(Context context, int i, String str, String str2, String str3, String str4) {
        View inflate = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(i, null);
        inflate.setImportantForAccessibility(1);
        TextView textView = (TextView) inflate.findViewById(R.id.row_label);
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        textView.setText(Html.fromHtml(str));
        try {
            if (TextUtils.isEmpty(str2)) {
                str2 = CAccessibility.getInstance(context).applyFilterGeneral(textView.getText().toString());
            }
            textView.setContentDescription(Html.fromHtml(str2));
        } catch (Exception unused) {
        }
        TextView textView2 = (TextView) inflate.findViewById(R.id.row_value);
        if (TextUtils.isEmpty(str3)) {
            str3 = "";
        }
        textView2.setText(Html.fromHtml(str3));
        try {
            if (TextUtils.isEmpty(str4)) {
                str4 = CAccessibility.getInstance(context).applyFilterGeneral(textView2.getText().toString());
            }
            textView2.setContentDescription(Html.fromHtml(str4));
        } catch (Exception unused2) {
        }
        return inflate;
    }
}
