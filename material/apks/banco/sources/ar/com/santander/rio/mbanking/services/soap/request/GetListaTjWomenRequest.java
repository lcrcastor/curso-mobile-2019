package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.GetListaTjWomenRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetListaTjWomenResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public class GetListaTjWomenRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private GetListaTjWomenRequestBean getListaTjWomenRequestBean;
    private GetListaTjWomenResponseBean getListaTjWomenResponseBean;

    public int getMethod() {
        return 1;
    }

    public void onResponseBean(IBeanWS iBeanWS) {
    }

    public void onUnknowError(Exception exc) {
    }

    public GetListaTjWomenRequest(Context context, GetListaTjWomenRequestBean getListaTjWomenRequestBean2, ErrorRequestServer errorRequestServer) {
        super(context, false);
        this.mErrorRequestServer = errorRequestServer;
        this.getListaTjWomenRequestBean = getListaTjWomenRequestBean2;
        this.getListaTjWomenResponseBean = new GetListaTjWomenResponseBean();
    }

    public GetListaTjWomenRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
        this.getListaTjWomenRequestBean = new GetListaTjWomenRequestBean();
        this.getListaTjWomenResponseBean = new GetListaTjWomenResponseBean();
    }

    public IBeanWS getBeanToRequest() {
        return this.getListaTjWomenRequestBean;
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

    public Class getBeanResponseClass() {
        if (this.getListaTjWomenResponseBean == null) {
            this.getListaTjWomenResponseBean = new GetListaTjWomenResponseBean();
        }
        return this.getListaTjWomenResponseBean.getClass();
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(2:13|14) */
    /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
        r7 = new com.indra.httpclient.json.JSONArray();
        r7.put((java.lang.Object) r14.getJSONObject("listaLeyendas").getJSONObject("leyenda"));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:?, code lost:
        r7 = new com.indra.httpclient.json.JSONArray();
        r7.put((java.lang.Object) r14.getJSONObject("listaUsuarios").getJSONObject("usuario"));
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0085 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:59:0x01d3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean parserResponse(com.indra.httpclient.json.JSONObject r14) {
        /*
            r13 = this;
            boolean r0 = super.parserResponse(r14)
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
            ar.com.santander.rio.mbanking.services.soap.beans.ListaUsuarios r1 = new ar.com.santander.rio.mbanking.services.soap.beans.ListaUsuarios
            r1.<init>()
            ar.com.santander.rio.mbanking.services.soap.beans.body.ListaLeyendas r2 = new ar.com.santander.rio.mbanking.services.soap.beans.body.ListaLeyendas
            r2.<init>()
            boolean r3 = r0.booleanValue()
            if (r3 == 0) goto L_0x02b2
            com.google.gson.Gson r3 = new com.google.gson.Gson
            r3.<init>()
            java.lang.String r4 = "soapenv:Envelope"
            com.indra.httpclient.json.JSONObject r14 = r14.getJSONObject(r4)     // Catch:{ JSONException -> 0x02ac }
            java.lang.String r4 = "soapenv:Body"
            com.indra.httpclient.json.JSONObject r14 = r14.getJSONObject(r4)     // Catch:{ JSONException -> 0x02ac }
            java.lang.String r4 = "xml"
            com.indra.httpclient.json.JSONObject r14 = r14.getJSONObject(r4)     // Catch:{ JSONException -> 0x02ac }
            ar.com.santander.rio.mbanking.services.soap.beans.body.GetListaTjWomenResponseBean r4 = new ar.com.santander.rio.mbanking.services.soap.beans.body.GetListaTjWomenResponseBean     // Catch:{ JSONException -> 0x02ac }
            r4.<init>()     // Catch:{ JSONException -> 0x02ac }
            java.lang.String r5 = "header"
            com.indra.httpclient.json.JSONObject r5 = r14.getJSONObject(r5)     // Catch:{ JSONException -> 0x02ac }
            java.lang.String r5 = r5.toString()     // Catch:{ JSONException -> 0x02ac }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean> r6 = ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean.class
            java.lang.Object r5 = r3.fromJson(r5, r6)     // Catch:{ JSONException -> 0x02ac }
            ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean r5 = (ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean) r5     // Catch:{ JSONException -> 0x02ac }
            ar.com.santander.rio.mbanking.services.soap.beans.body.GetListaTjWomenBodyResponseBean r6 = new ar.com.santander.rio.mbanking.services.soap.beans.body.GetListaTjWomenBodyResponseBean     // Catch:{ JSONException -> 0x02ac }
            r6.<init>()     // Catch:{ JSONException -> 0x02ac }
            java.lang.String r7 = "body"
            com.indra.httpclient.json.JSONObject r14 = r14.getJSONObject(r7)     // Catch:{ JSONException -> 0x02ac }
            java.lang.String r7 = "listaLeyendas"
            boolean r7 = r14.has(r7)     // Catch:{ JSONException -> 0x02ac }
            r8 = 0
            if (r7 == 0) goto L_0x0146
            java.lang.String r7 = "listaLeyendas"
            java.lang.Object r7 = r14.get(r7)     // Catch:{ JSONException -> 0x02ac }
            java.lang.String r7 = r7.toString()     // Catch:{ JSONException -> 0x02ac }
            boolean r7 = android.text.TextUtils.isEmpty(r7)     // Catch:{ JSONException -> 0x02ac }
            if (r7 != 0) goto L_0x0115
            java.lang.String r7 = "listaLeyendas"
            com.indra.httpclient.json.JSONObject r7 = r14.getJSONObject(r7)     // Catch:{ JSONException -> 0x02ac }
            java.lang.String r9 = "leyenda"
            boolean r7 = r7.has(r9)     // Catch:{ JSONException -> 0x02ac }
            if (r7 == 0) goto L_0x0115
            java.lang.String r7 = "listaLeyendas"
            com.indra.httpclient.json.JSONObject r7 = r14.getJSONObject(r7)     // Catch:{ JSONException -> 0x0085 }
            java.lang.String r9 = "leyenda"
            com.indra.httpclient.json.JSONArray r7 = r7.getJSONArray(r9)     // Catch:{ JSONException -> 0x0085 }
            goto L_0x0099
        L_0x0085:
            com.indra.httpclient.json.JSONArray r7 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x02ac }
            r7.<init>()     // Catch:{ JSONException -> 0x02ac }
            java.lang.String r9 = "listaLeyendas"
            com.indra.httpclient.json.JSONObject r9 = r14.getJSONObject(r9)     // Catch:{ JSONException -> 0x02ac }
            java.lang.String r10 = "leyenda"
            com.indra.httpclient.json.JSONObject r9 = r9.getJSONObject(r10)     // Catch:{ JSONException -> 0x02ac }
            r7.put(r9)     // Catch:{ JSONException -> 0x02ac }
        L_0x0099:
            if (r7 == 0) goto L_0x00bc
            r9 = 0
        L_0x009c:
            int r10 = r7.length()     // Catch:{ JSONException -> 0x02ac }
            if (r9 >= r10) goto L_0x0111
            com.indra.httpclient.json.JSONObject r10 = r7.getJSONObject(r9)     // Catch:{ JSONException -> 0x02ac }
            java.lang.String r10 = r10.toString()     // Catch:{ JSONException -> 0x02ac }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda> r11 = ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda.class
            java.lang.Object r10 = r3.fromJson(r10, r11)     // Catch:{ JSONException -> 0x02ac }
            ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda r10 = (ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda) r10     // Catch:{ JSONException -> 0x02ac }
            java.util.List r11 = r2.getLstLeyendas()     // Catch:{ JSONException -> 0x02ac }
            r11.add(r10)     // Catch:{ JSONException -> 0x02ac }
            int r9 = r9 + 1
            goto L_0x009c
        L_0x00bc:
            java.lang.String r7 = "listaLeyendas"
            com.indra.httpclient.json.JSONObject r7 = r14.getJSONObject(r7)     // Catch:{ JSONException -> 0x02ac }
            java.lang.String r9 = "leyenda"
            java.lang.Object r7 = r7.get(r9)     // Catch:{ JSONException -> 0x02ac }
            boolean r7 = r7 instanceof com.indra.httpclient.json.JSONObject     // Catch:{ JSONException -> 0x02ac }
            if (r7 == 0) goto L_0x00ee
            java.lang.String r7 = "listaLeyendas"
            com.indra.httpclient.json.JSONObject r7 = r14.getJSONObject(r7)     // Catch:{ JSONException -> 0x02ac }
            java.lang.String r9 = "leyenda"
            com.indra.httpclient.json.JSONObject r7 = r7.getJSONObject(r9)     // Catch:{ JSONException -> 0x02ac }
            if (r7 == 0) goto L_0x0111
            java.lang.String r7 = r7.toString()     // Catch:{ JSONException -> 0x02ac }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda> r9 = ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda.class
            java.lang.Object r7 = r3.fromJson(r7, r9)     // Catch:{ JSONException -> 0x02ac }
            ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda r7 = (ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda) r7     // Catch:{ JSONException -> 0x02ac }
            java.util.List r9 = r2.getLstLeyendas()     // Catch:{ JSONException -> 0x02ac }
            r9.add(r7)     // Catch:{ JSONException -> 0x02ac }
            goto L_0x0111
        L_0x00ee:
            java.lang.String r7 = "listaLeyendas"
            com.indra.httpclient.json.JSONObject r7 = r14.getJSONObject(r7)     // Catch:{ JSONException -> 0x02ac }
            boolean r7 = r7 instanceof com.indra.httpclient.json.JSONObject     // Catch:{ JSONException -> 0x02ac }
            if (r7 == 0) goto L_0x0111
            java.lang.String r7 = "listaLeyendas"
            com.indra.httpclient.json.JSONObject r7 = r14.getJSONObject(r7)     // Catch:{ JSONException -> 0x02ac }
            java.lang.String r7 = r7.toString()     // Catch:{ JSONException -> 0x02ac }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda> r9 = ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda.class
            java.lang.Object r7 = r3.fromJson(r7, r9)     // Catch:{ JSONException -> 0x02ac }
            ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda r7 = (ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda) r7     // Catch:{ JSONException -> 0x02ac }
            java.util.List r9 = r2.getLstLeyendas()     // Catch:{ JSONException -> 0x02ac }
            r9.add(r7)     // Catch:{ JSONException -> 0x02ac }
        L_0x0111:
            r6.setListaLeyendas(r2)     // Catch:{ JSONException -> 0x02ac }
            goto L_0x0146
        L_0x0115:
            java.lang.String r7 = "listaLeyendas"
            java.lang.Object r7 = r14.get(r7)     // Catch:{ JSONException -> 0x02ac }
            java.lang.String r7 = r7.toString()     // Catch:{ JSONException -> 0x02ac }
            boolean r7 = android.text.TextUtils.isEmpty(r7)     // Catch:{ JSONException -> 0x02ac }
            if (r7 != 0) goto L_0x0146
            java.lang.String r7 = "listaLeyendas"
            com.indra.httpclient.json.JSONObject r7 = r14.getJSONObject(r7)     // Catch:{ JSONException -> 0x02ac }
            if (r7 == 0) goto L_0x0146
            java.lang.String r7 = "listaLeyendas"
            com.indra.httpclient.json.JSONObject r7 = r14.getJSONObject(r7)     // Catch:{ JSONException -> 0x02ac }
            java.lang.String r7 = r7.toString()     // Catch:{ JSONException -> 0x02ac }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda> r9 = ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda.class
            java.lang.Object r7 = r3.fromJson(r7, r9)     // Catch:{ JSONException -> 0x02ac }
            ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda r7 = (ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda) r7     // Catch:{ JSONException -> 0x02ac }
            java.util.List r2 = r2.getLstLeyendas()     // Catch:{ JSONException -> 0x02ac }
            r2.add(r7)     // Catch:{ JSONException -> 0x02ac }
        L_0x0146:
            java.lang.String r2 = "res"
            boolean r2 = r14.has(r2)     // Catch:{ JSONException -> 0x02ac }
            if (r2 == 0) goto L_0x0157
            java.lang.String r2 = "res"
            java.lang.String r2 = r14.getString(r2)     // Catch:{ JSONException -> 0x02ac }
            r6.setRes(r2)     // Catch:{ JSONException -> 0x02ac }
        L_0x0157:
            java.lang.String r2 = "titular"
            boolean r2 = r14.has(r2)     // Catch:{ JSONException -> 0x02ac }
            if (r2 == 0) goto L_0x0168
            java.lang.String r2 = "titular"
            java.lang.String r2 = r14.getString(r2)     // Catch:{ JSONException -> 0x02ac }
            r6.setTitular(r2)     // Catch:{ JSONException -> 0x02ac }
        L_0x0168:
            java.lang.String r2 = "suscriptoTitular"
            boolean r2 = r14.has(r2)     // Catch:{ JSONException -> 0x02ac }
            if (r2 == 0) goto L_0x0179
            java.lang.String r2 = "suscriptoTitular"
            java.lang.String r2 = r14.getString(r2)     // Catch:{ JSONException -> 0x02ac }
            r6.setSuscriptoTitular(r2)     // Catch:{ JSONException -> 0x02ac }
        L_0x0179:
            java.lang.String r2 = "suscriptoAdicional"
            boolean r2 = r14.has(r2)     // Catch:{ JSONException -> 0x02ac }
            if (r2 == 0) goto L_0x018a
            java.lang.String r2 = "suscriptoAdicional"
            java.lang.String r2 = r14.getString(r2)     // Catch:{ JSONException -> 0x02ac }
            r6.setSuscriptoAdicional(r2)     // Catch:{ JSONException -> 0x02ac }
        L_0x018a:
            java.lang.String r2 = "adicionalNoTitular"
            boolean r2 = r14.has(r2)     // Catch:{ JSONException -> 0x02ac }
            if (r2 == 0) goto L_0x019b
            java.lang.String r2 = "adicionalNoTitular"
            java.lang.String r2 = r14.getString(r2)     // Catch:{ JSONException -> 0x02ac }
            r6.setAdicionalNoTitular(r2)     // Catch:{ JSONException -> 0x02ac }
        L_0x019b:
            java.lang.String r2 = "listaUsuarios"
            boolean r2 = r14.has(r2)     // Catch:{ JSONException -> 0x02ac }
            if (r2 == 0) goto L_0x026a
            java.lang.String r2 = "listaUsuarios"
            java.lang.Object r2 = r14.get(r2)     // Catch:{ JSONException -> 0x02ac }
            java.lang.String r2 = r2.toString()     // Catch:{ JSONException -> 0x02ac }
            boolean r2 = android.text.TextUtils.isEmpty(r2)     // Catch:{ JSONException -> 0x02ac }
            if (r2 != 0) goto L_0x0340
            java.lang.String r2 = "listaUsuarios"
            com.indra.httpclient.json.JSONObject r2 = r14.getJSONObject(r2)     // Catch:{ JSONException -> 0x02ac }
            java.lang.String r7 = "usuario"
            boolean r2 = r2.has(r7)     // Catch:{ JSONException -> 0x02ac }
            if (r2 == 0) goto L_0x0340
            com.indra.httpclient.json.JSONArray r2 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x02ac }
            r2.<init>()     // Catch:{ JSONException -> 0x02ac }
            java.lang.String r7 = "listaUsuarios"
            com.indra.httpclient.json.JSONObject r7 = r14.getJSONObject(r7)     // Catch:{ JSONException -> 0x01d3 }
            java.lang.String r9 = "usuario"
            com.indra.httpclient.json.JSONArray r7 = r7.getJSONArray(r9)     // Catch:{ JSONException -> 0x01d3 }
            goto L_0x01e7
        L_0x01d3:
            com.indra.httpclient.json.JSONArray r7 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x02ac }
            r7.<init>()     // Catch:{ JSONException -> 0x02ac }
            java.lang.String r9 = "listaUsuarios"
            com.indra.httpclient.json.JSONObject r14 = r14.getJSONObject(r9)     // Catch:{ JSONException -> 0x02ac }
            java.lang.String r9 = "usuario"
            com.indra.httpclient.json.JSONObject r14 = r14.getJSONObject(r9)     // Catch:{ JSONException -> 0x02ac }
            r7.put(r14)     // Catch:{ JSONException -> 0x02ac }
        L_0x01e7:
            r14 = 0
        L_0x01e8:
            int r9 = r7.length()     // Catch:{ JSONException -> 0x02ac }
            if (r14 >= r9) goto L_0x023e
            com.indra.httpclient.json.JSONObject r9 = r7.getJSONObject(r14)     // Catch:{ JSONException -> 0x02ac }
            java.lang.String r10 = "listaTarjetas"
            boolean r10 = r9.has(r10)     // Catch:{ JSONException -> 0x02ac }
            if (r10 == 0) goto L_0x023b
            java.lang.String r10 = "listaTarjetas"
            com.indra.httpclient.json.JSONObject r10 = r9.getJSONObject(r10)     // Catch:{ JSONException -> 0x02ac }
            java.lang.String r11 = "tarjeta"
            boolean r10 = r10.has(r11)     // Catch:{ JSONException -> 0x02ac }
            if (r10 == 0) goto L_0x023b
            java.lang.String r10 = "listaTarjetas"
            com.indra.httpclient.json.JSONObject r10 = r9.getJSONObject(r10)     // Catch:{ JSONException -> 0x02ac }
            com.indra.httpclient.json.JSONArray r11 = new com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x02ac }
            r11.<init>()     // Catch:{ JSONException -> 0x02ac }
            java.lang.String r12 = "tarjeta"
            com.indra.httpclient.json.JSONArray r12 = r10.getJSONArray(r12)     // Catch:{ JSONException -> 0x021a }
            goto L_0x0224
        L_0x021a:
            java.lang.String r12 = "tarjeta"
            com.indra.httpclient.json.JSONObject r12 = r10.getJSONObject(r12)     // Catch:{ JSONException -> 0x02ac }
            r11.put(r12)     // Catch:{ JSONException -> 0x02ac }
            r12 = r11
        L_0x0224:
            java.lang.String r11 = "tarjeta"
            r10.remove(r11)     // Catch:{ JSONException -> 0x02ac }
            java.lang.String r11 = "tarjeta"
            r10.put(r11, r12)     // Catch:{ JSONException -> 0x02ac }
            java.lang.String r11 = "listaTarjetas"
            r9.remove(r11)     // Catch:{ JSONException -> 0x02ac }
            java.lang.String r11 = "listaTarjetas"
            r9.put(r11, r10)     // Catch:{ JSONException -> 0x02ac }
            r2.put(r14, r9)     // Catch:{ JSONException -> 0x02ac }
        L_0x023b:
            int r14 = r14 + 1
            goto L_0x01e8
        L_0x023e:
            int r14 = r2.length()     // Catch:{ JSONException -> 0x02ac }
            if (r8 >= r14) goto L_0x025e
            com.indra.httpclient.json.JSONObject r14 = r2.getJSONObject(r8)     // Catch:{ JSONException -> 0x02ac }
            java.lang.String r14 = r14.toString()     // Catch:{ JSONException -> 0x02ac }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.Usuario> r7 = ar.com.santander.rio.mbanking.services.soap.beans.Usuario.class
            java.lang.Object r14 = r3.fromJson(r14, r7)     // Catch:{ JSONException -> 0x02ac }
            ar.com.santander.rio.mbanking.services.soap.beans.Usuario r14 = (ar.com.santander.rio.mbanking.services.soap.beans.Usuario) r14     // Catch:{ JSONException -> 0x02ac }
            java.util.List r7 = r1.getUsuario()     // Catch:{ JSONException -> 0x02ac }
            r7.add(r14)     // Catch:{ JSONException -> 0x02ac }
            int r8 = r8 + 1
            goto L_0x023e
        L_0x025e:
            r6.setListaUsuarios(r1)     // Catch:{ JSONException -> 0x02ac }
            r4.headerBean = r5     // Catch:{ JSONException -> 0x02ac }
            r4.getListaTjWomenBodyResponseBean = r6     // Catch:{ JSONException -> 0x02ac }
            r13.onResponseBean(r4)     // Catch:{ JSONException -> 0x02ac }
            goto L_0x0340
        L_0x026a:
            java.lang.String r1 = "resCod"
            boolean r1 = r14.has(r1)     // Catch:{ JSONException -> 0x02ac }
            if (r1 == 0) goto L_0x0279
            java.lang.String r1 = "resCod"
            java.lang.String r1 = r14.getString(r1)     // Catch:{ JSONException -> 0x02ac }
            goto L_0x027b
        L_0x0279:
            java.lang.String r1 = ""
        L_0x027b:
            r6.resCod = r1     // Catch:{ JSONException -> 0x02ac }
            java.lang.String r1 = "resDesc"
            boolean r1 = r14.has(r1)     // Catch:{ JSONException -> 0x02ac }
            if (r1 == 0) goto L_0x028c
            java.lang.String r1 = "resDesc"
            java.lang.String r1 = r14.getString(r1)     // Catch:{ JSONException -> 0x02ac }
            goto L_0x028e
        L_0x028c:
            java.lang.String r1 = ""
        L_0x028e:
            r6.resDesc = r1     // Catch:{ JSONException -> 0x02ac }
            java.lang.String r1 = "res"
            boolean r1 = r14.has(r1)     // Catch:{ JSONException -> 0x02ac }
            if (r1 == 0) goto L_0x029f
            java.lang.String r1 = "res"
            java.lang.String r14 = r14.getString(r1)     // Catch:{ JSONException -> 0x02ac }
            goto L_0x02a1
        L_0x029f:
            java.lang.String r14 = ""
        L_0x02a1:
            r6.res = r14     // Catch:{ JSONException -> 0x02ac }
            r4.headerBean = r5     // Catch:{ JSONException -> 0x02ac }
            r4.getListaTjWomenBodyResponseBean = r6     // Catch:{ JSONException -> 0x02ac }
            r13.onResponseBean(r4)     // Catch:{ JSONException -> 0x02ac }
            goto L_0x0340
        L_0x02ac:
            r14 = move-exception
            r13.onUnknowError(r14)
            goto L_0x0340
        L_0x02b2:
            com.google.gson.Gson r1 = new com.google.gson.Gson
            r1.<init>()
            java.lang.String r2 = "soapenv:Envelope"
            com.indra.httpclient.json.JSONObject r14 = r14.getJSONObject(r2)     // Catch:{ JSONException -> 0x033c }
            java.lang.String r2 = "soapenv:Body"
            com.indra.httpclient.json.JSONObject r14 = r14.getJSONObject(r2)     // Catch:{ JSONException -> 0x033c }
            java.lang.String r2 = "xml"
            com.indra.httpclient.json.JSONObject r14 = r14.getJSONObject(r2)     // Catch:{ JSONException -> 0x033c }
            ar.com.santander.rio.mbanking.services.soap.beans.body.GetListaTjWomenResponseBean r2 = new ar.com.santander.rio.mbanking.services.soap.beans.body.GetListaTjWomenResponseBean     // Catch:{ JSONException -> 0x033c }
            r2.<init>()     // Catch:{ JSONException -> 0x033c }
            java.lang.String r3 = "header"
            com.indra.httpclient.json.JSONObject r3 = r14.getJSONObject(r3)     // Catch:{ JSONException -> 0x033c }
            java.lang.String r3 = r3.toString()     // Catch:{ JSONException -> 0x033c }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean> r4 = ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean.class
            java.lang.Object r1 = r1.fromJson(r3, r4)     // Catch:{ JSONException -> 0x033c }
            ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean r1 = (ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean) r1     // Catch:{ JSONException -> 0x033c }
            ar.com.santander.rio.mbanking.services.soap.beans.body.GetListaTjWomenBodyResponseBean r3 = new ar.com.santander.rio.mbanking.services.soap.beans.body.GetListaTjWomenBodyResponseBean     // Catch:{ JSONException -> 0x033c }
            r3.<init>()     // Catch:{ JSONException -> 0x033c }
            java.lang.String r4 = "body"
            com.indra.httpclient.json.JSONObject r14 = r14.getJSONObject(r4)     // Catch:{ JSONException -> 0x033c }
            java.lang.String r4 = "resTitulo"
            boolean r4 = r14.has(r4)     // Catch:{ JSONException -> 0x033c }
            if (r4 == 0) goto L_0x02fa
            java.lang.String r4 = "resTitulo"
            java.lang.String r4 = r14.getString(r4)     // Catch:{ JSONException -> 0x033c }
            goto L_0x02fc
        L_0x02fa:
            java.lang.String r4 = ""
        L_0x02fc:
            r3.resTitle = r4     // Catch:{ JSONException -> 0x033c }
            java.lang.String r4 = "resCod"
            boolean r4 = r14.has(r4)     // Catch:{ JSONException -> 0x033c }
            if (r4 == 0) goto L_0x030d
            java.lang.String r4 = "resCod"
            java.lang.String r4 = r14.getString(r4)     // Catch:{ JSONException -> 0x033c }
            goto L_0x030f
        L_0x030d:
            java.lang.String r4 = ""
        L_0x030f:
            r3.resCod = r4     // Catch:{ JSONException -> 0x033c }
            java.lang.String r4 = "resDesc"
            boolean r4 = r14.has(r4)     // Catch:{ JSONException -> 0x033c }
            if (r4 == 0) goto L_0x0320
            java.lang.String r4 = "resDesc"
            java.lang.String r4 = r14.getString(r4)     // Catch:{ JSONException -> 0x033c }
            goto L_0x0322
        L_0x0320:
            java.lang.String r4 = ""
        L_0x0322:
            r3.resDesc = r4     // Catch:{ JSONException -> 0x033c }
            java.lang.String r4 = "res"
            boolean r4 = r14.has(r4)     // Catch:{ JSONException -> 0x033c }
            if (r4 == 0) goto L_0x0333
            java.lang.String r4 = "res"
            java.lang.String r14 = r14.getString(r4)     // Catch:{ JSONException -> 0x033c }
            goto L_0x0335
        L_0x0333:
            java.lang.String r14 = ""
        L_0x0335:
            r3.res = r14     // Catch:{ JSONException -> 0x033c }
            r2.headerBean = r1     // Catch:{ JSONException -> 0x033c }
            r2.getListaTjWomenBodyResponseBean = r3     // Catch:{ JSONException -> 0x033c }
            goto L_0x0340
        L_0x033c:
            r14 = move-exception
            r13.onUnknowError(r14)
        L_0x0340:
            boolean r14 = r0.booleanValue()
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.services.soap.request.GetListaTjWomenRequest.parserResponse(com.indra.httpclient.json.JSONObject):boolean");
    }
}
