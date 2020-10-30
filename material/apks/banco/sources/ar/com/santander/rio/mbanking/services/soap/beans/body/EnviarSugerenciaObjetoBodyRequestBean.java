package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EnviarSugerenciaObjetoBodyRequestBean implements Parcelable {
    public static final Creator<EnviarSugerenciaObjetoBodyRequestBean> CREATOR = new Creator<EnviarSugerenciaObjetoBodyRequestBean>() {
        public EnviarSugerenciaObjetoBodyRequestBean createFromParcel(Parcel parcel) {
            return new EnviarSugerenciaObjetoBodyRequestBean(parcel);
        }

        public EnviarSugerenciaObjetoBodyRequestBean[] newArray(int i) {
            return new EnviarSugerenciaObjetoBodyRequestBean[i];
        }
    };
    @SerializedName("marca")
    @Expose
    private String marca;
    @SerializedName("modelo")
    @Expose
    private String modelo;
    @SerializedName("tipo")
    @Expose
    private String tipo;
    @SerializedName("valor")
    @Expose
    private String valor;

    public int describeContents() {
        return 0;
    }

    protected EnviarSugerenciaObjetoBodyRequestBean(Parcel parcel) {
        this.tipo = (String) parcel.readValue(String.class.getClassLoader());
        this.marca = (String) parcel.readValue(String.class.getClassLoader());
        this.modelo = (String) parcel.readValue(String.class.getClassLoader());
        this.valor = (String) parcel.readValue(String.class.getClassLoader());
    }

    public EnviarSugerenciaObjetoBodyRequestBean(String str, String str2, String str3, String str4) {
        this.tipo = str;
        this.marca = str2;
        this.modelo = str3;
        this.valor = str4;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String str) {
        this.tipo = str;
    }

    public String getMarca() {
        return this.marca;
    }

    public void setMarca(String str) {
        this.marca = str;
    }

    public String getModelo() {
        return this.modelo;
    }

    public void setModelo(String str) {
        this.modelo = str;
    }

    public String getValor() {
        return this.valor;
    }

    public void setValor(String str) {
        this.valor = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(this.tipo);
        parcel.writeValue(this.marca);
        parcel.writeValue(this.modelo);
        parcel.writeValue(this.valor);
    }
}
