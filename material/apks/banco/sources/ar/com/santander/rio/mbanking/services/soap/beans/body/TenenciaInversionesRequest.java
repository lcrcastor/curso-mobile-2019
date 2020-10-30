package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import ar.com.santander.rio.mbanking.services.soap.request.BaseRequest;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public class TenenciaInversionesRequest extends BaseRequest implements IBeanRequestWS {
    private TenenciaInversionesRequestBean mTenenciaInversionesRequestBean;
    private TenenciaInversionesResponseBean mTenenciaInversionesResponseBean;

    public int getMethod() {
        return 1;
    }

    public void onResponseBean(IBeanWS iBeanWS) {
    }

    public void onUnknowError(Exception exc) {
    }

    public TenenciaInversionesRequest(Context context, TenenciaInversionesRequestBean tenenciaInversionesRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, false);
        this.mTenenciaInversionesRequestBean = tenenciaInversionesRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public TenenciaInversionesRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mTenenciaInversionesRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mTenenciaInversionesResponseBean == null) {
            this.mTenenciaInversionesResponseBean = new TenenciaInversionesResponseBean();
        }
        return this.mTenenciaInversionesResponseBean.getClass();
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(2:23|24) */
    /* JADX WARNING: Code restructure failed: missing block: B:112:?, code lost:
        r11 = new com.indra.httpclient.json.JSONArray();
        r11.put((java.lang.Object) r9.getJSONObject("producto"));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:137:?, code lost:
        r5 = new com.indra.httpclient.json.JSONArray();
        r5.put((java.lang.Object) r4.getJSONObject("leyenda"));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        r8 = new com.indra.httpclient.json.JSONArray();
        r8.put((java.lang.Object) r7.getJSONObject("cuenta"));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:?, code lost:
        r12 = new com.indra.httpclient.json.JSONArray();
        r12.put((java.lang.Object) r10.getJSONObject("producto"));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:?, code lost:
        r7 = new com.indra.httpclient.json.JSONArray();
        r7.put((java.lang.Object) r6.getJSONObject("leyenda"));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:?, code lost:
        r8 = new com.indra.httpclient.json.JSONArray();
        r8.put((java.lang.Object) r7.getJSONObject("cuenta"));
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:111:0x0234 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:136:0x02b5 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:23:0x0088 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:42:0x00e6 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:67:0x015c */
    /* JADX WARNING: Missing exception handler attribute for start block: B:93:0x01d7 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean parserResponse(com.indra.httpclient.json.JSONObject r15) {
        /*
            r14 = this;
            boolean r0 = super.parserResponse(r15)
            if (r0 == 0) goto L_0x02f0
            com.google.gson.Gson r1 = new com.google.gson.Gson
            r1.<init>()
            java.lang.String r2 = "soapenv:Envelope"
            com.indra.httpclient.json.JSONObject r2 = r15.getJSONObject(r2)     // Catch:{ JSONException -> 0x02ec }
            java.lang.String r3 = "soapenv:Body"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r3)     // Catch:{ JSONException -> 0x02ec }
            java.lang.String r3 = "xml"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r3)     // Catch:{ JSONException -> 0x02ec }
            ar.com.santander.rio.mbanking.services.soap.beans.body.TenenciaInversionesResponseBean r3 = new ar.com.santander.rio.mbanking.services.soap.beans.body.TenenciaInversionesResponseBean     // Catch:{ JSONException -> 0x02ec }
            r3.<init>()     // Catch:{ JSONException -> 0x02ec }
            java.lang.String r4 = "header"
            com.indra.httpclient.json.JSONObject r4 = r2.getJSONObject(r4)     // Catch:{ JSONException -> 0x02ec }
            java.lang.String r4 = r4.toString()     // Catch:{ JSONException -> 0x02ec }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean> r5 = ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean.class
            java.lang.Object r1 = r1.fromJson(r4, r5)     // Catch:{ JSONException -> 0x02ec }
            ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean r1 = (ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean) r1     // Catch:{ JSONException -> 0x02ec }
            java.lang.String r4 = "body"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r4)     // Catch:{ JSONException -> 0x02ec }
            r4 = 0
            r5 = 0
            if (r2 == 0) goto L_0x013d
            java.lang.String r6 = "bancaPrivada"
            boolean r6 = r2.has(r6)     // Catch:{ JSONException -> 0x02ec }
            if (r6 == 0) goto L_0x013d
            java.lang.String r6 = "bancaPrivada"
            java.lang.Object r6 = r2.get(r6)     // Catch:{ JSONException -> 0x02ec }
            com.indra.httpclient.json.JSONObject r6 = (com.indra.httpclient.json.JSONObject) r6     // Catch:{ JSONException -> 0x02ec }
            if (r6 == 0) goto L_0x0065
            java.lang.String r7 = "tenConsolidada"
            boolean r7 = r6.has(r7)     // Catch:{ JSONException -> 0x02ec }
            if (r7 == 0) goto L_0x0065
            java.lang.String r7 = "tenConsolidada"
            java.lang.Object r7 = r6.get(r7)     // Catch:{ JSONException -> 0x02ec }
            com.indra.httpclient.json.JSONObject r7 = (com.indra.httpclient.json.JSONObject) r7     // Catch:{ JSONException -> 0x02ec }
            java.lang.String r8 = "tenConsolidada"
            r6.put(r8, r7)     // Catch:{ JSONException -> 0x02ec }
        L_0x0065:
            if (r6 == 0) goto L_0x0138
            java.lang.String r7 = "agrupadorCuenta"
            boolean r7 = r6.has(r7)     // Catch:{ JSONException -> 0x02ec }
            if (r7 == 0) goto L_0x0138
            java.lang.String r7 = "agrupadorCuenta"
            java.lang.Object r7 = r6.get(r7)     // Catch:{ JSONException -> 0x02ec }
            com.indra.httpclient.json.JSONObject r7 = (com.indra.httpclient.json.JSONObject) r7     // Catch:{ JSONException -> 0x02ec }
            if (r7 == 0) goto L_0x0133
            java.lang.String r8 = "cuenta"
            boolean r8 = r7.has(r8)     // Catch:{ JSONException -> 0x02ec }
            if (r8 == 0) goto L_0x0133
            java.lang.String r8 = "cuenta"
            com.indra.httpclient.json.JSONArray r8 = r7.getJSONArray(r8)     // Catch:{ JSONException -> 0x0088 }
            goto L_0x0096
        L_0x0088:
            com.indra.httpclient.json.JSONArray r8 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x02ec }
            r8.<init>()     // Catch:{ JSONException -> 0x02ec }
            java.lang.String r9 = "cuenta"
            com.indra.httpclient.json.JSONObject r9 = r7.getJSONObject(r9)     // Catch:{ JSONException -> 0x02ec }
            r8.put(r9)     // Catch:{ JSONException -> 0x02ec }
        L_0x0096:
            r9 = 0
        L_0x0097:
            int r10 = r8.length()     // Catch:{ JSONException -> 0x02ec }
            if (r9 >= r10) goto L_0x012e
            java.lang.Object r10 = r8.get(r9)     // Catch:{ JSONException -> 0x02ec }
            com.indra.httpclient.json.JSONObject r10 = (com.indra.httpclient.json.JSONObject) r10     // Catch:{ JSONException -> 0x02ec }
            java.lang.String r11 = "productos"
            boolean r10 = r10.has(r11)     // Catch:{ JSONException -> 0x02ec }
            if (r10 == 0) goto L_0x00b8
            java.lang.Object r10 = r8.get(r9)     // Catch:{ JSONException -> 0x02ec }
            com.indra.httpclient.json.JSONObject r10 = (com.indra.httpclient.json.JSONObject) r10     // Catch:{ JSONException -> 0x02ec }
            java.lang.String r11 = "productos"
            com.indra.httpclient.json.JSONObject r10 = r10.getJSONObject(r11)     // Catch:{ JSONException -> 0x02ec }
            goto L_0x00b9
        L_0x00b8:
            r10 = r5
        L_0x00b9:
            java.lang.Object r11 = r8.get(r9)     // Catch:{ JSONException -> 0x02ec }
            com.indra.httpclient.json.JSONObject r11 = (com.indra.httpclient.json.JSONObject) r11     // Catch:{ JSONException -> 0x02ec }
            java.lang.String r12 = "tenReexpresada"
            boolean r11 = r11.has(r12)     // Catch:{ JSONException -> 0x02ec }
            if (r11 == 0) goto L_0x00d4
            java.lang.Object r11 = r8.get(r9)     // Catch:{ JSONException -> 0x02ec }
            com.indra.httpclient.json.JSONObject r11 = (com.indra.httpclient.json.JSONObject) r11     // Catch:{ JSONException -> 0x02ec }
            java.lang.String r12 = "tenReexpresada"
            com.indra.httpclient.json.JSONObject r11 = r11.getJSONObject(r12)     // Catch:{ JSONException -> 0x02ec }
            goto L_0x00d5
        L_0x00d4:
            r11 = r5
        L_0x00d5:
            if (r10 == 0) goto L_0x00fe
            java.lang.String r12 = "producto"
            boolean r12 = r10.has(r12)     // Catch:{ JSONException -> 0x02ec }
            if (r12 == 0) goto L_0x00fe
            java.lang.String r12 = "producto"
            com.indra.httpclient.json.JSONArray r12 = r10.getJSONArray(r12)     // Catch:{ JSONException -> 0x00e6 }
            goto L_0x00f4
        L_0x00e6:
            com.indra.httpclient.json.JSONArray r12 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x02ec }
            r12.<init>()     // Catch:{ JSONException -> 0x02ec }
            java.lang.String r13 = "producto"
            com.indra.httpclient.json.JSONObject r13 = r10.getJSONObject(r13)     // Catch:{ JSONException -> 0x02ec }
            r12.put(r13)     // Catch:{ JSONException -> 0x02ec }
        L_0x00f4:
            java.lang.String r13 = "producto"
            r10.remove(r13)     // Catch:{ JSONException -> 0x02ec }
            java.lang.String r13 = "producto"
            r10.put(r13, r12)     // Catch:{ JSONException -> 0x02ec }
        L_0x00fe:
            if (r11 == 0) goto L_0x012a
            java.lang.String r10 = "pesos"
            boolean r10 = r11.has(r10)     // Catch:{ JSONException -> 0x02ec }
            if (r10 == 0) goto L_0x010f
            java.lang.String r10 = "pesos"
            com.indra.httpclient.json.JSONObject r10 = r11.getJSONObject(r10)     // Catch:{ JSONException -> 0x02ec }
            goto L_0x0110
        L_0x010f:
            r10 = r5
        L_0x0110:
            java.lang.String r12 = "dolar"
            boolean r12 = r11.has(r12)     // Catch:{ JSONException -> 0x02ec }
            if (r12 == 0) goto L_0x011f
            java.lang.String r12 = "dolar"
            com.indra.httpclient.json.JSONObject r12 = r11.getJSONObject(r12)     // Catch:{ JSONException -> 0x02ec }
            goto L_0x0120
        L_0x011f:
            r12 = r5
        L_0x0120:
            java.lang.String r13 = "pesos"
            r11.put(r13, r10)     // Catch:{ JSONException -> 0x02ec }
            java.lang.String r10 = "dolar"
            r11.put(r10, r12)     // Catch:{ JSONException -> 0x02ec }
        L_0x012a:
            int r9 = r9 + 1
            goto L_0x0097
        L_0x012e:
            java.lang.String r9 = "cuenta"
            r7.put(r9, r8)     // Catch:{ JSONException -> 0x02ec }
        L_0x0133:
            java.lang.String r8 = "agrupadorCuenta"
            r6.put(r8, r7)     // Catch:{ JSONException -> 0x02ec }
        L_0x0138:
            java.lang.String r7 = "bancaPrivada"
            r2.put(r7, r6)     // Catch:{ JSONException -> 0x02ec }
        L_0x013d:
            if (r2 == 0) goto L_0x0174
            java.lang.String r6 = "listaLeyendas"
            boolean r6 = r2.has(r6)     // Catch:{ JSONException -> 0x02ec }
            if (r6 == 0) goto L_0x0174
            java.lang.String r6 = "listaLeyendas"
            com.indra.httpclient.json.JSONObject r6 = r2.getJSONObject(r6)     // Catch:{ JSONException -> 0x02ec }
            java.lang.String r7 = "leyenda"
            boolean r7 = r6.has(r7)     // Catch:{ JSONException -> 0x02ec }
            if (r7 == 0) goto L_0x016f
            java.lang.String r7 = "leyenda"
            com.indra.httpclient.json.JSONArray r7 = r6.getJSONArray(r7)     // Catch:{ JSONException -> 0x015c }
            goto L_0x016a
        L_0x015c:
            com.indra.httpclient.json.JSONArray r7 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x02ec }
            r7.<init>()     // Catch:{ JSONException -> 0x02ec }
            java.lang.String r8 = "leyenda"
            com.indra.httpclient.json.JSONObject r8 = r6.getJSONObject(r8)     // Catch:{ JSONException -> 0x02ec }
            r7.put(r8)     // Catch:{ JSONException -> 0x02ec }
        L_0x016a:
            java.lang.String r8 = "leyenda"
            r6.put(r8, r7)     // Catch:{ JSONException -> 0x02ec }
        L_0x016f:
            java.lang.String r7 = "listaLeyendas"
            r2.put(r7, r6)     // Catch:{ JSONException -> 0x02ec }
        L_0x0174:
            if (r2 == 0) goto L_0x0296
            java.lang.String r6 = "individuos"
            boolean r6 = r2.has(r6)     // Catch:{ JSONException -> 0x02ec }
            if (r6 == 0) goto L_0x0296
            java.lang.String r6 = "individuos"
            java.lang.Object r6 = r2.get(r6)     // Catch:{ JSONException -> 0x02ec }
            com.indra.httpclient.json.JSONObject r6 = (com.indra.httpclient.json.JSONObject) r6     // Catch:{ JSONException -> 0x02ec }
            if (r6 == 0) goto L_0x019d
            java.lang.String r7 = "tenConsolidada"
            boolean r7 = r6.has(r7)     // Catch:{ JSONException -> 0x02ec }
            if (r7 == 0) goto L_0x019d
            java.lang.String r7 = "tenConsolidada"
            java.lang.Object r7 = r6.get(r7)     // Catch:{ JSONException -> 0x02ec }
            com.indra.httpclient.json.JSONObject r7 = (com.indra.httpclient.json.JSONObject) r7     // Catch:{ JSONException -> 0x02ec }
            java.lang.String r8 = "tenConsolidada"
            r6.put(r8, r7)     // Catch:{ JSONException -> 0x02ec }
        L_0x019d:
            if (r6 == 0) goto L_0x01b4
            java.lang.String r7 = "plazoFijo"
            boolean r7 = r6.has(r7)     // Catch:{ JSONException -> 0x02ec }
            if (r7 == 0) goto L_0x01b4
            java.lang.String r7 = "plazoFijo"
            java.lang.Object r7 = r6.get(r7)     // Catch:{ JSONException -> 0x02ec }
            com.indra.httpclient.json.JSONObject r7 = (com.indra.httpclient.json.JSONObject) r7     // Catch:{ JSONException -> 0x02ec }
            java.lang.String r8 = "plazoFijo"
            r6.put(r8, r7)     // Catch:{ JSONException -> 0x02ec }
        L_0x01b4:
            if (r6 == 0) goto L_0x0291
            java.lang.String r7 = "agrupadorCuenta"
            boolean r7 = r6.has(r7)     // Catch:{ JSONException -> 0x02ec }
            if (r7 == 0) goto L_0x0291
            java.lang.String r7 = "agrupadorCuenta"
            java.lang.Object r7 = r6.get(r7)     // Catch:{ JSONException -> 0x02ec }
            com.indra.httpclient.json.JSONObject r7 = (com.indra.httpclient.json.JSONObject) r7     // Catch:{ JSONException -> 0x02ec }
            if (r7 == 0) goto L_0x028c
            java.lang.String r8 = "cuenta"
            boolean r8 = r7.has(r8)     // Catch:{ JSONException -> 0x02ec }
            if (r8 == 0) goto L_0x028c
            java.lang.String r8 = "cuenta"
            com.indra.httpclient.json.JSONArray r8 = r7.getJSONArray(r8)     // Catch:{ JSONException -> 0x01d7 }
            goto L_0x01e5
        L_0x01d7:
            com.indra.httpclient.json.JSONArray r8 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x02ec }
            r8.<init>()     // Catch:{ JSONException -> 0x02ec }
            java.lang.String r9 = "cuenta"
            com.indra.httpclient.json.JSONObject r9 = r7.getJSONObject(r9)     // Catch:{ JSONException -> 0x02ec }
            r8.put(r9)     // Catch:{ JSONException -> 0x02ec }
        L_0x01e5:
            int r9 = r8.length()     // Catch:{ JSONException -> 0x02ec }
            if (r4 >= r9) goto L_0x0287
            java.lang.Object r9 = r8.get(r4)     // Catch:{ JSONException -> 0x02ec }
            com.indra.httpclient.json.JSONObject r9 = (com.indra.httpclient.json.JSONObject) r9     // Catch:{ JSONException -> 0x02ec }
            java.lang.String r10 = "productos"
            boolean r9 = r9.has(r10)     // Catch:{ JSONException -> 0x02ec }
            if (r9 == 0) goto L_0x0206
            java.lang.Object r9 = r8.get(r4)     // Catch:{ JSONException -> 0x02ec }
            com.indra.httpclient.json.JSONObject r9 = (com.indra.httpclient.json.JSONObject) r9     // Catch:{ JSONException -> 0x02ec }
            java.lang.String r10 = "productos"
            com.indra.httpclient.json.JSONObject r9 = r9.getJSONObject(r10)     // Catch:{ JSONException -> 0x02ec }
            goto L_0x0207
        L_0x0206:
            r9 = r5
        L_0x0207:
            java.lang.Object r10 = r8.get(r4)     // Catch:{ JSONException -> 0x02ec }
            com.indra.httpclient.json.JSONObject r10 = (com.indra.httpclient.json.JSONObject) r10     // Catch:{ JSONException -> 0x02ec }
            java.lang.String r11 = "tenReexpresada"
            boolean r10 = r10.has(r11)     // Catch:{ JSONException -> 0x02ec }
            if (r10 == 0) goto L_0x0222
            java.lang.Object r10 = r8.get(r4)     // Catch:{ JSONException -> 0x02ec }
            com.indra.httpclient.json.JSONObject r10 = (com.indra.httpclient.json.JSONObject) r10     // Catch:{ JSONException -> 0x02ec }
            java.lang.String r11 = "tenReexpresada"
            com.indra.httpclient.json.JSONObject r10 = r10.getJSONObject(r11)     // Catch:{ JSONException -> 0x02ec }
            goto L_0x0223
        L_0x0222:
            r10 = r5
        L_0x0223:
            if (r9 == 0) goto L_0x024c
            java.lang.String r11 = "producto"
            boolean r11 = r9.has(r11)     // Catch:{ JSONException -> 0x02ec }
            if (r11 == 0) goto L_0x024c
            java.lang.String r11 = "producto"
            com.indra.httpclient.json.JSONArray r11 = r9.getJSONArray(r11)     // Catch:{ JSONException -> 0x0234 }
            goto L_0x0242
        L_0x0234:
            com.indra.httpclient.json.JSONArray r11 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x02ec }
            r11.<init>()     // Catch:{ JSONException -> 0x02ec }
            java.lang.String r12 = "producto"
            com.indra.httpclient.json.JSONObject r12 = r9.getJSONObject(r12)     // Catch:{ JSONException -> 0x02ec }
            r11.put(r12)     // Catch:{ JSONException -> 0x02ec }
        L_0x0242:
            java.lang.String r12 = "producto"
            r9.remove(r12)     // Catch:{ JSONException -> 0x02ec }
            java.lang.String r12 = "producto"
            r9.put(r12, r11)     // Catch:{ JSONException -> 0x02ec }
        L_0x024c:
            if (r10 == 0) goto L_0x0278
            java.lang.String r9 = "pesos"
            boolean r9 = r10.has(r9)     // Catch:{ JSONException -> 0x02ec }
            if (r9 == 0) goto L_0x025d
            java.lang.String r9 = "pesos"
            com.indra.httpclient.json.JSONObject r9 = r10.getJSONObject(r9)     // Catch:{ JSONException -> 0x02ec }
            goto L_0x025e
        L_0x025d:
            r9 = r5
        L_0x025e:
            java.lang.String r11 = "dolar"
            boolean r11 = r10.has(r11)     // Catch:{ JSONException -> 0x02ec }
            if (r11 == 0) goto L_0x026d
            java.lang.String r11 = "dolar"
            com.indra.httpclient.json.JSONObject r11 = r10.getJSONObject(r11)     // Catch:{ JSONException -> 0x02ec }
            goto L_0x026e
        L_0x026d:
            r11 = r5
        L_0x026e:
            java.lang.String r12 = "pesos"
            r10.put(r12, r9)     // Catch:{ JSONException -> 0x02ec }
            java.lang.String r9 = "dolar"
            r10.put(r9, r11)     // Catch:{ JSONException -> 0x02ec }
        L_0x0278:
            java.lang.Object r9 = r8.get(r4)     // Catch:{ JSONException -> 0x02ec }
            com.indra.httpclient.json.JSONObject r9 = (com.indra.httpclient.json.JSONObject) r9     // Catch:{ JSONException -> 0x02ec }
            java.lang.String r11 = "tenReexpresada"
            r9.put(r11, r10)     // Catch:{ JSONException -> 0x02ec }
            int r4 = r4 + 1
            goto L_0x01e5
        L_0x0287:
            java.lang.String r4 = "cuenta"
            r7.put(r4, r8)     // Catch:{ JSONException -> 0x02ec }
        L_0x028c:
            java.lang.String r4 = "agrupadorCuenta"
            r6.put(r4, r7)     // Catch:{ JSONException -> 0x02ec }
        L_0x0291:
            java.lang.String r4 = "individuos"
            r2.put(r4, r6)     // Catch:{ JSONException -> 0x02ec }
        L_0x0296:
            if (r2 == 0) goto L_0x02cd
            java.lang.String r4 = "listaLeyendas"
            boolean r4 = r2.has(r4)     // Catch:{ JSONException -> 0x02ec }
            if (r4 == 0) goto L_0x02cd
            java.lang.String r4 = "listaLeyendas"
            com.indra.httpclient.json.JSONObject r4 = r2.getJSONObject(r4)     // Catch:{ JSONException -> 0x02ec }
            java.lang.String r5 = "leyenda"
            boolean r5 = r4.has(r5)     // Catch:{ JSONException -> 0x02ec }
            if (r5 == 0) goto L_0x02c8
            java.lang.String r5 = "leyenda"
            com.indra.httpclient.json.JSONArray r5 = r4.getJSONArray(r5)     // Catch:{ JSONException -> 0x02b5 }
            goto L_0x02c3
        L_0x02b5:
            com.indra.httpclient.json.JSONArray r5 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x02ec }
            r5.<init>()     // Catch:{ JSONException -> 0x02ec }
            java.lang.String r6 = "leyenda"
            com.indra.httpclient.json.JSONObject r6 = r4.getJSONObject(r6)     // Catch:{ JSONException -> 0x02ec }
            r5.put(r6)     // Catch:{ JSONException -> 0x02ec }
        L_0x02c3:
            java.lang.String r6 = "leyenda"
            r4.put(r6, r5)     // Catch:{ JSONException -> 0x02ec }
        L_0x02c8:
            java.lang.String r5 = "listaLeyendas"
            r2.put(r5, r4)     // Catch:{ JSONException -> 0x02ec }
        L_0x02cd:
            java.lang.String r4 = "body"
            r15.put(r4, r2)     // Catch:{ JSONException -> 0x02ec }
            com.google.gson.Gson r15 = new com.google.gson.Gson     // Catch:{ JSONException -> 0x02ec }
            r15.<init>()     // Catch:{ JSONException -> 0x02ec }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ JSONException -> 0x02ec }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.body.TenenciaInversionesBodyResponseBean> r4 = ar.com.santander.rio.mbanking.services.soap.beans.body.TenenciaInversionesBodyResponseBean.class
            java.lang.Object r15 = r15.fromJson(r2, r4)     // Catch:{ JSONException -> 0x02ec }
            ar.com.santander.rio.mbanking.services.soap.beans.body.TenenciaInversionesBodyResponseBean r15 = (ar.com.santander.rio.mbanking.services.soap.beans.body.TenenciaInversionesBodyResponseBean) r15     // Catch:{ JSONException -> 0x02ec }
            r3.setTenenciaInversionesBodyResponseBean(r15)     // Catch:{ JSONException -> 0x02ec }
            r3.header = r1     // Catch:{ JSONException -> 0x02ec }
            r14.onResponseBean(r3)     // Catch:{ JSONException -> 0x02ec }
            goto L_0x02f0
        L_0x02ec:
            r15 = move-exception
            r14.onUnknowError(r15)
        L_0x02f0:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.services.soap.beans.body.TenenciaInversionesRequest.parserResponse(com.indra.httpclient.json.JSONObject):boolean");
    }
}
