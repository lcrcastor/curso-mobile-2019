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
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.ui.constants.LoginConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.ProgresIndicator;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.checkversion.CheckVersionManager.RESPONSE_CODES;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.ModificarSuscripcionEvent;
import ar.com.santander.rio.mbanking.services.events.RegistrarSuscripcionEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult;
import ar.com.santander.rio.mbanking.services.model.general.Destino;
import ar.com.santander.rio.mbanking.services.model.general.Destinos;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ErrorBodyBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LoginUnicoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VModificarSuscripcion;
import ar.com.santander.rio.mbanking.services.soap.versions.VRegistrarSuscripcion;
import ar.com.santander.rio.mbanking.utils.Utils;
import ar.com.santander.rio.mbanking.utils.zurich_sdk.Constants.ResultValues;
import ar.com.santander.rio.mbanking.view.EditTextValidator;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.indra.httpclient.beans.IBeanErroWS;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import javax.inject.Inject;

public class SuscripcionActivity extends BaseActivity implements OnClickListener {
    public static String ESTADO_SUSCRIPCION = "ESTADO_SUSCRIPCION";
    @InjectView(2131364117)
    Button aceptarButton;
    @InjectView(2131364659)
    EditTextValidator editTextMail;
    @InjectView(2131364660)
    LinearLayout empresaCelular;
    @InjectView(2131364763)
    View idLine;
    @InjectView(2131365023)
    LinearLayout linearCelular;
    @InjectView(2131365024)
    LinearLayout linearCelular1;
    @InjectView(2131365099)
    LinearLayout linearMail;
    @Inject
    Bus p;
    @Inject
    IDataManager q;
    @Inject
    SessionManager r;
    @Inject
    AnalyticsManager s;
    private boolean t;
    @InjectView(2131364751)
    TextView textViewCelular;
    @InjectView(2131364754)
    TextView textViewEmpresaCelular;
    private String u;
    private ProgresIndicator v;
    private String w = "";
    private boolean x = false;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setContentView((int) R.layout.suscripcion_activity);
        getWindow().setSoftInputMode(2);
        this.s.trackScreen(getString(R.string.analytics_screen_name_login_actualizacion_suscripcion));
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
                SuscripcionActivity.this.onBackPressed();
            }
        });
        this.u = getIntent().getExtras().getString(ESTADO_SUSCRIPCION);
        if (this.u.equalsIgnoreCase(LoginConstants.SUSC_ALTA_CEL)) {
            this.linearMail.setVisibility(8);
            this.idLine.setVisibility(8);
        } else if (this.u.equalsIgnoreCase(LoginConstants.SUSC_ALTA_MAIL)) {
            this.linearCelular1.setVisibility(8);
            this.empresaCelular.setVisibility(8);
        }
        this.aceptarButton.setOnClickListener(this);
        this.linearCelular.setOnClickListener(this);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.aceptarButton) {
            LoginUnicoBodyResponseBean loginUnico = this.r.getLoginUnico();
            if (this.u.equalsIgnoreCase(LoginConstants.SUSC_NO_SUSC)) {
                if (!this.editTextMail.validate()) {
                    this.s.trackEvent(getString(R.string.analytics_category_login), getString(R.string.analytics_action_login_error_validacion_actualizacion), "mail");
                    a(getResources().getString(R.string.MSG_USER000033_Login_errorCorreo));
                } else if (!this.t) {
                    this.s.trackEvent(getString(R.string.analytics_category_login), getString(R.string.analytics_action_login_error_validacion_actualizacion), "celular");
                    a(getResources().getString(R.string.MSG_USER000032_Login_errorCelular));
                } else if (loginUnico != null) {
                    String nup = loginUnico.getDatosPersonales().getNup();
                    String obj = this.editTextMail.getText().toString();
                    String charSequence = this.textViewCelular.getText().toString();
                    this.s.trackEvent(getString(R.string.analytics_category_login_primer_ingreso), getString(R.string.analytics_action_login_actualizacion_mail_celular), getString(R.string.analytics_label_login_actualizacion_mail_celular));
                    this.v = ProgresIndicator.newInstance(VRegistrarSuscripcion.nameService);
                    this.v.show(getSupportFragmentManager(), "");
                    this.q.registrarSuscripcionMyA(nup, obj, Utils.getPhoneFormatForService(charSequence), this.w);
                }
            } else if (this.u.equalsIgnoreCase(LoginConstants.SUSC_ALTA_CEL)) {
                String charSequence2 = this.textViewCelular.getText().toString();
                this.textViewEmpresaCelular.getText().toString();
                if (this.t) {
                    this.v = ProgresIndicator.newInstance(VModificarSuscripcion.nameService);
                    this.q.modificarSuscripcionMyA(a("CEL", charSequence2, this.w));
                    this.v.show(getSupportFragmentManager(), "");
                    return;
                }
                this.s.trackEvent(getString(R.string.analytics_category_login), getString(R.string.analytics_action_login_error_validacion_actualizacion), "celular");
                a(getResources().getString(R.string.MSG_USER000032_Login_errorCelular));
            } else if (this.u.equalsIgnoreCase(LoginConstants.SUSC_ALTA_MAIL)) {
                String obj2 = this.editTextMail.getText().toString();
                if (this.editTextMail.validate()) {
                    this.v = ProgresIndicator.newInstance(VModificarSuscripcion.nameService);
                    this.q.modificarSuscripcionMyA(a("MAIL", obj2, ""));
                    this.v.show(getSupportFragmentManager(), "");
                    return;
                }
                this.s.trackEvent(getString(R.string.analytics_category_login), getString(R.string.analytics_action_login_error_validacion_actualizacion), "mail");
                a(getResources().getString(R.string.MSG_USER000033_Login_errorCorreo));
            }
        } else if (view.getId() != R.id.linearCelular) {
        } else {
            if (this.x) {
                a(this.textViewCelular.getText().toString(), this.textViewEmpresaCelular.getText().toString(), true, getString(R.string.ID63_LOGIN_EDITMOBILE_LBL_TITLE));
            } else {
                a("", "", true, getString(R.string.ID63_LOGIN_EDITMOBILE_LBL_TITLE));
            }
        }
    }

    public void onBackPressed() {
        c();
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 1 && i2 == -1) {
            String stringExtra = intent.getStringExtra("celular");
            String stringExtra2 = intent.getStringExtra("empresaCel");
            this.w = intent.getStringExtra("codCel");
            if (stringExtra2.equalsIgnoreCase("")) {
                this.t = false;
                a(getResources().getString(R.string.MSG_USER000032_Login_errorCelular));
                return;
            }
            this.textViewEmpresaCelular.setText(stringExtra2);
            this.textViewEmpresaCelular.setTextColor(getResources().getColor(R.color.red_light));
            if (stringExtra.equalsIgnoreCase("errorLength")) {
                this.t = false;
                a(getResources().getString(R.string.MSG_USER000032_Login_errorCelular));
                return;
            }
            this.t = true;
            this.x = true;
            this.textViewCelular.setTextColor(getResources().getColor(R.color.red_light));
            this.textViewCelular.setText(stringExtra);
        }
    }

    @Subscribe
    public void onRegistrarSuscripcion(RegistrarSuscripcionEvent registrarSuscripcionEvent) {
        a((WebServiceEvent) registrarSuscripcionEvent);
    }

    @Subscribe
    public void onModificarSuscripcion(ModificarSuscripcionEvent modificarSuscripcionEvent) {
        a((WebServiceEvent) modificarSuscripcionEvent);
    }

    private void a(WebServiceEvent webServiceEvent) {
        this.v.dismiss();
        if (webServiceEvent.getResult() == TypeResult.OK) {
            ErrorBodyBean errorBodyBean = (ErrorBodyBean) ((IBeanErroWS) webServiceEvent.getBeanResponse()).getErrorBeanObject();
            if (errorBodyBean == null || !RESPONSE_CODES.ERROR.equalsIgnoreCase(errorBodyBean.res)) {
                b();
                return;
            }
            IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(errorBodyBean.resTitle, errorBodyBean.resDesc, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null, null);
            newInstance.show(getSupportFragmentManager(), "Dialog");
            newInstance.setDialogListener(new IDialogListener() {
                public void onItemSelected(String str) {
                    SuscripcionActivity.this.b();
                }

                public void onPositiveButton() {
                    SuscripcionActivity.this.b();
                }

                public void onNegativeButton() {
                    SuscripcionActivity.this.b();
                }

                public void onSimpleActionButton() {
                    SuscripcionActivity.this.b();
                }
            });
        } else if (webServiceEvent.getResult() != TypeResult.BEAN_WARNING) {
            checkError(webServiceEvent, "");
        }
    }

    /* access modifiers changed from: private */
    public void b() {
        Intent intent = new Intent();
        intent.putExtra(LoginConstants.SUSCRIPCION, ResultValues.OK);
        setResult(-1, intent);
        finish();
    }

    private Destinos a(String str, String str2, String str3) {
        Destinos destinos = new Destinos();
        ArrayList arrayList = new ArrayList();
        Destino destino = new Destino();
        destino.setDestinoTipo(str);
        destino.setDestinoSecuencia("1");
        if ("MAIL".equalsIgnoreCase(str)) {
            destino.setDestinoDescripcion(str2);
        } else {
            destino.setDestinoDescripcion(Utils.getPhoneFormatForService(str2));
        }
        destino.setDestinoEmpresaCel(str3);
        destino.setCodOperacion("A");
        arrayList.add(destino);
        destinos.setDestinos(arrayList);
        return destinos;
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        this.p.register(this);
        super.onResume();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.p.unregister(this);
    }

    private void a(String str) {
        IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), str, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null, null).show(getSupportFragmentManager(), "Dialog");
    }

    private void c() {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getResources().getString(R.string.MSG_USER000030_Menu_avisoSalir), null, null, "Sí", "No", null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                SuscripcionActivity.this.q.logOutSession();
                Intent intent = new Intent();
                intent.putExtra(LoginConstants.SUSCRIPCION, LoginConstants.CANCEL);
                SuscripcionActivity.this.setResult(-1, intent);
                SuscripcionActivity.this.finish();
                SuscripcionActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        newInstance.show(getSupportFragmentManager(), "Dialog");
    }

    private void a(String str, String str2, boolean z, String str3) {
        Intent intent = new Intent(this, EditarCelularActivity.class);
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "";
        }
        Bundle bundle = new Bundle();
        bundle.putString(EditarCelularActivity.TELEFONO, str);
        bundle.putString(EditarCelularActivity.EMPRESA, str2);
        bundle.putBoolean(EditarCelularActivity.PRINCIPAL, z);
        bundle.putString(EditarCelularActivity.TITLE, str3);
        intent.putExtras(bundle);
        startActivityForResult(intent, 1);
    }
}
