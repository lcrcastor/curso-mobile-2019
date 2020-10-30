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

public class BuyDollarsConfirmationActivity extends BuySellDollarsConfirmationActivity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.p.trackScreen(getString(R.string.analytics_screen_confirmar_compra_dolares));
    }

    public void configureLayout() {
        this.activityTitle.setText(getString(R.string.ID3597_COMPRA_VENTA_LBL_CONFIRMAR_COMPRA));
        this.lbl_dollarsToOperate.setText(getString(R.string.ID3580_COMPRA_VENTA_LBL_DOLARES_ACREDITAR));
        this.lbl_pesosToOperate.setText(getString(R.string.ID3585_COMPRA_VENTA_LBL_PESOS_DEBITAR));
    }

    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.F20_01_btn_confirm) {
            IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.ID3570_COMPRA_VENTA_LBL_CONFIRMAR_COMPRA_DOLAR), Html.fromHtml(getString(R.string.ID3600_COMPRA_VENTA_LBL_CONFiRMA_COMPRA)).toString(), null, null, getString(R.string.ID3610_COMPRA_VENTA_BTN_SI), getString(R.string.ID3605_COMPRA_VENTA_BTN_NO), null);
            newInstance.setDialogListener(new IDialogListener() {
                public void onItemSelected(String str) {
                }

                public void onNegativeButton() {
                }

                public void onSimpleActionButton() {
                }

                public void onPositiveButton() {
                    CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean = new CompraVentaDolaresCuentaBean(BuyDollarsConfirmationActivity.this.mOriginAccount.tipo, BuyDollarsConfirmationActivity.this.mOriginAccount.sucursal, BuyDollarsConfirmationActivity.this.mOriginAccount.numero, "", "");
                    CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean2 = new CompraVentaDolaresCuentaBean(BuyDollarsConfirmationActivity.this.mDestinationAccount.tipo, BuyDollarsConfirmationActivity.this.mDestinationAccount.sucursal, BuyDollarsConfirmationActivity.this.mDestinationAccount.numero, "", "");
                    BuyDollarsConfirmationActivity.this.confirmationBuySellDollarsPresenter.performBuyingOperation(BuyDollarsConfirmationActivity.this.mSelectedCurrency, BuyDollarsConfirmationActivity.this.mAmountToBeDebited, BuyDollarsConfirmationActivity.this.mAmountToBeDeposited, BuyDollarsConfirmationActivity.this.mExchangeRate, compraVentaDolaresCuentaBean, compraVentaDolaresCuentaBean2);
                }
            });
            newInstance.show(getSupportFragmentManager(), "popupConfirmation");
        }
    }

    public void displayReceipt() {
        Intent extraInformation = setExtraInformation();
        extraInformation.setClass(this, BuyDollarsReceiptActivity.class);
        startActivityForResult(extraInformation, 3);
    }

    /* access modifiers changed from: protected */
    public void showConfirmation(CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean, CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean2, String str, String str2, String str3, String str4) {
        super.showConfirmation(compraVentaDolaresCuentaBean, compraVentaDolaresCuentaBean2, str, str2, str3, str4);
        this.mCAmount = new CAmount(str2);
        this.mCAmount.setSymbolCurrencyDollarOrPeso(true);
        this.lbl_data_dollarsToOperate.setText(this.mCAmount.getAmount());
        try {
            this.lbl_data_dollarsToOperate.setContentDescription(CAccessibility.getInstance(this).applyFilterAmount(this.lbl_data_dollarsToOperate.getText().toString()));
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        this.mCAmount = new CAmount(str);
        this.mCAmount.setSymbolCurrencyDollarOrPeso(false);
        this.lbl_data_pesosToOperate.setText(this.mCAmount.getAmount());
        try {
            this.lbl_data_pesosToOperate.setContentDescription(CAccessibility.getInstance(this).applyFilterAmount(this.lbl_data_pesosToOperate.getText().toString()));
        } catch (Exception e2) {
            e2.fillInStackTrace();
        }
    }
}
