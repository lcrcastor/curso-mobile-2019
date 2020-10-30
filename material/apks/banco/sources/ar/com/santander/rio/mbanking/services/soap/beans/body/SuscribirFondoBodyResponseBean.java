package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class SuscribirFondoBodyResponseBean extends ErrorBodyBean {
    @SerializedName("alertaRiesgo")
    private String alertaRiesgo;
    @SerializedName("idEvaluacionRiesgo")
    private String idEvaluacionRiesgo;
    @SerializedName("mensaje")
    private AlertaEvaluacionRiesgoBean mensaje;
    @SerializedName("suscripcion")
    private SuscripcionFondosBean suscripcion;

    public SuscribirFondoBodyResponseBean() {
    }

    public SuscribirFondoBodyResponseBean(String str, SuscripcionFondosBean suscripcionFondosBean) {
        this.alertaRiesgo = str;
        this.suscripcion = suscripcionFondosBean;
    }

    public SuscribirFondoBodyResponseBean(String str, String str2, AlertaEvaluacionRiesgoBean alertaEvaluacionRiesgoBean) {
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

    public SuscripcionFondosBean getSuscripcion() {
        return this.suscripcion;
    }

    public void setSuscripcion(SuscripcionFondosBean suscripcionFondosBean) {
        this.suscripcion = suscripcionFondosBean;
    }

    public AlertaEvaluacionRiesgoBean getMensaje() {
        return this.mensaje;
    }

    public void setMensaje(AlertaEvaluacionRiesgoBean alertaEvaluacionRiesgoBean) {
        this.mensaje = alertaEvaluacionRiesgoBean;
    }
}
