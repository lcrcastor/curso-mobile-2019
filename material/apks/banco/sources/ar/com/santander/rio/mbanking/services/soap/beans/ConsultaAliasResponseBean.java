package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaAliasBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class ConsultaAliasResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    private ConsultaAliasBodyResponseBean consultaAliasBodyResponseBean;
    @SerializedName("header")
    private PrivateHeaderBean header;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public ConsultaAliasResponseBean() {
        this.consultaAliasBodyResponseBean = new ConsultaAliasBodyResponseBean();
    }

    public ConsultaAliasResponseBean(PrivateHeaderBean privateHeaderBean, ConsultaAliasBodyResponseBean consultaAliasBodyResponseBean2) {
        this.header = privateHeaderBean;
        this.consultaAliasBodyResponseBean = consultaAliasBodyResponseBean2;
    }

    public Object getErrorBeanObject() {
        return this.consultaAliasBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public ConsultaAliasBodyResponseBean getConsultaAliasBodyResponseBean() {
        return this.consultaAliasBodyResponseBean;
    }

    public void setConsultaAliasBodyResponseBean(ConsultaAliasBodyResponseBean consultaAliasBodyResponseBean2) {
        this.consultaAliasBodyResponseBean = consultaAliasBodyResponseBean2;
    }
}
