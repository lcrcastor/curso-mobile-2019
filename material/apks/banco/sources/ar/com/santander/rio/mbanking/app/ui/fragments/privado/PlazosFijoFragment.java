package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.commons.CAmount;
import ar.com.santander.rio.mbanking.app.commons.CPageAnimationViewFlipper;
import ar.com.santander.rio.mbanking.app.commons.CustomActionBarListener;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.module.inversionesbancaprivada.analytics.InversionesAnalyticsImpl;
import ar.com.santander.rio.mbanking.app.module.longtermdeposit.LongTermDepositPresenter;
import ar.com.santander.rio.mbanking.app.module.longtermdeposit.LongTermDepositPresenterImp;
import ar.com.santander.rio.mbanking.app.module.longtermdeposit.LongTermDepositView;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDatePickerDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDatePickerDialogFragment.IDateDialogListener;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListenerExtended;
import ar.com.santander.rio.mbanking.components.ProgresIndicator;
import ar.com.santander.rio.mbanking.components.itrsa.OneClickListener;
import ar.com.santander.rio.mbanking.components.itrsa.OneClickListener.OneClicked;
import ar.com.santander.rio.mbanking.components.share.OptionsToShare;
import ar.com.santander.rio.mbanking.components.share.OptionsToShareImpl;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.CnsTasasPFTEvent;
import ar.com.santander.rio.mbanking.services.events.CnsTenenciasEvent;
import ar.com.santander.rio.mbanking.services.events.ConstPlazoFijoEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.model.general.TipoCliente;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsTenenciasDatosPFBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VConstPlazoFijo;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import ar.com.santander.rio.mbanking.utils.UtilFile;
import ar.com.santander.rio.mbanking.view.AmountView;
import ar.com.santander.rio.mbanking.view.NumericEditText;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.squareup.otto.Subscribe;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import javax.inject.Inject;

public class PlazosFijoFragment extends BaseFragment implements CustomActionBarListener, LongTermDepositView, IDialogListenerExtended, OneClicked {
    public static final String ID_DIALOG_CONFIRM = "dialog_confirm";
    public static final String ID_DIALOG_ERROR = "dialog_error";
    public static final String ID_DIALOG_SELECTOR_ACCOUNT = "selector_account";
    public static final String ID_DIALOG_SELECTOR_CURRENCY = "selector_currency";
    public static final String ID_DIALOG_SELECTOR_CURRENCY_RATES = "selector_currency_rates";
    public static final String ID_DIALOG_SELECTOR_EXPIRED_ACTION = "selector_expired_action";
    public static final int INDEX_PAGE_LIST = 0;
    public static final int INDEX_PAGE_RECEIPT = 5;
    public static final String PLAZOFIJO_INFO_ADICIONAL = "1";
    public static final String PLAZOFIJO_SUBPRODUCTO_UVA_PROCREAR = "0062";
    public static final String PLAZOFIJO_SUBPRODUCTO_UVI = "0060";
    public static final String TAG_FRAGMENT_LOAD = "tag_fragment_load";
    private String a;
    @Inject
    public AnalyticsManager analyticsManager;
    private boolean b = false;
    @InjectView(2131364192)
    public Button btnConstituirInConfirm;
    @InjectView(2131364194)
    public Button btnContinueInCreate;
    @InjectView(2131362141)
    public TextView btnLinkTasas;
    @InjectView(2131364201)
    public Button btnReturnInReceipt;
    private OptionsToShare c;
    private ProgresIndicator d;
    private LongTermDepositView e;
    private String f;
    /* access modifiers changed from: private */
    public String g;
    private String h;
    /* access modifiers changed from: private */
    public InversionesAnalyticsImpl i;
    @InjectView(2131362219)
    public TextView lblMontoMinimo;
    @InjectView(2131362140)
    public TextView lbl_errorWs;
    @InjectView(2131362152)
    public LinearLayout lnlCapitalUvi;
    @Inject
    public AnalyticsManager mAnalyticsManager;
    @InjectView(2131366360)
    public ViewFlipper mControlPager;
    @Inject
    public IDataManager mDataManager;
    public IsbanDatePickerDialogFragment mDatePicker;
    public View mMainView;
    public TextView mSelectorCurrency;
    @Inject
    public SessionManager mSessionManager;
    @InjectView(2131365286)
    public View pageConfirm;
    @InjectView(2131365288)
    public View pageCreate;
    @InjectView(2131365290)
    public View pageDetail;
    @InjectView(2131365292)
    public View pageList;
    @InjectView(2131365295)
    public View pageRates;
    @InjectView(2131365297)
    public View pageReceipt;
    public LongTermDepositPresenter presenterLongTermDeposit;
    @InjectView(2131362206)
    public RelativeLayout rltValorUva;
    @InjectView(2131365800)
    public ScrollView svPageConfirm;
    @InjectView(2131365801)
    public ScrollView svPageCreate;
    @InjectView(2131365803)
    public ScrollView svPageReceipt;
    @InjectView(2131366119)
    public TextView tvLegendConfirm;
    @InjectView(2131366120)
    public TextView tvLegendReceipt;
    @InjectView(2131362178)
    public TextView txtAccionVencimientoInDetail;
    @InjectView(2131362156)
    public TextView txtAccionVencimientoLabel;
    @InjectView(2131362179)
    public TextView txtCanalInDetail;
    @InjectView(2131362180)
    public TextView txtCapitalInDetail;
    @InjectView(2131362181)
    public TextView txtCapitalUviInDetail;
    @InjectView(2131362176)
    public TextView txtCapitalUviLabel;
    @InjectView(2131362182)
    public TextView txtCertificadoInDetail;
    @InjectView(2131362183)
    public TextView txtConstitucionInDetail;
    @InjectView(2131362184)
    public TextView txtImpuestosInDetail;
    @InjectView(2131362185)
    public TextView txtInteresesInDetail;
    @InjectView(2131362191)
    public TextView txtInteresesLabel;
    @InjectView(2131362194)
    public TextView txtLblLeyendaInDetail;
    @InjectView(2131362195)
    public AmountView txtMontoInDetail;
    @InjectView(2131366374)
    public TextView txtMsjNoPlazos;
    @InjectView(2131366373)
    public TextView txtMsjNoPlazosBold;
    @InjectView(2131362186)
    public TextView txtSucursalInDetail;
    @InjectView(2131362196)
    public TextView txtSucursalLabel;
    @InjectView(2131362187)
    public TextView txtTasaInDetail;
    @InjectView(2131362197)
    public TextView txtTasaLabel;
    @InjectView(2131362198)
    public TextView txtTipoInDetail;
    @InjectView(2131362188)
    public TextView txtValorUvaDetail;
    @InjectView(2131362202)
    public TextView txtVencimientoInDetail;
    @InjectView(2131366367)
    public LinearLayout vgErrorWSLongTermDeposit;
    @InjectView(2131366438)
    public ViewGroup vgLegendConfirm;
    @InjectView(2131366439)
    public ViewGroup vgLegendReceipt;
    @InjectView(2131366372)
    public LinearLayout vgListWithoutRowsLongTermDeposit;
    @InjectView(2131366389)
    public ViewGroup vgWrapperConfirmLongTermDeposit;
    @InjectView(2131366390)
    public ViewGroup vgWrapperCreateLongTermDeposit;
    @InjectView(2131366393)
    public ViewGroup vgWrapperListLongTermDeposit;
    @InjectView(2131366394)
    public ViewGroup vgWrapperRatesLongTermDeposit;
    @InjectView(2131366396)
    public ViewGroup vgWrapperReceiptLongTermDeposit;
    @InjectView(2131366351)
    public TextView vtitlebp;

