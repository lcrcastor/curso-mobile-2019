package io.fabric.sdk.android.services.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import com.appsflyer.share.Constants;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.persistence.PreferenceStore;
import io.fabric.sdk.android.services.persistence.PreferenceStoreImpl;

class AdvertisingInfoProvider {
    private final Context a;
    private final PreferenceStore b;

    public AdvertisingInfoProvider(Context context) {
        this.a = context.getApplicationContext();
        this.b = new PreferenceStoreImpl(context, "TwitterAdvertisingInfoPreferences");
    }

    public AdvertisingInfo a() {
        AdvertisingInfo b2 = b();
        if (c(b2)) {
            Fabric.getLogger().d(Fabric.TAG, "Using AdvertisingInfo from Preference Store");
            a(b2);
            return b2;
        }
        AdvertisingInfo e = e();
        b(e);
        return e;
    }

    private void a(final AdvertisingInfo advertisingInfo) {
        new Thread(new BackgroundPriorityRunnable() {
            public void onRun() {
                AdvertisingInfo a2 = AdvertisingInfoProvider.this.e();
                if (!advertisingInfo.equals(a2)) {
                    Fabric.getLogger().d(Fabric.TAG, "Asychronously getting Advertising Info and storing it to preferences");
                    AdvertisingInfoProvider.this.b(a2);
                }
            }
        }).start();
    }

    /* access modifiers changed from: private */
    @SuppressLint({"CommitPrefEdits"})
    public void b(AdvertisingInfo advertisingInfo) {
        if (c(advertisingInfo)) {
            this.b.save(this.b.edit().putString(Constants.URL_ADVERTISING_ID, advertisingInfo.a).putBoolean("limit_ad_tracking_enabled", advertisingInfo.b));
        } else {
            this.b.save(this.b.edit().remove(Constants.URL_ADVERTISING_ID).remove("limit_ad_tracking_enabled"));
        }
    }

    /* access modifiers changed from: protected */
    public AdvertisingInfo b() {
        return new AdvertisingInfo(this.b.get().getString(Constants.URL_ADVERTISING_ID, ""), this.b.get().getBoolean("limit_ad_tracking_enabled", false));
    }

    public AdvertisingInfoStrategy c() {
        return new AdvertisingInfoReflectionStrategy(this.a);
    }

    public AdvertisingInfoStrategy d() {
        return new AdvertisingInfoServiceStrategy(this.a);
    }

    private boolean c(AdvertisingInfo advertisingInfo) {
        return advertisingInfo != null && !TextUtils.isEmpty(advertisingInfo.a);
    }

    /* access modifiers changed from: private */
    public AdvertisingInfo e() {
        AdvertisingInfo advertisingInfo = c().getAdvertisingInfo();
        if (!c(advertisingInfo)) {
            advertisingInfo = d().getAdvertisingInfo();
            if (!c(advertisingInfo)) {
                Fabric.getLogger().d(Fabric.TAG, "AdvertisingInfo not present");
            } else {
                Fabric.getLogger().d(Fabric.TAG, "Using AdvertisingInfo from Service Provider");
            }
        } else {
            Fabric.getLogger().d(Fabric.TAG, "Using AdvertisingInfo from Reflection Provider");
        }
        return advertisingInfo;
    }
}
