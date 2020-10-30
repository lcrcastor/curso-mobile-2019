package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.AccessibilityDelegate;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ProductoRecalificacionItem;
import ar.com.santander.rio.mbanking.view.AmountView;
import java.util.List;

public class RecalificacionComprobanteAdapter extends Adapter<ViewHolder> {
    /* access modifiers changed from: private */
    public final String a;
    /* access modifiers changed from: private */
    public final String b;
    /* access modifiers changed from: private */
    public Context c;
    private List<ProductoRecalificacionItem> d;
    private List<Leyenda> e;
    private String f;
    private String g;
    private String h;
    /* access modifiers changed from: private */
    public AccessibilityDelegate i = new AccessibilityDelegate() {
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            accessibilityNodeInfo.setText(view.getContentDescription());
        }
    };

    public class DataHolder extends ViewHolder {
        public LinearLayout llFechaSolicitud;
        public LinearLayout llLimiteSinAsignar;
        public LinearLayout llNroComprobante;
        public TextView tvFechaSolicitudLabel;
        public TextView tvFechaSolicitudValue;
        public TextView tvLimiteSinAsignarLabel;
        public TextView tvLimiteSinAsignarValue;
        public TextView tvNroComprobanteLabel;
        public TextView tvNroComprobanteValue;

        DataHolder(View view) {
            super(view);
            this.llLimiteSinAsignar = (LinearLayout) view.findViewById(R.id.llLimiteSinAsignar);
            this.tvLimiteSinAsignarLabel = (TextView) view.findViewById(R.id.tvLimiteSinAsignarLabel);
            this.tvLimiteSinAsignarLabel.setText(RecalificacionComprobanteAdapter.this.c.getString(R.string.ID_4849_RECALIFICACION_LBL_LIMITE_SIN_ASIGNAR));
            this.tvLimiteSinAsignarValue = (TextView) view.findViewById(R.id.tvLimiteSinAsignarValue);
            this.llFechaSolicitud = (LinearLayout) view.findViewById(R.id.llFechaSolicitud);
            this.tvFechaSolicitudLabel = (TextView) view.findViewById(R.id.tvFechaSolicitudLabel);
            this.tvFechaSolicitudLabel.setText(R.string.ID_4857_RECALIFICACION_LBL_FECHA_DE_SOLICITUD);
            this.tvFechaSolicitudValue = (TextView) view.findViewById(R.id.tvFechaSolicitudValue);
            this.llNroComprobante = (LinearLayout) view.findViewById(R.id.llNroComprobante);
            this.tvNroComprobanteLabel = (TextView) view.findViewById(R.id.tvNroComprobanteLabel);
            this.tvNroComprobanteLabel.setText(R.string.ID_4858_RECALIFICACION_LBL_NRO_COMPROBANTE);
            this.tvNroComprobanteValue = (TextView) view.findViewById(R.id.tvNroComprobanteValue);
        }

        /* access modifiers changed from: 0000 */
        public void a(String str, String str2, String str3) {
            this.tvLimiteSinAsignarValue.setText(str);
            this.tvFechaSolicitudValue.setText(str2);
            this.tvNroComprobanteValue.setText(str3);
            try {
                this.tvLimiteSinAsignarValue.setContentDescription(CAccessibility.getInstance(RecalificacionComprobanteAdapter.this.c).applyFilterAmount(this.tvLimiteSinAsignarValue.getText().toString()));
                this.tvFechaSolicitudValue.setContentDescription(CAccessibility.getInstance(RecalificacionComprobanteAdapter.this.c).applyFilterAmount(this.tvFechaSolicitudValue.getText().toString()));
                this.tvNroComprobanteValue.setContentDescription(CAccessibility.getInstance(RecalificacionComprobanteAdapter.this.c).applyFilterAmount(this.tvNroComprobanteValue.getText().toString()));
                this.tvLimiteSinAsignarValue.setAccessibilityDelegate(RecalificacionComprobanteAdapter.this.i);
                this.tvFechaSolicitudValue.setAccessibilityDelegate(RecalificacionComprobanteAdapter.this.i);
                this.tvNroComprobanteValue.setAccessibilityDelegate(RecalificacionComprobanteAdapter.this.i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class HeaderHolder extends ViewHolder {
        public AmountView avLimiteTotalCrediticio;
        public TextView tvNuevoLimiteTotalCrediticio;

        HeaderHolder(View view) {
            super(view);
            this.tvNuevoLimiteTotalCrediticio = (TextView) view.findViewById(R.id.tvNuevoLimiteTotalCrediticio);
            this.avLimiteTotalCrediticio = (AmountView) view.findViewById(R.id.avLimiteTotalCrediticio);
        }

        /* access modifiers changed from: 0000 */
        public void t() {
            this.tvNuevoLimiteTotalCrediticio.setText(RecalificacionComprobanteAdapter.this.a);
            this.avLimiteTotalCrediticio.setText(RecalificacionComprobanteAdapter.this.b);
            try {
                this.avLimiteTotalCrediticio.setContentDescription(CAccessibility.getInstance(RecalificacionComprobanteAdapter.this.c).applyFilterAmount(this.avLimiteTotalCrediticio.getText().toString()));
                this.avLimiteTotalCrediticio.setAccessibilityDelegate(RecalificacionComprobanteAdapter.this.i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class ItemHolder extends ViewHolder {
        public LinearLayout llLimiteAnterior;
        public LinearLayout llLimiteDisponible;
        public LinearLayout llNuevoLimite;
        private final String n = "01";
        private final String o = "40";
        private final String p = "41";
        private final String q = TarjetasConstants.CODIGO_TARJETA_AMEX;
        private final String r = "02";
        private final String s = "03";
        public TextView tvRecalificacionComprobanteLimiteAnteriorLabel;
        public TextView tvRecalificacionComprobanteLimiteAnteriorValue;
        public TextView tvRecalificacionComprobanteLimiteDisponibleValue;
        public TextView tvRecalificacionComprobanteLimiteProductoNombre;
        public TextView tvRecalificacionComprobanteNuevoLimiteLabel;
        public TextView tvRecalificacionComprobanteNuevoLimiteValue;

        ItemHolder(View view) {
            super(view);
            this.tvRecalificacionComprobanteLimiteProductoNombre = (TextView) view.findViewById(R.id.tvRecalificacionComprobanteLimiteProductoNombre);
            this.llLimiteAnterior = (LinearLayout) view.findViewById(R.id.llLimiteAnterior);
            this.tvRecalificacionComprobanteLimiteAnteriorLabel = (TextView) view.findViewById(R.id.tvRecalificacionComprobanteLimiteAnteriorLabel);
            this.tvRecalificacionComprobanteLimiteAnteriorValue = (TextView) view.findViewById(R.id.tvRecalificacionComprobanteLimiteAnteriorValue);
            this.llNuevoLimite = (LinearLayout) view.findViewById(R.id.llNuevoLimite);
            this.tvRecalificacionComprobanteNuevoLimiteLabel = (TextView) view.findViewById(R.id.tvRecalificacionComprobanteNuevoLimiteLabel);
            this.tvRecalificacionComprobanteNuevoLimiteValue = (TextView) view.findViewById(R.id.tvRecalificacionComprobanteNuevoLimiteValue);
            this.llLimiteDisponible = (LinearLayout) view.findViewById(R.id.llLimiteDisponible);
            this.tvRecalificacionComprobanteLimiteDisponibleValue = (TextView) view.findViewById(R.id.tvRecalificacionComprobanteLimiteDisponibleValue);
        }

        /* access modifiers changed from: 0000 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void a(ar.com.santander.rio.mbanking.services.soap.beans.body.ProductoRecalificacionItem r5) {
            /*
                r4 = this;
                android.widget.TextView r0 = r4.tvRecalificacionComprobanteLimiteProductoNombre
                java.lang.String r1 = r5.getNombreProducto()
                android.text.Spanned r1 = android.text.Html.fromHtml(r1)
                r0.setText(r1)
                android.widget.LinearLayout r0 = r4.llLimiteAnterior
                r1 = 8
                r0.setVisibility(r1)
                android.widget.LinearLayout r0 = r4.llNuevoLimite
                r2 = 0
                r0.setVisibility(r2)
                android.widget.LinearLayout r0 = r4.llLimiteDisponible
                r0.setVisibility(r1)
                android.widget.LinearLayout r0 = r4.llLimiteAnterior
                r0.setVisibility(r2)
                android.widget.TextView r0 = r4.tvRecalificacionComprobanteLimiteAnteriorValue
                java.lang.String r3 = r5.getLimiteAnteriorProducto()
                r0.setText(r3)
                java.lang.String r0 = r5.getIdProducto()
                int r3 = r0.hashCode()
                switch(r3) {
                    case 1537: goto L_0x006e;
                    case 1538: goto L_0x0064;
                    case 1539: goto L_0x005a;
                    default: goto L_0x0038;
                }
            L_0x0038:
                switch(r3) {
                    case 1660: goto L_0x0050;
                    case 1661: goto L_0x0046;
                    case 1662: goto L_0x003c;
                    default: goto L_0x003b;
                }
            L_0x003b:
                goto L_0x0078
            L_0x003c:
                java.lang.String r3 = "42"
                boolean r0 = r0.equals(r3)
                if (r0 == 0) goto L_0x0078
                r0 = 2
                goto L_0x0079
            L_0x0046:
                java.lang.String r3 = "41"
                boolean r0 = r0.equals(r3)
                if (r0 == 0) goto L_0x0078
                r0 = 5
                goto L_0x0079
            L_0x0050:
                java.lang.String r3 = "40"
                boolean r0 = r0.equals(r3)
                if (r0 == 0) goto L_0x0078
                r0 = 1
                goto L_0x0079
            L_0x005a:
                java.lang.String r3 = "03"
                boolean r0 = r0.equals(r3)
                if (r0 == 0) goto L_0x0078
                r0 = 4
                goto L_0x0079
            L_0x0064:
                java.lang.String r3 = "02"
                boolean r0 = r0.equals(r3)
                if (r0 == 0) goto L_0x0078
                r0 = 3
                goto L_0x0079
            L_0x006e:
                java.lang.String r3 = "01"
                boolean r0 = r0.equals(r3)
                if (r0 == 0) goto L_0x0078
                r0 = 0
                goto L_0x0079
            L_0x0078:
                r0 = -1
            L_0x0079:
                switch(r0) {
                    case 0: goto L_0x00b9;
                    case 1: goto L_0x00a7;
                    case 2: goto L_0x00a7;
                    case 3: goto L_0x009d;
                    case 4: goto L_0x008b;
                    case 5: goto L_0x007d;
                    default: goto L_0x007c;
                }
            L_0x007c:
                goto L_0x00c2
            L_0x007d:
                android.widget.LinearLayout r0 = r4.llNuevoLimite
                r0.setVisibility(r1)
                android.widget.TextView r0 = r4.tvRecalificacionComprobanteLimiteAnteriorLabel
                r1 = 2131756874(0x7f10074a, float:1.9144668E38)
                r0.setText(r1)
                goto L_0x00c2
            L_0x008b:
                android.widget.TextView r0 = r4.tvRecalificacionComprobanteNuevoLimiteLabel
                r1 = 2131756887(0x7f100757, float:1.9144694E38)
                r0.setText(r1)
                android.widget.TextView r0 = r4.tvRecalificacionComprobanteNuevoLimiteValue
                java.lang.String r1 = r5.getNuevoLimiteProducto()
                r0.setText(r1)
                goto L_0x00c2
            L_0x009d:
                android.widget.TextView r0 = r4.tvRecalificacionComprobanteNuevoLimiteValue
                java.lang.String r1 = r5.getNuevoLimiteProducto()
                r0.setText(r1)
                goto L_0x00c2
            L_0x00a7:
                android.widget.TextView r0 = r4.tvRecalificacionComprobanteNuevoLimiteLabel
                r1 = 2131756886(0x7f100756, float:1.9144692E38)
                r0.setText(r1)
                android.widget.TextView r0 = r4.tvRecalificacionComprobanteNuevoLimiteValue
                java.lang.String r1 = r5.getNuevoLimiteProducto()
                r0.setText(r1)
                goto L_0x00c2
            L_0x00b9:
                android.widget.TextView r0 = r4.tvRecalificacionComprobanteNuevoLimiteValue
                java.lang.String r1 = r5.getNuevoLimiteProducto()
                r0.setText(r1)
            L_0x00c2:
                java.lang.String r0 = r5.getNuevoDisponibleProd()
                if (r0 == 0) goto L_0x00e2
                java.lang.String r0 = r5.getNuevoDisponibleProd()
                java.lang.String r1 = ""
                boolean r0 = r0.equalsIgnoreCase(r1)
                if (r0 != 0) goto L_0x00e2
                android.widget.LinearLayout r0 = r4.llLimiteDisponible
                r0.setVisibility(r2)
                android.widget.TextView r0 = r4.tvRecalificacionComprobanteLimiteDisponibleValue
                java.lang.String r5 = r5.getNuevoDisponibleProd()
                r0.setText(r5)
            L_0x00e2:
                android.widget.TextView r5 = r4.tvRecalificacionComprobanteLimiteAnteriorValue     // Catch:{ Exception -> 0x015b }
                ar.com.santander.rio.mbanking.app.ui.adapters.RecalificacionComprobanteAdapter r0 = ar.com.santander.rio.mbanking.app.ui.adapters.RecalificacionComprobanteAdapter.this     // Catch:{ Exception -> 0x015b }
                android.content.Context r0 = r0.c     // Catch:{ Exception -> 0x015b }
                ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r0 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r0)     // Catch:{ Exception -> 0x015b }
                android.widget.TextView r1 = r4.tvRecalificacionComprobanteLimiteAnteriorValue     // Catch:{ Exception -> 0x015b }
                java.lang.CharSequence r1 = r1.getText()     // Catch:{ Exception -> 0x015b }
                java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x015b }
                java.lang.String r0 = r0.applyFilterAmount(r1)     // Catch:{ Exception -> 0x015b }
                r5.setContentDescription(r0)     // Catch:{ Exception -> 0x015b }
                android.widget.TextView r5 = r4.tvRecalificacionComprobanteNuevoLimiteValue     // Catch:{ Exception -> 0x015b }
                ar.com.santander.rio.mbanking.app.ui.adapters.RecalificacionComprobanteAdapter r0 = ar.com.santander.rio.mbanking.app.ui.adapters.RecalificacionComprobanteAdapter.this     // Catch:{ Exception -> 0x015b }
                android.content.Context r0 = r0.c     // Catch:{ Exception -> 0x015b }
                ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r0 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r0)     // Catch:{ Exception -> 0x015b }
                android.widget.TextView r1 = r4.tvRecalificacionComprobanteNuevoLimiteValue     // Catch:{ Exception -> 0x015b }
                java.lang.CharSequence r1 = r1.getText()     // Catch:{ Exception -> 0x015b }
                java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x015b }
                java.lang.String r0 = r0.applyFilterAmount(r1)     // Catch:{ Exception -> 0x015b }
                r5.setContentDescription(r0)     // Catch:{ Exception -> 0x015b }
                android.widget.TextView r5 = r4.tvRecalificacionComprobanteLimiteDisponibleValue     // Catch:{ Exception -> 0x015b }
                ar.com.santander.rio.mbanking.app.ui.adapters.RecalificacionComprobanteAdapter r0 = ar.com.santander.rio.mbanking.app.ui.adapters.RecalificacionComprobanteAdapter.this     // Catch:{ Exception -> 0x015b }
                android.content.Context r0 = r0.c     // Catch:{ Exception -> 0x015b }
                ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r0 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r0)     // Catch:{ Exception -> 0x015b }
                android.widget.TextView r1 = r4.tvRecalificacionComprobanteLimiteDisponibleValue     // Catch:{ Exception -> 0x015b }
                java.lang.CharSequence r1 = r1.getText()     // Catch:{ Exception -> 0x015b }
                java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x015b }
                java.lang.String r0 = r0.applyFilterAmount(r1)     // Catch:{ Exception -> 0x015b }
                r5.setContentDescription(r0)     // Catch:{ Exception -> 0x015b }
                android.widget.TextView r5 = r4.tvRecalificacionComprobanteLimiteDisponibleValue     // Catch:{ Exception -> 0x015b }
                ar.com.santander.rio.mbanking.app.ui.adapters.RecalificacionComprobanteAdapter r0 = ar.com.santander.rio.mbanking.app.ui.adapters.RecalificacionComprobanteAdapter.this     // Catch:{ Exception -> 0x015b }
                android.view.View$AccessibilityDelegate r0 = r0.i     // Catch:{ Exception -> 0x015b }
                r5.setAccessibilityDelegate(r0)     // Catch:{ Exception -> 0x015b }
                android.widget.TextView r5 = r4.tvRecalificacionComprobanteNuevoLimiteValue     // Catch:{ Exception -> 0x015b }
                ar.com.santander.rio.mbanking.app.ui.adapters.RecalificacionComprobanteAdapter r0 = ar.com.santander.rio.mbanking.app.ui.adapters.RecalificacionComprobanteAdapter.this     // Catch:{ Exception -> 0x015b }
                android.view.View$AccessibilityDelegate r0 = r0.i     // Catch:{ Exception -> 0x015b }
                r5.setAccessibilityDelegate(r0)     // Catch:{ Exception -> 0x015b }
                android.widget.TextView r5 = r4.tvRecalificacionComprobanteLimiteAnteriorValue     // Catch:{ Exception -> 0x015b }
                ar.com.santander.rio.mbanking.app.ui.adapters.RecalificacionComprobanteAdapter r0 = ar.com.santander.rio.mbanking.app.ui.adapters.RecalificacionComprobanteAdapter.this     // Catch:{ Exception -> 0x015b }
                android.view.View$AccessibilityDelegate r0 = r0.i     // Catch:{ Exception -> 0x015b }
                r5.setAccessibilityDelegate(r0)     // Catch:{ Exception -> 0x015b }
                goto L_0x015f
            L_0x015b:
                r5 = move-exception
                r5.printStackTrace()
            L_0x015f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.adapters.RecalificacionComprobanteAdapter.ItemHolder.a(ar.com.santander.rio.mbanking.services.soap.beans.body.ProductoRecalificacionItem):void");
        }
    }

    public class LegendHolder extends ViewHolder {
        public TextView tvLegendsComprobanteRecalificacion;

        LegendHolder(View view) {
            super(view);
            this.tvLegendsComprobanteRecalificacion = (TextView) view.findViewById(R.id.tvLegendsComprobanteRecalificacion);
        }

        /* access modifiers changed from: 0000 */
        public void a(Leyenda leyenda) {
            this.tvLegendsComprobanteRecalificacion.setText(Html.fromHtml(leyenda.descripcion));
        }
    }

    public class TitleHolder extends ViewHolder {
        public TextView functionality_title;

        TitleHolder(View view) {
            super(view);
            this.functionality_title = (TextView) view.findViewById(R.id.functionality_title);
        }

        /* access modifiers changed from: 0000 */
        public void t() {
            this.functionality_title.setText(R.string.ID_4855_RECALIFICACION_TIT_COMPROBANTE_DE_CAMBIO_DE_LIMITES);
        }
    }

    private boolean a(int i2) {
        return i2 == 0;
    }

    private boolean b(int i2) {
        return i2 == 1;
    }

    public RecalificacionComprobanteAdapter(Context context, String str, String str2, List<ProductoRecalificacionItem> list, List<Leyenda> list2, String str3, String str4, String str5) {
        this.c = context;
        this.b = str2;
        this.a = str;
        this.d = list;
        this.e = list2;
        this.f = str3;
        this.g = str4;
        this.h = str5;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i2) {
        switch (i2) {
            case 0:
                return new TitleHolder(LayoutInflater.from(this.c).inflate(R.layout.generic_view_title_layout, viewGroup, false));
            case 1:
                return new HeaderHolder(LayoutInflater.from(this.c).inflate(R.layout.list_item_recalificacion_producto_header_comprobante, viewGroup, false));
            case 2:
                return new LegendHolder(LayoutInflater.from(this.c).inflate(R.layout.recalificacion_comprobante_footer, viewGroup, false));
            case 3:
                return new ItemHolder(LayoutInflater.from(this.c).inflate(R.layout.list_item_recalificacion_producto_comprobante, viewGroup, false));
            case 4:
                return new DataHolder(LayoutInflater.from(this.c).inflate(R.layout.list_item_recalificacion_producto_comprobante_data, viewGroup, false));
            default:
                return null;
        }
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i2) {
        if (viewHolder instanceof TitleHolder) {
            ((TitleHolder) viewHolder).t();
        } else if (viewHolder instanceof HeaderHolder) {
            ((HeaderHolder) viewHolder).t();
        } else if (viewHolder instanceof ItemHolder) {
            ((ItemHolder) viewHolder).a((ProductoRecalificacionItem) this.d.get(i2 - 2));
        } else if (viewHolder instanceof DataHolder) {
            ((DataHolder) viewHolder).a(this.f, this.g, this.h);
        } else if (viewHolder instanceof LegendHolder) {
            ((LegendHolder) viewHolder).a((Leyenda) this.e.get(i2 - ((this.d.size() + 2) + 1)));
        }
    }

    public int getItemCount() {
        return this.d.size() + 2 + 1 + this.e.size();
    }

    public int getItemViewType(int i2) {
        if (a(i2)) {
            return 0;
        }
        if (b(i2)) {
            return 1;
        }
        if (d(i2)) {
            return 4;
        }
        return c(i2) ? 2 : 3;
    }

    private boolean c(int i2) {
        return i2 > (this.d.size() + 2) - 1;
    }

    private boolean d(int i2) {
        return i2 == this.d.size() + 2;
    }
}
