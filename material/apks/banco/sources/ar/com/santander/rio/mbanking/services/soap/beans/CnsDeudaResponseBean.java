package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsDeudaBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class CnsDeudaResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public CnsDeudaBodyResponseBean cnsDeudaBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public CnsDeudaResponseBean() {
        this.cnsDeudaBodyResponseBean = new CnsDeudaBodyResponseBean();
    }

    public CnsDeudaResponseBean(PrivateHeaderBean privateHeaderBean, CnsDeudaBodyResponseBean cnsDeudaBodyResponseBean2) {
        this.headerBean = privateHeaderBean;
        this.cnsDeudaBodyResponseBean = cnsDeudaBodyResponseBean2;
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.cnsDeudaBodyResponseBean;
    }

    public CnsDeudaBodyResponseBean getCnsDeudaBodyResponseBean() {
        return this.cnsDeudaBodyResponseBean;
    }

    public void setCnsDeudaBodyResponseBean(CnsDeudaBodyResponseBean cnsDeudaBodyResponseBean2) {
        this.cnsDeudaBodyResponseBean = cnsDeudaBodyResponseBean2;
    }
}
