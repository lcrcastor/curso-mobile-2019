package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsTasasPFTRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsTasasPFTResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class CnsTasasPFTRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private final String TAG = CnsTasasPFTRequest.class.getName();
    private CnsTasasPFTRequestBean mCnsTasasPFTRequestBean;
    private CnsTasasPFTResponseBean mCnsTasasPFTResponseBean;

    public int getMethod() {
        return 1;
    }

    public CnsTasasPFTRequest(Context context, CnsTasasPFTRequestBean cnsTasasPFTRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.mCnsTasasPFTRequestBean = cnsTasasPFTRequestBean;
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
        return this.mCnsTasasPFTRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mCnsTasasPFTResponseBean == null) {
            this.mCnsTasasPFTResponseBean = new CnsTasasPFTResponseBean();
        }
        return this.mCnsTasasPFTResponseBean.getClass();
    }
}
