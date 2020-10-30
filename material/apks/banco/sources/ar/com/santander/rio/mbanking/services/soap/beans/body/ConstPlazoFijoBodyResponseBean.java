package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class ConstPlazoFijoBodyResponseBean extends ErrorBodyBean {
    public String accionAlVto;
    public String accionAlVtoDescr;
    public String capital;
    @SerializedName("cuentaDebito")
    public CuentaDebitoBean cuentaDebitoBean;
    public String descrPlazo;
    public String fechaAlta;
    public String fechaHora;
    public String fechaVto;
    public String interes;
    @SerializedName("listaLeyendas")
    public ListaLeyendas listaLeyendas;
    public String moneda;
    public String nroCertificado;
    public String nroComprobante;
    public String plazoDias;
    public String tasaEfectivaAnual;
    public String tasaNominalAnual;
    @SerializedName("impuestos")
    public List<TipoImpuestoBean> tipoImpuestoBean;
    public String tipoInvocacion;
    public String tipoPlazo;

    public ConstPlazoFijoBodyResponseBean() {
        this.tipoImpuestoBean = new ArrayList();
    }

    public ConstPlazoFijoBodyResponseBean(String str, CuentaDebitoBean cuentaDebitoBean2, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14, String str15, String str16, List<TipoImpuestoBean> list, ListaLeyendas listaLeyendas2) {
        this.tipoInvocacion = str;
        this.cuentaDebitoBean = cuentaDebitoBean2;
        this.tipoPlazo = str2;
        this.descrPlazo = str3;
        this.plazoDias = str4;
        this.fechaVto = str5;
        this.tasaNominalAnual = str6;
        this.tasaEfectivaAnual = str7;
        this.capital = str8;
        this.interes = str9;
        this.moneda = str10;
        this.fechaAlta = str11;
        this.accionAlVto = str12;
        this.accionAlVtoDescr = str13;
        this.nroCertificado = str14;
        this.nroComprobante = str15;
        this.fechaHora = str16;
        this.tipoImpuestoBean = list;
        this.listaLeyendas = listaLeyendas2;
    }
}
