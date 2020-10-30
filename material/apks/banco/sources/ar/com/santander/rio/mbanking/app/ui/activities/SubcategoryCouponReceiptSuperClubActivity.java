package ar.com.santander.rio.mbanking.app.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.View;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.ui.constants.SuperClubConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.share.OptionsToShareImpl;
import ar.com.santander.rio.mbanking.components.share.intent.AllIntent;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CategoriaSuperClubBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LocalesAdheridosSuperClub;
import java.util.ArrayList;

public class SubcategoryCouponReceiptSuperClubActivity extends CouponReceiptSuperClubActivity {
    protected LocalesAdheridosSuperClub mStoresData;
    protected String mStoresName;

    public void initialize() {
        super.initialize();
        this.mSubcategoryData = (CategoriaSuperClubBean) getIntent().getParcelableExtra(SuperClubConstants.EXTRA_SUBCATEGORY);
        this.mStoresName = getIntent().getStringExtra(SuperClubConstants.EXTRA_STORES_NAME);
        this.mStoresData = (LocalesAdheridosSuperClub) getIntent().getParcelableExtra(SuperClubConstants.EXTRA_STORES);
        this.btnStores.setOnClickListener(this);
    }

    public void configureOptionsShare() {
        this.sharingOptions = new OptionsToShareImpl(this, getApplicationContext(), getSupportFragmentManager()) {
            public View getViewToShare() {
                return SubcategoryCouponReceiptSuperClubActivity.this.scrReceipt;
            }

            public void receiveIntentAppShare(Intent intent) {
                SubcategoryCouponReceiptSuperClubActivity.this.startActivityForResult(Intent.createChooser(intent, SubcategoryCouponReceiptSuperClubActivity.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)), 4);
            }

            public String getFileName() {
                return String.format(SubcategoryCouponReceiptSuperClubActivity.this.getString(R.string.ID3025_COMODINES_SUPER_CLUB_ARCHIVO_JPG_COMPROBANTE), new Object[]{SubcategoryCouponReceiptSuperClubActivity.this.mReceiptNumber});
            }

            public String getSubjectReceiptToShare() {
                return String.format(SubcategoryCouponReceiptSuperClubActivity.this.getString(R.string.ID3024_COMODINES_SUPER_CLUB_ASUNTO_MAIL_CANJE_SUBRUBRO), new Object[]{SubcategoryCouponReceiptSuperClubActivity.this.mCategoryData.nombre});
            }

            public void optionDownloadSelected() {
                super.optionDownloadSelected();
                SubcategoryCouponReceiptSuperClubActivity.this.receiptHasBeenDownloaded = Boolean.valueOf(true);
                SubcategoryCouponReceiptSuperClubActivity.this.r.trackEvent(SubcategoryCouponReceiptSuperClubActivity.this.getString(R.string.analytics_screen_category_comodines_superclub), SubcategoryCouponReceiptSuperClubActivity.this.getString(R.string.analytics_screen_action_descargar_comprobante), SubcategoryCouponReceiptSuperClubActivity.this.getString(R.string.analytics_screen_label_comprobante));
            }

            public void optionShareSelected() {
                super.optionShareSelected();
                SubcategoryCouponReceiptSuperClubActivity.this.receiptHasBeenDownloaded = Boolean.valueOf(true);
                SubcategoryCouponReceiptSuperClubActivity.this.r.trackEvent(SubcategoryCouponReceiptSuperClubActivity.this.getString(R.string.analytics_screen_category_comodines_superclub), SubcategoryCouponReceiptSuperClubActivity.this.getString(R.string.analytics_screen_action_compartir_comprobante), SubcategoryCouponReceiptSuperClubActivity.this.getString(R.string.analytics_screen_label_comprobante));
            }

            public void onAbortShare() {
                super.onAbortShare();
                SubcategoryCouponReceiptSuperClubActivity.this.receiptHasBeenDownloaded = Boolean.valueOf(true);
                SubcategoryCouponReceiptSuperClubActivity.this.onBackPressed();
            }
        };
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        this.sharingOptions.onRequestPermissionsResult(i, strArr, iArr);
    }

    public void configureOptionsShare(int i) {
        final int i2 = i;
        AnonymousClass2 r0 = new OptionsToShareImpl(this, getApplicationContext(), getSupportFragmentManager()) {
            public View getViewToShare() {
                return SubcategoryCouponReceiptSuperClubActivity.this.scrReceipt;
            }

            public void receiveIntentAppShare(Intent intent) {
                SubcategoryCouponReceiptSuperClubActivity.this.startActivityForResult(Intent.createChooser(intent, SubcategoryCouponReceiptSuperClubActivity.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)), 4);
            }

            public String getFileName() {
                return String.format(SubcategoryCouponReceiptSuperClubActivity.this.getString(R.string.ID3025_COMODINES_SUPER_CLUB_ARCHIVO_JPG_COMPROBANTE), new Object[]{SubcategoryCouponReceiptSuperClubActivity.this.mReceiptNumber});
            }

            public String getSubjectReceiptToShare() {
                return String.format(SubcategoryCouponReceiptSuperClubActivity.this.getString(R.string.ID3024_COMODINES_SUPER_CLUB_ASUNTO_MAIL_CANJE_SUBRUBRO), new Object[]{SubcategoryCouponReceiptSuperClubActivity.this.mSubcategoryData.nombre});
            }

            public void optionDownloadSelected() {
                super.optionDownloadSelected();
                SubcategoryCouponReceiptSuperClubActivity.this.receiptHasBeenDownloaded = Boolean.valueOf(true);
            }

            public void optionShareSelected() {
                super.optionShareSelected();
                SubcategoryCouponReceiptSuperClubActivity.this.receiptHasBeenDownloaded = Boolean.valueOf(true);
            }

            public void onAbortShare() {
                super.onAbortShare();
                SubcategoryCouponReceiptSuperClubActivity.this.receiptHasBeenDownloaded = Boolean.valueOf(true);
                SubcategoryCouponReceiptSuperClubActivity.this.onClickItem(i2);
            }
        };
        this.sharingOptionsAtExit = r0;
    }

    public void configureLayout() {
        this.btnStores.setVisibility(0);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.r.trackScreen(getString(R.string.analytics_screen_superclub_comprobante_canje, new Object[]{this.mCategoryData.nombre, this.mSubcategoryData.nombre, this.mCouponData.descripcion}));
    }

    public void onClick(View view) {
        super.onClick(view);
        int id2 = view.getId();
        if (id2 == R.id.F20_03_LBL_STORES) {
            if (this.mStoresData != null) {
                Intent intent = new Intent(this, StoresSuperClubActivity.class);
                intent.putExtra(SuperClubConstants.EXTRA_STORES_NAME, this.mStoresName);
                intent.putExtra(SuperClubConstants.EXTRA_STORES, this.mStoresData);
                intent.putExtra(SuperClubConstants.EXTRA_CATEGORY, this.mCategoryData);
                intent.putExtra(SuperClubConstants.EXTRA_SUBCATEGORY, this.mSubcategoryData);
                intent.putExtra(SuperClubConstants.EXTRA_COMODIN, this.mCouponData);
                startActivity(intent);
            } else {
                IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.USER00004_TITLE), Html.fromHtml(getString(R.string.USER00004_BODY)).toString(), null, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
                newInstance.setDialogListener(new IDialogListener() {
                    public void onItemSelected(String str) {
                    }

                    public void onNegativeButton() {
                    }

                    public void onPositiveButton() {
                    }

                    public void onSimpleActionButton() {
                    }
                });
                newInstance.show(getSupportFragmentManager(), "Dialog");
            }
            this.r.trackScreen(getString(R.string.analytics_screen_superclub_compartir_comprobante, new Object[]{this.mCategoryData.nombre, this.mSubcategoryData.nombre, this.mCouponData.descripcion}));
        } else if (id2 == R.id.menu) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(getResources().getString(R.string.ID3020_COMODINES_SUPER_CLUB_BTN_COMPARTIR));
            arrayList.add(getResources().getString(R.string.ID3021_COMODINES_SUPER_CLUB_BTN_DESCARGAR));
            arrayList.add(getResources().getString(R.string.ID3022_COMODINES_SUPER_CLUB_BTN_COMPARTIR_LOCALES));
            IsbanDialogFragment newInstance2 = IsbanDialogFragment.newInstance("mPopupMenu", getResources().getString(R.string.ID3019_COMODINES_SUPER_CLUB_LBL_COMPROBANTE), null, arrayList, getString(R.string.ID3032_COMODINES_SUPER_CLUB_BTN_CANCELAR), null, null, null, arrayList);
            newInstance2.setDialogListener(new IDialogListener() {
                public void onNegativeButton() {
                }

                public void onPositiveButton() {
                }

                public void onSimpleActionButton() {
                }

                public void onItemSelected(String str) {
                    if (str.equalsIgnoreCase(SubcategoryCouponReceiptSuperClubActivity.this.getResources().getString(R.string.ID3020_COMODINES_SUPER_CLUB_BTN_COMPARTIR))) {
                        SubcategoryCouponReceiptSuperClubActivity.this.sharingOptions.optionShareSelected();
                    } else if (str.equalsIgnoreCase(SubcategoryCouponReceiptSuperClubActivity.this.getResources().getString(R.string.ID3021_COMODINES_SUPER_CLUB_BTN_DESCARGAR))) {
                        SubcategoryCouponReceiptSuperClubActivity.this.sharingOptions.optionDownloadSelected();
                    } else if (str.equalsIgnoreCase(SubcategoryCouponReceiptSuperClubActivity.this.getResources().getString(R.string.ID3022_COMODINES_SUPER_CLUB_BTN_COMPARTIR_LOCALES))) {
                        SubcategoryCouponReceiptSuperClubActivity.this.r.trackEvent(SubcategoryCouponReceiptSuperClubActivity.this.getString(R.string.analytics_screen_category_comodines_superclub), SubcategoryCouponReceiptSuperClubActivity.this.getString(R.string.analytics_screen_action_compartir_locales_adheridos), SubcategoryCouponReceiptSuperClubActivity.this.getString(R.string.analytics_screen_label_comprobante));
                        if (SubcategoryCouponReceiptSuperClubActivity.this.mStoresData != null) {
                            Bundle bundle = new Bundle();
                            bundle.putString("android.intent.extra.TEXT", Html.fromHtml(StoresSuperClubActivity.getBodyStoresShare(SubcategoryCouponReceiptSuperClubActivity.this.getApplicationContext(), SubcategoryCouponReceiptSuperClubActivity.this.mStoresName, SubcategoryCouponReceiptSuperClubActivity.this.mStoresData)).toString());
                            bundle.putString("android.intent.extra.SUBJECT", Html.fromHtml(StoresSuperClubActivity.getTitleStoresShare(SubcategoryCouponReceiptSuperClubActivity.this.getApplicationContext(), SubcategoryCouponReceiptSuperClubActivity.this.mStoresName)).toString());
                            AllIntent allIntent = new AllIntent();
                            allIntent.setExtras(bundle);
                            SubcategoryCouponReceiptSuperClubActivity.this.startActivityForResult(Intent.createChooser(allIntent.getAllIntents(), SubcategoryCouponReceiptSuperClubActivity.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)), 4);
                            return;
                        }
                        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(SubcategoryCouponReceiptSuperClubActivity.this.getString(R.string.USER00004_TITLE), Html.fromHtml(SubcategoryCouponReceiptSuperClubActivity.this.getString(R.string.USER00004_BODY)).toString(), null, null, SubcategoryCouponReceiptSuperClubActivity.this.getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
                        newInstance.setDialogListener(new IDialogListener() {
                            public void onItemSelected(String str) {
                            }

                            public void onNegativeButton() {
                            }

                            public void onPositiveButton() {
                            }

                            public void onSimpleActionButton() {
                            }
                        });
                        newInstance.show(SubcategoryCouponReceiptSuperClubActivity.this.getSupportFragmentManager(), "Dialog");
                    }
                }
            });
            newInstance2.setCancelable(true);
            newInstance2.show(getSupportFragmentManager(), "mPopupMenu");
        }
    }
}
