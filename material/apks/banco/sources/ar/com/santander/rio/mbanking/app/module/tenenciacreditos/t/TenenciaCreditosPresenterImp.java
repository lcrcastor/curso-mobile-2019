package ar.com.santander.rio.mbanking.app.module.tenenciacreditos.t;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.commons.CAccounts;
import ar.com.santander.rio.mbanking.app.commons.CActionVto;
import ar.com.santander.rio.mbanking.app.commons.CAmountIU;
import ar.com.santander.rio.mbanking.app.commons.CAmountWebService;
import ar.com.santander.rio.mbanking.app.commons.CConsDescripciones;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.exceptions.AccountEmptyException;
import ar.com.santander.rio.mbanking.app.module.tenenciacreditos.t.analytics.TenenciaCreditosAnalytics;
import ar.com.santander.rio.mbanking.app.module.tenenciacreditos.t.analytics.TenenciaCreditosAnalyticsImp;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.services.events.ConfirmarPagoEvent;
import ar.com.santander.rio.mbanking.services.events.GetDetalleCuotaTenenciaCreditoEvent;
import ar.com.santander.rio.mbanking.services.events.GetProximasCuotasCreditoEvent;
import ar.com.santander.rio.mbanking.services.events.GetTenenciaCreditosEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.CategoriaCredito;
import ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.CuentaOperativa;
import ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.DatosCredito;
import ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.Listaleyendas;
import ar.com.santander.rio.mbanking.services.soap.beans.ConfirmarPagoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetDetalleCuotaTenenciaCreditoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetProximasCuotasResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetTenenciaCreditosResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConstPlazoFijoBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaDebitoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetTenenciaCreditosBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListGroupBean;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import com.crashlytics.android.Crashlytics;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.ReadablePartial;

public class TenenciaCreditosPresenterImp implements TenenciaCreditosPresenter {
    private static String a = "1";
    private static String b = "0";
    private Cuenta c;
    private String d;
    /* access modifiers changed from: private */
    public CategoriaCredito e;
    /* access modifiers changed from: private */
    public DatosCredito f;
    /* access modifiers changed from: private */
    public TenenciaCreditosView g;
    private CAccounts h;
    private List<Cuenta> i;
    /* access modifiers changed from: private */
    public GetTenenciaCreditosResponseBean j;
    private GetDetalleCuotaTenenciaCreditoResponseBean k;
    private GetTenenciaCreditosResponseBean l;
    private ConfirmarPagoResponseBean m;
    private GetProximasCuotasResponseBean n;
    private CActionVto o;
    private Listaleyendas p;
    private String q = "";
    private boolean r = true;
    private boolean s = false;
    /* access modifiers changed from: private */
    public List<CuentaOperativa> t;
    private CuentaOperativa u = null;
    private String v = null;
    private TenenciaCreditosAnalytics w;
    private int x = 0;

    public void onChangeCurrencySelected(String str) {
    }

    public void onGoToSolicitudCreditos() {
    }

    public String safeString(String str) {
        return str == null ? "" : str;
    }

    public TenenciaCreditosPresenterImp(TenenciaCreditosView tenenciaCreditosView) {
        this.g = tenenciaCreditosView;
        if (this.g.getSessionManager().usuarioTieneCuentas()) {
            this.h = CAccounts.getInstance(this.g.getSessionManager());
            this.i = this.h.filterDuplicateAccounts(this.h.getListAccountsUserFromSession());
        }
        this.o = new CActionVto(this.g.getSessionManager());
        this.w = new TenenciaCreditosAnalyticsImp(this.g.getActContext(), this.g.getAnalyticsManager());
    }

    public void onLoad() {
        setTitleToPages();
        if (this.x == 0) {
            this.x = 1;
            this.g.showDialogLoading();
            this.g.getDataManager().getTenenciaCreditos();
        }
    }

