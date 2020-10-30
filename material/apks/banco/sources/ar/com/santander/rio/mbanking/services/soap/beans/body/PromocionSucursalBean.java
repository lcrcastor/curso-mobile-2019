package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import ar.com.santander.rio.mbanking.components.asymmetricgridview.model.AsymmetricItem;

public class PromocionSucursalBean implements AsymmetricItem {
    public static final Creator<PromocionSucursalBean> CREATOR = new Creator<PromocionSucursalBean>() {
        public PromocionSucursalBean createFromParcel(Parcel parcel) {
            return new PromocionSucursalBean(parcel);
        }

        public PromocionSucursalBean[] newArray(int i) {
            return new PromocionSucursalBean[i];
        }
    };
    public String descHtml;
    public String descHtml3;
    public String descripcion;
    public String descripcion2;
    public String descripcion3;
    public String destaque;
    public String destaqueImagen;
    public String direccion;

    /* renamed from: id reason: collision with root package name */
    public String f267id;
    public String latitud;
    public String legales;
    public String longitud;
    public String nombre;
    public int position;
    public String promoURL;
    public String socialStyle;
    public String thumbnailBig;
    public String thumbnailSmall;
    public String tipo;
    public String valor;

    public int describeContents() {
        return 0;
    }

    public int getRowSpan() {
        return 1;
    }

    public PromocionSucursalBean() {
    }

    public PromocionSucursalBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14, String str15, String str16) {
        this.f267id = str;
        this.tipo = str2;
        this.nombre = str3;
        this.descHtml3 = str4;
        this.descripcion = str5;
        this.descripcion2 = str6;
        this.descripcion3 = str7;
        this.destaque = str8;
        this.valor = str9;
        this.direccion = str10;
        this.latitud = str11;
        this.longitud = str12;
        this.thumbnailSmall = str13;
        this.thumbnailBig = str14;
        this.socialStyle = str15;
        this.promoURL = str16;
    }

    protected PromocionSucursalBean(Parcel parcel) {
        this.position = parcel.readInt();
        this.f267id = parcel.readString();
        this.tipo = parcel.readString();
        this.nombre = parcel.readString();
        this.descHtml3 = parcel.readString();
        this.descripcion = parcel.readString();
        this.descripcion2 = parcel.readString();
        this.descripcion3 = parcel.readString();
        this.destaque = parcel.readString();
        this.valor = parcel.readString();
        this.direccion = parcel.readString();
        this.latitud = parcel.readString();
        this.longitud = parcel.readString();
        this.thumbnailSmall = parcel.readString();
        this.thumbnailBig = parcel.readString();
        this.socialStyle = parcel.readString();
        this.promoURL = parcel.readString();
        this.destaqueImagen = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.position);
        parcel.writeString(this.f267id);
        parcel.writeString(this.tipo);
        parcel.writeString(this.nombre);
        parcel.writeString(this.descHtml3);
        parcel.writeString(this.descripcion);
        parcel.writeString(this.descripcion2);
        parcel.writeString(this.descripcion3);
        parcel.writeString(this.destaque);
        parcel.writeString(this.valor);
        parcel.writeString(this.direccion);
        parcel.writeString(this.latitud);
        parcel.writeString(this.longitud);
        parcel.writeString(this.thumbnailSmall);
        parcel.writeString(this.thumbnailBig);
        parcel.writeString(this.socialStyle);
        parcel.writeString(this.promoURL);
        parcel.writeString(this.destaqueImagen);
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getColumnSpan() {
        /*
            r4 = this;
            java.lang.String r0 = r4.destaque
            int r1 = r0.hashCode()
            r2 = 1
            r3 = 2
            switch(r1) {
                case 48: goto L_0x0020;
                case 49: goto L_0x0016;
                case 50: goto L_0x000c;
                default: goto L_0x000b;
            }
        L_0x000b:
            goto L_0x002a
        L_0x000c:
            java.lang.String r1 = "2"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x002a
            r0 = 2
            goto L_0x002b
        L_0x0016:
            java.lang.String r1 = "1"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x002a
            r0 = 1
            goto L_0x002b
        L_0x0020:
            java.lang.String r1 = "0"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x002a
            r0 = 0
            goto L_0x002b
        L_0x002a:
            r0 = -1
        L_0x002b:
            switch(r0) {
                case 0: goto L_0x0031;
                case 1: goto L_0x0030;
                case 2: goto L_0x002f;
                default: goto L_0x002e;
            }
        L_0x002e:
            return r3
        L_0x002f:
            return r3
        L_0x0030:
            return r2
        L_0x0031:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.services.soap.beans.body.PromocionSucursalBean.getColumnSpan():int");
    }

    public String getDestaqueImagen() {
        return this.destaqueImagen;
    }

    public void setDestaqueImagen(String str) {
        this.destaqueImagen = str;
    }
}
