package ar.com.santander.rio.mbanking.app.module.payments.services;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.View;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.CAccounts;
import ar.com.santander.rio.mbanking.app.commons.CAmount;
import ar.com.santander.rio.mbanking.app.commons.CAmountIU;
import ar.com.santander.rio.mbanking.app.commons.CAmountWebService;
import ar.com.santander.rio.mbanking.app.commons.CLegend;
import ar.com.santander.rio.mbanking.app.commons.CLegend.TypeLegend;
import ar.com.santander.rio.mbanking.app.module.payments.commons.BasePaymentsPresenter;
import ar.com.santander.rio.mbanking.app.module.payments.commons.PaymentServicesView;
import ar.com.santander.rio.mbanking.app.module.payments.commons.RowViewCreator;
import ar.com.santander.rio.mbanking.app.module.payments.commons.TypeDebt;
import ar.com.santander.rio.mbanking.app.module.payments.services.analytics.PaymentServicesAnalyticsImp;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.adapters.BasePaymentAdapter;
import ar.com.santander.rio.mbanking.app.ui.adapters.PaymentServicesAdapter;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaDebitoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.RecargaCelularesBodyResponseBean;
import ar.com.santander.rio.mbanking.utils.UtilCurrency;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import ar.com.santander.rio.mbanking.view.tables.BaseColumn.TypeStyle;
import ar.com.santander.rio.mbanking.view.tables.ColumAmountView;
import ar.com.santander.rio.mbanking.view.tables.RowColumnView;
import ar.com.santander.rio.mbanking.view.tables.TableView;
import org.joda.time.DateTime;

public class PaymentServicesPresenterImp extends BasePaymentsPresenter {
    private RowColumnView a;

    public PaymentServicesPresenterImp(PaymentServicesView paymentServicesView) {
        super(TypeDebt.PS, paymentServicesView);
    }

    public Activity getActivity() {
        return (SantanderRioMainActivity) getmPaymentServicesView().getActContext();
    }

    public void setAnalyticManager(AnalyticsManager analyticsManager) {
        this.mPaymentServicesAnalitycs = new PaymentServicesAnalyticsImp(getmPaymentServicesView().getActContext(), analyticsManager);
    }

    public void onUpdateView() {
        super.onUpdateView();
        b();
        c();
        d();
        a();
    }

    private void a() {
        getmPaymentServicesView().setLabelButtonConfirm(R.string.ID383_CELULAR_DETAIL_LBL_CONFIRMRE);
    }

    private void b() {
        setTitleSecondPage(R.string.ID177_PAYMENT_DETAILWITH_LBL_TITLE);
    }

    private void c() {
        setTitleThirdPage(R.string.ID201_PAYMENT_DETAIL_LBL_TITLE);
    }

    private void d() {
        setTitleFourthPage(R.string.ID214_PAYMENTPROOF_LBL_PROOF);
    }

    public BasePaymentAdapter getPaymentAdapter() {
        return new PaymentServicesAdapter(getmPaymentServicesView().getActContext()) {
            public void onItemClickTransactionAdapter(DatosDeudaBean datosDeudaBean) {
                PaymentServicesPresenterImp.this.onItemListPaymentServicesClicked(datosDeudaBean);
            }
        };
    }

    /* access modifiers changed from: protected */
    public void onShowPagePreparePayment() {
        super.onShowPagePreparePayment();
        if (isAmountEditable()) {
            getmPaymentServicesView().setListenerClickSelectorAmount();
            this.mPaymentServicesAnalitycs.registerScreenPrepareWithOutAmount();
            return;
        }
        this.mPaymentServicesAnalitycs.registerScreenPrepareWithAmount();
    }

    public void onSelectorAmountClicked() {
        try {
            e();
        } catch (Exception unused) {
        }
    }

    private void e() {
        if (this.a != null) {
            ((ColumAmountView) this.a.getColValue()).setEditable();
            getmPaymentServicesView().setFocusAndShowKeyboard(((ColumAmountView) this.a.getColValue()).mViewInput);
            if (getmPaymentServicesView().getSelectorAmountValue() != null) {
                try {
                    if (CAmountIU.getInstance().getDoubleFromInputUser(getmPaymentServicesView().getSelectorAmountValue()).doubleValue() <= 0.0d) {
                        getmPaymentServicesView().setSelectorValue("");
                    }
                } catch (Exception unused) {
                }
            }
        }
    }

    public void btnConfirmClicked() {
        onBtnConfirmClicked(R.string.ID213_PAYMENT_DETAIL_LBL_CONFIRM, R.string.MSG_USER000043_PagoServ_avisoConfirmacion);
    }

