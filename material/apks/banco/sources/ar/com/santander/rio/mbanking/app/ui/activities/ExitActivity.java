package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;

public class ExitActivity extends Activity {
    public static void exitApplication(Context context) {
        Intent intent = new Intent(context, ExitActivity.class);
        intent.addFlags(276922368);
        context.startActivity(intent);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (VERSION.SDK_INT >= 21) {
            finishAndRemoveTask();
        } else {
            finish();
        }
    }
}
