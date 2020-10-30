package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ViajesBean implements Parcelable {
    public static final Creator<ViajesBean> CREATOR = new Creator<ViajesBean>() {
        public ViajesBean createFromParcel(Parcel parcel) {
            return new ViajesBean(parcel);
        }

        public ViajesBean[] newArray(int i) {
            return new ViajesBean[i];
        }
    };
    @SerializedName("viaje")
    private List<ViajeBean> listaViajes;

    public int describeContents() {
        return 0;
    }

    public ViajesBean() {
    }

    public ViajesBean(List<ViajeBean> list) {
        this.listaViajes = list;
    }

    public List<ViajeBean> getListaViajes() {
        return this.listaViajes;
    }

    public void setListaViajes(List<ViajeBean> list) {
        this.listaViajes = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.listaViajes);
    }

    protected ViajesBean(Parcel parcel) {
        this.listaViajes = parcel.createTypedArrayList(ViajeBean.CREATOR);
    }
}
