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
import ar.com.santander.rio.mbanking.app.commons.CFormatterAmounts;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ProductoRecalificacionItem;
import ar.com.santander.rio.mbanking.view.AmountView;
import java.util.List;

public class RecalificacionesProductsConfirmarAdapter extends Adapter<ViewHolder> {
    /* access modifiers changed from: private */
    public Context a;
    private List<ProductoRecalificacionItem> b;
    private List<Leyenda> c;
    private final int d = 0;
    private final int e = 1;
    private final int f = 2;
    private final int g = 3;
    private final int h = 4;
    private final int i = 1;
    private final int j = 1;
    private final int k = 1;
    /* access modifiers changed from: private */
    public String l;
    /* access modifiers changed from: private */
    public String m;
    private int n;
    /* access modifiers changed from: private */
    public AccessibilityDelegate o = new AccessibilityDelegate() {
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            accessibilityNodeInfo.setText(view.getContentDescription());
        }
    };

    public class HeaderHolder extends ViewHolder {
        public AmountView avLimiteTotalCrediticio;
        public TextView tvNuevoLineaTotalCrediticia;

        HeaderHolder(View view) {
            super(view);
            this.tvNuevoLineaTotalCrediticia = (TextView) view.findViewById(R.id.tvNuevoLineaTotalCrediticia);
            this.avLimiteTotalCrediticio = (AmountView) view.findViewById(R.id.avLimiteTotalCrediticio);
        }

        /* access modifiers changed from: 0000 */
        public void t() {
            this.tvNuevoLineaTotalCrediticia.setText(RecalificacionesProductsConfirmarAdapter.this.m);
            this.avLimiteTotalCrediticio.setText(RecalificacionesProductsConfirmarAdapter.this.l);
            try {
                this.avLimiteTotalCrediticio.setContentDescription(CAccessibility.getInstance(RecalificacionesProductsConfirmarAdapter.this.a).applyFilterAmount(this.avLimiteTotalCrediticio.getText().toString()));
                this.avLimiteTotalCrediticio.setAccessibilityDelegate(RecalificacionesProductsConfirmarAdapter.this.o);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class ItemHolder extends ViewHolder {
        public LinearLayout llLimiteActual;
        public LinearLayout llNuevoDisponible;
        public LinearLayout llNuevoLimite;
        private final String n = "40";
        private final String o = "41";
        private final String p = TarjetasConstants.CODIGO_TARJETA_AMEX;
        private final String q = "03";
        private final String r = "02";
        private final String s = "01";
        public TextView tvRecalificacionLimiteActualLabel;
        public TextView tvRecalificacionLimiteActualValue;
        public TextView tvRecalificacionLimiteProductoNombre;
        public TextView tvRecalificacionMessageValue;
        public TextView tvRecalificacionNuevoDisponibleValue;
        public TextView tvRecalificacionNuevoLimiteLabel;
        public TextView tvRecalificacionNuevoLimiteValue;

        ItemHolder(View view) {
            super(view);
            this.tvRecalificacionLimiteProductoNombre = (TextView) view.findViewById(R.id.tvRecalificacionLimiteProductoNombre);
            this.llLimiteActual = (LinearLayout) view.findViewById(R.id.llLimiteActual);
            this.tvRecalificacionLimiteActualLabel = (TextView) view.findViewById(R.id.tvRecalificacionLimiteActualLabel);
            this.tvRecalificacionLimiteActualValue = (TextView) view.findViewById(R.id.tvRecalificacionLimiteActualValue);
            this.llNuevoLimite = (LinearLayout) view.findViewById(R.id.llNuevoLimite);
            this.tvRecalificacionNuevoLimiteLabel = (TextView) view.findViewById(R.id.tvRecalificacionNuevoLimiteLabel);
            this.tvRecalificacionNuevoLimiteValue = (TextView) view.findViewById(R.id.tvRecalificacionNuevoLimiteValue);
            this.llNuevoDisponible = (LinearLayout) view.findViewById(R.id.llNuevoDisponible);
            this.tvRecalificacionNuevoDisponibleValue = (TextView) view.findViewById(R.id.tvRecalificacionNuevoDisponibleValue);
            this.tvRecalificacionMessageValue = (TextView) view.findViewById(R.id.tvRecalificacionMessageValue);
        }

        /* access modifiers changed from: 0000 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void a(ar.com.santander.rio.mbanking.services.soap.beans.body.ProductoRecalificacionItem r6) {
            /*
                r5 = this;
                android.widget.TextView r0 = r5.tvRecalificacionLimiteProductoNombre
                java.lang.String r1 = r6.getNombreProducto()
                android.text.Spanned r1 = android.text.Html.fromHtml(r1)
                r0.setText(r1)
                android.widget.LinearLayout r0 = r5.llLimiteActual
                r1 = 0
                r0.setVisibility(r1)
                android.widget.LinearLayout r0 = r5.llNuevoLimite
                r2 = 8
                r0.setVisibility(r2)
                android.widget.LinearLayout r0 = r5.llNuevoDisponible
                r0.setVisibility(r2)
                android.widget.TextView r0 = r5.tvRecalificacionMessageValue
                r0.setVisibility(r2)
                int r0 = r6.getNuevoLimiteProductoAux()
                java.lang.Integer r2 = r6.getLimiteActualProd()
                int r2 = r2.intValue()
                int r0 = r0 - r2
                java.lang.Integer r2 = r6.getDisponibleProd()
                int r2 = r2.intValue()
                int r0 = r0 + r2
                android.widget.TextView r2 = r5.tvRecalificacionLimiteActualValue
                java.lang.Integer r3 = r6.getLimiteActualProd()
                int r3 = r3.intValue()
                double r3 = (double) r3
                java.lang.String r3 = ar.com.santander.rio.mbanking.app.commons.CFormatterAmounts.getAmountAndCurrencyFromDouble(r3)
                r2.setText(r3)
                android.widget.TextView r2 = r5.tvRecalificacionLimiteActualValue     // Catch:{ Exception -> 0x0075 }
                ar.com.santander.rio.mbanking.app.ui.adapters.RecalificacionesProductsConfirmarAdapter r3 = ar.com.santander.rio.mbanking.app.ui.adapters.RecalificacionesProductsConfirmarAdapter.this     // Catch:{ Exception -> 0x0075 }
                android.content.Context r3 = r3.a     // Catch:{ Exception -> 0x0075 }
                ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r3 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r3)     // Catch:{ Exception -> 0x0075 }
                android.widget.TextView r4 = r5.tvRecalificacionLimiteActualValue     // Catch:{ Exception -> 0x0075 }
                java.lang.CharSequence r4 = r4.getText()     // Catch:{ Exception -> 0x0075 }
                java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0075 }
                java.lang.String r3 = r3.applyFilterAmount(r4)     // Catch:{ Exception -> 0x0075 }
                r2.setContentDescription(r3)     // Catch:{ Exception -> 0x0075 }
                android.widget.TextView r2 = r5.tvRecalificacionLimiteActualValue     // Catch:{ Exception -> 0x0075 }
                ar.com.santander.rio.mbanking.app.ui.adapters.RecalificacionesProductsConfirmarAdapter r3 = ar.com.santander.rio.mbanking.app.ui.adapters.RecalificacionesProductsConfirmarAdapter.this     // Catch:{ Exception -> 0x0075 }
                android.view.View$AccessibilityDelegate r3 = r3.o     // Catch:{ Exception -> 0x0075 }
                r2.setAccessibilityDelegate(r3)     // Catch:{ Exception -> 0x0075 }
                goto L_0x0079
            L_0x0075:
                r2 = move-exception
                r2.printStackTrace()
            L_0x0079:
                java.lang.String r2 = r6.getIdProducto()
                r3 = -1
                int r4 = r2.hashCode()
                switch(r4) {
                    case 1537: goto L_0x00bb;
                    case 1538: goto L_0x00b1;
                    case 1539: goto L_0x00a7;
                    default: goto L_0x0085;
                }
            L_0x0085:
                switch(r4) {
                    case 1660: goto L_0x009d;
                    case 1661: goto L_0x0093;
                    case 1662: goto L_0x0089;
                    default: goto L_0x0088;
                }
            L_0x0088:
                goto L_0x00c5
            L_0x0089:
                java.lang.String r4 = "42"
                boolean r2 = r2.equals(r4)
                if (r2 == 0) goto L_0x00c5
                r2 = 2
                goto L_0x00c6
            L_0x0093:
                java.lang.String r4 = "41"
                boolean r2 = r2.equals(r4)
                if (r2 == 0) goto L_0x00c5
                r2 = 5
                goto L_0x00c6
            L_0x009d:
                java.lang.String r4 = "40"
                boolean r2 = r2.equals(r4)
                if (r2 == 0) goto L_0x00c5
                r2 = 1
                goto L_0x00c6
            L_0x00a7:
                java.lang.String r4 = "03"
                boolean r2 = r2.equals(r4)
                if (r2 == 0) goto L_0x00c5
                r2 = 4
                goto L_0x00c6
            L_0x00b1:
                java.lang.String r4 = "02"
                boolean r2 = r2.equals(r4)
                if (r2 == 0) goto L_0x00c5
                r2 = 3
                goto L_0x00c6
            L_0x00bb:
                java.lang.String r4 = "01"
                boolean r2 = r2.equals(r4)
                if (r2 == 0) goto L_0x00c5
                r2 = 0
                goto L_0x00c6
            L_0x00c5:
                r2 = -1
            L_0x00c6:
                r3 = 2131756893(0x7f10075d, float:1.9144706E38)
                switch(r2) {
                    case 0: goto L_0x01c1;
                    case 1: goto L_0x016c;
                    case 2: goto L_0x016c;
                    case 3: goto L_0x0117;
                    case 4: goto L_0x00d8;
                    case 5: goto L_0x00ce;
                    default: goto L_0x00cc;
                }
            L_0x00cc:
                goto L_0x024f
            L_0x00ce:
                android.widget.TextView r6 = r5.tvRecalificacionLimiteActualLabel
                r0 = 2131756874(0x7f10074a, float:1.9144668E38)
                r6.setText(r0)
                goto L_0x024f
            L_0x00d8:
                android.widget.LinearLayout r6 = r5.llNuevoLimite
                r6.setVisibility(r1)
                android.widget.TextView r6 = r5.tvRecalificacionLimiteActualLabel
                r6.setText(r3)
                android.widget.TextView r6 = r5.tvRecalificacionNuevoLimiteLabel
                r0 = 2131756887(0x7f100757, float:1.9144694E38)
                r6.setText(r0)
                android.widget.TextView r6 = r5.tvRecalificacionNuevoLimiteValue
                r0 = 2131756898(0x7f100762, float:1.9144717E38)
                r6.setText(r0)
                android.widget.TextView r6 = r5.tvRecalificacionNuevoLimiteValue     // Catch:{ Exception -> 0x0111 }
                ar.com.santander.rio.mbanking.app.ui.adapters.RecalificacionesProductsConfirmarAdapter r0 = ar.com.santander.rio.mbanking.app.ui.adapters.RecalificacionesProductsConfirmarAdapter.this     // Catch:{ Exception -> 0x0111 }
                android.content.Context r0 = r0.a     // Catch:{ Exception -> 0x0111 }
                ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r0 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r0)     // Catch:{ Exception -> 0x0111 }
                android.widget.TextView r1 = r5.tvRecalificacionNuevoLimiteValue     // Catch:{ Exception -> 0x0111 }
                java.lang.CharSequence r1 = r1.getText()     // Catch:{ Exception -> 0x0111 }
                java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0111 }
                java.lang.String r0 = r0.applyFilterAmount(r1)     // Catch:{ Exception -> 0x0111 }
                r6.setContentDescription(r0)     // Catch:{ Exception -> 0x0111 }
                goto L_0x024f
            L_0x0111:
                r6 = move-exception
                r6.printStackTrace()
                goto L_0x024f
            L_0x0117:
                android.widget.LinearLayout r0 = r5.llNuevoLimite
                r0.setVisibility(r1)
                android.widget.TextView r0 = r5.tvRecalificacionLimiteActualLabel
                r0.setText(r3)
                android.widget.TextView r0 = r5.tvRecalificacionNuevoLimiteLabel
                r1 = 2131756885(0x7f100755, float:1.914469E38)
                r0.setText(r1)
                android.widget.TextView r0 = r5.tvRecalificacionNuevoLimiteValue
                java.lang.String r1 = r6.getNuevoLimiteProducto()
                if (r1 == 0) goto L_0x0136
                java.lang.String r6 = r6.getNuevoLimiteProducto()
                goto L_0x0138
            L_0x0136:
                java.lang.String r6 = "0"
            L_0x0138:
                java.lang.Double r6 = java.lang.Double.valueOf(r6)
                double r1 = r6.doubleValue()
                java.lang.String r6 = ar.com.santander.rio.mbanking.app.commons.CFormatterAmounts.getAmountAndCurrencyFromDouble(r1)
                r0.setText(r6)
                android.widget.TextView r6 = r5.tvRecalificacionNuevoLimiteValue     // Catch:{ Exception -> 0x0166 }
                ar.com.santander.rio.mbanking.app.ui.adapters.RecalificacionesProductsConfirmarAdapter r0 = ar.com.santander.rio.mbanking.app.ui.adapters.RecalificacionesProductsConfirmarAdapter.this     // Catch:{ Exception -> 0x0166 }
                android.content.Context r0 = r0.a     // Catch:{ Exception -> 0x0166 }
                ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r0 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r0)     // Catch:{ Exception -> 0x0166 }
                android.widget.TextView r1 = r5.tvRecalificacionNuevoLimiteValue     // Catch:{ Exception -> 0x0166 }
                java.lang.CharSequence r1 = r1.getText()     // Catch:{ Exception -> 0x0166 }
                java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0166 }
                java.lang.String r0 = r0.applyFilterAmount(r1)     // Catch:{ Exception -> 0x0166 }
                r6.setContentDescription(r0)     // Catch:{ Exception -> 0x0166 }
                goto L_0x024f
            L_0x0166:
                r6 = move-exception
                r6.printStackTrace()
                goto L_0x024f
            L_0x016c:
                android.widget.LinearLayout r0 = r5.llNuevoLimite
                r0.setVisibility(r1)
                android.widget.TextView r0 = r5.tvRecalificacionLimiteActualLabel
                r0.setText(r3)
                android.widget.TextView r0 = r5.tvRecalificacionNuevoLimiteLabel
                r1 = 2131756886(0x7f100756, float:1.9144692E38)
                r0.setText(r1)
                android.widget.TextView r0 = r5.tvRecalificacionNuevoLimiteValue
                java.lang.String r1 = r6.getNuevoLimiteProducto()
                if (r1 == 0) goto L_0x018b
                java.lang.String r6 = r6.getNuevoLimiteProducto()
                goto L_0x018d
            L_0x018b:
                java.lang.String r6 = "0"
            L_0x018d:
                java.lang.Double r6 = java.lang.Double.valueOf(r6)
                double r1 = r6.doubleValue()
                java.lang.String r6 = ar.com.santander.rio.mbanking.app.commons.CFormatterAmounts.getAmountAndCurrencyFromDouble(r1)
                r0.setText(r6)
                android.widget.TextView r6 = r5.tvRecalificacionNuevoLimiteValue     // Catch:{ Exception -> 0x01bb }
                ar.com.santander.rio.mbanking.app.ui.adapters.RecalificacionesProductsConfirmarAdapter r0 = ar.com.santander.rio.mbanking.app.ui.adapters.RecalificacionesProductsConfirmarAdapter.this     // Catch:{ Exception -> 0x01bb }
                android.content.Context r0 = r0.a     // Catch:{ Exception -> 0x01bb }
                ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r0 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r0)     // Catch:{ Exception -> 0x01bb }
                android.widget.TextView r1 = r5.tvRecalificacionNuevoLimiteValue     // Catch:{ Exception -> 0x01bb }
                java.lang.CharSequence r1 = r1.getText()     // Catch:{ Exception -> 0x01bb }
                java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x01bb }
                java.lang.String r0 = r0.applyFilterAmount(r1)     // Catch:{ Exception -> 0x01bb }
                r6.setContentDescription(r0)     // Catch:{ Exception -> 0x01bb }
                goto L_0x024f
            L_0x01bb:
                r6 = move-exception
                r6.printStackTrace()
                goto L_0x024f
            L_0x01c1:
                android.widget.LinearLayout r2 = r5.llNuevoLimite
                r2.setVisibility(r1)
                android.widget.LinearLayout r2 = r5.llNuevoDisponible
                r2.setVisibility(r1)
                android.widget.TextView r1 = r5.tvRecalificacionLimiteActualLabel
                r1.setText(r3)
                android.widget.TextView r1 = r5.tvRecalificacionNuevoLimiteValue
                java.lang.String r2 = r6.getNuevoLimiteProducto()
                if (r2 == 0) goto L_0x01dd
                java.lang.String r6 = r6.getNuevoLimiteProducto()
                goto L_0x01df
            L_0x01dd:
                java.lang.String r6 = "0"
            L_0x01df:
                java.lang.Double r6 = java.lang.Double.valueOf(r6)
                double r2 = r6.doubleValue()
                java.lang.String r6 = ar.com.santander.rio.mbanking.app.commons.CFormatterAmounts.getAmountAndCurrencyFromDouble(r2)
                r1.setText(r6)
                android.widget.TextView r6 = r5.tvRecalificacionNuevoDisponibleValue
                if (r0 > 0) goto L_0x01fd
                java.lang.String r0 = "0"
                java.lang.Double r0 = java.lang.Double.valueOf(r0)
                double r0 = r0.doubleValue()
                goto L_0x01fe
            L_0x01fd:
                double r0 = (double) r0
            L_0x01fe:
                java.lang.String r0 = ar.com.santander.rio.mbanking.app.commons.CFormatterAmounts.getAmountAndCurrencyFromDouble(r0)
                r6.setText(r0)
                android.widget.TextView r6 = r5.tvRecalificacionNuevoLimiteValue     // Catch:{ Exception -> 0x024b }
                ar.com.santander.rio.mbanking.app.ui.adapters.RecalificacionesProductsConfirmarAdapter r0 = ar.com.santander.rio.mbanking.app.ui.adapters.RecalificacionesProductsConfirmarAdapter.this     // Catch:{ Exception -> 0x024b }
                android.content.Context r0 = r0.a     // Catch:{ Exception -> 0x024b }
                ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r0 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r0)     // Catch:{ Exception -> 0x024b }
                android.widget.TextView r1 = r5.tvRecalificacionNuevoLimiteValue     // Catch:{ Exception -> 0x024b }
                java.lang.CharSequence r1 = r1.getText()     // Catch:{ Exception -> 0x024b }
                java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x024b }
                java.lang.String r0 = r0.applyFilterAmount(r1)     // Catch:{ Exception -> 0x024b }
                r6.setContentDescription(r0)     // Catch:{ Exception -> 0x024b }
                android.widget.TextView r6 = r5.tvRecalificacionNuevoDisponibleValue     // Catch:{ Exception -> 0x024b }
                ar.com.santander.rio.mbanking.app.ui.adapters.RecalificacionesProductsConfirmarAdapter r0 = ar.com.santander.rio.mbanking.app.ui.adapters.RecalificacionesProductsConfirmarAdapter.this     // Catch:{ Exception -> 0x024b }
                android.content.Context r0 = r0.a     // Catch:{ Exception -> 0x024b }
                ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r0 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r0)     // Catch:{ Exception -> 0x024b }
                android.widget.TextView r1 = r5.tvRecalificacionNuevoDisponibleValue     // Catch:{ Exception -> 0x024b }
                java.lang.CharSequence r1 = r1.getText()     // Catch:{ Exception -> 0x024b }
                java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x024b }
                java.lang.String r0 = r0.applyFilterAmount(r1)     // Catch:{ Exception -> 0x024b }
                r6.setContentDescription(r0)     // Catch:{ Exception -> 0x024b }
                android.widget.TextView r6 = r5.tvRecalificacionNuevoDisponibleValue     // Catch:{ Exception -> 0x024b }
                ar.com.santander.rio.mbanking.app.ui.adapters.RecalificacionesProductsConfirmarAdapter r0 = ar.com.santander.rio.mbanking.app.ui.adapters.RecalificacionesProductsConfirmarAdapter.this     // Catch:{ Exception -> 0x024b }
                android.view.View$AccessibilityDelegate r0 = r0.o     // Catch:{ Exception -> 0x024b }
                r6.setAccessibilityDelegate(r0)     // Catch:{ Exception -> 0x024b }
                goto L_0x024f
            L_0x024b:
                r6 = move-exception
                r6.printStackTrace()
            L_0x024f:
                android.widget.TextView r6 = r5.tvRecalificacionNuevoLimiteValue
                ar.com.santander.rio.mbanking.app.ui.adapters.RecalificacionesProductsConfirmarAdapter r0 = ar.com.santander.rio.mbanking.app.ui.adapters.RecalificacionesProductsConfirmarAdapter.this
                android.view.View$AccessibilityDelegate r0 = r0.o
                r6.setAccessibilityDelegate(r0)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.adapters.RecalificacionesProductsConfirmarAdapter.ItemHolder.a(ar.com.santander.rio.mbanking.services.soap.beans.body.ProductoRecalificacionItem):void");
        }
    }

    public class LegendHolder extends ViewHolder {
        public TextView tvLegendsConfirmarRecalificacion;

        LegendHolder(View view) {
            super(view);
            this.tvLegendsConfirmarRecalificacion = (TextView) view.findViewById(R.id.tvLegendsConfirmarRecalificacion);
        }

        /* access modifiers changed from: 0000 */
        public void a(Leyenda leyenda) {
            this.tvLegendsConfirmarRecalificacion.setText(Html.fromHtml(leyenda.descripcion));
        }
    }

    public class LimitAvailableHolder extends ViewHolder {
        public TextView tvRecalificacionLimiteSinAsignarValue;

        LimitAvailableHolder(View view) {
            super(view);
            this.tvRecalificacionLimiteSinAsignarValue = (TextView) view.findViewById(R.id.tvRecalificacionLimiteSinAsignarValue);
        }

        /* access modifiers changed from: 0000 */
        public void c(int i) {
            this.tvRecalificacionLimiteSinAsignarValue.setText(CFormatterAmounts.getAmountAndCurrencyFromDouble((double) i));
            try {
                this.tvRecalificacionLimiteSinAsignarValue.setContentDescription(CAccessibility.getInstance(RecalificacionesProductsConfirmarAdapter.this.a).applyFilterAmount(this.tvRecalificacionLimiteSinAsignarValue.getText().toString()));
                this.tvRecalificacionLimiteSinAsignarValue.setAccessibilityDelegate(RecalificacionesProductsConfirmarAdapter.this.o);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
            this.functionality_title.setText(R.string.ID_4853_RECALIFICACION_TIT_CONFIRMAR_NUEVOS_LIMITES);
        }
    }

    private boolean a(int i2) {
        return i2 == 0;
    }

    private boolean b(int i2) {
        return i2 == 1;
    }

    public RecalificacionesProductsConfirmarAdapter(Context context, List<ProductoRecalificacionItem> list, List<Leyenda> list2, String str, String str2, int i2) {
        this.a = context;
        this.b = list;
        this.c = list2;
        this.l = str;
        this.m = str2;
        this.n = i2;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i2) {
        switch (i2) {
            case 0:
                return new TitleHolder(LayoutInflater.from(this.a).inflate(R.layout.generic_view_title_layout, viewGroup, false));
            case 1:
                return new HeaderHolder(LayoutInflater.from(this.a).inflate(R.layout.list_item_recalificacion_producto_header_confirmar, viewGroup, false));
            case 2:
                return new ItemHolder(LayoutInflater.from(this.a).inflate(R.layout.list_item_recalificacion_producto_confirmar, viewGroup, false));
            case 3:
                return new LimitAvailableHolder(LayoutInflater.from(this.a).inflate(R.layout.list_item_recalificacion_producto_limite_disponible, viewGroup, false));
            case 4:
                return new LegendHolder(LayoutInflater.from(this.a).inflate(R.layout.recalificacion_confirmar_footer, viewGroup, false));
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
            ((ItemHolder) viewHolder).a((ProductoRecalificacionItem) this.b.get(i2 - 2));
        } else if (viewHolder instanceof LimitAvailableHolder) {
            ((LimitAvailableHolder) viewHolder).c(this.n);
        } else if (viewHolder instanceof LegendHolder) {
            ((LegendHolder) viewHolder).a((Leyenda) this.c.get(i2 - ((this.b.size() + 2) + 1)));
        }
    }

    public int getItemCount() {
        return this.b.size() + 2 + 1 + this.c.size();
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
        return c(i2) ? 3 : 2;
    }

    private boolean c(int i2) {
        return i2 == ((this.b.size() + 2) + 1) - 1;
    }

    private boolean d(int i2) {
        return i2 > ((this.b.size() + 2) + 1) - 1;
    }
}
