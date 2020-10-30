package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.PromocionDetalleRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.PromocionDetalleResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;

public abstract class PromocionDetalleRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private PromocionDetalleRequestBean mPromocionRequestBean;
    private PromocionDetalleResponseBean mPromocionResponseBean;

    public int getMethod() {
        return 1;
    }

    public PromocionDetalleRequest(Context context, PromocionDetalleRequestBean promocionDetalleRequestBean, ErrorRequestServer errorRequestServer) {
        super(context);
        this.mPromocionRequestBean = promocionDetalleRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mPromocionRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mPromocionResponseBean == null) {
            this.mPromocionResponseBean = new PromocionDetalleResponseBean();
        }
        return this.mPromocionResponseBean.getClass();
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
                onResponseBean((PromocionDetalleResponseBean) new Gson().fromJson(jSONObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body").getJSONObject("xml").toString(), PromocionDetalleResponseBean.class));
            } catch (JSONException e) {
                onUnknowError(e);
            }
        }
        return parserResponse;
    }
}
