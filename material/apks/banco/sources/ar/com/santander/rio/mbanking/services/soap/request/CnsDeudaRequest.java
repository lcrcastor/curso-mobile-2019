package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsDeudaRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsDeudaResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class CnsDeudaRequest extends BaseRequest implements IBeanRequestWS {
    private CnsDeudaRequestBean mCnsDeudaRequestBean;
    private CnsDeudaResponseBean mCnsDeudaResponseBean;

    public int getMethod() {
        return 1;
    }

    public CnsDeudaRequest(Context context, CnsDeudaRequestBean cnsDeudaRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.mCnsDeudaRequestBean = cnsDeudaRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mCnsDeudaRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mCnsDeudaResponseBean == null) {
            this.mCnsDeudaResponseBean = new CnsDeudaResponseBean();
        }
        return this.mCnsDeudaResponseBean.getClass();
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }
}
