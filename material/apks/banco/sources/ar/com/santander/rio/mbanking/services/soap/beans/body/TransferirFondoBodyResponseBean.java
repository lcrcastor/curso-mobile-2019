package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class TransferirFondoBodyResponseBean extends ErrorBodyBean {
    @SerializedName("alertaRiesgo")
    private String alertaRiesgo;
    @SerializedName("idEvaluacionRiesgo")
    private String idEvaluacionRiesgo;
    @SerializedName("mensaje")
    private AlertaEvaluacionRiesgoBean mensaje;
    @SerializedName("transferencia")
    private TransferenciaFondosBean transferencia;

    public TransferirFondoBodyResponseBean() {
    }

    public TransferirFondoBodyResponseBean(String str, TransferenciaFondosBean transferenciaFondosBean) {
        this.alertaRiesgo = str;
        this.transferencia = transferenciaFondosBean;
    }

    public TransferirFondoBodyResponseBean(String str, String str2, AlertaEvaluacionRiesgoBean alertaEvaluacionRiesgoBean) {
        this.alertaRiesgo = str;
        this.idEvaluacionRiesgo = str2;
        this.mensaje = alertaEvaluacionRiesgoBean;
    }

    public String getAlertaRiesgo() {
        return this.alertaRiesgo;
    }

    public void setAlertaRiesgo(String str) {
        this.alertaRiesgo = str;
    }

    public String getIdEvaluacionRiesgo() {
        return this.idEvaluacionRiesgo;
    }

    public void setIdEvaluacionRiesgo(String str) {
        this.idEvaluacionRiesgo = str;
    }

    public TransferenciaFondosBean getTransferencia() {
        return this.transferencia;
    }

    public void setTransferencia(TransferenciaFondosBean transferenciaFondosBean) {
        this.transferencia = transferenciaFondosBean;
    }

    public AlertaEvaluacionRiesgoBean getMensaje() {
        return this.mensaje;
    }

    public void setMensaje(AlertaEvaluacionRiesgoBean alertaEvaluacionRiesgoBean) {
        this.mensaje = alertaEvaluacionRiesgoBean;
    }
}
