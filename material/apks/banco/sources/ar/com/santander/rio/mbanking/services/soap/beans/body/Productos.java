package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Productos implements Parcelable {
    public static final Creator<Productos> CREATOR = new Creator<Productos>() {
        public Productos createFromParcel(Parcel parcel) {
            return new Productos(parcel);
        }

        public Productos[] newArray(int i) {
            return new Productos[i];
        }
    };
    @SerializedName("producto")
    private List<Producto> productoList;

    public int describeContents() {
        return 0;
    }

    protected Productos(Parcel parcel) {
        this.productoList = parcel.createTypedArrayList(Producto.CREATOR);
    }

    public List<Producto> getProductoList() {
        return this.productoList;
    }

    public void setProductoList(List<Producto> list) {
        this.productoList = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.productoList);
    }
}
