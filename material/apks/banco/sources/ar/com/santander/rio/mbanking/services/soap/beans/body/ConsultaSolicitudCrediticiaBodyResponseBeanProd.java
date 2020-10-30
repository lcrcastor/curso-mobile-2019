package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.model.creditos.ConsultaCalificacionCrediticiaProd;
import com.google.gson.annotations.SerializedName;

public class ConsultaSolicitudCrediticiaBodyResponseBeanProd extends ErrorBodyBean {
    @SerializedName("consCalifCred")
    ConsultaCalificacionCrediticiaProd consultaCalificacionCrediticiaProd;

    public ConsultaCalificacionCrediticiaProd getConsultaCalificacionCrediticiaProd() {
        return this.consultaCalificacionCrediticiaProd;
    }

    public void setConsultaCalificacionCrediticiaProd(ConsultaCalificacionCrediticiaProd consultaCalificacionCrediticiaProd2) {
        this.consultaCalificacionCrediticiaProd = consultaCalificacionCrediticiaProd2;
    }
}
