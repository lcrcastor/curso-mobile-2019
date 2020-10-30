package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import ar.com.santander.rio.mbanking.services.soap.request.BaseRequest;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class GetCotizacionSeguroAccidenteRequest extends BaseRequest implements IBeanRequestWS {
    private GetCotizacionSeguroAccidenteRequestBean mgetCotizacionSeguroAccidenteRequestBean;
    private GetCotizacionSeguroAccidenteResponseBean mgetCotizacionSeguroAccidenteResponseBean;

    public int getMethod() {
        return 1;
    }

    public GetCotizacionSeguroAccidenteRequest(Context context, GetCotizacionSeguroAccidenteRequestBean getCotizacionSeguroAccidenteRequestBean, ErrorRequestServer errorRequestServer) {
        super(context);
        this.mgetCotizacionSeguroAccidenteRequestBean = getCotizacionSeguroAccidenteRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public GetCotizacionSeguroAccidenteRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mgetCotizacionSeguroAccidenteRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mgetCotizacionSeguroAccidenteResponseBean == null) {
            this.mgetCotizacionSeguroAccidenteResponseBean = new GetCotizacionSeguroAccidenteResponseBean();
        }
        return this.mgetCotizacionSeguroAccidenteResponseBean.getClass();
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
    /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0061 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean parserResponse(com.indra.httpclient.json.JSONObject r13) {
        /*
            r12 = this;
            boolean r0 = super.parserResponse(r13)
            if (r0 == 0) goto L_0x010b
            com.google.gson.Gson r1 = new com.google.gson.Gson
            r1.<init>()
            java.lang.String r2 = "soapenv:Envelope"
            com.indra.httpclient.json.JSONObject r2 = r13.getJSONObject(r2)     // Catch:{ JSONException -> 0x0107 }
            java.lang.String r3 = "soapenv:Body"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r3)     // Catch:{ JSONException -> 0x0107 }
            java.lang.String r3 = "xml"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r3)     // Catch:{ JSONException -> 0x0107 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroAccidenteResponseBean r3 = new ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroAccidenteResponseBean     // Catch:{ JSONException -> 0x0107 }
            r3.<init>()     // Catch:{ JSONException -> 0x0107 }
            java.lang.String r4 = "header"
            com.indra.httpclient.json.JSONObject r4 = r2.getJSONObject(r4)     // Catch:{ JSONException -> 0x0107 }
            java.lang.String r4 = r4.toString()     // Catch:{ JSONException -> 0x0107 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean> r5 = ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean.class
            java.lang.Object r1 = r1.fromJson(r4, r5)     // Catch:{ JSONException -> 0x0107 }
            ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean r1 = (ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean) r1     // Catch:{ JSONException -> 0x0107 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.CotizacionSeguroAccidenteBean r4 = new ar.com.santander.rio.mbanking.services.soap.beans.body.CotizacionSeguroAccidenteBean     // Catch:{ JSONException -> 0x0107 }
            r4.<init>()     // Catch:{ JSONException -> 0x0107 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroAccidenteBodyResponseBean r4 = new ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroAccidenteBodyResponseBean     // Catch:{ JSONException -> 0x0107 }
            r4.<init>()     // Catch:{ JSONException -> 0x0107 }
            java.lang.String r4 = "body"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r4)     // Catch:{ JSONException -> 0x0107 }
            java.lang.String r4 = "listaLeyendas"
            boolean r4 = r2.has(r4)     // Catch:{ JSONException -> 0x0107 }
            if (r4 == 0) goto L_0x00d9
            java.lang.String r4 = "listaLeyendas"
            com.indra.httpclient.json.JSONObject r4 = r2.getJSONObject(r4)     // Catch:{ JSONException -> 0x0107 }
            java.lang.String r5 = "leyenda"
            boolean r5 = r4.has(r5)     // Catch:{ JSONException -> 0x0107 }
            if (r5 == 0) goto L_0x00d9
            java.lang.String r5 = "leyenda"
            com.indra.httpclient.json.JSONArray r5 = r4.getJSONArray(r5)     // Catch:{ JSONException -> 0x0061 }
            goto L_0x006f
        L_0x0061:
            com.indra.httpclient.json.JSONArray r5 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x0107 }
            r5.<init>()     // Catch:{ JSONException -> 0x0107 }
            java.lang.String r6 = "leyenda"
            com.indra.httpclient.json.JSONObject r6 = r4.getJSONObject(r6)     // Catch:{ JSONException -> 0x0107 }
            r5.put(r6)     // Catch:{ JSONException -> 0x0107 }
        L_0x006f:
            com.indra.httpclient.json.JSONArray r6 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x0107 }
            r6.<init>()     // Catch:{ JSONException -> 0x0107 }
            r7 = 0
        L_0x0075:
            int r8 = r5.length()     // Catch:{ JSONException -> 0x0107 }
            if (r7 >= r8) goto L_0x00b9
            com.indra.httpclient.json.JSONObject r8 = new com.indra.httpclient.json.JSONObject     // Catch:{ JSONException -> 0x0107 }
            r8.<init>()     // Catch:{ JSONException -> 0x0107 }
            java.lang.String r9 = "descripcion"
            java.lang.Object r10 = r5.get(r7)     // Catch:{ JSONException -> 0x0107 }
            com.indra.httpclient.json.JSONObject r10 = (com.indra.httpclient.json.JSONObject) r10     // Catch:{ JSONException -> 0x0107 }
            java.lang.String r11 = "descripcion"
            java.lang.String r10 = r10.getString(r11)     // Catch:{ JSONException -> 0x0107 }
            r8.put(r9, r10)     // Catch:{ JSONException -> 0x0107 }
            java.lang.String r9 = "idLeyenda"
            java.lang.Object r10 = r5.get(r7)     // Catch:{ JSONException -> 0x0107 }
            com.indra.httpclient.json.JSONObject r10 = (com.indra.httpclient.json.JSONObject) r10     // Catch:{ JSONException -> 0x0107 }
            java.lang.String r11 = "idLeyenda"
            java.lang.String r10 = r10.getString(r11)     // Catch:{ JSONException -> 0x0107 }
            r8.put(r9, r10)     // Catch:{ JSONException -> 0x0107 }
            java.lang.String r9 = "titulo"
            java.lang.Object r10 = r5.get(r7)     // Catch:{ JSONException -> 0x0107 }
            com.indra.httpclient.json.JSONObject r10 = (com.indra.httpclient.json.JSONObject) r10     // Catch:{ JSONException -> 0x0107 }
            java.lang.String r11 = "titulo"
            java.lang.String r10 = r10.getString(r11)     // Catch:{ JSONException -> 0x0107 }
            r8.put(r9, r10)     // Catch:{ JSONException -> 0x0107 }
            r6.put(r8)     // Catch:{ JSONException -> 0x0107 }
            int r7 = r7 + 1
            goto L_0x0075
        L_0x00b9:
            if (r4 == 0) goto L_0x00d9
            java.lang.String r5 = "leyenda"
            r4.remove(r5)     // Catch:{ JSONException -> 0x0107 }
            java.lang.String r5 = "leyenda"
            r4.put(r5, r6)     // Catch:{ JSONException -> 0x0107 }
            java.lang.String r5 = "listaLeyendas"
            r2.remove(r5)     // Catch:{ JSONException -> 0x0107 }
            java.lang.String r5 = "listaLeyendas"
            r2.put(r5, r4)     // Catch:{ JSONException -> 0x0107 }
            java.lang.String r4 = "body"
            r13.remove(r4)     // Catch:{ JSONException -> 0x0107 }
            java.lang.String r4 = "body"
            r13.put(r4, r2)     // Catch:{ JSONException -> 0x0107 }
        L_0x00d9:
            com.google.gson.Gson r13 = new com.google.gson.Gson     // Catch:{ JSONException -> 0x0107 }
            r13.<init>()     // Catch:{ JSONException -> 0x0107 }
            java.lang.String r4 = java.lang.String.valueOf(r2)     // Catch:{ JSONException -> 0x0107 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.body.CotizacionSeguroAccidenteBean> r5 = ar.com.santander.rio.mbanking.services.soap.beans.body.CotizacionSeguroAccidenteBean.class
            java.lang.Object r13 = r13.fromJson(r4, r5)     // Catch:{ JSONException -> 0x0107 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.CotizacionSeguroAccidenteBean r13 = (ar.com.santander.rio.mbanking.services.soap.beans.body.CotizacionSeguroAccidenteBean) r13     // Catch:{ JSONException -> 0x0107 }
            com.google.gson.Gson r4 = new com.google.gson.Gson     // Catch:{ JSONException -> 0x0107 }
            r4.<init>()     // Catch:{ JSONException -> 0x0107 }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ JSONException -> 0x0107 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroAccidenteBodyResponseBean> r5 = ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroAccidenteBodyResponseBean.class
            java.lang.Object r2 = r4.fromJson(r2, r5)     // Catch:{ JSONException -> 0x0107 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroAccidenteBodyResponseBean r2 = (ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroAccidenteBodyResponseBean) r2     // Catch:{ JSONException -> 0x0107 }
            r2.setCotizacion(r13)     // Catch:{ JSONException -> 0x0107 }
            r3.setGetSegurosBodyResponseBean(r2)     // Catch:{ JSONException -> 0x0107 }
            r3.header = r1     // Catch:{ JSONException -> 0x0107 }
            r12.onResponseBean(r3)     // Catch:{ JSONException -> 0x0107 }
            goto L_0x010b
        L_0x0107:
            r13 = move-exception
            r12.onUnknowError(r13)
        L_0x010b:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroAccidenteRequest.parserResponse(com.indra.httpclient.json.JSONObject):boolean");
    }
}
