package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class RecargaBean implements Parcelable {
    public static final Creator<RecargaBean> CREATOR = new Creator<RecargaBean>() {
        public RecargaBean createFromParcel(Parcel parcel) {
            return new RecargaBean(parcel);
        }

        public RecargaBean[] newArray(int i) {
            return new RecargaBean[i];
        }
    };
    @SerializedName("alias")
    private String alias;
    @SerializedName("empDescr")
    private String empDescr;
    @SerializedName("empServ")
    private String empServ;
    @SerializedName("identificacion")
    private String identificacion;

    public int describeContents() {
        return 0;
    }

    public RecargaBean() {
    }

    protected RecargaBean(Parcel parcel) {
        this.empServ = parcel.readString();
        this.empDescr = parcel.readString();
        this.identificacion = parcel.readString();
        this.alias = parcel.readString();
    }

    public String getEmpServ() {
        return this.empServ;
    }

    public void setEmpServ(String str) {
        this.empServ = str;
    }

    public String getEmpDescr() {
        return this.empDescr;
    }

    public void setEmpDescr(String str) {
        this.empDescr = str;
    }

    public String getIdentificacion() {
        return this.identificacion;
    }

    public void setIdentificacion(String str) {
        this.identificacion = str;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String str) {
        this.alias = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.empServ);
        parcel.writeString(this.empDescr);
        parcel.writeString(this.identificacion);
        parcel.writeString(this.alias);
    }
}
