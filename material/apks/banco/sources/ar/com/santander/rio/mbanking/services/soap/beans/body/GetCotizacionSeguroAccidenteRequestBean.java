package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.soap.beans.BaseResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class GetCotizacionSeguroAccidenteRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public GetCotizacionSeguroAccidenteBodyRequestBean getCotizacionSeguroAccidenteBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public GetCotizacionSeguroAccidenteRequestBean() {
    }

    public GetCotizacionSeguroAccidenteRequestBean(PrivateHeaderBean privateHeaderBean, GetCotizacionSeguroAccidenteBodyRequestBean getCotizacionSeguroAccidenteBodyRequestBean2) {
        this.header = privateHeaderBean;
        this.getCotizacionSeguroAccidenteBodyRequestBean = getCotizacionSeguroAccidenteBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
