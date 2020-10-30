package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.soap.beans.BaseResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class GetCircuitoTurnoRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public GetCircuitoTurnoBodyRequestBean getCircuitoTurnoBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public GetCircuitoTurnoRequestBean() {
    }

    public GetCircuitoTurnoRequestBean(PrivateHeaderBean privateHeaderBean, GetCircuitoTurnoBodyRequestBean getCircuitoTurnoBodyRequestBean2) {
        this.header = privateHeaderBean;
        this.getCircuitoTurnoBodyRequestBean = getCircuitoTurnoBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
