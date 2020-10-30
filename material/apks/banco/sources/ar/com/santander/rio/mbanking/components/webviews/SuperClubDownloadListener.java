package ar.com.santander.rio.mbanking.components.webviews;

import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.webkit.DownloadListener;
import android.widget.Toast;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.share.OptionsToShareImpl;

public class SuperClubDownloadListener implements DownloadListener {
    private final FragmentManager a;
    /* access modifiers changed from: private */
    public Fragment b;
    private Context c;

    public SuperClubDownloadListener(Fragment fragment, Context context, FragmentManager fragmentManager) {
        this.b = fragment;
        this.c = context;
        this.a = fragmentManager;
    }

    public void onDownloadStart(String str, String str2, String str3, String str4, long j) {
        if (ContextCompat.checkSelfPermission(this.c, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            showRequestPermissionExplation(OptionsToShareImpl.PERMISSION_WRITE_EXTERNAL_STORAGE_FLAG_SHARE);
            return;
        }
        String[] split = str.split("/");
        try {
            Request request = new Request(Uri.parse(str));
            request.setAllowedNetworkTypes(3);
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(1);
            request.setTitle("Descargando comprobante...");
            request.setVisibleInDownloadsUi(true);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, split[split.length - 1]);
            ((DownloadManager) this.c.getSystemService("download")).enqueue(request);
            Toast.makeText(this.c, "Descargando comprobante...", 1).show();
        } catch (Exception unused) {
            Toast.makeText(this.c, "Se produjo un error en la descarga", 1).show();
        }
    }

    public void showRequestPermissionExplation(final int i) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(this.c.getResources().getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), this.c.getResources().getString(R.string.write_external_permission_request_message), null, this.c.getResources().getString(R.string.BTN_DIALOG_ACCEPT), null, null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
                if (VERSION.SDK_INT >= 23) {
                    SuperClubDownloadListener.this.b.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, i);
                }
            }
        });
        newInstance.show(this.a, OptionsToShareImpl.PERMISSION_DIALOG_TAG);
    }
}
