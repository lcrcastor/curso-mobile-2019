package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.PagoServicioCBRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.PagoServicioCBResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class PagoServicioCBRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private final String TAG = PagoServicioCBRequest.class.getName();
    private PagoServicioCBRequestBean mPagoServicioCBRequestBean;
    private PagoServicioCBResponseBean mPagoServicioCBResponseBean;

    public int getMethod() {
        return 1;
    }

    public PagoServicioCBRequest(Context context, PagoServicioCBRequestBean pagoServicioCBRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.mPagoServicioCBRequestBean = pagoServicioCBRequestBean;
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
        return this.mPagoServicioCBRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mPagoServicioCBResponseBean == null) {
            this.mPagoServicioCBResponseBean = new PagoServicioCBResponseBean();
        }
        return this.mPagoServicioCBResponseBean.getClass();
    }
}
