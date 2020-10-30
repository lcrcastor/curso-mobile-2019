package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetInfoAdmFondosBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class GetInfoAdmFondosResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public GetInfoAdmFondosBodyResponseBean getInfoAdmFondosBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public GetInfoAdmFondosResponseBean(GetInfoAdmFondosBodyResponseBean getInfoAdmFondosBodyResponseBean2) {
        this.getInfoAdmFondosBodyResponseBean = getInfoAdmFondosBodyResponseBean2;
    }

    public GetInfoAdmFondosResponseBean() {
    }

    public Object getErrorBeanObject() {
        return this.getInfoAdmFondosBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public GetInfoAdmFondosBodyResponseBean getGetInfoAdmFondosBodyResponseBean() {
        return this.getInfoAdmFondosBodyResponseBean;
    }

    public void setGetInfoAdmFondosBodyResponseBean(GetInfoAdmFondosBodyResponseBean getInfoAdmFondosBodyResponseBean2) {
        this.getInfoAdmFondosBodyResponseBean = getInfoAdmFondosBodyResponseBean2;
    }

    public PrivateHeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }
}
