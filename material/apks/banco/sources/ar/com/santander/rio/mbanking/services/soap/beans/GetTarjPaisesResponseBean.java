package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetTarjPaisesBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class GetTarjPaisesResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public GetTarjPaisesBodyResponseBean getTarjPaisesBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public GetTarjPaisesResponseBean() {
        this.getTarjPaisesBodyResponseBean = new GetTarjPaisesBodyResponseBean();
    }

    public GetTarjPaisesResponseBean(PrivateHeaderBean privateHeaderBean, GetTarjPaisesBodyResponseBean getTarjPaisesBodyResponseBean2) {
        this.header = privateHeaderBean;
        this.getTarjPaisesBodyResponseBean = getTarjPaisesBodyResponseBean2;
    }

    public Object getErrorBeanObject() {
        return this.getTarjPaisesBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public GetTarjPaisesBodyResponseBean getGetTarjPaisesBodyResponseBean() {
        return this.getTarjPaisesBodyResponseBean;
    }

    public void setGetTarjPaisesBodyResponseBean(GetTarjPaisesBodyResponseBean getTarjPaisesBodyResponseBean2) {
        this.getTarjPaisesBodyResponseBean = getTarjPaisesBodyResponseBean2;
    }
}
