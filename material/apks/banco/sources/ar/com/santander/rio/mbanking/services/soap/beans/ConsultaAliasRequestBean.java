package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaAliasBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class ConsultaAliasRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public ConsultaAliasBodyRequestBean consultaAliasBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public ConsultaAliasRequestBean() {
    }

    public ConsultaAliasRequestBean(PrivateHeaderBean privateHeaderBean, ConsultaAliasBodyRequestBean consultaAliasBodyRequestBean2) {
        this.header = privateHeaderBean;
        this.consultaAliasBodyRequestBean = consultaAliasBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