    public View buildViewPreparePayment(DatosDeudaBean datosDeudaBean, CuentaDebitoBean cuentaDebitoBean) {
        TableView tableView = new TableView(getmPaymentServicesView().getActContext());
        tableView.addRowView(RowViewCreator.getRowCompany(getmPaymentServicesView().getActContext(), datosDeudaBean.empDescr, TypeStyle.TYPE_STYLE_LABEL));
        tableView.addRowView(RowViewCreator.getRowIdentification(getmPaymentServicesView().getActContext(), datosDeudaBean.identificacion, TypeStyle.TYPE_STYLE_LABEL));
        if (isAmountEditable()) {
            this.a = RowViewCreator.getRowAmountSwitchValueToInput(getmPaymentServicesView().getActContext(), datosDeudaBean.importe, TypeStyle.TYPE_STYLE_LABEL);
            tableView.addRowView(this.a);
        } else {
            Context actContext = getmPaymentServicesView().getActContext();
            String string = getmPaymentServicesView().getActContext().getString(R.string.ID377_CELULAR_DETAIL_LBL_IMPORT);
            StringBuilder sb = new StringBuilder();
            sb.append(UtilCurrency.getSimbolCurrencyFromString(datosDeudaBean.moneda));
            sb.append(CAmount.getInstance(datosDeudaBean.importe).getAmountFormattedES());
            tableView.addRowView(RowViewCreator.getRowAmountValue(actContext, string, sb.toString(), null, TypeStyle.TYPE_STYLE_LABEL, Boolean.valueOf(true)));
        }
        tableView.addRowView(RowViewCreator.getRowCurrency(getmPaymentServicesView().getActContext(), TypeStyle.TYPE_STYLE_LABEL));
        tableView.addRowView(RowViewCreator.getRowAccountSelector(getmPaymentServicesView().getActContext(), getAbrevAccount(cuentaDebitoBean), TypeStyle.TYPE_STYLE_LABEL, Boolean.valueOf(true)));
        tableView.addRowView(RowViewCreator.getRowExpired(getmPaymentServicesView().getActContext(), datosDeudaBean.vencimiento, TypeStyle.TYPE_STYLE_LABEL));
        tableView.addRowView(RowViewCreator.getRowInvoice(getmPaymentServicesView().getActContext(), datosDeudaBean.factura, TypeStyle.TYPE_STYLE_LABEL));
        tableView.addRowView(RowViewCreator.getRowMethodPayment(getmPaymentServicesView().getActContext(), TypeStyle.TYPE_STYLE_LABEL));
        return tableView;
    }

    public View buildViewConfirmPayment() {
        TableView tableView = new TableView(getmPaymentServicesView().getActContext());
        tableView.addRowView(RowViewCreator.getRowCompany(getmPaymentServicesView().getActContext(), getmDatosDeudaBeanSelected().empDescr, TypeStyle.TYPE_STYLE_DATA_VALUE));
        tableView.addRowView(RowViewCreator.getRowIdentification(getmPaymentServicesView().getActContext(), getmDatosDeudaBeanSelected().identificacion, TypeStyle.TYPE_STYLE_DATA_VALUE));
        Context actContext = getmPaymentServicesView().getActContext();
        String string = getmPaymentServicesView().getActContext().getString(R.string.ID377_CELULAR_DETAIL_LBL_IMPORT);
        StringBuilder sb = new StringBuilder();
        sb.append(UtilCurrency.getSimbolCurrencyFromString(getmDatosDeudaBeanSelected().moneda));
        sb.append(a(getmDatosDeudaBeanSelected().importe));
        tableView.addRowView(RowViewCreator.getRowAmountValue(actContext, string, sb.toString(), Integer.valueOf(R.color.black), TypeStyle.TYPE_STYLE_DATA_VALUE, Boolean.valueOf(false)));
        tableView.addRowView(RowViewCreator.getRowCurrency(getmPaymentServicesView().getActContext(), TypeStyle.TYPE_STYLE_DATA_VALUE));
        tableView.addRowView(RowViewCreator.getRowAccountValue(getmPaymentServicesView().getActContext(), getmPaymentServicesView().getActContext().getString(R.string.ID183_PAYMENT_DETAILWITH_LBL_DEBITACCOUNT), getAbrevAccount(getmAccountSelected()), Integer.valueOf(R.color.black), TypeStyle.TYPE_STYLE_DATA_VALUE, Boolean.valueOf(false)));
        tableView.addRowView(RowViewCreator.getRowExpired(getmPaymentServicesView().getActContext(), getmDatosDeudaBeanSelected().vencimiento, TypeStyle.TYPE_STYLE_DATA_VALUE));
        tableView.addRowView(RowViewCreator.getRowInvoice(getmPaymentServicesView().getActContext(), getmDatosDeudaBeanSelected().factura, TypeStyle.TYPE_STYLE_DATA_VALUE));
        tableView.addRowView(RowViewCreator.getRowMethodPayment(getmPaymentServicesView().getActContext(), TypeStyle.TYPE_STYLE_DATA_VALUE));
        return tableView;
    }

