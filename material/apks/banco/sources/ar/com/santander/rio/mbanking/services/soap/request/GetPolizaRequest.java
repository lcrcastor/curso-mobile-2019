package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.GetPolizaRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetPolizaResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public abstract class GetPolizaRequest extends BaseRequest implements IBeanRequestWS {
    private GetPolizaRequestBean getPolizaRequestBean;
    private GetPolizaResponseBean getPolizaResponseBean;

    public int getMethod() {
        return 1;
    }

    public GetPolizaRequest(Context context, GetPolizaRequestBean getPolizaRequestBean2, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.getPolizaRequestBean = getPolizaRequestBean2;
        this.mErrorRequestServer = errorRequestServer;
    }

    public GetPolizaRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.getPolizaRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.getPolizaResponseBean == null) {
            this.getPolizaResponseBean = new GetPolizaResponseBean();
        }
        return this.getPolizaResponseBean.getClass();
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public boolean parserResponse(JSONObject jSONObject) {
        return super.parserResponse(jSONObject);
    }
}
