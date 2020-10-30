package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ABMDestinatarioTransfBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeader;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class ABMDestinatarioTransfResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public ABMDestinatarioTransfBodyResponseBean abmDestinatarioTransfBodyResponseBean;
    @SerializedName("header")
    public PrivateHeader headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public ABMDestinatarioTransfResponseBean() {
        this.abmDestinatarioTransfBodyResponseBean = new ABMDestinatarioTransfBodyResponseBean();
    }

    public ABMDestinatarioTransfResponseBean(PrivateHeader privateHeader, ABMDestinatarioTransfBodyResponseBean aBMDestinatarioTransfBodyResponseBean) {
        this.headerBean = privateHeader;
        this.abmDestinatarioTransfBodyResponseBean = aBMDestinatarioTransfBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.abmDestinatarioTransfBodyResponseBean;
    }

    public ABMDestinatarioTransfBodyResponseBean getAbmDestinatarioTransfBodyResponseBean() {
        return this.abmDestinatarioTransfBodyResponseBean;
    }

    public void setAbmDestinatarioTransfBodyResponseBean(ABMDestinatarioTransfBodyResponseBean aBMDestinatarioTransfBodyResponseBean) {
        this.abmDestinatarioTransfBodyResponseBean = aBMDestinatarioTransfBodyResponseBean;
    }
}
