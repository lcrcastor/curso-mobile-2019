package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.WorkSource;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

public class ActivityRecognitionRequest extends AbstractSafeParcelable {
    public static final Creator<ActivityRecognitionRequest> CREATOR = new zza();
    private final int a;
    private long b;
    private boolean c;
    @Nullable
    private WorkSource d;
    @Nullable
    private String e;
    @Nullable
    private int[] f;
    @Nullable
    private boolean g;
    @Nullable
    private String h;
    private final long i;

    ActivityRecognitionRequest(int i2, long j, boolean z, @Nullable WorkSource workSource, @Nullable String str, @Nullable int[] iArr, boolean z2, @Nullable String str2, long j2) {
        this.a = i2;
        this.b = j;
        this.c = z;
        this.d = workSource;
        this.e = str;
        this.f = iArr;
        this.g = z2;
        this.h = str2;
        this.i = j2;
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return this.a;
    }

    @Nullable
    public String getAccountName() {
        return this.h;
    }

    public long getIntervalMillis() {
        return this.b;
    }

    @Nullable
    public String getTag() {
        return this.e;
    }

    public void writeToParcel(Parcel parcel, int i2) {
        zza.a(this, parcel, i2);
    }

    public boolean zzbox() {
        return this.c;
    }

    @Nullable
    public WorkSource zzboy() {
        return this.d;
    }

    @Nullable
    public int[] zzboz() {
        return this.f;
    }

    public boolean zzbpa() {
        return this.g;
    }

    public long zzbpb() {
        return this.i;
    }
}
