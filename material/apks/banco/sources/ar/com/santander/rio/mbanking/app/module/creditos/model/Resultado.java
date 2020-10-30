package ar.com.santander.rio.mbanking.app.module.creditos.model;

import android.text.TextUtils;
import ar.com.santander.rio.mbanking.app.commons.CAmount;

public class Resultado {
    String A;
    String B;
    String C;
    String D;
    String E;
    String F;
    String G;
    String a;
    String b;
    String c;
    String d;
    String e;
    String f;
    String g;
    String h;
    String i;
    String j;
    String k;
    String l;
    String m;
    String n;
    String o;
    String p;
    String q;
    String r;
    String s;
    String t;
    String u;
    String v;
    String w;
    String x;
    String y;
    String z;

    public String getTasaCFTNA() {
        return this.q;
    }

    public void setTasaCFTNA(String str) {
        this.q = str;
    }

    public String getTasaCFTNAIVA() {
        return this.r;
    }

    public void setTasaCFTNAIVA(String str) {
        this.r = str;
    }

    public String getTasaEfectAnual() {
        return this.s;
    }

    public void setTasaEfectAnual(String str) {
        this.s = str;
    }

    public String getImporteSolicitud() {
        return this.a;
    }

    public void setImporteSolicitud(String str) {
        this.a = str;
    }

    public String getImporteNeto() {
        return this.b;
    }

    public void setImporteNeto(String str) {
        this.b = str;
    }

    public String getCuentaDestino() {
        return this.c;
    }

    public void setCuentaDestino(String str) {
        if (str.contains("CTA")) {
            this.c = str.replace("CTA", "CTA $");
        }
    }

    public String getCantidadCuotas() {
        return this.d;
    }

    public void setCantidadCuotas(String str) {
        this.d = str;
    }

    public String getTipoTasa() {
        return this.e;
    }

    public void setTipoTasa(String str) {
        this.e = str;
    }

    public String getTasaNominalAnual() {
        return this.f;
    }

    public void setTasaNominalAnual(String str) {
        this.f = str;
    }

    public String getDestino() {
        return this.g;
    }

    public void setDestino(String str) {
        this.g = str;
    }

    public String getFechaPrimerVencimiento() {
        return this.h;
    }

    public void setFechaPrimerVencimiento(String str) {
        this.h = str;
    }

    public String getCapitalIntereses() {
        return this.i;
    }

    public void setCapitalIntereses(String str) {
        if (!TextUtils.isEmpty(str)) {
            CAmount cAmount = new CAmount(str);
            cAmount.setSymbolCurrencyDollarOrPeso(false);
            this.i = cAmount.getAmount();
        }
    }

    public String getCargoSeguro() {
        return this.j;
    }

    public void setCargoSeguro(String str) {
        if (!TextUtils.isEmpty(str)) {
            CAmount cAmount = new CAmount(str);
            cAmount.setSymbolCurrencyDollarOrPeso(false);
            this.j = cAmount.getAmount();
        }
    }

    public String getIva() {
        return this.k;
    }

    public void setIva(String str) {
        if (!TextUtils.isEmpty(str)) {
            CAmount cAmount = new CAmount(str);
            cAmount.setSymbolCurrencyDollarOrPeso(false);
            this.k = cAmount.getAmount();
        }
    }

    public String getImporte() {
        return this.l;
    }

    public void setImporte(String str) {
        if (!TextUtils.isEmpty(str)) {
            CAmount cAmount = new CAmount(str);
            cAmount.setSymbolCurrencyDollarOrPeso(false);
            this.l = cAmount.getAmount();
        }
    }

    public String getLeyenda() {
        return this.m;
    }

    public void setLeyenda(String str) {
        this.m = str;
    }

    public String getNumeroComprobante() {
        return this.n;
    }

    public void setNumeroComprobante(String str) {
        this.n = str;
    }

    public String getLeyendaConf() {
        return this.o;
    }

    public void setLeyendaConf(String str) {
        this.o = str;
    }

    public String getOtrosImpuestos() {
        return this.p;
    }

    public void setOtrosImpuestos(String str) {
        this.p = str;
    }

    public String getImporteSolUVA() {
        return this.B;
    }

    public void setImporteSolUVA(String str) {
        this.B = str;
    }

    public String getCuotaPuraUVA() {
        return this.C;
    }

    public void setCuotaPuraUVA(String str) {
        this.C = str;
    }

    public String getCotizacionUVA() {
        return this.D;
    }

    public void setCotizacionUVA(String str) {
        this.D = str;
    }

    public String getImporteCuotaUVA() {
        return this.E;
    }

    public void setImporteCuotaUVA(String str) {
        this.E = str;
    }

    public String getFechaCotizaUVA() {
        return this.F;
    }

    public void setFechaCotizaUVA(String str) {
        this.F = str;
    }

    public String getDescripcionLink() {
        return this.t;
    }

    public void setDescripcionLink(String str) {
        this.t = str;
    }

    public String getCodigoLink() {
        return this.u;
    }

    public void setCodigoLink(String str) {
        this.u = str;
    }

    public String getMostrarLink() {
        return this.v;
    }

    public void setMostrarLink(String str) {
        this.v = str;
    }

    public String getSucursal() {
        return this.w;
    }

    public void setSucursal(String str) {
        this.w = str;
    }

    public String getTipoCta() {
        return this.x;
    }

    public void setTipoCta(String str) {
        this.x = str;
    }

    public String getNumeroCta() {
        return this.y;
    }

    public void setNumeroCta(String str) {
        this.y = str;
    }

    public String getLeyendaTasa() {
        return this.z;
    }

    public void setLeyendaTasa(String str) {
        this.z = str;
    }

    public String getLeyendaCuotaPuraUvas() {
        return this.A;
    }

    public void setLeyendaCuotaPuraUvas(String str) {
        this.A = str;
    }

    public String getIndicadorUVA() {
        return this.G;
    }

    public void setIndicadorUVA(String str) {
        this.G = str;
    }
}
