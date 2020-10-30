package com.crashlytics.android.core;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.persistence.FileStore;
import java.io.File;
import java.io.IOException;

class CrashlyticsFileMarker {
    private final String a;
    private final FileStore b;

    public CrashlyticsFileMarker(String str, FileStore fileStore) {
        this.a = str;
        this.b = fileStore;
    }

    public boolean a() {
        try {
            return d().createNewFile();
        } catch (IOException e) {
            Logger logger = Fabric.getLogger();
            String str = CrashlyticsCore.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Error creating marker: ");
            sb.append(this.a);
            logger.e(str, sb.toString(), e);
            return false;
        }
    }

    public boolean b() {
        return d().exists();
    }

    public boolean c() {
        return d().delete();
    }

    private File d() {
        return new File(this.b.getFilesDir(), this.a);
    }
}
