package com.crashlytics.android.core;

import android.content.Context;
import android.os.Bundle;

class ManifestUnityVersionProvider implements UnityVersionProvider {
    private final Context a;
    private final String b;

    public ManifestUnityVersionProvider(Context context, String str) {
        this.a = context;
        this.b = str;
    }

    public String a() {
        try {
            Bundle bundle = this.a.getPackageManager().getApplicationInfo(this.b, 128).metaData;
            if (bundle != null) {
                return bundle.getString("io.fabric.unity.crashlytics.version");
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }
}
