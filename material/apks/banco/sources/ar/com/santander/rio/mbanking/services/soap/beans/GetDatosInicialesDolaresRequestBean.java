package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetDatosInicialesDolaresBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class GetDatosInicialesDolaresRequestBean implements IBeanWS {
    @SerializedName("body")
    public GetDatosInicialesDolaresBodyRequestBean getDatosInicialesDolaresBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public GetDatosInicialesDolaresRequestBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }

    public GetDatosInicialesDolaresRequestBean(PrivateHeaderBean privateHeaderBean, GetDatosInicialesDolaresBodyRequestBean getDatosInicialesDolaresBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.getDatosInicialesDolaresBodyRequestBean = getDatosInicialesDolaresBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
