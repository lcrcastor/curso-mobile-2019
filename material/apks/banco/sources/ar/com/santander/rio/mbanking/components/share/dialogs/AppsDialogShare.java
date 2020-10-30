package ar.com.santander.rio.mbanking.components.share.dialogs;

import android.content.Context;
import ar.com.santander.rio.mbanking.R;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class AppsDialogShare {
    private Map<EApssShareDialog, String> a;

    public enum EApssShareDialog {
        EMAIL_SHARE,
        SMS_SHARE,
        WHATSAPP_SHARE,
        OTHER_SHARE
    }

    public AppsDialogShare(Context context) {
        this.a = a(context);
    }

    public ArrayList<String> getOptions() {
        ArrayList<String> arrayList = new ArrayList<>();
        Map<EApssShareDialog, String> map = this.a;
        for (EApssShareDialog eApssShareDialog : map.keySet()) {
            arrayList.add(map.get(eApssShareDialog));
        }
        return arrayList;
    }

    private Map<EApssShareDialog, String> a(Context context) {
        TreeMap treeMap = new TreeMap();
        treeMap.put(EApssShareDialog.EMAIL_SHARE, context.getString(R.string.IDXX_SHARE_OPTION_APP_EMAIL));
        treeMap.put(EApssShareDialog.SMS_SHARE, context.getString(R.string.IDXX_SHARE_OPTION_APP_SMS));
        treeMap.put(EApssShareDialog.WHATSAPP_SHARE, context.getString(R.string.IDXX_SHARE_OPTION_APP_WHATSAPP));
        treeMap.put(EApssShareDialog.OTHER_SHARE, context.getString(R.string.IDXX_SHARE_OPTION_APP_OTHERS));
        return treeMap;
    }

    public EApssShareDialog getAppOptionShare(String str) {
        Map<EApssShareDialog, String> map = this.a;
        for (EApssShareDialog eApssShareDialog : map.keySet()) {
            if (((String) map.get(eApssShareDialog)).toString().toLowerCase().equals(str.toLowerCase())) {
                return eApssShareDialog;
            }
        }
        return EApssShareDialog.OTHER_SHARE;
    }
}
