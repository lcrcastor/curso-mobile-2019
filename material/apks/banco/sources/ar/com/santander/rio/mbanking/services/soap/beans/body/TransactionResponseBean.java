package ar.com.santander.rio.mbanking.services.soap.beans.body;

public class TransactionResponseBean {
    public String codigo;
    public String descripcionCorta;
    public String descripcionLarga;
    public String fecha;
    public String fechaValor;
    public String hora;
    public String importe;
    public String indObservacion;
    public String indicadorFunc;
    public String moneda;
    public String nombreSucursal;
    public String nroComprobante;
    public String observacion;
    public String saldo;
    public String sucursal;

    public TransactionResponseBean() {
    }

    public TransactionResponseBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14, String str15) {
        this.descripcionCorta = str;
        this.moneda = str2;
        this.codigo = str3;
        this.fechaValor = str4;
        this.nroComprobante = str5;
        this.descripcionLarga = str6;
        this.importe = str7;
        this.indicadorFunc = str8;
        this.indObservacion = str9;
        this.fecha = str10;
        this.saldo = str11;
        this.hora = str12;
        this.sucursal = str13;
        this.nombreSucursal = str14;
        this.observacion = str15;
    }

    public TransactionResponseBean(TransactionResponseBean transactionResponseBean) {
        this.descripcionCorta = transactionResponseBean.descripcionCorta;
        this.moneda = transactionResponseBean.moneda;
        this.codigo = transactionResponseBean.codigo;
        this.fechaValor = transactionResponseBean.fechaValor;
        this.nroComprobante = transactionResponseBean.nroComprobante;
        this.descripcionLarga = transactionResponseBean.descripcionLarga;
        this.importe = transactionResponseBean.importe;
        this.indicadorFunc = transactionResponseBean.indicadorFunc;
        this.indObservacion = transactionResponseBean.indObservacion;
        this.fecha = transactionResponseBean.fecha;
        this.saldo = transactionResponseBean.saldo;
        this.hora = transactionResponseBean.hora;
        this.sucursal = transactionResponseBean.sucursal;
        this.nombreSucursal = transactionResponseBean.nombreSucursal;
        this.observacion = transactionResponseBean.observacion;
    }
}
