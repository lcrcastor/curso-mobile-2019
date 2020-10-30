package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.EnviarSugerenciaObjetoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class EnviarSugerenciaObjetoResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public EnviarSugerenciaObjetoBodyResponseBean enviarSugerenciaObjetoBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public EnviarSugerenciaObjetoResponseBean(PrivateHeaderBean privateHeaderBean, EnviarSugerenciaObjetoBodyResponseBean enviarSugerenciaObjetoBodyResponseBean2) {
        this.headerBean = privateHeaderBean;
        this.enviarSugerenciaObjetoBodyResponseBean = enviarSugerenciaObjetoBodyResponseBean2;
    }

    public EnviarSugerenciaObjetoResponseBean() {
        this.enviarSugerenciaObjetoBodyResponseBean = new EnviarSugerenciaObjetoBodyResponseBean();
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.enviarSugerenciaObjetoBodyResponseBean;
    }

    public EnviarSugerenciaObjetoBodyResponseBean getEnviarSugerenciaObjetoBodyResponseBean() {
        return this.enviarSugerenciaObjetoBodyResponseBean;
    }

    public void setEnviarSugerenciaObjetoBodyResponseBean(EnviarSugerenciaObjetoBodyResponseBean enviarSugerenciaObjetoBodyResponseBean2) {
        this.enviarSugerenciaObjetoBodyResponseBean = enviarSugerenciaObjetoBodyResponseBean2;
    }

    public PrivateHeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }
}
