package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import ar.com.santander.rio.mbanking.app.commons.CConsDescripciones;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.utils.UtilAccount;

public class CompraVentaDolaresCuentaBean implements Parcelable {
    public static final Creator<CompraVentaDolaresCuentaBean> CREATOR = new Creator<CompraVentaDolaresCuentaBean>() {
        public CompraVentaDolaresCuentaBean createFromParcel(Parcel parcel) {
            return new CompraVentaDolaresCuentaBean(parcel);
        }

        public CompraVentaDolaresCuentaBean[] newArray(int i) {
            return new CompraVentaDolaresCuentaBean[i];
        }
    };
    public String clase;
    public String numero;
    public String saldo;
    public String sucursal;
    public String tipo;

    public int describeContents() {
        return 0;
    }

    public CompraVentaDolaresCuentaBean() {
        this.tipo = "";
        this.sucursal = "";
        this.numero = "";
        this.saldo = "";
        this.clase = "";
    }

    public CompraVentaDolaresCuentaBean(String str, String str2, String str3, String str4, String str5) {
        this.tipo = str;
        this.sucursal = str2;
        this.numero = str3;
        this.saldo = str4;
        this.clase = str5;
    }

    protected CompraVentaDolaresCuentaBean(Parcel parcel) {
        this.tipo = parcel.readString();
        this.sucursal = parcel.readString();
        this.numero = parcel.readString();
        this.saldo = parcel.readString();
        this.clase = parcel.readString();
    }

    public String getAbreviatureAndAccountFormat(SessionManager sessionManager) {
        return UtilAccount.getAbreviatureAndAccountFormat(CConsDescripciones.getConsDescripcionTPOCTACORTA(sessionManager), this.tipo, this.sucursal, this.numero);
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.tipo);
        parcel.writeString(this.sucursal);
        parcel.writeString(this.numero);
        parcel.writeString(this.saldo);
        parcel.writeString(this.clase);
    }
}
