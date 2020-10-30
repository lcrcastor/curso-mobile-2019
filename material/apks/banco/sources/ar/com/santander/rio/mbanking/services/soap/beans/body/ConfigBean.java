package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class ConfigBean {

    /* renamed from: id reason: collision with root package name */
    public String f257id;
    @SerializedName("valor")
    public String value;

    public ConfigBean() {
    }

    public ConfigBean(String str, String str2) {
        this.f257id = str;
        this.value = str2;
    }
}
