package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import ar.com.santander.rio.mbanking.services.soap.request.BaseRequest;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public class ConfirmarRecalificacionRequest extends BaseRequest implements IBeanRequestWS {
    private ConfirmarRecalificacionRequestBean confirmarRecalificacionRequestBean;
    private ConfirmarRecalificacionResponseBean mConfirmarRecalificacionResponseBean;

    public int getMethod() {
        return 1;
    }

    public void onResponseBean(IBeanWS iBeanWS) {
    }

    public void onUnknowError(Exception exc) {
    }

    public ConfirmarRecalificacionRequest(Context context, ConfirmarRecalificacionRequestBean confirmarRecalificacionRequestBean2, ErrorRequestServer errorRequestServer) {
        super(context, false);
        this.confirmarRecalificacionRequestBean = confirmarRecalificacionRequestBean2;
        this.mErrorRequestServer = errorRequestServer;
    }

    public ConfirmarRecalificacionRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.confirmarRecalificacionRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mConfirmarRecalificacionResponseBean == null) {
            this.mConfirmarRecalificacionResponseBean = new ConfirmarRecalificacionResponseBean();
        }
        return this.mConfirmarRecalificacionResponseBean.getClass();
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(2:15|16) */
    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        r5 = new com.indra.httpclient.json.JSONArray();
        r5.put((java.lang.Object) r4.getJSONObject("producto"));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        r5 = new com.indra.httpclient.json.JSONArray();
        r5.put((java.lang.Object) r4.getJSONObject("leyenda"));
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0062 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:29:0x00a2 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean parserResponse(com.indra.httpclient.json.JSONObject r8) {
        /*
            r7 = this;
            boolean r0 = super.parserResponse(r8)
            if (r0 == 0) goto L_0x00e2
            com.google.gson.Gson r1 = new com.google.gson.Gson
            r1.<init>()
            java.lang.String r2 = "soapenv:Envelope"
            com.indra.httpclient.json.JSONObject r2 = r8.getJSONObject(r2)     // Catch:{ JSONException -> 0x00de }
            java.lang.String r3 = "soapenv:Body"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r3)     // Catch:{ JSONException -> 0x00de }
            java.lang.String r3 = "xml"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r3)     // Catch:{ JSONException -> 0x00de }
            ar.com.santander.rio.mbanking.services.soap.beans.body.ConfirmarRecalificacionResponseBean r3 = new ar.com.santander.rio.mbanking.services.soap.beans.body.ConfirmarRecalificacionResponseBean     // Catch:{ JSONException -> 0x00de }
            r3.<init>()     // Catch:{ JSONException -> 0x00de }
            java.lang.String r4 = "header"
            com.indra.httpclient.json.JSONObject r4 = r2.getJSONObject(r4)     // Catch:{ JSONException -> 0x00de }
            java.lang.String r4 = r4.toString()     // Catch:{ JSONException -> 0x00de }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean> r5 = ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean.class
            java.lang.Object r1 = r1.fromJson(r4, r5)     // Catch:{ JSONException -> 0x00de }
            ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean r1 = (ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean) r1     // Catch:{ JSONException -> 0x00de }
            ar.com.santander.rio.mbanking.services.soap.beans.body.ConfirmarRecalificacionBodyResponseBean r4 = new ar.com.santander.rio.mbanking.services.soap.beans.body.ConfirmarRecalificacionBodyResponseBean     // Catch:{ JSONException -> 0x00de }
            r4.<init>()     // Catch:{ JSONException -> 0x00de }
            java.lang.String r4 = "body"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r4)     // Catch:{ JSONException -> 0x00de }
            if (r2 == 0) goto L_0x007f
            java.lang.String r4 = "listaProductos"
            boolean r4 = r2.has(r4)     // Catch:{ JSONException -> 0x00de }
            if (r4 == 0) goto L_0x007f
            java.lang.String r4 = "listaProductos"
            java.lang.Object r4 = r2.get(r4)     // Catch:{ JSONException -> 0x00de }
            com.indra.httpclient.json.JSONObject r4 = (com.indra.httpclient.json.JSONObject) r4     // Catch:{ JSONException -> 0x00de }
            if (r4 == 0) goto L_0x007a
            java.lang.String r5 = "producto"
            boolean r5 = r4.has(r5)     // Catch:{ JSONException -> 0x00de }
            if (r5 == 0) goto L_0x007a
            java.lang.String r5 = "producto"
            com.indra.httpclient.json.JSONArray r5 = r4.getJSONArray(r5)     // Catch:{ JSONException -> 0x0062 }
            goto L_0x0070
        L_0x0062:
            com.indra.httpclient.json.JSONArray r5 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x00de }
            r5.<init>()     // Catch:{ JSONException -> 0x00de }
            java.lang.String r6 = "producto"
            com.indra.httpclient.json.JSONObject r6 = r4.getJSONObject(r6)     // Catch:{ JSONException -> 0x00de }
            r5.put(r6)     // Catch:{ JSONException -> 0x00de }
        L_0x0070:
            java.lang.String r6 = "producto"
            r4.remove(r6)     // Catch:{ JSONException -> 0x00de }
            java.lang.String r6 = "producto"
            r4.put(r6, r5)     // Catch:{ JSONException -> 0x00de }
        L_0x007a:
            java.lang.String r5 = "listaProductos"
            r2.put(r5, r4)     // Catch:{ JSONException -> 0x00de }
        L_0x007f:
            if (r2 == 0) goto L_0x00bf
            java.lang.String r4 = "listaLeyendas"
            boolean r4 = r2.has(r4)     // Catch:{ JSONException -> 0x00de }
            if (r4 == 0) goto L_0x00bf
            java.lang.String r4 = "listaLeyendas"
            java.lang.Object r4 = r2.get(r4)     // Catch:{ JSONException -> 0x00de }
            com.indra.httpclient.json.JSONObject r4 = (com.indra.httpclient.json.JSONObject) r4     // Catch:{ JSONException -> 0x00de }
            if (r4 == 0) goto L_0x00ba
            java.lang.String r5 = "leyenda"
            boolean r5 = r4.has(r5)     // Catch:{ JSONException -> 0x00de }
            if (r5 == 0) goto L_0x00ba
            java.lang.String r5 = "leyenda"
            com.indra.httpclient.json.JSONArray r5 = r4.getJSONArray(r5)     // Catch:{ JSONException -> 0x00a2 }
            goto L_0x00b0
        L_0x00a2:
            com.indra.httpclient.json.JSONArray r5 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x00de }
            r5.<init>()     // Catch:{ JSONException -> 0x00de }
            java.lang.String r6 = "leyenda"
            com.indra.httpclient.json.JSONObject r6 = r4.getJSONObject(r6)     // Catch:{ JSONException -> 0x00de }
            r5.put(r6)     // Catch:{ JSONException -> 0x00de }
        L_0x00b0:
            java.lang.String r6 = "leyenda"
            r4.remove(r6)     // Catch:{ JSONException -> 0x00de }
            java.lang.String r6 = "leyenda"
            r4.put(r6, r5)     // Catch:{ JSONException -> 0x00de }
        L_0x00ba:
            java.lang.String r5 = "listaLeyendas"
            r2.put(r5, r4)     // Catch:{ JSONException -> 0x00de }
        L_0x00bf:
            java.lang.String r4 = "body"
            r8.put(r4, r2)     // Catch:{ JSONException -> 0x00de }
            com.google.gson.Gson r8 = new com.google.gson.Gson     // Catch:{ JSONException -> 0x00de }
            r8.<init>()     // Catch:{ JSONException -> 0x00de }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ JSONException -> 0x00de }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.body.ConfirmarRecalificacionBodyResponseBean> r4 = ar.com.santander.rio.mbanking.services.soap.beans.body.ConfirmarRecalificacionBodyResponseBean.class
            java.lang.Object r8 = r8.fromJson(r2, r4)     // Catch:{ JSONException -> 0x00de }
            ar.com.santander.rio.mbanking.services.soap.beans.body.ConfirmarRecalificacionBodyResponseBean r8 = (ar.com.santander.rio.mbanking.services.soap.beans.body.ConfirmarRecalificacionBodyResponseBean) r8     // Catch:{ JSONException -> 0x00de }
            r3.confirmarRecalificacionBodyResponseBean(r8)     // Catch:{ JSONException -> 0x00de }
            r3.header = r1     // Catch:{ JSONException -> 0x00de }
            r7.onResponseBean(r3)     // Catch:{ JSONException -> 0x00de }
            goto L_0x00e2
        L_0x00de:
            r8 = move-exception
            r7.onUnknowError(r8)
        L_0x00e2:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.services.soap.beans.body.ConfirmarRecalificacionRequest.parserResponse(com.indra.httpclient.json.JSONObject):boolean");
    }
}
