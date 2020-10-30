package ar.com.santander.rio.mbanking.services.model.creditos;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import ar.com.santander.rio.mbanking.app.commons.CCreditos;
import com.google.common.collect.ComparisonChain;
import com.google.gson.annotations.SerializedName;
import java.util.Comparator;
import java.util.List;

public class PrestamosPermitidos implements Parcelable {
    public static final Creator<PrestamosPermitidos> CREATOR = new Creator<PrestamosPermitidos>() {
        public PrestamosPermitidos createFromParcel(Parcel parcel) {
            return new PrestamosPermitidos(parcel);
        }

        public PrestamosPermitidos[] newArray(int i) {
            return new PrestamosPermitidos[i];
        }
    };
    @SerializedName("datosPrestPerm")
    List<DatosPrestamoPermitido> listaDatosPrestamoPermitido;

    public static class ComparatorPrestamos implements Comparator<DatosPrestamoPermitido> {
        public int compare(DatosPrestamoPermitido datosPrestamoPermitido, DatosPrestamoPermitido datosPrestamoPermitido2) {
            String formatTipoTasa = CCreditos.formatTipoTasa(datosPrestamoPermitido.getTipoTasa(), datosPrestamoPermitido.getIndicadorUVA());
            String formatTipoTasa2 = CCreditos.formatTipoTasa(datosPrestamoPermitido2.getTipoTasa(), datosPrestamoPermitido2.getIndicadorUVA());
            if (formatTipoTasa == null || formatTipoTasa2 == null) {
                return ComparisonChain.start().compare((Comparable<?>) datosPrestamoPermitido.getMinCantCuotas(), (Comparable<?>) datosPrestamoPermitido2.getMinCantCuotas()).compare((Comparable<?>) datosPrestamoPermitido.getMaxCantCuotas(), (Comparable<?>) datosPrestamoPermitido2.getMaxCantCuotas()).result();
            }
            return ComparisonChain.start().compare((Comparable<?>) formatTipoTasa, (Comparable<?>) formatTipoTasa2).compare((Comparable<?>) datosPrestamoPermitido.getMinCantCuotas(), (Comparable<?>) datosPrestamoPermitido2.getMinCantCuotas()).compare((Comparable<?>) datosPrestamoPermitido.getMaxCantCuotas(), (Comparable<?>) datosPrestamoPermitido2.getMaxCantCuotas()).result();
        }
    }

    public int describeContents() {
        return 0;
    }

    public PrestamosPermitidos(Parcel parcel) {
        this.listaDatosPrestamoPermitido = parcel.createTypedArrayList(DatosPrestamoPermitido.CREATOR);
    }

    public PrestamosPermitidos() {
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.listaDatosPrestamoPermitido);
    }

    public List<DatosPrestamoPermitido> getListaDatosPrestamoPermitido() {
        return this.listaDatosPrestamoPermitido;
    }

    public void setListaDatosPrestamoPermitido(List<DatosPrestamoPermitido> list) {
        this.listaDatosPrestamoPermitido = list;
    }
}
