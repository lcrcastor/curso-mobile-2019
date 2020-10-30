package ar.com.santander.rio.mbanking.app.module.longtermdeposit;

import android.annotation.TargetApi;
import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.CAccounts;
import ar.com.santander.rio.mbanking.app.commons.CActionVto;
import ar.com.santander.rio.mbanking.app.commons.CAmount;
import ar.com.santander.rio.mbanking.app.commons.CAmountIU;
import ar.com.santander.rio.mbanking.app.commons.CAmountWebService;
import ar.com.santander.rio.mbanking.app.commons.CConsDescripciones;
import ar.com.santander.rio.mbanking.app.commons.CFormatterAmounts;
import ar.com.santander.rio.mbanking.app.commons.CLegend;
import ar.com.santander.rio.mbanking.app.commons.CLegend.TypeLegend;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.exceptions.AccountEmptyException;
import ar.com.santander.rio.mbanking.app.module.longtermdeposit.analytics.LongTermDepositAnalytics;
import ar.com.santander.rio.mbanking.app.module.longtermdeposit.analytics.LongTermDepositAnalyticsImp;
import ar.com.santander.rio.mbanking.app.module.payments.commons.RowViewCreator;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.services.events.CnsTasasPFTEvent;
import ar.com.santander.rio.mbanking.services.events.CnsTenenciasEvent;
import ar.com.santander.rio.mbanking.services.events.ConstPlazoFijoEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.model.general.TipoCliente;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsTasasPFTResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsTenenciasResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConstPlazoFijoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsTasasPFTPlazoFijoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsTasasPFTTasaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsTenenciasBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsTenenciasDatosPFBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsTenenciasListaLeyendasPFBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConstPlazoFijoBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaDebitoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListGroupBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaLeyendas;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TipoImpuestoBean;
import ar.com.santander.rio.mbanking.utils.UtilCurrency;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import ar.com.santander.rio.mbanking.utils.UtilString;
import ar.com.santander.rio.mbanking.utils.UtilTasaNominal;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import ar.com.santander.rio.mbanking.view.tables.BaseColumn.TypeStyle;
import ar.com.santander.rio.mbanking.view.tables.RowColumnView;
import ar.com.santander.rio.mbanking.view.tables.TableView;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.ReadablePartial;

public class LongTermDepositPresenterImp implements LongTermDepositPresenter {
    public static String TYPE_ALTA = "1";
    public static String TYPE_SIMULATION = "0";
    private Cuenta a;
    private String b;
    /* access modifiers changed from: private */
    public CnsTenenciasDatosPFBean c;
    private LongTermDepositView d;
    private CAccounts e;
    private List<Cuenta> f;
    private ConstPlazoFijoResponseBean g;
    private CnsTenenciasResponseBean h;
    private CnsTasasPFTResponseBean i;
    private CActionVto j;
    private ListaLeyendas k;
    private String l = "";
    private boolean m = true;
    private boolean n = false;
    private CnsTenenciasListaLeyendasPFBean o;
    private String p = null;
    private LongTermDepositAnalytics q;
    private String r;

    public LongTermDepositPresenterImp(LongTermDepositView longTermDepositView, String str) {
        this.r = str;
        this.d = longTermDepositView;
        this.e = CAccounts.getInstance(this.d.getSessionManager());
        this.f = this.e.filterDuplicateAccounts(this.e.getListAccountsUserFromSession());
        this.j = new CActionVto(this.d.getSessionManager());
        this.q = new LongTermDepositAnalyticsImp(this.d.getActContext(), this.d.getAnalyticsManager());
    }

    public void onLoad() {
        this.d.showDialogLoading();
        if (this.r.equals(TipoCliente.BP_RTL.getTipoCliente())) {
            this.r = TipoCliente.BP.getTipoCliente();
        }
        setTitleToPages();
        if (this.d.getSessionManager().usuarioTieneCuentas()) {
            this.d.getDataManager().cnsTenencias(this.r);
        } else {
            c();
        }
    }

    public void updateMainView(String str) {
        setTitleToPages();
        this.d.showDialogLoading();
        if (str.equals("BP/RTL")) {
            str = "RTL";
            this.r = str;
        }
        if (this.d.getSessionManager().usuarioTieneCuentas()) {
            this.d.getDataManager().cnsTenencias(str);
        } else {
            c();
        }
    }

