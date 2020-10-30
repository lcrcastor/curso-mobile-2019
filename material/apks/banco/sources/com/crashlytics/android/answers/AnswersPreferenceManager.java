package com.crashlytics.android.answers;

import android.annotation.SuppressLint;
import android.content.Context;
import io.fabric.sdk.android.services.persistence.PreferenceStore;
import io.fabric.sdk.android.services.persistence.PreferenceStoreImpl;

class AnswersPreferenceManager {
    private final PreferenceStore a;

    public static AnswersPreferenceManager a(Context context) {
        return new AnswersPreferenceManager(new PreferenceStoreImpl(context, "settings"));
    }

    AnswersPreferenceManager(PreferenceStore preferenceStore) {
        this.a = preferenceStore;
    }

    @SuppressLint({"CommitPrefEdits"})
    public void a() {
        this.a.save(this.a.edit().putBoolean("analytics_launched", true));
    }

    @SuppressLint({"CommitPrefEdits"})
    public boolean b() {
        return this.a.get().getBoolean("analytics_launched", false);
    }
}
