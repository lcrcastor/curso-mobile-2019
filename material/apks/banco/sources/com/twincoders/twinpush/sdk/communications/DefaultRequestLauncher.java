package com.twincoders.twinpush.sdk.communications;

import android.content.Context;
import android.os.Handler;
import com.twincoders.twinpush.sdk.TwinPushSDK;
import com.twincoders.twinpush.sdk.communications.asyhttp.AsyncHttpClient;
import com.twincoders.twinpush.sdk.communications.asyhttp.AsyncHttpResponseHandler;
import com.twincoders.twinpush.sdk.communications.asyhttp.PersistentCookieStore;
import com.twincoders.twinpush.sdk.communications.security.TwinPushSSLSocketFactory;
import com.twincoders.twinpush.sdk.communications.security.TwinPushTrustManager;
import com.twincoders.twinpush.sdk.logging.Ln;
import cz.msebera.android.httpclient.client.HttpResponseException;
import cz.msebera.android.httpclient.entity.StringEntity;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;

public class DefaultRequestLauncher implements TwinRequestLauncher {
    Context a;
    int b = 60;
    private Map<TwinRequest, AsyncHttpClient> c = new HashMap();

    public DefaultRequestLauncher() {
    }

    public DefaultRequestLauncher(Context context) {
        this.a = context;
    }

    public void setTimeOutSeconds(int i) {
        this.b = i;
    }

    public void launchRequest(TwinRequest twinRequest) {
        Ln.v("Starting request: %s", twinRequest.getClass().getName());
        if (!this.c.containsKey(twinRequest)) {
            a(twinRequest);
        } else {
            Ln.w("Request already on queue. Ignoring...", new Object[0]);
        }
    }

    public void cancelRequest(TwinRequest twinRequest) {
        if (this.c.containsKey(twinRequest)) {
            ((AsyncHttpClient) this.c.get(twinRequest)).cancelRequests(getContext(), true);
            this.c.remove(twinRequest);
            Ln.v("Request canceled", new Object[0]);
            return;
        }
        Ln.v("Could not cancel request, not currently active", new Object[0]);
    }

    public Context getContext() {
        return this.a;
    }

    private void a(final TwinRequest twinRequest) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setTimeout(this.b * 1000);
        asyncHttpClient.setCookieStore(new PersistentCookieStore(this.a));
        AnonymousClass1 r5 = new AsyncHttpResponseHandler() {
            public void onFailure(Throwable th, String str) {
                boolean z;
                if (th instanceof HttpResponseException) {
                    z = twinRequest.isHttpResponseStatusValid(((HttpResponseException) th).getStatusCode());
                } else {
                    z = false;
                }
                if (z) {
                    onSuccess(str);
                    return;
                }
                Ln.w(th, "Request failed. Response: %s", str);
                DefaultRequestLauncher.this.b(twinRequest);
                twinRequest.onRequestError(new Exception(th));
            }

            public void onSuccess(String str) {
                Ln.v("OUTPUT: %s", str);
                DefaultRequestLauncher.this.b(twinRequest);
                twinRequest.onResponseProcess(str);
            }
        };
        twinRequest.onSetupClient(asyncHttpClient);
        this.c.put(twinRequest, asyncHttpClient);
        try {
            KeyStore instance = KeyStore.getInstance(KeyStore.getDefaultType());
            instance.load(null, null);
            TwinPushTrustManager twinPushTrustManager = new TwinPushTrustManager();
            TwinPushSDK instance2 = TwinPushSDK.getInstance(this.a);
            twinPushTrustManager.setPublicKey(instance2.getSSLPublicKeyCheck());
            twinPushTrustManager.setIssuerChecks(instance2.getSSLIssuerChecks());
            twinPushTrustManager.setSubjectChecks(instance2.getSSLSubjectChecks());
            TwinPushSSLSocketFactory twinPushSSLSocketFactory = new TwinPushSSLSocketFactory(instance, twinPushTrustManager);
            twinPushSSLSocketFactory.setHostnameVerifier(TwinPushSSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            asyncHttpClient.setSSLSocketFactory(twinPushSSLSocketFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!twinRequest.isDummy()) {
            String url = twinRequest.getURL();
            Ln.d("Launching request: %s", url);
            try {
                switch (twinRequest.getHttpMethod()) {
                    case GET:
                        asyncHttpClient.get(url, r5);
                        return;
                    case POST:
                        String bodyContent = twinRequest.getBodyContent();
                        Ln.d("INPUT: %s", bodyContent);
                        asyncHttpClient.post(getContext(), url, new StringEntity(bodyContent, twinRequest.getEncoding()), twinRequest.getContentType(), r5);
                        return;
                    case DELETE:
                        asyncHttpClient.delete(getContext(), url, r5);
                        return;
                    default:
                        return;
                }
            } catch (Exception e2) {
                Ln.w(e2, "Request failed", new Object[0]);
                b(twinRequest);
                twinRequest.onRequestError(e2);
            }
        } else {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    if (!twinRequest.isCanceled().booleanValue()) {
                        DefaultRequestLauncher.this.b(twinRequest);
                        twinRequest.onResponseProcess("");
                    }
                }
            }, 1000);
        }
    }

    /* access modifiers changed from: private */
    public void b(TwinRequest twinRequest) {
        this.c.remove(twinRequest);
    }
}
