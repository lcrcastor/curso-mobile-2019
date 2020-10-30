package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroMovilBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class GetCotizacionSeguroMovilRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public GetCotizacionSeguroMovilBodyRequestBean getCotizacionSeguroMovilBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public GetCotizacionSeguroMovilRequestBean() {
    }

    public GetCotizacionSeguroMovilRequestBean(PrivateHeaderBean privateHeaderBean, GetCotizacionSeguroMovilBodyRequestBean getCotizacionSeguroMovilBodyRequestBean2) {
        this.header = privateHeaderBean;
        this.getCotizacionSeguroMovilBodyRequestBean = getCotizacionSeguroMovilBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
