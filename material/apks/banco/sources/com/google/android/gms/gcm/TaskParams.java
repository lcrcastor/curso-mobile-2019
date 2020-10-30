package com.google.android.gms.gcm;

import android.os.Bundle;

public class TaskParams {
    private final String a;
    private final Bundle b;

    public TaskParams(String str) {
        this(str, null);
    }

    public TaskParams(String str, Bundle bundle) {
        this.a = str;
        this.b = bundle;
    }

    public Bundle getExtras() {
        return this.b;
    }

    public String getTag() {
        return this.a;
    }
}
