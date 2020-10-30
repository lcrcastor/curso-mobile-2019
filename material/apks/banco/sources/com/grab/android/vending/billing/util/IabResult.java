package com.grab.android.vending.billing.util;

public class IabResult {
    int a;
    String b;

    public IabResult(int i, String str) {
        this.a = i;
        if (str == null || str.trim().length() == 0) {
            this.b = IabHelper.getResponseDesc(i);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(" (response: ");
        sb.append(IabHelper.getResponseDesc(i));
        sb.append(")");
        this.b = sb.toString();
    }

    public int getResponse() {
        return this.a;
    }

    public String getMessage() {
        return this.b;
    }

    public boolean isSuccess() {
        return this.a == 0;
    }

    public boolean isFailure() {
        return !isSuccess();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("IabResult: ");
        sb.append(getMessage());
        return sb.toString();
    }
}
