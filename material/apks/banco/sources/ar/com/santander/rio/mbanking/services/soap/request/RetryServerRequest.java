package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.RetryServerRequestBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class RetryServerRequest extends BaseRequest implements IBeanRequestWS {
    private final String TAG = RetryServerRequest.class.getName();
    private RetryServerRequestBean retryServerRequestBean;

    public Class getBeanResponseClass() {
        return null;
    }

    public int getMethod() {
        return 1;
    }

    public RetryServerRequest(Context context, RetryServerRequestBean retryServerRequestBean2, ErrorRequestServer errorRequestServer) {
        super(context);
        this.retryServerRequestBean = retryServerRequestBean2;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.retryServerRequestBean;
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }
}
