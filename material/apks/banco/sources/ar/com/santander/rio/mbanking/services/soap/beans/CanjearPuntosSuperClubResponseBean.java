package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.CanjearPuntosSuperClubBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class CanjearPuntosSuperClubResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public CanjearPuntosSuperClubBodyResponseBean canjearPuntosBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public CanjearPuntosSuperClubResponseBean() {
    }

    public CanjearPuntosSuperClubResponseBean(PrivateHeaderBean privateHeaderBean, CanjearPuntosSuperClubBodyResponseBean canjearPuntosSuperClubBodyResponseBean) {
        this.headerBean = privateHeaderBean;
        this.canjearPuntosBodyResponseBean = canjearPuntosSuperClubBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.canjearPuntosBodyResponseBean;
    }
}
