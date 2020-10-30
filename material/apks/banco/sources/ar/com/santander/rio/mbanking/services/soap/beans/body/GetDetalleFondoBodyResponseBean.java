package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class GetDetalleFondoBodyResponseBean extends ErrorBodyBean {
    @SerializedName("informacionFondo")
    private InformacionFondoBean informacionFondo;

    public GetDetalleFondoBodyResponseBean(InformacionFondoBean informacionFondoBean) {
        this.informacionFondo = informacionFondoBean;
    }

    public GetDetalleFondoBodyResponseBean() {
    }

    public InformacionFondoBean getInformacionFondo() {
        return this.informacionFondo;
    }

    public void setInformacionFondo(InformacionFondoBean informacionFondoBean) {
        this.informacionFondo = informacionFondoBean;
    }
}
