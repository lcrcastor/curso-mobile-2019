package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Custodia implements Parcelable {
    public static final Creator<Custodia> CREATOR = new Creator<Custodia>() {
        public Custodia createFromParcel(Parcel parcel) {
            return new Custodia(parcel);
        }

        public Custodia[] newArray(int i) {
            return new Custodia[i];
        }
    };
    private String canValorNom;
    private String estado;
    private String precioMercado;
    private String tenValuadaHoy;
    private String tipoEspecie;

    public int describeContents() {
        return 0;
    }

    protected Custodia(Parcel parcel) {
        this.tenValuadaHoy = parcel.readString();
        this.tipoEspecie = parcel.readString();
        this.canValorNom = parcel.readString();
        this.precioMercado = parcel.readString();
        this.estado = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.tenValuadaHoy);
        parcel.writeString(this.tipoEspecie);
        parcel.writeString(this.canValorNom);
        parcel.writeString(this.precioMercado);
        parcel.writeString(this.estado);
    }

    public String getTenValuadaHoy() {
        return this.tenValuadaHoy;
    }

    public void setTenValuadaHoy(String str) {
        this.tenValuadaHoy = str;
    }

    public String getTipoEspecie() {
        return this.tipoEspecie;
    }

    public void setTipoEspecie(String str) {
        this.tipoEspecie = str;
    }

    public String getCanValorNom() {
        return this.canValorNom;
    }

    public void setCanValorNom(String str) {
        this.canValorNom = str;
    }

    public String getPrecioMercado() {
        return this.precioMercado;
    }

    public void setPrecioMercado(String str) {
        this.precioMercado = str;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String str) {
        this.estado = str;
    }
}
