package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.model.general.Tarjetas;
import com.google.gson.annotations.SerializedName;

public class CnsDeudaBodyResponseBean extends ErrorBodyBean {
    @SerializedName("cotizacionDolar")
    private String cotizacionDolar;
    @SerializedName("cuentas")
    public CuentasDeudasBean cuentasDeudasBean;
    @SerializedName("datosDeudas")
    public DatosDeudasBean datosDeudas;
    @SerializedName("fechaActual")
    private String fechaActual;
    @SerializedName("horaBancaria")
    private String horaBancaria;
    @SerializedName("mensajeHBancario")
    private String mensajeHBancario;
    @SerializedName("mensajeProgramado")
    private String mensajeProgramado;
    @SerializedName("stop_mensaje_mes")
    private String stop_mensaje_mes;
    @SerializedName("stop_recordatorio")
    private String stop_recordatorio;
    @SerializedName("tarjetas")
    private Tarjetas tarjetas;
    @SerializedName("Tarjetas")
    public TarjetasDeudaBean tarjetasDeudaBean;

    public CnsDeudaBodyResponseBean() {
        this.cuentasDeudasBean = new CuentasDeudasBean();
        this.tarjetasDeudaBean = new TarjetasDeudaBean();
        this.datosDeudas = new DatosDeudasBean();
    }

    public CnsDeudaBodyResponseBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, CuentasDeudasBean cuentasDeudasBean2, TarjetasDeudaBean tarjetasDeudaBean2, DatosDeudasBean datosDeudasBean) {
        this.fechaActual = str;
        this.horaBancaria = str2;
        this.mensajeHBancario = str3;
        this.stop_mensaje_mes = str4;
        this.stop_recordatorio = str5;
        this.cotizacionDolar = str6;
        this.mensajeProgramado = str7;
        this.cuentasDeudasBean = cuentasDeudasBean2;
        this.tarjetasDeudaBean = tarjetasDeudaBean2;
        this.datosDeudas = datosDeudasBean;
    }

    public Tarjetas getTarjetas() {
        return this.tarjetas;
    }

    public void setTarjetas(Tarjetas tarjetas2) {
        this.tarjetas = tarjetas2;
    }

    public CuentasDeudasBean getCuentas() {
        return this.cuentasDeudasBean;
    }
}
