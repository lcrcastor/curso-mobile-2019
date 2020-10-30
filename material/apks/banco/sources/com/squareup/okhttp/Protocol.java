package com.squareup.okhttp;

import java.io.IOException;

public enum Protocol {
    HTTP_1_0("http/1.0"),
    HTTP_1_1("http/1.1"),
    SPDY_3("spdy/3.1"),
    HTTP_2("h2");
    
    private final String a;

    private Protocol(String str) {
        this.a = str;
    }

    public static Protocol get(String str) {
        if (str.equals(HTTP_1_0.a)) {
            return HTTP_1_0;
        }
        if (str.equals(HTTP_1_1.a)) {
            return HTTP_1_1;
        }
        if (str.equals(HTTP_2.a)) {
            return HTTP_2;
        }
        if (str.equals(SPDY_3.a)) {
            return SPDY_3;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unexpected protocol: ");
        sb.append(str);
        throw new IOException(sb.toString());
    }

    public String toString() {
        return this.a;
    }
}
