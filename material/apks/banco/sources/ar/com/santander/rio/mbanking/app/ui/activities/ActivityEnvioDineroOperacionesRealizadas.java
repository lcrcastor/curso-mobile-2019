package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.commons.CExtEnv;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.Model.ObjectDestinatarios;
import ar.com.santander.rio.mbanking.app.ui.Model.ObjectEnvioDineroOperacionesRealizadas;
import ar.com.santander.rio.mbanking.app.ui.adapters.AdapterEnvioDineroOperacionesRealizadas;
import ar.com.santander.rio.mbanking.app.ui.constants.EnvioConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.EnvioConstants.BodyRequest.TipoOperacion;
import ar.com.santander.rio.mbanking.app.ui.constants.EnvioConstants.Estado;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.ConsultaMandatosExtEnvEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaMandatosExtEnvResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ExtEnvFiltroBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VConsultaMandatosExtEnv;
import ar.com.santander.rio.mbanking.utils.UtilStyleCommons;
import ar.com.santander.rio.mbanking.utils.Utils;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import javax.inject.Inject;

public class ActivityEnvioDineroOperacionesRealizadas extends BaseActivity {
    @InjectView(2131362343)
    ImageView imgBusccar;
    @InjectView(2131362345)
    TextView lblBuscar;
    @InjectView(2131362350)
    TextView lblVto;
    @InjectView(2131362349)
    TextView messageEmptyList;
    @Inject
    Bus p;
    @Inject
    IDataManager q;
    @Inject
    AnalyticsManager r;
    @InjectView(2131362355)
    LinearLayout rll_listadoOperaciones;
    @InjectView(2131362356)
    LinearLayout rll_listadoOperacionesVacio;
    @Inject
    SessionManager s;
    ImageView t;
    String u = "Operaciones del día";
    ExtEnvFiltroBean v;
    ArrayList<ObjectDestinatarios> w;
    private ArrayList<ObjectEnvioDineroOperacionesRealizadas> x;
    private String y;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_envio_dinero_operaciones_realizadas);
        ButterKnife.inject((Activity) this);
        setActionBarType(ActionBarType.BACK_ONLY);
        getSupportActionBar().getCustomView();
        this.t = (ImageView) findViewById(R.id.back_imgButton);
        this.t.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ActivityEnvioDineroOperacionesRealizadas.this.setResult(0, new Intent());
                ActivityEnvioDineroOperacionesRealizadas.this.onBackPressed();
            }
        });
        this.x = getIntent().getParcelableArrayListExtra(getResources().getString(R.string.ED_INTENT_PUT_EXTRA_CONSULTAMANDATOS));
        this.y = getIntent().getStringExtra(getResources().getString(R.string.ED_INTENT_PUT_EXTRA_CONSULTAMANDATOS_RES4));
        displayScreen();
        this.r.trackScreen(getString(R.string.analytics_enviodinero_consulta_operaciones));
        this.t.setContentDescription(CAccessibility.applyFilterMaskBotton(getString(R.string.CONTENT_ATRAS)));
        this.lblBuscar.setContentDescription(CAccessibility.applyFilterMaskBotton(this.lblBuscar.getText().toString()));
        this.imgBusccar.setContentDescription(CAccessibility.applyFilterMaskBotton(this.lblBuscar.getText().toString()));
        this.lblVto.setContentDescription(getString(R.string.ID520_CCARDS_LASTBRIEF_LBL_EXPIRATION));
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

    public void displayScreen() {
        if (this.x.size() > 0) {
            this.rll_listadoOperaciones.setVisibility(0);
            this.rll_listadoOperacionesVacio.setVisibility(8);
            ListView listView = (ListView) findViewById(R.id.F12_07_lst_operaciones);
            listView.setAdapter(new AdapterEnvioDineroOperacionesRealizadas(this, this.s, R.layout.layout_item_envio_dinero_operaciones_realizadas, this.x));
            listView.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                    Context context = view.getContext();
                    ObjectEnvioDineroOperacionesRealizadas objectEnvioDineroOperacionesRealizadas = (ObjectEnvioDineroOperacionesRealizadas) adapterView.getItemAtPosition(i);
                    Intent intent = new Intent(ActivityEnvioDineroOperacionesRealizadas.this, ActivityEnvioDineroDetalleOperacion.class);
                    intent.putExtra(context.getString(R.string.ED_INTENT_PUT_EXTRA_ESTADO_OR), objectEnvioDineroOperacionesRealizadas.getEstado());
                    intent.putExtra(context.getString(R.string.ED_INTENT_PUT_EXTRA_FECHA_ALTA_OR), objectEnvioDineroOperacionesRealizadas.getFechaAlta());
                    intent.putExtra(context.getString(R.string.ED_INTENT_PUT_EXTRA_FECHA_VENCIMIENTO_OR), objectEnvioDineroOperacionesRealizadas.getFechaVencimiento());
                    intent.putExtra(context.getString(R.string.INTENT_PUT_EXTRA_CUENTA), objectEnvioDineroOperacionesRealizadas.getCuenta());
                    intent.putExtra(context.getString(R.string.INTENT_PUT_EXTRA_TIPOCUENTA), objectEnvioDineroOperacionesRealizadas.getTipoCuenta());
                    intent.putExtra(context.getString(R.string.INTENT_PUT_EXTRA_SUCURSALCUENTA), objectEnvioDineroOperacionesRealizadas.getSucursalCuenta());
                    intent.putExtra(context.getString(R.string.INTENT_PUT_EXTRA_NUMEROCUENTA), objectEnvioDineroOperacionesRealizadas.getNumeroCuenta());
                    intent.putExtra(context.getString(R.string.INTENT_PUT_EXTRA_NOMBRE), objectEnvioDineroOperacionesRealizadas.getNombre());
                    intent.putExtra(context.getString(R.string.INTENT_PUT_EXTRA_MAIL), objectEnvioDineroOperacionesRealizadas.getEmail());
                    intent.putExtra(context.getString(R.string.INTENT_PUT_EXTRA_TIPODOC), objectEnvioDineroOperacionesRealizadas.getTipoDoc());
                    intent.putExtra(context.getString(R.string.INTENT_PUT_EXTRA_DOCUMENTO), objectEnvioDineroOperacionesRealizadas.getDocumento());
                    intent.putExtra(context.getString(R.string.INTENT_PUT_EXTRA_DINERO), objectEnvioDineroOperacionesRealizadas.getImporte());
                    intent.putExtra(context.getString(R.string.ED_INTENT_PUT_EXTRA_COD_EXTRACCION_OR), objectEnvioDineroOperacionesRealizadas.getCodExtraccion());
                    intent.putExtra(context.getString(R.string.ED_INTENT_PUT_EXTRA_COD_TRANSACCION_OR), objectEnvioDineroOperacionesRealizadas.getCodTransaccion());
                    intent.putExtra(context.getString(R.string.ED_INTENT_PUT_EXTRA_NRO_COMPROBANTE_OR), objectEnvioDineroOperacionesRealizadas.getNroComprobante());
                    intent.putExtra(ActivityEnvioDineroOperacionesRealizadas.this.getResources().getString(R.string.INTENT_PUT_EXTRA_FUNCIONALIDAD), TipoOperacion.EnvioDinero);
                    ActivityEnvioDineroOperacionesRealizadas.this.startActivityForResult(intent, 8);
                }
            });
            return;
        }
        this.r.trackScreen(getString(R.string.analytics_enviodinero_sin_operaciones));
        this.rll_listadoOperaciones.setVisibility(8);
        this.rll_listadoOperacionesVacio.setVisibility(0);
        if (!TextUtils.isEmpty(this.y)) {
            this.messageEmptyList.setText(Html.fromHtml(Utils.formatIsbanHTMLCode(this.y)));
        }
    }

    public void applyLetterSpacing() {
        UtilStyleCommons.setLetterSpacing(findViewById(R.id.F12_07_lbl_operacionesRealizadas), -1.5f);
        UtilStyleCommons.setLetterSpacing(findViewById(R.id.F12_07_lbl_buscar), -1.5f);
        UtilStyleCommons.setLetterSpacing(findViewById(R.id.F12_07_lbl_operRealizadasAlta), -1.5f);
        UtilStyleCommons.setLetterSpacing(findViewById(R.id.F12_07_lbl_operRealizadasDestinatario), -1.5f);
        UtilStyleCommons.setLetterSpacing(findViewById(R.id.F12_07_lbl_operRealizadasImporte), -1.5f);
        UtilStyleCommons.setLetterSpacing(findViewById(R.id.F12_07_lbl_operRealizadasEstado), -1.5f);
        UtilStyleCommons.setLetterSpacing(findViewById(R.id.F12_07_lbl_listadoVacio), -1.5f);
    }

    /* access modifiers changed from: 0000 */
    @OnClick({2131362345, 2131362343})
    public void b() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getString(R.string.ID2057_ENVEFECT_BTN_FILTRO1).replace(getString(R.string.STRING_PARAM_KEY), CExtEnv.getFiltroDias1(this.s)));
        arrayList.add(getString(R.string.ID2057_ENVEFECT_BTN_FILTRO2).replace(getString(R.string.STRING_PARAM_KEY), CExtEnv.getFiltroDias2(this.s)));
        arrayList.add(getString(R.string.ID2058_ENVEFECT_BTN_OPERPEND));
        arrayList.add(getString(R.string.ID2059_ENVEFECT_BTN_BUSAVANZ));
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("opciones_filtro", "Buscar", null, arrayList, getString(R.string.ID2060_ENVEFECT_BTN_CANCELAR), null, null, this.u, arrayList);
        newInstance.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                ActivityEnvioDineroOperacionesRealizadas.this.u = str;
                if (str.equalsIgnoreCase(ActivityEnvioDineroOperacionesRealizadas.this.getString(R.string.ID2047_EXTTARJETA_BTN_OPERDIA))) {
                    ActivityEnvioDineroOperacionesRealizadas.this.r.trackScreen(ActivityEnvioDineroOperacionesRealizadas.this.getString(R.string.analytics_enviodinero_consulta_operaciones_del_dia));
                    ActivityEnvioDineroOperacionesRealizadas.this.r.trackEvent(ActivityEnvioDineroOperacionesRealizadas.this.getString(R.string.analytics_category_enviodinero), ActivityEnvioDineroOperacionesRealizadas.this.getString(R.string.analytics_action_filtrar_operaciones), ActivityEnvioDineroOperacionesRealizadas.this.getString(R.string.analytics_label_operaciones_del_dia));
                    ActivityEnvioDineroOperacionesRealizadas.this.showProgress(VConsultaMandatosExtEnv.nameService);
                    CExtEnv.consultaMandatosEnv(ActivityEnvioDineroOperacionesRealizadas.this.q, "DIA");
                } else if (str.contains(CExtEnv.getFiltroDias1(ActivityEnvioDineroOperacionesRealizadas.this.s))) {
                    ActivityEnvioDineroOperacionesRealizadas.this.r.trackScreen(ActivityEnvioDineroOperacionesRealizadas.this.getString(R.string.analytics_enviodinero_consulta_operaciones_7dias));
                    ActivityEnvioDineroOperacionesRealizadas.this.r.trackEvent(ActivityEnvioDineroOperacionesRealizadas.this.getString(R.string.analytics_category_enviodinero), ActivityEnvioDineroOperacionesRealizadas.this.getString(R.string.analytics_action_filtrar_operaciones), ActivityEnvioDineroOperacionesRealizadas.this.getString(R.string.analytics_label_operaciones_ultimos_7_dias));
                    ActivityEnvioDineroOperacionesRealizadas.this.showProgress(VConsultaMandatosExtEnv.nameService);
                    CExtEnv.consultaMandatosEnv(ActivityEnvioDineroOperacionesRealizadas.this.q, CExtEnv.getFiltroDias1(ActivityEnvioDineroOperacionesRealizadas.this.s));
                } else if (str.contains(CExtEnv.getFiltroDias2(ActivityEnvioDineroOperacionesRealizadas.this.s))) {
                    ActivityEnvioDineroOperacionesRealizadas.this.showProgress(VConsultaMandatosExtEnv.nameService);
                    CExtEnv.consultaMandatosEnv(ActivityEnvioDineroOperacionesRealizadas.this.q, CExtEnv.getFiltroDias2(ActivityEnvioDineroOperacionesRealizadas.this.s));
                } else if (str.equalsIgnoreCase(ActivityEnvioDineroOperacionesRealizadas.this.getString(R.string.ID2058_ENVEFECT_BTN_OPERPEND))) {
                    ActivityEnvioDineroOperacionesRealizadas.this.r.trackScreen(ActivityEnvioDineroOperacionesRealizadas.this.getString(R.string.analytics_enviodinero_consulta_operaciones_pendientes));
                    ActivityEnvioDineroOperacionesRealizadas.this.r.trackEvent(ActivityEnvioDineroOperacionesRealizadas.this.getString(R.string.analytics_category_enviodinero), ActivityEnvioDineroOperacionesRealizadas.this.getString(R.string.analytics_action_filtrar_operaciones), ActivityEnvioDineroOperacionesRealizadas.this.getString(R.string.analytics_label_operaciones_pendientes));
                    ActivityEnvioDineroOperacionesRealizadas.this.showProgress(VConsultaMandatosExtEnv.nameService);
                    CExtEnv.consultaMandatosEnv(ActivityEnvioDineroOperacionesRealizadas.this.q, Estado.Pendiente);
                } else if (str.equalsIgnoreCase(ActivityEnvioDineroOperacionesRealizadas.this.getString(R.string.ID2059_ENVEFECT_BTN_BUSAVANZ))) {
                    Intent intent = new Intent(ActivityEnvioDineroOperacionesRealizadas.this, BusquedaAvanzadaActivity.class);
                    intent.putExtra(ActivityEnvioDineroOperacionesRealizadas.this.getString(R.string.ED_INTENT_PUT_EXTRA_FILTROS_AVANZADOS), ActivityEnvioDineroOperacionesRealizadas.this.v);
                    intent.putParcelableArrayListExtra(ActivityEnvioDineroOperacionesRealizadas.this.getString(R.string.ED_INTENT_PUT_EXTRA_LISTA_DESTINATARIOS), ActivityEnvioDineroOperacionesRealizadas.this.w);
                    intent.putExtra(ActivityEnvioDineroOperacionesRealizadas.this.getString(R.string.INTENT_PUT_EXTRA_FUNCIONALIDAD), TipoOperacion.EnvioDinero);
                    ActivityEnvioDineroOperacionesRealizadas.this.startActivityForResult(intent, 11);
                }
            }
        });
        newInstance.setCancelable(true);
        newInstance.show(getSupportFragmentManager(), "Dialog");
    }

    public void onBackPressed() {
        super.onBackPressed();
        hideKeyboard();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 8) {
            Intent intent2 = new Intent();
            switch (i2) {
                case -1:
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
                    return;
                default:
                    return;
            }
        } else if (i != 11 || i2 != -1) {
        } else {
            if (intent.hasExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION)) {
                Intent intent3 = new Intent();
                intent3.putExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION, intent.getStringExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION));
                if (intent.hasExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE)) {
                    intent3.putExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE, intent.getStringExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE));
                }
                setResult(-1, intent3);
                finish();
            } else if (intent.hasExtra(getString(R.string.ED_INTENT_PUT_EXTRA_FILTROS_AVANZADOS))) {
                this.v = (ExtEnvFiltroBean) intent.getParcelableExtra(getString(R.string.ED_INTENT_PUT_EXTRA_FILTROS_AVANZADOS));
                this.w = intent.getParcelableArrayListExtra(getString(R.string.ED_INTENT_PUT_EXTRA_LISTA_DESTINATARIOS));
                this.x = intent.getParcelableArrayListExtra(getResources().getString(R.string.ED_INTENT_PUT_EXTRA_CONSULTAMANDATOS));
                this.y = intent.getStringExtra(getResources().getString(R.string.ED_INTENT_PUT_EXTRA_CONSULTAMANDATOS_RES4));
                displayScreen();
            }
        }
    }

    @Subscribe
    public void onConsultaMandatos(ConsultaMandatosExtEnvEvent consultaMandatosExtEnvEvent) {
        dismissProgress();
        final ConsultaMandatosExtEnvEvent consultaMandatosExtEnvEvent2 = consultaMandatosExtEnvEvent;
        AnonymousClass4 r0 = new BaseWSResponseHandler(this, TypeOption.INTERMDIATE_VIEW, FragmentConstants.ENVIO_EFECTIVO, this) {
            public void onOk() {
                ActivityEnvioDineroOperacionesRealizadas.this.a(consultaMandatosExtEnvEvent2);
            }

            public void onRes4Error(WebServiceEvent webServiceEvent) {
                ActivityEnvioDineroOperacionesRealizadas.this.a(consultaMandatosExtEnvEvent2);
            }
        };
        r0.handleWSResponse(consultaMandatosExtEnvEvent);
    }

    /* access modifiers changed from: private */
    public void a(ConsultaMandatosExtEnvEvent consultaMandatosExtEnvEvent) {
        try {
            this.x = CExtEnv.getMandatos(consultaMandatosExtEnvEvent.getBeanResponse() != null ? ((ConsultaMandatosExtEnvResponseBean) consultaMandatosExtEnvEvent.getBeanResponse()).consultaMandatosExtEnvBodyResponseBean.listaMandatos : null);
            this.y = consultaMandatosExtEnvEvent.getResult() == TypeResult.BEAN_ERROR_RES_4 ? consultaMandatosExtEnvEvent.getMessageToShow() : "";
            displayScreen();
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onConsultaMandatosResultOk: ", e);
            e.fillInStackTrace();
        }
    }
}