    /* access modifiers changed from: protected */
    public void setTitleToPages() {
        this.g.setTitleLayout(this.g.getPageList(), this.g.getActContext().getString(R.string.F00_Tenencia_de_prestamos_Title));
        this.g.setTitleLayout(this.g.getPageDetail(), this.g.getActContext().getString(R.string.F01_Det_cuota__Title));
        this.g.setTitleLayout(this.g.getPagePayment(), this.g.getActContext().getString(R.string.ID309_FIXEDTERM_LBL_CONSTITUTION));
        this.g.setTitleLayout(this.g.getPageConfirm(), this.g.getActContext().getString(R.string.ID319_FIXEDTERM_LBL_CONFIRMMESSAGE));
        this.g.setTitleLayout(this.g.getPageReceipt(), this.g.getActContext().getString(R.string.ID333_FIXEDTERM_PROOF_LBL_TEXT));
    }

    /* access modifiers changed from: protected */
    public List<String> getDescriptionsCurrencies() {
        ArrayList arrayList = new ArrayList();
        for (ListGroupBean label : getCurrencies()) {
            arrayList.add(label.getLabel());
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public List<ListGroupBean> getCurrencies() {
        return CConsDescripciones.getConsDescripcionMONEDADESCPALABRA(this.g.getSessionManager()).listGroupBeans;
    }

    /* access modifiers changed from: protected */
    public String getDefaultCurrency() {
        List<ListGroupBean> currencies = getCurrencies();
        if (currencies != null && currencies.size() > 0) {
            for (ListGroupBean listGroupBean : currencies) {
                if ("ARS".equals(listGroupBean.code)) {
                    return listGroupBean.getLabel();
                }
            }
        }
        return null;
    }

    public void onSelectorAccountClicked() {
        try {
            ArrayList arrayList = new ArrayList();
            if (this.t != null) {
                for (CuentaOperativa cuentaOperativa : this.t) {
                    if (cuentaOperativa.getMoneda().equals(this.f.getMoneda())) {
                        String[] split = cuentaOperativa.getDescCtaDebito().split(UtilsCuentas.SEPARAOR2);
                        StringBuilder sb = new StringBuilder();
                        sb.append(split[split.length - 2]);
                        sb.append(UtilsCuentas.SEPARAOR2);
                        sb.append(split[split.length - 1]);
                        String sb2 = sb.toString();
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(this.h.getAbrevAccountOperativa(cuentaOperativa));
                        sb3.append(System.getProperty("line.separator"));
                        sb3.append(sb2);
                        arrayList.add(sb3.toString());
                    }
                }
                if (arrayList.size() > 0) {
                    this.g.showDialogSelector("selector_account", this.g.getActContext().getString(R.string.TEXT_SELECTOR_ACCOUNT), arrayList, this.u != null ? this.h.getAbrevAccountOperativa(this.u) : "");
                    return;
                }
                throw new AccountEmptyException(this.g.getActContext().getString(R.string.ERR_ACCOUNT_EMPTY));
            }
            throw new AccountEmptyException(this.g.getActContext().getString(R.string.ERR_ACCOUNT_EMPTY));
        } catch (AccountEmptyException unused) {
            Log.d("error", this.g.getActContext().getString(R.string.ERR_ACCOUNT_EMPTY));
        }
    }

    public void setAccount() {
        try {
            ArrayList arrayList = new ArrayList();
            if (this.t != null) {
                for (CuentaOperativa cuentaOperativa : this.t) {
                    if (cuentaOperativa.getMoneda().equals(this.f.getMoneda())) {
                        String[] split = cuentaOperativa.getDescCtaDebito().split(UtilsCuentas.SEPARAOR2);
                        StringBuilder sb = new StringBuilder();
                        sb.append(split[split.length - 2]);
                        sb.append(UtilsCuentas.SEPARAOR2);
                        sb.append(split[split.length - 1]);
                        String sb2 = sb.toString();
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(this.h.getAbrevAccountOperativa(cuentaOperativa));
                        sb3.append(System.getProperty("line.separator"));
                        sb3.append(sb2);
                        arrayList.add(sb3.toString());
                    }
                }
                if (arrayList.size() > 1) {
                    this.g.setCountDetailCuot2(this.g.getActContext().getString(R.string.F24_20_lbl_data_seleccionar), UtilsCuentas.SEPARAOR2);
                    this.g.enableAccountSelector();
                    this.u = null;
                }
                if (arrayList.size() == 1) {
                    String str = ((String) arrayList.get(0)).split(System.getProperty("line.separator"))[0];
                    this.u = this.h.getAccountOperativaFromString(this.t, str);
                    if (this.u != null) {
                        this.g.setLabelSelectorAccount(str, this.u.getDescCtaDebito());
                    }
                    onAccountSelected((String) arrayList.get(0));
                    this.g.disableAccountSelector();
                    return;
                }
                throw new AccountEmptyException(this.g.getActContext().getString(R.string.ERR_ACCOUNT_EMPTY));
            }
            throw new AccountEmptyException(this.g.getActContext().getString(R.string.ERR_ACCOUNT_EMPTY));
        } catch (AccountEmptyException unused) {
            Log.d("error", this.g.getActContext().getString(R.string.ERR_ACCOUNT_EMPTY));
        }
    }

    public void onAccountSelected(String str) {
        String str2 = str.split(System.getProperty("line.separator"))[0];
        this.u = this.h.getAccountOperativaFromString(this.t, str2);
        if (this.u != null) {
            this.g.setLabelSelectorAccount(str2, this.u.getDescCtaDebito());
        }
    }

    public void onSelectorCurrencyClicked() {
        try {
            List descriptionsCurrencies = getDescriptionsCurrencies();
            if (descriptionsCurrencies.size() > 0) {
                this.g.showDialogSelector("selector_currency", this.g.getActContext().getString(R.string.TEXT_SELECTOR_CURRENCY), (ArrayList) descriptionsCurrencies, this.g.getValueCurrency());
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void onCurrencySelected(String str) {
        this.d = str;
        if (this.d != null) {
            this.g.setLabelSelectorCurrency(str);
        } else {
            this.g.restoreSelectorCurrency();
        }
    }

    public void onSelectorExpiredDateClicked() {
        this.g.showDialogDate(this.g.getActContext().getString(R.string.ID314_LONG_TERM_DEPOSIT_EXPIRATION_DATE), this.g.getValueExpiredDate());
    }

    public void onExpiredDateSelected(Date date) {
        if (DateTime.now().toLocalDate().isBefore(new DateTime((Object) date).toLocalDate())) {
            this.g.setValueExpiredDate(UtilDate.getDateFormat(date, Constants.FORMAT_DATE_APP_2));
            this.g.setTermValue(a(date));
            return;
        }
        Log.d("@dev", "Fecha anterior a la actual");
    }

    public void onExpiredActionClicked() {
        this.g.showDialogSelector("selector_expired_action", this.g.getActContext().getString(R.string.TEXT_SELECTOR_ACTION_EXPIRATION), this.o.getListStringExpiredActions(), this.g.getValueExpiredAction());
    }

    public void onExpiredActionSelected(String str) {
        this.g.setValueExpiredAction(this.o.findExpiredAction(str).getLabel());
    }

    public void onGetDetalleCuotaResponseOk(GetDetalleCuotaTenenciaCreditoEvent getDetalleCuotaTenenciaCreditoEvent) {
        try {
            this.k = getDetalleCuotaTenenciaCreditoEvent.getResponse();
            a();
        } catch (Exception e2) {
            e2.fillInStackTrace();
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onConstPlazoFijoResponseOk: ", e2);
        }
        this.g.closeDialogLoading();
    }

    public void onConfirmarPagoCuotasOK(ConfirmarPagoEvent confirmarPagoEvent) {
        try {
            this.m = confirmarPagoEvent.getResponse();
            this.g.configReceiptUI(this.m.getmGeConfirmarPagoResponseBean());
        } catch (Exception e2) {
            e2.fillInStackTrace();
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onConstPlazoFijoResponseOk: ", e2);
        }
    }

    public void onProximasCuotasOK(GetProximasCuotasCreditoEvent getProximasCuotasCreditoEvent) {
        try {
            this.n = (GetProximasCuotasResponseBean) getProximasCuotasCreditoEvent.getBeanResponse();
            this.g.startListaProximasCuotasActivity(this.n.getGetCategoriasBodyResponseBean().getProximasCuotas());
        } catch (Exception e2) {
            e2.fillInStackTrace();
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onConstPlazoFijoResponseOk: ", e2);
        }
    }

    public void onGetDetalleCuotaTenenciaCreditoResponseOK(GetDetalleCuotaTenenciaCreditoEvent getDetalleCuotaTenenciaCreditoEvent) {
        try {
            this.k = (GetDetalleCuotaTenenciaCreditoResponseBean) getDetalleCuotaTenenciaCreditoEvent.getBeanResponse();
            this.g.configDetailUI(this.k.getDetalleCuotaTenenciaCreditoBodyResponseBean(), this.e, this.f);
        } catch (Exception e2) {
            e2.fillInStackTrace();
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onConstPlazoFijoResponseOk: ", e2);
        }
    }

    public void onTenenciasResponse(GetTenenciaCreditosEvent getTenenciaCreditosEvent, BaseActivity baseActivity) {
        final GetTenenciaCreditosEvent getTenenciaCreditosEvent2 = getTenenciaCreditosEvent;
        AnonymousClass1 r0 = new BaseWSResponseHandler(baseActivity, TypeOption.INITIAL_VIEW, FragmentConstants.CREDITOS, baseActivity) {
            /* access modifiers changed from: protected */
            public void onOk() {
                TenenciaCreditosPresenterImp.this.j = (GetTenenciaCreditosResponseBean) getTenenciaCreditosEvent2.getBeanResponse();
                TenenciaCreditosPresenterImp.this.t = TenenciaCreditosPresenterImp.this.j.getCategoriasBodyResponseBean.getCuentaoperativa();
                TenenciaCreditosPresenterImp.this.g.setListaLeyendas(TenenciaCreditosPresenterImp.this.j.getCategoriasBodyResponseBean.listaleyendas);
                TenenciaCreditosPresenterImp.this.a();
            }

            /* access modifiers changed from: protected */
            public void onRes4Error(WebServiceEvent webServiceEvent) {
                TenenciaCreditosPresenterImp.this.j = new GetTenenciaCreditosResponseBean();
                TenenciaCreditosPresenterImp.this.j.getCategoriasBodyResponseBean = new GetTenenciaCreditosBodyResponseBean();
                TenenciaCreditosPresenterImp.this.j.getCategoriasBodyResponseBean.resDesc = getTenenciaCreditosEvent2.getMessageToShow();
                TenenciaCreditosPresenterImp.this.j.getCategoriasBodyResponseBean.resTitle = getTenenciaCreditosEvent2.getTitleToShow();
                TenenciaCreditosPresenterImp.this.b();
            }

            /* access modifiers changed from: protected */
            public void onRes3Error(WebServiceEvent webServiceEvent) {
                TenenciaCreditosPresenterImp.this.g.onShowDialogConfirmPayment(getTenenciaCreditosEvent2.getTitleToShow(), getTenenciaCreditosEvent2.getMessageToShow());
            }
        };
        r0.handleWSResponse(getTenenciaCreditosEvent);
    }

    public void onConfirmarPagoResponse(ConfirmarPagoEvent confirmarPagoEvent, BaseActivity baseActivity, String str, String str2) {
        final ConfirmarPagoEvent confirmarPagoEvent2 = confirmarPagoEvent;
        final String str3 = str2;
        AnonymousClass2 r0 = new BaseWSResponseHandler(baseActivity, TypeOption.INITIAL_VIEW, str, baseActivity) {
            /* access modifiers changed from: protected */
            public void onOk() {
                TenenciaCreditosPresenterImp.this.onConfirmarPagoCuotasOK(confirmarPagoEvent2);
            }

            /* access modifiers changed from: protected */
            public void onRes1Error() {
                TenenciaCreditosPresenterImp.this.g.onShowErrorRes1(confirmarPagoEvent2);
            }

            /* access modifiers changed from: protected */
            public void onRes4Error(WebServiceEvent webServiceEvent) {
                TenenciaCreditosPresenterImp.this.g.addBlockBodyErrorConfirmarPagoRes4(confirmarPagoEvent2.getMessageToShow());
            }

            /* access modifiers changed from: protected */
            public void onRes8Error(WebServiceEvent webServiceEvent) {
                TenenciaCreditosPresenterImp.this.g.addBlockBodyErrorConfirmarPagoRes8(confirmarPagoEvent2.getMessageToShow(), str3);
            }
        };
        r0.handleWSResponse(confirmarPagoEvent);
    }

    /* access modifiers changed from: private */
    public void a() {
        this.g.removeBlockBodyPageList();
        this.g.restartMainActionBar();
        try {
            if (this.j.getCategoriasBodyResponseBean.getListacategoriascreditos() != null) {
                this.w.registerScreenLongTermDepositList();
                this.p = this.j.getCategoriasBodyResponseBean.listaleyendas;
                this.g.setListaLeyendas(this.p);
                this.g.addBlockBodyPageListPlazosFijosExistentes(d());
            } else if (this.j.getCategoriasBodyResponseBean.getListacategoriascreditos() == null || this.l.getCategoriasBodyResponseBean.getListacategoriascreditos().getCategoriacredito().size() == 0) {
                this.w.registerScreenLongTermDepositEmptyList();
                this.g.addBlockBodyPageListSinPlazosFijos();
            }
        } catch (Exception e2) {
            Crashlytics.logException(e2);
            b();
        }
    }

    /* access modifiers changed from: private */
    public void b() {
        this.g.removeBlockBodyPageList();
        this.g.addBlockBodyPageListErrorDevolucionPlazosFijos(this.j.getCategoriasBodyResponseBean.resDesc);
    }

    public void onGoToRatesClicked(String str) {
        this.g.showDialogLoading();
        this.g.getDataManager().cnsTasasPFT();
        this.v = str;
    }

    public void onChangeCurrencyClicked() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.g.getActContext().getString(R.string.IDXXX_F10_XX_TRADICIONAL_PESOS));
        arrayList.add(this.g.getActContext().getString(R.string.IDXXX_F10_XX_TRADICIONAL_DOLARES));
        this.g.showDialogSelector("selector_currency_rates", this.g.getActContext().getString(R.string.TEXT_SELECTOR_ACTION_CHANGE_CURRENCY), arrayList, this.g.getValueChangeCurrencyAction());
    }

    public void setListeners() {
        this.g.setListenerClickRowAccount();
        this.g.setEventChangedValueTerm();
        this.g.setEventLostFocusTerm();
        this.g.setListenerClickRowCurrency();
        this.g.setListenerClickExpiredDate();
        this.g.setListenerClickExpiredAction();
        this.g.setListenerClickGoToCreateNewAction();
        this.g.setListenerClickGoToRatesAction();
        this.g.setListenerClickChangeCurrency();
    }

    public String getNumComprobante() {
        return this.q;
    }

    private void a(boolean z) {
        this.g.restartMainActionBar();
        if (z == this.r) {
            this.g.setPreviusPageAnimation();
        } else {
            this.g.setModalOutPageAnimation();
        }
        this.g.gotoPage(this.g.getIndexViewPage(this.g.getPageList()));
    }

    public void onBtnContinueInCreateClicked() {
        if (c()) {
            this.g.showDialogLoading();
            a(Integer.parseInt(a));
        }
    }

    private boolean c() {
        if (e() == null) {
            this.g.onShowDialog("dialog_error", this.g.getActContext().getString(R.string.IDX_ALERT_LBL_TITLE_ERROR), this.g.getActContext().getString(R.string.MSG_MSG_USER0000100));
            return false;
        } else if (f() == null) {
            this.g.onShowDialog("dialog_error", this.g.getActContext().getString(R.string.IDX_ALERT_LBL_TITLE_ERROR), this.g.getActContext().getString(R.string.MSG_MSG_USER0000100));
            return false;
        } else if ("000000000000.00".equals(f())) {
            this.g.onShowDialog("dialog_error", this.g.getActContext().getString(R.string.IDX_ALERT_LBL_TITLE_ERROR), this.g.getActContext().getString(R.string.MSG_USER000056_Plazos_errorMontoVacio));
            return false;
        } else if (g() == null) {
            this.g.onShowDialog("dialog_error", this.g.getActContext().getString(R.string.IDX_ALERT_LBL_TITLE_ERROR), this.g.getActContext().getString(R.string.MSG_MSG_USER0000100));
            return false;
        } else if (h() == null) {
            this.g.onShowDialog("dialog_error", this.g.getActContext().getString(R.string.IDX_ALERT_LBL_TITLE_ERROR), this.g.getActContext().getString(R.string.MSG_MSG_USER0000100));
            return false;
        } else if (this.c != null) {
            return true;
        } else {
            this.g.onShowDialog("dialog_error", this.g.getActContext().getString(R.string.IDX_ALERT_LBL_TITLE_ERROR), this.g.getActContext().getString(R.string.MSG_MSG_USER0000100));
            return false;
        }
    }

    private void a(int i2) {
        this.g.getDataManager().getConstPlazoFijo(b(i2));
    }

    private ConstPlazoFijoBodyRequestBean b(int i2) {
        ConstPlazoFijoBodyRequestBean constPlazoFijoBodyRequestBean = new ConstPlazoFijoBodyRequestBean(Integer.toString(i2), i(), e(), f(), g(), h());
        return constPlazoFijoBodyRequestBean;
    }

    public String getExpirationDate(String str) {
        if (str != null) {
            try {
                return DateTime.now().plusDays(Integer.valueOf(Integer.parseInt(str)).intValue()).toString(Constants.FORMAT_DATE_APP_2);
            } catch (Exception unused) {
            }
        }
        return DateTime.now().toString(Constants.FORMAT_DATE_APP_2);
    }

    private String a(Date date) {
        try {
            return Integer.toString(Days.daysBetween((ReadablePartial) DateTime.now().toLocalDate(), (ReadablePartial) new DateTime((Object) date).toLocalDate()).getDays());
        } catch (Exception unused) {
            return "0";
        }
    }

    private View d() {
        TableLayout tableLayout = new TableLayout(this.g.getActContext());
        LayoutInflater layoutInflater = (LayoutInflater) this.g.getActContext().getSystemService("layout_inflater");
        for (int i2 = 0; i2 < this.j.getCategoriasBodyResponseBean.getListacategoriascreditos().getCategoriacredito().size(); i2++) {
            if (((CategoriaCredito) this.j.getCategoriasBodyResponseBean.getListacategoriascreditos().getCategoriacredito().get(i2)).desccategoriacredito != null) {
                RelativeLayout relativeLayout = (RelativeLayout) layoutInflater.inflate(R.layout.list_header_tenencia_creditos, null);
                ((TextView) relativeLayout.findViewById(R.id.textViewTittleListTenenciaCreditos)).setText(Html.fromHtml(((CategoriaCredito) this.j.getCategoriasBodyResponseBean.getListacategoriascreditos().getCategoriacredito().get(i2)).desccategoriacredito));
                tableLayout.addView(relativeLayout);
            }
            for (int i3 = 0; i3 < ((CategoriaCredito) this.j.getCategoriasBodyResponseBean.getListacategoriascreditos().getCategoriacredito().get(i2)).listacreditos.getDatoscredito().size(); i3++) {
                RelativeLayout relativeLayout2 = (RelativeLayout) layoutInflater.inflate(R.layout.list_item_tenencia_creditos, null);
                TextView textView = (TextView) relativeLayout2.findViewById(R.id.F10_00_lbl_adapter_nro_prestamo);
                TextView textView2 = (TextView) relativeLayout2.findViewById(R.id.F10_00_lbl_adapter_nro_cuota);
                TextView textView3 = (TextView) relativeLayout2.findViewById(R.id.F10_00_lbl_adapter_capital_cuota_data);
                TextView textView4 = (TextView) relativeLayout2.findViewById(R.id.F10_00_lbl_adapter_vencimiento);
                textView.setText(safeString(((DatosCredito) ((CategoriaCredito) this.j.getCategoriasBodyResponseBean.getListacategoriascreditos().getCategoriacredito().get(i2)).listacreditos.getDatoscredito().get(i3)).getDescripcredito()));
                StringBuilder sb = new StringBuilder();
                sb.append("Cuota ");
                sb.append(safeString(((DatosCredito) ((CategoriaCredito) this.j.getCategoriasBodyResponseBean.getListacategoriascreditos().getCategoriacredito().get(i2)).listacreditos.getDatoscredito().get(i3)).getNroCuotaCredito()));
                sb.append(" - ");
                textView2.setText(sb.toString());
                textView3.setText(safeString(((DatosCredito) ((CategoriaCredito) this.j.getCategoriasBodyResponseBean.getListacategoriascreditos().getCategoriacredito().get(i2)).listacreditos.getDatoscredito().get(i3)).getImpoteCuota()));
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Vencimiento ");
                sb2.append(safeString(((DatosCredito) ((CategoriaCredito) this.j.getCategoriasBodyResponseBean.getListacategoriascreditos().getCategoriacredito().get(i2)).listacreditos.getDatoscredito().get(i3)).getFechavencimiento()));
                textView4.setText(sb2.toString());
                try {
                    textView.setContentDescription(CAccessibility.getInstance(this.g.getActContext()).applyFilterGeneral("Número de préstamo, ").concat(CAccessibility.getInstance(this.g.getActContext()).applyFilterCharacterToCharacter(textView.getText().toString())));
                    textView2.setContentDescription(CAccessibility.getInstance(this.g.getActContext()).applyFilterTasaInteres("Número de ".concat(textView2.getText().toString()).concat(",")));
                    textView3.setContentDescription(CAccessibility.getInstance(this.g.getActContext()).applyFilterAmount(textView3.getText().toString()));
                    textView4.setContentDescription(CAccessibility.getInstance(this.g.getActContext()).applyFilterAmount(textView4.getText().toString()));
                } catch (Exception unused) {
                }
                relativeLayout2.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (!TenenciaCreditosPresenterImp.this.g.isDialogLoadingVisible()) {
                            int id2 = view.getId();
                            int i = id2 / 1000;
                            int i2 = id2 - (i * 1000);
                            TenenciaCreditosPresenterImp.this.e = (CategoriaCredito) TenenciaCreditosPresenterImp.this.j.getCategoriasBodyResponseBean.getListacategoriascreditos().getCategoriacredito().get(i);
                            TenenciaCreditosPresenterImp.this.f = (DatosCredito) ((CategoriaCredito) TenenciaCreditosPresenterImp.this.j.getCategoriasBodyResponseBean.getListacategoriascreditos().getCategoriacredito().get(i)).listacreditos.getDatoscredito().get(i2);
                            TenenciaCreditosPresenterImp.this.g.showDialogLoading();
                            TenenciaCreditosPresenterImp.this.onRedButtonClicked();
                            TenenciaCreditosPresenterImp.this.g.getDataManager().getDetalleCuotaTenenciaCredito(TenenciaCreditosPresenterImp.this.f.getSuccredito(), TenenciaCreditosPresenterImp.this.f.getNrocredito(), TenenciaCreditosPresenterImp.this.f.getTipocredito());
                        }
                    }
                });
                relativeLayout2.setId((i2 * 1000) + i3);
                tableLayout.addView(relativeLayout2);
            }
        }
        return tableLayout;
    }

