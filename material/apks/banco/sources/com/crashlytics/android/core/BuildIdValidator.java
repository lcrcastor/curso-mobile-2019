package com.crashlytics.android.core;

import android.util.Log;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.CommonUtils;

class BuildIdValidator {
    private final String a;
    private final boolean b;

    /* access modifiers changed from: protected */
    public String b(String str, String str2) {
        return "This app relies on Crashlytics. Please sign up for access at https://fabric.io/sign_up,\ninstall an Android build tool and ask a team member to invite you to this app's organization.";
    }

    public BuildIdValidator(String str, boolean z) {
        this.a = str;
        this.b = z;
    }

    public void a(String str, String str2) {
        if (CommonUtils.isNullOrEmpty(this.a) && this.b) {
            String b2 = b(str, str2);
            Log.e(CrashlyticsCore.TAG, ".");
            Log.e(CrashlyticsCore.TAG, ".     |  | ");
            Log.e(CrashlyticsCore.TAG, ".     |  |");
            Log.e(CrashlyticsCore.TAG, ".     |  |");
            Log.e(CrashlyticsCore.TAG, ".   \\ |  | /");
            Log.e(CrashlyticsCore.TAG, ".    \\    /");
            Log.e(CrashlyticsCore.TAG, ".     \\  /");
            Log.e(CrashlyticsCore.TAG, ".      \\/");
            Log.e(CrashlyticsCore.TAG, ".");
            Log.e(CrashlyticsCore.TAG, b2);
            Log.e(CrashlyticsCore.TAG, ".");
            Log.e(CrashlyticsCore.TAG, ".      /\\");
            Log.e(CrashlyticsCore.TAG, ".     /  \\");
            Log.e(CrashlyticsCore.TAG, ".    /    \\");
            Log.e(CrashlyticsCore.TAG, ".   / |  | \\");
            Log.e(CrashlyticsCore.TAG, ".     |  |");
            Log.e(CrashlyticsCore.TAG, ".     |  |");
            Log.e(CrashlyticsCore.TAG, ".     |  |");
            Log.e(CrashlyticsCore.TAG, ".");
            throw new CrashlyticsMissingDependencyException(b2);
        } else if (!this.b) {
            Fabric.getLogger().d(CrashlyticsCore.TAG, "Configured not to require a build ID.");
        }
    }
}
