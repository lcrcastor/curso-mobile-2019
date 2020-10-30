package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import ar.com.santander.rio.mbanking.services.soap.request.PrivateBaseRequest;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public class GetCotizacionSeguroObjetoRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private GetCotizacionSeguroObjetoRequestBean mgetCotizacionSeguroObjetoRequestBean;
    private GetCotizacionSeguroObjetoResponseBean mgetCotizacionSeguroObjetoResponseBean;

    public int getMethod() {
        return 1;
    }

    public void onResponseBean(IBeanWS iBeanWS) {
    }

    public void onUnknowError(Exception exc) {
    }

    public GetCotizacionSeguroObjetoRequest(Context context, GetCotizacionSeguroObjetoRequestBean getCotizacionSeguroObjetoRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, false);
        this.mgetCotizacionSeguroObjetoRequestBean = getCotizacionSeguroObjetoRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public GetCotizacionSeguroObjetoRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mgetCotizacionSeguroObjetoRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mgetCotizacionSeguroObjetoResponseBean == null) {
            this.mgetCotizacionSeguroObjetoResponseBean = new GetCotizacionSeguroObjetoResponseBean();
        }
        return this.mgetCotizacionSeguroObjetoResponseBean.getClass();
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(2:15|16) */
    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        r6 = new com.indra.httpclient.json.JSONArray();
        r6.put((java.lang.Object) r5.getJSONObject("plan"));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        r9 = new com.indra.httpclient.json.JSONArray();
        r9.put((java.lang.Object) r8.getJSONObject("cobertura"));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:?, code lost:
        r6 = new com.indra.httpclient.json.JSONArray();
        r6.put((java.lang.Object) r5.getJSONObject("leyenda"));
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x006e */
    /* JADX WARNING: Missing exception handler attribute for start block: B:27:0x00a6 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:43:0x00e9 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean parserResponse(com.indra.httpclient.json.JSONObject r12) {
        /*
            r11 = this;
            boolean r0 = super.parserResponse(r12)
            if (r0 == 0) goto L_0x013d
            com.google.gson.Gson r1 = new com.google.gson.Gson
            r1.<init>()
            java.lang.String r2 = "soapenv:Envelope"
            com.indra.httpclient.json.JSONObject r2 = r12.getJSONObject(r2)     // Catch:{ JSONException -> 0x0139 }
            java.lang.String r3 = "soapenv:Body"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r3)     // Catch:{ JSONException -> 0x0139 }
            java.lang.String r3 = "xml"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r3)     // Catch:{ JSONException -> 0x0139 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroObjetoResponseBean r3 = new ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroObjetoResponseBean     // Catch:{ JSONException -> 0x0139 }
            r3.<init>()     // Catch:{ JSONException -> 0x0139 }
            java.lang.String r4 = "header"
            com.indra.httpclient.json.JSONObject r4 = r2.getJSONObject(r4)     // Catch:{ JSONException -> 0x0139 }
            java.lang.String r4 = r4.toString()     // Catch:{ JSONException -> 0x0139 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean> r5 = ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean.class
            java.lang.Object r1 = r1.fromJson(r4, r5)     // Catch:{ JSONException -> 0x0139 }
            ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean r1 = (ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean) r1     // Catch:{ JSONException -> 0x0139 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroObjetoBodyResponseBean r4 = new ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroObjetoBodyResponseBean     // Catch:{ JSONException -> 0x0139 }
            r4.<init>()     // Catch:{ JSONException -> 0x0139 }
            java.lang.String r4 = "body"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r4)     // Catch:{ JSONException -> 0x0139 }
            java.lang.String r4 = "cotizacion"
            boolean r4 = r2.has(r4)     // Catch:{ JSONException -> 0x0139 }
            if (r4 == 0) goto L_0x011f
            java.lang.String r4 = "cotizacion"
            com.indra.httpclient.json.JSONObject r4 = r2.getJSONObject(r4)     // Catch:{ JSONException -> 0x0139 }
            if (r4 == 0) goto L_0x00c6
            java.lang.String r5 = "listaPlanes"
            boolean r5 = r4.has(r5)     // Catch:{ JSONException -> 0x0139 }
            if (r5 == 0) goto L_0x00c6
            java.lang.String r5 = "listaPlanes"
            java.lang.Object r5 = r4.get(r5)     // Catch:{ JSONException -> 0x0139 }
            com.indra.httpclient.json.JSONObject r5 = (com.indra.httpclient.json.JSONObject) r5     // Catch:{ JSONException -> 0x0139 }
            java.lang.String r6 = "plan"
            boolean r6 = r5.has(r6)     // Catch:{ JSONException -> 0x0139 }
            if (r6 == 0) goto L_0x00c1
            java.lang.String r6 = "plan"
            com.indra.httpclient.json.JSONArray r6 = r5.getJSONArray(r6)     // Catch:{ JSONException -> 0x006e }
            goto L_0x007c
        L_0x006e:
            com.indra.httpclient.json.JSONArray r6 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x0139 }
            r6.<init>()     // Catch:{ JSONException -> 0x0139 }
            java.lang.String r7 = "plan"
            com.indra.httpclient.json.JSONObject r7 = r5.getJSONObject(r7)     // Catch:{ JSONException -> 0x0139 }
            r6.put(r7)     // Catch:{ JSONException -> 0x0139 }
        L_0x007c:
            r7 = 0
        L_0x007d:
            int r8 = r6.length()     // Catch:{ JSONException -> 0x0139 }
            if (r7 >= r8) goto L_0x00bc
            java.lang.Object r8 = r6.get(r7)     // Catch:{ JSONException -> 0x0139 }
            com.indra.httpclient.json.JSONObject r8 = (com.indra.httpclient.json.JSONObject) r8     // Catch:{ JSONException -> 0x0139 }
            java.lang.String r9 = "listaCoberturas"
            boolean r9 = r8.has(r9)     // Catch:{ JSONException -> 0x0139 }
            if (r9 == 0) goto L_0x00b9
            java.lang.String r9 = "listaCoberturas"
            com.indra.httpclient.json.JSONObject r8 = r8.getJSONObject(r9)     // Catch:{ JSONException -> 0x0139 }
            java.lang.String r9 = "cobertura"
            boolean r9 = r8.has(r9)     // Catch:{ JSONException -> 0x0139 }
            if (r9 == 0) goto L_0x00b9
            java.lang.String r9 = "cobertura"
            com.indra.httpclient.json.JSONArray r9 = r8.getJSONArray(r9)     // Catch:{ JSONException -> 0x00a6 }
            goto L_0x00b4
        L_0x00a6:
            com.indra.httpclient.json.JSONArray r9 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x0139 }
            r9.<init>()     // Catch:{ JSONException -> 0x0139 }
            java.lang.String r10 = "cobertura"
            com.indra.httpclient.json.JSONObject r10 = r8.getJSONObject(r10)     // Catch:{ JSONException -> 0x0139 }
            r9.put(r10)     // Catch:{ JSONException -> 0x0139 }
        L_0x00b4:
            java.lang.String r10 = "cobertura"
            r8.put(r10, r9)     // Catch:{ JSONException -> 0x0139 }
        L_0x00b9:
            int r7 = r7 + 1
            goto L_0x007d
        L_0x00bc:
            java.lang.String r7 = "plan"
            r5.put(r7, r6)     // Catch:{ JSONException -> 0x0139 }
        L_0x00c1:
            java.lang.String r6 = "listaPlanes"
            r4.put(r6, r5)     // Catch:{ JSONException -> 0x0139 }
        L_0x00c6:
            if (r4 == 0) goto L_0x010b
            java.lang.String r5 = "listaLeyendas"
            boolean r5 = r4.has(r5)     // Catch:{ JSONException -> 0x0139 }
            if (r5 == 0) goto L_0x010b
            java.lang.String r5 = "listaLeyendas"
            java.lang.Object r5 = r4.get(r5)     // Catch:{ JSONException -> 0x0139 }
            com.indra.httpclient.json.JSONObject r5 = (com.indra.httpclient.json.JSONObject) r5     // Catch:{ JSONException -> 0x0139 }
            if (r5 == 0) goto L_0x0101
            java.lang.String r6 = "leyenda"
            boolean r6 = r5.has(r6)     // Catch:{ JSONException -> 0x0139 }
            if (r6 == 0) goto L_0x0101
            java.lang.String r6 = "leyenda"
            com.indra.httpclient.json.JSONArray r6 = r5.getJSONArray(r6)     // Catch:{ JSONException -> 0x00e9 }
            goto L_0x00f7
        L_0x00e9:
            com.indra.httpclient.json.JSONArray r6 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x0139 }
            r6.<init>()     // Catch:{ JSONException -> 0x0139 }
            java.lang.String r7 = "leyenda"
            com.indra.httpclient.json.JSONObject r7 = r5.getJSONObject(r7)     // Catch:{ JSONException -> 0x0139 }
            r6.put(r7)     // Catch:{ JSONException -> 0x0139 }
        L_0x00f7:
            java.lang.String r7 = "leyenda"
            r5.remove(r7)     // Catch:{ JSONException -> 0x0139 }
            java.lang.String r7 = "leyenda"
            r5.put(r7, r6)     // Catch:{ JSONException -> 0x0139 }
        L_0x0101:
            java.lang.String r6 = "listaLeyendas"
            r4.remove(r6)     // Catch:{ JSONException -> 0x0139 }
            java.lang.String r6 = "listaLeyendas"
            r4.put(r6, r5)     // Catch:{ JSONException -> 0x0139 }
        L_0x010b:
            java.lang.String r5 = "cotizacion"
            r2.remove(r5)     // Catch:{ JSONException -> 0x0139 }
            java.lang.String r5 = "cotizacion"
            r2.put(r5, r4)     // Catch:{ JSONException -> 0x0139 }
            java.lang.String r4 = "body"
            r12.remove(r4)     // Catch:{ JSONException -> 0x0139 }
            java.lang.String r4 = "body"
            r12.put(r4, r2)     // Catch:{ JSONException -> 0x0139 }
        L_0x011f:
            com.google.gson.Gson r12 = new com.google.gson.Gson     // Catch:{ JSONException -> 0x0139 }
            r12.<init>()     // Catch:{ JSONException -> 0x0139 }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ JSONException -> 0x0139 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroObjetoBodyResponseBean> r4 = ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroObjetoBodyResponseBean.class
            java.lang.Object r12 = r12.fromJson(r2, r4)     // Catch:{ JSONException -> 0x0139 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroObjetoBodyResponseBean r12 = (ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroObjetoBodyResponseBean) r12     // Catch:{ JSONException -> 0x0139 }
            r3.setGetCotizacionSeguroObjetoResponseBean(r12)     // Catch:{ JSONException -> 0x0139 }
            r3.header = r1     // Catch:{ JSONException -> 0x0139 }
            r11.onResponseBean(r3)     // Catch:{ JSONException -> 0x0139 }
            goto L_0x013d
        L_0x0139:
            r12 = move-exception
            r11.onUnknowError(r12)
        L_0x013d:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroObjetoRequest.parserResponse(com.indra.httpclient.json.JSONObject):boolean");
    }
}
