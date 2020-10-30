package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class ABMDestinatarioTransfBodyRequestBean {
    @SerializedName("cuentaAgenda")
    private String cuentaAgenda;
    @SerializedName("cuentaBSR")
    private DatosCuentasDestBSRBean cuentaBSRBean;
    @SerializedName("cuentaBanco")
    private String cuentaBanco;
    @SerializedName("cuentaOB")
    private DatosCuentasDestOBBean cuentaOBBean;
    @SerializedName("tipoOperacion")
    private String tipoOperacion;

    public ABMDestinatarioTransfBodyRequestBean(String str, String str2, String str3, DatosCuentasDestBSRBean datosCuentasDestBSRBean, DatosCuentasDestOBBean datosCuentasDestOBBean) {
        this.tipoOperacion = str;
        this.cuentaBanco = str2;
        this.cuentaAgenda = str3;
        this.cuentaBSRBean = datosCuentasDestBSRBean;
        this.cuentaOBBean = datosCuentasDestOBBean;
    }
}
