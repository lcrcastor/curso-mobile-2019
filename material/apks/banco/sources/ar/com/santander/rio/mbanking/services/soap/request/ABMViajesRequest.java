package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.ABMViajesRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ABMViajesResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class ABMViajesRequest extends BaseRequest implements IBeanRequestWS {
    private ABMViajesRequestBean abmViajesRequestBean;
    private ABMViajesResponseBean abmViajesResponseBean;

    public int getMethod() {
        return 1;
    }

    public ABMViajesRequest(Context context, ABMViajesRequestBean aBMViajesRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.abmViajesRequestBean = aBMViajesRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.abmViajesRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.abmViajesResponseBean == null) {
            this.abmViajesResponseBean = new ABMViajesResponseBean();
        }
        return this.abmViajesResponseBean.getClass();
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }
}
