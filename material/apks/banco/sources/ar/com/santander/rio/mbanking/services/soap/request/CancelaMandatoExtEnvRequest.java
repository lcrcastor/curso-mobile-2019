package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.CancelaMandatoExtEnvRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CancelaMandatoExtEnvResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class CancelaMandatoExtEnvRequest extends BaseRequest implements IBeanRequestWS {
    private final String TAG = ConsultaMandatosExtEnvRequest.class.getName();
    private CancelaMandatoExtEnvRequestBean cancelaMandatosExtEnvRequestBean;
    private CancelaMandatoExtEnvResponseBean cancelaMandatosExtEnvResponseBean;

    public int getMethod() {
        return 1;
    }

    public CancelaMandatoExtEnvRequest(Context context, CancelaMandatoExtEnvRequestBean cancelaMandatoExtEnvRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.cancelaMandatosExtEnvRequestBean = cancelaMandatoExtEnvRequestBean;
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
            this.cancelaMandatosExtEnvResponseBean = new CancelaMandatoExtEnvResponseBean();
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
