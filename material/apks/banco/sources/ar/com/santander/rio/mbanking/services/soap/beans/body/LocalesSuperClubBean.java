package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class LocalesSuperClubBean implements Parcelable {
    public static final Creator<LocalesSuperClubBean> CREATOR = new Creator<LocalesSuperClubBean>() {
        public LocalesSuperClubBean createFromParcel(Parcel parcel) {
            return new LocalesSuperClubBean(parcel);
        }

        public LocalesSuperClubBean[] newArray(int i) {
            return new LocalesSuperClubBean[i];
        }
    };
    @SerializedName("local")
    public List<LocalSuperClubBean> local;

    public int describeContents() {
        return 0;
    }

    public LocalesSuperClubBean() {
        this.local = new ArrayList();
    }

    public LocalesSuperClubBean(List<LocalSuperClubBean> list) {
        this.local = list;
    }

    private LocalesSuperClubBean(Parcel parcel) {
        this.local = new ArrayList();
        parcel.readTypedList(this.local, LocalSuperClubBean.CREATOR);
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.local);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof LocalesSuperClubBean)) {
            return false;
        }
        return this.local.equals(((LocalesSuperClubBean) obj).local);
    }
}
