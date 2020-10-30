package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.ModificarSuscripcionRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ModificarSuscripcionResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;

public abstract class ModificarSuscripcionMyARequest extends PrivateBaseRequest implements IBeanRequestWS {
    private final String TAG = ModificarSuscripcionMyARequest.class.getName();
    private ModificarSuscripcionRequestBean modificarSuscripcionRequestBean;
    private ModificarSuscripcionResponseBean modificarSuscripcionResponseBean;

    public int getMethod() {
        return 1;
    }

    public ModificarSuscripcionMyARequest(Context context, ModificarSuscripcionRequestBean modificarSuscripcionRequestBean2, ErrorRequestServer errorRequestServer) {
        super(context);
        this.modificarSuscripcionRequestBean = modificarSuscripcionRequestBean2;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str, getXMLBodyRequest());
    }

    public IBeanWS getBeanToRequest() {
        return this.modificarSuscripcionRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.modificarSuscripcionResponseBean == null) {
            this.modificarSuscripcionResponseBean = new ModificarSuscripcionResponseBean();
        }
        return this.modificarSuscripcionResponseBean.getClass();
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
                jSONObject2 = jSONObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body").getJSONObject("xml").getJSONObject("body");
            } catch (JSONException e) {
                e.printStackTrace();
                jSONObject2 = null;
            }
            onResponseBean((ModificarSuscripcionResponseBean) gson.fromJson(jSONObject2.toString(), ModificarSuscripcionResponseBean.class));
        }
        return parserResponse;
    }

    public String getXMLBodyRequest() {
        return this.modificarSuscripcionRequestBean.getModificarSuscripcionnBodyRequestBean().getXMLRequest();
    }
}
