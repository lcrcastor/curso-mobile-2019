package io.fabric.sdk.android.services.settings;

import io.fabric.sdk.android.services.common.CurrentTimeProvider;
import org.json.JSONObject;

class DefaultSettingsJsonTransform implements SettingsJsonTransform {
    DefaultSettingsJsonTransform() {
    }

    public SettingsData buildFromJson(CurrentTimeProvider currentTimeProvider, JSONObject jSONObject) {
        int optInt = jSONObject.optInt(SettingsJsonConstants.SETTINGS_VERSION, 0);
        int optInt2 = jSONObject.optInt(SettingsJsonConstants.CACHE_DURATION_KEY, 3600);
        SettingsData settingsData = new SettingsData(a(currentTimeProvider, (long) optInt2, jSONObject), a(jSONObject.getJSONObject("app")), e(jSONObject.getJSONObject(SettingsJsonConstants.SESSION_KEY)), f(jSONObject.getJSONObject(SettingsJsonConstants.PROMPT_KEY)), c(jSONObject.getJSONObject(SettingsJsonConstants.FEATURES_KEY)), d(jSONObject.getJSONObject(SettingsJsonConstants.ANALYTICS_KEY)), g(jSONObject.getJSONObject("beta")), optInt, optInt2);
        return settingsData;
    }

    public JSONObject toJson(SettingsData settingsData) {
        return new JSONObject().put(SettingsJsonConstants.EXPIRES_AT_KEY, settingsData.expiresAtMillis).put(SettingsJsonConstants.CACHE_DURATION_KEY, settingsData.cacheDuration).put(SettingsJsonConstants.SETTINGS_VERSION, settingsData.settingsVersion).put(SettingsJsonConstants.FEATURES_KEY, a(settingsData.featuresData)).put(SettingsJsonConstants.ANALYTICS_KEY, a(settingsData.analyticsSettingsData)).put("beta", a(settingsData.betaSettingsData)).put("app", a(settingsData.appData)).put(SettingsJsonConstants.SESSION_KEY, a(settingsData.sessionData)).put(SettingsJsonConstants.PROMPT_KEY, a(settingsData.promptData));
    }

    private AppSettingsData a(JSONObject jSONObject) {
        AppSettingsData appSettingsData = new AppSettingsData(jSONObject.getString(SettingsJsonConstants.APP_IDENTIFIER_KEY), jSONObject.getString("status"), jSONObject.getString("url"), jSONObject.getString(SettingsJsonConstants.APP_REPORTS_URL_KEY), jSONObject.optBoolean(SettingsJsonConstants.APP_UPDATE_REQUIRED_KEY, false), (!jSONObject.has(SettingsJsonConstants.APP_ICON_KEY) || !jSONObject.getJSONObject(SettingsJsonConstants.APP_ICON_KEY).has(SettingsJsonConstants.ICON_HASH_KEY)) ? null : b(jSONObject.getJSONObject(SettingsJsonConstants.APP_ICON_KEY)));
        return appSettingsData;
    }

    private AppIconSettingsData b(JSONObject jSONObject) {
        return new AppIconSettingsData(jSONObject.getString(SettingsJsonConstants.ICON_HASH_KEY), jSONObject.getInt(SettingsJsonConstants.ICON_WIDTH_KEY), jSONObject.getInt(SettingsJsonConstants.ICON_HEIGHT_KEY));
    }

    private FeaturesSettingsData c(JSONObject jSONObject) {
        return new FeaturesSettingsData(jSONObject.optBoolean(SettingsJsonConstants.FEATURES_PROMPT_ENABLED_KEY, false), jSONObject.optBoolean(SettingsJsonConstants.FEATURES_COLLECT_LOGGED_EXCEPTIONS_KEY, true), jSONObject.optBoolean(SettingsJsonConstants.FEATURES_COLLECT_REPORTS_KEY, true), jSONObject.optBoolean(SettingsJsonConstants.FEATURES_COLLECT_ANALYTICS_KEY, false));
    }

