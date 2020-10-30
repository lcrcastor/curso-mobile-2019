package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaPrestamosPermitidosBodyResponseBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class ConsultaPrestamosPermitidosResponseBeanProd implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public ConsultaPrestamosPermitidosBodyResponseBeanProd consultaPrestamosPermitidosBodyResponseBeanProd;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public ConsultaPrestamosPermitidosResponseBeanProd() {
    }

    public ConsultaPrestamosPermitidosResponseBeanProd(PrivateHeaderBean privateHeaderBean, ConsultaPrestamosPermitidosBodyResponseBeanProd consultaPrestamosPermitidosBodyResponseBeanProd2) {
        this.headerBean = privateHeaderBean;
        this.consultaPrestamosPermitidosBodyResponseBeanProd = consultaPrestamosPermitidosBodyResponseBeanProd2;
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.consultaPrestamosPermitidosBodyResponseBeanProd;
    }
}
