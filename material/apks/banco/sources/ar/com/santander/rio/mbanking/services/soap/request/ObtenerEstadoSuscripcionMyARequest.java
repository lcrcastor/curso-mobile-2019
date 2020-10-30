package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.ObtenerEstadoSuscripcionMyARequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ObtenerEstadoSuscripcionMyAResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONArray;
import com.indra.httpclient.json.JSONObject;

public abstract class ObtenerEstadoSuscripcionMyARequest extends PrivateBaseRequest implements IBeanRequestWS {
    private final String TAG = ObtenerEstadoSuscripcionMyARequest.class.getName();
    private ObtenerEstadoSuscripcionMyARequestBean obtenerEstadoSuscripcionMyARequestRequestBean;
    private ObtenerEstadoSuscripcionMyAResponseBean obtenerEstadoSuscripcionMyAResponseBean;

    public int getMethod() {
        return 1;
    }

    public ObtenerEstadoSuscripcionMyARequest(Context context, ObtenerEstadoSuscripcionMyARequestBean obtenerEstadoSuscripcionMyARequestBean, ErrorRequestServer errorRequestServer) {
        super(context);
        this.obtenerEstadoSuscripcionMyARequestRequestBean = obtenerEstadoSuscripcionMyARequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.obtenerEstadoSuscripcionMyARequestRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.obtenerEstadoSuscripcionMyAResponseBean == null) {
            this.obtenerEstadoSuscripcionMyAResponseBean = new ObtenerEstadoSuscripcionMyAResponseBean();
        }
        return this.obtenerEstadoSuscripcionMyAResponseBean.getClass();
    }

    public boolean parserResponse(JSONObject jSONObject) {
        boolean parserResponse = super.parserResponse(jSONObject);
        if (parserResponse) {
            Gson gson = new Gson();
            try {
                JSONObject jSONObject2 = jSONObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body").getJSONObject("xml");
                JSONObject jSONObject3 = jSONObject2.getJSONObject("body").getJSONObject("cliente").getJSONObject("destinos");
                Object obj = jSONObject3.get("destino");
                if (obj instanceof JSONObject) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("[");
                    sb.append(obj.toString());
                    sb.append("]");
                    jSONObject3.put("destino", (Object) new JSONArray(sb.toString()));
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
