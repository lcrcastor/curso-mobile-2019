package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsDeudaRecargaBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class CnsDeudaRecargaResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public CnsDeudaRecargaBodyResponseBean cnsDeudaRecargaBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public CnsDeudaRecargaResponseBean() {
        this.cnsDeudaRecargaBodyResponseBean = new CnsDeudaRecargaBodyResponseBean();
    }

    public CnsDeudaRecargaResponseBean(PrivateHeaderBean privateHeaderBean, CnsDeudaRecargaBodyResponseBean cnsDeudaRecargaBodyResponseBean2) {
        this.headerBean = privateHeaderBean;
        this.cnsDeudaRecargaBodyResponseBean = cnsDeudaRecargaBodyResponseBean2;
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.cnsDeudaRecargaBodyResponseBean;
    }

    public CnsDeudaRecargaBodyResponseBean getCnsDeudaRecargaBodyResponseBean() {
        return this.cnsDeudaRecargaBodyResponseBean;
    }

    public void setCnsDeudaRecargaBodyResponseBean(CnsDeudaRecargaBodyResponseBean cnsDeudaRecargaBodyResponseBean2) {
        this.cnsDeudaRecargaBodyResponseBean = cnsDeudaRecargaBodyResponseBean2;
    }
}
