package ar.com.santander.rio.mbanking.app.commons;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.support.v4.content.FileProvider;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.components.share.ShareIntentComponent;
import ar.com.santander.rio.mbanking.components.share.intent.AllIntent;
import ar.com.santander.rio.mbanking.components.share.intent.EmailIntent;
import ar.com.santander.rio.mbanking.components.share.intent.MmsIntent;
import ar.com.santander.rio.mbanking.components.share.intent.WhatsAppIntent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class CPaymentShareIntent extends ShareIntentComponent {
    private PackageManager a;

    public CPaymentShareIntent(PackageManager packageManager) {
        this.a = packageManager;
    }

    public Intent getEmailIntent(String str, Uri uri) {
        EmailIntent emailIntent = new EmailIntent();
        emailIntent.setAction("android.intent.action.SEND");
        emailIntent.setType("image/jpeg");
        emailIntent.setExtra("android.intent.extra.SUBJECT", str);
        emailIntent.setExtra("android.intent.extra.STREAM", uri);
        Intent intent = emailIntent.getIntent(this.a, Constants.INTENT_GMAIL);
        if (verifyAppReceiveIntent(this.a, intent).booleanValue()) {
            return intent;
        }
        return null;
    }

    public Intent getWhatsAppIntent(String str, Uri uri) {
        WhatsAppIntent whatsAppIntent = new WhatsAppIntent();
        whatsAppIntent.setType("image/jpeg");
        whatsAppIntent.setExtra("android.intent.extra.SUBJECT", str);
        whatsAppIntent.setExtra("android.intent.extra.STREAM", uri);
        Intent intent = whatsAppIntent.getIntent(this.a, Constants.INTENT_WHATSAPP);
        if (verifyAppReceiveIntent(this.a, intent).booleanValue()) {
            return intent;
        }
        return null;
    }

    public Intent getOtherIntentImg(String str, Uri uri) {
        AllIntent allIntent = new AllIntent();
        allIntent.setType("image/jpeg");
        allIntent.setExtra("android.intent.extra.SUBJECT", str);
        allIntent.setExtra("android.intent.extra.STREAM", uri);
        Intent allIntents = allIntent.getAllIntents();
        if (verifyAppReceiveIntent(this.a, allIntents).booleanValue()) {
            return allIntents;
        }
        return null;
    }

    public Intent getOtherIntentImg(String str, File file, Context context) {
        Intent intent;
        Uri uriForFile = FileProvider.getUriForFile(context, context.getPackageName(), file);
        if (VERSION.SDK_INT >= 21) {
            intent = new Intent("android.intent.action.SEND_MULTIPLE");
            intent.putParcelableArrayListExtra("android.intent.extra.STREAM", new ArrayList(Arrays.asList(new Uri[]{uriForFile})));
        } else {
            intent = new Intent("android.intent.action.SEND");
            intent.putExtra("android.intent.extra.STREAM", uriForFile);
        }
        intent.setType(AllIntent.MIME_TYPE_IMAGE_JPEG);
        intent.putExtra("android.intent.extra.SUBJECT", str);
        intent.addFlags(1);
        intent.addFlags(2);
        if (setContentUriPermissionsByIntent(context, intent, uriForFile).booleanValue() && verifyAppReceiveIntent(this.a, intent).booleanValue()) {
            return intent;
        }
        return null;
    }

    public Intent getMMSIntent(String str, Uri uri) {
        MmsIntent mmsIntent = new MmsIntent();
        mmsIntent.setType("image/jpeg");
        mmsIntent.setExtra("android.intent.extra.SUBJECT", str);
        mmsIntent.setExtra("android.intent.extra.STREAM", uri);
        Intent intent = mmsIntent.getIntent(this.a, Constants.INTENT_HANGOUTS);
        if (verifyAppReceiveIntent(this.a, intent).booleanValue()) {
            return intent;
        }
        return null;
    }
}