    public View buildViewReceipt(DatosDeudaBean datosDeudaBean, CuentaDebitoBean cuentaDebitoBean, RecargaCelularesBodyResponseBean recargaCelularesBodyResponseBean) {
        DatosDeudaBean datosDeudaBean2 = datosDeudaBean;
        RecargaCelularesBodyResponseBean recargaCelularesBodyResponseBean2 = recargaCelularesBodyResponseBean;
        TableView tableView = new TableView(getmPaymentServicesView().getActContext());
        tableView.addRowView(RowViewCreator.getRowCompany(getmPaymentServicesView().getActContext(), datosDeudaBean2.empDescr, TypeStyle.TYPE_STYLE_DATA_VALUE));
        tableView.addRowView(RowViewCreator.getRowIdentification(getmPaymentServicesView().getActContext(), datosDeudaBean2.identificacion, TypeStyle.TYPE_STYLE_DATA_VALUE));
        Context actContext = getmPaymentServicesView().getActContext();
        String string = getmPaymentServicesView().getActContext().getString(R.string.ID180_PAYMENT_DETAILWITH_LBL_PAYMENTAMOUNT);
        StringBuilder sb = new StringBuilder();
        sb.append(UtilCurrency.getSimbolCurrencyFromString(datosDeudaBean2.moneda));
        sb.append(CAmount.getInstance(datosDeudaBean2.importe).getAmountFormattedES());
        tableView.addRowView(RowViewCreator.getRowAmountValue(actContext, string, sb.toString(), Integer.valueOf(R.color.black), TypeStyle.TYPE_STYLE_DATA_VALUE, Boolean.valueOf(false)));
        tableView.addRowView(RowViewCreator.getRowAccountValue(getmPaymentServicesView().getActContext(), getmPaymentServicesView().getActContext().getString(R.string.ID183_PAYMENT_DETAILWITH_LBL_DEBITACCOUNT), CAccounts.getMaskAccount(getAbrevAccount(getmAccountSelected())), Integer.valueOf(R.color.black), TypeStyle.TYPE_STYLE_DATA_VALUE, Boolean.valueOf(false)));
        tableView.addRowView(RowViewCreator.getRowInvoice(getmPaymentServicesView().getActContext(), datosDeudaBean2.factura, TypeStyle.TYPE_STYLE_DATA_VALUE));
        tableView.addRowView(RowViewCreator.getRowNroRecipt(getmPaymentServicesView().getActContext(), recargaCelularesBodyResponseBean2.nroComp, TypeStyle.TYPE_STYLE_DATA_VALUE));
        tableView.addRowView(RowViewCreator.getRowNroControl(getmPaymentServicesView().getActContext(), recargaCelularesBodyResponseBean2.nroControl, TypeStyle.TYPE_STYLE_DATA_VALUE));
        this.mPaymentServicesAnalitycs.registerTransactionHits(recargaCelularesBodyResponseBean2.nroComp);
        return tableView;
    }

    /* access modifiers changed from: protected */
    public void showAndHideLegendReceipt(RecargaCelularesBodyResponseBean recargaCelularesBodyResponseBean) {
        if (recargaCelularesBodyResponseBean.getLstLeyendas() == null || recargaCelularesBodyResponseBean.getLstLeyendas().lstLeyendas == null) {
            getmPaymentServicesView().hideViewLegend();
            return;
        }
        getmPaymentServicesView().showViewLegend();
        getmPaymentServicesView().setTextLegendTop(Html.fromHtml(CLegend.getInstance().getDescriptionLegend(recargaCelularesBodyResponseBean.getLstLeyendas(), TypeLegend.PAG_COMP, "")).toString());
        getmPaymentServicesView().setTextLegendBottom(Html.fromHtml(CLegend.getInstance().getDescriptionLegend(recargaCelularesBodyResponseBean.getLstLeyendas(), TypeLegend.PAG_REC, "")).toString());
    }

    private String a(String str) {
        try {
            Double amountFromWebService = CAmountWebService.getAmountFromWebService(str);
            if (amountFromWebService != null) {
                return CAmountIU.getInstance().getOutputUserFromDouble(amountFromWebService);
            }
        } catch (Exception e) {
            Log.e("@dev", "Error al convertir la cantidad del usuario", e);
        }
        return "0";
    }

    public void onShareReceipt() {
        Object obj;
        StringBuilder sb = new StringBuilder();
        sb.append(getmDatosDeudaBeanSelected().empDescr);
        sb.append(UtilsCuentas.SEPARAOR2);
        if (this.mRecargaCelularesBodyResponseBean != null) {
            obj = this.mRecargaCelularesBodyResponseBean.nroComp;
        } else {
            obj = DateTime.now().millisOfSecond();
        }
        sb.append(obj);
        String sb2 = sb.toString();
        getmPaymentServicesView().showActionShareReceipt(getmPaymentServicesView().getActContext().getString(R.string.IDXX_SERVICE_APP_SHARE, new Object[]{getmDatosDeudaBeanSelected().empDescr}), getmPaymentServicesView().getActContext().getString(R.string.IDXX_SERVICE_APP_SHARE, new Object[]{sb2}));
    }

    public String getTypeMonto() {
        try {
            if (isAmountChange()) {
                return "3";
            }
        } catch (Exception unused) {
        }
        return "0";
    }
}
