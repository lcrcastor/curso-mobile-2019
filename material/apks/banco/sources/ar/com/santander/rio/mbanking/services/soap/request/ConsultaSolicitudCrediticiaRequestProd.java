package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaSolicitudCrediticiaRequestBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaSolicitudCrediticiaResponseBeanProd;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public abstract class ConsultaSolicitudCrediticiaRequestProd extends BaseRequest implements IBeanRequestWS {
    private final String TAG = ConsultaSolicitudCrediticiaRequestProd.class.getName();
    private ConsultaSolicitudCrediticiaRequestBeanProd consultaSolicitudCrediticiaRequestBeanProd;
    private ConsultaSolicitudCrediticiaResponseBeanProd consultaSolicitudCrediticiaResponseBeanProd;

    public int getMethod() {
        return 1;
    }

    public ConsultaSolicitudCrediticiaRequestProd(Context context, ConsultaSolicitudCrediticiaRequestBeanProd consultaSolicitudCrediticiaRequestBeanProd2, ErrorRequestServer errorRequestServer) {
        super(context);
        this.consultaSolicitudCrediticiaRequestBeanProd = consultaSolicitudCrediticiaRequestBeanProd2;
        this.mErrorRequestServer = errorRequestServer;
    }

    public ConsultaSolicitudCrediticiaRequestProd(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.consultaSolicitudCrediticiaRequestBeanProd;
    }

    public Class getBeanResponseClass() {
        if (this.consultaSolicitudCrediticiaResponseBeanProd == null) {
            this.consultaSolicitudCrediticiaResponseBeanProd = new ConsultaSolicitudCrediticiaResponseBeanProd();
        }
        return this.consultaSolicitudCrediticiaResponseBeanProd.getClass();
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
