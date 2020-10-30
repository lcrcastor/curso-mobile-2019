package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.GetDetallePreAutorizacionCompradorRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetDetallePreAutorizacionCompradorResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public class GetDetallePreAutorizacionCompradorRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private GetDetallePreAutorizacionCompradorRequestBean getDetallePreAutorizacionCompradorRequestBean;
    private GetDetallePreAutorizacionCompradorResponseBean getDetallePreAutorizacionCompradorResponseBean;

    public int getMethod() {
        return 1;
    }

    public void onResponseBean(IBeanWS iBeanWS) {
    }

    public void onUnknowError(Exception exc) {
    }

    public GetDetallePreAutorizacionCompradorRequest(Context context, GetDetallePreAutorizacionCompradorRequestBean getDetallePreAutorizacionCompradorRequestBean2, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.getDetallePreAutorizacionCompradorRequestBean = getDetallePreAutorizacionCompradorRequestBean2;
        this.mErrorRequestServer = errorRequestServer;
    }

    public GetDetallePreAutorizacionCompradorRequest(Context context, boolean z, GetDetallePreAutorizacionCompradorRequestBean getDetallePreAutorizacionCompradorRequestBean2, GetDetallePreAutorizacionCompradorResponseBean getDetallePreAutorizacionCompradorResponseBean2) {
        super(context, z);
        this.getDetallePreAutorizacionCompradorRequestBean = getDetallePreAutorizacionCompradorRequestBean2;
        this.getDetallePreAutorizacionCompradorResponseBean = getDetallePreAutorizacionCompradorResponseBean2;
    }

    public GetDetallePreAutorizacionCompradorRequest(GetDetallePreAutorizacionCompradorRequestBean getDetallePreAutorizacionCompradorRequestBean2, GetDetallePreAutorizacionCompradorResponseBean getDetallePreAutorizacionCompradorResponseBean2) {
        this.getDetallePreAutorizacionCompradorRequestBean = getDetallePreAutorizacionCompradorRequestBean2;
        this.getDetallePreAutorizacionCompradorResponseBean = getDetallePreAutorizacionCompradorResponseBean2;
    }

    public Class getBeanResponseClass() {
        if (this.getDetallePreAutorizacionCompradorResponseBean == null) {
            this.getDetallePreAutorizacionCompradorResponseBean = new GetDetallePreAutorizacionCompradorResponseBean();
        }
        return this.getDetallePreAutorizacionCompradorResponseBean.getClass();
    }

    public IBeanWS getBeanToRequest() {
        return this.getDetallePreAutorizacionCompradorRequestBean;
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
