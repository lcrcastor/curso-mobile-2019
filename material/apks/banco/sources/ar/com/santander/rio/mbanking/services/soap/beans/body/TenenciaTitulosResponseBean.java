package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.soap.beans.BaseResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class TenenciaTitulosResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("header")
    public PrivateHeaderBean header;
    @SerializedName("body")
    private TenenciaTitulosBodyResponseBean tenenciaTitulosBodyResponseBean;

    public PrivateHeaderBean getHeader() {
        return this.header;
    }

    public void setHeader(PrivateHeaderBean privateHeaderBean) {
        this.header = privateHeaderBean;
    }

    public TenenciaTitulosBodyResponseBean getTenenciaTitulosBodyResponseBean() {
        return this.tenenciaTitulosBodyResponseBean;
    }

    public void setTenenciaTitulosBodyResponseBean(TenenciaTitulosBodyResponseBean tenenciaTitulosBodyResponseBean2) {
        this.tenenciaTitulosBodyResponseBean = tenenciaTitulosBodyResponseBean2;
    }

    public Object getErrorBeanObject() {
        return this.tenenciaTitulosBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }
}
