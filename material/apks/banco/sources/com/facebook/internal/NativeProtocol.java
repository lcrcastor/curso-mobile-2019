package com.facebook.internal;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.SessionDefaultAudience;
import com.facebook.Settings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.UUID;

public final class NativeProtocol {
    public static final String ACTION_FEED_DIALOG = "com.facebook.platform.action.request.FEED_DIALOG";
    public static final String ACTION_FEED_DIALOG_REPLY = "com.facebook.platform.action.reply.FEED_DIALOG";
    public static final String ACTION_LIKE_DIALOG = "com.facebook.platform.action.request.LIKE_DIALOG";
    public static final String ACTION_LIKE_DIALOG_REPLY = "com.facebook.platform.action.reply.LIKE_DIALOG";
    public static final String ACTION_MESSAGE_DIALOG = "com.facebook.platform.action.request.MESSAGE_DIALOG";
    public static final String ACTION_MESSAGE_DIALOG_REPLY = "com.facebook.platform.action.reply.MESSAGE_DIALOG";
    public static final String ACTION_OGACTIONPUBLISH_DIALOG = "com.facebook.platform.action.request.OGACTIONPUBLISH_DIALOG";
    public static final String ACTION_OGACTIONPUBLISH_DIALOG_REPLY = "com.facebook.platform.action.reply.OGACTIONPUBLISH_DIALOG";
    public static final String ACTION_OGMESSAGEPUBLISH_DIALOG = "com.facebook.platform.action.request.OGMESSAGEPUBLISH_DIALOG";
    public static final String ACTION_OGMESSAGEPUBLISH_DIALOG_REPLY = "com.facebook.platform.action.reply.OGMESSAGEPUBLISH_DIALOG";
    public static final String AUDIENCE_EVERYONE = "everyone";
    public static final String AUDIENCE_FRIENDS = "friends";
    public static final String AUDIENCE_ME = "only_me";
    public static final String BRIDGE_ARG_ACTION_ID_STRING = "action_id";
    public static final String BRIDGE_ARG_APP_NAME_STRING = "app_name";
    public static final String BRIDGE_ARG_ERROR_BUNDLE = "error";
    public static final String BRIDGE_ARG_ERROR_CODE = "error_code";
    public static final String BRIDGE_ARG_ERROR_DESCRIPTION = "error_description";
    public static final String BRIDGE_ARG_ERROR_JSON = "error_json";
    public static final String BRIDGE_ARG_ERROR_SUBCODE = "error_subcode";
    public static final String BRIDGE_ARG_ERROR_TYPE = "error_type";
    public static final int DIALOG_REQUEST_CODE = 64207;
    public static final String ERROR_APPLICATION_ERROR = "ApplicationError";
    public static final String ERROR_NETWORK_ERROR = "NetworkError";
    public static final String ERROR_PERMISSION_DENIED = "PermissionDenied";
    public static final String ERROR_PROTOCOL_ERROR = "ProtocolError";
    public static final String ERROR_SERVICE_DISABLED = "ServiceDisabled";
    public static final String ERROR_UNKNOWN_ERROR = "UnknownError";
    public static final String ERROR_USER_CANCELED = "UserCanceled";
    public static final String EXTRA_ACCESS_TOKEN = "com.facebook.platform.extra.ACCESS_TOKEN";
    public static final String EXTRA_ACTION = "com.facebook.platform.extra.ACTION";
    public static final String EXTRA_ACTION_TYPE = "com.facebook.platform.extra.ACTION_TYPE";
    public static final String EXTRA_APPLICATION_ID = "com.facebook.platform.extra.APPLICATION_ID";
    public static final String EXTRA_APPLICATION_NAME = "com.facebook.platform.extra.APPLICATION_NAME";
    public static final String EXTRA_DATA_FAILURES_FATAL = "com.facebook.platform.extra.DATA_FAILURES_FATAL";
    public static final String EXTRA_DESCRIPTION = "com.facebook.platform.extra.DESCRIPTION";
    public static final String EXTRA_EXPIRES_SECONDS_SINCE_EPOCH = "com.facebook.platform.extra.EXPIRES_SECONDS_SINCE_EPOCH";
    public static final String EXTRA_FRIEND_TAGS = "com.facebook.platform.extra.FRIENDS";
    public static final String EXTRA_GET_INSTALL_DATA_PACKAGE = "com.facebook.platform.extra.INSTALLDATA_PACKAGE";
    public static final String EXTRA_IMAGE = "com.facebook.platform.extra.IMAGE";
    public static final String EXTRA_LIKE_COUNT_STRING_WITHOUT_LIKE = "com.facebook.platform.extra.LIKE_COUNT_STRING_WITHOUT_LIKE";
    public static final String EXTRA_LIKE_COUNT_STRING_WITH_LIKE = "com.facebook.platform.extra.LIKE_COUNT_STRING_WITH_LIKE";
    public static final String EXTRA_LINK = "com.facebook.platform.extra.LINK";
    public static final String EXTRA_OBJECT_ID = "com.facebook.platform.extra.OBJECT_ID";
    public static final String EXTRA_OBJECT_IS_LIKED = "com.facebook.platform.extra.OBJECT_IS_LIKED";
    public static final String EXTRA_PERMISSIONS = "com.facebook.platform.extra.PERMISSIONS";
    public static final String EXTRA_PHOTOS = "com.facebook.platform.extra.PHOTOS";
    public static final String EXTRA_PLACE_TAG = "com.facebook.platform.extra.PLACE";
    public static final String EXTRA_PREVIEW_PROPERTY_NAME = "com.facebook.platform.extra.PREVIEW_PROPERTY_NAME";
    public static final String EXTRA_PROTOCOL_ACTION = "com.facebook.platform.protocol.PROTOCOL_ACTION";
    public static final String EXTRA_PROTOCOL_BRIDGE_ARGS = "com.facebook.platform.protocol.BRIDGE_ARGS";
    public static final String EXTRA_PROTOCOL_CALL_ID = "com.facebook.platform.protocol.CALL_ID";
    public static final String EXTRA_PROTOCOL_METHOD_ARGS = "com.facebook.platform.protocol.METHOD_ARGS";
    public static final String EXTRA_PROTOCOL_METHOD_RESULTS = "com.facebook.platform.protocol.RESULT_ARGS";
    public static final String EXTRA_PROTOCOL_VERSION = "com.facebook.platform.protocol.PROTOCOL_VERSION";
    public static final String EXTRA_REF = "com.facebook.platform.extra.REF";
    public static final String EXTRA_SOCIAL_SENTENCE_WITHOUT_LIKE = "com.facebook.platform.extra.SOCIAL_SENTENCE_WITHOUT_LIKE";
    public static final String EXTRA_SOCIAL_SENTENCE_WITH_LIKE = "com.facebook.platform.extra.SOCIAL_SENTENCE_WITH_LIKE";
    public static final String EXTRA_SUBTITLE = "com.facebook.platform.extra.SUBTITLE";
    public static final String EXTRA_TITLE = "com.facebook.platform.extra.TITLE";
    public static final String EXTRA_UNLIKE_TOKEN = "com.facebook.platform.extra.UNLIKE_TOKEN";
    public static final String FACEBOOK_PROXY_AUTH_APP_ID_KEY = "client_id";
    public static final String FACEBOOK_PROXY_AUTH_E2E_KEY = "e2e";
    public static final String FACEBOOK_PROXY_AUTH_PERMISSIONS_KEY = "scope";
    public static final String IMAGE_URL_KEY = "url";
    public static final String IMAGE_USER_GENERATED_KEY = "user_generated";
    public static final int MESSAGE_GET_ACCESS_TOKEN_REPLY = 65537;
    public static final int MESSAGE_GET_ACCESS_TOKEN_REQUEST = 65536;
    public static final int MESSAGE_GET_INSTALL_DATA_REPLY = 65541;
    public static final int MESSAGE_GET_INSTALL_DATA_REQUEST = 65540;
    public static final int MESSAGE_GET_LIKE_STATUS_REPLY = 65543;
    public static final int MESSAGE_GET_LIKE_STATUS_REQUEST = 65542;
    public static final String METHOD_ARGS_ACTION = "ACTION";
    public static final String METHOD_ARGS_ACTION_TYPE = "ACTION_TYPE";
    public static final String METHOD_ARGS_DATA_FAILURES_FATAL = "DATA_FAILURES_FATAL";
    public static final String METHOD_ARGS_DESCRIPTION = "DESCRIPTION";
    public static final String METHOD_ARGS_FRIEND_TAGS = "FRIENDS";
    public static final String METHOD_ARGS_IMAGE = "IMAGE";
    public static final String METHOD_ARGS_LINK = "LINK";
    public static final String METHOD_ARGS_OBJECT_ID = "object_id";
    public static final String METHOD_ARGS_PHOTOS = "PHOTOS";
    public static final String METHOD_ARGS_PLACE_TAG = "PLACE";
    public static final String METHOD_ARGS_PREVIEW_PROPERTY_NAME = "PREVIEW_PROPERTY_NAME";
    public static final String METHOD_ARGS_REF = "REF";
    public static final String METHOD_ARGS_SUBTITLE = "SUBTITLE";
    public static final String METHOD_ARGS_TITLE = "TITLE";
    public static final String METHOD_ARGS_VIDEO = "VIDEO";
    public static final int NO_PROTOCOL_AVAILABLE = -1;
    public static final String OPEN_GRAPH_CREATE_OBJECT_KEY = "fbsdk:create_object";
    public static final int PROTOCOL_VERSION_20121101 = 20121101;
    public static final int PROTOCOL_VERSION_20130502 = 20130502;
    public static final int PROTOCOL_VERSION_20130618 = 20130618;
    public static final int PROTOCOL_VERSION_20131107 = 20131107;
    public static final int PROTOCOL_VERSION_20140204 = 20140204;
    public static final int PROTOCOL_VERSION_20140324 = 20140324;
    public static final int PROTOCOL_VERSION_20140701 = 20140701;
    public static final int PROTOCOL_VERSION_20141001 = 20141001;
    public static final int PROTOCOL_VERSION_20141028 = 20141028;
    public static final int PROTOCOL_VERSION_20141107 = 20141107;
    public static final String RESULT_ARGS_ACCESS_TOKEN = "access_token";
    public static final String RESULT_ARGS_EXPIRES_SECONDS_SINCE_EPOCH = "expires_seconds_since_epoch";
    public static final String RESULT_ARGS_PERMISSIONS = "permissions";
    public static final String STATUS_ERROR_CODE = "com.facebook.platform.status.ERROR_CODE";
    public static final String STATUS_ERROR_DESCRIPTION = "com.facebook.platform.status.ERROR_DESCRIPTION";
    public static final String STATUS_ERROR_JSON = "com.facebook.platform.status.ERROR_JSON";
    public static final String STATUS_ERROR_SUBCODE = "com.facebook.platform.status.ERROR_SUBCODE";
    public static final String STATUS_ERROR_TYPE = "com.facebook.platform.status.ERROR_TYPE";
    private static final NativeAppInfo a = new KatanaAppInfo();
    private static List<NativeAppInfo> b = a();
    private static Map<String, List<NativeAppInfo>> c = b();
    private static final List<Integer> d = Arrays.asList(new Integer[]{Integer.valueOf(PROTOCOL_VERSION_20141107), Integer.valueOf(PROTOCOL_VERSION_20141028), Integer.valueOf(PROTOCOL_VERSION_20141001), Integer.valueOf(PROTOCOL_VERSION_20140701), Integer.valueOf(PROTOCOL_VERSION_20140324), Integer.valueOf(PROTOCOL_VERSION_20140204), Integer.valueOf(PROTOCOL_VERSION_20131107), Integer.valueOf(PROTOCOL_VERSION_20130618), Integer.valueOf(PROTOCOL_VERSION_20130502), Integer.valueOf(PROTOCOL_VERSION_20121101)});

