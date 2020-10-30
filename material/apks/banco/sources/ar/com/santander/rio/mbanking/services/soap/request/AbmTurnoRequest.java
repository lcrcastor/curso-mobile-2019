package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.AbmTurnoRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.AbmTurnoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class AbmTurnoRequest extends BaseRequest implements IBeanRequestWS {
    private AbmTurnoRequestBean mabmTurnoRequestBean;
    private AbmTurnoResponseBean mabmTurnoResponseBean;

    public int getMethod() {
        return 1;
    }

    public AbmTurnoRequest(Context context, AbmTurnoRequestBean abmTurnoRequestBean, ErrorRequestServer errorRequestServer) {
        super(context);
        this.mabmTurnoRequestBean = abmTurnoRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public AbmTurnoRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mabmTurnoRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mabmTurnoResponseBean == null) {
            this.mabmTurnoResponseBean = new AbmTurnoResponseBean();
        }
        return this.mabmTurnoResponseBean.getClass();
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
    /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x005c */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean parserResponse(com.indra.httpclient.json.JSONObject r8) {
        /*
            r7 = this;
            boolean r0 = super.parserResponse(r8)
            if (r0 == 0) goto L_0x00a8
            com.google.gson.Gson r1 = new com.google.gson.Gson
            r1.<init>()
            java.lang.String r2 = "soapenv:Envelope"
            com.indra.httpclient.json.JSONObject r2 = r8.getJSONObject(r2)     // Catch:{ JSONException -> 0x00a4 }
            java.lang.String r3 = "soapenv:Body"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r3)     // Catch:{ JSONException -> 0x00a4 }
            java.lang.String r3 = "xml"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r3)     // Catch:{ JSONException -> 0x00a4 }
            ar.com.santander.rio.mbanking.services.soap.beans.AbmTurnoResponseBean r3 = new ar.com.santander.rio.mbanking.services.soap.beans.AbmTurnoResponseBean     // Catch:{ JSONException -> 0x00a4 }
            r3.<init>()     // Catch:{ JSONException -> 0x00a4 }
            java.lang.String r4 = "header"
            com.indra.httpclient.json.JSONObject r4 = r2.getJSONObject(r4)     // Catch:{ JSONException -> 0x00a4 }
            java.lang.String r4 = r4.toString()     // Catch:{ JSONException -> 0x00a4 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean> r5 = ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean.class
            java.lang.Object r1 = r1.fromJson(r4, r5)     // Catch:{ JSONException -> 0x00a4 }
            ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean r1 = (ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean) r1     // Catch:{ JSONException -> 0x00a4 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.AbmTurnoBodyResponseBean r4 = new ar.com.santander.rio.mbanking.services.soap.beans.body.AbmTurnoBodyResponseBean     // Catch:{ JSONException -> 0x00a4 }
            r4.<init>()     // Catch:{ JSONException -> 0x00a4 }
            java.lang.String r4 = "body"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r4)     // Catch:{ JSONException -> 0x00a4 }
            java.lang.String r4 = "listaLeyendas"
            boolean r4 = r2.has(r4)     // Catch:{ JSONException -> 0x00a4 }
            if (r4 == 0) goto L_0x008a
            java.lang.String r4 = "listaLeyendas"
            com.indra.httpclient.json.JSONObject r4 = r2.getJSONObject(r4)     // Catch:{ JSONException -> 0x00a4 }
            java.lang.String r5 = "leyenda"
            boolean r5 = r4.has(r5)     // Catch:{ JSONException -> 0x00a4 }
            if (r5 == 0) goto L_0x008a
            java.lang.String r5 = "leyenda"
            com.indra.httpclient.json.JSONArray r5 = r4.getJSONArray(r5)     // Catch:{ JSONException -> 0x005c }
            goto L_0x006a
        L_0x005c:
            com.indra.httpclient.json.JSONArray r5 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x00a4 }
            r5.<init>()     // Catch:{ JSONException -> 0x00a4 }
            java.lang.String r6 = "leyenda"
            com.indra.httpclient.json.JSONObject r6 = r4.getJSONObject(r6)     // Catch:{ JSONException -> 0x00a4 }
            r5.put(r6)     // Catch:{ JSONException -> 0x00a4 }
        L_0x006a:
            if (r4 == 0) goto L_0x008a
            java.lang.String r6 = "leyenda"
            r4.remove(r6)     // Catch:{ JSONException -> 0x00a4 }
            java.lang.String r6 = "leyenda"
            r4.put(r6, r5)     // Catch:{ JSONException -> 0x00a4 }
            java.lang.String r5 = "listaLeyendas"
            r2.remove(r5)     // Catch:{ JSONException -> 0x00a4 }
            java.lang.String r5 = "listaLeyendas"
            r2.put(r5, r4)     // Catch:{ JSONException -> 0x00a4 }
            java.lang.String r4 = "leyenda"
            r8.remove(r4)     // Catch:{ JSONException -> 0x00a4 }
            java.lang.String r4 = "body"
            r8.put(r4, r2)     // Catch:{ JSONException -> 0x00a4 }
        L_0x008a:
            com.google.gson.Gson r8 = new com.google.gson.Gson     // Catch:{ JSONException -> 0x00a4 }
            r8.<init>()     // Catch:{ JSONException -> 0x00a4 }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ JSONException -> 0x00a4 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.body.AbmTurnoBodyResponseBean> r4 = ar.com.santander.rio.mbanking.services.soap.beans.body.AbmTurnoBodyResponseBean.class
            java.lang.Object r8 = r8.fromJson(r2, r4)     // Catch:{ JSONException -> 0x00a4 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.AbmTurnoBodyResponseBean r8 = (ar.com.santander.rio.mbanking.services.soap.beans.body.AbmTurnoBodyResponseBean) r8     // Catch:{ JSONException -> 0x00a4 }
            r3.setAbmTurnoBodyResponseBean(r8)     // Catch:{ JSONException -> 0x00a4 }
            r3.header = r1     // Catch:{ JSONException -> 0x00a4 }
            r7.onResponseBean(r3)     // Catch:{ JSONException -> 0x00a4 }
            goto L_0x00a8
        L_0x00a4:
            r8 = move-exception
            r7.onUnknowError(r8)
        L_0x00a8:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.services.soap.request.AbmTurnoRequest.parserResponse(com.indra.httpclient.json.JSONObject):boolean");
    }
}
