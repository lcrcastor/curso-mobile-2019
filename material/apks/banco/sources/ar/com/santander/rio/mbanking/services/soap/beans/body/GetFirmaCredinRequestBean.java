package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.soap.beans.BaseResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class GetFirmaCredinRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    private GetFirmaCredinBodyRequestBean getFirmaCredinBodyRequestBean;
    @SerializedName("header")
    private PrivateHeaderBean headerBean;

    public GetFirmaCredinRequestBean() {
    }

    public GetFirmaCredinRequestBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }

    public GetFirmaCredinRequestBean(PrivateHeaderBean privateHeaderBean, GetFirmaSCBodyRequestBean getFirmaSCBodyRequestBean) {
    }

    public GetFirmaCredinRequestBean(PrivateHeaderBean privateHeaderBean, GetFirmaCredinBodyRequestBean getFirmaCredinBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.getFirmaCredinBodyRequestBean = getFirmaCredinBodyRequestBean2;
    }

    public PrivateHeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }

    public Class getClassBean() {
        return getClass();
    }
}
