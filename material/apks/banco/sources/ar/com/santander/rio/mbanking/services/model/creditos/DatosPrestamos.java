package ar.com.santander.rio.mbanking.services.model.creditos;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class DatosPrestamos implements Parcelable {
    public static final Creator<DatosPrestamos> CREATOR = new Creator<DatosPrestamos>() {
        public DatosPrestamos createFromParcel(Parcel parcel) {
            return new DatosPrestamos(parcel);
        }

        public DatosPrestamos[] newArray(int i) {
            return new DatosPrestamos[i];
        }
    };
    @SerializedName("maxImpPrest")
    String maxImpPrest;
    @SerializedName("minImpPrest")
    String minImpPrest;
    @SerializedName("valorDivisa")
    String valorDivisa;

    public int describeContents() {
        return 0;
    }

    protected DatosPrestamos(Parcel parcel) {
        this.minImpPrest = parcel.readString();
        this.maxImpPrest = parcel.readString();
        this.valorDivisa = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.minImpPrest);
        parcel.writeString(this.maxImpPrest);
        parcel.writeString(this.valorDivisa);
    }

    public String getMinImpPrest() {
        return this.minImpPrest;
    }

    public void setMinImpPrest(String str) {
        this.minImpPrest = str;
    }

    public String getMaxImpPrest() {
        return this.maxImpPrest;
    }

    public void setMaxImpPrest(String str) {
        this.maxImpPrest = str;
    }

    public String getValorDivisa() {
        return this.valorDivisa;
    }

    public void setValorDivisa(String str) {
        this.valorDivisa = str;
    }
}
