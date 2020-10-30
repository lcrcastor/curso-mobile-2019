package com.crashlytics.android.core;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class InvalidSessionReport implements Report {
    private final File[] a;
    private final Map<String, String> b = new HashMap(ReportUploader.a);
    private final String c;

    public InvalidSessionReport(String str, File[] fileArr) {
        this.a = fileArr;
        this.c = str;
    }

    public String a() {
        return this.a[0].getName();
    }

    public String b() {
        return this.c;
    }

    public File c() {
        return this.a[0];
    }

    public File[] d() {
        return this.a;
    }

    public Map<String, String> e() {
        return Collections.unmodifiableMap(this.b);
    }

    public void f() {
        File[] fileArr;
        for (File file : this.a) {
            Logger logger = Fabric.getLogger();
            String str = CrashlyticsCore.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Removing invalid report file at ");
            sb.append(file.getPath());
            logger.d(str, sb.toString());
            file.delete();
        }
    }
}
