package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaAgendadosEnvEfeRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaAgendadosEnvEfeResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class ConsultaAgendadosEnvEfeRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private final String TAG = ConsultaAgendadosEnvEfeRequest.class.getName();
    private ConsultaAgendadosEnvEfeRequestBean mConsultaAgendadosEnvEfeRequestBean;
    private ConsultaAgendadosEnvEfeResponseBean mConsultaAgendadosEnvEfeResponseBean;

    public int getMethod() {
        return 1;
    }

    public ConsultaAgendadosEnvEfeRequest(Context context, ConsultaAgendadosEnvEfeRequestBean consultaAgendadosEnvEfeRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.mConsultaAgendadosEnvEfeRequestBean = consultaAgendadosEnvEfeRequestBean;
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
        return this.mConsultaAgendadosEnvEfeRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mConsultaAgendadosEnvEfeResponseBean == null) {
            this.mConsultaAgendadosEnvEfeResponseBean = new ConsultaAgendadosEnvEfeResponseBean();
        }
        return this.mConsultaAgendadosEnvEfeResponseBean.getClass();
    }
}
