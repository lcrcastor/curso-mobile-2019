package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.soap.beans.BaseResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class GetFamiliasObjetosResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public GetFamiliasObjetosBodyResponseBean getFamiliasObjetosBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public GetFamiliasObjetosResponseBean() {
        this.getFamiliasObjetosBodyResponseBean = new GetFamiliasObjetosBodyResponseBean();
    }

    public GetFamiliasObjetosResponseBean(PrivateHeaderBean privateHeaderBean, GetFamiliasObjetosBodyResponseBean getFamiliasObjetosBodyResponseBean2) {
        this.header = privateHeaderBean;
        this.getFamiliasObjetosBodyResponseBean = getFamiliasObjetosBodyResponseBean2;
    }

    public Object getErrorBeanObject() {
        return this.getFamiliasObjetosBodyResponseBean;
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

    public GetFamiliasObjetosBodyResponseBean getFamiliasObjetosBodyResponseBean() {
        return this.getFamiliasObjetosBodyResponseBean;
    }

    public void setGetFamiliasObjetosResponseBean(GetFamiliasObjetosBodyResponseBean getFamiliasObjetosBodyResponseBean2) {
        this.getFamiliasObjetosBodyResponseBean = getFamiliasObjetosBodyResponseBean2;
    }
}
