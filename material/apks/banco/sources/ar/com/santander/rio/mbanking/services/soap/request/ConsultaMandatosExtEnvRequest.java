package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaMandatosExtEnvRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaMandatosExtEnvResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class ConsultaMandatosExtEnvRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private final String TAG = ConsultaMandatosExtEnvRequest.class.getName();
    private ConsultaMandatosExtEnvRequestBean consultaMandatosExtEnvRequestBean;
    private ConsultaMandatosExtEnvResponseBean consultaMandatosExtEnvResponseBean;

    public int getMethod() {
        return 1;
    }

    public ConsultaMandatosExtEnvRequest(Context context, ConsultaMandatosExtEnvRequestBean consultaMandatosExtEnvRequestBean2, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.consultaMandatosExtEnvRequestBean = consultaMandatosExtEnvRequestBean2;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.consultaMandatosExtEnvRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.consultaMandatosExtEnvResponseBean == null) {
            this.consultaMandatosExtEnvResponseBean = new ConsultaMandatosExtEnvResponseBean();
        }
        return this.consultaMandatosExtEnvResponseBean.getClass();
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }
}
