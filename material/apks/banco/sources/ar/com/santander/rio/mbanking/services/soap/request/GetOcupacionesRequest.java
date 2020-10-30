package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.GetOcupacionesRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetOcupacionesResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class GetOcupacionesRequest extends BaseRequest implements IBeanRequestWS {
    private GetOcupacionesRequestBean getOcupacionesRequestBean;
    private GetOcupacionesResponseBean getOcupacionesResponseBean;

    public int getMethod() {
        return 1;
    }

    public GetOcupacionesRequest(Context context, GetOcupacionesRequestBean getOcupacionesRequestBean2, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.getOcupacionesRequestBean = getOcupacionesRequestBean2;
        this.mErrorRequestServer = errorRequestServer;
    }

    public GetOcupacionesRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.getOcupacionesRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.getOcupacionesResponseBean == null) {
            this.getOcupacionesResponseBean = new GetOcupacionesResponseBean();
        }
        return this.getOcupacionesResponseBean.getClass();
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }
}
