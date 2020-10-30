package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.GetFirmaCredinResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetFirmaCredinRequestBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class GetFirmaCredinRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private GetFirmaCredinRequestBean mGetFirmaCredinRequestBean;
    private GetFirmaCredinResponseBean mGetFirmaCredinResponseBean;

    public int getMethod() {
        return 1;
    }

    public void onResponseBean(IBeanWS iBeanWS) {
    }

    public void onUnknowError(Exception exc) {
    }

    public GetFirmaCredinRequest(Context context, GetFirmaCredinRequestBean getFirmaCredinRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.mGetFirmaCredinRequestBean = getFirmaCredinRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public GetFirmaCredinRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public IBeanWS getBeanToRequest() {
        return this.mGetFirmaCredinRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mGetFirmaCredinResponseBean == null) {
            this.mGetFirmaCredinResponseBean = new GetFirmaCredinResponseBean();
        }
        return this.mGetFirmaCredinResponseBean.getClass();
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
