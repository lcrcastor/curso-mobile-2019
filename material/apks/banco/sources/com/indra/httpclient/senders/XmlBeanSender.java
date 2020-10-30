package com.indra.httpclient.senders;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.client.ClientWS;
import com.indra.httpclient.client.CustomRetry;
import com.indra.httpclient.constants.ConstantWS;
import com.indra.httpclient.json.JSONObject;
import com.indra.httpclient.request.XmlRequest;
import com.indra.httpclient.utils.BackgroundThread;
import com.indra.httpclient.utils.UtilBeanXml;

public abstract class XmlBeanSender<T> {
    /* access modifiers changed from: private */
    public boolean automaticJsonParsing;
    private ClientWS mClientWS;
    /* access modifiers changed from: private */
    public Handler mUiHandler = new Handler();
    private XmlRequest mXmlRequest;
    String responseGlobalString = "";
    private RetryPolicy retryPolicy;

    public abstract void onResponseServer(IBeanWS iBeanWS);

    public abstract void onResponseServer(JSONObject jSONObject);

    public abstract void onVolleyErrorServer(VolleyError volleyError);

    public XmlBeanSender(Context context, boolean z) {
        this.mClientWS = new ClientWS(context);
        this.automaticJsonParsing = z;
    }

    public void setRetryPolicy(RetryPolicy retryPolicy2) {
        this.retryPolicy = retryPolicy2;
    }

    public void sendRequest(IBeanRequestWS iBeanRequestWS, String str) {
        try {
            XmlRequest xmlRequest = new XmlRequest(iBeanRequestWS.getMethod(), iBeanRequestWS.getUrlWS(), getResponseListener(iBeanRequestWS.getBeanResponseClass()), getResponseErrorListener(), UtilBeanXml.beanToStringXml(iBeanRequestWS.getBeanToRequest(), "").getBytes(ConstantWS.DEFAULT_ENCODE_XML));
            this.mXmlRequest = xmlRequest;
            if (this.retryPolicy != null) {
                this.mXmlRequest.setRetryPolicy(this.retryPolicy);
            } else {
                this.mXmlRequest.setRetryPolicy(new CustomRetry());
            }
            this.mClientWS.addToRequestQueue(this.mXmlRequest.getStringRequest().setTag(str));
        } catch (Exception e) {
            Log.e("@dev", "Error al enviar la petición al servidor", e);
        }
    }

    public void sendRequest(IBeanRequestWS iBeanRequestWS, String str, String str2) {
        try {
            XmlRequest xmlRequest = new XmlRequest(iBeanRequestWS.getMethod(), iBeanRequestWS.getUrlWS(), getResponseListener(iBeanRequestWS.getBeanResponseClass()), getResponseErrorListener(), UtilBeanXml.beanToStringXml(iBeanRequestWS.getBeanToRequest(), "").replaceAll("<body>(\\S+)</body>", str2).getBytes(ConstantWS.DEFAULT_ENCODE_XML));
            this.mXmlRequest = xmlRequest;
            if (this.retryPolicy != null) {
                this.mXmlRequest.setRetryPolicy(this.retryPolicy);
            } else {
                this.mXmlRequest.setRetryPolicy(new CustomRetry());
            }
            this.mClientWS.addToRequestQueue(this.mXmlRequest.getStringRequest());
            setTagRequest(str);
        } catch (Exception e) {
            Log.e("@dev", "Error al enviar la petición al servidor", e);
        }
    }

    private Listener<String> getResponseListener(final Class<T> cls) {
        return new Listener<String>() {
            /* renamed from: a */
            public void onResponse(String str) {
                XmlBeanSender.this.responseGlobalString = str;
                BackgroundThread backgroundThread = new BackgroundThread("xmlBeanSenderThread");
                backgroundThread.start();
                backgroundThread.prepareHandler();
                backgroundThread.postTask(new Runnable() {
                    public void run() {
                        if (XmlBeanSender.this.automaticJsonParsing) {
                            final IBeanWS stringXmlToBean = UtilBeanXml.stringXmlToBean(XmlBeanSender.this.responseGlobalString, cls);
                            XmlBeanSender.this.mUiHandler.post(new Runnable() {
                                public void run() {
                                    XmlBeanSender.this.onResponseServer(stringXmlToBean);
                                }
                            });
                            if (Looper.myLooper() != Looper.getMainLooper()) {
                                Looper.myLooper().quit();
                                return;
                            }
                            return;
                        }
                        final JSONObject convertStringXmlToJsonObject = UtilBeanXml.convertStringXmlToJsonObject(XmlBeanSender.this.responseGlobalString, null);
                        XmlBeanSender.this.mUiHandler.post(new Runnable() {
                            public void run() {
                                XmlBeanSender.this.onResponseServer(convertStringXmlToJsonObject);
                            }
                        });
                        if (Looper.myLooper() != Looper.getMainLooper()) {
                            Looper.myLooper().quit();
                        }
                    }
                });
            }
        };
    }

    private ErrorListener getResponseErrorListener() {
        return new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                XmlBeanSender.this.onVolleyErrorServer(volleyError);
            }
        };
    }

    public void setTagRequest(String str) {
        this.mXmlRequest.getStringRequest().setTag(str);
    }

    public void cancelRequest() {
        this.mClientWS.cancelAllRequest();
    }

    public void cancelRequest(String str) {
        this.mClientWS.cancelRequest(str);
    }

    public ClientWS getClientWsToSender() {
        return this.mClientWS;
    }
}
