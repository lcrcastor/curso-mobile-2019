package ar.com.santander.rio.mbanking.components.share.intent;

import android.content.Intent;
import android.net.Uri;
import cz.msebera.android.httpclient.protocol.HTTP;

public class EmailIntent extends ShareIntentImpl {
    public EmailIntent() {
        this.intent = new Intent("android.intent.action.SENDTO");
        this.intent.setType(HTTP.PLAIN_TEXT_TYPE);
        this.intent.setData(Uri.parse("mailto:"));
    }
}
