package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsDeudaCBRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsDeudaCBResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class CnsDeudaCBRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private final String TAG = CnsDeudaCBRequest.class.getName();
    private CnsDeudaCBRequestBean mCnsDeudaCBRequestBean;
    private CnsDeudaCBResponseBean mCnsDeudaCBResponseBean;

    public int getMethod() {
        return 1;
    }

    public CnsDeudaCBRequest(Context context, CnsDeudaCBRequestBean cnsDeudaCBRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.mCnsDeudaCBRequestBean = cnsDeudaCBRequestBean;
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
        return this.mCnsDeudaCBRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mCnsDeudaCBResponseBean == null) {
            this.mCnsDeudaCBResponseBean = new CnsDeudaCBResponseBean();
        }
        return this.mCnsDeudaCBResponseBean.getClass();
    }
}
