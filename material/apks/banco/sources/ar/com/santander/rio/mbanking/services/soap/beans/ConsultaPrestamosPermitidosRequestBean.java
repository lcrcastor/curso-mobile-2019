package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaPrestamosPermitidosBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class ConsultaPrestamosPermitidosRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public ConsultaPrestamosPermitidosBodyRequestBean consultaPrestamosPermitidosBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public ConsultaPrestamosPermitidosRequestBean(PrivateHeaderBean privateHeaderBean, ConsultaPrestamosPermitidosBodyRequestBean consultaPrestamosPermitidosBodyRequestBean2) {
        this.consultaPrestamosPermitidosBodyRequestBean = consultaPrestamosPermitidosBodyRequestBean2;
        this.headerBean = privateHeaderBean;
    }

    public ConsultaPrestamosPermitidosRequestBean() {
    }

    public Class getClassBean() {
        return getClass();
    }
}
