package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaAliasRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaAliasResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class ConsultaAliasRequest extends BaseRequest implements IBeanRequestWS {
    private ConsultaAliasRequestBean consultaAliasRequestBean;
    private ConsultaAliasResponseBean consultaAliasResponseBean;

    public int getMethod() {
        return 1;
    }

    public ConsultaAliasRequest(Context context, ConsultaAliasRequestBean consultaAliasRequestBean2, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.consultaAliasRequestBean = consultaAliasRequestBean2;
        this.mErrorRequestServer = errorRequestServer;
    }

    public ConsultaAliasRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.consultaAliasRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.consultaAliasResponseBean == null) {
            this.consultaAliasResponseBean = new ConsultaAliasResponseBean();
        }
        return this.consultaAliasResponseBean.getClass();
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }
}