    private AnalyticsSettingsData d(JSONObject jSONObject) {
        AnalyticsSettingsData analyticsSettingsData = new AnalyticsSettingsData(jSONObject.optString("url", SettingsJsonConstants.ANALYTICS_URL_DEFAULT), jSONObject.optInt(SettingsJsonConstants.ANALYTICS_FLUSH_INTERVAL_SECS_KEY, SettingsJsonConstants.ANALYTICS_FLUSH_INTERVAL_SECS_DEFAULT), jSONObject.optInt(SettingsJsonConstants.ANALYTICS_MAX_BYTE_SIZE_PER_FILE_KEY, 8000), jSONObject.optInt(SettingsJsonConstants.ANALYTICS_MAX_FILE_COUNT_PER_SEND_KEY, 1), jSONObject.optInt(SettingsJsonConstants.ANALYTICS_MAX_PENDING_SEND_FILE_COUNT_KEY, 100), jSONObject.optBoolean(SettingsJsonConstants.ANALYTICS_TRACK_CUSTOM_EVENTS_KEY, true), jSONObject.optBoolean(SettingsJsonConstants.ANALYTICS_TRACK_PREDEFINED_EVENTS_KEY, true), jSONObject.optInt(SettingsJsonConstants.ANALYTICS_SAMPLING_RATE_KEY, 1), jSONObject.optBoolean(SettingsJsonConstants.ANALYTICS_FLUSH_ON_BACKGROUND_KEY, true));
        return analyticsSettingsData;
    }

    private SessionSettingsData e(JSONObject jSONObject) {
        SessionSettingsData sessionSettingsData = new SessionSettingsData(jSONObject.optInt(SettingsJsonConstants.SETTINGS_LOG_BUFFER_SIZE_KEY, SettingsJsonConstants.SETTINGS_LOG_BUFFER_SIZE_DEFAULT), jSONObject.optInt(SettingsJsonConstants.SETTINGS_MAX_CHAINED_EXCEPTION_DEPTH_KEY, 8), jSONObject.optInt(SettingsJsonConstants.SETTINGS_MAX_CUSTOM_EXCEPTION_EVENTS_KEY, 64), jSONObject.optInt(SettingsJsonConstants.SETTINGS_MAX_CUSTOM_KEY_VALUE_PAIRS_KEY, 64), jSONObject.optInt(SettingsJsonConstants.SETTINGS_IDENTIFIER_MASK_KEY, 255), jSONObject.optBoolean(SettingsJsonConstants.SETTINGS_SEND_SESSION_WITHOUT_CRASH_KEY, false));
        return sessionSettingsData;
    }

    private PromptSettingsData f(JSONObject jSONObject) {
        PromptSettingsData promptSettingsData = new PromptSettingsData(jSONObject.optString("title", SettingsJsonConstants.PROMPT_TITLE_DEFAULT), jSONObject.optString("message", SettingsJsonConstants.PROMPT_MESSAGE_DEFAULT), jSONObject.optString(SettingsJsonConstants.PROMPT_SEND_BUTTON_TITLE_KEY, SettingsJsonConstants.PROMPT_SEND_BUTTON_TITLE_DEFAULT), jSONObject.optBoolean(SettingsJsonConstants.PROMPT_SHOW_CANCEL_BUTTON_KEY, true), jSONObject.optString(SettingsJsonConstants.PROMPT_CANCEL_BUTTON_TITLE_KEY, SettingsJsonConstants.PROMPT_CANCEL_BUTTON_TITLE_DEFAULT), jSONObject.optBoolean(SettingsJsonConstants.PROMPT_SHOW_ALWAYS_SEND_BUTTON_KEY, true), jSONObject.optString(SettingsJsonConstants.PROMPT_ALWAYS_SEND_BUTTON_TITLE_KEY, SettingsJsonConstants.PROMPT_ALWAYS_SEND_BUTTON_TITLE_DEFAULT));
        return promptSettingsData;
    }

    private BetaSettingsData g(JSONObject jSONObject) {
        return new BetaSettingsData(jSONObject.optString(SettingsJsonConstants.BETA_UPDATE_ENDPOINT, SettingsJsonConstants.BETA_UPDATE_ENDPOINT_DEFAULT), jSONObject.optInt(SettingsJsonConstants.BETA_UPDATE_SUSPEND_DURATION, 3600));
    }

    private long a(CurrentTimeProvider currentTimeProvider, long j, JSONObject jSONObject) {
        if (jSONObject.has(SettingsJsonConstants.EXPIRES_AT_KEY)) {
            return jSONObject.getLong(SettingsJsonConstants.EXPIRES_AT_KEY);
        }
        return currentTimeProvider.getCurrentTimeMillis() + (j * 1000);
    }

