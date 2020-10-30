package ar.com.santander.rio.mbanking.components.share;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.provider.MediaStore.Images.Media;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.CPaymentShareIntent;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.activities.ContratarSeguroAccidenteActivity;
import ar.com.santander.rio.mbanking.app.ui.forms.FormConstraintLayout;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListenerExtended;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListenerThreeOptions;
import ar.com.santander.rio.mbanking.components.share.dialogs.OptionsDialogShare;
import ar.com.santander.rio.mbanking.components.share.dialogs.OptionsDialogShare.EOptionsShareDialog;
import ar.com.santander.rio.mbanking.utils.UtilFile;
import java.io.File;
import java.util.ArrayList;

public abstract class OptionsToShareImpl implements IDialogListenerExtended, IDialogListenerThreeOptions, OptionsToShare {
    public static final String PERMISSION_DIALOG_TAG = "Permission";
    public static final int PERMISSION_WRITE_EXTERNAL_STORAGE_FLAG_DOWNLOAD = 20200;
    public static final int PERMISSION_WRITE_EXTERNAL_STORAGE_FLAG_SHARE = 10100;
    private IsbanDialogFragment a;
    private OptionsDialogShare b;
    private Context c;
    /* access modifiers changed from: private */
    public Fragment d;
    private FragmentManager e;
    private CPaymentShareIntent f;
    private Boolean g = Boolean.valueOf(false);
    private Integer h;
    private Integer i;
    /* access modifiers changed from: private */
    public AppCompatActivity j;

    public void onAbortShare() {
    }

    public void onFinishShare() {
    }

    public void onGetPolizaResult() {
    }

    public void onSimpleActionButton(String str) {
    }

    public OptionsToShareImpl(AppCompatActivity appCompatActivity, Context context, FragmentManager fragmentManager) {
        this.b = new OptionsDialogShare(appCompatActivity);
        this.c = context;
        this.j = appCompatActivity;
        this.e = fragmentManager;
        this.f = new CPaymentShareIntent(this.c.getPackageManager());
    }

    public OptionsToShareImpl(Fragment fragment, Context context, FragmentManager fragmentManager) {
        this.b = new OptionsDialogShare(context);
        this.c = context;
        this.d = fragment;
        this.e = fragmentManager;
        this.f = new CPaymentShareIntent(this.c.getPackageManager());
    }

    public OptionsToShareImpl(AppCompatActivity appCompatActivity, Context context, FragmentManager fragmentManager, Boolean bool) {
        this.b = new OptionsDialogShare(context);
        this.c = context;
        this.j = appCompatActivity;
        this.e = fragmentManager;
        this.f = new CPaymentShareIntent(this.c.getPackageManager());
        this.g = bool;
    }

    public OptionsToShareImpl(Fragment fragment, Context context, FragmentManager fragmentManager, Boolean bool) {
        this.b = new OptionsDialogShare(context);
        this.c = context;
        this.d = fragment;
        this.e = fragmentManager;
        this.f = new CPaymentShareIntent(this.c.getPackageManager());
        this.g = bool;
    }

    public void onRequestPermissionsResult(int i2, String[] strArr, int[] iArr) {
        if (i2 != 10100) {
            if (i2 != 20200) {
                return;
            }
        } else if (iArr.length <= 0 || iArr[0] != 0) {
            Toast.makeText(this.c, this.c.getString(R.string.write_external_permission_request_message), 0).show();
        } else {
            optionShareSelected();
        }
        if (iArr.length <= 0 || iArr[0] != 0) {
            Toast.makeText(this.c, this.c.getString(R.string.write_external_permission_request_message), 0).show();
        } else {
            a();
        }
    }

    public void optionDownloadSelected() {
        if (ContextCompat.checkSelfPermission(this.c, "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
            a();
        } else if (VERSION.SDK_INT < 23) {
        } else {
            if (this.j != null) {
                ActivityCompat.requestPermissions(this.j, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, PERMISSION_WRITE_EXTERNAL_STORAGE_FLAG_DOWNLOAD);
            } else if (this.d != null) {
                this.d.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, PERMISSION_WRITE_EXTERNAL_STORAGE_FLAG_DOWNLOAD);
            }
        }
    }

    private void a() {
        if (d() != null) {
            onDownloadShare();
        } else {
            onErrorDownload();
        }
    }

    public void optionShareSelected() {
        if (ContextCompat.checkSelfPermission(this.c, "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
            c();
        } else if (VERSION.SDK_INT < 23) {
        } else {
            if (this.j != null) {
                ActivityCompat.requestPermissions(this.j, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, PERMISSION_WRITE_EXTERNAL_STORAGE_FLAG_SHARE);
            } else if (this.d != null) {
                this.d.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, PERMISSION_WRITE_EXTERNAL_STORAGE_FLAG_SHARE);
            }
        }
    }

