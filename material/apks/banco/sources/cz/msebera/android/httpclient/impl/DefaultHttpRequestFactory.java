package cz.msebera.android.httpclient.impl;

import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpRequestFactory;
import cz.msebera.android.httpclient.MethodNotSupportedException;
import cz.msebera.android.httpclient.RequestLine;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.message.BasicHttpEntityEnclosingRequest;
import cz.msebera.android.httpclient.message.BasicHttpRequest;
import cz.msebera.android.httpclient.util.Args;

@Immutable
public class DefaultHttpRequestFactory implements HttpRequestFactory {
    public static final DefaultHttpRequestFactory INSTANCE = new DefaultHttpRequestFactory();
    private static final String[] a = {"GET"};
    private static final String[] b = {"POST", "PUT"};
    private static final String[] c = {"HEAD", "OPTIONS", "DELETE", "TRACE", "CONNECT"};

    private static boolean a(String[] strArr, String str) {
        for (String equalsIgnoreCase : strArr) {
            if (equalsIgnoreCase.equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }

    public HttpRequest newHttpRequest(RequestLine requestLine) {
        Args.notNull(requestLine, "Request line");
        String method = requestLine.getMethod();
        if (a(a, method)) {
            return new BasicHttpRequest(requestLine);
        }
        if (a(b, method)) {
            return new BasicHttpEntityEnclosingRequest(requestLine);
        }
        if (a(c, method)) {
            return new BasicHttpRequest(requestLine);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(method);
        sb.append(" method not supported");
        throw new MethodNotSupportedException(sb.toString());
    }

    public HttpRequest newHttpRequest(String str, String str2) {
        if (a(a, str)) {
            return new BasicHttpRequest(str, str2);
        }
        if (a(b, str)) {
            return new BasicHttpEntityEnclosingRequest(str, str2);
        }
        if (a(c, str)) {
            return new BasicHttpRequest(str, str2);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(" method not supported");
        throw new MethodNotSupportedException(sb.toString());
    }
}
