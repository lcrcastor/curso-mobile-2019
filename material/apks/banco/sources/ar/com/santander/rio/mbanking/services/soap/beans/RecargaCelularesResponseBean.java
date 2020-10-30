package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.RecargaCelularesBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class RecargaCelularesResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("header")
    public PrivateHeaderBean headerBean;
    @SerializedName("body")
    public RecargaCelularesBodyResponseBean recargaCelularesBodyResponseBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public RecargaCelularesResponseBean() {
        this.recargaCelularesBodyResponseBean = new RecargaCelularesBodyResponseBean();
    }

    public RecargaCelularesResponseBean(PrivateHeaderBean privateHeaderBean, RecargaCelularesBodyResponseBean recargaCelularesBodyResponseBean2) {
        this.headerBean = privateHeaderBean;
        this.recargaCelularesBodyResponseBean = recargaCelularesBodyResponseBean2;
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.recargaCelularesBodyResponseBean;
    }
}
