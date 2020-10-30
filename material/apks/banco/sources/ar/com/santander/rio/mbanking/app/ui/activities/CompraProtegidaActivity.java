package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.commons.CConsDescripciones;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.module.seguros.ComprobanteContratacionCProtegidaPresenter;
import ar.com.santander.rio.mbanking.app.module.seguros.ComprobanteContratacionCProtegidaView;
import ar.com.santander.rio.mbanking.app.module.seguros.ConfirmarContratacionCProtegidaPresenter;
import ar.com.santander.rio.mbanking.app.module.seguros.ConfirmarContratacionCProtegidaView;
import ar.com.santander.rio.mbanking.app.module.seguros.ContratacionSeguroPresenter;
import ar.com.santander.rio.mbanking.app.module.seguros.ContratacionSeguroView;
import ar.com.santander.rio.mbanking.app.module.seguros.SolicitarSeguroPresenter;
import ar.com.santander.rio.mbanking.app.module.seguros.SolicitarSeguroView;
import ar.com.santander.rio.mbanking.app.ui.adapters.SegurosTarjAdapter;
import ar.com.santander.rio.mbanking.app.ui.constants.LoginConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.SegurosConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.SegurosConstants.idLeyenda;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.share.OptionsToShare;
import ar.com.santander.rio.mbanking.components.share.OptionsToShareImpl;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.model.general.Tarjeta;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarCompraProtegidaBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CotizacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaShortBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetOcupacionesBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LeyendaItem;
import ar.com.santander.rio.mbanking.services.soap.beans.body.OcupacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PlanSeguroBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TarjetaBean;
import ar.com.santander.rio.mbanking.utils.UtilAccount;
import ar.com.santander.rio.mbanking.utils.Utils;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import ar.com.santander.rio.mbanking.view.SlideAnimationViewFlipper;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;

public class CompraProtegidaActivity extends MvpPrivateMenuActivity implements OnClickListener, ComprobanteContratacionCProtegidaView, ConfirmarContratacionCProtegidaView, ContratacionSeguroView, SolicitarSeguroView {
    private CotizacionBean A;
    /* access modifiers changed from: private */
    public List<TarjetaBean> B = new ArrayList();
    /* access modifiers changed from: private */
    public TarjetaBean C = null;
    /* access modifiers changed from: private */
    public CuentaShortBean D = null;
    private OcupacionBean E = null;
    /* access modifiers changed from: private */
    public String F = "";
    @InjectView(2131363487)
    Button btn_confirmar_confirmacion;
    @InjectView(2131364060)
    Button btn_continuar_contratacion;
    @InjectView(2131363453)
    Button btn_continuar_solicitar_seguro;
    @InjectView(2131363505)
    Button btn_volver_comprobante;
    @InjectView(2131361802)
    ScrollView comprobanteContratacion;
    @InjectView(2131363488)
    ImageView img_confirmacion_checkbox;
    @InjectView(2131363502)
    TextView img_confirmacion_terminos;
    @InjectView(2131363507)
    TextView lbl_comprobante_cobertura;
    @InjectView(2131363508)
    TextView lbl_comprobante_fecha;
    @InjectView(2131363510)
    TextView lbl_comprobante_importe_mensual;
    @InjectView(2131363511)
    TextView lbl_comprobante_medio_pago;
    @InjectView(2131363512)
    TextView lbl_comprobante_ocupacion;
    @InjectView(2131363513)
    TextView lbl_comprobante_poliza;
    @InjectView(2131363514)
    TextView lbl_comprobante_suma_asegurada;
    @InjectView(2131363491)
    TextView lbl_confirmacion_cobertura;
    @InjectView(2131363492)
    TextView lbl_confirmacion_forma_pago;
    @InjectView(2131363493)
    TextView lbl_confirmacion_importe_mensual;
    @InjectView(2131363494)
    TextView lbl_confirmacion_medio_pago;
    @InjectView(2131363495)
    TextView lbl_confirmacion_ocupacion;
    @InjectView(2131363496)
    TextView lbl_confirmacion_suma_asegurada;
    @InjectView(2131363469)
    TextView lbl_contratcion_cobertura;
    @InjectView(2131363473)
    TextView lbl_contratcion_forma_pago;
    @InjectView(2131363472)
    TextView lbl_contratcion_importe_mensual;
    @InjectView(2131363481)
    TextView lbl_contratcion_leyenda;
    @InjectView(2131363474)
    TextView lbl_contratcion_medio_pago;
    @InjectView(2131363476)
    TextView lbl_contratcion_suma_asegurada;
    @InjectView(2131363479)
    TextView lbl_seleccionar_ocupacion;
    @InjectView(2131363456)
    TextView lbl_solicitar_seguro_detalle;
    @InjectView(2131363454)
    TextView lbl_solicitar_seguro_importe_mesual;
    @InjectView(2131363455)
    TextView lbl_solicitar_seguro_suma_asegurada;
    @InjectView(2131363450)
    ListView listViewTarjetas;
    @InjectView(2131365147)
    LinearLayout ll_solicitar_seguro;
    @InjectView(2131365148)
    LinearLayout ll_tarjetas_aseguradas_solicitar;
    @InjectView(2131364336)
    ViewFlipper mControlPager;
    @Inject
    AnalyticsManager p;
    Boolean q = Boolean.valueOf(false);
    private SolicitarSeguroPresenter r;
    @InjectView(2131365591)
    RelativeLayout row_Ocupacion;
    private ContratacionSeguroPresenter s;
    @InjectView(2131363457)
    ScrollView scrollSolicitar;
    private ConfirmarContratacionCProtegidaPresenter t;
    public ArrayList<TarjetaBean> tarjetas;
    @InjectView(2131363504)
    TableLayout tl_lista_tarjetas_comprobante;
    @InjectView(2131363486)
    TableLayout tl_lista_tarjetas_confirmar;
    @InjectView(2131363509)
    TextView txt_forma_Pago_comprobante;
    /* access modifiers changed from: private */
    public ComprobanteContratacionCProtegidaPresenter u;
    /* access modifiers changed from: private */
    public IsbanDialogFragment v;
    /* access modifiers changed from: private */
    public OptionsToShare w;
    /* access modifiers changed from: private */
    public boolean x = false;
    /* access modifiers changed from: private */
    public ContratarCompraProtegidaBodyResponseBean y;
    private PlanSeguroBean z;

    public void clearScreenData() {
    }

    public void configureActionBar() {
    }

    public void configureLayout() {
    }

    public Context getContext() {
        return this;
    }

    public int getMainLayout() {
        return R.layout.layout_compra_protegida_activity;
    }

