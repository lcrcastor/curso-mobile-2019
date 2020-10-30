package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.GetNumerosUtilesRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetNumerosUtilesResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class GetNumerosUtilesRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private GetNumerosUtilesRequestBean mGetNumerosUtilesRequestBean;
    private GetNumerosUtilesResponseBean mGetNumerosUtilesResponseBean;

    public int getMethod() {
        return 1;
    }

    public GetNumerosUtilesRequest(Context context, GetNumerosUtilesRequestBean getNumerosUtilesRequestBean, ErrorRequestServer errorRequestServer) {
        super(context);
        this.mGetNumerosUtilesRequestBean = getNumerosUtilesRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mGetNumerosUtilesRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mGetNumerosUtilesResponseBean == null) {
            this.mGetNumerosUtilesResponseBean = new GetNumerosUtilesResponseBean();
        }
        return this.mGetNumerosUtilesResponseBean.getClass();
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(5:20|21|22|23|42) */
    /* JADX WARNING: Can't wrap try/catch for region: R(7:28|29|30|31|32|33|50) */
    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        onUnknowError(new java.lang.Exception());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        onUnknowError(new java.lang.Exception());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:?, code lost:
        return r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:?, code lost:
        return r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:?, code lost:
        return r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:?, code lost:
        return r5;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:20:0x0084 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:22:0x008e */
    /* JADX WARNING: Missing exception handler attribute for start block: B:30:0x00e0 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:32:0x00e9 */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:32:0x00e9=Splitter:B:32:0x00e9, B:22:0x008e=Splitter:B:22:0x008e} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean parserResponse(com.indra.httpclient.json.JSONObject r5) {
        /*
            r4 = this;
            r0 = 0
            if (r5 == 0) goto L_0x00f4
            java.lang.String r1 = "soapenv:Envelope"
            com.indra.httpclient.json.JSONObject r1 = r5.getJSONObject(r1)     // Catch:{ Exception -> 0x00f2 }
            java.lang.String r2 = "soapenv:Body"
            com.indra.httpclient.json.JSONObject r1 = r1.getJSONObject(r2)     // Catch:{ Exception -> 0x00f2 }
            java.lang.String r2 = "xml"
            com.indra.httpclient.json.JSONObject r1 = r1.getJSONObject(r2)     // Catch:{ Exception -> 0x00f2 }
            java.lang.String r2 = "body"
            com.indra.httpclient.json.JSONObject r1 = r1.getJSONObject(r2)     // Catch:{ Exception -> 0x00f2 }
            java.lang.String r2 = "respuesta"
            boolean r2 = r1.has(r2)     // Catch:{ Exception -> 0x00f2 }
            if (r2 == 0) goto L_0x0041
            java.lang.String r1 = "soapenv:Envelope"
            com.indra.httpclient.json.JSONObject r5 = r5.getJSONObject(r1)     // Catch:{ Exception -> 0x00f2 }
            java.lang.String r1 = "soapenv:Body"
            com.indra.httpclient.json.JSONObject r5 = r5.getJSONObject(r1)     // Catch:{ Exception -> 0x00f2 }
            java.lang.String r1 = "xml"
            com.indra.httpclient.json.JSONObject r5 = r5.getJSONObject(r1)     // Catch:{ Exception -> 0x00f2 }
            java.lang.String r1 = "body"
            com.indra.httpclient.json.JSONObject r5 = r5.getJSONObject(r1)     // Catch:{ Exception -> 0x00f2 }
            java.lang.String r1 = "respuesta"
            com.indra.httpclient.json.JSONObject r1 = r5.getJSONObject(r1)     // Catch:{ Exception -> 0x00f2 }
        L_0x0041:
            java.lang.String r5 = "res"
            int r5 = r1.getInt(r5)     // Catch:{ Exception -> 0x00f2 }
            if (r5 != 0) goto L_0x004b
            r5 = 1
            goto L_0x004c
        L_0x004b:
            r5 = 0
        L_0x004c:
            if (r5 == 0) goto L_0x0097
            com.google.gson.Gson r2 = new com.google.gson.Gson     // Catch:{ Exception -> 0x00fe }
            r2.<init>()     // Catch:{ Exception -> 0x00fe }
            ar.com.santander.rio.mbanking.services.soap.beans.GetNumerosUtilesResponseBean r2 = new ar.com.santander.rio.mbanking.services.soap.beans.GetNumerosUtilesResponseBean     // Catch:{ Exception -> 0x0084 }
            r2.<init>()     // Catch:{ Exception -> 0x0084 }
            java.lang.String r3 = "datosUtiles"
            java.lang.Object r3 = r1.get(r3)     // Catch:{ Exception -> 0x0084 }
            boolean r3 = r3 instanceof com.indra.httpclient.json.JSONArray     // Catch:{ Exception -> 0x0084 }
            if (r3 == 0) goto L_0x0074
            java.lang.String r3 = "datosUtiles"
            java.lang.Object r1 = r1.get(r3)     // Catch:{ Exception -> 0x0084 }
            com.indra.httpclient.json.JSONArray r1 = (com.indra.httpclient.json.JSONArray) r1     // Catch:{ Exception -> 0x0084 }
            java.lang.Object r0 = r1.get(r0)     // Catch:{ Exception -> 0x0084 }
            com.indra.httpclient.json.JSONObject r0 = (com.indra.httpclient.json.JSONObject) r0     // Catch:{ Exception -> 0x0084 }
            r2.setJsonElement(r0)     // Catch:{ Exception -> 0x0084 }
            goto L_0x007f
        L_0x0074:
            java.lang.String r0 = "datosUtiles"
            java.lang.Object r0 = r1.get(r0)     // Catch:{ Exception -> 0x0084 }
            com.indra.httpclient.json.JSONObject r0 = (com.indra.httpclient.json.JSONObject) r0     // Catch:{ Exception -> 0x0084 }
            r2.setJsonElement(r0)     // Catch:{ Exception -> 0x0084 }
        L_0x007f:
            r4.onResponseBean(r2)     // Catch:{ Exception -> 0x0084 }
            goto L_0x0106
        L_0x0084:
            java.lang.Exception r0 = new java.lang.Exception     // Catch:{ Exception -> 0x008e }
            r0.<init>()     // Catch:{ Exception -> 0x008e }
            r4.onUnknowError(r0)     // Catch:{ Exception -> 0x008e }
            goto L_0x0106
        L_0x008e:
            java.lang.Exception r0 = new java.lang.Exception     // Catch:{ Exception -> 0x00fe }
            r0.<init>()     // Catch:{ Exception -> 0x00fe }
            r4.onUnknowError(r0)     // Catch:{ Exception -> 0x00fe }
            goto L_0x0106
        L_0x0097:
            java.lang.String r0 = "res"
            boolean r0 = r1.has(r0)     // Catch:{ Exception -> 0x00fe }
            if (r0 == 0) goto L_0x0106
            java.lang.String r0 = "res"
            int r0 = r1.getInt(r0)     // Catch:{ Exception -> 0x00fe }
            if (r0 == 0) goto L_0x0106
            ar.com.santander.rio.mbanking.services.soap.beans.GetNumerosUtilesResponseBean r0 = new ar.com.santander.rio.mbanking.services.soap.beans.GetNumerosUtilesResponseBean     // Catch:{ Exception -> 0x00e0 }
            r0.<init>()     // Catch:{ Exception -> 0x00e0 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.GetNumerosUtilesBodyResponseBean r2 = new ar.com.santander.rio.mbanking.services.soap.beans.body.GetNumerosUtilesBodyResponseBean     // Catch:{ Exception -> 0x00e0 }
            r2.<init>()     // Catch:{ Exception -> 0x00e0 }
            java.lang.String r3 = "res"
            java.lang.String r3 = r1.getString(r3)     // Catch:{ Exception -> 0x00e0 }
            r2.res = r3     // Catch:{ Exception -> 0x00e0 }
            java.lang.String r3 = "resCod"
            java.lang.String r3 = r1.getString(r3)     // Catch:{ Exception -> 0x00e0 }
            r2.resCod = r3     // Catch:{ Exception -> 0x00e0 }
            java.lang.String r3 = "resTitle"
            java.lang.String r3 = r1.getString(r3)     // Catch:{ Exception -> 0x00e0 }
            r2.resTitle = r3     // Catch:{ Exception -> 0x00e0 }
            java.lang.String r3 = "resDesc"
            java.lang.String r3 = r1.getString(r3)     // Catch:{ Exception -> 0x00e0 }
            r2.resDesc = r3     // Catch:{ Exception -> 0x00e0 }
            java.lang.String r3 = "idSist"
            java.lang.String r1 = r1.getString(r3)     // Catch:{ Exception -> 0x00e0 }
            r2.idSist = r1     // Catch:{ Exception -> 0x00e0 }
            r0.setGetNumerosUtilesBodyResponseBean(r2)     // Catch:{ Exception -> 0x00e0 }
            r4.onResponseBean(r0)     // Catch:{ Exception -> 0x00e0 }
            goto L_0x0106
        L_0x00e0:
            java.lang.Exception r0 = new java.lang.Exception     // Catch:{ Exception -> 0x00e9 }
            r0.<init>()     // Catch:{ Exception -> 0x00e9 }
            r4.onUnknowError(r0)     // Catch:{ Exception -> 0x00e9 }
            goto L_0x0106
        L_0x00e9:
            java.lang.Exception r0 = new java.lang.Exception     // Catch:{ Exception -> 0x00fe }
            r0.<init>()     // Catch:{ Exception -> 0x00fe }
            r4.onUnknowError(r0)     // Catch:{ Exception -> 0x00fe }
            goto L_0x0106
        L_0x00f2:
            r5 = 0
            goto L_0x00fe
        L_0x00f4:
            java.lang.Exception r5 = new java.lang.Exception     // Catch:{ Exception -> 0x00f2 }
            r5.<init>()     // Catch:{ Exception -> 0x00f2 }
            r4.onUnknowError(r5)     // Catch:{ Exception -> 0x00f2 }
            r5 = 0
            goto L_0x0106
        L_0x00fe:
            java.lang.Exception r0 = new java.lang.Exception
            r0.<init>()
            r4.onUnknowError(r0)
        L_0x0106:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.services.soap.request.GetNumerosUtilesRequest.parserResponse(com.indra.httpclient.json.JSONObject):boolean");
    }
}
