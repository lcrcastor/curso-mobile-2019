package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.soap.beans.BaseResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class TenenciaInversionesResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("header")
    public PrivateHeaderBean header;
    @SerializedName("body")
    public TenenciaInversionesBodyResponseBean tenenciaInversionesBodyResponseBean;

    public PrivateHeaderBean getHeader() {
        return this.header;
    }

    public void setHeader(PrivateHeaderBean privateHeaderBean) {
        this.header = privateHeaderBean;
    }

    public TenenciaInversionesBodyResponseBean getTenenciaInversionesBodyResponseBean() {
        return this.tenenciaInversionesBodyResponseBean;
    }

    public void setTenenciaInversionesBodyResponseBean(TenenciaInversionesBodyResponseBean tenenciaInversionesBodyResponseBean2) {
        this.tenenciaInversionesBodyResponseBean = tenenciaInversionesBodyResponseBean2;
    }

    public Object getErrorBeanObject() {
        return this.tenenciaInversionesBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }
}
