package ar.com.santander.rio.mbanking.components.share.intent;

import android.content.Intent;
import android.net.Uri;

public class SmsIntent extends ShareIntentImpl {
    public SmsIntent() {
        this.intent = new Intent("android.intent.action.VIEW");
        this.intent.setData(Uri.parse("sms:"));
        this.intent.setType("vnd.android-dir/mms-sms");
    }
}
