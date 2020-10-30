package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class DatosAdicionalesDeudaBean implements Parcelable {
    public static final Creator<DatosAdicionalesDeudaBean> CREATOR = new Creator<DatosAdicionalesDeudaBean>() {
        public DatosAdicionalesDeudaBean createFromParcel(Parcel parcel) {
            return new DatosAdicionalesDeudaBean(parcel);
        }

        public DatosAdicionalesDeudaBean[] newArray(int i) {
            return new DatosAdicionalesDeudaBean[i];
        }
    };
    @SerializedName("ejemplo")
    public String ejemplo;
    @SerializedName("fechaDesde")
    public String fechaDesde;
    @SerializedName("fechaHasta")
    public String fechaHasta;
    @SerializedName("leyenda")
    public String leyenda;
    @SerializedName("rangoDesde")
    public String rangoDesde;
    @SerializedName("rangoHasta")
    public String rangoHasta;

    public int describeContents() {
        return 0;
    }

    public DatosAdicionalesDeudaBean() {
    }

    public DatosAdicionalesDeudaBean(String str, String str2, String str3, String str4, String str5, String str6) {
        this.leyenda = str;
        this.ejemplo = str2;
        this.fechaDesde = str3;
        this.fechaHasta = str4;
        this.rangoDesde = str5;
        this.rangoHasta = str6;
    }

    public DatosAdicionalesDeudaBean(DatosAdicionalesDeudaBean datosAdicionalesDeudaBean) {
        this.leyenda = datosAdicionalesDeudaBean.leyenda;
        this.ejemplo = datosAdicionalesDeudaBean.ejemplo;
        this.fechaDesde = datosAdicionalesDeudaBean.fechaDesde;
        this.fechaHasta = datosAdicionalesDeudaBean.fechaHasta;
        this.rangoDesde = datosAdicionalesDeudaBean.rangoDesde;
        this.rangoHasta = datosAdicionalesDeudaBean.rangoHasta;
    }

    protected DatosAdicionalesDeudaBean(Parcel parcel) {
        this.leyenda = parcel.readString();
        this.ejemplo = parcel.readString();
        this.fechaDesde = parcel.readString();
        this.fechaHasta = parcel.readString();
        this.rangoDesde = parcel.readString();
        this.rangoHasta = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.leyenda);
        parcel.writeString(this.ejemplo);
        parcel.writeString(this.fechaDesde);
        parcel.writeString(this.fechaHasta);
        parcel.writeString(this.rangoDesde);
        parcel.writeString(this.rangoHasta);
    }
}
