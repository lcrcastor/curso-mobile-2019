package ar.com.santander.rio.mbanking.app.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.CAmount;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CompraVentaDolaresCuentaBean;

public class SellDollarsConfirmationActivity extends BuySellDollarsConfirmationActivity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.p.trackScreen(getString(R.string.analytics_screen_confirmar_venta_dolares));
    }

    public void configureLayout() {
        this.activityTitle.setText(getString(R.string.ID3572_COMPRA_VENTA_LBLCONFIRMAR_VENTA_DOLAR));
        this.lbl_dollarsToOperate.setText(getString(R.string.ID3670_COMPRA_VENTA_LBL_DOLARES_DEBITAR));
        this.lbl_pesosToOperate.setText(getString(R.string.ID3675_COMPRA_VENTA_LBL_PESOS_ACREDITAR));
    }

    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.F20_01_btn_confirm) {
            IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.ID3572_COMPRA_VENTA_LBLCONFIRMAR_VENTA_DOLAR), Html.fromHtml(getString(R.string.ID3685_COMPRA_VENTA_LBL_CONFIRMA_VENTA)).toString(), null, null, getString(R.string.ID3610_COMPRA_VENTA_BTN_SI), getString(R.string.ID3605_COMPRA_VENTA_BTN_NO), null);
            newInstance.setDialogListener(new IDialogListener() {
                public void onItemSelected(String str) {
                }

                public void onNegativeButton() {
                }

                public void onSimpleActionButton() {
                }

                public void onPositiveButton() {
                    CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean = new CompraVentaDolaresCuentaBean(SellDollarsConfirmationActivity.this.mOriginAccount.tipo, SellDollarsConfirmationActivity.this.mOriginAccount.sucursal, SellDollarsConfirmationActivity.this.mOriginAccount.numero, "", "");
                    CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean2 = new CompraVentaDolaresCuentaBean(SellDollarsConfirmationActivity.this.mDestinationAccount.tipo, SellDollarsConfirmationActivity.this.mDestinationAccount.sucursal, SellDollarsConfirmationActivity.this.mDestinationAccount.numero, "", "");
                    SellDollarsConfirmationActivity.this.confirmationBuySellDollarsPresenter.performSellingOperation(SellDollarsConfirmationActivity.this.mSelectedCurrency, SellDollarsConfirmationActivity.this.mAmountToBeDebited, SellDollarsConfirmationActivity.this.mAmountToBeDeposited, SellDollarsConfirmationActivity.this.mExchangeRate, compraVentaDolaresCuentaBean, compraVentaDolaresCuentaBean2);
                }
            });
            newInstance.show(getSupportFragmentManager(), "popupConfirmation");
        }
    }

    public void displayReceipt() {
        Intent extraInformation = setExtraInformation();
        extraInformation.setClass(this, SellDollarsReceiptActivity.class);
        startActivityForResult(extraInformation, 3);
    }

    /* access modifiers changed from: protected */
    public void showConfirmation(CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean, CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean2, String str, String str2, String str3, String str4) {
        super.showConfirmation(compraVentaDolaresCuentaBean, compraVentaDolaresCuentaBean2, str, str2, str3, str4);
        this.mCAmount = new CAmount(str);
        this.mCAmount.setSymbolCurrencyDollarOrPeso(true);
        this.lbl_data_dollarsToOperate.setText(this.mCAmount.getAmount());
        try {
            this.lbl_data_dollarsToOperate.setContentDescription(CAccessibility.getInstance(this).applyFilterAmount(this.lbl_data_dollarsToOperate.getText().toString()));
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        this.mCAmount = new CAmount(str2);
        this.mCAmount.setSymbolCurrencyDollarOrPeso(false);
        this.lbl_data_pesosToOperate.setText(this.mCAmount.getAmount());
        try {
            this.lbl_data_pesosToOperate.setContentDescription(CAccessibility.getInstance(this).applyFilterAmount(this.lbl_data_pesosToOperate.getText().toString()));
        } catch (Exception e2) {
            e2.fillInStackTrace();
        }
    }
}