    static class KatanaAppInfo extends NativeAppInfo {
        /* access modifiers changed from: protected */
        public String a() {
            return Constants.INTENT_FACEBOOK;
        }

        private KatanaAppInfo() {
            super();
        }
    }

    static class MessengerAppInfo extends NativeAppInfo {
        /* access modifiers changed from: protected */
        public String a() {
            return "com.facebook.orca";
        }

        private MessengerAppInfo() {
            super();
        }
    }

    static abstract class NativeAppInfo {
        private static final HashSet<String> a = b();

        /* access modifiers changed from: protected */
        public abstract String a();

        private NativeAppInfo() {
        }

        private static HashSet<String> b() {
            HashSet<String> hashSet = new HashSet<>();
            hashSet.add("8a3c4b262d721acd49a4bf97d5213199c86fa2b9");
            hashSet.add("a4b7452e2ed8f5f191058ca7bbfd26b0d3214bfc");
            hashSet.add("5e8f16062ea3cd2c4a0d547876baa6f38cabf625");
            return hashSet;
        }

        public boolean a(Context context, String str) {
            String str2 = Build.BRAND;
            int i = context.getApplicationInfo().flags;
            if (str2.startsWith("generic") && (i & 2) != 0) {
                return true;
            }
            try {
                for (Signature byteArray : context.getPackageManager().getPackageInfo(str, 64).signatures) {
                    if (a.contains(Utility.a(byteArray.toByteArray()))) {
                        return true;
                    }
                }
                return false;
            } catch (NameNotFoundException unused) {
                return false;
            }
        }
    }

