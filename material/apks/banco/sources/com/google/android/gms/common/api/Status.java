package com.google.android.gms.common.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;

public final class Status extends AbstractSafeParcelable implements Result, ReflectedParcelable {
    public static final Creator<Status> CREATOR = new zzh();
    public static final Status vY = new Status(0);
    public static final Status vZ = new Status(14);
    public static final Status wa = new Status(8);
    public static final Status wb = new Status(15);
    public static final Status wc = new Status(16);
    public static final Status wd = new Status(17);
    public static final Status we = new Status(18);
    private final int a;
    private final int b;
    private final String c;
    private final PendingIntent d;

    public Status(int i) {
        this(i, null);
    }

    Status(int i, int i2, String str, PendingIntent pendingIntent) {
        this.a = i;
        this.b = i2;
        this.c = str;
        this.d = pendingIntent;
    }

    public Status(int i, String str) {
        this(1, i, str, null);
    }

    public Status(int i, String str, PendingIntent pendingIntent) {
        this(1, i, str, pendingIntent);
    }

    private String c() {
        return this.c != null ? this.c : CommonStatusCodes.getStatusCodeString(this.b);
    }

    /* access modifiers changed from: 0000 */
    public PendingIntent a() {
        return this.d;
    }

    /* access modifiers changed from: 0000 */
    public int b() {
        return this.a;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof Status)) {
            return false;
        }
        Status status = (Status) obj;
        if (this.a == status.a && this.b == status.b && zzab.equal(this.c, status.c) && zzab.equal(this.d, status.d)) {
            z = true;
        }
        return z;
    }

    public PendingIntent getResolution() {
        return this.d;
    }

    public Status getStatus() {
        return this;
    }

    public int getStatusCode() {
        return this.b;
    }

    @Nullable
    public String getStatusMessage() {
        return this.c;
    }

    public boolean hasResolution() {
        return this.d != null;
    }

    public int hashCode() {
        return zzab.hashCode(Integer.valueOf(this.a), Integer.valueOf(this.b), this.c, this.d);
    }

    public boolean isCanceled() {
        return this.b == 16;
    }

    public boolean isInterrupted() {
        return this.b == 14;
    }

    public boolean isSuccess() {
        return this.b <= 0;
    }

    public void startResolutionForResult(Activity activity, int i) {
        if (hasResolution()) {
            activity.startIntentSenderForResult(this.d.getIntentSender(), i, null, 0, 0, 0);
        }
    }

    public String toString() {
        return zzab.zzx(this).zzg("statusCode", c()).zzg("resolution", this.d).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzh.a(this, parcel, i);
    }
}
