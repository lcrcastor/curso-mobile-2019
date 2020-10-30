package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ListaVariacionesBean implements Parcelable {
    public static final Creator<ListaVariacionesBean> CREATOR = new Creator<ListaVariacionesBean>() {
        public ListaVariacionesBean createFromParcel(Parcel parcel) {
            return new ListaVariacionesBean(parcel);
        }

        public ListaVariacionesBean[] newArray(int i) {
            return new ListaVariacionesBean[i];
        }
    };
    @SerializedName("variacion")
    public List<Variacion> variacion;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
    }

    protected ListaVariacionesBean(Parcel parcel) {
    }
}
