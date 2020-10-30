package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsDeudaManualBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class CnsDeudaManualRequestBean implements IBeanWS {
    @SerializedName("body")
    public CnsDeudaManualBodyRequestBean cnsDeudaManualBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public CnsDeudaManualRequestBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }

    public CnsDeudaManualRequestBean(PrivateHeaderBean privateHeaderBean, CnsDeudaManualBodyRequestBean cnsDeudaManualBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.cnsDeudaManualBodyRequestBean = cnsDeudaManualBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
