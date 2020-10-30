package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetPreAutorizacionesRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetPreAutorizacionesResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class GetPreAutorizacionesRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private GetPreAutorizacionesRequestBean getPreAutorizacionesRequestBean;
    private GetPreAutorizacionesResponseBean getPreAutorizacionesResponseBean;

    public int getMethod() {
        return 1;
    }

    public GetPreAutorizacionesRequest(Context context, GetPreAutorizacionesRequestBean getPreAutorizacionesRequestBean2, ErrorRequestServer errorRequestServer) {
        super(context, false);
        this.getPreAutorizacionesRequestBean = getPreAutorizacionesRequestBean2;
        this.mErrorRequestServer = errorRequestServer;
    }

    public GetPreAutorizacionesRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public IBeanWS getBeanToRequest() {
        return this.getPreAutorizacionesRequestBean;
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

    public Class getBeanResponseClass() {
        if (this.getPreAutorizacionesResponseBean == null) {
            this.getPreAutorizacionesResponseBean = new GetPreAutorizacionesResponseBean();
        }
        return this.getPreAutorizacionesResponseBean.getClass();
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(2:13|14) */
    /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
        r6 = new com.indra.httpclient.json.JSONArray();
        r6.put((java.lang.Object) r11.getJSONObject("preautorizaciones").getJSONObject("preautorizacion"));
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x007f */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean parserResponse(com.indra.httpclient.json.JSONObject r11) {
        /*
            r10 = this;
            boolean r0 = super.parserResponse(r11)
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
            ar.com.santander.rio.mbanking.services.soap.beans.body.ListaPreAutorizacionesBean r1 = new ar.com.santander.rio.mbanking.services.soap.beans.body.ListaPreAutorizacionesBean
            r1.<init>()
            boolean r2 = r0.booleanValue()
            if (r2 == 0) goto L_0x012c
            com.google.gson.Gson r2 = new com.google.gson.Gson
            r2.<init>()
            java.lang.String r3 = "soapenv:Envelope"
            com.indra.httpclient.json.JSONObject r11 = r11.getJSONObject(r3)     // Catch:{ JSONException -> 0x0128 }
            java.lang.String r3 = "soapenv:Body"
            com.indra.httpclient.json.JSONObject r11 = r11.getJSONObject(r3)     // Catch:{ JSONException -> 0x0128 }
            java.lang.String r3 = "xml"
            com.indra.httpclient.json.JSONObject r11 = r11.getJSONObject(r3)     // Catch:{ JSONException -> 0x0128 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.GetPreAutorizacionesResponseBean r3 = new ar.com.santander.rio.mbanking.services.soap.beans.body.GetPreAutorizacionesResponseBean     // Catch:{ JSONException -> 0x0128 }
            r3.<init>()     // Catch:{ JSONException -> 0x0128 }
            java.lang.String r4 = "header"
            com.indra.httpclient.json.JSONObject r4 = r11.getJSONObject(r4)     // Catch:{ JSONException -> 0x0128 }
            java.lang.String r4 = r4.toString()     // Catch:{ JSONException -> 0x0128 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean> r5 = ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean.class
            java.lang.Object r4 = r2.fromJson(r4, r5)     // Catch:{ JSONException -> 0x0128 }
            ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean r4 = (ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean) r4     // Catch:{ JSONException -> 0x0128 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.GetPreAutorizacionesBodyResponseBean r5 = new ar.com.santander.rio.mbanking.services.soap.beans.body.GetPreAutorizacionesBodyResponseBean     // Catch:{ JSONException -> 0x0128 }
            r5.<init>()     // Catch:{ JSONException -> 0x0128 }
            java.lang.String r6 = "body"
            com.indra.httpclient.json.JSONObject r11 = r11.getJSONObject(r6)     // Catch:{ JSONException -> 0x0128 }
            java.lang.String r6 = "preautorizaciones"
            boolean r6 = r11.has(r6)     // Catch:{ JSONException -> 0x0128 }
            if (r6 == 0) goto L_0x00df
            java.lang.String r6 = "preautorizaciones"
            java.lang.Object r6 = r11.get(r6)     // Catch:{ JSONException -> 0x0128 }
            java.lang.String r6 = r6.toString()     // Catch:{ JSONException -> 0x0128 }
            boolean r6 = android.text.TextUtils.isEmpty(r6)     // Catch:{ JSONException -> 0x0128 }
            if (r6 != 0) goto L_0x00df
            java.lang.String r6 = "preautorizaciones"
            com.indra.httpclient.json.JSONObject r6 = r11.getJSONObject(r6)     // Catch:{ JSONException -> 0x0128 }
            java.lang.String r7 = "preautorizacion"
            boolean r6 = r6.has(r7)     // Catch:{ JSONException -> 0x0128 }
            if (r6 == 0) goto L_0x00df
            java.lang.String r6 = "preautorizaciones"
            com.indra.httpclient.json.JSONObject r6 = r11.getJSONObject(r6)     // Catch:{ JSONException -> 0x007f }
            java.lang.String r7 = "preautorizacion"
            com.indra.httpclient.json.JSONArray r6 = r6.getJSONArray(r7)     // Catch:{ JSONException -> 0x007f }
            goto L_0x0093
        L_0x007f:
            com.indra.httpclient.json.JSONArray r6 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x0128 }
            r6.<init>()     // Catch:{ JSONException -> 0x0128 }
            java.lang.String r7 = "preautorizaciones"
            com.indra.httpclient.json.JSONObject r7 = r11.getJSONObject(r7)     // Catch:{ JSONException -> 0x0128 }
            java.lang.String r8 = "preautorizacion"
            com.indra.httpclient.json.JSONObject r7 = r7.getJSONObject(r8)     // Catch:{ JSONException -> 0x0128 }
            r6.put(r7)     // Catch:{ JSONException -> 0x0128 }
        L_0x0093:
            if (r6 == 0) goto L_0x00b2
            r7 = 0
        L_0x0096:
            int r8 = r6.length()     // Catch:{ JSONException -> 0x0128 }
            if (r7 >= r8) goto L_0x00df
            com.indra.httpclient.json.JSONObject r8 = r6.getJSONObject(r7)     // Catch:{ JSONException -> 0x0128 }
            java.lang.String r8 = r8.toString()     // Catch:{ JSONException -> 0x0128 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.PreautorizacionBean> r9 = ar.com.santander.rio.mbanking.services.soap.beans.PreautorizacionBean.class
            java.lang.Object r8 = r2.fromJson(r8, r9)     // Catch:{ JSONException -> 0x0128 }
            ar.com.santander.rio.mbanking.services.soap.beans.PreautorizacionBean r8 = (ar.com.santander.rio.mbanking.services.soap.beans.PreautorizacionBean) r8     // Catch:{ JSONException -> 0x0128 }
            r1.add(r8)     // Catch:{ JSONException -> 0x0128 }
            int r7 = r7 + 1
            goto L_0x0096
        L_0x00b2:
            java.lang.String r6 = "preautorizaciones"
            com.indra.httpclient.json.JSONObject r6 = r11.getJSONObject(r6)     // Catch:{ JSONException -> 0x0128 }
            java.lang.String r7 = "preautorizacion"
            java.lang.Object r6 = r6.get(r7)     // Catch:{ JSONException -> 0x0128 }
            boolean r6 = r6 instanceof com.indra.httpclient.json.JSONObject     // Catch:{ JSONException -> 0x0128 }
            if (r6 == 0) goto L_0x00df
            java.lang.String r6 = "preautorizaciones"
            com.indra.httpclient.json.JSONObject r6 = r11.getJSONObject(r6)     // Catch:{ JSONException -> 0x0128 }
            java.lang.String r7 = "preautorizacion"
            com.indra.httpclient.json.JSONObject r6 = r6.getJSONObject(r7)     // Catch:{ JSONException -> 0x0128 }
            if (r6 == 0) goto L_0x00df
            java.lang.String r6 = r6.toString()     // Catch:{ JSONException -> 0x0128 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.PreautorizacionBean> r7 = ar.com.santander.rio.mbanking.services.soap.beans.PreautorizacionBean.class
            java.lang.Object r2 = r2.fromJson(r6, r7)     // Catch:{ JSONException -> 0x0128 }
            ar.com.santander.rio.mbanking.services.soap.beans.PreautorizacionBean r2 = (ar.com.santander.rio.mbanking.services.soap.beans.PreautorizacionBean) r2     // Catch:{ JSONException -> 0x0128 }
            r1.add(r2)     // Catch:{ JSONException -> 0x0128 }
        L_0x00df:
            java.lang.String r2 = "siguientePagina"
            boolean r2 = r11.has(r2)     // Catch:{ JSONException -> 0x0128 }
            if (r2 == 0) goto L_0x00ee
            java.lang.String r2 = "siguientePagina"
            java.lang.String r2 = r11.getString(r2)     // Catch:{ JSONException -> 0x0128 }
            goto L_0x00f0
        L_0x00ee:
            java.lang.String r2 = ""
        L_0x00f0:
            r5.setSiguientePagina(r2)     // Catch:{ JSONException -> 0x0128 }
            java.util.List r1 = r1.getListPreAutorizacionesBean()     // Catch:{ JSONException -> 0x0128 }
            r5.setListPreautorizaciones(r1)     // Catch:{ JSONException -> 0x0128 }
            java.lang.String r1 = "resCod"
            boolean r1 = r11.has(r1)     // Catch:{ JSONException -> 0x0128 }
            if (r1 == 0) goto L_0x0109
            java.lang.String r1 = "resCod"
            java.lang.String r1 = r11.getString(r1)     // Catch:{ JSONException -> 0x0128 }
            goto L_0x010b
        L_0x0109:
            java.lang.String r1 = ""
        L_0x010b:
            r5.resCod = r1     // Catch:{ JSONException -> 0x0128 }
            java.lang.String r1 = "resDesc"
            boolean r1 = r11.has(r1)     // Catch:{ JSONException -> 0x0128 }
            if (r1 == 0) goto L_0x011c
            java.lang.String r1 = "resDesc"
            java.lang.String r11 = r11.getString(r1)     // Catch:{ JSONException -> 0x0128 }
            goto L_0x011e
        L_0x011c:
            java.lang.String r11 = ""
        L_0x011e:
            r5.resDesc = r11     // Catch:{ JSONException -> 0x0128 }
            r3.headerBean = r4     // Catch:{ JSONException -> 0x0128 }
            r3.getPreAutorizacionesBodyResponseBean = r5     // Catch:{ JSONException -> 0x0128 }
            r10.onResponseBean(r3)     // Catch:{ JSONException -> 0x0128 }
            goto L_0x012c
        L_0x0128:
            r11 = move-exception
            r10.onUnknowError(r11)
        L_0x012c:
            boolean r11 = r0.booleanValue()
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.services.soap.request.GetPreAutorizacionesRequest.parserResponse(com.indra.httpclient.json.JSONObject):boolean");
    }
}
