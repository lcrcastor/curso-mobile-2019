package ar.com.santander.rio.mbanking.components.share.intent;

import android.content.Intent;
import cz.msebera.android.httpclient.protocol.HTTP;

public class AllIntent extends ShareIntentImpl {
    public AllIntent() {
        this.intent = new Intent("android.intent.action.SEND");
        this.intent.setType(HTTP.PLAIN_TEXT_TYPE);
    }
}
