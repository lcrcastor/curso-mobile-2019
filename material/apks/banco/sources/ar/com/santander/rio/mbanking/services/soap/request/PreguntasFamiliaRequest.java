package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.PreguntasFamiliaResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.PreguntasFamiliaResquestBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class PreguntasFamiliaRequest extends BaseRequest implements IBeanRequestWS {
    private PreguntasFamiliaResponseBean mPreguntasFamiliaResponseBean;
    private PreguntasFamiliaResquestBean mPreguntasFamiliaResquestBean;

    public int getMethod() {
        return 1;
    }

    public PreguntasFamiliaRequest(Context context, PreguntasFamiliaResquestBean preguntasFamiliaResquestBean, ErrorRequestServer errorRequestServer) {
        super(context);
        this.mPreguntasFamiliaResquestBean = preguntasFamiliaResquestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public PreguntasFamiliaRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mPreguntasFamiliaResquestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mPreguntasFamiliaResponseBean == null) {
            this.mPreguntasFamiliaResponseBean = new PreguntasFamiliaResponseBean();
        }
        return this.mPreguntasFamiliaResponseBean.getClass();
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(2:11|12) */
    /* JADX WARNING: Can't wrap try/catch for region: R(2:52|53) */
    /* JADX WARNING: Code restructure failed: missing block: B:12:?, code lost:
        r9 = new com.indra.httpclient.json.JSONArray();
        r9.put((java.lang.Object) r4.getJSONObject("pregunta"));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:?, code lost:
        r2 = new com.indra.httpclient.json.JSONArray();
        r2.put((java.lang.Object) r1.getJSONObject("opcion"));
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x006a */
    /* JADX WARNING: Missing exception handler attribute for start block: B:52:0x0158 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean parserResponse(com.indra.httpclient.json.JSONObject r19) {
        /*
            r18 = this;
            r1 = r18
            boolean r2 = super.parserResponse(r19)
            if (r2 == 0) goto L_0x0202
            com.google.gson.Gson r3 = new com.google.gson.Gson
            r3.<init>()
            java.lang.String r4 = "soapenv:Envelope"
            r5 = r19
            com.indra.httpclient.json.JSONObject r4 = r5.getJSONObject(r4)     // Catch:{ JSONException -> 0x01fa }
            java.lang.String r5 = "soapenv:Body"
            com.indra.httpclient.json.JSONObject r4 = r4.getJSONObject(r5)     // Catch:{ JSONException -> 0x01fa }
            java.lang.String r5 = "xml"
            com.indra.httpclient.json.JSONObject r4 = r4.getJSONObject(r5)     // Catch:{ JSONException -> 0x01fa }
            ar.com.santander.rio.mbanking.services.soap.beans.PreguntasFamiliaResponseBean r5 = new ar.com.santander.rio.mbanking.services.soap.beans.PreguntasFamiliaResponseBean     // Catch:{ JSONException -> 0x01fa }
            r5.<init>()     // Catch:{ JSONException -> 0x01fa }
            java.lang.String r6 = "header"
            com.indra.httpclient.json.JSONObject r6 = r4.getJSONObject(r6)     // Catch:{ JSONException -> 0x01fa }
            java.lang.String r6 = r6.toString()     // Catch:{ JSONException -> 0x01fa }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean> r7 = ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean.class
            java.lang.Object r3 = r3.fromJson(r6, r7)     // Catch:{ JSONException -> 0x01fa }
            ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean r3 = (ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean) r3     // Catch:{ JSONException -> 0x01fa }
            ar.com.santander.rio.mbanking.services.soap.beans.body.ListaPreguntasFamilia r6 = new ar.com.santander.rio.mbanking.services.soap.beans.body.ListaPreguntasFamilia     // Catch:{ JSONException -> 0x01fa }
            r6.<init>()     // Catch:{ JSONException -> 0x01fa }
            ar.com.santander.rio.mbanking.services.soap.beans.body.GetPreguntasFamiliaBodyResponseBean r7 = new ar.com.santander.rio.mbanking.services.soap.beans.body.GetPreguntasFamiliaBodyResponseBean     // Catch:{ JSONException -> 0x01fa }
            r7.<init>()     // Catch:{ JSONException -> 0x01fa }
            java.lang.String r8 = "body"
            com.indra.httpclient.json.JSONObject r4 = r4.getJSONObject(r8)     // Catch:{ JSONException -> 0x01fa }
            java.lang.String r8 = "listaPreguntas"
            boolean r8 = r4.has(r8)     // Catch:{ JSONException -> 0x01fa }
            if (r8 == 0) goto L_0x01e4
            java.lang.String r8 = "listaPreguntas"
            com.indra.httpclient.json.JSONObject r4 = r4.getJSONObject(r8)     // Catch:{ JSONException -> 0x01fa }
            java.util.ArrayList r8 = new java.util.ArrayList     // Catch:{ JSONException -> 0x01fa }
            r8.<init>()     // Catch:{ JSONException -> 0x01fa }
            java.lang.String r9 = "pregunta"
            boolean r9 = r4.has(r9)     // Catch:{ JSONException -> 0x01fa }
            if (r9 == 0) goto L_0x01d9
            java.lang.String r9 = "pregunta"
            com.indra.httpclient.json.JSONArray r9 = r4.getJSONArray(r9)     // Catch:{ JSONException -> 0x006a }
            goto L_0x0078
        L_0x006a:
            com.indra.httpclient.json.JSONArray r9 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x01fa }
            r9.<init>()     // Catch:{ JSONException -> 0x01fa }
            java.lang.String r10 = "pregunta"
            com.indra.httpclient.json.JSONObject r4 = r4.getJSONObject(r10)     // Catch:{ JSONException -> 0x01fa }
            r9.put(r4)     // Catch:{ JSONException -> 0x01fa }
        L_0x0078:
            r10 = 0
        L_0x0079:
            int r11 = r9.length()     // Catch:{ JSONException -> 0x01fa }
            if (r10 >= r11) goto L_0x01d9
            ar.com.santander.rio.mbanking.services.soap.beans.body.PreguntasFamiliaBean r11 = new ar.com.santander.rio.mbanking.services.soap.beans.body.PreguntasFamiliaBean     // Catch:{ JSONException -> 0x01fa }
            r11.<init>()     // Catch:{ JSONException -> 0x01fa }
            ar.com.santander.rio.mbanking.services.soap.beans.ListaOpcionesFamiliaObjetos r12 = new ar.com.santander.rio.mbanking.services.soap.beans.ListaOpcionesFamiliaObjetos     // Catch:{ JSONException -> 0x01fa }
            r12.<init>()     // Catch:{ JSONException -> 0x01fa }
            java.lang.Object r13 = r9.get(r10)     // Catch:{ JSONException -> 0x01fa }
            com.indra.httpclient.json.JSONObject r13 = (com.indra.httpclient.json.JSONObject) r13     // Catch:{ JSONException -> 0x01fa }
            java.lang.String r14 = "idPregunta"
            boolean r13 = r13.has(r14)     // Catch:{ JSONException -> 0x01fa }
            if (r13 == 0) goto L_0x00a4
            java.lang.Object r13 = r9.get(r10)     // Catch:{ JSONException -> 0x01fa }
            com.indra.httpclient.json.JSONObject r13 = (com.indra.httpclient.json.JSONObject) r13     // Catch:{ JSONException -> 0x01fa }
            java.lang.String r14 = "idPregunta"
            java.lang.String r13 = r13.getString(r14)     // Catch:{ JSONException -> 0x01fa }
            goto L_0x00a6
        L_0x00a4:
            java.lang.String r13 = ""
        L_0x00a6:
            java.lang.Object r14 = r9.get(r10)     // Catch:{ JSONException -> 0x01fa }
            com.indra.httpclient.json.JSONObject r14 = (com.indra.httpclient.json.JSONObject) r14     // Catch:{ JSONException -> 0x01fa }
            java.lang.String r15 = "textoPregunta"
            boolean r14 = r14.has(r15)     // Catch:{ JSONException -> 0x01fa }
            if (r14 == 0) goto L_0x00c1
            java.lang.Object r14 = r9.get(r10)     // Catch:{ JSONException -> 0x01fa }
            com.indra.httpclient.json.JSONObject r14 = (com.indra.httpclient.json.JSONObject) r14     // Catch:{ JSONException -> 0x01fa }
            java.lang.String r15 = "textoPregunta"
            java.lang.String r14 = r14.getString(r15)     // Catch:{ JSONException -> 0x01fa }
            goto L_0x00c3
        L_0x00c1:
            java.lang.String r14 = ""
        L_0x00c3:
            java.lang.Object r15 = r9.get(r10)     // Catch:{ JSONException -> 0x01fa }
            com.indra.httpclient.json.JSONObject r15 = (com.indra.httpclient.json.JSONObject) r15     // Catch:{ JSONException -> 0x01fa }
            java.lang.String r4 = "tipoRespuesta"
            boolean r4 = r15.has(r4)     // Catch:{ JSONException -> 0x01fa }
            if (r4 == 0) goto L_0x00de
            java.lang.Object r4 = r9.get(r10)     // Catch:{ JSONException -> 0x01fa }
            com.indra.httpclient.json.JSONObject r4 = (com.indra.httpclient.json.JSONObject) r4     // Catch:{ JSONException -> 0x01fa }
            java.lang.String r15 = "tipoRespuesta"
            java.lang.String r4 = r4.getString(r15)     // Catch:{ JSONException -> 0x01fa }
            goto L_0x00e0
        L_0x00de:
            java.lang.String r4 = ""
        L_0x00e0:
            java.lang.Object r15 = r9.get(r10)     // Catch:{ JSONException -> 0x01fa }
            com.indra.httpclient.json.JSONObject r15 = (com.indra.httpclient.json.JSONObject) r15     // Catch:{ JSONException -> 0x01fa }
            r16 = r2
            java.lang.String r2 = "cantMaxCaracters"
            boolean r2 = r15.has(r2)     // Catch:{ JSONException -> 0x01f4 }
            if (r2 == 0) goto L_0x00fd
            java.lang.Object r2 = r9.get(r10)     // Catch:{ JSONException -> 0x01f4 }
            com.indra.httpclient.json.JSONObject r2 = (com.indra.httpclient.json.JSONObject) r2     // Catch:{ JSONException -> 0x01f4 }
            java.lang.String r15 = "cantMaxCaracters"
            java.lang.String r2 = r2.getString(r15)     // Catch:{ JSONException -> 0x01f4 }
            goto L_0x00ff
        L_0x00fd:
            java.lang.String r2 = ""
        L_0x00ff:
            java.lang.Object r15 = r9.get(r10)     // Catch:{ JSONException -> 0x01f4 }
            com.indra.httpclient.json.JSONObject r15 = (com.indra.httpclient.json.JSONObject) r15     // Catch:{ JSONException -> 0x01f4 }
            java.lang.String r1 = "respuestaOpcional"
            boolean r1 = r15.has(r1)     // Catch:{ JSONException -> 0x01df }
            if (r1 == 0) goto L_0x011a
            java.lang.Object r1 = r9.get(r10)     // Catch:{ JSONException -> 0x01df }
            com.indra.httpclient.json.JSONObject r1 = (com.indra.httpclient.json.JSONObject) r1     // Catch:{ JSONException -> 0x01df }
            java.lang.String r15 = "respuestaOpcional"
            java.lang.String r1 = r1.getString(r15)     // Catch:{ JSONException -> 0x01df }
            goto L_0x011c
        L_0x011a:
            java.lang.String r1 = ""
        L_0x011c:
            r11.setIdPregunta(r13)     // Catch:{ JSONException -> 0x01df }
            r11.setTextoPregunta(r14)     // Catch:{ JSONException -> 0x01df }
            r11.setTipoRespuesta(r4)     // Catch:{ JSONException -> 0x01df }
            r11.setCantMaxCaracteres(r2)     // Catch:{ JSONException -> 0x01df }
            r11.setRespuestaOpcional(r1)     // Catch:{ JSONException -> 0x01df }
            java.lang.Object r1 = r9.get(r10)     // Catch:{ JSONException -> 0x01df }
            com.indra.httpclient.json.JSONObject r1 = (com.indra.httpclient.json.JSONObject) r1     // Catch:{ JSONException -> 0x01df }
            java.lang.String r2 = "listaOpciones"
            boolean r1 = r1.has(r2)     // Catch:{ JSONException -> 0x01df }
            if (r1 == 0) goto L_0x0146
            java.lang.Object r1 = r9.get(r10)     // Catch:{ JSONException -> 0x01df }
            com.indra.httpclient.json.JSONObject r1 = (com.indra.httpclient.json.JSONObject) r1     // Catch:{ JSONException -> 0x01df }
            java.lang.String r2 = "listaOpciones"
            com.indra.httpclient.json.JSONObject r1 = r1.getJSONObject(r2)     // Catch:{ JSONException -> 0x01df }
            goto L_0x0147
        L_0x0146:
            r1 = 0
        L_0x0147:
            if (r1 == 0) goto L_0x01c7
            java.lang.String r2 = "opcion"
            boolean r2 = r1.has(r2)     // Catch:{ JSONException -> 0x01df }
            if (r2 == 0) goto L_0x01c7
            java.lang.String r2 = "opcion"
            com.indra.httpclient.json.JSONArray r2 = r1.getJSONArray(r2)     // Catch:{ JSONException -> 0x0158 }
            goto L_0x0166
        L_0x0158:
            com.indra.httpclient.json.JSONArray r2 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x01df }
            r2.<init>()     // Catch:{ JSONException -> 0x01df }
            java.lang.String r4 = "opcion"
            com.indra.httpclient.json.JSONObject r1 = r1.getJSONObject(r4)     // Catch:{ JSONException -> 0x01df }
            r2.put(r1)     // Catch:{ JSONException -> 0x01df }
        L_0x0166:
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ JSONException -> 0x01df }
            r1.<init>()     // Catch:{ JSONException -> 0x01df }
            r4 = 0
        L_0x016c:
            int r13 = r2.length()     // Catch:{ JSONException -> 0x01df }
            if (r4 >= r13) goto L_0x01c1
            ar.com.santander.rio.mbanking.services.soap.beans.body.OpcionBean r13 = new ar.com.santander.rio.mbanking.services.soap.beans.body.OpcionBean     // Catch:{ JSONException -> 0x01df }
            r13.<init>()     // Catch:{ JSONException -> 0x01df }
            java.lang.Object r14 = r2.get(r4)     // Catch:{ JSONException -> 0x01df }
            com.indra.httpclient.json.JSONObject r14 = (com.indra.httpclient.json.JSONObject) r14     // Catch:{ JSONException -> 0x01df }
            java.lang.String r15 = "idOpcion"
            boolean r14 = r14.has(r15)     // Catch:{ JSONException -> 0x01df }
            if (r14 == 0) goto L_0x0192
            java.lang.Object r14 = r2.get(r4)     // Catch:{ JSONException -> 0x01df }
            com.indra.httpclient.json.JSONObject r14 = (com.indra.httpclient.json.JSONObject) r14     // Catch:{ JSONException -> 0x01df }
            java.lang.String r15 = "idOpcion"
            java.lang.String r14 = r14.getString(r15)     // Catch:{ JSONException -> 0x01df }
            goto L_0x0194
        L_0x0192:
            java.lang.String r14 = ""
        L_0x0194:
            java.lang.Object r15 = r2.get(r4)     // Catch:{ JSONException -> 0x01df }
            com.indra.httpclient.json.JSONObject r15 = (com.indra.httpclient.json.JSONObject) r15     // Catch:{ JSONException -> 0x01df }
            r17 = r9
            java.lang.String r9 = "textoOpcion"
            boolean r9 = r15.has(r9)     // Catch:{ JSONException -> 0x01df }
            if (r9 == 0) goto L_0x01b1
            java.lang.Object r9 = r2.get(r4)     // Catch:{ JSONException -> 0x01df }
            com.indra.httpclient.json.JSONObject r9 = (com.indra.httpclient.json.JSONObject) r9     // Catch:{ JSONException -> 0x01df }
            java.lang.String r15 = "textoOpcion"
            java.lang.String r9 = r9.getString(r15)     // Catch:{ JSONException -> 0x01df }
            goto L_0x01b3
        L_0x01b1:
            java.lang.String r9 = ""
        L_0x01b3:
            r13.setIdOpcion(r14)     // Catch:{ JSONException -> 0x01df }
            r13.setTextoOpcion(r9)     // Catch:{ JSONException -> 0x01df }
            r1.add(r13)     // Catch:{ JSONException -> 0x01df }
            int r4 = r4 + 1
            r9 = r17
            goto L_0x016c
        L_0x01c1:
            r17 = r9
            r12.setOpcion(r1)     // Catch:{ JSONException -> 0x01df }
            goto L_0x01c9
        L_0x01c7:
            r17 = r9
        L_0x01c9:
            r11.setListaOpciones(r12)     // Catch:{ JSONException -> 0x01df }
            r8.add(r11)     // Catch:{ JSONException -> 0x01df }
            int r10 = r10 + 1
            r2 = r16
            r9 = r17
            r1 = r18
            goto L_0x0079
        L_0x01d9:
            r16 = r2
            r6.setPregunta(r8)     // Catch:{ JSONException -> 0x01df }
            goto L_0x01e6
        L_0x01df:
            r0 = move-exception
            r2 = r0
            r1 = r18
            goto L_0x01fe
        L_0x01e4:
            r16 = r2
        L_0x01e6:
            r7.setListaPreguntas(r6)     // Catch:{ JSONException -> 0x01f6 }
            r5.setGetPreguntasFamiliaBodyResponseBean(r7)     // Catch:{ JSONException -> 0x01f6 }
            r5.header = r3     // Catch:{ JSONException -> 0x01f6 }
            r1 = r18
            r1.onResponseBean(r5)     // Catch:{ JSONException -> 0x01f4 }
            goto L_0x0204
        L_0x01f4:
            r0 = move-exception
            goto L_0x01fd
        L_0x01f6:
            r0 = move-exception
            r1 = r18
            goto L_0x01fd
        L_0x01fa:
            r0 = move-exception
            r16 = r2
        L_0x01fd:
            r2 = r0
        L_0x01fe:
            r1.onUnknowError(r2)
            goto L_0x0204
        L_0x0202:
            r16 = r2
        L_0x0204:
            return r16
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.services.soap.request.PreguntasFamiliaRequest.parserResponse(com.indra.httpclient.json.JSONObject):boolean");
    }
}
