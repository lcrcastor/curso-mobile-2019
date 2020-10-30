package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaLeyendasBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class ConsultaLeyendasRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public ConsultaLeyendasBodyRequestBean consultaLeyendasBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public ConsultaLeyendasRequestBean(PrivateHeaderBean privateHeaderBean, ConsultaLeyendasBodyRequestBean consultaLeyendasBodyRequestBean2) {
        this.consultaLeyendasBodyRequestBean = consultaLeyendasBodyRequestBean2;
        this.headerBean = privateHeaderBean;
    }

    public Class getClassBean() {
        return getClass();
    }
}
