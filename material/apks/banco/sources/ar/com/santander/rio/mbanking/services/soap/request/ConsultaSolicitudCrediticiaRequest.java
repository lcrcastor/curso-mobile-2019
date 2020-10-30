package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaSolicitudCrediticiaRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaSolicitudCrediticiaResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public abstract class ConsultaSolicitudCrediticiaRequest extends BaseRequest implements IBeanRequestWS {
    private final String TAG = ConsultaSolicitudCrediticiaRequest.class.getName();
    private ConsultaSolicitudCrediticiaRequestBean consultaSolicitudCrediticiaRequestBean;
    private ConsultaSolicitudCrediticiaResponseBean consultaSolicitudCrediticiaResponseBean;

    public int getMethod() {
        return 1;
    }

    public ConsultaSolicitudCrediticiaRequest(Context context, ConsultaSolicitudCrediticiaRequestBean consultaSolicitudCrediticiaRequestBean2, ErrorRequestServer errorRequestServer) {
        super(context);
        this.consultaSolicitudCrediticiaRequestBean = consultaSolicitudCrediticiaRequestBean2;
        this.mErrorRequestServer = errorRequestServer;
    }

    public ConsultaSolicitudCrediticiaRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.consultaSolicitudCrediticiaRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.consultaSolicitudCrediticiaResponseBean == null) {
            this.consultaSolicitudCrediticiaResponseBean = new ConsultaSolicitudCrediticiaResponseBean();
        }
        return this.consultaSolicitudCrediticiaResponseBean.getClass();
    }

    public boolean parserResponse(JSONObject jSONObject) {
        boolean parserResponse = super.parserResponse(jSONObject);
        if (parserResponse) {
            Gson gson = new Gson();
            try {
                JSONObject jSONObject2 = jSONObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body").getJSONObject("xml");
                if (jSONObject2.getJSONObject("body").has("consCalifCred")) {
                    onResponseBean((IBeanWS) gson.fromJson(jSONObject2.toString(), getBeanResponseClass()));
                }
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
