package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.CanjearPuntosSuperClubRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CanjearPuntosSuperClubResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class CanjearPuntosSuperClubRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private final String TAG = CanjearPuntosSuperClubRequest.class.getName();
    private CanjearPuntosSuperClubRequestBean mCanjearPuntosSuperClubRequestBean;
    private CanjearPuntosSuperClubResponseBean mCanjearPuntosSuperClubResponseBean;

    public int getMethod() {
        return 1;
    }

    public CanjearPuntosSuperClubRequest(Context context, CanjearPuntosSuperClubRequestBean canjearPuntosSuperClubRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.mCanjearPuntosSuperClubRequestBean = canjearPuntosSuperClubRequestBean;
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
        return this.mCanjearPuntosSuperClubRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mCanjearPuntosSuperClubResponseBean == null) {
            this.mCanjearPuntosSuperClubResponseBean = new CanjearPuntosSuperClubResponseBean();
        }
        return this.mCanjearPuntosSuperClubResponseBean.getClass();
    }
}
