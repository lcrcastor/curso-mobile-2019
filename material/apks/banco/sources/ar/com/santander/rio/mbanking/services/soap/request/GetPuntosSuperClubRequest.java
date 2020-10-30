package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.GetPuntosSuperClubRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetPuntosSuperClubResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class GetPuntosSuperClubRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private final String TAG = GetPuntosSuperClubRequest.class.getName();
    private GetPuntosSuperClubRequestBean mGetPuntosSuperClubRequestBean;
    private GetPuntosSuperClubResponseBean mGetPuntosSuperClubResponseBean;

    public int getMethod() {
        return 1;
    }

    public GetPuntosSuperClubRequest(Context context, GetPuntosSuperClubRequestBean getPuntosSuperClubRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.mGetPuntosSuperClubRequestBean = getPuntosSuperClubRequestBean;
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
        return this.mGetPuntosSuperClubRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mGetPuntosSuperClubResponseBean == null) {
            this.mGetPuntosSuperClubResponseBean = new GetPuntosSuperClubResponseBean();
        }
        return this.mGetPuntosSuperClubResponseBean.getClass();
    }
}
