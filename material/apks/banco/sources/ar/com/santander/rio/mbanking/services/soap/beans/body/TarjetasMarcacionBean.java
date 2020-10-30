package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class TarjetasMarcacionBean implements Parcelable {
    public static final Creator<TarjetasMarcacionBean> CREATOR = new Creator<TarjetasMarcacionBean>() {
        public TarjetasMarcacionBean createFromParcel(Parcel parcel) {
            return new TarjetasMarcacionBean(parcel);
        }

        public TarjetasMarcacionBean[] newArray(int i) {
            return new TarjetasMarcacionBean[i];
        }
    };
    @SerializedName("tarjeta")
    private List<TarjetaMarcacionBean> listaTarjetas;

    public int describeContents() {
        return 0;
    }

    public TarjetasMarcacionBean() {
        this.listaTarjetas = new ArrayList();
    }

    public TarjetasMarcacionBean(List<TarjetaMarcacionBean> list) {
        this.listaTarjetas = list;
    }

    protected TarjetasMarcacionBean(Parcel parcel) {
        this.listaTarjetas = new ArrayList();
        parcel.readList(this.listaTarjetas, TarjetaMarcacionBean.class.getClassLoader());
    }

    public List<TarjetaMarcacionBean> getListaTarjetas() {
        return this.listaTarjetas;
    }

    public void setListaTarjetas(List<TarjetaMarcacionBean> list) {
        this.listaTarjetas = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(this.listaTarjetas);
    }
}
