package ar.com.santander.rio.mbanking.app.module.accounts.account;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import ar.com.santander.rio.mbanking.app.exceptions.EmptyAppShare;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.components.share.ShareIntentComponent;
import ar.com.santander.rio.mbanking.components.share.intent.EmailIntent;
import ar.com.santander.rio.mbanking.components.share.intent.SmsIntent;
import ar.com.santander.rio.mbanking.components.share.intent.WhatsAppIntent;
import java.util.ArrayList;
import java.util.List;

public class DispatchShareIntentAccount extends ShareIntentComponent {
    private PackageManager a;
    private String b;
    private String c;

    public DispatchShareIntentAccount(PackageManager packageManager) {
        this.a = packageManager;
    }

    public List<Intent> getListIntentToShare() {
        ArrayList arrayList = new ArrayList();
        Intent b2 = b();
        if (b2 != null) {
            arrayList.add(b2);
        }
        Intent c2 = c();
        if (c2 != null) {
            arrayList.add(c2);
        }
        Intent d = d();
        if (d != null) {
            arrayList.add(d);
        }
        if (arrayList.size() > 0) {
            return arrayList;
        }
        throw new EmptyAppShare("");
    }

    public void setMessage(String str) {
        this.b = str;
    }

    public void setSubject(String str) {
        this.c = str;
    }

    private Bundle a() {
        Bundle bundle = new Bundle();
        bundle.putString("android.intent.extra.TEXT", this.b);
        bundle.putString("android.intent.extra.SUBJECT", this.c);
        return bundle;
    }

    private Intent b() {
        EmailIntent emailIntent = new EmailIntent();
        emailIntent.setExtras(a());
        if (verifyAppReceiveIntent(this.a, emailIntent.getIntent(this.a, Constants.INTENT_GMAIL)).booleanValue()) {
            return emailIntent.getAllIntents();
        }
        return null;
    }

    private Intent c() {
        WhatsAppIntent whatsAppIntent = new WhatsAppIntent();
        whatsAppIntent.setExtras(a());
        Intent intent = whatsAppIntent.getIntent(this.a, Constants.INTENT_WHATSAPP);
        if (verifyAppReceiveIntent(this.a, intent).booleanValue()) {
            return intent;
        }
        return null;
    }

    private Intent d() {
        SmsIntent smsIntent = new SmsIntent();
        Bundle a2 = a();
        a2.putString("sms_body", this.b);
        smsIntent.setExtras(a2);
        Intent allIntents = smsIntent.getAllIntents();
        if (verifyAppReceiveIntent(this.a, allIntents).booleanValue()) {
            return allIntents;
        }
        return null;
    }
}
