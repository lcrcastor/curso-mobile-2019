package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import ar.com.santander.rio.mbanking.services.model.general.ListaLeyenda;
import com.google.gson.annotations.SerializedName;

public class CambiarLimiteBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<CambiarLimiteBodyResponseBean> CREATOR = new Creator<CambiarLimiteBodyResponseBean>() {
        public CambiarLimiteBodyResponseBean createFromParcel(Parcel parcel) {
            return new CambiarLimiteBodyResponseBean(parcel);
        }

        public CambiarLimiteBodyResponseBean[] newArray(int i) {
            return new CambiarLimiteBodyResponseBean[i];
        }
    };
    @SerializedName("fechaOperacion")
    public String fechaOperacion;
    @SerializedName("listaLeyendas")
    public ListaLeyenda listaLeyendas;
    @SerializedName("nroComprobante")
    public String nroComprobante;

    public int describeContents() {
        return 0;
    }

    protected CambiarLimiteBodyResponseBean(Parcel parcel) {
        this.nroComprobante = parcel.readString();
        this.fechaOperacion = parcel.readString();
        this.listaLeyendas = (ListaLeyenda) parcel.readParcelable(ListaLeyenda.class.getClassLoader());
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.nroComprobante);
        parcel.writeString(this.fechaOperacion);
        parcel.writeParcelable(this.listaLeyendas, i);
    }
}
