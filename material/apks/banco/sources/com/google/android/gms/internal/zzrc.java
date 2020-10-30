package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Map.Entry;
import java.util.WeakHashMap;

@TargetApi(11)
public final class zzrc extends Fragment implements zzrb {
    private static WeakHashMap<Activity, WeakReference<zzrc>> a = new WeakHashMap<>();
    private Map<String, zzra> b = new ArrayMap();
    /* access modifiers changed from: private */
    public int c = 0;
    /* access modifiers changed from: private */
    public Bundle d;

    private void a(final String str, @NonNull final zzra zzra) {
        if (this.c > 0) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    if (zzrc.this.c >= 1) {
                        zzra.onCreate(zzrc.this.d != null ? zzrc.this.d.getBundle(str) : null);
                    }
                    if (zzrc.this.c >= 2) {
                        zzra.onStart();
                    }
                    if (zzrc.this.c >= 3) {
                        zzra.onStop();
                    }
                    if (zzrc.this.c >= 4) {
                        zzra.onDestroy();
                    }
                }
            });
        }
    }

    public static zzrc zzt(Activity activity) {
        WeakReference weakReference = (WeakReference) a.get(activity);
        if (weakReference != null) {
            zzrc zzrc = (zzrc) weakReference.get();
            if (zzrc != null) {
                return zzrc;
            }
        }
        try {
            zzrc zzrc2 = (zzrc) activity.getFragmentManager().findFragmentByTag("LifecycleFragmentImpl");
            if (zzrc2 == null || zzrc2.isRemoving()) {
                zzrc2 = new zzrc();
                activity.getFragmentManager().beginTransaction().add(zzrc2, "LifecycleFragmentImpl").commitAllowingStateLoss();
            }
            a.put(activity, new WeakReference(zzrc2));
            return zzrc2;
        } catch (ClassCastException e) {
            throw new IllegalStateException("Fragment with tag LifecycleFragmentImpl is not a LifecycleFragmentImpl", e);
        }
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        super.dump(str, fileDescriptor, printWriter, strArr);
        for (zzra dump : this.b.values()) {
            dump.dump(str, fileDescriptor, printWriter, strArr);
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        for (zzra onActivityResult : this.b.values()) {
            onActivityResult.onActivityResult(i, i2, intent);
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.c = 1;
        this.d = bundle;
        for (Entry entry : this.b.entrySet()) {
            ((zzra) entry.getValue()).onCreate(bundle != null ? bundle.getBundle((String) entry.getKey()) : null);
        }
    }

    public void onDestroy() {
        super.onStop();
        this.c = 4;
        for (zzra onDestroy : this.b.values()) {
            onDestroy.onDestroy();
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (bundle != null) {
            for (Entry entry : this.b.entrySet()) {
                Bundle bundle2 = new Bundle();
                ((zzra) entry.getValue()).onSaveInstanceState(bundle2);
                bundle.putBundle((String) entry.getKey(), bundle2);
            }
        }
    }

    public void onStart() {
        super.onStop();
        this.c = 2;
        for (zzra onStart : this.b.values()) {
            onStart.onStart();
        }
    }

    public void onStop() {
        super.onStop();
        this.c = 3;
        for (zzra onStop : this.b.values()) {
            onStop.onStop();
        }
    }

    public <T extends zzra> T zza(String str, Class<T> cls) {
        return (zzra) cls.cast(this.b.get(str));
    }

    public void zza(String str, @NonNull zzra zzra) {
        if (!this.b.containsKey(str)) {
            this.b.put(str, zzra);
            a(str, zzra);
            return;
        }
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 59);
        sb.append("LifecycleCallback with tag ");
        sb.append(str);
        sb.append(" already added to this fragment.");
        throw new IllegalArgumentException(sb.toString());
    }

    public Activity zzasq() {
        return getActivity();
    }
}
