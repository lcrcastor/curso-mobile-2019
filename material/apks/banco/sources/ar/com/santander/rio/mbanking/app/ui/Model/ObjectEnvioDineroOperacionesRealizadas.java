package ar.com.santander.rio.mbanking.app.ui.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ObjectEnvioDineroOperacionesRealizadas implements Parcelable {
    public static final Creator<ObjectEnvioDineroOperacionesRealizadas> CREATOR = new Creator<ObjectEnvioDineroOperacionesRealizadas>() {
        /* renamed from: a */
        public ObjectEnvioDineroOperacionesRealizadas createFromParcel(Parcel parcel) {
            ObjectEnvioDineroOperacionesRealizadas objectEnvioDineroOperacionesRealizadas = new ObjectEnvioDineroOperacionesRealizadas(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString());
            return objectEnvioDineroOperacionesRealizadas;
        }

        /* renamed from: a */
        public ObjectEnvioDineroOperacionesRealizadas[] newArray(int i) {
            return new ObjectEnvioDineroOperacionesRealizadas[i];
        }
    };
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;
    private String h;
    private String i;
    private String j;
    private String k;
    private String l;
    private String m;
    private String n;
    private String o;

    public int describeContents() {
        return 0;
    }

    public ObjectEnvioDineroOperacionesRealizadas(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14, String str15) {
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = str4;
        this.e = str5;
        this.f = str6;
        this.g = str7;
        this.h = str8;
        this.i = str9;
        this.j = str10;
        this.k = str11;
        this.l = str12;
        this.m = str13;
        this.n = str14;
        this.o = str15;
    }

    public boolean equals(ObjectEnvioDineroOperacionesRealizadas objectEnvioDineroOperacionesRealizadas) {
        return this.o.equals(objectEnvioDineroOperacionesRealizadas.o);
    }

    public String getCuenta() {
        return this.a;
    }

    public String getTipoCuenta() {
        return this.b;
    }

    public String getSucursalCuenta() {
        return this.c;
    }

    public String getNumeroCuenta() {
        return this.d;
    }

    public String getNombre() {
        return this.e;
    }

    public String getEmail() {
        return this.f;
    }

    public String getTipoDoc() {
        return this.g;
    }

    public String getDocumento() {
        return this.h;
    }

    public String getImporte() {
        return this.i;
    }

    public String getFechaAlta() {
        return this.j;
    }

    public String getFechaVencimiento() {
        return this.k;
    }

    public String getEstado() {
        return this.l;
    }

    public String getCodExtraccion() {
        return this.m;
    }

    public String getCodTransaccion() {
        return this.n;
    }

    public String getNroComprobante() {
        return this.o;
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeString(this.c);
        parcel.writeString(this.d);
        parcel.writeString(this.e);
        parcel.writeString(this.f);
        parcel.writeString(this.g);
        parcel.writeString(this.h);
        parcel.writeString(this.i);
        parcel.writeString(this.j);
        parcel.writeString(this.k);
        parcel.writeString(this.l);
        parcel.writeString(this.m);
        parcel.writeString(this.n);
        parcel.writeString(this.o);
    }
}
