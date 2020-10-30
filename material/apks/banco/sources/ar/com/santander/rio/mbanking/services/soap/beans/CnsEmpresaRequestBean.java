package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsEmpresaBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class CnsEmpresaRequestBean implements IBeanWS {
    @SerializedName("body")
    public CnsEmpresaBodyRequestBean cnsEmpresaBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public CnsEmpresaRequestBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }

    public CnsEmpresaRequestBean(PrivateHeaderBean privateHeaderBean, CnsEmpresaBodyRequestBean cnsEmpresaBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.cnsEmpresaBodyRequestBean = cnsEmpresaBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
