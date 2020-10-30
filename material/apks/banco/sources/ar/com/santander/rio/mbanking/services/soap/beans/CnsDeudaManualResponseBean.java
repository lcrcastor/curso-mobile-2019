package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsDeudaManualBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class CnsDeudaManualResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public CnsDeudaManualBodyResponseBean cnsDeudaManualBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public CnsDeudaManualResponseBean(PrivateHeaderBean privateHeaderBean, CnsDeudaManualBodyResponseBean cnsDeudaManualBodyResponseBean2) {
        this.headerBean = privateHeaderBean;
        this.cnsDeudaManualBodyResponseBean = cnsDeudaManualBodyResponseBean2;
    }

    public CnsDeudaManualResponseBean() {
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.cnsDeudaManualBodyResponseBean;
    }
}
