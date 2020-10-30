package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCategoriasSuperClubBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class GetCategoriasSuperClubResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public GetCategoriasSuperClubBodyResponseBean getCategoriasBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public GetCategoriasSuperClubResponseBean() {
    }

    public GetCategoriasSuperClubResponseBean(PrivateHeaderBean privateHeaderBean, GetCategoriasSuperClubBodyResponseBean getCategoriasSuperClubBodyResponseBean) {
        this.headerBean = privateHeaderBean;
        this.getCategoriasBodyResponseBean = getCategoriasSuperClubBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.getCategoriasBodyResponseBean;
    }
}
