package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.soap.beans.BaseResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class BlanqueoPinResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public BlanqueoPinBodyResponseBean blanqueoPinBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public BlanqueoPinResponseBean(BlanqueoPinBodyResponseBean blanqueoPinBodyResponseBean2) {
        this.blanqueoPinBodyResponseBean = blanqueoPinBodyResponseBean2;
    }

    public BlanqueoPinResponseBean(PrivateHeaderBean privateHeaderBean, BlanqueoPinBodyResponseBean blanqueoPinBodyResponseBean2) {
        this.header = privateHeaderBean;
        this.blanqueoPinBodyResponseBean = blanqueoPinBodyResponseBean2;
    }

    public BlanqueoPinResponseBean() {
    }

    public PrivateHeaderBean getHeader() {
        return this.header;
    }

    public void setHeader(PrivateHeaderBean privateHeaderBean) {
        this.header = privateHeaderBean;
    }

    public BlanqueoPinBodyResponseBean getBlanqueoPinBodyResponseBean() {
        return this.blanqueoPinBodyResponseBean;
    }

    public void setBlanqueoPinBodyResponseBean(BlanqueoPinBodyResponseBean blanqueoPinBodyResponseBean2) {
        this.blanqueoPinBodyResponseBean = blanqueoPinBodyResponseBean2;
    }

    public Object getErrorBeanObject() {
        return this.blanqueoPinBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }
}
