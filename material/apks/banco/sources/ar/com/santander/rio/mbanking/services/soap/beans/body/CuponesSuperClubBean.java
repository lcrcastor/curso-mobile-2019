package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class CuponesSuperClubBean {
    @SerializedName("cupon")
    public List<CuponSuperClubBean> cupon;

    public CuponesSuperClubBean() {
        this.cupon = new ArrayList();
    }

    public CuponesSuperClubBean(List<CuponSuperClubBean> list) {
        this.cupon = list;
    }
}
