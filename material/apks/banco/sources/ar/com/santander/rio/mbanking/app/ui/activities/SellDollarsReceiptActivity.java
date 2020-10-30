package ar.com.santander.rio.mbanking.app.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.CAmount;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.module.buySellDollars.ReceiptBuySellDollarsPresenter;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.share.OptionsToShareImpl;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CompraVentaDolaresCuentaBean;
import java.util.ArrayList;

public class SellDollarsReceiptActivity extends BuySellDollarsReceiptActivity {
    protected ReceiptBuySellDollarsPresenter receiptBuySellDollarsPresenter;

    public String getFileName() {
        return null;
    }

    public String getSubjectReceiptToShare() {
        return null;
    }

    public View getViewToShare() {
        return null;
    }

    public void onAbortShare() {
    }

    public void onDownloadShare() {
    }

    public void onError(Exception exc) {
    }

    public void onErrorDownload() {
    }

    public void onFinishShare() {
    }

    public void onGetPolizaResult() {
    }

    public void onOption1Button() {
    }

    public void onOption2Button() {
    }

    public void onOption3Button() {
    }

    public void optionCancelSelected() {
    }

    public void optionDownloadSelected() {
    }

    public void optionShareSelected() {
    }

    public void receiveIntentAppShare(Intent intent) {
    }

    public void show() {
    }

    public void show(String str) {
    }

    public void showAlert() {
    }

    /* access modifiers changed from: protected */
    public void showSharedOption() {
    }

    public void showWithCancel() {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.p.trackScreen(getString(R.string.analytics_screen_comprobante_venta_dolares));
    }

