package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class ClasificatorBean {
    @SerializedName("iconoUrl")
    public String iconUrl;
    @SerializedName("id")

    /* renamed from: id reason: collision with root package name */
    public String f255id;
    @SerializedName("nombre")
    public String name;
    @SerializedName("orden")
    public String order;

    public ClasificatorBean() {
    }

    public ClasificatorBean(String str, String str2, String str3, String str4) {
        this.f255id = str;
        this.name = str2;
        this.order = str3;
        this.iconUrl = str4;
    }
}
