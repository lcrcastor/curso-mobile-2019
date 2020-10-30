package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.GetCategoriasSuperClubRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetCategoriasSuperClubResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class GetCategoriasSuperClubRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private final String TAG = GetCategoriasSuperClubRequest.class.getName();
    private GetCategoriasSuperClubRequestBean mGetCategoriasSuperClubRequestBean;
    private GetCategoriasSuperClubResponseBean mGetCategoriasSuperClubResponseBean;

    public int getMethod() {
        return 1;
    }

    public GetCategoriasSuperClubRequest(Context context, GetCategoriasSuperClubRequestBean getCategoriasSuperClubRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.mGetCategoriasSuperClubRequestBean = getCategoriasSuperClubRequestBean;
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
        return this.mGetCategoriasSuperClubRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mGetCategoriasSuperClubResponseBean == null) {
            this.mGetCategoriasSuperClubResponseBean = new GetCategoriasSuperClubResponseBean();
        }
        return this.mGetCategoriasSuperClubResponseBean.getClass();
    }
}
