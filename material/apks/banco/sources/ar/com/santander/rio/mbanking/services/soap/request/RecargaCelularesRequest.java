package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.RecargaCelularesRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.RecargaCelularesResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class RecargaCelularesRequest extends BaseRequest implements IBeanRequestWS {
    private final String TAG = RecargaCelularesRequest.class.getName();
    private RecargaCelularesRequestBean mRecargaCelularesRequestBean;
    private RecargaCelularesResponseBean mRecargaCelularesResponseBean;

    public int getMethod() {
        return 1;
    }

    public RecargaCelularesRequest(Context context, RecargaCelularesRequestBean recargaCelularesRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.mRecargaCelularesRequestBean = recargaCelularesRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mRecargaCelularesRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mRecargaCelularesResponseBean == null) {
            this.mRecargaCelularesResponseBean = new RecargaCelularesResponseBean();
        }
        return this.mRecargaCelularesResponseBean.getClass();
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }
}
