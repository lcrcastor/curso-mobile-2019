package ar.com.santander.rio.mbanking.components.share;

import android.content.Intent;
import android.view.View;

public interface OptionsToShare {
    String getFileName();

    String getSubjectReceiptToShare();

    View getViewToShare();

    void onAbortShare();

    void onDownloadShare();

    void onError(Exception exc);

    void onErrorDownload();

    void onFinishShare();

    void onGetPolizaResult();

    void onRequestPermissionsResult(int i, String[] strArr, int[] iArr);

    void optionCancelSelected();

    void optionDownloadSelected();

    void optionShareSelected();

    void receiveIntentAppShare(Intent intent);

    void show();

    void show(String str);

    void showAlert();

    void showWithCancel();
}
