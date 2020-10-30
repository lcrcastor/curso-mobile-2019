package ar.com.santander.rio.mbanking.app.ui.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ObjectDestinatarios implements Parcelable {
    public static final Creator<ObjectDestinatarios> CREATOR = new Creator<ObjectDestinatarios>() {
        /* renamed from: a */
        public ObjectDestinatarios createFromParcel(Parcel parcel) {
            return new ObjectDestinatarios(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString());
        }

        /* renamed from: a */
        public ObjectDestinatarios[] newArray(int i) {
            return new ObjectDestinatarios[i];
        }
    };
    private String a;
    private String b;
    private String c;
    private String d;
    private Boolean e = Boolean.valueOf(false);
    private Boolean f = Boolean.valueOf(false);

    public int describeContents() {
        return 0;
    }

    public ObjectDestinatarios(String str, String str2, String str3, String str4) {
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = str4;
    }

    public boolean equals(ObjectDestinatarios objectDestinatarios) {
        return this.a.equals(objectDestinatarios.a) && this.c.equals(objectDestinatarios.c) && this.d.equals(objectDestinatarios.d);
    }

    public String getNombre() {
        return this.a;
    }

    public String getTipoDoc() {
        return this.b;
    }

    public String getDocumento() {
        return this.c;
    }

    public String getMail() {
        return this.d;
    }

    public Boolean getSelected() {
        return this.e;
    }

    public void setSelected(Boolean bool) {
        this.e = bool;
    }

    public Boolean getSwiped() {
        return this.f;
    }

    public void setSwiped(Boolean bool) {
        this.f = bool;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeString(this.c);
        parcel.writeString(this.d);
    }
}
