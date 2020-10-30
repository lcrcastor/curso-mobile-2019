package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarSeguroMovilBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class ContratarSeguroMovilResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public ContratarSeguroMovilBodyResponseBean contratarSeguroMovilBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public Class getClassBean() {
        return null;
    }

    public void setJsonElement(JSONObject jSONObject) {
    }

    public ContratarSeguroMovilResponseBean() {
        this.contratarSeguroMovilBodyResponseBean = new ContratarSeguroMovilBodyResponseBean();
    }

    public ContratarSeguroMovilResponseBean(PrivateHeaderBean privateHeaderBean, ContratarSeguroMovilBodyResponseBean contratarSeguroMovilBodyResponseBean2) {
        this.header = privateHeaderBean;
        this.contratarSeguroMovilBodyResponseBean = contratarSeguroMovilBodyResponseBean2;
    }

    public Object getErrorBeanObject() {
        return this.contratarSeguroMovilBodyResponseBean;
    }

    public PrivateHeaderBean getHeader() {
        return this.header;
    }

    public void setHeader(PrivateHeaderBean privateHeaderBean) {
        this.header = privateHeaderBean;
    }

    public ContratarSeguroMovilBodyResponseBean getContratarSeguroMovilBodyResponseBean() {
        return this.contratarSeguroMovilBodyResponseBean;
    }

    public void setContratarSeguroMovilBodyResponseBean(ContratarSeguroMovilBodyResponseBean contratarSeguroMovilBodyResponseBean2) {
        this.contratarSeguroMovilBodyResponseBean = contratarSeguroMovilBodyResponseBean2;
    }
}
