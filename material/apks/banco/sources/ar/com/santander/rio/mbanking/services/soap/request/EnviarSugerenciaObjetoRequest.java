package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.EnviarSugerenciaObjetoRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.EnviarSugerenciaObjetoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public class EnviarSugerenciaObjetoRequest extends BaseRequest implements IBeanRequestWS {
    private EnviarSugerenciaObjetoRequestBean enviarSugerenciaObjetoRequestBean;
    private EnviarSugerenciaObjetoResponseBean enviarSugerenciaObjetoResponseBean;
    private EnviarSugerenciaObjetoResponseBean responseBean;

    public int getMethod() {
        return 1;
    }

    public void onResponseBean(IBeanWS iBeanWS) {
    }

    public void onUnknowError(Exception exc) {
    }

    public EnviarSugerenciaObjetoRequest(Context context, EnviarSugerenciaObjetoRequestBean enviarSugerenciaObjetoRequestBean2, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.enviarSugerenciaObjetoRequestBean = enviarSugerenciaObjetoRequestBean2;
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
        return this.enviarSugerenciaObjetoRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.enviarSugerenciaObjetoResponseBean == null) {
            this.enviarSugerenciaObjetoResponseBean = new EnviarSugerenciaObjetoResponseBean();
        }
        return this.enviarSugerenciaObjetoResponseBean.getClass();
    }
}
