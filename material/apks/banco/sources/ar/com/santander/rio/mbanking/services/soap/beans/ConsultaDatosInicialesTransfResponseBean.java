package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaDatosInicialesTransfBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeader;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class ConsultaDatosInicialesTransfResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public ConsultaDatosInicialesTransfBodyResponseBean consultaDatosInicialesTransfBodyResponseBean;
    @SerializedName("header")
    public PrivateHeader headerBean;

    public ConsultaDatosInicialesTransfResponseBean(ConsultaDatosInicialesTransfBodyResponseBean consultaDatosInicialesTransfBodyResponseBean2) {
        this.consultaDatosInicialesTransfBodyResponseBean = consultaDatosInicialesTransfBodyResponseBean2;
    }

    public ConsultaDatosInicialesTransfResponseBean() {
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.consultaDatosInicialesTransfBodyResponseBean;
    }

    public void setConsultaDatosInicialesTransfBodyResponseBean(ConsultaDatosInicialesTransfBodyResponseBean consultaDatosInicialesTransfBodyResponseBean2) {
        this.consultaDatosInicialesTransfBodyResponseBean = consultaDatosInicialesTransfBodyResponseBean2;
    }

    public ConsultaDatosInicialesTransfBodyResponseBean getConsultaDatosInicialesTransfBodyResponseBean() {
        return this.consultaDatosInicialesTransfBodyResponseBean;
    }
}