    /* access modifiers changed from: protected */
    public void setTitleToPages() {
        this.d.setTitleLayout(this.d.getPageCreate(), this.d.getActContext().getString(R.string.ID309_FIXEDTERM_LBL_CONSTITUTION));
        this.d.setTitleLayout(this.d.getPageConfirm(), this.d.getActContext().getString(R.string.ID319_FIXEDTERM_LBL_CONFIRMMESSAGE));
        this.d.setTitleLayout(this.d.getPageReceipt(), this.d.getActContext().getString(R.string.ID333_FIXEDTERM_PROOF_LBL_TEXT));
        this.d.setTitleLayout(this.d.getPageList(), this.d.getActContext().getString(R.string.ID3000_LISTADOTEN_LBL_TIT));
        this.d.setTitleLayout(this.d.getPageDetail(), this.d.getActContext().getString(R.string.ID3006_DETALLETRADARS_LBL_TIT));
        this.d.setTitleLayout(this.d.getPageRates(), this.d.getActContext().getString(R.string.ID3043_TASAS_LBL_TIT));
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
        return CConsDescripciones.getConsDescripcionMONEDADESCPALABRA(this.d.getSessionManager()).listGroupBeans;
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
            for (Cuenta cuenta : this.f) {
                if (Integer.valueOf(cuenta.getSucursalPaq()).intValue() < 250 || Integer.valueOf(cuenta.getSucursalPaq()).intValue() > 259) {
                    arrayList.add(this.e.getAbrevAccount(cuenta));
                }
            }
            if (arrayList.size() > 0) {
                this.d.showDialogSelector("selector_account", this.d.getActContext().getString(R.string.TEXT_SELECTOR_ACCOUNT), arrayList, this.a != null ? this.e.getAbrevAccount(this.a) : "");
                return;
            }
            throw new AccountEmptyException(this.d.getActContext().getString(R.string.ERR_ACCOUNT_EMPTY));
        } catch (AccountEmptyException | Exception unused) {
        }
    }

    public void onAccountSelected(String str) {
        this.a = this.e.getAccountFromString(this.f, str);
        if (this.a != null) {
            this.d.setLabelSelectorAccount(str);
        }
    }

    public void onSelectorCurrencyClicked() {
        try {
            List descriptionsCurrencies = getDescriptionsCurrencies();
            if (descriptionsCurrencies.size() > 0) {
                this.d.showDialogSelector("selector_currency", this.d.getActContext().getString(R.string.TEXT_SELECTOR_CURRENCY), (ArrayList) descriptionsCurrencies, this.d.getValueCurrency());
            }
        } catch (Exception unused) {
        }
    }

    public void onCurrencySelected(String str) {
        this.b = str;
        if (this.b != null) {
            this.d.setLabelSelectorCurrency(str);
        } else {
            this.d.restoreSelectorCurrency();
        }
    }

    public void onSelectorExpiredDateClicked() {
        this.d.showDialogDate(this.d.getActContext().getString(R.string.ID314_LONG_TERM_DEPOSIT_EXPIRATION_DATE), this.d.getValueExpiredDate());
    }

    public void onExpiredDateSelected(Date date) {
        if (DateTime.now().toLocalDate().isBefore(new DateTime((Object) date).toLocalDate())) {
            this.d.setValueExpiredDate(UtilDate.getDateFormat(date, Constants.FORMAT_DATE_APP_2));
            this.d.setTermValue(a(date));
            return;
        }
        Log.d("@dev", "Fecha anterior a la actual");
    }

    public void onExpiredActionClicked() {
        this.d.showDialogSelector("selector_expired_action", this.d.getActContext().getString(R.string.TEXT_SELECTOR_ACTION_EXPIRATION), this.j.getListStringExpiredActions(), this.d.getValueExpiredAction());
    }

    public void onExpiredActionSelected(String str) {
        this.d.setValueExpiredAction(this.j.findExpiredAction(str).getLabel());
    }

    public void onChangeCurrencySelected(String str) {
        this.d.setValueChangeCurrencyAction(str);
        a(str);
        this.q.registerEventRatesCurrencyChange(str);
    }

    public void onConstPlazoFijoResponseOk(ConstPlazoFijoEvent constPlazoFijoEvent) {
        try {
            this.g = constPlazoFijoEvent.getResponse();
            if (TYPE_ALTA.equals(this.g.constPlazoFijoBodyResponseBean.tipoInvocacion)) {
                a();
            } else {
                b();
            }
        } catch (Exception e2) {
            e2.fillInStackTrace();
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onConstPlazoFijoResponseOk: ", e2);
        }
    }

    public void onCnsTenenciasResponse(CnsTenenciasEvent cnsTenenciasEvent) {
        if (cnsTenenciasEvent != null) {
            try {
                if (cnsTenenciasEvent.getResult().equals(TypeResult.OK)) {
                    this.h = (CnsTenenciasResponseBean) cnsTenenciasEvent.getBeanResponse();
                    this.h.cnsTenenciasBodyResponseBean.res = "0";
                    c();
                    return;
                }
            } catch (Exception e2) {
                e2.fillInStackTrace();
                Log.e(WSErrorHandlerConstants.LOG_TAG, "onCnsTenenciasResponse: ", e2);
                return;
            }
        }
        if (cnsTenenciasEvent == null) {
            return;
        }
        if (cnsTenenciasEvent.getResult().equals(TypeResult.BEAN_ERROR_RES_4) || cnsTenenciasEvent.getResult().equals(TypeResult.SERVER_ERROR)) {
            this.h = (CnsTenenciasResponseBean) cnsTenenciasEvent.getBeanResponse();
            this.h = new CnsTenenciasResponseBean();
            this.h.cnsTenenciasBodyResponseBean = new CnsTenenciasBodyResponseBean(null, null);
            this.h.cnsTenenciasBodyResponseBean.resTitle = cnsTenenciasEvent.getTitleToShow();
            if (cnsTenenciasEvent.getResult().equals(TypeResult.BEAN_ERROR_RES_4)) {
                this.h.cnsTenenciasBodyResponseBean.resDesc = cnsTenenciasEvent.getMessageToShow();
                this.q.registerScreenErrorParcial();
            } else if (cnsTenenciasEvent.getResult().equals(TypeResult.SERVER_ERROR)) {
                this.q.registerScreeErrorTotal();
                this.h.cnsTenenciasBodyResponseBean.resDesc = this.d.getActContext().getString(R.string.MSG_USER000002_General_errorNoconexion);
            }
            c();
        }
    }

    public void onCnsTasasPFTEventResponseOk(CnsTasasPFTEvent cnsTasasPFTEvent) {
        try {
            this.i = (CnsTasasPFTResponseBean) cnsTasasPFTEvent.getBeanResponse();
            k();
            a(this.d.getActContext().getString(R.string.IDXXX_F10_XX_TRADICIONAL_PESOS));
        } catch (Exception e2) {
            e2.fillInStackTrace();
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onCnsTasasPFTEventResponseOk: ", e2);
        }
    }

    private void a() {
        j();
        g();
    }

    private void b() {
        this.k = this.g.constPlazoFijoBodyResponseBean.listaLeyendas;
        if (this.d.getCurrentIndexViewPage() == this.d.getIndexViewPage(this.d.getPageCreate())) {
            i();
            f();
            return;
        }
        this.d.onShowError(this.d.getActContext().getString(R.string.IDX_ALERT_LBL_TITLE_ERROR), this.d.getActContext().getString(R.string.MSG_ERROR_UNKNOW));
        m();
        e();
    }

    private void c() {
        this.d.removeBlockBodyPageList();
        this.d.setTasas();
        try {
            if (this.h.cnsTenenciasBodyResponseBean.res == null) {
                setTitleToPages();
                this.q.registerScreenLongTermDepositListError();
                this.d.addBlockBodyPageListErrorDevolucionPlazosFijos(this.h.cnsTenenciasBodyResponseBean.resDesc);
            } else if (this.h.cnsTenenciasBodyResponseBean.listaPlazoFijo != null) {
                this.q.registerScreenLongTermDepositList();
                this.d.addBlockBodyPageListPlazosFijosExistentes(p());
                this.o = this.h.cnsTenenciasBodyResponseBean.leyendasTenencia;
            } else if (this.h.cnsTenenciasBodyResponseBean.listaPlazoFijo == null || this.h.cnsTenenciasBodyResponseBean.listaPlazoFijo.getLstCnsTenencias().size() == 0) {
                this.q.registerScreenLongTermDepositEmptyList();
                this.d.addBlockBodyPageListSinPlazosFijos();
            }
        } catch (Exception unused) {
            this.d.addBlockBodyPageListErrorDevolucionPlazosFijos(this.h.cnsTenenciasBodyResponseBean.resDesc);
        }
    }

    /* access modifiers changed from: private */
    public void d() {
        this.d.removeBlockBodyPageDetail();
        if (this.o != null) {
            this.d.addBlockBodyPageDetail(this.c, this.o.getLeyendas(this.c.subproducto));
        } else {
            this.d.addBlockBodyPageDetail(this.c, "");
        }
    }

    private void a(String str) {
        this.d.removeBlockBodyPageRates();
        this.d.addBlockBodyPageRates(b(str));
    }

    private void e() {
        this.d.removeBlockBodyPageCreate();
        h();
    }

    private void f() {
        this.d.removeBlockBodyPageConfirm();
        this.d.addBlockBodyPageConfirm(q());
        x();
    }

    private void g() {
        this.d.loadBarShare();
        this.d.removeBlockBodyPageReceipt();
        this.d.addBlockBodyPageReceipt(r());
        y();
        this.q.registerTransactionHits(this.g.constPlazoFijoBodyResponseBean.nroComprobante);
    }

    public void onGoToCreateNewClicked() {
        if (n()) {
            m();
            e();
            setTitleToPages();
            return;
        }
        this.d.onShowDialog("dialog_error", this.d.getActContext().getString(R.string.IDX_ALERT_LBL_TITLE_ERROR), this.d.getActContext().getString(R.string.MSG_MSG_USER0000101));
    }

    public void onGoToRatesClicked(String str) {
        this.d.showDialogLoading();
        this.d.getDataManager().cnsTasasPFT();
        this.p = str;
    }

    public void onChangeCurrencyClicked() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.d.getActContext().getString(R.string.IDXXX_F10_XX_TRADICIONAL_PESOS));
        arrayList.add(this.d.getActContext().getString(R.string.IDXXX_F10_XX_TRADICIONAL_DOLARES));
        this.d.showDialogSelector("selector_currency_rates", this.d.getActContext().getString(R.string.TEXT_SELECTOR_ACTION_CHANGE_CURRENCY), arrayList, this.d.getValueChangeCurrencyAction());
    }

    private void h() {
        try {
            if (this.f == null || this.f.size() <= 0) {
                this.d.errorEmptyAccount();
                return;
            }
            this.a = this.e.getDefaultAccount(this.f, "02");
            this.d.addBlockBodyPageCreate(a(this.a));
            this.d.setListenerClickGoToRatesAction();
            this.d.setListenerClickCambiarTipoCuentas();
        } catch (AccountEmptyException e2) {
            Log.e("@dev", e2.toString());
        }
    }

    public void setListeners() {
        this.d.setListenerClickRowAccount();
        this.d.setEventChangedValueTerm();
        this.d.setEventLostFocusTerm();
        this.d.setListenerClickRowCurrency();
        this.d.setListenerClickExpiredDate();
        this.d.setListenerClickExpiredAction();
        this.d.setListenerClickGoToCreateNewAction();
        this.d.setListenerClickGoToRatesAction();
        this.d.setListenerClickCambiarTipoCuentas();
        this.d.setListenerClickChangeCurrency();
    }

    public String getNumComprobante() {
        return this.l;
    }

    private void i() {
        this.d.loadBarReturn();
        this.d.onTopPageConfirm();
        this.d.setNextPageAnimation();
        this.d.gotoPage(this.d.getIndexViewPage(this.d.getPageConfirm()));
        this.q.registerScreenConfirm();
    }

    private void j() {
        this.d.onTopPageReceipt();
        this.d.setNextPageAnimation();
        this.d.gotoPage(this.d.getIndexViewPage(this.d.getPageReceipt()));
        this.q.registerScreenReceipt();
    }

    private void k() {
        this.d.loadBarClose();
        this.d.setModalInPageAnimation();
        this.d.gotoPage(this.d.getIndexViewPage(this.d.getPageRates()));
        this.q.registerScreenRates();
    }

    /* access modifiers changed from: private */
    public void l() {
        this.d.loadBarReturn();
        this.d.setNextPageAnimation();
        this.d.gotoPage(this.d.getIndexViewPage(this.d.getPageDetail()));
        this.q.registerScreenLongTermDepositDetail(this.c.tituloResumen);
    }

    private void m() {
        this.d.loadBarReturn();
        this.d.onTopPageCreate();
        this.d.setNextPageAnimation();
        this.d.gotoPage(this.d.getIndexViewPage(this.d.getPageCreate()));
        this.q.registerScreenConstitution();
    }

    private void a(boolean z) {
        this.d.loadBarReturn();
        this.d.onTopPageCreate();
        if (z == this.m) {
            this.d.setPreviusPageAnimation();
        } else {
            this.d.setModalOutPageAnimation();
        }
        this.d.gotoPage(this.d.getIndexViewPage(this.d.getPageCreate()));
        this.q.registerScreenConstitution();
    }

    private void b(boolean z) {
        this.d.restartMainActionBar();
        if (z == this.m) {
            this.d.setPreviusPageAnimation();
        } else {
            this.d.setModalOutPageAnimation();
        }
        this.d.gotoPage(this.d.getIndexViewPage(this.d.getPageList()));
    }

    private boolean n() {
        for (Cuenta cuenta : this.f) {
            if (Integer.parseInt(cuenta.getSucursalPaq()) >= 250) {
                if (Integer.parseInt(cuenta.getSucursalPaq()) > 259) {
                }
            }
            return true;
        }
        return false;
    }

    public void onBtnContinueInCreateClicked() {
        if (o()) {
            this.d.showDialogLoading();
            a(Integer.parseInt(TYPE_SIMULATION));
        }
    }

    private boolean o() {
        if (s() == null) {
            this.d.onShowDialog("dialog_error", this.d.getActContext().getString(R.string.IDX_ALERT_LBL_TITLE_ERROR), this.d.getActContext().getString(R.string.MSG_MSG_USER0000100));
            return false;
        } else if (t() == null) {
            this.d.onShowDialog("dialog_error", this.d.getActContext().getString(R.string.IDX_ALERT_LBL_TITLE_ERROR), this.d.getActContext().getString(R.string.MSG_MSG_USER0000100));
            return false;
        } else if ("000000000000.00".equals(t())) {
            this.d.onShowDialog("dialog_error", this.d.getActContext().getString(R.string.IDX_ALERT_LBL_TITLE_ERROR), this.d.getActContext().getString(R.string.MSG_USER000056_Plazos_errorMontoVacio));
            return false;
        } else if (u() == null) {
            this.d.onShowDialog("dialog_error", this.d.getActContext().getString(R.string.IDX_ALERT_LBL_TITLE_ERROR), this.d.getActContext().getString(R.string.MSG_MSG_USER0000100));
            return false;
        } else if (v() == null) {
            this.d.onShowDialog("dialog_error", this.d.getActContext().getString(R.string.IDX_ALERT_LBL_TITLE_ERROR), this.d.getActContext().getString(R.string.MSG_MSG_USER0000100));
            return false;
        } else if (this.a != null) {
            return true;
        } else {
            this.d.onShowDialog("dialog_error", this.d.getActContext().getString(R.string.IDX_ALERT_LBL_TITLE_ERROR), this.d.getActContext().getString(R.string.MSG_MSG_USER0000100));
            return false;
        }
    }

    private void a(int i2) {
        this.d.getDataManager().getConstPlazoFijo(b(i2));
    }

    private ConstPlazoFijoBodyRequestBean b(int i2) {
        ConstPlazoFijoBodyRequestBean constPlazoFijoBodyRequestBean = new ConstPlazoFijoBodyRequestBean(Integer.toString(i2), w(), s(), t(), u(), v());
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

    private View p() {
        TableLayout tableLayout = new TableLayout(this.d.getActContext());
        LayoutInflater layoutInflater = (LayoutInflater) this.d.getActContext().getSystemService("layout_inflater");
        int i2 = 0;
        for (final CnsTenenciasDatosPFBean cnsTenenciasDatosPFBean : this.h.cnsTenenciasBodyResponseBean.listaPlazoFijo.getLstCnsTenencias()) {
            RelativeLayout relativeLayout = (RelativeLayout) layoutInflater.inflate(R.layout.list_item_plazo_fijo, null);
            TextView textView = (TextView) relativeLayout.findViewById(R.id.F10_00_lbl_adapter_tipo);
            TextView textView2 = (TextView) relativeLayout.findViewById(R.id.F10_00_lbl_adapter_fecha);
            TextView textView3 = (TextView) relativeLayout.findViewById(R.id.F10_00_lbl_adapter_tasa);
            TextView textView4 = (TextView) relativeLayout.findViewById(R.id.F10_00_lbl_adapter_tasa_data);
            TextView textView5 = (TextView) relativeLayout.findViewById(R.id.F10_00_lbl_adapter_capital_data);
            TextView textView6 = (TextView) relativeLayout.findViewById(R.id.F10_00_lbl_adapter_total_data);
            TextView textView7 = (TextView) relativeLayout.findViewById(R.id.tipo_cuenta_lbl_row);
            textView.setText(Html.fromHtml(cnsTenenciasDatosPFBean.tituloResumen));
            LayoutInflater layoutInflater2 = layoutInflater;
            textView2.setText(UtilDate.getDateFormat(cnsTenenciasDatosPFBean.fechaProxVencimiento, Constants.FORMAT_DATE_DASH, Constants.FORMAT_DATE_APP_2));
            textView4.setText(cnsTenenciasDatosPFBean.tasaTNA);
            if (cnsTenenciasDatosPFBean.nroCuenta == null) {
                textView7.setVisibility(8);
            } else {
                textView7.setText(cnsTenenciasDatosPFBean.nroCuenta);
            }
            CAmount cAmount = new CAmount(cnsTenenciasDatosPFBean.capital);
            cAmount.setSymbolCurrencyDollarOrPeso(cnsTenenciasDatosPFBean.capital.contains(Constants.SYMBOL_CURRENCY_DOLAR));
            textView5.setText(cAmount.getAmountPossitive());
            CAmount cAmount2 = new CAmount(cnsTenenciasDatosPFBean.totalACobrar);
            cAmount2.setSymbolCurrencyDollarOrPeso(cnsTenenciasDatosPFBean.capital.contains(Constants.SYMBOL_CURRENCY_DOLAR));
            textView6.setText(cAmount2.getAmountPossitive());
            try {
                textView.setContentDescription(CAccessibility.getInstance(this.d.getActContext()).applyFilterGeneral(textView.getText().toString()));
                textView2.setContentDescription(CAccessibility.getInstance(this.d.getActContext()).applyFilterDate(textView2.getText().toString()));
                textView3.setContentDescription(CAccessibility.getInstance(this.d.getActContext()).applyFilterTasaInteres(textView3.getText().toString()));
                textView4.setContentDescription(CAccessibility.getInstance(this.d.getActContext()).applyFilterTasaValue(textView4.getText().toString()));
                textView5.setContentDescription(CAccessibility.getInstance(this.d.getActContext()).applyFilterAmount(textView5.getText().toString()));
                textView6.setContentDescription(CAccessibility.getInstance(this.d.getActContext()).applyFilterAmount(textView6.getText().toString()));
                textView7.setContentDescription(CAccessibility.getInstance(this.d.getActContext()).applyFilterAccount(textView7.getText().toString()));
            } catch (Exception unused) {
            }
            relativeLayout.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    LongTermDepositPresenterImp.this.c = cnsTenenciasDatosPFBean;
                    LongTermDepositPresenterImp.this.l();
                    LongTermDepositPresenterImp.this.d();
                }
            });
            if (i2 % 2 == 0) {
                relativeLayout.setBackgroundColor(this.d.getActContext().getResources().getColor(R.color.grey_list_background));
            } else {
                relativeLayout.setBackgroundColor(this.d.getActContext().getResources().getColor(R.color.white));
            }
            tableLayout.addView(relativeLayout, i2);
            i2++;
            layoutInflater = layoutInflater2;
        }
        return tableLayout;
    }

    private View b(String str) {
        TableLayout tableLayout = new TableLayout(this.d.getActContext());
        TextView lblMontoMinimoRates = this.d.getLblMontoMinimoRates();
        for (CnsTasasPFTPlazoFijoBean cnsTasasPFTPlazoFijoBean : this.i.cnsTasasPFTBodyResponseBean.listaPlazos.getLstPlazoFijo()) {
            if ((str.equalsIgnoreCase(this.d.getActContext().getString(R.string.IDXXX_F10_XX_TRADICIONAL_PESOS)) && "ARS".equalsIgnoreCase(cnsTasasPFTPlazoFijoBean.monedaTasa)) || (str.equalsIgnoreCase(this.d.getActContext().getString(R.string.IDXXX_F10_XX_TRADICIONAL_DOLARES)) && "USD".equalsIgnoreCase(cnsTasasPFTPlazoFijoBean.monedaTasa))) {
                LayoutInflater layoutInflater = (LayoutInflater) this.d.getActContext().getSystemService("layout_inflater");
                StringBuilder sb = new StringBuilder();
                sb.append(this.d.getActContext().getString(R.string.ID3044_TASAS_LBL_MONTOMIN));
                sb.append(UtilsCuentas.SEPARAOR2);
                sb.append(UtilCurrency.getSimbolCurrencyFromString(cnsTasasPFTPlazoFijoBean.monedaTasa));
                sb.append(UtilsCuentas.SEPARAOR2);
                sb.append(cnsTasasPFTPlazoFijoBean.importeMinimo);
                lblMontoMinimoRates.setText(sb.toString());
                String str2 = "";
                try {
                    CAccessibility instance = CAccessibility.getInstance(this.d.getActContext());
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(UtilCurrency.getSimbolCurrencyFromString(cnsTasasPFTPlazoFijoBean.monedaTasa));
                    sb2.append(UtilsCuentas.SEPARAOR2);
                    sb2.append(cnsTasasPFTPlazoFijoBean.importeMinimo);
                    str2 = instance.applyFilterAmount(sb2.toString());
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                StringBuilder sb3 = new StringBuilder();
                sb3.append(this.d.getActContext().getString(R.string.ID3044_TASAS_LBL_MONTOMIN));
                sb3.append(UtilsCuentas.SEPARAOR2);
                sb3.append(str2);
                lblMontoMinimoRates.setContentDescription(sb3.toString());
                if ("ARS".equalsIgnoreCase(cnsTasasPFTPlazoFijoBean.monedaTasa)) {
                    this.d.setValueChangeCurrencyAction(this.d.getActContext().getString(R.string.IDXXX_F10_XX_TRADICIONAL_PESOS));
                } else if ("USD".equalsIgnoreCase(cnsTasasPFTPlazoFijoBean.monedaTasa)) {
                    this.d.setValueChangeCurrencyAction(this.d.getActContext().getString(R.string.IDXXX_F10_XX_TRADICIONAL_DOLARES));
                }
                int i2 = 0;
                for (CnsTasasPFTTasaBean cnsTasasPFTTasaBean : cnsTasasPFTPlazoFijoBean.listaTasas.getListaTasas()) {
                    LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.list_item_tasas_plazo_fijo, null);
                    TextView textView = (TextView) linearLayout.findViewById(R.id.lbl_column2);
                    ((TextView) linearLayout.findViewById(R.id.lbl_column1)).setText(String.valueOf(Integer.parseInt(cnsTasasPFTTasaBean.plazoTasa)));
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(cnsTasasPFTTasaBean.tasaOnline);
                    sb4.append(Constants.SYMBOL_PERCENTAGE);
                    textView.setText(sb4.toString());
                    try {
                        textView.setContentDescription(CAccessibility.getInstance(this.d.getActContext()).applyFilterTasaValue(textView.getText().toString()));
                    } catch (Exception unused) {
                    }
                    tableLayout.addView(linearLayout, i2);
                    i2++;
                }
            }
        }
        return tableLayout;
    }

    @TargetApi(17)
    private View a(Cuenta cuenta) {
        TableView tableView = new TableView(this.d.getActContext());
        tableView.addRowView(RowViewCreator.getRowAccountSelector(this.d.getActContext(), this.e.getAbrevAccount(cuenta), TypeStyle.TYPE_STYLE_LABEL, Boolean.valueOf(true)));
        tableView.addRowView(RowViewCreatorLongTermDeposit.getRowCurrency(this.d.getActContext(), getDefaultCurrency()));
        tableView.addRowView(RowViewCreatorLongTermDeposit.getRowAmount(this.d.getActContext(), c("0")));
        tableView.addRowView(RowViewCreatorLongTermDeposit.getRowTerm(this.d.getActContext(), "0"));
        tableView.addRowView(RowViewCreatorLongTermDeposit.getRowExpirationDate(this.d.getActContext(), getExpirationDate("0")));
        tableView.addRowView(RowViewCreatorLongTermDeposit.getRowType(this.d.getActContext(), this.d.getActContext().getString(R.string.ID315_FIXEDTERM_LBL_TYPE), this.d.getActContext().getString(R.string.ID335_FIXEDTERM_PROOF_LBL_TRADITIONAL), TypeStyle.TYPE_STYLE_LABEL));
        tableView.addRowView(RowViewCreatorLongTermDeposit.getRowExpiredAction(this.d.getActContext(), this.j.getDefaultExpiredActions()));
        return tableView;
    }

    private TableView q() {
        TableView tableView = new TableView(this.d.getActContext());
        tableView.addRowView(RowViewCreatorLongTermDeposit.getRowType(this.d.getActContext(), this.d.getActContext().getString(R.string.ID320_FIXEDTERM_CONFIRM_LBL_TYPE), this.d.getActContext().getString(R.string.ID335_FIXEDTERM_PROOF_LBL_TRADITIONAL), TypeStyle.TYPE_STYLE_DATA_VALUE));
        Context actContext = this.d.getActContext();
        StringBuilder sb = new StringBuilder();
        sb.append(UtilCurrency.getSimbolCurrencyFromString(this.g.constPlazoFijoBodyResponseBean.moneda));
        sb.append(c(this.g.constPlazoFijoBodyResponseBean.capital));
        tableView.addRowView(RowViewCreatorLongTermDeposit.getRowCapital(actContext, sb.toString(), TypeStyle.TYPE_STYLE_DATA_VALUE));
        tableView.addRowView(RowViewCreatorLongTermDeposit.getRowCurrencyValue(this.d.getActContext(), UtilCurrency.getCurrencyFromSymbol(this.d.getActContext(), this.g.constPlazoFijoBodyResponseBean.moneda), TypeStyle.TYPE_STYLE_DATA_VALUE));
        tableView.addRowView(RowViewCreatorLongTermDeposit.getRowTermDaysValue(this.d.getActContext(), this.d.getActContext().getString(R.string.TEXT_LONG_TERM_DEPOSIT_DAYS, new Object[]{UtilString.getNumberFromString(this.g.constPlazoFijoBodyResponseBean.plazoDias)}), TypeStyle.TYPE_STYLE_DATA_VALUE));
        tableView.addRowView(RowViewCreatorLongTermDeposit.getRowTNA(this.d.getActContext(), UtilTasaNominal.getTasaFormatted(this.g.constPlazoFijoBodyResponseBean.tasaNominalAnual), TypeStyle.TYPE_STYLE_DATA_VALUE));
        tableView.addRowView(RowViewCreatorLongTermDeposit.getRowTEA(this.d.getActContext(), UtilTasaNominal.getTasaFormatted(this.g.constPlazoFijoBodyResponseBean.tasaEfectivaAnual), TypeStyle.TYPE_STYLE_DATA_VALUE));
        Context actContext2 = this.d.getActContext();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(UtilCurrency.getSimbolCurrencyFromString(this.g.constPlazoFijoBodyResponseBean.moneda));
        sb2.append(c(this.g.constPlazoFijoBodyResponseBean.interes));
        tableView.addRowView(RowViewCreatorLongTermDeposit.getRowIntereses(actContext2, sb2.toString(), TypeStyle.TYPE_STYLE_DATA_VALUE));
        List<TipoImpuestoBean> list = this.g.constPlazoFijoBodyResponseBean.tipoImpuestoBean;
        if (list != null && list.size() > 0) {
            for (int i2 = 0; i2 < list.size(); i2++) {
                Context actContext3 = this.d.getActContext();
                String str = ((TipoImpuestoBean) list.get(i2)).descImpuesto;
                StringBuilder sb3 = new StringBuilder();
                sb3.append(UtilCurrency.getSimbolCurrencyFromString(this.g.constPlazoFijoBodyResponseBean.moneda));
                sb3.append(c(((TipoImpuestoBean) list.get(i2)).impLocal));
                tableView.addRowView(RowViewCreatorLongTermDeposit.getRowImpuesto(actContext3, str, sb3.toString(), TypeStyle.TYPE_STYLE_DATA_VALUE));
            }
        }
        tableView.addRowView(RowViewCreatorLongTermDeposit.getRowAccountValue(this.d.getActContext(), this.e.getAbrevAccount(this.a), TypeStyle.TYPE_STYLE_DATA_VALUE));
        tableView.addRowView(RowViewCreatorLongTermDeposit.getRowConstitutionDate(this.d.getActContext(), UtilDate.getDateFormat(this.g.constPlazoFijoBodyResponseBean.fechaAlta, Constants.FORMAT_DATE_WS_1, Constants.FORMAT_DATE_APP_2), TypeStyle.TYPE_STYLE_DATA_VALUE));
        tableView.addRowView(RowViewCreatorLongTermDeposit.getRowVtoDate(this.d.getActContext(), UtilDate.getDateFormat(this.g.constPlazoFijoBodyResponseBean.fechaVto, Constants.FORMAT_DATE_WS_1, Constants.FORMAT_DATE_APP_2), TypeStyle.TYPE_STYLE_DATA_VALUE));
        tableView.addRowView(RowViewCreatorLongTermDeposit.getRowActionVto(this.d.getActContext(), new CActionVto(this.d.getSessionManager()).findExpiredActionByCode(this.g.constPlazoFijoBodyResponseBean.accionAlVto).getLabel(), TypeStyle.TYPE_STYLE_DATA_VALUE));
        return tableView;
    }

    private TableView r() {
        TableView tableView = new TableView(this.d.getActContext());
        tableView.addRowView(RowViewCreatorLongTermDeposit.getRowType(this.d.getActContext(), this.d.getActContext().getString(R.string.ID320_FIXEDTERM_CONFIRM_LBL_TYPE), this.d.getActContext().getString(R.string.ID335_FIXEDTERM_PROOF_LBL_TRADITIONAL), TypeStyle.TYPE_STYLE_DATA_VALUE));
        Context actContext = this.d.getActContext();
        StringBuilder sb = new StringBuilder();
        sb.append(UtilCurrency.getSimbolCurrencyFromString(this.g.constPlazoFijoBodyResponseBean.moneda));
        sb.append(c(this.g.constPlazoFijoBodyResponseBean.capital));
        tableView.addRowView(RowViewCreatorLongTermDeposit.getRowCapital(actContext, sb.toString(), TypeStyle.TYPE_STYLE_DATA_VALUE));
        tableView.addRowView(RowViewCreatorLongTermDeposit.getRowTermDaysValue(this.d.getActContext(), this.d.getActContext().getString(R.string.TEXT_LONG_TERM_DEPOSIT_DAYS, new Object[]{UtilString.getNumberFromString(this.g.constPlazoFijoBodyResponseBean.plazoDias)}), TypeStyle.TYPE_STYLE_DATA_VALUE));
        tableView.addRowView(RowViewCreatorLongTermDeposit.getRowTNA(this.d.getActContext(), UtilTasaNominal.getTasaFormatted(this.g.constPlazoFijoBodyResponseBean.tasaNominalAnual), TypeStyle.TYPE_STYLE_DATA_VALUE));
        tableView.addRowView(RowViewCreatorLongTermDeposit.getRowTEA(this.d.getActContext(), UtilTasaNominal.getTasaFormatted(this.g.constPlazoFijoBodyResponseBean.tasaEfectivaAnual), TypeStyle.TYPE_STYLE_DATA_VALUE));
        Context actContext2 = this.d.getActContext();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(UtilCurrency.getSimbolCurrencyFromString(this.g.constPlazoFijoBodyResponseBean.moneda));
        sb2.append(c(this.g.constPlazoFijoBodyResponseBean.interes));
        tableView.addRowView(RowViewCreatorLongTermDeposit.getRowIntereses(actContext2, sb2.toString(), TypeStyle.TYPE_STYLE_DATA_VALUE));
        List<TipoImpuestoBean> list = this.g.constPlazoFijoBodyResponseBean.tipoImpuestoBean;
        if (list != null && list.size() > 0) {
            for (int i2 = 0; i2 < list.size(); i2++) {
                Context actContext3 = this.d.getActContext();
                String str = ((TipoImpuestoBean) list.get(i2)).descImpuesto;
                StringBuilder sb3 = new StringBuilder();
                sb3.append(UtilCurrency.getSimbolCurrencyFromString(this.g.constPlazoFijoBodyResponseBean.moneda));
                sb3.append(c(((TipoImpuestoBean) list.get(i2)).impLocal));
                tableView.addRowView(RowViewCreatorLongTermDeposit.getRowImpuesto(actContext3, str, sb3.toString(), TypeStyle.TYPE_STYLE_DATA_VALUE));
            }
        }
        tableView.addRowView(RowViewCreatorLongTermDeposit.getRowAccountValue(this.d.getActContext(), this.e.getAbrevAccount(this.a), TypeStyle.TYPE_STYLE_DATA_VALUE));
        tableView.addRowView(RowViewCreatorLongTermDeposit.getRowConstitutionDate(this.d.getActContext(), UtilDate.getDateFormat(this.g.constPlazoFijoBodyResponseBean.fechaAlta, Constants.FORMAT_DATE_WS_1, Constants.FORMAT_DATE_APP_2), TypeStyle.TYPE_STYLE_DATA_VALUE));
        tableView.addRowView(RowViewCreatorLongTermDeposit.getRowVtoDate(this.d.getActContext(), UtilDate.getDateFormat(this.g.constPlazoFijoBodyResponseBean.fechaVto, Constants.FORMAT_DATE_WS_1, Constants.FORMAT_DATE_APP_2), TypeStyle.TYPE_STYLE_DATA_VALUE));
        RowColumnView rowActionVto = RowViewCreatorLongTermDeposit.getRowActionVto(this.d.getActContext(), new CActionVto(this.d.getSessionManager()).findExpiredActionByCode(this.g.constPlazoFijoBodyResponseBean.accionAlVto).getLabel(), TypeStyle.TYPE_STYLE_DATA_VALUE);
        rowActionVto.setBoldBottomSeparator();
        tableView.addRowView(rowActionVto);
        tableView.addRowView(RowViewCreatorLongTermDeposit.getRowNumCert(this.d.getActContext(), this.g.constPlazoFijoBodyResponseBean.nroCertificado, TypeStyle.TYPE_STYLE_DATA_VALUE));
        RowColumnView rowNumComp = RowViewCreatorLongTermDeposit.getRowNumComp(this.d.getActContext(), this.g.constPlazoFijoBodyResponseBean.nroComprobante, TypeStyle.TYPE_STYLE_DATA_VALUE);
        this.l = this.g.constPlazoFijoBodyResponseBean.nroComprobante;
        rowNumComp.setBoldBottomSeparator();
        tableView.addRowView(rowNumComp);
        return tableView;
    }

    private String s() {
        try {
            String valueTerm = this.d.getValueTerm();
            if (valueTerm != null && Integer.parseInt(valueTerm) >= 0) {
                return valueTerm;
            }
        } catch (Exception e2) {
            Log.e("@dev", "Error al obtener el plazo", e2);
        }
        return null;
    }

    private String t() {
        try {
            Double doubleFromInputUser = CAmountIU.getInstance().getDoubleFromInputUser(this.d.getValueAmount());
            if (doubleFromInputUser != null) {
                return CAmountWebService.getAmountToWebService(doubleFromInputUser, false);
            }
        } catch (Exception e2) {
            Log.e("@dev", "Error al obtener el importe del formulario", e2);
        }
        return null;
    }

    private String u() {
        try {
            String valueCurrency = this.d.getValueCurrency();
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

    private String v() {
        try {
            String valueExpiredAction = this.d.getValueExpiredAction();
            if (valueExpiredAction != null) {
                return new CActionVto(this.d.getSessionManager()).findExpiredAction(valueExpiredAction).code;
            }
        } catch (Exception e2) {
            Log.d("@dev", "Error al obtener la acción de vencimiento", e2);
        }
        return null;
    }

    public void onBtnConstituirClicked() {
        this.d.onShowDialogConfirmPayment(this.d.getActContext().getString(R.string.ID332_FIXEDTERM_CONFIRM_LBL_ALERT), this.d.getActContext().getString(R.string.MSG_USER000045));
        this.q.registerScreenDialogConfirm();
    }

    public void onBtnCancelConfirmLongTermDepositClicked() {
        this.q.registerEventCancelDialogConfirm();
    }

    public void sendConstituirLongTerm() {
        this.d.showDialogLoading();
        a(Integer.parseInt(TYPE_ALTA));
    }

    public void onBtnReceiptClicked() {
        if (this.d.isForgetShareReceipt()) {
            this.d.rememberShareReceipt();
        } else {
            this.d.reloadFragment();
        }
    }

    public void backPagePressed(int i2) {
        int id2 = this.d.getViewPageFromIndex(i2).getId();
        if (id2 == this.d.getPageDetail().getId()) {
            b(this.m);
        } else if (id2 == this.d.getPageRates().getId()) {
            if (this.p.equalsIgnoreCase("FROM_LIST")) {
                b(this.n);
            } else if (this.p.equalsIgnoreCase("FROM_CREATE")) {
                a(this.n);
            }
        } else if (id2 == this.d.getPageCreate().getId()) {
            b(this.m);
        } else if (id2 == this.d.getPageConfirm().getId()) {
            a(this.m);
        } else if (id2 == this.d.getPageReceipt().getId()) {
            b(this.m);
            updateMainView(TipoCliente.RTL.getTipoCliente());
        }
    }

    private CuentaDebitoBean w() {
        return new CuentaDebitoBean(this.a);
    }

    private void x() {
        if (this.k != null) {
            this.d.showLegendConfirm();
            LongTermDepositView longTermDepositView = this.d;
            StringBuilder sb = new StringBuilder();
            sb.append(Html.fromHtml(CLegend.getInstance().getDescriptionLegend(this.k, TypeLegend.PLAFIJ_LEG, "")).toString());
            sb.append("<br><br>");
            sb.append(Html.fromHtml(CLegend.getInstance().getDescriptionLegend(this.k, TypeLegend.PLAFIJ_REC, "")).toString());
            sb.append("<br><br>");
            sb.append(Html.fromHtml(CLegend.getInstance().getDescriptionLegend(this.k, TypeLegend.PLAFIJ_VER, "")).toString());
            longTermDepositView.setLegendConfirm(sb.toString());
            return;
        }
        this.d.hideLegendConfirm();
    }

    private void y() {
        if (this.k != null) {
            this.d.showLegendReceipt();
            LongTermDepositView longTermDepositView = this.d;
            StringBuilder sb = new StringBuilder();
            sb.append(Html.fromHtml(CLegend.getInstance().getDescriptionLegend(this.k, TypeLegend.PLAFIJ_LEG, "")).toString());
            sb.append("<br><br>");
            sb.append(Html.fromHtml(CLegend.getInstance().getDescriptionLegend(this.k, TypeLegend.PLAFIJ_REC, "")).toString());
            sb.append("<br><br>");
            sb.append(Html.fromHtml(CLegend.getInstance().getDescriptionLegend(this.k, TypeLegend.PLAFIJ_VER, "")).toString());
            longTermDepositView.setLegendReceipt(sb.toString());
            return;
        }
        this.d.hideLegendReceipt();
    }

    public void onPauseEvent() {
        try {
            this.d.closeDialogLoading();
        } catch (Exception unused) {
            Log.e("@dev", "Error al pausar el fragment");
        }
    }

    private String c(String str) {
        CFormatterAmounts cFormatterAmounts = new CFormatterAmounts();
        try {
            return cFormatterAmounts.getAmountToUser(cFormatterAmounts.getDoubleFromString(str).doubleValue());
        } catch (ParseException unused) {
            return "";
        }
    }
}
