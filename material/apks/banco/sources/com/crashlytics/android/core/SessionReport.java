package com.crashlytics.android.core;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class SessionReport implements Report {
    private final File a;
    private final File[] b;
    private final Map<String, String> c;

    public SessionReport(File file) {
        this(file, Collections.emptyMap());
    }

    public SessionReport(File file, Map<String, String> map) {
        this.a = file;
        this.b = new File[]{file};
        this.c = new HashMap(map);
        if (this.a.length() == 0) {
            this.c.putAll(ReportUploader.a);
        }
    }

    public File c() {
        return this.a;
    }

    public File[] d() {
        return this.b;
    }

    public String a() {
        return c().getName();
    }

    public String b() {
        String a2 = a();
        return a2.substring(0, a2.lastIndexOf(46));
    }

    public Map<String, String> e() {
        return Collections.unmodifiableMap(this.c);
    }

    public void f() {
        Logger logger = Fabric.getLogger();
        String str = CrashlyticsCore.TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("Removing report at ");
        sb.append(this.a.getPath());
        logger.d(str, sb.toString());
        this.a.delete();
    }
}
