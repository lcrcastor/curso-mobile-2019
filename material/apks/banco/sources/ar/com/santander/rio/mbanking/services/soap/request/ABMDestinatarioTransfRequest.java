package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.ABMDestinatarioTransfRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ABMDestinatarioTransfResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class ABMDestinatarioTransfRequest extends BaseRequest implements IBeanRequestWS {
    private ABMDestinatarioTransfRequestBean mABMDestinatarioTransfRequestBean;
    private ABMDestinatarioTransfResponseBean mABMDestinatarioTransfResponseBean;

    public int getMethod() {
        return 1;
    }

    public ABMDestinatarioTransfRequest(Context context, ABMDestinatarioTransfRequestBean aBMDestinatarioTransfRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.mABMDestinatarioTransfRequestBean = aBMDestinatarioTransfRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public ABMDestinatarioTransfRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mABMDestinatarioTransfRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mABMDestinatarioTransfResponseBean == null) {
            this.mABMDestinatarioTransfResponseBean = new ABMDestinatarioTransfResponseBean();
        }
        return this.mABMDestinatarioTransfResponseBean.getClass();
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }
}
