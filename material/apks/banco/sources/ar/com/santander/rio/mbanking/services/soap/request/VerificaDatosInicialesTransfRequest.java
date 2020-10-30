package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.VerificaDatosInicialesTransfOBResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.VerificaDatosInicialesTransfRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.VerificaDatosInicialesTransfResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;

public abstract class VerificaDatosInicialesTransfRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private final String TAG = VerificaDatosInicialesTransfRequest.class.getName();
    private VerificaDatosInicialesTransfRequestBean mVerificaDatosInicialesTransfRequestBean;
    private VerificaDatosInicialesTransfResponseBean mVerificaDatosInicialesTransfResponseBean;

    public int getMethod() {
        return 1;
    }

    public VerificaDatosInicialesTransfRequest(Context context, VerificaDatosInicialesTransfRequestBean verificaDatosInicialesTransfRequestBean, ErrorRequestServer errorRequestServer) {
        super(context);
        this.mVerificaDatosInicialesTransfRequestBean = verificaDatosInicialesTransfRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mVerificaDatosInicialesTransfRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mVerificaDatosInicialesTransfResponseBean == null) {
            this.mVerificaDatosInicialesTransfResponseBean = new VerificaDatosInicialesTransfResponseBean();
        }
        return this.mVerificaDatosInicialesTransfResponseBean.getClass();
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
