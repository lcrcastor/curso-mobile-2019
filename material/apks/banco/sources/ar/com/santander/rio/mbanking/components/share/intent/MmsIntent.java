package ar.com.santander.rio.mbanking.components.share.intent;

import android.content.Intent;

public class MmsIntent extends ShareIntentImpl {
    public MmsIntent() {
        this.intent = new Intent("android.intent.action.SEND");
    }
}
