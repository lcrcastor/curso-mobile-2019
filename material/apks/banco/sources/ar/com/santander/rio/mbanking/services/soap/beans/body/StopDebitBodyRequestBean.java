package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class StopDebitBodyRequestBean {
    public static final String TAG = "ar.com.santander.rio.mbanking.services.soap.beans.body.StopDebitBodyRequestBean";
    @SerializedName("codigo")
    private String codigo;
    @SerializedName("nroCuentaDebito")
    private String numCuentaDebito;
    @SerializedName("numCuentaProduc")
    private String numCuentaProduc;
    @SerializedName("sucursalCuentaDebito")
    private String sucCuentaDebito;
    @SerializedName("tipoCuentaDebito")
    private String tipoCuentaDebito;

    public StopDebitBodyRequestBean() {
    }

    public StopDebitBodyRequestBean(ArrayList<String> arrayList) {
        setTipoCuentaDebito((String) arrayList.get(0));
        setSucCuentaDebito((String) arrayList.get(1));
        setNumCuentaDebito((String) arrayList.get(2));
        setCodigo((String) arrayList.get(3));
        setNumCuentaProduc((String) arrayList.get(4));
    }

    public String getTipoCuentaDebito() {
        return this.tipoCuentaDebito;
    }

    public void setTipoCuentaDebito(String str) {
        this.tipoCuentaDebito = str;
    }

    public String getSucCuentaDebito() {
        return this.sucCuentaDebito;
    }

    public void setSucCuentaDebito(String str) {
        this.sucCuentaDebito = str;
    }

    public String getNumCuentaDebito() {
        return this.numCuentaDebito;
    }

    public void setNumCuentaDebito(String str) {
        this.numCuentaDebito = str;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String str) {
        this.codigo = str;
    }

    public String getNumCuentaProduc() {
        return this.numCuentaProduc;
    }

    public void setNumCuentaProduc(String str) {
        this.numCuentaProduc = str;
    }
}