    private String e() {
        try {
            String valueTerm = this.g.getValueTerm();
            if (valueTerm != null && Integer.parseInt(valueTerm) >= 0) {
                return valueTerm;
            }
        } catch (Exception e2) {
            Log.e("@dev", "Error al obtener el plazo", e2);
        }
        return null;
    }

    private String f() {
        try {
            Double doubleFromInputUser = CAmountIU.getInstance().getDoubleFromInputUser(this.g.getValueAmount());
            if (doubleFromInputUser != null) {
                return CAmountWebService.getAmountToWebService(doubleFromInputUser, false);
            }
        } catch (Exception e2) {
            Log.e("@dev", "Error al obtener el importe del formulario", e2);
        }
        return null;
    }

    private String g() {
        try {
            String valueCurrency = this.g.getValueCurrency();
            if (valueCurrency != null) {
                List currencies = getCurrencies();
                for (int i2 = 0; i2 < currencies.size(); i2++) {
                    if (((ListGroupBean) currencies.get(i2)).getLabel().equals(valueCurrency)) {
                        return ((ListGroupBean) currencies.get(i2)).code;
                    }
                }
            }
        } catch (Exception e2) {
            Log.e("@dev", "Error al obtener la moneda", e2);
        }
        return null;
    }

    private String h() {
        try {
            String valueExpiredAction = this.g.getValueExpiredAction();
            if (valueExpiredAction != null) {
                return new CActionVto(this.g.getSessionManager()).findExpiredAction(valueExpiredAction).code;
            }
        } catch (Exception e2) {
            Log.d("@dev", "Error al obtener la acción de vencimiento", e2);
        }
        return null;
    }

