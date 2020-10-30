package com.crashlytics.android.beta;

import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import org.json.JSONObject;

class CheckForUpdatesResponseTransform {
    CheckForUpdatesResponseTransform() {
    }

    public CheckForUpdatesResponse a(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        CheckForUpdatesResponse checkForUpdatesResponse = new CheckForUpdatesResponse(jSONObject.optString("url", null), jSONObject.optString("version_string", null), jSONObject.optString("display_version", null), jSONObject.optString("build_version", null), jSONObject.optString(SettingsJsonConstants.APP_IDENTIFIER_KEY, null), jSONObject.optString("instance_identifier", null));
        return checkForUpdatesResponse;
    }
}
