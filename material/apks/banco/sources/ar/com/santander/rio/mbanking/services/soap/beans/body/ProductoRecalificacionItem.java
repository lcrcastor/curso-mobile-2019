package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ProductoRecalificacionItem extends ProductoItem implements Parcelable {
    public static final Creator<ProductoRecalificacionItem> CREATOR = new Creator<ProductoRecalificacionItem>() {
        public ProductoRecalificacionItem createFromParcel(Parcel parcel) {
            return new ProductoRecalificacionItem(parcel);
        }

        public ProductoRecalificacionItem[] newArray(int i) {
            return new ProductoRecalificacionItem[i];
        }
    };
    private Integer disponibleProd;
    private Integer limiteActualProd;
    private String limiteAnteriorProducto;
    private int limiteMaximo;
    private int limiteMinimo;
    private ListaVariacionesBean listaVariacion;
    private MensajeLimiteBean mensajeLimite;
    private MensajeRecalificacionBean mensajeRecalificacion;
    private String mostrarSlider;
    private String nuevoDisponibleProd;
    private String nuevoLimiteProducto;
    private int nuevoLimiteProductoAux;
    private int rangoVariacion;
    private String resCod;
    private String resDesc;

    public int describeContents() {
        return 0;
    }

    protected ProductoRecalificacionItem(Parcel parcel) {
        this.limiteActualProd = (Integer) parcel.readValue(Integer.class.getClassLoader());
        this.disponibleProd = (Integer) parcel.readValue(Integer.class.getClassLoader());
        this.mostrarSlider = parcel.readString();
        this.limiteMinimo = parcel.readInt();
        this.limiteMaximo = parcel.readInt();
        this.rangoVariacion = parcel.readInt();
        this.nuevoLimiteProductoAux = parcel.readInt();
        this.limiteAnteriorProducto = parcel.readString();
        this.nuevoLimiteProducto = parcel.readString();
        this.nuevoDisponibleProd = parcel.readString();
        this.resCod = parcel.readString();
        this.resDesc = parcel.readString();
        this.listaVariacion = (ListaVariacionesBean) parcel.readParcelable(ListaVariacionesBean.class.getClassLoader());
        this.mensajeLimite = (MensajeLimiteBean) parcel.readParcelable(MensajeLimiteBean.class.getClassLoader());
        this.mensajeRecalificacion = (MensajeRecalificacionBean) parcel.readParcelable(MensajeRecalificacionBean.class.getClassLoader());
    }

    public Integer getLimiteActualProd() {
        if (this.limiteActualProd != null) {
            return this.limiteActualProd;
        }
        return Integer.valueOf(0);
    }

    public Integer getLimiteActualProdNullOrValue() {
        return this.limiteActualProd;
    }

    public void setLimiteActualProd(int i) {
        this.limiteActualProd = Integer.valueOf(i);
    }

    public int getLimiteMinimo() {
        return this.limiteMinimo;
    }

    public void setLimiteMinimo(int i) {
        this.limiteMinimo = i;
    }

    public int getLimiteMaximo() {
        return this.limiteMaximo;
    }

    public void setLimiteMaximo(int i) {
        this.limiteMaximo = i;
    }

    public int getRangoVariacion() {
        return this.rangoVariacion;
    }

    public void setRangoVariacion(int i) {
        this.rangoVariacion = i;
    }

    public int getNuevoLimiteProductoAux() {
        return this.nuevoLimiteProductoAux;
    }

    public void setNuevoLimiteProductoAux(int i) {
        this.nuevoLimiteProductoAux = i;
    }

    public Integer getDisponibleProd() {
        if (this.disponibleProd != null) {
            return this.disponibleProd;
        }
        return Integer.valueOf(0);
    }

    public Integer getDisponibleProdNullOrValue() {
        return this.disponibleProd;
    }

    public void setDisponibleProd(int i) {
        this.disponibleProd = Integer.valueOf(i);
    }

    public String getMostrarSlider() {
        return this.mostrarSlider;
    }

    public void setMostrarSlider(String str) {
        this.mostrarSlider = str;
    }

    public String getLimiteAnteriorProducto() {
        return this.limiteAnteriorProducto;
    }

    public void setLimiteAnteriorProducto(String str) {
        this.limiteAnteriorProducto = str;
    }

    public String getNuevoLimiteProducto() {
        return this.nuevoLimiteProducto;
    }

    public void setNuevoLimiteProducto(String str) {
        this.nuevoLimiteProducto = str;
    }

    public String getNuevoDisponibleProd() {
        return this.nuevoDisponibleProd;
    }

    public void setNuevoDisponibleProd(String str) {
        this.nuevoDisponibleProd = str;
    }

    public String getResCod() {
        return this.resCod;
    }

    public void setResCod(String str) {
        this.resCod = str;
    }

    public String getResDesc() {
        return this.resDesc;
    }

    public void setResDesc(String str) {
        this.resDesc = str;
    }

    public ListaVariacionesBean getListaVariacion() {
        return this.listaVariacion;
    }

    public void setListaVariacion(ListaVariacionesBean listaVariacionesBean) {
        this.listaVariacion = listaVariacionesBean;
    }

    public MensajeLimiteBean getMensajeLimite() {
        return this.mensajeLimite;
    }

    public void setMensajeLimite(MensajeLimiteBean mensajeLimiteBean) {
        this.mensajeLimite = mensajeLimiteBean;
    }

    public MensajeRecalificacionBean getMensajeRecalificacion() {
        return this.mensajeRecalificacion;
    }

    public void setMensajeRecalificacion(MensajeRecalificacionBean mensajeRecalificacionBean) {
        this.mensajeRecalificacion = mensajeRecalificacionBean;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(this.limiteActualProd);
        parcel.writeValue(this.disponibleProd);
        parcel.writeString(this.mostrarSlider);
        parcel.writeInt(this.limiteMinimo);
        parcel.writeInt(this.limiteMaximo);
        parcel.writeInt(this.rangoVariacion);
        parcel.writeInt(this.nuevoLimiteProductoAux);
        parcel.writeString(this.limiteAnteriorProducto);
        parcel.writeString(this.nuevoLimiteProducto);
        parcel.writeString(this.nuevoDisponibleProd);
        parcel.writeString(this.resCod);
        parcel.writeString(this.resDesc);
        parcel.writeParcelable(this.listaVariacion, i);
        parcel.writeParcelable(this.mensajeLimite, i);
        parcel.writeParcelable(this.mensajeRecalificacion, i);
    }
}
