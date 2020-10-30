package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class DatoDeudaBean implements Parcelable {
    public static final Creator<DatoDeudaBean> CREATOR = new Creator<DatoDeudaBean>() {
        public DatoDeudaBean createFromParcel(Parcel parcel) {
            return new DatoDeudaBean(parcel);
        }

        public DatoDeudaBean[] newArray(int i) {
            return new DatoDeudaBean[i];
        }
    };
    @SerializedName("leyenda")
    public String leyenda;
    @SerializedName("valor")
    public String valor;

    public int describeContents() {
        return 0;
    }

    public DatoDeudaBean() {
    }

    public DatoDeudaBean(String str, String str2) {
        this.leyenda = str;
        this.valor = str2;
    }

    protected DatoDeudaBean(Parcel parcel) {
        this.leyenda = parcel.readString();
        this.valor = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.leyenda);
        parcel.writeString(this.valor);
    }
}
