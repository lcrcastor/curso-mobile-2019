package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class ContratarSeguroAccidenteBodyResponseBean extends ErrorBodyBean {
    @SerializedName("contratarSeguroAccidenteBean")
    private ContratarSeguroAccidenteBean contratarSeguroAccidenteBean;

    public ContratarSeguroAccidenteBodyResponseBean() {
    }

    public ContratarSeguroAccidenteBodyResponseBean(ContratarSeguroAccidenteBean contratarSeguroAccidenteBean2) {
        this.contratarSeguroAccidenteBean = contratarSeguroAccidenteBean2;
    }

    public ContratarSeguroAccidenteBean getContratarSeguroAccidenteBean() {
        return this.contratarSeguroAccidenteBean;
    }

    public void setContratarSeguroAccidenteBean(ContratarSeguroAccidenteBean contratarSeguroAccidenteBean2) {
        this.contratarSeguroAccidenteBean = contratarSeguroAccidenteBean2;
    }
}
