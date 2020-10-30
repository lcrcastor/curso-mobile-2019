package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import cz.msebera.android.httpclient.util.TextUtils;

public class LeyendaItem implements Parcelable {
    public static final Creator<LeyendaItem> CREATOR = new Creator<LeyendaItem>() {
        public LeyendaItem createFromParcel(Parcel parcel) {
            return new LeyendaItem(parcel);
        }

        public LeyendaItem[] newArray(int i) {
            return new LeyendaItem[i];
        }
    };
    @SerializedName("descripcion")
    private String descripcion = "";
    @SerializedName("idLeyenda")
    private String idLeyenda = "";
    @SerializedName("titulo")
    private String titulo = "";

    public int describeContents() {
        return 0;
    }

    protected LeyendaItem(Parcel parcel) {
        this.descripcion = (String) parcel.readValue(String.class.getClassLoader());
        this.idLeyenda = (String) parcel.readValue(String.class.getClassLoader());
        this.titulo = (String) parcel.readValue(String.class.getClassLoader());
    }

    public LeyendaItem() {
    }

    public LeyendaItem(String str, String str2, String str3) {
        this.descripcion = str;
        this.idLeyenda = str2;
        this.titulo = str3;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String str) {
        this.descripcion = str;
    }

    public String getIdLeyenda() {
        return this.idLeyenda;
    }

    public void setIdLeyenda(String str) {
        this.idLeyenda = str;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String str) {
        this.titulo = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(this.descripcion);
        parcel.writeValue(this.idLeyenda);
        parcel.writeValue(this.titulo);
    }

    public boolean isEmpty() {
        return TextUtils.isEmpty(this.idLeyenda);
    }
}
