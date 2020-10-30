package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class ZonaSuperClubBean implements Parcelable {
    public static final Creator<ZonaSuperClubBean> CREATOR = new Creator<ZonaSuperClubBean>() {
        public ZonaSuperClubBean createFromParcel(Parcel parcel) {
            return new ZonaSuperClubBean(parcel);
        }

        public ZonaSuperClubBean[] newArray(int i) {
            return new ZonaSuperClubBean[i];
        }
    };
    @SerializedName("locales")
    public LocalesSuperClubBean locales;
    @SerializedName("nombreZona")
    public String nombreZona;

    public int describeContents() {
        return 0;
    }

    public ZonaSuperClubBean() {
    }

    public ZonaSuperClubBean(String str, LocalesSuperClubBean localesSuperClubBean) {
        this.nombreZona = str;
        this.locales = localesSuperClubBean;
    }

    private ZonaSuperClubBean(Parcel parcel) {
        this.nombreZona = parcel.readString();
        this.locales = (LocalesSuperClubBean) parcel.readParcelable(LocalesSuperClubBean.class.getClassLoader());
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.nombreZona);
        parcel.writeParcelable(this.locales, i);
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof ZonaSuperClubBean)) {
            return false;
        }
        ZonaSuperClubBean zonaSuperClubBean = (ZonaSuperClubBean) obj;
        if (this.nombreZona.equalsIgnoreCase(zonaSuperClubBean.nombreZona) && this.locales.equals(zonaSuperClubBean.locales)) {
            z = true;
        }
        return z;
    }
}
