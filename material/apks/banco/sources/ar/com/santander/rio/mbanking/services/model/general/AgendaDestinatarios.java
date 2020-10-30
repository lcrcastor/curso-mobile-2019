package ar.com.santander.rio.mbanking.services.model.general;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;

public class AgendaDestinatarios implements Parcelable {
    public static final Creator<AgendaDestinatarios> CREATOR = new Creator<AgendaDestinatarios>() {
        public AgendaDestinatarios createFromParcel(Parcel parcel) {
            return new AgendaDestinatarios(parcel);
        }

        public AgendaDestinatarios[] newArray(int i) {
            return new AgendaDestinatarios[i];
        }
    };
    private String alias;
    private String banco;
    private String beneficiario;
    private boolean checked;
    private String descripcion;
    private String email;
    private String info1;
    private String info2;
    private String nombre;
    private int position;
    private Boolean swiped;
    private String tipoDescripcion;
    private String tipoDestino;
    private String titulo;

    public int describeContents() {
        return 0;
    }

    public AgendaDestinatarios(String str, String str2, String str3, String str4, Boolean bool, String str5, String str6, String str7, String str8, String str9, String str10, String str11) {
        this.titulo = str;
        this.nombre = str2;
        this.info1 = str3;
        this.info2 = str4;
        this.checked = bool.booleanValue();
        this.position = 0;
        this.swiped = Boolean.valueOf(false);
        if (TextUtils.isEmpty(str5)) {
            str5 = "";
        }
        this.tipoDescripcion = str5;
        if (TextUtils.isEmpty(str6)) {
            str6 = "";
        }
        this.email = str6;
        if (TextUtils.isEmpty(str7)) {
            str7 = "";
        }
        this.tipoDestino = str7;
        if (TextUtils.isEmpty(str8)) {
            str8 = "";
        }
        this.descripcion = str8;
        if (TextUtils.isEmpty(str9)) {
            str9 = "";
        }
        this.banco = str9;
        if (TextUtils.isEmpty(str10)) {
            str10 = "";
        }
        this.beneficiario = str10;
        if (TextUtils.isEmpty(str11)) {
            str11 = "";
        }
        this.alias = str11;
    }

    public AgendaDestinatarios(Parcel parcel) {
        this.titulo = parcel.readString();
        this.nombre = parcel.readString();
        this.info1 = parcel.readString();
        this.info2 = parcel.readString();
        boolean z = false;
        this.checked = parcel.readInt() == 1;
        this.position = parcel.readInt();
        if (parcel.readInt() == 1) {
            z = true;
        }
        this.swiped = Boolean.valueOf(z);
        this.tipoDescripcion = parcel.readString();
        this.email = parcel.readString();
        this.tipoDestino = parcel.readString();
        this.descripcion = parcel.readString();
        this.banco = parcel.readString();
        this.beneficiario = parcel.readString();
        this.alias = parcel.readString();
    }

    public String getBanco() {
        return this.banco;
    }

    public void setBanco(String str) {
        this.banco = str;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String str) {
        this.descripcion = str;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String str) {
        this.titulo = str;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String str) {
        this.nombre = str;
    }

    public String getInfo1() {
        return this.info1;
    }

    public void setInfo1(String str) {
        this.info1 = str;
    }

    public String getInfo2() {
        return this.info2;
    }

    public void setInfo2(String str) {
        this.info2 = str;
    }

    public boolean getChecked() {
        return this.checked;
    }

    public void setChecked(boolean z) {
        this.checked = z;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String str) {
        this.email = str;
    }

    public String getTipoDestino() {
        return this.tipoDestino;
    }

    public void setTipoDestino(String str) {
        this.tipoDestino = str;
    }

    public String getBeneficiario() {
        return this.beneficiario;
    }

    public void setBeneficiario(String str) {
        this.beneficiario = str;
    }

    public String getTipoDescripcion() {
        return this.tipoDescripcion;
    }

    public void setTipoDescripcion(String str) {
        this.tipoDescripcion = str;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String str) {
        this.alias = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getTitulo());
        parcel.writeString(getNombre());
        parcel.writeString(getInfo1());
        parcel.writeString(getInfo2());
        parcel.writeInt(getChecked() ? 1 : 0);
        parcel.writeInt(getPosition());
        parcel.writeInt(getSwiped().booleanValue() ? 1 : 0);
        parcel.writeString(getTipoDescripcion());
        parcel.writeString(getEmail());
        parcel.writeString(getTipoDestino());
        parcel.writeString(getDescripcion());
        parcel.writeString(getBanco());
        parcel.writeString(getBeneficiario());
        parcel.writeString(getAlias());
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int i) {
        this.position = i;
    }

    public Boolean getSwiped() {
        return this.swiped;
    }

    public void setSwiped(Boolean bool) {
        this.swiped = bool;
    }
}
