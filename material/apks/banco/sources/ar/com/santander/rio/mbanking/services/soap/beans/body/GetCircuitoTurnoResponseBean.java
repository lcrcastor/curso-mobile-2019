package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.soap.beans.BaseResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class GetCircuitoTurnoResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public GetCircuitoTurnoBodyResponseBean getCircuitoTurnoBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public GetCircuitoTurnoResponseBean() {
        this.getCircuitoTurnoBodyResponseBean = new GetCircuitoTurnoBodyResponseBean();
    }

    public GetCircuitoTurnoResponseBean(PrivateHeaderBean privateHeaderBean, GetCircuitoTurnoBodyResponseBean getCircuitoTurnoBodyResponseBean2) {
        this.header = privateHeaderBean;
        this.getCircuitoTurnoBodyResponseBean = getCircuitoTurnoBodyResponseBean2;
    }

    public Object getErrorBeanObject() {
        return this.getCircuitoTurnoBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public PrivateHeaderBean getHeader() {
        return this.header;
    }

    public void setHeader(PrivateHeaderBean privateHeaderBean) {
        this.header = privateHeaderBean;
    }

    public GetCircuitoTurnoBodyResponseBean getCircuitoTurnoBodyResponseBean() {
        return this.getCircuitoTurnoBodyResponseBean;
    }

    public void setGetSegurosBodyResponseBean(GetCircuitoTurnoBodyResponseBean getCircuitoTurnoBodyResponseBean2) {
        this.getCircuitoTurnoBodyResponseBean = getCircuitoTurnoBodyResponseBean2;
    }
}
