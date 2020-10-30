package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ABMViajesBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class ABMViajesResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public ABMViajesBodyResponseBean abmViajesBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public ABMViajesResponseBean() {
        this.abmViajesBodyResponseBean = new ABMViajesBodyResponseBean();
    }

    public ABMViajesResponseBean(PrivateHeaderBean privateHeaderBean, ABMViajesBodyResponseBean aBMViajesBodyResponseBean) {
        this.header = privateHeaderBean;
        this.abmViajesBodyResponseBean = aBMViajesBodyResponseBean;
    }

    public Object getErrorBeanObject() {
        return this.abmViajesBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public ABMViajesBodyResponseBean getAbmViajesBodyResponseBean() {
        return this.abmViajesBodyResponseBean;
    }

    public void setAbmViajesBodyResponseBean(ABMViajesBodyResponseBean aBMViajesBodyResponseBean) {
        this.abmViajesBodyResponseBean = aBMViajesBodyResponseBean;
    }
}
