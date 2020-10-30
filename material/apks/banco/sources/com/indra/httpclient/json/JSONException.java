package com.indra.httpclient.json;

public class JSONException extends Exception {
    private static final long serialVersionUID = 0;
    private Throwable a;

    public JSONException(String str) {
        super(str);
    }

    public JSONException(Throwable th) {
        super(th.getMessage());
        this.a = th;
    }

    public Throwable getCause() {
        return this.a;
    }
}
