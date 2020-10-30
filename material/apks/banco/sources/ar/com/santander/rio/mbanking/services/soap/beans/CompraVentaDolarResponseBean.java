package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.CompraVentaDolarBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class CompraVentaDolarResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public CompraVentaDolarBodyResponseBean compraVentaDolarBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public CompraVentaDolarResponseBean(PrivateHeaderBean privateHeaderBean, CompraVentaDolarBodyResponseBean compraVentaDolarBodyResponseBean2) {
        this.headerBean = privateHeaderBean;
        this.compraVentaDolarBodyResponseBean = compraVentaDolarBodyResponseBean2;
    }

    public CompraVentaDolarResponseBean() {
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.compraVentaDolarBodyResponseBean;
    }
}
