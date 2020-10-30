package ar.com.santander.rio.mbanking.app.module.payments.phones;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.CAccounts;
import ar.com.santander.rio.mbanking.app.commons.CAmount;
import ar.com.santander.rio.mbanking.app.commons.CConsDescripciones;
import ar.com.santander.rio.mbanking.app.module.payments.commons.BasePaymentsPresenter;
import ar.com.santander.rio.mbanking.app.module.payments.commons.PaymentServicesView;
import ar.com.santander.rio.mbanking.app.module.payments.commons.RowViewCreator;
import ar.com.santander.rio.mbanking.app.module.payments.commons.TypeDebt;
import ar.com.santander.rio.mbanking.app.module.payments.phones.analytics.PhoneTopUpAnalyticsImp;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.adapters.BasePaymentAdapter;
import ar.com.santander.rio.mbanking.app.ui.adapters.RecargaCelularAdapter;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaDebitoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListGroupBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.RecargaCelularesBodyResponseBean;
import ar.com.santander.rio.mbanking.utils.UtilCurrency;
import ar.com.santander.rio.mbanking.utils.UtilList;
import ar.com.santander.rio.mbanking.view.tables.BaseColumn.TypeStyle;
import ar.com.santander.rio.mbanking.view.tables.TableView;
import java.util.ArrayList;

public class PhoneTopUpPresenterImp extends BasePaymentsPresenter {
    public String getTypeMonto() {
        return "3";
    }

    public PhoneTopUpPresenterImp(PaymentServicesView paymentServicesView) {
        super(TypeDebt.RC, paymentServicesView);
    }

    public Activity getActivity() {
        return (SantanderRioMainActivity) getmPaymentServicesView().getActContext();
    }

    public void onUpdateView() {
        super.onUpdateView();
        b();
        c();
        d();
        a();
    }

    private void a() {
        getmPaymentServicesView().setLabelButtonConfirm(R.string.ID384_CELULAR_CONFIRM_LBL_CONFIRM);
    }

    public void setAnalyticManager(AnalyticsManager analyticsManager) {
        this.mPaymentServicesAnalitycs = new PhoneTopUpAnalyticsImp(getmPaymentServicesView().getActContext(), analyticsManager);
    }

    private void b() {
        setTitleSecondPage(R.string.ID361_CELULAR_MAIN_LBL_RECHARGE);
    }

    private void c() {
        setTitleThirdPage(R.string.ID374_CELULAR_DETAIL_LBL_CONFIRM);
    }

    private void d() {
        setTitleFourthPage(R.string.ID385_CELULAR_PROOF_LBL_TITLE);
    }

    public BasePaymentAdapter getPaymentAdapter() {
        return new RecargaCelularAdapter(getmPaymentServicesView().getActContext()) {
            public void onItemClickTransactionAdapter(DatosDeudaBean datosDeudaBean) {
                PhoneTopUpPresenterImp.this.onItemListPaymentServicesClicked(datosDeudaBean);
            }
        };
    }

    /* access modifiers changed from: protected */
    public void onShowPagePreparePayment() {
        super.onShowPagePreparePayment();
        getmPaymentServicesView().setListenerClickSelectorAmount();
        this.mPaymentServicesAnalitycs.registerScreenPrepareWithAmount();
    }

    public void onSelectorAmountClicked() {
        try {
            getmPaymentServicesView().showDialogSelector(R.id.input_amount_id, e(), getmPaymentServicesView().getSelectorAmountValue(), getmPaymentServicesView().getActContext().getString(R.string.ID980_CCARDS_PREPARAR_PAGO_LABEL_SELECCIONARIMPORTE));
        } catch (Exception unused) {
        }
    }

    private ArrayList<String> e() {
        ArrayList arrayList = new ArrayList();
        for (ListGroupBean listGroupBean : CConsDescripciones.getConsDescripcionRECARG_MONTO(getmPaymentServicesView().getSessionManager()).listGroupBeans) {
            if (!"sel".equals(listGroupBean.getLabel().toLowerCase())) {
                arrayList.add(listGroupBean.getLabel());
            }
        }
        return (ArrayList) UtilList.orderArrayIndexStringNumeric(arrayList);
    }

