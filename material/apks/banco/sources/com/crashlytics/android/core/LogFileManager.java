package com.crashlytics.android.core;

import android.content.Context;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.persistence.FileStore;
import java.io.File;
import java.util.Set;

class LogFileManager {
    private static final NoopLogStore a = new NoopLogStore();
    private final Context b;
    private final FileStore c;
    private FileLogStore d;

    static final class NoopLogStore implements FileLogStore {
        public ByteString a() {
            return null;
        }

        public void a(long j, String str) {
        }

        public void b() {
        }

        public void c() {
        }

        private NoopLogStore() {
        }
    }

    public LogFileManager(Context context, FileStore fileStore) {
        this(context, fileStore, null);
    }

    public LogFileManager(Context context, FileStore fileStore, String str) {
        this.b = context;
        this.c = fileStore;
        this.d = a;
        a(str);
    }

    public final void a(String str) {
        this.d.b();
        this.d = a;
        if (str != null) {
            if (!c()) {
                Fabric.getLogger().d(CrashlyticsCore.TAG, "Preferences requested no custom logs. Aborting log file creation.");
            } else {
                a(b(str), 65536);
            }
        }
    }

    public void a(long j, String str) {
        this.d.a(j, str);
    }

    public ByteString a() {
        return this.d.a();
    }

    public void b() {
        this.d.c();
    }

    public void a(Set<String> set) {
        File[] listFiles = d().listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                if (!set.contains(a(file))) {
                    file.delete();
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(File file, int i) {
        this.d = new QueueFileLogStore(file, i);
    }

    private File b(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("crashlytics-userlog-");
        sb.append(str);
        sb.append(".temp");
        return new File(d(), sb.toString());
    }

    private String a(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".temp");
        if (lastIndexOf == -1) {
            return name;
        }
        return name.substring("crashlytics-userlog-".length(), lastIndexOf);
    }

    private boolean c() {
        return CommonUtils.getBooleanResourceValue(this.b, "com.crashlytics.CollectCustomLogs", true);
    }

    private File d() {
        File file = new File(this.c.getFilesDir(), "log-files");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }
}
