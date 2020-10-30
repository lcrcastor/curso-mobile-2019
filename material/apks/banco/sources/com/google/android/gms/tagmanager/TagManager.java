package com.google.android.gms.tagmanager;

import android.annotation.TargetApi;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RawRes;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.common.api.PendingResult;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TagManager {
    private static TagManager g;
    private final zza a;
    private final Context b;
    private final DataLayer c;
    private final zzdb d;
    private final ConcurrentMap<String, zzo> e;
    private final zzt f;

    public interface zza {
        zzp zza(Context context, TagManager tagManager, Looper looper, String str, int i, zzt zzt);
    }

    TagManager(Context context, zza zza2, DataLayer dataLayer, zzdb zzdb) {
        if (context == null) {
            throw new NullPointerException("context cannot be null");
        }
        this.b = context.getApplicationContext();
        this.d = zzdb;
        this.a = zza2;
        this.e = new ConcurrentHashMap();
        this.c = dataLayer;
        this.c.a((zzb) new zzb() {
            public void a(Map<String, Object> map) {
                Object obj = map.get("event");
                if (obj != null) {
                    TagManager.this.a(obj.toString());
                }
            }
        });
        this.c.a((zzb) new zzd(this.b));
        this.f = new zzt();
        a();
        b();
    }

    @TargetApi(14)
    private void a() {
        if (VERSION.SDK_INT >= 14) {
            this.b.registerComponentCallbacks(new ComponentCallbacks2() {
                public void onConfigurationChanged(Configuration configuration) {
                }

                public void onLowMemory() {
                }

                public void onTrimMemory(int i) {
                    if (i == 20) {
                        TagManager.this.dispatch();
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void a(String str) {
        for (zzo a2 : this.e.values()) {
            a2.a(str);
        }
    }

    private void b() {
        zza.zzdz(this.b);
    }

    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public static TagManager getInstance(Context context) {
        TagManager tagManager;
        synchronized (TagManager.class) {
            if (g == null) {
                if (context == null) {
                    zzbo.e("TagManager.getInstance requires non-null context.");
                    throw new NullPointerException();
                }
                g = new TagManager(context, new zza() {
                    public zzp zza(Context context, TagManager tagManager, Looper looper, String str, int i, zzt zzt) {
                        zzp zzp = new zzp(context, tagManager, looper, str, i, zzt);
                        return zzp;
                    }
                }, new DataLayer(new zzx(context)), zzdc.a());
            }
            tagManager = g;
        }
        return tagManager;
    }

    /* access modifiers changed from: 0000 */
    public synchronized boolean a(Uri uri) {
        boolean z;
        zzcj a2 = zzcj.a();
        if (a2.a(uri)) {
            String d2 = a2.d();
            switch (a2.b()) {
                case NONE:
                    zzo zzo = (zzo) this.e.get(d2);
                    if (zzo != null) {
                        zzo.b(null);
                        zzo.refresh();
                        break;
                    }
                    break;
                case CONTAINER:
                case CONTAINER_DEBUG:
                    for (String str : this.e.keySet()) {
                        zzo zzo2 = (zzo) this.e.get(str);
                        if (str.equals(d2)) {
                            zzo2.b(a2.c());
                        } else if (zzo2.b() != null) {
                            zzo2.b(null);
                        }
                        zzo2.refresh();
                    }
                    break;
            }
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    public void dispatch() {
        this.d.dispatch();
    }

    public DataLayer getDataLayer() {
        return this.c;
    }

    public PendingResult<ContainerHolder> loadContainerDefaultOnly(String str, @RawRes int i) {
        zzp zza2 = this.a.zza(this.b, this, null, str, i, this.f);
        zza2.zzceb();
        return zza2;
    }

    public PendingResult<ContainerHolder> loadContainerDefaultOnly(String str, @RawRes int i, Handler handler) {
        zzp zza2 = this.a.zza(this.b, this, handler.getLooper(), str, i, this.f);
        zza2.zzceb();
        return zza2;
    }

    public PendingResult<ContainerHolder> loadContainerPreferFresh(String str, @RawRes int i) {
        zzp zza2 = this.a.zza(this.b, this, null, str, i, this.f);
        zza2.zzced();
        return zza2;
    }

    public PendingResult<ContainerHolder> loadContainerPreferFresh(String str, @RawRes int i, Handler handler) {
        zzp zza2 = this.a.zza(this.b, this, handler.getLooper(), str, i, this.f);
        zza2.zzced();
        return zza2;
    }

    public PendingResult<ContainerHolder> loadContainerPreferNonDefault(String str, @RawRes int i) {
        zzp zza2 = this.a.zza(this.b, this, null, str, i, this.f);
        zza2.zzcec();
        return zza2;
    }

    public PendingResult<ContainerHolder> loadContainerPreferNonDefault(String str, @RawRes int i, Handler handler) {
        zzp zza2 = this.a.zza(this.b, this, handler.getLooper(), str, i, this.f);
        zza2.zzcec();
        return zza2;
    }

    public void setVerboseLoggingEnabled(boolean z) {
        zzbo.setLogLevel(z ? 2 : 5);
    }

    public int zza(zzo zzo) {
        this.e.put(zzo.a(), zzo);
        return this.e.size();
    }

    public boolean zzb(zzo zzo) {
        return this.e.remove(zzo.a()) != null;
    }
}
