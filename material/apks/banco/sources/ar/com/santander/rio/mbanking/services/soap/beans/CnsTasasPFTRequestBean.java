package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsTasasPFTBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class CnsTasasPFTRequestBean implements IBeanWS {
    @SerializedName("body")
    public CnsTasasPFTBodyRequestBean cnsTasasPFTBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public CnsTasasPFTRequestBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }

    public CnsTasasPFTRequestBean(PrivateHeaderBean privateHeaderBean, CnsTasasPFTBodyRequestBean cnsTasasPFTBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.cnsTasasPFTBodyRequestBean = cnsTasasPFTBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
