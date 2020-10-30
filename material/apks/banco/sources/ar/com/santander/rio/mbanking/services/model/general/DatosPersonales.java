package ar.com.santander.rio.mbanking.services.model.general;

import android.text.Html;
import ar.com.santander.rio.mbanking.utils.UtilString;
import com.google.gson.annotations.SerializedName;

public class DatosPersonales {
    @SerializedName("aceptaTyC")
    private String aceptaTyC;
    @SerializedName("apellido")
    private String apellido;
    @SerializedName("fechaNacimiento")
    private String fechaNacimiento;
    @SerializedName("idsegmento")
    private String idSegmento;
    @SerializedName("mensaje")
    private String mensaje;
    @SerializedName("nombre")
    private String nombre;
    @SerializedName("nroDocumento")
    private String nroDocumento;
    @SerializedName("nup")
    private String nup;
    @SerializedName("segmento")
    private String segmento;
    @SerializedName("tipoCliente")
    private String tipoCliente;
    @SerializedName("tipoDocumento")
    private String tipoDocumento;
    @SerializedName("ultimoAcceso")
    private String ultimoAcceso;
    @SerializedName("urlsegmento")
    private String urlSegmento;

    public DatosPersonales(String str, String str2, String str3) {
        this.nup = str;
        this.nroDocumento = str2;
        this.fechaNacimiento = str3;
    }

    public String getNup() {
        return this.nup;
    }

    public void setNup(String str) {
        this.nup = str;
    }

    public String getMensaje() {
        return this.mensaje;
    }

    public void setMensaje(String str) {
        this.mensaje = str;
    }

    public String getNombre() {
        try {
            return UtilString.capitalize(Html.fromHtml(this.nombre).toString());
        } catch (Exception e) {
            e.fillInStackTrace();
            return this.nombre;
        }
    }

    public void setNombre(String str) {
        this.nombre = str;
    }

    public String getApellido() {
        try {
            return UtilString.capitalize(Html.fromHtml(this.apellido).toString());
        } catch (Exception e) {
            e.fillInStackTrace();
            return this.apellido;
        }
    }

    public void setApellido(String str) {
        this.apellido = str;
    }

    public String getTipoDocumento() {
        return this.tipoDocumento;
    }

    public void setTipoDocumento(String str) {
        this.tipoDocumento = str;
    }

    public String getNroDocumento() {
        return this.nroDocumento;
    }

    public void setNroDocumento(String str) {
        this.nroDocumento = str;
    }

    public String getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public void setFechaNacimiento(String str) {
        this.fechaNacimiento = str;
    }

    public String getAceptaTyC() {
        return this.aceptaTyC;
    }

    public void setAceptaTyC(String str) {
        this.aceptaTyC = str;
    }

    public String getUltimoAcceso() {
        return this.ultimoAcceso;
    }

    public void setUltimoAcceso(String str) {
        this.ultimoAcceso = str;
    }

    public String getIdSegmento() {
        return this.idSegmento;
    }

    public void setIdSegmento(String str) {
        if (str == null) {
            this.idSegmento = "";
        } else {
            this.idSegmento = str;
        }
    }

    public String getSegmento() {
        return this.segmento;
    }

    public void setSegmento(String str) {
        if (str == null) {
            this.segmento = "";
        } else {
            this.segmento = str;
        }
    }

    public String getUrlSegmento() {
        if (this.urlSegmento == null) {
            this.urlSegmento = "";
        }
        return this.urlSegmento;
    }

    public void setUrlSegmento(String str) {
        if (str == null) {
            this.urlSegmento = "";
        } else {
            this.urlSegmento = str;
        }
    }

    public String getTipoCliente() {
        return this.tipoCliente;
    }

    public void setTipoCliente(String str) {
        this.tipoCliente = str;
    }
}
