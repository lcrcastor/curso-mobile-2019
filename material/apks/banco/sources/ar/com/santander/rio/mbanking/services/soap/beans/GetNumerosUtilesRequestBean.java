package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetNumerosUtilesBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class GetNumerosUtilesRequestBean implements IBeanWS {
    @SerializedName("body")
    public GetNumerosUtilesBodyRequestBean getNumerosUtilesBodyRequestBean;
    @SerializedName("header")
    public HeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public GetNumerosUtilesRequestBean() {
        this.headerBean = new HeaderBean();
        this.getNumerosUtilesBodyRequestBean = new GetNumerosUtilesBodyRequestBean();
    }

    public GetNumerosUtilesRequestBean(GetNumerosUtilesBodyRequestBean getNumerosUtilesBodyRequestBean2) {
        this.getNumerosUtilesBodyRequestBean = getNumerosUtilesBodyRequestBean2;
    }

    public GetNumerosUtilesRequestBean(HeaderBean headerBean2, GetNumerosUtilesBodyRequestBean getNumerosUtilesBodyRequestBean2) {
        this.headerBean = headerBean2;
        this.getNumerosUtilesBodyRequestBean = getNumerosUtilesBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
