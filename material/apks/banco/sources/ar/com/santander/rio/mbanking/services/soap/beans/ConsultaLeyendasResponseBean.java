package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaLeyendasBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class ConsultaLeyendasResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public ConsultaLeyendasBodyResponseBean consultaLeyendasBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public ConsultaLeyendasResponseBean(ConsultaLeyendasBodyResponseBean consultaLeyendasBodyResponseBean2) {
        this.consultaLeyendasBodyResponseBean = consultaLeyendasBodyResponseBean2;
    }

    public ConsultaLeyendasResponseBean() {
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.consultaLeyendasBodyResponseBean;
    }

    public ConsultaLeyendasBodyResponseBean getConsultaLeyendasBodyResponseBean() {
        return this.consultaLeyendasBodyResponseBean;
    }

    public void setConsultaLeyendasBodyResponseBean(ConsultaLeyendasBodyResponseBean consultaLeyendasBodyResponseBean2) {
        this.consultaLeyendasBodyResponseBean = consultaLeyendasBodyResponseBean2;
    }
}
