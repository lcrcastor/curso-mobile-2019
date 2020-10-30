package ar.com.santander.rio.mbanking.utils.itrsa;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.TextView.BufferType;

public class HtmlTextView extends AppCompatTextView {
    public HtmlTextView(Context context) {
        super(context);
    }

    public HtmlTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public HtmlTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setText(CharSequence charSequence, BufferType bufferType) {
        super.setText(Html.fromHtml(charSequence.toString()), bufferType);
    }
}
