package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class LeyendaBean implements Parcelable {
    public static final Creator<LeyendaBean> CREATOR = new Creator<LeyendaBean>() {
        public LeyendaBean createFromParcel(Parcel parcel) {
            return new LeyendaBean(parcel);
        }

        public LeyendaBean[] newArray(int i) {
            return new LeyendaBean[i];
        }
    };
    @SerializedName("descripcion")
    private String descripcion;
    @SerializedName("idLeyenda")
    private String idLeyenda;
    @SerializedName("titulo")
    private String titulo;

    public int describeContents() {
        return 0;
    }

    public LeyendaBean() {
    }

    public LeyendaBean(String str, String str2, String str3) {
        this.idLeyenda = str;
        this.titulo = str2;
        this.descripcion = str3;
    }

    public String getIdLeyenda() {
        return this.idLeyenda;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setIdLeyenda(String str) {
        this.idLeyenda = str;
    }

    public void setTitulo(String str) {
        this.titulo = str;
    }

    public void setDescripcion(String str) {
        this.descripcion = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.idLeyenda);
        parcel.writeString(this.titulo);
        parcel.writeString(this.descripcion);
    }

    protected LeyendaBean(Parcel parcel) {
        this.idLeyenda = parcel.readString();
        this.titulo = parcel.readString();
        this.descripcion = parcel.readString();
    }
}
