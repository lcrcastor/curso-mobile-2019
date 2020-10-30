package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import ar.com.santander.rio.mbanking.app.commons.CCreditos;
import ar.com.santander.rio.mbanking.app.commons.CPageAnimationViewFlipper;
import ar.com.santander.rio.mbanking.app.commons.CustomActionBarListener;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.module.tenenciacreditos.t.TenenciaCreditosPresenter;
import ar.com.santander.rio.mbanking.app.module.tenenciacreditos.t.TenenciaCreditosPresenterImp;
import ar.com.santander.rio.mbanking.app.module.tenenciacreditos.t.TenenciaCreditosView;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.activities.ListDetailsActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.ProximasCuotasActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.constants.CreditosConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.PagoTarjetasConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.EmptyFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDatePickerDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDatePickerDialogFragment.IDateDialogListener;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListenerExtended;
import ar.com.santander.rio.mbanking.components.ProgresIndicator;
import ar.com.santander.rio.mbanking.components.share.OptionsToShare;
import ar.com.santander.rio.mbanking.components.share.OptionsToShareImpl;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.ConfirmarPagoEvent;
import ar.com.santander.rio.mbanking.services.events.ConsultaPrestamosPermitidosEvent;
import ar.com.santander.rio.mbanking.services.events.ConsultaSolicitudCrediticiaEvent;
import ar.com.santander.rio.mbanking.services.events.GetDetalleCuotaTenenciaCreditoEvent;
import ar.com.santander.rio.mbanking.services.events.GetProximasCuotasCreditoEvent;
import ar.com.santander.rio.mbanking.services.events.GetTenenciaCreditosEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.model.ProximasCuotasModel.ProximasCuotas;
import ar.com.santander.rio.mbanking.services.model.creditos.DatosCuenta;
import ar.com.santander.rio.mbanking.services.model.creditos.ListaCuentas;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.model.general.Cuentas;
import ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.CategoriaCredito;
import ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.CuentaOperativa;
import ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.DatosCredito;
import ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.DatosLeyenda;
import ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.Listaleyendas;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaPrestamosPermitidosResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaSolicitudCrediticiaResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AccountRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AccountResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsTenenciasDatosPFBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConfirmarPagoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaPrestamosPermitidosBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaPrestamosPermitidosBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaSolicitudCrediticiaBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaSolicitudCrediticiaBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetDetalleCuotaTenenciaCreditoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetTenenciaCreditosBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetTenenciaCreditos;
import ar.com.santander.rio.mbanking.utils.UtilAccount;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import ar.com.santander.rio.mbanking.utils.UtilFile;
import ar.com.santander.rio.mbanking.utils.Utils;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import ar.com.santander.rio.mbanking.view.AmountView;
import ar.com.santander.rio.mbanking.view.NumericEditText;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.squareup.otto.Subscribe;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;

public class TenenciaCreditosFragment extends BaseFragment implements OnClickListener, CustomActionBarListener, TenenciaCreditosView, IDialogListenerExtended {
    public static final String ACCOUNTNAME = "ACCOUNTNAME";
    public static final String AMOUNT = "AMOUNT";
    public static final String CHARACTERTOCHARACTER = "CHARACTERTOCHARACTER";
    public static final String DATE = "DATE";
    public static final String DIALOG_SELECTOR = "DialogSelector";
    public static final String GENERAL = "GENERAL";
    public static final String IDENTIFICADOR_UVA = "UVA";
    public static final String ID_DIALOG_CONFIRM = "dialog_confirm";
    public static final String ID_DIALOG_ERROR = "dialog_error";
    public static final String ID_DIALOG_SELECTOR_ACCOUNT = "selector_account";
    public static final String ID_DIALOG_SELECTOR_CURRENCY = "selector_currency";
    public static final String ID_DIALOG_SELECTOR_CURRENCY_RATES = "selector_currency_rates";
    public static final String ID_DIALOG_SELECTOR_EXPIRED_ACTION = "selector_expired_action";
    public static final int INDEX_PAGE_DETAIL = 1;
    public static final int INDEX_PAGE_LIST = 0;
    public static final int INDEX_PAGE_RECEIPT = 4;
    public static final String LEYENDA = "LEYENDA";
    public static final String OPCION_PROX_CUOTA_NEGATIVE_VALUE = "N";
    public static final String PERCENTAJE = "PERCENTAJE";
    public static final String PLAZOFIJO_INFO_ADICIONAL = "1";
    public static final String PLAZOFIJO_SUBPRODUCTO_UVA_PROCREAR = "0062";
    public static final String PLAZOFIJO_SUBPRODUCTO_UVI = "0060";
    public static final String POSITIVE_HAS_PAGAR_CREDITO_OPTION = "S";

    /* renamed from: VER_DETALLE_DEL_PRÉSTAMO_OPTION reason: contains not printable characters */
    public static final String f232VER_DETALLE_DEL_PRSTAMO_OPTION = "Ver detalle del préstamo";
    public static final String VER_PROXIMAS_CUOTAS_OPTION = "Ver próximas cuotas";
    @InjectView(2131361794)
    public TextView CFTNAData;
    @InjectView(2131361796)
    public RelativeLayout CFTNARl;
    @InjectView(2131361797)
    public TextView CFTNAsIvaData;
    @InjectView(2131362159)
    public TextView CFTNAsIvaName;
    @InjectView(2131361798)
    public RelativeLayout CFTNAsIvaRl;
    @InjectView(2131362205)
    public RelativeLayout F10_01_rlt_titular;
    @InjectView(2131364093)
    public TextView OtrosImpuestosPaymentCreditData;
    private long a = 0;
    @InjectView(2131365312)
    public ImageView accountArrowImageView;
    @InjectView(2131364113)
    public TextView accountData;
    @InjectView(2131364116)
    public RelativeLayout accountRl;
    private CategoriaCredito ad;
    /* access modifiers changed from: private */
    public DatosCredito ae;
    private ConfirmarPagoBodyResponseBean af;
    private CuentaOperativa ag;
    /* access modifiers changed from: private */
    public Listaleyendas ah;
    /* access modifiers changed from: private */
    public ConsultaSolicitudCrediticiaBodyResponseBean ai;
    /* access modifiers changed from: private */
    public List<Cuenta> aj = new ArrayList();
    @InjectView(2131364144)
    public TextView ajusteCapitalMoraData;
    @InjectView(2131364146)
    public RelativeLayout ajusteCapitalMoraRl;
    @InjectView(2131364145)
    public TextView ajuste_capital_mora_data_payment;
    @InjectView(2131364147)
    public RelativeLayout ajuste_capital_mora_rl_payment;
    /* access modifiers changed from: private */
    public AccountRequestBean ak;
    /* access modifiers changed from: private */
    public ConsultaPrestamosPermitidosBodyResponseBean al;
    private String am;
    /* access modifiers changed from: private */
    public GetTenenciaCreditosBodyResponseBean b;
    @InjectView(2131364192)
    public Button btnConstituirInConfirm;
    @InjectView(2131364194)
    public Button btnContinueInCreate;
    @InjectView(2131364201)
    public Button btnReturnInReceipt;
    /* access modifiers changed from: private */
    public boolean c = false;
    public CAccessibility cAccessibility;
    @InjectView(2131364247)
    public TextView capitalData;
    @InjectView(2131364248)
    public RelativeLayout capitalRl;
    @InjectView(2131364249)
    public TextView capital_uva_data;
    @InjectView(2131364250)
    public TextView capital_uva_dataPayment;
    @InjectView(2131364251)
    public TextView capital_uva_dataReceip;
    @InjectView(2131364252)
    public RelativeLayout capital_uva_rl;
    @InjectView(2131364253)
    public RelativeLayout capital_uva_rl_Payment;
    @InjectView(2131364254)
    public RelativeLayout capital_uva_rl_Receip;
    @InjectView(2131364256)
    public TextView cargoSeguroVidaData;
    @InjectView(2131364257)
    public RelativeLayout cargoSeguroVidaRl;
    @InjectView(2131361795)
    public TextView cftnaLabel;
    @InjectView(2131364305)
    public TextView coeficienteData;
    @InjectView(2131364306)
    public RelativeLayout coeficienteRl;
    @InjectView(2131364352)
    public RelativeLayout condicion_uva_compr_layout;
    @InjectView(2131364355)
    public TextView confirmCuota;
    @InjectView(2131364356)
    public TextView confirmFechaVencimiento;
    @InjectView(2131364357)
    public AmountView confirmHeaderData;
    @InjectView(2131364358)
    public TextView confirmHeaderDetail;
    @InjectView(2131364359)
    public TextView confirmHeaderTitle;
    @InjectView(2131364361)
    public TextView confirmImporteCuota;
    @InjectView(2131362190)
    public TextView confirmImporteCuotaField;
    @InjectView(2131364362)
    public TextView confirmMontoCuenta;
    @InjectView(2131365842)
    Button confirmNextButton;
    @InjectView(2131364363)
    public TextView confirmNroCuenta;
    @InjectView(2131362203)
    public LinearLayout confirmPagodetail;
    @InjectView(2131364393)
    public TextView confirmPlazoPrestamo;
    @InjectView(2131364405)
    public TextView confirmTipoPrestamo;
    @InjectView(2131364406)
    public TextView confirmTipoPrestamoLabel;
    @InjectView(2131364407)
    public TextView confirmTitle;
    @InjectView(2131364440)
    public LinearLayout creditAccountRow;
    @InjectView(2131364484)
    public AmountView creditHeaderData;
    @InjectView(2131364485)
    public TextView creditHeaderDesc;
    @InjectView(2131364486)
    public TextView creditHeaderTitle;
    @InjectView(2131364071)
    public TextView creditTitle;
    @InjectView(2131364488)
    public TextView creditUVAMsg;
    @InjectView(2131364562)
    public RelativeLayout cuil_compr_layout;
    @InjectView(2131364564)
    public TextView cuotaNumberData;
    @InjectView(2131364565)
    public RelativeLayout cuotaNumberRl;
    @InjectView(2131364566)
    public TextView cuotaValueData;
    @InjectView(2131364567)
    public RelativeLayout cuotaValueRl;
    private OptionsToShare d;
    @InjectView(2131364576)
    public TextView dateData;
    @InjectView(2131364583)
    public RelativeLayout dateRl;
    @InjectView(2131365843)
    Button detailNextButton;
    @InjectView(2131364625)
    public TextView disponibleUsoData;
    @InjectView(2131364626)
    public RelativeLayout disponibleUsoRl;
    private View e;
    private View f;
    @InjectView(2131364695)
    public RelativeLayout fechaCotizacionDeUVAs_rl;
    @InjectView(2131364696)
    public TextView fechaDeCotizacionDeUVAs_data;
    @InjectView(2131364697)
    public TextView fechaVtoData;
    @InjectView(2131364698)
    public RelativeLayout fechaVtoRl;
    private View g;
    @InjectView(2131364604)
    public LinearLayout getConfirmPayment;
    @InjectView(2131364720)
    public TextView goodAssuranceData;
    @InjectView(2131364721)
    public RelativeLayout goodAssuranceRl;
    private ProgresIndicator h;
    private GetDetalleCuotaTenenciaCreditoBodyResponseBean i;
    @InjectView(2131364803)
    public ImageView imgErrorConfirmPago;
    @InjectView(2131364842)
    public TextView interesPesosUvaData;
    @InjectView(2131364843)
    public RelativeLayout interesPesosUvaRl;
    @InjectView(2131364844)
    public TextView interesesCompData;
    @InjectView(2131364845)
    public RelativeLayout interesesCompRl;
    @InjectView(2131364847)
    public TextView interesesPunitoriosData;
    @InjectView(2131364849)
    public RelativeLayout interesesPunitoriosRl;
    @InjectView(2131364848)
    public TextView intereses_punitorios_data_payment;
    @InjectView(2131364850)
    public RelativeLayout intereses_punitorios_rl_payment;
    @InjectView(2131364853)
    public TextView intereses_uva_data;
    @InjectView(2131364854)
    public TextView intereses_uva_data_Payment;
    @InjectView(2131364855)
    public TextView intereses_uva_data_Receip;
    @InjectView(2131364856)
    public RelativeLayout intereses_uva_rl;
    @InjectView(2131364857)
    public RelativeLayout intereses_uva_rl_Payment;
    @InjectView(2131364858)
    public RelativeLayout intereses_uva_rl_Receip;
    @InjectView(2131364874)
    public RelativeLayout iva_rl;
    @InjectView(2131362219)
    public TextView lblMontoMinimo;
    @InjectView(2131362201)
    public TextView lbl_de_cuenta;
    @InjectView(2131362140)
    public TextView lbl_errorWs;
    @InjectView(2131364996)
    public TextView leyendaData;
    @InjectView(2131365002)
    public RelativeLayout leyendaRl;
    @InjectView(2131365003)
    public TextView leyendaTasaDetalle;
    @InjectView(2131365005)
    public TextView leyendaTasaPayment;
    @InjectView(2131364997)
    public TextView leyenda_data_2;
    @InjectView(2131364998)
    public TextView leyenda_data_confirm;
    @InjectView(2131365000)
    public RelativeLayout leyenda_r2;
    @InjectView(2131365001)
    public RelativeLayout leyenda_r3;
    @InjectView(2131365006)
    public TextView leyenda_tasa_receipt;
    @InjectView(2131365021)
    public TextView lineaCreditoData;
    @InjectView(2131365022)
    public RelativeLayout lineaCreditoRl;
    @InjectView(2131365057)
    public LinearLayout linearLayoutIVAPaymentCredit;
    @InjectView(2131365058)
    public LinearLayout linearLayoutIVAReceipTenenciaCreditos;
    @InjectView(2131362152)
    public LinearLayout lnlCapitalUvi;
    @InjectView(2131365157)
    public TextView loanNumberData;
    @InjectView(2131365158)
    public RelativeLayout loanNumberRl;
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
    @InjectView(2131364666)
    public TextView msgErrorConfirmPago;
    @InjectView(2131365286)
    public View pageConfirm;
    @InjectView(2131365287)
    public View pageConfirmPF;
    @InjectView(2131365290)
    public View pageDetail;
    @InjectView(2131365291)
    public View pageDetailPF;
    @InjectView(2131365292)
    public View pageList;
    @InjectView(2131365293)
    public View pageListPF;
    @InjectView(2131365294)
    public View pagePayment;
    @InjectView(2131365296)
    public View pagePaymentPF;
    @InjectView(2131365297)
    public View pageReceipt;
    @InjectView(2131365298)
    public View pageReceiptPF;
    @InjectView(2131365313)
    public TextView paymentCapitalData;
    @InjectView(2131365314)
    public TextView paymentCftnaData;
    @InjectView(2131362166)
    public TextView paymentCftnaName;
    @InjectView(2131365315)
    public TextView paymentCftnaSivaData;
    @InjectView(2131362167)
    public TextView paymentCftnaSivaName;
    @InjectView(2131365316)
    public TextView paymentCuota;
    @InjectView(2131365317)
    public TextView paymentFechaVencimiento;
    @InjectView(2131365318)
    public AmountView paymentHeaderData;
    @InjectView(2131365319)
    public TextView paymentHeaderDetail;
    @InjectView(2131365320)
    public TextView paymentHeaderTitle;
    @InjectView(2131365321)
    public TextView paymentImporteCuotaData;
    @InjectView(2131365322)
    public TextView paymentImpuestosIvaData;
    @InjectView(2131365323)
    public TextView paymentImpuestosSelladoData;
    @InjectView(2131365324)
    public TextView paymentInteresesCompData;
    @InjectView(2131365844)
    Button paymentNextButton;
    @InjectView(2131365326)
    public TextView paymentPlazoPrestamo;
    @InjectView(2131365327)
    public TextView paymentSeguroVidaData;
    @InjectView(2131365328)
    public TextView paymentTeaData;
    @InjectView(2131362165)
    public TextView paymentTeaName;
    @InjectView(2131365329)
    public TextView paymentTipoPrestamo;
    @InjectView(2131365330)
    public TextView paymentTipoPrestamoLabel;
    @InjectView(2131365331)
    public TextView paymentTitle;
    @InjectView(2131365332)
    public TextView paymentTnaData;
    @InjectView(2131362164)
    public TextView paymentTnaName;
    @InjectView(2131365325)
    public TextView payment_monto_cuenta;
    public TenenciaCreditosPresenter presenterLongTermDeposit;
    @InjectView(2131365462)
    public RelativeLayout ratesRl;
    @InjectView(2131365472)
    public TextView receip_textViewSeguroDeVida_data;
    @InjectView(2131365475)
    public TextView receiptCapitalData;
    @InjectView(2131365476)
    public TextView receiptComprobanteData;
    @InjectView(2131365477)
    public TextView receiptFechaData;
    @InjectView(2131365488)
    public AmountView receiptHeaderData;
    @InjectView(2131365489)
    public TextView receiptHeaderDetail;
    @InjectView(2131365490)
    public TextView receiptHeaderTitle;
    @InjectView(2131365478)
    public TextView receiptImporteCuotaData;
    @InjectView(2131365479)
    public TextView receiptInteresesCompData;
    @InjectView(2131365480)
    public TextView receiptInteresesPunitoriosData;
    @InjectView(2131365845)
    Button receiptNextButton;
    @InjectView(2131365481)
    public TextView receiptOtrosImpuestosData;
    @InjectView(2131365493)
    public TextView receiptPrestamoData;
    @InjectView(2131365482)
    public TextView receiptTeaData;
    @InjectView(2131365494)
    public TextView receiptTipoPrestamoLabel;
    @InjectView(2131365483)
    public TextView receiptTnaData;
    @InjectView(2131365473)
    public TextView receipt_CFTEA_SinIVA_data;
    @InjectView(2131365474)
    public TextView receipt_CFTEA_data;
    @InjectView(2131365484)
    public TextView reciptCuentaDebitoData;
    @InjectView(2131365486)
    public TextView reciptCuotaData;
    @InjectView(2131365487)
    public TextView reciptFechaVencimientoData;
    @InjectView(2131365492)
    public TextView reciptPlazoPrestamoData;
    @InjectView(2131365495)
    public TextView reciptSaldoCapitalData;
    @InjectView(2131365496)
    public TextView reciptTitle;
    @InjectView(2131365485)
    public TextView recipt_cuil_data;
    @InjectView(2131365491)
    public TextView recipt_iva_data;
    @InjectView(2131365497)
    public TextView recipt_titular_data;
    @InjectView(2131365508)
    public RelativeLayout relativeImporteDeCuotaPuraUVAS;
    @InjectView(2131365509)
    public RelativeLayout relativeImporteDeCuotaPuraUVASPayment;
    @InjectView(2131365510)
    public RelativeLayout relativeImporteDeCuotaPuraUVASReceip;
    @InjectView(2131365516)
    public RelativeLayout relativeLayoutCFTEA;
    @InjectView(2131365517)
    public RelativeLayout relativeLayoutCFTEASinIVA;
    @InjectView(2131365518)
    public RelativeLayout relativeLayoutCFTNA;
    @InjectView(2131365519)
    public RelativeLayout relativeLayoutCFTNASiva;
    @InjectView(2131365520)
    public LinearLayout relativeLayoutCapital;
    @InjectView(2131365521)
    RelativeLayout relativeLayoutCapitalReceipTenenciaCreditos;
    @InjectView(2131365522)
    public LinearLayout relativeLayoutCargoPorSeguroDeVida;
    @InjectView(2131365523)
    public RelativeLayout relativeLayoutCuotaConfirmTenenciaCredito;
    @InjectView(2131365524)
    public RelativeLayout relativeLayoutCuotaReceipTenenciaCreditos;
    @InjectView(2131365525)
    public RelativeLayout relativeLayoutDatosCuenta;
    @InjectView(2131365526)
    public LinearLayout relativeLayoutFechaDeVencimiento;
    @InjectView(2131365527)
    public RelativeLayout relativeLayoutFechaDeVencimientoReceipTenenciaCreditos;
    @InjectView(2131365528)
    public RelativeLayout relativeLayoutFechaVencimientoConfirmTenenciaCredito;
    @InjectView(2131365530)
    public RelativeLayout relativeLayoutImporteCuotaReceipTenenciaCreditos;
    @InjectView(2131365531)
    public LinearLayout relativeLayoutImporteDeLaCuota;
    @InjectView(2131365532)
    public RelativeLayout relativeLayoutImporteDeLaCuotaConfirmTenenciaCredito;
    @InjectView(2131365533)
    public LinearLayout relativeLayoutImpuestosDeSellado;
    @InjectView(2131365534)
    public LinearLayout relativeLayoutImpuestosIVA;
    @InjectView(2131365535)
    public RelativeLayout relativeLayoutInteresesCompensatoriosPeriodoReceipTenenciaCreditos;
    @InjectView(2131365536)
    public LinearLayout relativeLayoutInteresesDelPeriodo;
    @InjectView(2131365537)
    public RelativeLayout relativeLayoutInteresesPunitoriosReceipTenenciaCreditos;
    @InjectView(2131365542)
    public LinearLayout relativeLayoutOtrosImpuestosPaymentCredit;
    @InjectView(2131365543)
    public RelativeLayout relativeLayoutOtrosImpuestosReceipTenenciaCreditos;
    @InjectView(2131365545)
    public LinearLayout relativeLayoutPlazoDePrestamo;
    @InjectView(2131365546)
    public RelativeLayout relativeLayoutPlazoDePrestamoReceipTenenciaCreditos;
    @InjectView(2131365547)
    public RelativeLayout relativeLayoutPlazoPrestamoConfirmTenenciaCredito;
    @InjectView(2131365549)
    public RelativeLayout relativeLayoutSaldoAnteriorDelCapitalSinAjustarReceipTenenciaCreditos;
    @InjectView(2131365550)
    public RelativeLayout relativeLayoutSeguroDeVida;
    @InjectView(2131365551)
    public RelativeLayout relativeLayoutTEA;
    @InjectView(2131365552)
    public RelativeLayout relativeLayoutTNA;
    @InjectView(2131365553)
    public RelativeLayout relativeLayoutTasaEfectivaAnualReceipTenenciaCreditos;
    @InjectView(2131365554)
    public RelativeLayout relativeLayoutTasaNominalAnualReceipTenenciaCreditos;
    @InjectView(2131365555)
    public RelativeLayout relativeLayoutTasas;
    @InjectView(2131362192)
    public TextView saldoAnteriorDeCapital;
    @InjectView(2131365616)
    public TextView saldoAnteriorSinAjustarData;
    @InjectView(2131365617)
    public RelativeLayout saldoAnteriorSinAjustarRl;
    @InjectView(2131365646)
    public ScrollView scrollViewConfirm;
    @InjectView(2131365647)
    public ScrollView scrollViewPayment;
    @InjectView(2131365648)
    public ScrollView scrollViewReceipt;
    @InjectView(2131365670)
    public TextView seguroIncendioData;
    @InjectView(2131365671)
    public RelativeLayout seguroIncendioRl;
    @InjectView(2131365683)
    public TextView selector_account_id_2;
    @InjectView(2131365692)
    public View separador4_compr;
    @InjectView(2131365693)
    public View separador5_compr;
    @InjectView(2131365694)
    public View separador6_compr;
    @InjectView(2131365800)
    public ScrollView svPageConfirm;
    @InjectView(2131365801)
    public ScrollView svPageCreate;
    @InjectView(2131365803)
    public ScrollView svPageReceipt;
    @InjectView(2131365836)
    public TextView taxesData;
    @InjectView(2131365837)
    public RelativeLayout taxesRl;
    @InjectView(2131365839)
    public TextView teaData;
    @InjectView(2131362157)
    public TextView teaLabel;
    @InjectView(2131365840)
    public RelativeLayout teaRl;
    @InjectView(2131365939)
    public TextView textViewIVAData;
    @InjectView(2131365940)
    public TextView textViewIVADataPaymentCredit;
    @InjectView(2131365941)
    public TextView textViewIVADataReceipTenenciaCreditos;
    @InjectView(2131365942)
    public TextView textViewIVATittle;
    @InjectView(2131365943)
    public TextView textViewIVATittlePaymentCredit;
    @InjectView(2131365944)
    public TextView textViewIVATittleReceipTenenciaCreditos;
    @InjectView(2131365949)
    public TextView textViewImporteDeCuotaPuraUVAS_data;
    @InjectView(2131365950)
    public TextView textViewImporteDeCuotaPuraUVAS_dataPayment;
    @InjectView(2131365951)
    public TextView textViewImporteDeCuotaPuraUVAS_dataReceip;
    @InjectView(2131366052)
    public TextView tnaData;
    @InjectView(2131362158)
    public TextView tnaLabel;
    @InjectView(2131366053)
    public RelativeLayout tnaRl;
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
    @InjectView(2131362186)
    public TextView txtSucursalInDetail;
    @InjectView(2131362187)
    public TextView txtTasaInDetail;
    @InjectView(2131362197)
    public TextView txtTasaLabel;
    @InjectView(2131362188)
    public TextView txtValorUvaDetail;
    @InjectView(2131362202)
    public TextView txtVencimientoInDetail;
    @InjectView(2131362196)
    public TextView txtViewSaldoAnteriorLabel;
    @InjectView(2131366354)
    public TextView valor_uva_data;
    @InjectView(2131366355)
    public RelativeLayout valor_uva_rl;
    @InjectView(2131366367)
    public LinearLayout vgErrorWSLongTermDeposit;
    @InjectView(2131366368)
    public LinearLayout vgErrorWSRes;
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

