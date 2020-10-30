package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import ar.com.santander.rio.mbanking.services.soap.request.BaseRequest;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class BlanqueoPinRequest extends BaseRequest implements IBeanRequestWS {
    private BlanqueoPinRequestBean blanqueoPinRequestBean;
    private BlanqueoPinResponseBean blanqueoPinResponseBean;

    public int getMethod() {
        return 1;
    }

    public BlanqueoPinRequest(Context context, BlanqueoPinRequestBean blanqueoPinRequestBean2, ErrorRequestServer errorRequestServer) {
        super(context);
        this.blanqueoPinRequestBean = blanqueoPinRequestBean2;
        this.mErrorRequestServer = errorRequestServer;
    }

    public BlanqueoPinRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.blanqueoPinRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.blanqueoPinResponseBean == null) {
            this.blanqueoPinResponseBean = new BlanqueoPinResponseBean();
        }
        return this.blanqueoPinResponseBean.getClass();
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(2:11|12) */
    /* JADX WARNING: Code restructure failed: missing block: B:12:?, code lost:
        r5 = new com.indra.httpclient.json.JSONArray();
        r5.put((java.lang.Object) r4.getJSONObject("leyenda"));
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0057 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean parserResponse(com.indra.httpclient.json.JSONObject r8) {
        /*
            r7 = this;
            boolean r0 = super.parserResponse(r8)
            if (r0 == 0) goto L_0x00a3
            com.google.gson.Gson r1 = new com.google.gson.Gson
            r1.<init>()
            java.lang.String r2 = "soapenv:Envelope"
            com.indra.httpclient.json.JSONObject r2 = r8.getJSONObject(r2)     // Catch:{ JSONException -> 0x009f }
            java.lang.String r3 = "soapenv:Body"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r3)     // Catch:{ JSONException -> 0x009f }
            java.lang.String r3 = "xml"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r3)     // Catch:{ JSONException -> 0x009f }
            ar.com.santander.rio.mbanking.services.soap.beans.body.BlanqueoPinResponseBean r3 = new ar.com.santander.rio.mbanking.services.soap.beans.body.BlanqueoPinResponseBean     // Catch:{ JSONException -> 0x009f }
            r3.<init>()     // Catch:{ JSONException -> 0x009f }
            java.lang.String r4 = "header"
            com.indra.httpclient.json.JSONObject r4 = r2.getJSONObject(r4)     // Catch:{ JSONException -> 0x009f }
            java.lang.String r4 = r4.toString()     // Catch:{ JSONException -> 0x009f }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean> r5 = ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean.class
            java.lang.Object r1 = r1.fromJson(r4, r5)     // Catch:{ JSONException -> 0x009f }
            ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean r1 = (ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean) r1     // Catch:{ JSONException -> 0x009f }
            java.lang.String r4 = "body"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r4)     // Catch:{ JSONException -> 0x009f }
            java.lang.String r4 = "listaLeyendas"
            boolean r4 = r2.has(r4)     // Catch:{ JSONException -> 0x009f }
            if (r4 == 0) goto L_0x0085
            java.lang.String r4 = "listaLeyendas"
            com.indra.httpclient.json.JSONObject r4 = r2.getJSONObject(r4)     // Catch:{ JSONException -> 0x009f }
            java.lang.String r5 = "leyenda"
            boolean r5 = r4.has(r5)     // Catch:{ JSONException -> 0x009f }
            if (r5 == 0) goto L_0x0085
            java.lang.String r5 = "leyenda"
            com.indra.httpclient.json.JSONArray r5 = r4.getJSONArray(r5)     // Catch:{ JSONException -> 0x0057 }
            goto L_0x0065
        L_0x0057:
            com.indra.httpclient.json.JSONArray r5 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x009f }
            r5.<init>()     // Catch:{ JSONException -> 0x009f }
            java.lang.String r6 = "leyenda"
            com.indra.httpclient.json.JSONObject r6 = r4.getJSONObject(r6)     // Catch:{ JSONException -> 0x009f }
            r5.put(r6)     // Catch:{ JSONException -> 0x009f }
        L_0x0065:
            if (r4 == 0) goto L_0x0085
            java.lang.String r6 = "leyenda"
            r4.remove(r6)     // Catch:{ JSONException -> 0x009f }
            java.lang.String r6 = "leyenda"
            r4.put(r6, r5)     // Catch:{ JSONException -> 0x009f }
            java.lang.String r5 = "listaLeyendas"
            r2.remove(r5)     // Catch:{ JSONException -> 0x009f }
            java.lang.String r5 = "listaLeyendas"
            r2.put(r5, r4)     // Catch:{ JSONException -> 0x009f }
            java.lang.String r4 = "body"
            r8.remove(r4)     // Catch:{ JSONException -> 0x009f }
            java.lang.String r4 = "body"
            r8.put(r4, r2)     // Catch:{ JSONException -> 0x009f }
        L_0x0085:
            com.google.gson.Gson r8 = new com.google.gson.Gson     // Catch:{ JSONException -> 0x009f }
            r8.<init>()     // Catch:{ JSONException -> 0x009f }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ JSONException -> 0x009f }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.body.BlanqueoPinBodyResponseBean> r4 = ar.com.santander.rio.mbanking.services.soap.beans.body.BlanqueoPinBodyResponseBean.class
            java.lang.Object r8 = r8.fromJson(r2, r4)     // Catch:{ JSONException -> 0x009f }
            ar.com.santander.rio.mbanking.services.soap.beans.body.BlanqueoPinBodyResponseBean r8 = (ar.com.santander.rio.mbanking.services.soap.beans.body.BlanqueoPinBodyResponseBean) r8     // Catch:{ JSONException -> 0x009f }
            r3.setBlanqueoPinBodyResponseBean(r8)     // Catch:{ JSONException -> 0x009f }
            r3.header = r1     // Catch:{ JSONException -> 0x009f }
            r7.onResponseBean(r3)     // Catch:{ JSONException -> 0x009f }
            goto L_0x00a3
        L_0x009f:
            r8 = move-exception
            r7.onUnknowError(r8)
        L_0x00a3:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.services.soap.beans.body.BlanqueoPinRequest.parserResponse(com.indra.httpclient.json.JSONObject):boolean");
    }
}
