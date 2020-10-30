package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarSeguroAccidenteBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class ContratarSeguroAccidenteResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public ContratarSeguroAccidenteBodyResponseBean contratarSeguroAccidenteBean;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public ContratarSeguroAccidenteResponseBean() {
        this.contratarSeguroAccidenteBean = new ContratarSeguroAccidenteBodyResponseBean();
    }

    public ContratarSeguroAccidenteResponseBean(PrivateHeaderBean privateHeaderBean, ContratarSeguroAccidenteBodyResponseBean contratarSeguroAccidenteBodyResponseBean) {
        this.header = privateHeaderBean;
        this.contratarSeguroAccidenteBean = contratarSeguroAccidenteBodyResponseBean;
    }

    public Object getErrorBeanObject() {
        return this.contratarSeguroAccidenteBean;
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

    public ContratarSeguroAccidenteBodyResponseBean getContratarSeguroAccidenteBean() {
        return this.contratarSeguroAccidenteBean;
    }

    public void setContratarSeguroAccidenteBean(ContratarSeguroAccidenteBodyResponseBean contratarSeguroAccidenteBodyResponseBean) {
        this.contratarSeguroAccidenteBean = contratarSeguroAccidenteBodyResponseBean;
    }
}
