package io.fabric.sdk.android.services.persistence;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Environment;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import java.io.File;

public class FileStoreImpl implements FileStore {
    private final Context a;
    private final String b;
    private final String c;

    public FileStoreImpl(Kit kit) {
        if (kit.getContext() == null) {
            throw new IllegalStateException("Cannot get directory before context has been set. Call Fabric.with() first");
        }
        this.a = kit.getContext();
        this.b = kit.getPath();
        StringBuilder sb = new StringBuilder();
        sb.append("Android/");
        sb.append(this.a.getPackageName());
        this.c = sb.toString();
    }

    public File getCacheDir() {
        return a(this.a.getCacheDir());
    }

    public File getExternalCacheDir() {
        File file;
        if (!a()) {
            file = null;
        } else if (VERSION.SDK_INT >= 8) {
            file = this.a.getExternalCacheDir();
        } else {
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            StringBuilder sb = new StringBuilder();
            sb.append(this.c);
            sb.append("/cache/");
            sb.append(this.b);
            file = new File(externalStorageDirectory, sb.toString());
        }
        return a(file);
    }

    public File getFilesDir() {
        return a(this.a.getFilesDir());
    }

    @TargetApi(8)
    public File getExternalFilesDir() {
        File file = null;
        if (a()) {
            if (VERSION.SDK_INT >= 8) {
                file = this.a.getExternalFilesDir(null);
            } else {
                File externalStorageDirectory = Environment.getExternalStorageDirectory();
                StringBuilder sb = new StringBuilder();
                sb.append(this.c);
                sb.append("/files/");
                sb.append(this.b);
                file = new File(externalStorageDirectory, sb.toString());
            }
        }
        return a(file);
    }

    /* access modifiers changed from: 0000 */
    public File a(File file) {
        if (file == null) {
            Fabric.getLogger().d(Fabric.TAG, "Null File");
        } else if (file.exists() || file.mkdirs()) {
            return file;
        } else {
            Fabric.getLogger().w(Fabric.TAG, "Couldn't create file");
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public boolean a() {
        if ("mounted".equals(Environment.getExternalStorageState())) {
            return true;
        }
        Fabric.getLogger().w(Fabric.TAG, "External Storage is not mounted and/or writable\nHave you declared android.permission.WRITE_EXTERNAL_STORAGE in the manifest?");
        return false;
    }
}
