package com.google.android.gms.tagmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class PreviewActivity extends Activity {
    private void a(String str, String str2, String str3) {
        AlertDialog create = new Builder(this).create();
        create.setTitle(str);
        create.setMessage(str2);
        create.setButton(-1, str3, new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        create.show();
    }

    public void onCreate(Bundle bundle) {
        try {
            super.onCreate(bundle);
            zzbo.zzde("Preview activity");
            Uri data = getIntent().getData();
            if (!TagManager.getInstance(this).a(data)) {
                String valueOf = String.valueOf(data);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 73);
                sb.append("Cannot preview the app with the uri: ");
                sb.append(valueOf);
                sb.append(". Launching current version instead.");
                String sb2 = sb.toString();
                zzbo.zzdf(sb2);
                a("Preview failure", sb2, "Continue");
            }
            Intent launchIntentForPackage = getPackageManager().getLaunchIntentForPackage(getPackageName());
            if (launchIntentForPackage != null) {
                String str = "Invoke the launch activity for package name: ";
                String valueOf2 = String.valueOf(getPackageName());
                zzbo.zzde(valueOf2.length() != 0 ? str.concat(valueOf2) : new String(str));
                startActivity(launchIntentForPackage);
                return;
            }
            String str2 = "No launch activity found for package name: ";
            String valueOf3 = String.valueOf(getPackageName());
            zzbo.zzde(valueOf3.length() != 0 ? str2.concat(valueOf3) : new String(str2));
        } catch (Exception e) {
            String str3 = "Calling preview threw an exception: ";
            String valueOf4 = String.valueOf(e.getMessage());
            zzbo.e(valueOf4.length() != 0 ? str3.concat(valueOf4) : new String(str3));
        }
    }
}
