package ar.com.santander.rio.mbanking.services.model.general;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import ar.com.santander.rio.mbanking.utils.UtilAccount;
import ar.com.santander.rio.mbanking.utils.UtilString;
import com.google.gson.annotations.SerializedName;
import java.text.DecimalFormat;

public class Cuenta implements Parcelable, Cloneable {
    public static final Creator<Cuenta> CREATOR = new Creator<Cuenta>() {
        public Cuenta createFromParcel(Parcel parcel) {
            return new Cuenta(parcel);
        }

        public Cuenta[] newArray(int i) {
            return new Cuenta[i];
        }
    };
    @SerializedName("alias")
    String alias;
    @SerializedName("cbu")
    String cbu;
    @SerializedName("clase")
    String clase;
    @SerializedName("clasePaquete")
    String clasePaquete;
    @SerializedName("claveBancariaUnificada")
    String claveBancariaUnificada;
    @SerializedName("codPaquete")
    String codPaquete;
    @SerializedName("codigoTitularidad")
    String codigoTitularidad;
    private boolean cuentaEnDolares;
    @SerializedName("estadoTarjetaCredito")
    String estadoTarjetaCredito;
    String formattedName;
    @SerializedName("indJerarquia")
    String indJerarquia;
    @SerializedName("nroPaq")
    String nroPaq;
    @SerializedName("nroSuc")
    String nroSuc;
    @SerializedName("nroTarjetaCredito")
    String nroTarjetaCredito;
    @SerializedName("numero")
    String numero;
    @SerializedName("sucursal")
    String sucursal;
    @SerializedName("sucursalPaq")
    String sucursalPaq;
    @SerializedName("tipo")
    String tipo;

    public int describeContents() {
        return 0;
    }

    public Cuenta() {
    }

    public Cuenta(String str, String str2, String str3) {
        this.tipo = str;
        this.numero = str2;
        this.nroSuc = str3;
    }

    protected Cuenta(Parcel parcel) {
        this.nroTarjetaCredito = parcel.readString();
        this.sucursal = parcel.readString();
        this.nroSuc = parcel.readString();
        this.indJerarquia = parcel.readString();
        this.clasePaquete = parcel.readString();
        this.tipo = parcel.readString();
        this.estadoTarjetaCredito = parcel.readString();
        this.clase = parcel.readString();
        this.codigoTitularidad = parcel.readString();
        this.nroPaq = parcel.readString();
        this.sucursalPaq = parcel.readString();
        this.claveBancariaUnificada = parcel.readString();
        this.numero = parcel.readString();
        this.codPaquete = parcel.readString();
        this.formattedName = parcel.readString();
        this.cuentaEnDolares = parcel.readByte() != 0;
        this.cbu = parcel.readString();
        this.alias = parcel.readString();
    }

    public String getNroTarjetaCredito() {
        return this.nroTarjetaCredito;
    }

    public void setNroTarjetaCredito(String str) {
        this.nroTarjetaCredito = str;
    }

    public String getNroSuc() {
        return this.nroSuc;
    }

    public int getNroSucInt() {
        return Integer.parseInt(this.nroSuc);
    }

    public void setNroSuc(String str) {
        this.nroSuc = str;
    }

    public String getIndJerarquia() {
        return this.indJerarquia;
    }

    public void setIndJerarquia(String str) {
        this.indJerarquia = str;
    }

    public String getClasePaquete() {
        return this.clasePaquete;
    }

    public void setClasePaquete(String str) {
        this.clasePaquete = str;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String str) {
        this.tipo = str;
    }

    public String getEstadoTarjetaCredito() {
        return this.estadoTarjetaCredito;
    }

    public void setEstadoTarjetaCredito(String str) {
        this.estadoTarjetaCredito = str;
    }

    public String getClase() {
        return this.clase;
    }

    public void setClase(String str) {
        this.clase = str;
    }

