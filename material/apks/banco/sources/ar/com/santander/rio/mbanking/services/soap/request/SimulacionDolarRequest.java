package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.SimulacionDolarRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.SimulacionDolarResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class SimulacionDolarRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private final String TAG = SimulacionDolarRequest.class.getName();
    private SimulacionDolarRequestBean mSimulacionDolarRequestBean;
    private SimulacionDolarResponseBean mSimulacionDolarResponseBean;

    public int getMethod() {
        return 1;
    }

    public SimulacionDolarRequest(Context context, SimulacionDolarRequestBean simulacionDolarRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.mSimulacionDolarRequestBean = simulacionDolarRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public SimulacionDolarRequest(ErrorRequestServer errorRequestServer) {
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
        return this.mSimulacionDolarRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mSimulacionDolarResponseBean == null) {
            this.mSimulacionDolarResponseBean = new SimulacionDolarResponseBean();
        }
        return this.mSimulacionDolarResponseBean.getClass();
    }
}
