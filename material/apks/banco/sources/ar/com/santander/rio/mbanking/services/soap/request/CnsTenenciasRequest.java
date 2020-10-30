package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsTenenciasRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsTenenciasResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class CnsTenenciasRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private final String TAG = CnsTenenciasRequest.class.getName();
    private CnsTenenciasRequestBean mCnsTenenciasRequestBean;
    private CnsTenenciasResponseBean mCnsTenenciasResponseBean;

    public int getMethod() {
        return 1;
    }

    public CnsTenenciasRequest(Context context, CnsTenenciasRequestBean cnsTenenciasRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.mCnsTenenciasRequestBean = cnsTenenciasRequestBean;
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
        return this.mCnsTenenciasRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mCnsTenenciasResponseBean == null) {
            this.mCnsTenenciasResponseBean = new CnsTenenciasResponseBean();
        }
        return this.mCnsTenenciasResponseBean.getClass();
    }
}
