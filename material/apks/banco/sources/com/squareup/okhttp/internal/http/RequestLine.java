package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import cz.msebera.android.httpclient.message.TokenParser;
import java.net.Proxy.Type;
import java.net.URL;

public final class RequestLine {
    private RequestLine() {
    }

    static String a(Request request, Type type, Protocol protocol) {
        StringBuilder sb = new StringBuilder();
        sb.append(request.method());
        sb.append(TokenParser.SP);
        if (a(request, type)) {
            sb.append(request.url());
        } else {
            sb.append(requestPath(request.url()));
        }
        sb.append(TokenParser.SP);
        sb.append(version(protocol));
        return sb.toString();
    }

    private static boolean a(Request request, Type type) {
        return !request.isHttps() && type == Type.HTTP;
    }

    public static String requestPath(URL url) {
        String file = url.getFile();
        if (file == null) {
            return "/";
        }
        if (file.startsWith("/")) {
            return file;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("/");
        sb.append(file);
        return sb.toString();
    }

    public static String version(Protocol protocol) {
        return protocol == Protocol.HTTP_1_0 ? "HTTP/1.0" : "HTTP/1.1";
    }
}
