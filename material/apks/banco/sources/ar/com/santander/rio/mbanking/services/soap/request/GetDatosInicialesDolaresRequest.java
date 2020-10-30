package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.GetDatosInicialesDolaresRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetDatosInicialesDolaresResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public abstract class GetDatosInicialesDolaresRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private final String TAG = GetDatosInicialesDolaresRequest.class.getName();
    private GetDatosInicialesDolaresRequestBean mGetDatosInicialesDolaresRequestBean;
    private GetDatosInicialesDolaresResponseBean mGetDatosInicialesDolaresResponseBean;

    public int getMethod() {
        return 1;
    }

    public GetDatosInicialesDolaresRequest(Context context, GetDatosInicialesDolaresRequestBean getDatosInicialesDolaresRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.mGetDatosInicialesDolaresRequestBean = getDatosInicialesDolaresRequestBean;
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
        return this.mGetDatosInicialesDolaresRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mGetDatosInicialesDolaresResponseBean == null) {
            this.mGetDatosInicialesDolaresResponseBean = new GetDatosInicialesDolaresResponseBean();
        }
        return this.mGetDatosInicialesDolaresResponseBean.getClass();
    }
}
