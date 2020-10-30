package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.PagoServiciosBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class PagoServicioCBResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("header")
    public PrivateHeaderBean headerBean;
    @SerializedName("body")
    public PagoServiciosBodyResponseBean pagoServicioCBBodyResponseBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public PagoServicioCBResponseBean(PrivateHeaderBean privateHeaderBean, PagoServiciosBodyResponseBean pagoServiciosBodyResponseBean) {
        this.headerBean = privateHeaderBean;
        this.pagoServicioCBBodyResponseBean = pagoServiciosBodyResponseBean;
    }

    public PagoServicioCBResponseBean() {
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.pagoServicioCBBodyResponseBean;
    }
}
