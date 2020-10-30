package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCuponesSuperClubBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class GetCuponesSuperClubResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public GetCuponesSuperClubBodyResponseBean getCuponesBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public GetCuponesSuperClubResponseBean() {
    }

    public GetCuponesSuperClubResponseBean(PrivateHeaderBean privateHeaderBean, GetCuponesSuperClubBodyResponseBean getCuponesSuperClubBodyResponseBean) {
        this.headerBean = privateHeaderBean;
        this.getCuponesBodyResponseBean = getCuponesSuperClubBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.getCuponesBodyResponseBean;
    }
}
