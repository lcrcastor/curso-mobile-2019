package com.indra.httpclient.request;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.StringRequest;

public class XmlRequest {
    private StringRequest a;
    private final String b = "text/xml; charset=UTF-8";

    public XmlRequest(int i, String str, Listener listener, ErrorListener errorListener, byte[] bArr) {
        a(i, str, listener, errorListener, bArr);
    }

    public StringRequest getStringRequest() {
        return this.a;
    }

    private void a(int i, String str, Listener listener, ErrorListener errorListener, byte[] bArr) {
        final byte[] bArr2 = bArr;
        AnonymousClass1 r0 = new StringRequest(i, str, listener, errorListener) {
            public String getBodyContentType() {
                return "text/xml; charset=UTF-8";
            }

            public byte[] getBody() {
                try {
                    if (bArr2 != null) {
                        return bArr2;
                    }
                } catch (Exception unused) {
                }
                return super.getBody();
            }
        };
        this.a = r0;
    }

    public void setRetryPolicy(RetryPolicy retryPolicy) {
        if (this.a != null) {
            this.a.setRetryPolicy(retryPolicy);
        }
    }
}
