package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.TransferenciasRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.TransferenciasResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;

public abstract class TransferenciasRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private final String TAG = TransferenciasRequest.class.getName();
    private TransferenciasRequestBean mTransferenciasRequestBean;
    private TransferenciasResponseBean mTransferenciasResponseBean;

    public int getMethod() {
        return 1;
    }

    public TransferenciasRequest(Context context, TransferenciasRequestBean transferenciasRequestBean, ErrorRequestServer errorRequestServer) {
        super(context);
        this.mTransferenciasRequestBean = transferenciasRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mTransferenciasRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mTransferenciasResponseBean == null) {
            this.mTransferenciasResponseBean = new TransferenciasResponseBean();
        }
        return this.mTransferenciasResponseBean.getClass();
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
                onResponseBean((TransferenciasResponseBean) new Gson().fromJson(jSONObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body").getJSONObject("xml").toString(), TransferenciasResponseBean.class));
            } catch (JSONException e) {
                onUnknowError(e);
            }
        }
        return parserResponse;
    }
}
