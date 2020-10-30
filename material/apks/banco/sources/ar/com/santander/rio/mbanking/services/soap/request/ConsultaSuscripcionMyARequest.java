package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaSuscripcionMyARequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaSuscripcionMyAResponeBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONArray;
import com.indra.httpclient.json.JSONObject;

public abstract class ConsultaSuscripcionMyARequest extends BaseRequest implements IBeanRequestWS {
    private final String TAG = ConsultaSuscripcionMyARequest.class.getName();
    private ConsultaSuscripcionMyARequestBean consultaSuscripcionMyARequestBean;
    private ConsultaSuscripcionMyAResponeBean consultaSuscripcionMyAResponeBean;

    public int getMethod() {
        return 1;
    }

    public ConsultaSuscripcionMyARequest(Context context, ConsultaSuscripcionMyARequestBean consultaSuscripcionMyARequestBean2, ErrorRequestServer errorRequestServer) {
        super(context);
        this.consultaSuscripcionMyARequestBean = consultaSuscripcionMyARequestBean2;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.consultaSuscripcionMyARequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.consultaSuscripcionMyAResponeBean == null) {
            this.consultaSuscripcionMyAResponeBean = new ConsultaSuscripcionMyAResponeBean();
        }
        return this.consultaSuscripcionMyAResponeBean.getClass();
    }

    public boolean parserResponse(JSONObject jSONObject) {
        boolean parserResponse = super.parserResponse(jSONObject);
        if (parserResponse) {
            Gson gson = new Gson();
            try {
                JSONObject jSONObject2 = jSONObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body").getJSONObject("xml");
                JSONObject jSONObject3 = jSONObject2.getJSONObject("body");
                JSONObject jSONObject4 = jSONObject3.getJSONObject("destinos");
                Object obj = jSONObject4.get("destino");
                if (obj instanceof JSONObject) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("[");
                    sb.append(obj.toString());
                    sb.append("]");
                    jSONObject4.put("destino", (Object) new JSONArray(sb.toString()));
                }
                JSONObject jSONObject5 = jSONObject3.getJSONObject("listaProductos");
                Object obj2 = jSONObject5.get("producto");
                if (obj2 instanceof JSONObject) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("[");
                    sb2.append(obj2.toString());
                    sb2.append("]");
                    jSONObject5.put("producto", (Object) new JSONArray(sb2.toString()));
                }
                JSONArray jSONArray = jSONObject5.getJSONArray("producto");
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject6 = jSONArray.getJSONObject(i);
                    if (jSONObject6.has("listaDeSuscripciones")) {
                        JSONObject jSONObject7 = jSONObject6.getJSONObject("listaDeSuscripciones");
                        Object obj3 = jSONObject7.get("suscripcion");
                        if (obj3 instanceof JSONObject) {
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append("[");
                            sb3.append(obj3.toString());
                            sb3.append("]");
                            jSONObject7.put("suscripcion", (Object) new JSONArray(sb3.toString()));
                        }
                    }
                }
                Object obj4 = jSONObject3.getJSONObject("listaLeyendas").get("leyenda");
                if (obj4 instanceof JSONObject) {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("[");
                    sb4.append(obj4.toString());
                    sb4.append("]");
                    jSONObject5.put("leyenda", (Object) new JSONArray(sb4.toString()));
                }
                onResponseBean((IBeanWS) gson.fromJson(jSONObject2.toString(), getBeanResponseClass()));
            } catch (Exception unused) {
            }
        }
        return parserResponse;
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }
}
