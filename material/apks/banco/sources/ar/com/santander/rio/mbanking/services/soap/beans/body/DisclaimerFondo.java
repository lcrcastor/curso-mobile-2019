package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class DisclaimerFondo implements Parcelable {
    public static final Creator<DisclaimerFondo> CREATOR = new Creator<DisclaimerFondo>() {
        public DisclaimerFondo createFromParcel(Parcel parcel) {
            return new DisclaimerFondo(parcel);
        }

        public DisclaimerFondo[] newArray(int i) {
            return new DisclaimerFondo[i];
        }
    };
    @SerializedName("texto")
    private String texto;
    @SerializedName("titulo")
    private String titulo;

    public int describeContents() {
        return 0;
    }

    public DisclaimerFondo() {
    }

    public DisclaimerFondo(String str, String str2) {
        this.titulo = str;
        this.texto = str2;
    }

    protected DisclaimerFondo(Parcel parcel) {
        this.titulo = parcel.readString();
        this.texto = parcel.readString();
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String str) {
        this.titulo = str;
    }

    public String getTexto() {
        return this.texto;
    }

    public void setTexto(String str) {
        this.texto = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.titulo);
        parcel.writeString(this.texto);
    }
}
