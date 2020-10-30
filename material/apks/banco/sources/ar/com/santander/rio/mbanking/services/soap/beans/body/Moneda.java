package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class Moneda implements Parcelable {
    public static final List<String> ORDER_ID_CATEGORIA_TITULOS_VALORES = new ArrayList(Arrays.asList(new String[]{"PRV", "PUB", "ACC", "CDR"}));
    public final Creator<Moneda> CREATOR = new Creator<Moneda>() {
        public Moneda createFromParcel(Parcel parcel) {
            return new Moneda(parcel);
        }

        public Moneda[] newArray(int i) {
            return new Moneda[i];
        }
    };
    @SerializedName("categoriaTitulos")
    private List<CategoriaTitulos> categoriaTitulosList;
    @SerializedName("custodias")
    private List<Custodia> custodiasList;

    public int describeContents() {
        return 0;
    }

    protected Moneda(Parcel parcel) {
        this.categoriaTitulosList = parcel.createTypedArrayList(CategoriaTitulos.CREATOR);
        this.custodiasList = parcel.createTypedArrayList(Custodia.CREATOR);
    }

    public List<CategoriaTitulos> getCategoriaTitulosList() {
        return this.categoriaTitulosList;
    }

    public void setCategoriaTitulosList(List<CategoriaTitulos> list) {
        this.categoriaTitulosList = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.categoriaTitulosList);
        parcel.writeTypedList(this.custodiasList);
    }

    public LinkedHashMap<String, List<Titulo>> getTitulos() {
        LinkedHashMap<String, List<Titulo>> linkedHashMap = new LinkedHashMap<>();
        try {
            for (String str : ORDER_ID_CATEGORIA_TITULOS_VALORES) {
                for (int i = 0; i < this.categoriaTitulosList.size(); i++) {
                    if (str.equalsIgnoreCase(((CategoriaTitulos) this.categoriaTitulosList.get(i)).getIdCategoria())) {
                        linkedHashMap.put(str, ((CategoriaTitulos) this.categoriaTitulosList.get(i)).getTitulos().getTituloList());
                    }
                }
            }
        } catch (Exception unused) {
            Log.d("getTitulos", "Error obteniendo titulos valores");
        }
        return linkedHashMap;
    }

    public List<Custodia> getCustodiasList() {
        return this.custodiasList;
    }

    public void setCustodiasList(List<Custodia> list) {
        this.custodiasList = list;
    }
}
