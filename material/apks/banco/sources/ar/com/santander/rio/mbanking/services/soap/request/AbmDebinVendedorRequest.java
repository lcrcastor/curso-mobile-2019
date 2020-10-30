package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.AbmDebinVendedorRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.AbmDebinVendedorResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public class AbmDebinVendedorRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private AbmDebinVendedorRequestBean mAbmDebinVendedorRequestBean;
    private AbmDebinVendedorResponseBean mAbmDebinVendedorResponseBean;

    public int getMethod() {
        return 1;
    }

    public void onResponseBean(IBeanWS iBeanWS) {
    }

    public void onUnknowError(Exception exc) {
    }

    public AbmDebinVendedorRequest(Context context, AbmDebinVendedorRequestBean abmDebinVendedorRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.mAbmDebinVendedorRequestBean = abmDebinVendedorRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public AbmDebinVendedorRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public IBeanWS getBeanToRequest() {
        return this.mAbmDebinVendedorRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mAbmDebinVendedorResponseBean == null) {
            this.mAbmDebinVendedorResponseBean = new AbmDebinVendedorResponseBean();
        }
        return this.mAbmDebinVendedorResponseBean.getClass();
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
