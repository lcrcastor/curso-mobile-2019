package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ListaProductosBean implements Parcelable {
    public static final Creator<ListaProductosBean> CREATOR = new Creator<ListaProductosBean>() {
        public ListaProductosBean createFromParcel(Parcel parcel) {
            return new ListaProductosBean(parcel);
        }

        public ListaProductosBean[] newArray(int i) {
            return new ListaProductosBean[i];
        }
    };
    @SerializedName("producto")
    public List<ProductoItem> producto;

    public int describeContents() {
        return 0;
    }

    protected ListaProductosBean(Parcel parcel) {
        this.producto = parcel.createTypedArrayList(ProductoItem.CREATOR);
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.producto);
    }
}
