package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.LogOutRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.LogOutResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;

public abstract class LogOutRequest extends BaseRequest implements IBeanRequestWS {
    private final String TAG = LogOutRequest.class.getName();
    private LogOutRequestBean mLogOutRequestBean;
    private LogOutResponseBean mLogOutResponseBean;

    public int getMethod() {
        return 1;
    }

    public LogOutRequest(Context context, LogOutRequestBean logOutRequestBean, ErrorRequestServer errorRequestServer) {
        super(context);
        this.mLogOutRequestBean = logOutRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mLogOutRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mLogOutResponseBean == null) {
            this.mLogOutResponseBean = new LogOutResponseBean();
        }
        return this.mLogOutResponseBean.getClass();
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
                onResponseBean((LogOutResponseBean) new Gson().fromJson(jSONObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body").getJSONObject("xml").toString(), LogOutResponseBean.class));
            } catch (JSONException e) {
                onUnknowError(e);
            }
        }
        return parserResponse;
    }
}
