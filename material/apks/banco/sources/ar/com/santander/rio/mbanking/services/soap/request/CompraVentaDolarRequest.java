package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.CompraVentaDolarRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CompraVentaDolarResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class CompraVentaDolarRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private final String TAG = CompraVentaDolarRequest.class.getName();
    private CompraVentaDolarRequestBean mCompraVentaDolarRequestBean;
    private CompraVentaDolarResponseBean mCompraVentaDolarResponseBean;

    public int getMethod() {
        return 1;
    }

    public CompraVentaDolarRequest(Context context, CompraVentaDolarRequestBean compraVentaDolarRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.mCompraVentaDolarRequestBean = compraVentaDolarRequestBean;
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
        return this.mCompraVentaDolarRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mCompraVentaDolarResponseBean == null) {
            this.mCompraVentaDolarResponseBean = new CompraVentaDolarResponseBean();
        }
        return this.mCompraVentaDolarResponseBean.getClass();
    }
}
