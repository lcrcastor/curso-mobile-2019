package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.GetPromocionesRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetPromocionesResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetPromocionesBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public abstract class GetPromocionesRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private GetPromocionesRequestBean mGetPromocionesRequestBean;
    private GetPromocionesResponseBean mGetPromocionesResponseBean;

    public int getMethod() {
        return 1;
    }

    public GetPromocionesRequest(Context context, GetPromocionesRequestBean getPromocionesRequestBean, ErrorRequestServer errorRequestServer) {
        super(context);
        this.mGetPromocionesRequestBean = getPromocionesRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mGetPromocionesRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mGetPromocionesResponseBean == null) {
            this.mGetPromocionesResponseBean = new GetPromocionesResponseBean();
        }
        return this.mGetPromocionesResponseBean.getClass();
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
                GetPromocionesResponseBean getPromocionesResponseBean = (GetPromocionesResponseBean) new Gson().fromJson(jSONObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body").getJSONObject("xml").toString(), GetPromocionesResponseBean.class);
                if (getPromocionesResponseBean.getPromocionesBodyResponseBean == null) {
                    getPromocionesResponseBean.getPromocionesBodyResponseBean = new GetPromocionesBodyResponseBean();
                }
                onResponseBean(getPromocionesResponseBean);
            } catch (Exception e) {
                onUnknowError(e);
            }
        }
        return parserResponse;
    }
}
