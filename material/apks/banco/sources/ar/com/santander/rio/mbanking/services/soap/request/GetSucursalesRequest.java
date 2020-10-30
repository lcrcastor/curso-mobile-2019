package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.GetSucursalesRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetSucursalesResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class GetSucursalesRequest extends BaseRequest implements IBeanRequestWS {
    private GetSucursalesRequestBean mGetSucursalesRequestBean;
    private GetSucursalesResponseBean mGetSucursalesResponseBean;

    public int getMethod() {
        return 1;
    }

    public GetSucursalesRequest(Context context, GetSucursalesRequestBean getSucursalesRequestBean, ErrorRequestServer errorRequestServer) {
        super(context);
        this.mGetSucursalesRequestBean = getSucursalesRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public GetSucursalesRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mGetSucursalesRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mGetSucursalesResponseBean == null) {
            this.mGetSucursalesResponseBean = new GetSucursalesResponseBean();
        }
        return this.mGetSucursalesResponseBean.getClass();
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0044 A[SYNTHETIC, Splitter:B:15:0x0044] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean parserResponse(com.indra.httpclient.json.JSONObject r8) {
        /*
            r7 = this;
            boolean r0 = super.parserResponse(r8)
            if (r0 == 0) goto L_0x00cd
            com.google.gson.Gson r1 = new com.google.gson.Gson
            r1.<init>()
            r2 = 0
            java.lang.String r3 = "soapenv:Envelope"
            com.indra.httpclient.json.JSONObject r8 = r8.getJSONObject(r3)     // Catch:{ JsonSyntaxException -> 0x0041 }
            java.lang.String r3 = "soapenv:Body"
            com.indra.httpclient.json.JSONObject r8 = r8.getJSONObject(r3)     // Catch:{ JsonSyntaxException -> 0x0041 }
            java.lang.String r3 = "xml"
            com.indra.httpclient.json.JSONObject r8 = r8.getJSONObject(r3)     // Catch:{ JsonSyntaxException -> 0x0041 }
            java.lang.String r2 = r8.toString()     // Catch:{ JsonSyntaxException -> 0x0042 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.GetSucursalesResponseBean> r3 = ar.com.santander.rio.mbanking.services.soap.beans.GetSucursalesResponseBean.class
            java.lang.Object r2 = r1.fromJson(r2, r3)     // Catch:{ JsonSyntaxException -> 0x0042 }
            ar.com.santander.rio.mbanking.services.soap.beans.GetSucursalesResponseBean r2 = (ar.com.santander.rio.mbanking.services.soap.beans.GetSucursalesResponseBean) r2     // Catch:{ JsonSyntaxException -> 0x0042 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.GetSucursalesBodyResponseBean r3 = r2.getSucursalesBodyResponseBean     // Catch:{ JsonSyntaxException -> 0x0042 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.SucursalesBean r3 = r3.sucursalesBean     // Catch:{ JsonSyntaxException -> 0x0042 }
            if (r3 != 0) goto L_0x0039
            ar.com.santander.rio.mbanking.services.soap.beans.body.GetSucursalesBodyResponseBean r3 = r2.getSucursalesBodyResponseBean     // Catch:{ JsonSyntaxException -> 0x0042 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.SucursalesBean r4 = new ar.com.santander.rio.mbanking.services.soap.beans.body.SucursalesBean     // Catch:{ JsonSyntaxException -> 0x0042 }
            r4.<init>()     // Catch:{ JsonSyntaxException -> 0x0042 }
            r3.sucursalesBean = r4     // Catch:{ JsonSyntaxException -> 0x0042 }
        L_0x0039:
            r7.onResponseBean(r2)     // Catch:{ JsonSyntaxException -> 0x0042 }
            goto L_0x00cd
        L_0x003e:
            r8 = move-exception
            goto L_0x00ca
        L_0x0041:
            r8 = r2
        L_0x0042:
            if (r8 == 0) goto L_0x00cd
            ar.com.santander.rio.mbanking.services.soap.beans.GetSucursalesResponseBean r2 = new ar.com.santander.rio.mbanking.services.soap.beans.GetSucursalesResponseBean     // Catch:{ JSONException -> 0x003e }
            r2.<init>()     // Catch:{ JSONException -> 0x003e }
            java.lang.String r3 = "header"
            com.indra.httpclient.json.JSONObject r3 = r8.getJSONObject(r3)     // Catch:{ JSONException -> 0x003e }
            java.lang.String r3 = r3.toString()     // Catch:{ JSONException -> 0x003e }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean> r4 = ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean.class
            java.lang.Object r3 = r1.fromJson(r3, r4)     // Catch:{ JSONException -> 0x003e }
            ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean r3 = (ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean) r3     // Catch:{ JSONException -> 0x003e }
            r2.setHeaderBean(r3)     // Catch:{ JSONException -> 0x003e }
            ar.com.santander.rio.mbanking.services.soap.beans.body.GetSucursalesBodyResponseBean r3 = new ar.com.santander.rio.mbanking.services.soap.beans.body.GetSucursalesBodyResponseBean     // Catch:{ JSONException -> 0x003e }
            r3.<init>()     // Catch:{ JSONException -> 0x003e }
            java.lang.String r4 = "body"
            com.indra.httpclient.json.JSONObject r8 = r8.getJSONObject(r4)     // Catch:{ JSONException -> 0x003e }
            java.lang.String r4 = "pagina"
            java.lang.String r4 = r8.getString(r4)     // Catch:{ JSONException -> 0x003e }
            r3.pagina = r4     // Catch:{ JSONException -> 0x003e }
            java.lang.String r4 = "paginas"
            java.lang.String r4 = r8.getString(r4)     // Catch:{ JSONException -> 0x003e }
            r3.paginas = r4     // Catch:{ JSONException -> 0x003e }
            ar.com.santander.rio.mbanking.services.soap.beans.body.SucursalesBean r4 = new ar.com.santander.rio.mbanking.services.soap.beans.body.SucursalesBean     // Catch:{ JSONException -> 0x003e }
            r4.<init>()     // Catch:{ JSONException -> 0x003e }
            java.lang.String r5 = "sucursales"
            java.lang.Object r8 = r8.get(r5)     // Catch:{ JSONException -> 0x003e }
            boolean r5 = r8 instanceof com.indra.httpclient.json.JSONObject     // Catch:{ JSONException -> 0x003e }
            if (r5 == 0) goto L_0x00cd
            r5 = r8
            com.indra.httpclient.json.JSONObject r5 = (com.indra.httpclient.json.JSONObject) r5     // Catch:{ JSONException -> 0x003e }
            java.lang.String r6 = "sucursal"
            java.lang.Object r5 = r5.get(r6)     // Catch:{ JSONException -> 0x003e }
            if (r5 == 0) goto L_0x00c1
            boolean r6 = r5 instanceof com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x003e }
            if (r6 == 0) goto L_0x00a5
            java.lang.String r8 = r8.toString()     // Catch:{ JSONException -> 0x003e }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.body.SucursalesBean> r4 = ar.com.santander.rio.mbanking.services.soap.beans.body.SucursalesBean.class
            java.lang.Object r8 = r1.fromJson(r8, r4)     // Catch:{ JSONException -> 0x003e }
            r4 = r8
            ar.com.santander.rio.mbanking.services.soap.beans.body.SucursalesBean r4 = (ar.com.santander.rio.mbanking.services.soap.beans.body.SucursalesBean) r4     // Catch:{ JSONException -> 0x003e }
            goto L_0x00c1
        L_0x00a5:
            boolean r8 = r5 instanceof com.indra.httpclient.json.JSONObject     // Catch:{ JSONException -> 0x003e }
            if (r8 == 0) goto L_0x00c1
            java.lang.String r8 = r5.toString()     // Catch:{ JSONException -> 0x003e }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.body.SucursalBean> r5 = ar.com.santander.rio.mbanking.services.soap.beans.body.SucursalBean.class
            java.lang.Object r8 = r1.fromJson(r8, r5)     // Catch:{ JSONException -> 0x003e }
            ar.com.santander.rio.mbanking.services.soap.beans.body.SucursalBean r8 = (ar.com.santander.rio.mbanking.services.soap.beans.body.SucursalBean) r8     // Catch:{ JSONException -> 0x003e }
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ JSONException -> 0x003e }
            r1.<init>()     // Catch:{ JSONException -> 0x003e }
            r4.sucursalBean = r1     // Catch:{ JSONException -> 0x003e }
            java.util.List<ar.com.santander.rio.mbanking.services.soap.beans.body.SucursalBean> r1 = r4.sucursalBean     // Catch:{ JSONException -> 0x003e }
            r1.add(r8)     // Catch:{ JSONException -> 0x003e }
        L_0x00c1:
            r3.sucursalesBean = r4     // Catch:{ JSONException -> 0x003e }
            r2.setGetSucursalesBodyResponseBean(r3)     // Catch:{ JSONException -> 0x003e }
            r7.onResponseBean(r2)     // Catch:{ JSONException -> 0x003e }
            goto L_0x00cd
        L_0x00ca:
            r7.onUnknowError(r8)
        L_0x00cd:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.services.soap.request.GetSucursalesRequest.parserResponse(com.indra.httpclient.json.JSONObject):boolean");
    }
}
