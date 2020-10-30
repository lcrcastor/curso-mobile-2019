package ar.com.santander.rio.mbanking.app.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.share.OptionsToShareImpl;
import java.util.ArrayList;

public class CategoryCouponReceiptSuperClubActivity extends CouponReceiptSuperClubActivity {
    public void initialize() {
        super.initialize();
    }

    public void configureOptionsShare() {
        this.sharingOptions = new OptionsToShareImpl(this, getApplicationContext(), getSupportFragmentManager()) {
            public View getViewToShare() {
                return CategoryCouponReceiptSuperClubActivity.this.scrReceipt;
            }

            public void receiveIntentAppShare(Intent intent) {
                CategoryCouponReceiptSuperClubActivity.this.startActivityForResult(Intent.createChooser(intent, CategoryCouponReceiptSuperClubActivity.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)), 4);
            }

            public String getFileName() {
                return String.format(CategoryCouponReceiptSuperClubActivity.this.getString(R.string.ID3025_COMODINES_SUPER_CLUB_ARCHIVO_JPG_COMPROBANTE), new Object[]{CategoryCouponReceiptSuperClubActivity.this.mReceiptNumber});
            }

            public String getSubjectReceiptToShare() {
                return String.format(CategoryCouponReceiptSuperClubActivity.this.getString(R.string.ID3023_COMODINES_SUPER_CLUB_ASUNTO_MAIL_CANJE_RUBRO), new Object[]{CategoryCouponReceiptSuperClubActivity.this.mCategoryData.nombre});
            }

            public void optionDownloadSelected() {
                super.optionDownloadSelected();
                CategoryCouponReceiptSuperClubActivity.this.receiptHasBeenDownloaded = Boolean.valueOf(true);
            }

            public void optionShareSelected() {
                super.optionShareSelected();
                CategoryCouponReceiptSuperClubActivity.this.receiptHasBeenDownloaded = Boolean.valueOf(true);
            }

            public void onAbortShare() {
                super.onAbortShare();
                CategoryCouponReceiptSuperClubActivity.this.receiptHasBeenDownloaded = Boolean.valueOf(true);
                CategoryCouponReceiptSuperClubActivity.this.onBackPressed();
            }
        };
    }

    public void configureOptionsShare(int i) {
        final int i2 = i;
        AnonymousClass2 r0 = new OptionsToShareImpl(this, getApplicationContext(), getSupportFragmentManager()) {
            public View getViewToShare() {
                return CategoryCouponReceiptSuperClubActivity.this.scrReceipt;
            }

            public void receiveIntentAppShare(Intent intent) {
                CategoryCouponReceiptSuperClubActivity.this.startActivityForResult(Intent.createChooser(intent, CategoryCouponReceiptSuperClubActivity.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)), 4);
            }

            public String getFileName() {
                return String.format(CategoryCouponReceiptSuperClubActivity.this.getString(R.string.ID3025_COMODINES_SUPER_CLUB_ARCHIVO_JPG_COMPROBANTE), new Object[]{CategoryCouponReceiptSuperClubActivity.this.mReceiptNumber});
            }

            public String getSubjectReceiptToShare() {
                return String.format(CategoryCouponReceiptSuperClubActivity.this.getString(R.string.ID3023_COMODINES_SUPER_CLUB_ASUNTO_MAIL_CANJE_RUBRO), new Object[]{CategoryCouponReceiptSuperClubActivity.this.mCategoryData.nombre});
            }

            public void optionDownloadSelected() {
                super.optionDownloadSelected();
                CategoryCouponReceiptSuperClubActivity.this.receiptHasBeenDownloaded = Boolean.valueOf(true);
                CategoryCouponReceiptSuperClubActivity.this.r.trackEvent(CategoryCouponReceiptSuperClubActivity.this.getString(R.string.analytics_screen_category_comodines_superclub), CategoryCouponReceiptSuperClubActivity.this.getString(R.string.analytics_screen_action_descargar_comprobante), CategoryCouponReceiptSuperClubActivity.this.getString(R.string.analytics_screen_label_comprobante));
            }

            public void optionShareSelected() {
                super.optionShareSelected();
                CategoryCouponReceiptSuperClubActivity.this.receiptHasBeenDownloaded = Boolean.valueOf(true);
                CategoryCouponReceiptSuperClubActivity.this.r.trackEvent(CategoryCouponReceiptSuperClubActivity.this.getString(R.string.analytics_screen_category_comodines_superclub), CategoryCouponReceiptSuperClubActivity.this.getString(R.string.analytics_screen_action_compartir_comprobante), CategoryCouponReceiptSuperClubActivity.this.getString(R.string.analytics_screen_label_comprobante));
            }

            public void onAbortShare() {
                super.onAbortShare();
                CategoryCouponReceiptSuperClubActivity.this.receiptHasBeenDownloaded = Boolean.valueOf(true);
                CategoryCouponReceiptSuperClubActivity.this.onClickItem(i2);
            }
        };
        this.sharingOptionsAtExit = r0;
    }

    public void configureLayout() {
        this.btnStores.setVisibility(8);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.r.trackScreen(getString(R.string.analytics_screen_superclub_comprobante_canje_2, new Object[]{this.mCategoryData.nombre, this.mCouponData.descripcion}));
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        this.sharingOptions.onRequestPermissionsResult(i, strArr, iArr);
    }

    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.menu) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(getResources().getString(R.string.ID3020_COMODINES_SUPER_CLUB_BTN_COMPARTIR));
            arrayList.add(getResources().getString(R.string.ID3021_COMODINES_SUPER_CLUB_BTN_DESCARGAR));
            IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("mPopupMenu", getResources().getString(R.string.ID3019_COMODINES_SUPER_CLUB_LBL_COMPROBANTE), null, arrayList, getString(R.string.ID3032_COMODINES_SUPER_CLUB_BTN_CANCELAR), null, null, null, arrayList);
            newInstance.setDialogListener(new IDialogListener() {
                public void onNegativeButton() {
                }

                public void onPositiveButton() {
                }

                public void onSimpleActionButton() {
                }

                public void onItemSelected(String str) {
                    if (str.equalsIgnoreCase(CategoryCouponReceiptSuperClubActivity.this.getResources().getString(R.string.ID3020_COMODINES_SUPER_CLUB_BTN_COMPARTIR))) {
                        CategoryCouponReceiptSuperClubActivity.this.sharingOptions.optionShareSelected();
                    } else if (str.equalsIgnoreCase(CategoryCouponReceiptSuperClubActivity.this.getResources().getString(R.string.ID3021_COMODINES_SUPER_CLUB_BTN_DESCARGAR))) {
                        CategoryCouponReceiptSuperClubActivity.this.sharingOptions.optionDownloadSelected();
                    }
                }
            });
            newInstance.setCancelable(true);
            newInstance.show(getSupportFragmentManager(), "mPopupMenu");
            this.r.trackScreen(getString(R.string.analytics_screen_superclub_compartir_comprobante_2, new Object[]{this.mCategoryData.nombre, this.mCouponData.descripcion}));
        }
    }
}
