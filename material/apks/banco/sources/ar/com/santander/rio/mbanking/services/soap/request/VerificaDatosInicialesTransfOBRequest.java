package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.VerificaDatosInicialesTransfOBRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.VerificaDatosInicialesTransfOBResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;

public abstract class VerificaDatosInicialesTransfOBRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private final String TAG = VerificaDatosInicialesTransfOBRequest.class.getName();
    private VerificaDatosInicialesTransfOBRequestBean mVerificaDatosInicialesTransfOBRequestBean;
    private VerificaDatosInicialesTransfOBResponseBean mVerificaDatosInicialesTransfOBResponseBean;

    public int getMethod() {
        return 1;
    }

    public VerificaDatosInicialesTransfOBRequest(Context context, VerificaDatosInicialesTransfOBRequestBean verificaDatosInicialesTransfOBRequestBean, ErrorRequestServer errorRequestServer) {
        super(context);
        this.mVerificaDatosInicialesTransfOBRequestBean = verificaDatosInicialesTransfOBRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mVerificaDatosInicialesTransfOBRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mVerificaDatosInicialesTransfOBResponseBean == null) {
            this.mVerificaDatosInicialesTransfOBResponseBean = new VerificaDatosInicialesTransfOBResponseBean();
        }
        return this.mVerificaDatosInicialesTransfOBResponseBean.getClass();
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public boolean parserResponse(JSONObject jSONObject) {
        boolean parserResponse = super.parserResponse(jSONObject);
        if (parserResponse) {
            try {
                onResponseBean((VerificaDatosInicialesTransfOBResponseBean) new Gson().fromJson(jSONObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body").getJSONObject("xml").toString(), VerificaDatosInicialesTransfOBResponseBean.class));
            } catch (JSONException e) {
                onUnknowError(e);
            }
        }
        return parserResponse;
    }
}
