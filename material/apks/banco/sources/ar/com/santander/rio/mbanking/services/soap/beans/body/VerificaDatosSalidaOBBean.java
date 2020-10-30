package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class VerificaDatosSalidaOBBean extends VerificaDatosSalidaBean implements Parcelable {
    public static final Creator<VerificaDatosSalidaOBBean> CREATOR = new Creator<VerificaDatosSalidaOBBean>() {
        public VerificaDatosSalidaOBBean createFromParcel(Parcel parcel) {
            return new VerificaDatosSalidaOBBean(parcel);
        }

        public VerificaDatosSalidaOBBean[] newArray(int i) {
            return new VerificaDatosSalidaOBBean[i];
        }
    };
    @SerializedName("bancoReceptor")
    private String bancoReceptor;
    @SerializedName("cbu")
    private String cbu;
    @SerializedName("diferido")
    private String diferido;
    @SerializedName("numero")
    private String numero;
    @SerializedName("sucursal")
    private String sucursal;
    @SerializedName("tipo")
    private String tipo;
    @SerializedName("tipoDescripcion")
    private String tipoDescripcion;

    public int describeContents() {
        return 0;
    }

    public VerificaDatosSalidaOBBean() {
    }

    public VerificaDatosSalidaOBBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14, String str15, String str16, String str17) {
        super(str, str3, str2, str4, str5, str8, str11, str12, str14, str15);
        this.tipo = str6;
        this.tipoDescripcion = str7;
        this.sucursal = str9;
        this.numero = str10;
        this.bancoReceptor = str13;
        this.diferido = str16;
        this.cbu = str17;
    }

    protected VerificaDatosSalidaOBBean(Parcel parcel) {
        this.tipo = parcel.readString();
        this.tipoDescripcion = parcel.readString();
        this.sucursal = parcel.readString();
        this.numero = parcel.readString();
        this.bancoReceptor = parcel.readString();
        this.diferido = parcel.readString();
        this.cbu = parcel.readString();
        setTitular(parcel.readString());
        setTipoCuil(parcel.readString());
        setNumeroCuil(parcel.readString());
        setNup(parcel.readString());
        setCtaDestino(parcel.readString());
        setBancoDestino(parcel.readString());
        setTipoCtaFromBane(parcel.readString());
        setTipoCtaToBane(parcel.readString());
        setFiid(parcel.readString());
        setUser(parcel.readString());
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String str) {
        this.tipo = str;
    }

    public String getTipoDescripcion() {
        return this.tipoDescripcion;
    }

    public void setTipoDescripcion(String str) {
        this.tipoDescripcion = str;
    }

    public String getSucursal() {
        return this.sucursal;
    }

    public void setSucursal(String str) {
        this.sucursal = str;
    }

    public String getNumero() {
        return this.numero;
    }

    public void setNumero(String str) {
        this.numero = str;
    }

    public String getBancoReceptor() {
        return this.bancoReceptor;
    }

    public void setBancoReceptor(String str) {
        this.bancoReceptor = str;
    }

    public String getDiferido() {
        return this.diferido;
    }

    public void setDiferido(String str) {
        this.diferido = str;
    }

    public String getCbu() {
        return this.cbu;
    }

    public void setCbu(String str) {
        this.cbu = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.tipo);
        parcel.writeString(this.tipoDescripcion);
        parcel.writeString(this.sucursal);
        parcel.writeString(this.numero);
        parcel.writeString(this.bancoReceptor);
        parcel.writeString(this.diferido);
        parcel.writeString(this.cbu);
        parcel.writeString(getTitular());
        parcel.writeString(getTipoCuil());
        parcel.writeString(getNumeroCuil());
        parcel.writeString(getNup());
        parcel.writeString(getCtaDestino());
        parcel.writeString(getBancoDestino());
        parcel.writeString(getTipoCtaFromBane());
        parcel.writeString(getTipoCtaToBane());
        parcel.writeString(getFiid());
        parcel.writeString(getUser());
    }
}
