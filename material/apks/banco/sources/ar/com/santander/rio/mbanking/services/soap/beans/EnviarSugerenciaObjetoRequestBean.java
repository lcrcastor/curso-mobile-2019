package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.EnviarSugerenciaObjetoBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class EnviarSugerenciaObjetoRequestBean implements IBeanWS {
    @SerializedName("body")
    public EnviarSugerenciaObjetoBodyRequestBean enviarSugerenciaObjetoBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public EnviarSugerenciaObjetoRequestBean(PrivateHeaderBean privateHeaderBean, EnviarSugerenciaObjetoBodyRequestBean enviarSugerenciaObjetoBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.enviarSugerenciaObjetoBodyRequestBean = enviarSugerenciaObjetoBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