    public View buildViewPreparePayment(DatosDeudaBean datosDeudaBean, CuentaDebitoBean cuentaDebitoBean) {
        TableView tableView = new TableView(getmPaymentServicesView().getActContext());
        tableView.addRowView(RowViewCreator.getRowNumberTopUp(getmPaymentServicesView().getActContext(), datosDeudaBean.identificacion, TypeStyle.TYPE_STYLE_LABEL));
        tableView.addRowView(RowViewCreator.getRowCompany(getmPaymentServicesView().getActContext(), datosDeudaBean.empDescr, TypeStyle.TYPE_STYLE_LABEL));
        tableView.addRowView(RowViewCreator.getRowAmountSelector(getmPaymentServicesView().getActContext(), getDefaultAmountPhoneTopUp(), TypeStyle.TYPE_STYLE_LABEL, Boolean.valueOf(true)));
        tableView.addRowView(RowViewCreator.getRowCurrency(getmPaymentServicesView().getActContext(), TypeStyle.TYPE_STYLE_LABEL));
        tableView.addRowView(RowViewCreator.getRowAccountSelector(getmPaymentServicesView().getActContext(), getAbrevAccount(cuentaDebitoBean), TypeStyle.TYPE_STYLE_LABEL, Boolean.valueOf(true)));
        tableView.addRowView(RowViewCreator.getRowExpired(getmPaymentServicesView().getActContext(), datosDeudaBean.vencimiento, TypeStyle.TYPE_STYLE_LABEL));
        tableView.addRowView(RowViewCreator.getRowMethodPayment(getmPaymentServicesView().getActContext(), TypeStyle.TYPE_STYLE_LABEL));
        return tableView;
    }

    public void btnConfirmClicked() {
        onBtnConfirmClicked(R.string.ID384_CELULAR_CONFIRM_LBL_CONFIRM, R.string.MSG_USER000044_Recarga_avisoConfirmacion);
    }

    public View buildViewConfirmPayment() {
        TableView tableView = new TableView(getmPaymentServicesView().getActContext());
        tableView.addRowView(RowViewCreator.getRowNumberTopUp(getmPaymentServicesView().getActContext(), getmDatosDeudaBeanSelected().identificacion, TypeStyle.TYPE_STYLE_DATA_VALUE));
        tableView.addRowView(RowViewCreator.getRowCompany(getmPaymentServicesView().getActContext(), getmDatosDeudaBeanSelected().empDescr, TypeStyle.TYPE_STYLE_DATA_VALUE));
        Context actContext = getmPaymentServicesView().getActContext();
        String string = getmPaymentServicesView().getActContext().getString(R.string.ID377_CELULAR_DETAIL_LBL_IMPORT);
        StringBuilder sb = new StringBuilder();
        sb.append(UtilCurrency.getSimbolCurrencyFromString(getmDatosDeudaBeanSelected().moneda, Constants.SYMBOL_CURRENCY_PESOS));
        sb.append(CAmount.getInstance(getmDatosDeudaBeanSelected().importe).getAmountFormattedES());
        tableView.addRowView(RowViewCreator.getRowAmountValue(actContext, string, sb.toString(), Integer.valueOf(R.color.black), TypeStyle.TYPE_STYLE_DATA_VALUE, Boolean.valueOf(false)));
        tableView.addRowView(RowViewCreator.getRowCurrency(getmPaymentServicesView().getActContext(), TypeStyle.TYPE_STYLE_DATA_VALUE));
        tableView.addRowView(RowViewCreator.getRowAccountValue(getmPaymentServicesView().getActContext(), getmPaymentServicesView().getActContext().getString(R.string.ID183_PAYMENT_DETAILWITH_LBL_DEBITACCOUNT), getAbrevAccount(getmAccountSelected()), Integer.valueOf(R.color.black), TypeStyle.TYPE_STYLE_DATA_VALUE, Boolean.valueOf(false)));
        tableView.addRowView(RowViewCreator.getRowExpired(getmPaymentServicesView().getActContext(), getmDatosDeudaBeanSelected().vencimiento, TypeStyle.TYPE_STYLE_DATA_VALUE));
        tableView.addRowView(RowViewCreator.getRowMethodPayment(getmPaymentServicesView().getActContext(), TypeStyle.TYPE_STYLE_DATA_VALUE));
        return tableView;
    }

