package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ListaProductosRecalificacionBean implements Parcelable {
    public static final Creator<ListaProductosRecalificacionBean> CREATOR = new Creator<ListaProductosRecalificacionBean>() {
        public ListaProductosRecalificacionBean createFromParcel(Parcel parcel) {
            return new ListaProductosRecalificacionBean(parcel);
        }

        public ListaProductosRecalificacionBean[] newArray(int i) {
            return new ListaProductosRecalificacionBean[i];
        }
    };
    @SerializedName("producto")
    public List<ProductoRecalificacionItem> producto;

    public int describeContents() {
        return 0;
    }

    protected ListaProductosRecalificacionBean(Parcel parcel) {
        this.producto = parcel.createTypedArrayList(ProductoRecalificacionItem.CREATOR);
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.producto);
    }
}
