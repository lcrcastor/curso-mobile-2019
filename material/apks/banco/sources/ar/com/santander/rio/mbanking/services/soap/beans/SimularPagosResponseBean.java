package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.SimularPagosBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class SimularPagosResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("header")
    public PrivateHeaderBean headerBean;
    @SerializedName("body")
    public SimularPagosBodyResponseBean mGetSimularPagosBodyResponseBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public SimularPagosResponseBean() {
    }

    public SimularPagosResponseBean(PrivateHeaderBean privateHeaderBean, SimularPagosBodyResponseBean simularPagosBodyResponseBean) {
        this.headerBean = privateHeaderBean;
        this.mGetSimularPagosBodyResponseBean = simularPagosBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.mGetSimularPagosBodyResponseBean;
    }
}
