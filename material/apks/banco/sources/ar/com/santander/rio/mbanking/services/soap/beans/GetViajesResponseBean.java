package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetViajesBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class GetViajesResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public GetViajesBodyResponseBean getViajesBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public GetViajesResponseBean() {
        this.getViajesBodyResponseBean = new GetViajesBodyResponseBean();
    }

    public GetViajesResponseBean(PrivateHeaderBean privateHeaderBean, GetViajesBodyResponseBean getViajesBodyResponseBean2) {
        this.header = privateHeaderBean;
        this.getViajesBodyResponseBean = getViajesBodyResponseBean2;
    }

    public Object getErrorBeanObject() {
        return this.getViajesBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public GetViajesBodyResponseBean getGetViajesBodyResponseBean() {
        return this.getViajesBodyResponseBean;
    }

    public void setGetViajesBodyResponseBean(GetViajesBodyResponseBean getViajesBodyResponseBean2) {
        this.getViajesBodyResponseBean = getViajesBodyResponseBean2;
    }
}
