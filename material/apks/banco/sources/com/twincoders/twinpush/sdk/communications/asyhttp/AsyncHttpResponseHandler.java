package com.twincoders.twinpush.sdk.communications.asyhttp;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.StatusLine;
import cz.msebera.android.httpclient.client.HttpResponseException;
import cz.msebera.android.httpclient.entity.BufferedHttpEntity;
import cz.msebera.android.httpclient.util.EntityUtils;
import java.io.IOException;

public class AsyncHttpResponseHandler {
    protected static final int FAILURE_MESSAGE = 1;
    protected static final int FINISH_MESSAGE = 3;
    protected static final int START_MESSAGE = 2;
    protected static final int SUCCESS_MESSAGE = 0;
    private Handler a;

    @Deprecated
    public void onFailure(Throwable th) {
    }

    public void onFinish() {
    }

    public void onStart() {
    }

    public void onSuccess(String str) {
    }

    public AsyncHttpResponseHandler() {
        if (Looper.myLooper() != null) {
            this.a = new Handler() {
                public void handleMessage(Message message) {
                    AsyncHttpResponseHandler.this.handleMessage(message);
                }
            };
        }
    }

    public void onSuccess(int i, Header[] headerArr, String str) {
        onSuccess(i, str);
    }

    public void onSuccess(int i, String str) {
        onSuccess(str);
    }

    public void onFailure(Throwable th, String str) {
        onFailure(th);
    }

    /* access modifiers changed from: protected */
    public void sendSuccessMessage(int i, Header[] headerArr, String str) {
        sendMessage(obtainMessage(0, new Object[]{Integer.valueOf(i), headerArr, str}));
    }

    /* access modifiers changed from: protected */
    public void sendFailureMessage(Throwable th, String str) {
        sendMessage(obtainMessage(1, new Object[]{th, str}));
    }

    /* access modifiers changed from: protected */
    public void sendFailureMessage(Throwable th, byte[] bArr) {
        sendMessage(obtainMessage(1, new Object[]{th, bArr}));
    }

    /* access modifiers changed from: protected */
    public void sendStartMessage() {
        sendMessage(obtainMessage(2, null));
    }

    /* access modifiers changed from: protected */
    public void sendFinishMessage() {
        sendMessage(obtainMessage(3, null));
    }

    /* access modifiers changed from: protected */
    public void handleSuccessMessage(int i, Header[] headerArr, String str) {
        onSuccess(i, headerArr, str);
    }

    /* access modifiers changed from: protected */
    public void handleFailureMessage(Throwable th, String str) {
        onFailure(th, str);
    }

    /* access modifiers changed from: protected */
    public void handleMessage(Message message) {
        switch (message.what) {
            case 0:
                Object[] objArr = (Object[]) message.obj;
                handleSuccessMessage(((Integer) objArr[0]).intValue(), (Header[]) objArr[1], (String) objArr[2]);
                return;
            case 1:
                Object[] objArr2 = (Object[]) message.obj;
                handleFailureMessage((Throwable) objArr2[0], (String) objArr2[1]);
                return;
            case 2:
                onStart();
                return;
            case 3:
                onFinish();
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: protected */
    public void sendMessage(Message message) {
        if (this.a != null) {
            this.a.sendMessage(message);
        } else {
            handleMessage(message);
        }
    }

    /* access modifiers changed from: protected */
    public Message obtainMessage(int i, Object obj) {
        if (this.a != null) {
            return this.a.obtainMessage(i, obj);
        }
        Message obtain = Message.obtain();
        obtain.what = i;
        obtain.obj = obj;
        return obtain;
    }

    /* access modifiers changed from: 0000 */
    public void a(HttpResponse httpResponse) {
        StatusLine statusLine = httpResponse.getStatusLine();
        String str = null;
        try {
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                str = EntityUtils.toString((HttpEntity) new BufferedHttpEntity(entity), "UTF-8");
            }
        } catch (IOException e) {
            sendFailureMessage((Throwable) e, (String) null);
        }
        if (statusLine.getStatusCode() >= 300) {
            sendFailureMessage((Throwable) new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase()), str);
        } else {
            sendSuccessMessage(statusLine.getStatusCode(), httpResponse.getAllHeaders(), str);
        }
    }
}
