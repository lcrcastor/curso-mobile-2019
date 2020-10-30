package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class TransferenciasBodyRequestBean {
    @SerializedName("datosCuentaDebito")
    private DatosCuentaDebitoBean datosCuentaDebitoBean;
    @SerializedName("datosCuentaDestinoBSR")
    private DatosCuentasDestinoBSRBean datosCuentasDestinoBSRBean;
    @SerializedName("datosCuentaDestinoOB")
    private DatosCuentasDestinoOBBean datosCuentasDestinoOBBean;
    @SerializedName("datosTransferencia")
    private DatosTransferenciaBean datosTransferenciaBean;

    public TransferenciasBodyRequestBean() {
    }

    public TransferenciasBodyRequestBean(DatosTransferenciaBean datosTransferenciaBean2, DatosCuentaDebitoBean datosCuentaDebitoBean2, DatosCuentasDestinoBSRBean datosCuentasDestinoBSRBean2) {
        this.datosTransferenciaBean = datosTransferenciaBean2;
        this.datosCuentaDebitoBean = datosCuentaDebitoBean2;
        this.datosCuentasDestinoBSRBean = datosCuentasDestinoBSRBean2;
    }

    public TransferenciasBodyRequestBean(DatosTransferenciaBean datosTransferenciaBean2, DatosCuentaDebitoBean datosCuentaDebitoBean2, DatosCuentasDestinoOBBean datosCuentasDestinoOBBean2) {
        this.datosTransferenciaBean = datosTransferenciaBean2;
        this.datosCuentaDebitoBean = datosCuentaDebitoBean2;
        this.datosCuentasDestinoOBBean = datosCuentasDestinoOBBean2;
    }

    public DatosTransferenciaBean getDatosTransferenciaBean() {
        return this.datosTransferenciaBean;
    }

    public DatosCuentaDebitoBean getDatosCuentaDebitoBean() {
        return this.datosCuentaDebitoBean;
    }

    public DatosCuentasDestinoBSRBean getDatosCuentasDestinoBSRBean() {
        return this.datosCuentasDestinoBSRBean;
    }

    public DatosCuentasDestinoOBBean getDatosCuentasDestinoOBBean() {
        return this.datosCuentasDestinoOBBean;
    }
}
