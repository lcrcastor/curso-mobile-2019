package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCajerosBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class GetCajerosRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public GetCajerosBodyRequestBean getCajerosBodyRequestBean;
    @SerializedName("header")
    public HeaderBean headerBean;

    public GetCajerosRequestBean() {
    }

    public GetCajerosRequestBean(GetCajerosBodyRequestBean getCajerosBodyRequestBean2) {
        this.headerBean = new HeaderBean();
        this.getCajerosBodyRequestBean = getCajerosBodyRequestBean2;
    }

    public GetCajerosRequestBean(HeaderBean headerBean2, GetCajerosBodyRequestBean getCajerosBodyRequestBean2) {
        this.headerBean = headerBean2;
        this.getCajerosBodyRequestBean = getCajerosBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
