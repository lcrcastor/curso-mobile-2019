package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.module.funds.DetalleFondoPresenter;
import ar.com.santander.rio.mbanking.app.module.funds.DetalleFondoView;
import ar.com.santander.rio.mbanking.app.module.funds.DetalleMovimientoFondoPresenter;
import ar.com.santander.rio.mbanking.app.module.funds.DetalleMovimientoFondoView;
import ar.com.santander.rio.mbanking.app.module.funds.MovimientosFondoView;
import ar.com.santander.rio.mbanking.app.module.funds.MovimientosFondosPresenter;
import ar.com.santander.rio.mbanking.app.module.inversionesbancaprivada.analytics.InversionesAnalyticsImpl;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.adapters.MovimientosFondoAdapter;
import ar.com.santander.rio.mbanking.app.ui.adapters.MovimientosFondoAdapter.OnItemClickListener;
import ar.com.santander.rio.mbanking.app.ui.constants.FondosConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.FondosConstants.LeyendasLegales;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.services.model.general.TipoCliente;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaOperativaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FondoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda;
import ar.com.santander.rio.mbanking.services.soap.beans.body.MovCtasBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.MovimientoFondosBean;
import ar.com.santander.rio.mbanking.utils.UtilAccount;
import ar.com.santander.rio.mbanking.utils.UtilCurrency;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import ar.com.santander.rio.mbanking.view.SlideAnimationViewFlipper;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class DetalleFondoActivity extends MvpPrivateMenuActivity implements OnClickListener, DetalleFondoView, DetalleMovimientoFondoView, MovimientosFondoView, OnItemClickListener {
    private MovimientosFondoAdapter A;
    private CAccessibility B;
    private InversionesAnalyticsImpl C;
    @InjectView(2131362919)
    Button btnSuscribirCuotapartes;
    @InjectView(2131363166)
    LinearLayout comprobanteFondo;
    @InjectView(2131362956)
    ImageView flechaDetalle;
    @InjectView(2131362957)
    TextView lblBuscar;
    @InjectView(2131362987)
    TextView lblConceptoDetalle;
    @InjectView(2131362923)
    TextView lblDataAdministracionFondo;
    @InjectView(2131362976)
    TextView lblDataCertificadoDetalleMov;
    @InjectView(2131362977)
    TextView lblDataConceptoDetalleMov;
    @InjectView(2131362932)
    TextView lblDataCotizacion;
    @InjectView(2131362978)
    TextView lblDataCotizacionDetalleMov;
    @InjectView(2131362924)
    TextView lblDataCuentaTitulo;
    @InjectView(2131362979)
    TextView lblDataCuentaTituloDetalleMov;
    @InjectView(2131362925)
    TextView lblDataCuotaPartes;
    @InjectView(2131362980)
    TextView lblDataCuotaparteDetalleMov;
    @InjectView(2131362926)
    TextView lblDataEntrada;
    @InjectView(2131362981)
    TextView lblDataFechaDetalleMov;
    @InjectView(2131362982)
    TextView lblDataFondoDetalleMov;
    @InjectView(2131362983)
    TextView lblDataFondoTxtDetalleMov;
    @InjectView(2131362927)
    TextView lblDataHorario;
    @InjectView(2131362928)
    TextView lblDataImporte;
    @InjectView(2131362929)
    TextView lblDataMoneda;
    @InjectView(2131362930)
    TextView lblDataPlazoPago;
    @InjectView(2131362931)
    TextView lblDataSalida;
    @InjectView(2131362938)
    TextView lblDataTextoLegal;
    @InjectView(2131362933)
    TextView lblDataValorCuotarpe;
    @InjectView(2131362985)
    TextView lblFechaFondo;
    @InjectView(2131362940)
    TextView lblNombreDetalle;
    @InjectView(2131362966)
    LinearLayout lblSinMovimientos;
    @InjectView(2131362967)
    TextView lblSinMovimientosTxt;
    @InjectView(2131362965)
    TextView lblTitleUltimosMov;
    @InjectView(2131362961)
    TextView lblUltimosMovimientos;
    @InjectView(2131362962)
    LinearLayout linearTitle;
    @InjectView(2131362946)
    View lnlCotizacion;
    @InjectView(2131362964)
    RecyclerView lstMovimientos;
    public String mContratoSusc;
    public String mContratoTransf;
    @InjectView(2131364611)
    ViewFlipper mControlPager;
    public ArrayList<CuentaFondosBean> mListaCuentas;
    @Inject
    AnalyticsManager p;
    private List<Leyenda> q;
    /* access modifiers changed from: private */
    public DetalleFondoPresenter r;
    @InjectView(2131362963)
    RelativeLayout rllBusquedaAvanzada;
    private MovimientosFondosPresenter s;
    private DetalleMovimientoFondoPresenter t;
    @InjectView(2131362988)
    TextView titleDetalleMovimiento;
    private String u;
    /* access modifiers changed from: private */
    public FondoBean v;
    /* access modifiers changed from: private */
    public CuentaFondosBean w;
    private List<MovimientoFondosBean> x;
    private ArrayList<CuentaOperativaBean> y = new ArrayList<>();
    private IsbanDialogFragment z;

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
        return R.layout.detalle_fondo_activity;
    }

    public AnalyticsManager getAnalyticsManager() {
        return this.p;
    }

    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        this.B = CAccessibility.getInstance(getApplicationContext());
        this.p.trackScreen(getString(R.string.analytics_screen_mostrar_detalles_mis_fondos));
        this.C = new InversionesAnalyticsImpl(this, this.p);
        ButterKnife.inject((Activity) this);
        initialize();
    }

    public void initialize() {
        this.r = new DetalleFondoPresenter(this.mBus, this.mDataManager, this.sessionManager, this);
        this.s = new MovimientosFondosPresenter(this.mBus, this.mDataManager);
        this.t = new DetalleMovimientoFondoPresenter(this.mBus, this.mDataManager);
        this.u = getIntent().getStringExtra("ORIGEN");
        this.v = (FondoBean) getIntent().getParcelableExtra(FondosConstants.INTENT_EXTRA_FONDO);
        this.w = (CuentaFondosBean) getIntent().getParcelableExtra("CUENTA");
        this.y = getIntent().getParcelableArrayListExtra(FondosConstants.INTENT_EXTRA_LST_CUENTAS_OPERATIVAS);
        b();
        initializeList();
        if (getIntent().getStringExtra("ORIGEN").equalsIgnoreCase("DETALLE_FONDO")) {
            this.mListaCuentas = getIntent().getParcelableArrayListExtra("CUENTAS");
            this.mContratoSusc = getIntent().getStringExtra(FondosConstants.INTENT_EXTRA_CONTRATO_SUSC);
            this.mContratoTransf = getIntent().getStringExtra(FondosConstants.INTENT_EXTRA_CONTRATO_TRANSF);
            this.q = getIntent().getParcelableArrayListExtra(FondosConstants.INTENT_EXTRA_LEGALES);
            gotoDetalleFondo();
        } else if (getIntent().getStringExtra("ORIGEN").equalsIgnoreCase(FondosConstants.ORIGEN_ULTIMOS_MOVIMIENTOS)) {
            gotoMovimiento(getIntent().getParcelableArrayListExtra(FondosConstants.INTENT_EXTRA_LST_MOVIMIENTOS), FondosConstants.ORIGEN_ULTIMOS_MOVIMIENTOS);
        }
        c();
    }

    private void b() {
        try {
            if (a(this.w.getSucursalCuenta())) {
                this.C.detalleFondoComun(TipoCliente.BP.getTipoCliente());
            } else {
                this.C.detalleFondoComun(TipoCliente.RTL.getTipoCliente());
            }
        } catch (Exception unused) {
        }
    }

    private boolean a(String str) {
        return Integer.valueOf(str).intValue() >= 250 && Integer.valueOf(str).intValue() <= 259;
    }

    private void c() {
        try {
            RelativeLayout relativeLayout = this.rllBusquedaAvanzada;
            CAccessibility cAccessibility = this.B;
            StringBuilder sb = new StringBuilder();
            sb.append(this.lblUltimosMovimientos.getText().toString());
            sb.append("  ");
            sb.append(this.lblBuscar.getText().toString());
            relativeLayout.setContentDescription(cAccessibility.applyFilterGeneral(sb.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gotoDetalleFondo() {
        gotoPage(0);
        this.r.onCreatePage();
    }

    public void setActionBarDetalle() {
        setActionBarType(ActionBarType.BACK_WITH_MENU);
        this.mActionBar = getSupportActionBar().getCustomView();
        lockMenu(true);
        d();
    }

    private void d() {
        View customView = getSupportActionBar().getCustomView();
        View findViewById = customView.findViewById(R.id.back_imgButton);
        View findViewById2 = customView.findViewById(R.id.menu);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    DetalleFondoActivity.this.backButtonDetalleFondo();
                }
            });
        }
        if (this.w.isBancaPrivada()) {
            findViewById2.setVisibility(8);
        } else if (findViewById2 != null) {
            findViewById2.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    DetalleFondoActivity.this.p.trackEvent(DetalleFondoActivity.this.getString(R.string.analytics_event_category_fondos), DetalleFondoActivity.this.getString(R.string.analytics_event_action_cancelar), DetalleFondoActivity.this.getString(R.string.analytics_event_label_opciones_fondo_contratado));
                    DetalleFondoActivity.this.e();
                }
            });
        }
    }

    public void backButtonDetalleFondo() {
        setResult(0);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.noAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToDownAnimation());
                        break;
                    }
                    break;
                case 1:
                    if (z2) {
                        if (!this.u.equalsIgnoreCase(FondosConstants.ORIGEN_ULTIMOS_MOVIMIENTOS)) {
                            this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromDownAnimation());
                            this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.noAnimation());
                            break;
                        }
                    } else {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.noAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
                        break;
                    }
                    break;
                case 2:
                    this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                    this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                    break;
            }
            this.mControlPager.setDisplayedChild(i);
            attachView();
        }
    }

    public void setDetalleFondoView() {
        setActionBarDetalle();
        TextView textView = this.lblDataImporte;
        StringBuilder sb = new StringBuilder();
        sb.append(UtilCurrency.getSimbolCurrencyFromDescription(Html.fromHtml(this.v.getMoneda()).toString()));
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(this.v.getImporte());
        textView.setText(sb.toString());
        try {
            TextView textView2 = this.lblDataImporte;
            CAccessibility cAccessibility = new CAccessibility(getContext());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(UtilCurrency.getSimbolCurrencyFromDescription(Html.fromHtml(this.v.getMoneda()).toString()));
            sb2.append(this.v.getImporte());
            textView2.setContentDescription(cAccessibility.applyFilterGeneral(sb2.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(this.v.getVariacionCotizaDiaria())) {
            this.lnlCotizacion.setVisibility(0);
            if (this.v.getVariacionCotizaDiaria().indexOf("-") == -1) {
                this.lblDataCotizacion.setText(this.v.getVariacionCotizaDiaria().replace(Constants.SYMBOL_POSITIVE, ""));
                try {
                    this.lblDataCotizacion.setContentDescription(new CAccessibility(getContext()).applyFilterTasaValue(this.v.getVariacionCotizaDiaria().replace(Constants.SYMBOL_POSITIVE, "")));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                this.flechaDetalle.setBackground(getResources().getDrawable(R.drawable.green_arrow));
            } else {
                this.lblDataCotizacion.setText(this.v.getVariacionCotizaDiaria().replace("-", ""));
                this.flechaDetalle.setBackground(getResources().getDrawable(R.drawable.red_arrow));
            }
        } else {
            this.lnlCotizacion.setVisibility(4);
        }
        this.lblDataCuotaPartes.setText(this.v.getCantidadCuotapartes());
        this.lblNombreDetalle.setText(Html.fromHtml(this.v.getNombre()));
        try {
            this.lblNombreDetalle.setContentDescription(FondosConstants.applyAccesibilityFilterName(getContext(), this.lblNombreDetalle.getText().toString()));
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        this.lblDataCuentaTitulo.setText(UtilAccount.formatCuentaTitulo(this.w.getNumero()));
        try {
            this.lblDataCuentaTitulo.setContentDescription(new CAccessibility(getContext()).applyFilterCharacterToCharacter(UtilAccount.formatCuentaTitulo(this.w.getNumero())));
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        this.lblDataValorCuotarpe.setText(this.v.getValorCuotaParte());
        try {
            this.lblDataValorCuotarpe.setContentDescription(new CAccessibility(getContext()).applyFilterAmount(this.v.getValorCuotaParte()));
        } catch (Exception e5) {
            e5.printStackTrace();
        }
        this.lblDataMoneda.setText(Html.fromHtml(this.v.getMoneda()));
        this.lblDataPlazoPago.setText(Html.fromHtml(this.v.getPlazoPago()));
        try {
            this.lblDataPlazoPago.setContentDescription(new CAccessibility(getContext()).applyFilterGeneral(Html.fromHtml(this.v.getPlazoPago()).toString()));
        } catch (Exception e6) {
            e6.printStackTrace();
        }
        TextView textView3 = this.lblDataHorario;
        StringBuilder sb3 = new StringBuilder();
        sb3.append(this.v.getHorarioDesde());
        sb3.append(UtilsCuentas.SEPARAOR2);
        sb3.append(getString(R.string.F24_05_TXT_HRS));
        sb3.append(UtilsCuentas.SEPARAOR2);
        sb3.append(getString(R.string.F24_05_TXT_A));
        sb3.append(UtilsCuentas.SEPARAOR2);
        sb3.append(this.v.getHorarioHasta());
        sb3.append(UtilsCuentas.SEPARAOR2);
        sb3.append(getString(R.string.F24_05_TXT_HRS));
        textView3.setText(sb3.toString());
        try {
            TextView textView4 = this.lblDataHorario;
            StringBuilder sb4 = new StringBuilder();
            CAccessibility cAccessibility2 = new CAccessibility(getContext());
            StringBuilder sb5 = new StringBuilder();
            sb5.append(this.v.getHorarioDesde());
            sb5.append(UtilsCuentas.SEPARAOR2);
            sb5.append(getString(R.string.F24_05_TXT_HRS));
            sb4.append(cAccessibility2.applyFilterTime(sb5.toString()));
            sb4.append(" a ");
            CAccessibility cAccessibility3 = new CAccessibility(getContext());
            StringBuilder sb6 = new StringBuilder();
            sb6.append(this.v.getHorarioHasta());
            sb6.append(UtilsCuentas.SEPARAOR2);
            sb6.append(getString(R.string.F24_05_TXT_HRS));
            sb4.append(cAccessibility3.applyFilterGeneral(sb6.toString()));
            textView4.setContentDescription(sb4.toString());
        } catch (Exception e7) {
            e7.printStackTrace();
        }
        if (this.v.getHonorarios() != null) {
            this.lblDataAdministracionFondo.setText(this.v.getHonorarios().getAdmin());
            try {
                this.lblDataAdministracionFondo.setContentDescription(new CAccessibility(getContext()).applyFilterTasaValue(this.v.getHonorarios().getAdmin()));
            } catch (Exception e8) {
                e8.printStackTrace();
            }
            this.lblDataEntrada.setText(this.v.getHonorarios().getEntrada());
            try {
                this.lblDataEntrada.setContentDescription(new CAccessibility(getContext()).applyFilterTasaValue(this.v.getHonorarios().getEntrada()));
            } catch (Exception e9) {
                e9.printStackTrace();
            }
            this.lblDataSalida.setText(this.v.getHonorarios().getSalida());
            try {
                this.lblDataSalida.setContentDescription(new CAccessibility(getContext()).applyFilterTasaValue(this.v.getHonorarios().getSalida()));
            } catch (Exception e10) {
                e10.printStackTrace();
            }
        }
        if (this.q == null || this.q.size() <= 0) {
            this.lblDataTextoLegal.setText("");
        } else {
            for (int i = 0; i < this.q.size(); i++) {
                if (((Leyenda) this.q.get(i)).getIdLeyenda().equals(LeyendasLegales.TENENCIA)) {
                    this.lblDataTextoLegal.setText(Html.fromHtml(((Leyenda) this.q.get(i)).getDescripcion()));
                }
            }
        }
        try {
            this.lblDataTextoLegal.setContentDescription(new CAccessibility(getContext()).applyFilterGeneral(this.lblDataTextoLegal.getText().toString()));
        } catch (Exception e11) {
            e11.printStackTrace();
        }
        if (this.w.isBancaPrivada() || !this.v.getAptoSuscrip().equalsIgnoreCase("S")) {
            this.btnSuscribirCuotapartes.setVisibility(8);
        } else {
            this.btnSuscribirCuotapartes.setOnClickListener(this);
        }
    }

    /* access modifiers changed from: private */
    public void e() {
        ArrayList arrayList = new ArrayList();
        boolean[] zArr = new boolean[4];
        arrayList.add(getResources().getString(R.string.F24_TXT_DIALOG_ULTIMOS_MOVIMIENTOS));
        zArr[0] = true;
        arrayList.add(getResources().getString(R.string.F24_TXT_DIALOG_SUSCRIBIR_MAS));
        arrayList.add(getResources().getString(R.string.F24_TXT_DIALOG_TRANSFERIR));
        arrayList.add(getResources().getString(R.string.F24_TXT_DIALOG_RESCATAR));
        zArr[3] = true;
        if (this.v.getAptoSuscrip().equalsIgnoreCase("S")) {
            zArr[1] = true;
            zArr[2] = true;
        } else {
            zArr[1] = false;
            zArr[2] = false;
        }
        this.z = IsbanDialogFragment.newInstance("mPopupMenu", null, null, arrayList, getString(R.string.F24_TXT_DIALOG_CANCELAR), null, null, null, arrayList, zArr);
        this.z.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onItemSelected(String str) {
                if (str.equalsIgnoreCase(DetalleFondoActivity.this.getResources().getString(R.string.F24_TXT_DIALOG_ULTIMOS_MOVIMIENTOS))) {
                    DetalleFondoActivity.this.p.trackEvent(DetalleFondoActivity.this.getString(R.string.analytics_event_category_fondos), DetalleFondoActivity.this.getString(R.string.analytics_event_action_ultimos_movimientos), DetalleFondoActivity.this.getString(R.string.analytics_event_label_dia_fecha_busquedaAvanzada));
                    DetalleFondoActivity.this.r.onGetMovimiento(DetalleFondoActivity.this.v, DetalleFondoActivity.this.w);
                }
                if (str.equalsIgnoreCase(DetalleFondoActivity.this.getResources().getString(R.string.F24_TXT_DIALOG_SUSCRIBIR_MAS))) {
                    DetalleFondoActivity.this.suscribirFondo(null);
                }
                if (str.equalsIgnoreCase(DetalleFondoActivity.this.getResources().getString(R.string.F24_TXT_DIALOG_TRANSFERIR))) {
                    DetalleFondoActivity.this.transferirFondo();
                }
                if (str.equalsIgnoreCase(DetalleFondoActivity.this.getResources().getString(R.string.F24_TXT_DIALOG_RESCATAR))) {
                    DetalleFondoActivity.this.rescatarFondo();
                }
            }

            public void onSimpleActionButton() {
                DetalleFondoActivity.this.p.trackEvent(DetalleFondoActivity.this.getString(R.string.analytics_event_category_fondos), DetalleFondoActivity.this.getString(R.string.analytics_event_action_cancelar), DetalleFondoActivity.this.getString(R.string.analytics_event_label_cancelacion_mis_fondos));
            }
        });
        this.z.setCancelable(true);
        this.z.show(getSupportFragmentManager(), "Dialog");
    }

    public void rescatarFondo() {
        Intent intent = new Intent(this, RescatarFondoActivity.class);
        intent.putParcelableArrayListExtra("CUENTAS", this.mListaCuentas);
        intent.putExtra(FondosConstants.INTENT_EXTRA_FONDO, this.v);
        intent.putExtra("CUENTA", this.w);
        intent.putExtra("ORIGEN", "DETALLE_FONDO");
        intent.putParcelableArrayListExtra(FondosConstants.INTENT_EXTRA_LST_CUENTAS_OPERATIVAS, this.y);
        startActivityForResult(intent, 4);
    }

    public void transferirFondo() {
        Intent intent = new Intent(this, TransferirFondoActivity.class);
        intent.putParcelableArrayListExtra("CUENTAS", this.mListaCuentas);
        intent.putExtra(FondosConstants.INTENT_EXTRA_FONDO, this.v);
        intent.putExtra("CUENTA", this.w);
        intent.putExtra("ORIGEN", "DETALLE_FONDO");
        intent.putExtra(FondosConstants.INTENT_EXTRA_CONTRATO, this.mContratoTransf);
        intent.putParcelableArrayListExtra(FondosConstants.INTENT_EXTRA_LEGALES, (ArrayList) this.q);
        startActivityForResult(intent, 5);
    }

    public void suscribirFondo(MovCtasBodyResponseBean movCtasBodyResponseBean) {
        Intent intent = new Intent(this, SuscribirFondoActivity.class);
        intent.putExtra("ORIGEN", "DETALLE_FONDO");
        intent.putExtra(FondosConstants.INTENT_EXTRA_ACCION, FondosConstants.ACCION_SUSCRIBIR_CUOTAPARTES);
        intent.putParcelableArrayListExtra("CUENTAS", this.mListaCuentas);
        intent.putExtra("CUENTA", this.w);
        intent.putExtra(FondosConstants.INTENT_EXTRA_FONDO, this.v);
        intent.putExtra(FondosConstants.INTENT_EXTRA_CONTRATO, this.mContratoSusc);
        intent.putParcelableArrayListExtra(FondosConstants.INTENT_EXTRA_LEGALES, (ArrayList) this.q);
        intent.putParcelableArrayListExtra(FondosConstants.INTENT_EXTRA_LST_CUENTAS_OPERATIVAS, this.y);
        startActivityForResult(intent, 6);
    }

    public void gotoBusquedaAvanzada() {
        Intent intent = new Intent(this, BusquedaAvanzadaMovimientoFondosActivity.class);
        intent.putExtra(FondosConstants.INTENT_EXTRA_FONDO, this.v);
        intent.putExtra("CUENTA", this.w);
        startActivityForResult(intent, 2);
        this.p.trackScreen(getString(R.string.analytics_screen_busqueda_por_fecha_movimiento_fondo));
    }

    public void onClick(View view) {
        int id2 = view.getId();
        if (id2 == R.id.F24_05_BTN_SUSCRIBIR_MAS_CUOTAPARTES) {
            suscribirFondo(null);
        } else if (id2 == R.id.F24_06_RLL_BUSCAR) {
            this.p.trackEvent(getString(R.string.analytics_event_category_fondos), getString(R.string.analytics_event_action_cancelar), getString(R.string.analytics_event_label_buscar_movimientos));
            this.s.showSelectSearchDialog(this.v, this.w);
        }
    }

    public void gotoMovimiento(List<MovimientoFondosBean> list, String str) {
        if (str.equalsIgnoreCase(FondosConstants.ORIGEN_BUSQUEDA_AVANZADA)) {
            gotoPage(1, false);
        } else {
            gotoPage(1, true);
        }
        this.s.onCreatePage(list, str);
    }

    public void setActionBarMovimiento() {
        if (this.u.equalsIgnoreCase("DETALLE_FONDO")) {
            setActionBarType(ActionBarType.PUSH_CLOSE);
            this.mActionBar = getSupportActionBar().getCustomView();
            lockMenu(true);
            f();
        } else if (this.u.equalsIgnoreCase(FondosConstants.ORIGEN_ULTIMOS_MOVIMIENTOS)) {
            setActionBarType(ActionBarType.BACK);
            this.mActionBar = getSupportActionBar().getCustomView();
            lockMenu(true);
            g();
        }
    }

    private void f() {
        View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.ok);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    DetalleFondoActivity.this.h();
                }
            });
        }
    }

    private void g() {
        View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.toggle);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    DetalleFondoActivity.this.onBackPressed();
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void h() {
        onBackPressed();
    }

    public void setMovimientosFondoView(List<MovimientoFondosBean> list, String str) {
        this.p.trackScreen(getString(R.string.analytics_screen_mostrar_ultimos_movimientos_mis_fondos));
        this.x = list;
        setActionBarMovimiento();
        initializeList();
        configureListAdapter(list);
        this.lblTitleUltimosMov.setText(Html.fromHtml(this.v.getNombre()));
        try {
            this.lblTitleUltimosMov.setContentDescription(FondosConstants.applyAccesibilityFilterName(getContext(), this.lblTitleUltimosMov.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.rllBusquedaAvanzada.setOnClickListener(this);
        if (this.x.size() > 0) {
            this.lstMovimientos.setVisibility(0);
            this.lblSinMovimientos.setVisibility(8);
            this.linearTitle.setVisibility(0);
            return;
        }
        this.linearTitle.setVisibility(8);
        this.lstMovimientos.setVisibility(8);
        this.lblSinMovimientos.setVisibility(0);
        if (str.equalsIgnoreCase(FondosConstants.ORIGEN_BUSQUEDA_AVANZADA)) {
            this.lblSinMovimientosTxt.setText(getString(R.string.MSG_USER00425_Fondos_de_Inversion));
        } else {
            this.lblSinMovimientosTxt.setText(getString(R.string.ID_3968_FONDOS_LBL_ULTIMOS_MOVIMIENTOS));
        }
    }

    public void onItemClick(View view) {
        gotoDetalleMovimiento((MovimientoFondosBean) this.x.get(this.lstMovimientos.getChildPosition(view)));
    }

    /* access modifiers changed from: protected */
    public void initializeList() {
        this.lstMovimientos.setHasFixedSize(true);
        this.lstMovimientos.setLayoutManager(new LinearLayoutManager(this, 1, false));
    }

    /* access modifiers changed from: protected */
    public void configureListAdapter(List<MovimientoFondosBean> list) {
        this.A = new MovimientosFondoAdapter(this, list);
        this.A.setOnClickListener(this);
        this.lstMovimientos.setAdapter(this.A);
    }

    public void gotoDetalleMovimiento(MovimientoFondosBean movimientoFondosBean) {
        gotoPage(2);
        this.t.onCreatePage(movimientoFondosBean);
    }

    public void setActionBarDetalleMovimiento() {
        setActionBarType(ActionBarType.BACK);
        this.mActionBar = getSupportActionBar().getCustomView();
        lockMenu(true);
        i();
    }

    private void i() {
        View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.toggle);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    DetalleFondoActivity.this.onBackPressed();
                }
            });
        }
    }

    public void setDetalleMovimientoFondoView(MovimientoFondosBean movimientoFondosBean) {
        this.p.trackScreen(getString(R.string.f210analytics_screen_detalle_movimiento_fondo_seeccionado));
        setActionBarDetalleMovimiento();
        String str = UtilDate.getDateWithNameMonth(movimientoFondosBean.getFecha().toString(), Constants.FORMAT_DATE_APP).toString();
        TextView textView = this.lblFechaFondo;
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(str);
        textView.setText(sb.toString());
        try {
            this.lblFechaFondo.setContentDescription(new CAccessibility(getContext()).applyFilterDate(str));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.lblDataFondoDetalleMov.setText(movimientoFondosBean.getImporte());
        try {
            this.lblDataFondoDetalleMov.setContentDescription(new CAccessibility(getContext()).applyFilterAmount(movimientoFondosBean.getImporte()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.lblConceptoDetalle.setText(Html.fromHtml(movimientoFondosBean.getConcepto()));
        this.lblDataCuentaTituloDetalleMov.setText(UtilAccount.formatCuentaTitulo(this.w.getNumero()));
        try {
            this.lblDataCuentaTituloDetalleMov.setContentDescription(new CAccessibility(getContext()).applyFilterCharacterToCharacter(UtilAccount.formatCuentaTitulo(this.w.getNumero())));
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        this.lblDataFondoTxtDetalleMov.setText(Html.fromHtml(this.v.getNombre()));
        try {
            this.lblDataFondoTxtDetalleMov.setContentDescription(FondosConstants.applyAccesibilityFilterName(getContext(), this.lblDataFondoTxtDetalleMov.getText().toString()));
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        this.lblDataCuotaparteDetalleMov.setText(movimientoFondosBean.getCuotapartes());
        this.lblDataCotizacionDetalleMov.setText(movimientoFondosBean.getCotizacion());
        this.lblDataFechaDetalleMov.setText(movimientoFondosBean.getFecha());
        try {
            this.lblDataFechaDetalleMov.setContentDescription(new CAccessibility(getContext()).applyFilterDateTime(movimientoFondosBean.getFecha()));
        } catch (Exception e5) {
            e5.printStackTrace();
        }
        this.lblDataConceptoDetalleMov.setText(movimientoFondosBean.getConcepto());
        this.lblDataCertificadoDetalleMov.setText(movimientoFondosBean.getCertificado());
        try {
            this.lblDataCertificadoDetalleMov.setContentDescription(new CAccessibility(getContext()).applyFilterCharacterToCharacter(movimientoFondosBean.getCertificado()));
        } catch (Exception e6) {
            e6.printStackTrace();
        }
    }

    public void attachView() {
        int id2 = this.mControlPager.getCurrentView().getId();
        if (id2 != R.id.layout_movimientos_fondo) {
            switch (id2) {
                case R.id.layout_detalle_fondo /*2131364919*/:
                    if (!this.r.isViewAttached()) {
                        this.r.attachView(this);
                        return;
                    }
                    return;
                case R.id.layout_detalle_movimiento_fondo /*2131364920*/:
                    if (!this.t.isViewAttached()) {
                        this.t.attachView(this);
                        return;
                    }
                    return;
                default:
                    return;
            }
        } else if (!this.s.isViewAttached()) {
            this.s.attachView(this);
        }
    }

    public void detachView() {
        if (this.r.isViewAttached()) {
            this.r.detachView();
        }
        if (this.s.isViewAttached()) {
            this.s.detachView();
        }
        if (this.t.isViewAttached()) {
            this.t.detachView();
        }
    }

    public void onBackPressed() {
        int id2 = this.mControlPager.getCurrentView().getId();
        if (id2 != R.id.layout_movimientos_fondo) {
            switch (id2) {
                case R.id.layout_detalle_fondo /*2131364919*/:
                    backButtonDetalleFondo();
                    return;
                case R.id.layout_detalle_movimiento_fondo /*2131364920*/:
                    gotoPage(1, false);
                    setActionBarMovimiento();
                    return;
                default:
                    return;
            }
        } else if (this.u.equalsIgnoreCase("DETALLE_FONDO")) {
            gotoPage(0, false);
            setActionBarDetalle();
        } else if (this.u.equalsIgnoreCase(FondosConstants.ORIGEN_ULTIMOS_MOVIMIENTOS)) {
            super.onBackPressed();
            hideKeyboard();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (activityResultHandler(i2, intent)) {
            return;
        }
        if (i != 2) {
            switch (i) {
                case 4:
                case 5:
                case 6:
                    if (i2 == -1 && intent != null) {
                        setResult(-1, intent);
                        finish();
                        return;
                    }
                    return;
                default:
                    return;
            }
        } else if (i2 == -1) {
            gotoMovimiento(intent.getParcelableArrayListExtra(FondosConstants.INTENT_EXTRA_LST_MOVIMIENTOS), FondosConstants.ORIGEN_BUSQUEDA_AVANZADA);
        }
    }

    public void onDestroy() {
        detachView();
        super.onDestroy();
    }
}