    public void onSimpleActionButton(String str) {
    }

    public String safeString(String str) {
        return str == null ? "" : str;
    }

    public static TenenciaCreditosFragment getInstance() {
        return new TenenciaCreditosFragment();
    }

    public static TenenciaCreditosFragment getInstance(String str) {
        TenenciaCreditosFragment tenenciaCreditosFragment = new TenenciaCreditosFragment();
        tenenciaCreditosFragment.am = str;
        return tenenciaCreditosFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.presenterLongTermDeposit = new TenenciaCreditosPresenterImp(this);
        this.h = ProgresIndicator.newInstance(VGetTenenciaCreditos.nameService);
        setErrorListener(E());
        this.cAccessibility = CAccessibility.getInstance(getActContext());
    }

    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        this.mMainView = layoutInflater.inflate(R.layout.tenencia_creditos_fragment, viewGroup, false);
        ButterKnife.inject((Object) this, this.mMainView);
        if (!y()) {
            this.presenterLongTermDeposit.onLoad();
        } else {
            consultarSolicitudCrediticia();
        }
        this.detailNextButton.setOnClickListener(this);
        this.paymentNextButton.setOnClickListener(this);
        this.confirmNextButton.setOnClickListener(this);
        this.receiptNextButton.setOnClickListener(this);
        this.confirmImporteCuota.setOnClickListener(this);
        return this.mMainView;
    }

    private boolean y() {
        return this.am != null && this.am.equals(FragmentConstants.SOLICITAR_CREDITO);
    }

    public void enableAccountSelector() {
        this.creditAccountRow.setClickable(true);
        this.creditAccountRow.setOnClickListener(this);
        this.relativeLayoutDatosCuenta.setOnClickListener(this);
        this.accountArrowImageView.setVisibility(0);
    }

    public void disableAccountSelector() {
        this.creditAccountRow.setClickable(false);
        this.accountArrowImageView.setVisibility(8);
    }

    public IDataManager getDataManager() {
        return this.mDataManager;
    }

    @Subscribe
    public void onConfirmarPagoCuota(ConfirmarPagoEvent confirmarPagoEvent) {
        closeDialogLoading();
        final ConfirmarPagoEvent confirmarPagoEvent2 = confirmarPagoEvent;
        AnonymousClass1 r0 = new BaseWSResponseHandler(getActivity(), TypeOption.INITIAL_VIEW, FragmentConstants.TENENCIA_CREDITOS, this, (BaseActivity) getActivity()) {
            /* access modifiers changed from: protected */
            public void onOk() {
                TenenciaCreditosFragment.this.c = false;
                TenenciaCreditosFragment.this.presenterLongTermDeposit.onConfirmarPagoResponse(confirmarPagoEvent2, (BaseActivity) TenenciaCreditosFragment.this.getActivity(), FragmentConstants.CREDITOS, TenenciaCreditosFragment.this.getString(R.string.ID16_PRIVATEMENU_BTN_CREDITS));
            }
        };
        r0.handleWSResponse(confirmarPagoEvent);
    }

    @Subscribe
    public void onProximasCuotas(GetProximasCuotasCreditoEvent getProximasCuotasCreditoEvent) {
        closeDialogLoading();
        final GetProximasCuotasCreditoEvent getProximasCuotasCreditoEvent2 = getProximasCuotasCreditoEvent;
        AnonymousClass2 r0 = new BaseWSResponseHandler(getActivity(), TypeOption.INITIAL_VIEW, FragmentConstants.TENENCIA_CREDITOS, this, (BaseActivity) getActivity()) {
            /* access modifiers changed from: protected */
            public void onOk() {
                TenenciaCreditosFragment.this.presenterLongTermDeposit.onProximasCuotasOK(getProximasCuotasCreditoEvent2);
            }

            /* access modifiers changed from: protected */
            public void onRes3Error(WebServiceEvent webServiceEvent) {
                TenenciaCreditosFragment.this.onShowError(getProximasCuotasCreditoEvent2.getTitleToShow(), getProximasCuotasCreditoEvent2.getMessageToShow());
            }
        };
        r0.handleWSResponse(getProximasCuotasCreditoEvent);
    }

    @Subscribe
    public void onGetTenenciaCreditos(GetTenenciaCreditosEvent getTenenciaCreditosEvent) {
        closeDialogLoading();
        final GetTenenciaCreditosEvent getTenenciaCreditosEvent2 = getTenenciaCreditosEvent;
        AnonymousClass3 r0 = new BaseWSResponseHandler(getActivity(), TypeOption.INTERMDIATE_VIEW, FragmentConstants.TENENCIA_CREDITOS, this, (BaseActivity) getActivity()) {
            /* access modifiers changed from: protected */
            public void onOk() {
                TenenciaCreditosFragment.this.c = false;
                TenenciaCreditosFragment.this.presenterLongTermDeposit.onTenenciasResponse(getTenenciaCreditosEvent2, TenenciaCreditosFragment.this.E());
                TenenciaCreditosFragment.this.b = getTenenciaCreditosEvent2.getResponse().getCategoriasBodyResponseBean;
            }
        };
        r0.handleWSResponse(getTenenciaCreditosEvent);
    }

    @Subscribe
    public void onGetDetalleCuotaCredito(GetDetalleCuotaTenenciaCreditoEvent getDetalleCuotaTenenciaCreditoEvent) {
        this.c = false;
        closeDialogLoading();
        final GetDetalleCuotaTenenciaCreditoEvent getDetalleCuotaTenenciaCreditoEvent2 = getDetalleCuotaTenenciaCreditoEvent;
        AnonymousClass4 r1 = new BaseWSResponseHandler(getActivity(), TypeOption.INTERMDIATE_VIEW, FragmentConstants.TENENCIA_CREDITOS, this, (BaseActivity) getActivity()) {
            /* access modifiers changed from: protected */
            public void onOk() {
                TenenciaCreditosFragment.this.presenterLongTermDeposit.onGetDetalleCuotaTenenciaCreditoResponseOK(getDetalleCuotaTenenciaCreditoEvent2);
            }

            /* access modifiers changed from: protected */
            public void onRes3Error(WebServiceEvent webServiceEvent) {
                TenenciaCreditosFragment.this.onShowError(getDetalleCuotaTenenciaCreditoEvent2.getTitleToShow(), getDetalleCuotaTenenciaCreditoEvent2.getMessageToShow());
            }
        };
        r1.handleWSResponse(getDetalleCuotaTenenciaCreditoEvent);
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:0x0375 A[Catch:{ Exception -> 0x038a }] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x03cd  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x0402  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x045f  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x0465  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x048c  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x049d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void configDetailUI(ar.com.santander.rio.mbanking.services.soap.beans.body.GetDetalleCuotaTenenciaCreditoBodyResponseBean r9, ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.CategoriaCredito r10, ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.DatosCredito r11) {
        /*
            r8 = this;
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r0 = new ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility
            android.support.v4.app.FragmentActivity r1 = r8.getActivity()
            android.content.Context r1 = r1.getApplicationContext()
            r0.<init>(r1)
            java.lang.String r1 = r11.getNombrecredito()
            if (r1 == 0) goto L_0x0031
            android.widget.TextView r1 = r8.creditTitle
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Detalle de cuota de Préstamo "
            r2.append(r3)
            java.lang.String r3 = r11.getNombrecredito()
            android.text.Spanned r3 = android.text.Html.fromHtml(r3)
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.setText(r2)
        L_0x0031:
            java.lang.String r1 = r11.getNombrecredito()
            if (r1 == 0) goto L_0x0055
            android.widget.TextView r1 = r8.creditHeaderTitle
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Préstamo "
            r2.append(r3)
            java.lang.String r3 = r11.getNombrecredito()
            android.text.Spanned r3 = android.text.Html.fromHtml(r3)
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.setText(r2)
        L_0x0055:
            java.lang.String r1 = r11.getImpoteCuota()
            r2 = 0
            r3 = 8
            if (r1 == 0) goto L_0x007d
            ar.com.santander.rio.mbanking.view.AmountView r1 = r8.creditHeaderData
            java.lang.String r4 = r11.getImpoteCuota()
            r1.setText(r4)
            android.widget.TextView r1 = r8.creditUVAMsg
            r1.setVisibility(r3)
            ar.com.santander.rio.mbanking.view.AmountView r1 = r8.creditHeaderData
            r1.setVisibility(r2)
            android.widget.Button r1 = r8.detailNextButton
            r4 = 1
            r1.setEnabled(r4)
            android.widget.TextView r1 = r8.creditHeaderTitle
            r1.setVisibility(r2)
            goto L_0x00a9
        L_0x007d:
            java.lang.String r1 = r11.getMensajeUVA()
            if (r1 == 0) goto L_0x00a9
            android.widget.TextView r1 = r8.creditUVAMsg
            java.lang.String r4 = r11.getMensajeUVA()
            android.text.Spanned r4 = android.text.Html.fromHtml(r4)
            r1.setText(r4)
            android.widget.TextView r1 = r8.creditUVAMsg
            r1.setVisibility(r2)
            ar.com.santander.rio.mbanking.view.AmountView r1 = r8.creditHeaderData
            r1.setVisibility(r3)
            android.widget.TextView r1 = r8.creditHeaderTitle
            r1.setVisibility(r3)
            android.widget.Button r1 = r8.detailNextButton
            r1.setEnabled(r2)
            android.widget.RelativeLayout r1 = r8.leyendaRl
            r1.setVisibility(r3)
        L_0x00a9:
            android.widget.TextView r1 = r8.saldoAnteriorDeCapital
            r4 = 2131757285(0x7f1008e5, float:1.9145501E38)
            java.lang.String r4 = r8.getString(r4)
            r1.setText(r4)
            ar.com.santander.rio.mbanking.view.AmountView r1 = r8.creditHeaderData     // Catch:{ Exception -> 0x038a }
            ar.com.santander.rio.mbanking.view.AmountView r4 = r8.creditHeaderData     // Catch:{ Exception -> 0x038a }
            java.lang.CharSequence r4 = r4.getText()     // Catch:{ Exception -> 0x038a }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x038a }
            java.lang.String r4 = r0.applyFilterAmount(r4)     // Catch:{ Exception -> 0x038a }
            r1.setContentDescription(r4)     // Catch:{ Exception -> 0x038a }
            java.lang.String r1 = r11.getDescripcredito()     // Catch:{ Exception -> 0x038a }
            android.widget.RelativeLayout r4 = r8.loanNumberRl     // Catch:{ Exception -> 0x038a }
            android.widget.TextView r5 = r8.loanNumberData     // Catch:{ Exception -> 0x038a }
            java.lang.String r6 = "CHARACTERTOCHARACTER"
            android.content.Context r7 = r8.getActContext()     // Catch:{ Exception -> 0x038a }
            setUpLabel(r1, r4, r5, r6, r7)     // Catch:{ Exception -> 0x038a }
            java.lang.String r1 = r9.getCuentaDebito()     // Catch:{ Exception -> 0x038a }
            android.widget.RelativeLayout r4 = r8.accountRl     // Catch:{ Exception -> 0x038a }
            android.widget.TextView r5 = r8.accountData     // Catch:{ Exception -> 0x038a }
            java.lang.String r6 = "ACCOUNTNAME"
            android.content.Context r7 = r8.getActContext()     // Catch:{ Exception -> 0x038a }
            setUpLabel(r1, r4, r5, r6, r7)     // Catch:{ Exception -> 0x038a }
            android.widget.TextView r1 = r8.selector_account_id_2     // Catch:{ Exception -> 0x038a }
            java.lang.String r4 = r9.getCuentaDebito()     // Catch:{ Exception -> 0x038a }
            java.lang.String r4 = r8.safeString(r4)     // Catch:{ Exception -> 0x038a }
            r1.setText(r4)     // Catch:{ Exception -> 0x038a }
            java.lang.String r1 = r11.getNroCuotaCredito()     // Catch:{ Exception -> 0x038a }
            android.widget.RelativeLayout r4 = r8.cuotaNumberRl     // Catch:{ Exception -> 0x038a }
            android.widget.TextView r5 = r8.cuotaNumberData     // Catch:{ Exception -> 0x038a }
            setUpLabel(r1, r4, r5)     // Catch:{ Exception -> 0x038a }
            java.lang.String r1 = r9.getPlazoCredito()     // Catch:{ Exception -> 0x038a }
            android.widget.RelativeLayout r4 = r8.dateRl     // Catch:{ Exception -> 0x038a }
            android.widget.TextView r5 = r8.dateData     // Catch:{ Exception -> 0x038a }
            setUpLabel(r1, r4, r5)     // Catch:{ Exception -> 0x038a }
            java.lang.String r1 = r11.getFechavencimiento()     // Catch:{ Exception -> 0x038a }
            android.widget.RelativeLayout r4 = r8.fechaVtoRl     // Catch:{ Exception -> 0x038a }
            android.widget.TextView r5 = r8.fechaVtoData     // Catch:{ Exception -> 0x038a }
            setUpLabel(r1, r4, r5)     // Catch:{ Exception -> 0x038a }
            java.lang.String r1 = r11.getMensajeUVA()     // Catch:{ Exception -> 0x038a }
            if (r1 == 0) goto L_0x012f
            java.lang.String r1 = r11.getMensajeUVA()     // Catch:{ Exception -> 0x038a }
            boolean r1 = r1.isEmpty()     // Catch:{ Exception -> 0x038a }
            if (r1 == 0) goto L_0x0129
            goto L_0x012f
        L_0x0129:
            android.widget.RelativeLayout r1 = r8.saldoAnteriorSinAjustarRl     // Catch:{ Exception -> 0x038a }
            r1.setVisibility(r3)     // Catch:{ Exception -> 0x038a }
            goto L_0x0140
        L_0x012f:
            java.lang.String r1 = r11.getSaldoSinAjustar()     // Catch:{ Exception -> 0x038a }
            android.widget.RelativeLayout r4 = r8.saldoAnteriorSinAjustarRl     // Catch:{ Exception -> 0x038a }
            android.widget.TextView r5 = r8.saldoAnteriorSinAjustarData     // Catch:{ Exception -> 0x038a }
            java.lang.String r6 = "AMOUNT"
            android.content.Context r7 = r8.getActContext()     // Catch:{ Exception -> 0x038a }
            setUpLabel(r1, r4, r5, r6, r7)     // Catch:{ Exception -> 0x038a }
        L_0x0140:
            java.lang.String r1 = r11.getCapitalAjustado()     // Catch:{ Exception -> 0x038a }
            android.widget.RelativeLayout r4 = r8.interesPesosUvaRl     // Catch:{ Exception -> 0x038a }
            android.widget.TextView r5 = r8.interesPesosUvaData     // Catch:{ Exception -> 0x038a }
            java.lang.String r6 = "AMOUNT"
            android.content.Context r7 = r8.getActContext()     // Catch:{ Exception -> 0x038a }
            setUpLabel(r1, r4, r5, r6, r7)     // Catch:{ Exception -> 0x038a }
            java.lang.String r1 = r11.getImpoteCuota()     // Catch:{ Exception -> 0x038a }
            android.widget.RelativeLayout r4 = r8.cuotaValueRl     // Catch:{ Exception -> 0x038a }
            android.widget.TextView r5 = r8.cuotaValueData     // Catch:{ Exception -> 0x038a }
            java.lang.String r6 = "AMOUNT"
            android.content.Context r7 = r8.getActContext()     // Catch:{ Exception -> 0x038a }
            setUpLabel(r1, r4, r5, r6, r7)     // Catch:{ Exception -> 0x038a }
            java.lang.String r1 = r11.getCapitalcuotacredito()     // Catch:{ Exception -> 0x038a }
            android.widget.RelativeLayout r4 = r8.capitalRl     // Catch:{ Exception -> 0x038a }
            android.widget.TextView r5 = r8.capitalData     // Catch:{ Exception -> 0x038a }
            java.lang.String r6 = "AMOUNT"
            android.content.Context r7 = r8.getActContext()     // Catch:{ Exception -> 0x038a }
            setUpLabel(r1, r4, r5, r6, r7)     // Catch:{ Exception -> 0x038a }
            java.lang.String r1 = r11.getAjusteCapitalMora()     // Catch:{ Exception -> 0x038a }
            android.widget.RelativeLayout r4 = r8.ajusteCapitalMoraRl     // Catch:{ Exception -> 0x038a }
            android.widget.TextView r5 = r8.ajusteCapitalMoraData     // Catch:{ Exception -> 0x038a }
            java.lang.String r6 = "AMOUNT"
            android.content.Context r7 = r8.getActContext()     // Catch:{ Exception -> 0x038a }
            setUpLabel(r1, r4, r5, r6, r7)     // Catch:{ Exception -> 0x038a }
            java.lang.String r1 = r11.getInteresescomp()     // Catch:{ Exception -> 0x038a }
            android.widget.RelativeLayout r4 = r8.interesesCompRl     // Catch:{ Exception -> 0x038a }
            android.widget.TextView r5 = r8.interesesCompData     // Catch:{ Exception -> 0x038a }
            java.lang.String r6 = "AMOUNT"
            android.content.Context r7 = r8.getActContext()     // Catch:{ Exception -> 0x038a }
            setUpLabel(r1, r4, r5, r6, r7)     // Catch:{ Exception -> 0x038a }
            java.lang.String r1 = r11.getInteresesPunitorios()     // Catch:{ Exception -> 0x038a }
            android.widget.RelativeLayout r4 = r8.interesesPunitoriosRl     // Catch:{ Exception -> 0x038a }
            android.widget.TextView r5 = r8.interesesPunitoriosData     // Catch:{ Exception -> 0x038a }
            java.lang.String r6 = "AMOUNT"
            android.content.Context r7 = r8.getActContext()     // Catch:{ Exception -> 0x038a }
            setUpLabel(r1, r4, r5, r6, r7)     // Catch:{ Exception -> 0x038a }
            java.lang.String r1 = r11.getOtrosImpuestos()     // Catch:{ Exception -> 0x038a }
            android.widget.RelativeLayout r4 = r8.taxesRl     // Catch:{ Exception -> 0x038a }
            android.widget.TextView r5 = r8.taxesData     // Catch:{ Exception -> 0x038a }
            java.lang.String r6 = "AMOUNT"
            android.content.Context r7 = r8.getActContext()     // Catch:{ Exception -> 0x038a }
            setUpLabel(r1, r4, r5, r6, r7)     // Catch:{ Exception -> 0x038a }
            java.lang.String r1 = r11.getIva()     // Catch:{ Exception -> 0x038a }
            android.widget.RelativeLayout r4 = r8.iva_rl     // Catch:{ Exception -> 0x038a }
            android.widget.TextView r5 = r8.textViewIVAData     // Catch:{ Exception -> 0x038a }
            java.lang.String r6 = "AMOUNT"
            android.content.Context r7 = r8.getActContext()     // Catch:{ Exception -> 0x038a }
            setUpLabel(r1, r4, r5, r6, r7)     // Catch:{ Exception -> 0x038a }
            android.widget.TextView r1 = r8.textViewIVATittle     // Catch:{ Exception -> 0x038a }
            java.lang.String r4 = "iba"
            r1.setContentDescription(r4)     // Catch:{ Exception -> 0x038a }
            java.lang.String r1 = r11.getCargoSeguroVida()     // Catch:{ Exception -> 0x038a }
            android.widget.RelativeLayout r4 = r8.cargoSeguroVidaRl     // Catch:{ Exception -> 0x038a }
            android.widget.TextView r5 = r8.cargoSeguroVidaData     // Catch:{ Exception -> 0x038a }
            java.lang.String r6 = "AMOUNT"
            android.content.Context r7 = r8.getActContext()     // Catch:{ Exception -> 0x038a }
            setUpLabel(r1, r4, r5, r6, r7)     // Catch:{ Exception -> 0x038a }
            java.lang.String r1 = r11.getSeguroBien()     // Catch:{ Exception -> 0x038a }
            android.widget.RelativeLayout r4 = r8.goodAssuranceRl     // Catch:{ Exception -> 0x038a }
            android.widget.TextView r5 = r8.goodAssuranceData     // Catch:{ Exception -> 0x038a }
            java.lang.String r6 = "AMOUNT"
            android.content.Context r7 = r8.getActContext()     // Catch:{ Exception -> 0x038a }
            setUpLabel(r1, r4, r5, r6, r7)     // Catch:{ Exception -> 0x038a }
            java.lang.String r1 = r11.getSeguroIncendio()     // Catch:{ Exception -> 0x038a }
            android.widget.RelativeLayout r4 = r8.seguroIncendioRl     // Catch:{ Exception -> 0x038a }
            android.widget.TextView r5 = r8.seguroIncendioData     // Catch:{ Exception -> 0x038a }
            java.lang.String r6 = "AMOUNT"
            android.content.Context r7 = r8.getActContext()     // Catch:{ Exception -> 0x038a }
            setUpLabel(r1, r4, r5, r6, r7)     // Catch:{ Exception -> 0x038a }
            java.lang.String r1 = r11.getCoeficiente()     // Catch:{ Exception -> 0x038a }
            android.widget.RelativeLayout r4 = r8.coeficienteRl     // Catch:{ Exception -> 0x038a }
            android.widget.TextView r5 = r8.coeficienteData     // Catch:{ Exception -> 0x038a }
            java.lang.String r6 = "AMOUNT"
            android.content.Context r7 = r8.getActContext()     // Catch:{ Exception -> 0x038a }
            setUpLabel(r1, r4, r5, r6, r7)     // Catch:{ Exception -> 0x038a }
            java.lang.String r1 = r9.getDisponibleUsos()     // Catch:{ Exception -> 0x038a }
            android.widget.RelativeLayout r4 = r8.disponibleUsoRl     // Catch:{ Exception -> 0x038a }
            android.widget.TextView r5 = r8.disponibleUsoData     // Catch:{ Exception -> 0x038a }
            java.lang.String r6 = "AMOUNT"
            android.content.Context r7 = r8.getActContext()     // Catch:{ Exception -> 0x038a }
            setUpLabel(r1, r4, r5, r6, r7)     // Catch:{ Exception -> 0x038a }
            java.lang.String r1 = r9.getLineaTotalCredit()     // Catch:{ Exception -> 0x038a }
            android.widget.RelativeLayout r4 = r8.lineaCreditoRl     // Catch:{ Exception -> 0x038a }
            android.widget.TextView r5 = r8.lineaCreditoData     // Catch:{ Exception -> 0x038a }
            java.lang.String r6 = "AMOUNT"
            android.content.Context r7 = r8.getActContext()     // Catch:{ Exception -> 0x038a }
            setUpLabel(r1, r4, r5, r6, r7)     // Catch:{ Exception -> 0x038a }
            java.lang.String r1 = r11.getImporteCuotaUVA()     // Catch:{ Exception -> 0x038a }
            android.widget.RelativeLayout r4 = r8.relativeImporteDeCuotaPuraUVAS     // Catch:{ Exception -> 0x038a }
            android.widget.TextView r5 = r8.textViewImporteDeCuotaPuraUVAS_data     // Catch:{ Exception -> 0x038a }
            java.lang.String r6 = "AMOUNT"
            android.content.Context r7 = r8.getActContext()     // Catch:{ Exception -> 0x038a }
            setUpLabel(r1, r4, r5, r6, r7)     // Catch:{ Exception -> 0x038a }
            java.lang.String r1 = r11.getCapitalUVA()     // Catch:{ Exception -> 0x038a }
            android.widget.RelativeLayout r4 = r8.capital_uva_rl     // Catch:{ Exception -> 0x038a }
            android.widget.TextView r5 = r8.capital_uva_data     // Catch:{ Exception -> 0x038a }
            java.lang.String r6 = "GENERAL"
            android.content.Context r7 = r8.getActContext()     // Catch:{ Exception -> 0x038a }
            setUpLabel(r1, r4, r5, r6, r7)     // Catch:{ Exception -> 0x038a }
            java.lang.String r1 = r11.getInteresesUVA()     // Catch:{ Exception -> 0x038a }
            android.widget.RelativeLayout r4 = r8.intereses_uva_rl     // Catch:{ Exception -> 0x038a }
            android.widget.TextView r5 = r8.intereses_uva_data     // Catch:{ Exception -> 0x038a }
            java.lang.String r6 = "PERCENTAJE"
            android.content.Context r7 = r8.getActContext()     // Catch:{ Exception -> 0x038a }
            setUpLabel(r1, r4, r5, r6, r7)     // Catch:{ Exception -> 0x038a }
            java.lang.String r1 = r11.getValorUVA()     // Catch:{ Exception -> 0x038a }
            android.widget.RelativeLayout r4 = r8.valor_uva_rl     // Catch:{ Exception -> 0x038a }
            android.widget.TextView r5 = r8.valor_uva_data     // Catch:{ Exception -> 0x038a }
            java.lang.String r6 = "AMOUNT"
            android.content.Context r7 = r8.getActContext()     // Catch:{ Exception -> 0x038a }
            setUpLabel(r1, r4, r5, r6, r7)     // Catch:{ Exception -> 0x038a }
            java.lang.String r1 = r11.getFechaCotizaUVA()     // Catch:{ Exception -> 0x038a }
            android.widget.RelativeLayout r4 = r8.fechaCotizacionDeUVAs_rl     // Catch:{ Exception -> 0x038a }
            android.widget.TextView r5 = r8.fechaDeCotizacionDeUVAs_data     // Catch:{ Exception -> 0x038a }
            java.lang.String r6 = "DATE"
            android.content.Context r7 = r8.getActContext()     // Catch:{ Exception -> 0x038a }
            setUpLabel(r1, r4, r5, r6, r7)     // Catch:{ Exception -> 0x038a }
            java.lang.String r1 = r11.getTasacftna()     // Catch:{ Exception -> 0x038a }
            if (r1 != 0) goto L_0x02a2
            java.lang.String r1 = r11.getTasactfnaiva()     // Catch:{ Exception -> 0x038a }
            if (r1 != 0) goto L_0x02a2
            java.lang.String r1 = r11.getTasatea()     // Catch:{ Exception -> 0x038a }
            if (r1 != 0) goto L_0x02a2
            java.lang.String r1 = r11.getTasatna()     // Catch:{ Exception -> 0x038a }
            if (r1 == 0) goto L_0x02a8
        L_0x02a2:
            java.lang.String r1 = r11.getMensajeUVA()     // Catch:{ Exception -> 0x038a }
            if (r1 == 0) goto L_0x02ae
        L_0x02a8:
            android.widget.RelativeLayout r1 = r8.ratesRl     // Catch:{ Exception -> 0x038a }
            r1.setVisibility(r3)     // Catch:{ Exception -> 0x038a }
            goto L_0x02b3
        L_0x02ae:
            android.widget.RelativeLayout r1 = r8.ratesRl     // Catch:{ Exception -> 0x038a }
            r1.setVisibility(r2)     // Catch:{ Exception -> 0x038a }
        L_0x02b3:
            java.lang.String r1 = r11.getTasatea()     // Catch:{ Exception -> 0x038a }
            android.widget.RelativeLayout r4 = r8.teaRl     // Catch:{ Exception -> 0x038a }
            android.widget.TextView r5 = r8.teaData     // Catch:{ Exception -> 0x038a }
            java.lang.String r6 = "PERCENTAJE"
            android.content.Context r7 = r8.getActContext()     // Catch:{ Exception -> 0x038a }
            setUpLabel(r1, r4, r5, r6, r7)     // Catch:{ Exception -> 0x038a }
            android.widget.TextView r1 = r8.teaData     // Catch:{ Exception -> 0x038a }
            android.widget.TextView r4 = r8.teaData     // Catch:{ Exception -> 0x038a }
            java.lang.CharSequence r4 = r4.getText()     // Catch:{ Exception -> 0x038a }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x038a }
            java.lang.String r4 = r0.applyFilterTasaValue(r4)     // Catch:{ Exception -> 0x038a }
            r1.setContentDescription(r4)     // Catch:{ Exception -> 0x038a }
            java.lang.String r1 = r11.getTasatna()     // Catch:{ Exception -> 0x038a }
            android.widget.RelativeLayout r4 = r8.tnaRl     // Catch:{ Exception -> 0x038a }
            android.widget.TextView r5 = r8.tnaData     // Catch:{ Exception -> 0x038a }
            java.lang.String r6 = "PERCENTAJE"
            android.content.Context r7 = r8.getActContext()     // Catch:{ Exception -> 0x038a }
            setUpLabel(r1, r4, r5, r6, r7)     // Catch:{ Exception -> 0x038a }
            android.widget.TextView r1 = r8.tnaData     // Catch:{ Exception -> 0x038a }
            android.content.Context r4 = r8.getActContext()     // Catch:{ Exception -> 0x038a }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r4 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r4)     // Catch:{ Exception -> 0x038a }
            android.widget.TextView r5 = r8.tnaData     // Catch:{ Exception -> 0x038a }
            java.lang.CharSequence r5 = r5.getText()     // Catch:{ Exception -> 0x038a }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x038a }
            java.lang.String r4 = r4.applyFilterTasaValue(r5)     // Catch:{ Exception -> 0x038a }
            r1.setContentDescription(r4)     // Catch:{ Exception -> 0x038a }
            java.lang.String r1 = r11.getTasacftna()     // Catch:{ Exception -> 0x038a }
            android.widget.RelativeLayout r4 = r8.CFTNARl     // Catch:{ Exception -> 0x038a }
            android.widget.TextView r5 = r8.CFTNAData     // Catch:{ Exception -> 0x038a }
            java.lang.String r6 = "PERCENTAJE"
            android.content.Context r7 = r8.getActContext()     // Catch:{ Exception -> 0x038a }
            setUpLabel(r1, r4, r5, r6, r7)     // Catch:{ Exception -> 0x038a }
            android.widget.RelativeLayout r1 = r8.CFTNARl     // Catch:{ Exception -> 0x038a }
            android.content.Context r4 = r8.getActContext()     // Catch:{ Exception -> 0x038a }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r4 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r4)     // Catch:{ Exception -> 0x038a }
            android.widget.TextView r5 = r8.cftnaLabel     // Catch:{ Exception -> 0x038a }
            java.lang.CharSequence r5 = r5.getText()     // Catch:{ Exception -> 0x038a }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x038a }
            android.widget.TextView r6 = r8.CFTNAData     // Catch:{ Exception -> 0x038a }
            java.lang.CharSequence r6 = r6.getText()     // Catch:{ Exception -> 0x038a }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x038a }
            java.lang.String r6 = r0.applyFilterTasaValue(r6)     // Catch:{ Exception -> 0x038a }
            java.lang.String r4 = r4.applyCFTEA(r5, r6)     // Catch:{ Exception -> 0x038a }
            r1.setContentDescription(r4)     // Catch:{ Exception -> 0x038a }
            java.lang.String r1 = r11.getTasactfnaiva()     // Catch:{ Exception -> 0x038a }
            android.widget.RelativeLayout r4 = r8.CFTNAsIvaRl     // Catch:{ Exception -> 0x038a }
            android.widget.TextView r5 = r8.CFTNAsIvaData     // Catch:{ Exception -> 0x038a }
            java.lang.String r6 = "PERCENTAJE"
            android.content.Context r7 = r8.getActContext()     // Catch:{ Exception -> 0x038a }
            setUpLabel(r1, r4, r5, r6, r7)     // Catch:{ Exception -> 0x038a }
            android.widget.RelativeLayout r1 = r8.CFTNAsIvaRl     // Catch:{ Exception -> 0x038a }
            android.widget.TextView r4 = r8.CFTNAsIvaName     // Catch:{ Exception -> 0x038a }
            java.lang.CharSequence r4 = r4.getText()     // Catch:{ Exception -> 0x038a }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x038a }
            android.widget.TextView r5 = r8.CFTNAsIvaData     // Catch:{ Exception -> 0x038a }
            java.lang.CharSequence r5 = r5.getText()     // Catch:{ Exception -> 0x038a }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x038a }
            java.lang.String r5 = r0.applyFilterTasaValue(r5)     // Catch:{ Exception -> 0x038a }
            java.lang.String r0 = r0.applyCFTEA(r4, r5)     // Catch:{ Exception -> 0x038a }
            r1.setContentDescription(r0)     // Catch:{ Exception -> 0x038a }
            java.lang.String r0 = r11.getMensajeUVA()     // Catch:{ Exception -> 0x038a }
            if (r0 == 0) goto L_0x038e
            android.widget.RelativeLayout r0 = r8.teaRl     // Catch:{ Exception -> 0x038a }
            r0.setVisibility(r3)     // Catch:{ Exception -> 0x038a }
            android.widget.RelativeLayout r0 = r8.tnaRl     // Catch:{ Exception -> 0x038a }
            r0.setVisibility(r3)     // Catch:{ Exception -> 0x038a }
            android.widget.RelativeLayout r0 = r8.CFTNARl     // Catch:{ Exception -> 0x038a }
            r0.setVisibility(r3)     // Catch:{ Exception -> 0x038a }
            android.widget.RelativeLayout r0 = r8.CFTNAsIvaRl     // Catch:{ Exception -> 0x038a }
            r0.setVisibility(r3)     // Catch:{ Exception -> 0x038a }
            goto L_0x038e
        L_0x038a:
            r0 = move-exception
            r0.printStackTrace()
        L_0x038e:
            java.lang.String r0 = "TC_DETA_CUOTA"
            ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.DatosLeyenda r0 = r8.b(r0)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            if (r0 == 0) goto L_0x03c1
            java.lang.String r4 = r11.getMensajeUVA()
            if (r4 != 0) goto L_0x03c1
            java.lang.String r0 = r0.getDescripcion()
            android.text.Spanned r0 = android.text.Html.fromHtml(r0)
            java.lang.String r0 = r0.toString()
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            android.widget.RelativeLayout r4 = r8.leyendaRl
            android.widget.TextView r5 = r8.leyendaData
            java.lang.String r6 = "LEYENDA"
            android.content.Context r7 = r8.getActContext()
            setUpLabel(r0, r4, r5, r6, r7)
        L_0x03c1:
            java.lang.String r0 = r11.getTipocredito()
            java.lang.String r4 = "O"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x0402
            java.lang.String r0 = "TC_OP_PAGOMIN"
            ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.DatosLeyenda r0 = r8.b(r0)
            if (r0 == 0) goto L_0x044b
            java.lang.String r4 = r1.toString()
            boolean r4 = r4.isEmpty()
            if (r4 == 0) goto L_0x03e0
            goto L_0x03e5
        L_0x03e0:
            java.lang.String r4 = "\n"
            r1.append(r4)
        L_0x03e5:
            java.lang.String r0 = r0.getDescripcion()
            android.text.Spanned r0 = android.text.Html.fromHtml(r0)
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            android.widget.RelativeLayout r1 = r8.leyendaRl
            android.widget.TextView r4 = r8.leyendaData
            java.lang.String r5 = "LEYENDA"
            android.content.Context r6 = r8.getActContext()
            setUpLabel(r0, r1, r4, r5, r6)
            goto L_0x044b
        L_0x0402:
            ar.com.santander.rio.mbanking.services.soap.beans.body.GetTenenciaCreditosBodyResponseBean r0 = r8.b
            ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.Listaleyendas r0 = r0.getListaleyendas()
            java.lang.String r0 = ar.com.santander.rio.mbanking.app.commons.CCreditos.getLeyendaTasa(r0, r11)
            boolean r1 = r0.isEmpty()
            if (r1 != 0) goto L_0x0446
            java.lang.String r1 = r11.getMensajeUVA()
            if (r1 != 0) goto L_0x0446
            android.widget.TextView r1 = r8.leyendaTasaDetalle
            r1.setVisibility(r2)
            android.widget.TextView r1 = r8.leyendaTasaDetalle
            r1.setText(r0)
            android.content.Context r1 = r8.getContext()
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r1)
            java.lang.String r0 = r1.applyCFTEA_IVA_3(r0)
            android.content.Context r1 = r8.getContext()     // Catch:{ Exception -> 0x043c }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r1)     // Catch:{ Exception -> 0x043c }
            java.lang.String r1 = r1.applyFilterAcronymTasas(r0)     // Catch:{ Exception -> 0x043c }
            r0 = r1
            goto L_0x0440
        L_0x043c:
            r1 = move-exception
            r1.printStackTrace()
        L_0x0440:
            android.widget.TextView r1 = r8.leyendaTasaDetalle
            r1.setContentDescription(r0)
            goto L_0x044b
        L_0x0446:
            android.widget.TextView r0 = r8.leyendaTasaDetalle
            r0.setVisibility(r3)
        L_0x044b:
            r8.i = r9
            r8.ad = r10
            r8.ae = r11
            ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.DatosCredito r9 = r8.ae
            java.lang.String r9 = r9.getHabpagarcredito()
            java.lang.String r10 = "S"
            boolean r9 = r9.equals(r10)
            if (r9 == 0) goto L_0x0465
            android.widget.Button r9 = r8.detailNextButton
            r9.setVisibility(r2)
            goto L_0x046a
        L_0x0465:
            android.widget.Button r9 = r8.detailNextButton
            r9.setVisibility(r3)
        L_0x046a:
            android.widget.RelativeLayout r9 = r8.loanNumberRl
            r10 = 2131362173(0x7f0a017d, float:1.834412E38)
            android.view.View r9 = r9.findViewById(r10)
            android.widget.TextView r9 = (android.widget.TextView) r9
            android.widget.RelativeLayout r10 = r8.tnaRl
            r0 = 2131362158(0x7f0a016e, float:1.8344089E38)
            android.view.View r10 = r10.findViewById(r0)
            android.widget.TextView r10 = (android.widget.TextView) r10
            java.lang.String r11 = r11.getTipocredito()
            java.lang.String r0 = "O"
            boolean r11 = r11.equals(r0)
            if (r11 == 0) goto L_0x049d
            r11 = 2131755196(0x7f1000bc, float:1.9141264E38)
            r9.setText(r11)
            r9 = 2131755197(0x7f1000bd, float:1.9141266E38)
            java.lang.String r9 = r8.getString(r9)
            r10.setText(r9)
            goto L_0x04ad
        L_0x049d:
            r11 = 2131755198(0x7f1000be, float:1.9141269E38)
            r9.setText(r11)
            r9 = 2131755194(0x7f1000ba, float:1.914126E38)
            java.lang.String r9 = r8.getString(r9)
            r10.setText(r9)
        L_0x04ad:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.fragments.privado.TenenciaCreditosFragment.configDetailUI(ar.com.santander.rio.mbanking.services.soap.beans.body.GetDetalleCuotaTenenciaCreditoBodyResponseBean, ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.CategoriaCredito, ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.DatosCredito):void");
    }

    private DatosLeyenda b(String str) {
        for (DatosLeyenda datosLeyenda : this.ah.getDatosleyenda()) {
            if (datosLeyenda.getIdleyenda().equals(str)) {
                return datosLeyenda;
            }
        }
        return null;
    }

    public void configConfirmUI() {
        this.presenterLongTermDeposit.setAccount();
        Typeface createFromAsset = Typeface.createFromAsset(getResources().getAssets(), "fonts/OpenSans-Semibold.otf");
        this.payment_monto_cuenta.setTypeface(createFromAsset);
        TextView textView = this.paymentTitle;
        StringBuilder sb = new StringBuilder();
        sb.append("Pago de cuota de Préstamo ");
        sb.append(Html.fromHtml(this.ae.getNombrecredito()));
        textView.setText(sb.toString());
        TextView textView2 = this.paymentHeaderTitle;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Préstamo ");
        sb2.append(Html.fromHtml(this.ae.getNombrecredito()));
        textView2.setText(sb2.toString());
        this.paymentHeaderData.setText(safeString(this.ae.getImpoteCuota()));
        this.payment_monto_cuenta.setTypeface(createFromAsset);
        try {
            if (this.ae.getIdentificadorUVA() == null) {
                this.saldoAnteriorDeCapital.setText(getString(R.string.Saldo_Anterior_De_Capital_Sin_Ajustar_UVAS));
            } else {
                this.saldoAnteriorDeCapital.setText(getString(R.string.Saldo_Anterior_De_Capital_Sin_Ajustar_UVAS));
            }
        } catch (Exception unused) {
            this.saldoAnteriorDeCapital.setText(getString(R.string.Saldo_Anterior_De_Capital_Sin_Ajustar_UVAS));
        }
        try {
            this.paymentHeaderData.setContentDescription(this.cAccessibility.applyFilterAmount(this.paymentHeaderData.getText().toString()));
        } catch (Exception unused2) {
        }
        this.paymentHeaderDetail.setText("");
        if (this.ae.getNombrecredito() != null) {
            TextView textView3 = this.paymentTipoPrestamoLabel;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Préstamo ");
            sb3.append(Html.fromHtml(this.ae.getNombrecredito()));
            textView3.setText(sb3.toString());
        }
        this.paymentTipoPrestamo.setText(safeString(this.ae.getDescripcredito()));
        this.paymentCuota.setText(safeString(this.ae.getNroCuotaCredito()));
        try {
            this.paymentTipoPrestamo.setContentDescription(this.cAccessibility.applyFilterCharacterToCharacter(this.paymentTipoPrestamo.getText().toString()));
            this.lbl_de_cuenta.setContentDescription(this.cAccessibility.applyFilterGeneral(this.lbl_de_cuenta.getText().toString()));
            this.selector_account_id_2.setContentDescription(this.cAccessibility.applyFilterAccount(this.selector_account_id_2.getText().toString()));
            this.payment_monto_cuenta.setContentDescription(this.cAccessibility.applyFilterAmount(this.payment_monto_cuenta.getText().toString()));
            setUpLabel(this.i.getPlazoCredito(), this.relativeLayoutPlazoDePrestamo, this.paymentPlazoPrestamo);
            setUpLabel(this.ae.getFechavencimiento(), this.relativeLayoutFechaDeVencimiento, this.paymentFechaVencimiento);
            setUpLabel(this.ae.getImpoteCuota(), this.relativeLayoutImporteDeLaCuota, this.paymentImporteCuotaData, "AMOUNT", getActContext());
            setUpLabel(this.ae.getCapitalcuotacredito(), this.relativeLayoutCapital, this.paymentCapitalData, "AMOUNT", getActContext());
            setUpLabel(this.ae.getInteresescomp(), this.relativeLayoutInteresesDelPeriodo, this.paymentInteresesCompData, "AMOUNT", getActContext());
            setUpLabel(this.ae.getCargoSeguroVida(), this.relativeLayoutCargoPorSeguroDeVida, this.paymentSeguroVidaData, "AMOUNT", getActContext());
            setUpLabel(this.ae.getImpuestoIva(), this.relativeLayoutImpuestosIVA, this.paymentImpuestosIvaData, "AMOUNT", getActContext());
            setUpLabel(this.ae.getOtrosImpuestos(), this.relativeLayoutOtrosImpuestosPaymentCredit, this.OtrosImpuestosPaymentCreditData, "AMOUNT", getActContext());
            this.textViewIVATittlePaymentCredit.setContentDescription(getContext().getString(R.string.IVA));
            setUpLabel(this.ae.getIva(), this.linearLayoutIVAPaymentCredit, this.textViewIVADataPaymentCredit, "AMOUNT", getActContext());
            setUpLabel(this.ae.getAjusteCapitalMora(), this.ajuste_capital_mora_rl_payment, this.ajuste_capital_mora_data_payment, "AMOUNT", getActContext());
            setUpLabel(this.ae.getInteresesPunitorios(), this.intereses_punitorios_rl_payment, this.intereses_punitorios_data_payment, "AMOUNT", getActContext());
            setUpLabel(this.ae.getImporteCuotaUVA(), this.relativeImporteDeCuotaPuraUVASPayment, this.textViewImporteDeCuotaPuraUVAS_dataPayment, "AMOUNT", getActContext());
            setUpLabel(this.ae.getCapitalUVA(), this.capital_uva_rl_Payment, this.capital_uva_dataPayment, GENERAL, getActContext());
            setUpLabel(this.ae.getInteresesUVA(), this.intereses_uva_rl_Payment, this.intereses_uva_data_Payment, PERCENTAJE, getActContext());
            this.OtrosImpuestosPaymentCreditData.setText(this.ae.getOtrosImpuestos());
            setUpLabel(this.i.getImpuestoSellado(), this.relativeLayoutImpuestosDeSellado, this.paymentImpuestosSelladoData, "AMOUNT", getActContext());
            setUpLabel(this.ae.getTasatna(), this.relativeLayoutTNA, this.paymentTnaData, PERCENTAJE, getActContext());
            this.paymentTnaData.setText(this.ae.getTasatna());
            this.relativeLayoutTNA.setContentDescription(CAccessibility.getInstance(getActContext()).applyFilterAcronymTasas(this.paymentTnaName.getText().toString(), this.paymentTnaData.getText().toString()));
            this.paymentTeaData.setText(this.ae.getTasatea());
            setUpLabel(this.ae.getTasatea(), this.relativeLayoutTEA, this.paymentTeaData, PERCENTAJE, getActContext());
            this.relativeLayoutTEA.setContentDescription(CAccessibility.getInstance(getActContext()).applyFilterAcronymTasas(this.paymentTeaName.getText().toString(), this.paymentTeaData.getText().toString()));
            this.paymentCftnaData.setText(this.ae.getTasacftna());
            setUpLabel(this.ae.getTasacftna(), this.relativeLayoutCFTNA, this.paymentCftnaData, PERCENTAJE, getActContext());
            this.relativeLayoutCFTNA.setContentDescription(CAccessibility.getInstance(getActContext()).applyCFTEA(this.paymentCftnaName.getText().toString()).concat(this.cAccessibility.applyFilterTasaValue(this.paymentCftnaData.getText().toString())));
            this.paymentCftnaSivaData.setText(this.ae.getTasactfnaiva());
            setUpLabel(this.ae.getTasactfnaiva(), this.relativeLayoutCFTNASiva, this.paymentCftnaSivaData, PERCENTAJE, getActContext());
            this.relativeLayoutCFTNASiva.setContentDescription(CAccessibility.getInstance(getActContext()).applyCFTN_IVA(this.paymentCftnaSivaName.getText().toString(), this.cAccessibility.applyFilterTasaValue(this.paymentCftnaSivaData.getText().toString())));
            DatosLeyenda b2 = b(CreditosConstants.TC_CONF_PAGO);
            if (b2 != null) {
                setUpLabel(Html.fromHtml(b2.getDescripcion()).toString(), this.leyenda_r3, this.leyenda_data_2);
            }
            String leyendaTasa = CCreditos.getLeyendaTasa(this.b.getListaleyendas(), this.ae);
            if (leyendaTasa == null || leyendaTasa.isEmpty()) {
                this.leyendaTasaPayment.setVisibility(8);
                return;
            }
            this.leyendaTasaPayment.setText(leyendaTasa);
            String applyCFTEA_IVA_3 = CAccessibility.getInstance(getContext()).applyCFTEA_IVA_3(leyendaTasa);
            try {
                applyCFTEA_IVA_3 = CAccessibility.getInstance(getContext()).applyFilterAcronymTasas(applyCFTEA_IVA_3);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            this.leyendaTasaPayment.setContentDescription(applyCFTEA_IVA_3);
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    public void configPaymentUI(CuentaOperativa cuentaOperativa) {
        this.ag = cuentaOperativa;
        Typeface createFromAsset = Typeface.createFromAsset(getResources().getAssets(), "fonts/OpenSans-Semibold.otf");
        this.confirmMontoCuenta.setTypeface(createFromAsset);
        if (this.ae.getNombrecredito() != null) {
            TextView textView = this.confirmTitle;
            StringBuilder sb = new StringBuilder();
            sb.append("Confirmación de pago de cuota de Préstamo ");
            sb.append(Html.fromHtml(this.ae.getNombrecredito()));
            textView.setText(sb.toString());
        }
        if (this.ae.getNombrecredito() != null) {
            TextView textView2 = this.confirmHeaderTitle;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Préstamo ");
            sb2.append(Html.fromHtml(this.ae.getNombrecredito()));
            textView2.setText(sb2.toString());
        }
        this.confirmHeaderData.setText(safeString(this.ae.getImpoteCuota()));
        this.confirmHeaderDetail.setText("");
        try {
            this.confirmHeaderData.setContentDescription(this.cAccessibility.applyFilterAmount(this.confirmHeaderData.getText().toString()));
        } catch (Exception unused) {
        }
        if (this.ae.getNombrecredito() != null) {
            TextView textView3 = this.confirmTipoPrestamoLabel;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Préstamo ");
            sb3.append(Html.fromHtml(this.ae.getNombrecredito()));
            textView3.setText(sb3.toString());
        }
        this.confirmTipoPrestamo.setText(safeString(this.ae.getDescripcredito()));
        try {
            this.confirmTipoPrestamo.setContentDescription(this.cAccessibility.applyFilterCharacterToCharacter(this.confirmTipoPrestamo.getText().toString()));
        } catch (Exception unused2) {
        }
        this.getConfirmPayment.setVisibility(0);
        this.confirmNextButton.setVisibility(0);
        this.vgErrorWSRes.setVisibility(8);
        setUpLabel(this.ae.getNroCuotaCredito(), this.relativeLayoutCuotaConfirmTenenciaCredito, this.confirmCuota);
        setUpLabel(this.i.getPlazoCredito(), this.relativeLayoutPlazoPrestamoConfirmTenenciaCredito, this.confirmPlazoPrestamo);
        setUpLabel(this.ae.getFechavencimiento(), this.relativeLayoutFechaVencimientoConfirmTenenciaCredito, this.confirmFechaVencimiento);
        setUpLabel(this.ae.getImpoteCuota(), this.relativeLayoutImporteDeLaCuotaConfirmTenenciaCredito, this.confirmImporteCuota, "AMOUNT", getActContext());
        setUpLabel(this.i.getImpuestoSellado(), this.relativeLayoutImpuestosDeSellado, this.paymentImpuestosSelladoData, "AMOUNT", getActContext());
        setUpLabel(this.ae.getCapitalcuotacredito(), this.relativeLayoutCapital, this.paymentCapitalData, "AMOUNT", getActContext());
        this.relativeLayoutImporteDeLaCuotaConfirmTenenciaCredito.setContentDescription(CAccessibility.getInstance(getActContext()).applyImport(this.confirmImporteCuotaField.getText().toString(), this.confirmImporteCuota.getText().toString()));
        this.confirmNextButton.setContentDescription(" Confirmar, ".concat(getString(R.string.boton_confirmar_pago_credito)).concat(Html.fromHtml(this.ae.getNombrecredito()).toString()));
        String[] split = this.ag.getDescCtaDebito().split(UtilsCuentas.SEPARAOR2);
        if (split.length == 5) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(split[0]);
            sb4.append(UtilsCuentas.SEPARAOR2);
            sb4.append(split[1]);
            sb4.append(UtilsCuentas.SEPARAOR2);
            sb4.append(split[2]);
            String sb5 = sb4.toString();
            StringBuilder sb6 = new StringBuilder();
            sb6.append(split[split.length - 2]);
            sb6.append(UtilsCuentas.SEPARAOR2);
            sb6.append(split[split.length - 1]);
            String sb7 = sb6.toString();
            this.confirmNroCuenta.setText(safeString(sb5));
            this.confirmMontoCuenta.setText(safeString(sb7));
            try {
                this.confirmNroCuenta.setContentDescription(this.cAccessibility.applyFilterAccount(sb5));
                this.confirmMontoCuenta.setContentDescription(this.cAccessibility.applyFilterAmount(sb7));
            } catch (Throwable unused3) {
            }
            DatosLeyenda b2 = b(CreditosConstants.TC_CONF_PAGO);
            if (b2 != null) {
                setUpLabel(Html.fromHtml(b2.getDescripcion()).toString(), this.leyenda_r2, this.leyenda_data_confirm);
            }
        }
        this.confirmMontoCuenta.setTypeface(createFromAsset);
    }

    public void configReceiptUI(ConfirmarPagoBodyResponseBean confirmarPagoBodyResponseBean) {
        this.presenterLongTermDeposit.nextPage();
        loadBarShare();
        this.af = confirmarPagoBodyResponseBean;
        TextView textView = this.reciptTitle;
        StringBuilder sb = new StringBuilder();
        sb.append("Comprobante de pago de Cuota de Préstamo ");
        sb.append(Html.fromHtml(this.ae.getNombrecredito()));
        textView.setText(sb.toString());
        if (this.ae.getNombrecredito() != null) {
            TextView textView2 = this.receiptHeaderTitle;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Préstamo ");
            sb2.append(Html.fromHtml(this.ae.getNombrecredito()));
            textView2.setText(sb2.toString());
        }
        this.receiptHeaderData.setText(safeString(this.af.getImporteCuota()));
        this.receiptHeaderDetail.setText(safeString(""));
        try {
            this.receiptHeaderData.setContentDescription(this.cAccessibility.applyFilterAmount(this.receiptHeaderData.getText().toString()));
        } catch (Exception unused) {
        }
        if (this.ae.getNombrecredito() != null) {
            TextView textView3 = this.receiptTipoPrestamoLabel;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Préstamo ");
            sb3.append(Html.fromHtml(this.ae.getNombrecredito()));
            textView3.setText(sb3.toString());
        }
        this.receiptPrestamoData.setText(safeString(this.ae.getDescripcredito()));
        try {
            this.receiptPrestamoData.setContentDescription(this.cAccessibility.applyFilterCharacterToCharacter(this.receiptPrestamoData.getText().toString()));
        } catch (Exception unused2) {
        }
        String[] split = this.ag.getDescCtaDebito().split(UtilsCuentas.SEPARAOR2);
        if (split.length == 5) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(split[0]);
            sb4.append(UtilsCuentas.SEPARAOR2);
            sb4.append(split[1]);
            sb4.append(UtilsCuentas.SEPARAOR2);
            sb4.append(split[2]);
            String sb5 = sb4.toString();
            this.reciptCuentaDebitoData.setText(safeString(sb5));
            try {
                this.reciptCuentaDebitoData.setContentDescription(this.cAccessibility.applyFilterAccount(sb5));
            } catch (Throwable unused3) {
            }
        }
        try {
            if (this.ae.getIdentificadorUVA() == null || !this.ae.getIdentificadorUVA().equalsIgnoreCase("UVA")) {
                this.txtViewSaldoAnteriorLabel.setText(getString(R.string.Saldo_Anterior_De_Capital_Sin_Ajustar_UVAS));
                setUpLabel(this.af.getTitularCredito(), this.F10_01_rlt_titular, this.recipt_titular_data);
                setUpLabel(this.af.getCuil(), this.cuil_compr_layout, this.recipt_cuil_data);
                setUpLabel(this.af.getCondicionIVA(), this.condicion_uva_compr_layout, this.recipt_iva_data);
            } else {
                this.txtViewSaldoAnteriorLabel.setText(getString(R.string.Saldo_Anterior_De_Capital_Sin_Ajustar_UVAS));
                this.F10_01_rlt_titular.setVisibility(8);
                this.separador4_compr.setVisibility(8);
                this.cuil_compr_layout.setVisibility(8);
                this.separador5_compr.setVisibility(8);
                this.condicion_uva_compr_layout.setVisibility(8);
                this.separador6_compr.setVisibility(8);
            }
        } catch (Exception unused4) {
            this.txtViewSaldoAnteriorLabel.setText(getString(R.string.Saldo_Anterior_De_Capital_Sin_Ajustar_UVAS));
        }
        setUpLabel(this.ae.getNroCuotaCredito(), this.relativeLayoutCuotaReceipTenenciaCreditos, this.reciptCuotaData);
        setUpLabel(this.i.getPlazoCredito(), this.relativeLayoutPlazoDePrestamoReceipTenenciaCreditos, this.reciptPlazoPrestamoData);
        setUpLabel(this.ae.getFechavencimiento(), this.relativeLayoutFechaDeVencimientoReceipTenenciaCreditos, this.reciptFechaVencimientoData);
        setUpLabel(this.af.getSaldoCapitalSinAjustar(), this.relativeLayoutSaldoAnteriorDelCapitalSinAjustarReceipTenenciaCreditos, this.reciptSaldoCapitalData, "AMOUNT", getActContext());
        setUpLabel(this.af.getImporteCuota(), this.relativeLayoutImporteCuotaReceipTenenciaCreditos, this.receiptImporteCuotaData, "AMOUNT", getActContext());
        setUpLabel(this.af.getCapitalCuota(), this.relativeLayoutCapitalReceipTenenciaCreditos, this.receiptCapitalData, "AMOUNT", getActContext());
        setUpLabel(this.af.getInteresesCompPeriodo(), this.relativeLayoutInteresesCompensatoriosPeriodoReceipTenenciaCreditos, this.receiptInteresesCompData, "AMOUNT", getActContext());
        setUpLabel(this.af.getInteresesCompVto(), this.relativeLayoutInteresesPunitoriosReceipTenenciaCreditos, this.receiptInteresesPunitoriosData, "AMOUNT", getActContext());
        setUpLabel(this.ae.getCargoSeguroVida(), this.relativeLayoutSeguroDeVida, this.receip_textViewSeguroDeVida_data, "AMOUNT", getActContext());
        setUpLabel(this.af.getOtrosImpuestos(), this.relativeLayoutOtrosImpuestosReceipTenenciaCreditos, this.receiptOtrosImpuestosData, "AMOUNT", getActContext());
        this.textViewIVATittleReceipTenenciaCreditos.setContentDescription(getContext().getString(R.string.IVA));
        setUpLabel(this.af.getIva(), this.linearLayoutIVAReceipTenenciaCreditos, this.textViewIVADataReceipTenenciaCreditos, "AMOUNT", getActContext());
        setUpLabel(this.ae.getTasatna(), this.relativeLayoutTasaNominalAnualReceipTenenciaCreditos, this.receiptTnaData, PERCENTAJE, getActContext());
        setUpLabel(this.ae.getTasatea(), this.relativeLayoutTasaEfectivaAnualReceipTenenciaCreditos, this.receiptTeaData, PERCENTAJE, getActContext());
        setUpLabel(this.ae.getTasacftna(), this.relativeLayoutCFTEA, this.receipt_CFTEA_data, PERCENTAJE, getActContext());
        setUpLabel(this.ae.getTasactfnaiva(), this.relativeLayoutCFTEASinIVA, this.receipt_CFTEA_SinIVA_data, PERCENTAJE, getActContext());
        setUpLabel(this.af.getImporteCuotaUVA(), this.relativeImporteDeCuotaPuraUVASReceip, this.textViewImporteDeCuotaPuraUVAS_dataReceip, "AMOUNT", getActContext());
        setUpLabel(this.af.getCapitalUVA(), this.capital_uva_rl_Receip, this.capital_uva_dataReceip, GENERAL, getActContext());
        setUpLabel(this.af.getInteresesUVA(), this.intereses_uva_rl_Receip, this.intereses_uva_data_Receip, PERCENTAJE, getActContext());
        this.receiptFechaData.setText(Html.fromHtml(safeString(this.af.getFechaHora())));
        this.receiptComprobanteData.setText(safeString(this.af.getNroComprobante()));
        try {
            this.receiptFechaData.setContentDescription(this.cAccessibility.applyFilterDateHour(this.receiptFechaData.getText().toString()));
            this.receiptComprobanteData.setContentDescription(this.cAccessibility.applyFilterCharacterToCharacter(this.receiptComprobanteData.getText().toString()));
        } catch (Exception unused5) {
        }
        String leyendaTasa = CCreditos.getLeyendaTasa(this.b.getListaleyendas(), this.ae);
        if (leyendaTasa == null || leyendaTasa.isEmpty()) {
            this.leyenda_tasa_receipt.setVisibility(8);
            return;
        }
        this.leyenda_tasa_receipt.setText(leyendaTasa);
        String applyCFTEA_IVA_3 = CAccessibility.getInstance(getContext()).applyCFTEA_IVA_3(leyendaTasa);
        try {
            applyCFTEA_IVA_3 = CAccessibility.getInstance(getContext()).applyFilterAcronymTasas(applyCFTEA_IVA_3);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.leyenda_tasa_receipt.setContentDescription(applyCFTEA_IVA_3);
    }

    public static void setUpLabel(String str, RelativeLayout relativeLayout, TextView textView) {
        setUpLabel(str, relativeLayout, textView, (String) null, (Context) null);
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void setUpLabel(java.lang.String r1, android.widget.RelativeLayout r2, android.widget.TextView r3, java.lang.String r4, android.content.Context r5) {
        /*
            if (r1 == 0) goto L_0x00c2
            boolean r0 = r1.isEmpty()
            if (r0 == 0) goto L_0x000a
            goto L_0x00c2
        L_0x000a:
            r0 = 0
            r2.setVisibility(r0)
            r3.setText(r1)
            if (r4 == 0) goto L_0x00c7
            if (r5 == 0) goto L_0x00c7
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r5)
            r2 = -1
            int r5 = r4.hashCode()     // Catch:{ Exception -> 0x00c7 }
            switch(r5) {
                case -1275086312: goto L_0x0053;
                case -436740361: goto L_0x0049;
                case 2090926: goto L_0x003f;
                case 523665477: goto L_0x0035;
                case 790540518: goto L_0x002b;
                case 1934443608: goto L_0x0022;
                default: goto L_0x0021;
            }     // Catch:{ Exception -> 0x00c7 }
        L_0x0021:
            goto L_0x005d
        L_0x0022:
            java.lang.String r5 = "AMOUNT"
            boolean r4 = r4.equals(r5)     // Catch:{ Exception -> 0x00c7 }
            if (r4 == 0) goto L_0x005d
            goto L_0x005e
        L_0x002b:
            java.lang.String r5 = "LEYENDA"
            boolean r4 = r4.equals(r5)     // Catch:{ Exception -> 0x00c7 }
            if (r4 == 0) goto L_0x005d
            r0 = 4
            goto L_0x005e
        L_0x0035:
            java.lang.String r5 = "CHARACTERTOCHARACTER"
            boolean r4 = r4.equals(r5)     // Catch:{ Exception -> 0x00c7 }
            if (r4 == 0) goto L_0x005d
            r0 = 1
            goto L_0x005e
        L_0x003f:
            java.lang.String r5 = "DATE"
            boolean r4 = r4.equals(r5)     // Catch:{ Exception -> 0x00c7 }
            if (r4 == 0) goto L_0x005d
            r0 = 2
            goto L_0x005e
        L_0x0049:
            java.lang.String r5 = "PERCENTAJE"
            boolean r4 = r4.equals(r5)     // Catch:{ Exception -> 0x00c7 }
            if (r4 == 0) goto L_0x005d
            r0 = 3
            goto L_0x005e
        L_0x0053:
            java.lang.String r5 = "ACCOUNTNAME"
            boolean r4 = r4.equals(r5)     // Catch:{ Exception -> 0x00c7 }
            if (r4 == 0) goto L_0x005d
            r0 = 5
            goto L_0x005e
        L_0x005d:
            r0 = -1
        L_0x005e:
            switch(r0) {
                case 0: goto L_0x00b2;
                case 1: goto L_0x00a2;
                case 2: goto L_0x0092;
                case 3: goto L_0x0082;
                case 4: goto L_0x0072;
                case 5: goto L_0x0062;
                default: goto L_0x0061;
            }     // Catch:{ Exception -> 0x00c7 }
        L_0x0061:
            goto L_0x00c7
        L_0x0062:
            java.lang.CharSequence r2 = r3.getText()     // Catch:{ Exception -> 0x00c7 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00c7 }
            java.lang.String r1 = r1.applyFilterAccount(r2)     // Catch:{ Exception -> 0x00c7 }
            r3.setContentDescription(r1)     // Catch:{ Exception -> 0x00c7 }
            goto L_0x00c7
        L_0x0072:
            java.lang.CharSequence r2 = r3.getText()     // Catch:{ Exception -> 0x00c7 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00c7 }
            java.lang.String r1 = r1.applyFilterGeneral(r2)     // Catch:{ Exception -> 0x00c7 }
            r3.setContentDescription(r1)     // Catch:{ Exception -> 0x00c7 }
            goto L_0x00c7
        L_0x0082:
            java.lang.CharSequence r2 = r3.getText()     // Catch:{ Exception -> 0x00c7 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00c7 }
            java.lang.String r1 = r1.applyFilterTasaValue(r2)     // Catch:{ Exception -> 0x00c7 }
            r3.setContentDescription(r1)     // Catch:{ Exception -> 0x00c7 }
            goto L_0x00c7
        L_0x0092:
            java.lang.CharSequence r2 = r3.getText()     // Catch:{ Exception -> 0x00c7 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00c7 }
            java.lang.String r1 = r1.applyFilterDate(r2)     // Catch:{ Exception -> 0x00c7 }
            r3.setContentDescription(r1)     // Catch:{ Exception -> 0x00c7 }
            goto L_0x00c7
        L_0x00a2:
            java.lang.CharSequence r2 = r3.getText()     // Catch:{ Exception -> 0x00c7 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00c7 }
            java.lang.String r1 = r1.applyFilterCharacterToCharacter(r2)     // Catch:{ Exception -> 0x00c7 }
            r3.setContentDescription(r1)     // Catch:{ Exception -> 0x00c7 }
            goto L_0x00c7
        L_0x00b2:
            java.lang.CharSequence r2 = r3.getText()     // Catch:{ Exception -> 0x00c7 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00c7 }
            java.lang.String r1 = r1.applyFilterAmount(r2)     // Catch:{ Exception -> 0x00c7 }
            r3.setContentDescription(r1)     // Catch:{ Exception -> 0x00c7 }
            goto L_0x00c7
        L_0x00c2:
            r1 = 8
            r2.setVisibility(r1)
        L_0x00c7:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.fragments.privado.TenenciaCreditosFragment.setUpLabel(java.lang.String, android.widget.RelativeLayout, android.widget.TextView, java.lang.String, android.content.Context):void");
    }

    public static void setUpLabel(String str, LinearLayout linearLayout, TextView textView) {
        setUpLabel(str, linearLayout, textView, (String) null, (Context) null);
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void setUpLabel(java.lang.String r1, android.widget.LinearLayout r2, android.widget.TextView r3, java.lang.String r4, android.content.Context r5) {
        /*
            if (r1 == 0) goto L_0x00c2
            boolean r0 = r1.isEmpty()
            if (r0 == 0) goto L_0x000a
            goto L_0x00c2
        L_0x000a:
            r0 = 0
            r2.setVisibility(r0)
            r3.setText(r1)
            if (r4 == 0) goto L_0x00c7
            if (r5 == 0) goto L_0x00c7
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r5)
            r2 = -1
            int r5 = r4.hashCode()     // Catch:{ Exception -> 0x00c7 }
            switch(r5) {
                case -1275086312: goto L_0x0053;
                case -436740361: goto L_0x0049;
                case 2090926: goto L_0x003f;
                case 523665477: goto L_0x0035;
                case 637834440: goto L_0x002b;
                case 1934443608: goto L_0x0022;
                default: goto L_0x0021;
            }     // Catch:{ Exception -> 0x00c7 }
        L_0x0021:
            goto L_0x005d
        L_0x0022:
            java.lang.String r5 = "AMOUNT"
            boolean r4 = r4.equals(r5)     // Catch:{ Exception -> 0x00c7 }
            if (r4 == 0) goto L_0x005d
            goto L_0x005e
        L_0x002b:
            java.lang.String r5 = "GENERAL"
            boolean r4 = r4.equals(r5)     // Catch:{ Exception -> 0x00c7 }
            if (r4 == 0) goto L_0x005d
            r0 = 4
            goto L_0x005e
        L_0x0035:
            java.lang.String r5 = "CHARACTERTOCHARACTER"
            boolean r4 = r4.equals(r5)     // Catch:{ Exception -> 0x00c7 }
            if (r4 == 0) goto L_0x005d
            r0 = 1
            goto L_0x005e
        L_0x003f:
            java.lang.String r5 = "DATE"
            boolean r4 = r4.equals(r5)     // Catch:{ Exception -> 0x00c7 }
            if (r4 == 0) goto L_0x005d
            r0 = 2
            goto L_0x005e
        L_0x0049:
            java.lang.String r5 = "PERCENTAJE"
            boolean r4 = r4.equals(r5)     // Catch:{ Exception -> 0x00c7 }
            if (r4 == 0) goto L_0x005d
            r0 = 3
            goto L_0x005e
        L_0x0053:
            java.lang.String r5 = "ACCOUNTNAME"
            boolean r4 = r4.equals(r5)     // Catch:{ Exception -> 0x00c7 }
            if (r4 == 0) goto L_0x005d
            r0 = 5
            goto L_0x005e
        L_0x005d:
            r0 = -1
        L_0x005e:
            switch(r0) {
                case 0: goto L_0x00b2;
                case 1: goto L_0x00a2;
                case 2: goto L_0x0092;
                case 3: goto L_0x0082;
                case 4: goto L_0x0072;
                case 5: goto L_0x0062;
                default: goto L_0x0061;
            }     // Catch:{ Exception -> 0x00c7 }
        L_0x0061:
            goto L_0x00c7
        L_0x0062:
            java.lang.CharSequence r2 = r3.getText()     // Catch:{ Exception -> 0x00c7 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00c7 }
            java.lang.String r1 = r1.applyFilterAccount(r2)     // Catch:{ Exception -> 0x00c7 }
            r3.setContentDescription(r1)     // Catch:{ Exception -> 0x00c7 }
            goto L_0x00c7
        L_0x0072:
            java.lang.CharSequence r2 = r3.getText()     // Catch:{ Exception -> 0x00c7 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00c7 }
            java.lang.String r1 = r1.applyFilterGeneral(r2)     // Catch:{ Exception -> 0x00c7 }
            r3.setContentDescription(r1)     // Catch:{ Exception -> 0x00c7 }
            goto L_0x00c7
        L_0x0082:
            java.lang.CharSequence r2 = r3.getText()     // Catch:{ Exception -> 0x00c7 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00c7 }
            java.lang.String r1 = r1.applyFilterTasaValue(r2)     // Catch:{ Exception -> 0x00c7 }
            r3.setContentDescription(r1)     // Catch:{ Exception -> 0x00c7 }
            goto L_0x00c7
        L_0x0092:
            java.lang.CharSequence r2 = r3.getText()     // Catch:{ Exception -> 0x00c7 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00c7 }
            java.lang.String r1 = r1.applyFilterDate(r2)     // Catch:{ Exception -> 0x00c7 }
            r3.setContentDescription(r1)     // Catch:{ Exception -> 0x00c7 }
            goto L_0x00c7
        L_0x00a2:
            java.lang.CharSequence r2 = r3.getText()     // Catch:{ Exception -> 0x00c7 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00c7 }
            java.lang.String r1 = r1.applyFilterCharacterToCharacter(r2)     // Catch:{ Exception -> 0x00c7 }
            r3.setContentDescription(r1)     // Catch:{ Exception -> 0x00c7 }
            goto L_0x00c7
        L_0x00b2:
            java.lang.CharSequence r2 = r3.getText()     // Catch:{ Exception -> 0x00c7 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00c7 }
            java.lang.String r1 = r1.applyFilterAmount(r2)     // Catch:{ Exception -> 0x00c7 }
            r3.setContentDescription(r1)     // Catch:{ Exception -> 0x00c7 }
            goto L_0x00c7
        L_0x00c2:
            r1 = 8
            r2.setVisibility(r1)
        L_0x00c7:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.fragments.privado.TenenciaCreditosFragment.setUpLabel(java.lang.String, android.widget.LinearLayout, android.widget.TextView, java.lang.String, android.content.Context):void");
    }

    public View getPagePayment() {
        return this.pagePayment;
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

    public ViewGroup getWrapperDataCreate() {
        return this.vgWrapperCreateLongTermDeposit;
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
    }

    public void addBlockBodyErrorConfirmarPagoRes4(String str) {
        this.presenterLongTermDeposit.setListeners();
        this.getConfirmPayment.setVisibility(8);
        this.confirmNextButton.setVisibility(8);
        this.vgErrorWSRes.setVisibility(0);
        if (str != null) {
            this.msgErrorConfirmPago.setText(Html.fromHtml(str));
        }
        this.imgErrorConfirmPago.setImageResource(R.drawable.error_continuacion);
        this.mAnalyticsManager.trackScreen(getString(R.string.analytics_screen_name_confirm_payment_services_home));
    }

    public void addBlockBodyErrorConfirmarPagoRes8(String str, String str2) {
        this.presenterLongTermDeposit.setListeners();
        this.getConfirmPayment.setVisibility(8);
        this.confirmNextButton.setVisibility(8);
        this.vgErrorWSRes.setVisibility(0);
        this.confirmTitle.setText(str2);
        if (str != null) {
            this.msgErrorConfirmPago.setText(Html.fromHtml(Utils.formatIsbanHTMLCode(str)));
        }
        this.imgErrorConfirmPago.setImageResource(R.drawable.ico_reloj_gris);
        this.msgErrorConfirmPago.setContentDescription(new CAccessibility(getActContext()).applyFilterTimeAvailability(this.msgErrorConfirmPago.getText().toString()));
        CAccessibility.sendAccessibilityEvent(getActContext(), this.msgErrorConfirmPago.getContentDescription().toString());
        this.mAnalyticsManager.trackScreen(getString(R.string.analytics_screen_name_confirm_payment_services_home));
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
        if (!TextUtils.isEmpty(str)) {
            this.txtLblLeyendaInDetail.setText(Html.fromHtml(str));
            this.txtLblLeyendaInDetail.setVisibility(0);
        } else {
            this.txtLblLeyendaInDetail.setVisibility(8);
        }
        if (cnsTenenciasDatosPFBean.infoAdicional.equalsIgnoreCase("1")) {
            this.lnlCapitalUvi.setVisibility(0);
            this.txtValorUvaDetail.setText(cnsTenenciasDatosPFBean.valorUVA);
            if (!TextUtils.isEmpty(str)) {
                this.txtInteresesLabel.setText(String.format("%s%s", new Object[]{getString(R.string.ID3010_DETALLETRADARS_LBL_INTERESES), Constants.LEYEND_ASTERISK}));
            }
        } else {
            this.lnlCapitalUvi.setVisibility(8);
            this.txtInteresesLabel.setText(R.string.ID3010_DETALLETRADARS_LBL_INTERESES);
            this.txtValorUvaDetail.setText("");
        }
        this.saldoAnteriorDeCapital.setText(getString(R.string.Saldo_Anterior_De_Capital_Sin_Ajustar_UVAS));
        try {
            this.txtMontoInDetail.setContentDescription(CAccessibility.getInstance(getActContext()).applyFilterAmount(this.txtMontoInDetail.getText().toString()));
            TextView textView = this.txtVencimientoInDetail;
            StringBuilder sb = new StringBuilder();
            sb.append(CAccessibility.getInstance(getActContext()).applyFilterGeneral(getString(R.string.IN_VTO)));
            sb.append(CAccessibility.getInstance(getActContext()).applyFilterDate(UtilDate.getDateFormat(cnsTenenciasDatosPFBean.fechaProxVencimiento, Constants.FORMAT_DATE_DASH, Constants.FORMAT_DATE_APP_2)));
            textView.setContentDescription(sb.toString());
            this.txtCapitalInDetail.setContentDescription(CAccessibility.getInstance(getActContext()).applyFilterAmount(this.txtCapitalInDetail.getText().toString()));
            this.txtInteresesInDetail.setContentDescription(CAccessibility.getInstance(getActContext()).applyFilterAmount(this.txtInteresesInDetail.getText().toString()));
            this.txtTasaInDetail.setContentDescription(CAccessibility.getInstance(getActContext()).applyFilterTasaValue(this.txtTasaInDetail.getText().toString()));
            this.txtImpuestosInDetail.setContentDescription(CAccessibility.getInstance(getActContext()).applyFilterAmount(this.txtImpuestosInDetail.getText().toString()));
            this.txtConstitucionInDetail.setContentDescription(CAccessibility.getInstance(getActContext()).applyFilterDate(this.txtConstitucionInDetail.getText().toString()));
            this.txtCertificadoInDetail.setContentDescription(CAccessibility.getInstance(getActContext()).applyFilterCharacterToCharacter(this.txtCertificadoInDetail.getText().toString()));
            this.txtSucursalInDetail.setContentDescription(CAccessibility.getInstance(getActContext()).applyFilterCharacterToCharacter(this.txtSucursalInDetail.getText().toString()));
            this.txtTasaLabel.setContentDescription(CAccessibility.getInstance(getActContext()).applyFilterTasaInteres(this.txtTasaLabel.getText().toString()));
            this.txtViewSaldoAnteriorLabel.setContentDescription(CAccessibility.getInstance(getActContext()).applyFilterGeneral(this.txtViewSaldoAnteriorLabel.getText().toString()));
            this.txtAccionVencimientoLabel.setContentDescription(CAccessibility.getInstance(getActContext()).applyFilterGeneral(this.txtAccionVencimientoLabel.getText().toString()));
            this.txtCapitalUviLabel.setContentDescription(CAccessibility.getInstance(getActContext()).applyFilterGeneral(this.txtCapitalUviLabel.getText().toString()));
            this.txtValorUvaDetail.setContentDescription(CAccessibility.getInstance(getActContext()).applyFilterGeneral(this.txtValorUvaDetail.getText().toString()));
            this.txtLblLeyendaInDetail.setContentDescription(CAccessibility.getInstance(getActContext()).applyFilterGeneral(this.txtLblLeyendaInDetail.getText().toString()));
            this.creditHeaderData.setContentDescription(CAccessibility.getInstance(getActContext()).applyFilterAmount(this.creditHeaderData.getText().toString()));
            this.loanNumberData.setContentDescription(CAccessibility.getInstance(getActContext()).applyFilterCharacterToCharacter(this.loanNumberData.getText().toString()));
            this.accountData.setContentDescription(CAccessibility.getInstance(getActContext()).applyFilterCharacterToCharacter(this.accountData.getText().toString()));
            this.saldoAnteriorSinAjustarData.setContentDescription(CAccessibility.getInstance(getActContext()).applyFilterAmount(this.saldoAnteriorSinAjustarData.getText().toString()));
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
            rowSelectorAccount.setOnClickListener(this);
        }
    }

    public void setListenerClickRowCurrency() {
        View findViewById = this.mMainView.findViewById(R.id.row_selector_currency_id);
        if (findViewById != null) {
            findViewById.setOnClickListener(this);
        }
    }

    public View getRowSelectorAccount() {
        return this.mMainView.findViewById(R.id.row_selector_account_id);
    }

    public View getViewSelectorAccount() {
        return this.mMainView.findViewById(R.id.selector_account_id_2);
    }

    public void setLabelSelectorAccount(String str, String str2) {
        TextView textView = (TextView) getViewSelectorAccount();
        TextView textView2 = (TextView) this.mMainView.findViewById(R.id.payment_monto_cuenta);
        CAccessibility cAccessibility2 = new CAccessibility(getActivity().getApplicationContext());
        if (textView != null) {
            textView.setText(str);
            String[] split = str2.split(UtilsCuentas.SEPARAOR2);
            StringBuilder sb = new StringBuilder();
            sb.append(split[split.length - 2]);
            sb.append(UtilsCuentas.SEPARAOR2);
            sb.append(split[split.length - 1]);
            String sb2 = sb.toString();
            textView2.setText(sb2);
            try {
                textView.setContentDescription(cAccessibility2.applyFilterAccount(str));
                textView2.setContentDescription(cAccessibility2.applyFilterAmount(sb2));
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
        newInstance.show(getActivity().getSupportFragmentManager(), DIALOG_SELECTOR);
    }

    public void configActionBarShared() {
        ((SantanderRioMainActivity) getActivity()).setActionBarType(ActionBarType.MAIN_WITH_MENU);
        View customView = ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView();
        ImageView imageView = (ImageView) customView.findViewById(R.id.menu);
        ((ImageView) customView.findViewById(R.id.toggle)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                TenenciaCreditosFragment.this.switchDrawer();
            }
        });
        imageView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                TenenciaCreditosFragment.this.showCreditOptions();
            }
        });
    }

    public void showCreditOptions() {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        arrayList.add("Solicitar Préstamo Personal");
        arrayList.add("Usar Open Credit");
        final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("popUpDebin", null, null, arrayList, getString(R.string.F32_00_4412), null, null, "", arrayList2);
        newInstance.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onItemSelected(String str) {
                if (str.equalsIgnoreCase("Solicitar Préstamo Personal")) {
                    TenenciaCreditosFragment.this.consultarSolicitudCrediticia();
                }
            }

            public void onSimpleActionButton() {
                newInstance.dismiss();
            }
        });
        newInstance.show(getFragmentManager(), "popUpDebin");
    }

    public void setListenerClickExpiredDate() {
        View findViewById = this.mMainView.findViewById(R.id.row_selector_date_id);
        if (findViewById != null) {
            findViewById.setOnClickListener(this);
        }
    }

    private void a(final ViewGroup viewGroup, final View view) {
        if (viewGroup != null) {
            viewGroup.post(new Runnable() {
                public void run() {
                    viewGroup.addView(view);
                    if (TenenciaCreditosFragment.this.mControlPager.getVisibility() == 8) {
                        TenenciaCreditosFragment.this.mControlPager.setVisibility(0);
                    }
                    String str = "";
                    for (int i = 0; i < TenenciaCreditosFragment.this.ah.getDatosleyenda().size(); i++) {
                        if (((DatosLeyenda) TenenciaCreditosFragment.this.ah.getDatosleyenda().get(i)).getIdleyenda().equals(CreditosConstants.TC_TEN_CRED)) {
                            str = ((DatosLeyenda) TenenciaCreditosFragment.this.ah.getDatosleyenda().get(i)).getDescripcion();
                        }
                    }
                    TenenciaCreditosFragment.this.txtLblLeyendaInDetail.setText(Html.fromHtml(str));
                    try {
                        TenenciaCreditosFragment.this.txtLblLeyendaInDetail.setContentDescription(CAccessibility.getInstance(TenenciaCreditosFragment.this.getContext()).applyFilterGeneral(TenenciaCreditosFragment.this.txtLblLeyendaInDetail.getText().toString()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    TenenciaCreditosFragment.this.presenterLongTermDeposit.setListeners();
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

    public void onClick(View view) {
        if (SystemClock.elapsedRealtime() - this.a >= 1000) {
            if (view.getId() == R.id.row_selector_account_id) {
                this.presenterLongTermDeposit.onSelectorAccountClicked();
            } else if (view.getId() == R.id.row_selector_currency_id) {
                this.presenterLongTermDeposit.onSelectorCurrencyClicked();
            } else if (view.getId() == R.id.row_selector_date_id) {
                this.presenterLongTermDeposit.onSelectorExpiredDateClicked();
            } else if (view.getId() == R.id.row_selector_expired_action_id) {
                this.presenterLongTermDeposit.onExpiredActionClicked();
            } else if (view.getId() == R.id.F10_00_btn_nuevoPlazoFijo) {
                consultarSolicitudCrediticia();
            } else if (view.getId() == R.id.F10_00_lbl_linkTasas) {
                this.presenterLongTermDeposit.onGoToRatesClicked("FROM_LIST");
            } else if (view.getId() == R.id.F10_CREATE_lbl_linkTasas) {
                this.presenterLongTermDeposit.onGoToRatesClicked("FROM_CREATE");
            } else if (view.getId() == R.id.F10_02_lnl_cambiarMoneda) {
                this.presenterLongTermDeposit.onChangeCurrencyClicked();
            } else if (view.getId() == R.id.tenencia_de_prestamos_detail_button) {
                this.presenterLongTermDeposit.onRedButtonClicked();
            } else if (view.getId() == R.id.tenencia_de_prestamos_payment_button) {
                this.presenterLongTermDeposit.onRedButtonClicked();
            } else if (view.getId() == R.id.tenencia_de_prestamos_confirm_button) {
                this.confirmNextButton.setContentDescription(getString(R.string.boton_confirmar_pago_credito).concat(Html.fromHtml(this.ae.getNombrecredito()).toString()));
                this.presenterLongTermDeposit.onRedButtonClicked();
            } else if (view.getId() == R.id.tenencia_de_prestamos_receipt_button) {
                onCreditoIrCreditos();
            } else if (view.getId() == R.id.credit_account_row_selector) {
                this.presenterLongTermDeposit.onSelectorAccountClicked();
            } else if (view.getId() == R.id.relativeLayoutDatosCuenta) {
                this.presenterLongTermDeposit.onSelectorAccountClicked();
            }
            this.a = SystemClock.elapsedRealtime();
        }
    }

    public void onCreditoIrCreditos() {
        if (!this.c) {
            rememberShareReceipt();
        } else {
            this.presenterLongTermDeposit.onRedButtonClicked();
        }
    }

    public void createSolicitarCreditosFragment() {
        ((SantanderRioMainActivity) getActivity()).changeFragmentAnimation(CreditosFragment.newInstance(this.ai, this.ak, this.al));
        loadBarReturn();
    }

    public void consultarSolicitudCrediticia() {
        if (!(this.mSessionManager == null || this.mSessionManager.getLoginUnico() == null || this.mSessionManager.getLoginUnico().getProductos() == null || this.mSessionManager.getLoginUnico().getProductos().getCuentas() == null)) {
            Cuentas cuentas = this.mSessionManager.getLoginUnico().getProductos().getCuentas();
            if (cuentas.getCuentas() != null && cuentas.getCuentas().size() > 0) {
                this.aj = cuentas.getCuentas();
            }
        }
        ConsultaSolicitudCrediticiaBodyRequestBean consultaSolicitudCrediticiaBodyRequestBean = new ConsultaSolicitudCrediticiaBodyRequestBean();
        ArrayList arrayList = new ArrayList();
        if (this.aj != null && this.aj.size() > 0) {
            for (Cuenta cuenta : this.aj) {
                DatosCuenta datosCuenta = new DatosCuenta();
                datosCuenta.setNroCta(UtilAccount.formatNumeroCuenta(cuenta.getNumberAccount()));
                datosCuenta.setSucursalCta(UtilAccount.formatSucursalCuenta(cuenta.getNroSuc()));
                arrayList.add(datosCuenta);
            }
        }
        ListaCuentas listaCuentas = new ListaCuentas();
        listaCuentas.setListaDatosCuenta(arrayList);
        consultaSolicitudCrediticiaBodyRequestBean.setListaCuentas(listaCuentas);
        showDialogLoading();
        this.mDataManager.consultaSolicitudCrediticia(consultaSolicitudCrediticiaBodyRequestBean);
    }

    @Subscribe
    public void onConsultaSolicitudCrediticia(ConsultaSolicitudCrediticiaEvent consultaSolicitudCrediticiaEvent) {
        final ConsultaSolicitudCrediticiaEvent consultaSolicitudCrediticiaEvent2 = consultaSolicitudCrediticiaEvent;
        AnonymousClass9 r0 = new BaseWSResponseHandler(getActivity(), TypeOption.INITIAL_VIEW, "", (BaseActivity) getActivity()) {
            /* access modifiers changed from: protected */
            public void onOk() {
                TenenciaCreditosFragment.this.ai = ((ConsultaSolicitudCrediticiaResponseBean) consultaSolicitudCrediticiaEvent2.getBeanResponse()).getConsultaSolicitudCrediticiaBodyResponseBean();
                AccountResponseBean accountResponseBean = TenenciaCreditosFragment.this.ai.getConsultaCalificacionCrediticia().getAccountResponseBean();
                AccountRequestBean accountRequestBean = new AccountRequestBean();
                for (Cuenta cuenta : TenenciaCreditosFragment.this.aj) {
                    if (cuenta.getNroSuc().contains(accountResponseBean.sucursal) && cuenta.getNumero().contains(accountResponseBean.numero)) {
                        accountRequestBean.divisa = "0";
                        accountRequestBean.sucursalCta = cuenta.getNroSuc();
                        accountRequestBean.nroCta = accountResponseBean.numero;
                        accountRequestBean.sucursalPaq = cuenta.getSucursalPaq();
                        accountRequestBean.nroPaq = cuenta.getNroPaq();
                        accountRequestBean.tipo = cuenta.getTipo();
                        accountRequestBean.sucursal = cuenta.getSucursal();
                        accountRequestBean.numero = cuenta.getNumero();
                    }
                }
                TenenciaCreditosFragment.this.ak = accountRequestBean;
                TenenciaCreditosFragment.this.sendRequestConsultaPrestamosPermitidos(TenenciaCreditosFragment.this.mDataManager, true);
            }

            /* access modifiers changed from: protected */
            public void commonAllErrorsAfterProcess(WebServiceEvent webServiceEvent) {
                TenenciaCreditosFragment.this.closeDialogLoading();
                super.commonAllErrorsAfterProcess(webServiceEvent);
            }

            /* access modifiers changed from: protected */
            public void onRes1Error(WebServiceEvent webServiceEvent) {
                super.onRes1Error(webServiceEvent);
            }

            /* access modifiers changed from: protected */
            public void onRes1Error() {
                super.onRes1Error();
            }

            /* access modifiers changed from: protected */
            public void onRes8Error() {
                TenenciaCreditosFragment.this.pageList.setImportantForAccessibility(4);
                EmptyFragment newInstance = EmptyFragment.newInstance(EmptyFragment.TITLE_TENENCIA, Html.fromHtml(consultaSolicitudCrediticiaEvent2.getErrorBodyBean().resDesc).toString(), R.drawable.ico_reloj_gris);
                FragmentManager supportFragmentManager = TenenciaCreditosFragment.this.getActivity().getSupportFragmentManager();
                if (supportFragmentManager != null) {
                    TenenciaCreditosFragment.this.loadBarReturn();
                    supportFragmentManager.beginTransaction().add(R.id.content_frame, newInstance, EmptyFragment.TITLE_TENENCIA).addToBackStack(EmptyFragment.TITLE_TENENCIA).commit();
                }
            }
        };
        r0.handleWSResponse(consultaSolicitudCrediticiaEvent);
    }

    @Subscribe
    public void onConsultaPrestamosPermitidos(ConsultaPrestamosPermitidosEvent consultaPrestamosPermitidosEvent) {
        closeDialogLoading();
        final ConsultaPrestamosPermitidosEvent consultaPrestamosPermitidosEvent2 = consultaPrestamosPermitidosEvent;
        AnonymousClass10 r0 = new BaseWSResponseHandler(getActivity(), TypeOption.INITIAL_VIEW, "", (BaseActivity) getActivity()) {
            /* access modifiers changed from: protected */
            public void onOk() {
                TenenciaCreditosFragment.this.al = ((ConsultaPrestamosPermitidosResponseBean) consultaPrestamosPermitidosEvent2.getBeanResponse()).consultaPrestamosPermitidosBodyResponseBean;
                TenenciaCreditosFragment.this.createSolicitarCreditosFragment();
            }

            /* access modifiers changed from: protected */
            public void commonAllErrorsAfterProcess(WebServiceEvent webServiceEvent) {
                super.commonAllErrorsAfterProcess(webServiceEvent);
                TenenciaCreditosFragment.this.closeDialogLoading();
            }
        };
        r0.handleWSResponse(consultaPrestamosPermitidosEvent);
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
        } else if (str2.equals("IDID")) {
            Log.d("Eligió", str);
            if (str.equals(f232VER_DETALLE_DEL_PRSTAMO_OPTION)) {
                Intent intent = new Intent(getActContext(), ListDetailsActivity.class);
                String leyendaTasaDetalleCuota = CCreditos.getLeyendaTasaDetalleCuota(this.b.getListaleyendas(), this.i);
                intent.putExtra("credito_seleccionado", this.ae);
                intent.putExtra("desc_categoria", this.ad.desccategoriacredito);
                intent.putExtra("detalle_cuota", this.i);
                intent.putExtra("tasa_leyendas", leyendaTasaDetalleCuota);
                startActivity(intent);
            } else if (str.equals(VER_PROXIMAS_CUOTAS_OPTION)) {
                getDataManager().getProximasCuotas(this.ae.getNrocredito(), this.ae.getSuccredito(), this.ae.getTipocredito());
                showDialogLoading();
            }
        }
    }

    public void onPositiveButton(String str) {
        if ("dialog_confirm".equals(str)) {
            getDataManager().getConfirmarPago(this.ag.getSucursal(), this.ag.getTipoCta(), this.ag.getNumero(), this.ae.getSuccredito(), this.ae.getTipocredito(), this.ae.getNrocredito(), this.ae.getImpoteCuota().replace("$", "").replace(UtilsCuentas.SEPARAOR2, ""), this.ae.getIdentificadorUVA());
            showDialogLoading();
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
            findViewById.setOnClickListener(this);
        }
    }

    public void setListenerClickGoToCreateNewAction() {
        View findViewById = this.mMainView.findViewById(R.id.F10_00_btn_nuevoPlazoFijo);
        if (findViewById != null) {
            findViewById.setOnClickListener(this);
        }
    }

    public void setListenerClickGoToRatesAction() {
        View findViewById = this.mMainView.findViewById(R.id.F10_00_lbl_linkTasas);
        if (findViewById != null) {
            findViewById.setOnClickListener(this);
        }
        View findViewById2 = this.mMainView.findViewById(R.id.F10_CREATE_lbl_linkTasas);
        if (findViewById2 != null) {
            findViewById2.setOnClickListener(this);
        }
    }

    public void setListenerClickChangeCurrency() {
        View findViewById = this.mMainView.findViewById(R.id.F10_02_lnl_cambiarMoneda);
        if (findViewById != null) {
            findViewById.setOnClickListener(this);
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
                TenenciaCreditosFragment.this.presenterLongTermDeposit.onExpiredDateSelected(date);
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
        ((SantanderRioMainActivity) getActivity()).goToOption(FragmentConstants.TENENCIA_CREDITOS);
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
        this.presenterLongTermDeposit.onBtnReceiptClicked();
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
        if (this.h != null && !this.h.isVisible()) {
            this.h.show(getActivity().getSupportFragmentManager(), "loading");
        }
    }

    public void closeDialogLoading() {
        this.h.dismiss();
    }

    public boolean isDialogLoadingVisible() {
        return this.h.isVisible();
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

    public void setCountDetailCuot(String str) {
        this.selector_account_id_2.setText(str);
    }

    public void setCountDetailCuot2(String str, String str2) {
        this.selector_account_id_2.setText(str);
        this.payment_monto_cuenta.setText(str2);
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
                    String expirationDate = TenenciaCreditosFragment.this.presenterLongTermDeposit.getExpirationDate(obj);
                    if (UtilDate.isDate(expirationDate, Constants.FORMAT_DATE_APP_2)) {
                        TenenciaCreditosFragment.this.setValueExpiredDate(expirationDate);
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

    public void onShowErrorRes1(WebServiceEvent webServiceEvent) {
        FragmentManager supportFragmentManager = getActivity().getSupportFragmentManager();
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(webServiceEvent.getTitleToShow(), Utils.formatIsbanHTMLCode(Html.fromHtml(webServiceEvent.getMessageToShow()).toString()), null, null, getActContext().getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
        newInstance.setDialogListenerExtended(this);
        newInstance.show(supportFragmentManager, "Dialog");
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
        ((BaseActivity) getActivity()).setActionBarType(ActionBarType.BACK);
        z();
        E().lockMenu(true);
    }

    public void loadBarClose() {
        ((BaseActivity) getActivity()).setActionBarType(ActionBarType.PUSH_CLOSE);
        B();
        E().lockMenu(true);
    }

    public void loadBarShare() {
        ((BaseActivity) getActivity()).setActionBarType(ActionBarType.SHARE);
        D();
        E().lockMenu(false);
    }

    public void loadBarBackMenu() {
        ((BaseActivity) getActivity()).setActionBarType(ActionBarType.BACK_WITH_MENU);
        A();
        E().lockMenu(false);
    }

    public void loadBarCloseAndReturn() {
        ((BaseActivity) getActivity()).setActionBarType(ActionBarType.PUSH_CLOSE);
        C();
        E().lockMenu(false);
    }

    public void restartMainActionBar() {
        E().restartActionBar();
        E().lockMenu(false);
        ((BaseActivity) getActivity()).setActionBarType(ActionBarType.MENU);
        View customView = ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView();
        if (customView != null) {
            customView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    ((SantanderRioMainActivity) TenenciaCreditosFragment.this.getActivity()).switchDrawer();
                }
            });
        }
    }

    private void z() {
        View customView = ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView();
        if (customView != null) {
            this.e = customView.findViewById(R.id.toggle);
            if (this.e != null) {
                this.e.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        TenenciaCreditosFragment.this.onBackPressed();
                    }
                });
            }
        }
    }

    private void A() {
        ((BaseActivity) getActivity()).setActionBarType(ActionBarType.BACK_WITH_MENU);
        View customView = ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView();
        if (customView != null) {
            this.e = customView.findViewById(R.id.back_imgButton);
            if (this.e != null) {
                this.e.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        TenenciaCreditosFragment.this.onBackPressed();
                    }
                });
            }
        }
        this.f = customView.findViewById(R.id.menu);
        if (this.f != null) {
            this.f.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (TenenciaCreditosFragment.this.getCurrentIndexViewPage() == 1) {
                        TenenciaCreditosFragment.this.onClickShowActionDetails(TenenciaCreditosFragment.this.ae);
                    } else {
                        TenenciaCreditosFragment.this.onClickShowActionShareReceipt();
                    }
                }
            });
        }
    }

    private void B() {
        View customView = ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView();
        if (customView != null) {
            this.g = customView.findViewById(R.id.ok);
            if (this.g != null) {
                this.g.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        TenenciaCreditosFragment.this.onBackPressed();
                    }
                });
            }
        }
    }

    private void C() {
        View customView = ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView();
        if (customView != null) {
            this.g = customView.findViewById(R.id.ok);
            if (this.g != null) {
                this.g.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        TenenciaCreditosFragment.this.onBackPressed();
                    }
                });
            }
            this.e = customView.findViewById(R.id.toggle);
            if (this.e != null) {
                this.e.setVisibility(0);
                this.e.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        TenenciaCreditosFragment.this.onBackPressed();
                    }
                });
            }
        }
    }

    private void D() {
        View customView = ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView();
        if (customView != null) {
            this.f = customView.findViewById(R.id.share);
            if (this.f != null) {
                this.f.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        TenenciaCreditosFragment.this.onClickShowActionShareReceipt();
                    }
                });
            }
            View findViewById = customView.findViewById(R.id.toggle);
            if (findViewById != null) {
                findViewById.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (TenenciaCreditosFragment.this.isForgetShareReceipt()) {
                            TenenciaCreditosFragment.this.rememberShareReceipt();
                        } else {
                            TenenciaCreditosFragment.this.switchDrawer();
                        }
                    }
                });
            }
        }
    }

    public void onClickShowActionShareReceipt() {
        this.c = true;
        F();
        if (this.d != null) {
            this.d.show(getActContext().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_CANCEL));
        }
    }

    public void onClickShowActionDetails(DatosCredito datosCredito) {
        this.c = true;
        ArrayList arrayList = new ArrayList();
        if (datosCredito != null) {
            arrayList.add(f232VER_DETALLE_DEL_PRSTAMO_OPTION);
            if (datosCredito.getOpcionProxCuota() != null && !datosCredito.getOpcionProxCuota().equals("N")) {
                arrayList.add(VER_PROXIMAS_CUOTAS_OPTION);
            }
            FragmentManager supportFragmentManager = getActivity().getSupportFragmentManager();
            IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("IDID", "Seleccionar", null, arrayList, PagoTarjetasConstants.ISBAN_DIALOG_CANCEL_BUTTON_TEXT, null, null, null);
            newInstance.setDialogListenerExtended(this);
            newInstance.show(supportFragmentManager, "DialogConfirm");
        }
    }

    /* access modifiers changed from: private */
    public SantanderRioMainActivity E() {
        return (SantanderRioMainActivity) getActivity();
    }

    public void actionBarLoaded(int i2, View view) {
        if (i2 == 0) {
            z();
        } else if (i2 == 1) {
            D();
        } else if (i2 == 2) {
            B();
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
        AnonymousClass24 r0 = new OptionsToShareImpl(this, getActivity().getApplicationContext(), getActivity().getSupportFragmentManager()) {
            public View getViewToShare() {
                return TenenciaCreditosFragment.this.scrollViewReceipt;
            }

            public void receiveIntentAppShare(Intent intent) {
                TenenciaCreditosFragment.this.startActivityForResult(Intent.createChooser(intent, TenenciaCreditosFragment.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)), 999);
            }

            public String getFileName() {
                return str3;
            }

            public String getSubjectReceiptToShare() {
                return str4;
            }

            public void onOption2Button() {
                super.onOption2Button();
                ((SantanderRioMainActivity) TenenciaCreditosFragment.this.getActivity()).goToOption(FragmentConstants.CREDITOS);
            }
        };
        this.d = r0;
    }

    public void rememberShareReceipt() {
        if (!this.c) {
            if (this.d == null) {
                F();
            }
            this.c = true;
            this.d.showAlert();
        }
    }

    private void F() {
        String str = "Comprobante de Pago de Cuota";
        configActionShareReceipt(str, str.concat("-").concat(this.af.getNroComprobante()));
    }

    public boolean isForgetShareReceipt() {
        return getCurrentIndexViewPage() == 4 && !this.c;
    }

    public void startListaProximasCuotasActivity(ProximasCuotas proximasCuotas) {
        Intent intent = new Intent(getActContext(), ProximasCuotasActivity.class);
        intent.putExtra("linea_total", this.i.getImporteSolicitado());
        intent.putExtra("nombre_credito", this.ae.getNombrecredito());
        intent.putExtra("nro_credito", this.ae.getDescripcredito());
        intent.putExtra("intereses_comp", this.ae.getInteresescomp());
        intent.putExtra("tipo_credito", this.ae.getTipocredito());
        intent.putExtra("capital", this.ae.getCapitalcuotacredito());
        intent.putExtra("iva", this.ae.getTasactfnaiva());
        intent.putExtra("otros_importes", this.ae.getOtrosImpuestos());
        intent.putExtra("lista_cuotas", proximasCuotas);
        intent.putExtra("lista_leyendas", this.ah);
        intent.putExtra("selected_credit", this.ae);
        intent.putExtra("cuenta_debito", this.i.getCuentaDebito());
        intent.putExtra("disponible_usos", this.i.getDisponibleUsos());
        intent.putExtra("saldo_deuda_capital", this.i.getSaldoDeudaCapital());
        intent.putExtra("plazo_credito", this.i.getPlazoCredito());
        String str = "No hay leyenda";
        for (int i2 = 0; i2 < this.ah.getDatosleyenda().size(); i2++) {
            if (((DatosLeyenda) this.ah.getDatosleyenda().get(i2)).getIdleyenda().equals(CreditosConstants.TC_PROX_CUOTAS)) {
                str = ((DatosLeyenda) this.ah.getDatosleyenda().get(i2)).getDescripcion();
            }
        }
        intent.putExtra("des_leyenda", str);
        startActivity(intent);
    }

    public void setListaLeyendas(Listaleyendas listaleyendas) {
        this.ah = listaleyendas;
    }

    public void onActivityResult(int i2, int i3, Intent intent) {
        if (i2 == 999) {
            UtilFile.deleteDirectory(new File(UtilFile.getPathReceiptTmp()));
        } else {
            super.onActivityResult(i2, i3, intent);
        }
    }

    public void onBackPressed() {
        int currentIndexViewPage = getCurrentIndexViewPage();
        if (currentIndexViewPage == 0) {
            if (((BaseActivity) getActivity()).getActionBarType().equals(ActionBarType.MENU)) {
                switchDrawer();
                ((BaseActivity) getActivity()).setActionBarType(ActionBarType.MENU);
                View customView = ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView();
                if (customView != null) {
                    customView.setOnClickListener(new OnClickListener() {
                        public void onClick(View view) {
                            TenenciaCreditosFragment.this.switchDrawer();
                        }
                    });
                }
            } else if (((BaseActivity) getActivity()).getActionBarType().equals(ActionBarType.BACK)) {
                ((SantanderRioMainActivity) getActivity()).backLastFragment();
                if (this.pageList != null) {
                    this.pageList.setImportantForAccessibility(0);
                }
                restartMainActionBar();
            }
        } else if (isForgetShareReceipt()) {
            rememberShareReceipt();
        } else {
            this.presenterLongTermDeposit.backPagePressed(currentIndexViewPage);
        }
    }

    public void changeFragmentAnimation(Fragment fragment, String str, int i2, int i3) {
        FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();
        beginTransaction.setCustomAnimations(i2, i3, i2, i3);
        beginTransaction.replace(R.id.content, fragment, str);
        beginTransaction.addToBackStack(str);
        beginTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        beginTransaction.commit();
    }

    public void sendRequestConsultaPrestamosPermitidos(IDataManager iDataManager, boolean z) {
        ConsultaPrestamosPermitidosBodyRequestBean consultaPrestamosPermitidosBodyRequestBean = new ConsultaPrestamosPermitidosBodyRequestBean();
        consultaPrestamosPermitidosBodyRequestBean.setDatosCuenta(this.ak);
        iDataManager.consultaPrestamosPermitidos(consultaPrestamosPermitidosBodyRequestBean, z);
    }

    public void clearScreenData() {
        if (y()) {
            this.am = null;
            this.presenterLongTermDeposit.onLoad();
        }
    }
}
