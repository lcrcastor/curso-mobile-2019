package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class TransferenciasBodyResponseBean extends ErrorBodyBean {
    @SerializedName("datosTransferencia")
    private DatosTransferenciaSalidaBean datosTransferenciaSalidaBean;
    @SerializedName("tipoDestino")
    private String tipoDestino;

    public TransferenciasBodyResponseBean(String str, DatosTransferenciaSalidaBean datosTransferenciaSalidaBean2) {
        this.tipoDestino = str;
        this.datosTransferenciaSalidaBean = datosTransferenciaSalidaBean2;
    }

    public DatosTransferenciaSalidaBean getDatosTransferenciaSalidaBean() {
        return this.datosTransferenciaSalidaBean;
    }

    public String getTipoDestino() {
        return this.tipoDestino;
    }
}
