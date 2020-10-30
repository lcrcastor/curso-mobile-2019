package io.fabric.sdk.android;

public class KitInfo {
    private final String a;
    private final String b;
    private final String c;

    public KitInfo(String str, String str2, String str3) {
        this.a = str;
        this.b = str2;
        this.c = str3;
    }

    public String getIdentifier() {
        return this.a;
    }

    public String getVersion() {
        return this.b;
    }

    public String getBuildType() {
        return this.c;
    }
}
