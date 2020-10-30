package ar.com.santander.rio.mbanking.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;

public class TelephoneRow extends RelativeLayout {
    private TextView a;
    private Context b;

    public TelephoneRow(Context context) {
        super(context);
        this.b = context;
        a(context, null);
    }

    public TelephoneRow(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.b = context;
        a(context, attributeSet);
    }

    public TelephoneRow(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.b = context;
        a(context, attributeSet);
    }

    private void a(Context context, AttributeSet attributeSet) {
        inflate(getContext(), R.layout.phone, this);
        this.a = (TextView) findViewById(R.id.txtNumber);
        ImageView imageView = (ImageView) findViewById(R.id.imgPhone);
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, new int[]{16843087, 16843033});
            this.a.setText(obtainStyledAttributes.getText(0));
            try {
                setContentDescription(this.b.getString(R.string.ACCESSIBILITY_TELEFONO, new Object[]{CAccessibility.getInstance(context).applyFilterCharacterToCharacter(this.a.getText().toString())}));
            } catch (Exception e) {
                e.printStackTrace();
            }
            obtainStyledAttributes.recycle();
        }
    }

    public String getText() {
        return this.a.getText().toString();
    }

    public void setNumber(String str) {
        if (str != null) {
            String trim = str.trim();
            try {
                setContentDescription(this.b.getString(R.string.ACCESSIBILITY_TELEFONO, new Object[]{CAccessibility.getInstance(this.b).applyFilterPhoneNumber(trim)}));
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.a.setText(trim);
        }
    }
}
