package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.LayoutParams;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.commons.CExtEnv;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.constants.EnvioConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.EnvioConstants.BodyRequest.TipoOperacion;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.ConsultaAgendadosEnvEfeEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaAgendadosEnvEfeResponseBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VConsultaAgendadosEnvEfe;
import ar.com.santander.rio.mbanking.utils.UtilStyleCommons;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import javax.inject.Inject;

public class ActivityEnvioDineroPrepararEnvio extends BaseActivity {
    @InjectView(2131362450)
    TextView cuentaDebitoEnvio;
    @InjectView(2131362448)
    ImageView image_seleccionarDestinatarioEnvio;
    @InjectView(2131362451)
    TextView importeEnvio;
    @Inject
    Bus p;
    @Inject
    IDataManager q;
    @Inject
    AnalyticsManager r;
    String s;
    @InjectView(2131362455)
    TextView seleccionarDestinatarioEnvio;
    String t;
    String u;
    String v;
    Integer w;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_envio_dinero_preparar_envio);
        ButterKnife.inject((Activity) this);
        setActionBarType(ActionBarType.BACK_ONLY);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setElevation(BitmapDescriptorFactory.HUE_RED);
        supportActionBar.setDisplayOptions(16);
        View inflate = ((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.actionbar_back_only, null);
        supportActionBar.setCustomView(inflate, new LayoutParams(-1, -1, 17));
        ((ImageView) inflate.findViewById(R.id.back_imgButton)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ActivityEnvioDineroPrepararEnvio.this.onBackPressed();
            }
        });
        Intent intent = getIntent();
        this.s = intent.getStringExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_CUENTA));
        this.t = intent.getStringExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_TIPOCUENTA));
        this.u = intent.getStringExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_SUCURSALCUENTA));
        this.v = intent.getStringExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_NUMEROCUENTA));
        this.w = Integer.valueOf(intent.getStringExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_DINERO)));
        this.cuentaDebitoEnvio.setText(this.s);
        this.importeEnvio.setText(CExtEnv.getFormattedValue(this.w));
        AnonymousClass2 r1 = new OnClickListener() {
            public void onClick(View view) {
                ActivityEnvioDineroPrepararEnvio.this.showProgress(VConsultaAgendadosEnvEfe.nameService);
                CExtEnv.consultaAgendados(ActivityEnvioDineroPrepararEnvio.this.q);
            }
        };
        this.seleccionarDestinatarioEnvio.setOnClickListener(r1);
        this.image_seleccionarDestinatarioEnvio.setOnClickListener(r1);
        this.r.trackScreen(getString(R.string.analytics_enviodinero_preparar_envio));
        inflate.findViewById(R.id.back_imgButton).setContentDescription(CAccessibility.applyFilterMaskBotton(getString(R.string.CONTENT_ATRAS)));
        try {
            this.cuentaDebitoEnvio.setContentDescription(new CAccessibility(this).applyFilterAccount(this.s));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            this.importeEnvio.setContentDescription(new CAccessibility(this).applyFilterAmount(this.importeEnvio.getText().toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.seleccionarDestinatarioEnvio.setContentDescription(CAccessibility.applyFilterMaskVinculo(getString(R.string.ID489_TRANSFERENCE_PROOF_DONE_LBL_SELECT)));
        this.image_seleccionarDestinatarioEnvio.setContentDescription(CAccessibility.applyFilterMaskVinculo(getString(R.string.ID489_TRANSFERENCE_PROOF_DONE_LBL_SELECT)));
    }

    public void onBackPressed() {
        super.onBackPressed();
        hideKeyboard();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 4) {
            Intent intent2 = new Intent();
            if (i2 == -1) {
                if (intent.hasExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION)) {
                    intent2.putExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION, intent.getStringExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION));
                    if (intent.hasExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE)) {
                        intent2.putExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE, intent.getStringExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE));
                    }
                } else if (intent.hasExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION)) {
                    intent2.putExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION, intent.getIntExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION, -1));
                }
                intent2.putExtra(EnvioConstants.RECARGAR_PANTALLA_INICIAL_ENV, intent.getBooleanExtra(EnvioConstants.RECARGAR_PANTALLA_INICIAL_ENV, false));
                setResult(-1, intent2);
                finish();
            }
        }
    }

    public void applyLetterSpacing() {
        UtilStyleCommons.setLetterSpacing(findViewById(R.id.F12_17_lbl_prepararEnvio), -1.5f);
        UtilStyleCommons.setLetterSpacing(findViewById(R.id.F12_17_lbl_cuentaDebito), -1.5f);
        UtilStyleCommons.setLetterSpacing(findViewById(R.id.F12_17_lbl_data_cuentaDebito), -1.5f);
        UtilStyleCommons.setLetterSpacing(findViewById(R.id.F12_17_lbl_importe), -1.5f);
        UtilStyleCommons.setLetterSpacing(findViewById(R.id.F12_17_lbl_data_importe), -1.5f);
        UtilStyleCommons.setLetterSpacing(findViewById(R.id.F12_17_lbl_destinatario), -1.5f);
        UtilStyleCommons.setLetterSpacing(findViewById(R.id.F12_17_lbl_seleccionarDestinatario), -1.5f);
    }

    @Subscribe
    public void onConsultaAgendados(ConsultaAgendadosEnvEfeEvent consultaAgendadosEnvEfeEvent) {
        dismissProgress();
        final ConsultaAgendadosEnvEfeEvent consultaAgendadosEnvEfeEvent2 = consultaAgendadosEnvEfeEvent;
        AnonymousClass3 r0 = new BaseWSResponseHandler(this, TypeOption.INTERMDIATE_VIEW, FragmentConstants.ENVIO_EFECTIVO, this) {
            public void onOk() {
                ActivityEnvioDineroPrepararEnvio.this.a(consultaAgendadosEnvEfeEvent2);
            }
        };
        r0.handleWSResponse(consultaAgendadosEnvEfeEvent);
    }

    /* access modifiers changed from: private */
    public void a(ConsultaAgendadosEnvEfeEvent consultaAgendadosEnvEfeEvent) {
        try {
            Intent intent = new Intent(this, ActivityEnvioDineroListadoDestinatarios.class);
            intent.putExtra(getResources().getString(R.string.ED_INTENT_PUT_EXTRA_DESTINATARIOS_MODE), getResources().getString(R.string.ED_INTENT_PUT_EXTRA_DESTINATARIOS_SINGLE_CHOICE));
            intent.putExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_DINERO), this.w.toString());
            intent.putExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_CUENTA), this.s);
            intent.putExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_TIPOCUENTA), this.t);
            intent.putExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_SUCURSALCUENTA), this.u);
            intent.putExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_NUMEROCUENTA), this.v);
            intent.putParcelableArrayListExtra(getResources().getString(R.string.ED_INTENT_PUT_EXTRA_CONSULTAAGENDADOS), CExtEnv.getDestinatarios(((ConsultaAgendadosEnvEfeResponseBean) consultaAgendadosEnvEfeEvent.getBeanResponse()).consultaAgendadosEnvEfeBodyResponseBean.listaDestinatarios));
            intent.putExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_EXTENV_TIPOTRANSACCION), TipoOperacion.EnvioDinero);
            startActivityForResult(intent, 4);
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onConsultaAgendadosResultOk: ", e);
            e.fillInStackTrace();
        }
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
}
