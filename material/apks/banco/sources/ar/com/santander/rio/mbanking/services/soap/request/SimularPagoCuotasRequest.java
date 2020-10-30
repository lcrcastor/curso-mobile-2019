package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.SimularPagosRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.SimularPagosResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class SimularPagoCuotasRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private SimularPagosRequestBean mGetSimularPagosRequestBean;
    private SimularPagosResponseBean mGetSimularPagosResponseBean;

    public int getMethod() {
        return 1;
    }

    public SimularPagoCuotasRequest(Context context, SimularPagosRequestBean simularPagosRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.mGetSimularPagosRequestBean = simularPagosRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public IBeanWS getBeanToRequest() {
        return this.mGetSimularPagosRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mGetSimularPagosResponseBean == null) {
            this.mGetSimularPagosResponseBean = new SimularPagosResponseBean();
        }
        return this.mGetSimularPagosResponseBean.getClass();
    }
}
