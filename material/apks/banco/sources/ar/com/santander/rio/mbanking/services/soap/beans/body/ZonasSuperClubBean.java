package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class ZonasSuperClubBean implements Parcelable {
    public static final Creator<ZonasSuperClubBean> CREATOR = new Creator<ZonasSuperClubBean>() {
        public ZonasSuperClubBean createFromParcel(Parcel parcel) {
            return new ZonasSuperClubBean(parcel);
        }

        public ZonasSuperClubBean[] newArray(int i) {
            return new ZonasSuperClubBean[i];
        }
    };
    @SerializedName("zona")
    public List<ZonaSuperClubBean> zona;

    public int describeContents() {
        return 0;
    }

    public ZonasSuperClubBean() {
        this.zona = new ArrayList();
    }

    public ZonasSuperClubBean(List<ZonaSuperClubBean> list) {
        this.zona = list;
    }

    private ZonasSuperClubBean(Parcel parcel) {
        this.zona = new ArrayList();
        parcel.readTypedList(this.zona, ZonaSuperClubBean.CREATOR);
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.zona);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ZonasSuperClubBean)) {
            return false;
        }
        return this.zona.equals(((ZonasSuperClubBean) obj).zona);
    }
}
