package com.google.android.gms.location;

import android.os.Parcel;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.location.internal.ClientIdentity;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class ActivityTransitionRequest extends AbstractSafeParcelable {
    public static final zzc CREATOR = new zzc();
    public static final Comparator<ActivityTransition> agL = new Comparator<ActivityTransition>() {
        /* renamed from: a */
        public int compare(ActivityTransition activityTransition, ActivityTransition activityTransition2) {
            int zzbeb = activityTransition.zzbeb();
            int zzbeb2 = activityTransition2.zzbeb();
            int i = 1;
            if (zzbeb != zzbeb2) {
                if (zzbeb < zzbeb2) {
                    i = -1;
                }
                return i;
            }
            int zzbpc = activityTransition.zzbpc();
            int zzbpc2 = activityTransition2.zzbpc();
            if (zzbpc == zzbpc2) {
                return 0;
            }
            if (zzbpc < zzbpc2) {
                i = -1;
            }
            return i;
        }
    };
    private final int a;
    private final List<ActivityTransition> b;
    @Nullable
    private final String c;
    private final List<ClientIdentity> d;

    ActivityTransitionRequest(int i, List<ActivityTransition> list, @Nullable String str, @Nullable List<ClientIdentity> list2) {
        zzac.zzb(list, (Object) "transitions can't be null");
        zzac.zzb(list.size() > 0, (Object) "transitions can't be empty.");
        a(list);
        this.a = i;
        this.b = Collections.unmodifiableList(list);
        this.c = str;
        this.d = list2 == null ? Collections.emptyList() : Collections.unmodifiableList(list2);
    }

    private static void a(List<ActivityTransition> list) {
        TreeSet treeSet = new TreeSet(agL);
        for (ActivityTransition activityTransition : list) {
            zzac.zzb(treeSet.add(activityTransition), (Object) String.format("Found duplicated transition: %s.", new Object[]{activityTransition}));
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ActivityTransitionRequest activityTransitionRequest = (ActivityTransitionRequest) obj;
        return zzab.equal(this.b, activityTransitionRequest.b) && zzab.equal(this.c, activityTransitionRequest.c) && zzab.equal(this.d, activityTransitionRequest.d);
    }

    @Nullable
    public String getTag() {
        return this.c;
    }

    public int getVersionCode() {
        return this.a;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((this.b.hashCode() * 31) + (this.c != null ? this.c.hashCode() : 0)) * 31;
        if (this.d != null) {
            i = this.d.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        String valueOf = String.valueOf(this.b);
        String str = this.c;
        String valueOf2 = String.valueOf(this.d);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 61 + String.valueOf(str).length() + String.valueOf(valueOf2).length());
        sb.append("ActivityTransitionRequest [mTransitions=");
        sb.append(valueOf);
        sb.append(", mTag='");
        sb.append(str);
        sb.append("'");
        sb.append(", mClients=");
        sb.append(valueOf2);
        sb.append("]");
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzc.a(this, parcel, i);
    }

    public List<ActivityTransition> zzbpd() {
        return this.b;
    }

    public List<ClientIdentity> zzbpe() {
        return this.d;
    }
}
