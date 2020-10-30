package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarSeguroObjetoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class ContratarSeguroObjetoResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public ContratarSeguroObjetoBodyResponseBean contratarSeguroObjetoBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public ContratarSeguroObjetoResponseBean() {
        this.contratarSeguroObjetoBodyResponseBean = new ContratarSeguroObjetoBodyResponseBean();
    }

    public ContratarSeguroObjetoResponseBean(PrivateHeaderBean privateHeaderBean, ContratarSeguroObjetoBodyResponseBean contratarSeguroObjetoBodyResponseBean2) {
        this.header = privateHeaderBean;
        this.contratarSeguroObjetoBodyResponseBean = contratarSeguroObjetoBodyResponseBean2;
    }

    public Object getErrorBeanObject() {
        return this.contratarSeguroObjetoBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public PrivateHeaderBean getHeader() {
        return this.header;
    }

    public void setHeader(PrivateHeaderBean privateHeaderBean) {
        this.header = privateHeaderBean;
    }

    public ContratarSeguroObjetoBodyResponseBean getContratarSeguroObjetoBodyResponseBean() {
        return this.contratarSeguroObjetoBodyResponseBean;
    }

    public void setGetPreguntasFamiliaBodyResponseBean(ContratarSeguroObjetoBodyResponseBean contratarSeguroObjetoBodyResponseBean2) {
        this.contratarSeguroObjetoBodyResponseBean = contratarSeguroObjetoBodyResponseBean2;
    }
}
