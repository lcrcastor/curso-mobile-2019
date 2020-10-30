package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.PagoServiciosBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class PagoServiciosResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("header")
    public PrivateHeaderBean headerBean;
    @SerializedName("body")
    public PagoServiciosBodyResponseBean pagoServiciosBodyResponseBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public PagoServiciosResponseBean() {
        this.pagoServiciosBodyResponseBean = new PagoServiciosBodyResponseBean();
    }

    public PagoServiciosResponseBean(PrivateHeaderBean privateHeaderBean, PagoServiciosBodyResponseBean pagoServiciosBodyResponseBean2) {
        this.headerBean = privateHeaderBean;
        this.pagoServiciosBodyResponseBean = pagoServiciosBodyResponseBean2;
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.pagoServiciosBodyResponseBean;
    }
}
