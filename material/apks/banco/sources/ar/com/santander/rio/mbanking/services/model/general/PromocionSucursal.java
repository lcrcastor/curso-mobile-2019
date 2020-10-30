package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;

public class PromocionSucursal {
    @SerializedName("descHtml3")
    String descHtml3;
    @SerializedName("descripcion")
    String descripcion;
    @SerializedName("descripcion2")
    String descripcion2;
    @SerializedName("descripcio3")
    String descripcion3;
    @SerializedName("destaque")
    String destaque;
    @SerializedName("direccion")
    String direccion;
    @SerializedName("id")

    /* renamed from: id reason: collision with root package name */
    String f249id;
    @SerializedName("latitud")
    String latitud;
    @SerializedName("longitud")
    String longitud;
    @SerializedName("nombre")
    String nombre;
    @SerializedName("promoURL")
    String promoURL;
    @SerializedName("socialStyle")
    String socialStyle;
    @SerializedName("thumbnailBig")
    String thumbnailBig;
    @SerializedName("thumbnailSmall")
    String thumbnailSmall;
    @SerializedName("tipo")
    String tipo;
    @SerializedName("valor")
    String valor;

    public String getId() {
        return this.f249id;
    }

    public void setId(String str) {
        this.f249id = str;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String str) {
        this.tipo = str;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String str) {
        this.nombre = str;
    }

    public String getDescHtml3() {
        return this.descHtml3;
    }

    public void setDescHtml3(String str) {
        this.descHtml3 = str;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String str) {
        this.descripcion = str;
    }

    public String getDescripcion2() {
        return this.descripcion2;
    }

    public void setDescripcion2(String str) {
        this.descripcion2 = str;
    }

    public String getDescripcion3() {
        return this.descripcion3;
    }

    public void setDescripcion3(String str) {
        this.descripcion3 = str;
    }

    public String getDestaque() {
        return this.destaque;
    }

    public void setDestaque(String str) {
        this.destaque = str;
    }

    public String getValor() {
        return this.valor;
    }

    public void setValor(String str) {
        this.valor = str;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String str) {
        this.direccion = str;
    }

    public String getLatitud() {
        return this.latitud;
    }

    public void setLatitud(String str) {
        this.latitud = str;
    }

    public String getLongitud() {
        return this.longitud;
    }

    public void setLongitud(String str) {
        this.longitud = str;
    }

    public String getThumbnailSmall() {
        return this.thumbnailSmall;
    }

    public void setThumbnailSmall(String str) {
        this.thumbnailSmall = str;
    }

    public String getThumbnailBig() {
        return this.thumbnailBig;
    }

    public void setThumbnailBig(String str) {
        this.thumbnailBig = str;
    }

    public String getSocialStyle() {
        return this.socialStyle;
    }

    public void setSocialStyle(String str) {
        this.socialStyle = str;
    }

    public String getPromoURL() {
        return this.promoURL;
    }

    public void setPromoURL(String str) {
        this.promoURL = str;
    }
}
