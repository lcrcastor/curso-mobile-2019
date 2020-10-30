package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class AccountRequestBean implements Parcelable {
    public static final Creator<AccountRequestBean> CREATOR = new Creator<AccountRequestBean>() {
        public AccountRequestBean createFromParcel(Parcel parcel) {
            return new AccountRequestBean(parcel);
        }

        public AccountRequestBean[] newArray(int i) {
            return new AccountRequestBean[i];
        }
    };
    @SerializedName("divisa")
    public String divisa;
    @SerializedName("nroCta")
    public String nroCta;
    @SerializedName("nroPaq")
    public String nroPaq;
    @SerializedName("numero")
    public String numero;
    @SerializedName("sucursal")
    public String sucursal;
    @SerializedName("sucursalCta")
    public String sucursalCta;
    @SerializedName("sucursalPaq")
    public String sucursalPaq;
    @SerializedName("tipo")
    public String tipo;

    public int describeContents() {
        return 0;
    }

    public AccountRequestBean() {
    }

    public AccountRequestBean(String str, String str2, String str3) {
        this.tipo = str;
        this.sucursal = str2;
        this.numero = str3;
    }

    protected AccountRequestBean(Parcel parcel) {
        this.tipo = parcel.readString();
        this.sucursal = parcel.readString();
        this.numero = parcel.readString();
        this.divisa = parcel.readString();
        this.sucursalCta = parcel.readString();
        this.nroCta = parcel.readString();
        this.sucursalPaq = parcel.readString();
        this.nroPaq = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.tipo);
        parcel.writeString(this.sucursal);
        parcel.writeString(this.numero);
        parcel.writeString(this.divisa);
        parcel.writeString(this.sucursalCta);
        parcel.writeString(this.nroCta);
        parcel.writeString(this.sucursalPaq);
        parcel.writeString(this.nroPaq);
    }
}
