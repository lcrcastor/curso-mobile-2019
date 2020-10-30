package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.EnviarTokenOBPRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.EnviarTokenOBPResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;

public class EnviarTokenOBPRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private EnviarTokenOBPRequestBean mEnviarTokenOBPRequestBean;
    private EnviarTokenOBPResponseBean mEnviarTokenOBPResponseBean;

    public int getMethod() {
        return 1;
    }

    public void onResponseBean(IBeanWS iBeanWS) {
    }

    public void onUnknowError(Exception exc) {
    }

    public EnviarTokenOBPRequest(Context context, EnviarTokenOBPRequestBean enviarTokenOBPRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, true);
        this.mEnviarTokenOBPRequestBean = enviarTokenOBPRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public EnviarTokenOBPRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public IBeanWS getBeanToRequest() {
        return this.mEnviarTokenOBPRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mEnviarTokenOBPResponseBean == null) {
            this.mEnviarTokenOBPResponseBean = new EnviarTokenOBPResponseBean();
        }
        return this.mEnviarTokenOBPResponseBean.getClass();
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
}
