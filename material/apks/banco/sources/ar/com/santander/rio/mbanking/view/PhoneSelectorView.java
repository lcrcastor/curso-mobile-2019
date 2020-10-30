package ar.com.santander.rio.mbanking.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CBaseAccesibility;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CElementAcc;

public class PhoneSelectorView extends LinearLayout {
    private CAccessibility a;
    private LayoutInflater b;
    private Context c;
    private String d;
    private String e;
    private String f;
    private String g;
    private String h;
    private CElementAcc i;
    /* access modifiers changed from: private */
    public PhoneSelectorListener j;

    public interface PhoneSelectorListener {
        void onPhoneSelected(String str, String str2);
    }

    /* JADX INFO: finally extract failed */
    public PhoneSelectorView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.PhoneSelector, 0, 0);
        this.c = context;
        try {
            this.d = obtainStyledAttributes.getString(0);
            this.e = obtainStyledAttributes.getString(2);
            this.f = obtainStyledAttributes.getString(1);
            this.g = obtainStyledAttributes.getString(3);
            obtainStyledAttributes.recycle();
            this.i = new CBaseAccesibility();
            this.a = CAccessibility.getInstance(context);
            a(context);
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }

    private void a(Context context) {
        this.b = (LayoutInflater) context.getSystemService("layout_inflater");
        addView(this.b.inflate(R.layout.celular_selector, null));
        setLabelCelular(this.d);
        setValueCelular(this.e);
        setLabelEmpresa(this.f);
        setValueEmpresa(this.g);
    }

    public void setLabelCelular(String str) {
        ((TextView) findViewById(R.id.idLabelCelular)).setText(str);
        if (this.i != null) {
            this.i.applyFilter((TextView) findViewById(R.id.idLabelCelular), str);
        }
    }

    public void setValueCelular(String str) {
        this.e = str;
        ((TextView) findViewById(R.id.idValueCelular)).setText(str);
        final TextView textView = (TextView) findViewById(R.id.idValueCelular);
        final TextView textView2 = (TextView) findViewById(R.id.idEmpresaValue);
        ((LinearLayout) findViewById(R.id.linearCelular)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (PhoneSelectorView.this.j != null) {
                    PhoneSelectorView.this.j.onPhoneSelected(textView.getText().toString(), textView2.getText().toString());
                }
            }
        });
        if (str.equalsIgnoreCase(this.c.getResources().getString(R.string.ID424_SUSSOPR_MODIF_BTN_ADDNUMBER))) {
            ((TextView) findViewById(R.id.idValueCelular)).setContentDescription(str);
            return;
        }
        try {
            TextView textView3 = (TextView) findViewById(R.id.idValueCelular);
            StringBuilder sb = new StringBuilder();
            sb.append(this.c.getResources().getString(R.string.ACCESSIBILITY_SUSCRIPCION_MODIFICAR_CELULAR));
            sb.append(this.a.applyFilterCharacterToCharacter(str));
            textView3.setContentDescription(sb.toString());
        } catch (Exception unused) {
        }
    }

    public void setLabelEmpresa(String str) {
        ((TextView) findViewById(R.id.idLabelEmpresa)).setText(str);
        if (this.i != null) {
            this.i.applyFilter((TextView) findViewById(R.id.idLabelEmpresa), str);
        }
    }

    public void setValueEmpresa(String str) {
        if (str == null || str.isEmpty()) {
            a(false);
            return;
        }
        this.g = str;
        ((TextView) findViewById(R.id.idEmpresaValue)).setText(str);
        ((TextView) findViewById(R.id.idEmpresaValue)).setContentDescription(str);
        a(true);
    }

    private void a(boolean z) {
        if (z) {
            findViewById(R.id.empresaCelular).setVisibility(0);
        } else {
            findViewById(R.id.empresaCelular).setVisibility(8);
        }
    }

    public void setVoidPhone() {
        findViewById(R.id.empresaCelular).setVisibility(8);
        setLabelCelular(this.c.getString(R.string.ID414_SUSSOPR_MAIN_LBL_SECONDPHONE));
        setValueCelular(this.c.getString(R.string.ID415_SUSSOPR_MAIN_BTN_ADDNUMBER));
        setValueEmpresa("");
        setLabelEmpresa(this.c.getString(R.string.ID413_SUSSOPR_MAIN_LBL_COMPANY));
    }

    public PhoneSelectorListener getPhoneSelectorListener() {
        return this.j;
    }

    public void setPhoneSelectorListener(PhoneSelectorListener phoneSelectorListener) {
        this.j = phoneSelectorListener;
    }

    public String getCodCelular() {
        return this.h;
    }

    public void setCodCelular(String str) {
        this.h = str;
    }

    public String getValueCelular() {
        return this.e;
    }

    public String getValueEmpresa() {
        return this.g;
    }
}
