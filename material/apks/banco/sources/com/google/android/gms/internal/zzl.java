package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class zzl {
    private AtomicInteger a;
    private final Map<String, Queue<zzk<?>>> b;
    private final Set<zzk<?>> c;
    private final PriorityBlockingQueue<zzk<?>> d;
    private final PriorityBlockingQueue<zzk<?>> e;
    private final zzb f;
    private final zzf g;
    private final zzn h;
    private zzg[] i;
    private zzc j;
    private List<zza> k;

    public interface zza<T> {
        void zzg(zzk<T> zzk);
    }

    public zzl(zzb zzb, zzf zzf) {
        this(zzb, zzf, 4);
    }

    public zzl(zzb zzb, zzf zzf, int i2) {
        this(zzb, zzf, i2, new zze(new Handler(Looper.getMainLooper())));
    }

    public zzl(zzb zzb, zzf zzf, int i2, zzn zzn) {
        this.a = new AtomicInteger();
        this.b = new HashMap();
        this.c = new HashSet();
        this.d = new PriorityBlockingQueue<>();
        this.e = new PriorityBlockingQueue<>();
        this.k = new ArrayList();
        this.f = zzb;
        this.g = zzf;
        this.i = new zzg[i2];
        this.h = zzn;
    }

    /* access modifiers changed from: 0000 */
    public <T> void a(zzk<T> zzk) {
        synchronized (this.c) {
            this.c.remove(zzk);
        }
        synchronized (this.k) {
            for (zza zzg : this.k) {
                zzg.zzg(zzk);
            }
        }
        if (zzk.zzq()) {
            synchronized (this.b) {
                String zzg2 = zzk.zzg();
                Queue queue = (Queue) this.b.remove(zzg2);
                if (queue != null) {
                    if (zzs.DEBUG) {
                        zzs.zza("Releasing %d waiting requests for cacheKey=%s.", Integer.valueOf(queue.size()), zzg2);
                    }
                    this.d.addAll(queue);
                }
            }
        }
    }

    public int getSequenceNumber() {
        return this.a.incrementAndGet();
    }

    public void start() {
        stop();
        this.j = new zzc(this.d, this.e, this.f, this.h);
        this.j.start();
        for (int i2 = 0; i2 < this.i.length; i2++) {
            zzg zzg = new zzg(this.e, this.g, this.f, this.h);
            this.i[i2] = zzg;
            zzg.start();
        }
    }

    public void stop() {
        if (this.j != null) {
            this.j.quit();
        }
        for (int i2 = 0; i2 < this.i.length; i2++) {
            if (this.i[i2] != null) {
                this.i[i2].quit();
            }
        }
    }

    public <T> zzk<T> zze(zzk<T> zzk) {
        zzk.zza(this);
        synchronized (this.c) {
            this.c.add(zzk);
        }
        zzk.zza(getSequenceNumber());
        zzk.zzc("add-to-queue");
        if (!zzk.zzq()) {
            this.e.add(zzk);
            return zzk;
        }
        synchronized (this.b) {
            String zzg = zzk.zzg();
            if (this.b.containsKey(zzg)) {
                Queue queue = (Queue) this.b.get(zzg);
                if (queue == null) {
                    queue = new LinkedList();
                }
                queue.add(zzk);
                this.b.put(zzg, queue);
                if (zzs.DEBUG) {
                    zzs.zza("Request for cacheKey=%s is in flight, putting on hold.", zzg);
                }
            } else {
                this.b.put(zzg, null);
                this.d.add(zzk);
            }
        }
        return zzk;
    }
}
