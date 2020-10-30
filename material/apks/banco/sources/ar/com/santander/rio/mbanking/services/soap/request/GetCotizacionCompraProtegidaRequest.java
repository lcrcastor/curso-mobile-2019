package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.services.soap.beans.GetCotizacionCompraProtegidaRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetCotizacionCompraProtegidaResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LeyendaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PlanSeguroBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;

public abstract class GetCotizacionCompraProtegidaRequest extends BaseRequest implements IBeanRequestWS {
    private GetCotizacionCompraProtegidaRequestBean getCotizacionCompraProtegidaRequestBean;
    private GetCotizacionCompraProtegidaResponseBean getCotizacionCompraProtegidaResponseBean;

    public int getMethod() {
        return 1;
    }

    public GetCotizacionCompraProtegidaRequest(Context context, GetCotizacionCompraProtegidaRequestBean getCotizacionCompraProtegidaRequestBean2, ErrorRequestServer errorRequestServer) {
        super(context);
        this.getCotizacionCompraProtegidaRequestBean = getCotizacionCompraProtegidaRequestBean2;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.getCotizacionCompraProtegidaRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.getCotizacionCompraProtegidaResponseBean == null) {
            this.getCotizacionCompraProtegidaResponseBean = new GetCotizacionCompraProtegidaResponseBean();
        }
        return this.getCotizacionCompraProtegidaResponseBean.getClass();
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(2:24|25) */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        r6 = new com.indra.httpclient.json.JSONArray();
        r6.put((java.lang.Object) r5.getJSONObject("plan"));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        r5 = new com.indra.httpclient.json.JSONArray();
        r5.put((java.lang.Object) r15.getJSONObject("leyenda"));
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:24:0x00a9 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:38:0x0136 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean parserResponse(com.indra.httpclient.json.JSONObject r15) {
        /*
            r14 = this;
            boolean r0 = super.parserResponse(r15)
            if (r0 == 0) goto L_0x01bc
            com.google.gson.Gson r1 = new com.google.gson.Gson
            r1.<init>()
            java.lang.String r2 = "soapenv:Envelope"
            com.indra.httpclient.json.JSONObject r15 = r15.getJSONObject(r2)     // Catch:{ JSONException -> 0x01b8 }
            java.lang.String r2 = "soapenv:Body"
            com.indra.httpclient.json.JSONObject r15 = r15.getJSONObject(r2)     // Catch:{ JSONException -> 0x01b8 }
            java.lang.String r2 = "xml"
            com.indra.httpclient.json.JSONObject r15 = r15.getJSONObject(r2)     // Catch:{ JSONException -> 0x01b8 }
            ar.com.santander.rio.mbanking.services.soap.beans.GetCotizacionCompraProtegidaResponseBean r2 = new ar.com.santander.rio.mbanking.services.soap.beans.GetCotizacionCompraProtegidaResponseBean     // Catch:{ JSONException -> 0x01b8 }
            r2.<init>()     // Catch:{ JSONException -> 0x01b8 }
            java.lang.String r3 = "header"
            com.indra.httpclient.json.JSONObject r3 = r15.getJSONObject(r3)     // Catch:{ JSONException -> 0x01b8 }
            java.lang.String r3 = r3.toString()     // Catch:{ JSONException -> 0x01b8 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean> r4 = ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean.class
            java.lang.Object r1 = r1.fromJson(r3, r4)     // Catch:{ JSONException -> 0x01b8 }
            ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean r1 = (ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean) r1     // Catch:{ JSONException -> 0x01b8 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionCompraProtegidaBodyResponseBean r3 = new ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionCompraProtegidaBodyResponseBean     // Catch:{ JSONException -> 0x01b8 }
            r3.<init>()     // Catch:{ JSONException -> 0x01b8 }
            java.lang.String r4 = "body"
            com.indra.httpclient.json.JSONObject r15 = r15.getJSONObject(r4)     // Catch:{ JSONException -> 0x01b8 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.CotizacionBean r4 = new ar.com.santander.rio.mbanking.services.soap.beans.body.CotizacionBean     // Catch:{ JSONException -> 0x01b8 }
            r4.<init>()     // Catch:{ JSONException -> 0x01b8 }
            java.lang.String r5 = "cotizacion"
            boolean r5 = r15.has(r5)     // Catch:{ JSONException -> 0x01b8 }
            if (r5 == 0) goto L_0x01b0
            java.lang.String r5 = "cotizacion"
            com.indra.httpclient.json.JSONObject r15 = r15.getJSONObject(r5)     // Catch:{ JSONException -> 0x01b8 }
            java.lang.String r5 = "codRamo"
            boolean r5 = r15.has(r5)     // Catch:{ JSONException -> 0x01b8 }
            if (r5 == 0) goto L_0x0061
            java.lang.String r5 = "codRamo"
            java.lang.String r5 = r15.getString(r5)     // Catch:{ JSONException -> 0x01b8 }
            goto L_0x0063
        L_0x0061:
            java.lang.String r5 = ""
        L_0x0063:
            r4.setCodRamo(r5)     // Catch:{ JSONException -> 0x01b8 }
            java.lang.String r5 = "codProducto"
            boolean r5 = r15.has(r5)     // Catch:{ JSONException -> 0x01b8 }
            if (r5 == 0) goto L_0x0075
            java.lang.String r5 = "codProducto"
            java.lang.String r5 = r15.getString(r5)     // Catch:{ JSONException -> 0x01b8 }
            goto L_0x0077
        L_0x0075:
            java.lang.String r5 = ""
        L_0x0077:
            r4.setCodProducto(r5)     // Catch:{ JSONException -> 0x01b8 }
            java.lang.String r5 = "numCotizacion"
            boolean r5 = r15.has(r5)     // Catch:{ JSONException -> 0x01b8 }
            if (r5 == 0) goto L_0x0089
            java.lang.String r5 = "numCotizacion"
            java.lang.String r5 = r15.getString(r5)     // Catch:{ JSONException -> 0x01b8 }
            goto L_0x008b
        L_0x0089:
            java.lang.String r5 = ""
        L_0x008b:
            r4.setNumCotizacion(r5)     // Catch:{ JSONException -> 0x01b8 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.PlanesSeguroBean r5 = new ar.com.santander.rio.mbanking.services.soap.beans.body.PlanesSeguroBean     // Catch:{ JSONException -> 0x01b8 }
            r5.<init>()     // Catch:{ JSONException -> 0x01b8 }
            java.lang.String r5 = "planes"
            com.indra.httpclient.json.JSONObject r5 = r15.getJSONObject(r5)     // Catch:{ JSONException -> 0x01b8 }
            java.lang.String r6 = "plan"
            boolean r6 = r5.has(r6)     // Catch:{ JSONException -> 0x01b8 }
            r7 = 0
            if (r6 == 0) goto L_0x0121
            java.lang.String r6 = "plan"
            com.indra.httpclient.json.JSONArray r6 = r5.getJSONArray(r6)     // Catch:{ JSONException -> 0x00a9 }
            goto L_0x00b7
        L_0x00a9:
            com.indra.httpclient.json.JSONArray r6 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x01b8 }
            r6.<init>()     // Catch:{ JSONException -> 0x01b8 }
            java.lang.String r8 = "plan"
            com.indra.httpclient.json.JSONObject r8 = r5.getJSONObject(r8)     // Catch:{ JSONException -> 0x01b8 }
            r6.put(r8)     // Catch:{ JSONException -> 0x01b8 }
        L_0x00b7:
            com.indra.httpclient.json.JSONArray r8 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x01b8 }
            r8.<init>()     // Catch:{ JSONException -> 0x01b8 }
            r9 = 0
        L_0x00bd:
            int r10 = r6.length()     // Catch:{ JSONException -> 0x01b8 }
            if (r9 >= r10) goto L_0x0101
            com.indra.httpclient.json.JSONObject r10 = new com.indra.httpclient.json.JSONObject     // Catch:{ JSONException -> 0x01b8 }
            r10.<init>()     // Catch:{ JSONException -> 0x01b8 }
            java.lang.String r11 = "sumaAsegurada"
            java.lang.Object r12 = r6.get(r9)     // Catch:{ JSONException -> 0x01b8 }
            com.indra.httpclient.json.JSONObject r12 = (com.indra.httpclient.json.JSONObject) r12     // Catch:{ JSONException -> 0x01b8 }
            java.lang.String r13 = "sumaAsegurada"
            java.lang.String r12 = r12.getString(r13)     // Catch:{ JSONException -> 0x01b8 }
            r10.put(r11, r12)     // Catch:{ JSONException -> 0x01b8 }
            java.lang.String r11 = "cantTarjetas"
            java.lang.Object r12 = r6.get(r9)     // Catch:{ JSONException -> 0x01b8 }
            com.indra.httpclient.json.JSONObject r12 = (com.indra.httpclient.json.JSONObject) r12     // Catch:{ JSONException -> 0x01b8 }
            java.lang.String r13 = "cantTarjetas"
            java.lang.String r12 = r12.getString(r13)     // Catch:{ JSONException -> 0x01b8 }
            r10.put(r11, r12)     // Catch:{ JSONException -> 0x01b8 }
            java.lang.String r11 = "cuota"
            java.lang.Object r12 = r6.get(r9)     // Catch:{ JSONException -> 0x01b8 }
            com.indra.httpclient.json.JSONObject r12 = (com.indra.httpclient.json.JSONObject) r12     // Catch:{ JSONException -> 0x01b8 }
            java.lang.String r13 = "cuota"
            java.lang.String r12 = r12.getString(r13)     // Catch:{ JSONException -> 0x01b8 }
            r10.put(r11, r12)     // Catch:{ JSONException -> 0x01b8 }
            r8.put(r10)     // Catch:{ JSONException -> 0x01b8 }
            int r9 = r9 + 1
            goto L_0x00bd
        L_0x0101:
            if (r5 == 0) goto L_0x010d
            java.lang.String r6 = "plan"
            r5.remove(r6)     // Catch:{ JSONException -> 0x01b8 }
            java.lang.String r6 = "plan"
            r5.put(r6, r8)     // Catch:{ JSONException -> 0x01b8 }
        L_0x010d:
            com.google.gson.Gson r6 = new com.google.gson.Gson     // Catch:{ JSONException -> 0x01b8 }
            r6.<init>()     // Catch:{ JSONException -> 0x01b8 }
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ JSONException -> 0x01b8 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.body.PlanesSeguroBean> r8 = ar.com.santander.rio.mbanking.services.soap.beans.body.PlanesSeguroBean.class
            java.lang.Object r5 = r6.fromJson(r5, r8)     // Catch:{ JSONException -> 0x01b8 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.PlanesSeguroBean r5 = (ar.com.santander.rio.mbanking.services.soap.beans.body.PlanesSeguroBean) r5     // Catch:{ JSONException -> 0x01b8 }
            r4.setPlanes(r5)     // Catch:{ JSONException -> 0x01b8 }
        L_0x0121:
            java.lang.String r5 = "listaLeyendas"
            com.indra.httpclient.json.JSONObject r15 = r15.getJSONObject(r5)     // Catch:{ JSONException -> 0x01b8 }
            java.lang.String r5 = "leyenda"
            boolean r5 = r15.has(r5)     // Catch:{ JSONException -> 0x01b8 }
            if (r5 == 0) goto L_0x01ad
            java.lang.String r5 = "leyenda"
            com.indra.httpclient.json.JSONArray r5 = r15.getJSONArray(r5)     // Catch:{ JSONException -> 0x0136 }
            goto L_0x0144
        L_0x0136:
            com.indra.httpclient.json.JSONArray r5 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x01b8 }
            r5.<init>()     // Catch:{ JSONException -> 0x01b8 }
            java.lang.String r6 = "leyenda"
            com.indra.httpclient.json.JSONObject r6 = r15.getJSONObject(r6)     // Catch:{ JSONException -> 0x01b8 }
            r5.put(r6)     // Catch:{ JSONException -> 0x01b8 }
        L_0x0144:
            com.indra.httpclient.json.JSONArray r6 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x01b8 }
            r6.<init>()     // Catch:{ JSONException -> 0x01b8 }
        L_0x0149:
            int r8 = r5.length()     // Catch:{ JSONException -> 0x01b8 }
            if (r7 >= r8) goto L_0x018d
            com.indra.httpclient.json.JSONObject r8 = new com.indra.httpclient.json.JSONObject     // Catch:{ JSONException -> 0x01b8 }
            r8.<init>()     // Catch:{ JSONException -> 0x01b8 }
            java.lang.String r9 = "descripcion"
            java.lang.Object r10 = r5.get(r7)     // Catch:{ JSONException -> 0x01b8 }
            com.indra.httpclient.json.JSONObject r10 = (com.indra.httpclient.json.JSONObject) r10     // Catch:{ JSONException -> 0x01b8 }
            java.lang.String r11 = "descripcion"
            java.lang.String r10 = r10.getString(r11)     // Catch:{ JSONException -> 0x01b8 }
            r8.put(r9, r10)     // Catch:{ JSONException -> 0x01b8 }
            java.lang.String r9 = "idLeyenda"
            java.lang.Object r10 = r5.get(r7)     // Catch:{ JSONException -> 0x01b8 }
            com.indra.httpclient.json.JSONObject r10 = (com.indra.httpclient.json.JSONObject) r10     // Catch:{ JSONException -> 0x01b8 }
            java.lang.String r11 = "idLeyenda"
            java.lang.String r10 = r10.getString(r11)     // Catch:{ JSONException -> 0x01b8 }
            r8.put(r9, r10)     // Catch:{ JSONException -> 0x01b8 }
            java.lang.String r9 = "titulo"
            java.lang.Object r10 = r5.get(r7)     // Catch:{ JSONException -> 0x01b8 }
            com.indra.httpclient.json.JSONObject r10 = (com.indra.httpclient.json.JSONObject) r10     // Catch:{ JSONException -> 0x01b8 }
            java.lang.String r11 = "titulo"
            java.lang.String r10 = r10.getString(r11)     // Catch:{ JSONException -> 0x01b8 }
            r8.put(r9, r10)     // Catch:{ JSONException -> 0x01b8 }
            r6.put(r8)     // Catch:{ JSONException -> 0x01b8 }
            int r7 = r7 + 1
            goto L_0x0149
        L_0x018d:
            if (r15 == 0) goto L_0x0199
            java.lang.String r5 = "leyenda"
            r15.remove(r5)     // Catch:{ JSONException -> 0x01b8 }
            java.lang.String r5 = "leyenda"
            r15.put(r5, r6)     // Catch:{ JSONException -> 0x01b8 }
        L_0x0199:
            com.google.gson.Gson r5 = new com.google.gson.Gson     // Catch:{ JSONException -> 0x01b8 }
            r5.<init>()     // Catch:{ JSONException -> 0x01b8 }
            java.lang.String r15 = java.lang.String.valueOf(r15)     // Catch:{ JSONException -> 0x01b8 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.body.ListaLeyendaSeguroMovil> r6 = ar.com.santander.rio.mbanking.services.soap.beans.body.ListaLeyendaSeguroMovil.class
            java.lang.Object r15 = r5.fromJson(r15, r6)     // Catch:{ JSONException -> 0x01b8 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.ListaLeyendaSeguroMovil r15 = (ar.com.santander.rio.mbanking.services.soap.beans.body.ListaLeyendaSeguroMovil) r15     // Catch:{ JSONException -> 0x01b8 }
            r4.setListaLeyendas(r15)     // Catch:{ JSONException -> 0x01b8 }
        L_0x01ad:
            r3.setCotizacion(r4)     // Catch:{ JSONException -> 0x01b8 }
        L_0x01b0:
            r2.header = r1     // Catch:{ JSONException -> 0x01b8 }
            r2.getCotizacionCompraProtegidaBodyResponseBean = r3     // Catch:{ JSONException -> 0x01b8 }
            r14.onResponseBean(r2)     // Catch:{ JSONException -> 0x01b8 }
            goto L_0x01bc
        L_0x01b8:
            r15 = move-exception
            r14.onUnknowError(r15)
        L_0x01bc:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.services.soap.request.GetCotizacionCompraProtegidaRequest.parserResponse(com.indra.httpclient.json.JSONObject):boolean");
    }

    private PlanSeguroBean getPlanObject(Gson gson, JSONObject jSONObject) {
        PlanSeguroBean planSeguroBean = new PlanSeguroBean();
        try {
            planSeguroBean.setCodPlan(jSONObject.has("codPlan") ? jSONObject.getString("codPlan") : "");
            planSeguroBean.setDesc(jSONObject.has(TarjetasConstants.DESC) ? jSONObject.getString(TarjetasConstants.DESC) : "");
            planSeguroBean.setCuota(jSONObject.has("cuota") ? jSONObject.getString("cuota") : "");
            planSeguroBean.setCantTarjetas(jSONObject.has("cantTarjetas") ? jSONObject.getString("cantTarjetas") : "");
            planSeguroBean.setSumaAsegurada1(jSONObject.has("sumaAsegurada1") ? jSONObject.getString("sumaAsegurada1") : "");
        } catch (JSONException e) {
            onUnknowError(e);
        }
        return planSeguroBean;
    }

    private LeyendaBean getLeyendaObject(Gson gson, JSONObject jSONObject) {
        LeyendaBean leyendaBean = new LeyendaBean();
        try {
            leyendaBean.setIdLeyenda(jSONObject.has("idLeyenda") ? jSONObject.getString("idLeyenda") : "");
            leyendaBean.setTitulo(jSONObject.has("titulo") ? jSONObject.getString("titulo") : "");
            leyendaBean.setDescripcion(jSONObject.has("descripcion") ? jSONObject.getString("descripcion") : "");
        } catch (JSONException e) {
            onUnknowError(e);
        }
        return leyendaBean;
    }
}
