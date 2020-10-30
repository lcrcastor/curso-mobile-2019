package com.google.android.gms.internal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.ArrayMap;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Map.Entry;
import java.util.WeakHashMap;

public final class zzrn extends Fragment implements zzrb {
    private static WeakHashMap<FragmentActivity, WeakReference<zzrn>> a = new WeakHashMap<>();
    private Map<String, zzra> b = new ArrayMap();
    /* access modifiers changed from: private */
    public int c = 0;
    /* access modifiers changed from: private */
    public Bundle d;

    private void a(final String str, @NonNull final zzra zzra) {
        if (this.c > 0) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    if (zzrn.this.c >= 1) {
                        zzra.onCreate(zzrn.this.d != null ? zzrn.this.d.getBundle(str) : null);
                    }
                    if (zzrn.this.c >= 2) {
                        zzra.onStart();
                    }
                    if (zzrn.this.c >= 3) {
                        zzra.onStop();
                    }
                    if (zzrn.this.c >= 4) {
                        zzra.onDestroy();
                    }
                }
            });
        }
    }

    public static zzrn zza(FragmentActivity fragmentActivity) {
        WeakReference weakReference = (WeakReference) a.get(fragmentActivity);
        if (weakReference != null) {
            zzrn zzrn = (zzrn) weakReference.get();
            if (zzrn != null) {
                return zzrn;
            }
        }
        try {
            zzrn zzrn2 = (zzrn) fragmentActivity.getSupportFragmentManager().findFragmentByTag("SupportLifecycleFragmentImpl");
            if (zzrn2 == null || zzrn2.isRemoving()) {
                zzrn2 = new zzrn();
                fragmentActivity.getSupportFragmentManager().beginTransaction().add((Fragment) zzrn2, "SupportLifecycleFragmentImpl").commitAllowingStateLoss();
            }
            a.put(fragmentActivity, new WeakReference(zzrn2));
            return zzrn2;
        } catch (ClassCastException e) {
            throw new IllegalStateException("Fragment with tag SupportLifecycleFragmentImpl is not a SupportLifecycleFragmentImpl", e);
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

    /* renamed from: zzass */
    public FragmentActivity zzasq() {
        return getActivity();
    }
}
