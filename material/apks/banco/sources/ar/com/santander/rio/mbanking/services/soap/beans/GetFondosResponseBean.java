package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetFondosBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class GetFondosResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public GetFondosBodyResponseBean getFondosBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public GetFondosResponseBean(GetFondosBodyResponseBean getFondosBodyResponseBean2) {
        this.getFondosBodyResponseBean = getFondosBodyResponseBean2;
    }

    public GetFondosResponseBean() {
    }

    public Object getErrorBeanObject() {
        return this.getFondosBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public GetFondosBodyResponseBean getGetFondosBodyResponseBean() {
        return this.getFondosBodyResponseBean;
    }

    public void setGetFondosBodyResponseBean(GetFondosBodyResponseBean getFondosBodyResponseBean2) {
        this.getFondosBodyResponseBean = getFondosBodyResponseBean2;
    }
}
