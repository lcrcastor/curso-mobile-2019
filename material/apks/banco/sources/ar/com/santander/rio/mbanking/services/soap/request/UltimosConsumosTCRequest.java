package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.UltimosConsumosTCRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.UltimosConsumosTCResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class UltimosConsumosTCRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private final String TAG = UltimosConsumosTCRequest.class.getName();
    private UltimosConsumosTCRequestBean ultimosConsumosTCRequestBean;
    private UltimosConsumosTCResponseBean ultimosConsumosTCResponseBean;

    public int getMethod() {
        return 1;
    }

    public UltimosConsumosTCRequest(Context context, UltimosConsumosTCRequestBean ultimosConsumosTCRequestBean2, ErrorRequestServer errorRequestServer) {
        super(context);
        this.ultimosConsumosTCRequestBean = ultimosConsumosTCRequestBean2;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.ultimosConsumosTCRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.ultimosConsumosTCResponseBean == null) {
            this.ultimosConsumosTCResponseBean = new UltimosConsumosTCResponseBean();
        }
        return this.ultimosConsumosTCResponseBean.getClass();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:143:0x077c, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:144:0x077d, code lost:
        r16 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:147:0x078d, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:148:0x078e, code lost:
        r16 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x010d, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        android.util.Log.d(getClass().getCanonicalName(), r0.toString());
        r11 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0182, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0183, code lost:
        r2 = r0;
        r17 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x01b2, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x01b4, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x01b5, code lost:
        r17 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x01b7, code lost:
        r2 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:?, code lost:
        android.util.Log.e(getClass().getCanonicalName(), r2.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x0291, code lost:
        r0 = e;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:132:0x0728  */
    /* JADX WARNING: Removed duplicated region for block: B:147:0x078d A[ExcHandler: JSONException (e com.indra.httpclient.json.JSONException), Splitter:B:3:0x0011] */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x0291 A[Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }, ExcHandler: JSONException (e com.indra.httpclient.json.JSONException), PHI: r1 r16 
      PHI: (r1v12 ar.com.santander.rio.mbanking.services.soap.request.UltimosConsumosTCRequest) = (r1v9 ar.com.santander.rio.mbanking.services.soap.request.UltimosConsumosTCRequest), (r1v9 ar.com.santander.rio.mbanking.services.soap.request.UltimosConsumosTCRequest), (r1v0 ar.com.santander.rio.mbanking.services.soap.request.UltimosConsumosTCRequest), (r1v0 ar.com.santander.rio.mbanking.services.soap.request.UltimosConsumosTCRequest), (r1v0 ar.com.santander.rio.mbanking.services.soap.request.UltimosConsumosTCRequest), (r1v0 ar.com.santander.rio.mbanking.services.soap.request.UltimosConsumosTCRequest), (r1v0 ar.com.santander.rio.mbanking.services.soap.request.UltimosConsumosTCRequest), (r1v0 ar.com.santander.rio.mbanking.services.soap.request.UltimosConsumosTCRequest), (r1v0 ar.com.santander.rio.mbanking.services.soap.request.UltimosConsumosTCRequest), (r1v0 ar.com.santander.rio.mbanking.services.soap.request.UltimosConsumosTCRequest), (r1v0 ar.com.santander.rio.mbanking.services.soap.request.UltimosConsumosTCRequest), (r1v0 ar.com.santander.rio.mbanking.services.soap.request.UltimosConsumosTCRequest) binds: [B:137:0x0770, B:138:?, B:27:0x0136, B:28:?, B:30:0x0170, B:51:0x01b8, B:31:?, B:37:0x0187, B:38:?, B:42:0x018f, B:33:0x0174, B:34:?] A[DONT_GENERATE, DONT_INLINE]
      PHI: (r16v11 boolean) = (r16v7 boolean), (r16v7 boolean), (r16v16 boolean), (r16v16 boolean), (r16v16 boolean), (r16v16 boolean), (r16v16 boolean), (r16v16 boolean), (r16v16 boolean), (r16v16 boolean), (r16v16 boolean), (r16v16 boolean) binds: [B:137:0x0770, B:138:?, B:27:0x0136, B:28:?, B:30:0x0170, B:51:0x01b8, B:31:?, B:37:0x0187, B:38:?, B:42:0x018f, B:33:0x0174, B:34:?] A[DONT_GENERATE, DONT_INLINE], Splitter:B:27:0x0136] */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x033b A[SYNTHETIC, Splitter:B:89:0x033b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean parserResponse(com.indra.httpclient.json.JSONObject r25) {
        /*
            r24 = this;
            r1 = r24
            boolean r2 = super.parserResponse(r25)
            if (r2 == 0) goto L_0x079e
            com.google.gson.Gson r3 = new com.google.gson.Gson
            r3.<init>()
            java.lang.String r4 = "soapenv:Envelope"
            r5 = r25
            com.indra.httpclient.json.JSONObject r4 = r5.getJSONObject(r4)     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            java.lang.String r5 = "soapenv:Body"
            com.indra.httpclient.json.JSONObject r4 = r4.getJSONObject(r5)     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            java.lang.String r5 = "xml"
            com.indra.httpclient.json.JSONObject r4 = r4.getJSONObject(r5)     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            ar.com.santander.rio.mbanking.services.soap.beans.UltimosConsumosTCResponseBean r5 = new ar.com.santander.rio.mbanking.services.soap.beans.UltimosConsumosTCResponseBean     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            r5.<init>()     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            java.lang.String r6 = "header"
            com.indra.httpclient.json.JSONObject r6 = r4.getJSONObject(r6)     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            java.lang.String r6 = r6.toString()     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            java.lang.Class<ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean> r7 = ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean.class
            java.lang.Object r6 = r3.fromJson(r6, r7)     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean r6 = (ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean) r6     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            r5.setHeader(r6)     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            ar.com.santander.rio.mbanking.services.soap.beans.body.UltimosConsumosTCBodyResponseBean r6 = new ar.com.santander.rio.mbanking.services.soap.beans.body.UltimosConsumosTCBodyResponseBean     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            r6.<init>()     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            ar.com.santander.rio.mbanking.services.model.general.DocumentConsumos r7 = new ar.com.santander.rio.mbanking.services.model.general.DocumentConsumos     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            r7.<init>()     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            ar.com.santander.rio.mbanking.services.model.general.Totales r8 = new ar.com.santander.rio.mbanking.services.model.general.Totales     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            r8.<init>()     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            ar.com.santander.rio.mbanking.services.model.general.AuthTarjeta r8 = new ar.com.santander.rio.mbanking.services.model.general.AuthTarjeta     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            r8.<init>()     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            ar.com.santander.rio.mbanking.services.model.general.MovimientoConsumos r8 = new ar.com.santander.rio.mbanking.services.model.general.MovimientoConsumos     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            r8.<init>()     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            ar.com.santander.rio.mbanking.services.model.general.TotalMovAuto r8 = new ar.com.santander.rio.mbanking.services.model.general.TotalMovAuto     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            r8.<init>()     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            java.lang.String r8 = "body"
            com.indra.httpclient.json.JSONObject r4 = r4.getJSONObject(r8)     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            ar.com.santander.rio.mbanking.services.model.general.DatosConsumos r8 = new ar.com.santander.rio.mbanking.services.model.general.DatosConsumos     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            r8.<init>()     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            java.lang.String r8 = "tarjetas"
            com.indra.httpclient.json.JSONObject r8 = r4.getJSONObject(r8)     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            java.lang.String r9 = "tarjeta"
            com.indra.httpclient.json.JSONObject r8 = r8.getJSONObject(r9)     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            java.lang.String r9 = "document"
            com.indra.httpclient.json.JSONObject r8 = r8.getJSONObject(r9)     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            java.lang.String r9 = "datos"
            com.indra.httpclient.json.JSONObject r8 = r8.getJSONObject(r9)     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            java.lang.String r8 = r8.toString()     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            java.lang.Class<ar.com.santander.rio.mbanking.services.model.general.DatosConsumos> r9 = ar.com.santander.rio.mbanking.services.model.general.DatosConsumos.class
            java.lang.Object r8 = r3.fromJson(r8, r9)     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            ar.com.santander.rio.mbanking.services.model.general.DatosConsumos r8 = (ar.com.santander.rio.mbanking.services.model.general.DatosConsumos) r8     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            ar.com.santander.rio.mbanking.services.model.general.AutorizacionesConsumos r9 = new ar.com.santander.rio.mbanking.services.model.general.AutorizacionesConsumos     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            r9.<init>()     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            java.lang.String r10 = "tarjetas"
            com.indra.httpclient.json.JSONObject r10 = r4.getJSONObject(r10)     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            java.lang.String r11 = "tarjeta"
            com.indra.httpclient.json.JSONObject r10 = r10.getJSONObject(r11)     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            java.lang.String r11 = "document"
            com.indra.httpclient.json.JSONObject r10 = r10.getJSONObject(r11)     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            java.lang.String r11 = "autorizaciones"
            com.indra.httpclient.json.JSONObject r10 = r10.getJSONObject(r11)     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            java.lang.String r11 = "resAutorizacion"
            java.lang.Object r10 = r10.get(r11)     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            java.lang.String r10 = r10.toString()     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            java.lang.String r11 = ""
            java.lang.String r12 = "tarjetas"
            com.indra.httpclient.json.JSONObject r12 = r4.getJSONObject(r12)     // Catch:{ Exception -> 0x00d4, JSONException -> 0x078d }
            java.lang.String r13 = "tarjeta"
            com.indra.httpclient.json.JSONObject r12 = r12.getJSONObject(r13)     // Catch:{ Exception -> 0x00d4, JSONException -> 0x078d }
            java.lang.String r13 = "document"
            com.indra.httpclient.json.JSONObject r12 = r12.getJSONObject(r13)     // Catch:{ Exception -> 0x00d4, JSONException -> 0x078d }
            java.lang.String r13 = "autorizaciones"
            com.indra.httpclient.json.JSONObject r12 = r12.getJSONObject(r13)     // Catch:{ Exception -> 0x00d4, JSONException -> 0x078d }
            java.lang.String r13 = "mensajeAutorizacion"
            java.lang.Object r12 = r12.get(r13)     // Catch:{ Exception -> 0x00d4, JSONException -> 0x078d }
            java.lang.String r12 = r12.toString()     // Catch:{ Exception -> 0x00d4, JSONException -> 0x078d }
            goto L_0x00e6
        L_0x00d4:
            r0 = move-exception
            r12 = r0
            java.lang.Class r13 = r24.getClass()     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            java.lang.String r13 = r13.getCanonicalName()     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            java.lang.String r12 = r12.toString()     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            android.util.Log.d(r13, r12)     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            r12 = r11
        L_0x00e6:
            java.lang.String r11 = "0"
            boolean r11 = r10.equals(r11)     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            if (r11 == 0) goto L_0x02d5
            java.lang.String r11 = "tarjetas"
            com.indra.httpclient.json.JSONObject r11 = r4.getJSONObject(r11)     // Catch:{ Exception -> 0x010d, JSONException -> 0x078d }
            java.lang.String r15 = "tarjeta"
            com.indra.httpclient.json.JSONObject r11 = r11.getJSONObject(r15)     // Catch:{ Exception -> 0x010d, JSONException -> 0x078d }
            java.lang.String r15 = "document"
            com.indra.httpclient.json.JSONObject r11 = r11.getJSONObject(r15)     // Catch:{ Exception -> 0x010d, JSONException -> 0x078d }
            java.lang.String r15 = "autorizaciones"
            com.indra.httpclient.json.JSONObject r11 = r11.getJSONObject(r15)     // Catch:{ Exception -> 0x010d, JSONException -> 0x078d }
            java.lang.String r15 = "tarjeta"
            java.lang.Object r11 = r11.get(r15)     // Catch:{ Exception -> 0x010d, JSONException -> 0x078d }
            goto L_0x011f
        L_0x010d:
            r0 = move-exception
            r11 = r0
            java.lang.Class r15 = r24.getClass()     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            java.lang.String r15 = r15.getCanonicalName()     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            java.lang.String r11 = r11.toString()     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            android.util.Log.d(r15, r11)     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            r11 = 0
        L_0x011f:
            java.util.ArrayList r15 = new java.util.ArrayList     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            r15.<init>()     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            if (r11 == 0) goto L_0x0294
            boolean r13 = r11 instanceof com.indra.httpclient.json.JSONObject     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            if (r13 == 0) goto L_0x01d2
            ar.com.santander.rio.mbanking.services.model.general.AuthTarjeta r13 = new ar.com.santander.rio.mbanking.services.model.general.AuthTarjeta     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            r13.<init>()     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            r14 = r11
            com.indra.httpclient.json.JSONObject r14 = (com.indra.httpclient.json.JSONObject) r14     // Catch:{ JSONException -> 0x078d, Exception -> 0x077c }
            r16 = r2
            java.lang.String r2 = "dolares"
            java.lang.Object r2 = r14.get(r2)     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            java.lang.String r2 = r2.toString()     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            r13.setDolares(r2)     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            r2 = r11
            com.indra.httpclient.json.JSONObject r2 = (com.indra.httpclient.json.JSONObject) r2     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            java.lang.String r14 = "pesos"
            java.lang.Object r2 = r2.get(r14)     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            java.lang.String r2 = r2.toString()     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            r13.setPesos(r2)     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            r2 = r11
            com.indra.httpclient.json.JSONObject r2 = (com.indra.httpclient.json.JSONObject) r2     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            java.lang.String r14 = "numero"
            java.lang.Object r2 = r2.get(r14)     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            java.lang.String r2 = r2.toString()     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            r13.setNumero(r2)     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            com.indra.httpclient.json.JSONObject r11 = (com.indra.httpclient.json.JSONObject) r11     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            java.lang.String r2 = "autorizacion"
            java.lang.Object r2 = r11.get(r2)     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            java.util.ArrayList r11 = new java.util.ArrayList     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            r11.<init>()     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            if (r2 == 0) goto L_0x01c8
            boolean r14 = r2 instanceof com.indra.httpclient.json.JSONObject     // Catch:{ Exception -> 0x01b4, JSONException -> 0x0291 }
            if (r14 == 0) goto L_0x0187
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0182, JSONException -> 0x0291 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.model.general.Autorizacion> r14 = ar.com.santander.rio.mbanking.services.model.general.Autorizacion.class
            java.lang.Object r2 = r3.fromJson(r2, r14)     // Catch:{ Exception -> 0x0182, JSONException -> 0x0291 }
            r11.add(r2)     // Catch:{ Exception -> 0x0182, JSONException -> 0x0291 }
            goto L_0x01c8
        L_0x0182:
            r0 = move-exception
            r2 = r0
            r17 = r5
            goto L_0x01b8
        L_0x0187:
            boolean r14 = r2 instanceof com.indra.httpclient.json.JSONArray     // Catch:{ Exception -> 0x01b4, JSONException -> 0x0291 }
            if (r14 == 0) goto L_0x01c8
            r17 = r5
            r14 = 0
        L_0x018e:
            r5 = r2
            com.indra.httpclient.json.JSONArray r5 = (com.indra.httpclient.json.JSONArray) r5     // Catch:{ Exception -> 0x01b2, JSONException -> 0x0291 }
            int r5 = r5.length()     // Catch:{ Exception -> 0x01b2, JSONException -> 0x0291 }
            if (r14 >= r5) goto L_0x01ca
            r5 = r2
            com.indra.httpclient.json.JSONArray r5 = (com.indra.httpclient.json.JSONArray) r5     // Catch:{ Exception -> 0x01b2, JSONException -> 0x0291 }
            java.lang.Object r5 = r5.get(r14)     // Catch:{ Exception -> 0x01b2, JSONException -> 0x0291 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x01b2, JSONException -> 0x0291 }
            r18 = r2
            java.lang.Class<ar.com.santander.rio.mbanking.services.model.general.Autorizacion> r2 = ar.com.santander.rio.mbanking.services.model.general.Autorizacion.class
            java.lang.Object r2 = r3.fromJson(r5, r2)     // Catch:{ Exception -> 0x01b2, JSONException -> 0x0291 }
            r11.add(r2)     // Catch:{ Exception -> 0x01b2, JSONException -> 0x0291 }
            int r14 = r14 + 1
            r2 = r18
            goto L_0x018e
        L_0x01b2:
            r0 = move-exception
            goto L_0x01b7
        L_0x01b4:
            r0 = move-exception
            r17 = r5
        L_0x01b7:
            r2 = r0
        L_0x01b8:
            java.lang.Class r5 = r24.getClass()     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            java.lang.String r5 = r5.getCanonicalName()     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            java.lang.String r2 = r2.toString()     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            android.util.Log.e(r5, r2)     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            goto L_0x01ca
        L_0x01c8:
            r17 = r5
        L_0x01ca:
            r13.setAutorizacion(r11)     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            r15.add(r13)     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            goto L_0x0298
        L_0x01d2:
            r16 = r2
            r17 = r5
            boolean r2 = r11 instanceof com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            if (r2 == 0) goto L_0x0298
            r2 = 0
        L_0x01db:
            r5 = r11
            com.indra.httpclient.json.JSONArray r5 = (com.indra.httpclient.json.JSONArray) r5     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            int r5 = r5.length()     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            if (r2 >= r5) goto L_0x0298
            ar.com.santander.rio.mbanking.services.model.general.AuthTarjeta r5 = new ar.com.santander.rio.mbanking.services.model.general.AuthTarjeta     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            r5.<init>()     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            r13 = r11
            com.indra.httpclient.json.JSONArray r13 = (com.indra.httpclient.json.JSONArray) r13     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            java.lang.Object r13 = r13.get(r2)     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            com.indra.httpclient.json.JSONObject r13 = (com.indra.httpclient.json.JSONObject) r13     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            java.lang.String r14 = "dolares"
            java.lang.Object r13 = r13.get(r14)     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            java.lang.String r13 = r13.toString()     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            r5.setDolares(r13)     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            r13 = r11
            com.indra.httpclient.json.JSONArray r13 = (com.indra.httpclient.json.JSONArray) r13     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            java.lang.Object r13 = r13.get(r2)     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            com.indra.httpclient.json.JSONObject r13 = (com.indra.httpclient.json.JSONObject) r13     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            java.lang.String r14 = "pesos"
            java.lang.Object r13 = r13.get(r14)     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            java.lang.String r13 = r13.toString()     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            r5.setPesos(r13)     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            r13 = r11
            com.indra.httpclient.json.JSONArray r13 = (com.indra.httpclient.json.JSONArray) r13     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            java.lang.Object r13 = r13.get(r2)     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            com.indra.httpclient.json.JSONObject r13 = (com.indra.httpclient.json.JSONObject) r13     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            java.lang.String r14 = "numero"
            java.lang.Object r13 = r13.get(r14)     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            java.lang.String r13 = r13.toString()     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            r5.setNumero(r13)     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            r13 = r11
            com.indra.httpclient.json.JSONArray r13 = (com.indra.httpclient.json.JSONArray) r13     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            java.lang.Object r13 = r13.get(r2)     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            com.indra.httpclient.json.JSONObject r13 = (com.indra.httpclient.json.JSONObject) r13     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            java.lang.String r14 = "autorizacion"
            java.lang.Object r13 = r13.get(r14)     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            java.util.ArrayList r14 = new java.util.ArrayList     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            r14.<init>()     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            if (r13 == 0) goto L_0x027e
            r19 = r11
            boolean r11 = r13 instanceof com.indra.httpclient.json.JSONObject     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            if (r11 == 0) goto L_0x0255
            java.lang.String r11 = r13.toString()     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            java.lang.Class<ar.com.santander.rio.mbanking.services.model.general.Autorizacion> r13 = ar.com.santander.rio.mbanking.services.model.general.Autorizacion.class
            java.lang.Object r11 = r3.fromJson(r11, r13)     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            r14.add(r11)     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            goto L_0x0280
        L_0x0255:
            boolean r11 = r13 instanceof com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            if (r11 == 0) goto L_0x0280
            r11 = 0
        L_0x025a:
            r1 = r13
            com.indra.httpclient.json.JSONArray r1 = (com.indra.httpclient.json.JSONArray) r1     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            int r1 = r1.length()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            if (r11 >= r1) goto L_0x0280
            r1 = r13
            com.indra.httpclient.json.JSONArray r1 = (com.indra.httpclient.json.JSONArray) r1     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.Object r1 = r1.get(r11)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r1 = r1.toString()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r20 = r13
            java.lang.Class<ar.com.santander.rio.mbanking.services.model.general.Autorizacion> r13 = ar.com.santander.rio.mbanking.services.model.general.Autorizacion.class
            java.lang.Object r1 = r3.fromJson(r1, r13)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r14.add(r1)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            int r11 = r11 + 1
            r13 = r20
            goto L_0x025a
        L_0x027e:
            r19 = r11
        L_0x0280:
            r5.setAutorizacion(r14)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r15.add(r5)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            int r2 = r2 + 1
            r11 = r19
            r1 = r24
            goto L_0x01db
        L_0x028e:
            r0 = move-exception
            goto L_0x077f
        L_0x0291:
            r0 = move-exception
            goto L_0x0790
        L_0x0294:
            r16 = r2
            r17 = r5
        L_0x0298:
            java.lang.String r1 = "tarjetas"
            com.indra.httpclient.json.JSONObject r1 = r4.getJSONObject(r1)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r2 = "tarjeta"
            com.indra.httpclient.json.JSONObject r1 = r1.getJSONObject(r2)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r2 = "document"
            com.indra.httpclient.json.JSONObject r1 = r1.getJSONObject(r2)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r2 = "autorizaciones"
            com.indra.httpclient.json.JSONObject r1 = r1.getJSONObject(r2)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r2 = "totales"
            com.indra.httpclient.json.JSONObject r1 = r1.getJSONObject(r2)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r1 = r1.toString()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.model.general.Totales> r2 = ar.com.santander.rio.mbanking.services.model.general.Totales.class
            java.lang.Object r1 = r3.fromJson(r1, r2)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            ar.com.santander.rio.mbanking.services.model.general.Totales r1 = (ar.com.santander.rio.mbanking.services.model.general.Totales) r1     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r9.setTotales(r1)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r9.setTarjeta(r15)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            goto L_0x02d9
        L_0x02c9:
            r0 = move-exception
            r2 = r0
            r1 = r24
            goto L_0x0780
        L_0x02cf:
            r0 = move-exception
            r2 = r0
            r1 = r24
            goto L_0x0791
        L_0x02d5:
            r16 = r2
            r17 = r5
        L_0x02d9:
            r9.setResAutorizacion(r10)     // Catch:{ JSONException -> 0x0778, Exception -> 0x0774 }
            r9.setMensajeAutorizacion(r12)     // Catch:{ JSONException -> 0x0778, Exception -> 0x0774 }
            ar.com.santander.rio.mbanking.services.model.general.MovimientosConsumos r1 = new ar.com.santander.rio.mbanking.services.model.general.MovimientosConsumos     // Catch:{ JSONException -> 0x0778, Exception -> 0x0774 }
            r1.<init>()     // Catch:{ JSONException -> 0x0778, Exception -> 0x0774 }
            ar.com.santander.rio.mbanking.services.model.general.Totales r2 = new ar.com.santander.rio.mbanking.services.model.general.Totales     // Catch:{ JSONException -> 0x0778, Exception -> 0x0774 }
            r2.<init>()     // Catch:{ JSONException -> 0x0778, Exception -> 0x0774 }
            java.lang.String r2 = "tarjetas"
            com.indra.httpclient.json.JSONObject r2 = r4.getJSONObject(r2)     // Catch:{ JSONException -> 0x0778, Exception -> 0x0774 }
            java.lang.String r5 = "tarjeta"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r5)     // Catch:{ JSONException -> 0x0778, Exception -> 0x0774 }
            java.lang.String r5 = "document"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r5)     // Catch:{ JSONException -> 0x0778, Exception -> 0x0774 }
            java.lang.String r5 = "movimientos"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r5)     // Catch:{ JSONException -> 0x0778, Exception -> 0x0774 }
            java.lang.String r5 = "totales"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r5)     // Catch:{ JSONException -> 0x0778, Exception -> 0x0774 }
            java.lang.String r2 = r2.toString()     // Catch:{ JSONException -> 0x0778, Exception -> 0x0774 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.model.general.Totales> r5 = ar.com.santander.rio.mbanking.services.model.general.Totales.class
            java.lang.Object r2 = r3.fromJson(r2, r5)     // Catch:{ JSONException -> 0x0778, Exception -> 0x0774 }
            ar.com.santander.rio.mbanking.services.model.general.Totales r2 = (ar.com.santander.rio.mbanking.services.model.general.Totales) r2     // Catch:{ JSONException -> 0x0778, Exception -> 0x0774 }
            r1.setTotales(r2)     // Catch:{ JSONException -> 0x0778, Exception -> 0x0774 }
            java.util.ArrayList r5 = new java.util.ArrayList     // Catch:{ JSONException -> 0x0778, Exception -> 0x0774 }
            r5.<init>()     // Catch:{ JSONException -> 0x0778, Exception -> 0x0774 }
            java.lang.String r10 = "tarjetas"
            com.indra.httpclient.json.JSONObject r10 = r4.getJSONObject(r10)     // Catch:{ JSONException -> 0x0778, Exception -> 0x0774 }
            java.lang.String r11 = "tarjeta"
            com.indra.httpclient.json.JSONObject r10 = r10.getJSONObject(r11)     // Catch:{ JSONException -> 0x0778, Exception -> 0x0774 }
            java.lang.String r11 = "document"
            com.indra.httpclient.json.JSONObject r10 = r10.getJSONObject(r11)     // Catch:{ JSONException -> 0x0778, Exception -> 0x0774 }
            java.lang.String r11 = "movimientos"
            com.indra.httpclient.json.JSONObject r10 = r10.getJSONObject(r11)     // Catch:{ JSONException -> 0x0778, Exception -> 0x0774 }
            java.lang.String r11 = "tarjeta"
            java.lang.Object r10 = r10.get(r11)     // Catch:{ JSONException -> 0x0778, Exception -> 0x0774 }
            if (r10 == 0) goto L_0x0728
            boolean r11 = r10 instanceof com.indra.httpclient.json.JSONObject     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            if (r11 == 0) goto L_0x0514
            ar.com.santander.rio.mbanking.services.model.general.TarjetaConsumos r11 = new ar.com.santander.rio.mbanking.services.model.general.TarjetaConsumos     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r11.<init>()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r12 = r10
            com.indra.httpclient.json.JSONObject r12 = (com.indra.httpclient.json.JSONObject) r12     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r13 = "dolares"
            java.lang.Object r12 = r12.get(r13)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r12 = r12.toString()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r13 = r10
            com.indra.httpclient.json.JSONObject r13 = (com.indra.httpclient.json.JSONObject) r13     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r14 = "pesos"
            java.lang.Object r13 = r13.get(r14)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r13 = r13.toString()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r14 = r10
            com.indra.httpclient.json.JSONObject r14 = (com.indra.httpclient.json.JSONObject) r14     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r15 = "codigoTarjeta"
            java.lang.Object r14 = r14.get(r15)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r14 = r14.toString()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r11.setDolares(r12)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r11.setPesos(r13)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r11.setCodigoTarjeta(r14)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r12 = r10
            com.indra.httpclient.json.JSONObject r12 = (com.indra.httpclient.json.JSONObject) r12     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r13 = "movimiento"
            boolean r12 = r12.has(r13)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            if (r12 == 0) goto L_0x0388
            com.indra.httpclient.json.JSONObject r10 = (com.indra.httpclient.json.JSONObject) r10     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r12 = "movimiento"
            java.lang.Object r13 = r10.get(r12)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            goto L_0x0389
        L_0x0388:
            r13 = 0
        L_0x0389:
            if (r13 == 0) goto L_0x0505
            boolean r10 = r13 instanceof com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            if (r10 == 0) goto L_0x0467
            java.util.ArrayList r10 = new java.util.ArrayList     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r10.<init>()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r12 = 0
        L_0x0395:
            r14 = r13
            com.indra.httpclient.json.JSONArray r14 = (com.indra.httpclient.json.JSONArray) r14     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            int r14 = r14.length()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            if (r12 >= r14) goto L_0x0460
            ar.com.santander.rio.mbanking.services.model.general.MovimientoConsumos r14 = new ar.com.santander.rio.mbanking.services.model.general.MovimientoConsumos     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r14.<init>()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r15 = r13
            com.indra.httpclient.json.JSONArray r15 = (com.indra.httpclient.json.JSONArray) r15     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.Object r15 = r15.get(r12)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            com.indra.httpclient.json.JSONObject r15 = (com.indra.httpclient.json.JSONObject) r15     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r21 = r6
            java.lang.String r6 = "importe"
            com.indra.httpclient.json.JSONObject r6 = r15.getJSONObject(r6)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r15 = "content"
            java.lang.Object r6 = r6.get(r15)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r6 = r6.toString()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r14.setImporte(r6)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r6 = r13
            com.indra.httpclient.json.JSONArray r6 = (com.indra.httpclient.json.JSONArray) r6     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.Object r6 = r6.get(r12)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            com.indra.httpclient.json.JSONObject r6 = (com.indra.httpclient.json.JSONObject) r6     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r15 = "importe"
            com.indra.httpclient.json.JSONObject r6 = r6.getJSONObject(r15)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r15 = "codigoTarjeta"
            java.lang.Object r6 = r6.get(r15)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r6 = r6.toString()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r14.setCodigoTarjeta(r6)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r6 = r13
            com.indra.httpclient.json.JSONArray r6 = (com.indra.httpclient.json.JSONArray) r6     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.Object r6 = r6.get(r12)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            com.indra.httpclient.json.JSONObject r6 = (com.indra.httpclient.json.JSONObject) r6     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r15 = "establecimiento"
            com.indra.httpclient.json.JSONObject r6 = r6.getJSONObject(r15)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r15 = "content"
            java.lang.Object r6 = r6.get(r15)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r6 = r6.toString()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r14.setEstablecimiento(r6)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r6 = r13
            com.indra.httpclient.json.JSONArray r6 = (com.indra.httpclient.json.JSONArray) r6     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.Object r6 = r6.get(r12)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            com.indra.httpclient.json.JSONObject r6 = (com.indra.httpclient.json.JSONObject) r6     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r15 = "establecimiento"
            com.indra.httpclient.json.JSONObject r6 = r6.getJSONObject(r15)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r15 = "codigo"
            java.lang.Object r6 = r6.get(r15)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r6 = r6.toString()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r14.setCodigo(r6)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r6 = r13
            com.indra.httpclient.json.JSONArray r6 = (com.indra.httpclient.json.JSONArray) r6     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.Object r6 = r6.get(r12)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            com.indra.httpclient.json.JSONObject r6 = (com.indra.httpclient.json.JSONObject) r6     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r15 = "fecha"
            java.lang.Object r6 = r6.get(r15)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r6 = r6.toString()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r14.setFecha(r6)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r6 = r13
            com.indra.httpclient.json.JSONArray r6 = (com.indra.httpclient.json.JSONArray) r6     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.Object r6 = r6.get(r12)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            com.indra.httpclient.json.JSONObject r6 = (com.indra.httpclient.json.JSONObject) r6     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r15 = "ticket"
            java.lang.Object r6 = r6.get(r15)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r6 = r6.toString()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r14.setTicket(r6)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r6 = r13
            com.indra.httpclient.json.JSONArray r6 = (com.indra.httpclient.json.JSONArray) r6     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.Object r6 = r6.get(r12)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            com.indra.httpclient.json.JSONObject r6 = (com.indra.httpclient.json.JSONObject) r6     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r15 = "tipoMoneda"
            java.lang.Object r6 = r6.get(r15)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r6 = r6.toString()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r14.setTipoMoneda(r6)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r10.add(r14)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            int r12 = r12 + 1
            r6 = r21
            goto L_0x0395
        L_0x0460:
            r21 = r6
            r11.setMovimientos(r10)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            goto L_0x050f
        L_0x0467:
            r21 = r6
            boolean r6 = r13 instanceof com.indra.httpclient.json.JSONObject     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            if (r6 == 0) goto L_0x050f
            java.util.ArrayList r6 = new java.util.ArrayList     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r6.<init>()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            ar.com.santander.rio.mbanking.services.model.general.MovimientoConsumos r10 = new ar.com.santander.rio.mbanking.services.model.general.MovimientoConsumos     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r10.<init>()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r12 = r13
            com.indra.httpclient.json.JSONObject r12 = (com.indra.httpclient.json.JSONObject) r12     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r14 = "importe"
            com.indra.httpclient.json.JSONObject r12 = r12.getJSONObject(r14)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r14 = "content"
            java.lang.Object r12 = r12.get(r14)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r12 = r12.toString()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r10.setImporte(r12)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r12 = r13
            com.indra.httpclient.json.JSONObject r12 = (com.indra.httpclient.json.JSONObject) r12     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r14 = "importe"
            com.indra.httpclient.json.JSONObject r12 = r12.getJSONObject(r14)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r14 = "codigoTarjeta"
            java.lang.Object r12 = r12.get(r14)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r12 = r12.toString()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r10.setCodigoTarjeta(r12)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r12 = r13
            com.indra.httpclient.json.JSONObject r12 = (com.indra.httpclient.json.JSONObject) r12     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r14 = "establecimiento"
            com.indra.httpclient.json.JSONObject r12 = r12.getJSONObject(r14)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r14 = "content"
            java.lang.Object r12 = r12.get(r14)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r12 = r12.toString()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r10.setEstablecimiento(r12)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r12 = r13
            com.indra.httpclient.json.JSONObject r12 = (com.indra.httpclient.json.JSONObject) r12     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r14 = "establecimiento"
            com.indra.httpclient.json.JSONObject r12 = r12.getJSONObject(r14)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r14 = "codigo"
            java.lang.Object r12 = r12.get(r14)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r12 = r12.toString()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r10.setCodigo(r12)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r12 = r13
            com.indra.httpclient.json.JSONObject r12 = (com.indra.httpclient.json.JSONObject) r12     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r14 = "fecha"
            java.lang.Object r12 = r12.get(r14)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r12 = r12.toString()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r10.setFecha(r12)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r12 = r13
            com.indra.httpclient.json.JSONObject r12 = (com.indra.httpclient.json.JSONObject) r12     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r14 = "ticket"
            java.lang.Object r12 = r12.get(r14)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r12 = r12.toString()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r10.setTicket(r12)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            com.indra.httpclient.json.JSONObject r13 = (com.indra.httpclient.json.JSONObject) r13     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r12 = "tipoMoneda"
            java.lang.Object r12 = r13.get(r12)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r12 = r12.toString()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r10.setTipoMoneda(r12)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r6.add(r10)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r11.setMovimientos(r6)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            goto L_0x050f
        L_0x0505:
            r21 = r6
            java.util.ArrayList r6 = new java.util.ArrayList     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r6.<init>()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r11.setMovimientos(r6)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
        L_0x050f:
            r5.add(r11)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            goto L_0x072a
        L_0x0514:
            r21 = r6
            boolean r6 = r10 instanceof com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            if (r6 == 0) goto L_0x072a
            r6 = 0
        L_0x051b:
            r11 = r10
            com.indra.httpclient.json.JSONArray r11 = (com.indra.httpclient.json.JSONArray) r11     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            int r11 = r11.length()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            if (r6 >= r11) goto L_0x072a
            ar.com.santander.rio.mbanking.services.model.general.TarjetaConsumos r11 = new ar.com.santander.rio.mbanking.services.model.general.TarjetaConsumos     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r11.<init>()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r12 = r10
            com.indra.httpclient.json.JSONArray r12 = (com.indra.httpclient.json.JSONArray) r12     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.Object r12 = r12.get(r6)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            com.indra.httpclient.json.JSONObject r12 = (com.indra.httpclient.json.JSONObject) r12     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r13 = "dolares"
            java.lang.Object r12 = r12.get(r13)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r12 = r12.toString()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r13 = r10
            com.indra.httpclient.json.JSONArray r13 = (com.indra.httpclient.json.JSONArray) r13     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.Object r13 = r13.get(r6)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            com.indra.httpclient.json.JSONObject r13 = (com.indra.httpclient.json.JSONObject) r13     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r14 = "pesos"
            java.lang.Object r13 = r13.get(r14)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r13 = r13.toString()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r14 = r10
            com.indra.httpclient.json.JSONArray r14 = (com.indra.httpclient.json.JSONArray) r14     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.Object r14 = r14.get(r6)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            com.indra.httpclient.json.JSONObject r14 = (com.indra.httpclient.json.JSONObject) r14     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r15 = "codigoTarjeta"
            java.lang.Object r14 = r14.get(r15)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r14 = r14.toString()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r11.setDolares(r12)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r11.setPesos(r13)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r11.setCodigoTarjeta(r14)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r12 = r10
            com.indra.httpclient.json.JSONArray r12 = (com.indra.httpclient.json.JSONArray) r12     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.Object r12 = r12.get(r6)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            com.indra.httpclient.json.JSONObject r12 = (com.indra.httpclient.json.JSONObject) r12     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r13 = "movimiento"
            boolean r12 = r12.has(r13)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            if (r12 == 0) goto L_0x058c
            r12 = r10
            com.indra.httpclient.json.JSONArray r12 = (com.indra.httpclient.json.JSONArray) r12     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.Object r12 = r12.get(r6)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            com.indra.httpclient.json.JSONObject r12 = (com.indra.httpclient.json.JSONObject) r12     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r13 = "movimiento"
            java.lang.Object r13 = r12.get(r13)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            goto L_0x058d
        L_0x058c:
            r13 = 0
        L_0x058d:
            if (r13 == 0) goto L_0x0711
            boolean r12 = r13 instanceof com.indra.httpclient.json.JSONArray     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            if (r12 == 0) goto L_0x0671
            java.util.ArrayList r12 = new java.util.ArrayList     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r12.<init>()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r14 = 0
        L_0x0599:
            r15 = r13
            com.indra.httpclient.json.JSONArray r15 = (com.indra.httpclient.json.JSONArray) r15     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            int r15 = r15.length()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            if (r14 >= r15) goto L_0x0668
            ar.com.santander.rio.mbanking.services.model.general.MovimientoConsumos r15 = new ar.com.santander.rio.mbanking.services.model.general.MovimientoConsumos     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r15.<init>()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r22 = r10
            r10 = r13
            com.indra.httpclient.json.JSONArray r10 = (com.indra.httpclient.json.JSONArray) r10     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.Object r10 = r10.get(r14)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            com.indra.httpclient.json.JSONObject r10 = (com.indra.httpclient.json.JSONObject) r10     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r23 = r8
            java.lang.String r8 = "importe"
            com.indra.httpclient.json.JSONObject r8 = r10.getJSONObject(r8)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r10 = "content"
            java.lang.Object r8 = r8.get(r10)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r8 = r8.toString()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r15.setImporte(r8)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r8 = r13
            com.indra.httpclient.json.JSONArray r8 = (com.indra.httpclient.json.JSONArray) r8     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.Object r8 = r8.get(r14)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            com.indra.httpclient.json.JSONObject r8 = (com.indra.httpclient.json.JSONObject) r8     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r10 = "importe"
            com.indra.httpclient.json.JSONObject r8 = r8.getJSONObject(r10)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r10 = "codigoTarjeta"
            java.lang.Object r8 = r8.get(r10)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r8 = r8.toString()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r15.setCodigoTarjeta(r8)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r8 = r13
            com.indra.httpclient.json.JSONArray r8 = (com.indra.httpclient.json.JSONArray) r8     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.Object r8 = r8.get(r14)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            com.indra.httpclient.json.JSONObject r8 = (com.indra.httpclient.json.JSONObject) r8     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r10 = "establecimiento"
            com.indra.httpclient.json.JSONObject r8 = r8.getJSONObject(r10)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r10 = "content"
            java.lang.Object r8 = r8.get(r10)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r8 = r8.toString()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r15.setEstablecimiento(r8)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r8 = r13
            com.indra.httpclient.json.JSONArray r8 = (com.indra.httpclient.json.JSONArray) r8     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.Object r8 = r8.get(r14)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            com.indra.httpclient.json.JSONObject r8 = (com.indra.httpclient.json.JSONObject) r8     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r10 = "establecimiento"
            com.indra.httpclient.json.JSONObject r8 = r8.getJSONObject(r10)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r10 = "codigo"
            java.lang.Object r8 = r8.get(r10)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r8 = r8.toString()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r15.setCodigo(r8)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r8 = r13
            com.indra.httpclient.json.JSONArray r8 = (com.indra.httpclient.json.JSONArray) r8     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.Object r8 = r8.get(r14)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            com.indra.httpclient.json.JSONObject r8 = (com.indra.httpclient.json.JSONObject) r8     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r10 = "fecha"
            java.lang.Object r8 = r8.get(r10)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r8 = r8.toString()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r15.setFecha(r8)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r8 = r13
            com.indra.httpclient.json.JSONArray r8 = (com.indra.httpclient.json.JSONArray) r8     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.Object r8 = r8.get(r14)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            com.indra.httpclient.json.JSONObject r8 = (com.indra.httpclient.json.JSONObject) r8     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r10 = "ticket"
            java.lang.Object r8 = r8.get(r10)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r8 = r8.toString()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r15.setTicket(r8)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r8 = r13
            com.indra.httpclient.json.JSONArray r8 = (com.indra.httpclient.json.JSONArray) r8     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.Object r8 = r8.get(r14)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            com.indra.httpclient.json.JSONObject r8 = (com.indra.httpclient.json.JSONObject) r8     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r10 = "tipoMoneda"
            java.lang.Object r8 = r8.get(r10)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r8 = r8.toString()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r15.setTipoMoneda(r8)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r12.add(r15)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            int r14 = r14 + 1
            r10 = r22
            r8 = r23
            goto L_0x0599
        L_0x0668:
            r23 = r8
            r22 = r10
            r11.setMovimientos(r12)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            goto L_0x071d
        L_0x0671:
            r23 = r8
            r22 = r10
            boolean r8 = r13 instanceof com.indra.httpclient.json.JSONObject     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            if (r8 == 0) goto L_0x071d
            java.util.ArrayList r8 = new java.util.ArrayList     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r8.<init>()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            ar.com.santander.rio.mbanking.services.model.general.MovimientoConsumos r10 = new ar.com.santander.rio.mbanking.services.model.general.MovimientoConsumos     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r10.<init>()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r12 = r13
            com.indra.httpclient.json.JSONObject r12 = (com.indra.httpclient.json.JSONObject) r12     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r14 = "importe"
            com.indra.httpclient.json.JSONObject r12 = r12.getJSONObject(r14)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r14 = "content"
            java.lang.Object r12 = r12.get(r14)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r12 = r12.toString()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r10.setImporte(r12)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r12 = r13
            com.indra.httpclient.json.JSONObject r12 = (com.indra.httpclient.json.JSONObject) r12     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r14 = "importe"
            com.indra.httpclient.json.JSONObject r12 = r12.getJSONObject(r14)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r14 = "codigoTarjeta"
            java.lang.Object r12 = r12.get(r14)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r12 = r12.toString()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r10.setCodigoTarjeta(r12)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r12 = r13
            com.indra.httpclient.json.JSONObject r12 = (com.indra.httpclient.json.JSONObject) r12     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r14 = "establecimiento"
            com.indra.httpclient.json.JSONObject r12 = r12.getJSONObject(r14)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r14 = "content"
            java.lang.Object r12 = r12.get(r14)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r12 = r12.toString()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r10.setEstablecimiento(r12)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r12 = r13
            com.indra.httpclient.json.JSONObject r12 = (com.indra.httpclient.json.JSONObject) r12     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r14 = "establecimiento"
            com.indra.httpclient.json.JSONObject r12 = r12.getJSONObject(r14)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r14 = "codigo"
            java.lang.Object r12 = r12.get(r14)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r12 = r12.toString()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r10.setCodigo(r12)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r12 = r13
            com.indra.httpclient.json.JSONObject r12 = (com.indra.httpclient.json.JSONObject) r12     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r14 = "fecha"
            java.lang.Object r12 = r12.get(r14)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r12 = r12.toString()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r10.setFecha(r12)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r12 = r13
            com.indra.httpclient.json.JSONObject r12 = (com.indra.httpclient.json.JSONObject) r12     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r14 = "ticket"
            java.lang.Object r12 = r12.get(r14)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r12 = r12.toString()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r10.setTicket(r12)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            com.indra.httpclient.json.JSONObject r13 = (com.indra.httpclient.json.JSONObject) r13     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r12 = "tipoMoneda"
            java.lang.Object r12 = r13.get(r12)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            java.lang.String r12 = r12.toString()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r10.setTipoMoneda(r12)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r8.add(r10)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r11.setMovimientos(r8)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            goto L_0x071d
        L_0x0711:
            r23 = r8
            r22 = r10
            java.util.ArrayList r8 = new java.util.ArrayList     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r8.<init>()     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            r11.setMovimientos(r8)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
        L_0x071d:
            r5.add(r11)     // Catch:{ JSONException -> 0x02cf, Exception -> 0x02c9 }
            int r6 = r6 + 1
            r10 = r22
            r8 = r23
            goto L_0x051b
        L_0x0728:
            r21 = r6
        L_0x072a:
            r23 = r8
            r1.setTotales(r2)     // Catch:{ JSONException -> 0x0778, Exception -> 0x0774 }
            r1.setTarjetas(r5)     // Catch:{ JSONException -> 0x0778, Exception -> 0x0774 }
            java.lang.String r2 = "tarjetas"
            com.indra.httpclient.json.JSONObject r2 = r4.getJSONObject(r2)     // Catch:{ JSONException -> 0x0778, Exception -> 0x0774 }
            java.lang.String r4 = "tarjeta"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r4)     // Catch:{ JSONException -> 0x0778, Exception -> 0x0774 }
            java.lang.String r4 = "document"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r4)     // Catch:{ JSONException -> 0x0778, Exception -> 0x0774 }
            java.lang.String r4 = "totalMovAuto"
            com.indra.httpclient.json.JSONObject r2 = r2.getJSONObject(r4)     // Catch:{ JSONException -> 0x0778, Exception -> 0x0774 }
            java.lang.String r2 = r2.toString()     // Catch:{ JSONException -> 0x0778, Exception -> 0x0774 }
            java.lang.Class<ar.com.santander.rio.mbanking.services.model.general.TotalMovAuto> r4 = ar.com.santander.rio.mbanking.services.model.general.TotalMovAuto.class
            java.lang.Object r2 = r3.fromJson(r2, r4)     // Catch:{ JSONException -> 0x0778, Exception -> 0x0774 }
            ar.com.santander.rio.mbanking.services.model.general.TotalMovAuto r2 = (ar.com.santander.rio.mbanking.services.model.general.TotalMovAuto) r2     // Catch:{ JSONException -> 0x0778, Exception -> 0x0774 }
            r7.setAutorizaciones(r9)     // Catch:{ JSONException -> 0x0778, Exception -> 0x0774 }
            r8 = r23
            r7.setDatos(r8)     // Catch:{ JSONException -> 0x0778, Exception -> 0x0774 }
            r7.setMovimientos(r1)     // Catch:{ JSONException -> 0x0778, Exception -> 0x0774 }
            r7.setTotalMovAuto(r2)     // Catch:{ JSONException -> 0x0778, Exception -> 0x0774 }
            r1 = r21
            r1.setDocument(r7)     // Catch:{ JSONException -> 0x0778, Exception -> 0x0774 }
            r2 = r17
            r2.setBody(r1)     // Catch:{ JSONException -> 0x0778, Exception -> 0x0774 }
            r1 = r24
            r1.onResponseBean(r2)     // Catch:{ JSONException -> 0x0291, Exception -> 0x028e }
            goto L_0x07a0
        L_0x0774:
            r0 = move-exception
            r1 = r24
            goto L_0x077f
        L_0x0778:
            r0 = move-exception
            r1 = r24
            goto L_0x0790
        L_0x077c:
            r0 = move-exception
            r16 = r2
        L_0x077f:
            r2 = r0
        L_0x0780:
            java.lang.String r3 = "@dev"
            java.lang.String r4 = r2.toString()
            android.util.Log.d(r3, r4)
            r1.onUnknowError(r2)
            goto L_0x07a0
        L_0x078d:
            r0 = move-exception
            r16 = r2
        L_0x0790:
            r2 = r0
        L_0x0791:
            java.lang.String r3 = "@dev"
            java.lang.String r4 = r2.toString()
            android.util.Log.d(r3, r4)
            r1.onUnknowError(r2)
            goto L_0x07a0
        L_0x079e:
            r16 = r2
        L_0x07a0:
            return r16
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.services.soap.request.UltimosConsumosTCRequest.parserResponse(com.indra.httpclient.json.JSONObject):boolean");
    }
}
