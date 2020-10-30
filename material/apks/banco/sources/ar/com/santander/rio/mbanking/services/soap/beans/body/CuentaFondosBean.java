package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import ar.com.santander.rio.mbanking.utils.UtilString;
import com.google.gson.annotations.SerializedName;
import java.text.DecimalFormat;

public class CuentaFondosBean implements Parcelable {
    public static final Creator<CuentaFondosBean> CREATOR = new Creator<CuentaFondosBean>() {
        public CuentaFondosBean createFromParcel(Parcel parcel) {
            return new CuentaFondosBean(parcel);
        }

        public CuentaFondosBean[] newArray(int i) {
            return new CuentaFondosBean[i];
        }
    };
    @SerializedName("importeDolares")
    private String importeDolares;
    @SerializedName("importePesos")
    private String importePesos;
    @SerializedName("fondos")
    private ListaFondosBean listaFondos;
    @SerializedName("msjCod")
    private String mensajeCodigo;
    @SerializedName("msjDesc")
    private String mensajeDescripcion;
    @SerializedName("msjTitulo")
    private String mensajeTitulo;
    @SerializedName("numeroCuenta")
    private String numero;
    @SerializedName("sucursalCuenta")
    private String sucursalCuenta;
    @SerializedName("tipoCuenta")
    private String tipoCuenta;

    public int describeContents() {
        return 0;
    }

    public CuentaFondosBean(String str, String str2, String str3, String str4, String str5, ListaFondosBean listaFondosBean, String str6, String str7, String str8) {
        this.tipoCuenta = str;
        this.sucursalCuenta = str2;
        this.numero = str3;
        this.importePesos = str4;
        this.importeDolares = str5;
        this.listaFondos = listaFondosBean;
        this.mensajeCodigo = str6;
        this.mensajeTitulo = str7;
        this.mensajeDescripcion = str8;
    }

    public CuentaFondosBean() {
    }

    protected CuentaFondosBean(Parcel parcel) {
        this.tipoCuenta = parcel.readString();
        this.sucursalCuenta = parcel.readString();
        this.numero = parcel.readString();
        this.importePesos = parcel.readString();
        this.importeDolares = parcel.readString();
        this.listaFondos = (ListaFondosBean) parcel.readParcelable(ListaFondosBean.class.getClassLoader());
        this.mensajeCodigo = parcel.readString();
        this.mensajeTitulo = parcel.readString();
        this.mensajeDescripcion = parcel.readString();
    }

    public String getNumero() {
        return this.numero;
    }

    public void setNumero(String str) {
        this.numero = str;
    }

    public String getImportePesos() {
        return this.importePesos;
    }

    public void setImportePesos(String str) {
        this.importePesos = str;
    }

    public String getImporteDolares() {
        return this.importeDolares;
    }

    public void setImporteDolares(String str) {
        this.importeDolares = str;
    }

    public ListaFondosBean getListaFondos() {
        return this.listaFondos;
    }

    public void setListaFondos(ListaFondosBean listaFondosBean) {
        this.listaFondos = listaFondosBean;
    }

    public String getTipoCuenta() {
        return this.tipoCuenta;
    }

    public void setTipoCuenta(String str) {
        this.tipoCuenta = str;
    }

    public String getSucursalCuenta() {
        return this.sucursalCuenta;
    }

    public void setSucursalCuenta(String str) {
        this.sucursalCuenta = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.tipoCuenta);
        parcel.writeString(this.sucursalCuenta);
        parcel.writeString(this.numero);
        parcel.writeString(this.importePesos);
        parcel.writeString(this.importeDolares);
        parcel.writeParcelable(this.listaFondos, i);
        parcel.writeString(this.mensajeCodigo);
        parcel.writeString(this.mensajeTitulo);
        parcel.writeString(this.mensajeDescripcion);
    }

    public String getFormattedSucCuentaLarge() {
        String str = "Cuenta Títulos ";
        String str2 = "Cuenta Banca Privada ";
        if (Integer.valueOf(this.sucursalCuenta).intValue() < 250 || Integer.valueOf(this.sucursalCuenta).intValue() > 259) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(getFormattedSucCuenta());
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str2);
        sb2.append(getFormattedSucCuenta());
        return sb2.toString();
    }

    private String getFormattedSucCuenta() {
        String str;
        String str2 = "";
        if (Integer.valueOf(this.sucursalCuenta).intValue() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(getSucursalFormat(this.sucursalCuenta));
            sb.append("-");
            str2 = sb.toString();
        }
        if (this.numero != null && this.numero.length() < 7) {
            this.numero = getNumberFormat(this.numero);
        }
        if (Integer.valueOf(this.sucursalCuenta).intValue() < 250 || Integer.valueOf(this.sucursalCuenta).intValue() > 259) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(getNumberFormat(this.numero.substring(0, this.numero.length() - 1)));
            sb2.append("/");
            sb2.append(this.numero.substring(this.numero.length() - 1));
            str = sb2.toString();
        } else {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(this.numero.substring(this.numero.length() - 7, this.numero.length() - 1));
            sb3.append("/");
            sb3.append(this.numero.substring(this.numero.length() - 1));
            str = sb3.toString();
        }
        StringBuilder sb4 = new StringBuilder();
        sb4.append(str2);
        sb4.append(str);
        return sb4.toString();
    }

    private static String getNumberFormat(String str) {
        try {
            if (UtilString.isNumericDouble(str)) {
                return new DecimalFormat("0000000").format(Double.valueOf(str));
            }
        } catch (Exception unused) {
        }
        return str;
    }

    private static String getSucursalFormat(String str) {
        try {
            if (UtilString.isNumericDouble(str)) {
                return new DecimalFormat("000").format(Double.valueOf(str));
            }
        } catch (Exception unused) {
        }
        return str;
    }

    public boolean isBancaPrivada() {
        return Integer.valueOf(this.sucursalCuenta).intValue() >= 250 && Integer.valueOf(this.sucursalCuenta).intValue() <= 259;
    }

    public String getMensajeCodigo() {
        return this.mensajeCodigo;
    }

    public void setMensajeCodigo(String str) {
        this.mensajeCodigo = str;
    }

    public String getMensajeTitulo() {
        return this.mensajeTitulo;
    }

    public void setMensajeTitulo(String str) {
        this.mensajeTitulo = str;
    }

    public String getMensajeDescripcion() {
        return this.mensajeDescripcion;
    }

    public void setMensajeDescripcion(String str) {
        this.mensajeDescripcion = str;
    }

    public boolean hasFondosBloqueados() {
        return this.mensajeCodigo != null && this.mensajeCodigo.equals("USER000720");
    }
}
