package com.android.installreferrer.api;

import android.os.Bundle;

public class ReferrerDetails {
    private final Bundle a;

    public ReferrerDetails(Bundle bundle) {
        this.a = bundle;
    }

    public String getInstallReferrer() {
        return this.a.getString("install_referrer");
    }

    public long getReferrerClickTimestampSeconds() {
        return this.a.getLong("referrer_click_timestamp_seconds");
    }

    public long getInstallBeginTimestampSeconds() {
        return this.a.getLong("install_begin_timestamp_seconds");
    }
}
