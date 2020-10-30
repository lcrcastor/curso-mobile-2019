package com.crashlytics.android.core;

public class UserMetaData {
    public static final UserMetaData EMPTY = new UserMetaData();
    public final String email;

    /* renamed from: id reason: collision with root package name */
    public final String f270id;
    public final String name;

    public UserMetaData() {
        this(null, null, null);
    }

    public UserMetaData(String str, String str2, String str3) {
        this.f270id = str;
        this.name = str2;
        this.email = str3;
    }

    public boolean isEmpty() {
        return this.f270id == null && this.name == null && this.email == null;
    }
}