    public void onSimpleActionButton(String str) {
    }

    public static PlazosFijoFragment newInstance(String str, String str2) {
        PlazosFijoFragment plazosFijoFragment = new PlazosFijoFragment();
        plazosFijoFragment.a = str2;
        Bundle bundle = new Bundle();
        bundle.putString("TIPO_CLIENTE", str);
        plazosFijoFragment.setArguments(bundle);
        return plazosFijoFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() == null || getArguments().getString("TIPO_CLIENTE") == null) {
            this.g = this.mSessionManager.getLoginUnico().getDatosPersonales().getTipoCliente();
        } else {
            this.f = getArguments().getString("TIPO_CLIENTE");
            this.g = this.f;
            this.h = getArguments().getString("flag", "");
            if (!this.h.isEmpty()) {
                this.g = TipoCliente.RTL.getTipoCliente();
            }
        }
        this.e = this;
        this.presenterLongTermDeposit = new LongTermDepositPresenterImp(this, this.g);
        this.d = ProgresIndicator.newInstance(VConstPlazoFijo.nameService);
        setErrorListener(C());
        this.i = new InversionesAnalyticsImpl(getContext(), this.analyticsManager);
    }

    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        this.mMainView = layoutInflater.inflate(R.layout.plazos_fijos_fragment, viewGroup, false);
        ButterKnife.inject((Object) this, this.mMainView);
        y();
        return this.mMainView;
    }

    private void y() {
        if (this.a == null || !this.a.equals(FragmentConstants.PLAZOS_FIJOS)) {
            this.presenterLongTermDeposit.onGoToCreateNewClicked();
            return;
        }
        this.presenterLongTermDeposit.onLoad();
        if (this.h != null && !this.h.isEmpty()) {
            this.presenterLongTermDeposit.updateMainView(TipoCliente.RTL.getTipoCliente());
        }
    }

    public IDataManager getDataManager() {
        return this.mDataManager;
    }

    @Subscribe
    public void onGetConstPlazoFijo(ConstPlazoFijoEvent constPlazoFijoEvent) {
        this.b = false;
        closeDialogLoading();
        final ConstPlazoFijoEvent constPlazoFijoEvent2 = constPlazoFijoEvent;
        AnonymousClass1 r1 = new BaseWSResponseHandler(getActivity(), TypeOption.TRANSACTIONAL_FINAL_VIEW, FragmentConstants.PLAZOS_FIJOS, this, (BaseActivity) getActContext()) {
            /* access modifiers changed from: protected */
            public void onOk() {
                PlazosFijoFragment.this.presenterLongTermDeposit.onConstPlazoFijoResponseOk(constPlazoFijoEvent2);
            }

            /* access modifiers changed from: protected */
            public void onServerError() {
                PlazosFijoFragment.this.onShowError(constPlazoFijoEvent2.getTitleToShow(), "");
            }

            /* access modifiers changed from: protected */
            public void onRes3Error(WebServiceEvent webServiceEvent) {
                PlazosFijoFragment.this.onShowError(constPlazoFijoEvent2.getTitleToShow(), constPlazoFijoEvent2.getMessageToShow());
            }
        };
        r1.handleWSResponse(constPlazoFijoEvent);
    }

    @Subscribe
    public void onCnsTenenciasEvent(CnsTenenciasEvent cnsTenenciasEvent) {
        closeDialogLoading();
        final CnsTenenciasEvent cnsTenenciasEvent2 = cnsTenenciasEvent;
        AnonymousClass2 r0 = new BaseWSResponseHandler(getActivity(), TypeOption.INITIAL_VIEW, FragmentConstants.PLAZOS_FIJOS, this, (BaseActivity) getActContext()) {
            /* access modifiers changed from: protected */
            public void onOk() {
                PlazosFijoFragment.this.presenterLongTermDeposit.onCnsTenenciasResponse(cnsTenenciasEvent2);
            }

            /* access modifiers changed from: protected */
            public void onServerError() {
                PlazosFijoFragment.this.presenterLongTermDeposit.onCnsTenenciasResponse(cnsTenenciasEvent2);
            }

            /* access modifiers changed from: protected */
            public void onRes4Error(WebServiceEvent webServiceEvent) {
                PlazosFijoFragment.this.presenterLongTermDeposit.onCnsTenenciasResponse(cnsTenenciasEvent2);
            }
        };
        r0.handleWSResponse(cnsTenenciasEvent);
    }

    @Subscribe
    public void onCnsTasasPFTEvent(CnsTasasPFTEvent cnsTasasPFTEvent) {
        closeDialogLoading();
        final CnsTasasPFTEvent cnsTasasPFTEvent2 = cnsTasasPFTEvent;
        AnonymousClass3 r0 = new BaseWSResponseHandler(getActivity(), TypeOption.INTERMDIATE_VIEW, FragmentConstants.PLAZOS_FIJOS, this, (BaseActivity) getActivity()) {
            /* access modifiers changed from: protected */
            public void onOk() {
                PlazosFijoFragment.this.presenterLongTermDeposit.onCnsTasasPFTEventResponseOk(cnsTasasPFTEvent2);
            }
        };
        r0.handleWSResponse(cnsTasasPFTEvent);
    }

    public View getPageCreate() {
        return this.pageCreate;
    }

    public View getPageConfirm() {
        return this.pageConfirm;
    }

    public View getPageReceipt() {
        return this.pageReceipt;
    }

    public View getPageList() {
        return this.pageList;
    }

    public View getPageDetail() {
        return this.pageDetail;
    }

    public View getPageRates() {
        return this.pageRates;
    }

    public ViewGroup getWrapperDataCreate() {
        return this.vgWrapperCreateLongTermDeposit;
    }

    public ViewGroup getWrapperDataRates() {
        return this.vgWrapperRatesLongTermDeposit;
    }

    public ViewGroup getWrapperDataConfirm() {
        return this.vgWrapperConfirmLongTermDeposit;
    }

    public ViewGroup getWrapperDataReceipt() {
        return this.vgWrapperReceiptLongTermDeposit;
    }

    public Button getBtnContinueInCreate() {
        return this.btnContinueInCreate;
    }

    public Button getBtnConstituirInConfirm() {
        return this.btnConstituirInConfirm;
    }

    public Button getBtnReturnInReceipt() {
        return this.btnReturnInReceipt;
    }

    public void setTitleLayout(View view, String str) {
        TextView textView = (TextView) view.findViewById(R.id.vTitle);
        TextView textView2 = (TextView) view.findViewById(R.id.vTitleBP);
        if (textView != null) {
            textView.setText(str);
        }
    }

    public SessionManager getSessionManager() {
        return this.mSessionManager;
    }

    public void removeBlockBodyPageList() {
        this.vgWrapperListLongTermDeposit.removeAllViews();
    }

    public void removeBlockBodyPageDetail() {
        this.txtMontoInDetail.setText(String.valueOf(""));
        this.txtVencimientoInDetail.setText(String.format(getString(R.string.ID3008_DETALLETRADARS_LBL_VTO), new Object[]{String.valueOf("")}));
        this.txtTipoInDetail.setText(String.valueOf(""));
        this.txtCapitalInDetail.setText(String.valueOf(""));
        this.txtInteresesInDetail.setText(String.valueOf(""));
        this.txtTasaInDetail.setText(String.valueOf(""));
        this.txtImpuestosInDetail.setText(String.valueOf(""));
        this.txtConstitucionInDetail.setText(String.valueOf(""));
        this.txtCertificadoInDetail.setText(String.valueOf(""));
        this.txtSucursalInDetail.setText(String.valueOf(""));
        this.txtCanalInDetail.setText(String.valueOf(""));
        this.txtAccionVencimientoInDetail.setText(String.valueOf(""));
        this.txtValorUvaDetail.setText(String.valueOf(""));
    }

    public void removeBlockBodyPageRates() {
        getWrapperDataRates().removeAllViews();
    }

    public void removeBlockBodyPageCreate() {
        getWrapperDataCreate().removeAllViews();
    }

    public void removeBlockBodyPageConfirm() {
        getWrapperDataConfirm().removeAllViews();
    }

    public void removeBlockBodyPageReceipt() {
        getWrapperDataReceipt().removeAllViews();
    }

    public AnalyticsManager getAnalyticsManager() {
        return this.mAnalyticsManager;
    }

    public void addBlockBodyPageCreate(View view) {
        a(this.vgWrapperCreateLongTermDeposit, view);
    }

    public void addBlockBodyPageListPlazosFijosExistentes(View view) {
        a(this.vgWrapperListLongTermDeposit, view);
        this.vgListWithoutRowsLongTermDeposit.setVisibility(8);
        this.vgErrorWSLongTermDeposit.setVisibility(8);
        this.mAnalyticsManager.trackScreen(getString(R.string.analytics_screen_name_long_term_deposit_list));
    }

    public void addBlockBodyPageListSinPlazosFijos() {
        this.presenterLongTermDeposit.setListeners();
        this.vgListWithoutRowsLongTermDeposit.setVisibility(0);
        if (this.g.equalsIgnoreCase(TipoCliente.BP.getTipoCliente()) || this.g.equalsIgnoreCase(TipoCliente.BP_RTL.getTipoCliente())) {
            this.txtMsjNoPlazos.setText(getResources().getString(R.string.ID3058_SINTENENCIA_LBL_SINTEN_BP));
            this.i = new InversionesAnalyticsImpl(getContext(), this.analyticsManager);
            this.i.TenenciaPlazoFijoErrorParcial();
        } else {
            this.txtMsjNoPlazos.setText(getResources().getString(R.string.ID3058_SINTENENCIA_LBL_SINTEN));
            this.txtMsjNoPlazosBold.setText(getResources().getString(R.string.ID3058_SINTENENCIA_LBL_SINTEN_BOLD));
            this.i = new InversionesAnalyticsImpl(getContext(), this.analyticsManager);
            this.i.TenenciaPlazoFijoErrorParcial();
        }
        this.vgErrorWSLongTermDeposit.setVisibility(8);
        this.mAnalyticsManager.trackScreen(getString(R.string.analytics_screen_name_long_term_deposit_list_empty));
    }

    public void addBlockBodyPageListErrorDevolucionPlazosFijos(String str) {
        this.presenterLongTermDeposit.setListeners();
        this.vgListWithoutRowsLongTermDeposit.setVisibility(8);
        this.vgErrorWSLongTermDeposit.setVisibility(0);
        if (!(str == null || str == "")) {
            this.lbl_errorWs.setText(Html.fromHtml(str));
        }
        this.mAnalyticsManager.trackScreen(getString(R.string.analytics_screen_name_long_term_deposit_list_error));
        this.i = new InversionesAnalyticsImpl(getContext(), this.analyticsManager);
        this.i.TenenciaPlazoFijoErrorTotal();
    }

    public void addBlockBodyPageDetail(CnsTenenciasDatosPFBean cnsTenenciasDatosPFBean, String str) {
        if (cnsTenenciasDatosPFBean.subproducto.equalsIgnoreCase("0060")) {
            this.mAnalyticsManager.trackScreen(getString(R.string.analytics_screen_name_long_term_detalle_uvis_pesos));
        } else if (cnsTenenciasDatosPFBean.subproducto.equalsIgnoreCase("0062")) {
            this.mAnalyticsManager.trackScreen(getString(R.string.analytics_screen_name_long_term_detalle_uvas_procrear));
        }
        ((ScrollView) this.pageDetail.findViewById(R.id.F10_00_scrl)).scrollTo(0, 0);
        CAmount cAmount = new CAmount(cnsTenenciasDatosPFBean.totalACobrar);
        cAmount.setSymbolCurrencyDollarOrPeso(cnsTenenciasDatosPFBean.totalACobrar.contains(Constants.SYMBOL_CURRENCY_DOLAR));
        this.txtMontoInDetail.setText(cAmount.getAmountPossitive());
        this.txtVencimientoInDetail.setText(String.format(getString(R.string.ID3008_DETALLETRADARS_LBL_VTO), new Object[]{UtilDate.getDateFormat(cnsTenenciasDatosPFBean.fechaProxVencimiento, Constants.FORMAT_DATE_DASH, Constants.FORMAT_DATE_APP_2)}));
        this.txtTipoInDetail.setText(Html.fromHtml(cnsTenenciasDatosPFBean.tituloDetalle));
        this.txtCapitalUviInDetail.setText(cnsTenenciasDatosPFBean.capitalUVI);
        CAmount cAmount2 = new CAmount(cnsTenenciasDatosPFBean.capital);
        cAmount2.setSymbolCurrencyDollarOrPeso(cnsTenenciasDatosPFBean.capital.contains(Constants.SYMBOL_CURRENCY_DOLAR));
        this.txtCapitalInDetail.setText(cAmount2.getAmountPossitive());
        CAmount cAmount3 = new CAmount(cnsTenenciasDatosPFBean.intereses);
        cAmount3.setSymbolCurrencyDollarOrPeso(cnsTenenciasDatosPFBean.intereses.contains(Constants.SYMBOL_CURRENCY_DOLAR));
        this.txtInteresesInDetail.setText(cAmount3.getAmountPossitive());
        this.txtTasaInDetail.setText(cnsTenenciasDatosPFBean.tasaTNA);
        CAmount cAmount4 = new CAmount(cnsTenenciasDatosPFBean.impuestos);
        cAmount4.setSymbolCurrencyDollarOrPeso(cnsTenenciasDatosPFBean.impuestos.contains(Constants.SYMBOL_CURRENCY_DOLAR));
        this.txtImpuestosInDetail.setText(cAmount4.getAmountPossitive());
        this.txtConstitucionInDetail.setText(UtilDate.getDateFormat(cnsTenenciasDatosPFBean.fechaDeAlta, Constants.FORMAT_DATE_DASH, Constants.FORMAT_DATE_APP_2));
        this.txtCertificadoInDetail.setText(cnsTenenciasDatosPFBean.certificado);
        this.txtSucursalInDetail.setText(cnsTenenciasDatosPFBean.sucursalDeRadicacion);
        this.txtCanalInDetail.setText(cnsTenenciasDatosPFBean.canalApertura);
        this.txtAccionVencimientoInDetail.setText(Html.fromHtml(cnsTenenciasDatosPFBean.accionAlVto));
        if (this.g.equals(TipoCliente.BP.getTipoCliente()) || this.g.equals(TipoCliente.BP_RTL.getTipoCliente())) {
            this.i.tenenciaPlazoFijoDetallebp();
        } else if (this.g.equals(TipoCliente.RTL.getTipoCliente())) {
            this.i.tenenciaPlazoFijoDetallertl();
        }
        if (!TextUtils.isEmpty(str)) {
            this.txtLblLeyendaInDetail.setText(Html.fromHtml(str));
            this.txtLblLeyendaInDetail.setVisibility(0);
        } else {
            this.txtLblLeyendaInDetail.setVisibility(8);
        }
        if (cnsTenenciasDatosPFBean.infoAdicional == null) {
            this.lnlCapitalUvi.setVisibility(8);
            this.rltValorUva.setVisibility(8);
            this.txtInteresesLabel.setText(R.string.ID3010_DETALLETRADARS_LBL_INTERESES);
            this.txtValorUvaDetail.setText("");
        } else if (cnsTenenciasDatosPFBean.infoAdicional.equalsIgnoreCase("1")) {
            this.lnlCapitalUvi.setVisibility(0);
            this.rltValorUva.setVisibility(0);
            this.txtValorUvaDetail.setText(cnsTenenciasDatosPFBean.valorUVA);
            this.txtInteresesLabel.setText(String.format("%s%s", new Object[]{getString(R.string.ID3010_DETALLETRADARS_LBL_INTERESES), Constants.LEYEND_ASTERISK}));
        } else {
            this.lnlCapitalUvi.setVisibility(8);
            this.rltValorUva.setVisibility(8);
            this.txtInteresesLabel.setText(R.string.ID3010_DETALLETRADARS_LBL_INTERESES);
            this.txtValorUvaDetail.setText("");
        }
        try {
            this.txtMontoInDetail.setContentDescription(CAccessibility.getInstance(getActContext()).applyFilterAmount(this.txtMontoInDetail.getText().toString()));
            TextView textView = this.txtVencimientoInDetail;
            StringBuilder sb = new StringBuilder();
            sb.append(CAccessibility.getInstance(getActContext()).applyFilterGeneral(getString(R.string.IN_VTO)));
            sb.append(CAccessibility.getInstance(getActContext()).applyFilterDate(UtilDate.getDateFormat(cnsTenenciasDatosPFBean.fechaProxVencimiento, Constants.FORMAT_DATE_DASH, Constants.FORMAT_DATE_APP_2)));
            textView.setContentDescription(sb.toString());
            this.txtTipoInDetail.setContentDescription(CAccessibility.getInstance(getActContext()).applyFilterGeneral(this.txtTipoInDetail.getText().toString()));
            this.txtCapitalInDetail.setContentDescription(CAccessibility.getInstance(getActContext()).applyFilterAmount(this.txtCapitalInDetail.getText().toString()));
            this.txtInteresesInDetail.setContentDescription(CAccessibility.getInstance(getActContext()).applyFilterAmount(this.txtInteresesInDetail.getText().toString()));
            this.txtTasaInDetail.setContentDescription(CAccessibility.getInstance(getActContext()).applyFilterTasaValue(this.txtTasaInDetail.getText().toString()));
            this.txtImpuestosInDetail.setContentDescription(CAccessibility.getInstance(getActContext()).applyFilterAmount(this.txtImpuestosInDetail.getText().toString()));
            this.txtConstitucionInDetail.setContentDescription(CAccessibility.getInstance(getActContext()).applyFilterDate(this.txtConstitucionInDetail.getText().toString()));
            this.txtCertificadoInDetail.setContentDescription(CAccessibility.getInstance(getActContext()).applyFilterCharacterToCharacter(this.txtCertificadoInDetail.getText().toString()));
            this.txtSucursalInDetail.setContentDescription(CAccessibility.getInstance(getActContext()).applyFilterCharacterToCharacter(this.txtSucursalInDetail.getText().toString()));
            this.txtTasaLabel.setContentDescription(CAccessibility.getInstance(getActContext()).applyFilterTasaInteres(this.txtTasaLabel.getText().toString()));
            this.txtSucursalLabel.setContentDescription(CAccessibility.getInstance(getActContext()).applyFilterGeneral(this.txtSucursalLabel.getText().toString()));
            this.txtAccionVencimientoLabel.setContentDescription(CAccessibility.getInstance(getActContext()).applyFilterGeneral(this.txtAccionVencimientoLabel.getText().toString()));
            this.txtCapitalUviLabel.setContentDescription(CAccessibility.getInstance(getActContext()).applyFilterGeneral(this.txtCapitalUviLabel.getText().toString()));
            this.txtValorUvaDetail.setContentDescription(CAccessibility.getInstance(getActContext()).applyFilterGeneral(this.txtValorUvaDetail.getText().toString()));
            this.txtLblLeyendaInDetail.setContentDescription(CAccessibility.getInstance(getActContext()).applyFilterGeneral(this.txtLblLeyendaInDetail.getText().toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public TextView getLblMontoMinimoRates() {
        return this.lblMontoMinimo;
    }

    public void addBlockBodyPageRates(View view) {
        a(this.vgWrapperRatesLongTermDeposit, view);
        this.mAnalyticsManager.trackScreen(getString(R.string.analytics_screen_name_long_term_deposit_valid_rates));
    }

    public void addBlockBodyPageConfirm(View view) {
        a(this.vgWrapperConfirmLongTermDeposit, view);
    }

    public void addBlockBodyPageReceipt(View view) {
        a(this.vgWrapperReceiptLongTermDeposit, view);
    }

    public void setListenerClickRowAccount() {
        View rowSelectorAccount = getRowSelectorAccount();
        if (rowSelectorAccount != null) {
            rowSelectorAccount.setOnClickListener(new OneClickListener(this));
        }
    }

    public void setListenerClickRowCurrency() {
        View findViewById = this.mMainView.findViewById(R.id.row_selector_currency_id);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OneClickListener(this));
        }
    }

    public View getRowSelectorAccount() {
        return this.mMainView.findViewById(R.id.row_selector_account_id);
    }

    public View getViewSelectorAccount() {
        return this.mMainView.findViewById(R.id.selector_account_id);
    }

    public void setLabelSelectorAccount(String str) {
        TextView textView = (TextView) getViewSelectorAccount();
        CAccessibility cAccessibility = new CAccessibility(getActivity().getApplicationContext());
        if (textView != null) {
            textView.setText(str);
            try {
                textView.setContentDescription(cAccessibility.applyFilterAccount(str));
            } catch (Throwable unused) {
            }
        }
    }

    public TextView getSelectorCurrency() {
        if (this.mSelectorCurrency == null) {
            this.mSelectorCurrency = (TextView) this.mMainView.findViewById(R.id.selector_currency_id);
        }
        return this.mSelectorCurrency;
    }

    public void showDialogSelector(String str, String str2, ArrayList<String> arrayList, String str3) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(str, str2, null, arrayList, getString(R.string.ID93_ACCOUNTS_CHANGEACC_BTN_CANCEL), null, null, str3);
        newInstance.setDialogListenerExtended(this);
        newInstance.setCancelable(true);
        newInstance.show(getActivity().getSupportFragmentManager(), TenenciaCreditosFragment.DIALOG_SELECTOR);
    }

    public void setListenerClickExpiredDate() {
        View findViewById = this.mMainView.findViewById(R.id.row_selector_date_id);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OneClickListener(this));
        }
    }

    private void a(final ViewGroup viewGroup, final View view) {
        if (viewGroup != null) {
            viewGroup.post(new Runnable() {
                public void run() {
                    viewGroup.addView(view);
                    if (PlazosFijoFragment.this.mControlPager.getVisibility() == 8) {
                        PlazosFijoFragment.this.mControlPager.setVisibility(0);
                    }
                    PlazosFijoFragment.this.presenterLongTermDeposit.setListeners();
                }
            });
        }
    }

    public void nextPage() {
        setNextPageAnimation();
        this.mControlPager.showNext();
    }

    public void previousPage() {
        setPreviusPageAnimation();
        this.mControlPager.showPrevious();
    }

    public void gotoPage(int i2) {
        if (this.mControlPager != null) {
            this.mControlPager.setDisplayedChild(i2);
        }
        if (i2 == 0) {
            this.analyticsManager.setScreenName(getString(R.string.analytics_screen_name_long_term_deposit_list));
        }
    }

    public void setNextPageAnimation() {
        if (this.mControlPager != null) {
            this.mControlPager.setInAnimation(CPageAnimationViewFlipper.getNextInAnimation());
            this.mControlPager.setOutAnimation(CPageAnimationViewFlipper.getNextOutAnimation());
        }
    }

    public void setModalInPageAnimation() {
        if (this.mControlPager != null) {
            this.mControlPager.setInAnimation(AnimationUtils.loadAnimation(getActContext(), R.anim.slide_up));
            this.mControlPager.setOutAnimation(AnimationUtils.loadAnimation(getActContext(), R.anim.no_animation));
        }
    }

    public void setModalOutPageAnimation() {
        if (this.mControlPager != null) {
            this.mControlPager.setInAnimation(AnimationUtils.loadAnimation(getActContext(), R.anim.no_animation));
            this.mControlPager.setOutAnimation(AnimationUtils.loadAnimation(getActContext(), R.anim.slide_down));
        }
    }

    public void setPreviusPageAnimation() {
        if (this.mControlPager != null) {
            this.mControlPager.setInAnimation(CPageAnimationViewFlipper.getPreviusInAnimation());
            this.mControlPager.setOutAnimation(CPageAnimationViewFlipper.getPreviusOutAnimation());
        }
    }

    public int getIndexViewPage(View view) {
        return this.mControlPager.indexOfChild(view);
    }

    public Context getActContext() {
        return getActivity();
    }

    public void onClicked(View view) {
        if (view.getId() == R.id.row_selector_account_id) {
            this.presenterLongTermDeposit.onSelectorAccountClicked();
        } else if (view.getId() == R.id.row_selector_currency_id) {
            this.presenterLongTermDeposit.onSelectorCurrencyClicked();
        } else if (view.getId() == R.id.row_selector_date_id) {
            this.presenterLongTermDeposit.onSelectorExpiredDateClicked();
        } else if (view.getId() == R.id.row_selector_expired_action_id) {
            this.presenterLongTermDeposit.onExpiredActionClicked();
        } else if (view.getId() == R.id.F10_00_btn_nuevoPlazoFijo) {
            this.i.registerEventSuscribirNuevoPlazoFijo();
            this.presenterLongTermDeposit.onGoToCreateNewClicked();
        } else if (view.getId() == R.id.F10_00_lbl_linkTasas) {
            this.presenterLongTermDeposit.onGoToRatesClicked("FROM_LIST");
        } else if (view.getId() == R.id.selector_cuentas_privadas) {
            selecTipoCuenta();
        } else if (view.getId() == R.id.F10_CREATE_lbl_linkTasas) {
            this.presenterLongTermDeposit.onGoToRatesClicked("FROM_CREATE");
        } else if (view.getId() == R.id.F10_02_lnl_cambiarMoneda) {
            this.presenterLongTermDeposit.onChangeCurrencyClicked();
        }
    }

    public void onItemSelected(String str, String str2) {
        if ("selector_account".equals(str2)) {
            this.presenterLongTermDeposit.onAccountSelected(str);
        } else if ("selector_currency".equals(str2)) {
            this.presenterLongTermDeposit.onCurrencySelected(str);
        } else if ("selector_expired_action".equals(str2)) {
            this.presenterLongTermDeposit.onExpiredActionSelected(str);
        } else if ("selector_currency_rates".equals(str2)) {
            this.presenterLongTermDeposit.onChangeCurrencySelected(str);
        }
    }

    public void onPositiveButton(String str) {
        if ("dialog_confirm".equals(str)) {
            this.presenterLongTermDeposit.sendConstituirLongTerm();
        }
    }

    public void onNegativeButton(String str) {
        if ("dialog_confirm".equals(str)) {
            this.presenterLongTermDeposit.onBtnCancelConfirmLongTermDepositClicked();
        }
    }

    public void setLabelSelectorCurrency(String str) {
        this.mSelectorCurrency = getSelectorCurrency();
        if (this.mSelectorCurrency != null) {
            this.mSelectorCurrency.setText(str);
        }
    }

    public void restoreSelectorCurrency() {
        this.mSelectorCurrency = getSelectorCurrency();
        if (this.mSelectorCurrency != null) {
            this.mSelectorCurrency.setText(getString(R.string.TEXT_SELECTOR_CURRENCY));
        }
    }

    public String getValueTerm() {
        try {
            return getTermView().getText().toString();
        } catch (Exception unused) {
            return null;
        }
    }

    public void setTermValue(String str) {
        getTermView().setText(str);
    }

    public void setListenerClickExpiredAction() {
        View findViewById = this.mMainView.findViewById(R.id.row_selector_expired_action_id);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OneClickListener(this));
        }
    }

    public void setListenerClickGoToCreateNewAction() {
        View findViewById = this.mMainView.findViewById(R.id.F10_00_btn_nuevoPlazoFijo);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OneClickListener(this));
        }
    }

    public void setListenerClickGoToRatesAction() {
        View findViewById = this.mMainView.findViewById(R.id.F10_00_lbl_linkTasas);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OneClickListener(this));
        }
        View findViewById2 = this.mMainView.findViewById(R.id.F10_CREATE_lbl_linkTasas);
        if (findViewById2 != null) {
            findViewById2.setOnClickListener(new OneClickListener(this));
        }
    }

    public void setListenerClickCambiarTipoCuentas() {
        View findViewById = this.mMainView.findViewById(R.id.selector_cuentas_privadas);
        Button button = (Button) this.mMainView.findViewById(R.id.F10_00_btn_nuevoPlazoFijo);
        if (this.mSessionManager.getLoginUnico().getDatosPersonales().getTipoCliente().equalsIgnoreCase(TipoCliente.BP.getTipoCliente()) || this.mSessionManager.getLoginUnico().getDatosPersonales().getTipoCliente().equalsIgnoreCase(TipoCliente.RTL.getTipoCliente())) {
            findViewById.setVisibility(8);
        } else {
            findViewById.setOnClickListener(new OneClickListener(this));
        }
        if (this.g.equals(TipoCliente.BP.getTipoCliente()) || this.g.equals(TipoCliente.BP_RTL.getTipoCliente())) {
            button.setVisibility(8);
            this.vtitlebp.setText(getResources().getString(R.string.TITLE_TENENCIA_DE_INVERSIONES_BANCA_PRIVADA));
        } else if (this.g.equals(TipoCliente.RTL.getTipoCliente())) {
            button.setVisibility(0);
            this.vtitlebp.setText(getResources().getString(R.string.TITLE_TENENCIA_DE_INVERSIONES_INDIVIDUOS));
        }
    }

    public void setListenerClickChangeCurrency() {
        View findViewById = this.mMainView.findViewById(R.id.F10_02_lnl_cambiarMoneda);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OneClickListener(this));
        }
    }

    public TextView getTermView() {
        return (TextView) this.mMainView.findViewById(R.id.input_term_id);
    }

    public void showDialogDate(String str, String str2) {
        FragmentManager supportFragmentManager = getActivity().getSupportFragmentManager();
        this.mDatePicker = IsbanDatePickerDialogFragment.newInstance(str, str2, Constants.FORMAT_DATE_APP);
        setListenerPickerExpiredDate();
        this.mDatePicker.show(supportFragmentManager, "DIALOG_PICKER");
    }

    public void setListenerPickerExpiredDate() {
        this.mDatePicker.setDialogListener(new IDateDialogListener() {
            public void onDateSelected(Date date) {
                PlazosFijoFragment.this.presenterLongTermDeposit.onExpiredDateSelected(date);
            }
        });
    }

    public String getValueExpiredDate() {
        TextView textView = (TextView) this.mMainView.findViewById(R.id.selector_date_id);
        return textView != null ? textView.getText().toString() : "";
    }

    public void setValueExpiredDate(String str) {
        TextView textView = (TextView) this.mMainView.findViewById(R.id.selector_date_id);
        if (textView != null) {
            textView.setText(str);
        }
    }

    public void errorEmptyAccount() {
        onShowError(getString(R.string.IDX_ALERT_LBL_TITLE_ERROR), getString(R.string.ID546_PAYTRANSFIXED_ERROR_LBL_NOACCOUNTS));
        openMenu();
    }

    public void reloadFragment() {
        PlazosFijoFragment newInstance = newInstance(TipoCliente.RTL.getTipoCliente(), FragmentConstants.PLAZOS_FIJOS);
        newInstance.setTAG(FragmentConstants.PLAZOS_FIJOS);
        ((SantanderRioMainActivity) getActivity()).selectMenuItem(FragmentConstants.PLAZOS_FIJOS);
        ((SantanderRioMainActivity) getActivity()).changeFragmentAnimation(newInstance, R.anim.slide_in_right, R.anim.slide_out_left, R.anim.no_animation, R.anim.slide_out_right);
    }

    @OnClick({2131364194})
    public void onClickContinueInCreate() {
        this.presenterLongTermDeposit.onBtnContinueInCreateClicked();
    }

    @OnClick({2131364192})
    public void onClickConstituirInConfirm() {
        this.presenterLongTermDeposit.onBtnConstituirClicked();
    }

    @OnClick({2131364201})
    public void onClickReceipt() {
        onBackPressed();
    }

    public void onShowDialog(String str, String str2, String str3) {
        FragmentManager supportFragmentManager = getActivity().getSupportFragmentManager();
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(str, str2, str3, null, getString(R.string.BTN_DIALOG_ACCEPT), null, null, null);
        newInstance.setDialogListenerExtended(this);
        newInstance.show(supportFragmentManager, "DialogError");
    }

    public String getValueAmount() {
        NumericEditText numericEditText = (NumericEditText) this.mMainView.findViewById(R.id.input_amount_id);
        if (numericEditText != null) {
            return numericEditText.getText().toString();
        }
        return null;
    }

    public String getValueCurrency() {
        TextView textView = (TextView) this.mMainView.findViewById(R.id.selector_currency_id);
        if (textView != null) {
            return textView.getText().toString();
        }
        return null;
    }

    public String getValueExpiredAction() {
        TextView textView = (TextView) this.mMainView.findViewById(R.id.input_expired_action_id);
        if (textView != null) {
            return textView.getText().toString();
        }
        return null;
    }

    public void setValueExpiredAction(String str) {
        TextView textView = (TextView) this.mMainView.findViewById(R.id.input_expired_action_id);
        if (textView != null) {
            textView.setText(str);
        }
    }

    public String getValueChangeCurrencyAction() {
        TextView textView = (TextView) this.mMainView.findViewById(R.id.F10_02_lbl_tipoPlazoFijo);
        if (textView != null) {
            return textView.getText().toString();
        }
        return null;
    }

    public void setValueChangeCurrencyAction(String str) {
        TextView textView = (TextView) this.mMainView.findViewById(R.id.F10_02_lbl_tipoPlazoFijo);
        if (textView != null) {
            textView.setText(str);
        }
    }

    public void showDialogLoading() {
        if (this.d != null && !this.d.isVisible()) {
            this.d.show(getActivity().getSupportFragmentManager(), "loading");
        }
    }

    public void closeDialogLoading() {
        if (this.d != null) {
            this.d.dismiss();
        }
    }

    public int getCurrentIndexViewPage() {
        if (this.mControlPager != null) {
            return this.mControlPager.getDisplayedChild();
        }
        return -1;
    }

    public View getViewPageFromIndex(int i2) {
        return this.mControlPager.getChildAt(i2);
    }

    public EditText getViewTerm() {
        return (EditText) this.mMainView.findViewById(R.id.input_term_id);
    }

    public void setEventChangedValueTerm() {
        final EditText viewTerm = getViewTerm();
        if (viewTerm != null) {
            viewTerm.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                public void afterTextChanged(Editable editable) {
                    String obj = editable.toString();
                    if (obj.startsWith("0")) {
                        obj = obj.substring(1, obj.length());
                        viewTerm.setText(obj);
                    }
                    String expirationDate = PlazosFijoFragment.this.presenterLongTermDeposit.getExpirationDate(obj);
                    if (UtilDate.isDate(expirationDate, Constants.FORMAT_DATE_APP_2)) {
                        PlazosFijoFragment.this.setValueExpiredDate(expirationDate);
                    }
                }
            });
        }
    }

    public void setEventLostFocusTerm() {
        final EditText viewTerm = getViewTerm();
        if (viewTerm != null) {
            viewTerm.setOnFocusChangeListener(new OnFocusChangeListener() {
                public void onFocusChange(View view, boolean z) {
                    if (!z) {
                        if (viewTerm.length() == 0) {
                            viewTerm.setText("0");
                        }
                    } else if (viewTerm.length() == 1 && "0".equals(viewTerm.getText().toString())) {
                        viewTerm.setText("");
                    }
                }
            });
        }
    }

    public void onShowDialogConfirmPayment(String str, String str2) {
        FragmentManager supportFragmentManager = getActivity().getSupportFragmentManager();
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("dialog_confirm", str, str2, null, null, getString(R.string.IDX_ALERT_BTN_YES), getString(R.string.IDX_ALERT_BTN_NO), null);
        newInstance.setDialogListenerExtended(this);
        newInstance.show(supportFragmentManager, "DialogConfirm");
    }

    public void onShowError(String str, String str2) {
        FragmentManager supportFragmentManager = getActivity().getSupportFragmentManager();
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("dialog_error", str, str2, null, getString(R.string.BTN_DIALOG_ACCEPT), null, null, null);
        newInstance.setDialogListenerExtended(this);
        newInstance.show(supportFragmentManager, "DialogError");
    }

    public void setLegendConfirm(String str) {
        if (this.tvLegendConfirm != null) {
            this.tvLegendConfirm.setText(Html.fromHtml(str));
            try {
                this.tvLegendConfirm.setContentDescription(CAccessibility.getInstance(getActContext()).applyFilterGeneral(this.tvLegendConfirm.getText().toString()));
            } catch (Exception unused) {
            }
        }
    }

    public void setLegendReceipt(String str) {
        if (this.tvLegendReceipt != null) {
            this.tvLegendReceipt.setText(Html.fromHtml(str));
            try {
                this.tvLegendReceipt.setContentDescription(CAccessibility.getInstance(getActContext()).applyFilterGeneral(this.tvLegendReceipt.getText().toString()));
            } catch (Exception unused) {
            }
        }
    }

    public void hideLegendConfirm() {
        if (this.vgLegendConfirm != null) {
            this.vgLegendConfirm.setVisibility(8);
        }
    }

    public void showLegendConfirm() {
        if (this.vgLegendConfirm != null) {
            this.vgLegendConfirm.setVisibility(0);
        }
    }

    public void hideLegendReceipt() {
        if (this.vgLegendReceipt != null) {
            this.vgLegendReceipt.setVisibility(8);
        }
    }

    public void showLegendReceipt() {
        if (this.vgLegendReceipt != null) {
            this.vgLegendReceipt.setVisibility(0);
        }
    }

    public void onDispatchEventsError(WebServiceEvent webServiceEvent) {
        if (getErrorListener() != null) {
            getErrorListener().onWebServiceErrorEvent(webServiceEvent, getTAG());
        }
    }

    public void onTopPageCreate() {
        a(this.svPageCreate);
    }

    public void onTopPageConfirm() {
        a(this.svPageConfirm);
    }

    public void onTopPageReceipt() {
        a(this.svPageReceipt);
    }

    private void a(final ScrollView scrollView) {
        if (scrollView != null) {
            scrollView.post(new Runnable() {
                public void run() {
                    scrollView.scrollTo(0, 0);
                }
            });
        }
    }

    public void loadBarReturn() {
        ((BaseActivity) getActivity()).setActionBarType(ActionBarType.BACK_ONLY);
        z();
        C().lockMenu(true);
    }

    public void loadBarClose() {
        ((BaseActivity) getActivity()).setActionBarType(ActionBarType.PUSH_CLOSE);
        A();
        C().lockMenu(true);
    }

    public void loadBarShare() {
        ((BaseActivity) getActivity()).setActionBarType(ActionBarType.SHARE);
        B();
        C().lockMenu(false);
    }

    public void restartMainActionBar() {
        C().restartActionBar();
        C().lockMenu(false);
        ((BaseActivity) getActivity()).setActionBarType(ActionBarType.MENU);
        View findViewById = ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView().findViewById(R.id.toggle);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OneClickListener(new OneClicked() {
                public void onClicked(View view) {
                    PlazosFijoFragment.this.switchDrawer();
                }
            }));
        }
    }

    private void z() {
        View customView = ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView();
        if (customView != null) {
            View findViewById = customView.findViewById(R.id.back_imgButton);
            if (findViewById != null) {
                findViewById.setOnClickListener(new OneClickListener(new OneClicked() {
                    public void onClicked(View view) {
                        PlazosFijoFragment.this.onBackPressed();
                    }
                }));
            }
        }
    }

    private void A() {
        View customView = ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView();
        if (customView != null) {
            View findViewById = customView.findViewById(R.id.ok);
            if (findViewById != null) {
                findViewById.setOnClickListener(new OneClickListener(new OneClicked() {
                    public void onClicked(View view) {
                        PlazosFijoFragment.this.onBackPressed();
                    }
                }));
            }
        }
    }

    private void B() {
        View customView = ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView();
        if (customView != null) {
            View findViewById = customView.findViewById(R.id.share);
            if (findViewById != null) {
                findViewById.setOnClickListener(new OneClickListener(new OneClicked() {
                    public void onClicked(View view) {
                        PlazosFijoFragment.this.onClickShowActionShareReceipt();
                    }
                }));
            }
            View findViewById2 = customView.findViewById(R.id.toggle);
            if (findViewById2 != null) {
                findViewById2.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (PlazosFijoFragment.this.isForgetShareReceipt()) {
                            PlazosFijoFragment.this.rememberShareReceipt();
                        } else {
                            PlazosFijoFragment.this.switchDrawer();
                        }
                    }
                });
            }
        }
    }

    public void onClickShowActionShareReceipt() {
        this.b = true;
        D();
        if (this.c != null) {
            this.c.showWithCancel();
        }
        this.b = true;
    }

    private SantanderRioMainActivity C() {
        return (SantanderRioMainActivity) getActivity();
    }

    public void actionBarLoaded(int i2, View view) {
        if (i2 == 0) {
            z();
        } else if (i2 == 1) {
            B();
        } else if (i2 == 2) {
            A();
        }
    }

    public void onPause() {
        super.onPause();
        this.presenterLongTermDeposit.onPauseEvent();
    }

    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public void configActionShareReceipt(String str, String str2) {
        final String str3 = str2;
        final String str4 = str;
        AnonymousClass14 r0 = new OptionsToShareImpl(this, getActivity().getApplicationContext(), getActivity().getSupportFragmentManager()) {
            public View getViewToShare() {
                return PlazosFijoFragment.this.svPageReceipt;
            }

            public void receiveIntentAppShare(Intent intent) {
                PlazosFijoFragment.this.startActivityForResult(Intent.createChooser(intent, PlazosFijoFragment.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)), 999);
            }

            public String getFileName() {
                return str3;
            }

            public String getSubjectReceiptToShare() {
                return str4;
            }

            public void optionDownloadSelected() {
                super.optionDownloadSelected();
            }

            public void optionShareSelected() {
                super.optionShareSelected();
            }

            public void optionCancelSelected() {
                super.optionCancelSelected();
            }
        };
        this.c = r0;
    }

    public void showRequestPermissionExplation(final int i2) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getString(R.string.write_external_permission_request_message), null, getString(R.string.BTN_DIALOG_ACCEPT), null, null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
                PlazosFijoFragment.this.rememberShareReceipt();
            }

            public void onSimpleActionButton() {
                if (VERSION.SDK_INT >= 23) {
                    PlazosFijoFragment.this.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, i2);
                }
            }
        });
        newInstance.show(getFragmentManager(), OptionsToShareImpl.PERMISSION_DIALOG_TAG);
    }

    public void onRequestPermissionsResult(int i2, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i2, strArr, iArr);
        this.c.onRequestPermissionsResult(i2, strArr, iArr);
    }

    public void rememberShareReceipt() {
        if (!this.b) {
            if (this.c == null) {
                D();
            }
            this.b = true;
            this.c.showAlert();
        }
    }

    private void D() {
        configActionShareReceipt(getString(R.string.IDXX_LONG_TERM_APP_SHARE), getString(R.string.IDXX_LONG_TERM_APP_SHARE).concat("-").concat(this.presenterLongTermDeposit.getNumComprobante()));
    }

    public boolean isForgetShareReceipt() {
        return getCurrentIndexViewPage() == 5 && !this.b;
    }

    public void setTasas() {
        if (this.g.equals(TipoCliente.BP.getTipoCliente()) || this.g.equals(TipoCliente.BP_RTL.getTipoCliente())) {
            this.btnLinkTasas.setVisibility(8);
        } else {
            this.btnLinkTasas.setVisibility(0);
        }
    }

    public void onActivityResult(int i2, int i3, Intent intent) {
        if (i2 == 999) {
            UtilFile.deleteDirectory(new File(UtilFile.getPathReceiptTmp()));
        } else {
            super.onActivityResult(i2, i3, intent);
        }
    }

    public void selecTipoCuenta() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getString(R.string.ID_4297_INVERSIONES_LBL_INVERSIONES_BANCA_PRIVADA));
        arrayList.add(getString(R.string.ID_4298_INVERSIONES_LBL_INVERSIONES));
        final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.IDXX_POP_UP_HOME), null, null, arrayList, getString(R.string.IDX_ALERT_BTN_CANCEL), null, null, null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onItemSelected(String str) {
                if (str.equals(PlazosFijoFragment.this.getString(R.string.ID_4297_INVERSIONES_LBL_INVERSIONES_BANCA_PRIVADA))) {
                    PlazosFijoFragment.this.i.registerEventCambiarCuentaTenenciaPlazoFijoBP();
                    PlazosFijoFragment.this.vtitlebp.setText(PlazosFijoFragment.this.getText(R.string.ID_4297_INVERSIONES_LBL_INVERSIONES_BANCA_PRIVADA));
                    PlazosFijoFragment.this.g = TipoCliente.BP.getTipoCliente();
                    PlazosFijoFragment.this.presenterLongTermDeposit.updateMainView(TipoCliente.BP.getTipoCliente());
                } else if (str.equals(PlazosFijoFragment.this.getString(R.string.ID_4298_INVERSIONES_LBL_INVERSIONES))) {
                    PlazosFijoFragment.this.i.registerEventCambiarCuentaTenenciaPlazoFijoRTL();
                    PlazosFijoFragment.this.vtitlebp.setText(PlazosFijoFragment.this.getText(R.string.ID_4298_INVERSIONES_LBL_INVERSIONES));
                    PlazosFijoFragment.this.g = TipoCliente.RTL.getTipoCliente();
                    PlazosFijoFragment.this.presenterLongTermDeposit.updateMainView(TipoCliente.RTL.getTipoCliente());
                }
            }

            public void onSimpleActionButton() {
                newInstance.closeDialog();
            }
        });
        newInstance.show(getFragmentManager(), getString(R.string.IDXX_POP_UP_HOME));
    }

    public void onBackPressed() {
        int currentIndexViewPage = getCurrentIndexViewPage();
        if (currentIndexViewPage == 0) {
            switchDrawer();
            ((BaseActivity) getActivity()).setActionBarType(ActionBarType.MENU);
            View findViewById = ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView().findViewById(R.id.toggle);
            if (findViewById != null) {
                findViewById.setOnClickListener(new OneClickListener(new OneClicked() {
                    public void onClicked(View view) {
                        PlazosFijoFragment.this.switchDrawer();
                    }
                }));
            }
        } else if (isForgetShareReceipt()) {
            rememberShareReceipt();
        } else {
            C().hideKeyboard();
            this.presenterLongTermDeposit.backPagePressed(currentIndexViewPage);
        }
    }
}
