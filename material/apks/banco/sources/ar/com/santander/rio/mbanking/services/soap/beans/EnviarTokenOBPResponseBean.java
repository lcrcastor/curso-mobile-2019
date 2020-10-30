package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.EnviarTokenOBPBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class EnviarTokenOBPResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    private EnviarTokenOBPBodyResponseBean enviarTokenOBPBodyResponseBean;
    @SerializedName("header")
    private PrivateHeaderBean headerBean;

    public EnviarTokenOBPResponseBean(EnviarTokenOBPBodyResponseBean enviarTokenOBPBodyResponseBean2) {
        this.enviarTokenOBPBodyResponseBean = enviarTokenOBPBodyResponseBean2;
    }

    public EnviarTokenOBPResponseBean() {
    }

    public Object getErrorBeanObject() {
        return this.enviarTokenOBPBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public EnviarTokenOBPBodyResponseBean enviarTokenOBPBodyResponseBean() {
        return this.enviarTokenOBPBodyResponseBean;
    }

    public void setEnviarTokenOBPBodyResponseBean(EnviarTokenOBPBodyResponseBean enviarTokenOBPBodyResponseBean2) {
        this.enviarTokenOBPBodyResponseBean = enviarTokenOBPBodyResponseBean2;
    }

    public PrivateHeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }
}
