package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class OpcionBean implements Parcelable {
    public static final Creator<OpcionBean> CREATOR = new Creator<OpcionBean>() {
        public OpcionBean createFromParcel(Parcel parcel) {
            return new OpcionBean(parcel);
        }

        public OpcionBean[] newArray(int i) {
            return new OpcionBean[i];
        }
    };
    @SerializedName("idOpcion")
    private String idOpcion;
    @SerializedName("textoOpcion")
    private String textoOpcion;

    public int describeContents() {
        return 0;
    }

    public OpcionBean() {
    }

    protected OpcionBean(Parcel parcel) {
        this.idOpcion = parcel.readString();
        this.textoOpcion = parcel.readString();
    }

    public String getIdOpcion() {
        return this.idOpcion;
    }

    public void setIdOpcion(String str) {
        this.idOpcion = str;
    }

    public String getTextoOpcion() {
        return this.textoOpcion;
    }

    public void setTextoOpcion(String str) {
        this.textoOpcion = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.idOpcion);
        parcel.writeString(this.textoOpcion);
    }
}
