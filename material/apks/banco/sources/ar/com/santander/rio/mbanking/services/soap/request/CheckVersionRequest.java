package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.CheckVersionRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CheckVersionResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;

public abstract class CheckVersionRequest extends BaseRequest implements IBeanRequestWS {
    private final String TAG = CheckVersionRequest.class.getName();
    private CheckVersionRequestBean mCheckVersionRequestBean;
    private CheckVersionResponseBean mCheckVersionResponseBean;

    public int getMethod() {
        return 1;
    }

    public CheckVersionRequest(Context context, CheckVersionRequestBean checkVersionRequestBean, ErrorRequestServer errorRequestServer) {
        super(context);
        this.mCheckVersionRequestBean = checkVersionRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public CheckVersionRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mCheckVersionRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mCheckVersionResponseBean == null) {
            this.mCheckVersionResponseBean = new CheckVersionResponseBean();
        }
        return this.mCheckVersionResponseBean.getClass();
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
                onResponseBean((CheckVersionResponseBean) new Gson().fromJson(jSONObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body").getJSONObject("xml").toString(), CheckVersionResponseBean.class));
            } catch (JSONException e) {
                onUnknowError(e);
            }
        }
        return parserResponse;
    }
}
