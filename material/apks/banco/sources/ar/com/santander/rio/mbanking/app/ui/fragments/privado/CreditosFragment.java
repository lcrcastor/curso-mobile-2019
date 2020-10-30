package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.AccessibilityDelegate;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.commons.CConsDescripciones;
import ar.com.santander.rio.mbanking.app.commons.CCreditos;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccountAcc;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAmountAcc;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CCFTNALabelACC;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CCFTNA_IVALabelACC;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CDateAcc;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CFiltersAccessibility;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CGeneralAcc;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CTasaValueAcc;
import ar.com.santander.rio.mbanking.app.module.creditos.ComprobantePresenter;
import ar.com.santander.rio.mbanking.app.module.creditos.ComprobantePresenterImp;
import ar.com.santander.rio.mbanking.app.module.creditos.ConfirmacionPresenter;
import ar.com.santander.rio.mbanking.app.module.creditos.ConfirmacionPresenterImp;
import ar.com.santander.rio.mbanking.app.module.creditos.CreditosView;
import ar.com.santander.rio.mbanking.app.module.creditos.InicioCreditoPresenter;
import ar.com.santander.rio.mbanking.app.module.creditos.InicioCreditoPresenterImp;
import ar.com.santander.rio.mbanking.app.module.creditos.SimulacionPresenter;
import ar.com.santander.rio.mbanking.app.module.creditos.SimulacionPresenterImp;
import ar.com.santander.rio.mbanking.app.module.creditos.model.Inicio;
import ar.com.santander.rio.mbanking.app.module.creditos.model.Resultado;
import ar.com.santander.rio.mbanking.app.module.creditos.model.Simulacion;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.activities.ContratarSeguroAccidenteActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.adapters.CreditosAdapter;
import ar.com.santander.rio.mbanking.app.ui.constants.PagoTarjetasConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.EmptyFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDatePickerDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDatePickerDialogFragment.IDateDialogListener;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListenerExtended;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListenerThreeOptions;
import ar.com.santander.rio.mbanking.components.itrsa.AdapterView.OnItemClickedListener;
import ar.com.santander.rio.mbanking.components.itrsa.AdapterView.OnItemClickedListener.OneItemClicked;
import ar.com.santander.rio.mbanking.components.itrsa.OneClickListener;
import ar.com.santander.rio.mbanking.components.itrsa.OneClickListener.OneClicked;
import ar.com.santander.rio.mbanking.components.share.OptionsToShare;
import ar.com.santander.rio.mbanking.components.share.OptionsToShareImpl;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.GetCotizacionSeguroAccidenteEvent;
import ar.com.santander.rio.mbanking.services.events.SolicitudPrestamoPreacordadoEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.model.creditos.DatosPrestamoPermitido;
import ar.com.santander.rio.mbanking.services.model.creditos.DatosSolicitudPrestamo;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.model.general.Cuentas;
import ar.com.santander.rio.mbanking.services.soap.beans.SolicitudPrestamoPreacordadoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AccountRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaPrestamosPermitidosBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaSolicitudCrediticiaBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SolicitudPrestamoPreacordadoBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SolicitudPrestamoPreacordadoBodyResponseBean;
import ar.com.santander.rio.mbanking.utils.ImageSurfaceView;
import ar.com.santander.rio.mbanking.utils.UtilAccount;
import ar.com.santander.rio.mbanking.utils.UtilFile;
import ar.com.santander.rio.mbanking.view.AmountView;
import ar.com.santander.rio.mbanking.view.SlideAnimationViewFlipper;
import ar.com.santander.rio.mbanking.view.tables.RowTwoColumnViewStyled;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.squareup.otto.Subscribe;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;

public class CreditosFragment extends BaseFragment implements OnClickListener, CreditosView, IDialogListenerExtended, IDialogListenerThreeOptions, OneItemClicked, OneClicked {
    public static String CUOTAS_DIALOG = "cuotas_dialog";
    public static String DESTINO_DIALOG = "destino_dialog";
    public static String LIQUIDACION_WS = "L";
    public static final String MOSTRAR_LINK = "S";
    public static final String OCULTAR_LINK = "N";
    public static String PRIMERA_CUOTA_DIALOG = "primera_cuota_dialog";
    public static final String RESPONSE_CONSULTA_PRESTAMOS = "RESPONSE_CONSULTA_PRESTAMOS";
    public static String RESPONSE_DATOS_CUENTA = "RESPONSE_DATOS_CUENTA";
    public static final String RESPONSE_SOLICITUD_CREDITICIA = "RESPONSE_SOLICITUD_CREDITICIA";
    public static String SIMULACION_WS = "S";
    @Inject
    IDataManager a;
    /* access modifiers changed from: private */
    public boolean ad = false;
    private boolean ae = false;
    /* access modifiers changed from: private */
    public boolean af = false;
    private Inicio ag;
    private Simulacion ah;
    private Resultado ai;
    private Resultado aj;
    private SantanderRioMainActivity ak;
    private boolean al = false;
    /* access modifiers changed from: private */
    public OptionsToShare am;
    /* access modifiers changed from: private */
    public boolean an = false;
    private List<Cuenta> ao;
    private Cuenta ap;
    private String aq = "";

    /* renamed from: ar reason: collision with root package name */
    private String f238ar = "";
    private ConsultaSolicitudCrediticiaBodyResponseBean as;
    private SolicitudPrestamoPreacordadoBodyRequestBean at;
    /* access modifiers changed from: private */
    public SolicitudPrestamoPreacordadoBodyResponseBean au;
    private IDialogListenerThreeOptions av;
    private AccountRequestBean aw;
    /* access modifiers changed from: private */
    public ConsultaPrestamosPermitidosBodyResponseBean ax;
    @Inject
    SessionManager b;
    @Inject
    AnalyticsManager c;
    @InjectView(2131364550)
    public Button confirmar;
    @InjectView(2131364551)
    public Button continuar;
    @InjectView(2131364752)
    public LinearLayout creditosView;
    /* access modifiers changed from: private */
    public Resultado d = null;
    private ViewGroup e;
    private InicioCreditoPresenter f;
    /* access modifiers changed from: private */
    public SimulacionPresenter g;
    /* access modifiers changed from: private */
    public ConfirmacionPresenter h;
    private ComprobantePresenter i;
    @InjectView(2131364552)
    public Button irCuentas;
    @InjectView(2131364546)
    public TextView leyendaPrestamoUvaComprobante;
    @InjectView(2131364545)
    public TextView leyendaPrestamoUvaConfirmar;
    @InjectView(2131365106)
    public ListView listCreditos;
    @InjectView(2131366365)
    public ViewFlipper mControlPager;
    @InjectView(2131365300)
    View pantallaComprobante;
    @InjectView(2131365301)
    View pantallaConfirmacion;
    @InjectView(2131365304)
    View pantallaInicial;
    @InjectView(2131365305)
    View pantallaSimulacion;
    @InjectView(2131366399)
    public ViewGroup vgWrapperSimulacion;
    @InjectView(2131366404)
    View viewComprobanteCreditos;

    public void gotoPage(int i2) {
    }

    public void onNegativeButton(String str) {
    }

    public void onPositiveButton(String str) {
    }

    public void onSimpleActionButton(String str) {
    }

    public void setModalInPageAnimation() {
    }

    public void setModalOutPageAnimation() {
    }

    public void setNextPageAnimation() {
    }

    public void setPreviusPageAnimation() {
    }

    @Nullable
    public /* bridge */ /* synthetic */ Activity getActivity() {
        return super.getActivity();
    }

    public void setConsultaSolicitudCrediticia(ConsultaSolicitudCrediticiaBodyResponseBean consultaSolicitudCrediticiaBodyResponseBean) {
        this.as = consultaSolicitudCrediticiaBodyResponseBean;
    }

    public void setConsultaPrestamosPermitidos(ConsultaPrestamosPermitidosBodyResponseBean consultaPrestamosPermitidosBodyResponseBean) {
        this.ax = consultaPrestamosPermitidosBodyResponseBean;
    }

    public void setDatosCuentaRequest(AccountRequestBean accountRequestBean) {
        this.aw = accountRequestBean;
    }

