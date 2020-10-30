package ar.com.santander.rio.mbanking.components.asymmetricgridview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.imageloader.ImageLoader;
import ar.com.santander.rio.mbanking.app.commons.imageloader.ImageLoaderFactory;
import ar.com.santander.rio.mbanking.components.asymmetricgridview.widget.AsymmetricGridView;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PromocionSucursalBean;
import com.google.android.gms.maps.model.LatLng;
import java.util.List;

public abstract class AsymmetricListAdapter extends AsymmetricInfiniteScrollAdapter<PromocionSucursalBean> {
    private ImageLoader a;
    private final LayoutInflater b;
    private final SessionManager c;

    public abstract String getDistanceMarkers(LatLng latLng);

    public AsymmetricListAdapter(Context context, AsymmetricGridView asymmetricGridView, List<PromocionSucursalBean> list, SessionManager sessionManager) {
        super(context, asymmetricGridView, list);
        this.a = ImageLoaderFactory.getImageLoader(context);
        this.c = sessionManager;
        this.b = LayoutInflater.from(context);
    }

    public PromocionSucursalBean getRealItem(int i) {
        return (PromocionSucursalBean) getItem(i);
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View getRealView(android.view.LayoutInflater r12, int r13, android.view.View r14, android.view.ViewGroup r15) {
        /*
            r11 = this;
            ar.com.santander.rio.mbanking.components.asymmetricgridview.model.AsymmetricItem r12 = r11.getItem(r13)
            ar.com.santander.rio.mbanking.services.soap.beans.body.PromocionSucursalBean r12 = (ar.com.santander.rio.mbanking.services.soap.beans.body.PromocionSucursalBean) r12
            r13 = 0
            ar.com.santander.rio.mbanking.managers.session.SessionManager r14 = r11.c     // Catch:{ Exception -> 0x0016 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.GetClasificadoresBodyResponseBean r14 = r14.getGetClasificadores()     // Catch:{ Exception -> 0x0016 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.ConfigsBean r14 = r14.configuraciones     // Catch:{ Exception -> 0x0016 }
            java.lang.String r0 = "PromocionesScreen.Destaque.Enable"
            ar.com.santander.rio.mbanking.services.soap.beans.body.ConfigBean r14 = r14.getConfigBeanForKey(r0)     // Catch:{ Exception -> 0x0016 }
            goto L_0x0017
        L_0x0016:
            r14 = r13
        L_0x0017:
            r0 = 8
            r1 = 2131366271(0x7f0a117f, float:1.835243E38)
            r2 = 2131366272(0x7f0a1180, float:1.8352433E38)
            r3 = 2131366274(0x7f0a1182, float:1.8352437E38)
            r4 = 2131366275(0x7f0a1183, float:1.8352439E38)
            r5 = 2131366287(0x7f0a118f, float:1.8352463E38)
            r6 = 2131493164(0x7f0c012c, float:1.86098E38)
            r7 = 2131364809(0x7f0a0bc9, float:1.8349466E38)
            r8 = 0
            if (r14 == 0) goto L_0x0123
            java.lang.String r14 = r14.value
            boolean r14 = java.lang.Boolean.parseBoolean(r14)
            if (r14 == 0) goto L_0x0123
            java.lang.String r14 = r12.destaque
            r9 = -1
            int r10 = r14.hashCode()
            switch(r10) {
                case 48: goto L_0x0058;
                case 49: goto L_0x004e;
                case 50: goto L_0x0044;
                default: goto L_0x0043;
            }
        L_0x0043:
            goto L_0x0062
        L_0x0044:
            java.lang.String r10 = "2"
            boolean r14 = r14.equals(r10)
            if (r14 == 0) goto L_0x0062
            r14 = 2
            goto L_0x0063
        L_0x004e:
            java.lang.String r10 = "1"
            boolean r14 = r14.equals(r10)
            if (r14 == 0) goto L_0x0062
            r14 = 1
            goto L_0x0063
        L_0x0058:
            java.lang.String r10 = "0"
            boolean r14 = r14.equals(r10)
            if (r14 == 0) goto L_0x0062
            r14 = 0
            goto L_0x0063
        L_0x0062:
            r14 = -1
        L_0x0063:
            r9 = 2131493163(0x7f0c012b, float:1.8609798E38)
            switch(r14) {
                case 0: goto L_0x0093;
                case 1: goto L_0x007f;
                case 2: goto L_0x006b;
                default: goto L_0x0069;
            }
        L_0x0069:
            goto L_0x0122
        L_0x006b:
            android.view.LayoutInflater r13 = r11.b
            android.view.View r13 = r13.inflate(r9, r15, r8)
            android.view.View r14 = r13.findViewById(r7)
            android.widget.ImageView r14 = (android.widget.ImageView) r14
            ar.com.santander.rio.mbanking.app.commons.imageloader.ImageLoader r15 = r11.a
            java.lang.String r12 = r12.destaqueImagen
            r15.loadImage(r12, r14)
            return r13
        L_0x007f:
            android.view.LayoutInflater r13 = r11.b
            android.view.View r13 = r13.inflate(r9, r15, r8)
            android.view.View r14 = r13.findViewById(r7)
            android.widget.ImageView r14 = (android.widget.ImageView) r14
            ar.com.santander.rio.mbanking.app.commons.imageloader.ImageLoader r15 = r11.a
            java.lang.String r12 = r12.destaqueImagen
            r15.loadImage(r12, r14)
            return r13
        L_0x0093:
            android.view.LayoutInflater r13 = r11.b
            android.view.View r13 = r13.inflate(r6, r15, r8)
            android.view.View r14 = r13.findViewById(r5)
            android.widget.TextView r14 = (android.widget.TextView) r14
            android.view.View r15 = r13.findViewById(r4)
            android.widget.TextView r15 = (android.widget.TextView) r15
            android.view.View r3 = r13.findViewById(r3)
            android.widget.TextView r3 = (android.widget.TextView) r3
            android.view.View r2 = r13.findViewById(r2)
            android.widget.TextView r2 = (android.widget.TextView) r2
            android.view.View r1 = r13.findViewById(r1)
            android.widget.TextView r1 = (android.widget.TextView) r1
            android.view.View r4 = r13.findViewById(r7)
            android.widget.ImageView r4 = (android.widget.ImageView) r4
            ar.com.santander.rio.mbanking.app.commons.imageloader.ImageLoader r5 = r11.a
            java.lang.String r6 = r12.thumbnailSmall
            r5.loadImage(r6, r4)
            java.lang.String r4 = r12.nombre
            android.text.Spanned r4 = android.text.Html.fromHtml(r4)
            r14.setText(r4)
            com.google.android.gms.maps.model.LatLng r14 = new com.google.android.gms.maps.model.LatLng
            java.lang.String r4 = r12.latitud
            double r4 = java.lang.Double.parseDouble(r4)
            java.lang.String r6 = r12.longitud
            double r6 = java.lang.Double.parseDouble(r6)
            r14.<init>(r4, r6)
            java.lang.String r14 = r11.getDistanceMarkers(r14)
            if (r14 == 0) goto L_0x00e7
            r15.setText(r14)
        L_0x00e7:
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CDistanceAcc r14 = new ar.com.santander.rio.mbanking.app.commons.accessibilty.CDistanceAcc
            r14.<init>()
            java.lang.CharSequence r4 = r15.getText()
            java.lang.String r4 = r4.toString()
            r14.applyFilter(r15, r4)
            java.lang.String r14 = r12.direccion
            android.text.Spanned r14 = android.text.Html.fromHtml(r14)
            r3.setText(r14)
            java.lang.String r14 = r12.descripcion
            android.text.Spanned r14 = android.text.Html.fromHtml(r14)
            r2.setText(r14)
            java.lang.String r14 = r12.descripcion3
            boolean r14 = android.text.TextUtils.isEmpty(r14)
            if (r14 == 0) goto L_0x0115
            r1.setVisibility(r0)
            goto L_0x0121
        L_0x0115:
            r1.setVisibility(r8)
            java.lang.String r12 = r12.descripcion3
            android.text.Spanned r12 = android.text.Html.fromHtml(r12)
            r1.setText(r12)
        L_0x0121:
            return r13
        L_0x0122:
            return r13
        L_0x0123:
            android.view.LayoutInflater r13 = r11.b
            android.view.View r13 = r13.inflate(r6, r15, r8)
            android.view.View r14 = r13.findViewById(r5)
            android.widget.TextView r14 = (android.widget.TextView) r14
            android.view.View r15 = r13.findViewById(r4)
            android.widget.TextView r15 = (android.widget.TextView) r15
            android.view.View r3 = r13.findViewById(r3)
            android.widget.TextView r3 = (android.widget.TextView) r3
            android.view.View r2 = r13.findViewById(r2)
            android.widget.TextView r2 = (android.widget.TextView) r2
            android.view.View r1 = r13.findViewById(r1)
            android.widget.TextView r1 = (android.widget.TextView) r1
            android.view.View r4 = r13.findViewById(r7)
            android.widget.ImageView r4 = (android.widget.ImageView) r4
            ar.com.santander.rio.mbanking.app.commons.imageloader.ImageLoader r5 = r11.a
            java.lang.String r6 = r12.thumbnailSmall
            r5.loadImage(r6, r4)
            java.lang.String r4 = r12.nombre
            android.text.Spanned r4 = android.text.Html.fromHtml(r4)
            r14.setText(r4)
            com.google.android.gms.maps.model.LatLng r14 = new com.google.android.gms.maps.model.LatLng
            java.lang.String r4 = r12.latitud
            double r4 = java.lang.Double.parseDouble(r4)
            java.lang.String r6 = r12.longitud
            double r6 = java.lang.Double.parseDouble(r6)
            r14.<init>(r4, r6)
            java.lang.String r14 = r11.getDistanceMarkers(r14)
            if (r14 == 0) goto L_0x0177
            r15.setText(r14)
        L_0x0177:
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CDistanceAcc r14 = new ar.com.santander.rio.mbanking.app.commons.accessibilty.CDistanceAcc
            r14.<init>()
            java.lang.CharSequence r4 = r15.getText()
            java.lang.String r4 = r4.toString()
            r14.applyFilter(r15, r4)
            java.lang.String r14 = r12.direccion
            android.text.Spanned r14 = android.text.Html.fromHtml(r14)
            r3.setText(r14)
            java.lang.String r14 = r12.descripcion
            android.text.Spanned r14 = android.text.Html.fromHtml(r14)
            r2.setText(r14)
            java.lang.String r14 = r12.descripcion3
            boolean r14 = android.text.TextUtils.isEmpty(r14)
            if (r14 == 0) goto L_0x01a5
            r1.setVisibility(r0)
            goto L_0x01b1
        L_0x01a5:
            r1.setVisibility(r8)
            java.lang.String r12 = r12.descripcion3
            android.text.Spanned r12 = android.text.Html.fromHtml(r12)
            r1.setText(r12)
        L_0x01b1:
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.components.asymmetricgridview.AsymmetricListAdapter.getRealView(android.view.LayoutInflater, int, android.view.View, android.view.ViewGroup):android.view.View");
    }

    public View getLoadingView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        return layoutInflater.inflate(R.layout.list_loading, null);
    }

    public View getActualView(int i, View view, ViewGroup viewGroup) {
        return getRealView(this.b, i, view, viewGroup);
    }
}
