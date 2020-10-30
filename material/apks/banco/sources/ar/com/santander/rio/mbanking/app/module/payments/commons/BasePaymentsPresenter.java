package ar.com.santander.rio.mbanking.app.module.payments.commons;

import android.util.Log;
import android.view.View;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.CAccounts;
import ar.com.santander.rio.mbanking.app.commons.CAmount;
import ar.com.santander.rio.mbanking.app.commons.CAmountIU;
import ar.com.santander.rio.mbanking.app.commons.CAmountWebService;
import ar.com.santander.rio.mbanking.app.commons.CConsDescripciones;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.constants.PagoTarjetasConstants;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.CnsDeudaRecargaEvent;
import ar.com.santander.rio.mbanking.services.events.RecargaCelularesEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsDeudaRecargaBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaDebitoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LinkSeguroBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListGroupBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListTableBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.RecargaCelularesBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.RecargaCelularesBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.versions.EVersionServices;
import ar.com.santander.rio.mbanking.services.soap.versions.VCndDeuda;
import ar.com.santander.rio.mbanking.utils.UtilAccount;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import ar.com.santander.rio.mbanking.view.tables.BaseColumn.TypeStyle;
import ar.com.santander.rio.mbanking.view.tables.TableView;
import com.indra.httpclient.request.VolleyRequestSingleton;
import java.util.ArrayList;
import java.util.List;

public abstract class BasePaymentsPresenter implements PaymentPresenter {
    private PaymentServicesView a;
    private CnsDeudaRecargaEvent b;
    private DatosDeudaBean c;
    private CuentaDebitoBean d;
    private TypeDebt e;
    private String f;
    protected PaymentsAnalytics mPaymentServicesAnalitycs;
    protected RecargaCelularesBodyResponseBean mRecargaCelularesBodyResponseBean;

    public abstract View buildViewConfirmPayment();

    public abstract View buildViewPreparePayment(DatosDeudaBean datosDeudaBean, CuentaDebitoBean cuentaDebitoBean);

    public abstract View buildViewReceipt(DatosDeudaBean datosDeudaBean, CuentaDebitoBean cuentaDebitoBean, RecargaCelularesBodyResponseBean recargaCelularesBodyResponseBean);

    public abstract String getTypeMonto();

    /* access modifiers changed from: protected */
    public boolean isValidData() {
        return true;
    }

    public abstract void showAndHideLegendReceipt(RecargaCelularesBodyResponseBean recargaCelularesBodyResponseBean);

    public BasePaymentsPresenter(TypeDebt typeDebt, PaymentServicesView paymentServicesView) {
        this.a = paymentServicesView;
        this.e = typeDebt;
    }

    public void onUpdateView() {
        this.mPaymentServicesAnalitycs.registerScreenHome();
    }

    public void onLoadData() {
        a();
        b();
        sendRequestCnsDeuda(this.a.getDataManager());
    }

    private void a() {
        if (this.a.getPaymentServicesAdapter().getAdapter() != null) {
            this.a.getPaymentServicesAdapter().getAdapter().cleanItems();
        }
    }

    private void b() {
        if (this.c != null) {
            this.c = null;
        }
        this.d = null;
    }

    public PaymentServicesView getmPaymentServicesView() {
        return this.a;
    }

    public void setmCnsDeudaEvent(CnsDeudaRecargaEvent cnsDeudaRecargaEvent) {
        this.b = cnsDeudaRecargaEvent;
    }

    public DatosDeudaBean getmDatosDeudaBeanSelected() {
        return this.c;
    }

    public void setmDatosDeudaBeanSelected(DatosDeudaBean datosDeudaBean) {
        try {
            DatosDeudaBean datosDeudaBean2 = new DatosDeudaBean(datosDeudaBean.empServ, datosDeudaBean.empDescr, datosDeudaBean.identificacion, datosDeudaBean.tipoImporte, a(datosDeudaBean.importe), datosDeudaBean.moneda, datosDeudaBean.factura, datosDeudaBean.vencimiento);
            this.c = datosDeudaBean2;
        } catch (Exception unused) {
            this.a.onDispatchEventsError(null);
        }
    }

