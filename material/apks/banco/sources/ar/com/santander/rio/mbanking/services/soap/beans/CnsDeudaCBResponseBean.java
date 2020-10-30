package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsDeudaCBBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class CnsDeudaCBResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public CnsDeudaCBBodyResponseBean cnsDeudaCBBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public CnsDeudaCBResponseBean() {
    }

    public CnsDeudaCBResponseBean(PrivateHeaderBean privateHeaderBean, CnsDeudaCBBodyResponseBean cnsDeudaCBBodyResponseBean2) {
        this.headerBean = privateHeaderBean;
        this.cnsDeudaCBBodyResponseBean = cnsDeudaCBBodyResponseBean2;
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.cnsDeudaCBBodyResponseBean;
    }
}
