package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultarTitularCuentaRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultarTitularCuentaResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public class ConsultarTitularCuentaRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private ConsultarTitularCuentaRequestBean mConsultarTitularCuentaRequestBean;
    private ConsultarTitularCuentaResponseBean mConsultarTitularCuentaResponseBean;

    public int getMethod() {
        return 1;
    }

    public void onResponseBean(IBeanWS iBeanWS) {
    }

    public void onUnknowError(Exception exc) {
    }

    public ConsultarTitularCuentaRequest(Context context, ConsultarTitularCuentaRequestBean consultarTitularCuentaRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.mConsultarTitularCuentaRequestBean = consultarTitularCuentaRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public ConsultarTitularCuentaRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public IBeanWS getBeanToRequest() {
        return this.mConsultarTitularCuentaRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mConsultarTitularCuentaResponseBean == null) {
            this.mConsultarTitularCuentaResponseBean = new ConsultarTitularCuentaResponseBean();
        }
        return this.mConsultarTitularCuentaResponseBean.getClass();
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
}
