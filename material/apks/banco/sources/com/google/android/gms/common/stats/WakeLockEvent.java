package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import java.util.List;

public final class WakeLockEvent extends StatsEvent {
    public static final Creator<WakeLockEvent> CREATOR = new zzg();
    final int a;
    private final long b;
    private int c;
    private final String d;
    private final String e;
    private final String f;
    private final int g;
    private final List<String> h;
    private final String i;
    private final long j;
    private int k;
    private final String l;
    private final float m;
    private final long n;
    private long o;

    WakeLockEvent(int i2, long j2, int i3, String str, int i4, List<String> list, String str2, long j3, int i5, String str3, String str4, float f2, long j4, String str5) {
        this.a = i2;
        this.b = j2;
        this.c = i3;
        this.d = str;
        this.e = str3;
        this.f = str5;
        this.g = i4;
        this.o = -1;
        this.h = list;
        this.i = str2;
        this.j = j3;
        this.k = i5;
        this.l = str4;
        this.m = f2;
        this.n = j4;
    }

    public WakeLockEvent(long j2, int i2, String str, int i3, List<String> list, String str2, long j3, int i4, String str3, String str4, float f2, long j4, String str5) {
        this(2, j2, i2, str, i3, list, str2, j3, i4, str3, str4, f2, j4, str5);
    }

    public int getEventType() {
        return this.c;
    }

    public long getTimeMillis() {
        return this.b;
    }

    public void writeToParcel(Parcel parcel, int i2) {
        zzg.a(this, parcel, i2);
    }

    public String zzawp() {
        return this.i;
    }

    public long zzawq() {
        return this.o;
    }

    public long zzaws() {
        return this.j;
    }

    public String zzawt() {
        String valueOf = String.valueOf("\t");
        String valueOf2 = String.valueOf(zzaww());
        String valueOf3 = String.valueOf("\t");
        int zzawz = zzawz();
        String valueOf4 = String.valueOf("\t");
        String join = zzaxa() == null ? "" : TextUtils.join(",", zzaxa());
        String valueOf5 = String.valueOf("\t");
        int zzaxb = zzaxb();
        String valueOf6 = String.valueOf("\t");
        String zzawx = zzawx() == null ? "" : zzawx();
        String valueOf7 = String.valueOf("\t");
        String zzaxc = zzaxc() == null ? "" : zzaxc();
        String valueOf8 = String.valueOf("\t");
        float zzaxd = zzaxd();
        String valueOf9 = String.valueOf("\t");
        String zzawy = zzawy() == null ? "" : zzawy();
        float f2 = zzaxd;
        int i2 = zzaxb;
        int i3 = zzawz;
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 37 + String.valueOf(valueOf2).length() + String.valueOf(valueOf3).length() + String.valueOf(valueOf4).length() + String.valueOf(join).length() + String.valueOf(valueOf5).length() + String.valueOf(valueOf6).length() + String.valueOf(zzawx).length() + String.valueOf(valueOf7).length() + String.valueOf(zzaxc).length() + String.valueOf(valueOf8).length() + String.valueOf(valueOf9).length() + String.valueOf(zzawy).length());
        sb.append(valueOf);
        sb.append(valueOf2);
        sb.append(valueOf3);
        sb.append(i3);
        sb.append(valueOf4);
        sb.append(join);
        sb.append(valueOf5);
        sb.append(i2);
        sb.append(valueOf6);
        sb.append(zzawx);
        sb.append(valueOf7);
        sb.append(zzaxc);
        sb.append(valueOf8);
        sb.append(f2);
        sb.append(valueOf9);
        sb.append(zzawy);
        return sb.toString();
    }

    public String zzaww() {
        return this.d;
    }

    public String zzawx() {
        return this.e;
    }

    public String zzawy() {
        return this.f;
    }

    public int zzawz() {
        return this.g;
    }

    public List<String> zzaxa() {
        return this.h;
    }

    public int zzaxb() {
        return this.k;
    }

    public String zzaxc() {
        return this.l;
    }

    public float zzaxd() {
        return this.m;
    }

    public long zzaxe() {
        return this.n;
    }
}
