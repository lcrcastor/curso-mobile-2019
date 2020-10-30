package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.LoginUnicoRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.LoginUnicoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class LoginUnicoRequest extends BaseRequest implements IBeanRequestWS {
    private final String TAG = LoginUnicoRequest.class.getName();
    private LoginUnicoRequestBean loginUnicoRequestBean;
    private LoginUnicoResponseBean loginUnicoResponseBean;

    public int getMethod() {
        return 1;
    }

    public LoginUnicoRequest(Context context, LoginUnicoRequestBean loginUnicoRequestBean2, ErrorRequestServer errorRequestServer) {
        super(context);
        this.loginUnicoRequestBean = loginUnicoRequestBean2;
        this.mErrorRequestServer = errorRequestServer;
    }

    public LoginUnicoRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.loginUnicoRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.loginUnicoResponseBean == null) {
            this.loginUnicoResponseBean = new LoginUnicoResponseBean();
        }
        return this.loginUnicoResponseBean.getClass();
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(17:43|(2:45|(1:47)(2:48|(1:50)(2:51|(1:53))))|54|(2:56|(1:58)(2:59|(1:61)(2:62|(1:64))))|65|(2:67|(1:69)(2:70|(1:72)(2:73|(1:75))))|76|(2:80|(1:82)(2:83|(1:85)(2:86|(1:88))))|89|(2:91|(1:93)(2:94|(1:96)(2:97|(1:99))))|100|101|102|103|(5:107|108|109|110|(4:114|(2:117|115)|157|118))|119|120) */
    /* JADX WARNING: Can't wrap try/catch for region: R(2:111|112) */
    /* JADX WARNING: Code restructure failed: missing block: B:112:?, code lost:
        r9 = new com.indra.httpclient.json.JSONArray().put((java.lang.Object) ((com.indra.httpclient.json.JSONObject) r5).getJSONObject("prestamo"));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:150:?, code lost:
        r5 = new com.indra.httpclient.json.JSONArray();
        r12 = r12.getJSONObject("pregunta");
        r6 = new ar.com.santander.rio.mbanking.services.soap.beans.body.PreguntaBean();
        r6.consulta = r12.getString("consulta");
        r6.ordenPregunta = r12.getInt("ordenPregunta");
        r5.put((java.lang.Object) r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:154:0x0467, code lost:
        r12 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:155:0x0468, code lost:
        r12.printStackTrace();
        onUnknowError(r12);
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:111:0x031d */
    /* JADX WARNING: Missing exception handler attribute for start block: B:119:0x034c */
    /* JADX WARNING: Missing exception handler attribute for start block: B:149:0x041c */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean parserResponse(com.indra.httpclient.json.JSONObject r12) {
        /*
            r11 = this;
            boolean r0 = super.parserResponse(r12)
            if (r0 == 0) goto L_0x046e
            com.google.gson.Gson r1 = new com.google.gson.Gson
            r1.<init>()
            java.lang.String r2 = "soapenv:Envelope"
            com.indra.httpclient.json.JSONObject r12 = r12.getJSONObject(r2)     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r2 = "soapenv:Body"
            com.indra.httpclient.json.JSONObject r12 = r12.getJSONObject(r2)     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r2 = "xml"
            com.indra.httpclient.json.JSONObject r12 = r12.getJSONObject(r2)     // Catch:{ JSONException -> 0x0467 }
            ar.com.santander.rio.mbanking.services.soap.beans.LoginUnicoResponseBean r2 = new ar.com.santander.rio.mbanking.services.soap.beans.LoginUnicoResponseBean     // Catch:{ JSONException -> 0x0467 }
            r2.<init>()     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r3 = "header"
            com.indra.httpclient.json.JSONObject r3 = r12.getJSONObject(r3)     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r3 = r3.toString()     // Catch:{ JSONException -> 0x0467 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean> r4 = ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean.class
            java.lang.Object r3 = r1.fromJson(r3, r4)     // Catch:{ JSONException -> 0x0467 }
            ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean r3 = (ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean) r3     // Catch:{ JSONException -> 0x0467 }
            r2.setHeaderBean(r3)     // Catch:{ JSONException -> 0x0467 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.LoginUnicoBodyResponseBean r3 = new ar.com.santander.rio.mbanking.services.soap.beans.body.LoginUnicoBodyResponseBean     // Catch:{ JSONException -> 0x0467 }
            r3.<init>()     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r4 = "body"
            com.indra.httpclient.json.JSONObject r12 = r12.getJSONObject(r4)     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r4 = "estado"
            java.lang.String r4 = r12.getString(r4)     // Catch:{ JSONException -> 0x0467 }
            r3.setEstado(r4)     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r4 = "habilitarWatson"
            boolean r4 = r12.has(r4)     // Catch:{ JSONException -> 0x0467 }
            if (r4 == 0) goto L_0x005c
            java.lang.String r4 = "habilitarWatson"
            java.lang.String r4 = r12.getString(r4)     // Catch:{ JSONException -> 0x0467 }
            r3.setHabilitarWatson(r4)     // Catch:{ JSONException -> 0x0467 }
        L_0x005c:
            java.lang.String r4 = r3.getEstado()     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r5 = "SI"
            boolean r4 = r4.equalsIgnoreCase(r5)     // Catch:{ JSONException -> 0x0467 }
            if (r4 != 0) goto L_0x0460
            java.lang.String r4 = r3.getEstado()     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r5 = "PI"
            boolean r4 = r4.equalsIgnoreCase(r5)     // Catch:{ JSONException -> 0x0467 }
            if (r4 != 0) goto L_0x0460
            java.lang.String r4 = r3.getEstado()     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r5 = "AU"
            boolean r4 = r4.equalsIgnoreCase(r5)     // Catch:{ JSONException -> 0x0467 }
            if (r4 != 0) goto L_0x0460
            java.lang.String r4 = r3.getEstado()     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r5 = "CV"
            boolean r4 = r4.equalsIgnoreCase(r5)     // Catch:{ JSONException -> 0x0467 }
            if (r4 != 0) goto L_0x0460
            java.lang.String r4 = r3.getEstado()     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r5 = "CU"
            boolean r4 = r4.equalsIgnoreCase(r5)     // Catch:{ JSONException -> 0x0467 }
            if (r4 != 0) goto L_0x0460
            java.lang.String r4 = "sesionUsuario"
            java.lang.String r4 = r12.getString(r4)     // Catch:{ JSONException -> 0x0467 }
            r3.setSesionUsuario(r4)     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r4 = "datosPersonales"
            java.lang.Object r4 = r12.get(r4)     // Catch:{ JSONException -> 0x0467 }
            if (r4 == 0) goto L_0x00b8
            java.lang.String r4 = r4.toString()     // Catch:{ JSONException -> 0x0467 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.model.general.DatosPersonales> r5 = ar.com.santander.rio.mbanking.services.model.general.DatosPersonales.class
            java.lang.Object r4 = r1.fromJson(r4, r5)     // Catch:{ JSONException -> 0x0467 }
            ar.com.santander.rio.mbanking.services.model.general.DatosPersonales r4 = (ar.com.santander.rio.mbanking.services.model.general.DatosPersonales) r4     // Catch:{ JSONException -> 0x0467 }
            r3.setDatosPersonales(r4)     // Catch:{ JSONException -> 0x0467 }
        L_0x00b8:
            ar.com.santander.rio.mbanking.services.model.general.DestinosMyA r4 = new ar.com.santander.rio.mbanking.services.model.general.DestinosMyA     // Catch:{ JSONException -> 0x0467 }
            r4.<init>()     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r5 = "destinosMyA"
            boolean r5 = r12.has(r5)     // Catch:{ JSONException -> 0x0467 }
            if (r5 == 0) goto L_0x011a
            java.lang.String r5 = "destinosMyA"
            java.lang.Object r5 = r12.get(r5)     // Catch:{ JSONException -> 0x0467 }
            if (r5 == 0) goto L_0x011a
            boolean r6 = r5 instanceof com.indra.httpclient.json.JSONObject     // Catch:{ JSONException -> 0x0467 }
            if (r6 == 0) goto L_0x011a
            r6 = r5
            com.indra.httpclient.json.JSONObject r6 = (com.indra.httpclient.json.JSONObject) r6     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r7 = "estadoSuscripcion"
            java.lang.String r6 = r6.getString(r7)     // Catch:{ JSONException -> 0x0467 }
            r4.setEstadoSuscripcion(r6)     // Catch:{ JSONException -> 0x0467 }
            r6 = r5
            com.indra.httpclient.json.JSONObject r6 = (com.indra.httpclient.json.JSONObject) r6     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r7 = "destinos"
            boolean r6 = r6.has(r7)     // Catch:{ JSONException -> 0x0467 }
            if (r6 == 0) goto L_0x011a
            r6 = r5
            com.indra.httpclient.json.JSONObject r6 = (com.indra.httpclient.json.JSONObject) r6     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r7 = "destinos"
            java.lang.Object r6 = r6.get(r7)     // Catch:{ JSONException -> 0x0467 }
            boolean r6 = r6 instanceof com.indra.httpclient.json.JSONObject     // Catch:{ JSONException -> 0x0467 }
            if (r6 == 0) goto L_0x011a
            r6 = r5
            com.indra.httpclient.json.JSONObject r6 = (com.indra.httpclient.json.JSONObject) r6     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r7 = "destinos"
            com.indra.httpclient.json.JSONObject r6 = r6.getJSONObject(r7)     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r7 = "destino"
            java.lang.Object r6 = r6.get(r7)     // Catch:{ JSONException -> 0x0467 }
            if (r6 != 0) goto L_0x0107
            goto L_0x011a
        L_0x0107:
            boolean r7 = r6 instanceof com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x0467 }
            if (r7 == 0) goto L_0x0118
            java.lang.String r4 = r5.toString()     // Catch:{ JSONException -> 0x0467 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.model.general.DestinosMyA> r5 = ar.com.santander.rio.mbanking.services.model.general.DestinosMyA.class
            java.lang.Object r4 = r1.fromJson(r4, r5)     // Catch:{ JSONException -> 0x0467 }
            ar.com.santander.rio.mbanking.services.model.general.DestinosMyA r4 = (ar.com.santander.rio.mbanking.services.model.general.DestinosMyA) r4     // Catch:{ JSONException -> 0x0467 }
            goto L_0x011a
        L_0x0118:
            boolean r5 = r6 instanceof com.indra.httpclient.json.JSONObject     // Catch:{ JSONException -> 0x0467 }
        L_0x011a:
            r3.setDestinosMyA(r4)     // Catch:{ JSONException -> 0x0467 }
            ar.com.santander.rio.mbanking.services.model.general.Productos r4 = new ar.com.santander.rio.mbanking.services.model.general.Productos     // Catch:{ JSONException -> 0x0467 }
            r4.<init>()     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r5 = "productos"
            boolean r5 = r12.has(r5)     // Catch:{ JSONException -> 0x0467 }
            if (r5 == 0) goto L_0x034f
            java.lang.String r5 = "productos"
            java.lang.Object r5 = r12.get(r5)     // Catch:{ JSONException -> 0x0467 }
            if (r5 == 0) goto L_0x034f
            boolean r6 = r5 instanceof com.indra.httpclient.json.JSONObject     // Catch:{ JSONException -> 0x0467 }
            if (r6 == 0) goto L_0x034f
            com.indra.httpclient.json.JSONObject r5 = (com.indra.httpclient.json.JSONObject) r5     // Catch:{ JSONException -> 0x0467 }
            ar.com.santander.rio.mbanking.services.model.general.Cuentas r6 = new ar.com.santander.rio.mbanking.services.model.general.Cuentas     // Catch:{ JSONException -> 0x0467 }
            r6.<init>()     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r7 = "cuentas"
            boolean r7 = r5.has(r7)     // Catch:{ JSONException -> 0x0467 }
            if (r7 == 0) goto L_0x018c
            java.lang.String r7 = "cuentas"
            com.indra.httpclient.json.JSONObject r7 = r5.getJSONObject(r7)     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r8 = "cuenta"
            java.lang.Object r7 = r7.get(r8)     // Catch:{ JSONException -> 0x0467 }
            if (r7 != 0) goto L_0x0154
            goto L_0x018c
        L_0x0154:
            boolean r8 = r7 instanceof com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x0467 }
            if (r8 == 0) goto L_0x016e
            java.lang.String r6 = "cuentas"
            com.indra.httpclient.json.JSONObject r6 = r5.getJSONObject(r6)     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r6 = r6.toString()     // Catch:{ JSONException -> 0x0467 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.model.general.Cuentas> r7 = ar.com.santander.rio.mbanking.services.model.general.Cuentas.class
            java.lang.Object r6 = r1.fromJson(r6, r7)     // Catch:{ JSONException -> 0x0467 }
            ar.com.santander.rio.mbanking.services.model.general.Cuentas r6 = (ar.com.santander.rio.mbanking.services.model.general.Cuentas) r6     // Catch:{ JSONException -> 0x0467 }
            r4.setCuentas(r6)     // Catch:{ JSONException -> 0x0467 }
            goto L_0x018c
        L_0x016e:
            boolean r8 = r7 instanceof com.indra.httpclient.json.JSONObject     // Catch:{ JSONException -> 0x0467 }
            if (r8 == 0) goto L_0x018c
            java.util.ArrayList r8 = new java.util.ArrayList     // Catch:{ JSONException -> 0x0467 }
            r8.<init>()     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r7 = r7.toString()     // Catch:{ JSONException -> 0x0467 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.model.general.Cuenta> r9 = ar.com.santander.rio.mbanking.services.model.general.Cuenta.class
            java.lang.Object r7 = r1.fromJson(r7, r9)     // Catch:{ JSONException -> 0x0467 }
            ar.com.santander.rio.mbanking.services.model.general.Cuenta r7 = (ar.com.santander.rio.mbanking.services.model.general.Cuenta) r7     // Catch:{ JSONException -> 0x0467 }
            r8.add(r7)     // Catch:{ JSONException -> 0x0467 }
            r6.setCuentas(r8)     // Catch:{ JSONException -> 0x0467 }
            r4.setCuentas(r6)     // Catch:{ JSONException -> 0x0467 }
        L_0x018c:
            ar.com.santander.rio.mbanking.services.model.general.Cuentas r6 = new ar.com.santander.rio.mbanking.services.model.general.Cuentas     // Catch:{ JSONException -> 0x0467 }
            r6.<init>()     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r7 = "cuentasBP"
            boolean r7 = r5.has(r7)     // Catch:{ JSONException -> 0x0467 }
            if (r7 == 0) goto L_0x01e0
            java.lang.String r7 = "cuentasBP"
            com.indra.httpclient.json.JSONObject r7 = r5.getJSONObject(r7)     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r8 = "cuentaBP"
            java.lang.Object r7 = r7.get(r8)     // Catch:{ JSONException -> 0x0467 }
            if (r7 != 0) goto L_0x01a8
            goto L_0x01e0
        L_0x01a8:
            boolean r8 = r7 instanceof com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x0467 }
            if (r8 == 0) goto L_0x01c2
            java.lang.String r6 = "cuentasBP"
            com.indra.httpclient.json.JSONObject r6 = r5.getJSONObject(r6)     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r6 = r6.toString()     // Catch:{ JSONException -> 0x0467 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.model.general.Cuentas> r7 = ar.com.santander.rio.mbanking.services.model.general.Cuentas.class
            java.lang.Object r6 = r1.fromJson(r6, r7)     // Catch:{ JSONException -> 0x0467 }
            ar.com.santander.rio.mbanking.services.model.general.Cuentas r6 = (ar.com.santander.rio.mbanking.services.model.general.Cuentas) r6     // Catch:{ JSONException -> 0x0467 }
            r4.setCuentasBP(r6)     // Catch:{ JSONException -> 0x0467 }
            goto L_0x01e0
        L_0x01c2:
            boolean r8 = r7 instanceof com.indra.httpclient.json.JSONObject     // Catch:{ JSONException -> 0x0467 }
            if (r8 == 0) goto L_0x01e0
            java.util.ArrayList r8 = new java.util.ArrayList     // Catch:{ JSONException -> 0x0467 }
            r8.<init>()     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r7 = r7.toString()     // Catch:{ JSONException -> 0x0467 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.model.general.Cuenta> r9 = ar.com.santander.rio.mbanking.services.model.general.Cuenta.class
            java.lang.Object r7 = r1.fromJson(r7, r9)     // Catch:{ JSONException -> 0x0467 }
            ar.com.santander.rio.mbanking.services.model.general.Cuenta r7 = (ar.com.santander.rio.mbanking.services.model.general.Cuenta) r7     // Catch:{ JSONException -> 0x0467 }
            r8.add(r7)     // Catch:{ JSONException -> 0x0467 }
            r6.setCuentasBP(r8)     // Catch:{ JSONException -> 0x0467 }
            r4.setCuentasBP(r6)     // Catch:{ JSONException -> 0x0467 }
        L_0x01e0:
            ar.com.santander.rio.mbanking.services.model.general.Tarjetas r6 = new ar.com.santander.rio.mbanking.services.model.general.Tarjetas     // Catch:{ JSONException -> 0x0467 }
            r6.<init>()     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r7 = "tarjetas"
            boolean r7 = r5.has(r7)     // Catch:{ JSONException -> 0x0467 }
            if (r7 == 0) goto L_0x022e
            java.lang.String r7 = "tarjetas"
            com.indra.httpclient.json.JSONObject r7 = r5.getJSONObject(r7)     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r8 = "tarjeta"
            java.lang.Object r7 = r7.get(r8)     // Catch:{ JSONException -> 0x0467 }
            if (r7 != 0) goto L_0x01fc
            goto L_0x022e
        L_0x01fc:
            boolean r8 = r7 instanceof com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x0467 }
            if (r8 == 0) goto L_0x0213
            java.lang.String r6 = "tarjetas"
            com.indra.httpclient.json.JSONObject r6 = r5.getJSONObject(r6)     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r6 = r6.toString()     // Catch:{ JSONException -> 0x0467 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.model.general.Tarjetas> r7 = ar.com.santander.rio.mbanking.services.model.general.Tarjetas.class
            java.lang.Object r6 = r1.fromJson(r6, r7)     // Catch:{ JSONException -> 0x0467 }
            ar.com.santander.rio.mbanking.services.model.general.Tarjetas r6 = (ar.com.santander.rio.mbanking.services.model.general.Tarjetas) r6     // Catch:{ JSONException -> 0x0467 }
            goto L_0x022e
        L_0x0213:
            boolean r8 = r7 instanceof com.indra.httpclient.json.JSONObject     // Catch:{ JSONException -> 0x0467 }
            if (r8 == 0) goto L_0x022e
            java.util.ArrayList r8 = new java.util.ArrayList     // Catch:{ JSONException -> 0x0467 }
            r8.<init>()     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r7 = r7.toString()     // Catch:{ JSONException -> 0x0467 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.model.general.Tarjeta> r9 = ar.com.santander.rio.mbanking.services.model.general.Tarjeta.class
            java.lang.Object r7 = r1.fromJson(r7, r9)     // Catch:{ JSONException -> 0x0467 }
            ar.com.santander.rio.mbanking.services.model.general.Tarjeta r7 = (ar.com.santander.rio.mbanking.services.model.general.Tarjeta) r7     // Catch:{ JSONException -> 0x0467 }
            r8.add(r7)     // Catch:{ JSONException -> 0x0467 }
            r6.setTarjetas(r8)     // Catch:{ JSONException -> 0x0467 }
        L_0x022e:
            r4.setTarjetas(r6)     // Catch:{ JSONException -> 0x0467 }
            ar.com.santander.rio.mbanking.services.model.general.TarjetasDebito r6 = new ar.com.santander.rio.mbanking.services.model.general.TarjetasDebito     // Catch:{ JSONException -> 0x0467 }
            r6.<init>()     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r7 = "tarjetasDebito"
            boolean r7 = r5.has(r7)     // Catch:{ JSONException -> 0x0467 }
            if (r7 == 0) goto L_0x028d
            java.lang.String r7 = "tarjetasDebito"
            com.indra.httpclient.json.JSONObject r7 = r5.getJSONObject(r7)     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r8 = "tarjetaDebito"
            boolean r7 = r7.has(r8)     // Catch:{ JSONException -> 0x0467 }
            if (r7 == 0) goto L_0x028d
            java.lang.String r7 = "tarjetasDebito"
            com.indra.httpclient.json.JSONObject r7 = r5.getJSONObject(r7)     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r8 = "tarjetaDebito"
            java.lang.Object r7 = r7.get(r8)     // Catch:{ JSONException -> 0x0467 }
            if (r7 != 0) goto L_0x025b
            goto L_0x028d
        L_0x025b:
            boolean r8 = r7 instanceof com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x0467 }
            if (r8 == 0) goto L_0x0272
            java.lang.String r6 = "tarjetasDebito"
            com.indra.httpclient.json.JSONObject r6 = r5.getJSONObject(r6)     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r6 = r6.toString()     // Catch:{ JSONException -> 0x0467 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.model.general.TarjetasDebito> r7 = ar.com.santander.rio.mbanking.services.model.general.TarjetasDebito.class
            java.lang.Object r6 = r1.fromJson(r6, r7)     // Catch:{ JSONException -> 0x0467 }
            ar.com.santander.rio.mbanking.services.model.general.TarjetasDebito r6 = (ar.com.santander.rio.mbanking.services.model.general.TarjetasDebito) r6     // Catch:{ JSONException -> 0x0467 }
            goto L_0x028d
        L_0x0272:
            boolean r8 = r7 instanceof com.indra.httpclient.json.JSONObject     // Catch:{ JSONException -> 0x0467 }
            if (r8 == 0) goto L_0x028d
            java.util.ArrayList r8 = new java.util.ArrayList     // Catch:{ JSONException -> 0x0467 }
            r8.<init>()     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r7 = r7.toString()     // Catch:{ JSONException -> 0x0467 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.model.general.Tarjeta> r9 = ar.com.santander.rio.mbanking.services.model.general.Tarjeta.class
            java.lang.Object r7 = r1.fromJson(r7, r9)     // Catch:{ JSONException -> 0x0467 }
            ar.com.santander.rio.mbanking.services.model.general.Tarjeta r7 = (ar.com.santander.rio.mbanking.services.model.general.Tarjeta) r7     // Catch:{ JSONException -> 0x0467 }
            r8.add(r7)     // Catch:{ JSONException -> 0x0467 }
            r6.setTarjetas(r8)     // Catch:{ JSONException -> 0x0467 }
        L_0x028d:
            java.lang.String r7 = r11.TAG     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r8 = "parserResponse: TERMINO CREACION DE TARJETAS DEBITO"
            android.util.Log.i(r7, r8)     // Catch:{ JSONException -> 0x0467 }
            r4.setTarjetasDebito(r6)     // Catch:{ JSONException -> 0x0467 }
            ar.com.santander.rio.mbanking.services.model.general.Cuentas r6 = new ar.com.santander.rio.mbanking.services.model.general.Cuentas     // Catch:{ JSONException -> 0x0467 }
            r6.<init>()     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r7 = "cuentasTitulo"
            boolean r7 = r5.has(r7)     // Catch:{ JSONException -> 0x0467 }
            if (r7 == 0) goto L_0x02e5
            java.lang.String r7 = "cuentasTitulo"
            com.indra.httpclient.json.JSONObject r7 = r5.getJSONObject(r7)     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r8 = "cuentaTitulo"
            java.lang.Object r7 = r7.get(r8)     // Catch:{ JSONException -> 0x0467 }
            if (r7 != 0) goto L_0x02b3
            goto L_0x02e5
        L_0x02b3:
            boolean r8 = r7 instanceof com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x0467 }
            if (r8 == 0) goto L_0x02ca
            java.lang.String r6 = "cuentasTitulo"
            com.indra.httpclient.json.JSONObject r6 = r5.getJSONObject(r6)     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r6 = r6.toString()     // Catch:{ JSONException -> 0x0467 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.model.general.Cuentas> r7 = ar.com.santander.rio.mbanking.services.model.general.Cuentas.class
            java.lang.Object r6 = r1.fromJson(r6, r7)     // Catch:{ JSONException -> 0x0467 }
            ar.com.santander.rio.mbanking.services.model.general.Cuentas r6 = (ar.com.santander.rio.mbanking.services.model.general.Cuentas) r6     // Catch:{ JSONException -> 0x0467 }
            goto L_0x02e5
        L_0x02ca:
            boolean r8 = r7 instanceof com.indra.httpclient.json.JSONObject     // Catch:{ JSONException -> 0x0467 }
            if (r8 == 0) goto L_0x02e5
            java.util.ArrayList r8 = new java.util.ArrayList     // Catch:{ JSONException -> 0x0467 }
            r8.<init>()     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r7 = r7.toString()     // Catch:{ JSONException -> 0x0467 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.model.general.Cuenta> r9 = ar.com.santander.rio.mbanking.services.model.general.Cuenta.class
            java.lang.Object r7 = r1.fromJson(r7, r9)     // Catch:{ JSONException -> 0x0467 }
            ar.com.santander.rio.mbanking.services.model.general.Cuenta r7 = (ar.com.santander.rio.mbanking.services.model.general.Cuenta) r7     // Catch:{ JSONException -> 0x0467 }
            r8.add(r7)     // Catch:{ JSONException -> 0x0467 }
            r6.setCuentasTitulo(r8)     // Catch:{ JSONException -> 0x0467 }
        L_0x02e5:
            r4.setCuentasTitulo(r6)     // Catch:{ JSONException -> 0x0467 }
            ar.com.santander.rio.mbanking.services.model.general.Prestamos r6 = new ar.com.santander.rio.mbanking.services.model.general.Prestamos     // Catch:{ JSONException -> 0x0467 }
            r6.<init>()     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r7 = "prestamos"
            boolean r7 = r5.has(r7)     // Catch:{ JSONException -> 0x034c }
            if (r7 == 0) goto L_0x034c
            java.lang.String r7 = "prestamos"
            com.indra.httpclient.json.JSONObject r7 = r5.getJSONObject(r7)     // Catch:{ JSONException -> 0x034c }
            java.lang.String r8 = "prestamo"
            boolean r7 = r7.has(r8)     // Catch:{ JSONException -> 0x034c }
            if (r7 == 0) goto L_0x034c
            java.lang.String r7 = "prestamos"
            java.lang.Object r5 = r5.get(r7)     // Catch:{ JSONException -> 0x034c }
            java.util.ArrayList r7 = new java.util.ArrayList     // Catch:{ JSONException -> 0x034c }
            r7.<init>()     // Catch:{ JSONException -> 0x034c }
            com.indra.httpclient.json.JSONArray r8 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x034c }
            r8.<init>()     // Catch:{ JSONException -> 0x034c }
            r9 = r5
            com.indra.httpclient.json.JSONObject r9 = (com.indra.httpclient.json.JSONObject) r9     // Catch:{ JSONException -> 0x031d }
            java.lang.String r10 = "prestamo"
            com.indra.httpclient.json.JSONArray r9 = r9.getJSONArray(r10)     // Catch:{ JSONException -> 0x031d }
            goto L_0x0329
        L_0x031d:
            com.indra.httpclient.json.JSONObject r5 = (com.indra.httpclient.json.JSONObject) r5     // Catch:{ JSONException -> 0x034c }
            java.lang.String r9 = "prestamo"
            com.indra.httpclient.json.JSONObject r5 = r5.getJSONObject(r9)     // Catch:{ JSONException -> 0x034c }
            com.indra.httpclient.json.JSONArray r9 = r8.put(r5)     // Catch:{ JSONException -> 0x034c }
        L_0x0329:
            if (r9 == 0) goto L_0x034c
            r5 = 0
        L_0x032c:
            int r8 = r9.length()     // Catch:{ JSONException -> 0x034c }
            if (r5 >= r8) goto L_0x0346
            java.lang.Object r8 = r9.get(r5)     // Catch:{ JSONException -> 0x034c }
            java.lang.String r8 = r8.toString()     // Catch:{ JSONException -> 0x034c }
            java.lang.Class<ar.com.santander.rio.mbanking.services.model.general.Prestamo> r10 = ar.com.santander.rio.mbanking.services.model.general.Prestamo.class
            java.lang.Object r8 = r1.fromJson(r8, r10)     // Catch:{ JSONException -> 0x034c }
            r7.add(r8)     // Catch:{ JSONException -> 0x034c }
            int r5 = r5 + 1
            goto L_0x032c
        L_0x0346:
            r6.setPrestamos(r7)     // Catch:{ JSONException -> 0x034c }
            r4.setPrestamos(r6)     // Catch:{ JSONException -> 0x034c }
        L_0x034c:
            r4.setPrestamos(r6)     // Catch:{ JSONException -> 0x0467 }
        L_0x034f:
            r3.setProductos(r4)     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r4 = "crm"
            boolean r4 = r12.has(r4)     // Catch:{ JSONException -> 0x0467 }
            if (r4 == 0) goto L_0x03e4
            ar.com.santander.rio.mbanking.services.soap.beans.body.Crm r4 = new ar.com.santander.rio.mbanking.services.soap.beans.body.Crm     // Catch:{ JSONException -> 0x0467 }
            r4.<init>()     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r5 = "crm"
            com.indra.httpclient.json.JSONObject r5 = r12.getJSONObject(r5)     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r6 = "cantidad"
            boolean r6 = r5.has(r6)     // Catch:{ JSONException -> 0x0467 }
            if (r6 == 0) goto L_0x0375
            java.lang.String r6 = "cantidad"
            java.lang.String r6 = r5.getString(r6)     // Catch:{ JSONException -> 0x0467 }
            r4.cantidad = r6     // Catch:{ JSONException -> 0x0467 }
        L_0x0375:
            java.lang.String r6 = "notificaciones"
            boolean r6 = r5.has(r6)     // Catch:{ JSONException -> 0x0467 }
            if (r6 == 0) goto L_0x03e4
            ar.com.santander.rio.mbanking.services.model.general.Notificaciones r6 = new ar.com.santander.rio.mbanking.services.model.general.Notificaciones     // Catch:{ JSONException -> 0x0467 }
            r6.<init>()     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r7 = "notificaciones"
            java.lang.Object r5 = r5.get(r7)     // Catch:{ JSONException -> 0x0467 }
            boolean r7 = r5 instanceof com.indra.httpclient.json.JSONObject     // Catch:{ JSONException -> 0x0467 }
            if (r7 == 0) goto L_0x03d1
            com.indra.httpclient.json.JSONObject r5 = (com.indra.httpclient.json.JSONObject) r5     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r7 = "notificacion"
            java.lang.Object r5 = r5.get(r7)     // Catch:{ JSONException -> 0x0467 }
            boolean r7 = r5 instanceof com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x0467 }
            if (r7 == 0) goto L_0x03b2
            ar.com.santander.rio.mbanking.services.soap.request.LoginUnicoRequest$1 r7 = new ar.com.santander.rio.mbanking.services.soap.request.LoginUnicoRequest$1     // Catch:{ JSONException -> 0x0467 }
            r7.<init>()     // Catch:{ JSONException -> 0x0467 }
            java.lang.reflect.Type r7 = r7.getType()     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r5 = r5.toString()     // Catch:{ JSONException -> 0x0467 }
            java.lang.Object r5 = r1.fromJson(r5, r7)     // Catch:{ JSONException -> 0x0467 }
            java.util.List r5 = (java.util.List) r5     // Catch:{ JSONException -> 0x0467 }
            r6.setListNotificaciones(r5)     // Catch:{ JSONException -> 0x0467 }
            r4.setNotificaciones(r6)     // Catch:{ JSONException -> 0x0467 }
            goto L_0x03e1
        L_0x03b2:
            boolean r7 = r5 instanceof com.indra.httpclient.json.JSONObject     // Catch:{ JSONException -> 0x0467 }
            if (r7 == 0) goto L_0x03e1
            java.util.ArrayList r7 = new java.util.ArrayList     // Catch:{ JSONException -> 0x0467 }
            r7.<init>()     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r5 = r5.toString()     // Catch:{ JSONException -> 0x0467 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.model.general.Notificacion> r8 = ar.com.santander.rio.mbanking.services.model.general.Notificacion.class
            java.lang.Object r5 = r1.fromJson(r5, r8)     // Catch:{ JSONException -> 0x0467 }
            ar.com.santander.rio.mbanking.services.model.general.Notificacion r5 = (ar.com.santander.rio.mbanking.services.model.general.Notificacion) r5     // Catch:{ JSONException -> 0x0467 }
            r7.add(r5)     // Catch:{ JSONException -> 0x0467 }
            r6.setListNotificaciones(r7)     // Catch:{ JSONException -> 0x0467 }
            r4.setNotificaciones(r6)     // Catch:{ JSONException -> 0x0467 }
            goto L_0x03e1
        L_0x03d1:
            ar.com.santander.rio.mbanking.services.model.general.Notificaciones r5 = new ar.com.santander.rio.mbanking.services.model.general.Notificaciones     // Catch:{ JSONException -> 0x0467 }
            r5.<init>()     // Catch:{ JSONException -> 0x0467 }
            java.util.ArrayList r6 = new java.util.ArrayList     // Catch:{ JSONException -> 0x0467 }
            r6.<init>()     // Catch:{ JSONException -> 0x0467 }
            r5.setListNotificaciones(r6)     // Catch:{ JSONException -> 0x0467 }
            r4.setNotificaciones(r5)     // Catch:{ JSONException -> 0x0467 }
        L_0x03e1:
            r3.setCrm(r4)     // Catch:{ JSONException -> 0x0467 }
        L_0x03e4:
            java.lang.String r4 = "calificacion"
            boolean r4 = r12.has(r4)     // Catch:{ JSONException -> 0x0467 }
            if (r4 == 0) goto L_0x0460
            ar.com.santander.rio.mbanking.services.soap.beans.body.CalificacionBean r4 = new ar.com.santander.rio.mbanking.services.soap.beans.body.CalificacionBean     // Catch:{ JSONException -> 0x0467 }
            r4.<init>()     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r5 = "calificacion"
            com.indra.httpclient.json.JSONObject r12 = r12.getJSONObject(r5)     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r5 = "idEncuesta"
            boolean r5 = r12.has(r5)     // Catch:{ JSONException -> 0x0467 }
            if (r5 == 0) goto L_0x0407
            java.lang.String r5 = "idEncuesta"
            java.lang.String r5 = r12.getString(r5)     // Catch:{ JSONException -> 0x0467 }
            r4.idEncuesta = r5     // Catch:{ JSONException -> 0x0467 }
        L_0x0407:
            java.lang.String r5 = "listaPreguntas"
            boolean r5 = r12.has(r5)     // Catch:{ JSONException -> 0x0467 }
            if (r5 == 0) goto L_0x045d
            java.lang.String r5 = "listaPreguntas"
            com.indra.httpclient.json.JSONObject r12 = r12.getJSONObject(r5)     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r5 = "pregunta"
            com.indra.httpclient.json.JSONArray r5 = r12.getJSONArray(r5)     // Catch:{ JSONException -> 0x041c }
            goto L_0x043f
        L_0x041c:
            com.indra.httpclient.json.JSONArray r5 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x0467 }
            r5.<init>()     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r6 = "pregunta"
            com.indra.httpclient.json.JSONObject r12 = r12.getJSONObject(r6)     // Catch:{ JSONException -> 0x0467 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.PreguntaBean r6 = new ar.com.santander.rio.mbanking.services.soap.beans.body.PreguntaBean     // Catch:{ JSONException -> 0x0467 }
            r6.<init>()     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r7 = "consulta"
            java.lang.String r7 = r12.getString(r7)     // Catch:{ JSONException -> 0x0467 }
            r6.consulta = r7     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r7 = "ordenPregunta"
            int r12 = r12.getInt(r7)     // Catch:{ JSONException -> 0x0467 }
            r6.ordenPregunta = r12     // Catch:{ JSONException -> 0x0467 }
            r5.put(r6)     // Catch:{ JSONException -> 0x0467 }
        L_0x043f:
            ar.com.santander.rio.mbanking.services.soap.beans.body.ListaPreguntasBean r12 = new ar.com.santander.rio.mbanking.services.soap.beans.body.ListaPreguntasBean     // Catch:{ JSONException -> 0x0467 }
            r12.<init>()     // Catch:{ JSONException -> 0x0467 }
            r4.listaPreguntas = r12     // Catch:{ JSONException -> 0x0467 }
            ar.com.santander.rio.mbanking.services.soap.request.LoginUnicoRequest$2 r12 = new ar.com.santander.rio.mbanking.services.soap.request.LoginUnicoRequest$2     // Catch:{ JSONException -> 0x0467 }
            r12.<init>()     // Catch:{ JSONException -> 0x0467 }
            java.lang.reflect.Type r12 = r12.getType()     // Catch:{ JSONException -> 0x0467 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.ListaPreguntasBean r6 = r4.listaPreguntas     // Catch:{ JSONException -> 0x0467 }
            java.lang.String r5 = r5.toString()     // Catch:{ JSONException -> 0x0467 }
            java.lang.Object r12 = r1.fromJson(r5, r12)     // Catch:{ JSONException -> 0x0467 }
            java.util.List r12 = (java.util.List) r12     // Catch:{ JSONException -> 0x0467 }
            r6.preguntas = r12     // Catch:{ JSONException -> 0x0467 }
        L_0x045d:
            r3.setCalificacion(r4)     // Catch:{ JSONException -> 0x0467 }
        L_0x0460:
            r2.setLoginUnicoBodyResponseBean(r3)     // Catch:{ JSONException -> 0x0467 }
            r11.onResponseBean(r2)     // Catch:{ JSONException -> 0x0467 }
            goto L_0x046e
        L_0x0467:
            r12 = move-exception
            r12.printStackTrace()
            r11.onUnknowError(r12)
        L_0x046e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.services.soap.request.LoginUnicoRequest.parserResponse(com.indra.httpclient.json.JSONObject):boolean");
    }
}
