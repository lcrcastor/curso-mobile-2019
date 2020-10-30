package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetDatosInicialesDolaresBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class GetDatosInicialesDolaresResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public GetDatosInicialesDolaresBodyResponseBean getDatosInicialesDolaresBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public GetDatosInicialesDolaresResponseBean(PrivateHeaderBean privateHeaderBean, GetDatosInicialesDolaresBodyResponseBean getDatosInicialesDolaresBodyResponseBean2) {
        this.headerBean = privateHeaderBean;
        this.getDatosInicialesDolaresBodyResponseBean = getDatosInicialesDolaresBodyResponseBean2;
    }

    public GetDatosInicialesDolaresResponseBean() {
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.getDatosInicialesDolaresBodyResponseBean;
    }
}
