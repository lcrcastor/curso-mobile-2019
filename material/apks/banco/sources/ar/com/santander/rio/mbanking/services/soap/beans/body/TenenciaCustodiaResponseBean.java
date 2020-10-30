package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.soap.beans.BaseResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class TenenciaCustodiaResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("header")
    public PrivateHeaderBean header;
    @SerializedName("body")
    private TenenciaCustodiaBodyResponseBean tenenciaCustodiaBodyResponseBean;

    public PrivateHeaderBean getHeader() {
        return this.header;
    }

    public void setHeader(PrivateHeaderBean privateHeaderBean) {
        this.header = privateHeaderBean;
    }

    public TenenciaCustodiaBodyResponseBean getTenenciaCustodiaBodyResponseBean() {
        return this.tenenciaCustodiaBodyResponseBean;
    }

    public void setTenenciaCustodiaBodyResponseBean(TenenciaCustodiaBodyResponseBean tenenciaCustodiaBodyResponseBean2) {
        this.tenenciaCustodiaBodyResponseBean = tenenciaCustodiaBodyResponseBean2;
    }

    public Object getErrorBeanObject() {
        return this.tenenciaCustodiaBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }
}
