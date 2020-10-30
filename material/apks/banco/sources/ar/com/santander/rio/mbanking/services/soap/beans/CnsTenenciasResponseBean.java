package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsTenenciasBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class CnsTenenciasResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public CnsTenenciasBodyResponseBean cnsTenenciasBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public CnsTenenciasResponseBean(PrivateHeaderBean privateHeaderBean, CnsTenenciasBodyResponseBean cnsTenenciasBodyResponseBean2) {
        this.headerBean = privateHeaderBean;
        this.cnsTenenciasBodyResponseBean = cnsTenenciasBodyResponseBean2;
    }

    public CnsTenenciasResponseBean() {
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.cnsTenenciasBodyResponseBean;
    }
}
