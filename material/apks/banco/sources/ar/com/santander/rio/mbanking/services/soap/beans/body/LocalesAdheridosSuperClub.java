package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class LocalesAdheridosSuperClub implements Parcelable {
    public static final Creator<LocalesAdheridosSuperClub> CREATOR = new Creator<LocalesAdheridosSuperClub>() {
        public LocalesAdheridosSuperClub createFromParcel(Parcel parcel) {
            return new LocalesAdheridosSuperClub(parcel);
        }

        public LocalesAdheridosSuperClub[] newArray(int i) {
            return new LocalesAdheridosSuperClub[i];
        }
    };
    @SerializedName("marca")
    public String marca;
    @SerializedName("zonas")
    public ZonasSuperClubBean zonas;

    public int describeContents() {
        return 0;
    }

    public LocalesAdheridosSuperClub() {
    }

    public LocalesAdheridosSuperClub(String str, ZonasSuperClubBean zonasSuperClubBean) {
        this.marca = str;
        this.zonas = zonasSuperClubBean;
    }

    private LocalesAdheridosSuperClub(Parcel parcel) {
        this.marca = parcel.readString();
        this.zonas = (ZonasSuperClubBean) parcel.readParcelable(ZonasSuperClubBean.class.getClassLoader());
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.marca);
        parcel.writeParcelable(this.zonas, i);
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof LocalesAdheridosSuperClub)) {
            return false;
        }
        LocalesAdheridosSuperClub localesAdheridosSuperClub = (LocalesAdheridosSuperClub) obj;
        if (this.marca.equalsIgnoreCase(localesAdheridosSuperClub.marca) && this.zonas.equals(localesAdheridosSuperClub.zonas)) {
            z = true;
        }
        return z;
    }
}
