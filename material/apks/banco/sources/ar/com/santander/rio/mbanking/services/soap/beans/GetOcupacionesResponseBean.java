package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetOcupacionesBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class GetOcupacionesResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    private GetOcupacionesBodyResponseBean getOcupacionesBodyResponseBean;
    @SerializedName("header")
    private PrivateHeaderBean header;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public GetOcupacionesResponseBean() {
        this.getOcupacionesBodyResponseBean = new GetOcupacionesBodyResponseBean();
    }

    public GetOcupacionesResponseBean(PrivateHeaderBean privateHeaderBean, GetOcupacionesBodyResponseBean getOcupacionesBodyResponseBean2) {
        this.header = privateHeaderBean;
        this.getOcupacionesBodyResponseBean = getOcupacionesBodyResponseBean2;
    }

    public Object getErrorBeanObject() {
        return this.getOcupacionesBodyResponseBean;
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

    public GetOcupacionesBodyResponseBean getGetOcupacionesBodyResponseBean() {
        return this.getOcupacionesBodyResponseBean;
    }

    public void setGetOcupacionesBodyResponseBean(GetOcupacionesBodyResponseBean getOcupacionesBodyResponseBean2) {
        this.getOcupacionesBodyResponseBean = getOcupacionesBodyResponseBean2;
    }
}
