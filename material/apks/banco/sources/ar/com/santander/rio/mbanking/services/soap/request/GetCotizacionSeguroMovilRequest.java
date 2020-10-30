package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.services.soap.beans.GetCotizacionSeguroMovilRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetCotizacionSeguroMovilResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LeyendaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PlanSeguroBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;

public abstract class GetCotizacionSeguroMovilRequest extends BaseRequest implements IBeanRequestWS {
    private GetCotizacionSeguroMovilRequestBean getCotizacionSeguroMovilRequestBean;
    private GetCotizacionSeguroMovilResponseBean getCotizacionSeguroMovilResponseBean;

    public int getMethod() {
        return 1;
    }

    public GetCotizacionSeguroMovilRequest(Context context, GetCotizacionSeguroMovilRequestBean getCotizacionSeguroMovilRequestBean2, ErrorRequestServer errorRequestServer) {
        super(context, false);
        this.getCotizacionSeguroMovilRequestBean = getCotizacionSeguroMovilRequestBean2;
        this.mErrorRequestServer = errorRequestServer;
    }

    public GetCotizacionSeguroMovilRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.getCotizacionSeguroMovilRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.getCotizacionSeguroMovilResponseBean == null) {
            this.getCotizacionSeguroMovilResponseBean = new GetCotizacionSeguroMovilResponseBean();
        }
        return this.getCotizacionSeguroMovilResponseBean.getClass();
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(2:24|25) */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        r6 = new com.indra.httpclient.json.JSONArray();
        r6.put((java.lang.Object) r5.getJSONObject("plan"));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:?, code lost:
        r5 = new com.indra.httpclient.json.JSONArray();
        r5.put((java.lang.Object) r15.getJSONObject("leyenda"));
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:24:0x00a4 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:70:0x020f */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean parserResponse(com.indra.httpclient.json.JSONObject r15) {
        /*
            r14 = this;
            boolean r0 = super.parserResponse(r15)
            if (r0 == 0) goto L_0x0295
            com.google.gson.Gson r1 = new com.google.gson.Gson
            r1.<init>()
            java.lang.String r2 = "soapenv:Envelope"
            com.indra.httpclient.json.JSONObject r15 = r15.getJSONObject(r2)     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r2 = "soapenv:Body"
            com.indra.httpclient.json.JSONObject r15 = r15.getJSONObject(r2)     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r2 = "xml"
            com.indra.httpclient.json.JSONObject r15 = r15.getJSONObject(r2)     // Catch:{ JSONException -> 0x0291 }
            ar.com.santander.rio.mbanking.services.soap.beans.GetCotizacionSeguroMovilResponseBean r2 = new ar.com.santander.rio.mbanking.services.soap.beans.GetCotizacionSeguroMovilResponseBean     // Catch:{ JSONException -> 0x0291 }
            r2.<init>()     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r3 = "header"
            com.indra.httpclient.json.JSONObject r3 = r15.getJSONObject(r3)     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r3 = r3.toString()     // Catch:{ JSONException -> 0x0291 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean> r4 = ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean.class
            java.lang.Object r1 = r1.fromJson(r3, r4)     // Catch:{ JSONException -> 0x0291 }
            ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean r1 = (ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean) r1     // Catch:{ JSONException -> 0x0291 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroMovilBodyResponseBean r3 = new ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroMovilBodyResponseBean     // Catch:{ JSONException -> 0x0291 }
            r3.<init>()     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r4 = "body"
            com.indra.httpclient.json.JSONObject r15 = r15.getJSONObject(r4)     // Catch:{ JSONException -> 0x0291 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.CotizacionBean r4 = new ar.com.santander.rio.mbanking.services.soap.beans.body.CotizacionBean     // Catch:{ JSONException -> 0x0291 }
            r4.<init>()     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r5 = "cotizacion"
            boolean r5 = r15.has(r5)     // Catch:{ JSONException -> 0x0291 }
            if (r5 == 0) goto L_0x0286
            java.lang.String r5 = "cotizacion"
            com.indra.httpclient.json.JSONObject r15 = r15.getJSONObject(r5)     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r5 = "codRamo"
            boolean r5 = r15.has(r5)     // Catch:{ JSONException -> 0x0291 }
            if (r5 == 0) goto L_0x0061
            java.lang.String r5 = "codRamo"
            java.lang.String r5 = r15.getString(r5)     // Catch:{ JSONException -> 0x0291 }
            goto L_0x0063
        L_0x0061:
            java.lang.String r5 = ""
        L_0x0063:
            r4.setCodRamo(r5)     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r5 = "codProducto"
            boolean r5 = r15.has(r5)     // Catch:{ JSONException -> 0x0291 }
            if (r5 == 0) goto L_0x0075
            java.lang.String r5 = "codProducto"
            java.lang.String r5 = r15.getString(r5)     // Catch:{ JSONException -> 0x0291 }
            goto L_0x0077
        L_0x0075:
            java.lang.String r5 = ""
        L_0x0077:
            r4.setCodProducto(r5)     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r5 = "numCotizacion"
            boolean r5 = r15.has(r5)     // Catch:{ JSONException -> 0x0291 }
            if (r5 == 0) goto L_0x0089
            java.lang.String r5 = "numCotizacion"
            java.lang.String r5 = r15.getString(r5)     // Catch:{ JSONException -> 0x0291 }
            goto L_0x008b
        L_0x0089:
            java.lang.String r5 = ""
        L_0x008b:
            r4.setNumCotizacion(r5)     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r5 = "planes"
            com.indra.httpclient.json.JSONObject r5 = r15.getJSONObject(r5)     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r6 = "plan"
            boolean r6 = r5.has(r6)     // Catch:{ JSONException -> 0x0291 }
            r7 = 0
            if (r6 == 0) goto L_0x01fa
            java.lang.String r6 = "plan"
            com.indra.httpclient.json.JSONArray r6 = r5.getJSONArray(r6)     // Catch:{ JSONException -> 0x00a4 }
            goto L_0x00b2
        L_0x00a4:
            com.indra.httpclient.json.JSONArray r6 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x0291 }
            r6.<init>()     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r8 = "plan"
            com.indra.httpclient.json.JSONObject r8 = r5.getJSONObject(r8)     // Catch:{ JSONException -> 0x0291 }
            r6.put(r8)     // Catch:{ JSONException -> 0x0291 }
        L_0x00b2:
            com.indra.httpclient.json.JSONArray r8 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x0291 }
            r8.<init>()     // Catch:{ JSONException -> 0x0291 }
            r9 = 0
        L_0x00b8:
            int r10 = r6.length()     // Catch:{ JSONException -> 0x0291 }
            if (r9 >= r10) goto L_0x01da
            com.indra.httpclient.json.JSONObject r10 = new com.indra.httpclient.json.JSONObject     // Catch:{ JSONException -> 0x0291 }
            r10.<init>()     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r11 = "cuota"
            java.lang.Object r12 = r6.get(r9)     // Catch:{ JSONException -> 0x0291 }
            com.indra.httpclient.json.JSONObject r12 = (com.indra.httpclient.json.JSONObject) r12     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r13 = "cuota"
            boolean r12 = r12.has(r13)     // Catch:{ JSONException -> 0x0291 }
            if (r12 == 0) goto L_0x00e0
            java.lang.Object r12 = r6.get(r9)     // Catch:{ JSONException -> 0x0291 }
            com.indra.httpclient.json.JSONObject r12 = (com.indra.httpclient.json.JSONObject) r12     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r13 = "cuota"
            java.lang.String r12 = r12.getString(r13)     // Catch:{ JSONException -> 0x0291 }
            goto L_0x00e2
        L_0x00e0:
            java.lang.String r12 = ""
        L_0x00e2:
            r10.put(r11, r12)     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r11 = "descripcionSuma1"
            java.lang.Object r12 = r6.get(r9)     // Catch:{ JSONException -> 0x0291 }
            com.indra.httpclient.json.JSONObject r12 = (com.indra.httpclient.json.JSONObject) r12     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r13 = "descripcionSuma1"
            boolean r12 = r12.has(r13)     // Catch:{ JSONException -> 0x0291 }
            if (r12 == 0) goto L_0x0102
            java.lang.Object r12 = r6.get(r9)     // Catch:{ JSONException -> 0x0291 }
            com.indra.httpclient.json.JSONObject r12 = (com.indra.httpclient.json.JSONObject) r12     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r13 = "descripcionSuma1"
            java.lang.String r12 = r12.getString(r13)     // Catch:{ JSONException -> 0x0291 }
            goto L_0x0104
        L_0x0102:
            java.lang.String r12 = ""
        L_0x0104:
            r10.put(r11, r12)     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r11 = "descripcionSuma2"
            java.lang.Object r12 = r6.get(r9)     // Catch:{ JSONException -> 0x0291 }
            com.indra.httpclient.json.JSONObject r12 = (com.indra.httpclient.json.JSONObject) r12     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r13 = "descripcionSuma2"
            boolean r12 = r12.has(r13)     // Catch:{ JSONException -> 0x0291 }
            if (r12 == 0) goto L_0x0124
            java.lang.Object r12 = r6.get(r9)     // Catch:{ JSONException -> 0x0291 }
            com.indra.httpclient.json.JSONObject r12 = (com.indra.httpclient.json.JSONObject) r12     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r13 = "descripcionSuma2"
            java.lang.String r12 = r12.getString(r13)     // Catch:{ JSONException -> 0x0291 }
            goto L_0x0126
        L_0x0124:
            java.lang.String r12 = ""
        L_0x0126:
            r10.put(r11, r12)     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r11 = "codPlan"
            java.lang.Object r12 = r6.get(r9)     // Catch:{ JSONException -> 0x0291 }
            com.indra.httpclient.json.JSONObject r12 = (com.indra.httpclient.json.JSONObject) r12     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r13 = "codPlan"
            boolean r12 = r12.has(r13)     // Catch:{ JSONException -> 0x0291 }
            if (r12 == 0) goto L_0x0146
            java.lang.Object r12 = r6.get(r9)     // Catch:{ JSONException -> 0x0291 }
            com.indra.httpclient.json.JSONObject r12 = (com.indra.httpclient.json.JSONObject) r12     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r13 = "codPlan"
            java.lang.String r12 = r12.getString(r13)     // Catch:{ JSONException -> 0x0291 }
            goto L_0x0148
        L_0x0146:
            java.lang.String r12 = ""
        L_0x0148:
            r10.put(r11, r12)     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r11 = "nombre"
            java.lang.Object r12 = r6.get(r9)     // Catch:{ JSONException -> 0x0291 }
            com.indra.httpclient.json.JSONObject r12 = (com.indra.httpclient.json.JSONObject) r12     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r13 = "nombre"
            boolean r12 = r12.has(r13)     // Catch:{ JSONException -> 0x0291 }
            if (r12 == 0) goto L_0x0168
            java.lang.Object r12 = r6.get(r9)     // Catch:{ JSONException -> 0x0291 }
            com.indra.httpclient.json.JSONObject r12 = (com.indra.httpclient.json.JSONObject) r12     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r13 = "nombre"
            java.lang.String r12 = r12.getString(r13)     // Catch:{ JSONException -> 0x0291 }
            goto L_0x016a
        L_0x0168:
            java.lang.String r12 = ""
        L_0x016a:
            r10.put(r11, r12)     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r11 = "sumaAsegurada2"
            java.lang.Object r12 = r6.get(r9)     // Catch:{ JSONException -> 0x0291 }
            com.indra.httpclient.json.JSONObject r12 = (com.indra.httpclient.json.JSONObject) r12     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r13 = "sumaAsegurada2"
            boolean r12 = r12.has(r13)     // Catch:{ JSONException -> 0x0291 }
            if (r12 == 0) goto L_0x018a
            java.lang.Object r12 = r6.get(r9)     // Catch:{ JSONException -> 0x0291 }
            com.indra.httpclient.json.JSONObject r12 = (com.indra.httpclient.json.JSONObject) r12     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r13 = "sumaAsegurada2"
            java.lang.String r12 = r12.getString(r13)     // Catch:{ JSONException -> 0x0291 }
            goto L_0x018c
        L_0x018a:
            java.lang.String r12 = ""
        L_0x018c:
            r10.put(r11, r12)     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r11 = "desc"
            java.lang.Object r12 = r6.get(r9)     // Catch:{ JSONException -> 0x0291 }
            com.indra.httpclient.json.JSONObject r12 = (com.indra.httpclient.json.JSONObject) r12     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r13 = "desc"
            boolean r12 = r12.has(r13)     // Catch:{ JSONException -> 0x0291 }
            if (r12 == 0) goto L_0x01ac
            java.lang.Object r12 = r6.get(r9)     // Catch:{ JSONException -> 0x0291 }
            com.indra.httpclient.json.JSONObject r12 = (com.indra.httpclient.json.JSONObject) r12     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r13 = "desc"
            java.lang.String r12 = r12.getString(r13)     // Catch:{ JSONException -> 0x0291 }
            goto L_0x01ae
        L_0x01ac:
            java.lang.String r12 = ""
        L_0x01ae:
            r10.put(r11, r12)     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r11 = "sumaAsegurada1"
            java.lang.Object r12 = r6.get(r9)     // Catch:{ JSONException -> 0x0291 }
            com.indra.httpclient.json.JSONObject r12 = (com.indra.httpclient.json.JSONObject) r12     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r13 = "sumaAsegurada1"
            boolean r12 = r12.has(r13)     // Catch:{ JSONException -> 0x0291 }
            if (r12 == 0) goto L_0x01ce
            java.lang.Object r12 = r6.get(r9)     // Catch:{ JSONException -> 0x0291 }
            com.indra.httpclient.json.JSONObject r12 = (com.indra.httpclient.json.JSONObject) r12     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r13 = "sumaAsegurada1"
            java.lang.String r12 = r12.getString(r13)     // Catch:{ JSONException -> 0x0291 }
            goto L_0x01d0
        L_0x01ce:
            java.lang.String r12 = ""
        L_0x01d0:
            r10.put(r11, r12)     // Catch:{ JSONException -> 0x0291 }
            r8.put(r10)     // Catch:{ JSONException -> 0x0291 }
            int r9 = r9 + 1
            goto L_0x00b8
        L_0x01da:
            if (r5 == 0) goto L_0x01e6
            java.lang.String r6 = "plan"
            r5.remove(r6)     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r6 = "plan"
            r5.put(r6, r8)     // Catch:{ JSONException -> 0x0291 }
        L_0x01e6:
            com.google.gson.Gson r6 = new com.google.gson.Gson     // Catch:{ JSONException -> 0x0291 }
            r6.<init>()     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ JSONException -> 0x0291 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.body.PlanesSeguroBean> r8 = ar.com.santander.rio.mbanking.services.soap.beans.body.PlanesSeguroBean.class
            java.lang.Object r5 = r6.fromJson(r5, r8)     // Catch:{ JSONException -> 0x0291 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.PlanesSeguroBean r5 = (ar.com.santander.rio.mbanking.services.soap.beans.body.PlanesSeguroBean) r5     // Catch:{ JSONException -> 0x0291 }
            r4.setPlanes(r5)     // Catch:{ JSONException -> 0x0291 }
        L_0x01fa:
            java.lang.String r5 = "listaLeyendas"
            com.indra.httpclient.json.JSONObject r15 = r15.getJSONObject(r5)     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r5 = "leyenda"
            boolean r5 = r15.has(r5)     // Catch:{ JSONException -> 0x0291 }
            if (r5 == 0) goto L_0x0286
            java.lang.String r5 = "leyenda"
            com.indra.httpclient.json.JSONArray r5 = r15.getJSONArray(r5)     // Catch:{ JSONException -> 0x020f }
            goto L_0x021d
        L_0x020f:
            com.indra.httpclient.json.JSONArray r5 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x0291 }
            r5.<init>()     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r6 = "leyenda"
            com.indra.httpclient.json.JSONObject r6 = r15.getJSONObject(r6)     // Catch:{ JSONException -> 0x0291 }
            r5.put(r6)     // Catch:{ JSONException -> 0x0291 }
        L_0x021d:
            com.indra.httpclient.json.JSONArray r6 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x0291 }
            r6.<init>()     // Catch:{ JSONException -> 0x0291 }
        L_0x0222:
            int r8 = r5.length()     // Catch:{ JSONException -> 0x0291 }
            if (r7 >= r8) goto L_0x0266
            com.indra.httpclient.json.JSONObject r8 = new com.indra.httpclient.json.JSONObject     // Catch:{ JSONException -> 0x0291 }
            r8.<init>()     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r9 = "descripcion"
            java.lang.Object r10 = r5.get(r7)     // Catch:{ JSONException -> 0x0291 }
            com.indra.httpclient.json.JSONObject r10 = (com.indra.httpclient.json.JSONObject) r10     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r11 = "descripcion"
            java.lang.String r10 = r10.getString(r11)     // Catch:{ JSONException -> 0x0291 }
            r8.put(r9, r10)     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r9 = "idLeyenda"
            java.lang.Object r10 = r5.get(r7)     // Catch:{ JSONException -> 0x0291 }
            com.indra.httpclient.json.JSONObject r10 = (com.indra.httpclient.json.JSONObject) r10     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r11 = "idLeyenda"
            java.lang.String r10 = r10.getString(r11)     // Catch:{ JSONException -> 0x0291 }
            r8.put(r9, r10)     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r9 = "titulo"
            java.lang.Object r10 = r5.get(r7)     // Catch:{ JSONException -> 0x0291 }
            com.indra.httpclient.json.JSONObject r10 = (com.indra.httpclient.json.JSONObject) r10     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r11 = "titulo"
            java.lang.String r10 = r10.getString(r11)     // Catch:{ JSONException -> 0x0291 }
            r8.put(r9, r10)     // Catch:{ JSONException -> 0x0291 }
            r6.put(r8)     // Catch:{ JSONException -> 0x0291 }
            int r7 = r7 + 1
            goto L_0x0222
        L_0x0266:
            if (r15 == 0) goto L_0x0272
            java.lang.String r5 = "leyenda"
            r15.remove(r5)     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r5 = "leyenda"
            r15.put(r5, r6)     // Catch:{ JSONException -> 0x0291 }
        L_0x0272:
            com.google.gson.Gson r5 = new com.google.gson.Gson     // Catch:{ JSONException -> 0x0291 }
            r5.<init>()     // Catch:{ JSONException -> 0x0291 }
            java.lang.String r15 = java.lang.String.valueOf(r15)     // Catch:{ JSONException -> 0x0291 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.body.ListaLeyendaSeguroMovil> r6 = ar.com.santander.rio.mbanking.services.soap.beans.body.ListaLeyendaSeguroMovil.class
            java.lang.Object r15 = r5.fromJson(r15, r6)     // Catch:{ JSONException -> 0x0291 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.ListaLeyendaSeguroMovil r15 = (ar.com.santander.rio.mbanking.services.soap.beans.body.ListaLeyendaSeguroMovil) r15     // Catch:{ JSONException -> 0x0291 }
            r4.setListaLeyendas(r15)     // Catch:{ JSONException -> 0x0291 }
        L_0x0286:
            r3.setCotizacion(r4)     // Catch:{ JSONException -> 0x0291 }
            r2.header = r1     // Catch:{ JSONException -> 0x0291 }
            r2.getCotizacionSeguroMovilBodyResponseBean = r3     // Catch:{ JSONException -> 0x0291 }
            r14.onResponseBean(r2)     // Catch:{ JSONException -> 0x0291 }
            goto L_0x0295
        L_0x0291:
            r15 = move-exception
            r14.onUnknowError(r15)
        L_0x0295:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.services.soap.request.GetCotizacionSeguroMovilRequest.parserResponse(com.indra.httpclient.json.JSONObject):boolean");
    }

    private PlanSeguroBean getPlanObject(Gson gson, JSONObject jSONObject) {
        PlanSeguroBean planSeguroBean = new PlanSeguroBean();
        try {
            planSeguroBean.setCodPlan(jSONObject.has("codPlan") ? jSONObject.getString("codPlan") : "");
            planSeguroBean.setCodPlan(jSONObject.has("nombre") ? jSONObject.getString("nombre") : "");
            planSeguroBean.setDesc(jSONObject.has(TarjetasConstants.DESC) ? jSONObject.getString(TarjetasConstants.DESC) : "");
            planSeguroBean.setCuota(jSONObject.has("cuota") ? jSONObject.getString("cuota") : "");
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
