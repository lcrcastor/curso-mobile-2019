package com.facebook.android;

public class FacebookError extends RuntimeException {
    private static final long serialVersionUID = 1;
    private int a = 0;
    private String b;

    @Deprecated
    public FacebookError(String str) {
        super(str);
    }

    @Deprecated
    public FacebookError(String str, String str2, int i) {
        super(str);
        this.b = str2;
        this.a = i;
    }

    @Deprecated
    public int getErrorCode() {
        return this.a;
    }

    @Deprecated
    public String getErrorType() {
        return this.b;
    }
}
