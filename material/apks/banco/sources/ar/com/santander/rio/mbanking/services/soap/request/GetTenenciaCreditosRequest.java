package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.services.soap.beans.GetTenenciaCreditosRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetTenenciaCreditosResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AsistenciaSeguroBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatoSeguroBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;

public abstract class GetTenenciaCreditosRequest extends BaseRequest implements IBeanRequestWS {
    private GetTenenciaCreditosResponseBean mGetTenenciaCreditosBodyRequestBean;
    private GetTenenciaCreditosRequestBean mGetTenencisCreditosRequestBean;
    private GetTenenciaCreditosResponseBean responseBean;

    public int getMethod() {
        return 1;
    }

    public GetTenenciaCreditosRequest(Context context, GetTenenciaCreditosRequestBean getTenenciaCreditosRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, false);
        this.mGetTenencisCreditosRequestBean = getTenenciaCreditosRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public GetTenenciaCreditosRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
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

    public IBeanWS getBeanToRequest() {
        return this.mGetTenencisCreditosRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mGetTenenciaCreditosBodyRequestBean == null) {
            this.mGetTenenciaCreditosBodyRequestBean = new GetTenenciaCreditosResponseBean();
        }
        return this.mGetTenenciaCreditosBodyRequestBean.getClass();
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(2:12|13) */
    /* JADX WARNING: Can't wrap try/catch for region: R(2:70|71) */
    /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
        r10 = new com.indra.httpclient.json.JSONArray();
        r10.put((java.lang.Object) r4.getJSONObject("listaLeyendas").getJSONObject("datosLeyenda"));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
        r11 = new com.indra.httpclient.json.JSONArray();
        r11.put((java.lang.Object) r4.getJSONObject("listaCtaOperativa").getJSONObject("cuentaOperativa"));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:?, code lost:
        r12 = new com.indra.httpclient.json.JSONArray();
        r12.put((java.lang.Object) r4.getJSONObject("listaCategoriasCreditos").getJSONObject("categoriaCredito"));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:?, code lost:
        r9 = new com.indra.httpclient.json.JSONArray();
        r16 = r12;
        r9.put((java.lang.Object) r14.getJSONObject("listaCreditos"));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:?, code lost:
        r2 = new com.indra.httpclient.json.JSONArray();
        r19 = r9;
        r2.put((java.lang.Object) ((com.indra.httpclient.json.JSONObject) r9.get(r12)).getJSONObject("datosCredito"));
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:12:0x0072 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:28:0x00da */
    /* JADX WARNING: Missing exception handler attribute for start block: B:42:0x0134 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:53:0x0181 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:70:0x01b7 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean parserResponse(com.indra.httpclient.json.JSONObject r22) {
        /*
            r21 = this;
            r1 = r21
            boolean r2 = super.parserResponse(r22)
            if (r2 == 0) goto L_0x0241
            com.google.gson.Gson r3 = new com.google.gson.Gson
            r3.<init>()
            java.lang.String r4 = "soapenv:Envelope"
            r5 = r22
            com.indra.httpclient.json.JSONObject r4 = r5.getJSONObject(r4)     // Catch:{ JSONException -> 0x0239 }
            java.lang.String r5 = "soapenv:Body"
            com.indra.httpclient.json.JSONObject r4 = r4.getJSONObject(r5)     // Catch:{ JSONException -> 0x0239 }
            java.lang.String r5 = "xml"
            com.indra.httpclient.json.JSONObject r4 = r4.getJSONObject(r5)     // Catch:{ JSONException -> 0x0239 }
            ar.com.santander.rio.mbanking.services.soap.beans.GetTenenciaCreditosResponseBean r5 = new ar.com.santander.rio.mbanking.services.soap.beans.GetTenenciaCreditosResponseBean     // Catch:{ JSONException -> 0x0239 }
            r5.<init>()     // Catch:{ JSONException -> 0x0239 }
            r1.responseBean = r5     // Catch:{ JSONException -> 0x0239 }
            java.lang.String r5 = "header"
            com.indra.httpclient.json.JSONObject r5 = r4.getJSONObject(r5)     // Catch:{ JSONException -> 0x0239 }
            java.lang.String r6 = "body"
            com.indra.httpclient.json.JSONObject r4 = r4.getJSONObject(r6)     // Catch:{ JSONException -> 0x0239 }
            java.lang.String r5 = r5.toString()     // Catch:{ JSONException -> 0x0239 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean> r6 = ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean.class
            java.lang.Object r5 = r3.fromJson(r5, r6)     // Catch:{ JSONException -> 0x0239 }
            ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean r5 = (ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean) r5     // Catch:{ JSONException -> 0x0239 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.GetTenenciaCreditosBodyResponseBean r6 = new ar.com.santander.rio.mbanking.services.soap.beans.body.GetTenenciaCreditosBodyResponseBean     // Catch:{ JSONException -> 0x0239 }
            r6.<init>()     // Catch:{ JSONException -> 0x0239 }
            ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.Listaleyendas r7 = new ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.Listaleyendas     // Catch:{ JSONException -> 0x0239 }
            r7.<init>()     // Catch:{ JSONException -> 0x0239 }
            java.lang.String r8 = "listaLeyendas"
            boolean r8 = r4.has(r8)     // Catch:{ JSONException -> 0x0239 }
            if (r8 == 0) goto L_0x00a8
            java.lang.String r8 = "listaLeyendas"
            com.indra.httpclient.json.JSONObject r8 = r4.getJSONObject(r8)     // Catch:{ JSONException -> 0x0239 }
            java.lang.String r10 = "datosLeyenda"
            boolean r8 = r8.has(r10)     // Catch:{ JSONException -> 0x0239 }
            if (r8 == 0) goto L_0x00a8
            java.util.ArrayList r8 = new java.util.ArrayList     // Catch:{ JSONException -> 0x0239 }
            r8.<init>()     // Catch:{ JSONException -> 0x0239 }
            java.lang.String r10 = "listaLeyendas"
            com.indra.httpclient.json.JSONObject r10 = r4.getJSONObject(r10)     // Catch:{ JSONException -> 0x0072 }
            java.lang.String r11 = "datosLeyenda"
            com.indra.httpclient.json.JSONArray r10 = r10.getJSONArray(r11)     // Catch:{ JSONException -> 0x0072 }
            goto L_0x0086
        L_0x0072:
            com.indra.httpclient.json.JSONArray r10 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x0239 }
            r10.<init>()     // Catch:{ JSONException -> 0x0239 }
            java.lang.String r11 = "listaLeyendas"
            com.indra.httpclient.json.JSONObject r11 = r4.getJSONObject(r11)     // Catch:{ JSONException -> 0x0239 }
            java.lang.String r12 = "datosLeyenda"
            com.indra.httpclient.json.JSONObject r11 = r11.getJSONObject(r12)     // Catch:{ JSONException -> 0x0239 }
            r10.put(r11)     // Catch:{ JSONException -> 0x0239 }
        L_0x0086:
            if (r10 == 0) goto L_0x00a8
            r11 = 0
        L_0x0089:
            int r12 = r10.length()     // Catch:{ JSONException -> 0x0239 }
            if (r11 >= r12) goto L_0x00a5
            com.indra.httpclient.json.JSONObject r12 = r10.getJSONObject(r11)     // Catch:{ JSONException -> 0x0239 }
            java.lang.String r12 = java.lang.String.valueOf(r12)     // Catch:{ JSONException -> 0x0239 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.DatosLeyenda> r13 = ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.DatosLeyenda.class
            java.lang.Object r12 = r3.fromJson(r12, r13)     // Catch:{ JSONException -> 0x0239 }
            ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.DatosLeyenda r12 = (ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.DatosLeyenda) r12     // Catch:{ JSONException -> 0x0239 }
            r8.add(r12)     // Catch:{ JSONException -> 0x0239 }
            int r11 = r11 + 1
            goto L_0x0089
        L_0x00a5:
            r7.setDatosleyenda(r8)     // Catch:{ JSONException -> 0x0239 }
        L_0x00a8:
            ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.ListaCtaOperativa r8 = new ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.ListaCtaOperativa     // Catch:{ JSONException -> 0x0239 }
            r8.<init>()     // Catch:{ JSONException -> 0x0239 }
            java.lang.String r10 = "listaCtaOperativa"
            boolean r10 = r4.has(r10)     // Catch:{ JSONException -> 0x0239 }
            if (r10 == 0) goto L_0x0110
            java.lang.String r10 = "listaCtaOperativa"
            com.indra.httpclient.json.JSONObject r10 = r4.getJSONObject(r10)     // Catch:{ JSONException -> 0x0239 }
            java.lang.String r11 = "cuentaOperativa"
            boolean r10 = r10.has(r11)     // Catch:{ JSONException -> 0x0239 }
            if (r10 == 0) goto L_0x0110
            java.util.ArrayList r10 = new java.util.ArrayList     // Catch:{ JSONException -> 0x0239 }
            r10.<init>()     // Catch:{ JSONException -> 0x0239 }
            java.util.ArrayList r10 = new java.util.ArrayList     // Catch:{ JSONException -> 0x0239 }
            r10.<init>()     // Catch:{ JSONException -> 0x0239 }
            java.lang.String r11 = "listaCtaOperativa"
            com.indra.httpclient.json.JSONObject r11 = r4.getJSONObject(r11)     // Catch:{ JSONException -> 0x00da }
            java.lang.String r12 = "cuentaOperativa"
            com.indra.httpclient.json.JSONArray r11 = r11.getJSONArray(r12)     // Catch:{ JSONException -> 0x00da }
            goto L_0x00ee
        L_0x00da:
            com.indra.httpclient.json.JSONArray r11 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x0239 }
            r11.<init>()     // Catch:{ JSONException -> 0x0239 }
            java.lang.String r12 = "listaCtaOperativa"
            com.indra.httpclient.json.JSONObject r12 = r4.getJSONObject(r12)     // Catch:{ JSONException -> 0x0239 }
            java.lang.String r13 = "cuentaOperativa"
            com.indra.httpclient.json.JSONObject r12 = r12.getJSONObject(r13)     // Catch:{ JSONException -> 0x0239 }
            r11.put(r12)     // Catch:{ JSONException -> 0x0239 }
        L_0x00ee:
            if (r11 == 0) goto L_0x0110
            r12 = 0
        L_0x00f1:
            int r13 = r11.length()     // Catch:{ JSONException -> 0x0239 }
            if (r12 >= r13) goto L_0x010d
            com.indra.httpclient.json.JSONObject r13 = r11.getJSONObject(r12)     // Catch:{ JSONException -> 0x0239 }
            java.lang.String r13 = java.lang.String.valueOf(r13)     // Catch:{ JSONException -> 0x0239 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.CuentaOperativa> r14 = ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.CuentaOperativa.class
            java.lang.Object r13 = r3.fromJson(r13, r14)     // Catch:{ JSONException -> 0x0239 }
            ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.CuentaOperativa r13 = (ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.CuentaOperativa) r13     // Catch:{ JSONException -> 0x0239 }
            r10.add(r13)     // Catch:{ JSONException -> 0x0239 }
            int r12 = r12 + 1
            goto L_0x00f1
        L_0x010d:
            r8.setCuentaoperativa(r10)     // Catch:{ JSONException -> 0x0239 }
        L_0x0110:
            ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.ListaCategoriasCreditos r10 = new ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.ListaCategoriasCreditos     // Catch:{ JSONException -> 0x0239 }
            r10.<init>()     // Catch:{ JSONException -> 0x0239 }
            java.lang.String r11 = "listaCategoriasCreditos"
            boolean r11 = r4.has(r11)     // Catch:{ JSONException -> 0x0239 }
            if (r11 == 0) goto L_0x0213
            java.util.ArrayList r11 = new java.util.ArrayList     // Catch:{ JSONException -> 0x0239 }
            r11.<init>()     // Catch:{ JSONException -> 0x0239 }
            com.indra.httpclient.json.JSONArray r12 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x0239 }
            r12.<init>()     // Catch:{ JSONException -> 0x0239 }
            java.lang.String r12 = "listaCategoriasCreditos"
            com.indra.httpclient.json.JSONObject r12 = r4.getJSONObject(r12)     // Catch:{ JSONException -> 0x0134 }
            java.lang.String r13 = "categoriaCredito"
            com.indra.httpclient.json.JSONArray r12 = r12.getJSONArray(r13)     // Catch:{ JSONException -> 0x0134 }
            goto L_0x0148
        L_0x0134:
            com.indra.httpclient.json.JSONArray r12 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x0239 }
            r12.<init>()     // Catch:{ JSONException -> 0x0239 }
            java.lang.String r13 = "listaCategoriasCreditos"
            com.indra.httpclient.json.JSONObject r4 = r4.getJSONObject(r13)     // Catch:{ JSONException -> 0x0239 }
            java.lang.String r13 = "categoriaCredito"
            com.indra.httpclient.json.JSONObject r4 = r4.getJSONObject(r13)     // Catch:{ JSONException -> 0x0239 }
            r12.put(r4)     // Catch:{ JSONException -> 0x0239 }
        L_0x0148:
            if (r12 == 0) goto L_0x0213
            r4 = 0
        L_0x014b:
            int r13 = r12.length()     // Catch:{ JSONException -> 0x0239 }
            if (r4 >= r13) goto L_0x0213
            ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.CategoriaCredito r13 = new ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.CategoriaCredito     // Catch:{ JSONException -> 0x0239 }
            r13.<init>()     // Catch:{ JSONException -> 0x0239 }
            java.lang.Object r14 = r12.get(r4)     // Catch:{ JSONException -> 0x0239 }
            com.indra.httpclient.json.JSONObject r14 = (com.indra.httpclient.json.JSONObject) r14     // Catch:{ JSONException -> 0x0239 }
            java.lang.String r15 = "descCategoriaCredito"
            java.lang.String r15 = r14.getString(r15)     // Catch:{ JSONException -> 0x0239 }
            r13.setDesccategoriacredito(r15)     // Catch:{ JSONException -> 0x0239 }
            java.lang.String r15 = "idCategoriaCredito"
            java.lang.String r15 = r14.getString(r15)     // Catch:{ JSONException -> 0x0239 }
            r13.setIdcategoriacredito(r15)     // Catch:{ JSONException -> 0x0239 }
            ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.ListaCreditos r15 = new ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.ListaCreditos     // Catch:{ JSONException -> 0x0239 }
            r15.<init>()     // Catch:{ JSONException -> 0x0239 }
            com.indra.httpclient.json.JSONArray r9 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x0239 }
            r9.<init>()     // Catch:{ JSONException -> 0x0239 }
            java.lang.String r9 = "listaCreditos"
            com.indra.httpclient.json.JSONArray r9 = r14.getJSONArray(r9)     // Catch:{ JSONException -> 0x0181 }
            r16 = r12
            goto L_0x0191
        L_0x0181:
            com.indra.httpclient.json.JSONArray r9 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x0239 }
            r9.<init>()     // Catch:{ JSONException -> 0x0239 }
            r16 = r12
            java.lang.String r12 = "listaCreditos"
            com.indra.httpclient.json.JSONObject r12 = r14.getJSONObject(r12)     // Catch:{ JSONException -> 0x0239 }
            r9.put(r12)     // Catch:{ JSONException -> 0x0239 }
        L_0x0191:
            r12 = 0
        L_0x0192:
            int r14 = r9.length()     // Catch:{ JSONException -> 0x0239 }
            if (r12 >= r14) goto L_0x01fc
            java.util.ArrayList r14 = new java.util.ArrayList     // Catch:{ JSONException -> 0x0239 }
            r14.<init>()     // Catch:{ JSONException -> 0x0239 }
            r17 = r2
            com.indra.httpclient.json.JSONArray r2 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x0237 }
            r2.<init>()     // Catch:{ JSONException -> 0x0237 }
            java.lang.Object r2 = r9.get(r12)     // Catch:{ JSONException -> 0x01b5 }
            com.indra.httpclient.json.JSONObject r2 = (com.indra.httpclient.json.JSONObject) r2     // Catch:{ JSONException -> 0x01b5 }
            r18 = r5
            java.lang.String r5 = "datosCredito"
            com.indra.httpclient.json.JSONArray r2 = r2.getJSONArray(r5)     // Catch:{ JSONException -> 0x01b7 }
            r19 = r9
            goto L_0x01cd
        L_0x01b5:
            r18 = r5
        L_0x01b7:
            com.indra.httpclient.json.JSONArray r2 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x0237 }
            r2.<init>()     // Catch:{ JSONException -> 0x0237 }
            java.lang.Object r5 = r9.get(r12)     // Catch:{ JSONException -> 0x0237 }
            com.indra.httpclient.json.JSONObject r5 = (com.indra.httpclient.json.JSONObject) r5     // Catch:{ JSONException -> 0x0237 }
            r19 = r9
            java.lang.String r9 = "datosCredito"
            com.indra.httpclient.json.JSONObject r5 = r5.getJSONObject(r9)     // Catch:{ JSONException -> 0x0237 }
            r2.put(r5)     // Catch:{ JSONException -> 0x0237 }
        L_0x01cd:
            r5 = 0
        L_0x01ce:
            int r9 = r2.length()     // Catch:{ JSONException -> 0x0237 }
            if (r5 >= r9) goto L_0x01f0
            java.lang.Object r9 = r2.get(r5)     // Catch:{ JSONException -> 0x0237 }
            com.indra.httpclient.json.JSONObject r9 = (com.indra.httpclient.json.JSONObject) r9     // Catch:{ JSONException -> 0x0237 }
            java.lang.String r9 = r9.toString()     // Catch:{ JSONException -> 0x0237 }
            r20 = r2
            java.lang.Class<ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.DatosCredito> r2 = ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.DatosCredito.class
            java.lang.Object r2 = r3.fromJson(r9, r2)     // Catch:{ JSONException -> 0x0237 }
            ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.DatosCredito r2 = (ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.DatosCredito) r2     // Catch:{ JSONException -> 0x0237 }
            r14.add(r2)     // Catch:{ JSONException -> 0x0237 }
            int r5 = r5 + 1
            r2 = r20
            goto L_0x01ce
        L_0x01f0:
            r15.setDatoscredito(r14)     // Catch:{ JSONException -> 0x0237 }
            int r12 = r12 + 1
            r2 = r17
            r5 = r18
            r9 = r19
            goto L_0x0192
        L_0x01fc:
            r17 = r2
            r18 = r5
            r13.setListacreditos(r15)     // Catch:{ JSONException -> 0x0237 }
            r11.add(r13)     // Catch:{ JSONException -> 0x0237 }
            r10.setCategoriacredito(r11)     // Catch:{ JSONException -> 0x0237 }
            int r4 = r4 + 1
            r12 = r16
            r2 = r17
            r5 = r18
            goto L_0x014b
        L_0x0213:
            r17 = r2
            r18 = r5
            r6.setListacategoriascreditos(r10)     // Catch:{ JSONException -> 0x0237 }
            r6.setListactaoperativa(r8)     // Catch:{ JSONException -> 0x0237 }
            r6.setListaleyendas(r7)     // Catch:{ JSONException -> 0x0237 }
            java.lang.String r2 = "0"
            r6.setRes(r2)     // Catch:{ JSONException -> 0x0237 }
            ar.com.santander.rio.mbanking.services.soap.beans.GetTenenciaCreditosResponseBean r2 = r1.responseBean     // Catch:{ JSONException -> 0x0237 }
            r5 = r18
            r2.setHeaderBean(r5)     // Catch:{ JSONException -> 0x0237 }
            ar.com.santander.rio.mbanking.services.soap.beans.GetTenenciaCreditosResponseBean r2 = r1.responseBean     // Catch:{ JSONException -> 0x0237 }
            r2.setGetCategoriasBodyResponseBean(r6)     // Catch:{ JSONException -> 0x0237 }
            ar.com.santander.rio.mbanking.services.soap.beans.GetTenenciaCreditosResponseBean r2 = r1.responseBean     // Catch:{ JSONException -> 0x0237 }
            r1.onResponseBean(r2)     // Catch:{ JSONException -> 0x0237 }
            goto L_0x0243
        L_0x0237:
            r0 = move-exception
            goto L_0x023c
        L_0x0239:
            r0 = move-exception
            r17 = r2
        L_0x023c:
            r2 = r0
            r2.printStackTrace()
            goto L_0x0243
        L_0x0241:
            r17 = r2
        L_0x0243:
            return r17
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.services.soap.request.GetTenenciaCreditosRequest.parserResponse(com.indra.httpclient.json.JSONObject):boolean");
    }

    private DatoSeguroBean getDatoSeguroObject(Gson gson, JSONObject jSONObject) {
        DatoSeguroBean datoSeguroBean = new DatoSeguroBean();
        try {
            datoSeguroBean.setDesc(jSONObject.has(TarjetasConstants.DESC) ? jSONObject.getString(TarjetasConstants.DESC) : "");
            datoSeguroBean.setValor(jSONObject.has("valor") ? jSONObject.getString("valor") : "");
        } catch (JSONException e) {
            onUnknowError(e);
        }
        return datoSeguroBean;
    }

    private AsistenciaSeguroBean getAsistenciaSeguroObject(Gson gson, JSONObject jSONObject) {
        AsistenciaSeguroBean asistenciaSeguroBean = new AsistenciaSeguroBean();
        try {
            asistenciaSeguroBean.setDesc(jSONObject.has(TarjetasConstants.DESC) ? jSONObject.getString(TarjetasConstants.DESC) : "");
            asistenciaSeguroBean.setTel(jSONObject.has("tel") ? jSONObject.getString("tel") : "");
        } catch (JSONException e) {
            onUnknowError(e);
        }
        return asistenciaSeguroBean;
    }
}
