package ar.com.santander.rio.mbanking.app.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.LayoutParams;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.ui.activities.ContratarSeguroAccidenteActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.share.OptionsToShareImpl;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;

public class ITRSABaseFragment extends BaseFragment implements ShareReceiptInterface, ShareReceiptPolizaListener {
    public static final String SARED_RECIPT_EXPLATION_CONSTANT = "SARED_RECIPT_EXPLATION";
    /* access modifiers changed from: private */
    public ShareReceiptListener a = null;
    /* access modifiers changed from: private */
    public ShareReceiptPolizaListener b = null;
    /* access modifiers changed from: private */
    public View c = null;
    /* access modifiers changed from: private */
    public String d = "";
    /* access modifiers changed from: private */
    public String e = "";
    private boolean f = false;
    /* access modifiers changed from: private */
    public FragmentActivity g;
    protected boolean receiptHasBeenDownloaded = false;

    public void onAttach(Context context) {
        super.onAttach(context);
        this.g = getActivity();
    }

    public void configureShareReceipt(View view, String str, String str2) {
        this.d = str;
        this.e = str2;
        this.c = view;
        createActionBarSharedreceipt();
    }

    public void configureShareReceiptPoliza(View view, String str, String str2) {
        this.d = str;
        this.e = str2;
        this.c = view;
        this.f = true;
        createActionBarSharedreceipt();
    }

    public void setShareReceiptListener(ShareReceiptListener shareReceiptListener) {
        this.a = shareReceiptListener;
    }

    public void setShareReceiptPolizaListener(ShareReceiptPolizaListener shareReceiptPolizaListener) {
        this.b = shareReceiptPolizaListener;
    }

    public boolean canExit() {
        if (!this.receiptHasBeenDownloaded) {
            shareReceiptBackAction();
        } else {
            this.a.onClikVolver();
        }
        return this.receiptHasBeenDownloaded;
    }

    public boolean canExitMenu() {
        if (!this.receiptHasBeenDownloaded) {
            shareReceiptMenuAction();
        }
        return this.receiptHasBeenDownloaded;
    }

