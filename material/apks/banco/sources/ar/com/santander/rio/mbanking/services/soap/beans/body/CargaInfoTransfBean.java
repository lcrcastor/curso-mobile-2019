package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class CargaInfoTransfBean implements Parcelable {
    public static final Creator<CargaInfoTransfBean> CREATOR = new Creator<CargaInfoTransfBean>() {
        public CargaInfoTransfBean createFromParcel(Parcel parcel) {
            return new CargaInfoTransfBean(parcel);
        }

        public CargaInfoTransfBean[] newArray(int i) {
            return new CargaInfoTransfBean[i];
        }
    };
    @SerializedName("listasAgendados")
    private AgendadosBean agendadosBean;
    @SerializedName("listaCuentasPropias")
    private CuentasPropiasBean cuentasPropiasBean;
    @SerializedName("fechaEjecucion")
    private String fechaEjecucion;
    @SerializedName("leyenda")
    private LeyendasBean leyendasBean;

    public int describeContents() {
        return 0;
    }

    public CargaInfoTransfBean(String str, CuentasPropiasBean cuentasPropiasBean2, AgendadosBean agendadosBean2, LeyendasBean leyendasBean2) {
        this.fechaEjecucion = str;
        this.cuentasPropiasBean = cuentasPropiasBean2;
        this.agendadosBean = agendadosBean2;
        this.leyendasBean = leyendasBean2;
    }

    protected CargaInfoTransfBean(Parcel parcel) {
        this.fechaEjecucion = parcel.readString();
        this.cuentasPropiasBean = (CuentasPropiasBean) parcel.readParcelable(CuentasPropiasBean.class.getClassLoader());
        this.agendadosBean = (AgendadosBean) parcel.readParcelable(AgendadosBean.class.getClassLoader());
        this.leyendasBean = (LeyendasBean) parcel.readParcelable(LeyendasBean.class.getClassLoader());
    }

    public String getFechaEjecucion() {
        return this.fechaEjecucion;
    }

    public CuentasPropiasBean getCuentasPropiasBean() {
        return this.cuentasPropiasBean;
    }

    public AgendadosBean getAgendadosBean() {
        return this.agendadosBean;
    }

    public LeyendasBean getLeyendasBean() {
        return this.leyendasBean;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.fechaEjecucion);
        parcel.writeParcelable(this.cuentasPropiasBean, i);
        parcel.writeParcelable(this.agendadosBean, i);
        parcel.writeParcelable(this.leyendasBean, i);
    }
}
