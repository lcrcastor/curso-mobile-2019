package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class Prestamos {
    @SerializedName("prestamos")
    List<Prestamo> prestamos;

    public Prestamos() {
        this.prestamos = new ArrayList();
    }

    public Prestamos(List<Prestamo> list) {
        this.prestamos = list;
    }

    public List<Prestamo> getPrestamos() {
        return this.prestamos;
    }

    public void setPrestamos(List<Prestamo> list) {
        this.prestamos = list;
    }

    public void add(Prestamo prestamo) {
        if (this.prestamos != null) {
            this.prestamos.add(prestamo);
        }
    }
}
