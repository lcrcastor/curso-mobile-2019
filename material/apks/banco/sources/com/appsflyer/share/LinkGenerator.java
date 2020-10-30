package com.appsflyer.share;

import android.content.Context;
import com.appsflyer.AFLogger;
import com.appsflyer.AppsFlyerProperties;
import com.appsflyer.CreateOneLinkHttpTask.ResponseListener;
import com.appsflyer.ServerConfigHandler;
import cz.msebera.android.httpclient.HttpHost;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class LinkGenerator {
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;
    private String h;
    private String i;
    private String j;
    private String k;
    private Map<String, String> l = new HashMap();
    private Map<String, String> m = new HashMap();

    public LinkGenerator(String str) {
        this.c = str;
    }

    /* access modifiers changed from: 0000 */
    public final LinkGenerator a(String str) {
        this.i = str;
        return this;
    }

    public LinkGenerator setDeeplinkPath(String str) {
        this.j = str;
        return this;
    }

    public LinkGenerator setBaseDeeplink(String str) {
        this.k = str;
        return this;
    }

    /* access modifiers changed from: 0000 */
    public final LinkGenerator b(String str) {
        this.h = str;
        return this;
    }

    public LinkGenerator setChannel(String str) {
        this.a = str;
        return this;
    }

    public String getChannel() {
        return this.a;
    }

    public LinkGenerator setReferrerCustomerId(String str) {
        this.e = str;
        return this;
    }

    public String getMediaSource() {
        return this.c;
    }

    public Map<String, String> getParameters() {
        return this.l;
    }

    public LinkGenerator setCampaign(String str) {
        this.b = str;
        return this;
    }

    public String getCampaign() {
        return this.b;
    }

    public LinkGenerator addParameter(String str, String str2) {
        this.l.put(str, str2);
        return this;
    }

    public LinkGenerator addParameters(Map<String, String> map) {
        if (map != null) {
            this.l.putAll(map);
        }
        return this;
    }

    public LinkGenerator setReferrerUID(String str) {
        this.d = str;
        return this;
    }

    public LinkGenerator setReferrerName(String str) {
        this.f = str;
        return this;
    }

    public LinkGenerator setReferrerImageURL(String str) {
        this.g = str;
        return this;
    }

    public LinkGenerator setBaseURL(String str, String str2, String str3) {
        if (str == null || str.length() <= 0) {
            this.h = String.format(Constants.AF_BASE_URL_FORMAT, new Object[]{ServerConfigHandler.getUrl(Constants.APPSFLYER_DEFAULT_APP_DOMAIN), str3});
        } else {
            if (str2 == null || str2.length() < 5) {
                str2 = Constants.ONELINK_DEFAULT_DOMAIN;
            }
            this.h = String.format(Constants.AF_BASE_URL_FORMAT, new Object[]{str2, str});
        }
        return this;
    }

    private StringBuilder a() {
        StringBuilder sb = new StringBuilder();
        if (this.h == null || !this.h.startsWith(HttpHost.DEFAULT_SCHEME_NAME)) {
            sb.append(ServerConfigHandler.getUrl(Constants.BASE_URL_APP_APPSFLYER_COM));
        } else {
            sb.append(this.h);
        }
        if (this.i != null) {
            sb.append('/');
            sb.append(this.i);
        }
        this.m.put(Constants.URL_MEDIA_SOURCE, this.c);
        sb.append('?');
        sb.append("pid=");
        sb.append(a(this.c, "media source"));
        if (this.d != null) {
            this.m.put(Constants.URL_REFERRER_UID, this.d);
            sb.append('&');
            sb.append("af_referrer_uid=");
            sb.append(a(this.d, "referrerUID"));
        }
        if (this.a != null) {
            this.m.put("af_channel", this.a);
            sb.append('&');
            sb.append("af_channel=");
            sb.append(a(this.a, AppsFlyerProperties.CHANNEL));
        }
        if (this.e != null) {
            this.m.put(Constants.URL_REFERRER_CUSTOMER_ID, this.e);
            sb.append('&');
            sb.append("af_referrer_customer_id=");
            sb.append(a(this.e, "referrerCustomerId"));
        }
        if (this.b != null) {
            this.m.put(Constants.URL_CAMPAIGN, this.b);
            sb.append('&');
            sb.append("c=");
            sb.append(a(this.b, "campaign"));
        }
        if (this.f != null) {
            this.m.put(Constants.URL_REFERRER_NAME, this.f);
            sb.append('&');
            sb.append("af_referrer_name=");
            sb.append(a(this.f, "referrerName"));
        }
        if (this.g != null) {
            this.m.put(Constants.URL_REFERRER_IMAGE_URL, this.g);
            sb.append('&');
            sb.append("af_referrer_image_url=");
            sb.append(a(this.g, "referrerImageURL"));
        }
        if (this.k != null) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.k);
            sb2.append(this.k.endsWith("/") ? "" : "/");
            if (this.j != null) {
                sb2.append(this.j);
            }
            this.m.put(Constants.URL_BASE_DEEPLINK, sb2.toString());
            sb.append('&');
            sb.append("af_dp=");
            sb.append(a(this.k, "baseDeeplink"));
            if (this.j != null) {
                sb.append(this.k.endsWith("/") ? "" : "%2F");
                sb.append(a(this.j, "deeplinkPath"));
            }
        }
        for (String str : this.l.keySet()) {
            String obj = sb.toString();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            sb3.append("=");
            sb3.append(a((String) this.l.get(str), str));
            if (!obj.contains(sb3.toString())) {
                sb.append('&');
                sb.append(str);
                sb.append('=');
                sb.append(a((String) this.l.get(str), str));
            }
        }
        return sb;
    }

    private static String a(String str, String str2) {
        try {
            return URLEncoder.encode(str, "utf8");
        } catch (UnsupportedEncodingException unused) {
            StringBuilder sb = new StringBuilder("Illegal ");
            sb.append(str2);
            sb.append(": ");
            sb.append(str);
            AFLogger.afInfoLog(sb.toString());
            return "";
        } catch (Throwable unused2) {
            return "";
        }
    }

    public String generateLink() {
        return a().toString();
    }

    public void generateLink(Context context, ResponseListener responseListener) {
        String string = AppsFlyerProperties.getInstance().getString(AppsFlyerProperties.ONELINK_ID);
        if (!this.l.isEmpty()) {
            for (Entry entry : this.l.entrySet()) {
                this.m.put(entry.getKey(), entry.getValue());
            }
        }
        a();
        ShareInviteHelper.generateUserInviteLink(context, string, this.m, responseListener);
    }
}
