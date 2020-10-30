package com.google.android.gms.common.api;

import android.app.Activity;
import android.content.IntentSender.SendIntentException;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.zzac;

public abstract class ResolvingResultCallbacks<R extends Result> extends ResultCallbacks<R> {
    private final Activity a;
    private final int b;

    protected ResolvingResultCallbacks(@NonNull Activity activity, int i) {
        this.a = (Activity) zzac.zzb(activity, (Object) "Activity must not be null");
        this.b = i;
    }

    public final void onFailure(@NonNull Status status) {
        if (status.hasResolution()) {
            try {
                status.startResolutionForResult(this.a, this.b);
                return;
            } catch (SendIntentException e) {
                Log.e("ResolvingResultCallback", "Failed to start resolution", e);
                status = new Status(8);
            }
        }
        onUnresolvableFailure(status);
    }

    public abstract void onSuccess(@NonNull R r);

    public abstract void onUnresolvableFailure(@NonNull Status status);
}
