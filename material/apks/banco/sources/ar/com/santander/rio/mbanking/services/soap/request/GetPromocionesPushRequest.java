package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.GetPromocionesPushRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetPromocionesPushResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class GetPromocionesPushRequest extends BaseRequest implements IBeanRequestWS {
    private final String TAG = GetPromocionesHomeRequest.class.getName();
    private GetPromocionesPushRequestBean mGetPromocionesPushRequestBean;
    private GetPromocionesPushResponseBean mGetPromocionesPushResponseBean;

    public int getMethod() {
        return 1;
    }

    public void onResponseBean(IBeanWS iBeanWS) {
    }

    public void onUnknowError(Exception exc) {
    }

    public GetPromocionesPushRequest(Context context, GetPromocionesPushRequestBean getPromocionesPushRequestBean, ErrorRequestServer errorRequestServer) {
        super(context);
        this.mErrorRequestServer = errorRequestServer;
        this.mGetPromocionesPushRequestBean = getPromocionesPushRequestBean;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mGetPromocionesPushRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mGetPromocionesPushResponseBean == null) {
            this.mGetPromocionesPushResponseBean = new GetPromocionesPushResponseBean();
        }
        return this.mGetPromocionesPushResponseBean.getClass();
    }

    public boolean parserResponse(JSONObject jSONObject) {
        boolean parserResponse = super.parserResponse(jSONObject);
        if (parserResponse) {
            try {
                onResponseBean((GetPromocionesPushResponseBean) new Gson().fromJson(jSONObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body").getJSONObject("xml").toString(), GetPromocionesPushResponseBean.class));
            } catch (Exception e) {
                onUnknowError(e);
            }
        }
        return parserResponse;
    }
}
