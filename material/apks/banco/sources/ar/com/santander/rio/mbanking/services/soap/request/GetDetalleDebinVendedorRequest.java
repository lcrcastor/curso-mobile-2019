package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.GetDetalleDebinVendedorRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetDetalleDebinVendedorResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class GetDetalleDebinVendedorRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private GetDetalleDebinVendedorRequestBean mGetDetalleDebinVendedorRequestBean;
    private GetDetalleDebinVendedorResponseBean mGetDetalleDebinVendedorResponseBean;

    public int getMethod() {
        return 1;
    }

    public GetDetalleDebinVendedorRequest(Context context, GetDetalleDebinVendedorRequestBean getDetalleDebinVendedorRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.mGetDetalleDebinVendedorRequestBean = getDetalleDebinVendedorRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public GetDetalleDebinVendedorRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public IBeanWS getBeanToRequest() {
        return this.mGetDetalleDebinVendedorRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mGetDetalleDebinVendedorResponseBean == null) {
            this.mGetDetalleDebinVendedorResponseBean = new GetDetalleDebinVendedorResponseBean();
        }
        return this.mGetDetalleDebinVendedorResponseBean.getClass();
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