    public void onBtnConstituirClicked() {
        this.g.onShowDialogConfirmPayment(this.g.getActContext().getString(R.string.ID332_FIXEDTERM_CONFIRM_LBL_ALERT), this.g.getActContext().getString(R.string.MSG_USER000045));
        this.w.registerScreenDialogConfirm();
    }

    public void onBtnCancelConfirmLongTermDepositClicked() {
        this.w.registerEventCancelDialogConfirm();
    }

    public void onBtnReceiptClicked() {
        if (this.g.isForgetShareReceipt()) {
            this.g.rememberShareReceipt();
        } else {
            this.g.reloadFragment();
        }
    }

    public void backPagePressed(int i2) {
        int currentIndexViewPage = this.g.getCurrentIndexViewPage();
        if (currentIndexViewPage == 1) {
            a(this.r);
        } else if (currentIndexViewPage == 4) {
            a(this.r);
        } else {
            this.g.previousPage();
            switch (currentIndexViewPage - 1) {
                case 1:
                    this.g.loadBarBackMenu();
                    break;
                case 2:
                    this.g.loadBarReturn();
                    break;
                case 3:
                    this.g.loadBarReturn();
                    break;
                case 4:
                    this.g.loadBarReturn();
                    break;
                case 5:
                    this.g.loadBarShare();
                    break;
            }
        }
    }

