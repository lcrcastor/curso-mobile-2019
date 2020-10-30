package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.text.TextUtils;
import com.google.gson.annotations.SerializedName;

public class CanjearPuntosSuperClubBodyRequestBean {
    @SerializedName("grupo")
    public String grupo;
    @SerializedName("idCupon")
    public String idCupon;
    @SerializedName("idPartner")
    public String idPartner;
    @SerializedName("idPunto")
    public String idPunto;
    @SerializedName("puntos")
    public String puntos;
    @SerializedName("segmento")
    public String segmento;

    public CanjearPuntosSuperClubBodyRequestBean() {
    }

    public CanjearPuntosSuperClubBodyRequestBean(String str, String str2, String str3, String str4, String str5, String str6) {
        this.idCupon = str;
        if (TextUtils.isEmpty(str2)) {
            str2 = "";
        }
        this.grupo = str2;
        this.segmento = str3;
        this.puntos = str4;
        this.idPunto = str5;
        this.idPartner = str6;
    }
}