    private JSONObject a(AppSettingsData appSettingsData) {
        JSONObject put = new JSONObject().put(SettingsJsonConstants.APP_IDENTIFIER_KEY, appSettingsData.identifier).put("status", appSettingsData.status).put("url", appSettingsData.url).put(SettingsJsonConstants.APP_REPORTS_URL_KEY, appSettingsData.reportsUrl).put(SettingsJsonConstants.APP_UPDATE_REQUIRED_KEY, appSettingsData.updateRequired);
        if (appSettingsData.icon != null) {
            put.put(SettingsJsonConstants.APP_ICON_KEY, a(appSettingsData.icon));
        }
        return put;
    }

    private JSONObject a(AppIconSettingsData appIconSettingsData) {
        return new JSONObject().put(SettingsJsonConstants.ICON_HASH_KEY, appIconSettingsData.a).put(SettingsJsonConstants.ICON_WIDTH_KEY, appIconSettingsData.b).put(SettingsJsonConstants.ICON_HEIGHT_KEY, appIconSettingsData.c);
    }

    private JSONObject a(FeaturesSettingsData featuresSettingsData) {
        return new JSONObject().put(SettingsJsonConstants.FEATURES_COLLECT_LOGGED_EXCEPTIONS_KEY, featuresSettingsData.collectLoggedException).put(SettingsJsonConstants.FEATURES_COLLECT_REPORTS_KEY, featuresSettingsData.collectReports).put(SettingsJsonConstants.FEATURES_COLLECT_ANALYTICS_KEY, featuresSettingsData.collectAnalytics);
    }

    private JSONObject a(SessionSettingsData sessionSettingsData) {
        return new JSONObject().put(SettingsJsonConstants.SETTINGS_LOG_BUFFER_SIZE_KEY, sessionSettingsData.logBufferSize).put(SettingsJsonConstants.SETTINGS_MAX_CHAINED_EXCEPTION_DEPTH_KEY, sessionSettingsData.maxChainedExceptionDepth).put(SettingsJsonConstants.SETTINGS_MAX_CUSTOM_EXCEPTION_EVENTS_KEY, sessionSettingsData.maxCustomExceptionEvents).put(SettingsJsonConstants.SETTINGS_MAX_CUSTOM_KEY_VALUE_PAIRS_KEY, sessionSettingsData.maxCustomKeyValuePairs).put(SettingsJsonConstants.SETTINGS_IDENTIFIER_MASK_KEY, sessionSettingsData.identifierMask).put(SettingsJsonConstants.SETTINGS_SEND_SESSION_WITHOUT_CRASH_KEY, sessionSettingsData.sendSessionWithoutCrash);
    }

    private JSONObject a(AnalyticsSettingsData analyticsSettingsData) {
        return new JSONObject().put("url", analyticsSettingsData.analyticsURL).put(SettingsJsonConstants.ANALYTICS_FLUSH_INTERVAL_SECS_KEY, analyticsSettingsData.flushIntervalSeconds).put(SettingsJsonConstants.ANALYTICS_MAX_BYTE_SIZE_PER_FILE_KEY, analyticsSettingsData.maxByteSizePerFile).put(SettingsJsonConstants.ANALYTICS_MAX_FILE_COUNT_PER_SEND_KEY, analyticsSettingsData.maxFileCountPerSend).put(SettingsJsonConstants.ANALYTICS_MAX_PENDING_SEND_FILE_COUNT_KEY, analyticsSettingsData.maxPendingSendFileCount);
    }

    private JSONObject a(BetaSettingsData betaSettingsData) {
        return new JSONObject().put(SettingsJsonConstants.BETA_UPDATE_ENDPOINT, betaSettingsData.updateUrl).put(SettingsJsonConstants.BETA_UPDATE_SUSPEND_DURATION, betaSettingsData.updateSuspendDurationSeconds);
    }

    private JSONObject a(PromptSettingsData promptSettingsData) {
        return new JSONObject().put("title", promptSettingsData.title).put("message", promptSettingsData.message).put(SettingsJsonConstants.PROMPT_SEND_BUTTON_TITLE_KEY, promptSettingsData.sendButtonTitle).put(SettingsJsonConstants.PROMPT_SHOW_CANCEL_BUTTON_KEY, promptSettingsData.showCancelButton).put(SettingsJsonConstants.PROMPT_CANCEL_BUTTON_TITLE_KEY, promptSettingsData.cancelButtonTitle).put(SettingsJsonConstants.PROMPT_SHOW_ALWAYS_SEND_BUTTON_KEY, promptSettingsData.showAlwaysSendButton).put(SettingsJsonConstants.PROMPT_ALWAYS_SEND_BUTTON_TITLE_KEY, promptSettingsData.alwaysSendButtonTitle);
    }
}
