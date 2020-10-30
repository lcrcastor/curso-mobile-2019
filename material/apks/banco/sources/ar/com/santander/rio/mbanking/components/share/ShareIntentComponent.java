package ar.com.santander.rio.mbanking.components.share;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

public class ShareIntentComponent {
    /* access modifiers changed from: protected */
    public Boolean verifyAppReceiveIntent(PackageManager packageManager, Intent intent) {
        boolean z = false;
        if (intent == null) {
            return Boolean.valueOf(false);
        }
        if (packageManager.queryIntentActivities(intent, 65536).size() > 0) {
            z = true;
        }
        return Boolean.valueOf(z);
    }

    /* access modifiers changed from: protected */
    public Boolean setContentUriPermissionsByIntent(Context context, Intent intent, Uri uri) {
        boolean z = false;
        if (intent == null) {
            return Boolean.valueOf(false);
        }
        context.grantUriPermission(context.getPackageName(), uri, 3);
        if (context.getPackageManager().queryIntentActivities(intent, 65536).size() > 0) {
            z = true;
        }
        return Boolean.valueOf(z);
    }
}
