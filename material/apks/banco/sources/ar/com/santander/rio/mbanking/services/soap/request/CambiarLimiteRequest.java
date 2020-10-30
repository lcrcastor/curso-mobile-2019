package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.CambiarLimiteRequestBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public class CambiarLimiteRequest extends BaseRequest implements IBeanRequestWS {
    private final String TAG = GetLimitesExtraccionRequest.class.getName();
    private CambiarLimiteRequestBean mCambiarLimiteRequestBean;

    public int getMethod() {
        return 1;
    }

    public void onResponseBean(IBeanWS iBeanWS) {
    }

    public void onUnknowError(Exception exc) {
    }

    public CambiarLimiteRequest(Context context, CambiarLimiteRequestBean cambiarLimiteRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, false);
        this.mCambiarLimiteRequestBean = cambiarLimiteRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public CambiarLimiteRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mCambiarLimiteRequestBean;
    }

    public Class getBeanResponseClass() {
        return getClass();
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(2:11|12) */
    /* JADX WARNING: Code restructure failed: missing block: B:12:?, code lost:
        r4 = new com.indra.httpclient.json.JSONArray();
        r4.put((java.lang.Object) r3.getJSONObject("leyenda"));
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0057 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean parserResponse(com.indra.httpclient.json.JSONObject r7) {
        /*
            r6 = this;
            boolean r0 = super.parserResponse(r7)
            if (r0 == 0) goto L_0x008d
            com.google.gson.Gson r1 = new com.google.gson.Gson
            r1.<init>()
            java.lang.String r2 = "soapenv:Envelope"
            com.indra.httpclient.json.JSONObject r7 = r7.getJSONObject(r2)     // Catch:{ JSONException -> 0x0089 }
            java.lang.String r2 = "soapenv:Body"
            com.indra.httpclient.json.JSONObject r7 = r7.getJSONObject(r2)     // Catch:{ JSONException -> 0x0089 }
            java.lang.String r2 = "xml"
            com.indra.httpclient.json.JSONObject r7 = r7.getJSONObject(r2)     // Catch:{ JSONException -> 0x0089 }
            ar.com.santander.rio.mbanking.services.soap.beans.CambiarLimiteResponseBean r2 = new ar.com.santander.rio.mbanking.services.soap.beans.CambiarLimiteResponseBean     // Catch:{ JSONException -> 0x0089 }
            r2.<init>()     // Catch:{ JSONException -> 0x0089 }
            java.lang.String r3 = "header"
            com.indra.httpclient.json.JSONObject r3 = r7.getJSONObject(r3)     // Catch:{ JSONException -> 0x0089 }
            java.lang.String r3 = r3.toString()     // Catch:{ JSONException -> 0x0089 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean> r4 = ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean.class
            java.lang.Object r1 = r1.fromJson(r3, r4)     // Catch:{ JSONException -> 0x0089 }
            ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean r1 = (ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean) r1     // Catch:{ JSONException -> 0x0089 }
            java.lang.String r3 = "body"
            com.indra.httpclient.json.JSONObject r7 = r7.getJSONObject(r3)     // Catch:{ JSONException -> 0x0089 }
            java.lang.String r3 = "listaLeyendas"
            boolean r3 = r7.has(r3)     // Catch:{ JSONException -> 0x0089 }
            if (r3 == 0) goto L_0x006f
            java.lang.String r3 = "listaLeyendas"
            com.indra.httpclient.json.JSONObject r3 = r7.getJSONObject(r3)     // Catch:{ JSONException -> 0x0089 }
            java.lang.String r4 = "leyenda"
            boolean r4 = r3.has(r4)     // Catch:{ JSONException -> 0x0089 }
            if (r4 == 0) goto L_0x006a
            java.lang.String r4 = "leyenda"
            com.indra.httpclient.json.JSONArray r4 = r3.getJSONArray(r4)     // Catch:{ JSONException -> 0x0057 }
            goto L_0x0065
        L_0x0057:
            com.indra.httpclient.json.JSONArray r4 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x0089 }
            r4.<init>()     // Catch:{ JSONException -> 0x0089 }
            java.lang.String r5 = "leyenda"
            com.indra.httpclient.json.JSONObject r5 = r3.getJSONObject(r5)     // Catch:{ JSONException -> 0x0089 }
            r4.put(r5)     // Catch:{ JSONException -> 0x0089 }
        L_0x0065:
            java.lang.String r5 = "leyenda"
            r3.put(r5, r4)     // Catch:{ JSONException -> 0x0089 }
        L_0x006a:
            java.lang.String r4 = "listaLeyendas"
            r7.put(r4, r3)     // Catch:{ JSONException -> 0x0089 }
        L_0x006f:
            com.google.gson.Gson r3 = new com.google.gson.Gson     // Catch:{ JSONException -> 0x0089 }
            r3.<init>()     // Catch:{ JSONException -> 0x0089 }
            java.lang.String r7 = java.lang.String.valueOf(r7)     // Catch:{ JSONException -> 0x0089 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.body.CambiarLimiteBodyResponseBean> r4 = ar.com.santander.rio.mbanking.services.soap.beans.body.CambiarLimiteBodyResponseBean.class
            java.lang.Object r7 = r3.fromJson(r7, r4)     // Catch:{ JSONException -> 0x0089 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.CambiarLimiteBodyResponseBean r7 = (ar.com.santander.rio.mbanking.services.soap.beans.body.CambiarLimiteBodyResponseBean) r7     // Catch:{ JSONException -> 0x0089 }
            r2.setCambiarLimiteBodyResponseBean(r7)     // Catch:{ JSONException -> 0x0089 }
            r2.headerBean = r1     // Catch:{ JSONException -> 0x0089 }
            r6.onResponseBean(r2)     // Catch:{ JSONException -> 0x0089 }
            goto L_0x008d
        L_0x0089:
            r7 = move-exception
            r6.onUnknowError(r7)
        L_0x008d:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.services.soap.request.CambiarLimiteRequest.parserResponse(com.indra.httpclient.json.JSONObject):boolean");
    }
}
