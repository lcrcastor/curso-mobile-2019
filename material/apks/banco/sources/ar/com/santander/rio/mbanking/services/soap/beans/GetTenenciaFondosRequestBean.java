package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetTenenciaFondosBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class GetTenenciaFondosRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    private GetTenenciaFondosBodyRequestBean getTenenciaFondosBodyRequestBean;
    @SerializedName("header")
    private PrivateHeaderBean headerBean;

    public GetTenenciaFondosRequestBean(PrivateHeaderBean privateHeaderBean, GetTenenciaFondosBodyRequestBean getTenenciaFondosBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.getTenenciaFondosBodyRequestBean = getTenenciaFondosBodyRequestBean2;
    }

    public GetTenenciaFondosRequestBean() {
    }

    public Class getClassBean() {
        return getClass();
    }
}
