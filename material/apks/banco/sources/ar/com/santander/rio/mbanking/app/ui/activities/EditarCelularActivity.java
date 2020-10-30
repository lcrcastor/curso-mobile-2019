package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.commons.CEditTextUtils;
import ar.com.santander.rio.mbanking.app.ui.constants.PagoTarjetasConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsDescriptionBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListGroupBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListTableBean;
import ar.com.santander.rio.mbanking.view.EditTextValidator;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnFocusChange;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.squareup.otto.Bus;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class EditarCelularActivity extends BaseActivity implements OnClickListener, IDialogListener {
    public static String EMPRESA = "empresa";
    public static String PREFIX_COD_AREA = "0";
    public static String PREFIX_NUMERO = "15";
    public static String PRINCIPAL = "esCelularPrincipal";
    public static String TELEFONO = "telefono";
    public static String TITLE = "title";
    @InjectView(2131364117)
    Button aceptarButton;
    @InjectView(2131364302)
    EditTextValidator codigoArea;
    @InjectView(2131364754)
    TextView empresaCelular;
    @InjectView(2131365272)
    EditTextValidator numero;
    @Inject
    SessionManager p;
    @Inject
    Bus q;
    @Inject
    AnalyticsManager r;
    List<ListGroupBean> s;
    private boolean t;
    private boolean u = false;
    @InjectView(2131366350)
    TextView vTitle;

    public void onNegativeButton() {
    }

    public void onPositiveButton() {
    }

    public void onSimpleActionButton() {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setContentView((int) R.layout.editar_celular_activity);
        findViewById(R.id.editarCelularLayout).requestFocus();
        getWindow().setSoftInputMode(2);
        this.r.trackScreen(getString(R.string.analytics_screen_name_login_actualizacion_celular));
        ButterKnife.inject((Activity) this);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setElevation(BitmapDescriptorFactory.HUE_RED);
        supportActionBar.setDisplayHomeAsUpEnabled(false);
        supportActionBar.setHomeButtonEnabled(false);
        supportActionBar.setDisplayShowCustomEnabled(true);
        supportActionBar.setDisplayShowTitleEnabled(false);
        supportActionBar.setDisplayUseLogoEnabled(false);
        View inflate = ((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.actionbar_back_row, null);
        supportActionBar.setCustomView(inflate, new LayoutParams(-1, -1));
        inflate.findViewById(R.id.toggle).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                EditarCelularActivity.this.onBackPressed();
            }
        });
        this.aceptarButton.setOnClickListener(this);
        this.empresaCelular.setOnClickListener(this);
        if (getIntent().getExtras() != null) {
            String string = getIntent().getExtras().getString(TELEFONO);
            String string2 = getIntent().getExtras().getString(EMPRESA);
            String string3 = getIntent().getExtras().getString(TITLE);
            if (string2 != null && !string2.equalsIgnoreCase("")) {
                onItemSelected(string2);
            }
            this.vTitle.setText(string3);
            this.t = getIntent().getExtras().getBoolean(PRINCIPAL);
            if (!string.equalsIgnoreCase("") && !string2.equalsIgnoreCase("") && !string.equalsIgnoreCase(getString(R.string.ID412_SUSSOPR_MAIN_BTN_ADDPHONE))) {
                a(string, string2);
            }
        }
        b();
    }

    private void a(String str, String str2) {
        String[] split = str.split("-");
        this.codigoArea.setText(split[0].replaceFirst(PREFIX_COD_AREA, ""));
        this.numero.setText(split[1].replaceFirst(PREFIX_NUMERO, ""));
        this.empresaCelular.setText(str2);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.aceptarButton) {
            hideKeyboard();
            String obj = this.codigoArea.getText().toString();
            String obj2 = this.numero.getText().toString();
            this.empresaCelular.getText().toString();
            if (!this.t && obj.length() == 0 && obj2.length() == 0) {
                Intent intent = new Intent();
                intent.putExtra("eliminar_celular", true);
                StringBuilder sb = new StringBuilder();
                sb.append(PREFIX_COD_AREA);
                sb.append(obj);
                sb.append("-");
                sb.append(PREFIX_NUMERO);
                sb.append(obj2);
                intent.putExtra("celular", sb.toString());
                intent.putExtra("empresaCel", this.empresaCelular.getText().toString());
                intent.putExtra("codCel", a(this.empresaCelular.getText().toString()));
                intent.putExtra(PRINCIPAL, this.t);
                setResult(-1, intent);
                finish();
            } else if (obj.length() + obj2.length() != 10) {
                b(getResources().getString(R.string.MSG_USER000032_Login_errorCelular));
            } else if (!this.u) {
                b(getResources().getString(R.string.MSG_USER000046_Login_avisoSeleccion));
            } else {
                Intent intent2 = new Intent();
                intent2.putExtra("eliminar_celular", false);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(PREFIX_COD_AREA);
                sb2.append(obj);
                sb2.append("-");
                sb2.append(PREFIX_NUMERO);
                sb2.append(obj2);
                intent2.putExtra("celular", sb2.toString());
                intent2.putExtra("empresaCel", this.empresaCelular.getText().toString());
                intent2.putExtra("codCel", a(this.empresaCelular.getText().toString()));
                intent2.putExtra(PRINCIPAL, this.t);
                setResult(-1, intent2);
                finish();
            }
        } else if (view.getId() == R.id.idEmpresaCelular && this.s != null) {
            hideKeyboard();
            ArrayList arrayList = new ArrayList();
            for (ListGroupBean label : this.s) {
                arrayList.add(label.getLabel());
            }
            IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.IDXXX_SUSSOPR_DIALOG_EMPRESA_LBL_TITLE), null, arrayList, getString(R.string.IDX_ALERT_BTN_CANCEL), null, null, this.empresaCelular.getText().toString());
            newInstance.setDialogListener(this);
            newInstance.show(getSupportFragmentManager(), "Dialog");
        }
    }

    private String a(String str) {
        if (this.s != null) {
            for (ListGroupBean listGroupBean : this.s) {
                if (listGroupBean.getLabel().equalsIgnoreCase(str)) {
                    return listGroupBean.code;
                }
            }
        }
        return null;
    }

    public void onBackPressed() {
        super.onBackPressed();
        hideKeyboard();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void onItemSelected(String str) {
        this.u = true;
        this.empresaCelular.setText(str);
        this.empresaCelular.setTextColor(getResources().getColor(R.color.generic_selectable));
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        this.q.register(this);
        super.onResume();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.q.unregister(this);
    }

    /* access modifiers changed from: 0000 */
    @OnFocusChange({2131364302, 2131365272})
    public void a(EditTextValidator editTextValidator, boolean z) {
        CEditTextUtils.cleanHintFocus(editTextValidator, z);
    }

    private void b(String str) {
        IsbanDialogFragment.newInstance(PagoTarjetasConstants.ISBAN_DIALOG_WARNING_TITLE, str, null, PagoTarjetasConstants.ISBAN_DIALOG_ACCEPT_BUTTON_TEXT, null, null, null).show(getSupportFragmentManager(), "Dialog");
    }

    private void b() {
        ConsDescriptionBodyResponseBean consDescripciones = this.p.getConsDescripciones();
        if (consDescripciones != null) {
            List<ListTableBean> list = consDescripciones.listTableBeans;
            if (list != null) {
                for (ListTableBean listTableBean : list) {
                    if (listTableBean.idTable.equalsIgnoreCase("TPOEMPRCEL")) {
                        this.s = listTableBean.listGroupBeans;
                    }
                }
            }
        }
    }
}
