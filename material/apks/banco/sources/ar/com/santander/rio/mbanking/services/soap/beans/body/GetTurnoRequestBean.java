package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.soap.beans.BaseResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class GetTurnoRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public GetTurnoBodyRequestBean getTurnoBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public GetTurnoRequestBean() {
    }

    public GetTurnoRequestBean(PrivateHeaderBean privateHeaderBean, GetTurnoBodyRequestBean getTurnoBodyRequestBean2) {
        this.header = privateHeaderBean;
        this.getTurnoBodyRequestBean = getTurnoBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
