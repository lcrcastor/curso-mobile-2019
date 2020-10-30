package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import android.util.Log;
import ar.com.santander.rio.mbanking.services.soap.beans.GenesysChatRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GenesysChatResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public class GenesysChatRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private GenesysChatRequestBean genesysChatRequestBean;
    private GenesysChatResponseBean genesysChatResponseBean;

    public int getMethod() {
        return 1;
    }

    public void onResponseBean(IBeanWS iBeanWS) {
    }

    public GenesysChatRequest(Context context, GenesysChatRequestBean genesysChatRequestBean2, ErrorRequestServer errorRequestServer) {
        super(context, false);
        this.genesysChatRequestBean = genesysChatRequestBean2;
        this.mErrorRequestServer = errorRequestServer;
        this.genesysChatResponseBean = new GenesysChatResponseBean();
    }

    public GenesysChatRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void onUnknowError(Exception exc) {
        StringBuilder sb = new StringBuilder();
        sb.append("onUnknowError: ");
        sb.append(exc.getLocalizedMessage());
        Log.d("error ", sb.toString());
    }

    public IBeanWS getBeanToRequest() {
        return this.genesysChatRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.genesysChatResponseBean == null) {
            this.genesysChatResponseBean = new GenesysChatResponseBean();
        }
        return this.genesysChatResponseBean.getClass();
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

    /* JADX WARNING: Can't wrap try/catch for region: R(2:43|44) */
    /* JADX WARNING: Code restructure failed: missing block: B:44:?, code lost:
        r9 = new com.indra.httpclient.json.JSONArray();
        r9.put((java.lang.Object) r15.getJSONObject("listaMensajes").getJSONObject("mensaje"));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:?, code lost:
        r4 = new com.indra.httpclient.json.JSONArray();
        r4.put((java.lang.Object) r15.getJSONObject("listaPreguntas").getString("pregunta"));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:?, code lost:
        r1 = new com.indra.httpclient.json.JSONArray();
        r1.put((java.lang.Object) r15.getJSONObject("listaOpciones").getString("opcion"));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:?, code lost:
        r1 = new com.indra.httpclient.json.JSONArray();
        r1.put((java.lang.Object) r15.getJSONObject("listaSugerencias").getString("sugerencia"));
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:43:0x0131 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:60:0x0196 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:77:0x01ef */
    /* JADX WARNING: Missing exception handler attribute for start block: B:94:0x0248 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean parserResponse(com.indra.httpclient.json.JSONObject r15) {
        /*
            r14 = this;
            boolean r0 = super.parserResponse(r15)
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
            ar.com.santander.rio.mbanking.services.soap.beans.body.ListaPreguntas r1 = new ar.com.santander.rio.mbanking.services.soap.beans.body.ListaPreguntas
            r1.<init>()
            ar.com.santander.rio.mbanking.services.soap.beans.body.ListaOpciones r2 = new ar.com.santander.rio.mbanking.services.soap.beans.body.ListaOpciones
            r2.<init>()
            ar.com.santander.rio.mbanking.services.soap.beans.body.ListaSugerencias r3 = new ar.com.santander.rio.mbanking.services.soap.beans.body.ListaSugerencias
            r3.<init>()
            ar.com.santander.rio.mbanking.services.soap.beans.body.ListaMensajes r4 = new ar.com.santander.rio.mbanking.services.soap.beans.body.ListaMensajes
            r4.<init>()
            boolean r5 = r0.booleanValue()
            if (r5 == 0) goto L_0x02f2
            com.google.gson.Gson r5 = new com.google.gson.Gson
            r5.<init>()
            java.lang.String r6 = "soapenv:Envelope"
            com.indra.httpclient.json.JSONObject r15 = r15.getJSONObject(r6)     // Catch:{ JSONException -> 0x02ee }
            java.lang.String r6 = "soapenv:Body"
            com.indra.httpclient.json.JSONObject r15 = r15.getJSONObject(r6)     // Catch:{ JSONException -> 0x02ee }
            java.lang.String r6 = "xml"
            com.indra.httpclient.json.JSONObject r15 = r15.getJSONObject(r6)     // Catch:{ JSONException -> 0x02ee }
            ar.com.santander.rio.mbanking.services.soap.beans.GenesysChatResponseBean r6 = new ar.com.santander.rio.mbanking.services.soap.beans.GenesysChatResponseBean     // Catch:{ JSONException -> 0x02ee }
            r6.<init>()     // Catch:{ JSONException -> 0x02ee }
            java.lang.String r7 = "header"
            com.indra.httpclient.json.JSONObject r7 = r15.getJSONObject(r7)     // Catch:{ JSONException -> 0x02ee }
            java.lang.String r7 = r7.toString()     // Catch:{ JSONException -> 0x02ee }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean> r8 = ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean.class
            java.lang.Object r7 = r5.fromJson(r7, r8)     // Catch:{ JSONException -> 0x02ee }
            ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean r7 = (ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean) r7     // Catch:{ JSONException -> 0x02ee }
            ar.com.santander.rio.mbanking.services.soap.beans.body.GenesysChatBodyResponseBean r8 = new ar.com.santander.rio.mbanking.services.soap.beans.body.GenesysChatBodyResponseBean     // Catch:{ JSONException -> 0x02ee }
            r8.<init>()     // Catch:{ JSONException -> 0x02ee }
            java.lang.String r9 = "body"
            com.indra.httpclient.json.JSONObject r15 = r15.getJSONObject(r9)     // Catch:{ JSONException -> 0x02ee }
            java.lang.String r9 = "res"
            boolean r9 = r15.has(r9)     // Catch:{ JSONException -> 0x02ee }
            if (r9 == 0) goto L_0x006c
            java.lang.String r9 = "res"
            java.lang.String r9 = r15.getString(r9)     // Catch:{ JSONException -> 0x02ee }
            r8.setRes(r9)     // Catch:{ JSONException -> 0x02ee }
        L_0x006c:
            java.lang.String r9 = "fecha"
            boolean r9 = r15.has(r9)     // Catch:{ JSONException -> 0x02ee }
            if (r9 == 0) goto L_0x007d
            java.lang.String r9 = "fecha"
            java.lang.String r9 = r15.getString(r9)     // Catch:{ JSONException -> 0x02ee }
            r8.setFecha(r9)     // Catch:{ JSONException -> 0x02ee }
        L_0x007d:
            java.lang.String r9 = "esPregunta"
            boolean r9 = r15.has(r9)     // Catch:{ JSONException -> 0x02ee }
            if (r9 == 0) goto L_0x008e
            java.lang.String r9 = "esPregunta"
            java.lang.String r9 = r15.getString(r9)     // Catch:{ JSONException -> 0x02ee }
            r8.setEsPregunta(r9)     // Catch:{ JSONException -> 0x02ee }
        L_0x008e:
            java.lang.String r9 = "conversacionId"
            boolean r9 = r15.has(r9)     // Catch:{ JSONException -> 0x02ee }
            if (r9 == 0) goto L_0x009f
            java.lang.String r9 = "conversacionId"
            java.lang.String r9 = r15.getString(r9)     // Catch:{ JSONException -> 0x02ee }
            r8.setConversacionId(r9)     // Catch:{ JSONException -> 0x02ee }
        L_0x009f:
            java.lang.String r9 = "chatId"
            boolean r9 = r15.has(r9)     // Catch:{ JSONException -> 0x02ee }
            if (r9 == 0) goto L_0x00b0
            java.lang.String r9 = "chatId"
            java.lang.String r9 = r15.getString(r9)     // Catch:{ JSONException -> 0x02ee }
            r8.setChatId(r9)     // Catch:{ JSONException -> 0x02ee }
        L_0x00b0:
            java.lang.String r9 = "intencion"
            boolean r9 = r15.has(r9)     // Catch:{ JSONException -> 0x02ee }
            if (r9 == 0) goto L_0x00c1
            java.lang.String r9 = "intencion"
            java.lang.String r9 = r15.getString(r9)     // Catch:{ JSONException -> 0x02ee }
            r8.setIntencion(r9)     // Catch:{ JSONException -> 0x02ee }
        L_0x00c1:
            java.lang.String r9 = "confianza"
            boolean r9 = r15.has(r9)     // Catch:{ JSONException -> 0x02ee }
            if (r9 == 0) goto L_0x00d2
            java.lang.String r9 = "confianza"
            java.lang.String r9 = r15.getString(r9)     // Catch:{ JSONException -> 0x02ee }
            r8.setConfianza(r9)     // Catch:{ JSONException -> 0x02ee }
        L_0x00d2:
            java.lang.String r9 = "esPregunta"
            boolean r9 = r15.has(r9)     // Catch:{ JSONException -> 0x02ee }
            if (r9 == 0) goto L_0x00e3
            java.lang.String r9 = "esPregunta"
            java.lang.String r9 = r15.getString(r9)     // Catch:{ JSONException -> 0x02ee }
            r8.setEsPregunta(r9)     // Catch:{ JSONException -> 0x02ee }
        L_0x00e3:
            java.lang.String r9 = "esPregunta"
            boolean r9 = r15.has(r9)     // Catch:{ JSONException -> 0x02ee }
            if (r9 == 0) goto L_0x00f4
            java.lang.String r9 = "esPregunta"
            java.lang.String r9 = r15.getString(r9)     // Catch:{ JSONException -> 0x02ee }
            r8.setEsPregunta(r9)     // Catch:{ JSONException -> 0x02ee }
        L_0x00f4:
            java.lang.String r9 = "nickName"
            boolean r9 = r15.has(r9)     // Catch:{ JSONException -> 0x02ee }
            if (r9 == 0) goto L_0x0105
            java.lang.String r9 = "nickName"
            java.lang.String r9 = r15.getString(r9)     // Catch:{ JSONException -> 0x02ee }
            r8.setNickName(r9)     // Catch:{ JSONException -> 0x02ee }
        L_0x0105:
            java.lang.String r9 = "listaMensajes"
            boolean r9 = r15.has(r9)     // Catch:{ JSONException -> 0x02ee }
            r10 = 0
            if (r9 == 0) goto L_0x016b
            java.lang.String r9 = "listaMensajes"
            boolean r9 = r15.has(r9)     // Catch:{ JSONException -> 0x02ee }
            if (r9 == 0) goto L_0x0168
            java.lang.String r9 = "listaMensajes"
            com.indra.httpclient.json.JSONObject r9 = r15.getJSONObject(r9)     // Catch:{ JSONException -> 0x02ee }
            java.lang.String r11 = "mensaje"
            boolean r9 = r9.has(r11)     // Catch:{ JSONException -> 0x02ee }
            if (r9 == 0) goto L_0x0168
            java.lang.String r9 = "listaMensajes"
            com.indra.httpclient.json.JSONObject r9 = r15.getJSONObject(r9)     // Catch:{ JSONException -> 0x0131 }
            java.lang.String r11 = "mensaje"
            com.indra.httpclient.json.JSONArray r9 = r9.getJSONArray(r11)     // Catch:{ JSONException -> 0x0131 }
            goto L_0x0145
        L_0x0131:
            com.indra.httpclient.json.JSONArray r9 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x02ee }
            r9.<init>()     // Catch:{ JSONException -> 0x02ee }
            java.lang.String r11 = "listaMensajes"
            com.indra.httpclient.json.JSONObject r11 = r15.getJSONObject(r11)     // Catch:{ JSONException -> 0x02ee }
            java.lang.String r12 = "mensaje"
            com.indra.httpclient.json.JSONObject r11 = r11.getJSONObject(r12)     // Catch:{ JSONException -> 0x02ee }
            r9.put(r11)     // Catch:{ JSONException -> 0x02ee }
        L_0x0145:
            if (r9 == 0) goto L_0x0168
            r11 = 0
        L_0x0148:
            int r12 = r9.length()     // Catch:{ JSONException -> 0x02ee }
            if (r11 >= r12) goto L_0x0168
            com.indra.httpclient.json.JSONObject r12 = r9.getJSONObject(r11)     // Catch:{ JSONException -> 0x02ee }
            java.lang.String r12 = r12.toString()     // Catch:{ JSONException -> 0x02ee }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.body.MensajeGenesysChat> r13 = ar.com.santander.rio.mbanking.services.soap.beans.body.MensajeGenesysChat.class
            java.lang.Object r12 = r5.fromJson(r12, r13)     // Catch:{ JSONException -> 0x02ee }
            ar.com.santander.rio.mbanking.services.soap.beans.body.MensajeGenesysChat r12 = (ar.com.santander.rio.mbanking.services.soap.beans.body.MensajeGenesysChat) r12     // Catch:{ JSONException -> 0x02ee }
            java.util.List r13 = r4.getMensaje()     // Catch:{ JSONException -> 0x02ee }
            r13.add(r12)     // Catch:{ JSONException -> 0x02ee }
            int r11 = r11 + 1
            goto L_0x0148
        L_0x0168:
            r8.setListaMensajes(r4)     // Catch:{ JSONException -> 0x02ee }
        L_0x016b:
            java.lang.String r4 = "listaPreguntas"
            boolean r4 = r15.has(r4)     // Catch:{ JSONException -> 0x02ee }
            if (r4 == 0) goto L_0x01c4
            java.lang.String r4 = "listaPreguntas"
            boolean r4 = r15.has(r4)     // Catch:{ JSONException -> 0x02ee }
            if (r4 == 0) goto L_0x01c1
            java.lang.String r4 = "listaPreguntas"
            com.indra.httpclient.json.JSONObject r4 = r15.getJSONObject(r4)     // Catch:{ JSONException -> 0x02ee }
            java.lang.String r9 = "pregunta"
            boolean r4 = r4.has(r9)     // Catch:{ JSONException -> 0x02ee }
            if (r4 == 0) goto L_0x01c1
            java.lang.String r4 = "listaPreguntas"
            com.indra.httpclient.json.JSONObject r4 = r15.getJSONObject(r4)     // Catch:{ JSONException -> 0x0196 }
            java.lang.String r9 = "pregunta"
            com.indra.httpclient.json.JSONArray r4 = r4.getJSONArray(r9)     // Catch:{ JSONException -> 0x0196 }
            goto L_0x01aa
        L_0x0196:
            com.indra.httpclient.json.JSONArray r4 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x02ee }
            r4.<init>()     // Catch:{ JSONException -> 0x02ee }
            java.lang.String r9 = "listaPreguntas"
            com.indra.httpclient.json.JSONObject r9 = r15.getJSONObject(r9)     // Catch:{ JSONException -> 0x02ee }
            java.lang.String r11 = "pregunta"
            java.lang.String r9 = r9.getString(r11)     // Catch:{ JSONException -> 0x02ee }
            r4.put(r9)     // Catch:{ JSONException -> 0x02ee }
        L_0x01aa:
            if (r4 == 0) goto L_0x01c1
            r9 = 0
        L_0x01ad:
            int r11 = r4.length()     // Catch:{ JSONException -> 0x02ee }
            if (r9 >= r11) goto L_0x01c1
            java.lang.String r11 = r4.getString(r9)     // Catch:{ JSONException -> 0x02ee }
            java.util.List r12 = r1.getPregunta()     // Catch:{ JSONException -> 0x02ee }
            r12.add(r11)     // Catch:{ JSONException -> 0x02ee }
            int r9 = r9 + 1
            goto L_0x01ad
        L_0x01c1:
            r8.setListaPreguntas(r1)     // Catch:{ JSONException -> 0x02ee }
        L_0x01c4:
            java.lang.String r1 = "listaOpciones"
            boolean r1 = r15.has(r1)     // Catch:{ JSONException -> 0x02ee }
            if (r1 == 0) goto L_0x021d
            java.lang.String r1 = "listaOpciones"
            boolean r1 = r15.has(r1)     // Catch:{ JSONException -> 0x02ee }
            if (r1 == 0) goto L_0x021a
            java.lang.String r1 = "listaOpciones"
            com.indra.httpclient.json.JSONObject r1 = r15.getJSONObject(r1)     // Catch:{ JSONException -> 0x02ee }
            java.lang.String r4 = "opcion"
            boolean r1 = r1.has(r4)     // Catch:{ JSONException -> 0x02ee }
            if (r1 == 0) goto L_0x021a
            java.lang.String r1 = "listaOpciones"
            com.indra.httpclient.json.JSONObject r1 = r15.getJSONObject(r1)     // Catch:{ JSONException -> 0x01ef }
            java.lang.String r4 = "opcion"
            com.indra.httpclient.json.JSONArray r1 = r1.getJSONArray(r4)     // Catch:{ JSONException -> 0x01ef }
            goto L_0x0203
        L_0x01ef:
            com.indra.httpclient.json.JSONArray r1 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x02ee }
            r1.<init>()     // Catch:{ JSONException -> 0x02ee }
            java.lang.String r4 = "listaOpciones"
            com.indra.httpclient.json.JSONObject r4 = r15.getJSONObject(r4)     // Catch:{ JSONException -> 0x02ee }
            java.lang.String r9 = "opcion"
            java.lang.String r4 = r4.getString(r9)     // Catch:{ JSONException -> 0x02ee }
            r1.put(r4)     // Catch:{ JSONException -> 0x02ee }
        L_0x0203:
            if (r1 == 0) goto L_0x021a
            r4 = 0
        L_0x0206:
            int r9 = r1.length()     // Catch:{ JSONException -> 0x02ee }
            if (r4 >= r9) goto L_0x021a
            java.lang.String r9 = r1.getString(r4)     // Catch:{ JSONException -> 0x02ee }
            java.util.List r11 = r2.getOpcion()     // Catch:{ JSONException -> 0x02ee }
            r11.add(r9)     // Catch:{ JSONException -> 0x02ee }
            int r4 = r4 + 1
            goto L_0x0206
        L_0x021a:
            r8.setListaOpciones(r2)     // Catch:{ JSONException -> 0x02ee }
        L_0x021d:
            java.lang.String r1 = "listaSugerencias"
            boolean r1 = r15.has(r1)     // Catch:{ JSONException -> 0x02ee }
            if (r1 == 0) goto L_0x0275
            java.lang.String r1 = "listaSugerencias"
            boolean r1 = r15.has(r1)     // Catch:{ JSONException -> 0x02ee }
            if (r1 == 0) goto L_0x0272
            java.lang.String r1 = "listaSugerencias"
            com.indra.httpclient.json.JSONObject r1 = r15.getJSONObject(r1)     // Catch:{ JSONException -> 0x02ee }
            java.lang.String r2 = "sugerencia"
            boolean r1 = r1.has(r2)     // Catch:{ JSONException -> 0x02ee }
            if (r1 == 0) goto L_0x0272
            java.lang.String r1 = "listaSugerencias"
            com.indra.httpclient.json.JSONObject r1 = r15.getJSONObject(r1)     // Catch:{ JSONException -> 0x0248 }
            java.lang.String r2 = "sugerencia"
            com.indra.httpclient.json.JSONArray r1 = r1.getJSONArray(r2)     // Catch:{ JSONException -> 0x0248 }
            goto L_0x025c
        L_0x0248:
            com.indra.httpclient.json.JSONArray r1 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x02ee }
            r1.<init>()     // Catch:{ JSONException -> 0x02ee }
            java.lang.String r2 = "listaSugerencias"
            com.indra.httpclient.json.JSONObject r2 = r15.getJSONObject(r2)     // Catch:{ JSONException -> 0x02ee }
            java.lang.String r4 = "sugerencia"
            java.lang.String r2 = r2.getString(r4)     // Catch:{ JSONException -> 0x02ee }
            r1.put(r2)     // Catch:{ JSONException -> 0x02ee }
        L_0x025c:
            if (r1 == 0) goto L_0x0272
        L_0x025e:
            int r2 = r1.length()     // Catch:{ JSONException -> 0x02ee }
            if (r10 >= r2) goto L_0x0272
            java.lang.String r2 = r1.getString(r10)     // Catch:{ JSONException -> 0x02ee }
            java.util.List r4 = r3.getSugerencia()     // Catch:{ JSONException -> 0x02ee }
            r4.add(r2)     // Catch:{ JSONException -> 0x02ee }
            int r10 = r10 + 1
            goto L_0x025e
        L_0x0272:
            r8.setListaSugerencias(r3)     // Catch:{ JSONException -> 0x02ee }
        L_0x0275:
            java.lang.String r1 = "rellamados"
            boolean r1 = r15.has(r1)     // Catch:{ JSONException -> 0x02ee }
            if (r1 == 0) goto L_0x029a
            java.lang.String r1 = "rellamados"
            boolean r1 = r15.has(r1)     // Catch:{ JSONException -> 0x02ee }
            if (r1 == 0) goto L_0x029a
            java.lang.String r1 = "rellamados"
            com.indra.httpclient.json.JSONObject r1 = r15.getJSONObject(r1)     // Catch:{ JSONException -> 0x02ee }
            java.lang.String r1 = r1.toString()     // Catch:{ JSONException -> 0x02ee }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.body.RellamadosBean> r2 = ar.com.santander.rio.mbanking.services.soap.beans.body.RellamadosBean.class
            java.lang.Object r1 = r5.fromJson(r1, r2)     // Catch:{ JSONException -> 0x02ee }
            ar.com.santander.rio.mbanking.services.soap.beans.body.RellamadosBean r1 = (ar.com.santander.rio.mbanking.services.soap.beans.body.RellamadosBean) r1     // Catch:{ JSONException -> 0x02ee }
            r8.setRellamados(r1)     // Catch:{ JSONException -> 0x02ee }
        L_0x029a:
            java.lang.String r1 = "resCod"
            boolean r1 = r15.has(r1)     // Catch:{ JSONException -> 0x02ee }
            if (r1 == 0) goto L_0x02a9
            java.lang.String r1 = "resCod"
            java.lang.String r1 = r15.getString(r1)     // Catch:{ JSONException -> 0x02ee }
            goto L_0x02ab
        L_0x02a9:
            java.lang.String r1 = ""
        L_0x02ab:
            r8.resCod = r1     // Catch:{ JSONException -> 0x02ee }
            java.lang.String r1 = "resDesc"
            boolean r1 = r15.has(r1)     // Catch:{ JSONException -> 0x02ee }
            if (r1 == 0) goto L_0x02bc
            java.lang.String r1 = "resDesc"
            java.lang.String r1 = r15.getString(r1)     // Catch:{ JSONException -> 0x02ee }
            goto L_0x02be
        L_0x02bc:
            java.lang.String r1 = ""
        L_0x02be:
            r8.resDesc = r1     // Catch:{ JSONException -> 0x02ee }
            java.lang.String r1 = "resTitle"
            boolean r1 = r15.has(r1)     // Catch:{ JSONException -> 0x02ee }
            if (r1 == 0) goto L_0x02cf
            java.lang.String r1 = "resTitle"
            java.lang.String r1 = r15.getString(r1)     // Catch:{ JSONException -> 0x02ee }
            goto L_0x02d1
        L_0x02cf:
            java.lang.String r1 = ""
        L_0x02d1:
            r8.resTitle = r1     // Catch:{ JSONException -> 0x02ee }
            java.lang.String r1 = "res"
            boolean r1 = r15.has(r1)     // Catch:{ JSONException -> 0x02ee }
            if (r1 == 0) goto L_0x02e2
            java.lang.String r1 = "res"
            java.lang.String r15 = r15.getString(r1)     // Catch:{ JSONException -> 0x02ee }
            goto L_0x02e4
        L_0x02e2:
            java.lang.String r15 = ""
        L_0x02e4:
            r8.res = r15     // Catch:{ JSONException -> 0x02ee }
            r6.headerBean = r7     // Catch:{ JSONException -> 0x02ee }
            r6.genesysChatBodyResponseBean = r8     // Catch:{ JSONException -> 0x02ee }
            r14.onResponseBean(r6)     // Catch:{ JSONException -> 0x02ee }
            goto L_0x02f2
        L_0x02ee:
            r15 = move-exception
            r14.onUnknowError(r15)
        L_0x02f2:
            boolean r15 = r0.booleanValue()
            return r15
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.services.soap.request.GenesysChatRequest.parserResponse(com.indra.httpclient.json.JSONObject):boolean");
    }
}
