package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class GetCuponesSuperClubBodyResponseBean extends ErrorBodyBean {
    @SerializedName("cupones")
    public CuponesSuperClubBean cupones;
    @SerializedName("localesAdheridos")
    public LocalesAdheridosSuperClub localesAdheridos;

    public GetCuponesSuperClubBodyResponseBean() {
    }

    public GetCuponesSuperClubBodyResponseBean(CuponesSuperClubBean cuponesSuperClubBean, LocalesAdheridosSuperClub localesAdheridosSuperClub) {
        this.cupones = cuponesSuperClubBean;
        this.localesAdheridos = localesAdheridosSuperClub;
    }

    public Boolean hasCoupons() {
        return Boolean.valueOf(this.cupones.cupon.size() != 0);
    }

    public Boolean hasStores() {
        return Boolean.valueOf((this.localesAdheridos == null || this.localesAdheridos.zonas.zona.size() == 0) ? false : true);
    }
}
