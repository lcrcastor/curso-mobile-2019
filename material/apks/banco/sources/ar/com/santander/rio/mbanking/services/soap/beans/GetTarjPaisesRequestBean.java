package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetTarjPaisesBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class GetTarjPaisesRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public GetTarjPaisesBodyRequestBean getTarjPaisesBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public GetTarjPaisesRequestBean() {
    }

    public GetTarjPaisesRequestBean(PrivateHeaderBean privateHeaderBean, GetTarjPaisesBodyRequestBean getTarjPaisesBodyRequestBean2) {
        this.header = privateHeaderBean;
        this.getTarjPaisesBodyRequestBean = getTarjPaisesBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClassBean();
    }
}
