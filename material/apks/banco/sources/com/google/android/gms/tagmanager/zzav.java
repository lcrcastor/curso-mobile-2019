package com.google.android.gms.tagmanager;

import android.content.Context;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.LinkedBlockingQueue;

class zzav extends Thread implements zzau {
    private static zzav d;
    private final LinkedBlockingQueue<Runnable> a = new LinkedBlockingQueue<>();
    private volatile boolean b = false;
    private volatile boolean c = false;
    /* access modifiers changed from: private */
    public volatile zzaw e;
    /* access modifiers changed from: private */
    public final Context f;

    private zzav(Context context) {
        super("GAThread");
        if (context != null) {
            context = context.getApplicationContext();
        }
        this.f = context;
        start();
    }

    static zzav a(Context context) {
        if (d == null) {
            d = new zzav(context);
        }
        return d;
    }

    private String a(Throwable th) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        th.printStackTrace(printStream);
        printStream.flush();
        return new String(byteArrayOutputStream.toByteArray());
    }

    public void a(Runnable runnable) {
        this.a.add(runnable);
    }

    public void a(String str) {
        a(str, System.currentTimeMillis());
    }

    /* access modifiers changed from: 0000 */
    public void a(String str, long j) {
        final long j2 = j;
        final String str2 = str;
        AnonymousClass1 r0 = new Runnable() {
            public void run() {
                if (zzav.this.e == null) {
                    zzdc a2 = zzdc.a();
                    a2.a(zzav.this.f, this);
                    zzav.this.e = a2.b();
                }
                zzav.this.e.a(j2, str2);
            }
        };
        a((Runnable) r0);
    }

    public void run() {
        while (!this.c) {
            try {
                Runnable runnable = (Runnable) this.a.take();
                if (!this.b) {
                    runnable.run();
                }
            } catch (InterruptedException e2) {
                zzbo.zzde(e2.toString());
            } catch (Throwable th) {
                String str = "Error on Google TagManager Thread: ";
                String valueOf = String.valueOf(a(th));
                zzbo.e(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
                zzbo.e("Google TagManager is shutting down.");
                this.b = true;
            }
        }
    }
}