    static class WakizashiAppInfo extends NativeAppInfo {
        /* access modifiers changed from: protected */
        public String a() {
            return "com.facebook.wakizashi";
        }

        private WakizashiAppInfo() {
            super();
        }
    }

    private static List<NativeAppInfo> a() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(a);
        arrayList.add(new WakizashiAppInfo());
        return arrayList;
    }

    private static Map<String, List<NativeAppInfo>> b() {
        HashMap hashMap = new HashMap();
        ArrayList arrayList = new ArrayList();
        arrayList.add(new MessengerAppInfo());
        hashMap.put(ACTION_OGACTIONPUBLISH_DIALOG, b);
        hashMap.put(ACTION_FEED_DIALOG, b);
        hashMap.put(ACTION_LIKE_DIALOG, b);
        hashMap.put(ACTION_MESSAGE_DIALOG, arrayList);
        hashMap.put(ACTION_OGMESSAGEPUBLISH_DIALOG, arrayList);
        return hashMap;
    }

    static Intent a(Context context, Intent intent, NativeAppInfo nativeAppInfo) {
        if (intent == null) {
            return null;
        }
        ResolveInfo resolveActivity = context.getPackageManager().resolveActivity(intent, 0);
        if (resolveActivity != null && nativeAppInfo.a(context, resolveActivity.activityInfo.packageName)) {
            return intent;
        }
        return null;
    }

    static Intent b(Context context, Intent intent, NativeAppInfo nativeAppInfo) {
        if (intent == null) {
            return null;
        }
        ResolveInfo resolveService = context.getPackageManager().resolveService(intent, 0);
        if (resolveService != null && nativeAppInfo.a(context, resolveService.serviceInfo.packageName)) {
            return intent;
        }
        return null;
    }

    public static Intent createProxyAuthIntent(Context context, String str, List<String> list, String str2, boolean z, SessionDefaultAudience sessionDefaultAudience) {
        for (NativeAppInfo nativeAppInfo : b) {
            Intent putExtra = new Intent().setClassName(nativeAppInfo.a(), "com.facebook.katana.ProxyAuth").putExtra("client_id", str);
            if (!Utility.isNullOrEmpty((Collection<T>) list)) {
                putExtra.putExtra("scope", TextUtils.join(",", list));
            }
            if (!Utility.isNullOrEmpty(str2)) {
                putExtra.putExtra("e2e", str2);
            }
            putExtra.putExtra(ServerProtocol.DIALOG_PARAM_RESPONSE_TYPE, "token");
            putExtra.putExtra(ServerProtocol.DIALOG_PARAM_RETURN_SCOPES, "true");
            putExtra.putExtra(ServerProtocol.DIALOG_PARAM_DEFAULT_AUDIENCE, sessionDefaultAudience.getNativeProtocolAudience());
            if (!Settings.getPlatformCompatibilityEnabled()) {
                putExtra.putExtra(ServerProtocol.DIALOG_PARAM_LEGACY_OVERRIDE, ServerProtocol.GRAPH_API_VERSION);
                if (z) {
                    putExtra.putExtra(ServerProtocol.DIALOG_PARAM_AUTH_TYPE, ServerProtocol.DIALOG_REREQUEST_AUTH_TYPE);
                }
            }
            Intent a2 = a(context, putExtra, nativeAppInfo);
            if (a2 != null) {
                return a2;
            }
        }
        return null;
    }

    public static Intent createTokenRefreshIntent(Context context) {
        for (NativeAppInfo nativeAppInfo : b) {
            Intent b2 = b(context, new Intent().setClassName(nativeAppInfo.a(), "com.facebook.katana.platform.TokenRefreshService"), nativeAppInfo);
            if (b2 != null) {
                return b2;
            }
        }
        return null;
    }

    public static final int getLatestKnownVersion() {
        return ((Integer) d.get(0)).intValue();
    }

    private static Intent a(Context context, String str, String str2) {
        List<NativeAppInfo> list = (List) c.get(str2);
        Intent intent = null;
        if (list == null) {
            return null;
        }
        for (NativeAppInfo nativeAppInfo : list) {
            intent = a(context, new Intent().setAction(str).setPackage(nativeAppInfo.a()).addCategory("android.intent.category.DEFAULT"), nativeAppInfo);
            if (intent != null) {
                return intent;
            }
        }
        return intent;
    }

    public static boolean isVersionCompatibleWithBucketedIntent(int i) {
        return d.contains(Integer.valueOf(i)) && i >= 20140701;
    }

    public static Intent createPlatformActivityIntent(Context context, String str, String str2, int i, String str3, Bundle bundle) {
        Intent a2 = a(context, "com.facebook.platform.PLATFORM_ACTIVITY", str2);
        if (a2 == null) {
            return null;
        }
        a2.putExtra(EXTRA_PROTOCOL_VERSION, i).putExtra(EXTRA_PROTOCOL_ACTION, str2).putExtra(EXTRA_APPLICATION_ID, Utility.getMetadataApplicationId(context));
        if (isVersionCompatibleWithBucketedIntent(i)) {
            Bundle bundle2 = new Bundle();
            bundle2.putString("action_id", str);
            bundle2.putString(BRIDGE_ARG_APP_NAME_STRING, str3);
            a2.putExtra(EXTRA_PROTOCOL_BRIDGE_ARGS, bundle2);
            if (bundle == null) {
                bundle = new Bundle();
            }
            a2.putExtra(EXTRA_PROTOCOL_METHOD_ARGS, bundle);
        } else {
            a2.putExtra(EXTRA_PROTOCOL_CALL_ID, str);
            a2.putExtra(EXTRA_APPLICATION_NAME, str3);
            a2.putExtras(bundle);
        }
        return a2;
    }

    public static Intent createPlatformServiceIntent(Context context) {
        for (NativeAppInfo nativeAppInfo : b) {
            Intent b2 = b(context, new Intent("com.facebook.platform.PLATFORM_SERVICE").setPackage(nativeAppInfo.a()).addCategory("android.intent.category.DEFAULT"), nativeAppInfo);
            if (b2 != null) {
                return b2;
            }
        }
        return null;
    }

    public static int getProtocolVersionFromIntent(Intent intent) {
        return intent.getIntExtra(EXTRA_PROTOCOL_VERSION, 0);
    }

    public static UUID getCallIdFromIntent(Intent intent) {
        String str;
        UUID uuid;
        if (intent == null) {
            return null;
        }
        if (isVersionCompatibleWithBucketedIntent(getProtocolVersionFromIntent(intent))) {
            Bundle bundleExtra = intent.getBundleExtra(EXTRA_PROTOCOL_BRIDGE_ARGS);
            str = bundleExtra != null ? bundleExtra.getString("action_id") : null;
        } else {
            str = intent.getStringExtra(EXTRA_PROTOCOL_CALL_ID);
        }
        if (str != null) {
            try {
                uuid = UUID.fromString(str);
            } catch (IllegalArgumentException unused) {
            }
            return uuid;
        }
        uuid = null;
        return uuid;
    }

    public static Bundle getBridgeArgumentsFromIntent(Intent intent) {
        if (!isVersionCompatibleWithBucketedIntent(getProtocolVersionFromIntent(intent))) {
            return null;
        }
        return intent.getBundleExtra(EXTRA_PROTOCOL_BRIDGE_ARGS);
    }

    public static Bundle getSuccessResultsFromIntent(Intent intent) {
        int protocolVersionFromIntent = getProtocolVersionFromIntent(intent);
        Bundle extras = intent.getExtras();
        return (!isVersionCompatibleWithBucketedIntent(protocolVersionFromIntent) || extras == null) ? extras : extras.getBundle(EXTRA_PROTOCOL_METHOD_RESULTS);
    }

    public static boolean isErrorResult(Intent intent) {
        Bundle bridgeArgumentsFromIntent = getBridgeArgumentsFromIntent(intent);
        if (bridgeArgumentsFromIntent != null) {
            return bridgeArgumentsFromIntent.containsKey("error");
        }
        return intent.hasExtra(STATUS_ERROR_TYPE);
    }

    public static Bundle getErrorDataFromResultIntent(Intent intent) {
        if (!isErrorResult(intent)) {
            return null;
        }
        Bundle bridgeArgumentsFromIntent = getBridgeArgumentsFromIntent(intent);
        if (bridgeArgumentsFromIntent != null) {
            return bridgeArgumentsFromIntent.getBundle("error");
        }
        return intent.getExtras();
    }

    public static Exception getExceptionFromErrorData(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        String string = bundle.getString(BRIDGE_ARG_ERROR_TYPE);
        if (string == null) {
            string = bundle.getString(STATUS_ERROR_TYPE);
        }
        String string2 = bundle.getString(BRIDGE_ARG_ERROR_DESCRIPTION);
        if (string2 == null) {
            string2 = bundle.getString(STATUS_ERROR_DESCRIPTION);
        }
        if (string == null || !string.equalsIgnoreCase(ERROR_USER_CANCELED)) {
            return new FacebookException(string2);
        }
        return new FacebookOperationCanceledException(string2);
    }

    public static int getLatestAvailableProtocolVersionForService(Context context, int i) {
        return a(context, b, new int[]{i});
    }

    public static int getLatestAvailableProtocolVersionForAction(Context context, String str, int[] iArr) {
        return a(context, (List) c.get(str), iArr);
    }

    private static int a(Context context, List<NativeAppInfo> list, int[] iArr) {
        if (list == null) {
            return -1;
        }
        for (NativeAppInfo a2 : list) {
            int a3 = a(context, a2, iArr);
            if (a3 != -1) {
                return a3;
            }
        }
        return -1;
    }

    private static int a(Context context, NativeAppInfo nativeAppInfo, int[] iArr) {
        return computeLatestAvailableVersionFromVersionSpec(a(context, nativeAppInfo), getLatestKnownVersion(), iArr);
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0042  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.TreeSet<java.lang.Integer> a(android.content.Context r7, com.facebook.internal.NativeProtocol.NativeAppInfo r8) {
        /*
            java.util.TreeSet r0 = new java.util.TreeSet
            r0.<init>()
            android.content.ContentResolver r1 = r7.getContentResolver()
            r7 = 1
            java.lang.String[] r3 = new java.lang.String[r7]
            java.lang.String r7 = "version"
            r2 = 0
            r3[r2] = r7
            android.net.Uri r2 = a(r8)
            r4 = 0
            r5 = 0
            r6 = 0
            android.database.Cursor r7 = r1.query(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x003e }
            if (r7 == 0) goto L_0x0038
        L_0x001e:
            boolean r8 = r7.moveToNext()     // Catch:{ all -> 0x0036 }
            if (r8 == 0) goto L_0x0038
            java.lang.String r8 = "version"
            int r8 = r7.getColumnIndex(r8)     // Catch:{ all -> 0x0036 }
            int r8 = r7.getInt(r8)     // Catch:{ all -> 0x0036 }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch:{ all -> 0x0036 }
            r0.add(r8)     // Catch:{ all -> 0x0036 }
            goto L_0x001e
        L_0x0036:
            r8 = move-exception
            goto L_0x0040
        L_0x0038:
            if (r7 == 0) goto L_0x003d
            r7.close()
        L_0x003d:
            return r0
        L_0x003e:
            r8 = move-exception
            r7 = 0
        L_0x0040:
            if (r7 == 0) goto L_0x0045
            r7.close()
        L_0x0045:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.internal.NativeProtocol.a(android.content.Context, com.facebook.internal.NativeProtocol$NativeAppInfo):java.util.TreeSet");
    }

    public static int computeLatestAvailableVersionFromVersionSpec(TreeSet<Integer> treeSet, int i, int[] iArr) {
        int length = iArr.length - 1;
        Iterator descendingIterator = treeSet.descendingIterator();
        int i2 = -1;
        int i3 = length;
        int i4 = -1;
        while (descendingIterator.hasNext()) {
            int intValue = ((Integer) descendingIterator.next()).intValue();
            i4 = Math.max(i4, intValue);
            while (i3 >= 0 && iArr[i3] > intValue) {
                i3--;
            }
            if (i3 < 0) {
                return -1;
            }
            if (iArr[i3] == intValue) {
                if (i3 % 2 == 0) {
                    i2 = Math.min(i4, i);
                }
                return i2;
            }
        }
        return -1;
    }

    private static Uri a(NativeAppInfo nativeAppInfo) {
        StringBuilder sb = new StringBuilder();
        sb.append("content://");
        sb.append(nativeAppInfo.a());
        sb.append(".provider.PlatformProvider/versions");
        return Uri.parse(sb.toString());
    }
}
