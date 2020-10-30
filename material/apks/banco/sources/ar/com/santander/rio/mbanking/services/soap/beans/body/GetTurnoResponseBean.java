package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.soap.beans.BaseResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class GetTurnoResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public GetTurnoBodyResponseBean getTurnoBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public GetTurnoResponseBean() {
        this.getTurnoBodyResponseBean = new GetTurnoBodyResponseBean();
    }

    public GetTurnoResponseBean(PrivateHeaderBean privateHeaderBean, GetTurnoBodyResponseBean getTurnoBodyResponseBean2) {
        this.header = privateHeaderBean;
        this.getTurnoBodyResponseBean = getTurnoBodyResponseBean2;
    }

    public Object getErrorBeanObject() {
        return this.getTurnoBodyResponseBean;
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

    public GetTurnoBodyResponseBean getTurnoBodyResponseBean() {
        return this.getTurnoBodyResponseBean;
    }

    public void setGetTurnoBodyResponseBean(GetTurnoBodyResponseBean getTurnoBodyResponseBean2) {
        this.getTurnoBodyResponseBean = getTurnoBodyResponseBean2;
    }
}
