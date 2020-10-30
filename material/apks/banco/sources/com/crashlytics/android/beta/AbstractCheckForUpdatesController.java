package com.crashlytics.android.beta;

import android.annotation.SuppressLint;
import android.content.Context;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.ApiKey;
import io.fabric.sdk.android.services.common.CurrentTimeProvider;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.common.IdManager.DeviceIdentifierType;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.persistence.PreferenceStore;
import io.fabric.sdk.android.services.settings.BetaSettingsData;
import java.util.concurrent.atomic.AtomicBoolean;

abstract class AbstractCheckForUpdatesController implements UpdatesController {
    private final AtomicBoolean a;
    private final AtomicBoolean b;
    private Context c;
    private Beta d;
    private IdManager e;
    private BetaSettingsData f;
    private BuildProperties g;
    private PreferenceStore h;
    private CurrentTimeProvider i;
    private HttpRequestFactory j;
    private long k;

    public AbstractCheckForUpdatesController() {
        this(false);
    }

    public AbstractCheckForUpdatesController(boolean z) {
        this.a = new AtomicBoolean();
        this.k = 0;
        this.b = new AtomicBoolean(z);
    }

    public void a(Context context, Beta beta, IdManager idManager, BetaSettingsData betaSettingsData, BuildProperties buildProperties, PreferenceStore preferenceStore, CurrentTimeProvider currentTimeProvider, HttpRequestFactory httpRequestFactory) {
        this.c = context;
        this.d = beta;
        this.e = idManager;
        this.f = betaSettingsData;
        this.g = buildProperties;
        this.h = preferenceStore;
        this.i = currentTimeProvider;
        this.j = httpRequestFactory;
        if (b()) {
            c();
        }
    }

    /* access modifiers changed from: protected */
    public boolean a() {
        this.b.set(true);
        return this.a.get();
    }

    /* access modifiers changed from: 0000 */
    public boolean b() {
        this.a.set(true);
        return this.b.get();
    }

    /* access modifiers changed from: protected */
    @SuppressLint({"CommitPrefEdits"})
    public void c() {
        synchronized (this.h) {
            if (this.h.get().contains("last_update_check")) {
                this.h.save(this.h.edit().remove("last_update_check"));
            }
        }
        long currentTimeMillis = this.i.getCurrentTimeMillis();
        long j2 = ((long) this.f.updateSuspendDurationSeconds) * 1000;
        Logger logger = Fabric.getLogger();
        String str = Beta.TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("Check for updates delay: ");
        sb.append(j2);
        logger.d(str, sb.toString());
        Logger logger2 = Fabric.getLogger();
        String str2 = Beta.TAG;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Check for updates last check time: ");
        sb2.append(d());
        logger2.d(str2, sb2.toString());
        long d2 = d() + j2;
        Logger logger3 = Fabric.getLogger();
        String str3 = Beta.TAG;
        StringBuilder sb3 = new StringBuilder();
        sb3.append("Check for updates current time: ");
        sb3.append(currentTimeMillis);
        sb3.append(", next check time: ");
        sb3.append(d2);
        logger3.d(str3, sb3.toString());
        if (currentTimeMillis >= d2) {
            try {
                e();
            } finally {
                a(currentTimeMillis);
            }
        } else {
            Fabric.getLogger().d(Beta.TAG, "Check for updates next check time was not passed");
        }
    }

    private void e() {
        Fabric.getLogger().d(Beta.TAG, "Performing update check");
        String value = new ApiKey().getValue(this.c);
        String str = (String) this.e.getDeviceIdentifiers().get(DeviceIdentifierType.FONT_TOKEN);
        CheckForUpdatesRequest checkForUpdatesRequest = new CheckForUpdatesRequest(this.d, this.d.a(), this.f.updateUrl, this.j, new CheckForUpdatesResponseTransform());
        checkForUpdatesRequest.a(value, str, this.g);
    }

    /* access modifiers changed from: 0000 */
    public void a(long j2) {
        this.k = j2;
    }

    /* access modifiers changed from: 0000 */
    public long d() {
        return this.k;
    }
}
