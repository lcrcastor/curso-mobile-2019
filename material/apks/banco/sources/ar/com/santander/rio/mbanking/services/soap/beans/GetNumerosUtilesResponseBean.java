package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetNumerosUtilesBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class GetNumerosUtilesResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public GetNumerosUtilesBodyResponseBean getNumerosUtilesBodyResponseBean;
    @SerializedName("header")
    public HeaderBean headerBean;
    private JSONObject jsonObject;

    public GetNumerosUtilesBodyResponseBean getGetNumerosUtilesBodyResponseBean() {
        return this.getNumerosUtilesBodyResponseBean;
    }

    public void setGetNumerosUtilesBodyResponseBean(GetNumerosUtilesBodyResponseBean getNumerosUtilesBodyResponseBean2) {
        this.getNumerosUtilesBodyResponseBean = getNumerosUtilesBodyResponseBean2;
    }

    public HeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(HeaderBean headerBean2) {
        this.headerBean = headerBean2;
    }

    public GetNumerosUtilesResponseBean() {
        this.headerBean = new HeaderBean();
        this.getNumerosUtilesBodyResponseBean = new GetNumerosUtilesBodyResponseBean();
    }

    public GetNumerosUtilesResponseBean(GetNumerosUtilesBodyResponseBean getNumerosUtilesBodyResponseBean2) {
        this.getNumerosUtilesBodyResponseBean = getNumerosUtilesBodyResponseBean2;
    }

    public GetNumerosUtilesResponseBean(HeaderBean headerBean2, GetNumerosUtilesBodyResponseBean getNumerosUtilesBodyResponseBean2) {
        this.headerBean = headerBean2;
        this.getNumerosUtilesBodyResponseBean = getNumerosUtilesBodyResponseBean2;
    }

    public Class getClassBean() {
        return getClass();
    }

    public void setJsonElement(JSONObject jSONObject) {
        this.jsonObject = jSONObject;
    }

    public JSONObject getJsonElement() {
        return this.jsonObject;
    }

    public Object getErrorBeanObject() {
        return this.getNumerosUtilesBodyResponseBean;
    }
}
