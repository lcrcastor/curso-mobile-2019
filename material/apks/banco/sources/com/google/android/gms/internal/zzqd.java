package com.google.android.gms.internal;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.MainThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiActivity;

public abstract class zzqd extends zzra implements OnCancelListener {
    /* access modifiers changed from: private */
    public ConnectionResult a;
    /* access modifiers changed from: private */
    public int b;
    private final Handler c;
    protected boolean mStarted;
    protected final GoogleApiAvailability vP;
    protected boolean wy;

    class zza implements Runnable {
        private zza() {
        }

        @MainThread
        public void run() {
            if (zzqd.this.mStarted) {
                if (zzqd.this.a.hasResolution()) {
                    zzqd.this.yY.startActivityForResult(GoogleApiActivity.zzb(zzqd.this.getActivity(), zzqd.this.a.getResolution(), zzqd.this.b, false), 1);
                } else if (zzqd.this.vP.isUserResolvableError(zzqd.this.a.getErrorCode())) {
                    zzqd.this.vP.zza(zzqd.this.getActivity(), zzqd.this.yY, zzqd.this.a.getErrorCode(), 2, zzqd.this);
                } else if (zzqd.this.a.getErrorCode() == 18) {
                    final Dialog zza = zzqd.this.vP.zza(zzqd.this.getActivity(), (OnCancelListener) zzqd.this);
                    zzqd.this.vP.zza(zzqd.this.getActivity().getApplicationContext(), (com.google.android.gms.internal.zzqv.zza) new com.google.android.gms.internal.zzqv.zza() {
                        public void zzaqp() {
                            zzqd.this.zzaqo();
                            if (zza.isShowing()) {
                                zza.dismiss();
                            }
                        }
                    });
                } else {
                    zzqd.this.zza(zzqd.this.a, zzqd.this.b);
                }
            }
        }
    }

    protected zzqd(zzrb zzrb) {
        this(zzrb, GoogleApiAvailability.getInstance());
    }

    zzqd(zzrb zzrb, GoogleApiAvailability googleApiAvailability) {
        super(zzrb);
        this.b = -1;
        this.c = new Handler(Looper.getMainLooper());
        this.vP = googleApiAvailability;
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onActivityResult(int r3, int r4, android.content.Intent r5) {
        /*
            r2 = this;
            r0 = 1
            r1 = 0
            switch(r3) {
                case 1: goto L_0x0021;
                case 2: goto L_0x0006;
                default: goto L_0x0005;
            }
        L_0x0005:
            goto L_0x0039
        L_0x0006:
            com.google.android.gms.common.GoogleApiAvailability r3 = r2.vP
            android.app.Activity r4 = r2.getActivity()
            int r3 = r3.isGooglePlayServicesAvailable(r4)
            if (r3 != 0) goto L_0x0013
            goto L_0x0014
        L_0x0013:
            r0 = 0
        L_0x0014:
            com.google.android.gms.common.ConnectionResult r4 = r2.a
            int r4 = r4.getErrorCode()
            r5 = 18
            if (r4 != r5) goto L_0x003a
            if (r3 != r5) goto L_0x003a
            return
        L_0x0021:
            r3 = -1
            if (r4 != r3) goto L_0x0025
            goto L_0x003a
        L_0x0025:
            if (r4 != 0) goto L_0x0039
            r3 = 13
            if (r5 == 0) goto L_0x0031
            java.lang.String r4 = "<<ResolutionFailureErrorDetail>>"
            int r3 = r5.getIntExtra(r4, r3)
        L_0x0031:
            com.google.android.gms.common.ConnectionResult r4 = new com.google.android.gms.common.ConnectionResult
            r5 = 0
            r4.<init>(r3, r5)
            r2.a = r4
        L_0x0039:
            r0 = 0
        L_0x003a:
            if (r0 == 0) goto L_0x0040
            r2.zzaqo()
            return
        L_0x0040:
            com.google.android.gms.common.ConnectionResult r3 = r2.a
            int r4 = r2.b
            r2.zza(r3, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzqd.onActivityResult(int, int, android.content.Intent):void");
    }

    public void onCancel(DialogInterface dialogInterface) {
        zza(new ConnectionResult(13, null), this.b);
        zzaqo();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.wy = bundle.getBoolean("resolving_error", false);
            if (this.wy) {
                this.b = bundle.getInt("failed_client_id", -1);
                this.a = new ConnectionResult(bundle.getInt("failed_status"), (PendingIntent) bundle.getParcelable("failed_resolution"));
            }
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("resolving_error", this.wy);
        if (this.wy) {
            bundle.putInt("failed_client_id", this.b);
            bundle.putInt("failed_status", this.a.getErrorCode());
            bundle.putParcelable("failed_resolution", this.a.getResolution());
        }
    }

    public void onStart() {
        super.onStart();
        this.mStarted = true;
    }

    public void onStop() {
        super.onStop();
        this.mStarted = false;
    }

    /* access modifiers changed from: protected */
    public abstract void zza(ConnectionResult connectionResult, int i);

    /* access modifiers changed from: protected */
    public abstract void zzaqk();

    /* access modifiers changed from: protected */
    public void zzaqo() {
        this.b = -1;
        this.wy = false;
        this.a = null;
        zzaqk();
    }

    public void zzb(ConnectionResult connectionResult, int i) {
        if (!this.wy) {
            this.wy = true;
            this.b = i;
            this.a = connectionResult;
            this.c.post(new zza());
        }
    }
}
