package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import ar.com.santander.rio.mbanking.services.soap.request.BaseRequest;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public class TenenciaTitulosRequest extends BaseRequest implements IBeanRequestWS {
    private TenenciaTitulosRequestBean mTenenciaTitulosRequestBean;
    private TenenciaTitulosResponseBean mTenenciaTitulosResponseBean;

    public int getMethod() {
        return 1;
    }

    public void onResponseBean(IBeanWS iBeanWS) {
    }

    public void onUnknowError(Exception exc) {
    }

    public TenenciaTitulosRequest(Context context, TenenciaTitulosRequestBean tenenciaTitulosRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, false);
        this.mTenenciaTitulosRequestBean = tenenciaTitulosRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public TenenciaTitulosRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mTenenciaTitulosRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mTenenciaTitulosResponseBean == null) {
            this.mTenenciaTitulosResponseBean = new TenenciaTitulosResponseBean();
        }
        return this.mTenenciaTitulosResponseBean.getClass();
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(2:23|24) */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        r8 = new com.indra.httpclient.json.JSONArray();
        r8.put((java.lang.Object) r7.getJSONObject("categoriaTitulos"));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:?, code lost:
        r11 = new com.indra.httpclient.json.JSONArray();
        r11.put((java.lang.Object) r10.getJSONObject("titulo"));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:?, code lost:
        r8 = new com.indra.httpclient.json.JSONArray();
        r8.put((java.lang.Object) r7.getJSONObject("categoriaTitulos"));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:?, code lost:
        r10 = new com.indra.httpclient.json.JSONArray();
        r10.put((java.lang.Object) r9.getJSONObject("titulo"));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:?, code lost:
        r5 = new com.indra.httpclient.json.JSONArray();
        r5.put((java.lang.Object) r4.getJSONObject("leyenda"));
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:23:0x0082 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:39:0x00c8 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:55:0x0110 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:70:0x0155 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:86:0x01a3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean parserResponse(com.indra.httpclient.json.JSONObject r14) {
        /*
            r13 = this;
            boolean r0 = super.parserResponse(r14)
            if (r0 == 0) goto L_0x01de
            com.google.gson.Gson r1 = new com.google.gson.Gson
            r1.<init>()
            java.lang.String r2 = "soapenv:Envelope"
            com.indra.httpclient.json.JSONObject r2 = r14.getJSONObject(r2)     // Catch:{ JSONException -> 0x01da }
            java.lang.String r3 = "soapenv:Body"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r3)     // Catch:{ JSONException -> 0x01da }
            java.lang.String r3 = "xml"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r3)     // Catch:{ JSONException -> 0x01da }
            ar.com.santander.rio.mbanking.services.soap.beans.body.TenenciaTitulosResponseBean r3 = new ar.com.santander.rio.mbanking.services.soap.beans.body.TenenciaTitulosResponseBean     // Catch:{ JSONException -> 0x01da }
            r3.<init>()     // Catch:{ JSONException -> 0x01da }
            java.lang.String r4 = "header"
            com.indra.httpclient.json.JSONObject r4 = r2.getJSONObject(r4)     // Catch:{ JSONException -> 0x01da }
            java.lang.String r4 = r4.toString()     // Catch:{ JSONException -> 0x01da }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean> r5 = ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean.class
            java.lang.Object r1 = r1.fromJson(r4, r5)     // Catch:{ JSONException -> 0x01da }
            ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean r1 = (ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean) r1     // Catch:{ JSONException -> 0x01da }
            java.lang.String r4 = "body"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r4)     // Catch:{ JSONException -> 0x01da }
            if (r2 == 0) goto L_0x0184
            java.lang.String r4 = "cuenta"
            boolean r4 = r2.has(r4)     // Catch:{ JSONException -> 0x01da }
            if (r4 == 0) goto L_0x0184
            java.lang.String r4 = "cuenta"
            java.lang.Object r4 = r2.get(r4)     // Catch:{ JSONException -> 0x01da }
            com.indra.httpclient.json.JSONObject r4 = (com.indra.httpclient.json.JSONObject) r4     // Catch:{ JSONException -> 0x01da }
            if (r4 == 0) goto L_0x017f
            java.lang.String r5 = "titulosValores"
            boolean r5 = r4.has(r5)     // Catch:{ JSONException -> 0x01da }
            if (r5 == 0) goto L_0x017f
            java.lang.String r5 = "titulosValores"
            java.lang.Object r5 = r4.get(r5)     // Catch:{ JSONException -> 0x01da }
            com.indra.httpclient.json.JSONObject r5 = (com.indra.httpclient.json.JSONObject) r5     // Catch:{ JSONException -> 0x01da }
            r6 = 0
            if (r5 == 0) goto L_0x00ed
            java.lang.String r7 = "pesos"
            boolean r7 = r5.has(r7)     // Catch:{ JSONException -> 0x01da }
            if (r7 == 0) goto L_0x00ed
            java.lang.String r7 = "pesos"
            java.lang.Object r7 = r5.get(r7)     // Catch:{ JSONException -> 0x01da }
            com.indra.httpclient.json.JSONObject r7 = (com.indra.httpclient.json.JSONObject) r7     // Catch:{ JSONException -> 0x01da }
            if (r7 == 0) goto L_0x00e8
            java.lang.String r8 = "categoriaTitulos"
            boolean r8 = r7.has(r8)     // Catch:{ JSONException -> 0x01da }
            if (r8 == 0) goto L_0x00e8
            java.lang.String r8 = "categoriaTitulos"
            com.indra.httpclient.json.JSONArray r8 = r7.getJSONArray(r8)     // Catch:{ JSONException -> 0x0082 }
            goto L_0x0090
        L_0x0082:
            com.indra.httpclient.json.JSONArray r8 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x01da }
            r8.<init>()     // Catch:{ JSONException -> 0x01da }
            java.lang.String r9 = "categoriaTitulos"
            com.indra.httpclient.json.JSONObject r9 = r7.getJSONObject(r9)     // Catch:{ JSONException -> 0x01da }
            r8.put(r9)     // Catch:{ JSONException -> 0x01da }
        L_0x0090:
            r9 = 0
        L_0x0091:
            int r10 = r8.length()     // Catch:{ JSONException -> 0x01da }
            if (r9 >= r10) goto L_0x00e3
            java.lang.Object r10 = r8.get(r9)     // Catch:{ JSONException -> 0x01da }
            if (r10 == 0) goto L_0x00e0
            java.lang.Object r10 = r8.get(r9)     // Catch:{ JSONException -> 0x01da }
            com.indra.httpclient.json.JSONObject r10 = (com.indra.httpclient.json.JSONObject) r10     // Catch:{ JSONException -> 0x01da }
            java.lang.String r11 = "titulos"
            boolean r10 = r10.has(r11)     // Catch:{ JSONException -> 0x01da }
            if (r10 == 0) goto L_0x00e0
            java.lang.Object r10 = r8.get(r9)     // Catch:{ JSONException -> 0x01da }
            com.indra.httpclient.json.JSONObject r10 = (com.indra.httpclient.json.JSONObject) r10     // Catch:{ JSONException -> 0x01da }
            java.lang.String r11 = "titulos"
            com.indra.httpclient.json.JSONObject r10 = r10.getJSONObject(r11)     // Catch:{ JSONException -> 0x01da }
            if (r10 == 0) goto L_0x00e0
            java.lang.String r11 = "titulo"
            boolean r11 = r10.has(r11)     // Catch:{ JSONException -> 0x01da }
            if (r11 == 0) goto L_0x00e0
            java.lang.String r11 = "titulo"
            com.indra.httpclient.json.JSONArray r11 = r10.getJSONArray(r11)     // Catch:{ JSONException -> 0x00c8 }
            goto L_0x00d6
        L_0x00c8:
            com.indra.httpclient.json.JSONArray r11 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x01da }
            r11.<init>()     // Catch:{ JSONException -> 0x01da }
            java.lang.String r12 = "titulo"
            com.indra.httpclient.json.JSONObject r12 = r10.getJSONObject(r12)     // Catch:{ JSONException -> 0x01da }
            r11.put(r12)     // Catch:{ JSONException -> 0x01da }
        L_0x00d6:
            java.lang.String r12 = "titulo"
            r10.remove(r12)     // Catch:{ JSONException -> 0x01da }
            java.lang.String r12 = "titulo"
            r10.put(r12, r11)     // Catch:{ JSONException -> 0x01da }
        L_0x00e0:
            int r9 = r9 + 1
            goto L_0x0091
        L_0x00e3:
            java.lang.String r9 = "categoriaTitulos"
            r7.put(r9, r8)     // Catch:{ JSONException -> 0x01da }
        L_0x00e8:
            java.lang.String r8 = "pesos"
            r5.put(r8, r7)     // Catch:{ JSONException -> 0x01da }
        L_0x00ed:
            if (r5 == 0) goto L_0x017a
            java.lang.String r7 = "dolares"
            boolean r7 = r5.has(r7)     // Catch:{ JSONException -> 0x01da }
            if (r7 == 0) goto L_0x017a
            java.lang.String r7 = "dolares"
            java.lang.Object r7 = r5.get(r7)     // Catch:{ JSONException -> 0x01da }
            com.indra.httpclient.json.JSONObject r7 = (com.indra.httpclient.json.JSONObject) r7     // Catch:{ JSONException -> 0x01da }
            if (r7 == 0) goto L_0x0175
            java.lang.String r8 = "categoriaTitulos"
            boolean r8 = r7.has(r8)     // Catch:{ JSONException -> 0x01da }
            if (r8 == 0) goto L_0x0175
            java.lang.String r8 = "categoriaTitulos"
            com.indra.httpclient.json.JSONArray r8 = r7.getJSONArray(r8)     // Catch:{ JSONException -> 0x0110 }
            goto L_0x011e
        L_0x0110:
            com.indra.httpclient.json.JSONArray r8 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x01da }
            r8.<init>()     // Catch:{ JSONException -> 0x01da }
            java.lang.String r9 = "categoriaTitulos"
            com.indra.httpclient.json.JSONObject r9 = r7.getJSONObject(r9)     // Catch:{ JSONException -> 0x01da }
            r8.put(r9)     // Catch:{ JSONException -> 0x01da }
        L_0x011e:
            int r9 = r8.length()     // Catch:{ JSONException -> 0x01da }
            if (r6 >= r9) goto L_0x0170
            java.lang.Object r9 = r8.get(r6)     // Catch:{ JSONException -> 0x01da }
            if (r9 == 0) goto L_0x016d
            java.lang.Object r9 = r8.get(r6)     // Catch:{ JSONException -> 0x01da }
            com.indra.httpclient.json.JSONObject r9 = (com.indra.httpclient.json.JSONObject) r9     // Catch:{ JSONException -> 0x01da }
            java.lang.String r10 = "titulos"
            boolean r9 = r9.has(r10)     // Catch:{ JSONException -> 0x01da }
            if (r9 == 0) goto L_0x016d
            java.lang.Object r9 = r8.get(r6)     // Catch:{ JSONException -> 0x01da }
            com.indra.httpclient.json.JSONObject r9 = (com.indra.httpclient.json.JSONObject) r9     // Catch:{ JSONException -> 0x01da }
            java.lang.String r10 = "titulos"
            com.indra.httpclient.json.JSONObject r9 = r9.getJSONObject(r10)     // Catch:{ JSONException -> 0x01da }
            if (r9 == 0) goto L_0x016d
            java.lang.String r10 = "titulo"
            boolean r10 = r9.has(r10)     // Catch:{ JSONException -> 0x01da }
            if (r10 == 0) goto L_0x016d
            java.lang.String r10 = "titulo"
            com.indra.httpclient.json.JSONArray r10 = r9.getJSONArray(r10)     // Catch:{ JSONException -> 0x0155 }
            goto L_0x0163
        L_0x0155:
            com.indra.httpclient.json.JSONArray r10 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x01da }
            r10.<init>()     // Catch:{ JSONException -> 0x01da }
            java.lang.String r11 = "titulo"
            com.indra.httpclient.json.JSONObject r11 = r9.getJSONObject(r11)     // Catch:{ JSONException -> 0x01da }
            r10.put(r11)     // Catch:{ JSONException -> 0x01da }
        L_0x0163:
            java.lang.String r11 = "titulo"
            r9.remove(r11)     // Catch:{ JSONException -> 0x01da }
            java.lang.String r11 = "titulo"
            r9.put(r11, r10)     // Catch:{ JSONException -> 0x01da }
        L_0x016d:
            int r6 = r6 + 1
            goto L_0x011e
        L_0x0170:
            java.lang.String r6 = "categoriaTitulos"
            r7.put(r6, r8)     // Catch:{ JSONException -> 0x01da }
        L_0x0175:
            java.lang.String r6 = "dolares"
            r5.put(r6, r7)     // Catch:{ JSONException -> 0x01da }
        L_0x017a:
            java.lang.String r6 = "titulosValores"
            r4.put(r6, r5)     // Catch:{ JSONException -> 0x01da }
        L_0x017f:
            java.lang.String r5 = "cuenta"
            r2.put(r5, r4)     // Catch:{ JSONException -> 0x01da }
        L_0x0184:
            if (r2 == 0) goto L_0x01bb
            java.lang.String r4 = "listaLeyendas"
            boolean r4 = r2.has(r4)     // Catch:{ JSONException -> 0x01da }
            if (r4 == 0) goto L_0x01bb
            java.lang.String r4 = "listaLeyendas"
            com.indra.httpclient.json.JSONObject r4 = r2.getJSONObject(r4)     // Catch:{ JSONException -> 0x01da }
            java.lang.String r5 = "leyenda"
            boolean r5 = r4.has(r5)     // Catch:{ JSONException -> 0x01da }
            if (r5 == 0) goto L_0x01b6
            java.lang.String r5 = "leyenda"
            com.indra.httpclient.json.JSONArray r5 = r4.getJSONArray(r5)     // Catch:{ JSONException -> 0x01a3 }
            goto L_0x01b1
        L_0x01a3:
            com.indra.httpclient.json.JSONArray r5 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x01da }
            r5.<init>()     // Catch:{ JSONException -> 0x01da }
            java.lang.String r6 = "leyenda"
            com.indra.httpclient.json.JSONObject r6 = r4.getJSONObject(r6)     // Catch:{ JSONException -> 0x01da }
            r5.put(r6)     // Catch:{ JSONException -> 0x01da }
        L_0x01b1:
            java.lang.String r6 = "leyenda"
            r4.put(r6, r5)     // Catch:{ JSONException -> 0x01da }
        L_0x01b6:
            java.lang.String r5 = "listaLeyendas"
            r2.put(r5, r4)     // Catch:{ JSONException -> 0x01da }
        L_0x01bb:
            java.lang.String r4 = "body"
            r14.put(r4, r2)     // Catch:{ JSONException -> 0x01da }
            com.google.gson.Gson r14 = new com.google.gson.Gson     // Catch:{ JSONException -> 0x01da }
            r14.<init>()     // Catch:{ JSONException -> 0x01da }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ JSONException -> 0x01da }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.body.TenenciaTitulosBodyResponseBean> r4 = ar.com.santander.rio.mbanking.services.soap.beans.body.TenenciaTitulosBodyResponseBean.class
            java.lang.Object r14 = r14.fromJson(r2, r4)     // Catch:{ JSONException -> 0x01da }
            ar.com.santander.rio.mbanking.services.soap.beans.body.TenenciaTitulosBodyResponseBean r14 = (ar.com.santander.rio.mbanking.services.soap.beans.body.TenenciaTitulosBodyResponseBean) r14     // Catch:{ JSONException -> 0x01da }
            r3.setTenenciaTitulosBodyResponseBean(r14)     // Catch:{ JSONException -> 0x01da }
            r3.header = r1     // Catch:{ JSONException -> 0x01da }
            r13.onResponseBean(r3)     // Catch:{ JSONException -> 0x01da }
            goto L_0x01de
        L_0x01da:
            r14 = move-exception
            r13.onUnknowError(r14)
        L_0x01de:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.services.soap.beans.body.TenenciaTitulosRequest.parserResponse(com.indra.httpclient.json.JSONObject):boolean");
    }
}
