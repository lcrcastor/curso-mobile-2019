package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class MandatoBean {
    @SerializedName("beneficiario")
    public BeneficiarioMandatoBean beneficiario;
    @SerializedName("detalle")
    public DetalleMandatoBean detalle;
    @SerializedName("mandatario")
    public MandatarioBean mandatario;

    public MandatoBean() {
    }

    public MandatoBean(MandatarioBean mandatarioBean, BeneficiarioMandatoBean beneficiarioMandatoBean, DetalleMandatoBean detalleMandatoBean) {
        this.mandatario = mandatarioBean;
        this.beneficiario = beneficiarioMandatoBean;
        this.detalle = detalleMandatoBean;
    }
}
