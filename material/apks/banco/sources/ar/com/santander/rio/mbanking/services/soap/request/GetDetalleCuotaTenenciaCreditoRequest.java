package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.GetDetalleCuotaTenenciaCreditoRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetDetalleCuotaTenenciaCreditoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;

public abstract class GetDetalleCuotaTenenciaCreditoRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private GetDetalleCuotaTenenciaCreditoRequestBean mGetDetalleCuotaTenenciaCreditoRequestBean;
    private GetDetalleCuotaTenenciaCreditoResponseBean mGetDetalleCuotaTenenciaCreditoResponseBean;

    public int getMethod() {
        return 1;
    }

    public GetDetalleCuotaTenenciaCreditoRequest(Context context, GetDetalleCuotaTenenciaCreditoRequestBean getDetalleCuotaTenenciaCreditoRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.mGetDetalleCuotaTenenciaCreditoRequestBean = getDetalleCuotaTenenciaCreditoRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mGetDetalleCuotaTenenciaCreditoRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mGetDetalleCuotaTenenciaCreditoResponseBean == null) {
            this.mGetDetalleCuotaTenenciaCreditoResponseBean = new GetDetalleCuotaTenenciaCreditoResponseBean();
        }
        return this.mGetDetalleCuotaTenenciaCreditoResponseBean.getClass();
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
                onResponseBean((GetDetalleCuotaTenenciaCreditoResponseBean) new Gson().fromJson(jSONObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body").getJSONObject("xml").toString(), GetDetalleCuotaTenenciaCreditoResponseBean.class));
            } catch (JSONException e) {
                onUnknowError(e);
            }
        }
        return parserResponse;
    }
}
