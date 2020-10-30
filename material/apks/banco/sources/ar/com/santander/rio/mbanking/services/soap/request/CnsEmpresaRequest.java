package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsEmpresaRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsEmpresaResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class CnsEmpresaRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private final String TAG = CnsEmpresaRequest.class.getName();
    private CnsEmpresaRequestBean mCnsEmpresaRequestBean;
    private CnsEmpresaResponseBean mCnsEmpresaResponseBean;

    public int getMethod() {
        return 1;
    }

    public CnsEmpresaRequest(Context context, CnsEmpresaRequestBean cnsEmpresaRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.mCnsEmpresaRequestBean = cnsEmpresaRequestBean;
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
        return this.mCnsEmpresaRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mCnsEmpresaResponseBean == null) {
            this.mCnsEmpresaResponseBean = new CnsEmpresaResponseBean();
        }
        return this.mCnsEmpresaResponseBean.getClass();
    }
}
