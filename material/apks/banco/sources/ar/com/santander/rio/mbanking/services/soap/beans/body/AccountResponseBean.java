package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.io.Serializable;

public class AccountResponseBean implements Parcelable, Serializable {
    public static final Creator<AccountResponseBean> CREATOR = new Creator<AccountResponseBean>() {
        public AccountResponseBean createFromParcel(Parcel parcel) {
            return new AccountResponseBean(parcel);
        }

        public AccountResponseBean[] newArray(int i) {
            return new AccountResponseBean[i];
        }
    };
    public String depositos24D;
    public String depositos24P;
    public String depositos48D;
    public String depositos48P;
    public String depositos72D;
    public String depositos72P;
    public String depositosEfvoD;
    public String depositosEfvoP;
    public String direccionaCA;
    public String limiteAcuerdoD;
    public String limiteAcuerdoP;
    public String numero;
    public String saldoCAD;
    public String saldoCAP;
    public String saldoCCD;
    public String saldoCCP;
    public String saldoCuentaD;
    public String saldoCuentaP;
    public String saldoDisponibleD;
    public String saldoDisponibleP;
    public String sucursal;
    public String tipo;

    public int describeContents() {
        return 0;
    }

    public AccountResponseBean() {
    }

    public AccountResponseBean(String str, String str2, String str3, String str4) {
        this.tipo = str;
        this.sucursal = str2;
        this.numero = str3;
        this.direccionaCA = str4;
    }

    protected AccountResponseBean(Parcel parcel) {
        this.tipo = parcel.readString();
        this.sucursal = parcel.readString();
        this.numero = parcel.readString();
        this.direccionaCA = parcel.readString();
        this.saldoCCD = parcel.readString();
        this.saldoCuentaD = parcel.readString();
        this.limiteAcuerdoD = parcel.readString();
        this.depositos72D = parcel.readString();
        this.depositos48D = parcel.readString();
        this.saldoCAP = parcel.readString();
        this.saldoCuentaP = parcel.readString();
        this.depositos24D = parcel.readString();
        this.limiteAcuerdoP = parcel.readString();
        this.saldoCAD = parcel.readString();
        this.depositos72P = parcel.readString();
        this.depositos48P = parcel.readString();
        this.depositos24P = parcel.readString();
        this.depositosEfvoD = parcel.readString();
        this.saldoDisponibleD = parcel.readString();
        this.saldoCCP = parcel.readString();
        this.saldoDisponibleP = parcel.readString();
        this.depositosEfvoP = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.tipo);
        parcel.writeString(this.sucursal);
        parcel.writeString(this.numero);
        parcel.writeString(this.direccionaCA);
        parcel.writeString(this.saldoCCD);
        parcel.writeString(this.saldoCuentaD);
        parcel.writeString(this.limiteAcuerdoD);
        parcel.writeString(this.depositos72D);
        parcel.writeString(this.depositos48D);
        parcel.writeString(this.saldoCAP);
        parcel.writeString(this.saldoCuentaP);
        parcel.writeString(this.depositos24D);
        parcel.writeString(this.limiteAcuerdoP);
        parcel.writeString(this.saldoCAD);
        parcel.writeString(this.depositos72P);
        parcel.writeString(this.depositos48P);
        parcel.writeString(this.depositos24P);
        parcel.writeString(this.depositosEfvoD);
        parcel.writeString(this.saldoDisponibleD);
        parcel.writeString(this.saldoCCP);
        parcel.writeString(this.saldoDisponibleP);
        parcel.writeString(this.depositosEfvoP);
    }
}
