package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.GetCajerosDetalleRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetCajerosDetalleResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;

public abstract class GetCajerosDetalleRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private GetCajerosDetalleRequestBean mGetCajerosRequestBean;
    private GetCajerosDetalleResponseBean mGetCajerosResponseBean;

    public int getMethod() {
        return 1;
    }

    public GetCajerosDetalleRequest(Context context, GetCajerosDetalleRequestBean getCajerosDetalleRequestBean, ErrorRequestServer errorRequestServer) {
        super(context);
        this.mGetCajerosRequestBean = getCajerosDetalleRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mGetCajerosRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mGetCajerosResponseBean == null) {
            this.mGetCajerosResponseBean = new GetCajerosDetalleResponseBean();
        }
        return this.mGetCajerosResponseBean.getClass();
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
                onResponseBean((GetCajerosDetalleResponseBean) new Gson().fromJson(jSONObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body").getJSONObject("xml").toString(), GetCajerosDetalleResponseBean.class));
            } catch (JSONException e) {
                onUnknowError(e);
            }
        }
        return parserResponse;
    }
}
