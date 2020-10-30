package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsDeudaRecargaRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsDeudaRecargaResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class CnsDeudaRecargaRequest extends BaseRequest implements IBeanRequestWS {
    private CnsDeudaRecargaRequestBean mCnsDeudaRecargaRequestBean;
    private CnsDeudaRecargaResponseBean mCnsDeudaRecargaResponseBean;

    public int getMethod() {
        return 1;
    }

    public CnsDeudaRecargaRequest(Context context, CnsDeudaRecargaRequestBean cnsDeudaRecargaRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.mCnsDeudaRecargaRequestBean = cnsDeudaRecargaRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public CnsDeudaRecargaRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mCnsDeudaRecargaRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mCnsDeudaRecargaResponseBean == null) {
            this.mCnsDeudaRecargaResponseBean = new CnsDeudaRecargaResponseBean();
        }
        return this.mCnsDeudaRecargaResponseBean.getClass();
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }
}
