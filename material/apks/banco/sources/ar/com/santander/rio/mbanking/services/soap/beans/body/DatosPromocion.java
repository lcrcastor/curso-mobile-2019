package ar.com.santander.rio.mbanking.services.soap.beans.body;

public class DatosPromocion {
    public String descHtml;
    public String descHtml2;
    public String imagenExtra;
    public String legales;

    public DatosPromocion(String str, String str2, String str3, String str4) {
        this.legales = str;
        this.descHtml = str2;
        this.descHtml2 = str3;
        this.imagenExtra = str4;
    }
}
