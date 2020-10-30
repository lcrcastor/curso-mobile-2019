package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.EnviarTokenOBPBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class EnviarTokenOBPRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    private EnviarTokenOBPBodyRequestBean enviarTokenOBPBodyRequestBean;
    @SerializedName("header")
    private PrivateHeaderBean headerBean;

    public EnviarTokenOBPRequestBean(PrivateHeaderBean privateHeaderBean, EnviarTokenOBPBodyRequestBean enviarTokenOBPBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.enviarTokenOBPBodyRequestBean = enviarTokenOBPBodyRequestBean2;
    }

    public EnviarTokenOBPRequestBean() {
    }

    public EnviarTokenOBPBodyRequestBean enviarTokenOBPBodyRequestBean() {
        return enviarTokenOBPBodyRequestBean();
    }

    public void setEnviarTokenOBPBodyRequestBean(EnviarTokenOBPBodyRequestBean enviarTokenOBPBodyRequestBean2) {
        this.enviarTokenOBPBodyRequestBean = enviarTokenOBPBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
