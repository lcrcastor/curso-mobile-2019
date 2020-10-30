package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class CuponSuperClubBean implements Parcelable {
    public static final Creator<CuponSuperClubBean> CREATOR = new Creator<CuponSuperClubBean>() {
        public CuponSuperClubBean createFromParcel(Parcel parcel) {
            return new CuponSuperClubBean(parcel);
        }

        public CuponSuperClubBean[] newArray(int i) {
            return new CuponSuperClubBean[i];
        }
    };
    @SerializedName("descripcion")
    public String descripcion;
    @SerializedName("grupo")
    public String grupo;
    @SerializedName("idCupon")
    public String idCupon;
    @SerializedName("idPartner")
    public String idPartner;
    @SerializedName("idPunto")
    public String idPunto;
    @SerializedName("imagenDescuentoMedioDePago")
    public String imagenDescuentoMedioDePago;
    @SerializedName("legales")
    public String legales;
    @SerializedName("medioDePago")
    public String medioDePago;
    @SerializedName("porcentajeDescuento")
    public String porcentajeDescuento;
    @SerializedName("puntos")
    public String puntos;
    @SerializedName("segmento")
    public String segmento;
    @SerializedName("topeDeAhorro")
    public String topeDeAhorro;
    @SerializedName("vigenciaDesde")
    public String vigenciaDesde;
    @SerializedName("vigenciaHasta")
    public String vigenciaHasta;

    public int describeContents() {
        return 0;
    }

    public CuponSuperClubBean() {
    }

    public CuponSuperClubBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14) {
        this.idCupon = str;
        this.grupo = str2;
        this.segmento = str3;
        this.puntos = str4;
        this.idPunto = str5;
        this.idPartner = str6;
        this.medioDePago = str7;
        this.porcentajeDescuento = str8;
        this.imagenDescuentoMedioDePago = str9;
        this.topeDeAhorro = str10;
        this.descripcion = str11;
        this.vigenciaDesde = str12;
        this.vigenciaHasta = str13;
        this.legales = str14;
    }

    private CuponSuperClubBean(Parcel parcel) {
        this.idCupon = parcel.readString();
        this.grupo = parcel.readString();
        this.segmento = parcel.readString();
        this.puntos = parcel.readString();
        this.idPunto = parcel.readString();
        this.idPartner = parcel.readString();
        this.medioDePago = parcel.readString();
        this.porcentajeDescuento = parcel.readString();
        this.imagenDescuentoMedioDePago = parcel.readString();
        this.topeDeAhorro = parcel.readString();
        this.descripcion = parcel.readString();
        this.vigenciaDesde = parcel.readString();
        this.vigenciaHasta = parcel.readString();
        this.legales = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.idCupon);
        parcel.writeString(this.grupo);
        parcel.writeString(this.segmento);
        parcel.writeString(this.puntos);
        parcel.writeString(this.idPunto);
        parcel.writeString(this.idPartner);
        parcel.writeString(this.medioDePago);
        parcel.writeString(this.porcentajeDescuento);
        parcel.writeString(this.imagenDescuentoMedioDePago);
        parcel.writeString(this.topeDeAhorro);
        parcel.writeString(this.descripcion);
        parcel.writeString(this.vigenciaDesde);
        parcel.writeString(this.vigenciaHasta);
        parcel.writeString(this.legales);
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof CuponSuperClubBean)) {
            return false;
        }
        CuponSuperClubBean cuponSuperClubBean = (CuponSuperClubBean) obj;
        if (this.idCupon.equalsIgnoreCase(cuponSuperClubBean.idCupon) && this.grupo.equalsIgnoreCase(cuponSuperClubBean.grupo) && this.segmento.equalsIgnoreCase(cuponSuperClubBean.segmento) && this.puntos.equalsIgnoreCase(cuponSuperClubBean.puntos) && this.idPunto.equalsIgnoreCase(cuponSuperClubBean.idPunto) && this.idPartner.equalsIgnoreCase(cuponSuperClubBean.idPartner) && this.medioDePago.equalsIgnoreCase(cuponSuperClubBean.medioDePago) && this.porcentajeDescuento.equalsIgnoreCase(cuponSuperClubBean.porcentajeDescuento) && this.imagenDescuentoMedioDePago.equalsIgnoreCase(cuponSuperClubBean.imagenDescuentoMedioDePago) && this.topeDeAhorro.equalsIgnoreCase(cuponSuperClubBean.topeDeAhorro) && this.descripcion.equalsIgnoreCase(cuponSuperClubBean.descripcion) && this.vigenciaDesde.equalsIgnoreCase(cuponSuperClubBean.vigenciaDesde) && this.vigenciaHasta.equalsIgnoreCase(cuponSuperClubBean.vigenciaHasta) && this.legales.equalsIgnoreCase(cuponSuperClubBean.legales)) {
            z = true;
        }
        return z;
    }
}
