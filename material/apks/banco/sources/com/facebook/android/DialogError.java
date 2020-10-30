package com.facebook.android;

public class DialogError extends Throwable {
    private static final long serialVersionUID = 1;
    private int a;
    private String b;

    @Deprecated
    public DialogError(String str, int i, String str2) {
        super(str);
        this.a = i;
        this.b = str2;
    }

    @Deprecated
    public int getErrorCode() {
        return this.a;
    }

    @Deprecated
    public String getFailingUrl() {
        return this.b;
    }
}
