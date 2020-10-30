package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.utils.RSACryptoUtil;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class LoginUnicoBodyRequestBean {
    private String datos;

    public LoginUnicoBodyRequestBean(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        generateJsonUserObject(str, str2, str3, str4, str5, str6, str7);
    }

    public LoginUnicoBodyRequestBean() {
    }

    private void generateJsonUserObject(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("documento", str);
        jsonObject.addProperty("clave", str2);
        jsonObject.addProperty("claveNueva", str3);
        jsonObject.addProperty("usuario", str4);
        jsonObject.addProperty("nuevoUsr", str5);
        jsonObject.addProperty("fechaNac", str6);
        this.datos = RSACryptoUtil.encrypt(new GsonBuilder().create().toJson((JsonElement) jsonObject), str7);
    }

    public String getDatos() {
        return this.datos;
    }

    public void setDatos(String str) {
        this.datos = str;
    }
}
