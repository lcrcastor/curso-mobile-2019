package ar.com.santander.rio.mbanking.components.share.dialogs;

import android.content.Context;
import ar.com.santander.rio.mbanking.R;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class OptionsDialogShare {
    private Context a;

    public enum EOptionsShareDialog {
        RECEIPT_SHARE,
        RECEIPT_DOWNLOAD,
        CANCEL_SHARE
    }

    public OptionsDialogShare(Context context) {
        this.a = context;
    }

    public ArrayList<String> getOptions() {
        return getArrOptions(getMapOptions(this.a));
    }

    public ArrayList<String> getOptionsWithCancel() {
        return getArrOptions(getMapOptionsWithCancel(this.a));
    }

    public ArrayList<String> getOptionsAfterError() {
        return getArrOptions(getMapOptionsAfterError(this.a));
    }

    public ArrayList<String> getOptionsAlert() {
        return getArrOptions(getMapOptionsAlert(this.a));
    }

    public ArrayList<String> getArrOptions(Map<EOptionsShareDialog, String> map) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (EOptionsShareDialog eOptionsShareDialog : map.keySet()) {
            arrayList.add(map.get(eOptionsShareDialog));
        }
        return arrayList;
    }

    public EOptionsShareDialog getOptionShare(String str, Map<EOptionsShareDialog, String> map) {
        for (EOptionsShareDialog eOptionsShareDialog : map.keySet()) {
            if (((String) map.get(eOptionsShareDialog)).toString().toLowerCase().equals(str.toLowerCase())) {
                return eOptionsShareDialog;
            }
        }
        return EOptionsShareDialog.CANCEL_SHARE;
    }

    public Map<EOptionsShareDialog, String> getMapOptions(Context context) {
        TreeMap treeMap = new TreeMap();
        treeMap.put(EOptionsShareDialog.RECEIPT_SHARE, context.getString(R.string.IDXX_SHARE_OPTION_RECEIPT));
        treeMap.put(EOptionsShareDialog.RECEIPT_DOWNLOAD, context.getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DOWNLOAD));
        return treeMap;
    }

    public Map<EOptionsShareDialog, String> getMapOptionsWithCancel(Context context) {
        TreeMap treeMap = new TreeMap();
        treeMap.put(EOptionsShareDialog.RECEIPT_SHARE, context.getString(R.string.IDXX_SHARE_OPTION_RECEIPT));
        treeMap.put(EOptionsShareDialog.RECEIPT_DOWNLOAD, context.getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DOWNLOAD));
        treeMap.put(EOptionsShareDialog.CANCEL_SHARE, context.getString(R.string.IDXX_SHARE_OPTION_RECEIPT_CANCEL));
        return treeMap;
    }

    public Map<EOptionsShareDialog, String> getMapOptionsAfterError(Context context) {
        TreeMap treeMap = new TreeMap();
        treeMap.put(EOptionsShareDialog.RECEIPT_SHARE, context.getString(R.string.IDXX_SHARE_OPTION_RECEIPT_SEND));
        treeMap.put(EOptionsShareDialog.CANCEL_SHARE, context.getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DONT_DOWNLOAD));
        return treeMap;
    }

    public Map<EOptionsShareDialog, String> getMapOptionsAlert(Context context) {
        TreeMap treeMap = new TreeMap();
        treeMap.put(EOptionsShareDialog.RECEIPT_SHARE, context.getString(R.string.IDXX_SHARE_OPTION_RECEIPT_SEND));
        treeMap.put(EOptionsShareDialog.RECEIPT_DOWNLOAD, context.getString(R.string.IDXX_SHARE_OPTION_RECEIPT_SAVE));
        treeMap.put(EOptionsShareDialog.CANCEL_SHARE, context.getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DONT_DOWNLOAD));
        return treeMap;
    }
}
