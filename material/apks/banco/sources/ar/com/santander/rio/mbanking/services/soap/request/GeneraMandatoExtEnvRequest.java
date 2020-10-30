package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.GeneraMandatoExtEnvRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GeneraMandatoExtEnvResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class GeneraMandatoExtEnvRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private final String TAG = GeneraMandatoExtEnvRequest.class.getName();
    private GeneraMandatoExtEnvRequestBean cancelaMandatosExtEnvRequestBean;
    private GeneraMandatoExtEnvResponseBean cancelaMandatosExtEnvResponseBean;

    public int getMethod() {
        return 1;
    }

    public GeneraMandatoExtEnvRequest(Context context, GeneraMandatoExtEnvRequestBean generaMandatoExtEnvRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.cancelaMandatosExtEnvRequestBean = generaMandatoExtEnvRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.cancelaMandatosExtEnvRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.cancelaMandatosExtEnvResponseBean == null) {
            this.cancelaMandatosExtEnvResponseBean = new GeneraMandatoExtEnvResponseBean();
        }
        return this.cancelaMandatosExtEnvResponseBean.getClass();
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }
}
