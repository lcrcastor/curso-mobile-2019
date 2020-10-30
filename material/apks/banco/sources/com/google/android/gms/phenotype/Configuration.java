package com.google.android.gms.phenotype;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class Configuration extends AbstractSafeParcelable implements Comparable<Configuration> {
    public static final Creator<Configuration> CREATOR = new zza();
    final int a;
    public final int axl;
    public final Flag[] axm;
    public final String[] axn;
    public final Map<String, Flag> axo = new TreeMap();

    Configuration(int i, int i2, Flag[] flagArr, String[] strArr) {
        this.a = i;
        this.axl = i2;
        this.axm = flagArr;
        for (Flag flag : flagArr) {
            this.axo.put(flag.name, flag);
        }
        this.axn = strArr;
        if (this.axn != null) {
            Arrays.sort(this.axn);
        }
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Configuration)) {
            return false;
        }
        Configuration configuration = (Configuration) obj;
        return this.a == configuration.a && this.axl == configuration.axl && zzab.equal(this.axo, configuration.axo) && Arrays.equals(this.axn, configuration.axn);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Configuration(");
        sb.append(this.a);
        sb.append(", ");
        sb.append(this.axl);
        sb.append(", ");
        sb.append("(");
        for (Flag append : this.axo.values()) {
            sb.append(append);
            sb.append(", ");
        }
        sb.append(")");
        sb.append(", ");
        sb.append("(");
        if (this.axn != null) {
            for (String append2 : this.axn) {
                sb.append(append2);
                sb.append(", ");
            }
        } else {
            sb.append("null");
        }
        sb.append(")");
        sb.append(")");
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zza.a(this, parcel, i);
    }

    /* renamed from: zza */
    public int compareTo(Configuration configuration) {
        return this.axl - configuration.axl;
    }
}
