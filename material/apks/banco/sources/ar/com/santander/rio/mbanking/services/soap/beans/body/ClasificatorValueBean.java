package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class ClasificatorValueBean {
    @SerializedName("id")

    /* renamed from: id reason: collision with root package name */
    public String f256id;
    @SerializedName("idClasificador")
    public String idClasificator;
    @SerializedName("idPadre")
    public String idParent;
    @SerializedName("principal")
    public String main;
    @SerializedName("nombre")
    public String name;
    @SerializedName("orden")
    public String order;
    @SerializedName("valor")
    public String value;

    public ClasificatorValueBean() {
    }

    public ClasificatorValueBean(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        this.f256id = str;
        this.idClasificator = str2;
        this.name = str3;
        this.order = str4;
        this.idParent = str5;
        this.main = str6;
        this.value = str7;
    }
}
