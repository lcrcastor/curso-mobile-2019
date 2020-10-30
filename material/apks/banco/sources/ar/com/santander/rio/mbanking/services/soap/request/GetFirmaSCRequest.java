package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.GetFirmaSCRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetFirmaSCResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class GetFirmaSCRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private GetFirmaSCRequestBean mGetFirmaSCRequestBean;
    private GetFirmaSCResponseBean mGetFirmaSCResponseBean;

    public int getMethod() {
        return 1;
    }

    public void onResponseBean(IBeanWS iBeanWS) {
    }

    public void onUnknowError(Exception exc) {
    }

    public GetFirmaSCRequest(Context context, GetFirmaSCRequestBean getFirmaSCRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.mGetFirmaSCRequestBean = getFirmaSCRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public GetFirmaSCRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public IBeanWS getBeanToRequest() {
        return this.mGetFirmaSCRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mGetFirmaSCResponseBean == null) {
            this.mGetFirmaSCResponseBean = new GetFirmaSCResponseBean();
        }
        return this.mGetFirmaSCResponseBean.getClass();
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
