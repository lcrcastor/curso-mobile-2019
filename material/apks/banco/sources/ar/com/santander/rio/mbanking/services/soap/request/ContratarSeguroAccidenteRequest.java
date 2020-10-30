package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.ContratarSeguroAccidenteRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ContratarSeguroAccidenteResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class ContratarSeguroAccidenteRequest extends BaseRequest implements IBeanRequestWS {
    private ContratarSeguroAccidenteRequestBean mcontratarSeguroAccidenteRequestBean;
    private ContratarSeguroAccidenteResponseBean mcontratarSeguroAccidenteResponseBean;

    public int getMethod() {
        return 1;
    }

    public ContratarSeguroAccidenteRequest(Context context, ContratarSeguroAccidenteRequestBean contratarSeguroAccidenteRequestBean, ErrorRequestServer errorRequestServer) {
        super(context);
        this.mcontratarSeguroAccidenteRequestBean = contratarSeguroAccidenteRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public ContratarSeguroAccidenteRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mcontratarSeguroAccidenteRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mcontratarSeguroAccidenteResponseBean == null) {
            this.mcontratarSeguroAccidenteResponseBean = new ContratarSeguroAccidenteResponseBean();
        }
        return this.mcontratarSeguroAccidenteResponseBean.getClass();
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(2:11|12) */
    /* JADX WARNING: Code restructure failed: missing block: B:12:?, code lost:
        r6 = new com.indra.httpclient.json.JSONArray();
        r6.put((java.lang.Object) r5.getJSONObject("leyenda"));
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0061 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean parserResponse(com.indra.httpclient.json.JSONObject r14) {
        /*
            r13 = this;
            boolean r0 = super.parserResponse(r14)
            if (r0 == 0) goto L_0x00fa
            com.google.gson.Gson r1 = new com.google.gson.Gson
            r1.<init>()
            java.lang.String r2 = "soapenv:Envelope"
            com.indra.httpclient.json.JSONObject r2 = r14.getJSONObject(r2)     // Catch:{ JSONException -> 0x00f6 }
            java.lang.String r3 = "soapenv:Body"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r3)     // Catch:{ JSONException -> 0x00f6 }
            java.lang.String r3 = "xml"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r3)     // Catch:{ JSONException -> 0x00f6 }
            ar.com.santander.rio.mbanking.services.soap.beans.ContratarSeguroAccidenteResponseBean r3 = new ar.com.santander.rio.mbanking.services.soap.beans.ContratarSeguroAccidenteResponseBean     // Catch:{ JSONException -> 0x00f6 }
            r3.<init>()     // Catch:{ JSONException -> 0x00f6 }
            java.lang.String r4 = "header"
            com.indra.httpclient.json.JSONObject r4 = r2.getJSONObject(r4)     // Catch:{ JSONException -> 0x00f6 }
            java.lang.String r4 = r4.toString()     // Catch:{ JSONException -> 0x00f6 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean> r5 = ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean.class
            java.lang.Object r1 = r1.fromJson(r4, r5)     // Catch:{ JSONException -> 0x00f6 }
            ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean r1 = (ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean) r1     // Catch:{ JSONException -> 0x00f6 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarSeguroAccidenteBean r4 = new ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarSeguroAccidenteBean     // Catch:{ JSONException -> 0x00f6 }
            r4.<init>()     // Catch:{ JSONException -> 0x00f6 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarSeguroAccidenteBodyResponseBean r4 = new ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarSeguroAccidenteBodyResponseBean     // Catch:{ JSONException -> 0x00f6 }
            r4.<init>()     // Catch:{ JSONException -> 0x00f6 }
            java.lang.String r5 = "body"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r5)     // Catch:{ JSONException -> 0x00f6 }
            java.lang.String r5 = "listaLeyendas"
            boolean r5 = r2.has(r5)     // Catch:{ JSONException -> 0x00f6 }
            if (r5 == 0) goto L_0x00d9
            java.lang.String r5 = "listaLeyendas"
            com.indra.httpclient.json.JSONObject r5 = r2.getJSONObject(r5)     // Catch:{ JSONException -> 0x00f6 }
            java.lang.String r6 = "leyenda"
            boolean r6 = r5.has(r6)     // Catch:{ JSONException -> 0x00f6 }
            if (r6 == 0) goto L_0x00d9
            java.lang.String r6 = "leyenda"
            com.indra.httpclient.json.JSONArray r6 = r5.getJSONArray(r6)     // Catch:{ JSONException -> 0x0061 }
            goto L_0x006f
        L_0x0061:
            com.indra.httpclient.json.JSONArray r6 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x00f6 }
            r6.<init>()     // Catch:{ JSONException -> 0x00f6 }
            java.lang.String r7 = "leyenda"
            com.indra.httpclient.json.JSONObject r7 = r5.getJSONObject(r7)     // Catch:{ JSONException -> 0x00f6 }
            r6.put(r7)     // Catch:{ JSONException -> 0x00f6 }
        L_0x006f:
            com.indra.httpclient.json.JSONArray r7 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x00f6 }
            r7.<init>()     // Catch:{ JSONException -> 0x00f6 }
            r8 = 0
        L_0x0075:
            int r9 = r6.length()     // Catch:{ JSONException -> 0x00f6 }
            if (r8 >= r9) goto L_0x00b9
            com.indra.httpclient.json.JSONObject r9 = new com.indra.httpclient.json.JSONObject     // Catch:{ JSONException -> 0x00f6 }
            r9.<init>()     // Catch:{ JSONException -> 0x00f6 }
            java.lang.String r10 = "descripcion"
            java.lang.Object r11 = r6.get(r8)     // Catch:{ JSONException -> 0x00f6 }
            com.indra.httpclient.json.JSONObject r11 = (com.indra.httpclient.json.JSONObject) r11     // Catch:{ JSONException -> 0x00f6 }
            java.lang.String r12 = "descripcion"
            java.lang.String r11 = r11.getString(r12)     // Catch:{ JSONException -> 0x00f6 }
            r9.put(r10, r11)     // Catch:{ JSONException -> 0x00f6 }
            java.lang.String r10 = "idLeyenda"
            java.lang.Object r11 = r6.get(r8)     // Catch:{ JSONException -> 0x00f6 }
            com.indra.httpclient.json.JSONObject r11 = (com.indra.httpclient.json.JSONObject) r11     // Catch:{ JSONException -> 0x00f6 }
            java.lang.String r12 = "idLeyenda"
            java.lang.String r11 = r11.getString(r12)     // Catch:{ JSONException -> 0x00f6 }
            r9.put(r10, r11)     // Catch:{ JSONException -> 0x00f6 }
            java.lang.String r10 = "titulo"
            java.lang.Object r11 = r6.get(r8)     // Catch:{ JSONException -> 0x00f6 }
            com.indra.httpclient.json.JSONObject r11 = (com.indra.httpclient.json.JSONObject) r11     // Catch:{ JSONException -> 0x00f6 }
            java.lang.String r12 = "titulo"
            java.lang.String r11 = r11.getString(r12)     // Catch:{ JSONException -> 0x00f6 }
            r9.put(r10, r11)     // Catch:{ JSONException -> 0x00f6 }
            r7.put(r9)     // Catch:{ JSONException -> 0x00f6 }
            int r8 = r8 + 1
            goto L_0x0075
        L_0x00b9:
            if (r5 == 0) goto L_0x00d9
            java.lang.String r6 = "leyenda"
            r5.remove(r6)     // Catch:{ JSONException -> 0x00f6 }
            java.lang.String r6 = "leyenda"
            r5.put(r6, r7)     // Catch:{ JSONException -> 0x00f6 }
            java.lang.String r6 = "listaLeyendas"
            r2.remove(r6)     // Catch:{ JSONException -> 0x00f6 }
            java.lang.String r6 = "listaLeyendas"
            r2.put(r6, r5)     // Catch:{ JSONException -> 0x00f6 }
            java.lang.String r5 = "body"
            r14.remove(r5)     // Catch:{ JSONException -> 0x00f6 }
            java.lang.String r5 = "body"
            r14.put(r5, r2)     // Catch:{ JSONException -> 0x00f6 }
        L_0x00d9:
            com.google.gson.Gson r14 = new com.google.gson.Gson     // Catch:{ JSONException -> 0x00f6 }
            r14.<init>()     // Catch:{ JSONException -> 0x00f6 }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ JSONException -> 0x00f6 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarSeguroAccidenteBean> r5 = ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarSeguroAccidenteBean.class
            java.lang.Object r14 = r14.fromJson(r2, r5)     // Catch:{ JSONException -> 0x00f6 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarSeguroAccidenteBean r14 = (ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarSeguroAccidenteBean) r14     // Catch:{ JSONException -> 0x00f6 }
            r4.setContratarSeguroAccidenteBean(r14)     // Catch:{ JSONException -> 0x00f6 }
            r3.setContratarSeguroAccidenteBean(r4)     // Catch:{ JSONException -> 0x00f6 }
            r3.header = r1     // Catch:{ JSONException -> 0x00f6 }
            r13.onResponseBean(r3)     // Catch:{ JSONException -> 0x00f6 }
            goto L_0x00fa
        L_0x00f6:
            r14 = move-exception
            r13.onUnknowError(r14)
        L_0x00fa:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.services.soap.request.ContratarSeguroAccidenteRequest.parserResponse(com.indra.httpclient.json.JSONObject):boolean");
    }
}