    private String a(String str) {
        return CAmountIU.getInstance().getOutputUserFromDouble(CAmountWebService.getAmountFromWebService(str));
    }

    public CuentaDebitoBean getmAccountSelected() {
        return this.d;
    }

    public void setmAccountSelected(CuentaDebitoBean cuentaDebitoBean) {
        this.d = cuentaDebitoBean;
    }

    /* access modifiers changed from: protected */
    public void setTitleFirstPage(int i) {
        this.a.setTitlePage(this.a.getvPageTablePaymentServices(), this.a.getActContext().getString(i));
    }

    /* access modifiers changed from: protected */
    public void setTitleSecondPage(int i) {
        this.a.setTitlePage(this.a.getvPagePreparePaymentServices(), this.a.getActContext().getString(i));
    }

    /* access modifiers changed from: protected */
    public void setTitleThirdPage(int i) {
        this.a.setTitlePage(this.a.getvPageConfirmPaymentServices(), this.a.getActContext().getString(i));
    }

    /* access modifiers changed from: protected */
    public void setTitleFourthPage(int i) {
        this.a.setTitlePage(this.a.getvPageReceiptPaymentServices(), this.a.getActContext().getString(i));
    }

    /* access modifiers changed from: protected */
    public void gotoPagePreparePayment() {
        this.a.onTopPagePrepare();
        this.a.setNextPageAnimation();
        this.a.gotoPage(this.a.getIndexViewPage(this.a.getvPagePreparePaymentServices()));
    }

    /* access modifiers changed from: protected */
    public void gotoPageConfirmPayment() {
        this.a.onTopPageConfirm();
        this.a.setNextPageAnimation();
        this.a.gotoPage(this.a.getIndexViewPage(this.a.getvPageConfirmPaymentServices()));
        this.mPaymentServicesAnalitycs.registerScreenConfirm();
    }

    /* access modifiers changed from: protected */
    public void gotoPageReceiptPayment() {
        this.a.onTopPageReceipt();
        this.a.setNextPageAnimation();
        this.a.gotoPage(this.a.getIndexViewPage(this.a.getvPageReceiptPaymentServices()));
        this.mPaymentServicesAnalitycs.registerReceipt();
    }

    /* access modifiers changed from: protected */
    public void backToPagePreparePayment() {
        this.a.onTopPagePrepare();
        this.a.setPreviusPageAnimation();
        this.a.gotoPage(this.a.getIndexViewPage(this.a.getvPagePreparePaymentServices()));
    }

    /* access modifiers changed from: protected */
    public void backToPageTablePayments() {
        a();
        b();
        updateTablePayments(getListDatosDeudasFromEvent());
        this.a.restartMainActionBar();
        this.a.setPreviusPageAnimation();
        this.a.gotoPage(this.a.getIndexViewPage(this.a.getvPageTablePaymentServices()));
    }

    /* access modifiers changed from: protected */
    public void updateTablePayments(List<DatosDeudaBean> list) {
        addItemsPaymentServices(list);
    }

