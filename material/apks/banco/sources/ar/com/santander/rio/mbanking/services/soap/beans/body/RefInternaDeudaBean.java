package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class RefInternaDeudaBean implements Parcelable {
    public static final Creator<RefInternaDeudaBean> CREATOR = new Creator<RefInternaDeudaBean>() {
        public RefInternaDeudaBean createFromParcel(Parcel parcel) {
            return new RefInternaDeudaBean(parcel);
        }

        public RefInternaDeudaBean[] newArray(int i) {
            return new RefInternaDeudaBean[i];
        }
    };
    @SerializedName("leyenda")
    public String leyenda;
    @SerializedName("valor")
    public String valor;

    public int describeContents() {
        return 0;
    }

    public RefInternaDeudaBean() {
    }

    public RefInternaDeudaBean(String str, String str2) {
        this.leyenda = str;
        this.valor = str2;
    }

    protected RefInternaDeudaBean(Parcel parcel) {
        this.leyenda = parcel.readString();
        this.valor = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.leyenda);
        parcel.writeString(this.valor);
    }
}
