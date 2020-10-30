package com.google.android.gms.analytics;

import android.content.Context;
import com.google.android.gms.analytics.HitBuilders.ExceptionBuilder;
import com.google.android.gms.analytics.internal.zzae;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;

public class ExceptionReporter implements UncaughtExceptionHandler {
    private final UncaughtExceptionHandler a;
    private final Tracker b;
    private final Context c;
    private ExceptionParser d;
    private GoogleAnalytics e;

    public ExceptionReporter(Tracker tracker, UncaughtExceptionHandler uncaughtExceptionHandler, Context context) {
        if (tracker == null) {
            throw new NullPointerException("tracker cannot be null");
        } else if (context == null) {
            throw new NullPointerException("context cannot be null");
        } else {
            this.a = uncaughtExceptionHandler;
            this.b = tracker;
            this.d = new StandardExceptionParser(context, new ArrayList());
            this.c = context.getApplicationContext();
            String str = "ExceptionReporter created, original handler is ";
            String valueOf = String.valueOf(uncaughtExceptionHandler == null ? "null" : uncaughtExceptionHandler.getClass().getName());
            zzae.v(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        }
    }

    /* access modifiers changed from: 0000 */
    public GoogleAnalytics a() {
        if (this.e == null) {
            this.e = GoogleAnalytics.getInstance(this.c);
        }
        return this.e;
    }

    /* access modifiers changed from: 0000 */
    public UncaughtExceptionHandler b() {
        return this.a;
    }

    public ExceptionParser getExceptionParser() {
        return this.d;
    }

    public void setExceptionParser(ExceptionParser exceptionParser) {
        this.d = exceptionParser;
    }

    public void uncaughtException(Thread thread, Throwable th) {
        String str = "UncaughtException";
        if (this.d != null) {
            str = this.d.getDescription(thread != null ? thread.getName() : null, th);
        }
        String str2 = "Reporting uncaught exception: ";
        String valueOf = String.valueOf(str);
        zzae.v(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        this.b.send(new ExceptionBuilder().setDescription(str).setFatal(true).build());
        GoogleAnalytics a2 = a();
        a2.dispatchLocalHits();
        a2.b();
        if (this.a != null) {
            zzae.v("Passing exception to the original handler");
            this.a.uncaughtException(thread, th);
        }
    }
}
