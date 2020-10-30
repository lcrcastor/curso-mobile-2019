package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetDetalleCuotaTenenciaCreditoBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class GetDetalleCuotaTenenciaCreditoRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("header")
    public HeaderBean headerBean;
    @SerializedName("body")
    public GetDetalleCuotaTenenciaCreditoBodyRequestBean mGetDetalleCuotaTenenciaCreditoBodyRequestBean;

    public GetDetalleCuotaTenenciaCreditoRequestBean() {
    }

    public GetDetalleCuotaTenenciaCreditoRequestBean(GetDetalleCuotaTenenciaCreditoBodyRequestBean getDetalleCuotaTenenciaCreditoBodyRequestBean) {
        this.headerBean = new HeaderBean();
        this.mGetDetalleCuotaTenenciaCreditoBodyRequestBean = getDetalleCuotaTenenciaCreditoBodyRequestBean;
    }

    public GetDetalleCuotaTenenciaCreditoRequestBean(HeaderBean headerBean2, GetDetalleCuotaTenenciaCreditoBodyRequestBean getDetalleCuotaTenenciaCreditoBodyRequestBean) {
        this.headerBean = headerBean2;
        this.mGetDetalleCuotaTenenciaCreditoBodyRequestBean = getDetalleCuotaTenenciaCreditoBodyRequestBean;
    }

    public Class getClassBean() {
        return getClass();
    }
}
