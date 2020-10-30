package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.GeneraMandatoExtEnvResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GeneraMandatoExtRequestBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class GeneraMandatoExtRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private final String TAG = GeneraMandatoExtRequest.class.getName();
    private GeneraMandatoExtEnvResponseBean cancelaMandatosExtEnvResponseBean;
    private GeneraMandatoExtRequestBean cancelaMandatosExtRequestBean;

    public int getMethod() {
        return 1;
    }

    public GeneraMandatoExtRequest(Context context, GeneraMandatoExtRequestBean generaMandatoExtRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.cancelaMandatosExtRequestBean = generaMandatoExtRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.cancelaMandatosExtRequestBean;
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
