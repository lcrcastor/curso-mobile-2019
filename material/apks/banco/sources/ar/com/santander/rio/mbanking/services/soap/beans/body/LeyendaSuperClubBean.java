package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class LeyendaSuperClubBean implements Parcelable {
    public static final Creator<LeyendaSuperClubBean> CREATOR = new Creator<LeyendaSuperClubBean>() {
        public LeyendaSuperClubBean createFromParcel(Parcel parcel) {
            return new LeyendaSuperClubBean(parcel);
        }

        public LeyendaSuperClubBean[] newArray(int i) {
            return new LeyendaSuperClubBean[i];
        }
    };
    @SerializedName("descripcion")
    public String descripcion;
    @SerializedName("idLeyenda")
    public String idLeyenda;
    @SerializedName("titulo")
    public String titulo;

    public int describeContents() {
        return 0;
    }

    public LeyendaSuperClubBean() {
    }

    public LeyendaSuperClubBean(String str, String str2, String str3) {
        this.idLeyenda = str;
        this.titulo = str2;
        this.descripcion = str3;
    }

    private LeyendaSuperClubBean(Parcel parcel) {
        this.idLeyenda = parcel.readString();
        this.titulo = parcel.readString();
        this.descripcion = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.idLeyenda);
        parcel.writeString(this.titulo);
        parcel.writeString(this.descripcion);
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof LeyendaSuperClubBean)) {
            return false;
        }
        LeyendaSuperClubBean leyendaSuperClubBean = (LeyendaSuperClubBean) obj;
        if (this.idLeyenda.equalsIgnoreCase(leyendaSuperClubBean.idLeyenda) && this.titulo.equalsIgnoreCase(leyendaSuperClubBean.titulo) && this.descripcion.equalsIgnoreCase(leyendaSuperClubBean.descripcion)) {
            z = true;
        }
        return z;
    }
}