    public void optionCancelSelected() {
        onAbortShare();
    }

    public void show() {
        a("id_dialog_share_option", this.c.getString(R.string.TITLE_RECEIPT_SHARE), null);
        this.a.setOptions(this.b.getOptions());
        this.a.show(this.e, "id_dialog_share_option");
    }

    public void showWithCancel() {
        a("id_dialog_share_option", this.c.getString(R.string.TITLE_RECEIPT_SHARE), null);
        this.a.setOptions(this.b.getOptionsWithCancel());
        this.a.show(this.e, "id_dialog_share_option");
    }

    public void show(String str) {
        a("id_dialog_share_option", this.c.getString(R.string.TITLE_RECEIPT_SHARE), str);
        this.a.setOptions(this.b.getOptions());
        this.a.show(this.e, "id_dialog_share_option");
    }

    public void showAlert() {
        createDialogExitWithoutSave("id_alert_dialog_share_option", this.c.getString(R.string.MSG_USER000054_exit_without_save));
        this.a.show(this.e, "id_alert_dialog_share_option");
    }

    private void b() {
        a("id_dialog_share_option");
        this.a.setOptions(this.b.getOptionsAfterError());
        this.a.setId("id_dialog_share_option");
        this.a.show(this.e, "id_dialog_share_option");
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

    public void createDialogExitWithoutSave(String str, String str2) {
        this.a = IsbanDialogFragment.newInstance(str, str2, null, this.c.getString(R.string.IDXX_SHARE_OPTION_RECEIPT_SAVE), this.c.getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DONT_DOWNLOAD), this.c.getString(R.string.IDXX_SHARE_OPTION_RECEIPT_SEND));
        this.a.setDialogListenerThreeOptions(this);
    }

    private void a(String str, String str2, String str3) {
        this.a = IsbanDialogFragment.newInstance(str, str2, null, new ArrayList(), str3, null, null, null);
        this.a.setDialogListenerExtended(this);
    }

