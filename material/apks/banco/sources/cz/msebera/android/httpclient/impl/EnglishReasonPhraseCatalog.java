package cz.msebera.android.httpclient.impl;

import ar.com.santander.rio.mbanking.utils.zurich_sdk.Constants.ResultValues;
import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.ReasonPhraseCatalog;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.util.Args;
import java.util.Locale;

@Immutable
public class EnglishReasonPhraseCatalog implements ReasonPhraseCatalog {
    public static final EnglishReasonPhraseCatalog INSTANCE = new EnglishReasonPhraseCatalog();
    private static final String[][] a = {null, new String[3], new String[8], new String[8], new String[25], new String[8]};

    static {
        a(200, ResultValues.OK);
        a(HttpStatus.SC_CREATED, "Created");
        a(HttpStatus.SC_ACCEPTED, "Accepted");
        a(HttpStatus.SC_NO_CONTENT, "No Content");
        a(HttpStatus.SC_MOVED_PERMANENTLY, "Moved Permanently");
        a(HttpStatus.SC_MOVED_TEMPORARILY, "Moved Temporarily");
        a(HttpStatus.SC_NOT_MODIFIED, "Not Modified");
        a(HttpStatus.SC_BAD_REQUEST, "Bad Request");
        a(HttpStatus.SC_UNAUTHORIZED, "Unauthorized");
        a(HttpStatus.SC_FORBIDDEN, "Forbidden");
        a(HttpStatus.SC_NOT_FOUND, "Not Found");
        a(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
        a(HttpStatus.SC_NOT_IMPLEMENTED, "Not Implemented");
        a(HttpStatus.SC_BAD_GATEWAY, "Bad Gateway");
        a(HttpStatus.SC_SERVICE_UNAVAILABLE, "Service Unavailable");
        a(100, "Continue");
        a(307, "Temporary Redirect");
        a(HttpStatus.SC_METHOD_NOT_ALLOWED, "Method Not Allowed");
        a(HttpStatus.SC_CONFLICT, "Conflict");
        a(HttpStatus.SC_PRECONDITION_FAILED, "Precondition Failed");
        a(HttpStatus.SC_REQUEST_TOO_LONG, "Request Too Long");
        a(HttpStatus.SC_REQUEST_URI_TOO_LONG, "Request-URI Too Long");
        a(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE, "Unsupported Media Type");
        a(HttpStatus.SC_MULTIPLE_CHOICES, "Multiple Choices");
        a(HttpStatus.SC_SEE_OTHER, "See Other");
        a(HttpStatus.SC_USE_PROXY, "Use Proxy");
        a(HttpStatus.SC_PAYMENT_REQUIRED, "Payment Required");
        a(HttpStatus.SC_NOT_ACCEPTABLE, "Not Acceptable");
        a(HttpStatus.SC_PROXY_AUTHENTICATION_REQUIRED, "Proxy Authentication Required");
        a(HttpStatus.SC_REQUEST_TIMEOUT, "Request Timeout");
        a(101, "Switching Protocols");
        a(HttpStatus.SC_NON_AUTHORITATIVE_INFORMATION, "Non Authoritative Information");
        a(HttpStatus.SC_RESET_CONTENT, "Reset Content");
        a(HttpStatus.SC_PARTIAL_CONTENT, "Partial Content");
        a(HttpStatus.SC_GATEWAY_TIMEOUT, "Gateway Timeout");
        a(HttpStatus.SC_HTTP_VERSION_NOT_SUPPORTED, "Http Version Not Supported");
        a(HttpStatus.SC_GONE, "Gone");
        a(HttpStatus.SC_LENGTH_REQUIRED, "Length Required");
        a(HttpStatus.SC_REQUESTED_RANGE_NOT_SATISFIABLE, "Requested Range Not Satisfiable");
        a(HttpStatus.SC_EXPECTATION_FAILED, "Expectation Failed");
        a(102, "Processing");
        a(HttpStatus.SC_MULTI_STATUS, "Multi-Status");
        a(HttpStatus.SC_UNPROCESSABLE_ENTITY, "Unprocessable Entity");
        a(HttpStatus.SC_INSUFFICIENT_SPACE_ON_RESOURCE, "Insufficient Space On Resource");
        a(HttpStatus.SC_METHOD_FAILURE, "Method Failure");
        a(HttpStatus.SC_LOCKED, "Locked");
        a(HttpStatus.SC_INSUFFICIENT_STORAGE, "Insufficient Storage");
        a(HttpStatus.SC_FAILED_DEPENDENCY, "Failed Dependency");
    }

    protected EnglishReasonPhraseCatalog() {
    }

    public String getReason(int i, Locale locale) {
        boolean z = i >= 100 && i < 600;
        StringBuilder sb = new StringBuilder();
        sb.append("Unknown category for status code ");
        sb.append(i);
        Args.check(z, sb.toString());
        int i2 = i / 100;
        int i3 = i - (i2 * 100);
        if (a[i2].length > i3) {
            return a[i2][i3];
        }
        return null;
    }

    private static void a(int i, String str) {
        int i2 = i / 100;
        a[i2][i - (i2 * 100)] = str;
    }
}
