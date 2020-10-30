package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.PagoServiciosRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.PagoServiciosResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class PagoServiciosRequest extends BaseRequest implements IBeanRequestWS {
    private final String TAG = PagoServiciosRequest.class.getName();
    private PagoServiciosRequestBean mPagoServiciosRequestBean;
    private PagoServiciosResponseBean mPagoServiciosResponseBean;

    public int getMethod() {
        return 1;
    }

    public PagoServiciosRequest(Context context, PagoServiciosRequestBean pagoServiciosRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.mPagoServiciosRequestBean = pagoServiciosRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mPagoServiciosRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mPagoServiciosResponseBean == null) {
            this.mPagoServiciosResponseBean = new PagoServiciosResponseBean();
        }
        return this.mPagoServiciosResponseBean.getClass();
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }
}
