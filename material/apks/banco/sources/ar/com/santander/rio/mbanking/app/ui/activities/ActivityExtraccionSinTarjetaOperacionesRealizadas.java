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
import ar.com.santander.rio.mbanking.app.ui.adapters.AdapterExtraccionSinTarjetaOperacionesRealizadas;
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
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult;
import ar.com.santander.rio.mbanking.services.model.general.DatosPersonales;
import ar.com.santander.rio.mbanking.services.model.general.Destino;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaMandatosExtEnvResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ExtEnvFiltroBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VConsultaMandatosExtEnv;
import ar.com.santander.rio.mbanking.utils.UtilString;
import ar.com.santander.rio.mbanking.utils.UtilStyleCommons;
import ar.com.santander.rio.mbanking.utils.Utils;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.Iterator;
import javax.inject.Inject;

public class ActivityExtraccionSinTarjetaOperacionesRealizadas extends BaseActivity {
    @InjectView(2131362581)
    ImageView img_buscarFlecha;
    @InjectView(2131362584)
    TextView lbl_buscar;
    @InjectView(2131362586)
    TextView messageEmptyList;
    @Inject
    Bus p;
    @Inject
    IDataManager q;
    @Inject
    SessionManager r;
    @InjectView(2131362593)
    LinearLayout rll_listadoOperaciones;
    @InjectView(2131362594)
    LinearLayout rll_listadoOperacionesVacio;
    @Inject
    AnalyticsManager s;
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
        setContentView((int) R.layout.activity_extraccion_sin_tarjeta_operaciones_realizadas);
        ButterKnife.inject((Activity) this);
        setActionBarType(ActionBarType.BACK_ONLY);
        getSupportActionBar().getCustomView();
        this.t = (ImageView) findViewById(R.id.back_imgButton);
        this.t.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ActivityExtraccionSinTarjetaOperacionesRealizadas.this.setResult(0, new Intent());
                ActivityExtraccionSinTarjetaOperacionesRealizadas.this.onBackPressed();
            }
        });
        this.x = getIntent().getParcelableArrayListExtra(getResources().getString(R.string.ED_INTENT_PUT_EXTRA_CONSULTAMANDATOS));
        this.y = getIntent().getStringExtra(getResources().getString(R.string.ED_INTENT_PUT_EXTRA_CONSULTAMANDATOS_RES4));
        this.t.setContentDescription(CAccessibility.applyFilterMaskBotton(getString(R.string.CONTENT_ATRAS)));
        this.lbl_buscar.setContentDescription(CAccessibility.applyFilterMaskBotton(getString(R.string.ID2042_EXTTARJETA_BTN_BUSCAR)));
        this.img_buscarFlecha.setContentDescription(CAccessibility.applyFilterMaskBotton(getString(R.string.ID2042_EXTTARJETA_BTN_BUSCAR)));
        displayScreen();
        this.s.trackScreen(getString(R.string.analytics_extraccion_operaciones));
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
            ListView listView = (ListView) findViewById(R.id.F19_07_lst_operaciones);
            listView.setAdapter(new AdapterExtraccionSinTarjetaOperacionesRealizadas(this, this.r, R.layout.layout_item_extraccion_sin_tarjeta_operaciones_realizadas, this.x));
            listView.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                    Context context = view.getContext();
                    DatosPersonales datosPersonales = ActivityExtraccionSinTarjetaOperacionesRealizadas.this.r.getLoginUnico().getDatosPersonales();
                    String str = "";
                    Iterator it = ActivityExtraccionSinTarjetaOperacionesRealizadas.this.r.getLoginUnico().getDestinosMyA().getDestinos().getDestinos().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        Destino destino = (Destino) it.next();
                        if (destino.getDestinoTipo().equals("MAIL")) {
                            str = destino.getDestinoDescripcion();
                            break;
                        }
                    }
                    ObjectEnvioDineroOperacionesRealizadas objectEnvioDineroOperacionesRealizadas = (ObjectEnvioDineroOperacionesRealizadas) adapterView.getItemAtPosition(i);
                    Intent intent = new Intent(context, ActivityEnvioDineroDetalleOperacion.class);
                    intent.putExtra(context.getString(R.string.ED_INTENT_PUT_EXTRA_ESTADO_OR), objectEnvioDineroOperacionesRealizadas.getEstado());
                    intent.putExtra(context.getString(R.string.ED_INTENT_PUT_EXTRA_FECHA_ALTA_OR), objectEnvioDineroOperacionesRealizadas.getFechaAlta());
                    intent.putExtra(context.getString(R.string.ED_INTENT_PUT_EXTRA_FECHA_VENCIMIENTO_OR), objectEnvioDineroOperacionesRealizadas.getFechaVencimiento());
                    intent.putExtra(context.getString(R.string.INTENT_PUT_EXTRA_CUENTA), objectEnvioDineroOperacionesRealizadas.getCuenta());
                    intent.putExtra(context.getString(R.string.INTENT_PUT_EXTRA_TIPOCUENTA), objectEnvioDineroOperacionesRealizadas.getTipoCuenta());
                    intent.putExtra(context.getString(R.string.INTENT_PUT_EXTRA_SUCURSALCUENTA), objectEnvioDineroOperacionesRealizadas.getSucursalCuenta());
                    intent.putExtra(context.getString(R.string.INTENT_PUT_EXTRA_NUMEROCUENTA), objectEnvioDineroOperacionesRealizadas.getNumeroCuenta());
                    String string = context.getString(R.string.INTENT_PUT_EXTRA_NOMBRE);
                    StringBuilder sb = new StringBuilder();
                    sb.append(datosPersonales.getNombre());
                    sb.append(UtilsCuentas.SEPARAOR2);
                    sb.append(datosPersonales.getApellido());
                    intent.putExtra(string, UtilString.capitalize(sb.toString()));
                    intent.putExtra(context.getString(R.string.INTENT_PUT_EXTRA_MAIL), str);
                    intent.putExtra(context.getString(R.string.INTENT_PUT_EXTRA_TIPODOC), datosPersonales.getTipoDocumento());
                    intent.putExtra(context.getString(R.string.INTENT_PUT_EXTRA_DOCUMENTO), datosPersonales.getNroDocumento());
                    intent.putExtra(context.getString(R.string.INTENT_PUT_EXTRA_DINERO), objectEnvioDineroOperacionesRealizadas.getImporte());
                    intent.putExtra(context.getString(R.string.ED_INTENT_PUT_EXTRA_COD_EXTRACCION_OR), objectEnvioDineroOperacionesRealizadas.getCodExtraccion());
                    intent.putExtra(context.getString(R.string.ED_INTENT_PUT_EXTRA_COD_TRANSACCION_OR), objectEnvioDineroOperacionesRealizadas.getCodTransaccion());
                    intent.putExtra(context.getString(R.string.ED_INTENT_PUT_EXTRA_NRO_COMPROBANTE_OR), objectEnvioDineroOperacionesRealizadas.getNroComprobante());
                    intent.putExtra(ActivityExtraccionSinTarjetaOperacionesRealizadas.this.getResources().getString(R.string.INTENT_PUT_EXTRA_FUNCIONALIDAD), TipoOperacion.ExtraccionSinTarjeta);
                    ActivityExtraccionSinTarjetaOperacionesRealizadas.this.startActivityForResult(intent, 8);
                }
            });
            return;
        }
        this.s.trackScreen(getString(R.string.analytics_extraccion_sin_operaciones));
        this.rll_listadoOperaciones.setVisibility(8);
        this.rll_listadoOperacionesVacio.setVisibility(0);
        if (!TextUtils.isEmpty(this.y)) {
            this.messageEmptyList.setText(Html.fromHtml(Utils.formatIsbanHTMLCode(this.y)));
        }
    }

    public void applyLetterSpacing() {
        UtilStyleCommons.setLetterSpacing(findViewById(R.id.F19_07_lbl_operacionesRealizadas), -1.5f);
        UtilStyleCommons.setLetterSpacing(findViewById(R.id.F19_07_lbl_buscar), -1.5f);
        UtilStyleCommons.setLetterSpacing(findViewById(R.id.F19_07_lbl_operRealizadasVto), -1.5f);
        UtilStyleCommons.setLetterSpacing(findViewById(R.id.F19_07_lbl_operRealizadasAlta), -1.5f);
        UtilStyleCommons.setLetterSpacing(findViewById(R.id.F19_07_lbl_operRealizadasImporte), -1.5f);
        UtilStyleCommons.setLetterSpacing(findViewById(R.id.F19_07_lbl_operRealizadasEstado), -1.5f);
        UtilStyleCommons.setLetterSpacing(findViewById(R.id.F19_07_lbl_listadoVacio), -1.5f);
    }

    /* access modifiers changed from: 0000 */
    @OnClick({2131362584, 2131362581})
    public void b() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getString(R.string.ID2047_EXTTARJETA_BTN_FILTRO1).replace(getString(R.string.STRING_PARAM_KEY), CExtEnv.getFiltroDias1(this.r)));
        arrayList.add(getString(R.string.ID2048_EXTTARJETA_BTN_FILTRO2).replace(getString(R.string.STRING_PARAM_KEY), CExtEnv.getFiltroDias2(this.r)));
        arrayList.add(getString(R.string.ID2049_EXTTARJETA_BTN_OPERPEND));
        arrayList.add(getString(R.string.ID2050_EXTTARJETA_BTN_BUSQAVANZ));
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("opciones_filtro", "Buscar", null, arrayList, getString(R.string.IDX_ALERT_BTN_CANCEL), null, null, this.u, arrayList);
        newInstance.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                ActivityExtraccionSinTarjetaOperacionesRealizadas.this.u = str;
                if (str.equalsIgnoreCase(ActivityExtraccionSinTarjetaOperacionesRealizadas.this.getString(R.string.ID2047_EXTTARJETA_BTN_OPERDIA))) {
                    ActivityExtraccionSinTarjetaOperacionesRealizadas.this.s.trackScreen(ActivityExtraccionSinTarjetaOperacionesRealizadas.this.getString(R.string.analytics_enviodinero_consulta_operaciones_del_dia));
                    ActivityExtraccionSinTarjetaOperacionesRealizadas.this.s.trackEvent(ActivityExtraccionSinTarjetaOperacionesRealizadas.this.getString(R.string.analytics_category_enviodinero), ActivityExtraccionSinTarjetaOperacionesRealizadas.this.getString(R.string.analytics_action_filtrar_operaciones), ActivityExtraccionSinTarjetaOperacionesRealizadas.this.getString(R.string.analytics_label_operaciones_del_dia));
                    ActivityExtraccionSinTarjetaOperacionesRealizadas.this.showProgress(VConsultaMandatosExtEnv.nameService);
                    CExtEnv.consultaMandatosExt(ActivityExtraccionSinTarjetaOperacionesRealizadas.this.q, "DIA");
                } else if (str.contains(CExtEnv.getFiltroDias1(ActivityExtraccionSinTarjetaOperacionesRealizadas.this.r))) {
                    ActivityExtraccionSinTarjetaOperacionesRealizadas.this.s.trackScreen(ActivityExtraccionSinTarjetaOperacionesRealizadas.this.getString(R.string.analytics_extraccion_operaciones_ultimos_7_dias));
                    ActivityExtraccionSinTarjetaOperacionesRealizadas.this.s.trackEvent(ActivityExtraccionSinTarjetaOperacionesRealizadas.this.getString(R.string.analytics_category_extraccion_sin_tarjeta), ActivityExtraccionSinTarjetaOperacionesRealizadas.this.getString(R.string.analytics_action_filtrar_operaciones), ActivityExtraccionSinTarjetaOperacionesRealizadas.this.getString(R.string.analytics_label_operaciones_ultimos_7_dias));
                    ActivityExtraccionSinTarjetaOperacionesRealizadas.this.showProgress(VConsultaMandatosExtEnv.nameService);
                    CExtEnv.consultaMandatosExt(ActivityExtraccionSinTarjetaOperacionesRealizadas.this.q, CExtEnv.getFiltroDias1(ActivityExtraccionSinTarjetaOperacionesRealizadas.this.r));
                } else if (str.contains(CExtEnv.getFiltroDias2(ActivityExtraccionSinTarjetaOperacionesRealizadas.this.r))) {
                    ActivityExtraccionSinTarjetaOperacionesRealizadas.this.showProgress(VConsultaMandatosExtEnv.nameService);
                    CExtEnv.consultaMandatosExt(ActivityExtraccionSinTarjetaOperacionesRealizadas.this.q, CExtEnv.getFiltroDias2(ActivityExtraccionSinTarjetaOperacionesRealizadas.this.r));
                } else if (str.equalsIgnoreCase(ActivityExtraccionSinTarjetaOperacionesRealizadas.this.getString(R.string.ID2049_EXTTARJETA_BTN_OPERPEND))) {
                    ActivityExtraccionSinTarjetaOperacionesRealizadas.this.s.trackScreen(ActivityExtraccionSinTarjetaOperacionesRealizadas.this.getString(R.string.analytics_extraccion_operaciones_pendientes));
                    ActivityExtraccionSinTarjetaOperacionesRealizadas.this.s.trackEvent(ActivityExtraccionSinTarjetaOperacionesRealizadas.this.getString(R.string.analytics_category_extraccion_sin_tarjeta), ActivityExtraccionSinTarjetaOperacionesRealizadas.this.getString(R.string.analytics_action_filtrar_operaciones), ActivityExtraccionSinTarjetaOperacionesRealizadas.this.getString(R.string.analytics_label_operaciones_pendientes));
                    ActivityExtraccionSinTarjetaOperacionesRealizadas.this.showProgress(VConsultaMandatosExtEnv.nameService);
                    CExtEnv.consultaMandatosExt(ActivityExtraccionSinTarjetaOperacionesRealizadas.this.q, Estado.Pendiente);
                } else if (str.equalsIgnoreCase(ActivityExtraccionSinTarjetaOperacionesRealizadas.this.getString(R.string.ID2050_EXTTARJETA_BTN_BUSQAVANZ))) {
                    Intent intent = new Intent(ActivityExtraccionSinTarjetaOperacionesRealizadas.this, BusquedaAvanzadaActivity.class);
                    intent.putExtra(ActivityExtraccionSinTarjetaOperacionesRealizadas.this.getString(R.string.ED_INTENT_PUT_EXTRA_FILTROS_AVANZADOS), ActivityExtraccionSinTarjetaOperacionesRealizadas.this.v);
                    intent.putParcelableArrayListExtra(ActivityExtraccionSinTarjetaOperacionesRealizadas.this.getString(R.string.ED_INTENT_PUT_EXTRA_LISTA_DESTINATARIOS), ActivityExtraccionSinTarjetaOperacionesRealizadas.this.w);
                    intent.putExtra(ActivityExtraccionSinTarjetaOperacionesRealizadas.this.getString(R.string.INTENT_PUT_EXTRA_FUNCIONALIDAD), TipoOperacion.ExtraccionSinTarjeta);
                    ActivityExtraccionSinTarjetaOperacionesRealizadas.this.startActivityForResult(intent, 11);
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
                    intent2.putExtra(EnvioConstants.RECARGAR_PANTALLA_INICIAL_EXT, intent.getBooleanExtra(EnvioConstants.RECARGAR_PANTALLA_INICIAL_EXT, false));
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
        AnonymousClass4 r0 = new BaseWSResponseHandler(this, TypeOption.INTERMDIATE_VIEW, FragmentConstants.EXTRACCION_SIN_TARJETA, this) {
            public void onOk() {
                ActivityExtraccionSinTarjetaOperacionesRealizadas.this.a(consultaMandatosExtEnvEvent2);
            }

            public void onRes4Error() {
                ActivityExtraccionSinTarjetaOperacionesRealizadas.this.a(consultaMandatosExtEnvEvent2);
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
