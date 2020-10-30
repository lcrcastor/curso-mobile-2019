package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.AbmAdhesionVendedorRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.AbmAdhesionVendedorResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class AbmAdhesionVendedorRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private AbmAdhesionVendedorRequestBean mAbmAdhesionVendedorRequestBean;
    private AbmAdhesionVendedorResponseBean mAbmAdhesionVendedorResponseBean;

    public int getMethod() {
        return 1;
    }

    public AbmAdhesionVendedorRequest(Context context, AbmAdhesionVendedorRequestBean abmAdhesionVendedorRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.mAbmAdhesionVendedorRequestBean = abmAdhesionVendedorRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public AbmAdhesionVendedorRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public IBeanWS getBeanToRequest() {
        return this.mAbmAdhesionVendedorRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mAbmAdhesionVendedorResponseBean == null) {
            this.mAbmAdhesionVendedorResponseBean = new AbmAdhesionVendedorResponseBean();
        }
        return this.mAbmAdhesionVendedorResponseBean.getClass();
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
