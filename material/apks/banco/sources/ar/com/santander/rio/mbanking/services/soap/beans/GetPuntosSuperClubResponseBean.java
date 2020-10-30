package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetPuntosSuperClubBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class GetPuntosSuperClubResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public GetPuntosSuperClubBodyResponseBean getPuntosBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public GetPuntosSuperClubResponseBean() {
    }

    public GetPuntosSuperClubResponseBean(PrivateHeaderBean privateHeaderBean, GetPuntosSuperClubBodyResponseBean getPuntosSuperClubBodyResponseBean) {
        this.headerBean = privateHeaderBean;
        this.getPuntosBodyResponseBean = getPuntosSuperClubBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.getPuntosBodyResponseBean;
    }
}
