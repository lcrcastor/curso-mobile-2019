package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class CnsEmpresaDatosEmpresa implements Parcelable {
    public static final Creator<CnsEmpresaDatosEmpresa> CREATOR = new Creator<CnsEmpresaDatosEmpresa>() {
        public CnsEmpresaDatosEmpresa createFromParcel(Parcel parcel) {
            return new CnsEmpresaDatosEmpresa(parcel);
        }

        public CnsEmpresaDatosEmpresa[] newArray(int i) {
            return new CnsEmpresaDatosEmpresa[i];
        }
    };
    public String codDatAdic;
    public String empDescr;
    public String empServ;
    public String gifFactura;
    public String identificacion1;
    public String identificacion2;
    public String tipoEmpresa = "";
    public String tipoImporte;
    public String tipoPago;

    public int describeContents() {
        return 0;
    }

    public CnsEmpresaDatosEmpresa(String str, String str2) {
        this.empServ = str;
        this.empDescr = str2;
    }

    public CnsEmpresaDatosEmpresa(CnsEmpresaDatosEmpresa cnsEmpresaDatosEmpresa) {
        this.empServ = cnsEmpresaDatosEmpresa.empServ;
        this.empDescr = cnsEmpresaDatosEmpresa.empDescr;
        this.tipoEmpresa = cnsEmpresaDatosEmpresa.tipoEmpresa;
        this.tipoImporte = cnsEmpresaDatosEmpresa.tipoImporte;
        this.tipoPago = cnsEmpresaDatosEmpresa.tipoPago;
        this.codDatAdic = cnsEmpresaDatosEmpresa.codDatAdic;
        this.identificacion1 = cnsEmpresaDatosEmpresa.identificacion1;
        this.identificacion2 = cnsEmpresaDatosEmpresa.identificacion2;
        this.gifFactura = cnsEmpresaDatosEmpresa.gifFactura;
    }

    public CnsEmpresaDatosEmpresa(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        this.empServ = str;
        this.empDescr = str2;
        this.tipoEmpresa = str3;
        this.tipoImporte = str4;
        this.tipoPago = str5;
        this.codDatAdic = str6;
        this.identificacion1 = str7;
        this.identificacion2 = str8;
        this.gifFactura = str9;
    }

    public CnsEmpresaDatosEmpresa() {
    }

    protected CnsEmpresaDatosEmpresa(Parcel parcel) {
        this.empServ = parcel.readString();
        this.empDescr = parcel.readString();
        this.tipoEmpresa = parcel.readString();
        this.tipoImporte = parcel.readString();
        this.tipoPago = parcel.readString();
        this.codDatAdic = parcel.readString();
        this.identificacion1 = parcel.readString();
        this.identificacion2 = parcel.readString();
        this.gifFactura = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.empServ);
        parcel.writeString(this.empDescr);
        parcel.writeString(this.tipoEmpresa);
        parcel.writeString(this.tipoImporte);
        parcel.writeString(this.tipoPago);
        parcel.writeString(this.codDatAdic);
        parcel.writeString(this.identificacion1);
        parcel.writeString(this.identificacion2);
        parcel.writeString(this.gifFactura);
    }
}