    public String getCodigoTitularidad() {
        return this.codigoTitularidad;
    }

    public void setCodigoTitularidad(String str) {
        this.codigoTitularidad = str;
    }

    public String getNroPaq() {
        return this.nroPaq;
    }

    public void setNroPaq(String str) {
        this.nroPaq = str;
    }

    public String getSucursalPaq() {
        return this.sucursalPaq;
    }

    public void setSucursalPaq(String str) {
        this.sucursalPaq = str;
    }

    public String getClaveBancariaUnificada() {
        return this.claveBancariaUnificada;
    }

    public void setClaveBancariaUnificada(String str) {
        this.claveBancariaUnificada = str;
    }

    public String getNumero() {
        return this.numero;
    }

    public int getNroCuentaInt() {
        return Integer.parseInt(this.numero);
    }

    public void setNumero(String str) {
        this.numero = str;
    }

    public String getCodPaquete() {
        return this.codPaquete;
    }

    public void setCodPaquete(String str) {
        this.codPaquete = str;
    }

    public String getFormattedName() {
        return !TextUtils.isEmpty(this.formattedName) ? this.formattedName : getFormattedAccountNumber();
    }

    public String getFormattedAccountNumber() {
        return UtilAccount.getAccountFormat(this.nroSuc, this.numero);
    }

    public void setFormattedName(String str) {
        this.formattedName = str;
    }

    public boolean esCuentaEnDolares() {
        if (getTipo().equalsIgnoreCase("03") || getTipo().equalsIgnoreCase("04") || getTipo().equalsIgnoreCase("10")) {
            return true;
        }
        if (getTipo().equalsIgnoreCase("02")) {
            return this.cuentaEnDolares;
        }
        return false;
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }

    public void setCuentaEnDolares(boolean z) {
        this.cuentaEnDolares = z;
    }

    public String getNumberAccount() {
        return getNumero();
    }

    public String getSucursal() {
        return this.sucursal;
    }

    public void setSucursal(String str) {
        this.sucursal = str;
    }

    public String getCbu() {
        return this.cbu;
    }

    public void setCbu(String str) {
        this.cbu = str;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String str) {
        this.alias = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.nroTarjetaCredito);
        parcel.writeString(this.sucursal);
        parcel.writeString(this.nroSuc);
        parcel.writeString(this.indJerarquia);
        parcel.writeString(this.clasePaquete);
        parcel.writeString(this.tipo);
        parcel.writeString(this.estadoTarjetaCredito);
        parcel.writeString(this.clase);
        parcel.writeString(this.codigoTitularidad);
        parcel.writeString(this.nroPaq);
        parcel.writeString(this.sucursalPaq);
        parcel.writeString(this.claveBancariaUnificada);
        parcel.writeString(this.numero);
        parcel.writeString(this.codPaquete);
        parcel.writeString(this.formattedName);
        parcel.writeByte(this.cuentaEnDolares ? (byte) 1 : 0);
        parcel.writeString(this.cbu);
        parcel.writeString(this.alias);
    }

    public String getFormattedSucCuentaLarge() {
        String str = "Cuenta Títulos ";
        String str2 = "Cta. Bca. Privada ";
        if (Integer.valueOf(this.nroSuc).intValue() < 250 || Integer.valueOf(this.nroSuc).intValue() > 259) {
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
        if (Integer.valueOf(this.nroSuc).intValue() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(getSucursalFormat(this.nroSuc));
            sb.append("-");
            str2 = sb.toString();
        }
        if (this.numero != null && this.numero.length() < 7) {
            this.numero = getNumberFormat(this.numero);
        }
        if (Integer.valueOf(this.nroSuc).intValue() < 250 || Integer.valueOf(this.nroSuc).intValue() > 259) {
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
        return Integer.valueOf(this.nroSuc).intValue() >= 250 && Integer.valueOf(this.nroSuc).intValue() <= 259;
    }
}
