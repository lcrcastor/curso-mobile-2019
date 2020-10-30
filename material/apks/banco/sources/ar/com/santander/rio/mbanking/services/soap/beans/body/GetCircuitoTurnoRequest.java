package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import ar.com.santander.rio.mbanking.services.soap.request.BaseRequest;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class GetCircuitoTurnoRequest extends BaseRequest implements IBeanRequestWS {
    private GetCircuitoTurnoRequestBean mgetCircuitoTurnoRequestBean;
    private GetCircuitoTurnoResponseBean mgetCircuitoTurnoResponseBean;

    public int getMethod() {
        return 1;
    }

    public GetCircuitoTurnoRequest(Context context, GetCircuitoTurnoRequestBean getCircuitoTurnoRequestBean, ErrorRequestServer errorRequestServer) {
        super(context);
        this.mgetCircuitoTurnoRequestBean = getCircuitoTurnoRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public GetCircuitoTurnoRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mgetCircuitoTurnoRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mgetCircuitoTurnoResponseBean == null) {
            this.mgetCircuitoTurnoResponseBean = new GetCircuitoTurnoResponseBean();
        }
        return this.mgetCircuitoTurnoResponseBean.getClass();
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
        r5.put((java.lang.Object) r4.getJSONObject("pantalla"));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        r8 = new com.indra.httpclient.json.JSONArray();
        r8.put((java.lang.Object) r7.getJSONObject("opcion"));
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x005c */
    /* JADX WARNING: Missing exception handler attribute for start block: B:26:0x009e */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean parserResponse(com.indra.httpclient.json.JSONObject r11) {
        /*
            r10 = this;
            boolean r0 = super.parserResponse(r11)
            if (r0 == 0) goto L_0x010b
            com.google.gson.Gson r1 = new com.google.gson.Gson
            r1.<init>()
            java.lang.String r2 = "soapenv:Envelope"
            com.indra.httpclient.json.JSONObject r2 = r11.getJSONObject(r2)     // Catch:{ JSONException -> 0x0107 }
            java.lang.String r3 = "soapenv:Body"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r3)     // Catch:{ JSONException -> 0x0107 }
            java.lang.String r3 = "xml"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r3)     // Catch:{ JSONException -> 0x0107 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.GetCircuitoTurnoResponseBean r3 = new ar.com.santander.rio.mbanking.services.soap.beans.body.GetCircuitoTurnoResponseBean     // Catch:{ JSONException -> 0x0107 }
            r3.<init>()     // Catch:{ JSONException -> 0x0107 }
            java.lang.String r4 = "header"
            com.indra.httpclient.json.JSONObject r4 = r2.getJSONObject(r4)     // Catch:{ JSONException -> 0x0107 }
            java.lang.String r4 = r4.toString()     // Catch:{ JSONException -> 0x0107 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean> r5 = ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean.class
            java.lang.Object r1 = r1.fromJson(r4, r5)     // Catch:{ JSONException -> 0x0107 }
            ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean r1 = (ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean) r1     // Catch:{ JSONException -> 0x0107 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.GetCircuitoTurnoBodyResponseBean r4 = new ar.com.santander.rio.mbanking.services.soap.beans.body.GetCircuitoTurnoBodyResponseBean     // Catch:{ JSONException -> 0x0107 }
            r4.<init>()     // Catch:{ JSONException -> 0x0107 }
            java.lang.String r4 = "body"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r4)     // Catch:{ JSONException -> 0x0107 }
            java.lang.String r4 = "listaPantallas"
            boolean r4 = r2.has(r4)     // Catch:{ JSONException -> 0x0107 }
            if (r4 == 0) goto L_0x00ed
            java.lang.String r4 = "listaPantallas"
            com.indra.httpclient.json.JSONObject r4 = r2.getJSONObject(r4)     // Catch:{ JSONException -> 0x0107 }
            java.lang.String r5 = "pantalla"
            boolean r5 = r4.has(r5)     // Catch:{ JSONException -> 0x0107 }
            if (r5 == 0) goto L_0x00ed
            java.lang.String r5 = "pantalla"
            com.indra.httpclient.json.JSONArray r5 = r4.getJSONArray(r5)     // Catch:{ JSONException -> 0x005c }
            goto L_0x006a
        L_0x005c:
            com.indra.httpclient.json.JSONArray r5 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x0107 }
            r5.<init>()     // Catch:{ JSONException -> 0x0107 }
            java.lang.String r6 = "pantalla"
            com.indra.httpclient.json.JSONObject r6 = r4.getJSONObject(r6)     // Catch:{ JSONException -> 0x0107 }
            r5.put(r6)     // Catch:{ JSONException -> 0x0107 }
        L_0x006a:
            r6 = 0
        L_0x006b:
            int r7 = r5.length()     // Catch:{ JSONException -> 0x0107 }
            if (r6 >= r7) goto L_0x00cf
            java.lang.Object r7 = r5.get(r6)     // Catch:{ JSONException -> 0x0107 }
            com.indra.httpclient.json.JSONObject r7 = (com.indra.httpclient.json.JSONObject) r7     // Catch:{ JSONException -> 0x0107 }
            java.lang.String r8 = "listaOpciones"
            boolean r7 = r7.has(r8)     // Catch:{ JSONException -> 0x0107 }
            if (r7 == 0) goto L_0x008c
            java.lang.Object r7 = r5.get(r6)     // Catch:{ JSONException -> 0x0107 }
            com.indra.httpclient.json.JSONObject r7 = (com.indra.httpclient.json.JSONObject) r7     // Catch:{ JSONException -> 0x0107 }
            java.lang.String r8 = "listaOpciones"
            com.indra.httpclient.json.JSONObject r7 = r7.getJSONObject(r8)     // Catch:{ JSONException -> 0x0107 }
            goto L_0x008d
        L_0x008c:
            r7 = 0
        L_0x008d:
            if (r7 == 0) goto L_0x00b6
            java.lang.String r8 = "opcion"
            boolean r8 = r7.has(r8)     // Catch:{ JSONException -> 0x0107 }
            if (r8 == 0) goto L_0x00b6
            java.lang.String r8 = "opcion"
            com.indra.httpclient.json.JSONArray r8 = r7.getJSONArray(r8)     // Catch:{ JSONException -> 0x009e }
            goto L_0x00ac
        L_0x009e:
            com.indra.httpclient.json.JSONArray r8 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x0107 }
            r8.<init>()     // Catch:{ JSONException -> 0x0107 }
            java.lang.String r9 = "opcion"
            com.indra.httpclient.json.JSONObject r9 = r7.getJSONObject(r9)     // Catch:{ JSONException -> 0x0107 }
            r8.put(r9)     // Catch:{ JSONException -> 0x0107 }
        L_0x00ac:
            java.lang.String r9 = "opcion"
            r7.remove(r9)     // Catch:{ JSONException -> 0x0107 }
            java.lang.String r9 = "opcion"
            r7.put(r9, r8)     // Catch:{ JSONException -> 0x0107 }
        L_0x00b6:
            java.lang.Object r8 = r5.get(r6)     // Catch:{ JSONException -> 0x0107 }
            com.indra.httpclient.json.JSONObject r8 = (com.indra.httpclient.json.JSONObject) r8     // Catch:{ JSONException -> 0x0107 }
            java.lang.String r9 = "listaOpciones"
            r8.remove(r9)     // Catch:{ JSONException -> 0x0107 }
            java.lang.Object r8 = r5.get(r6)     // Catch:{ JSONException -> 0x0107 }
            com.indra.httpclient.json.JSONObject r8 = (com.indra.httpclient.json.JSONObject) r8     // Catch:{ JSONException -> 0x0107 }
            java.lang.String r9 = "listaOpciones"
            r8.put(r9, r7)     // Catch:{ JSONException -> 0x0107 }
            int r6 = r6 + 1
            goto L_0x006b
        L_0x00cf:
            java.lang.String r6 = "pantalla"
            r4.remove(r6)     // Catch:{ JSONException -> 0x0107 }
            java.lang.String r6 = "pantalla"
            r4.put(r6, r5)     // Catch:{ JSONException -> 0x0107 }
            java.lang.String r5 = "listaPantallas"
            r2.remove(r5)     // Catch:{ JSONException -> 0x0107 }
            java.lang.String r5 = "listaPantallas"
            r2.put(r5, r4)     // Catch:{ JSONException -> 0x0107 }
            java.lang.String r4 = "body"
            r11.remove(r4)     // Catch:{ JSONException -> 0x0107 }
            java.lang.String r4 = "body"
            r11.put(r4, r2)     // Catch:{ JSONException -> 0x0107 }
        L_0x00ed:
            com.google.gson.Gson r11 = new com.google.gson.Gson     // Catch:{ JSONException -> 0x0107 }
            r11.<init>()     // Catch:{ JSONException -> 0x0107 }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ JSONException -> 0x0107 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.body.GetCircuitoTurnoBodyResponseBean> r4 = ar.com.santander.rio.mbanking.services.soap.beans.body.GetCircuitoTurnoBodyResponseBean.class
            java.lang.Object r11 = r11.fromJson(r2, r4)     // Catch:{ JSONException -> 0x0107 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.GetCircuitoTurnoBodyResponseBean r11 = (ar.com.santander.rio.mbanking.services.soap.beans.body.GetCircuitoTurnoBodyResponseBean) r11     // Catch:{ JSONException -> 0x0107 }
            r3.setGetSegurosBodyResponseBean(r11)     // Catch:{ JSONException -> 0x0107 }
            r3.header = r1     // Catch:{ JSONException -> 0x0107 }
            r10.onResponseBean(r3)     // Catch:{ JSONException -> 0x0107 }
            goto L_0x010b
        L_0x0107:
            r11 = move-exception
            r10.onUnknowError(r11)
        L_0x010b:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.services.soap.beans.body.GetCircuitoTurnoRequest.parserResponse(com.indra.httpclient.json.JSONObject):boolean");
    }
}
