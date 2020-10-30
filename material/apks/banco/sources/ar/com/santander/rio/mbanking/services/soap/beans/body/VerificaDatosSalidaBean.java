package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class VerificaDatosSalidaBean implements Parcelable {
    public static final Creator<VerificaDatosSalidaBean> CREATOR = new Creator<VerificaDatosSalidaBean>() {
        public VerificaDatosSalidaBean createFromParcel(Parcel parcel) {
            return new VerificaDatosSalidaBean(parcel);
        }

        public VerificaDatosSalidaBean[] newArray(int i) {
            return new VerificaDatosSalidaBean[i];
        }
    };
    @SerializedName("bancoDestino")
    private String bancoDestino;
    @SerializedName("ctaDestino")
    private String ctaDestino;
    @SerializedName("fiid")
    private String fiid;
    @SerializedName("numeroCuil")
    private String numeroCuil;
    @SerializedName("nup")
    private String nup;
    @SerializedName("tipoCtaFromBane")
    private String tipoCtaFromBane;
    @SerializedName("tipoCtaToBane")
    private String tipoCtaToBane = "";
    @SerializedName("tipoCuil")
    private String tipoCuil;
    @SerializedName("titular")
    private String titular;
    @SerializedName("user")
    private String user;

    public int describeContents() {
        return 0;
    }

    public VerificaDatosSalidaBean() {
    }

    public VerificaDatosSalidaBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10) {
        this.titular = str;
        this.tipoCuil = str2;
        this.numeroCuil = str3;
        this.nup = str4;
        this.ctaDestino = str5;
        this.bancoDestino = str6;
        this.tipoCtaFromBane = str7;
        if (str8 == null) {
            str8 = "";
        }
        this.tipoCtaToBane = str8;
        this.fiid = str9;
        this.user = str10;
    }

    protected VerificaDatosSalidaBean(Parcel parcel) {
        this.titular = parcel.readString();
        this.tipoCuil = parcel.readString();
        this.numeroCuil = parcel.readString();
        this.nup = parcel.readString();
        this.ctaDestino = parcel.readString();
        this.bancoDestino = parcel.readString();
        this.tipoCtaFromBane = parcel.readString();
        this.tipoCtaToBane = parcel.readString();
        if (this.tipoCtaFromBane != null) {
            this.tipoCtaFromBane = "";
        }
        this.fiid = parcel.readString();
        this.user = parcel.readString();
    }

    public static Creator<VerificaDatosSalidaBean> getCREATOR() {
        return CREATOR;
    }

    public String getTitular() {
        return this.titular;
    }

    public void setTitular(String str) {
        this.titular = str;
    }

    public String getTipoCuil() {
        return this.tipoCuil;
    }

    public void setTipoCuil(String str) {
        this.tipoCuil = str;
    }

    public String getNumeroCuil() {
        return this.numeroCuil;
    }

    public void setNumeroCuil(String str) {
        this.numeroCuil = str;
    }

    public String getNup() {
        return this.nup;
    }

    public void setNup(String str) {
        this.nup = str;
    }

    public String getCtaDestino() {
        return this.ctaDestino;
    }

    public void setCtaDestino(String str) {
        this.ctaDestino = str;
    }

    public String getBancoDestino() {
        return this.bancoDestino;
    }

    public void setBancoDestino(String str) {
        this.bancoDestino = str;
    }

    public String getTipoCtaFromBane() {
        return this.tipoCtaFromBane;
    }

    public void setTipoCtaFromBane(String str) {
        if (str == null) {
            str = "";
        }
        this.tipoCtaFromBane = str;
    }

    public String getTipoCtaToBane() {
        return this.tipoCtaToBane;
    }

    public void setTipoCtaToBane(String str) {
        this.tipoCtaToBane = str;
    }

    public String getFiid() {
        return this.fiid;
    }

    public void setFiid(String str) {
        this.fiid = str;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String str) {
        this.user = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.titular);
        parcel.writeString(this.tipoCuil);
        parcel.writeString(this.numeroCuil);
        parcel.writeString(this.nup);
        parcel.writeString(this.ctaDestino);
        parcel.writeString(this.bancoDestino);
        parcel.writeString(this.tipoCtaFromBane);
        parcel.writeString(this.tipoCtaToBane);
        parcel.writeString(this.fiid);
        parcel.writeString(this.user);
    }
}
