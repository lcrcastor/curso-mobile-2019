package com.crashlytics.android.beta;

import java.io.InputStream;
import java.util.Properties;

class BuildProperties {
    public final String a;
    public final String b;
    public final String c;
    public final String d;

    BuildProperties(String str, String str2, String str3, String str4) {
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = str4;
    }

    public static BuildProperties a(Properties properties) {
        return new BuildProperties(properties.getProperty("version_code"), properties.getProperty("version_name"), properties.getProperty("build_id"), properties.getProperty("package_name"));
    }

    public static BuildProperties a(InputStream inputStream) {
        Properties properties = new Properties();
        properties.load(inputStream);
        return a(properties);
    }
}
