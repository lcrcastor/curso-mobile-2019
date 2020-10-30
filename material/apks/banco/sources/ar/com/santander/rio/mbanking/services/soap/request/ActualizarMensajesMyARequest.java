package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.ActualizarMensajesMyARequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ActualizarMensajesMyARequestResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ErrorBodyBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;

public class ActualizarMensajesMyARequest extends BaseRequest implements IBeanRequestWS {
    private final String TAG = ObtenerEstadoSuscripcionMyARequest.class.getName();
    private ActualizarMensajesMyARequestBean actualizarMensajesMyARequestBean;
    private ActualizarMensajesMyARequestResponseBean actualizarMensajesMyARequestResponseBean;

    public int getMethod() {
        return 1;
    }

    public void onResponseBean(IBeanWS iBeanWS) {
    }

    public void onUnknowError(Exception exc) {
    }

    public ActualizarMensajesMyARequest(Context context, ActualizarMensajesMyARequestBean actualizarMensajesMyARequestBean2, ErrorRequestServer errorRequestServer) {
        super(context);
        this.actualizarMensajesMyARequestBean = actualizarMensajesMyARequestBean2;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.actualizarMensajesMyARequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.actualizarMensajesMyARequestResponseBean == null) {
            this.actualizarMensajesMyARequestResponseBean = new ActualizarMensajesMyARequestResponseBean();
        }
        return this.actualizarMensajesMyARequestResponseBean.getClass();
    }

    public boolean parserResponse(JSONObject jSONObject) {
        JSONObject jSONObject2;
        boolean parserResponse = super.parserResponse(jSONObject);
        if (parserResponse) {
            Gson gson = new Gson();
            try {
                jSONObject2 = jSONObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body").getJSONObject("xml").getJSONObject("body");
            } catch (JSONException e) {
                onUnknowError(e);
                jSONObject2 = null;
            }
            ErrorBodyBean errorBodyBean = (ErrorBodyBean) gson.fromJson(jSONObject2.toString(), ErrorBodyBean.class);
            ActualizarMensajesMyARequestResponseBean actualizarMensajesMyARequestResponseBean2 = new ActualizarMensajesMyARequestResponseBean();
            actualizarMensajesMyARequestResponseBean2.errorBodyBean = errorBodyBean;
            onResponseBean(actualizarMensajesMyARequestResponseBean2);
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
