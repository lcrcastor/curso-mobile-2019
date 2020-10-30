package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.AceptaTerminosRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.AceptaTerminosResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;

public abstract class AceptaTerminosRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private final String TAG = AceptaTerminosRequest.class.getName();
    private AceptaTerminosRequestBean aceptaTerminosRequestBean;
    private AceptaTerminosResponseBean aceptaTerminosResponseBean;

    public int getMethod() {
        return 1;
    }

    public AceptaTerminosRequest(Context context, AceptaTerminosRequestBean aceptaTerminosRequestBean2, ErrorRequestServer errorRequestServer) {
        super(context);
        this.aceptaTerminosRequestBean = aceptaTerminosRequestBean2;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.aceptaTerminosRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.aceptaTerminosResponseBean == null) {
            this.aceptaTerminosResponseBean = new AceptaTerminosResponseBean();
        }
        return this.aceptaTerminosResponseBean.getClass();
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
            onResponseBean((AceptaTerminosResponseBean) gson.fromJson(jSONObject2.toString(), AceptaTerminosResponseBean.class));
        }
        return parserResponse;
    }
}
