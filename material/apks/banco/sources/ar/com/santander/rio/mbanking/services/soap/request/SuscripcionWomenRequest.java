package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import android.util.Log;
import ar.com.santander.rio.mbanking.services.soap.beans.SuscripcionWomenRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.SuscripcionWomenResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public class SuscripcionWomenRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private SuscripcionWomenRequestBean suscripcionWomenRequestBean;
    private SuscripcionWomenResponseBean suscripcionWomenResponseBean;

    public int getMethod() {
        return 1;
    }

    public void onResponseBean(IBeanWS iBeanWS) {
    }

    public SuscripcionWomenRequest(Context context, SuscripcionWomenRequestBean suscripcionWomenRequestBean2, ErrorRequestServer errorRequestServer) {
        super(context, false);
        this.suscripcionWomenRequestBean = suscripcionWomenRequestBean2;
        this.mErrorRequestServer = errorRequestServer;
        this.suscripcionWomenResponseBean = new SuscripcionWomenResponseBean();
    }

    public SuscripcionWomenRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void onUnknowError(Exception exc) {
        StringBuilder sb = new StringBuilder();
        sb.append("onUnknowError: ");
        sb.append(exc.getLocalizedMessage());
        Log.d("error ", sb.toString());
    }

    public IBeanWS getBeanToRequest() {
        return this.suscripcionWomenRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.suscripcionWomenResponseBean == null) {
            this.suscripcionWomenResponseBean = new SuscripcionWomenResponseBean();
        }
        return this.suscripcionWomenResponseBean.getClass();
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(2:19|20) */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r6 = new com.indra.httpclient.json.JSONArray();
        r6.put((java.lang.Object) r10.getJSONObject("listaLeyendas").getJSONObject("leyenda"));
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x00a1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean parserResponse(com.indra.httpclient.json.JSONObject r10) {
        /*
            r9 = this;
            boolean r0 = super.parserResponse(r10)
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
            ar.com.santander.rio.mbanking.services.soap.beans.body.ListaLeyendas r1 = new ar.com.santander.rio.mbanking.services.soap.beans.body.ListaLeyendas
            r1.<init>()
            boolean r2 = r0.booleanValue()
            if (r2 == 0) goto L_0x0146
            com.google.gson.Gson r2 = new com.google.gson.Gson
            r2.<init>()
            java.lang.String r3 = "soapenv:Envelope"
            com.indra.httpclient.json.JSONObject r10 = r10.getJSONObject(r3)     // Catch:{ JSONException -> 0x0142 }
            java.lang.String r3 = "soapenv:Body"
            com.indra.httpclient.json.JSONObject r10 = r10.getJSONObject(r3)     // Catch:{ JSONException -> 0x0142 }
            java.lang.String r3 = "xml"
            com.indra.httpclient.json.JSONObject r10 = r10.getJSONObject(r3)     // Catch:{ JSONException -> 0x0142 }
            ar.com.santander.rio.mbanking.services.soap.beans.SuscripcionWomenResponseBean r3 = new ar.com.santander.rio.mbanking.services.soap.beans.SuscripcionWomenResponseBean     // Catch:{ JSONException -> 0x0142 }
            r3.<init>()     // Catch:{ JSONException -> 0x0142 }
            java.lang.String r4 = "header"
            com.indra.httpclient.json.JSONObject r4 = r10.getJSONObject(r4)     // Catch:{ JSONException -> 0x0142 }
            java.lang.String r4 = r4.toString()     // Catch:{ JSONException -> 0x0142 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean> r5 = ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean.class
            java.lang.Object r4 = r2.fromJson(r4, r5)     // Catch:{ JSONException -> 0x0142 }
            ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean r4 = (ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean) r4     // Catch:{ JSONException -> 0x0142 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.SuscripcionWomenBodyResponseBean r5 = new ar.com.santander.rio.mbanking.services.soap.beans.body.SuscripcionWomenBodyResponseBean     // Catch:{ JSONException -> 0x0142 }
            r5.<init>()     // Catch:{ JSONException -> 0x0142 }
            java.lang.String r6 = "body"
            com.indra.httpclient.json.JSONObject r10 = r10.getJSONObject(r6)     // Catch:{ JSONException -> 0x0142 }
            java.lang.String r6 = "nroComprobante"
            boolean r6 = r10.has(r6)     // Catch:{ JSONException -> 0x0142 }
            if (r6 == 0) goto L_0x0061
            java.lang.String r6 = "nroComprobante"
            java.lang.Object r6 = r10.get(r6)     // Catch:{ JSONException -> 0x0142 }
            java.lang.String r6 = r6.toString()     // Catch:{ JSONException -> 0x0142 }
            r5.setNroComprobante(r6)     // Catch:{ JSONException -> 0x0142 }
        L_0x0061:
            java.lang.String r6 = "fechaOperacion"
            boolean r6 = r10.has(r6)     // Catch:{ JSONException -> 0x0142 }
            if (r6 == 0) goto L_0x0076
            java.lang.String r6 = "fechaOperacion"
            java.lang.Object r6 = r10.get(r6)     // Catch:{ JSONException -> 0x0142 }
            java.lang.String r6 = r6.toString()     // Catch:{ JSONException -> 0x0142 }
            r5.setFechaOperacion(r6)     // Catch:{ JSONException -> 0x0142 }
        L_0x0076:
            java.lang.String r6 = "listaLeyendas"
            boolean r6 = r10.has(r6)     // Catch:{ JSONException -> 0x0142 }
            if (r6 == 0) goto L_0x0114
            java.lang.String r6 = "listaLeyendas"
            boolean r6 = r10.has(r6)     // Catch:{ JSONException -> 0x0142 }
            if (r6 == 0) goto L_0x0109
            java.lang.String r6 = "listaLeyendas"
            com.indra.httpclient.json.JSONObject r6 = r10.getJSONObject(r6)     // Catch:{ JSONException -> 0x0142 }
            java.lang.String r7 = "leyenda"
            boolean r6 = r6.has(r7)     // Catch:{ JSONException -> 0x0142 }
            if (r6 == 0) goto L_0x0109
            java.lang.String r6 = "listaLeyendas"
            com.indra.httpclient.json.JSONObject r6 = r10.getJSONObject(r6)     // Catch:{ JSONException -> 0x00a1 }
            java.lang.String r7 = "leyenda"
            com.indra.httpclient.json.JSONArray r6 = r6.getJSONArray(r7)     // Catch:{ JSONException -> 0x00a1 }
            goto L_0x00b5
        L_0x00a1:
            com.indra.httpclient.json.JSONArray r6 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x0142 }
            r6.<init>()     // Catch:{ JSONException -> 0x0142 }
            java.lang.String r7 = "listaLeyendas"
            com.indra.httpclient.json.JSONObject r7 = r10.getJSONObject(r7)     // Catch:{ JSONException -> 0x0142 }
            java.lang.String r8 = "leyenda"
            com.indra.httpclient.json.JSONObject r7 = r7.getJSONObject(r8)     // Catch:{ JSONException -> 0x0142 }
            r6.put(r7)     // Catch:{ JSONException -> 0x0142 }
        L_0x00b5:
            if (r6 == 0) goto L_0x00d8
            r10 = 0
        L_0x00b8:
            int r7 = r6.length()     // Catch:{ JSONException -> 0x0142 }
            if (r10 >= r7) goto L_0x0109
            com.indra.httpclient.json.JSONObject r7 = r6.getJSONObject(r10)     // Catch:{ JSONException -> 0x0142 }
            java.lang.String r7 = r7.toString()     // Catch:{ JSONException -> 0x0142 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda> r8 = ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda.class
            java.lang.Object r7 = r2.fromJson(r7, r8)     // Catch:{ JSONException -> 0x0142 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda r7 = (ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda) r7     // Catch:{ JSONException -> 0x0142 }
            java.util.List r8 = r1.getLstLeyendas()     // Catch:{ JSONException -> 0x0142 }
            r8.add(r7)     // Catch:{ JSONException -> 0x0142 }
            int r10 = r10 + 1
            goto L_0x00b8
        L_0x00d8:
            java.lang.String r6 = "listaLeyendas"
            com.indra.httpclient.json.JSONObject r6 = r10.getJSONObject(r6)     // Catch:{ JSONException -> 0x0142 }
            java.lang.String r7 = "leyenda"
            java.lang.Object r6 = r6.get(r7)     // Catch:{ JSONException -> 0x0142 }
            boolean r6 = r6 instanceof com.indra.httpclient.json.JSONObject     // Catch:{ JSONException -> 0x0142 }
            if (r6 == 0) goto L_0x0109
            java.lang.String r6 = "listaLeyendas"
            com.indra.httpclient.json.JSONObject r10 = r10.getJSONObject(r6)     // Catch:{ JSONException -> 0x0142 }
            java.lang.String r6 = "leyenda"
            com.indra.httpclient.json.JSONObject r10 = r10.getJSONObject(r6)     // Catch:{ JSONException -> 0x0142 }
            if (r10 == 0) goto L_0x0109
            java.lang.String r10 = r10.toString()     // Catch:{ JSONException -> 0x0142 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda> r6 = ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda.class
            java.lang.Object r10 = r2.fromJson(r10, r6)     // Catch:{ JSONException -> 0x0142 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda r10 = (ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda) r10     // Catch:{ JSONException -> 0x0142 }
            java.util.List r2 = r1.getLstLeyendas()     // Catch:{ JSONException -> 0x0142 }
            r2.add(r10)     // Catch:{ JSONException -> 0x0142 }
        L_0x0109:
            r5.setListaLeyendas(r1)     // Catch:{ JSONException -> 0x0142 }
            r3.headerBean = r4     // Catch:{ JSONException -> 0x0142 }
            r3.suscripcionWomenBodyResponseBean = r5     // Catch:{ JSONException -> 0x0142 }
            r9.onResponseBean(r3)     // Catch:{ JSONException -> 0x0142 }
            goto L_0x0146
        L_0x0114:
            java.lang.String r1 = "resCod"
            boolean r1 = r10.has(r1)     // Catch:{ JSONException -> 0x0142 }
            if (r1 == 0) goto L_0x0123
            java.lang.String r1 = "resCod"
            java.lang.String r1 = r10.getString(r1)     // Catch:{ JSONException -> 0x0142 }
            goto L_0x0125
        L_0x0123:
            java.lang.String r1 = ""
        L_0x0125:
            r5.resCod = r1     // Catch:{ JSONException -> 0x0142 }
            java.lang.String r1 = "resDesc"
            boolean r1 = r10.has(r1)     // Catch:{ JSONException -> 0x0142 }
            if (r1 == 0) goto L_0x0136
            java.lang.String r1 = "resDesc"
            java.lang.String r10 = r10.getString(r1)     // Catch:{ JSONException -> 0x0142 }
            goto L_0x0138
        L_0x0136:
            java.lang.String r10 = ""
        L_0x0138:
            r5.resDesc = r10     // Catch:{ JSONException -> 0x0142 }
            r3.headerBean = r4     // Catch:{ JSONException -> 0x0142 }
            r3.suscripcionWomenBodyResponseBean = r5     // Catch:{ JSONException -> 0x0142 }
            r9.onResponseBean(r3)     // Catch:{ JSONException -> 0x0142 }
            goto L_0x0146
        L_0x0142:
            r10 = move-exception
            r9.onUnknowError(r10)
        L_0x0146:
            boolean r10 = r0.booleanValue()
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.services.soap.request.SuscripcionWomenRequest.parserResponse(com.indra.httpclient.json.JSONObject):boolean");
    }
}
