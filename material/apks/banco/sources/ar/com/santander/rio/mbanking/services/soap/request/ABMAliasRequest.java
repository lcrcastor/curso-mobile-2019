package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.ABMAliasRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ABMAliasResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class ABMAliasRequest extends BaseRequest implements IBeanRequestWS {
    private ABMAliasRequestBean abmAliasRequestBean;
    private ABMAliasResponseBean abmAliasResponseBean;

    public int getMethod() {
        return 1;
    }

    public ABMAliasRequest(Context context, ABMAliasRequestBean aBMAliasRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.abmAliasRequestBean = aBMAliasRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.abmAliasRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.abmAliasResponseBean == null) {
            this.abmAliasResponseBean = new ABMAliasResponseBean();
        }
        return this.abmAliasResponseBean.getClass();
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }
}
