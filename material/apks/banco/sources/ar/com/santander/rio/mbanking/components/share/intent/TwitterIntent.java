package ar.com.santander.rio.mbanking.components.share.intent;

import android.content.Intent;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import cz.msebera.android.httpclient.protocol.HTTP;

public class TwitterIntent extends ShareIntentImpl {
    public TwitterIntent() {
        this.intent = new Intent("android.intent.action.SEND");
        this.intent.setClassName(Constants.INTENT_TWITTER, "com.twitter.android.composer.ComposerActivity");
        this.intent.setType(HTTP.PLAIN_TEXT_TYPE);
    }
}
