package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzafg;
import com.google.android.gms.internal.zzafg.zzc;
import com.google.android.gms.internal.zzafg.zzg;
import com.google.android.gms.internal.zzah.zzf;
import com.google.android.gms.internal.zzah.zzi;
import com.google.android.gms.internal.zzah.zzj;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Container {
    private final Context a;
    private final String b;
    private final DataLayer c;
    private zzcx d;
    private Map<String, FunctionCallMacroCallback> e = new HashMap();
    private Map<String, FunctionCallTagCallback> f = new HashMap();
    private volatile long g;
    private volatile String h = "";

    public interface FunctionCallMacroCallback {
        Object getValue(String str, Map<String, Object> map);
    }

    public interface FunctionCallTagCallback {
        void execute(String str, Map<String, Object> map);
    }

    class zza implements com.google.android.gms.tagmanager.zzu.zza {
        private zza() {
        }

        public Object zzf(String str, Map<String, Object> map) {
            FunctionCallMacroCallback a2 = Container.this.a(str);
            if (a2 == null) {
                return null;
            }
            return a2.getValue(str, map);
        }
    }

    class zzb implements com.google.android.gms.tagmanager.zzu.zza {
        private zzb() {
        }

        public Object zzf(String str, Map<String, Object> map) {
            FunctionCallTagCallback zzov = Container.this.zzov(str);
            if (zzov != null) {
                zzov.execute(str, map);
            }
            return zzdm.zzchk();
        }
    }

    Container(Context context, DataLayer dataLayer, String str, long j, zzc zzc) {
        this.a = context;
        this.c = dataLayer;
        this.b = str;
        this.g = j;
        a(zzc);
    }

    Container(Context context, DataLayer dataLayer, String str, long j, zzj zzj) {
        this.a = context;
        this.c = dataLayer;
        this.b = str;
        this.g = j;
        a(zzj.zzxr);
        if (zzj.zzxq != null) {
            a(zzj.zzxq);
        }
    }

    private void a(zzc zzc) {
        this.h = zzc.getVersion();
        zzc zzc2 = zzc;
        zzcx zzcx = new zzcx(this.a, zzc2, this.c, new zza(), new zzb(), b(this.h));
        a(zzcx);
        if (getBoolean("_gtm.loadEventEnabled")) {
            this.c.pushEvent("gtm.load", DataLayer.mapOf("gtm.id", this.b));
        }
    }

    private void a(zzf zzf) {
        if (zzf == null) {
            throw new NullPointerException();
        }
        try {
            a(zzafg.zzb(zzf));
        } catch (zzg e2) {
            String valueOf = String.valueOf(zzf);
            String valueOf2 = String.valueOf(e2.toString());
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 46 + String.valueOf(valueOf2).length());
            sb.append("Not loading resource: ");
            sb.append(valueOf);
            sb.append(" because it is invalid: ");
            sb.append(valueOf2);
            zzbo.e(sb.toString());
        }
    }

    private synchronized void a(zzcx zzcx) {
        this.d = zzcx;
    }

    private void a(zzi[] zziArr) {
        ArrayList arrayList = new ArrayList();
        for (zzi add : zziArr) {
            arrayList.add(add);
        }
        b().a((List<zzi>) arrayList);
    }

    private synchronized zzcx b() {
        return this.d;
    }

    /* access modifiers changed from: 0000 */
    public FunctionCallMacroCallback a(String str) {
        FunctionCallMacroCallback functionCallMacroCallback;
        synchronized (this.e) {
            functionCallMacroCallback = (FunctionCallMacroCallback) this.e.get(str);
        }
        return functionCallMacroCallback;
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        this.d = null;
    }

    /* access modifiers changed from: 0000 */
    public zzaj b(String str) {
        zzcj.a().b().equals(zza.CONTAINER_DEBUG);
        return new zzbw();
    }

    public boolean getBoolean(String str) {
        String sb;
        zzcx b2 = b();
        if (b2 == null) {
            sb = "getBoolean called for closed container.";
        } else {
            try {
                return zzdm.zzk((com.google.android.gms.internal.zzai.zza) b2.b(str).a()).booleanValue();
            } catch (Exception e2) {
                String valueOf = String.valueOf(e2.getMessage());
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf).length() + 66);
                sb2.append("Calling getBoolean() threw an exception: ");
                sb2.append(valueOf);
                sb2.append(" Returning default value.");
                sb = sb2.toString();
            }
        }
        zzbo.e(sb);
        return zzdm.zzchi().booleanValue();
    }

    public String getContainerId() {
        return this.b;
    }

    public double getDouble(String str) {
        String sb;
        zzcx b2 = b();
        if (b2 == null) {
            sb = "getDouble called for closed container.";
        } else {
            try {
                return zzdm.zzj((com.google.android.gms.internal.zzai.zza) b2.b(str).a()).doubleValue();
            } catch (Exception e2) {
                String valueOf = String.valueOf(e2.getMessage());
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf).length() + 65);
                sb2.append("Calling getDouble() threw an exception: ");
                sb2.append(valueOf);
                sb2.append(" Returning default value.");
                sb = sb2.toString();
            }
        }
        zzbo.e(sb);
        return zzdm.zzchh().doubleValue();
    }

    public long getLastRefreshTime() {
        return this.g;
    }

    public long getLong(String str) {
        String sb;
        zzcx b2 = b();
        if (b2 == null) {
            sb = "getLong called for closed container.";
        } else {
            try {
                return zzdm.zzi((com.google.android.gms.internal.zzai.zza) b2.b(str).a()).longValue();
            } catch (Exception e2) {
                String valueOf = String.valueOf(e2.getMessage());
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf).length() + 63);
                sb2.append("Calling getLong() threw an exception: ");
                sb2.append(valueOf);
                sb2.append(" Returning default value.");
                sb = sb2.toString();
            }
        }
        zzbo.e(sb);
        return zzdm.zzchg().longValue();
    }

    public String getString(String str) {
        String sb;
        zzcx b2 = b();
        if (b2 == null) {
            sb = "getString called for closed container.";
        } else {
            try {
                return zzdm.zzg((com.google.android.gms.internal.zzai.zza) b2.b(str).a());
            } catch (Exception e2) {
                String valueOf = String.valueOf(e2.getMessage());
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf).length() + 65);
                sb2.append("Calling getString() threw an exception: ");
                sb2.append(valueOf);
                sb2.append(" Returning default value.");
                sb = sb2.toString();
            }
        }
        zzbo.e(sb);
        return zzdm.zzchk();
    }

    public boolean isDefault() {
        return getLastRefreshTime() == 0;
    }

    public void registerFunctionCallMacroCallback(String str, FunctionCallMacroCallback functionCallMacroCallback) {
        if (functionCallMacroCallback == null) {
            throw new NullPointerException("Macro handler must be non-null");
        }
        synchronized (this.e) {
            this.e.put(str, functionCallMacroCallback);
        }
    }

    public void registerFunctionCallTagCallback(String str, FunctionCallTagCallback functionCallTagCallback) {
        if (functionCallTagCallback == null) {
            throw new NullPointerException("Tag callback must be non-null");
        }
        synchronized (this.f) {
            this.f.put(str, functionCallTagCallback);
        }
    }

    public void unregisterFunctionCallMacroCallback(String str) {
        synchronized (this.e) {
            this.e.remove(str);
        }
    }

    public void unregisterFunctionCallTagCallback(String str) {
        synchronized (this.f) {
            this.f.remove(str);
        }
    }

    public String zzcdw() {
        return this.h;
    }

    public FunctionCallTagCallback zzov(String str) {
        FunctionCallTagCallback functionCallTagCallback;
        synchronized (this.f) {
            functionCallTagCallback = (FunctionCallTagCallback) this.f.get(str);
        }
        return functionCallTagCallback;
    }

    public void zzow(String str) {
        b().a(str);
    }
}
