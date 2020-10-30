package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarCompraProtegidaBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class ContratarCompraProtegidaResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public ContratarCompraProtegidaBodyResponseBean contratarCompraProtegidaBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public Class getClassBean() {
        return null;
    }

    public void setJsonElement(JSONObject jSONObject) {
    }

    public ContratarCompraProtegidaResponseBean() {
        this.contratarCompraProtegidaBodyResponseBean = new ContratarCompraProtegidaBodyResponseBean();
    }

    public ContratarCompraProtegidaResponseBean(PrivateHeaderBean privateHeaderBean, ContratarCompraProtegidaBodyResponseBean contratarCompraProtegidaBodyResponseBean2) {
        this.header = privateHeaderBean;
        this.contratarCompraProtegidaBodyResponseBean = contratarCompraProtegidaBodyResponseBean2;
    }

    public Object getErrorBeanObject() {
        return this.contratarCompraProtegidaBodyResponseBean;
    }

    public PrivateHeaderBean getHeader() {
        return this.header;
    }

    public void setHeader(PrivateHeaderBean privateHeaderBean) {
        this.header = privateHeaderBean;
    }

    public ContratarCompraProtegidaBodyResponseBean getContratarCompraProtegidaBodyResponseBean() {
        return this.contratarCompraProtegidaBodyResponseBean;
    }

    public void setContratarCompraProtegidaBodyResponseBean(ContratarCompraProtegidaBodyResponseBean contratarCompraProtegidaBodyResponseBean2) {
        this.contratarCompraProtegidaBodyResponseBean = contratarCompraProtegidaBodyResponseBean2;
    }
}
