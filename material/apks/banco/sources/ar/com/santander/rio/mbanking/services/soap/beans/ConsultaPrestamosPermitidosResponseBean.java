package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaPrestamosPermitidosBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class ConsultaPrestamosPermitidosResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public ConsultaPrestamosPermitidosBodyResponseBean consultaPrestamosPermitidosBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public ConsultaPrestamosPermitidosResponseBean() {
    }

    public ConsultaPrestamosPermitidosResponseBean(PrivateHeaderBean privateHeaderBean, ConsultaPrestamosPermitidosBodyResponseBean consultaPrestamosPermitidosBodyResponseBean2) {
        this.headerBean = privateHeaderBean;
        this.consultaPrestamosPermitidosBodyResponseBean = consultaPrestamosPermitidosBodyResponseBean2;
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.consultaPrestamosPermitidosBodyResponseBean;
    }
}
