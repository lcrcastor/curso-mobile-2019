package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.BAgendadosEnvEfeRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.BAgendadosEnvEfeResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class BAgendadosEnvEfeRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private final String TAG = BAgendadosEnvEfeRequest.class.getName();
    private BAgendadosEnvEfeRequestBean bAgendadosEnvEfeRequestBean;
    private BAgendadosEnvEfeResponseBean bAgendadosEnvEfeResponseBean;

    public int getMethod() {
        return 1;
    }

    public BAgendadosEnvEfeRequest(Context context, BAgendadosEnvEfeRequestBean bAgendadosEnvEfeRequestBean2, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.bAgendadosEnvEfeRequestBean = bAgendadosEnvEfeRequestBean2;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.bAgendadosEnvEfeRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.bAgendadosEnvEfeResponseBean == null) {
            this.bAgendadosEnvEfeResponseBean = new BAgendadosEnvEfeResponseBean();
        }
        return this.bAgendadosEnvEfeResponseBean.getClass();
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }
}