    public void onPointerCaptureChanged(boolean z2) {
    }

    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        ButterKnife.inject((Activity) this);
        initialize();
    }

    public void initialize() {
        this.r = new SolicitarSeguroPresenter(this.mBus, this.mDataManager);
        this.s = new ContratacionSeguroPresenter(this.mBus, this.mDataManager);
        this.t = new ConfirmarContratacionCProtegidaPresenter(this.mBus, this.mDataManager, this);
        this.u = new ComprobanteContratacionCProtegidaPresenter(this.mBus, this.mDataManager, this);
        gotoSolicitarSeguro((CotizacionBean) getIntent().getParcelableExtra(SegurosConstants.INTENT_COTIZACION));
    }

    public void gotoPage(int i) {
        gotoPage(i, true);
    }

    public void gotoPage(int i, boolean z2) {
        if (this.mControlPager != null) {
            detachView();
            switch (i) {
                case 0:
                    if (!z2) {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
                        break;
                    }
                    break;
                case 1:
                    if (!z2) {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
                        break;
                    } else {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                        break;
                    }
                case 2:
                    if (!z2) {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
                        break;
                    } else {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                        break;
                    }
                case 3:
                    this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                    this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                    break;
            }
            this.mControlPager.setDisplayedChild(i);
            attachView();
            hideKeyboard();
            if (this.mControlPager.getDisplayedChild() == 3) {
                lockMenu(false);
            } else {
                lockMenu(true);
            }
        }
    }

    public void gotoSolicitarSeguro(CotizacionBean cotizacionBean) {
        gotoPage(0);
        this.r.onCreatePage(cotizacionBean);
    }

    public void setSolicitarSeguroView(CotizacionBean cotizacionBean) {
        boolean z2;
        this.A = cotizacionBean;
        setActionBarSolicitarSeguro();
        if (cotizacionBean == null) {
            this.p.trackScreen(getString(R.string.analytics_screen_Aviso_tarjetas_aseguradas));
            b();
            this.ll_solicitar_seguro.setVisibility(8);
            this.btn_continuar_solicitar_seguro.setVisibility(8);
            this.ll_tarjetas_aseguradas_solicitar.setVisibility(0);
            return;
        }
        this.p.trackScreen(getString(R.string.analytics_screen_Solicitar_seguro_compra_protegida));
        this.A = cotizacionBean;
        final ArrayList tarjetasAsegurables = this.sessionManager.getTarjetasAsegurables();
        int i = 0;
        for (int i2 = 0; i2 < tarjetasAsegurables.size(); i2++) {
            TarjetaBean tarjetaBean = (TarjetaBean) tarjetasAsegurables.get(i2);
            Iterator it = tarjetasAsegurables.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z2 = true;
                    break;
                }
                TarjetaBean tarjetaBean2 = (TarjetaBean) it.next();
                if (!tarjetaBean.equals(tarjetaBean2) && tarjetaBean2.getTipo().equalsIgnoreCase(tarjetaBean.getTipo()) && tarjetaBean2.isChecked().booleanValue()) {
                    z2 = false;
                    break;
                }
            }
            ((TarjetaBean) tarjetasAsegurables.get(i2)).setChecked(z2);
            if (z2) {
                i++;
            }
            if (i == 3) {
                break;
            }
        }
        c(i);
        AnonymousClass1 r1 = new SegurosTarjAdapter(this, R.layout.item_seguros_tarjetas, tarjetasAsegurables) {
            public void checkboxCheck(int i, boolean z) {
                if (i != 3 || !z) {
                    CompraProtegidaActivity.this.c(i);
                    return;
                }
                CompraProtegidaActivity.this.p.trackEvent(CompraProtegidaActivity.this.getString(R.string.analytics_trackevent_category_seguros), CompraProtegidaActivity.this.getString(R.string.analytics_trackevent_action_aceptar), CompraProtegidaActivity.this.getString(R.string.analytics_trackevent_label__limite_tarjetas_asegurar));
                CompraProtegidaActivity.this.showMaximoTarjetasDialog();
            }

            public void checkTipoDuplicadoTarjetas(String str) {
                CompraProtegidaActivity.this.showTipoDuplicadoTarjetaDialog(str);
            }
        };
        this.listViewTarjetas.setAdapter(r1);
        this.listViewTarjetas.setScrollContainer(false);
        Utils.setListViewHeight(this.listViewTarjetas, r1.getCount(), 80, this);
        setActionBarSolicitarSeguro();
        this.btn_continuar_solicitar_seguro.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CompraProtegidaActivity.this.B.clear();
                for (TarjetaBean tarjetaBean : tarjetasAsegurables) {
                    if (tarjetaBean.isChecked().booleanValue()) {
                        CompraProtegidaActivity.this.B.add(tarjetaBean);
                    }
                }
                CompraProtegidaActivity.this.gotoContratacionSeguro(null);
            }
        });
        this.scrollSolicitar.smoothScrollTo(0, 0);
        this.lbl_solicitar_seguro_detalle.setOnClickListener(this);
        l();
    }

    /* access modifiers changed from: private */
    public void c(int i) {
        this.z = null;
        if (i == 0) {
            this.lbl_solicitar_seguro_importe_mesual.setText(getString(R.string.NULL_AMOUNT));
            this.lbl_solicitar_seguro_importe_mesual.setText(getString(R.string.NULL_AMOUNT));
            try {
                this.lbl_solicitar_seguro_importe_mesual.setContentDescription(new CAccessibility(getContext()).applyFilterAmount(getString(R.string.NULL_AMOUNT)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.lbl_solicitar_seguro_suma_asegurada.setText(getString(R.string.NULL_AMOUNT));
            try {
                this.lbl_solicitar_seguro_suma_asegurada.setContentDescription(new CAccessibility(getContext()).applyFilterAmount(getString(R.string.NULL_AMOUNT)));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else {
            Iterator it = this.A.getPlanes().getListaPlanes().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                PlanSeguroBean planSeguroBean = (PlanSeguroBean) it.next();
                if (Integer.valueOf(planSeguroBean.getCantTarjetas()).intValue() == i) {
                    this.z = planSeguroBean;
                    break;
                }
            }
            if (this.z != null) {
                this.lbl_solicitar_seguro_importe_mesual.setText(this.z.getCuota());
                try {
                    this.lbl_solicitar_seguro_importe_mesual.setContentDescription(new CAccessibility(getContext()).applyFilterAmount(this.z.getCuota()));
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
                this.lbl_solicitar_seguro_suma_asegurada.setText(this.z.getSumaAsegurada());
                try {
                    this.lbl_solicitar_seguro_suma_asegurada.setContentDescription(new CAccessibility(getContext()).applyFilterAmount(this.z.getSumaAsegurada()));
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
            } else {
                this.lbl_solicitar_seguro_importe_mesual.setText(getString(R.string.NULL_AMOUNT));
                try {
                    this.lbl_solicitar_seguro_importe_mesual.setContentDescription(new CAccessibility(getContext()).applyFilterAmount(getString(R.string.NULL_AMOUNT)));
                } catch (Exception e5) {
                    e5.printStackTrace();
                }
                this.lbl_solicitar_seguro_suma_asegurada.setText(getString(R.string.NULL_AMOUNT));
                try {
                    this.lbl_solicitar_seguro_suma_asegurada.setContentDescription(new CAccessibility(getContext()).applyFilterAmount(getString(R.string.NULL_AMOUNT)));
                } catch (Exception e6) {
                    e6.printStackTrace();
                }
            }
        }
        actualizarBotonContinuarSeguro();
    }

    public void actualizarBotonContinuarSeguro() {
        if (this.z == null) {
            this.btn_continuar_solicitar_seguro.setEnabled(false);
            this.btn_continuar_solicitar_seguro.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_gris_claro));
            return;
        }
        this.btn_continuar_solicitar_seguro.setEnabled(true);
        this.btn_continuar_solicitar_seguro.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_rojo));
    }

    public void showMaximoTarjetasDialog() {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.F27_11_AVISO), getString(R.string.F27_11_TXT_TARJETAS), null, null, getString(R.string.F27_11_ACEPTAR), null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }
        });
        newInstance.show(getSupportFragmentManager(), "isbanDialogConfirm");
    }

    public void showTipoDuplicadoTarjetaDialog(String str) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.F27_11_AVISO), getString(R.string.F27_11_TXT_DIALOG_TIPO_TARJETA, new Object[]{str}), null, null, getString(R.string.F27_11_ACEPTAR), null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }
        });
        newInstance.show(getSupportFragmentManager(), "isbanDialogConfirm");
    }

    public void setActionBarSolicitarSeguro() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        b();
    }

    private void b() {
        View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.back_imgButton);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    CompraProtegidaActivity.this.onBackPressed();
                }
            });
        }
    }

    public void backButtonSolicitarSeguro() {
        setResult(0);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void gotoContratacionSeguro(PlanSeguroBean planSeguroBean) {
        gotoPage(1);
        this.s.onCreatePage(planSeguroBean);
    }

    public void setActionBarContratacionSeguro() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        c();
    }

    private void c() {
        View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.back_imgButton);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    CompraProtegidaActivity.this.onBackPressed();
                }
            });
        }
    }

    public void backButtonContratacionSeguro() {
        gotoPage(0, false);
    }

    public void setContratacionSeguroView(PlanSeguroBean planSeguroBean) {
        this.p.trackScreen(getString(R.string.analytics_screen_Contratar_seguro_compra_protegida));
        setActionBarContratacionSeguro();
        this.lbl_contratcion_importe_mensual.setText(this.z.getCuota());
        try {
            this.lbl_contratcion_importe_mensual.setContentDescription(new CAccessibility(getContext()).applyFilterAmount(this.z.getCuota()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.z.getCantTarjetas().equalsIgnoreCase("1")) {
            TextView textView = this.lbl_contratcion_cobertura;
            StringBuilder sb = new StringBuilder();
            sb.append(this.z.getCantTarjetas());
            sb.append(UtilsCuentas.SEPARAOR2);
            sb.append(getString(R.string.ID_4071_SEGUROS_LBL_TARJETA));
            textView.setText(sb.toString());
        } else {
            TextView textView2 = this.lbl_contratcion_cobertura;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.z.getCantTarjetas());
            sb2.append(UtilsCuentas.SEPARAOR2);
            sb2.append(getString(R.string.ID_4071_SEGUROS_LBL_TARJETAS));
            textView2.setText(sb2.toString());
        }
        this.lbl_contratcion_suma_asegurada.setText(this.z.getSumaAsegurada());
        try {
            this.lbl_contratcion_suma_asegurada.setContentDescription(new CAccessibility(getContext()).applyFilterAmount(this.z.getSumaAsegurada()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.lbl_contratcion_medio_pago.setOnClickListener(this);
        this.btn_continuar_contratacion.setOnClickListener(this);
        this.row_Ocupacion.setOnClickListener(this);
        if (this.E == null) {
            this.lbl_seleccionar_ocupacion.setText(getContext().getString(R.string.F24_20_lbl_data_seleccionar));
        } else {
            this.lbl_seleccionar_ocupacion.setText(Html.fromHtml(this.E.getDescOcupacion()));
        }
        this.D = null;
        this.C = null;
        this.lbl_contratcion_medio_pago.setText(getContext().getString(R.string.F27_12_lbl_txt_seleccionar));
        this.F = this.lbl_contratcion_medio_pago.getText().toString();
        l();
        this.lbl_contratcion_leyenda.setText(Html.fromHtml(getString(R.string.ID_4077_SEGUROS_LBL_LEYENDA_COMPRA_PROTEGIDA)));
        try {
            this.lbl_contratcion_leyenda.setContentDescription(new CAccessibility(getContext()).applyFilterGeneral(getString(R.string.ID_4077_SEGUROS_LBL_LEYENDA)));
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    public void showFormasPago() {
        final ArrayList<Tarjeta> arrayList = new ArrayList<>();
        final ArrayList<Cuenta> arrayList2 = new ArrayList<>();
        final ArrayList arrayList3 = new ArrayList();
        final ArrayList arrayList4 = new ArrayList();
        if (!(this.sessionManager.getLoginUnico().getProductos().getCuentas() == null || this.sessionManager.getLoginUnico().getProductos().getCuentas().getCuentas() == null || this.sessionManager.getLoginUnico().getProductos().getCuentas().getCuentas().isEmpty())) {
            for (Cuenta cuenta : this.sessionManager.getLoginUnico().getProductos().getCuentas().getCuentas()) {
                if (!cuenta.getTipo().equals(LoginConstants.TIPO_CUENTA_CJA_AHO_DOLAR) && !cuenta.getTipo().equals(LoginConstants.TIPO_CUENTA_CTA_CTE_DOLAR) && !cuenta.getTipo().equals(LoginConstants.TIPO_CUENTA_CU_DOLAR)) {
                    cuenta.setTipo(cuenta.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CU) ? LoginConstants.TIPO_CUENTA_CU_PESOS : cuenta.getTipo());
                    arrayList2.add(cuenta);
                }
            }
        }
        if (!(this.sessionManager.getLoginUnico().getProductos().getTarjetas() == null || this.sessionManager.getLoginUnico().getProductos().getTarjetas().getTarjetas() == null || this.sessionManager.getLoginUnico().getProductos().getTarjetas().getTarjetas().isEmpty())) {
            for (Tarjeta tarjeta : this.sessionManager.getLoginUnico().getProductos().getTarjetas().getTarjetas()) {
                if (!tarjeta.getClase().equalsIgnoreCase("T") && !tarjeta.getTipo().equalsIgnoreCase(TarjetasConstants.CODIGO_TARJETA_MASTERCARD)) {
                    arrayList.add(tarjeta);
                }
            }
        }
        for (Tarjeta tarjeta2 : arrayList) {
            arrayList3.add(Utils.mascaraTarjeta(tarjeta2));
            try {
                arrayList4.add(new CAccessibility(getContext()).applyFilterCreditCard(Utils.mascaraTarjeta(tarjeta2)));
            } catch (Exception e) {
                arrayList4.add("");
                e.printStackTrace();
            }
        }
        for (Cuenta cuenta2 : arrayList2) {
            String abreviatureAndAccountFormat = UtilAccount.getAbreviatureAndAccountFormat(CConsDescripciones.getConsDescripcionTPOCTACORTA(this.sessionManager), cuenta2.getTipo(), cuenta2.getNroSuc(), cuenta2.getNumero());
            arrayList3.add(abreviatureAndAccountFormat);
            try {
                arrayList4.add(new CAccessibility(getContext()).applyFilterAccount(abreviatureAndAccountFormat));
            } catch (Exception e2) {
                arrayList4.add("");
                e2.printStackTrace();
            }
        }
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("popUpMedioPago", null, null, arrayList3, getString(R.string.ID2060_ENVEFECT_BTN_CANCELAR), null, null, this.F, arrayList4);
        final IsbanDialogFragment isbanDialogFragment = newInstance;
        AnonymousClass7 r0 = new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onItemSelected(String str) {
                CompraProtegidaActivity.this.lbl_contratcion_medio_pago.setText(str);
                try {
                    CompraProtegidaActivity.this.lbl_contratcion_medio_pago.setContentDescription(new CAccessibility(CompraProtegidaActivity.this.getContext()).applyFilterGeneral((String) arrayList4.get(arrayList3.indexOf(str))));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                CompraProtegidaActivity.this.C = null;
                CompraProtegidaActivity.this.D = null;
                boolean z = false;
                Iterator it = arrayList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Tarjeta tarjeta = (Tarjeta) it.next();
                    if (str.equalsIgnoreCase(Utils.mascaraTarjeta(tarjeta))) {
                        z = true;
                        CompraProtegidaActivity.this.C = new TarjetaBean(tarjeta.getTipo(), tarjeta.getTipo().equalsIgnoreCase("05") ? tarjeta.getNroTarjetaDebito() : tarjeta.getNroTarjetaCredito(), null);
                    }
                }
                if (!z) {
                    Iterator it2 = arrayList2.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            break;
                        }
                        Cuenta cuenta = (Cuenta) it2.next();
                        if (str.equalsIgnoreCase(UtilAccount.getAbreviatureAndAccountFormat(CConsDescripciones.getConsDescripcionTPOCTACORTA(CompraProtegidaActivity.this.sessionManager), cuenta.getTipo(), cuenta.getNroSuc(), cuenta.getNumero()))) {
                            CompraProtegidaActivity.this.D = new CuentaShortBean(cuenta.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CU) ? LoginConstants.TIPO_CUENTA_CU_PESOS : cuenta.getTipo(), cuenta.getNroSuc(), cuenta.getNumero());
                        }
                    }
                }
                CompraProtegidaActivity.this.F = str;
                CompraProtegidaActivity.this.l();
            }

            public void onSimpleActionButton() {
                isbanDialogFragment.dismiss();
            }
        };
        newInstance.setDialogListener(r0);
        visualizarSeleccionar();
        newInstance.show(getSupportFragmentManager(), "popUpMedioPago");
    }

    public void visualizarSeleccionar() {
        this.lbl_contratcion_medio_pago.getText().toString().equalsIgnoreCase(getString(R.string.F27_12_lbl_txt_seleccionar));
    }

    public void gotoConfirmarContratacion() {
        gotoPage(2);
        this.t.onCreatePage();
    }

    public void showSeleccionarOcupacion(GetOcupacionesBodyResponseBean getOcupacionesBodyResponseBean, String str) {
        this.sessionManager.setListaOcupaciones(getOcupacionesBodyResponseBean);
        Intent intent = new Intent(this, SeleccionarOcupacionAltaSeguroActivity.class);
        intent.putExtra(SegurosConstants.INTENT_EXTRA_OCUPACIONES, (ArrayList) getOcupacionesBodyResponseBean.getOcupaciones().getListOcupaciones());
        intent.putExtra(SegurosConstants.INTENT_EXTRA_OCUPACION, this.E);
        startActivityForResult(intent, 3);
    }

    public void setActionBarConfirmarContratacion() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        d();
    }

    private void d() {
        View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.back_imgButton);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    CompraProtegidaActivity.this.onBackPressed();
                }
            });
        }
    }

    public void backButtonConfirmarContratacion() {
        gotoPage(1, false);
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x0186  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x0275  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setConfirmarContratacionView() {
        /*
            r38 = this;
            r1 = r38
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r2 = r1.p
            r3 = 2131757704(0x7f100a88, float:1.9146351E38)
            java.lang.String r3 = r1.getString(r3)
            r2.trackScreen(r3)
            r38.setActionBarConfirmarContratacion()
            android.widget.TextView r2 = r1.lbl_confirmacion_importe_mensual
            ar.com.santander.rio.mbanking.services.soap.beans.body.PlanSeguroBean r3 = r1.z
            java.lang.String r3 = r3.getCuota()
            r2.setText(r3)
            android.widget.TextView r2 = r1.lbl_confirmacion_importe_mensual     // Catch:{ Exception -> 0x0035 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r3 = new ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility     // Catch:{ Exception -> 0x0035 }
            android.content.Context r4 = r38.getContext()     // Catch:{ Exception -> 0x0035 }
            r3.<init>(r4)     // Catch:{ Exception -> 0x0035 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.PlanSeguroBean r4 = r1.z     // Catch:{ Exception -> 0x0035 }
            java.lang.String r4 = r4.getCuota()     // Catch:{ Exception -> 0x0035 }
            java.lang.String r3 = r3.applyFilterAmount(r4)     // Catch:{ Exception -> 0x0035 }
            r2.setContentDescription(r3)     // Catch:{ Exception -> 0x0035 }
            goto L_0x003a
        L_0x0035:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()
        L_0x003a:
            ar.com.santander.rio.mbanking.services.soap.beans.body.PlanSeguroBean r2 = r1.z
            java.lang.String r2 = r2.getCantTarjetas()
            java.lang.String r3 = "1"
            boolean r2 = r2.equalsIgnoreCase(r3)
            if (r2 == 0) goto L_0x006f
            android.widget.TextView r2 = r1.lbl_confirmacion_cobertura
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            ar.com.santander.rio.mbanking.services.soap.beans.body.PlanSeguroBean r4 = r1.z
            java.lang.String r4 = r4.getCantTarjetas()
            r3.append(r4)
            java.lang.String r4 = " "
            r3.append(r4)
            r4 = 2131756618(0x7f10064a, float:1.9144149E38)
            java.lang.String r4 = r1.getString(r4)
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r2.setText(r3)
            goto L_0x0095
        L_0x006f:
            android.widget.TextView r2 = r1.lbl_confirmacion_cobertura
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            ar.com.santander.rio.mbanking.services.soap.beans.body.PlanSeguroBean r4 = r1.z
            java.lang.String r4 = r4.getCantTarjetas()
            r3.append(r4)
            java.lang.String r4 = " "
            r3.append(r4)
            r4 = 2131756619(0x7f10064b, float:1.914415E38)
            java.lang.String r4 = r1.getString(r4)
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r2.setText(r3)
        L_0x0095:
            android.widget.TextView r2 = r1.lbl_confirmacion_suma_asegurada
            ar.com.santander.rio.mbanking.services.soap.beans.body.PlanSeguroBean r3 = r1.z
            java.lang.String r3 = r3.getSumaAsegurada()
            r2.setText(r3)
            android.widget.TextView r2 = r1.lbl_confirmacion_suma_asegurada     // Catch:{ Exception -> 0x00b9 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r3 = new ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility     // Catch:{ Exception -> 0x00b9 }
            android.content.Context r4 = r38.getContext()     // Catch:{ Exception -> 0x00b9 }
            r3.<init>(r4)     // Catch:{ Exception -> 0x00b9 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.PlanSeguroBean r4 = r1.z     // Catch:{ Exception -> 0x00b9 }
            java.lang.String r4 = r4.getSumaAsegurada()     // Catch:{ Exception -> 0x00b9 }
            java.lang.String r3 = r3.applyFilterAmount(r4)     // Catch:{ Exception -> 0x00b9 }
            r2.setContentDescription(r3)     // Catch:{ Exception -> 0x00b9 }
            goto L_0x00be
        L_0x00b9:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()
        L_0x00be:
            android.widget.TextView r2 = r1.lbl_confirmacion_medio_pago
            android.widget.TextView r3 = r1.lbl_contratcion_medio_pago
            java.lang.CharSequence r3 = r3.getText()
            java.lang.String r3 = r3.toString()
            r2.setText(r3)
            android.widget.TextView r2 = r1.lbl_contratcion_medio_pago     // Catch:{ Exception -> 0x012c }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x012c }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x012c }
            java.lang.String r3 = "VISA"
            boolean r2 = r2.contains(r3)     // Catch:{ Exception -> 0x012c }
            if (r2 != 0) goto L_0x010f
            android.widget.TextView r2 = r1.lbl_contratcion_medio_pago     // Catch:{ Exception -> 0x012c }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x012c }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x012c }
            java.lang.String r3 = "AMEX"
            boolean r2 = r2.contains(r3)     // Catch:{ Exception -> 0x012c }
            if (r2 == 0) goto L_0x00f2
            goto L_0x010f
        L_0x00f2:
            android.widget.TextView r2 = r1.lbl_confirmacion_medio_pago     // Catch:{ Exception -> 0x012c }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r3 = new ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility     // Catch:{ Exception -> 0x012c }
            android.content.Context r4 = r38.getContext()     // Catch:{ Exception -> 0x012c }
            r3.<init>(r4)     // Catch:{ Exception -> 0x012c }
            android.widget.TextView r4 = r1.lbl_contratcion_medio_pago     // Catch:{ Exception -> 0x012c }
            java.lang.CharSequence r4 = r4.getText()     // Catch:{ Exception -> 0x012c }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x012c }
            java.lang.String r3 = r3.applyFilterGeneral(r4)     // Catch:{ Exception -> 0x012c }
            r2.setContentDescription(r3)     // Catch:{ Exception -> 0x012c }
            goto L_0x0131
        L_0x010f:
            android.widget.TextView r2 = r1.lbl_confirmacion_medio_pago     // Catch:{ Exception -> 0x012c }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r3 = new ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility     // Catch:{ Exception -> 0x012c }
            android.content.Context r4 = r38.getContext()     // Catch:{ Exception -> 0x012c }
            r3.<init>(r4)     // Catch:{ Exception -> 0x012c }
            android.widget.TextView r4 = r1.lbl_contratcion_medio_pago     // Catch:{ Exception -> 0x012c }
            java.lang.CharSequence r4 = r4.getText()     // Catch:{ Exception -> 0x012c }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x012c }
            java.lang.String r3 = r3.applyFilterCreditCard(r4)     // Catch:{ Exception -> 0x012c }
            r2.setContentDescription(r3)     // Catch:{ Exception -> 0x012c }
            goto L_0x0131
        L_0x012c:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()
        L_0x0131:
            android.widget.TextView r2 = r1.lbl_confirmacion_ocupacion
            android.widget.TextView r3 = r1.lbl_seleccionar_ocupacion
            java.lang.CharSequence r3 = r3.getText()
            java.lang.String r3 = r3.toString()
            android.text.Spanned r3 = android.text.Html.fromHtml(r3)
            r2.setText(r3)
            android.widget.TextView r2 = r1.lbl_confirmacion_ocupacion     // Catch:{ Exception -> 0x0161 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r3 = new ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility     // Catch:{ Exception -> 0x0161 }
            android.content.Context r4 = r38.getContext()     // Catch:{ Exception -> 0x0161 }
            r3.<init>(r4)     // Catch:{ Exception -> 0x0161 }
            android.widget.TextView r4 = r1.lbl_seleccionar_ocupacion     // Catch:{ Exception -> 0x0161 }
            java.lang.CharSequence r4 = r4.getText()     // Catch:{ Exception -> 0x0161 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0161 }
            java.lang.String r3 = r3.applyFilterGeneral(r4)     // Catch:{ Exception -> 0x0161 }
            r2.setContentDescription(r3)     // Catch:{ Exception -> 0x0161 }
            goto L_0x0166
        L_0x0161:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()
        L_0x0166:
            android.widget.ImageView r2 = r1.img_confirmacion_checkbox
            r2.setOnClickListener(r1)
            android.widget.ImageView r2 = r1.img_confirmacion_checkbox
            r3 = 0
            r2.setVisibility(r3)
            r38.h()
            android.widget.TextView r2 = r1.img_confirmacion_terminos
            r2.setOnClickListener(r1)
            android.widget.TableLayout r2 = r1.tl_lista_tarjetas_confirmar
            r2.removeAllViews()
            java.util.List<ar.com.santander.rio.mbanking.services.soap.beans.body.TarjetaBean> r2 = r1.B
            boolean r2 = r2.isEmpty()
            if (r2 != 0) goto L_0x0275
            java.lang.String r2 = "layout_inflater"
            java.lang.Object r2 = r1.getSystemService(r2)
            android.view.LayoutInflater r2 = (android.view.LayoutInflater) r2
            java.util.List<ar.com.santander.rio.mbanking.services.soap.beans.body.TarjetaBean> r5 = r1.B
            java.util.Iterator r5 = r5.iterator()
            r6 = 0
        L_0x0195:
            boolean r7 = r5.hasNext()
            if (r7 == 0) goto L_0x027c
            java.lang.Object r7 = r5.next()
            ar.com.santander.rio.mbanking.services.soap.beans.body.TarjetaBean r7 = (ar.com.santander.rio.mbanking.services.soap.beans.body.TarjetaBean) r7
            r8 = 2131493363(0x7f0c01f3, float:1.8610204E38)
            r9 = 0
            android.view.View r8 = r2.inflate(r8, r9)
            android.widget.LinearLayout r8 = (android.widget.LinearLayout) r8
            r9 = 2131363484(0x7f0a069c, float:1.8346778E38)
            android.view.View r9 = r8.findViewById(r9)
            android.widget.TextView r9 = (android.widget.TextView) r9
            r10 = 2131363485(0x7f0a069d, float:1.834678E38)
            android.view.View r10 = r8.findViewById(r10)
            android.widget.TextView r10 = (android.widget.TextView) r10
            ar.com.santander.rio.mbanking.services.model.general.Tarjeta r15 = new ar.com.santander.rio.mbanking.services.model.general.Tarjeta
            java.lang.String r12 = r7.getNumTarjeta()
            r13 = 0
            r14 = 0
            r16 = 0
            java.lang.String r17 = r7.getTipo()
            r18 = 0
            java.lang.String r19 = ""
            r20 = 0
            r21 = 0
            r22 = 0
            r23 = 0
            r24 = 0
            r11 = r15
            r4 = r15
            r15 = r16
            r16 = r17
            r17 = r18
            r18 = r19
            r19 = r20
            r20 = r21
            r21 = r22
            r22 = r23
            r23 = r24
            r11.<init>(r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23)
            java.lang.String r4 = ar.com.santander.rio.mbanking.utils.Utils.mascaraTarjeta(r4)
            java.lang.String r11 = " "
            r12 = 2
            java.lang.String[] r4 = r4.split(r11, r12)
            int r11 = r4.length
            r12 = 1
            if (r11 == r12) goto L_0x0195
            r11 = r4[r3]
            java.lang.String r13 = ""
            boolean r11 = r11.equals(r13)
            if (r11 != 0) goto L_0x0195
            r11 = r4[r3]
            r9.setText(r11)
            r4 = r4[r12]
            r10.setText(r4)
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r4 = new ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility     // Catch:{ Exception -> 0x024b }
            android.content.Context r9 = r38.getContext()     // Catch:{ Exception -> 0x024b }
            r4.<init>(r9)     // Catch:{ Exception -> 0x024b }
            ar.com.santander.rio.mbanking.services.model.general.Tarjeta r9 = new ar.com.santander.rio.mbanking.services.model.general.Tarjeta     // Catch:{ Exception -> 0x024b }
            java.lang.String r26 = r7.getNumTarjeta()     // Catch:{ Exception -> 0x024b }
            r27 = 0
            r28 = 0
            r29 = 0
            java.lang.String r30 = r7.getTipo()     // Catch:{ Exception -> 0x024b }
            r31 = 0
            java.lang.String r32 = ""
            r33 = 0
            r34 = 0
            r35 = 0
            r36 = 0
            r37 = 0
            r25 = r9
            r25.<init>(r26, r27, r28, r29, r30, r31, r32, r33, r34, r35, r36, r37)     // Catch:{ Exception -> 0x024b }
            java.lang.String r7 = ar.com.santander.rio.mbanking.utils.Utils.mascaraTarjeta(r9)     // Catch:{ Exception -> 0x024b }
            java.lang.String r4 = r4.applyFilterCreditCard(r7)     // Catch:{ Exception -> 0x024b }
            r10.setContentDescription(r4)     // Catch:{ Exception -> 0x024b }
            goto L_0x0250
        L_0x024b:
            r0 = move-exception
            r4 = r0
            r4.printStackTrace()
        L_0x0250:
            java.lang.Integer r4 = java.lang.Integer.valueOf(r6)
            r8.setTag(r4)
            java.util.List<ar.com.santander.rio.mbanking.services.soap.beans.body.TarjetaBean> r4 = r1.B
            int r4 = r4.size()
            int r4 = r4 - r12
            if (r6 != r4) goto L_0x026c
            r4 = 2131363483(0x7f0a069b, float:1.8346776E38)
            android.view.View r4 = r8.findViewById(r4)
            r7 = 8
            r4.setVisibility(r7)
        L_0x026c:
            android.widget.TableLayout r4 = r1.tl_lista_tarjetas_confirmar
            r4.addView(r8)
            int r6 = r6 + 1
            goto L_0x0195
        L_0x0275:
            android.widget.TableLayout r2 = r1.tl_lista_tarjetas_confirmar
            r3 = 8
            r2.setVisibility(r3)
        L_0x027c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.activities.CompraProtegidaActivity.setConfirmarContratacionView():void");
    }

    private boolean e() {
        return this.q.booleanValue();
    }

    private void f() {
        if (!e()) {
            g();
        } else {
            h();
        }
    }

    private void g() {
        this.img_confirmacion_checkbox.setBackground(getResources().getDrawable(R.drawable.ic_checkbox_on_rojo));
        this.btn_confirmar_confirmacion.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_rojo));
        this.q = Boolean.valueOf(true);
        this.btn_confirmar_confirmacion.setOnClickListener(this);
        this.btn_confirmar_confirmacion.setEnabled(true);
    }

    private void h() {
        this.img_confirmacion_checkbox.setBackground(getResources().getDrawable(R.drawable.ic_checkbox_off_gris));
        this.btn_confirmar_confirmacion.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_gris_claro));
        this.q = Boolean.valueOf(false);
        this.btn_confirmar_confirmacion.setEnabled(false);
    }

    public void gotoComprobanteContratacion(ContratarCompraProtegidaBodyResponseBean contratarCompraProtegidaBodyResponseBean) {
        gotoPage(3);
        this.u.onCreatePage(contratarCompraProtegidaBodyResponseBean);
    }

    public void setActionBarComprobanteContratacion() {
        setActionBarType(ActionBarType.MAIN_WITH_MENU);
        this.mActionBar = getSupportActionBar().getCustomView();
        lockMenu(false);
        i();
    }

    private void i() {
        this.mActionBar = getSupportActionBar().getCustomView();
        ImageView imageView = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.toggle);
        ImageView imageView2 = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.menu);
        if (imageView != null) {
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    CompraProtegidaActivity.this.canExit();
                }
            });
        }
        j();
        if (imageView2 != null) {
            imageView2.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    CompraProtegidaActivity.this.p.trackEvent(CompraProtegidaActivity.this.getString(R.string.analytics_trackevent_category_seguros), CompraProtegidaActivity.this.getString(R.string.analytics_trackevent_action_contratacion_compra_protegida), CompraProtegidaActivity.this.getString(R.string.analytics_trackevent_label_ver_poliza_compartir_comprobante_descargar_comprobante_cancelar));
                    CompraProtegidaActivity.this.v.show(CompraProtegidaActivity.this.getSupportFragmentManager(), "Dialog");
                }
            });
        }
    }

    private void j() {
        this.w = k();
        ArrayList arrayList = new ArrayList();
        arrayList.add(getResources().getString(R.string.ID_4106_SEGUROS_LBL_VER_POLIZA));
        arrayList.add(getResources().getString(R.string.ID_4107_SEGUROS_LBL_COMP_COMPRO));
        arrayList.add(getResources().getString(R.string.ID_4108_SEGUROS_LBL_DESC_COMPRO));
        this.v = IsbanDialogFragment.newInstance("mPopupMenu", getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_TITLE), null, arrayList, getString(R.string.ID_4109_SEGUROS_LBL_CANCELAR), null, null, null, arrayList);
        this.v.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                if (str.equalsIgnoreCase(CompraProtegidaActivity.this.getResources().getString(R.string.ID_4107_SEGUROS_LBL_COMP_COMPRO))) {
                    if (ContextCompat.checkSelfPermission(CompraProtegidaActivity.this.getContext(), "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
                        CompraProtegidaActivity.this.w.optionShareSelected();
                    } else if (VERSION.SDK_INT >= 23) {
                        CompraProtegidaActivity.this.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
                    }
                } else if (str.equalsIgnoreCase(CompraProtegidaActivity.this.getResources().getString(R.string.ID_4108_SEGUROS_LBL_DESC_COMPRO))) {
                    if (ContextCompat.checkSelfPermission(CompraProtegidaActivity.this.getContext(), "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
                        CompraProtegidaActivity.this.w.optionDownloadSelected();
                    } else if (VERSION.SDK_INT >= 23) {
                        CompraProtegidaActivity.this.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
                    }
                } else if (!str.equalsIgnoreCase(CompraProtegidaActivity.this.getResources().getString(R.string.ID_4106_SEGUROS_LBL_VER_POLIZA))) {
                } else {
                    if (ContextCompat.checkSelfPermission(CompraProtegidaActivity.this.getContext(), "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
                        CompraProtegidaActivity.this.u.getPoliza(CompraProtegidaActivity.this.y.getCodRamo(), CompraProtegidaActivity.this.y.getNumPoliza(), CompraProtegidaActivity.this.y.getNumCertificado());
                    } else if (VERSION.SDK_INT >= 23) {
                        CompraProtegidaActivity.this.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
                    }
                }
            }
        });
        this.v.setCancelable(true);
    }

    private OptionsToShare k() {
        return new OptionsToShareImpl(this, getApplicationContext(), getSupportFragmentManager()) {
            public View getViewToShare() {
                return CompraProtegidaActivity.this.comprobanteContratacion;
            }

            public void receiveIntentAppShare(Intent intent) {
                CompraProtegidaActivity.this.startActivity(Intent.createChooser(intent, CompraProtegidaActivity.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)));
            }

            public String getFileName() {
                String string = CompraProtegidaActivity.this.getString(R.string.F27_10_COMPROBANTE_CONTRATACION_SEGURO_COMPRA_PROTEGIDA_FILENAME);
                StringBuilder sb = new StringBuilder();
                sb.append(" - ");
                sb.append(CompraProtegidaActivity.this.y.getNumCertificado());
                return Html.fromHtml(string.concat(sb.toString())).toString();
            }

            public String getSubjectReceiptToShare() {
                return CompraProtegidaActivity.this.getString(R.string.F27_10_COMPROBANTE_CONTRATACION_SEGURO_COMPRA_PROTEGIDA_SUBJECT);
            }

            public void optionDownloadSelected() {
                super.optionDownloadSelected();
                CompraProtegidaActivity.this.x = true;
            }

            public void optionShareSelected() {
                super.optionShareSelected();
                CompraProtegidaActivity.this.x = true;
            }

            public void onAbortShare() {
                super.onAbortShare();
                CompraProtegidaActivity.this.x = true;
                CompraProtegidaActivity.this.onBackPressed();
            }
        };
    }

    public boolean canExit() {
        if (this.x) {
            switchDrawer();
        } else if (ContextCompat.checkSelfPermission(getContext(), "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
            new OptionsToShareImpl(this, getApplicationContext(), getSupportFragmentManager()) {
                public View getViewToShare() {
                    return CompraProtegidaActivity.this.comprobanteContratacion;
                }

                public void receiveIntentAppShare(Intent intent) {
                    CompraProtegidaActivity.this.startActivity(Intent.createChooser(intent, CompraProtegidaActivity.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)));
                }

                public String getFileName() {
                    String string = CompraProtegidaActivity.this.getString(R.string.F27_10_COMPROBANTE_CONTRATACION_SEGURO_COMPRA_PROTEGIDA_FILENAME);
                    StringBuilder sb = new StringBuilder();
                    sb.append(" - ");
                    sb.append(CompraProtegidaActivity.this.y.getNumCertificado());
                    return Html.fromHtml(string.concat(sb.toString())).toString();
                }

                public String getSubjectReceiptToShare() {
                    return CompraProtegidaActivity.this.getString(R.string.F27_10_COMPROBANTE_CONTRATACION_SEGURO_COMPRA_PROTEGIDA_SUBJECT);
                }

                public void optionDownloadSelected() {
                    super.optionDownloadSelected();
                    CompraProtegidaActivity.this.x = true;
                }

                public void optionShareSelected() {
                    super.optionShareSelected();
                    CompraProtegidaActivity.this.x = true;
                }

                public void onAbortShare() {
                    super.onAbortShare();
                    CompraProtegidaActivity.this.x = true;
                }
            }.showAlert();
        } else if (VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
        }
        return this.x;
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        this.w.onRequestPermissionsResult(i, strArr, iArr);
    }

    public void showRequestPermissionExplation(final int i) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getString(R.string.write_external_permission_request_message), null, getString(R.string.BTN_DIALOG_ACCEPT), null, null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
                if (VERSION.SDK_INT >= 23) {
                    CompraProtegidaActivity.this.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, i);
                }
            }
        });
        newInstance.show(getSupportFragmentManager(), OptionsToShareImpl.PERMISSION_DIALOG_TAG);
    }

    /* JADX WARNING: Removed duplicated region for block: B:42:0x0212  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x02fd  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setComprobanteContratacionView(ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarCompraProtegidaBodyResponseBean r29) {
        /*
            r28 = this;
            r1 = r28
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r2 = r1.p
            r3 = 2131757701(0x7f100a85, float:1.9146345E38)
            java.lang.String r3 = r1.getString(r3)
            r2.trackScreen(r3)
            r2 = r29
            r1.y = r2
            android.widget.TextView r2 = r1.lbl_comprobante_importe_mensual
            ar.com.santander.rio.mbanking.services.soap.beans.body.PlanSeguroBean r3 = r1.z
            java.lang.String r3 = r3.getCuota()
            r2.setText(r3)
            android.widget.TextView r2 = r1.lbl_comprobante_importe_mensual     // Catch:{ Exception -> 0x0036 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r3 = new ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility     // Catch:{ Exception -> 0x0036 }
            android.content.Context r4 = r28.getContext()     // Catch:{ Exception -> 0x0036 }
            r3.<init>(r4)     // Catch:{ Exception -> 0x0036 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.PlanSeguroBean r4 = r1.z     // Catch:{ Exception -> 0x0036 }
            java.lang.String r4 = r4.getCuota()     // Catch:{ Exception -> 0x0036 }
            java.lang.String r3 = r3.applyFilterAmount(r4)     // Catch:{ Exception -> 0x0036 }
            r2.setContentDescription(r3)     // Catch:{ Exception -> 0x0036 }
            goto L_0x003b
        L_0x0036:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()
        L_0x003b:
            ar.com.santander.rio.mbanking.services.soap.beans.body.PlanSeguroBean r2 = r1.z
            java.lang.String r2 = r2.getCantTarjetas()
            java.lang.String r3 = "1"
            boolean r2 = r2.equalsIgnoreCase(r3)
            if (r2 == 0) goto L_0x0070
            android.widget.TextView r2 = r1.lbl_comprobante_cobertura
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            ar.com.santander.rio.mbanking.services.soap.beans.body.PlanSeguroBean r4 = r1.z
            java.lang.String r4 = r4.getCantTarjetas()
            r3.append(r4)
            java.lang.String r4 = " "
            r3.append(r4)
            r4 = 2131756635(0x7f10065b, float:1.9144183E38)
            java.lang.String r4 = r1.getString(r4)
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r2.setText(r3)
            goto L_0x0096
        L_0x0070:
            android.widget.TextView r2 = r1.lbl_comprobante_cobertura
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            ar.com.santander.rio.mbanking.services.soap.beans.body.PlanSeguroBean r4 = r1.z
            java.lang.String r4 = r4.getCantTarjetas()
            r3.append(r4)
            java.lang.String r4 = " "
            r3.append(r4)
            r4 = 2131756636(0x7f10065c, float:1.9144185E38)
            java.lang.String r4 = r1.getString(r4)
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r2.setText(r3)
        L_0x0096:
            android.widget.TextView r2 = r1.lbl_comprobante_suma_asegurada
            ar.com.santander.rio.mbanking.services.soap.beans.body.PlanSeguroBean r3 = r1.z
            java.lang.String r3 = r3.getSumaAsegurada()
            r2.setText(r3)
            android.widget.TextView r2 = r1.lbl_comprobante_suma_asegurada     // Catch:{ Exception -> 0x00ba }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r3 = new ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility     // Catch:{ Exception -> 0x00ba }
            android.content.Context r4 = r28.getContext()     // Catch:{ Exception -> 0x00ba }
            r3.<init>(r4)     // Catch:{ Exception -> 0x00ba }
            ar.com.santander.rio.mbanking.services.soap.beans.body.PlanSeguroBean r4 = r1.z     // Catch:{ Exception -> 0x00ba }
            java.lang.String r4 = r4.getSumaAsegurada()     // Catch:{ Exception -> 0x00ba }
            java.lang.String r3 = r3.applyFilterAmount(r4)     // Catch:{ Exception -> 0x00ba }
            r2.setContentDescription(r3)     // Catch:{ Exception -> 0x00ba }
            goto L_0x00bf
        L_0x00ba:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()
        L_0x00bf:
            ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarCompraProtegidaBodyResponseBean r2 = r1.y
            java.lang.String r2 = r2.getFechaInicio()
            android.widget.TextView r3 = r1.lbl_comprobante_fecha
            ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarCompraProtegidaBodyResponseBean r4 = r1.y
            java.lang.String r4 = r4.getFechaInicio()
            r3.setText(r4)
            android.widget.TextView r3 = r1.lbl_comprobante_fecha     // Catch:{ Exception -> 0x00e9 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r4 = new ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility     // Catch:{ Exception -> 0x00e9 }
            android.content.Context r5 = r28.getContext()     // Catch:{ Exception -> 0x00e9 }
            r4.<init>(r5)     // Catch:{ Exception -> 0x00e9 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarCompraProtegidaBodyResponseBean r5 = r1.y     // Catch:{ Exception -> 0x00e9 }
            java.lang.String r5 = r5.getFechaInicio()     // Catch:{ Exception -> 0x00e9 }
            java.lang.String r4 = r4.applyFilterDate(r5)     // Catch:{ Exception -> 0x00e9 }
            r3.setContentDescription(r4)     // Catch:{ Exception -> 0x00e9 }
            goto L_0x00ee
        L_0x00e9:
            r0 = move-exception
            r3 = r0
            r3.printStackTrace()
        L_0x00ee:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarCompraProtegidaBodyResponseBean r4 = r1.y
            java.lang.String r4 = r4.getNumPoliza()
            r3.append(r4)
            java.lang.String r4 = "/"
            r3.append(r4)
            ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarCompraProtegidaBodyResponseBean r4 = r1.y
            java.lang.String r4 = r4.getNumCertificado()
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            android.widget.TextView r4 = r1.lbl_comprobante_poliza
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarCompraProtegidaBodyResponseBean r6 = r1.y
            java.lang.String r6 = r6.getNumPoliza()
            r5.append(r6)
            java.lang.String r6 = "/"
            r5.append(r6)
            ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarCompraProtegidaBodyResponseBean r6 = r1.y
            java.lang.String r6 = r6.getNumCertificado()
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            r4.setText(r5)
            android.widget.TextView r4 = r1.lbl_comprobante_poliza     // Catch:{ Exception -> 0x0150 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r5 = new ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility     // Catch:{ Exception -> 0x0150 }
            android.content.Context r6 = r28.getContext()     // Catch:{ Exception -> 0x0150 }
            r5.<init>(r6)     // Catch:{ Exception -> 0x0150 }
            android.widget.TextView r6 = r1.lbl_comprobante_poliza     // Catch:{ Exception -> 0x0150 }
            java.lang.CharSequence r6 = r6.getText()     // Catch:{ Exception -> 0x0150 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x0150 }
            java.lang.String r5 = r5.applyFilterCharacterToCharacter(r6)     // Catch:{ Exception -> 0x0150 }
            r4.setContentDescription(r5)     // Catch:{ Exception -> 0x0150 }
            goto L_0x0155
        L_0x0150:
            r0 = move-exception
            r4 = r0
            r4.printStackTrace()
        L_0x0155:
            android.widget.TextView r4 = r1.lbl_comprobante_medio_pago
            android.widget.TextView r5 = r1.lbl_confirmacion_medio_pago
            java.lang.CharSequence r5 = r5.getText()
            java.lang.String r5 = r5.toString()
            r4.setText(r5)
            android.widget.TextView r4 = r1.lbl_confirmacion_medio_pago     // Catch:{ Exception -> 0x01c3 }
            java.lang.CharSequence r4 = r4.getText()     // Catch:{ Exception -> 0x01c3 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x01c3 }
            java.lang.String r5 = "VISA"
            boolean r4 = r4.contains(r5)     // Catch:{ Exception -> 0x01c3 }
            if (r4 != 0) goto L_0x01a6
            android.widget.TextView r4 = r1.lbl_confirmacion_medio_pago     // Catch:{ Exception -> 0x01c3 }
            java.lang.CharSequence r4 = r4.getText()     // Catch:{ Exception -> 0x01c3 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x01c3 }
            java.lang.String r5 = "AMEX"
            boolean r4 = r4.contains(r5)     // Catch:{ Exception -> 0x01c3 }
            if (r4 == 0) goto L_0x0189
            goto L_0x01a6
        L_0x0189:
            android.widget.TextView r4 = r1.lbl_comprobante_medio_pago     // Catch:{ Exception -> 0x01c3 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r5 = new ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility     // Catch:{ Exception -> 0x01c3 }
            android.content.Context r6 = r28.getContext()     // Catch:{ Exception -> 0x01c3 }
            r5.<init>(r6)     // Catch:{ Exception -> 0x01c3 }
            android.widget.TextView r6 = r1.lbl_confirmacion_medio_pago     // Catch:{ Exception -> 0x01c3 }
            java.lang.CharSequence r6 = r6.getText()     // Catch:{ Exception -> 0x01c3 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x01c3 }
            java.lang.String r5 = r5.applyFilterAccount(r6)     // Catch:{ Exception -> 0x01c3 }
            r4.setContentDescription(r5)     // Catch:{ Exception -> 0x01c3 }
            goto L_0x01c8
        L_0x01a6:
            android.widget.TextView r4 = r1.lbl_comprobante_medio_pago     // Catch:{ Exception -> 0x01c3 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r5 = new ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility     // Catch:{ Exception -> 0x01c3 }
            android.content.Context r6 = r28.getContext()     // Catch:{ Exception -> 0x01c3 }
            r5.<init>(r6)     // Catch:{ Exception -> 0x01c3 }
            android.widget.TextView r6 = r1.lbl_confirmacion_medio_pago     // Catch:{ Exception -> 0x01c3 }
            java.lang.CharSequence r6 = r6.getText()     // Catch:{ Exception -> 0x01c3 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x01c3 }
            java.lang.String r5 = r5.applyFilterCreditCard(r6)     // Catch:{ Exception -> 0x01c3 }
            r4.setContentDescription(r5)     // Catch:{ Exception -> 0x01c3 }
            goto L_0x01c8
        L_0x01c3:
            r0 = move-exception
            r4 = r0
            r4.printStackTrace()
        L_0x01c8:
            android.widget.TextView r4 = r1.lbl_comprobante_ocupacion
            android.widget.TextView r5 = r1.lbl_confirmacion_ocupacion
            java.lang.CharSequence r5 = r5.getText()
            java.lang.String r5 = r5.toString()
            android.text.Spanned r5 = android.text.Html.fromHtml(r5)
            r4.setText(r5)
            android.widget.TextView r4 = r1.lbl_comprobante_ocupacion     // Catch:{ Exception -> 0x01f8 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r5 = new ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility     // Catch:{ Exception -> 0x01f8 }
            android.content.Context r6 = r28.getContext()     // Catch:{ Exception -> 0x01f8 }
            r5.<init>(r6)     // Catch:{ Exception -> 0x01f8 }
            android.widget.TextView r6 = r1.lbl_confirmacion_ocupacion     // Catch:{ Exception -> 0x01f8 }
            java.lang.CharSequence r6 = r6.getText()     // Catch:{ Exception -> 0x01f8 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x01f8 }
            java.lang.String r5 = r5.applyFilterGeneral(r6)     // Catch:{ Exception -> 0x01f8 }
            r4.setContentDescription(r5)     // Catch:{ Exception -> 0x01f8 }
            goto L_0x01fd
        L_0x01f8:
            r0 = move-exception
            r4 = r0
            r4.printStackTrace()
        L_0x01fd:
            r28.setActionBarComprobanteContratacion()
            android.widget.Button r4 = r1.btn_volver_comprobante
            r4.setOnClickListener(r1)
            android.widget.TableLayout r4 = r1.tl_lista_tarjetas_comprobante
            r4.removeAllViews()
            java.util.List<ar.com.santander.rio.mbanking.services.soap.beans.body.TarjetaBean> r4 = r1.B
            boolean r4 = r4.isEmpty()
            if (r4 != 0) goto L_0x02fd
            java.lang.String r4 = "layout_inflater"
            java.lang.Object r4 = r1.getSystemService(r4)
            android.view.LayoutInflater r4 = (android.view.LayoutInflater) r4
            java.util.List<ar.com.santander.rio.mbanking.services.soap.beans.body.TarjetaBean> r6 = r1.B
            java.util.Iterator r6 = r6.iterator()
            r7 = 0
            r8 = 0
        L_0x0222:
            boolean r9 = r6.hasNext()
            if (r9 == 0) goto L_0x0304
            java.lang.Object r9 = r6.next()
            ar.com.santander.rio.mbanking.services.soap.beans.body.TarjetaBean r9 = (ar.com.santander.rio.mbanking.services.soap.beans.body.TarjetaBean) r9
            r10 = 2131493364(0x7f0c01f4, float:1.8610206E38)
            r11 = 0
            android.view.View r10 = r4.inflate(r10, r11)
            android.widget.LinearLayout r10 = (android.widget.LinearLayout) r10
            r11 = 2131363484(0x7f0a069c, float:1.8346778E38)
            android.view.View r11 = r10.findViewById(r11)
            android.widget.TextView r11 = (android.widget.TextView) r11
            r12 = 2131363485(0x7f0a069d, float:1.834678E38)
            android.view.View r12 = r10.findViewById(r12)
            android.widget.TextView r12 = (android.widget.TextView) r12
            ar.com.santander.rio.mbanking.services.model.general.Tarjeta r15 = new ar.com.santander.rio.mbanking.services.model.general.Tarjeta
            java.lang.String r14 = r9.getNumTarjeta()
            r16 = 0
            r17 = 0
            r18 = 0
            java.lang.String r19 = r9.getTipo()
            r20 = 0
            java.lang.String r21 = ""
            r22 = 0
            r23 = 0
            r24 = 0
            r25 = 0
            r26 = 0
            r13 = r15
            r5 = r15
            r15 = r16
            r16 = r17
            r17 = r18
            r18 = r19
            r19 = r20
            r20 = r21
            r21 = r22
            r22 = r23
            r23 = r24
            r24 = r25
            r25 = r26
            r13.<init>(r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25)
            java.lang.String r5 = ar.com.santander.rio.mbanking.utils.Utils.mascaraTarjeta(r5)
            java.lang.String r13 = " "
            r14 = 2
            java.lang.String[] r5 = r5.split(r13, r14)
            r13 = r5[r7]
            r11.setText(r13)
            r11 = 1
            r5 = r5[r11]
            r12.setText(r5)
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r5 = new ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility     // Catch:{ Exception -> 0x02d0 }
            android.content.Context r13 = r28.getContext()     // Catch:{ Exception -> 0x02d0 }
            r5.<init>(r13)     // Catch:{ Exception -> 0x02d0 }
            ar.com.santander.rio.mbanking.services.model.general.Tarjeta r13 = new ar.com.santander.rio.mbanking.services.model.general.Tarjeta     // Catch:{ Exception -> 0x02d0 }
            java.lang.String r15 = r9.getNumTarjeta()     // Catch:{ Exception -> 0x02d0 }
            r16 = 0
            r17 = 0
            r18 = 0
            java.lang.String r19 = r9.getTipo()     // Catch:{ Exception -> 0x02d0 }
            r20 = 0
            java.lang.String r21 = ""
            r22 = 0
            r23 = 0
            r24 = 0
            r25 = 0
            r26 = 0
            r14 = r13
            r14.<init>(r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25, r26)     // Catch:{ Exception -> 0x02d0 }
            java.lang.String r9 = ar.com.santander.rio.mbanking.utils.Utils.mascaraTarjeta(r13)     // Catch:{ Exception -> 0x02d0 }
            java.lang.String r5 = r5.applyFilterCreditCard(r9)     // Catch:{ Exception -> 0x02d0 }
            r12.setContentDescription(r5)     // Catch:{ Exception -> 0x02d0 }
            goto L_0x02d5
        L_0x02d0:
            r0 = move-exception
            r5 = r0
            r5.printStackTrace()
        L_0x02d5:
            java.lang.Integer r5 = java.lang.Integer.valueOf(r8)
            r10.setTag(r5)
            java.util.List<ar.com.santander.rio.mbanking.services.soap.beans.body.TarjetaBean> r5 = r1.B
            int r5 = r5.size()
            int r5 = r5 - r11
            if (r8 != r5) goto L_0x02f2
            r5 = 2131363483(0x7f0a069b, float:1.8346776E38)
            android.view.View r5 = r10.findViewById(r5)
            r9 = 8
            r5.setVisibility(r9)
            goto L_0x02f4
        L_0x02f2:
            r9 = 8
        L_0x02f4:
            android.widget.TableLayout r5 = r1.tl_lista_tarjetas_comprobante
            r5.addView(r10)
            int r8 = r8 + 1
            goto L_0x0222
        L_0x02fd:
            r9 = 8
            android.widget.TableLayout r4 = r1.tl_lista_tarjetas_comprobante
            r4.setVisibility(r9)
        L_0x0304:
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r4 = r1.p
            r5 = 2131758176(0x7f100c60, float:1.9147309E38)
            java.lang.String r5 = r1.getString(r5)
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            r6.append(r3)
            java.lang.String r3 = " "
            r6.append(r3)
            r6.append(r2)
            java.lang.String r2 = r6.toString()
            r4.trackTransaction(r5, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.activities.CompraProtegidaActivity.setComprobanteContratacionView(ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarCompraProtegidaBodyResponseBean):void");
    }

    public void attachView() {
        switch (this.mControlPager.getDisplayedChild()) {
            case 0:
                if (!this.r.isViewAttached()) {
                    this.r.attachView(this);
                    return;
                }
                return;
            case 1:
                if (!this.s.isViewAttached()) {
                    this.s.attachView(this);
                    return;
                }
                return;
            case 2:
                if (!this.t.isViewAttached()) {
                    this.t.attachView(this);
                    return;
                }
                return;
            case 3:
                if (!this.u.isViewAttached()) {
                    this.u.attachView(this);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void detachView() {
        if (this.r.isViewAttached()) {
            this.r.detachView();
        }
        if (this.s.isViewAttached()) {
            this.s.detachView();
        }
        if (this.u.isViewAttached()) {
            this.u.detachView();
        }
    }

    public void onBackPressed() {
        switch (this.mControlPager.getCurrentView().getId()) {
            case R.id.id_layout_comprobante_contratacion /*2131364778*/:
                if (!this.x) {
                    this.w.showAlert();
                    return;
                } else {
                    backButtonComprobanteContratacion();
                    return;
                }
            case R.id.id_layout_confirmar_contratacion /*2131364779*/:
                backButtonConfirmarContratacion();
                return;
            case R.id.id_layout_contratacion_seguro /*2131364780*/:
                backButtonContratacionSeguro();
                return;
            case R.id.id_layout_solicitar_seguro /*2131364781*/:
                backButtonSolicitarSeguro();
                return;
            default:
                return;
        }
    }

    public void backButtonComprobanteContratacion() {
        setResult(-1);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void onClick(View view) {
        LeyendaItem leyendaItem = new LeyendaItem("", "", "");
        switch (view.getId()) {
            case R.id.F27_11_btn_continuar /*2131363453*/:
                gotoContratacionSeguro(null);
                return;
            case R.id.F27_11_link_detalle_cobertura /*2131363456*/:
                for (LeyendaItem leyendaItem2 : this.A.getListaLeyendas().getLeyenda()) {
                    if (leyendaItem2.getIdLeyenda().equalsIgnoreCase(idLeyenda.COMPRA_PROTEGIDA_DETALLE)) {
                        leyendaItem = leyendaItem2;
                    }
                }
                this.r.showDetalle(leyendaItem.getTitulo(), leyendaItem.getDescripcion());
                this.p.trackScreen(getString(R.string.analytics_screen_Detalle_cobertura));
                return;
            case R.id.F27_12_lbl_data_medio_pago /*2131363474*/:
                this.p.trackEvent(getString(R.string.analytics_trackevent_category_seguros), getString(R.string.analytics_trackevent_action_seleccionar_medio_pago_compra_protegida), getString(R.string.analytics_trackevent_label_medio_pago));
                showFormasPago();
                return;
            case R.id.F27_13_btn_confirmar /*2131363487*/:
                this.t.showConfirmarDialog(this.A.getCodRamo(), this.A.getCodProducto(), this.A.getNumCotizacion(), this.E.getCodOcupacion(), this.B, this.D, this.C);
                return;
            case R.id.F27_13_img_checkbox /*2131363488*/:
                f();
                return;
            case R.id.F27_13_terminos /*2131363502*/:
                for (LeyendaItem leyendaItem3 : this.A.getListaLeyendas().getLeyenda()) {
                    if (leyendaItem3.getIdLeyenda().equalsIgnoreCase(idLeyenda.COMPRA_PROTEGIDA_TYC)) {
                        leyendaItem = leyendaItem3;
                    }
                }
                this.t.showDetalle(leyendaItem.getTitulo(), leyendaItem.getDescripcion());
                this.p.trackScreen(getString(R.string.f207analytics_screen_Trminos_condiciones));
                return;
            case R.id.F27_14_btn_volver /*2131363505*/:
                if (!this.x) {
                    this.w.showAlert();
                    return;
                }
                setResult(-1);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                return;
            case R.id.f2ID_4061_SEGUROS_BOTN_CONTRATARAHORA /*2131364060*/:
                gotoConfirmarContratacion();
                return;
            case R.id.rowOcupacion /*2131365591*/:
                this.s.getOcupaciones(this, this.E == null ? "" : this.E.getDescOcupacion(), this.sessionManager.getListaOcupaciones(), this.sessionManager);
                return;
            default:
                return;
        }
    }

    public void endActivity() {
        setResult(-1);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    /* access modifiers changed from: private */
    public void l() {
        if (this.E == null || this.E.equals(getContext().getString(R.string.F24_20_lbl_data_seleccionar)) || this.lbl_contratcion_medio_pago.getText().toString().equals("") || this.lbl_contratcion_medio_pago.getText().toString().equals(getContext().getString(R.string.F24_20_lbl_data_seleccionar))) {
            this.btn_continuar_contratacion.setEnabled(false);
            this.btn_continuar_contratacion.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_gris_claro));
            return;
        }
        this.btn_continuar_contratacion.setEnabled(true);
        this.btn_continuar_contratacion.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_rojo));
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (!activityResultHandler(i2, intent) && i == 3 && i2 == -1) {
            this.E = (OcupacionBean) intent.getParcelableExtra(SegurosConstants.INTENT_EXTRA_OCUPACION);
            this.lbl_seleccionar_ocupacion.setText(Html.fromHtml(this.E.getDescOcupacion()));
            l();
        }
    }

    public AnalyticsManager getAnalyticsManager() {
        return this.p;
    }
}
