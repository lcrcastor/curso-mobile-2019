package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.AMAgendadosEnvEfeRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.AMAgendadosEnvEfeResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class AMAgendadosEnvEfeRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private final String TAG = AMAgendadosEnvEfeRequest.class.getName();
    private AMAgendadosEnvEfeRequestBean amAgendadosEnvEfeRequestBean;
    private AMAgendadosEnvEfeResponseBean amAgendadosEnvEfeResponseBean;

    public int getMethod() {
        return 1;
    }

    public AMAgendadosEnvEfeRequest(Context context, AMAgendadosEnvEfeRequestBean aMAgendadosEnvEfeRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.amAgendadosEnvEfeRequestBean = aMAgendadosEnvEfeRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.amAgendadosEnvEfeRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.amAgendadosEnvEfeResponseBean == null) {
            this.amAgendadosEnvEfeResponseBean = new AMAgendadosEnvEfeResponseBean();
        }
        return this.amAgendadosEnvEfeResponseBean.getClass();
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }
}
