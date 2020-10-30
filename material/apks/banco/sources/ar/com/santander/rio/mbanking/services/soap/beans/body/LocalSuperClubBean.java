package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class LocalSuperClubBean implements Parcelable {
    public static final Creator<LocalSuperClubBean> CREATOR = new Creator<LocalSuperClubBean>() {
        public LocalSuperClubBean createFromParcel(Parcel parcel) {
            return new LocalSuperClubBean(parcel);
        }

        public LocalSuperClubBean[] newArray(int i) {
            return new LocalSuperClubBean[i];
        }
    };
    @SerializedName("direccion")
    public String direccion;
    @SerializedName("localNumero")
    public String localNumero;
    @SerializedName("localidad")
    public String localidad;
    @SerializedName("provincia")
    public String provincia;
    @SerializedName("shopping")
    public String shopping;

    public int describeContents() {
        return 0;
    }

    public LocalSuperClubBean() {
    }

    public LocalSuperClubBean(String str, String str2, String str3, String str4, String str5) {
        this.shopping = str;
        this.direccion = str2;
        this.localNumero = str3;
        this.localidad = str4;
        this.provincia = str5;
    }

    private LocalSuperClubBean(Parcel parcel) {
        this.shopping = parcel.readString();
        this.direccion = parcel.readString();
        this.localNumero = parcel.readString();
        this.localidad = parcel.readString();
        this.provincia = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.shopping);
        parcel.writeString(this.direccion);
        parcel.writeString(this.localNumero);
        parcel.writeString(this.localidad);
        parcel.writeString(this.provincia);
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof LocalSuperClubBean)) {
            return false;
        }
        LocalSuperClubBean localSuperClubBean = (LocalSuperClubBean) obj;
        if (this.shopping.equals(localSuperClubBean.shopping) && this.direccion.equalsIgnoreCase(localSuperClubBean.direccion) && this.localNumero.equalsIgnoreCase(localSuperClubBean.localNumero) && this.localidad.equalsIgnoreCase(localSuperClubBean.localidad) && this.provincia.equalsIgnoreCase(localSuperClubBean.provincia)) {
            z = true;
        }
        return z;
    }
}