    private void a(String str) {
        this.a = IsbanDialogFragment.newInstance(str, null, this.c.getString(R.string.MSG_USER0000XX_DOWNLOAD_ERROR), null, null, this.c.getString(R.string.IDXX_SHARE_OPTION_RECEIPT_SEND), this.c.getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DONT_DOWNLOAD), null);
        this.a.setDialogListenerExtended(this);
    }

    public void onItemSelected(String str, String str2) {
        if ("id_dialog_share_option".toLowerCase().equals(str2.toLowerCase())) {
            a(this.b.getOptionShare(str, this.b.getMapOptions(this.c)));
        } else if ("id_alert_dialog_share_option".toLowerCase().equals(str2.toLowerCase())) {
            a(this.b.getOptionShare(str, this.b.getMapOptionsAlert(this.c)));
        }
    }

    public void onPositiveButton(String str) {
        if (!"id_dialog_share_option".toLowerCase().equals(str.toLowerCase())) {
            return;
        }
        if (ContextCompat.checkSelfPermission(this.c, "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
            optionShareSelected();
        } else if (VERSION.SDK_INT < 23) {
        } else {
            if (this.j != null) {
                ActivityCompat.requestPermissions(this.j, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, PERMISSION_WRITE_EXTERNAL_STORAGE_FLAG_SHARE);
            } else if (this.d != null) {
                this.d.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, PERMISSION_WRITE_EXTERNAL_STORAGE_FLAG_SHARE);
            }
        }
    }

    public void showRequestPermission(int i2) {
        if (VERSION.SDK_INT < 23) {
            return;
        }
        if (this.j != null) {
            ActivityCompat.requestPermissions(this.j, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, i2);
        } else if (this.d != null) {
            this.d.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, i2);
        }
    }

    public void showRequestPermissionExplation(final int i2) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(this.c.getResources().getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), this.c.getResources().getString(R.string.write_external_permission_request_message), null, this.c.getResources().getString(R.string.BTN_DIALOG_ACCEPT), null, null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
                if (VERSION.SDK_INT < 23) {
                    return;
                }
                if (OptionsToShareImpl.this.j != null) {
                    ActivityCompat.requestPermissions(OptionsToShareImpl.this.j, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, i2);
                } else if (OptionsToShareImpl.this.d != null) {
                    OptionsToShareImpl.this.d.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, i2);
                }
            }
        });
        newInstance.show(this.e, PERMISSION_DIALOG_TAG);
        if (VERSION.SDK_INT < 23) {
            return;
        }
        if (this.j != null) {
            ActivityCompat.requestPermissions(this.j, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, i2);
        } else if (this.d != null) {
            this.d.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, i2);
        }
    }

    public void onNegativeButton(String str) {
        if ("id_dialog_share_option".toLowerCase().equals(str.toLowerCase())) {
            optionCancelSelected();
        }
    }

    public void onOption1Button() {
        optionDownloadSelected();
    }

    public void onOption2Button() {
        optionCancelSelected();
    }

    public void onOption3Button() {
        optionShareSelected();
    }

    private void a(EOptionsShareDialog eOptionsShareDialog) {
        switch (eOptionsShareDialog) {
            case RECEIPT_DOWNLOAD:
                optionDownloadSelected();
                return;
            case RECEIPT_SHARE:
                optionShareSelected();
                return;
            default:
                optionCancelSelected();
                return;
        }
    }

    private void c() {
        File e2 = e();
        if (e2 == null) {
            onError(new Exception(this.c.getString(R.string.MSG_USER0000XX_SHARE_ERROR)));
            return;
        }
        Intent otherIntentImg = this.f.getOtherIntentImg(getSubjectReceiptToShare(), e2, this.c);
        if (otherIntentImg != null) {
            receiveIntentAppShare(otherIntentImg);
        } else {
            onError(new Exception(this.c.getString(R.string.IDXX_ERROR_SHARE_RECEIPT)));
        }
    }

    private File d() {
        try {
            Bitmap g2 = g();
            if (g2 != null) {
                return UtilFile.saveBitmap(f(), g2);
            }
            return null;
        } catch (Exception e2) {
            onError(e2);
            return null;
        }
    }

    private File e() {
        try {
            return UtilFile.saveTmpBitmap(f(), g(), this.c);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public void onError(Exception exc) {
        Toast.makeText(this.c, exc.getMessage(), 1).show();
    }

    public void onErrorDownload() {
        b();
    }

    public void onDownloadShare() {
        a(d(), this.c);
        Toast.makeText(this.c, this.c.getString(R.string.MSG_USER0000XX_DOWNLOAD_OK), 1).show();
    }

    private void a(File file, Context context) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_data", file.getAbsolutePath());
        contentValues.put("mime_type", "image/jpeg");
        context.getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, contentValues);
    }

    private void a(View view) {
        a(view, 4);
        b(view, 0);
    }

    private void b(View view) {
        a(view, 0);
        b(view, 8);
    }

    private void a(View view, int i2) {
        if (view != null) {
            View d2 = d(view);
            if (d2 != null) {
                d2.setVisibility(i2);
            }
        }
    }

    private void b(View view, int i2) {
        if (view != null) {
            View e2 = e(view);
            if (this.h == null && e2 != null) {
                this.h = Integer.valueOf(e2.getVisibility());
            }
            if (e2 != null) {
                e2.setVisibility(i2 != 8 ? this.h.intValue() : i2);
            }
            View f2 = f(view);
            if (this.i == null && f2 != null) {
                this.i = Integer.valueOf(f2.getVisibility());
            }
            if (f2 != null) {
                f2.setVisibility(i2 != 8 ? this.i.intValue() : i2);
            }
            View c2 = c(view);
            if (c2 != null) {
                c2.setVisibility(i2);
            }
        }
    }

    private View c(View view) {
        if (view != null) {
            return view.findViewById(R.id.linearLayoutLeyendaPreautorizacionSeguro);
        }
        return null;
    }

    private View d(View view) {
        if (view != null) {
            return view.findViewById(R.id.vgWrapperBottomReceiptShare);
        }
        return null;
    }

    private View e(View view) {
        if (view != null) {
            return view.findViewById(R.id.layoutLeyendaLink);
        }
        return null;
    }

    private View f(View view) {
        if (view != null) {
            return view.findViewById(R.id.credito_page4_layoutLeyendaLink);
        }
        return null;
    }

    private String f() {
        StringBuilder sb = new StringBuilder();
        sb.append(getFileName());
        sb.append(Constants.EXTENSION_FILE_STORAGE);
        return sb.toString();
    }

    private Bitmap g() {
        Bitmap bitmap;
        try {
            b(getViewToShare());
            Thread.sleep(50);
            if (!(getViewToShare() instanceof FormConstraintLayout) || !((FormConstraintLayout) getViewToShare()).isFooter()) {
                bitmap = UtilFile.getBitmapFromView(getViewToShare());
            } else {
                bitmap = UtilFile.getBitmapFromView(getViewToShare(), (ViewGroup) ((LayoutInflater) this.c.getSystemService("layout_inflater")).inflate(R.layout.generic_footer_receipt, null));
            }
            return bitmap;
        } catch (Exception e2) {
            Log.d("BitMap Exception: ", e2.getMessage());
            return null;
        } finally {
            a(getViewToShare());
        }
    }
}
