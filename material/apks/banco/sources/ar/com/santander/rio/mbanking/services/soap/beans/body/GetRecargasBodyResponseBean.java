package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class GetRecargasBodyResponseBean implements Parcelable {
    public static final Creator<GetRecargasBodyResponseBean> CREATOR = new Creator<GetRecargasBodyResponseBean>() {
        public GetRecargasBodyResponseBean createFromParcel(Parcel parcel) {
            return new GetRecargasBodyResponseBean(parcel);
        }

        public GetRecargasBodyResponseBean[] newArray(int i) {
            return new GetRecargasBodyResponseBean[i];
        }
    };
    @SerializedName("msjDesc")
    private String descripcion;
    @SerializedName("listaCuentas")
    private List<CuentaRecargaBean> listaCuenta;
    @SerializedName("listaRecargas")
    private List<RecargaBean> listaRecargas;
    @SerializedName("listaValores")
    private List<ValorRecargaBean> listaValores;
    @SerializedName("sesionUsuario")
    private String sesionUsuario;
    @SerializedName("msjTitulo")
    private String titulo;

    public int describeContents() {
        return 0;
    }

    public GetRecargasBodyResponseBean() {
    }

    protected GetRecargasBodyResponseBean(Parcel parcel) {
        this.titulo = parcel.readString();
        this.descripcion = parcel.readString();
        this.listaRecargas = parcel.createTypedArrayList(RecargaBean.CREATOR);
        this.listaCuenta = parcel.createTypedArrayList(CuentaRecargaBean.CREATOR);
        this.listaValores = parcel.createTypedArrayList(ValorRecargaBean.CREATOR);
        this.sesionUsuario = parcel.readString();
    }

    public List<RecargaBean> getListaRecargas() {
        return this.listaRecargas;
    }

    public void setListaRecargas(List<RecargaBean> list) {
        this.listaRecargas = list;
    }

    public List<CuentaRecargaBean> getListaCuenta() {
        return this.listaCuenta;
    }

    public void setListaCuenta(List<CuentaRecargaBean> list) {
        this.listaCuenta = list;
    }

    public List<ValorRecargaBean> getListaValores() {
        return this.listaValores;
    }

    public void setListaValores(List<ValorRecargaBean> list) {
        this.listaValores = list;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String str) {
        this.titulo = str;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String str) {
        this.descripcion = str;
    }

    public String getSesionUsuario() {
        return this.sesionUsuario;
    }

    public void setSesionUsuario(String str) {
        this.sesionUsuario = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.listaRecargas);
        parcel.writeTypedList(this.listaCuenta);
        parcel.writeTypedList(this.listaValores);
        parcel.writeString(this.sesionUsuario);
        parcel.writeString(this.titulo);
        parcel.writeString(this.descripcion);
    }
}
