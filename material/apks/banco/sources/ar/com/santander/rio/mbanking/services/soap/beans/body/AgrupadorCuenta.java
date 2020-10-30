package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class AgrupadorCuenta implements Parcelable {
    public static final Creator<AgrupadorCuenta> CREATOR = new Creator<AgrupadorCuenta>() {
        public AgrupadorCuenta createFromParcel(Parcel parcel) {
            return new AgrupadorCuenta(parcel);
        }

        public AgrupadorCuenta[] newArray(int i) {
            return new AgrupadorCuenta[i];
        }
    };
    @SerializedName("cuenta")
    private List<Cuenta> cuentaList;
    private String msjeAgrupador;

    public int describeContents() {
        return 0;
    }

    protected AgrupadorCuenta(Parcel parcel) {
        this.cuentaList = parcel.createTypedArrayList(Cuenta.CREATOR);
        this.msjeAgrupador = parcel.readString();
    }

    public List<Cuenta> getCuentaList() {
        return this.cuentaList;
    }

    public void setCuentaList(List<Cuenta> list) {
        this.cuentaList = list;
    }

    public String getMsjeAgrupador() {
        return this.msjeAgrupador;
    }

    public void setMsjeAgrupador(String str) {
        this.msjeAgrupador = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.cuentaList);
        parcel.writeString(this.msjeAgrupador);
    }
}
