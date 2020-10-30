package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ConstPlazoFijoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class ConstPlazoFijoResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public ConstPlazoFijoBodyResponseBean constPlazoFijoBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public ConstPlazoFijoResponseBean() {
        this.constPlazoFijoBodyResponseBean = new ConstPlazoFijoBodyResponseBean();
    }

    public ConstPlazoFijoResponseBean(PrivateHeaderBean privateHeaderBean, ConstPlazoFijoBodyResponseBean constPlazoFijoBodyResponseBean2) {
        this.headerBean = privateHeaderBean;
        this.constPlazoFijoBodyResponseBean = constPlazoFijoBodyResponseBean2;
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.constPlazoFijoBodyResponseBean;
    }
}
