package com.rsa.mobilesdk.sdk;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class EmulatorDetection {
    private static Set<String> a = new HashSet();
    private ArrayList<Boolean> b = new ArrayList<>(3);

    static {
        a.add(CommonUtils.GOOGLE_SDK);
        a.add(CommonUtils.SDK);
        a.add("sdk_x86");
        a.add("vbox86p");
    }

    public EmulatorDetection(Context context) {
        this.b.add(Boolean.valueOf(a()));
        this.b.add(Boolean.valueOf(b()));
        this.b.add(Boolean.valueOf(c()));
    }

    public int isEmulator() {
        Iterator it = this.b.iterator();
        int i = 0;
        while (it.hasNext()) {
            i = (i << 1) + (((Boolean) it.next()).booleanValue() ? 1 : 0);
        }
        return i;
    }

    private boolean a() {
        return Build.FINGERPRINT.contains("generic");
    }

    private boolean b() {
        return a.contains(Build.PRODUCT);
    }

    @TargetApi(8)
    private boolean c() {
        if (VERSION.SDK_INT >= 8) {
            return "goldfish".equals(Build.HARDWARE);
        }
        return false;
    }
}