    public void configureLayout() {
        this.activityTitle.setText(getString(R.string.ID3615_COMPRA_VENTA_LBL_COMPROBANTE_VENTA));
        this.lbl_dollarsToOperate.setText(getString(R.string.ID3705_COMPRA_VENTA_LBL_DOLARES_DEBITADOS));
        this.lbl_pesosToOperate.setText(getString(R.string.ID3710_COMPRA_VENTA_LBL_PESOS_ACREDITADOS));
    }

    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.menu) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT));
            arrayList.add(getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DOWNLOAD));
            IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("mPopupMenu", getResources().getString(R.string.TITLE_RECEIPT_SHARE), null, arrayList, getString(R.string.IDXX_SHARE_OPTION_RECEIPT_CANCEL), null, null, null, arrayList);
            newInstance.setDialogListener(new IDialogListener() {
                public void onNegativeButton() {
                }

                public void onPositiveButton() {
                }

                public void onSimpleActionButton() {
                }

                public void onItemSelected(String str) {
                    if (str.equalsIgnoreCase(SellDollarsReceiptActivity.this.getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT))) {
                        SellDollarsReceiptActivity.this.sharingOptions.optionShareSelected();
                    } else if (str.equalsIgnoreCase(SellDollarsReceiptActivity.this.getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DOWNLOAD))) {
                        SellDollarsReceiptActivity.this.sharingOptions.optionDownloadSelected();
                    }
                }
            });
            newInstance.setCancelable(true);
            newInstance.show(getSupportFragmentManager(), "mPopupMenu");
        }
    }

    public void showReceipt(String str, String str2, String str3, CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean, CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean2, String str4, String str5, String str6, String str7, String str8) {
        super.showReceipt(str, str2, str3, compraVentaDolaresCuentaBean, compraVentaDolaresCuentaBean2, str4, str5, str6, str7, str8);
        this.mCAmount = new CAmount(str5);
        this.mCAmount.setSymbolCurrencyDollarOrPeso(true);
        this.lbl_data_dollarsToOperate.setText(this.mCAmount.getAmount());
        this.lbl_data_receipt_debitedAmount.setText(this.mCAmount.getAmount());
        this.mCAmount = new CAmount(str4);
        this.mCAmount.setSymbolCurrencyDollarOrPeso(false);
        this.lbl_data_pesosToOperate.setText(this.mCAmount.getAmount());
        this.lbl_data_receipt_depositedAmount.setText(this.mCAmount.getAmount());
        try {
            this.lbl_data_receipt_debitedAmount.setContentDescription(CAccessibility.getInstance(this).applyFilterAmount(this.lbl_data_receipt_debitedAmount.getText().toString()));
            this.lbl_data_receipt_depositedAmount.setContentDescription(CAccessibility.getInstance(this).applyFilterAmount(this.lbl_data_receipt_depositedAmount.getText().toString()));
            this.lbl_data_dollarsToOperate.setContentDescription(CAccessibility.getInstance(this).applyFilterAmount(this.lbl_data_dollarsToOperate.getText().toString()));
            this.lbl_data_pesosToOperate.setContentDescription(CAccessibility.getInstance(this).applyFilterAmount(this.lbl_data_pesosToOperate.getText().toString()));
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    public void configureOptionsShare() {
        this.sharingOptions = new OptionsToShareImpl(this, getApplicationContext(), getSupportFragmentManager()) {
            public View getViewToShare() {
                return SellDollarsReceiptActivity.this.lnlReceipt;
            }

            public void receiveIntentAppShare(Intent intent) {
                SellDollarsReceiptActivity.this.startActivityForResult(Intent.createChooser(intent, SellDollarsReceiptActivity.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)), 3);
            }

            public String getFileName() {
                return String.format(SellDollarsReceiptActivity.this.getString(R.string.IDXX_VENTA_DOLARES_ARCHIVO_JPG_COMPROBANTE), new Object[]{SellDollarsReceiptActivity.this.mReceiptNbr});
            }

            public String getSubjectReceiptToShare() {
                return String.format(SellDollarsReceiptActivity.this.getString(R.string.IDXX_VENTA_DOLARES_ASUNTO_MAIL), new Object[]{SellDollarsReceiptActivity.this.mReceiptNbr});
            }

            public void optionDownloadSelected() {
                super.optionDownloadSelected();
                SellDollarsReceiptActivity.this.receiptHasBeenDownloaded = Boolean.valueOf(true);
            }

            public void optionShareSelected() {
                super.optionShareSelected();
                SellDollarsReceiptActivity.this.receiptHasBeenDownloaded = Boolean.valueOf(true);
            }

            public void onAbortShare() {
                super.onAbortShare();
                SellDollarsReceiptActivity.this.receiptHasBeenDownloaded = Boolean.valueOf(true);
                SellDollarsReceiptActivity.this.onBackPressed();
            }
        };
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        this.sharingOptions.onRequestPermissionsResult(i, strArr, iArr);
    }

    public void configureOptionsShare(int i) {
        final int i2 = i;
        AnonymousClass3 r0 = new OptionsToShareImpl(this, getApplicationContext(), getSupportFragmentManager()) {
            public View getViewToShare() {
                return SellDollarsReceiptActivity.this.lnlReceipt;
            }

            public void receiveIntentAppShare(Intent intent) {
                SellDollarsReceiptActivity.this.startActivityForResult(Intent.createChooser(intent, SellDollarsReceiptActivity.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)), 3);
            }

            public String getFileName() {
                return String.format(SellDollarsReceiptActivity.this.getString(R.string.IDXX_VENTA_DOLARES_ARCHIVO_JPG_COMPROBANTE), new Object[]{SellDollarsReceiptActivity.this.mReceiptNbr});
            }

            public String getSubjectReceiptToShare() {
                return String.format(SellDollarsReceiptActivity.this.getString(R.string.IDXX_VENTA_DOLARES_ASUNTO_MAIL), new Object[]{SellDollarsReceiptActivity.this.mReceiptNbr});
            }

            public void optionDownloadSelected() {
                super.optionDownloadSelected();
                SellDollarsReceiptActivity.this.receiptHasBeenDownloaded = Boolean.valueOf(true);
            }

            public void optionShareSelected() {
                super.optionShareSelected();
                SellDollarsReceiptActivity.this.receiptHasBeenDownloaded = Boolean.valueOf(true);
            }

            public void onAbortShare() {
                super.onAbortShare();
                SellDollarsReceiptActivity.this.receiptHasBeenDownloaded = Boolean.valueOf(true);
                SellDollarsReceiptActivity.this.onClickItem(i2);
            }
        };
        this.sharingOptionsAtExit = r0;
    }
}
