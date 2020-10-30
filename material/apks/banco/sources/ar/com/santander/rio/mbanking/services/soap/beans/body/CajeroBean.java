package ar.com.santander.rio.mbanking.services.soap.beans.body;

public class CajeroBean {
    private String descripcion;
    private String direccion;

    /* renamed from: id reason: collision with root package name */
    private String f253id;
    private String latitud;
    private String longitud;
    private String nombre;
    private String socialStyle;
    private String thumbnailBig;
    private String thumbnailSmall;

    public String getId() {
        return this.f253id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public String getLatitud() {
        return this.latitud;
    }

    public String getLongitud() {
        return this.longitud;
    }

    public String getThumbnailSmall() {
        return this.thumbnailSmall;
    }

    public String getThumbnailBig() {
        return this.thumbnailBig;
    }

    public String getSocialStyle() {
        return this.socialStyle;
    }

    public CajeroBean() {
    }

    public CajeroBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        this.f253id = str;
        this.nombre = str2;
        this.direccion = str3;
        this.descripcion = str4;
        this.latitud = str5;
        this.longitud = str6;
        this.thumbnailSmall = str7;
        this.thumbnailBig = str8;
        this.socialStyle = str9;
    }
}
