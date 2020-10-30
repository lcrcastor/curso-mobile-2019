package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaMandatosExtEnvBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class ConsultaMandatosExtEnvRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public ConsultaMandatosExtEnvBodyRequestBean consultaMandatosExtEnvBodyRequestBean;
    @SerializedName("header")
    public HeaderBean headerBean;

    public ConsultaMandatosExtEnvRequestBean() {
        this.headerBean = new HeaderBean();
        this.consultaMandatosExtEnvBodyRequestBean = new ConsultaMandatosExtEnvBodyRequestBean();
    }

    public ConsultaMandatosExtEnvRequestBean(ConsultaMandatosExtEnvBodyRequestBean consultaMandatosExtEnvBodyRequestBean2) {
        this.consultaMandatosExtEnvBodyRequestBean = consultaMandatosExtEnvBodyRequestBean2;
    }

    public ConsultaMandatosExtEnvRequestBean(HeaderBean headerBean2, ConsultaMandatosExtEnvBodyRequestBean consultaMandatosExtEnvBodyRequestBean2) {
        this.headerBean = headerBean2;
        this.consultaMandatosExtEnvBodyRequestBean = consultaMandatosExtEnvBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
