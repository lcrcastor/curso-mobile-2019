package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.GetDetalleDebinCompradorRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetDetalleDebinCompradorResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class GetDetalleDebinCompradorRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private GetDetalleDebinCompradorRequestBean mGetDetalleDebinCompradorRequestBean;
    private GetDetalleDebinCompradorResponseBean mGetDetalleDebinCompradorResponseBean;

    public int getMethod() {
        return 1;
    }

    public GetDetalleDebinCompradorRequest(Context context, GetDetalleDebinCompradorRequestBean getDetalleDebinCompradorRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.mGetDetalleDebinCompradorRequestBean = getDetalleDebinCompradorRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public GetDetalleDebinCompradorRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public IBeanWS getBeanToRequest() {
        return this.mGetDetalleDebinCompradorRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mGetDetalleDebinCompradorResponseBean == null) {
            this.mGetDetalleDebinCompradorResponseBean = new GetDetalleDebinCompradorResponseBean();
        }
        return this.mGetDetalleDebinCompradorResponseBean.getClass();
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
