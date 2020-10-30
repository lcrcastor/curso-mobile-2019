package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import ar.com.santander.rio.mbanking.services.soap.request.BaseRequest;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public class TenenciaCustodiaRequest extends BaseRequest implements IBeanRequestWS {
    private TenenciaCustodiaRequestBean mTenenciaCustodiaRequestBean;
    private TenenciaCustodiaResponseBean mTenenciaCustodiaResponseBean;

    public int getMethod() {
        return 1;
    }

    public void onResponseBean(IBeanWS iBeanWS) {
    }

    public void onUnknowError(Exception exc) {
    }

    public TenenciaCustodiaRequest(Context context, TenenciaCustodiaRequestBean tenenciaCustodiaRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, false);
        this.mTenenciaCustodiaRequestBean = tenenciaCustodiaRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public TenenciaCustodiaRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mTenenciaCustodiaRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mTenenciaCustodiaResponseBean == null) {
            this.mTenenciaCustodiaResponseBean = new TenenciaCustodiaResponseBean();
        }
        return this.mTenenciaCustodiaResponseBean.getClass();
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(2:23|24) */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        r7 = new com.indra.httpclient.json.JSONArray();
        r7.put((java.lang.Object) r6.getJSONObject("custodia"));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:?, code lost:
        r7 = new com.indra.httpclient.json.JSONArray();
        r7.put((java.lang.Object) r6.getJSONObject("custodia"));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:?, code lost:
        r5 = new com.indra.httpclient.json.JSONArray();
        r5.put((java.lang.Object) r4.getJSONObject("leyenda"));
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:23:0x0081 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:41:0x00ce */
    /* JADX WARNING: Missing exception handler attribute for start block: B:67:0x014a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean parserResponse(com.indra.httpclient.json.JSONObject r10) {
        /*
            r9 = this;
            boolean r0 = super.parserResponse(r10)
            if (r0 == 0) goto L_0x0185
            com.google.gson.Gson r1 = new com.google.gson.Gson
            r1.<init>()
            java.lang.String r2 = "soapenv:Envelope"
            com.indra.httpclient.json.JSONObject r2 = r10.getJSONObject(r2)     // Catch:{ JSONException -> 0x0181 }
            java.lang.String r3 = "soapenv:Body"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r3)     // Catch:{ JSONException -> 0x0181 }
            java.lang.String r3 = "xml"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r3)     // Catch:{ JSONException -> 0x0181 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.TenenciaCustodiaResponseBean r3 = new ar.com.santander.rio.mbanking.services.soap.beans.body.TenenciaCustodiaResponseBean     // Catch:{ JSONException -> 0x0181 }
            r3.<init>()     // Catch:{ JSONException -> 0x0181 }
            java.lang.String r4 = "header"
            com.indra.httpclient.json.JSONObject r4 = r2.getJSONObject(r4)     // Catch:{ JSONException -> 0x0181 }
            java.lang.String r4 = r4.toString()     // Catch:{ JSONException -> 0x0181 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean> r5 = ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean.class
            java.lang.Object r1 = r1.fromJson(r4, r5)     // Catch:{ JSONException -> 0x0181 }
            ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean r1 = (ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean) r1     // Catch:{ JSONException -> 0x0181 }
            java.lang.String r4 = "body"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r4)     // Catch:{ JSONException -> 0x0181 }
            if (r2 == 0) goto L_0x012b
            java.lang.String r4 = "cuenta"
            boolean r4 = r2.has(r4)     // Catch:{ JSONException -> 0x0181 }
            if (r4 == 0) goto L_0x012b
            java.lang.String r4 = "cuenta"
            java.lang.Object r4 = r2.get(r4)     // Catch:{ JSONException -> 0x0181 }
            com.indra.httpclient.json.JSONObject r4 = (com.indra.httpclient.json.JSONObject) r4     // Catch:{ JSONException -> 0x0181 }
            if (r4 == 0) goto L_0x0099
            java.lang.String r5 = "pesos"
            boolean r5 = r4.has(r5)     // Catch:{ JSONException -> 0x0181 }
            if (r5 == 0) goto L_0x0099
            java.lang.String r5 = "pesos"
            java.lang.Object r5 = r4.get(r5)     // Catch:{ JSONException -> 0x0181 }
            com.indra.httpclient.json.JSONObject r5 = (com.indra.httpclient.json.JSONObject) r5     // Catch:{ JSONException -> 0x0181 }
            if (r5 == 0) goto L_0x0094
            java.lang.String r6 = "custodias"
            boolean r6 = r5.has(r6)     // Catch:{ JSONException -> 0x0181 }
            if (r6 == 0) goto L_0x0094
            java.lang.String r6 = "custodias"
            java.lang.Object r6 = r5.get(r6)     // Catch:{ JSONException -> 0x0181 }
            com.indra.httpclient.json.JSONObject r6 = (com.indra.httpclient.json.JSONObject) r6     // Catch:{ JSONException -> 0x0181 }
            if (r6 == 0) goto L_0x0094
            java.lang.String r7 = "custodia"
            boolean r7 = r6.has(r7)     // Catch:{ JSONException -> 0x0181 }
            if (r7 == 0) goto L_0x0094
            java.lang.String r7 = "custodia"
            com.indra.httpclient.json.JSONArray r7 = r6.getJSONArray(r7)     // Catch:{ JSONException -> 0x0081 }
            goto L_0x008f
        L_0x0081:
            com.indra.httpclient.json.JSONArray r7 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x0181 }
            r7.<init>()     // Catch:{ JSONException -> 0x0181 }
            java.lang.String r8 = "custodia"
            com.indra.httpclient.json.JSONObject r6 = r6.getJSONObject(r8)     // Catch:{ JSONException -> 0x0181 }
            r7.put(r6)     // Catch:{ JSONException -> 0x0181 }
        L_0x008f:
            java.lang.String r6 = "custodias"
            r5.put(r6, r7)     // Catch:{ JSONException -> 0x0181 }
        L_0x0094:
            java.lang.String r6 = "pesos"
            r4.put(r6, r5)     // Catch:{ JSONException -> 0x0181 }
        L_0x0099:
            if (r4 == 0) goto L_0x00e6
            java.lang.String r5 = "otraMoneda"
            boolean r5 = r4.has(r5)     // Catch:{ JSONException -> 0x0181 }
            if (r5 == 0) goto L_0x00e6
            java.lang.String r5 = "otraMoneda"
            java.lang.Object r5 = r4.get(r5)     // Catch:{ JSONException -> 0x0181 }
            com.indra.httpclient.json.JSONObject r5 = (com.indra.httpclient.json.JSONObject) r5     // Catch:{ JSONException -> 0x0181 }
            if (r5 == 0) goto L_0x00e1
            java.lang.String r6 = "custodias"
            boolean r6 = r5.has(r6)     // Catch:{ JSONException -> 0x0181 }
            if (r6 == 0) goto L_0x00e1
            java.lang.String r6 = "custodias"
            java.lang.Object r6 = r5.get(r6)     // Catch:{ JSONException -> 0x0181 }
            com.indra.httpclient.json.JSONObject r6 = (com.indra.httpclient.json.JSONObject) r6     // Catch:{ JSONException -> 0x0181 }
            if (r6 == 0) goto L_0x00e1
            java.lang.String r7 = "custodia"
            boolean r7 = r6.has(r7)     // Catch:{ JSONException -> 0x0181 }
            if (r7 == 0) goto L_0x00e1
            java.lang.String r7 = "custodia"
            com.indra.httpclient.json.JSONArray r7 = r6.getJSONArray(r7)     // Catch:{ JSONException -> 0x00ce }
            goto L_0x00dc
        L_0x00ce:
            com.indra.httpclient.json.JSONArray r7 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x0181 }
            r7.<init>()     // Catch:{ JSONException -> 0x0181 }
            java.lang.String r8 = "custodia"
            com.indra.httpclient.json.JSONObject r6 = r6.getJSONObject(r8)     // Catch:{ JSONException -> 0x0181 }
            r7.put(r6)     // Catch:{ JSONException -> 0x0181 }
        L_0x00dc:
            java.lang.String r6 = "custodias"
            r5.put(r6, r7)     // Catch:{ JSONException -> 0x0181 }
        L_0x00e1:
            java.lang.String r6 = "otraMoneda"
            r4.put(r6, r5)     // Catch:{ JSONException -> 0x0181 }
        L_0x00e6:
            java.lang.String r5 = "tenReexpresada"
            boolean r5 = r4.has(r5)     // Catch:{ JSONException -> 0x0181 }
            r6 = 0
            if (r5 == 0) goto L_0x00f6
            java.lang.String r5 = "tenReexpresada"
            com.indra.httpclient.json.JSONObject r5 = r4.getJSONObject(r5)     // Catch:{ JSONException -> 0x0181 }
            goto L_0x00f7
        L_0x00f6:
            r5 = r6
        L_0x00f7:
            if (r5 == 0) goto L_0x0126
            java.lang.String r7 = "pesos"
            boolean r7 = r5.has(r7)     // Catch:{ JSONException -> 0x0181 }
            if (r7 == 0) goto L_0x0108
            java.lang.String r7 = "pesos"
            com.indra.httpclient.json.JSONObject r7 = r5.getJSONObject(r7)     // Catch:{ JSONException -> 0x0181 }
            goto L_0x0109
        L_0x0108:
            r7 = r6
        L_0x0109:
            java.lang.String r8 = "dolar"
            boolean r8 = r5.has(r8)     // Catch:{ JSONException -> 0x0181 }
            if (r8 == 0) goto L_0x0117
            java.lang.String r6 = "dolar"
            com.indra.httpclient.json.JSONObject r6 = r5.getJSONObject(r6)     // Catch:{ JSONException -> 0x0181 }
        L_0x0117:
            java.lang.String r8 = "pesos"
            r5.put(r8, r7)     // Catch:{ JSONException -> 0x0181 }
            java.lang.String r7 = "dolar"
            r5.put(r7, r6)     // Catch:{ JSONException -> 0x0181 }
            java.lang.String r6 = "tenReexpresada"
            r4.put(r6, r5)     // Catch:{ JSONException -> 0x0181 }
        L_0x0126:
            java.lang.String r5 = "cuenta"
            r2.put(r5, r4)     // Catch:{ JSONException -> 0x0181 }
        L_0x012b:
            if (r2 == 0) goto L_0x0162
            java.lang.String r4 = "listaLeyendas"
            boolean r4 = r2.has(r4)     // Catch:{ JSONException -> 0x0181 }
            if (r4 == 0) goto L_0x0162
            java.lang.String r4 = "listaLeyendas"
            com.indra.httpclient.json.JSONObject r4 = r2.getJSONObject(r4)     // Catch:{ JSONException -> 0x0181 }
            java.lang.String r5 = "leyenda"
            boolean r5 = r4.has(r5)     // Catch:{ JSONException -> 0x0181 }
            if (r5 == 0) goto L_0x015d
            java.lang.String r5 = "leyenda"
            com.indra.httpclient.json.JSONArray r5 = r4.getJSONArray(r5)     // Catch:{ JSONException -> 0x014a }
            goto L_0x0158
        L_0x014a:
            com.indra.httpclient.json.JSONArray r5 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x0181 }
            r5.<init>()     // Catch:{ JSONException -> 0x0181 }
            java.lang.String r6 = "leyenda"
            com.indra.httpclient.json.JSONObject r6 = r4.getJSONObject(r6)     // Catch:{ JSONException -> 0x0181 }
            r5.put(r6)     // Catch:{ JSONException -> 0x0181 }
        L_0x0158:
            java.lang.String r6 = "leyenda"
            r4.put(r6, r5)     // Catch:{ JSONException -> 0x0181 }
        L_0x015d:
            java.lang.String r5 = "listaLeyendas"
            r2.put(r5, r4)     // Catch:{ JSONException -> 0x0181 }
        L_0x0162:
            java.lang.String r4 = "body"
            r10.put(r4, r2)     // Catch:{ JSONException -> 0x0181 }
            com.google.gson.Gson r10 = new com.google.gson.Gson     // Catch:{ JSONException -> 0x0181 }
            r10.<init>()     // Catch:{ JSONException -> 0x0181 }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ JSONException -> 0x0181 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.body.TenenciaCustodiaBodyResponseBean> r4 = ar.com.santander.rio.mbanking.services.soap.beans.body.TenenciaCustodiaBodyResponseBean.class
            java.lang.Object r10 = r10.fromJson(r2, r4)     // Catch:{ JSONException -> 0x0181 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.TenenciaCustodiaBodyResponseBean r10 = (ar.com.santander.rio.mbanking.services.soap.beans.body.TenenciaCustodiaBodyResponseBean) r10     // Catch:{ JSONException -> 0x0181 }
            r3.setTenenciaCustodiaBodyResponseBean(r10)     // Catch:{ JSONException -> 0x0181 }
            r3.header = r1     // Catch:{ JSONException -> 0x0181 }
            r9.onResponseBean(r3)     // Catch:{ JSONException -> 0x0181 }
            goto L_0x0185
        L_0x0181:
            r10 = move-exception
            r9.onUnknowError(r10)
        L_0x0185:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.services.soap.beans.body.TenenciaCustodiaRequest.parserResponse(com.indra.httpclient.json.JSONObject):boolean");
    }
}
