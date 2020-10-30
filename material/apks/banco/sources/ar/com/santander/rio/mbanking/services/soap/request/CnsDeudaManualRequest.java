package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsDeudaManualRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsDeudaManualResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class CnsDeudaManualRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private final String TAG = CnsDeudaManualRequest.class.getName();
    private CnsDeudaManualRequestBean mCnsDeudaManualRequestBean;
    private CnsDeudaManualResponseBean mCnsDeudaManualResponseBean;

    public int getMethod() {
        return 1;
    }

    public CnsDeudaManualRequest(Context context, CnsDeudaManualRequestBean cnsDeudaManualRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.mCnsDeudaManualRequestBean = cnsDeudaManualRequestBean;
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
        return this.mCnsDeudaManualRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mCnsDeudaManualResponseBean == null) {
            this.mCnsDeudaManualResponseBean = new CnsDeudaManualResponseBean();
        }
        return this.mCnsDeudaManualResponseBean.getClass();
    }
}
