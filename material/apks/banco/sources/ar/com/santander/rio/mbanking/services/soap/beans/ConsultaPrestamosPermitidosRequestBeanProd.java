package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaPrestamosPermitidosBodyRequestBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class ConsultaPrestamosPermitidosRequestBeanProd extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public ConsultaPrestamosPermitidosBodyRequestBeanProd consultaPrestamosPermitidosBodyRequestBeanProd;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public ConsultaPrestamosPermitidosRequestBeanProd(PrivateHeaderBean privateHeaderBean, ConsultaPrestamosPermitidosBodyRequestBeanProd consultaPrestamosPermitidosBodyRequestBeanProd2) {
        this.consultaPrestamosPermitidosBodyRequestBeanProd = consultaPrestamosPermitidosBodyRequestBeanProd2;
        this.headerBean = privateHeaderBean;
    }

    public ConsultaPrestamosPermitidosRequestBeanProd() {
    }

    public Class getClassBean() {
        return getClass();
    }
}
