package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.services.soap.beans.GetRecargasRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetRecargasResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetRecargasBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public abstract class GetRecargasRequest extends BaseRequest implements IBeanRequestWS {
    private GetRecargasRequestBean getRecargasRequestBean;
    private GetRecargasResponseBean getRecargasResponseBean;
    private JSONObject jsonResponse = new JSONObject();

    public int getMethod() {
        return 1;
    }

    public GetRecargasRequest(Context context, GetRecargasRequestBean getRecargasRequestBean2, ErrorRequestServer errorRequestServer) {
        super(context);
        this.getRecargasRequestBean = getRecargasRequestBean2;
        this.mErrorRequestServer = errorRequestServer;
    }

    public GetRecargasRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.getRecargasRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.getRecargasResponseBean == null) {
            this.getRecargasResponseBean = new GetRecargasResponseBean();
        }
        return this.getRecargasResponseBean.getClass();
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public boolean parserResponse(JSONObject jSONObject) {
        boolean parserResponse = super.parserResponse(jSONObject);
        if (parserResponse) {
            Gson gson = new Gson();
            try {
                JSONObject jSONObject2 = jSONObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body").getJSONObject("xml");
                GetRecargasResponseBean getRecargasResponseBean2 = new GetRecargasResponseBean();
                PrivateHeaderBean privateHeaderBean = (PrivateHeaderBean) gson.fromJson(jSONObject2.getJSONObject("header").toString(), PrivateHeaderBean.class);
                JSONObject jSONObject3 = jSONObject2.getJSONObject("body");
                addElementsToList("listaRecargas", "recarga", jSONObject3, new String[]{"empServ", "empDescr", "identificacion", "alias"});
                addElementsToList("listaCuentas", "cuenta", jSONObject3, new String[]{"sucursalCuenta", "tipo", "numeroCuenta", "descripcionCuenta"});
                addElementsToList("listaValores", "valor", jSONObject3, new String[]{TarjetasConstants.MONEDA, "importe", "descripcion"});
                this.jsonResponse.put("sesionUsuario", (Object) jSONObject3.get("sesionUsuario").toString());
                try {
                    this.jsonResponse.put("msjTitulo", (Object) jSONObject3.get("msjTitulo").toString());
                    this.jsonResponse.put("msjDesc", (Object) jSONObject3.get("msjDesc").toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                GetRecargasBodyResponseBean getRecargasBodyResponseBean = (GetRecargasBodyResponseBean) new Gson().fromJson(String.valueOf(this.jsonResponse), GetRecargasBodyResponseBean.class);
                getRecargasResponseBean2.header = privateHeaderBean;
                getRecargasResponseBean2.getRecargasBodyResponseBean = getRecargasBodyResponseBean;
                onResponseBean(getRecargasResponseBean2);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return parserResponse;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(2:7|8) */
    /* JADX WARNING: Code restructure failed: missing block: B:8:?, code lost:
        r11 = new com.indra.httpclient.json.JSONArray();
        r11.put((java.lang.Object) r0.getJSONObject(r10));
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0014 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void addElementsToList(java.lang.String r9, java.lang.String r10, com.indra.httpclient.json.JSONObject r11, java.lang.String[] r12) {
        /*
            r8 = this;
            com.indra.httpclient.json.JSONObject r0 = new com.indra.httpclient.json.JSONObject
            r0.<init>()
            boolean r1 = r11.has(r9)     // Catch:{ JSONException -> 0x005d }
            if (r1 == 0) goto L_0x000f
            com.indra.httpclient.json.JSONObject r0 = r11.getJSONObject(r9)     // Catch:{ JSONException -> 0x005d }
        L_0x000f:
            com.indra.httpclient.json.JSONArray r11 = r0.getJSONArray(r10)     // Catch:{ JSONException -> 0x0014 }
            goto L_0x0020
        L_0x0014:
            com.indra.httpclient.json.JSONArray r11 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x005d }
            r11.<init>()     // Catch:{ JSONException -> 0x005d }
            com.indra.httpclient.json.JSONObject r10 = r0.getJSONObject(r10)     // Catch:{ JSONException -> 0x005d }
            r11.put(r10)     // Catch:{ JSONException -> 0x005d }
        L_0x0020:
            com.indra.httpclient.json.JSONArray r10 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x005d }
            r10.<init>()     // Catch:{ JSONException -> 0x005d }
            r1 = 0
            r2 = 0
        L_0x0027:
            int r3 = r11.length()     // Catch:{ JSONException -> 0x005d }
            if (r2 >= r3) goto L_0x0055
            com.indra.httpclient.json.JSONObject r3 = new com.indra.httpclient.json.JSONObject     // Catch:{ JSONException -> 0x005d }
            r3.<init>()     // Catch:{ JSONException -> 0x005d }
            r4 = 0
        L_0x0033:
            int r5 = r12.length     // Catch:{ JSONException -> 0x005d }
            if (r4 >= r5) goto L_0x004f
            r5 = r12[r4]     // Catch:{ Exception -> 0x0048 }
            java.lang.Object r6 = r11.get(r2)     // Catch:{ Exception -> 0x0048 }
            com.indra.httpclient.json.JSONObject r6 = (com.indra.httpclient.json.JSONObject) r6     // Catch:{ Exception -> 0x0048 }
            r7 = r12[r4]     // Catch:{ Exception -> 0x0048 }
            java.lang.String r6 = r6.getString(r7)     // Catch:{ Exception -> 0x0048 }
            r3.put(r5, r6)     // Catch:{ Exception -> 0x0048 }
            goto L_0x004c
        L_0x0048:
            r5 = move-exception
            r5.printStackTrace()     // Catch:{ JSONException -> 0x005d }
        L_0x004c:
            int r4 = r4 + 1
            goto L_0x0033
        L_0x004f:
            r10.put(r3)     // Catch:{ JSONException -> 0x005d }
            int r2 = r2 + 1
            goto L_0x0027
        L_0x0055:
            if (r0 == 0) goto L_0x0061
            com.indra.httpclient.json.JSONObject r11 = r8.jsonResponse     // Catch:{ JSONException -> 0x005d }
            r11.put(r9, r10)     // Catch:{ JSONException -> 0x005d }
            goto L_0x0061
        L_0x005d:
            r9 = move-exception
            r9.printStackTrace()
        L_0x0061:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.services.soap.request.GetRecargasRequest.addElementsToList(java.lang.String, java.lang.String, com.indra.httpclient.json.JSONObject, java.lang.String[]):void");
    }
}
