package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.AbmDebinCompradorRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.AbmDebinCompradorResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public class AbmDebinCompradorRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private AbmDebinCompradorRequestBean mAbmDebinCompradorRequestBean;
    private AbmDebinCompradorResponseBean mAbmDebinCompradorResponseBean;

    public int getMethod() {
        return 1;
    }

    public void onResponseBean(IBeanWS iBeanWS) {
    }

    public void onUnknowError(Exception exc) {
    }

    public AbmDebinCompradorRequest(Context context, AbmDebinCompradorRequestBean abmDebinCompradorRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.mAbmDebinCompradorRequestBean = abmDebinCompradorRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public AbmDebinCompradorRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public IBeanWS getBeanToRequest() {
        return this.mAbmDebinCompradorRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mAbmDebinCompradorResponseBean == null) {
            this.mAbmDebinCompradorResponseBean = new AbmDebinCompradorResponseBean();
        }
        return this.mAbmDebinCompradorResponseBean.getClass();
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
