package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetSucursalesBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class GetSucursalesRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public GetSucursalesBodyRequestBean getSucursalesBodyRequestBean;
    @SerializedName("header")
    public HeaderBean headerBean;

    public GetSucursalesRequestBean() {
    }

    public GetSucursalesRequestBean(GetSucursalesBodyRequestBean getSucursalesBodyRequestBean2) {
        this.headerBean = new HeaderBean();
        this.getSucursalesBodyRequestBean = getSucursalesBodyRequestBean2;
    }

    public GetSucursalesRequestBean(HeaderBean headerBean2, GetSucursalesBodyRequestBean getSucursalesBodyRequestBean2) {
        this.headerBean = headerBean2;
        this.getSucursalesBodyRequestBean = getSucursalesBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
