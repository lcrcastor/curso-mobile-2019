package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.SolicitudLimiteTransfRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.SolicitudLimiteTransfResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public class SolicitudLimiteTransfRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private SolicitudLimiteTransfRequestBean mSolicitudLimiteTransfRequestBean;
    private SolicitudLimiteTransfResponseBean mSolicitudLimiteTransfResponseBean;

    public int getMethod() {
        return 1;
    }

    public void onResponseBean(IBeanWS iBeanWS) {
    }

    public void onUnknowError(Exception exc) {
    }

    public SolicitudLimiteTransfRequest(Context context, SolicitudLimiteTransfRequestBean solicitudLimiteTransfRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.mSolicitudLimiteTransfRequestBean = solicitudLimiteTransfRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public SolicitudLimiteTransfRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public IBeanWS getBeanToRequest() {
        return this.mSolicitudLimiteTransfRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mSolicitudLimiteTransfResponseBean == null) {
            this.mSolicitudLimiteTransfResponseBean = new SolicitudLimiteTransfResponseBean();
        }
        return this.mSolicitudLimiteTransfResponseBean.getClass();
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
