package io.fabric.sdk.android.services.common;

import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.util.Collections;
import java.util.Map;
import java.util.regex.Pattern;

public abstract class AbstractSpiCall {
    public static final String ACCEPT_JSON_VALUE = "application/json";
    public static final String ANDROID_CLIENT_TYPE = "android";
    public static final String CLS_ANDROID_SDK_DEVELOPER_TOKEN = "470fa2b4ae81cd56ecbcda9735803434cec591fa";
    public static final String CRASHLYTICS_USER_AGENT = "Crashlytics Android SDK/";
    public static final int DEFAULT_TIMEOUT = 10000;
    public static final String HEADER_ACCEPT = "Accept";
    public static final String HEADER_API_KEY = "X-CRASHLYTICS-API-KEY";
    public static final String HEADER_CLIENT_TYPE = "X-CRASHLYTICS-API-CLIENT-TYPE";
    public static final String HEADER_CLIENT_VERSION = "X-CRASHLYTICS-API-CLIENT-VERSION";
    public static final String HEADER_DEVELOPER_TOKEN = "X-CRASHLYTICS-DEVELOPER-TOKEN";
    public static final String HEADER_REQUEST_ID = "X-REQUEST-ID";
    public static final String HEADER_USER_AGENT = "User-Agent";
    private static final Pattern a = Pattern.compile("http(s?)://[^\\/]+", 2);
    private final String b;
    private final HttpRequestFactory c;
    private final HttpMethod d;
    private final String e;
    protected final Kit kit;

    public AbstractSpiCall(Kit kit2, String str, String str2, HttpRequestFactory httpRequestFactory, HttpMethod httpMethod) {
        if (str2 == null) {
            throw new IllegalArgumentException("url must not be null.");
        } else if (httpRequestFactory == null) {
            throw new IllegalArgumentException("requestFactory must not be null.");
        } else {
            this.kit = kit2;
            this.e = str;
            this.b = a(str2);
            this.c = httpRequestFactory;
            this.d = httpMethod;
        }
    }

    /* access modifiers changed from: protected */
    public String getUrl() {
        return this.b;
    }

    /* access modifiers changed from: protected */
    public HttpRequest getHttpRequest() {
        return getHttpRequest(Collections.emptyMap());
    }

    /* access modifiers changed from: protected */
    public HttpRequest getHttpRequest(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        sb.append(CRASHLYTICS_USER_AGENT);
        sb.append(this.kit.getVersion());
        return this.c.buildHttpRequest(this.d, getUrl(), map).useCaches(false).connectTimeout(10000).header("User-Agent", sb.toString()).header(HEADER_DEVELOPER_TOKEN, "470fa2b4ae81cd56ecbcda9735803434cec591fa");
    }

    private String a(String str) {
        return !CommonUtils.isNullOrEmpty(this.e) ? a.matcher(str).replaceFirst(this.e) : str;
    }
}
