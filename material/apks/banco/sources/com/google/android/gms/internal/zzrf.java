package com.google.android.gms.internal;

import android.app.Activity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zzb;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.concurrent.CancellationException;

public class zzrf extends zzqd {
    private TaskCompletionSource<Void> a = new TaskCompletionSource<>();

    private zzrf(zzrb zzrb) {
        super(zzrb);
        this.yY.zza("GmsAvailabilityHelper", (zzra) this);
    }

    public static zzrf zzu(Activity activity) {
        zzrb zzs = zzs(activity);
        zzrf zzrf = (zzrf) zzs.zza("GmsAvailabilityHelper", zzrf.class);
        if (zzrf == null) {
            return new zzrf(zzs);
        }
        if (zzrf.a.getTask().isComplete()) {
            zzrf.a = new TaskCompletionSource<>();
        }
        return zzrf;
    }

    public Task<Void> getTask() {
        return this.a.getTask();
    }

    public void onDestroy() {
        super.onDestroy();
        this.a.setException(new CancellationException("Host activity was destroyed before Google Play services could be made available."));
    }

    /* access modifiers changed from: protected */
    public void zza(ConnectionResult connectionResult, int i) {
        this.a.setException(zzb.zzl(connectionResult));
    }

    /* access modifiers changed from: protected */
    public void zzaqk() {
        int isGooglePlayServicesAvailable = this.vP.isGooglePlayServicesAvailable(this.yY.zzasq());
        if (isGooglePlayServicesAvailable == 0) {
            this.a.setResult(null);
        } else {
            zzk(new ConnectionResult(isGooglePlayServicesAvailable, null));
        }
    }

    public void zzk(ConnectionResult connectionResult) {
        zzb(connectionResult, 0);
    }
}
