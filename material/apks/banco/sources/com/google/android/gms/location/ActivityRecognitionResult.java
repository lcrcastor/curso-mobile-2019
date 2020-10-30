package com.google.android.gms.location;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzac;
import java.util.Collections;
import java.util.List;
import org.bouncycastle.asn1.eac.EACTags;

public class ActivityRecognitionResult extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final ActivityRecognitionResultCreator CREATOR = new ActivityRecognitionResultCreator();
    List<DetectedActivity> a;
    long b;
    long c;
    int d;
    Bundle e;
    private final int f;

    public ActivityRecognitionResult(int i, List<DetectedActivity> list, long j, long j2, int i2, Bundle bundle) {
        this.f = i;
        this.a = list;
        this.b = j;
        this.c = j2;
        this.d = i2;
        this.e = bundle;
    }

    public ActivityRecognitionResult(DetectedActivity detectedActivity, long j, long j2) {
        this(detectedActivity, j, j2, 0, (Bundle) null);
    }

    public ActivityRecognitionResult(DetectedActivity detectedActivity, long j, long j2, int i, Bundle bundle) {
        this(Collections.singletonList(detectedActivity), j, j2, i, bundle);
    }

    public ActivityRecognitionResult(List<DetectedActivity> list, long j, long j2) {
        this(list, j, j2, 0, (Bundle) null);
    }

    public ActivityRecognitionResult(List<DetectedActivity> list, long j, long j2, int i, Bundle bundle) {
        boolean z = false;
        zzac.zzb(list != null && list.size() > 0, (Object) "Must have at least 1 detected activity");
        if (j > 0 && j2 > 0) {
            z = true;
        }
        zzac.zzb(z, (Object) "Must set times");
        this.f = 2;
        this.a = list;
        this.b = j;
        this.c = j2;
        this.d = i;
        this.e = bundle;
    }

    private static boolean a(Intent intent) {
        if (intent == null) {
            return false;
        }
        return intent.hasExtra("com.google.android.location.internal.EXTRA_ACTIVITY_RESULT");
    }

    private static boolean a(Bundle bundle, Bundle bundle2) {
        if (bundle == null && bundle2 == null) {
            return true;
        }
        if ((bundle == null && bundle2 != null) || ((bundle != null && bundle2 == null) || bundle.size() != bundle2.size())) {
            return false;
        }
        for (String str : bundle.keySet()) {
            if (!bundle2.containsKey(str)) {
                return false;
            }
            if (bundle.get(str) == null) {
                if (bundle2.get(str) != null) {
                    return false;
                }
            } else if (bundle.get(str) instanceof Bundle) {
                if (!a(bundle.getBundle(str), bundle2.getBundle(str))) {
                    return false;
                }
            } else if (!bundle.get(str).equals(bundle2.get(str))) {
                return false;
            }
        }
        return true;
    }

    private static ActivityRecognitionResult b(Intent intent) {
        if (!hasResult(intent)) {
            return null;
        }
        Object obj = intent.getExtras().get("com.google.android.location.internal.EXTRA_ACTIVITY_RESULT");
        if (obj instanceof byte[]) {
            return (ActivityRecognitionResult) zzc.zza((byte[]) obj, CREATOR);
        }
        if (obj instanceof ActivityRecognitionResult) {
            return (ActivityRecognitionResult) obj;
        }
        return null;
    }

    public static ActivityRecognitionResult extractResult(Intent intent) {
        ActivityRecognitionResult b2 = b(intent);
        if (b2 != null) {
            return b2;
        }
        List zzz = zzz(intent);
        if (zzz == null || zzz.isEmpty()) {
            return null;
        }
        return (ActivityRecognitionResult) zzz.get(zzz.size() - 1);
    }

    public static boolean hasResult(Intent intent) {
        boolean z = false;
        if (intent == null) {
            return false;
        }
        if (a(intent)) {
            return true;
        }
        List zzz = zzz(intent);
        if (zzz != null && !zzz.isEmpty()) {
            z = true;
        }
        return z;
    }

    public static boolean zzy(Intent intent) {
        if (intent == null) {
            return false;
        }
        return intent.hasExtra("com.google.android.location.internal.EXTRA_ACTIVITY_RESULT_LIST");
    }

    @Nullable
    public static List<ActivityRecognitionResult> zzz(Intent intent) {
        if (!zzy(intent)) {
            return null;
        }
        return zzc.zzb(intent, "com.google.android.location.internal.EXTRA_ACTIVITY_RESULT_LIST", CREATOR);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ActivityRecognitionResult activityRecognitionResult = (ActivityRecognitionResult) obj;
        return this.b == activityRecognitionResult.b && this.c == activityRecognitionResult.c && this.d == activityRecognitionResult.d && zzab.equal(this.a, activityRecognitionResult.a) && a(this.e, activityRecognitionResult.e);
    }

    public int getActivityConfidence(int i) {
        for (DetectedActivity detectedActivity : this.a) {
            if (detectedActivity.getType() == i) {
                return detectedActivity.getConfidence();
            }
        }
        return 0;
    }

    public long getElapsedRealtimeMillis() {
        return this.c;
    }

    public DetectedActivity getMostProbableActivity() {
        return (DetectedActivity) this.a.get(0);
    }

    public List<DetectedActivity> getProbableActivities() {
        return this.a;
    }

    public long getTime() {
        return this.b;
    }

    public int getVersionCode() {
        return this.f;
    }

    public int hashCode() {
        return zzab.hashCode(Long.valueOf(this.b), Long.valueOf(this.c), Integer.valueOf(this.d), this.a, this.e);
    }

    public String toString() {
        String valueOf = String.valueOf(this.a);
        long j = this.b;
        long j2 = this.c;
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + EACTags.DYNAMIC_AUTHENTIFICATION_TEMPLATE);
        sb.append("ActivityRecognitionResult [probableActivities=");
        sb.append(valueOf);
        sb.append(", timeMillis=");
        sb.append(j);
        sb.append(", elapsedRealtimeMillis=");
        sb.append(j2);
        sb.append("]");
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        ActivityRecognitionResultCreator.a(this, parcel, i);
    }
}
