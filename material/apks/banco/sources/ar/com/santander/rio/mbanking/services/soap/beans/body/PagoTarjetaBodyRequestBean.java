package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.app.commons.CPagoTarjeta;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class PagoTarjetaBodyRequestBean {
    public static final String TAG = "ar.com.santander.rio.mbanking.services.soap.beans.body.PagoTarjetaBodyRequestBean";
    @SerializedName("codigoMoneda")
    private String codigoMoneda;
    @SerializedName("codigoTitularidad")
    private String codigoTitularidad;
    @SerializedName("cuentaBDolares")
    private String cuentaBDolares;
    @SerializedName("importeDolares")
    private String importeDolares;
    @SerializedName("importePagoTC")
    private String importePagoTC;
    @SerializedName("modoPagoTC")
    private String modoPagoTC;
    @SerializedName("momentoPagoTC")
    private String momentoPagoTC;
    @SerializedName("numCuentaBDolares")
    private String numCuentaBDolares;
    @SerializedName("numCuentaDebito")
    private String numCuentaDebito;
    @SerializedName("numCuentaT")
    private String numCuentaT;
    @SerializedName("numTarjetaCredito")
    private String numTarjetaCredito;
    @SerializedName("sucursalBDolares")
    private String sucursalBDolares;
    @SerializedName("sucursalCuentaD")
    private String sucursalCuentaD;
    @SerializedName("sucursalCuentaT")
    private String sucursalCuentaT;
    @SerializedName("tipoCuentaDebito")
    private String tipoCuentaDebito;
    @SerializedName("tipoPagoTC")
    private String tipoPagoTC;
    @SerializedName("tipoTarjetaCredito")
    private String tipoTarjetaCredito;

    public PagoTarjetaBodyRequestBean() {
    }

    public PagoTarjetaBodyRequestBean(ArrayList<String> arrayList) {
        if (arrayList.size() > 0) {
            setSucursalCuentaD((String) arrayList.get(0));
        }
        if (arrayList.size() > 1) {
            setTipoCuentaDebito((String) arrayList.get(1));
        }
        if (arrayList.size() > 2) {
            setNumCuentaDebito((String) arrayList.get(2));
        }
        if (arrayList.size() > 3) {
            setTipoTarjetaCredito((String) arrayList.get(3));
        }
        if (arrayList.size() > 4) {
            setSucursalCuentaT((String) arrayList.get(4));
        }
        if (arrayList.size() > 5) {
            setNumTarjetaCredito((String) arrayList.get(5));
        }
        if (arrayList.size() > 6) {
            setCodigoTitularidad((String) arrayList.get(6));
        }
        if (arrayList.size() > 7) {
            setNumCuentaT((String) arrayList.get(7));
        }
        if (arrayList.size() > 8) {
            setModoPagoTC((String) arrayList.get(8));
        }
        if (arrayList.size() > 9) {
            setTipoPagoTC((String) arrayList.get(9));
        }
        if (arrayList.size() > 10) {
            setMomentoPagoTC((String) arrayList.get(10));
        }
        if (arrayList.size() > 11) {
            setImportePagoTC(CPagoTarjeta.buildImportePagoTC((String) arrayList.get(11), Constants.LOCALE_DEFAULT_APP));
        }
        if (arrayList.size() > 12) {
            setCodigoMoneda((String) arrayList.get(12));
        }
        if (arrayList.size() > 13) {
            setCuentaBDolares((String) arrayList.get(13));
        }
        if (arrayList.size() > 14) {
            setSucursalBDolares((String) arrayList.get(14));
        }
        if (arrayList.size() > 15) {
            setNumCuentaBDolares((String) arrayList.get(15));
        }
        if (arrayList.size() > 16) {
            setImporteDolares(CPagoTarjeta.buildImporteDolares((String) arrayList.get(16), Constants.LOCALE_DEFAULT_APP));
        }
    }

    public String getSucursalCuentaD() {
        return this.sucursalCuentaD;
    }

    public void setSucursalCuentaD(String str) {
        this.sucursalCuentaD = str;
    }

    public String getTipoCuentaDebito() {
        return this.tipoCuentaDebito;
    }

    public void setTipoCuentaDebito(String str) {
        this.tipoCuentaDebito = str;
    }

    public String getNumCuentaDebito() {
        return this.numCuentaDebito;
    }

    public void setNumCuentaDebito(String str) {
        this.numCuentaDebito = str;
    }

    public String getTipoTarjetaCredito() {
        return this.tipoTarjetaCredito;
    }

    public void setTipoTarjetaCredito(String str) {
        this.tipoTarjetaCredito = str;
    }

    public String getSucursalCuentaT() {
        return this.sucursalCuentaT;
    }

    public void setSucursalCuentaT(String str) {
        this.sucursalCuentaT = str;
    }

    public String getNumTarjetaCredito() {
        return this.numTarjetaCredito;
    }

    public void setNumTarjetaCredito(String str) {
        this.numTarjetaCredito = str;
    }

    public String getCodigoTitularidad() {
        return this.codigoTitularidad;
    }

    public void setCodigoTitularidad(String str) {
        this.codigoTitularidad = str;
    }

    public String getNumCuentaT() {
        return this.numCuentaT;
    }

    public void setNumCuentaT(String str) {
        this.numCuentaT = str;
    }

    public String getModoPagoTC() {
        return this.modoPagoTC;
    }

    public void setModoPagoTC(String str) {
        this.modoPagoTC = str;
    }

    public String getImportePagoTC() {
        return this.importePagoTC;
    }

    public void setImportePagoTC(String str) {
        this.importePagoTC = str;
    }

    public String getTipoPagoTC() {
        return this.tipoPagoTC;
    }

    public void setTipoPagoTC(String str) {
        this.tipoPagoTC = str;
    }

    public String getMomentoPagoTC() {
        return this.momentoPagoTC;
    }

    public void setMomentoPagoTC(String str) {
        this.momentoPagoTC = str;
    }

    public String getCodigoMoneda() {
        return this.codigoMoneda;
    }

    public void setCodigoMoneda(String str) {
        this.codigoMoneda = str;
    }

    public String getCuentaBDolares() {
        return this.cuentaBDolares;
    }

    public void setCuentaBDolares(String str) {
        this.cuentaBDolares = str;
    }

    public String getSucursalBDolares() {
        return this.sucursalBDolares;
    }

    public void setSucursalBDolares(String str) {
        this.sucursalBDolares = str;
    }

    public String getNumCuentaBDolares() {
        return this.numCuentaBDolares;
    }

    public void setNumCuentaBDolares(String str) {
        this.numCuentaBDolares = str;
    }

    public String getImporteDolares() {
        return this.importeDolares;
    }

    public void setImporteDolares(String str) {
        this.importeDolares = str;
    }
}
