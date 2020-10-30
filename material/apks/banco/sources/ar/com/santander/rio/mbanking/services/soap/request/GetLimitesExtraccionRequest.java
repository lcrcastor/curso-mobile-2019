package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.GetLimitesExtraccionRequestBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public class GetLimitesExtraccionRequest extends BaseRequest implements IBeanRequestWS {
    private final String TAG = GetLimitesExtraccionRequest.class.getName();
    private GetLimitesExtraccionRequestBean mGetLimitesExtraccionRequestBean;

    public int getMethod() {
        return 1;
    }

    public void onResponseBean(IBeanWS iBeanWS) {
    }

    public void onUnknowError(Exception exc) {
    }

    public GetLimitesExtraccionRequest(Context context, GetLimitesExtraccionRequestBean getLimitesExtraccionRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, false);
        this.mGetLimitesExtraccionRequestBean = getLimitesExtraccionRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public GetLimitesExtraccionRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mGetLimitesExtraccionRequestBean;
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
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        r4 = new com.indra.httpclient.json.JSONArray();
        r4.put((java.lang.Object) r3.getJSONObject("limite"));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:?, code lost:
        r4 = new com.indra.httpclient.json.JSONArray();
        r4.put((java.lang.Object) r3.getJSONObject("limite"));
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0057 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:22:0x008c */
    /* JADX WARNING: Missing exception handler attribute for start block: B:33:0x00c1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean parserResponse(com.indra.httpclient.json.JSONObject r7) {
        /*
            r6 = this;
            boolean r0 = super.parserResponse(r7)
            if (r0 == 0) goto L_0x00f7
            com.google.gson.Gson r1 = new com.google.gson.Gson
            r1.<init>()
            java.lang.String r2 = "soapenv:Envelope"
            com.indra.httpclient.json.JSONObject r7 = r7.getJSONObject(r2)     // Catch:{ JSONException -> 0x00f3 }
            java.lang.String r2 = "soapenv:Body"
            com.indra.httpclient.json.JSONObject r7 = r7.getJSONObject(r2)     // Catch:{ JSONException -> 0x00f3 }
            java.lang.String r2 = "xml"
            com.indra.httpclient.json.JSONObject r7 = r7.getJSONObject(r2)     // Catch:{ JSONException -> 0x00f3 }
            ar.com.santander.rio.mbanking.services.soap.beans.GetLimitesExtraccionResponseBean r2 = new ar.com.santander.rio.mbanking.services.soap.beans.GetLimitesExtraccionResponseBean     // Catch:{ JSONException -> 0x00f3 }
            r2.<init>()     // Catch:{ JSONException -> 0x00f3 }
            java.lang.String r3 = "header"
            com.indra.httpclient.json.JSONObject r3 = r7.getJSONObject(r3)     // Catch:{ JSONException -> 0x00f3 }
            java.lang.String r3 = r3.toString()     // Catch:{ JSONException -> 0x00f3 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean> r4 = ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean.class
            java.lang.Object r1 = r1.fromJson(r3, r4)     // Catch:{ JSONException -> 0x00f3 }
            ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean r1 = (ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean) r1     // Catch:{ JSONException -> 0x00f3 }
            java.lang.String r3 = "body"
            com.indra.httpclient.json.JSONObject r7 = r7.getJSONObject(r3)     // Catch:{ JSONException -> 0x00f3 }
            java.lang.String r3 = "listaLeyendas"
            boolean r3 = r7.has(r3)     // Catch:{ JSONException -> 0x00f3 }
            if (r3 == 0) goto L_0x006f
            java.lang.String r3 = "listaLeyendas"
            com.indra.httpclient.json.JSONObject r3 = r7.getJSONObject(r3)     // Catch:{ JSONException -> 0x00f3 }
            java.lang.String r4 = "leyenda"
            boolean r4 = r3.has(r4)     // Catch:{ JSONException -> 0x00f3 }
            if (r4 == 0) goto L_0x006a
            java.lang.String r4 = "leyenda"
            com.indra.httpclient.json.JSONArray r4 = r3.getJSONArray(r4)     // Catch:{ JSONException -> 0x0057 }
            goto L_0x0065
        L_0x0057:
            com.indra.httpclient.json.JSONArray r4 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x00f3 }
            r4.<init>()     // Catch:{ JSONException -> 0x00f3 }
            java.lang.String r5 = "leyenda"
            com.indra.httpclient.json.JSONObject r5 = r3.getJSONObject(r5)     // Catch:{ JSONException -> 0x00f3 }
            r4.put(r5)     // Catch:{ JSONException -> 0x00f3 }
        L_0x0065:
            java.lang.String r5 = "leyenda"
            r3.put(r5, r4)     // Catch:{ JSONException -> 0x00f3 }
        L_0x006a:
            java.lang.String r4 = "listaLeyendas"
            r7.put(r4, r3)     // Catch:{ JSONException -> 0x00f3 }
        L_0x006f:
            java.lang.String r3 = "listaLimitesTemporales"
            boolean r3 = r7.has(r3)     // Catch:{ JSONException -> 0x00f3 }
            if (r3 == 0) goto L_0x00a4
            java.lang.String r3 = "listaLimitesTemporales"
            com.indra.httpclient.json.JSONObject r3 = r7.getJSONObject(r3)     // Catch:{ JSONException -> 0x00f3 }
            java.lang.String r4 = "limite"
            boolean r4 = r3.has(r4)     // Catch:{ JSONException -> 0x00f3 }
            if (r4 == 0) goto L_0x009f
            java.lang.String r4 = "limite"
            com.indra.httpclient.json.JSONArray r4 = r3.getJSONArray(r4)     // Catch:{ JSONException -> 0x008c }
            goto L_0x009a
        L_0x008c:
            com.indra.httpclient.json.JSONArray r4 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x00f3 }
            r4.<init>()     // Catch:{ JSONException -> 0x00f3 }
            java.lang.String r5 = "limite"
            com.indra.httpclient.json.JSONObject r5 = r3.getJSONObject(r5)     // Catch:{ JSONException -> 0x00f3 }
            r4.put(r5)     // Catch:{ JSONException -> 0x00f3 }
        L_0x009a:
            java.lang.String r5 = "limite"
            r3.put(r5, r4)     // Catch:{ JSONException -> 0x00f3 }
        L_0x009f:
            java.lang.String r4 = "listaLimitesTemporales"
            r7.put(r4, r3)     // Catch:{ JSONException -> 0x00f3 }
        L_0x00a4:
            java.lang.String r3 = "listaLimitesPermanentes"
            boolean r3 = r7.has(r3)     // Catch:{ JSONException -> 0x00f3 }
            if (r3 == 0) goto L_0x00d9
            java.lang.String r3 = "listaLimitesPermanentes"
            com.indra.httpclient.json.JSONObject r3 = r7.getJSONObject(r3)     // Catch:{ JSONException -> 0x00f3 }
            java.lang.String r4 = "limite"
            boolean r4 = r3.has(r4)     // Catch:{ JSONException -> 0x00f3 }
            if (r4 == 0) goto L_0x00d4
            java.lang.String r4 = "limite"
            com.indra.httpclient.json.JSONArray r4 = r3.getJSONArray(r4)     // Catch:{ JSONException -> 0x00c1 }
            goto L_0x00cf
        L_0x00c1:
            com.indra.httpclient.json.JSONArray r4 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x00f3 }
            r4.<init>()     // Catch:{ JSONException -> 0x00f3 }
            java.lang.String r5 = "limite"
            com.indra.httpclient.json.JSONObject r5 = r3.getJSONObject(r5)     // Catch:{ JSONException -> 0x00f3 }
            r4.put(r5)     // Catch:{ JSONException -> 0x00f3 }
        L_0x00cf:
            java.lang.String r5 = "limite"
            r3.put(r5, r4)     // Catch:{ JSONException -> 0x00f3 }
        L_0x00d4:
            java.lang.String r4 = "listaLimitesPermanentes"
            r7.put(r4, r3)     // Catch:{ JSONException -> 0x00f3 }
        L_0x00d9:
            com.google.gson.Gson r3 = new com.google.gson.Gson     // Catch:{ JSONException -> 0x00f3 }
            r3.<init>()     // Catch:{ JSONException -> 0x00f3 }
            java.lang.String r7 = java.lang.String.valueOf(r7)     // Catch:{ JSONException -> 0x00f3 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.body.GetLimitesExtraccionBodyResponseBean> r4 = ar.com.santander.rio.mbanking.services.soap.beans.body.GetLimitesExtraccionBodyResponseBean.class
            java.lang.Object r7 = r3.fromJson(r7, r4)     // Catch:{ JSONException -> 0x00f3 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.GetLimitesExtraccionBodyResponseBean r7 = (ar.com.santander.rio.mbanking.services.soap.beans.body.GetLimitesExtraccionBodyResponseBean) r7     // Catch:{ JSONException -> 0x00f3 }
            r2.setGetLimitesExtraccionBodyResponseBean(r7)     // Catch:{ JSONException -> 0x00f3 }
            r2.headerBean = r1     // Catch:{ JSONException -> 0x00f3 }
            r6.onResponseBean(r2)     // Catch:{ JSONException -> 0x00f3 }
            goto L_0x00f7
        L_0x00f3:
            r7 = move-exception
            r6.onUnknowError(r7)
        L_0x00f7:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.services.soap.request.GetLimitesExtraccionRequest.parserResponse(com.indra.httpclient.json.JSONObject):boolean");
    }
}
