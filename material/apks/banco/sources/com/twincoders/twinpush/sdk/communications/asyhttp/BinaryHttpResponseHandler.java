package com.twincoders.twinpush.sdk.communications.asyhttp;

import android.os.Message;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.StatusLine;
import cz.msebera.android.httpclient.client.HttpResponseException;
import cz.msebera.android.httpclient.entity.BufferedHttpEntity;
import cz.msebera.android.httpclient.util.EntityUtils;
import java.io.IOException;
import java.util.regex.Pattern;

public class BinaryHttpResponseHandler extends AsyncHttpResponseHandler {
    private static String[] a = {"image/jpeg", "image/png"};

    public void onSuccess(byte[] bArr) {
    }

    public BinaryHttpResponseHandler() {
    }

    public BinaryHttpResponseHandler(String[] strArr) {
        this();
        a = strArr;
    }

    public void onSuccess(int i, byte[] bArr) {
        onSuccess(bArr);
    }

    @Deprecated
    public void onFailure(Throwable th, byte[] bArr) {
        onFailure(th);
    }

    /* access modifiers changed from: protected */
    public void sendSuccessMessage(int i, byte[] bArr) {
        sendMessage(obtainMessage(0, new Object[]{Integer.valueOf(i), bArr}));
    }

    /* access modifiers changed from: protected */
    public void sendFailureMessage(Throwable th, byte[] bArr) {
        sendMessage(obtainMessage(1, new Object[]{th, bArr}));
    }

    /* access modifiers changed from: protected */
    public void handleSuccessMessage(int i, byte[] bArr) {
        onSuccess(i, bArr);
    }

    /* access modifiers changed from: protected */
    public void handleFailureMessage(Throwable th, byte[] bArr) {
        onFailure(th, bArr);
    }

    /* access modifiers changed from: protected */
    public void handleMessage(Message message) {
        switch (message.what) {
            case 0:
                Object[] objArr = (Object[]) message.obj;
                handleSuccessMessage(((Integer) objArr[0]).intValue(), (byte[]) objArr[1]);
                return;
            case 1:
                Object[] objArr2 = (Object[]) message.obj;
                handleFailureMessage((Throwable) objArr2[0], objArr2[1].toString());
                return;
            default:
                super.handleMessage(message);
                return;
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(HttpResponse httpResponse) {
        byte[] bArr;
        StatusLine statusLine = httpResponse.getStatusLine();
        Header[] headers = httpResponse.getHeaders("Content-Type");
        if (headers.length != 1) {
            sendFailureMessage(new HttpResponseException(statusLine.getStatusCode(), "None, or more than one, Content-Type Header found!"), null);
            return;
        }
        Header header = headers[0];
        boolean z = false;
        for (String matches : a) {
            if (Pattern.matches(matches, header.getValue())) {
                z = true;
            }
        }
        if (!z) {
            sendFailureMessage(new HttpResponseException(statusLine.getStatusCode(), "Content-Type not allowed!"), null);
            return;
        }
        try {
            HttpEntity entity = httpResponse.getEntity();
            bArr = EntityUtils.toByteArray(entity != null ? new BufferedHttpEntity(entity) : null);
        } catch (IOException e) {
            sendFailureMessage(e, null);
            bArr = null;
        }
        if (statusLine.getStatusCode() >= 300) {
            sendFailureMessage(new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase()), bArr);
        } else {
            sendSuccessMessage(statusLine.getStatusCode(), bArr);
        }
    }
}
