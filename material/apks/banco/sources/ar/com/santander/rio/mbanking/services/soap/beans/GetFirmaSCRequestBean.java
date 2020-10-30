package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetFirmaSCBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class GetFirmaSCRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    private GetFirmaSCBodyRequestBean consultarAdhesionVendedorBodyRequestBean;
    @SerializedName("header")
    private PrivateHeaderBean headerBean;

    public GetFirmaSCRequestBean() {
    }

    public GetFirmaSCRequestBean(PrivateHeaderBean privateHeaderBean, GetFirmaSCBodyRequestBean getFirmaSCBodyRequestBean) {
        this.headerBean = privateHeaderBean;
        this.consultarAdhesionVendedorBodyRequestBean = getFirmaSCBodyRequestBean;
    }

    public PrivateHeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }

    public GetFirmaSCBodyRequestBean getConsultarAdhesionVendedorBodyRequestBean() {
        return this.consultarAdhesionVendedorBodyRequestBean;
    }

    public void setConsultarAdhesionVendedorBodyRequestBean(GetFirmaSCBodyRequestBean getFirmaSCBodyRequestBean) {
        this.consultarAdhesionVendedorBodyRequestBean = getFirmaSCBodyRequestBean;
    }

    public Class getClassBean() {
        return getClass();
    }
}
