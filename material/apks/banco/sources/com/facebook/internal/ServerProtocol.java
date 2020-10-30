package com.facebook.internal;

import android.content.Context;
import android.os.Bundle;
import com.facebook.LoggingBehavior;
import com.facebook.Settings;
import com.facebook.android.Facebook;
import java.util.Collection;
import org.json.JSONException;
import org.json.JSONObject;

public final class ServerProtocol {
    public static final String DIALOG_PARAM_ACCESS_TOKEN = "access_token";
    public static final String DIALOG_PARAM_APP_ID = "app_id";
    public static final String DIALOG_PARAM_AUTH_TYPE = "auth_type";
    public static final String DIALOG_PARAM_CLIENT_ID = "client_id";
    public static final String DIALOG_PARAM_DEFAULT_AUDIENCE = "default_audience";
    public static final String DIALOG_PARAM_DISPLAY = "display";
    public static final String DIALOG_PARAM_E2E = "e2e";
    public static final String DIALOG_PARAM_LEGACY_OVERRIDE = "legacy_override";
    public static final String DIALOG_PARAM_REDIRECT_URI = "redirect_uri";
    public static final String DIALOG_PARAM_RESPONSE_TYPE = "response_type";
    public static final String DIALOG_PARAM_RETURN_SCOPES = "return_scopes";
    public static final String DIALOG_PARAM_SCOPE = "scope";
    public static final String DIALOG_PATH = "dialog/";
    public static final String DIALOG_REREQUEST_AUTH_TYPE = "rerequest";
    public static final String DIALOG_RESPONSE_TYPE_TOKEN = "token";
    public static final String DIALOG_RETURN_SCOPES_TRUE = "true";
    public static final String FALLBACK_DIALOG_DISPLAY_VALUE_TOUCH = "touch";
    public static final String FALLBACK_DIALOG_PARAM_APP_ID = "app_id";
    public static final String FALLBACK_DIALOG_PARAM_BRIDGE_ARGS = "bridge_args";
    public static final String FALLBACK_DIALOG_PARAM_KEY_HASH = "android_key_hash";
    public static final String FALLBACK_DIALOG_PARAM_METHOD_ARGS = "method_args";
    public static final String FALLBACK_DIALOG_PARAM_METHOD_RESULTS = "method_results";
    public static final String FALLBACK_DIALOG_PARAM_VERSION = "version";
    public static final String GRAPH_API_VERSION = "v2.2";
    private static final String a = "com.facebook.internal.ServerProtocol";
    public static final Collection<String> errorsProxyAuthDisabled = Utility.unmodifiableCollection(Facebook.SINGLE_SIGN_ON_DISABLED, "AndroidAuthKillSwitchException");
    public static final Collection<String> errorsUserCanceled = Utility.unmodifiableCollection("access_denied", "OAuthAccessDeniedException");

    public static final String getDialogAuthority() {
        return String.format("m.%s", new Object[]{Settings.getFacebookDomain()});
    }

    public static final String getGraphUrlBase() {
        return String.format("https://graph.%s", new Object[]{Settings.getFacebookDomain()});
    }

    public static final String getGraphVideoUrlBase() {
        return String.format("https://graph-video.%s", new Object[]{Settings.getFacebookDomain()});
    }

    public static final String getAPIVersion() {
        return Settings.getPlatformCompatibilityEnabled() ? "v1.0" : GRAPH_API_VERSION;
    }

    public static Bundle getQueryParamsForPlatformActivityIntentWebFallback(Context context, String str, int i, String str2, Bundle bundle) {
        String applicationSignature = Settings.getApplicationSignature(context);
        if (Utility.isNullOrEmpty(applicationSignature)) {
            return null;
        }
        Bundle bundle2 = new Bundle();
        bundle2.putString(FALLBACK_DIALOG_PARAM_KEY_HASH, applicationSignature);
        bundle2.putString("app_id", Settings.getApplicationId());
        bundle2.putInt("version", i);
        bundle2.putString(DIALOG_PARAM_DISPLAY, FALLBACK_DIALOG_DISPLAY_VALUE_TOUCH);
        Bundle bundle3 = new Bundle();
        bundle3.putString("action_id", str);
        bundle3.putString(NativeProtocol.BRIDGE_ARG_APP_NAME_STRING, str2);
        if (bundle == null) {
            bundle = new Bundle();
        }
        try {
            JSONObject convertToJSON = BundleJSONConverter.convertToJSON(bundle3);
            JSONObject convertToJSON2 = BundleJSONConverter.convertToJSON(bundle);
            if (convertToJSON != null) {
                if (convertToJSON2 != null) {
                    bundle2.putString(FALLBACK_DIALOG_PARAM_BRIDGE_ARGS, convertToJSON.toString());
                    bundle2.putString(FALLBACK_DIALOG_PARAM_METHOD_ARGS, convertToJSON2.toString());
                    return bundle2;
                }
            }
            return null;
        } catch (JSONException e) {
            LoggingBehavior loggingBehavior = LoggingBehavior.DEVELOPER_ERRORS;
            String str3 = a;
            StringBuilder sb = new StringBuilder();
            sb.append("Error creating Url -- ");
            sb.append(e);
            Logger.log(loggingBehavior, 6, str3, sb.toString());
            bundle2 = null;
        }
    }
}
