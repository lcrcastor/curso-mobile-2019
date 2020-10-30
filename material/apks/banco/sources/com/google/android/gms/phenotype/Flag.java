package com.google.android.gms.phenotype;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Comparator;

public class Flag extends AbstractSafeParcelable implements Comparable<Flag> {
    public static final Creator<Flag> CREATOR = new zzb();
    public static final zza axt = new zza();
    private static final Charset g = Charset.forName("UTF-8");
    final int a;
    public final int axr;
    public final int axs;
    final long b;
    final boolean c;
    final double d;
    final String e;
    final byte[] f;
    public final String name;

    public static class zza implements Comparator<Flag> {
        /* renamed from: zza */
        public int compare(Flag flag, Flag flag2) {
            return flag.axs == flag2.axs ? flag.name.compareTo(flag2.name) : flag.axs - flag2.axs;
        }
    }

    Flag(int i, String str, long j, boolean z, double d2, String str2, byte[] bArr, int i2, int i3) {
        this.a = i;
        this.name = str;
        this.b = j;
        this.c = z;
        this.d = d2;
        this.e = str2;
        this.f = bArr;
        this.axr = i2;
        this.axs = i3;
    }

    private static int a(byte b2, byte b3) {
        return b2 - b3;
    }

    private static int a(int i, int i2) {
        if (i < i2) {
            return -1;
        }
        return i == i2 ? 0 : 1;
    }

    private static int a(long j, long j2) {
        if (j < j2) {
            return -1;
        }
        return j == j2 ? 0 : 1;
    }

    private static int a(String str, String str2) {
        if (str == str2) {
            return 0;
        }
        if (str == null) {
            return -1;
        }
        if (str2 == null) {
            return 1;
        }
        return str.compareTo(str2);
    }

    private static int a(boolean z, boolean z2) {
        if (z == z2) {
            return 0;
        }
        return z ? 1 : -1;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj != null && (obj instanceof Flag)) {
            Flag flag = (Flag) obj;
            if (this.a == flag.a && zzab.equal(this.name, flag.name) && this.axr == flag.axr) {
                if (this.axs != flag.axs) {
                    return false;
                }
                switch (this.axr) {
                    case 1:
                        if (this.b == flag.b) {
                            z = true;
                            break;
                        }
                        break;
                    case 2:
                        if (this.c == flag.c) {
                            z = true;
                        }
                        return z;
                    case 3:
                        if (this.d == flag.d) {
                            z = true;
                        }
                        return z;
                    case 4:
                        return zzab.equal(this.e, flag.e);
                    case 5:
                        return Arrays.equals(this.f, flag.f);
                    default:
                        int i = this.axr;
                        StringBuilder sb = new StringBuilder(31);
                        sb.append("Invalid enum value: ");
                        sb.append(i);
                        throw new AssertionError(sb.toString());
                }
            }
        }
        return z;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x005d, code lost:
        r0.append(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0072, code lost:
        r0.append(", ");
        r0.append(r4.axr);
        r0.append(", ");
        r0.append(r4.axs);
        r0.append(")");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x008f, code lost:
        return r0.toString();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0058, code lost:
        r0.append(r1);
        r1 = "'";
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String toString() {
        /*
            r4 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Flag("
            r0.<init>(r1)
            int r1 = r4.a
            r0.append(r1)
            java.lang.String r1 = ", "
            r0.append(r1)
            java.lang.String r1 = r4.name
            r0.append(r1)
            java.lang.String r1 = ", "
            r0.append(r1)
            int r1 = r4.axr
            switch(r1) {
                case 1: goto L_0x006d;
                case 2: goto L_0x0067;
                case 3: goto L_0x0061;
                case 4: goto L_0x0051;
                case 5: goto L_0x003b;
                default: goto L_0x0020;
            }
        L_0x0020:
            java.lang.AssertionError r0 = new java.lang.AssertionError
            int r1 = r4.axr
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r3 = 31
            r2.<init>(r3)
            java.lang.String r3 = "Invalid enum value: "
            r2.append(r3)
            r2.append(r1)
            java.lang.String r1 = r2.toString()
            r0.<init>(r1)
            throw r0
        L_0x003b:
            byte[] r1 = r4.f
            if (r1 != 0) goto L_0x0042
            java.lang.String r1 = "null"
            goto L_0x005d
        L_0x0042:
            java.lang.String r1 = "'"
            r0.append(r1)
            java.lang.String r1 = new java.lang.String
            byte[] r2 = r4.f
            java.nio.charset.Charset r3 = g
            r1.<init>(r2, r3)
            goto L_0x0058
        L_0x0051:
            java.lang.String r1 = "'"
            r0.append(r1)
            java.lang.String r1 = r4.e
        L_0x0058:
            r0.append(r1)
            java.lang.String r1 = "'"
        L_0x005d:
            r0.append(r1)
            goto L_0x0072
        L_0x0061:
            double r1 = r4.d
            r0.append(r1)
            goto L_0x0072
        L_0x0067:
            boolean r1 = r4.c
            r0.append(r1)
            goto L_0x0072
        L_0x006d:
            long r1 = r4.b
            r0.append(r1)
        L_0x0072:
            java.lang.String r1 = ", "
            r0.append(r1)
            int r1 = r4.axr
            r0.append(r1)
            java.lang.String r1 = ", "
            r0.append(r1)
            int r1 = r4.axs
            r0.append(r1)
            java.lang.String r1 = ")"
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.phenotype.Flag.toString():java.lang.String");
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzb.a(this, parcel, i);
    }

    /* renamed from: zza */
    public int compareTo(Flag flag) {
        int compareTo = this.name.compareTo(flag.name);
        if (compareTo != 0) {
            return compareTo;
        }
        int a2 = a(this.axr, flag.axr);
        if (a2 != 0) {
            return a2;
        }
        switch (this.axr) {
            case 1:
                return a(this.b, flag.b);
            case 2:
                return a(this.c, flag.c);
            case 3:
                return Double.compare(this.d, flag.d);
            case 4:
                return a(this.e, flag.e);
            case 5:
                if (this.f == flag.f) {
                    return 0;
                }
                if (this.f == null) {
                    return -1;
                }
                if (flag.f == null) {
                    return 1;
                }
                for (int i = 0; i < Math.min(this.f.length, flag.f.length); i++) {
                    int a3 = a(this.f[i], flag.f[i]);
                    if (a3 != 0) {
                        return a3;
                    }
                }
                return a(this.f.length, flag.f.length);
            default:
                int i2 = this.axr;
                StringBuilder sb = new StringBuilder(31);
                sb.append("Invalid enum value: ");
                sb.append(i2);
                throw new AssertionError(sb.toString());
        }
    }
}
