package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class CoberturaBean implements Parcelable {
    public static final Creator<CoberturaBean> CREATOR = new Creator<CoberturaBean>() {
        public CoberturaBean createFromParcel(Parcel parcel) {
            return new CoberturaBean(parcel);
        }

        public CoberturaBean[] newArray(int i) {
            return new CoberturaBean[i];
        }
    };
    @SerializedName("codigo")
    private String codigo;
    @SerializedName("descripcion")
    private String descripcion;
    @SerializedName("sumaAseguradaCober")
    private String sumaAseguradaCober;

    public int describeContents() {
        return 0;
    }

    public CoberturaBean() {
    }

    protected CoberturaBean(Parcel parcel) {
        this.codigo = parcel.readString();
        this.descripcion = parcel.readString();
        this.sumaAseguradaCober = parcel.readString();
    }

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String str) {
        this.codigo = str;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String str) {
        this.descripcion = str;
    }

    public String getSumaAseguradaCober() {
        return this.sumaAseguradaCober;
    }

    public void setSumaAseguradaCober(String str) {
        this.sumaAseguradaCober = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.codigo);
        parcel.writeString(this.descripcion);
        parcel.writeString(this.sumaAseguradaCober);
    }
}