    public boolean shareReceiptMenuAction() {
        y();
        if (!this.receiptHasBeenDownloaded) {
            new OptionsToShareImpl((AppCompatActivity) getActivity(), getActivity(), getFragmentManager()) {
                public View getViewToShare() {
                    return ITRSABaseFragment.this.c;
                }

                public void receiveIntentAppShare(Intent intent) {
                    ITRSABaseFragment.this.startActivity(Intent.createChooser(intent, ITRSABaseFragment.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)));
                }

                public String getFileName() {
                    return ITRSABaseFragment.this.e.concat("-").concat(ITRSABaseFragment.this.d);
                }

                public String getSubjectReceiptToShare() {
                    return ITRSABaseFragment.this.e;
                }

                public void optionDownloadSelected() {
                    super.optionDownloadSelected();
                    ITRSABaseFragment.this.receiptHasBeenDownloaded = true;
                }

                public void optionCancelSelected() {
                    super.optionCancelSelected();
                    ITRSABaseFragment.this.receiptHasBeenDownloaded = true;
                }

                public void optionShareSelected() {
                    super.optionShareSelected();
                    ITRSABaseFragment.this.receiptHasBeenDownloaded = true;
                }
            }.showAlert();
        }
        return this.receiptHasBeenDownloaded;
    }

    public boolean shareReceiptBackAction() {
        y();
        if (!this.receiptHasBeenDownloaded) {
            new OptionsToShareImpl((AppCompatActivity) getActivity(), getActivity(), getFragmentManager()) {
                public View getViewToShare() {
                    return ITRSABaseFragment.this.c;
                }

                public void receiveIntentAppShare(Intent intent) {
                    ITRSABaseFragment.this.startActivity(Intent.createChooser(intent, ITRSABaseFragment.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)));
                }

                public String getFileName() {
                    return ITRSABaseFragment.this.e.concat("-").concat(ITRSABaseFragment.this.d);
                }

                public String getSubjectReceiptToShare() {
                    return ITRSABaseFragment.this.e;
                }

                public void optionDownloadSelected() {
                    if (ContextCompat.checkSelfPermission(ITRSABaseFragment.this.g, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                        showRequestPermissionExplation(OptionsToShareImpl.PERMISSION_WRITE_EXTERNAL_STORAGE_FLAG_DOWNLOAD);
                        return;
                    }
                    super.optionDownloadSelected();
                    ITRSABaseFragment.this.receiptHasBeenDownloaded = true;
                }

                public void optionCancelSelected() {
                    super.optionCancelSelected();
                    ITRSABaseFragment.this.receiptHasBeenDownloaded = true;
                    ITRSABaseFragment.this.a.onClikVolver();
                }

                public void optionShareSelected() {
                    if (ContextCompat.checkSelfPermission(ITRSABaseFragment.this.g, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                        showRequestPermissionExplation(OptionsToShareImpl.PERMISSION_WRITE_EXTERNAL_STORAGE_FLAG_DOWNLOAD);
                        return;
                    }
                    super.optionShareSelected();
                    ITRSABaseFragment.this.receiptHasBeenDownloaded = true;
                }
            }.showAlert();
        }
        return this.receiptHasBeenDownloaded;
    }

    public void shareReceiptExplation() {
        y();
        if (this.f) {
            b(this.g, this.d, this.c, this.e).show(getFragmentManager(), SARED_RECIPT_EXPLATION_CONSTANT);
        } else {
            a(this.g, this.d, this.c, this.e).show(getFragmentManager(), SARED_RECIPT_EXPLATION_CONSTANT);
        }
    }

    private IsbanDialogFragment a(final Activity activity, String str, View view, String str2) {
        final AnonymousClass3 r12 = new OptionsToShareImpl((AppCompatActivity) getActivity(), getActivity(), getFragmentManager()) {
            public View getViewToShare() {
                return ITRSABaseFragment.this.c;
            }

            public void receiveIntentAppShare(Intent intent) {
                ITRSABaseFragment.this.startActivity(Intent.createChooser(intent, ITRSABaseFragment.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)));
            }

            public String getFileName() {
                return ITRSABaseFragment.this.e.concat("-").concat(ITRSABaseFragment.this.d);
            }

            public String getSubjectReceiptToShare() {
                return ITRSABaseFragment.this.e;
            }

            public void optionDownloadSelected() {
                super.optionDownloadSelected();
                ITRSABaseFragment.this.receiptHasBeenDownloaded = true;
            }

            public void optionCancelSelected() {
                super.optionCancelSelected();
                ITRSABaseFragment.this.receiptHasBeenDownloaded = true;
            }

            public void optionShareSelected() {
                super.optionShareSelected();
                ITRSABaseFragment.this.receiptHasBeenDownloaded = true;
            }
        };
        ArrayList arrayList = new ArrayList();
        arrayList.add(activity.getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT));
        arrayList.add(activity.getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DOWNLOAD));
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("mPopupMenu", activity.getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_TITLE), null, arrayList, activity.getString(R.string.ID_4109_SEGUROS_LBL_CANCELAR), null, null, null, arrayList);
        newInstance.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                if (str.equalsIgnoreCase(activity.getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT))) {
                    r12.optionShareSelected();
                } else if (str.equalsIgnoreCase(activity.getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DOWNLOAD))) {
                    r12.optionDownloadSelected();
                }
            }
        });
        newInstance.setCancelable(true);
        return newInstance;
    }

    public void showRequestPermissionExplation(final int i) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getString(R.string.write_external_permission_request_message), null, getString(R.string.BTN_DIALOG_ACCEPT), null, null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
                ITRSABaseFragment.this.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, i);
            }
        });
        newInstance.show(getFragmentManager(), OptionsToShareImpl.PERMISSION_DIALOG_TAG);
    }

    /* access modifiers changed from: protected */
    public void createActionBarSharedreceipt() {
        BaseActivity baseActivity = (BaseActivity) getActivity();
        ActionBar supportActionBar = baseActivity != null ? baseActivity.getSupportActionBar() : null;
        if (supportActionBar != null) {
            supportActionBar.setElevation(BitmapDescriptorFactory.HUE_RED);
            supportActionBar.setDisplayOptions(16);
            baseActivity.setActionBarType(ActionBarType.SHARE);
            View customView = supportActionBar.getCustomView();
            if (customView != null) {
                customView.findViewById(R.id.toggle).setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        ITRSABaseFragment.this.switchDrawer();
                    }
                });
                customView.findViewById(R.id.share).setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        ITRSABaseFragment.this.shareReceiptExplation();
                    }
                });
                supportActionBar.setCustomView(customView, new LayoutParams(-1, -1));
            }
        }
    }

    /* access modifiers changed from: protected */
    public void createActionBarNotShared() {
        BaseActivity baseActivity = (BaseActivity) getActivity();
        ActionBar supportActionBar = baseActivity != null ? baseActivity.getSupportActionBar() : null;
        if (supportActionBar != null) {
            supportActionBar.setElevation(BitmapDescriptorFactory.HUE_RED);
            supportActionBar.setDisplayOptions(16);
            baseActivity.setActionBarType(ActionBarType.MAIN_WITH_MENU);
            ((SantanderRioMainActivity) getActivity()).lockMenu(false);
            View customView = supportActionBar.getCustomView();
            if (customView != null) {
                customView.findViewById(R.id.menu).setVisibility(8);
                customView.findViewById(R.id.toggle).setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        ITRSABaseFragment.this.switchDrawer();
                    }
                });
                supportActionBar.setCustomView(customView, new LayoutParams(-1, -1));
            }
        }
    }

    private void y() {
        if (!(getActivity() instanceof ShareReceiptListener)) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.g.toString());
            sb.append(" must implement ShareReceiptListener");
            throw new RuntimeException(sb.toString());
        } else if (this.c == null || TextUtils.isEmpty(this.e) || TextUtils.isEmpty(this.d)) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.g.toString());
            sb2.append("Bad configuration of SharedReceipt");
            throw new RuntimeException(sb2.toString());
        } else if (!this.f) {
        } else {
            if (this.c == null || TextUtils.isEmpty(this.e) || TextUtils.isEmpty(this.d) || this.b == null) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(this.g.toString());
                sb3.append("Bad configuration of SharedReceipt Poliza");
                throw new RuntimeException(sb3.toString());
            }
        }
    }

    private IsbanDialogFragment b(Activity activity, String str, View view, String str2) {
        final Activity activity2 = activity;
        final AnonymousClass9 r0 = new OptionsToShareImpl((AppCompatActivity) activity, activity, getFragmentManager(), Boolean.valueOf(true)) {
            public View getViewToShare() {
                return ITRSABaseFragment.this.c;
            }

            public void receiveIntentAppShare(Intent intent) {
                activity2.startActivity(Intent.createChooser(intent, activity2.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)));
            }

            public String getFileName() {
                return ITRSABaseFragment.this.e.concat("-").concat(ITRSABaseFragment.this.d);
            }

            public String getSubjectReceiptToShare() {
                return ITRSABaseFragment.this.e.concat("-").concat(ITRSABaseFragment.this.d);
            }

            public void optionDownloadSelected() {
                super.optionDownloadSelected();
                ITRSABaseFragment.this.receiptHasBeenDownloaded = true;
            }

            public void optionCancelSelected() {
                super.optionCancelSelected();
                ITRSABaseFragment.this.receiptHasBeenDownloaded = true;
            }

            public void optionShareSelected() {
                super.optionShareSelected();
                ITRSABaseFragment.this.receiptHasBeenDownloaded = true;
            }

            public void onGetPolizaResult() {
                if (ContextCompat.checkSelfPermission(ITRSABaseFragment.this.g, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                    showRequestPermission(1);
                } else if (ITRSABaseFragment.this.b != null) {
                    ITRSABaseFragment.this.b.onClikPoliza();
                }
            }
        };
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.g.getResources().getString(R.string.ID_4660_CREDITOS_BTN_VER_POLIZA));
        arrayList.add(this.g.getResources().getString(R.string.ID_4660_CREDITOS_BTN_COMPARTIR_COMPROBANTE));
        arrayList.add(this.g.getResources().getString(R.string.ID_4659_CREDITOS_BTN_DESCARGAR_COMPROBANTE));
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("mPopupMenuPoliza", this.g.getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_TITLE), null, arrayList, this.g.getString(R.string.ID_4109_SEGUROS_LBL_CANCELAR), null, null, null, arrayList);
        newInstance.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                if (str.equalsIgnoreCase(ITRSABaseFragment.this.g.getResources().getString(R.string.ID_4660_CREDITOS_BTN_VER_POLIZA))) {
                    r0.onGetPolizaResult();
                } else if (str.equalsIgnoreCase(ITRSABaseFragment.this.g.getResources().getString(R.string.ID_4660_CREDITOS_BTN_COMPARTIR_COMPROBANTE))) {
                    r0.optionShareSelected();
                } else if (str.equalsIgnoreCase(ITRSABaseFragment.this.g.getResources().getString(R.string.ID_4659_CREDITOS_BTN_DESCARGAR_COMPROBANTE))) {
                    r0.optionDownloadSelected();
                }
            }
        });
        newInstance.setCancelable(true);
        return newInstance;
    }

    public void showNoPdfApplicationPopUp(Context context) {
        final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(null, context.getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), context.getString(R.string.ID_XXXX_SEGUROS_MSG_POPUP_NOAPP), context.getString(R.string.ID1_ALERT_BTN_ACCEPT), null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
                newInstance.dismiss();
            }
        });
        newInstance.show(((ContratarSeguroAccidenteActivity) context).getSupportFragmentManager(), "popUp");
    }

    public void onClikPoliza() {
        Log.d("Paso", "Paso");
    }
}
