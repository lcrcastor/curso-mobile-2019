package ar.com.santander.rio.mbanking.app.base;

import android.app.Activity;

public class ReceiptEvent {
    private ITRSABaseFragment a;
    private Activity b;
    private String c;

    public ITRSABaseFragment getReceiptFragment() {
        return this.a;
    }

    public String getTAG() {
        return this.c;
    }

    public void setTAG(String str) {
        this.c = str;
    }

    public Activity getActivity() {
        return this.b;
    }

    public void setActivity(Activity activity) {
        this.b = activity;
    }

    public ReceiptEvent(ITRSABaseFragment iTRSABaseFragment, String str) {
        this.a = iTRSABaseFragment;
        this.c = str;
    }

    public ReceiptEvent(Activity activity, String str) {
        this.b = activity;
        this.c = str;
    }
}
