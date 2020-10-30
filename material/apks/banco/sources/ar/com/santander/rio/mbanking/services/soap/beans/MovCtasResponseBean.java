package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.MovCtasBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class MovCtasResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("header")
    public PrivateHeaderBean headerBean;
    @SerializedName("body")
    public MovCtasBodyResponseBean movCtasBodyResponseBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public MovCtasResponseBean() {
    }

    public MovCtasResponseBean(PrivateHeaderBean privateHeaderBean, MovCtasBodyResponseBean movCtasBodyResponseBean2) {
        this.headerBean = privateHeaderBean;
        this.movCtasBodyResponseBean = movCtasBodyResponseBean2;
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.movCtasBodyResponseBean;
    }
}
