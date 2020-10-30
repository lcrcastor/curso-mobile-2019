package io.fabric.sdk.android.services.settings;

import io.fabric.sdk.android.services.common.CurrentTimeProvider;
import org.json.JSONObject;

public interface SettingsJsonTransform {
    SettingsData buildFromJson(CurrentTimeProvider currentTimeProvider, JSONObject jSONObject);

    JSONObject toJson(SettingsData settingsData);
}
