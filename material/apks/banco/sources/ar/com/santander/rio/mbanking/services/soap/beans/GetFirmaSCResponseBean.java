package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetFirmaSCBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class GetFirmaSCResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public GetFirmaSCBodyResponseBean getFirmaSCBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public GetFirmaSCResponseBean() {
    }

    public GetFirmaSCResponseBean(PrivateHeaderBean privateHeaderBean, GetFirmaSCBodyResponseBean getFirmaSCBodyResponseBean2) {
        this.headerBean = privateHeaderBean;
        this.getFirmaSCBodyResponseBean = getFirmaSCBodyResponseBean2;
    }

    public PrivateHeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }

    public GetFirmaSCBodyResponseBean getGetFirmaSCBodyResponseBean() {
        return this.getFirmaSCBodyResponseBean;
    }

    public void setGetFirmaSCBodyResponseBean(GetFirmaSCBodyResponseBean getFirmaSCBodyResponseBean2) {
        this.getFirmaSCBodyResponseBean = getFirmaSCBodyResponseBean2;
    }

    public Object getErrorBeanObject() {
        return this.getFirmaSCBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }
}
