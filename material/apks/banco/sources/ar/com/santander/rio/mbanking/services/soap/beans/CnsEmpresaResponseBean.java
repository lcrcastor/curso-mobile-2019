package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsEmpresaBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class CnsEmpresaResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public CnsEmpresaBodyResponseBean cnsEmpresaBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public CnsEmpresaResponseBean(PrivateHeaderBean privateHeaderBean, CnsEmpresaBodyResponseBean cnsEmpresaBodyResponseBean2) {
        this.headerBean = privateHeaderBean;
        this.cnsEmpresaBodyResponseBean = cnsEmpresaBodyResponseBean2;
    }

    public CnsEmpresaResponseBean() {
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.cnsEmpresaBodyResponseBean;
    }
}
