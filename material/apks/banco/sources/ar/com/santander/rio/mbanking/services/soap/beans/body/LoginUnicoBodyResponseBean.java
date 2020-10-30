package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.model.general.DatosPersonales;
import ar.com.santander.rio.mbanking.services.model.general.DestinosMyA;
import ar.com.santander.rio.mbanking.services.model.general.Productos;
import com.google.gson.annotations.SerializedName;

public class LoginUnicoBodyResponseBean extends ErrorBodyBean {
    public static String ESTADO_AU = "AU";
    public static String ESTADO_CU = "CU";
    public static String ESTADO_CV = "CV";
    public static String ESTADO_OK = "OK";
    public static String ESTADO_PI = "PI";
    public static String ESTADO_SI = "SI";
    @SerializedName("calificacion")
    private CalificacionBean calificacion;
    @SerializedName("crm")
    public Crm crm;
    @SerializedName("datosPersonales")
    private DatosPersonales datosPersonales;
    @SerializedName("destinosMyA")
    private DestinosMyA destinosMyA;
    @SerializedName("estado")
    private String estado;
    @SerializedName("habilitarWatson")
    public String habilitarWatson;
    @SerializedName("productos")
    private Productos productos;
    @SerializedName("sesionUsuario")
    private String sesionUsuario;

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String str) {
        this.estado = str;
    }

    public String getSesionUsuario() {
        return this.sesionUsuario;
    }

    public void setSesionUsuario(String str) {
        this.sesionUsuario = str;
    }

    public DatosPersonales getDatosPersonales() {
        return this.datosPersonales;
    }

    public String getHabilitarWatson() {
        return this.habilitarWatson;
    }

    public void setHabilitarWatson(String str) {
        this.habilitarWatson = str;
    }

    public void setDatosPersonales(DatosPersonales datosPersonales2) {
        this.datosPersonales = datosPersonales2;
        if (this.datosPersonales.getUrlSegmento() == null) {
            this.datosPersonales.setUrlSegmento("");
        }
        if (this.datosPersonales.getSegmento() == null) {
            this.datosPersonales.setSegmento("");
        }
        if (this.datosPersonales.getIdSegmento() == null) {
            this.datosPersonales.setIdSegmento("");
        }
    }

    public DestinosMyA getDestinosMyA() {
        return this.destinosMyA;
    }

    public void setDestinosMyA(DestinosMyA destinosMyA2) {
        this.destinosMyA = destinosMyA2;
    }

    public Productos getProductos() {
        return this.productos;
    }

    public void setProductos(Productos productos2) {
        this.productos = productos2;
    }

    public Crm getCrm() {
        return this.crm;
    }

    public void setCrm(Crm crm2) {
        this.crm = crm2;
    }

    public CalificacionBean getCalificacion() {
        return this.calificacion;
    }

    public void setCalificacion(CalificacionBean calificacionBean) {
        this.calificacion = calificacionBean;
    }
}
