package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.MovCtasRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.MovCtasResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class MovCtasRequest extends BaseRequest implements IBeanRequestWS {
    private final String TAG = MovCtasRequest.class.getName();
    private MovCtasRequestBean mMovCtasRequestBean;
    private MovCtasResponseBean mMovCtasResponseBean;

    public int getMethod() {
        return 1;
    }

    public MovCtasRequest(Context context, MovCtasRequestBean movCtasRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.mMovCtasRequestBean = movCtasRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mMovCtasRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mMovCtasResponseBean == null) {
            this.mMovCtasResponseBean = new MovCtasResponseBean();
        }
        return this.mMovCtasResponseBean.getClass();
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }
}
