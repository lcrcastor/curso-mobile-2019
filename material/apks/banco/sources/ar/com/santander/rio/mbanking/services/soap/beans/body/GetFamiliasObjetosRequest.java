package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import ar.com.santander.rio.mbanking.services.soap.request.BaseRequest;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class GetFamiliasObjetosRequest extends BaseRequest implements IBeanRequestWS {
    private GetFamiliasObjetosRequestBean mgetFamiliasObjetosRequestBean;
    private GetFamiliasObjetosResponseBean mgetFamiliasObjetosResponseBean;

    public int getMethod() {
        return 1;
    }

    public GetFamiliasObjetosRequest(Context context, GetFamiliasObjetosRequestBean getFamiliasObjetosRequestBean, ErrorRequestServer errorRequestServer) {
        super(context);
        this.mgetFamiliasObjetosRequestBean = getFamiliasObjetosRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public GetFamiliasObjetosRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mgetFamiliasObjetosRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mgetFamiliasObjetosResponseBean == null) {
            this.mgetFamiliasObjetosResponseBean = new GetFamiliasObjetosResponseBean();
        }
        return this.mgetFamiliasObjetosResponseBean.getClass();
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
        r5.put((java.lang.Object) r4.getJSONObject("familia"));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        r5 = new com.indra.httpclient.json.JSONArray();
        r5.put((java.lang.Object) r4.getJSONObject("leyenda"));
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0061 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:26:0x0107 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean parserResponse(com.indra.httpclient.json.JSONObject r13) {
        /*
            r12 = this;
            boolean r0 = super.parserResponse(r13)
            if (r0 == 0) goto L_0x0153
            com.google.gson.Gson r1 = new com.google.gson.Gson
            r1.<init>()
            java.lang.String r2 = "soapenv:Envelope"
            com.indra.httpclient.json.JSONObject r2 = r13.getJSONObject(r2)     // Catch:{ JSONException -> 0x014f }
            java.lang.String r3 = "soapenv:Body"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r3)     // Catch:{ JSONException -> 0x014f }
            java.lang.String r3 = "xml"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r3)     // Catch:{ JSONException -> 0x014f }
            ar.com.santander.rio.mbanking.services.soap.beans.body.GetFamiliasObjetosResponseBean r3 = new ar.com.santander.rio.mbanking.services.soap.beans.body.GetFamiliasObjetosResponseBean     // Catch:{ JSONException -> 0x014f }
            r3.<init>()     // Catch:{ JSONException -> 0x014f }
            java.lang.String r4 = "header"
            com.indra.httpclient.json.JSONObject r4 = r2.getJSONObject(r4)     // Catch:{ JSONException -> 0x014f }
            java.lang.String r4 = r4.toString()     // Catch:{ JSONException -> 0x014f }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean> r5 = ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean.class
            java.lang.Object r1 = r1.fromJson(r4, r5)     // Catch:{ JSONException -> 0x014f }
            ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean r1 = (ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean) r1     // Catch:{ JSONException -> 0x014f }
            ar.com.santander.rio.mbanking.services.soap.beans.body.LinkSugerencia r4 = new ar.com.santander.rio.mbanking.services.soap.beans.body.LinkSugerencia     // Catch:{ JSONException -> 0x014f }
            r4.<init>()     // Catch:{ JSONException -> 0x014f }
            ar.com.santander.rio.mbanking.services.soap.beans.body.GetFamiliasObjetosBodyResponseBean r4 = new ar.com.santander.rio.mbanking.services.soap.beans.body.GetFamiliasObjetosBodyResponseBean     // Catch:{ JSONException -> 0x014f }
            r4.<init>()     // Catch:{ JSONException -> 0x014f }
            java.lang.String r4 = "body"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r4)     // Catch:{ JSONException -> 0x014f }
            java.lang.String r4 = "listaFamilias"
            boolean r4 = r2.has(r4)     // Catch:{ JSONException -> 0x014f }
            if (r4 == 0) goto L_0x00ea
            java.lang.String r4 = "listaFamilias"
            com.indra.httpclient.json.JSONObject r4 = r2.getJSONObject(r4)     // Catch:{ JSONException -> 0x014f }
            java.lang.String r5 = "familia"
            boolean r5 = r4.has(r5)     // Catch:{ JSONException -> 0x014f }
            if (r5 == 0) goto L_0x00ea
            java.lang.String r5 = "familia"
            com.indra.httpclient.json.JSONArray r5 = r4.getJSONArray(r5)     // Catch:{ JSONException -> 0x0061 }
            goto L_0x006f
        L_0x0061:
            com.indra.httpclient.json.JSONArray r5 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x014f }
            r5.<init>()     // Catch:{ JSONException -> 0x014f }
            java.lang.String r6 = "familia"
            com.indra.httpclient.json.JSONObject r6 = r4.getJSONObject(r6)     // Catch:{ JSONException -> 0x014f }
            r5.put(r6)     // Catch:{ JSONException -> 0x014f }
        L_0x006f:
            com.indra.httpclient.json.JSONArray r6 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x014f }
            r6.<init>()     // Catch:{ JSONException -> 0x014f }
            r7 = 0
        L_0x0075:
            int r8 = r5.length()     // Catch:{ JSONException -> 0x014f }
            if (r7 >= r8) goto L_0x00ca
            com.indra.httpclient.json.JSONObject r8 = new com.indra.httpclient.json.JSONObject     // Catch:{ JSONException -> 0x014f }
            r8.<init>()     // Catch:{ JSONException -> 0x014f }
            java.lang.String r9 = "idFamilia"
            java.lang.Object r10 = r5.get(r7)     // Catch:{ JSONException -> 0x014f }
            com.indra.httpclient.json.JSONObject r10 = (com.indra.httpclient.json.JSONObject) r10     // Catch:{ JSONException -> 0x014f }
            java.lang.String r11 = "idFamilia"
            java.lang.String r10 = r10.getString(r11)     // Catch:{ JSONException -> 0x014f }
            r8.put(r9, r10)     // Catch:{ JSONException -> 0x014f }
            java.lang.String r9 = "nombreFamilia"
            java.lang.Object r10 = r5.get(r7)     // Catch:{ JSONException -> 0x014f }
            com.indra.httpclient.json.JSONObject r10 = (com.indra.httpclient.json.JSONObject) r10     // Catch:{ JSONException -> 0x014f }
            java.lang.String r11 = "nombreFamilia"
            java.lang.String r10 = r10.getString(r11)     // Catch:{ JSONException -> 0x014f }
            r8.put(r9, r10)     // Catch:{ JSONException -> 0x014f }
            java.lang.String r9 = "imagenLista"
            java.lang.Object r10 = r5.get(r7)     // Catch:{ JSONException -> 0x014f }
            com.indra.httpclient.json.JSONObject r10 = (com.indra.httpclient.json.JSONObject) r10     // Catch:{ JSONException -> 0x014f }
            java.lang.String r11 = "imagenLista"
            java.lang.String r10 = r10.getString(r11)     // Catch:{ JSONException -> 0x014f }
            r8.put(r9, r10)     // Catch:{ JSONException -> 0x014f }
            java.lang.String r9 = "imagenDetalle"
            java.lang.Object r10 = r5.get(r7)     // Catch:{ JSONException -> 0x014f }
            com.indra.httpclient.json.JSONObject r10 = (com.indra.httpclient.json.JSONObject) r10     // Catch:{ JSONException -> 0x014f }
            java.lang.String r11 = "imagenDetalle"
            java.lang.String r10 = r10.getString(r11)     // Catch:{ JSONException -> 0x014f }
            r8.put(r9, r10)     // Catch:{ JSONException -> 0x014f }
            r6.put(r8)     // Catch:{ JSONException -> 0x014f }
            int r7 = r7 + 1
            goto L_0x0075
        L_0x00ca:
            if (r4 == 0) goto L_0x00ea
            java.lang.String r5 = "familia"
            r4.remove(r5)     // Catch:{ JSONException -> 0x014f }
            java.lang.String r5 = "familia"
            r4.put(r5, r6)     // Catch:{ JSONException -> 0x014f }
            java.lang.String r5 = "listaFamilias"
            r2.remove(r5)     // Catch:{ JSONException -> 0x014f }
            java.lang.String r5 = "listaFamilias"
            r2.put(r5, r4)     // Catch:{ JSONException -> 0x014f }
            java.lang.String r4 = "body"
            r13.remove(r4)     // Catch:{ JSONException -> 0x014f }
            java.lang.String r4 = "body"
            r13.put(r4, r2)     // Catch:{ JSONException -> 0x014f }
        L_0x00ea:
            java.lang.String r4 = "listaLeyendas"
            boolean r4 = r2.has(r4)     // Catch:{ JSONException -> 0x014f }
            if (r4 == 0) goto L_0x0135
            java.lang.String r4 = "listaLeyendas"
            com.indra.httpclient.json.JSONObject r4 = r2.getJSONObject(r4)     // Catch:{ JSONException -> 0x014f }
            java.lang.String r5 = "leyenda"
            boolean r5 = r4.has(r5)     // Catch:{ JSONException -> 0x014f }
            if (r5 == 0) goto L_0x0135
            java.lang.String r5 = "leyenda"
            com.indra.httpclient.json.JSONArray r5 = r4.getJSONArray(r5)     // Catch:{ JSONException -> 0x0107 }
            goto L_0x0115
        L_0x0107:
            com.indra.httpclient.json.JSONArray r5 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x014f }
            r5.<init>()     // Catch:{ JSONException -> 0x014f }
            java.lang.String r6 = "leyenda"
            com.indra.httpclient.json.JSONObject r6 = r4.getJSONObject(r6)     // Catch:{ JSONException -> 0x014f }
            r5.put(r6)     // Catch:{ JSONException -> 0x014f }
        L_0x0115:
            if (r4 == 0) goto L_0x0135
            java.lang.String r6 = "leyenda"
            r4.remove(r6)     // Catch:{ JSONException -> 0x014f }
            java.lang.String r6 = "leyenda"
            r4.put(r6, r5)     // Catch:{ JSONException -> 0x014f }
            java.lang.String r5 = "listaLeyendas"
            r2.remove(r5)     // Catch:{ JSONException -> 0x014f }
            java.lang.String r5 = "listaLeyendas"
            r2.put(r5, r4)     // Catch:{ JSONException -> 0x014f }
            java.lang.String r4 = "body"
            r13.remove(r4)     // Catch:{ JSONException -> 0x014f }
            java.lang.String r4 = "body"
            r13.put(r4, r2)     // Catch:{ JSONException -> 0x014f }
        L_0x0135:
            com.google.gson.Gson r13 = new com.google.gson.Gson     // Catch:{ JSONException -> 0x014f }
            r13.<init>()     // Catch:{ JSONException -> 0x014f }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ JSONException -> 0x014f }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.body.GetFamiliasObjetosBodyResponseBean> r4 = ar.com.santander.rio.mbanking.services.soap.beans.body.GetFamiliasObjetosBodyResponseBean.class
            java.lang.Object r13 = r13.fromJson(r2, r4)     // Catch:{ JSONException -> 0x014f }
            ar.com.santander.rio.mbanking.services.soap.beans.body.GetFamiliasObjetosBodyResponseBean r13 = (ar.com.santander.rio.mbanking.services.soap.beans.body.GetFamiliasObjetosBodyResponseBean) r13     // Catch:{ JSONException -> 0x014f }
            r3.setGetFamiliasObjetosResponseBean(r13)     // Catch:{ JSONException -> 0x014f }
            r3.header = r1     // Catch:{ JSONException -> 0x014f }
            r12.onResponseBean(r3)     // Catch:{ JSONException -> 0x014f }
            goto L_0x0153
        L_0x014f:
            r13 = move-exception
            r12.onUnknowError(r13)
        L_0x0153:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.services.soap.beans.body.GetFamiliasObjetosRequest.parserResponse(com.indra.httpclient.json.JSONObject):boolean");
    }
}
