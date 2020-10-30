package ar.com.santander.rio.mbanking.components.share.intent;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Arrays;

public class ShareIntentImpl implements ShareIntent {
    public static String MIME_TYPE_IMAGE_JPEG = "image/jpeg";
    public static String MIME_TYPE_TEXT_PLAIN = "text/plain";
    protected Bundle iBundle;
    protected Intent intent;

    public void setExtras(Bundle bundle) {
        this.iBundle = bundle;
    }

    public Intent getAllIntents() {
        a();
        return this.intent;
    }

    public Intent getIntent(PackageManager packageManager, String str) {
        a();
        if (!a(packageManager, str)) {
            return null;
        }
        this.intent.setPackage(str);
        return this.intent;
    }

    private void a() {
        if (this.iBundle != null) {
            for (String str : this.iBundle.keySet()) {
                this.intent.putExtra(str, this.iBundle.get(str).toString());
            }
        }
    }

    public void setType(String str) {
        this.intent.setType(str);
    }

    public void setAction(String str) {
        this.intent.setAction(str);
    }

    public void setExtra(String str, Uri uri) {
        this.intent.putExtra(str, uri);
    }

    public void setExtra(String str, String str2) {
        this.intent.putExtra(str, str2);
    }

    private boolean a(PackageManager packageManager, String str) {
        try {
            for (ResolveInfo resolveInfo : packageManager.queryIntentActivities(this.intent, 0)) {
                if (resolveInfo.activityInfo.packageName.equals(str)) {
                    return true;
                }
            }
        } catch (Exception unused) {
        }
        return false;
    }

    public void addFlags(int i) {
        this.intent.addFlags(i);
    }

    public void setParcelableArrayExtra(String str, Uri uri) {
        this.intent.putParcelableArrayListExtra(str, new ArrayList(Arrays.asList(new Uri[]{uri})));
    }
}
