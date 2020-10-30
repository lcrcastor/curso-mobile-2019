package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import ar.com.santander.rio.mbanking.services.soap.request.BaseRequest;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public class GetLimitesProductosRequest extends BaseRequest implements IBeanRequestWS {
    private GetLimitesProductosRequestBean mgetLimitesProductosRequestBean;
    private GetLimitesProductosResponseBean mgetLimitesProductosResponseBean;

    public int getMethod() {
        return 1;
    }

    public void onResponseBean(IBeanWS iBeanWS) {
    }

    public void onUnknowError(Exception exc) {
    }

    public GetLimitesProductosRequest(Context context, GetLimitesProductosRequestBean getLimitesProductosRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, false);
        this.mgetLimitesProductosRequestBean = getLimitesProductosRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public GetLimitesProductosRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mgetLimitesProductosRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mgetLimitesProductosResponseBean == null) {
            this.mgetLimitesProductosResponseBean = new GetLimitesProductosResponseBean();
        }
        return this.mgetLimitesProductosResponseBean.getClass();
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
    /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
        r8 = new com.indra.httpclient.json.JSONArray();
        r8.put((java.lang.Object) r7.getJSONObject("variacion"));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:?, code lost:
        r5 = new com.indra.httpclient.json.JSONArray();
        r5.put((java.lang.Object) r4.getJSONObject("leyenda"));
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0062 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:30:0x00a4 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:46:0x0107 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean parserResponse(com.indra.httpclient.json.JSONObject r11) {
        /*
            r10 = this;
            boolean r0 = super.parserResponse(r11)
            if (r0 == 0) goto L_0x0147
            com.google.gson.Gson r1 = new com.google.gson.Gson
            r1.<init>()
            java.lang.String r2 = "soapenv:Envelope"
            com.indra.httpclient.json.JSONObject r2 = r11.getJSONObject(r2)     // Catch:{ JSONException -> 0x0143 }
            java.lang.String r3 = "soapenv:Body"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r3)     // Catch:{ JSONException -> 0x0143 }
            java.lang.String r3 = "xml"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r3)     // Catch:{ JSONException -> 0x0143 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.GetLimitesProductosResponseBean r3 = new ar.com.santander.rio.mbanking.services.soap.beans.body.GetLimitesProductosResponseBean     // Catch:{ JSONException -> 0x0143 }
            r3.<init>()     // Catch:{ JSONException -> 0x0143 }
            java.lang.String r4 = "header"
            com.indra.httpclient.json.JSONObject r4 = r2.getJSONObject(r4)     // Catch:{ JSONException -> 0x0143 }
            java.lang.String r4 = r4.toString()     // Catch:{ JSONException -> 0x0143 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean> r5 = ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean.class
            java.lang.Object r1 = r1.fromJson(r4, r5)     // Catch:{ JSONException -> 0x0143 }
            ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean r1 = (ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean) r1     // Catch:{ JSONException -> 0x0143 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.GetLimitesProductosBodyResponseBean r4 = new ar.com.santander.rio.mbanking.services.soap.beans.body.GetLimitesProductosBodyResponseBean     // Catch:{ JSONException -> 0x0143 }
            r4.<init>()     // Catch:{ JSONException -> 0x0143 }
            java.lang.String r4 = "body"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r4)     // Catch:{ JSONException -> 0x0143 }
            if (r2 == 0) goto L_0x00e4
            java.lang.String r4 = "listaProductos"
            boolean r4 = r2.has(r4)     // Catch:{ JSONException -> 0x0143 }
            if (r4 == 0) goto L_0x00e4
            java.lang.String r4 = "listaProductos"
            java.lang.Object r4 = r2.get(r4)     // Catch:{ JSONException -> 0x0143 }
            com.indra.httpclient.json.JSONObject r4 = (com.indra.httpclient.json.JSONObject) r4     // Catch:{ JSONException -> 0x0143 }
            if (r4 == 0) goto L_0x00df
            java.lang.String r5 = "producto"
            boolean r5 = r4.has(r5)     // Catch:{ JSONException -> 0x0143 }
            if (r5 == 0) goto L_0x00df
            java.lang.String r5 = "producto"
            com.indra.httpclient.json.JSONArray r5 = r4.getJSONArray(r5)     // Catch:{ JSONException -> 0x0062 }
            goto L_0x0070
        L_0x0062:
            com.indra.httpclient.json.JSONArray r5 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x0143 }
            r5.<init>()     // Catch:{ JSONException -> 0x0143 }
            java.lang.String r6 = "producto"
            com.indra.httpclient.json.JSONObject r6 = r4.getJSONObject(r6)     // Catch:{ JSONException -> 0x0143 }
            r5.put(r6)     // Catch:{ JSONException -> 0x0143 }
        L_0x0070:
            r6 = 0
        L_0x0071:
            int r7 = r5.length()     // Catch:{ JSONException -> 0x0143 }
            if (r6 >= r7) goto L_0x00d5
            java.lang.Object r7 = r5.get(r6)     // Catch:{ JSONException -> 0x0143 }
            com.indra.httpclient.json.JSONObject r7 = (com.indra.httpclient.json.JSONObject) r7     // Catch:{ JSONException -> 0x0143 }
            java.lang.String r8 = "listaVariacion"
            boolean r7 = r7.has(r8)     // Catch:{ JSONException -> 0x0143 }
            if (r7 == 0) goto L_0x0092
            java.lang.Object r7 = r5.get(r6)     // Catch:{ JSONException -> 0x0143 }
            com.indra.httpclient.json.JSONObject r7 = (com.indra.httpclient.json.JSONObject) r7     // Catch:{ JSONException -> 0x0143 }
            java.lang.String r8 = "listaVariacion"
            com.indra.httpclient.json.JSONObject r7 = r7.getJSONObject(r8)     // Catch:{ JSONException -> 0x0143 }
            goto L_0x0093
        L_0x0092:
            r7 = 0
        L_0x0093:
            if (r7 == 0) goto L_0x00bc
            java.lang.String r8 = "variacion"
            boolean r8 = r7.has(r8)     // Catch:{ JSONException -> 0x0143 }
            if (r8 == 0) goto L_0x00bc
            java.lang.String r8 = "variacion"
            com.indra.httpclient.json.JSONArray r8 = r7.getJSONArray(r8)     // Catch:{ JSONException -> 0x00a4 }
            goto L_0x00b2
        L_0x00a4:
            com.indra.httpclient.json.JSONArray r8 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x0143 }
            r8.<init>()     // Catch:{ JSONException -> 0x0143 }
            java.lang.String r9 = "variacion"
            com.indra.httpclient.json.JSONObject r9 = r7.getJSONObject(r9)     // Catch:{ JSONException -> 0x0143 }
            r8.put(r9)     // Catch:{ JSONException -> 0x0143 }
        L_0x00b2:
            java.lang.String r9 = "variacion"
            r7.remove(r9)     // Catch:{ JSONException -> 0x0143 }
            java.lang.String r9 = "variacion"
            r7.put(r9, r8)     // Catch:{ JSONException -> 0x0143 }
        L_0x00bc:
            java.lang.Object r8 = r5.get(r6)     // Catch:{ JSONException -> 0x0143 }
            com.indra.httpclient.json.JSONObject r8 = (com.indra.httpclient.json.JSONObject) r8     // Catch:{ JSONException -> 0x0143 }
            java.lang.String r9 = "listaVariacion"
            r8.remove(r9)     // Catch:{ JSONException -> 0x0143 }
            java.lang.Object r8 = r5.get(r6)     // Catch:{ JSONException -> 0x0143 }
            com.indra.httpclient.json.JSONObject r8 = (com.indra.httpclient.json.JSONObject) r8     // Catch:{ JSONException -> 0x0143 }
            java.lang.String r9 = "listaVariacion"
            r8.put(r9, r7)     // Catch:{ JSONException -> 0x0143 }
            int r6 = r6 + 1
            goto L_0x0071
        L_0x00d5:
            java.lang.String r6 = "producto"
            r4.remove(r6)     // Catch:{ JSONException -> 0x0143 }
            java.lang.String r6 = "producto"
            r4.put(r6, r5)     // Catch:{ JSONException -> 0x0143 }
        L_0x00df:
            java.lang.String r5 = "listaProductos"
            r2.put(r5, r4)     // Catch:{ JSONException -> 0x0143 }
        L_0x00e4:
            if (r2 == 0) goto L_0x0124
            java.lang.String r4 = "listaLeyendas"
            boolean r4 = r2.has(r4)     // Catch:{ JSONException -> 0x0143 }
            if (r4 == 0) goto L_0x0124
            java.lang.String r4 = "listaLeyendas"
            java.lang.Object r4 = r2.get(r4)     // Catch:{ JSONException -> 0x0143 }
            com.indra.httpclient.json.JSONObject r4 = (com.indra.httpclient.json.JSONObject) r4     // Catch:{ JSONException -> 0x0143 }
            if (r4 == 0) goto L_0x011f
            java.lang.String r5 = "leyenda"
            boolean r5 = r4.has(r5)     // Catch:{ JSONException -> 0x0143 }
            if (r5 == 0) goto L_0x011f
            java.lang.String r5 = "leyenda"
            com.indra.httpclient.json.JSONArray r5 = r4.getJSONArray(r5)     // Catch:{ JSONException -> 0x0107 }
            goto L_0x0115
        L_0x0107:
            com.indra.httpclient.json.JSONArray r5 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x0143 }
            r5.<init>()     // Catch:{ JSONException -> 0x0143 }
            java.lang.String r6 = "leyenda"
            com.indra.httpclient.json.JSONObject r6 = r4.getJSONObject(r6)     // Catch:{ JSONException -> 0x0143 }
            r5.put(r6)     // Catch:{ JSONException -> 0x0143 }
        L_0x0115:
            java.lang.String r6 = "leyenda"
            r4.remove(r6)     // Catch:{ JSONException -> 0x0143 }
            java.lang.String r6 = "leyenda"
            r4.put(r6, r5)     // Catch:{ JSONException -> 0x0143 }
        L_0x011f:
            java.lang.String r5 = "listaLeyendas"
            r2.put(r5, r4)     // Catch:{ JSONException -> 0x0143 }
        L_0x0124:
            java.lang.String r4 = "body"
            r11.put(r4, r2)     // Catch:{ JSONException -> 0x0143 }
            com.google.gson.Gson r11 = new com.google.gson.Gson     // Catch:{ JSONException -> 0x0143 }
            r11.<init>()     // Catch:{ JSONException -> 0x0143 }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ JSONException -> 0x0143 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.body.GetLimitesProductosBodyResponseBean> r4 = ar.com.santander.rio.mbanking.services.soap.beans.body.GetLimitesProductosBodyResponseBean.class
            java.lang.Object r11 = r11.fromJson(r2, r4)     // Catch:{ JSONException -> 0x0143 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.GetLimitesProductosBodyResponseBean r11 = (ar.com.santander.rio.mbanking.services.soap.beans.body.GetLimitesProductosBodyResponseBean) r11     // Catch:{ JSONException -> 0x0143 }
            r3.setLimitesProductosBodyResponseBean(r11)     // Catch:{ JSONException -> 0x0143 }
            r3.header = r1     // Catch:{ JSONException -> 0x0143 }
            r10.onResponseBean(r3)     // Catch:{ JSONException -> 0x0143 }
            goto L_0x0147
        L_0x0143:
            r11 = move-exception
            r10.onUnknowError(r11)
        L_0x0147:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.services.soap.beans.body.GetLimitesProductosRequest.parserResponse(com.indra.httpclient.json.JSONObject):boolean");
    }
}
