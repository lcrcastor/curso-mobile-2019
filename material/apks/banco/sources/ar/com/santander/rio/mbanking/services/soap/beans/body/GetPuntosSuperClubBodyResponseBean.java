package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class GetPuntosSuperClubBodyResponseBean extends ErrorBodyBean {
    @SerializedName("puntosSuperClub")
    public String puntosSuperClub;

    public GetPuntosSuperClubBodyResponseBean() {
    }

    public GetPuntosSuperClubBodyResponseBean(String str) {
        this.puntosSuperClub = str;
    }
}
