package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ABMAliasBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class ABMAliasResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public ABMAliasBodyResponseBean abmAliasBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public ABMAliasResponseBean() {
        this.abmAliasBodyResponseBean = new ABMAliasBodyResponseBean();
    }

    public ABMAliasResponseBean(PrivateHeaderBean privateHeaderBean, ABMAliasBodyResponseBean aBMAliasBodyResponseBean) {
        this.header = privateHeaderBean;
        this.abmAliasBodyResponseBean = aBMAliasBodyResponseBean;
    }

    public Object getErrorBeanObject() {
        return this.abmAliasBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public ABMAliasBodyResponseBean getAbmAliasBodyResponseBean() {
        return this.abmAliasBodyResponseBean;
    }

    public void setAbmAliasBodyResponseBean(ABMAliasBodyResponseBean aBMAliasBodyResponseBean) {
        this.abmAliasBodyResponseBean = aBMAliasBodyResponseBean;
    }
}
