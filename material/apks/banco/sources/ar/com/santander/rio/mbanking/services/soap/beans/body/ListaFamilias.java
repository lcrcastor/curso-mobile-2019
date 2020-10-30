package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class ListaFamilias implements Parcelable {
    public static final Creator<ListaFamilias> CREATOR = new Creator<ListaFamilias>() {
        public ListaFamilias createFromParcel(Parcel parcel) {
            return new ListaFamilias(parcel);
        }

        public ListaFamilias[] newArray(int i) {
            return new ListaFamilias[i];
        }
    };
    @SerializedName("familia")
    private List<FamiliaBean> familia = new ArrayList();

    public int describeContents() {
        return 0;
    }

    protected ListaFamilias(Parcel parcel) {
        parcel.readList(this.familia, FamiliaBean.class.getClassLoader());
    }

    public ListaFamilias() {
    }

    public ListaFamilias(List<FamiliaBean> list) {
        this.familia = list;
    }

    public List<FamiliaBean> getFamilia() {
        return this.familia;
    }

    public void setFamilia(List<FamiliaBean> list) {
        this.familia = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(this.familia);
    }
}
