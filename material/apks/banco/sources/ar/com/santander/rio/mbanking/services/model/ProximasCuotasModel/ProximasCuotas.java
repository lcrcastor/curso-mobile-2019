package ar.com.santander.rio.mbanking.services.model.ProximasCuotasModel;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ProximasCuotas implements Parcelable {
    public static final Creator<ProximasCuotas> CREATOR = new Creator<ProximasCuotas>() {
        public ProximasCuotas createFromParcel(Parcel parcel) {
            return new ProximasCuotas(parcel);
        }

        public ProximasCuotas[] newArray(int i) {
            return new ProximasCuotas[i];
        }
    };
    @SerializedName("datosCuota")
    @Expose
    public List<DatosCuota> datosCuota = null;

    public int describeContents() {
        return 0;
    }

    public ProximasCuotas(Parcel parcel) {
        this.datosCuota = parcel.createTypedArrayList(DatosCuota.CREATOR);
    }

    public ProximasCuotas() {
    }

    public List<DatosCuota> getDatosCuota() {
        return this.datosCuota;
    }

    public void setDatosCuota(List<DatosCuota> list) {
        this.datosCuota = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.datosCuota);
    }
}
