package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.RegistrarSuscripcionRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.RegistrarSuscripcionResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;

public abstract class RegistrarSuscripcionMyARequest extends PrivateBaseRequest implements IBeanRequestWS {
    private final String TAG = RegistrarSuscripcionMyARequest.class.getName();
    private RegistrarSuscripcionRequestBean registrarSuscripcionRequestBean;
    private RegistrarSuscripcionResponseBean registrarSuscripcionResponseBean;

    public int getMethod() {
        return 1;
    }

    public RegistrarSuscripcionMyARequest(Context context, RegistrarSuscripcionRequestBean registrarSuscripcionRequestBean2, ErrorRequestServer errorRequestServer) {
        super(context);
        this.registrarSuscripcionRequestBean = registrarSuscripcionRequestBean2;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.registrarSuscripcionRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.registrarSuscripcionResponseBean == null) {
            this.registrarSuscripcionResponseBean = new RegistrarSuscripcionResponseBean();
        }
        return this.registrarSuscripcionResponseBean.getClass();
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public boolean parserResponse(JSONObject jSONObject) {
        JSONObject jSONObject2;
        boolean parserResponse = super.parserResponse(jSONObject);
        if (parserResponse) {
            Gson gson = new Gson();
            try {
                jSONObject2 = jSONObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body").getJSONObject("xml");
            } catch (JSONException e) {
                e.printStackTrace();
                jSONObject2 = null;
            }
            onResponseBean((RegistrarSuscripcionResponseBean) gson.fromJson(jSONObject2.toString(), RegistrarSuscripcionResponseBean.class));
        }
        return parserResponse;
    }
}
