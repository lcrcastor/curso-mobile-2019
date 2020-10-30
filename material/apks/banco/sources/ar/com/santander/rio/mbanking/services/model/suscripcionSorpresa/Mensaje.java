package ar.com.santander.rio.mbanking.services.model.suscripcionSorpresa;

import com.google.gson.annotations.SerializedName;

public class Mensaje {
    @SerializedName("dapDefault")
    String dapDefault;
    @SerializedName("descripcion")
    String descripcion;
    @SerializedName("destinosPermitidos")
    DestinosPermitidos destinosPermitidos;
    @SerializedName("frecuenciaDefault")
    String frecuenciaDefault;
    @SerializedName("id")

    /* renamed from: id reason: collision with root package name */
    String f251id;
    @SerializedName("listaFrecPer")
    ListaFrecuenciaPermitida listaFrecuenciaPermitida;
    @SerializedName("nombreMensaje")
    String nombreMensaje;
    @SerializedName("nroMensaje")
    String nroMensaje;

    public String getId() {
        return this.f251id;
    }

    public void setId(String str) {
        this.f251id = str;
    }

    public String getNroMensaje() {
        return this.nroMensaje;
    }

    public void setNroMensaje(String str) {
        this.nroMensaje = str;
    }

    public String getNombreMensaje() {
        return this.nombreMensaje;
    }

    public void setNombreMensaje(String str) {
        this.nombreMensaje = str;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String str) {
        this.descripcion = str;
    }

    public String getFrecuenciaDefault() {
        return this.frecuenciaDefault;
    }

    public void setFrecuenciaDefault(String str) {
        this.frecuenciaDefault = str;
    }

    public ListaFrecuenciaPermitida getListaFrecuenciaPermitida() {
        return this.listaFrecuenciaPermitida;
    }

    public void setListaFrecuenciaPermitida(ListaFrecuenciaPermitida listaFrecuenciaPermitida2) {
        this.listaFrecuenciaPermitida = listaFrecuenciaPermitida2;
    }

    public String getDapDefault() {
        return this.dapDefault;
    }

    public void setDapDefault(String str) {
        this.dapDefault = str;
    }

    public DestinosPermitidos getDestinosPermitidos() {
        return this.destinosPermitidos;
    }

    public void setDestinosPermitidos(DestinosPermitidos destinosPermitidos2) {
        this.destinosPermitidos = destinosPermitidos2;
    }
}
