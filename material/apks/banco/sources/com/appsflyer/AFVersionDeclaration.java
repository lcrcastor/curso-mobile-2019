package com.appsflyer;

public class AFVersionDeclaration {
    private static String a;

    private AFVersionDeclaration() {
    }

    public static void init() {
        a = "!SDK-VERSION-STRING!:com.appsflyer:af-android-sdk:4.8.9";
    }
}