    public static CreditosFragment newInstance(ConsultaSolicitudCrediticiaBodyResponseBean consultaSolicitudCrediticiaBodyResponseBean, AccountRequestBean accountRequestBean, ConsultaPrestamosPermitidosBodyResponseBean consultaPrestamosPermitidosBodyResponseBean) {
        CreditosFragment creditosFragment = new CreditosFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(RESPONSE_SOLICITUD_CREDITICIA, consultaSolicitudCrediticiaBodyResponseBean);
        bundle.putParcelable(RESPONSE_DATOS_CUENTA, accountRequestBean);
        bundle.putParcelable(RESPONSE_CONSULTA_PRESTAMOS, consultaPrestamosPermitidosBodyResponseBean);
        creditosFragment.setArguments(bundle);
        return creditosFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setErrorListener(C());
        this.f = new InicioCreditoPresenterImp(this);
        this.g = new SimulacionPresenterImp(this);
        this.h = new ConfirmacionPresenterImp(this);
        this.i = new ComprobantePresenterImp(this);
        if (getArguments() != null) {
            this.as = (ConsultaSolicitudCrediticiaBodyResponseBean) getArguments().getParcelable(RESPONSE_SOLICITUD_CREDITICIA);
            this.aw = (AccountRequestBean) getArguments().getParcelable(RESPONSE_DATOS_CUENTA);
            this.ax = (ConsultaPrestamosPermitidosBodyResponseBean) getArguments().getParcelable(RESPONSE_CONSULTA_PRESTAMOS);
        }
        this.g.setCuentaSelected(this.aw);
    }

    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.creditos_fragment, viewGroup, false);
        ButterKnife.inject((Object) this, inflate);
        this.creditosView.setVisibility(0);
        this.f.onCreatePage(this.as, this.aw, this.ax);
        this.continuar.setOnClickListener(new OneClickListener(this));
        this.irCuentas.setOnClickListener(new OneClickListener(this));
        this.creditosView.setVisibility(0);
        this.c.trackScreen(getString(R.string.analytics_screen_name_creditos_home));
        B();
        return inflate;
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.f.getResponseConsultaPrestamosPermitidos(this.ax);
    }

    private void B() {
        if (this.b != null && this.b.getLoginUnico() != null && this.b.getLoginUnico().getProductos() != null && this.b.getLoginUnico().getProductos().getCuentas() != null) {
            Cuentas cuentas = this.b.getLoginUnico().getProductos().getCuentas();
            if (cuentas.getCuentas() != null && cuentas.getCuentas().size() > 0) {
                this.ao = cuentas.getCuentas();
            }
        }
    }

    private SantanderRioMainActivity C() {
        return (SantanderRioMainActivity) getActivity();
    }

    public void onBackPressed() {
        FragmentManager supportFragmentManager = getActivity().getSupportFragmentManager();
        if (supportFragmentManager != null) {
            Fragment findFragmentByTag = supportFragmentManager.findFragmentByTag(EmptyFragment.TITLE_TENENCIA);
            if (findFragmentByTag != null && findFragmentByTag.isVisible()) {
                ((SantanderRioMainActivity) getActivity()).changeFragment(new TenenciaCreditosFragment());
                return;
            }
        }
        if (this.mControlPager.getDisplayedChild() > 1) {
            if (this.mControlPager.getDisplayedChild() != 3) {
                previousPage();
            } else if (!this.an) {
                F();
            } else {
                switchDrawer();
            }
        } else if (this.mControlPager.getDisplayedChild() == 1) {
            goToInicio();
        } else if (this.mControlPager.getDisplayedChild() == 0) {
            ((SantanderRioMainActivity) getActivity()).changeFragment(new TenenciaCreditosFragment());
        }
        this.c.trackScreen(getString(R.string.analytics_screen_name_creditos_home));
    }

    /* access modifiers changed from: 0000 */
    public void y() {
        this.g.getDestinoSelected();
        this.c.trackScreen(getString(R.string.analytics_screen_name_creditos_simulacion));
        this.g.validate(((EditText) this.vgWrapperSimulacion.findViewById(R.id.input_amount_id)).getText().toString());
    }

    /* access modifiers changed from: 0000 */
    public void z() {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.MSG_USER0000XX_CONFIRMACION), getString(R.string.MSG_USER000061), null, null, getString(R.string.IDX_ALERT_BTN_YES), getString(R.string.IDX_ALERT_BTN_NO), null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                CreditosFragment.this.c.trackScreen(CreditosFragment.this.getString(R.string.analytics_screen_name_creditos_comprobante));
                CreditosFragment.this.h.sendRequestConfirmacionPrestamoPermitido();
            }
        });
        newInstance.show(getFragmentManager(), "DialogConfirm");
    }

    /* access modifiers changed from: 0000 */
    public void A() {
        if (!this.an) {
            this.af = true;
            if (this.am == null) {
                I();
            }
            this.am.showAlert();
            return;
        }
        ((SantanderRioMainActivity) getActivity()).goToOption(FragmentConstants.CUENTAS);
    }

    public void onStop() {
        super.onStop();
    }

    public void setTitleLayout(String str) {
        TextView textView = (TextView) this.mControlPager.getCurrentView().findViewById(R.id.vTitle);
        if (textView != null) {
            textView.setText(str);
            if (str.equalsIgnoreCase(getString(R.string.ID155_CREDITS_VIEW_LBL_RECEIPT))) {
                textView.setContentDescription(getResources().getString(R.string.ID155_CREDITS_VIEW_LBL_RECEIPT_DECRIPTION));
            }
        }
    }

    public void setTitleContentDescription(String str) {
        TextView textView = (TextView) this.mControlPager.getCurrentView().findViewById(R.id.vTitle);
        if (textView != null && !TextUtils.isEmpty(str)) {
            textView.setContentDescription(str);
        }
    }

    public Context getActContext() {
        return getActivity();
    }

    public void setInicioView(Inicio inicio) {
        this.an = false;
        this.ag = inicio;
        enableBackButton();
        inicio.getTasas();
        if (!this.al) {
            this.e = (ViewGroup) getActivity().getLayoutInflater().inflate(R.layout.creditos_header, this.listCreditos, false);
            this.listCreditos.addHeaderView(this.e, null, true);
            this.al = true;
        }
        ((TextView) this.e.findViewById(R.id.idImporteMaximoTV)).setText(Html.fromHtml(getString(R.string.ID121_CREDITS_MAIN_LBL_MAXIMPORT)));
        TextView textView = (TextView) this.e.findViewById(R.id.vTitle);
        if (textView != null) {
            textView.setText(getResources().getString(R.string.ID138_CREDITS_LENDCONFIRM_LBL_SUPERLEND));
        }
        AmountView amountView = (AmountView) this.e.findViewById(R.id.f55creditoimporteMaximo);
        amountView.setColorAmount(true);
        amountView.setCElementAcc(new CAmountAcc());
        amountView.setAmount(inicio.getImporteMaximoPesos());
        AmountView amountView2 = (AmountView) this.e.findViewById(R.id.f56creditoimporteMinimo);
        amountView2.setCElementAcc(new CAmountAcc());
        amountView2.setAmount(inicio.getImporteMinimoPesos());
        AmountView amountView3 = (AmountView) this.e.findViewById(R.id.f54creditocuotaMaxima);
        amountView3.setCElementAcc(new CAmountAcc());
        amountView3.setAmount(inicio.getImporteMaximoCuotas());
        this.listCreditos.setAdapter(new CreditosAdapter(getActivity(), 0, inicio.getTasas()));
        this.listCreditos.setOnItemClickListener(new OnItemClickedListener(this));
    }

    public void setSimulacionView(Simulacion simulacion) {
        this.ah = simulacion;
        addBlockBodySimulacion(this.g.getViewForm(simulacion));
        TextView textView = (TextView) this.pantallaSimulacion.findViewById(R.id.f57creditopage2leyenda);
        textView.setText(Html.fromHtml(simulacion.getLeyenda()));
        try {
            textView.setContentDescription(CAccessibility.getInstance(getActContext()).applyFilter_CABA(CAccessibility.getInstance(getActContext()).applyFilterCUIT(textView.getText().toString())));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        TextView textView2 = (TextView) this.pantallaSimulacion.findViewById(R.id.f58creditopage2leyenda_tasa);
        if (simulacion.getLeyendaTasa() == null || simulacion.getLeyendaTasa().isEmpty()) {
            textView2.setVisibility(8);
        } else {
            textView2.setText(simulacion.getLeyendaTasa());
        }
        try {
            textView2.setContentDescription(CAccessibility.getInstance(getActContext()).applyCFTEA(CAccessibility.getInstance(getActContext()).applyFilterAcronymTasas(CAccessibility.getInstance(getActContext()).applyCFTEA_IVA_3(simulacion.getLeyendaTasa()))));
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    public void setConfirmacionView(Resultado resultado) {
        String str;
        this.ai = resultado;
        AnonymousClass2 r0 = new AccessibilityDelegate() {
            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.setText(view.getContentDescription());
            }
        };
        CFiltersAccessibility cFiltersAccessibility = new CFiltersAccessibility(getContext());
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f72creditopage3importe)).setmCElementAccValue(new CAmountAcc());
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f72creditopage3importe)).setContent(resultado.getImporteSolicitud());
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f75creditopage3importeNeto)).setmCElementAccValue(new CAmountAcc());
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f75creditopage3importeNeto)).setContent(resultado.getImporteNeto());
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f83creditopage3tipoTasa)).setContent(resultado.getTipoTasa());
        if (resultado.getTasaEfectAnual() == null || resultado.getTasaEfectAnual().isEmpty()) {
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f81creditopage3tasaEfectAnual)).setVisibility(8);
        } else {
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f81creditopage3tasaEfectAnual)).setmCElementAccValue(new CTasaValueAcc());
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f81creditopage3tasaEfectAnual)).setContent(resultado.getTasaEfectAnual());
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f81creditopage3tasaEfectAnual)).getLabelView().setContentDescription(getString(R.string.TASA_EFECTIVA_ANUAL_DESCRIPTION));
            try {
                String applyFilterAcronymTasas = CAccessibility.getInstance(getActContext()).applyFilterAcronymTasas(((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f81creditopage3tasaEfectAnual)).getLabel());
                String applyFilterTasaValue = CAccessibility.getInstance(getActContext()).applyFilterTasaValue(resultado.getTasaEfectAnual());
                RowTwoColumnViewStyled rowTwoColumnViewStyled = (RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f81creditopage3tasaEfectAnual);
                StringBuilder sb = new StringBuilder();
                sb.append(applyFilterAcronymTasas);
                sb.append(", ");
                sb.append(applyFilterTasaValue);
                rowTwoColumnViewStyled.setContentDescription(sb.toString());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        if (resultado.getTasaCFTNA() == null || resultado.getTasaCFTNA().isEmpty()) {
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f59creditopage3CFTNA)).setVisibility(8);
        } else {
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f59creditopage3CFTNA)).setmCElementAccLabel(new CCFTNALabelACC());
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f59creditopage3CFTNA)).setmCElementAccValue(new CTasaValueAcc());
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f59creditopage3CFTNA)).setContent(resultado.getTasaCFTNA());
            try {
                String applyCFTEA = CAccessibility.getInstance(getActContext()).applyCFTEA(((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f59creditopage3CFTNA)).getLabel());
                String applyFilterTasaValue2 = CAccessibility.getInstance(getActContext()).applyFilterTasaValue(resultado.getTasaCFTNA());
                RowTwoColumnViewStyled rowTwoColumnViewStyled2 = (RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f59creditopage3CFTNA);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(applyCFTEA);
                sb2.append(", ");
                sb2.append(applyFilterTasaValue2);
                rowTwoColumnViewStyled2.setContentDescription(sb2.toString());
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        if (resultado.getTasaCFTNAIVA() == null || resultado.getTasaCFTNAIVA().isEmpty()) {
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f60creditopage3CFTNA_IVA)).setVisibility(8);
        } else {
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f60creditopage3CFTNA_IVA)).setmCElementAccLabel(new CCFTNA_IVALabelACC());
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f60creditopage3CFTNA_IVA)).setmCElementAccValue(new CTasaValueAcc());
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f60creditopage3CFTNA_IVA)).setContent(resultado.getTasaCFTNAIVA());
            try {
                String applyCFTEA_IVA_3 = CAccessibility.getInstance(getActContext()).applyCFTEA_IVA_3(((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f60creditopage3CFTNA_IVA)).getLabel());
                String applyFilterTasaValue3 = CAccessibility.getInstance(getActContext()).applyFilterTasaValue(resultado.getTasaCFTNAIVA());
                RowTwoColumnViewStyled rowTwoColumnViewStyled3 = (RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f60creditopage3CFTNA_IVA);
                StringBuilder sb3 = new StringBuilder();
                sb3.append(applyCFTEA_IVA_3);
                sb3.append(", ");
                sb3.append(applyFilterTasaValue3);
                rowTwoColumnViewStyled3.setContentDescription(sb3.toString());
            } catch (Exception e4) {
                e4.printStackTrace();
            }
        }
        if (resultado.getTasaNominalAnual() == null || resultado.getTasaNominalAnual().isEmpty()) {
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f82creditopage3tasaNominal)).setVisibility(8);
        } else {
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f82creditopage3tasaNominal)).setmCElementAccValue(new CTasaValueAcc());
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f82creditopage3tasaNominal)).setContent(resultado.getTasaNominalAnual());
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f82creditopage3tasaNominal)).getLabelView().setContentDescription(getString(R.string.TASA_NOMINAL_ANUAL_DESCRIPTION));
            try {
                String applyFilterAcronymTasas2 = CAccessibility.getInstance(getActContext()).applyFilterAcronymTasas(((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f82creditopage3tasaNominal)).getLabel());
                String applyFilterTasaValue4 = CAccessibility.getInstance(getActContext()).applyFilterTasaValue(resultado.getTasaNominalAnual());
                RowTwoColumnViewStyled rowTwoColumnViewStyled4 = (RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f82creditopage3tasaNominal);
                StringBuilder sb4 = new StringBuilder();
                sb4.append(applyFilterAcronymTasas2);
                sb4.append(", ");
                sb4.append(applyFilterTasaValue4);
                rowTwoColumnViewStyled4.setContentDescription(sb4.toString());
            } catch (Exception e5) {
                e5.printStackTrace();
            }
        }
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f66creditopage3cuentaDestino)).setmCElementAccValue(new CAccountAcc());
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f66creditopage3cuentaDestino)).setContent(resultado.getCuentaDestino());
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f62creditopage3cantidadCuotas)).setContent(resultado.getCantidadCuotas());
        RowTwoColumnViewStyled rowTwoColumnViewStyled5 = (RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f69creditopage3destino);
        rowTwoColumnViewStyled5.setmCElementAccValue(new CGeneralAcc());
        rowTwoColumnViewStyled5.setContent(this.g.getDestinoSelected());
        rowTwoColumnViewStyled5.setContentDescription(b(this.g.getDestinoSelected()));
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f71creditopage3fechaPrimerVencimiento)).setmCElementAccValue(new CDateAcc());
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f71creditopage3fechaPrimerVencimiento)).setContent(resultado.getFechaPrimerVencimiento());
        if (resultado.getCapitalIntereses() != null) {
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f63creditopage3capitalIntereses)).setVisibility(0);
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f63creditopage3capitalIntereses)).setmCElementAccValue(new CAmountAcc());
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f63creditopage3capitalIntereses)).setContent(resultado.getCapitalIntereses());
        } else {
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f63creditopage3capitalIntereses)).setVisibility(8);
        }
        if (resultado.getCargoSeguro() != null) {
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f64creditopage3cargoSeguro)).setmCElementAccValue(new CAmountAcc());
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f64creditopage3cargoSeguro)).setContent(resultado.getCargoSeguro());
        } else {
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f64creditopage3cargoSeguro)).setVisibility(8);
        }
        if (resultado.getIva() != null) {
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f76creditopage3iva)).setVisibility(0);
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f76creditopage3iva)).setmCElementAccValue(new CAmountAcc());
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f76creditopage3iva)).setContent(resultado.getIva());
            try {
                String applyFilterAmount = CAccessibility.getInstance(getActContext()).applyFilterAmount(resultado.getIva());
                RowTwoColumnViewStyled rowTwoColumnViewStyled6 = (RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f76creditopage3iva);
                StringBuilder sb5 = new StringBuilder();
                sb5.append("iba, ");
                sb5.append(applyFilterAmount);
                rowTwoColumnViewStyled6.setContentDescription(sb5.toString());
            } catch (Exception e6) {
                e6.printStackTrace();
            }
        } else {
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f76creditopage3iva)).setVisibility(8);
        }
        if (resultado.getOtrosImpuestos() != null) {
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f80creditopage3otrosImpuestos)).setVisibility(0);
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f80creditopage3otrosImpuestos)).setmCElementAccValue(new CAmountAcc());
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f80creditopage3otrosImpuestos)).setContent(resultado.getOtrosImpuestos());
        } else {
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f80creditopage3otrosImpuestos)).setVisibility(8);
        }
        if (resultado.getImporte() != null) {
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f74creditopage3importeCuota)).setVisibility(0);
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f74creditopage3importeCuota)).setmCElementAccValue(new CAmountAcc());
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f74creditopage3importeCuota)).setContent(resultado.getImporte());
        } else {
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f74creditopage3importeCuota)).setVisibility(8);
        }
        if (resultado.getImporteSolUVA() != null) {
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f73creditopage3importeASolicitarEnUVAs)).setmCElementAccValue(new CAmountAcc());
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f73creditopage3importeASolicitarEnUVAs)).setContent(resultado.getImporteSolUVA());
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f73creditopage3importeASolicitarEnUVAs)).setVisibility(0);
            this.pantallaConfirmacion.findViewById(R.id.separatorSectionTitleDatosSiguienteCuota).setVisibility(0);
            this.pantallaConfirmacion.findViewById(R.id.separatorSectionTitleDatosSiguienteCuota).setVisibility(0);
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f61creditopage3DatosSiguienteCuota)).setVisibility(0);
        } else {
            this.pantallaConfirmacion.findViewById(R.id.separatorSectionTitleDatosSiguienteCuota).setVisibility(8);
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f61creditopage3DatosSiguienteCuota)).setVisibility(8);
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f73creditopage3importeASolicitarEnUVAs)).setVisibility(8);
        }
        if (resultado.getImporteCuotaUVA() != null) {
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f68creditopage3cuotaPuraUVA)).setmCElementAccValue(new CAmountAcc());
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f68creditopage3cuotaPuraUVA)).setContent(resultado.getImporteCuotaUVA());
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f68creditopage3cuotaPuraUVA)).setVisibility(0);
        } else {
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f68creditopage3cuotaPuraUVA)).setVisibility(8);
        }
        if (resultado.getIndicadorUVA() != null) {
            Spanned fromHtml = Html.fromHtml(CCreditos.getLeyenda(this.ax.getListaLeyenda(), resultado.getIndicadorUVA().equalsIgnoreCase("S") ? Constants.LEYENDA_PRE_UVA : Constants.LEYENDA_PRE_TRAD));
            this.leyendaPrestamoUvaConfirmar.setText(fromHtml);
            if (fromHtml != null) {
                try {
                    str = fromHtml.toString();
                } catch (Exception e7) {
                    e7.printStackTrace();
                }
            } else {
                str = null;
            }
            this.leyendaPrestamoUvaConfirmar.setContentDescription(cFiltersAccessibility.filterStringComun(cFiltersAccessibility.filter_OY(str)));
            this.leyendaPrestamoUvaConfirmar.setAccessibilityDelegate(r0);
        } else {
            this.leyendaPrestamoUvaConfirmar.setVisibility(8);
        }
        if (resultado.getCuotaPuraUVA() != null) {
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f67creditopage3cuotaPuraEnUVAS)).setmCElementAccValue(new CAmountAcc());
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f67creditopage3cuotaPuraEnUVAS)).setContent(resultado.getCuotaPuraUVA());
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f67creditopage3cuotaPuraEnUVAS)).setVisibility(0);
            TextView textView = (TextView) this.pantallaConfirmacion.findViewById(R.id.f78creditopage3leyenda_cuota_pura_uvas);
            if (resultado.getIndicadorUVA() == null || !resultado.getIndicadorUVA().equals("S")) {
                ((TextView) this.pantallaConfirmacion.findViewById(R.id.f78creditopage3leyenda_cuota_pura_uvas)).setVisibility(8);
            } else {
                ((TextView) this.pantallaConfirmacion.findViewById(R.id.f78creditopage3leyenda_cuota_pura_uvas)).setVisibility(0);
                textView.setText(Html.fromHtml(CCreditos.getLeyenda(this.ax.getListaLeyenda(), Constants.LEYENDA_CUOTA_PURA_UVAS)));
            }
        } else {
            ((TextView) this.pantallaConfirmacion.findViewById(R.id.f78creditopage3leyenda_cuota_pura_uvas)).setVisibility(8);
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f67creditopage3cuotaPuraEnUVAS)).setVisibility(8);
        }
        if (resultado.getCotizacionUVA() != null) {
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f65creditopage3cotizacionDeLaUVA)).setmCElementAccValue(new CAmountAcc());
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f65creditopage3cotizacionDeLaUVA)).setContent(resultado.getCotizacionUVA());
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f65creditopage3cotizacionDeLaUVA)).setVisibility(0);
        } else {
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f65creditopage3cotizacionDeLaUVA)).setVisibility(8);
        }
        if (resultado.getFechaCotizaUVA() != null) {
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f70creditopage3fechaDeCotizacionDeLaUVA)).setmCElementAccValue(new CDateAcc());
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f70creditopage3fechaDeCotizacionDeLaUVA)).setContent(resultado.getFechaCotizaUVA());
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f70creditopage3fechaDeCotizacionDeLaUVA)).setVisibility(0);
        } else {
            ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f70creditopage3fechaDeCotizacionDeLaUVA)).setVisibility(8);
        }
        this.confirmar.setOnClickListener(new OneClickListener(this));
        this.confirmar.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_rojo));
        TextView textView2 = (TextView) this.pantallaConfirmacion.findViewById(R.id.f77creditopage3leyenda);
        if (resultado.getLeyenda() == null || resultado.getLeyenda().isEmpty()) {
            textView2.setVisibility(8);
        } else {
            textView2.setText(Html.fromHtml(resultado.getLeyenda()));
        }
        try {
            textView2.setContentDescription(CAccessibility.getInstance(getActContext()).applyFilterGeneral(textView2.getText().toString()));
        } catch (Exception e8) {
            e8.printStackTrace();
        }
        TextView textView3 = (TextView) this.pantallaConfirmacion.findViewById(R.id.f79creditopage3leyenda_tasa);
        if (resultado.getLeyendaTasa() == null || resultado.getLeyendaTasa().isEmpty()) {
            textView3.setVisibility(8);
        } else {
            textView3.setText(resultado.getLeyendaTasa());
        }
        try {
            textView3.setContentDescription(CAccessibility.getInstance(getActContext()).applyCFTEA(CAccessibility.getInstance(getActContext()).applyFilterAcronymTasas(CAccessibility.getInstance(getActContext()).applyCFTEA_IVA_3(resultado.getLeyendaTasa()))));
        } catch (Exception e9) {
            e9.printStackTrace();
        }
    }

    public void setComprobacionView(Resultado resultado) {
        String str;
        this.aj = resultado;
        ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f95creditopage4importe)).setmCElementAccValue(new CAmountAcc());
        ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f95creditopage4importe)).setContent(resultado.getImporteSolicitud());
        if (resultado.getTipoTasa() != null) {
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f109creditopage4tipoTasa)).setContent(resultado.getTipoTasa());
        }
        if (resultado.getImporteSolUVA() != null) {
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f96creditopage4importeASolicitarEnUVAs)).setmCElementAccValue(new CAmountAcc());
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f96creditopage4importeASolicitarEnUVAs)).setContent(resultado.getImporteSolUVA());
            this.pantallaComprobante.findViewById(R.id.separatorSectionTitleDatosSiguienteCuotaComprobante).setVisibility(0);
            this.pantallaComprobante.findViewById(R.id.separatorSectionTitleDatosSiguienteCuotaComprobante).setVisibility(0);
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f85creditopage4DatosSiguienteCuota)).setVisibility(0);
        } else {
            this.pantallaComprobante.findViewById(R.id.separatorSectionTitleDatosSiguienteCuotaComprobante).setVisibility(8);
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f85creditopage4DatosSiguienteCuota)).setVisibility(8);
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f96creditopage4importeASolicitarEnUVAs)).setVisibility(8);
        }
        ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f98creditopage4importeNeto)).setmCElementAccValue(new CAmountAcc());
        ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f98creditopage4importeNeto)).setContent(resultado.getImporteNeto());
        if (resultado.getTasaEfectAnual() == null || resultado.getTasaEfectAnual().isEmpty()) {
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f107creditopage4tasaEfectAnual)).setVisibility(8);
        } else {
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f107creditopage4tasaEfectAnual)).getLabelView().setContentDescription(getString(R.string.TASA_EFECTIVA_ANUAL_DESCRIPTION));
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f107creditopage4tasaEfectAnual)).setmCElementAccValue(new CTasaValueAcc());
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f107creditopage4tasaEfectAnual)).setContent(resultado.getTasaEfectAnual());
            try {
                String applyFilterAcronymTasas = CAccessibility.getInstance(getActContext()).applyFilterAcronymTasas(((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f107creditopage4tasaEfectAnual)).getLabel());
                String applyFilterTasaValue = CAccessibility.getInstance(getActContext()).applyFilterTasaValue(resultado.getTasaEfectAnual());
                RowTwoColumnViewStyled rowTwoColumnViewStyled = (RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f107creditopage4tasaEfectAnual);
                StringBuilder sb = new StringBuilder();
                sb.append(applyFilterAcronymTasas);
                sb.append(", ");
                sb.append(applyFilterTasaValue);
                rowTwoColumnViewStyled.setContentDescription(sb.toString());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        if (resultado.getTasaCFTNA() == null || resultado.getTasaCFTNA().isEmpty()) {
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f105creditopage4tasaCFTNA)).setVisibility(8);
        } else {
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f105creditopage4tasaCFTNA)).setmCElementAccValue(new CTasaValueAcc());
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f105creditopage4tasaCFTNA)).setmCElementAccLabel(new CCFTNALabelACC());
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f105creditopage4tasaCFTNA)).setContent(resultado.getTasaCFTNA());
            try {
                String applyCFTEA = CAccessibility.getInstance(getActContext()).applyCFTEA(((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f105creditopage4tasaCFTNA)).getLabel());
                String applyFilterTasaValue2 = CAccessibility.getInstance(getActContext()).applyFilterTasaValue(resultado.getTasaCFTNA());
                RowTwoColumnViewStyled rowTwoColumnViewStyled2 = (RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f105creditopage4tasaCFTNA);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(applyCFTEA);
                sb2.append(", ");
                sb2.append(applyFilterTasaValue2);
                rowTwoColumnViewStyled2.setContentDescription(sb2.toString());
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        if (resultado.getTasaCFTNAIVA() == null || resultado.getTasaCFTNAIVA().isEmpty()) {
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f106creditopage4tasaCFTNAIVA)).setVisibility(8);
        } else {
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f106creditopage4tasaCFTNAIVA)).setmCElementAccValue(new CTasaValueAcc());
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f106creditopage4tasaCFTNAIVA)).setmCElementAccLabel(new CCFTNA_IVALabelACC());
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f106creditopage4tasaCFTNAIVA)).setContent(resultado.getTasaCFTNAIVA());
            try {
                String applyCFTEA_IVA_3 = CAccessibility.getInstance(getActContext()).applyCFTEA_IVA_3(((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f106creditopage4tasaCFTNAIVA)).getLabel());
                String applyFilterTasaValue3 = CAccessibility.getInstance(getActContext()).applyFilterTasaValue(resultado.getTasaCFTNAIVA());
                RowTwoColumnViewStyled rowTwoColumnViewStyled3 = (RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f106creditopage4tasaCFTNAIVA);
                StringBuilder sb3 = new StringBuilder();
                sb3.append(applyCFTEA_IVA_3);
                sb3.append(", ");
                sb3.append(applyFilterTasaValue3);
                rowTwoColumnViewStyled3.setContentDescription(sb3.toString());
            } catch (Exception e4) {
                e4.printStackTrace();
            }
        }
        if (resultado.getTasaNominalAnual() == null || resultado.getTasaNominalAnual().isEmpty()) {
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f108creditopage4tasaNominal)).setVisibility(8);
        } else {
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f108creditopage4tasaNominal)).getLabelView().setContentDescription(getString(R.string.TASA_NOMINAL_ANUAL_DESCRIPTION));
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f108creditopage4tasaNominal)).setmCElementAccValue(new CTasaValueAcc());
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f108creditopage4tasaNominal)).setContent(resultado.getTasaNominalAnual());
            try {
                String applyFilterAcronymTasas2 = CAccessibility.getInstance(getActContext()).applyFilterAcronymTasas(((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f108creditopage4tasaNominal)).getLabel());
                String applyFilterTasaValue4 = CAccessibility.getInstance(getActContext()).applyFilterTasaValue(resultado.getTasaNominalAnual());
                RowTwoColumnViewStyled rowTwoColumnViewStyled4 = (RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f108creditopage4tasaNominal);
                StringBuilder sb4 = new StringBuilder();
                sb4.append(applyFilterAcronymTasas2);
                sb4.append(", ");
                sb4.append(applyFilterTasaValue4);
                rowTwoColumnViewStyled4.setContentDescription(sb4.toString());
            } catch (Exception e5) {
                e5.printStackTrace();
            }
        }
        ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f90creditopage4cuentaDestino)).setmCElementAccValue(new CAccountAcc());
        ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f90creditopage4cuentaDestino)).setContent(resultado.getCuentaDestino());
        ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f86creditopage4cantidadCuotas)).setContent(resultado.getCantidadCuotas());
        ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f94creditopage4fechaPrimerVencimiento)).setmCElementAccValue(new CDateAcc());
        ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f94creditopage4fechaPrimerVencimiento)).setContent(resultado.getFechaPrimerVencimiento());
        if (resultado.getImporteCuotaUVA() != null) {
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f92creditopage4cuotaPuraUVA)).setmCElementAccValue(new CAmountAcc());
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f92creditopage4cuotaPuraUVA)).setContent(resultado.getImporteCuotaUVA());
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f92creditopage4cuotaPuraUVA)).setVisibility(0);
        } else {
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f92creditopage4cuotaPuraUVA)).setVisibility(8);
        }
        if (resultado.getCapitalIntereses() != null) {
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f87creditopage4capitalIntereses)).setmCElementAccValue(new CAmountAcc());
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f87creditopage4capitalIntereses)).setContent(resultado.getCapitalIntereses());
        } else {
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f87creditopage4capitalIntereses)).setVisibility(8);
        }
        if (resultado.getCargoSeguro() != null) {
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f88creditopage4cargoSeguro)).setmCElementAccValue(new CAmountAcc());
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f88creditopage4cargoSeguro)).setContent(resultado.getCargoSeguro());
        } else {
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f88creditopage4cargoSeguro)).setVisibility(8);
        }
        if (resultado.getIva() != null) {
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f99creditopage4iva)).setmCElementAccValue(new CAmountAcc());
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f99creditopage4iva)).setContent(resultado.getIva());
            try {
                String applyFilterAmount = CAccessibility.getInstance(getActContext()).applyFilterAmount(resultado.getIva());
                RowTwoColumnViewStyled rowTwoColumnViewStyled5 = (RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f99creditopage4iva);
                StringBuilder sb5 = new StringBuilder();
                sb5.append("iba, ");
                sb5.append(applyFilterAmount);
                rowTwoColumnViewStyled5.setContentDescription(sb5.toString());
            } catch (Exception e6) {
                e6.printStackTrace();
            }
        } else {
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f99creditopage4iva)).setVisibility(8);
        }
        if (resultado.getOtrosImpuestos() != null) {
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f104creditopage4otrosImpuestos)).setmCElementAccValue(new CAmountAcc());
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f104creditopage4otrosImpuestos)).setContent(resultado.getOtrosImpuestos());
        } else {
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f104creditopage4otrosImpuestos)).setVisibility(8);
        }
        if (resultado.getImporte() != null) {
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f97creditopage4importeCuota)).setmCElementAccValue(new CAmountAcc());
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f97creditopage4importeCuota)).setContent(resultado.getImporte());
        } else {
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f97creditopage4importeCuota)).setVisibility(8);
        }
        if (resultado.getCuotaPuraUVA() != null) {
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f91creditopage4cuotaPuraEnUVAS)).setmCElementAccValue(new CAmountAcc());
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f91creditopage4cuotaPuraEnUVAS)).setContent(resultado.getCuotaPuraUVA());
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f91creditopage4cuotaPuraEnUVAS)).setVisibility(0);
            ((TextView) this.pantallaComprobante.findViewById(R.id.textViewLeyendaCuotaPuraUvas)).setText(Html.fromHtml(CCreditos.getLeyenda(this.ax.getListaLeyenda(), Constants.LEYENDA_CUOTA_PURA_UVAS)));
        } else {
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f91creditopage4cuotaPuraEnUVAS)).setVisibility(8);
        }
        if (resultado.getCotizacionUVA() != null) {
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f89creditopage4cotizacionDeLaUVA)).setmCElementAccValue(new CAmountAcc());
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f89creditopage4cotizacionDeLaUVA)).setContent(resultado.getCotizacionUVA());
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f89creditopage4cotizacionDeLaUVA)).setVisibility(0);
        } else {
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f89creditopage4cotizacionDeLaUVA)).setVisibility(8);
        }
        if (resultado.getFechaCotizaUVA() != null) {
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f93creditopage4fechaDeCotizacionDeLaUVA)).setmCElementAccValue(new CDateAcc());
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f93creditopage4fechaDeCotizacionDeLaUVA)).setContent(resultado.getFechaCotizaUVA());
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f93creditopage4fechaDeCotizacionDeLaUVA)).setVisibility(0);
        } else {
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f93creditopage4fechaDeCotizacionDeLaUVA)).setVisibility(8);
        }
        ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f103creditopage4numeroComprobante)).setContent(resultado.getNumeroComprobante());
        try {
            String label = ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f103creditopage4numeroComprobante)).getLabel();
            String applyFilterCharacterToCharacter = CAccessibility.getInstance(getActContext()).applyFilterCharacterToCharacter(resultado.getNumeroComprobante());
            RowTwoColumnViewStyled rowTwoColumnViewStyled6 = (RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f103creditopage4numeroComprobante);
            StringBuilder sb6 = new StringBuilder();
            sb6.append(label);
            sb6.append(", ");
            sb6.append(applyFilterCharacterToCharacter);
            rowTwoColumnViewStyled6.setContentDescription(sb6.toString());
        } catch (Exception e7) {
            e7.printStackTrace();
        }
        CFiltersAccessibility cFiltersAccessibility = new CFiltersAccessibility(getContext());
        AnonymousClass3 r1 = new AccessibilityDelegate() {
            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.setText(view.getContentDescription());
            }
        };
        if (resultado.getIndicadorUVA() != null) {
            Spanned fromHtml = Html.fromHtml(CCreditos.getLeyenda(this.ax.getListaLeyenda(), resultado.getIndicadorUVA().equalsIgnoreCase("S") ? Constants.LEYENDA_PRE_UVA : Constants.LEYENDA_PRE_TRAD));
            this.leyendaPrestamoUvaComprobante.setText(fromHtml);
            if (fromHtml != null) {
                try {
                    str = fromHtml.toString();
                } catch (Exception e8) {
                    e8.printStackTrace();
                }
            } else {
                str = null;
            }
            this.leyendaPrestamoUvaComprobante.setContentDescription(cFiltersAccessibility.filterStringComun(cFiltersAccessibility.filter_OY(str)));
            this.leyendaPrestamoUvaComprobante.setAccessibilityDelegate(r1);
        } else {
            this.leyendaPrestamoUvaComprobante.setVisibility(8);
        }
        TextView textView = (TextView) this.pantallaComprobante.findViewById(R.id.f100creditopage4leyenda);
        if (resultado.getLeyenda() != null) {
            textView.setText(Html.fromHtml(resultado.getLeyenda().trim()));
            textView.setVisibility(0);
        } else {
            textView.setVisibility(8);
        }
        try {
            textView.setContentDescription(CAccessibility.getInstance(getContext()).applyFilterGeneral(textView.getText().toString()));
        } catch (Exception e9) {
            e9.printStackTrace();
        }
        LinearLayout linearLayout = (LinearLayout) this.pantallaComprobante.findViewById(R.id.credito_page4_layoutLeyendaLink);
        TextView textView2 = (TextView) this.pantallaComprobante.findViewById(R.id.credito_page4_idLeyendaLink);
        if (resultado.getMostrarLink() == null || !resultado.getMostrarLink().equals("S")) {
            linearLayout.setVisibility(8);
            textView2.setVisibility(8);
        } else {
            linearLayout.setVisibility(0);
            textView2.setVisibility(0);
            textView2.setText(Html.fromHtml(resultado.getDescripcionLink()).toString().trim());
            try {
                textView2.setContentDescription(CAccessibility.getInstance(getContext()).applyFilterAmount(textView2.getText().toString().trim()));
            } catch (Exception e10) {
                e10.printStackTrace();
            }
            this.d = resultado;
            D();
            LinearLayout linearLayout2 = (LinearLayout) this.pantallaComprobante.findViewById(R.id.linearLayoutLeyendaPreautorizacionSeguro);
            TextView textView3 = (TextView) this.pantallaComprobante.findViewById(R.id.textViewLeyendaPreautorizacionSeguro);
            if (this.ax.getListaLeyenda() != null) {
                linearLayout2.setVisibility(0);
                textView3.setText(Html.fromHtml(CCreditos.getLeyenda(this.ax.getListaLeyenda(), "CRED_SEGURO").trim()));
            } else {
                linearLayout2.setVisibility(8);
            }
        }
        TextView textView4 = (TextView) this.pantallaComprobante.findViewById(R.id.f102creditopage4leyenda_tasa_comp);
        if (resultado.getLeyendaTasa() == null || resultado.getLeyendaTasa().isEmpty()) {
            textView4.setVisibility(8);
        } else {
            textView4.setText(resultado.getLeyendaTasa().trim());
            textView4.setVisibility(0);
        }
        try {
            textView4.setContentDescription(CAccessibility.getInstance(getActContext()).applyCFTEA(CAccessibility.getInstance(getActContext()).applyFilterAcronymTasas(CAccessibility.getInstance(getActContext()).applyCFTEA_IVA_3(resultado.getLeyendaTasa()))));
        } catch (Exception e11) {
            e11.printStackTrace();
        }
        View customView = ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView();
        customView.findViewById(R.id.share).setVisibility(0);
        customView.findViewById(R.id.share).setOnClickListener(new OneClickListener(this));
        customView.findViewById(R.id.toggle).setOnClickListener(new OneClickListener(this));
        this.c.trackTransaction(getString(R.string.transaction_hit_product_name_transaccion_creditos), resultado.getNumeroComprobante());
    }

    /* access modifiers changed from: protected */
    public String getAbrevAccount(String str, String str2, String str3) {
        return UtilAccount.getAbreviatureAndAccountFormat(CConsDescripciones.getConsDescripcionTPOCTACORTA(this.b), str, str2, str3);
    }

    private void D() {
        ((TextView) this.pantallaComprobante.findViewById(R.id.credito_page4_Link)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!CreditosFragment.this.an) {
                    CreditosFragment.this.ad = true;
                    if (CreditosFragment.this.am == null) {
                        CreditosFragment.this.I();
                    }
                    CreditosFragment.this.am.showAlert();
                    return;
                }
                ((SantanderRioMainActivity) CreditosFragment.this.getActivity()).showProgress("Servicio");
                CreditosFragment.this.a.getCotizacionSeguroAccidente();
            }
        });
    }

    public void onRequestPermissionsResult(int i2, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i2, strArr, iArr);
        OptionsToShare optionsToShare = this.am;
        this.am.onRequestPermissionsResult(i2, strArr, iArr);
        if (i2 == 1) {
            for (int i3 = 0; i3 < strArr.length; i3++) {
                String str = strArr[i3];
                int i4 = iArr[i3];
                if (str.equals("android.permission.WRITE_EXTERNAL_STORAGE")) {
                    if (i4 == 0) {
                        if (this.ae) {
                            this.ae = false;
                            onClickShowActionShareReceipt();
                        } else {
                            if (this.am == null) {
                                I();
                            }
                            this.am.showAlert();
                        }
                    } else if (this.ad) {
                        this.ad = false;
                        ((SantanderRioMainActivity) getActivity()).showProgress("Servicio");
                        this.a.getCotizacionSeguroAccidente();
                    } else if (this.af) {
                        this.af = false;
                        ((SantanderRioMainActivity) getActivity()).goToOption(FragmentConstants.CUENTAS);
                    }
                }
            }
        }
    }

    @Subscribe
    public void getCotizacionSeguroAccidente(GetCotizacionSeguroAccidenteEvent getCotizacionSeguroAccidenteEvent) {
        ((SantanderRioMainActivity) getActivity()).dismissProgress();
        final GetCotizacionSeguroAccidenteEvent getCotizacionSeguroAccidenteEvent2 = getCotizacionSeguroAccidenteEvent;
        AnonymousClass5 r1 = new BaseWSResponseHandler(getActContext(), TypeOption.INITIAL_VIEW, FragmentConstants.SEGURO_COTIZACION, (BaseActivity) getActivity(), getActContext().getString(R.string.ID155_CREDITS_VIEW_LBL_RECEIPT)) {
            /* access modifiers changed from: protected */
            public void onOk() {
                Intent intent = new Intent(CreditosFragment.this.getActContext(), ContratarSeguroAccidenteActivity.class);
                intent.putExtra(ContratarSeguroAccidenteActivity.MEDIO_DE_PAGO, CreditosFragment.this.getAbrevAccount(CreditosFragment.this.getTipoCta(), CreditosFragment.this.d.getSucursal(), CreditosFragment.this.d.getNumeroCta()));
                intent.putExtra("TIPO_CUENTA", CreditosFragment.this.getTipoCta());
                intent.putExtra(ContratarSeguroAccidenteActivity.SUCURSAL_CUENTA, CreditosFragment.this.d.getSucursal());
                intent.putExtra(ContratarSeguroAccidenteActivity.NUMERO_CUENTA, CreditosFragment.this.d.getNumeroCta());
                intent.putExtra(ContratarSeguroAccidenteActivity.RESPONSE_GET_COTIZACION, getCotizacionSeguroAccidenteEvent2.getResponse().getCotizacionSeguroAccidenteBodyResponseBean.getCotizacion());
                intent.putParcelableArrayListExtra(ContratarSeguroAccidenteActivity.RESPONSE_GET_LISTA, (ArrayList) getCotizacionSeguroAccidenteEvent2.getResponse().getCotizacionSeguroAccidenteBodyResponseBean.getListaLeyendas().getLeyenda());
                CreditosFragment.this.startActivityForResult(intent, 1003);
                CreditosFragment.this.an = true;
            }
        };
        r1.handleWSResponse(getCotizacionSeguroAccidenteEvent);
    }

    public void goToInicio() {
        if (this.mControlPager.getDisplayedChild() != 0) {
            gotoPage(0, false);
            ((BaseActivity) getActivity()).setActionBarType(ActionBarType.BACK);
            ((SantanderRioMainActivity) getActivity()).lockMenu(false);
            enableBackButton();
            ((SantanderRioMainActivity) getActivity()).hideKeyboard();
            this.f.sendRequestConsultaPrestamosPermitidos(this.a, false);
        }
    }

    public void enableMenuButton() {
        View customView = ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView();
        this.ak = (SantanderRioMainActivity) getActivity();
        if (customView != null) {
            ((ImageView) customView.findViewById(R.id.toggle)).setOnClickListener(new OneClickListener(new OneClicked() {
                public void onClicked(View view) {
                    CreditosFragment.this.switchDrawer();
                }
            }));
        }
    }

    public void goToSimulacion(DatosPrestamoPermitido datosPrestamoPermitido) {
        gotoPage(1, true);
        b(this.pantallaSimulacion);
        ((BaseActivity) getActivity()).setActionBarType(ActionBarType.BACK);
        ((SantanderRioMainActivity) getActivity()).lockMenu(true);
        enableBackButton();
        this.g.onCreatePage(this.as, this.ax, datosPrestamoPermitido);
    }

    public void onPause() {
        super.onPause();
    }

    public void goToConfirmacion(DatosSolicitudPrestamo datosSolicitudPrestamo, ConsultaPrestamosPermitidosBodyResponseBean consultaPrestamosPermitidosBodyResponseBean) {
        nextPage();
        b(this.pantallaConfirmacion);
        ((BaseActivity) getActivity()).setActionBarType(ActionBarType.BACK);
        ((SantanderRioMainActivity) getActivity()).lockMenu(false);
        enableBackButton();
        this.h.onCreatePage(datosSolicitudPrestamo, this.aw, consultaPrestamosPermitidosBodyResponseBean);
    }

    public void goToComprobante(SolicitudPrestamoPreacordadoBodyResponseBean solicitudPrestamoPreacordadoBodyResponseBean, ConsultaPrestamosPermitidosBodyResponseBean consultaPrestamosPermitidosBodyResponseBean) {
        nextPage();
        b(this.pantallaComprobante);
        BaseActivity baseActivity = (BaseActivity) getActivity();
        if (baseActivity != null) {
            baseActivity.setActionBarType(ActionBarType.MENU);
        }
        ((SantanderRioMainActivity) getActivity()).lockMenu(false);
        enableMenuButton();
        this.i.onCreatePage(solicitudPrestamoPreacordadoBodyResponseBean, this.aw, consultaPrestamosPermitidosBodyResponseBean);
        this.aq = solicitudPrestamoPreacordadoBodyResponseBean.getDatosSolicitudPrestamo().getNumComprobante();
    }

    public void setCantidadCuota(String str) {
        this.ah.setCantidadCuotas(str);
        ((TextView) this.vgWrapperSimulacion.findViewById(R.id.selector_cant_cuotas)).setText(str);
    }

    public void setPrimeraCuota(String str) {
        this.ah.setPrimeraCuota(str);
        ((TextView) this.vgWrapperSimulacion.findViewById(R.id.selector_date_id)).setText(str);
    }

    public void setDestino(String str) {
        this.ah.setDestino(str);
        ((TextView) this.vgWrapperSimulacion.findViewById(R.id.selector_destino)).setText(str);
        AnonymousClass7 r0 = new AccessibilityDelegate() {
            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.setText(view.getContentDescription());
            }
        };
        this.vgWrapperSimulacion.findViewById(R.id.selector_destino).setContentDescription(new CFiltersAccessibility(getContext()).filterShorcuts(str));
        this.vgWrapperSimulacion.findViewById(R.id.selector_destino).setAccessibilityDelegate(r0);
    }

    public void showCantidadCuotaPicker(ArrayList<String> arrayList, String str) {
        ((SantanderRioMainActivity) getActivity()).hideKeyboard();
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("selector_cantidad_cuota", getString(R.string.ID_DIALOG_CRED_CANTIDAD_CUOTAS), null, arrayList, getString(R.string.IDX_ALERT_BTN_CANCEL), null, null, str);
        newInstance.setDialogListenerExtended(this);
        newInstance.setCancelable(true);
        newInstance.show(getActivity().getSupportFragmentManager(), CUOTAS_DIALOG);
    }

    public void showPrimeraCuotaPicker() {
        IsbanDatePickerDialogFragment newInstance = IsbanDatePickerDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_SELECCION_FECHA), ((TextView) this.vgWrapperSimulacion.findViewById(R.id.selector_date_id)).getText().toString(), getString(R.string.FORMAT_SHORT_DATE));
        newInstance.setDialogListener(new IDateDialogListener() {
            public void onDateSelected(Date date) {
                CreditosFragment.this.g.onPrimeraCuotaSelected(date);
            }
        });
        newInstance.show(getActivity().getSupportFragmentManager(), PRIMERA_CUOTA_DIALOG);
    }

    public void showDestinoPicker(ArrayList<String> arrayList, String str) {
        ArrayList arrayList2 = new ArrayList();
        ((SantanderRioMainActivity) getActivity()).hideKeyboard();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            try {
                arrayList2.add(b((String) it.next()));
            } catch (Exception e2) {
                arrayList2.add("");
                e2.printStackTrace();
            }
        }
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("selector_destino", getString(R.string.ID_DIALOG_CRED_SELECCIONAR_DESTINO), null, arrayList, PagoTarjetasConstants.ISBAN_DIALOG_CANCEL_BUTTON_TEXT, null, null, str, arrayList2);
        newInstance.setDialogListenerExtended(this);
        newInstance.setCancelable(true);
        newInstance.show(getActivity().getSupportFragmentManager(), DESTINO_DIALOG);
    }

    public void showProgress() {
        super.showProgress("");
    }

    public void dismisProgress() {
        super.dismissProgress();
    }

    public void showMessage(String str, final String str2) {
        FragmentManager fragmentManager = getFragmentManager();
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(str, str2, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null, null);
        newInstance.show(fragmentManager, "Dialog");
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
                Log.d(ImageSurfaceView.TAG, "OnIremSelected");
            }

            public void onPositiveButton() {
                if (str2.equalsIgnoreCase(CreditosFragment.this.getActContext().getResources().getString(R.string.MSG_USER000051))) {
                    CreditosFragment.this.a(CreditosFragment.this.pantallaSimulacion, CreditosFragment.this.vgWrapperSimulacion.findViewById(R.id.row_selector_cant_destino));
                } else if (str2.equalsIgnoreCase(CreditosFragment.this.getActContext().getResources().getString(R.string.MSG_USER0000XX_CREDITOS_90_DIAS)) || str2.equalsIgnoreCase(CreditosFragment.this.getActContext().getResources().getString(R.string.MSG_USER000042_Prestamos_errorFechaVencimiento))) {
                    CreditosFragment.this.a(CreditosFragment.this.pantallaSimulacion, CreditosFragment.this.vgWrapperSimulacion.findViewById(R.id.row_selector_date_id));
                }
            }

            public void onNegativeButton() {
                Log.d(ImageSurfaceView.TAG, "NEGAtive");
            }

            public void onSimpleActionButton() {
                if (str2.equalsIgnoreCase(CreditosFragment.this.getActContext().getResources().getString(R.string.MSG_USER000051))) {
                    CreditosFragment.this.a(CreditosFragment.this.pantallaSimulacion, CreditosFragment.this.vgWrapperSimulacion.findViewById(R.id.row_selector_cant_destino));
                } else if (str2.equalsIgnoreCase(CreditosFragment.this.getActContext().getResources().getString(R.string.MSG_USER0000XX_CREDITOS_90_DIAS)) || str2.equalsIgnoreCase(CreditosFragment.this.getActContext().getResources().getString(R.string.MSG_USER000042_Prestamos_errorFechaVencimiento))) {
                    CreditosFragment.this.a(CreditosFragment.this.pantallaSimulacion, CreditosFragment.this.vgWrapperSimulacion.findViewById(R.id.row_selector_date_id));
                } else if (str2.equalsIgnoreCase(CreditosFragment.this.getActContext().getResources().getString(R.string.MSG_USER0000XX_CREDITOS_FECHA_INVALIDA))) {
                    CreditosFragment.this.a(CreditosFragment.this.pantallaSimulacion, CreditosFragment.this.vgWrapperSimulacion.findViewById(R.id.row_selector_date_id));
                }
            }
        });
    }

    public IDataManager getDataManager() {
        return this.a;
    }

    public Cuenta getCuenta() {
        return this.ap;
    }

    public String getTipoCta() {
        return this.f238ar;
    }

    public void setTipoCta(String str) {
        this.f238ar = str;
    }

    public SolicitudPrestamoPreacordadoBodyRequestBean getSolicitudSimulacion() {
        return this.at;
    }

    public void setSolicitudSimulacion(SolicitudPrestamoPreacordadoBodyRequestBean solicitudPrestamoPreacordadoBodyRequestBean) {
        this.at = solicitudPrestamoPreacordadoBodyRequestBean;
    }

    public SessionManager getSessionManager() {
        return this.b;
    }

    public void openDestinoSelector() {
        this.g.onDestinosClicked();
    }

    public void addBlockBodySimulacion(View view) {
        a(this.vgWrapperSimulacion, view);
    }

    private void a(ViewGroup viewGroup, View view) {
        if (viewGroup != null) {
            viewGroup.removeAllViews();
            viewGroup.addView(view);
            E();
        }
    }

    private void E() {
        this.vgWrapperSimulacion.findViewById(R.id.row_selector_date_id).setOnClickListener(this);
        this.vgWrapperSimulacion.findViewById(R.id.row_selector_cant_cuotas).setOnClickListener(this);
        this.vgWrapperSimulacion.findViewById(R.id.row_selector_cant_destino).setOnClickListener(this);
    }

    public void clearImporte() {
        ((EditText) this.vgWrapperSimulacion.findViewById(R.id.input_amount_id)).setText("");
    }

    public void nextPage() {
        if (this.mControlPager != null) {
            this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
            this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
            this.mControlPager.showNext();
        }
    }

    public void previousPage() {
        if (this.mControlPager != null) {
            this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
            this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
            this.mControlPager.showPrevious();
        }
    }

    public void gotoPage(int i2, boolean z) {
        if (this.mControlPager != null) {
            if (!z) {
                this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
                this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
            } else {
                this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
            }
            this.mControlPager.setDisplayedChild(i2);
        }
    }

    public int getIndexViewPage(View view) {
        return this.mControlPager.indexOfChild(view);
    }

    public void enableBackButton() {
        View findViewById = ((BaseActivity) getActivity()).getSupportActionBar().getCustomView().findViewById(R.id.toggle);
        if (findViewById != null) {
            findViewById.findViewById(R.id.toggle).setOnClickListener(new OneClickListener(new OneClicked() {
                public void onClicked(View view) {
                    CreditosFragment.this.onBackPressed();
                }
            }));
        }
    }

    public void onItemSelected(String str, String str2) {
        if (str2.equalsIgnoreCase("selector_cantidad_cuota")) {
            setCantidadCuota(str);
            this.g.onNumCuotasSelected(str);
        } else if (str2.equalsIgnoreCase("selector_destino")) {
            setDestino(str);
            this.g.onDestinoSelected(str);
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.row_selector_date_id) {
            this.g.onPrimeraCuotaClicked();
        } else if (view.getId() == R.id.row_selector_cant_cuotas) {
            this.g.onCantidadCuotasClicked();
        } else if (view.getId() == R.id.row_selector_cant_destino) {
            this.g.onDestinosClicked();
        } else if (view.getId() == R.id.share) {
            onClickShowActionShareReceipt();
        } else if (view.getId() == R.id.toggle) {
            if (!this.an) {
                F();
            } else {
                switchDrawer();
            }
        } else if (view.getId() == R.id.threeOptionsButton1) {
            this.av.onOption1Button();
        } else if (view.getId() == R.id.threeOptionsButton2) {
            this.av.onOption2Button();
        } else if (view.getId() == R.id.threeOptionsButton3) {
            this.av.onOption3Button();
        }
    }

    public void onClickShowActionShareReceipt() {
        this.an = true;
        I();
        if (this.am != null) {
            this.am.show(getActContext().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_CANCEL));
        }
    }

    private void F() {
        if (!this.an) {
            this.an = true;
            showAlertVoucherDownload();
        }
    }

    public void showAlertVoucherDownload() {
        final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.ID3715_COMPRA_VENTA_LBL_AVISO), Html.fromHtml(getString(R.string.IDXX_COMPRA_VENTA_AVISO_DESCARGA_COMPROBANTE)).toString(), null, null, getString(R.string.ID3610_COMPRA_VENTA_BTN_SI), getString(R.string.ID3605_COMPRA_VENTA_BTN_NO), null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                if (CreditosFragment.this.am == null) {
                    CreditosFragment.this.I();
                }
                CreditosFragment.this.am.showAlert();
            }

            public void onNegativeButton() {
                newInstance.closeDialog();
            }
        });
        newInstance.show(getActivity().getSupportFragmentManager(), "popupConfirmation");
    }

    public void onOption1Button() {
        this.am.optionDownloadSelected();
    }

    public void onOption2Button() {
        this.am.optionCancelSelected();
    }

    public void onOption3Button() {
        this.am.optionShareSelected();
    }

    /* access modifiers changed from: private */
    public void G() {
        if (!this.an) {
            if (this.am == null) {
                H();
            }
            this.an = true;
            this.am.showAlert();
        }
    }

    private void H() {
        configActionShareReceiptClickLinkSeguros(getString(R.string.IDXX_SHARE_CREDITOS), getString(R.string.IDXX_SHARE_CREDITOS).concat("-").concat(this.aq));
    }

    /* access modifiers changed from: private */
    public void I() {
        configActionShareReceipt(getString(R.string.IDXX_SHARE_CREDITOS), getString(R.string.IDXX_SHARE_CREDITOS).concat("-").concat(this.aq));
    }

    public void showRequestPermissionExplation(final int i2) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getString(R.string.write_external_permission_request_message), null, getString(R.string.BTN_DIALOG_ACCEPT), null, null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
                CreditosFragment.this.G();
            }

            public void onSimpleActionButton() {
                CreditosFragment.this.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, i2);
            }
        });
        newInstance.show(getFragmentManager(), OptionsToShareImpl.PERMISSION_DIALOG_TAG);
    }

    public Cuenta getCuentaSelected() {
        return this.ap;
    }

    public void setCuentaSelected(Cuenta cuenta) {
        this.ap = cuenta;
    }

    private void b(final View view) {
        if (view != null) {
            view.post(new Runnable() {
                public void run() {
                    view.scrollTo(0, 0);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void a(final View view, final View view2) {
        new Handler().post(new Runnable() {
            public void run() {
                view2.requestFocus();
                view.scrollTo(0, view2.getBottom());
            }
        });
    }

    public void configActionShareReceiptClickLinkSeguros(String str, String str2) {
        final String str3 = str2;
        final String str4 = str;
        AnonymousClass15 r0 = new OptionsToShareImpl(this, getContext(), getFragmentManager()) {
            public View getViewToShare() {
                return CreditosFragment.this.viewComprobanteCreditos;
            }

            public void receiveIntentAppShare(Intent intent) {
                CreditosFragment.this.startActivityForResult(Intent.createChooser(intent, CreditosFragment.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)), 999);
            }

            public String getFileName() {
                return str3;
            }

            public String getSubjectReceiptToShare() {
                return str4;
            }

            public void onOption2Button() {
                ((SantanderRioMainActivity) CreditosFragment.this.getActivity()).showProgress("Servicio");
                CreditosFragment.this.a.getCotizacionSeguroAccidente();
            }

            public void onOption1Button() {
                super.onOption1Button();
                ((SantanderRioMainActivity) CreditosFragment.this.getActivity()).showProgress("Servicio");
                CreditosFragment.this.a.getCotizacionSeguroAccidente();
            }

            public void onOption3Button() {
                super.onOption3Button();
                ((SantanderRioMainActivity) CreditosFragment.this.getActivity()).showProgress("Servicio");
                CreditosFragment.this.a.getCotizacionSeguroAccidente();
            }
        };
        this.am = r0;
    }

    public void configActionShareReceipt(String str, String str2) {
        final String str3 = str2;
        final String str4 = str;
        AnonymousClass16 r0 = new OptionsToShareImpl(this, getContext(), getFragmentManager()) {
            public View getViewToShare() {
                return CreditosFragment.this.viewComprobanteCreditos;
            }

            public void receiveIntentAppShare(Intent intent) {
                CreditosFragment.this.startActivityForResult(Intent.createChooser(intent, CreditosFragment.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)), 999);
            }

            public String getFileName() {
                return str3;
            }

            public String getSubjectReceiptToShare() {
                return str4;
            }

            public void onOption2Button() {
                super.onOption2Button();
                CreditosFragment.this.an = true;
                if (CreditosFragment.this.af) {
                    CreditosFragment.this.af = false;
                    ((SantanderRioMainActivity) CreditosFragment.this.getActivity()).goToOption(FragmentConstants.CUENTAS);
                } else if (CreditosFragment.this.ad) {
                    CreditosFragment.this.ad = false;
                    ((SantanderRioMainActivity) CreditosFragment.this.getActivity()).showProgress("Servicio");
                    CreditosFragment.this.a.getCotizacionSeguroAccidente();
                }
            }

            public void onOption1Button() {
                super.onOption1Button();
                CreditosFragment.this.an = true;
            }

            public void onOption3Button() {
                super.onOption3Button();
                CreditosFragment.this.an = true;
            }
        };
        this.am = r0;
    }

    public void onActivityResult(int i2, int i3, Intent intent) {
        if (i2 == 999) {
            UtilFile.deleteDirectory(new File(UtilFile.getPathReceiptTmp()));
        } else {
            super.onActivityResult(i2, i3, intent);
        }
    }

    private String b(String str) {
        if (str == null) {
            return str;
        }
        if (str.contains("/")) {
            str = str.replace("/", "barra");
        }
        if (str.contains("herram")) {
            str = str.replace("herram", "herramientas");
        }
        if (str.contains("herram ")) {
            str = str.replace("herram ", "herramientas");
        }
        if (str.contains("cancelac ")) {
            str = str.replace("cancelac ", "cancelación ");
        }
        if (str.contains("equip")) {
            str = str.replace("equip", "equipos ");
        }
        return str.contains("art hog") ? str.replace("art hog", "artículos para el hogar") : str;
    }

    public void onClicked(View view) {
        if (view.getId() == R.id.f111creditoscontinuar) {
            y();
        } else if (view.getId() == R.id.f110creditosconfirmar) {
            z();
        } else if (view.getId() == R.id.f112creditosircuentas) {
            A();
        } else if (view.getId() == R.id.row_selector_date_id) {
            this.g.onPrimeraCuotaClicked();
        } else if (view.getId() == R.id.row_selector_cant_cuotas) {
            this.g.onCantidadCuotasClicked();
        } else if (view.getId() == R.id.row_selector_cant_destino) {
            this.g.onDestinosClicked();
        } else if (view.getId() == R.id.share) {
            onClickShowActionShareReceipt();
        } else if (view.getId() != R.id.toggle) {
        } else {
            if (!this.an) {
                F();
            } else {
                switchDrawer();
            }
        }
    }

    public void onItemClicked(AdapterView<?> adapterView, View view, int i2, long j) {
        this.c.trackScreen(getString(R.string.analytics_screen_name_creditos_configuracion));
        this.f.onItemClicked(i2);
    }

    @Subscribe
    public void onSolicitudPrestamoPreacordado(SolicitudPrestamoPreacordadoEvent solicitudPrestamoPreacordadoEvent) {
        dismisProgress();
        final SolicitudPrestamoPreacordadoEvent solicitudPrestamoPreacordadoEvent2 = solicitudPrestamoPreacordadoEvent;
        AnonymousClass17 r0 = new BaseWSResponseHandler(getActivity(), TypeOption.INITIAL_VIEW, "", (BaseActivity) getActivity()) {
            /* access modifiers changed from: protected */
            public void onOk() {
                CreditosFragment.this.au = ((SolicitudPrestamoPreacordadoResponseBean) solicitudPrestamoPreacordadoEvent2.getBeanResponse()).solicitudPrestamoPreacordadoBodyResponseBean;
                if (CreditosFragment.this.au.getDatosSolicitudPrestamo().getOpcion().equalsIgnoreCase(CreditosFragment.SIMULACION_WS)) {
                    CreditosFragment.this.g.getResponseSimulacionPrestamoPermitido(solicitudPrestamoPreacordadoEvent2, CreditosFragment.this.ax);
                } else {
                    CreditosFragment.this.h.getResponseConfirmacionPrestamoPermitido(solicitudPrestamoPreacordadoEvent2, CreditosFragment.this.ax);
                }
            }

            /* access modifiers changed from: protected */
            public void onRes8Error() {
                EmptyFragment newInstance = EmptyFragment.newInstance(EmptyFragment.TITLE_TENENCIA, Html.fromHtml(solicitudPrestamoPreacordadoEvent2.getErrorBodyBean().resDesc).toString(), R.drawable.ico_reloj_gris);
                FragmentManager supportFragmentManager = CreditosFragment.this.getActivity().getSupportFragmentManager();
                if (supportFragmentManager != null) {
                    supportFragmentManager.beginTransaction().add(R.id.content_frame, newInstance, EmptyFragment.TITLE_TENENCIA).addToBackStack(EmptyFragment.TITLE_TENENCIA).commit();
                }
            }
        };
        r0.handleWSResponse(solicitudPrestamoPreacordadoEvent);
    }
}
