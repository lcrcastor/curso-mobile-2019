package ar.com.santander.rio.mbanking.app.module.funds;

import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.widget.Toast;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.share.OptionsToShareImpl;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.InformacionFondoBean;
import com.squareup.otto.Bus;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class InformacionFondoPresenter extends BasePresenter<InformacionFondoView> {
    /* access modifiers changed from: private */
    public Context a;

    public InformacionFondoPresenter(Bus bus, IDataManager iDataManager, Context context) {
        super(bus, iDataManager);
        this.a = context;
    }

    public void onCreatePage(InformacionFondoBean informacionFondoBean) {
        if (ContextCompat.checkSelfPermission(this.a, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            ActivityCompat.requestPermissions((Activity) this.a, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
        }
        ((InformacionFondoView) getBaseView()).setInformacionFondoView(informacionFondoBean);
    }

    public void showOptionsDialog(FragmentManager fragmentManager, final InformacionFondoBean informacionFondoBean) {
        ArrayList arrayList = new ArrayList();
        if (!TextUtils.isEmpty(informacionFondoBean.getReglamento())) {
            arrayList.add(this.a.getString(R.string.F24_11_ACTIONSHEET_DESCARGAR_REGLAMENTO));
        }
        if (!TextUtils.isEmpty(informacionFondoBean.getCartera())) {
            arrayList.add(this.a.getString(R.string.F24_11_ACTIONSHEET_DESCARGAR_COMPOSICION));
        }
        final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(null, null, arrayList, this.a.getString(R.string.F24_00_ACTIONSHEET_CANCELAR), null, null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onItemSelected(String str) {
                if (str.equals(InformacionFondoPresenter.this.a.getString(R.string.F24_11_ACTIONSHEET_DESCARGAR_REGLAMENTO))) {
                    ((InformacionFondoView) InformacionFondoPresenter.this.getBaseView()).getAnalyticsManager().trackEvent(InformacionFondoPresenter.this.a.getString(R.string.analytics_event_category_fondos), InformacionFondoPresenter.this.a.getString(R.string.analytics_event_action_descargar_reglamento_gestion), InformacionFondoPresenter.this.a.getString(R.string.analytics_event_label_descargar_reglamento_gestion));
                    InformacionFondoPresenter informacionFondoPresenter = InformacionFondoPresenter.this;
                    String reglamento = informacionFondoBean.getReglamento();
                    StringBuilder sb = new StringBuilder();
                    sb.append("Reglamento - ");
                    sb.append(Html.fromHtml(informacionFondoBean.getNombre()));
                    sb.append(".pdf");
                    informacionFondoPresenter.downloadFiles(reglamento, sb.toString());
                } else if (str.equals(InformacionFondoPresenter.this.a.getString(R.string.F24_11_ACTIONSHEET_DESCARGAR_COMPOSICION))) {
                    ((InformacionFondoView) InformacionFondoPresenter.this.getBaseView()).getAnalyticsManager().trackEvent(InformacionFondoPresenter.this.a.getString(R.string.analytics_event_category_fondos), InformacionFondoPresenter.this.a.getString(R.string.analytics_event_action_descargar_reglamento_gestion), InformacionFondoPresenter.this.a.getString(R.string.analytics_event_label_descargar_composicion_cartera));
                    InformacionFondoPresenter informacionFondoPresenter2 = InformacionFondoPresenter.this;
                    String cartera = informacionFondoBean.getCartera();
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Cartera - ");
                    sb2.append(Html.fromHtml(informacionFondoBean.getNombre()));
                    sb2.append(".pdf");
                    informacionFondoPresenter2.downloadFiles(cartera, sb2.toString());
                }
            }

            public void onSimpleActionButton() {
                ((InformacionFondoView) InformacionFondoPresenter.this.getBaseView()).getAnalyticsManager().trackEvent(((InformacionFondoView) InformacionFondoPresenter.this.getBaseView()).getContext().getString(R.string.analytics_event_category_fondos), ((InformacionFondoView) InformacionFondoPresenter.this.getBaseView()).getContext().getString(R.string.analytics_event_action_cancelar), ((InformacionFondoView) InformacionFondoPresenter.this.getBaseView()).getContext().getString(R.string.analytics_event_label_cancelar_descarga_fondos));
                newInstance.dismiss();
            }
        });
        newInstance.show(fragmentManager, "infoFondoMenu");
    }

    public void downloadFiles(final String str, final String str2) {
        if (ContextCompat.checkSelfPermission(this.a, "android.permission.WRITE_EXTERNAL_STORAGE") == 0 || VERSION.SDK_INT < 23) {
            ((InformacionFondoView) getBaseView()).showProgressIndicator("downloadFile");
            new Thread(new Runnable() {
                public void run() {
                    Boolean.valueOf(false);
                    try {
                        URL url = new URL(str);
                        URLConnection openConnection = url.openConnection();
                        openConnection.connect();
                        openConnection.getContentLength();
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(url.openStream(), 8192);
                        String str = str2;
                        StringBuilder sb = new StringBuilder();
                        sb.append(Environment.getExternalStorageDirectory());
                        sb.append(File.separator);
                        sb.append("santander/");
                        String sb2 = sb.toString();
                        File file = new File(sb2);
                        if (!file.exists()) {
                            file.mkdirs();
                        }
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(sb2);
                        sb3.append(str);
                        FileOutputStream fileOutputStream = new FileOutputStream(sb3.toString());
                        byte[] bArr = new byte[1024];
                        while (true) {
                            int read = bufferedInputStream.read(bArr);
                            if (read == -1) {
                                break;
                            }
                            fileOutputStream.write(bArr, 0, read);
                        }
                        Boolean valueOf = Boolean.valueOf(true);
                        ((InformacionFondoView) InformacionFondoPresenter.this.getBaseView()).dismissProgressIndicator();
                        if (valueOf.booleanValue()) {
                            new Handler(InformacionFondoPresenter.this.a.getMainLooper()).post(new Runnable() {
                                public void run() {
                                    ((InformacionFondoView) InformacionFondoPresenter.this.getBaseView()).dismissProgressIndicator();
                                    Toast.makeText(InformacionFondoPresenter.this.a, InformacionFondoPresenter.this.a.getString(R.string.MSG_USER0000XX_FILE_DOWNLOAD_OK), 1).show();
                                }
                            });
                        }
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        bufferedInputStream.close();
                    } catch (Exception unused) {
                        new Handler(InformacionFondoPresenter.this.a.getMainLooper()).post(new Runnable() {
                            public void run() {
                                ((InformacionFondoView) InformacionFondoPresenter.this.getBaseView()).dismissProgressIndicator();
                                ((InformacionFondoView) InformacionFondoPresenter.this.getBaseView()).popUpErrorDownload();
                            }
                        });
                    }
                }
            }).start();
            return;
        }
        ((Activity) this.a).requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, OptionsToShareImpl.PERMISSION_WRITE_EXTERNAL_STORAGE_FLAG_SHARE);
    }
}
