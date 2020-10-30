package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.CargaDatosInicialesExtEnvRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CargaDatosInicialesExtEnvResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class CargaDatosInicialesExtEnvRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private final String TAG = CargaDatosInicialesExtEnvRequest.class.getName();
    private CargaDatosInicialesExtEnvRequestBean mCargaDatosInicialesExtEnvRequestBean;
    private CargaDatosInicialesExtEnvResponseBean mCargaDatosInicialesExtEnvResponseBean;

    public int getMethod() {
        return 1;
    }

    public CargaDatosInicialesExtEnvRequest(Context context, CargaDatosInicialesExtEnvRequestBean cargaDatosInicialesExtEnvRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.mCargaDatosInicialesExtEnvRequestBean = cargaDatosInicialesExtEnvRequestBean;
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
        return this.mCargaDatosInicialesExtEnvRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mCargaDatosInicialesExtEnvResponseBean == null) {
            this.mCargaDatosInicialesExtEnvResponseBean = new CargaDatosInicialesExtEnvResponseBean();
        }
        return this.mCargaDatosInicialesExtEnvResponseBean.getClass();
    }
}
