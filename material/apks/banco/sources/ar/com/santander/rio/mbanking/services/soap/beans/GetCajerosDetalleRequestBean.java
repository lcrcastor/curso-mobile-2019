package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCajerosDetalleBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class GetCajerosDetalleRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public GetCajerosDetalleBodyRequestBean getCajerosDetalleBodyRequestBean;
    @SerializedName("header")
    public HeaderBean headerBean;

    public GetCajerosDetalleRequestBean() {
    }

    public GetCajerosDetalleRequestBean(GetCajerosDetalleBodyRequestBean getCajerosDetalleBodyRequestBean2) {
        this.headerBean = new HeaderBean();
        this.getCajerosDetalleBodyRequestBean = getCajerosDetalleBodyRequestBean2;
    }

    public GetCajerosDetalleRequestBean(HeaderBean headerBean2, GetCajerosDetalleBodyRequestBean getCajerosDetalleBodyRequestBean2) {
        this.headerBean = headerBean2;
        this.getCajerosDetalleBodyRequestBean = getCajerosDetalleBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
