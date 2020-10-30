package com.facebook;

public class FacebookDialogException extends FacebookException {
    static final long serialVersionUID = 1;
    private int a;
    private String b;

    public FacebookDialogException(String str, int i, String str2) {
        super(str);
        this.a = i;
        this.b = str2;
    }

    public int getErrorCode() {
        return this.a;
    }

    public String getFailingUrl() {
        return this.b;
    }
}
