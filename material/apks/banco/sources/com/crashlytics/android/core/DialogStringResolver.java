package com.crashlytics.android.core;

import android.content.Context;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.settings.PromptSettingsData;

class DialogStringResolver {
    private final Context a;
    private final PromptSettingsData b;

    public DialogStringResolver(Context context, PromptSettingsData promptSettingsData) {
        this.a = context;
        this.b = promptSettingsData;
    }

    public String a() {
        return a("com.crashlytics.CrashSubmissionPromptTitle", this.b.title);
    }

    public String b() {
        return a("com.crashlytics.CrashSubmissionPromptMessage", this.b.message);
    }

    public String c() {
        return a("com.crashlytics.CrashSubmissionSendTitle", this.b.sendButtonTitle);
    }

    public String d() {
        return a("com.crashlytics.CrashSubmissionAlwaysSendTitle", this.b.alwaysSendButtonTitle);
    }

    public String e() {
        return a("com.crashlytics.CrashSubmissionCancelTitle", this.b.cancelButtonTitle);
    }

    private String a(String str, String str2) {
        return b(CommonUtils.getStringsFileValue(this.a, str), str2);
    }

    private String b(String str, String str2) {
        return a(str) ? str2 : str;
    }

    private boolean a(String str) {
        return str == null || str.length() == 0;
    }
}
