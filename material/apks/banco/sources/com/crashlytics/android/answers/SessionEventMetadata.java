package com.crashlytics.android.answers;

final class SessionEventMetadata {
    public final String a;
    public final String b;
    public final String c;
    public final String d;
    public final String e;
    public final Boolean f;
    public final String g;
    public final String h;
    public final String i;
    public final String j;
    public final String k;
    public final String l;
    private String m;

    public SessionEventMetadata(String str, String str2, String str3, String str4, String str5, Boolean bool, String str6, String str7, String str8, String str9, String str10, String str11) {
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = str4;
        this.e = str5;
        this.f = bool;
        this.g = str6;
        this.h = str7;
        this.i = str8;
        this.j = str9;
        this.k = str10;
        this.l = str11;
    }

    public String toString() {
        if (this.m == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("appBundleId=");
            sb.append(this.a);
            sb.append(", executionId=");
            sb.append(this.b);
            sb.append(", installationId=");
            sb.append(this.c);
            sb.append(", androidId=");
            sb.append(this.d);
            sb.append(", advertisingId=");
            sb.append(this.e);
            sb.append(", limitAdTrackingEnabled=");
            sb.append(this.f);
            sb.append(", betaDeviceToken=");
            sb.append(this.g);
            sb.append(", buildId=");
            sb.append(this.h);
            sb.append(", osVersion=");
            sb.append(this.i);
            sb.append(", deviceModel=");
            sb.append(this.j);
            sb.append(", appVersionCode=");
            sb.append(this.k);
            sb.append(", appVersionName=");
            sb.append(this.l);
            this.m = sb.toString();
        }
        return this.m;
    }
}
