package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class CategoriasSuperClubBean implements Parcelable {
    public static final Creator<CategoriasSuperClubBean> CREATOR = new Creator<CategoriasSuperClubBean>() {
        public CategoriasSuperClubBean createFromParcel(Parcel parcel) {
            return new CategoriasSuperClubBean(parcel);
        }

        public CategoriasSuperClubBean[] newArray(int i) {
            return new CategoriasSuperClubBean[i];
        }
    };
    @SerializedName("categoria")
    public List<CategoriaSuperClubBean> categoria;

    public int describeContents() {
        return 0;
    }

    public CategoriasSuperClubBean() {
        this.categoria = new ArrayList();
    }

    public CategoriasSuperClubBean(List<CategoriaSuperClubBean> list) {
        this.categoria = list;
    }

    private CategoriasSuperClubBean(Parcel parcel) {
        this.categoria = new ArrayList();
        parcel.readTypedList(this.categoria, CategoriaSuperClubBean.CREATOR);
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.categoria);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof CategoriasSuperClubBean)) {
            return false;
        }
        return this.categoria.equals(((CategoriasSuperClubBean) obj).categoria);
    }
}