    /* access modifiers changed from: protected */
    public List<DatosDeudaBean> getListDatosDeudasFromEvent() {
        try {
            List<DatosDeudaBean> lstDatosDeudaBean = this.b.getResponse().cnsDeudaRecargaBodyResponseBean.datosDeudas.getLstDatosDeudaBean();
            ArrayList arrayList = new ArrayList(lstDatosDeudaBean.size());
            for (DatosDeudaBean add : lstDatosDeudaBean) {
                arrayList.add(add);
            }
            return arrayList;
        } catch (Exception unused) {
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public void addItemsPaymentServices(List<DatosDeudaBean> list) {
        this.a.getPaymentServicesAdapter().getAdapter().addItems(list);
    }

    /* access modifiers changed from: protected */
    public boolean isValidForm() {
        if (getValueAmountFromForm() == null) {
            this.a.onShowDialogError(this.a.getActContext().getString(R.string.IDX_ALERT_LBL_TITLE_ERROR), this.a.getActContext().getString(R.string.MSG_MSG_USER0000100));
            return false;
        } else if (this.d != null) {
            return true;
        } else {
            this.a.onShowDialogError(this.a.getActContext().getString(R.string.IDX_ALERT_LBL_TITLE_ERROR), this.a.getActContext().getString(R.string.MSG_MSG_USER0000100));
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public void setAmountPayment(String str) {
        this.c.importe = str;
    }

    /* access modifiers changed from: protected */
    public String getValueAmountFromForm() {
        try {
            if (this.a.isVisibleAmountEditable() && CAmount.getInstance(this.a.getSelectorAmountValue()).getDoubleFromAmountFormattedES().doubleValue() > 0.0d) {
                return this.a.getSelectorAmountValue();
            }
        } catch (Exception e2) {
            Log.e("@dev", PagoTarjetasConstants.ISBAN_DIALOG_ERROR_TITLE, e2);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void updateAccountSelected(String str) {
        this.d = getAccountFromString(str);
        if (this.d != null) {
            this.a.setLabelSelectorAccount(str);
        } else {
            this.a.restoreSelectorAccount(getAbrevAccount(getDefaultAccountDeuda()));
        }
    }

    private void b(String str) {
        this.c.importe = str;
        if (this.c.importe != null) {
            this.a.setLabelSelectorAmount(str);
        } else {
            this.a.restoreSelectorAmount(getDefaultAmountPhoneTopUp());
        }
    }

    /* access modifiers changed from: protected */
    public CuentaDebitoBean getAccountFromString(String str) {
        if (str != null) {
            try {
                for (CuentaDebitoBean cuentaDebitoBean : getListCuentaDeudas()) {
                    if (str.equals(UtilAccount.getAbreviatureAndAccountFormat(CConsDescripciones.getConsDescripcionTPOCTACORTA(this.a.getSessionManager()), cuentaDebitoBean.tipo, cuentaDebitoBean.sucursal, cuentaDebitoBean.numero))) {
                        return cuentaDebitoBean;
                    }
                }
            } catch (Exception unused) {
            }
        }
        return null;
    }

    public void onShowPageList() {
        onLoadData();
    }

    public void onShowPagePreparePayment() {
        this.a.loadBarReturn();
        getmPaymentServicesView().cleanFormPreparePayment();
        CuentaDebitoBean defaultAccountDeuda = getDefaultAccountDeuda();
        if (defaultAccountDeuda != null) {
            setmAccountSelected(defaultAccountDeuda);
            getmPaymentServicesView().addViewFormPreparePayment(buildViewPreparePayment(getmDatosDeudaBeanSelected(), defaultAccountDeuda));
            getmPaymentServicesView().setListenerClickSelectorAccount();
            return;
        }
        setmAccountSelected(null);
    }

    /* access modifiers changed from: protected */
    public void onShowPageConfirmPayment() {
        if (isValidData()) {
            getmPaymentServicesView().cleanViewTableConfirmPayment();
            getmPaymentServicesView().addViewFormConfirmPayment(buildViewConfirmPayment());
        }
    }

    /* access modifiers changed from: protected */
    public void onShowPageReceiptPayment(RecargaCelularesBodyResponseBean recargaCelularesBodyResponseBean) {
        this.a.loadBarShare();
        getmPaymentServicesView().cleanViewReceiptPayment();
        getmPaymentServicesView().addViewReceiptTop(buildViewReceipt(getmDatosDeudaBeanSelected(), getmAccountSelected(), recargaCelularesBodyResponseBean));
        getmPaymentServicesView().addViewReceiptBottom(createAndGetDataReceipt(recargaCelularesBodyResponseBean));
        showAndHideLegendReceipt(recargaCelularesBodyResponseBean);
    }

    /* access modifiers changed from: protected */
    public List<CuentaDebitoBean> getListCuentaDeudas() {
        try {
            return a(this.b.getResponse().cnsDeudaRecargaBodyResponseBean.cuentasDeudasBean.lstCuentaDeudas);
        } catch (Exception unused) {
            return null;
        }
    }

    private List<CuentaDebitoBean> a(List<CuentaDebitoBean> list) {
        boolean z = false;
        boolean z2 = false;
        for (CuentaDebitoBean cuentaDebitoBean : list) {
            if ("09".equals(cuentaDebitoBean.tipo)) {
                z = true;
            } else if ("10".equals(cuentaDebitoBean.tipo)) {
                z2 = true;
            }
        }
        ArrayList arrayList = new ArrayList();
        if (!z || !z2) {
            return list;
        }
        for (CuentaDebitoBean cuentaDebitoBean2 : list) {
            if (!"10".equals(cuentaDebitoBean2.tipo)) {
                arrayList.add(cuentaDebitoBean2);
            }
        }
        return arrayList;
    }

    public void responseCnsDeudaRecargaEvent(CnsDeudaRecargaEvent cnsDeudaRecargaEvent) {
        if (TypeResult.OK.equals(cnsDeudaRecargaEvent.getResult())) {
            setmCnsDeudaEvent(cnsDeudaRecargaEvent);
            updateTablePayments(getListDatosDeudasFromEvent());
        } else if (TypeResult.BEAN_ERROR_RES_3.equals(cnsDeudaRecargaEvent.getResult())) {
            this.a.onShowDialogError(cnsDeudaRecargaEvent.getTitleToShow(), cnsDeudaRecargaEvent.getMessageToShow());
        } else {
            this.a.onDispatchEventsError(cnsDeudaRecargaEvent);
        }
    }

    public void sendRequestCnsDeuda(IDataManager iDataManager) {
        getmPaymentServicesView().showDialogLoading();
        iDataManager.cnsDeudaRecarga(new CnsDeudaRecargaBodyRequestBean(this.e.name()), EVersionServices.V1_0);
    }

    public void onItemListPaymentServicesClicked(DatosDeudaBean datosDeudaBean) {
        setmDatosDeudaBeanSelected(datosDeudaBean);
        this.f = datosDeudaBean.importe;
        List listCuentaDeudas = getListCuentaDeudas();
        if (getmDatosDeudaBeanSelected() == null || listCuentaDeudas == null) {
            getmPaymentServicesView().onDispatchEventsError(null);
            return;
        }
        gotoPagePreparePayment();
        onShowPagePreparePayment();
    }

    public void backPagePressed(int i) {
        int id2 = this.a.getViewPageFromIndex(i).getId();
        if (id2 == this.a.getvPagePreparePaymentServices().getId()) {
            backToPageTablePayments();
        } else if (id2 == this.a.getvPageConfirmPaymentServices().getId()) {
            backToPagePreparePayment();
        } else if (id2 == this.a.getvPageReceiptPaymentServices().getId()) {
            this.a.reloadFragment(this.e);
        }
    }

    public void onBtnPrepareClicked() {
        if (isValidForm()) {
            try {
                String amountToWebService = CAmountWebService.getAmountToWebService(CAmountIU.getInstance().getDoubleFromInputUser(this.a.getSelectorAmountValue()), false);
                if (amountToWebService != null && !amountToWebService.isEmpty()) {
                    setAmountPayment(amountToWebService);
                    onShowPageConfirmPayment();
                    gotoPageConfirmPayment();
                }
            } catch (Exception e2) {
                Log.e("@dev", "Error al obtener el valor del usuario", e2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onBtnConfirmClicked(int i, int i2) {
        if (isValidForm()) {
            this.a.onShowDialogConfirmPayment(this.a.getActContext().getString(i), this.a.getActContext().getString(i2));
            this.mPaymentServicesAnalitycs.registerDialogConfirm();
            return;
        }
        backToPagePreparePayment();
    }

    public void onBtnNegativeConfirmPaymentClicked() {
        this.mPaymentServicesAnalitycs.registerEventCancelDialogConfirm();
    }

    public void onBtnPossitiveClicked(IDataManager iDataManager) {
        getmPaymentServicesView().showDialogLoading();
        iDataManager.recargaCelulares(c(), getActivity());
    }

    public void onSelectorAccountClicked() {
        try {
            ArrayList arrayList = new ArrayList();
            for (CuentaDebitoBean cuentaDebitoBean : getListCuentaDeudas()) {
                arrayList.add(UtilAccount.getAbreviatureAndAccountFormat(CConsDescripciones.getConsDescripcionTPOCTACORTA(this.a.getSessionManager()), cuentaDebitoBean.tipo, cuentaDebitoBean.sucursal, cuentaDebitoBean.numero));
            }
            if (arrayList.size() > 0) {
                this.a.showDialogSelectorAccount(arrayList, this.a.getSelectorAccount());
            }
        } catch (Exception unused) {
        }
    }

    /* access modifiers changed from: protected */
    public String getDefaultAmountPhoneTopUp() {
        ListTableBean consDescripcionRECARG_MONTO = CConsDescripciones.getConsDescripcionRECARG_MONTO(this.a.getSessionManager());
        for (ListGroupBean listGroupBean : consDescripcionRECARG_MONTO.listGroupBeans) {
            if ("sel".equals(listGroupBean.code.toLowerCase())) {
                return listGroupBean.getLabel();
            }
        }
        if (consDescripcionRECARG_MONTO.listGroupBeans.size() > 0) {
            return ((ListGroupBean) consDescripcionRECARG_MONTO.listGroupBeans.get(0)).getLabel();
        }
        return this.a.getActContext().getString(R.string.LABEL_TABLE_PHONE_TOP_UP_SELECTOR_AMOUNT);
    }

    /* access modifiers changed from: protected */
    public CuentaDebitoBean getDefaultAccountDeuda() {
        for (CuentaDebitoBean cuentaDebitoBean : getListCuentaDeudas()) {
            if ("02".equals(cuentaDebitoBean.tipo)) {
                return cuentaDebitoBean;
            }
        }
        if (getListCuentaDeudas() == null || getListCuentaDeudas().size() <= 0) {
            return null;
        }
        return (CuentaDebitoBean) getListCuentaDeudas().get(0);
    }

    public void validateRecarga(LinkSeguroBean linkSeguroBean) {
        onShowPageReceiptPayment(this.mRecargaCelularesBodyResponseBean);
        gotoPageReceiptPayment();
    }

    public void responsePagoService(RecargaCelularesEvent recargaCelularesEvent) {
        if (recargaCelularesEvent != null) {
            try {
                if (recargaCelularesEvent.getResult().equals(TypeResult.OK) && recargaCelularesEvent.getResponse().recargaCelularesBodyResponseBean != null) {
                    ((SantanderRioMainActivity) getActivity()).dismissProgress();
                    this.mRecargaCelularesBodyResponseBean = recargaCelularesEvent.getResponse().recargaCelularesBodyResponseBean;
                    onShowPageReceiptPayment(this.mRecargaCelularesBodyResponseBean);
                    gotoPageReceiptPayment();
                    return;
                }
            } catch (Exception unused) {
                this.mRecargaCelularesBodyResponseBean = null;
                this.a.onDispatchEventsError(recargaCelularesEvent);
                return;
            }
        }
        if (TypeResult.BEAN_ERROR_RES_3.equals(recargaCelularesEvent.getResult())) {
            this.mRecargaCelularesBodyResponseBean = null;
            this.a.onShowDialogError(recargaCelularesEvent.getTitleToShow(), recargaCelularesEvent.getMessageToShow());
            return;
        }
        this.mRecargaCelularesBodyResponseBean = null;
        this.a.onDispatchEventsError(recargaCelularesEvent);
    }

    public void onAccountSelected(String str) {
        updateAccountSelected(str);
    }

    public void onAmountSelected(String str) {
        b(str);
    }

    public void onReceiptPaymentClicked() {
        this.a.reloadFragment(this.e);
    }

    /* access modifiers changed from: protected */
    public String getAbrevAccount(CuentaDebitoBean cuentaDebitoBean) {
        return UtilAccount.getAbreviatureAndAccountFormat(CConsDescripciones.getConsDescripcionTPOCTACORTA(this.a.getSessionManager()), cuentaDebitoBean.tipo, cuentaDebitoBean.sucursal, cuentaDebitoBean.numero);
    }

    /* access modifiers changed from: protected */
    public View createAndGetDataReceipt(RecargaCelularesBodyResponseBean recargaCelularesBodyResponseBean) {
        TableView tableView = new TableView(getmPaymentServicesView().getActContext());
        tableView.addRowView(RowViewCreator.getRowDataPayment(getmPaymentServicesView().getActContext(), TypeStyle.TYPE_STYLE_LABEL));
        tableView.addRowView(RowViewCreator.getRowDate(getmPaymentServicesView().getActContext(), UtilDate.getDateFormat(recargaCelularesBodyResponseBean.fechaHora, Constants.FORMAT_DATE_TIME, Constants.FORMAT_DATE_APP_2), TypeStyle.TYPE_STYLE_DATA_VALUE));
        tableView.addRowView(RowViewCreator.getRowTime(getmPaymentServicesView().getActContext(), UtilDate.getTimeFormat(recargaCelularesBodyResponseBean.fechaHora, Constants.FORMAT_DATE_TIME), TypeStyle.TYPE_STYLE_DATA_VALUE));
        return tableView;
    }

    private RecargaCelularesBodyRequestBean c() {
        RecargaCelularesBodyRequestBean recargaCelularesBodyRequestBean = new RecargaCelularesBodyRequestBean(getmDatosDeudaBeanSelected().empServ, getmDatosDeudaBeanSelected().identificacion, getTypeMonto(), getmDatosDeudaBeanSelected().factura, getmAccountSelected(), (getmDatosDeudaBeanSelected().moneda == null || getmDatosDeudaBeanSelected().moneda.toString().length() != 3) ? "ARS" : getmDatosDeudaBeanSelected().moneda, getmDatosDeudaBeanSelected().importe);
        return recargaCelularesBodyRequestBean;
    }

    public void onResumeEvent() {
        CAccounts instance = CAccounts.getInstance(this.a.getSessionManager());
        if (instance == null || instance.getListAccountsUserFromSession() == null || instance.getListAccountsUserFromSession().size() <= 0) {
            this.a.errorEmptyAccount();
        } else if (this.a.getCurrentViewPageShow().equals(this.a.getvPageTablePaymentServices())) {
            onShowPageList();
        }
    }

    public void onPauseEvent() {
        try {
            this.a.closeDialogLoading();
            VolleyRequestSingleton.getInstance(this.a.getActContext().getApplicationContext()).getRequestQueue().cancelAll((Object) VCndDeuda.nameService);
        } catch (Exception unused) {
            Log.e("@dev", "Error al pausar el fragment");
        }
    }

    /* access modifiers changed from: protected */
    public boolean isAmountEditable() {
        try {
            if (!"0".equals(getmDatosDeudaBeanSelected().tipoImporte)) {
                return true;
            }
        } catch (Exception unused) {
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean isAmountChange() {
        boolean z = true;
        try {
            Double amountFromWebService = CAmountWebService.getAmountFromWebService(this.c.importe);
            Double amountFromWebService2 = CAmountWebService.getAmountFromWebService(this.f);
            if (amountFromWebService == null || amountFromWebService2 == null || amountFromWebService.equals(amountFromWebService2)) {
                z = false;
            }
            return z;
        } catch (Exception unused) {
            return true ^ this.c.importe.equals(this.f);
        }
    }
}
