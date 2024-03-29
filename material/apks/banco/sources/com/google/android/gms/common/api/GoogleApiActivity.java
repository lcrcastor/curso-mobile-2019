package com.google.android.gms.common.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.util.Log;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.internal.zzqt;

public class GoogleApiActivity extends Activity implements OnCancelListener {
    protected int vD = 0;

    private void a() {
        String str;
        String str2;
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            str = "GoogleApiActivity";
            str2 = "Activity started without extras";
        } else {
            PendingIntent pendingIntent = (PendingIntent) extras.get("pending_intent");
            Integer num = (Integer) extras.get(NativeProtocol.BRIDGE_ARG_ERROR_CODE);
            if (pendingIntent == null && num == null) {
                str = "GoogleApiActivity";
                str2 = "Activity started without resolution";
            } else if (pendingIntent != null) {
                try {
                    startIntentSenderForResult(pendingIntent.getIntentSender(), 1, null, 0, 0, 0);
                    this.vD = 1;
                    return;
                } catch (SendIntentException e) {
                    Log.e("GoogleApiActivity", "Failed to launch pendingIntent", e);
                    finish();
                    return;
                }
            } else {
                GoogleApiAvailability.getInstance().showErrorDialogFragment(this, num.intValue(), 2, this);
                this.vD = 1;
                return;
            }
        }
        Log.e(str, str2);
        finish();
    }

    private void a(int i, zzqt zzqt) {
        switch (i) {
            case -1:
                zzqt.zzaqk();
                return;
            case 0:
                zzqt.zza(new ConnectionResult(13, null), getIntent().getIntExtra("failing_client_id", -1));
                return;
            default:
                return;
        }
    }

    public static PendingIntent zza(Context context, PendingIntent pendingIntent, int i) {
        return zza(context, pendingIntent, i, true);
    }

    public static PendingIntent zza(Context context, PendingIntent pendingIntent, int i, boolean z) {
        return PendingIntent.getActivity(context, 0, zzb(context, pendingIntent, i, z), 134217728);
    }

    public static Intent zzb(Context context, PendingIntent pendingIntent, int i, boolean z) {
        Intent intent = new Intent(context, GoogleApiActivity.class);
        intent.putExtra("pending_intent", pendingIntent);
        intent.putExtra("failing_client_id", i);
        intent.putExtra("notify_manager", z);
        return intent;
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 1) {
            boolean booleanExtra = getIntent().getBooleanExtra("notify_manager", true);
            this.vD = 0;
            zzqt zzasa = zzqt.zzasa();
            setResultCode(i2);
            if (booleanExtra) {
                a(i2, zzasa);
            }
        } else if (i == 2) {
            this.vD = 0;
            setResultCode(i2);
        }
        finish();
    }

    public void onCancel(DialogInterface dialogInterface) {
        this.vD = 0;
        setResult(0);
        finish();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.vD = bundle.getInt("resolution");
        }
        if (this.vD != 1) {
            a();
        }
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putInt("resolution", this.vD);
        super.onSaveInstanceState(bundle);
    }

    /* access modifiers changed from: protected */
    public void setResultCode(int i) {
        setResult(i);
    }
}
