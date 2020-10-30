package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.ConfirmarPagoRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConfirmarPagoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;

public abstract class ConfirmarPagoCuotaRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private ConfirmarPagoRequestBean mGeConfirmarPagoRequestBean;
    private ConfirmarPagoResponseBean mGetConfirmarPagoResponseBean;

    public int getMethod() {
        return 1;
    }

    public ConfirmarPagoCuotaRequest(Context context, ConfirmarPagoRequestBean confirmarPagoRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, false);
        this.mGeConfirmarPagoRequestBean = confirmarPagoRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public IBeanWS getBeanToRequest() {
        return this.mGeConfirmarPagoRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mGetConfirmarPagoResponseBean == null) {
            this.mGetConfirmarPagoResponseBean = new ConfirmarPagoResponseBean();
        }
        return this.mGetConfirmarPagoResponseBean.getClass();
    }

    public boolean parserResponse(JSONObject jSONObject) {
        boolean parserResponse = super.parserResponse(jSONObject);
        if (parserResponse) {
            try {
                onResponseBean((ConfirmarPagoResponseBean) new Gson().fromJson(jSONObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body").getJSONObject("xml").toString(), ConfirmarPagoResponseBean.class));
            } catch (JSONException e) {
                onUnknowError(e);
            }
        }
        return parserResponse;
    }
}
