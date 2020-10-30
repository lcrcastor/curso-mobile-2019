package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetTenenciaFondosBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class GetTenenciaFondosResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public GetTenenciaFondosBodyResponseBean getTenenciaFondosBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public GetTenenciaFondosResponseBean(GetTenenciaFondosBodyResponseBean getTenenciaFondosBodyResponseBean2) {
        this.getTenenciaFondosBodyResponseBean = getTenenciaFondosBodyResponseBean2;
    }

    public GetTenenciaFondosResponseBean() {
    }

    public Object getErrorBeanObject() {
        return this.getTenenciaFondosBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public GetTenenciaFondosBodyResponseBean getGetTenenciaFondosBodyResponseBean() {
        return this.getTenenciaFondosBodyResponseBean;
    }

    public void setGetTenenciaFondosBodyResponseBean(GetTenenciaFondosBodyResponseBean getTenenciaFondosBodyResponseBean2) {
        this.getTenenciaFondosBodyResponseBean = getTenenciaFondosBodyResponseBean2;
    }
}
