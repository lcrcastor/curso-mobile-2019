package com.appsflyer.cache;

import java.util.Scanner;

public class RequestCacheData {
    private String a;
    private String b;
    private String c;
    private String d;

    public RequestCacheData(String str, String str2, String str3) {
        this.c = str;
        this.b = str2;
        this.a = str3;
    }

    public RequestCacheData(char[] cArr) {
        Scanner scanner = new Scanner(new String(cArr));
        while (scanner.hasNextLine()) {
            String nextLine = scanner.nextLine();
            if (nextLine.startsWith("url=")) {
                this.c = nextLine.substring(4).trim();
            } else if (nextLine.startsWith("version=")) {
                this.a = nextLine.substring(8).trim();
            } else if (nextLine.startsWith("data=")) {
                this.b = nextLine.substring(5).trim();
            }
        }
        scanner.close();
    }

    public String getVersion() {
        return this.a;
    }

    public void setVersion(String str) {
        this.a = str;
    }

    public String getPostData() {
        return this.b;
    }

    public void setPostData(String str) {
        this.b = str;
    }

    public String getRequestURL() {
        return this.c;
    }

    public void setRequestURL(String str) {
        this.c = str;
    }

    public String getCacheKey() {
        return this.d;
    }

    public void setCacheKey(String str) {
        this.d = str;
    }
}