    public View buildViewReceipt(DatosDeudaBean datosDeudaBean, CuentaDebitoBean cuentaDebitoBean, RecargaCelularesBodyResponseBean recargaCelularesBodyResponseBean) {
        TableView tableView = new TableView(getmPaymentServicesView().getActContext());
        tableView.addRowView(RowViewCreator.getRowNumberTopUp(getmPaymentServicesView().getActContext(), datosDeudaBean.identificacion, TypeStyle.TYPE_STYLE_DATA_VALUE));
        tableView.addRowView(RowViewCreator.getRowCompany(getmPaymentServicesView().getActContext(), datosDeudaBean.empDescr, TypeStyle.TYPE_STYLE_DATA_VALUE));
        Context actContext = getmPaymentServicesView().getActContext();
        String string = getmPaymentServicesView().getActContext().getString(R.string.ID377_CELULAR_DETAIL_LBL_IMPORT);
        StringBuilder sb = new StringBuilder();
        sb.append(UtilCurrency.getSimbolCurrencyFromString(datosDeudaBean.moneda, Constants.SYMBOL_CURRENCY_PESOS));
        sb.append(CAmount.getInstance(datosDeudaBean.importe).getAmountFormattedES());
        tableView.addRowView(RowViewCreator.getRowAmountValue(actContext, string, sb.toString(), Integer.valueOf(R.color.black), TypeStyle.TYPE_STYLE_DATA_VALUE, Boolean.valueOf(false)));
        tableView.addRowView(RowViewCreator.getRowAccountValue(getmPaymentServicesView().getActContext(), getmPaymentServicesView().getActContext().getString(R.string.ID183_PAYMENT_DETAILWITH_LBL_DEBITACCOUNT), CAccounts.getMaskAccount(getAbrevAccount(getmAccountSelected())), Integer.valueOf(R.color.black), TypeStyle.TYPE_STYLE_DATA_VALUE, Boolean.valueOf(false)));
        tableView.addRowView(RowViewCreator.getRowNroRecipt(getmPaymentServicesView().getActContext(), recargaCelularesBodyResponseBean.nroComp, TypeStyle.TYPE_STYLE_DATA_VALUE));
        tableView.addRowView(RowViewCreator.getRowNroControl(getmPaymentServicesView().getActContext(), recargaCelularesBodyResponseBean.nroControl, TypeStyle.TYPE_STYLE_DATA_VALUE));
        this.mPaymentServicesAnalitycs.registerTransactionHits(recargaCelularesBodyResponseBean.nroComp);
        return tableView;
    }

    /* access modifiers changed from: protected */
    public void showAndHideLegendReceipt(RecargaCelularesBodyResponseBean recargaCelularesBodyResponseBean) {
        getmPaymentServicesView().showViewLegend();
        getmPaymentServicesView().setTextLegendTop(getmPaymentServicesView().getActContext().getString(R.string.ID395_CELULAR_PROOF_LBL_REMEMBER));
        getmPaymentServicesView().setTextLegendBottom(getmPaymentServicesView().getActContext().getString(R.string.ID396_CELULAR_PROOF_LBL_SEUO));
        if (recargaCelularesBodyResponseBean.getLinkSeguro() != null) {
            getmPaymentServicesView().showLink(recargaCelularesBodyResponseBean.getLinkSeguro(), getmAccountSelected(), recargaCelularesBodyResponseBean.nroComp);
        }
    }

    public void onShareReceipt() {
        StringBuilder sb = new StringBuilder();
        sb.append(UtilCurrency.getSimbolCurrencyFromString(getmDatosDeudaBeanSelected().moneda, Constants.SYMBOL_CURRENCY_PESOS));
        sb.append(CAmount.getInstance(getmDatosDeudaBeanSelected().importe).getAmountFormattedES());
        sb.append(" - ");
        sb.append(getmDatosDeudaBeanSelected().identificacion);
        String sb2 = sb.toString();
        getmPaymentServicesView().showActionShareReceipt(getmPaymentServicesView().getActContext().getString(R.string.IDXX_PHONE_APP_SHARE, new Object[]{sb2}), getmPaymentServicesView().getActContext().getString(R.string.IDXX_PHONE_APP_SHARE, new Object[]{sb2}));
    }
}
