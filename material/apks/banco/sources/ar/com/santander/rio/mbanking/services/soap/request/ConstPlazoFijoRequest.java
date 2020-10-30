package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.ConstPlazoFijoRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConstPlazoFijoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class ConstPlazoFijoRequest extends BaseRequest implements IBeanRequestWS {
    private final String TAG = ConstPlazoFijoRequest.class.getName();
    private ConstPlazoFijoRequestBean mConstPlazoFijoRequestBean;
    private ConstPlazoFijoResponseBean mConstPlazoFijoResponseBean;

    public int getMethod() {
        return 1;
    }

    public ConstPlazoFijoRequest(Context context, ConstPlazoFijoRequestBean constPlazoFijoRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.mConstPlazoFijoRequestBean = constPlazoFijoRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public ConstPlazoFijoRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mConstPlazoFijoRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mConstPlazoFijoResponseBean == null) {
            this.mConstPlazoFijoResponseBean = new ConstPlazoFijoResponseBean();
        }
        return this.mConstPlazoFijoResponseBean.getClass();
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }
}
