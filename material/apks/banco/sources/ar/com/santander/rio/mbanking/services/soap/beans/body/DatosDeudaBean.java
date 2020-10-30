package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class DatosDeudaBean implements Parcelable {
    public static final Creator<DatosDeudaBean> CREATOR = new Creator<DatosDeudaBean>() {
        public DatosDeudaBean createFromParcel(Parcel parcel) {
            return new DatosDeudaBean(parcel);
        }

        public DatosDeudaBean[] newArray(int i) {
            return new DatosDeudaBean[i];
        }
    };
    public String anticipoCuota;
    public String cuit;
    public String cuitEmpleador;
    public String cur;
    public String datAdicionales;
    public String datosAdic;
    public DatosAdicionalesDeudaBean datosAdicionales;
    public CnsEmpresaDatosEmpresa datosEmpresa;
    public String empDescr;
    public String empServ;
    public String factura;
    public String identificacion;
    public String importe;
    public String infoAdicional;
    public String moneda;
    public String numVep;
    public String periodo;
    public String tipoImporte;
    public CnsEmpresaValidaciones validaciones;
    public String vencimiento;

    public int describeContents() {
        return 0;
    }

    public DatosDeudaBean() {
        this.datosEmpresa = new CnsEmpresaDatosEmpresa();
        this.datosAdicionales = new DatosAdicionalesDeudaBean();
        this.validaciones = new CnsEmpresaValidaciones();
    }

    public DatosDeudaBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        this.empServ = str;
        this.empDescr = str2;
        this.identificacion = str3;
        this.tipoImporte = str4;
        this.importe = str5;
        this.moneda = str6;
        this.factura = str7;
        this.vencimiento = str8;
        this.datosEmpresa = new CnsEmpresaDatosEmpresa();
        this.datosAdicionales = new DatosAdicionalesDeudaBean();
        this.validaciones = new CnsEmpresaValidaciones();
    }

    public DatosDeudaBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14, String str15, CnsEmpresaDatosEmpresa cnsEmpresaDatosEmpresa, DatosAdicionalesDeudaBean datosAdicionalesDeudaBean, CnsEmpresaValidaciones cnsEmpresaValidaciones, String str16) {
        this(str, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, str12, "", str13, str14, str15, cnsEmpresaDatosEmpresa, datosAdicionalesDeudaBean, cnsEmpresaValidaciones, str16);
    }

    public DatosDeudaBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14, String str15, String str16, CnsEmpresaDatosEmpresa cnsEmpresaDatosEmpresa, DatosAdicionalesDeudaBean datosAdicionalesDeudaBean, CnsEmpresaValidaciones cnsEmpresaValidaciones, String str17) {
        CnsEmpresaDatosEmpresa cnsEmpresaDatosEmpresa2;
        DatosAdicionalesDeudaBean datosAdicionalesDeudaBean2;
        CnsEmpresaValidaciones cnsEmpresaValidaciones2;
        this.empServ = str;
        this.empDescr = str2;
        this.identificacion = str3;
        this.tipoImporte = str4;
        this.importe = str5;
        this.moneda = str6;
        this.factura = str7;
        this.vencimiento = str8;
        this.infoAdicional = str9;
        this.cur = str10;
        this.numVep = str11;
        this.cuit = str12;
        this.cuitEmpleador = str13;
        this.periodo = str14;
        this.anticipoCuota = str15;
        this.datosAdic = str16;
        if (cnsEmpresaDatosEmpresa != null) {
            cnsEmpresaDatosEmpresa2 = cnsEmpresaDatosEmpresa;
        } else {
            cnsEmpresaDatosEmpresa2 = new CnsEmpresaDatosEmpresa();
        }
        this.datosEmpresa = cnsEmpresaDatosEmpresa2;
        if (datosAdicionalesDeudaBean != null) {
            datosAdicionalesDeudaBean2 = datosAdicionalesDeudaBean;
        } else {
            datosAdicionalesDeudaBean2 = new DatosAdicionalesDeudaBean();
        }
        this.datosAdicionales = datosAdicionalesDeudaBean2;
        if (cnsEmpresaValidaciones != null) {
            cnsEmpresaValidaciones2 = cnsEmpresaValidaciones;
        } else {
            cnsEmpresaValidaciones2 = new CnsEmpresaValidaciones();
        }
        this.validaciones = cnsEmpresaValidaciones2;
        this.datAdicionales = str17;
    }

    public DatosDeudaBean(DatosDeudaBean datosDeudaBean) {
        this.empServ = datosDeudaBean.empServ;
        this.empDescr = datosDeudaBean.empDescr;
        this.identificacion = datosDeudaBean.identificacion;
        this.importe = datosDeudaBean.importe;
        this.tipoImporte = datosDeudaBean.tipoImporte;
        this.moneda = datosDeudaBean.moneda;
        this.factura = datosDeudaBean.factura;
        this.vencimiento = datosDeudaBean.vencimiento;
        this.infoAdicional = datosDeudaBean.infoAdicional;
        this.cur = datosDeudaBean.cur;
        this.numVep = datosDeudaBean.numVep;
        this.cuit = datosDeudaBean.cuit;
        this.cuitEmpleador = datosDeudaBean.cuitEmpleador;
        this.periodo = datosDeudaBean.periodo;
        this.anticipoCuota = datosDeudaBean.anticipoCuota;
        this.datosAdic = datosDeudaBean.datosAdic;
        this.datosEmpresa = datosDeudaBean.datosEmpresa != null ? datosDeudaBean.datosEmpresa : new CnsEmpresaDatosEmpresa();
        this.datosAdicionales = datosDeudaBean.datosAdicionales != null ? datosDeudaBean.datosAdicionales : new DatosAdicionalesDeudaBean();
        this.validaciones = datosDeudaBean.validaciones != null ? datosDeudaBean.validaciones : new CnsEmpresaValidaciones();
        this.datAdicionales = datosDeudaBean.datAdicionales;
    }

    protected DatosDeudaBean(Parcel parcel) {
        this.empServ = parcel.readString();
        this.empDescr = parcel.readString();
        this.identificacion = parcel.readString();
        this.tipoImporte = parcel.readString();
        this.importe = parcel.readString();
        this.moneda = parcel.readString();
        this.factura = parcel.readString();
        this.vencimiento = parcel.readString();
        this.infoAdicional = parcel.readString();
        this.cur = parcel.readString();
        this.numVep = parcel.readString();
        this.cuit = parcel.readString();
        this.cuitEmpleador = parcel.readString();
        this.periodo = parcel.readString();
        this.anticipoCuota = parcel.readString();
        this.datosAdic = parcel.readString();
        this.datosEmpresa = (CnsEmpresaDatosEmpresa) parcel.readParcelable(CnsEmpresaDatosEmpresa.class.getClassLoader());
        this.datosAdicionales = (DatosAdicionalesDeudaBean) parcel.readParcelable(DatosAdicionalesDeudaBean.class.getClassLoader());
        this.validaciones = (CnsEmpresaValidaciones) parcel.readParcelable(CnsEmpresaValidaciones.class.getClassLoader());
        this.datAdicionales = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.empServ);
        parcel.writeString(this.empDescr);
        parcel.writeString(this.identificacion);
        parcel.writeString(this.tipoImporte);
        parcel.writeString(this.importe);
        parcel.writeString(this.moneda);
        parcel.writeString(this.factura);
        parcel.writeString(this.vencimiento);
        parcel.writeString(this.infoAdicional);
        parcel.writeString(this.cur);
        parcel.writeString(this.numVep);
        parcel.writeString(this.cuit);
        parcel.writeString(this.cuitEmpleador);
        parcel.writeString(this.periodo);
        parcel.writeString(this.anticipoCuota);
        parcel.writeString(this.datosAdic);
        parcel.writeParcelable(this.datosEmpresa, i);
        parcel.writeParcelable(this.datosAdicionales, i);
        parcel.writeParcelable(this.validaciones, i);
        parcel.writeString(this.datAdicionales);
    }
}
