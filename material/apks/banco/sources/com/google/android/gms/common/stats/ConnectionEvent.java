package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public final class ConnectionEvent extends StatsEvent {
    public static final Creator<ConnectionEvent> CREATOR = new zza();
    final int a;
    private final long b;
    private int c;
    private final String d;
    private final String e;
    private final String f;
    private final String g;
    private final String h;
    private final String i;
    private final long j;
    private final long k;
    private long l;

    ConnectionEvent(int i2, long j2, int i3, String str, String str2, String str3, String str4, String str5, String str6, long j3, long j4) {
        this.a = i2;
        this.b = j2;
        this.c = i3;
        this.d = str;
        this.e = str2;
        this.f = str3;
        this.g = str4;
        this.l = -1;
        this.h = str5;
        this.i = str6;
        this.j = j3;
        this.k = j4;
    }

    public ConnectionEvent(long j2, int i2, String str, String str2, String str3, String str4, String str5, String str6, long j3, long j4) {
        this(1, j2, i2, str, str2, str3, str4, str5, str6, j3, j4);
    }

    public int getEventType() {
        return this.c;
    }

    public long getTimeMillis() {
        return this.b;
    }

    public void writeToParcel(Parcel parcel, int i2) {
        zza.a(this, parcel, i2);
    }

    public String zzawk() {
        return this.d;
    }

    public String zzawl() {
        return this.e;
    }

    public String zzawm() {
        return this.f;
    }

    public String zzawn() {
        return this.g;
    }

    public String zzawo() {
        return this.h;
    }

    public String zzawp() {
        return this.i;
    }

    public long zzawq() {
        return this.l;
    }

    public long zzawr() {
        return this.k;
    }

    public long zzaws() {
        return this.j;
    }

    public String zzawt() {
        String valueOf = String.valueOf("\t");
        String valueOf2 = String.valueOf(zzawk());
        String valueOf3 = String.valueOf(zzawl());
        String valueOf4 = String.valueOf("\t");
        String valueOf5 = String.valueOf(zzawm());
        String valueOf6 = String.valueOf(zzawn());
        String valueOf7 = String.valueOf("\t");
        String str = this.h == null ? "" : this.h;
        String valueOf8 = String.valueOf("\t");
        long zzawr = zzawr();
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 22 + String.valueOf(valueOf2).length() + String.valueOf(valueOf3).length() + String.valueOf(valueOf4).length() + String.valueOf(valueOf5).length() + String.valueOf(valueOf6).length() + String.valueOf(valueOf7).length() + String.valueOf(str).length() + String.valueOf(valueOf8).length());
        sb.append(valueOf);
        sb.append(valueOf2);
        sb.append("/");
        sb.append(valueOf3);
        sb.append(valueOf4);
        sb.append(valueOf5);
        sb.append("/");
        sb.append(valueOf6);
        sb.append(valueOf7);
        sb.append(str);
        sb.append(valueOf8);
        sb.append(zzawr);
        return sb.toString();
    }
}