    private CuentaDebitoBean i() {
        return new CuentaDebitoBean(this.c);
    }

    public void onPauseEvent() {
        try {
            this.g.closeDialogLoading();
        } catch (Exception unused) {
            Log.e("@dev", "Error al pausar el fragment");
        }
    }

    public void nextPage() {
        this.g.nextPage();
    }

    public void onRedButtonClicked() {
        switch (this.g.getCurrentIndexViewPage() + 1) {
            case 1:
                this.g.nextPage();
                this.g.loadBarBackMenu();
                return;
            case 2:
                this.g.nextPage();
                this.g.configConfirmUI();
                this.g.loadBarReturn();
                if (this.t.size() == 1) {
                    this.g.disableAccountSelector();
                    this.u = (CuentaOperativa) this.t.get(0);
                    return;
                }
                return;
            case 3:
                if (this.u != null) {
                    this.g.nextPage();
                    this.g.configPaymentUI(this.u);
                    this.g.loadBarReturn();
                    return;
                }
                this.g.onShowDialog("dialog_error", this.g.getActContext().getString(R.string.IDX_ALERT_LBL_TITLE_ERROR), "Por favor seleccione una cuenta");
                return;
            case 4:
                this.g.onShowDialogConfirmPayment("Confirmar", "¿Confirmás el pago de la cuota?");
                return;
            case 5:
                this.g.gotoPage(0);
                this.g.restartMainActionBar();
                this.x = 0;
                onLoad();
                return;
            default:
                return;
        }
    }
}
