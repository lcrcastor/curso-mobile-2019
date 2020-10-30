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
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.CFormatterAmounts;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CFiltersAccessibility;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.app.ui.utils.CustomSeekBar;
import ar.com.santander.rio.mbanking.app.ui.utils.CustomSeekBar.SeekBarEvents;
import ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ProductoRecalificacionItem;
import ar.com.santander.rio.mbanking.services.soap.beans.body.Variacion;
import ar.com.santander.rio.mbanking.view.AmountView;
import java.util.ArrayList;
import java.util.List;

public class RecalificacionesDistribucionLimitesAdapter extends Adapter<ViewHolder> {
    /* access modifiers changed from: private */
    public final Context a;
    /* access modifiers changed from: private */
    public final int b;
    private List<ProductoRecalificacionItem> c = new ArrayList();
    private List<Leyenda> d = new ArrayList();
    /* access modifiers changed from: private */
    public int e;
    /* access modifiers changed from: private */
    public int f;
    /* access modifiers changed from: private */
    public String g;
    /* access modifiers changed from: private */
    public AccessibilityDelegate h = new AccessibilityDelegate() {
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            accessibilityNodeInfo.setText(view.getContentDescription());
        }
    };
    /* access modifiers changed from: private */
    public Event i;

    public interface Event {
        void onVariacionChange(int i);
    }

    class HeaderHolder extends ViewHolder {
        private TextView n;
        private TextView o;
        private AmountView p;

        HeaderHolder(View view) {
            super(view);
            this.n = (TextView) view.findViewById(R.id.tvNuevaLineaTitle);
            this.o = (TextView) view.findViewById(R.id.tvLimiteSinAsignar);
            this.p = (AmountView) view.findViewById(R.id.avLimiteTotalCrediticio);
        }

        /* access modifiers changed from: 0000 */
        public void t() {
            this.n.setText(RecalificacionesDistribucionLimitesAdapter.this.g);
            this.p.setText(CFormatterAmounts.getAmountAndCurrencyFromDouble((double) RecalificacionesDistribucionLimitesAdapter.this.b));
            this.o.setText(RecalificacionesDistribucionLimitesAdapter.this.a.getString(R.string.ID_4849_RECALIFICACION_LBL_LIMITE_SIN_ASIGNAR_WITH_VALUE, new Object[]{CFormatterAmounts.getAmountAndCurrencyFromDouble((double) RecalificacionesDistribucionLimitesAdapter.this.e)}));
            try {
                this.o.setContentDescription(CAccessibility.getInstance(RecalificacionesDistribucionLimitesAdapter.this.a).applyFilterAmount(this.o.getText().toString()));
                this.p.setContentDescription(CAccessibility.getInstance(RecalificacionesDistribucionLimitesAdapter.this.a).applyFilterAmount(this.p.getText().toString()));
                this.p.setAccessibilityDelegate(RecalificacionesDistribucionLimitesAdapter.this.h);
                this.o.setAccessibilityDelegate(RecalificacionesDistribucionLimitesAdapter.this.h);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class ItemHolder extends ViewHolder {
        private final String A = "41";
        private final String B = TarjetasConstants.CODIGO_TARJETA_AMEX;
        private final String C = "02";
        private final String D = "03";
        private TextView n;
        private LinearLayout o;
        private TextView p;
        /* access modifiers changed from: private */
        public TextView q;
        private LinearLayout r;
        /* access modifiers changed from: private */
        public TextView s;
        /* access modifiers changed from: private */
        public LinearLayout t;
        private RelativeLayout u;
        private TextView v;
        private TextView w;
        private TextView x;
        private Context y;
        private final String z = "40";

        ItemHolder(View view, Context context) {
            super(view);
            this.y = context;
            this.n = (TextView) view.findViewById(R.id.tvRecalificacionLimiteProductoNombre);
            this.o = (LinearLayout) view.findViewById(R.id.llLimiteActual);
            this.p = (TextView) view.findViewById(R.id.tvRecalificacionLimiteActualLabel);
            this.q = (TextView) view.findViewById(R.id.tvRecalificacionLimiteActualValue);
            this.r = (LinearLayout) view.findViewById(R.id.llLimiteDisponible);
            this.u = (RelativeLayout) view.findViewById(R.id.rlMinimosMaximos);
            this.s = (TextView) view.findViewById(R.id.tvRecalificacionLimiteDisponibleValue);
            this.t = (LinearLayout) view.findViewById(R.id.sbLimites);
            this.v = (TextView) view.findViewById(R.id.tvMinimos);
            this.w = (TextView) view.findViewById(R.id.tvMaximos);
            this.x = (TextView) view.findViewById(R.id.tvMsjDesc);
        }

        /* access modifiers changed from: 0000 */
        public void a(final ProductoRecalificacionItem productoRecalificacionItem) {
            int i;
            int i2;
            int i3;
            final CFiltersAccessibility cFiltersAccessibility = new CFiltersAccessibility(RecalificacionesDistribucionLimitesAdapter.this.a);
            this.n.setText(Html.fromHtml(productoRecalificacionItem.getNombreProducto()));
            this.p.setText(R.string.ID_4850_RECALIFICACION_LBL_NUEVO_LIMITE);
            TextView textView = this.q;
            if (productoRecalificacionItem.getLimiteActualProd().intValue() == productoRecalificacionItem.getNuevoLimiteProductoAux()) {
                i = productoRecalificacionItem.getLimiteActualProd().intValue();
            } else {
                i = productoRecalificacionItem.getNuevoLimiteProductoAux();
            }
            textView.setText(CFormatterAmounts.getAmountAndCurrencyFromDouble((double) i));
            try {
                this.q.setContentDescription(cFiltersAccessibility.filterAmount(this.q.getText().toString()));
                this.q.setAccessibilityDelegate(RecalificacionesDistribucionLimitesAdapter.this.h);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.o.setVisibility(0);
            this.r.setVisibility(0);
            if (productoRecalificacionItem.getIdProducto().equalsIgnoreCase("03")) {
                this.o.setVisibility(8);
                this.r.setVisibility(8);
            }
            if (productoRecalificacionItem.getIdProducto().equalsIgnoreCase("02")) {
                this.r.setVisibility(8);
            }
            if (productoRecalificacionItem.getIdProducto().equalsIgnoreCase("40")) {
                this.r.setVisibility(8);
            }
            if (productoRecalificacionItem.getIdProducto().equalsIgnoreCase("40") || productoRecalificacionItem.getIdProducto().equalsIgnoreCase(TarjetasConstants.CODIGO_TARJETA_AMEX)) {
                this.p.setText(R.string.ID_4850_RECALIFICACION_LBL_NUEVO_LIMITE_1);
                this.r.setVisibility(8);
            } else if (productoRecalificacionItem.getIdProducto().equalsIgnoreCase("41")) {
                this.p.setText(R.string.ID_4839_RECALIFICACION_LBL_LIMITE_ACTUAL);
                this.r.setVisibility(8);
            }
            if (productoRecalificacionItem.getLimiteActualProd().intValue() == productoRecalificacionItem.getNuevoLimiteProductoAux()) {
                i2 = productoRecalificacionItem.getLimiteActualProd().intValue();
            } else {
                i2 = productoRecalificacionItem.getNuevoLimiteProductoAux();
            }
            int intValue = (i2 - productoRecalificacionItem.getLimiteActualProd().intValue()) + productoRecalificacionItem.getDisponibleProd().intValue();
            TextView textView2 = this.s;
            if (intValue <= 0) {
                intValue = 0;
            }
            textView2.setText(CFormatterAmounts.getAmountAndCurrencyFromDouble((double) intValue));
            try {
                this.s.setContentDescription(cFiltersAccessibility.filterAmount(this.s.getText().toString()));
                this.s.setAccessibilityDelegate(RecalificacionesDistribucionLimitesAdapter.this.h);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            if (productoRecalificacionItem.getMostrarSlider() != null && productoRecalificacionItem.getMostrarSlider().equalsIgnoreCase("S")) {
                final CustomSeekBar customSeekBar = new CustomSeekBar(this.y);
                this.t.setVisibility(0);
                this.u.setVisibility(0);
                this.x.setVisibility(8);
                this.v.setText(CFormatterAmounts.getAmountAndCurrencyFromDouble((double) productoRecalificacionItem.getLimiteMinimo()));
                this.w.setText(CFormatterAmounts.getAmountAndCurrencyFromDouble((double) productoRecalificacionItem.getLimiteMaximo()));
                try {
                    TextView textView3 = this.v;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Mínimo ");
                    sb.append(cFiltersAccessibility.filterAmount(this.v.getText().toString()));
                    textView3.setContentDescription(sb.toString());
                    TextView textView4 = this.w;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Máximo ");
                    sb2.append(cFiltersAccessibility.filterAmount(this.w.getText().toString()));
                    textView4.setContentDescription(sb2.toString());
                    this.v.setAccessibilityDelegate(RecalificacionesDistribucionLimitesAdapter.this.h);
                    this.w.setAccessibilityDelegate(RecalificacionesDistribucionLimitesAdapter.this.h);
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
                if (productoRecalificacionItem.getListaVariacion() == null || productoRecalificacionItem.getListaVariacion().variacion == null) {
                    customSeekBar.setMinMaxRange(productoRecalificacionItem.getLimiteMinimo(), productoRecalificacionItem.getLimiteMaximo(), productoRecalificacionItem.getRangoVariacion());
                } else {
                    ArrayList arrayList = new ArrayList();
                    for (Variacion variacion : productoRecalificacionItem.getListaVariacion().variacion) {
                        arrayList.add(Integer.valueOf(variacion.valor));
                    }
                    customSeekBar.setRange(arrayList);
                }
                if (productoRecalificacionItem.getLimiteActualProd().intValue() == productoRecalificacionItem.getNuevoLimiteProductoAux()) {
                    i3 = productoRecalificacionItem.getLimiteActualProd().intValue();
                } else {
                    i3 = productoRecalificacionItem.getNuevoLimiteProductoAux();
                }
                customSeekBar.showLabels(false).addSeekBar(this.t).setMax().setMaxValueAllowed(i3 + RecalificacionesDistribucionLimitesAdapter.this.f).setEvents(new SeekBarEvents() {
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                        int currentValue = (customSeekBar.getCurrentValue() - productoRecalificacionItem.getLimiteActualProd().intValue()) + productoRecalificacionItem.getDisponibleProd().intValue();
                        String amountAndCurrencyFromDouble = CFormatterAmounts.getAmountAndCurrencyFromDouble((double) customSeekBar.getCurrentValue());
                        String filterAmount = cFiltersAccessibility.filterAmount(amountAndCurrencyFromDouble);
                        if (currentValue <= 0) {
                            currentValue = 0;
                        }
                        String amountAndCurrencyFromDouble2 = CFormatterAmounts.getAmountAndCurrencyFromDouble((double) currentValue);
                        ItemHolder.this.q.setText(amountAndCurrencyFromDouble);
                        ItemHolder.this.q.setContentDescription(filterAmount);
                        ItemHolder.this.q.setAccessibilityDelegate(RecalificacionesDistribucionLimitesAdapter.this.h);
                        ItemHolder.this.s.setText(amountAndCurrencyFromDouble2);
                        ItemHolder.this.s.setContentDescription(cFiltersAccessibility.filterAmount(amountAndCurrencyFromDouble2));
                        ItemHolder.this.s.setAccessibilityDelegate(RecalificacionesDistribucionLimitesAdapter.this.h);
                        ItemHolder.this.t.setContentDescription(filterAmount);
                        seekBar.setContentDescription(filterAmount);
                    }

                    public void onStopTrackingTouch(SeekBar seekBar) {
                        RecalificacionesDistribucionLimitesAdapter.this.f = RecalificacionesDistribucionLimitesAdapter.this.f + (productoRecalificacionItem.getNuevoLimiteProductoAux() - customSeekBar.getCurrentValue());
                        productoRecalificacionItem.setNuevoLimiteProducto(String.valueOf(customSeekBar.getCurrentValue()));
                        productoRecalificacionItem.setNuevoLimiteProductoAux(customSeekBar.getCurrentValue());
                        customSeekBar.setMaxValueAllowed(productoRecalificacionItem.getNuevoLimiteProductoAux() + RecalificacionesDistribucionLimitesAdapter.this.f);
                        RecalificacionesDistribucionLimitesAdapter.this.i.onVariacionChange(RecalificacionesDistribucionLimitesAdapter.this.f);
                    }
                }).setCurrentValue(productoRecalificacionItem.getLimiteActualProd().intValue() == productoRecalificacionItem.getNuevoLimiteProductoAux() ? productoRecalificacionItem.getLimiteActualProd().intValue() : productoRecalificacionItem.getNuevoLimiteProductoAux());
                String filterAmount = cFiltersAccessibility.filterAmount(CFormatterAmounts.getAmountAndCurrencyFromDouble((double) customSeekBar.getCurrentValue()));
                customSeekBar.getSeekBar().setContentDescription(filterAmount);
                customSeekBar.getSeekBar().setAccessibilityDelegate(RecalificacionesDistribucionLimitesAdapter.this.h);
                customSeekBar.getSeekBar().setImportantForAccessibility(2);
                this.t.setContentDescription(filterAmount);
                this.t.setAccessibilityDelegate(RecalificacionesDistribucionLimitesAdapter.this.h);
            } else if (productoRecalificacionItem.getMostrarSlider() != null && productoRecalificacionItem.getMostrarSlider().equalsIgnoreCase("N")) {
                this.t.setVisibility(8);
                this.u.setVisibility(8);
                this.x.setVisibility(8);
                if (productoRecalificacionItem.getMensajeRecalificacion() != null && !productoRecalificacionItem.getMensajeRecalificacion().msjDesc.isEmpty()) {
                    this.x.setVisibility(0);
                    this.x.setText(Html.fromHtml(productoRecalificacionItem.getMensajeRecalificacion().msjDesc));
                }
            }
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
            this.functionality_title.setText(R.string.ID_4847_RECALIFICACION_TIT_DISTRIBUIR_LINEA_Y_ASIGNAR_NUEVOS_LIMITES);
        }
    }

    private boolean a(int i2) {
        return i2 == 0;
    }

    private boolean b(int i2) {
        return i2 == 1;
    }

    public RecalificacionesDistribucionLimitesAdapter(Event event, int i2, Context context) {
        this.i = event;
        this.a = context;
        this.b = i2;
    }

    public void setTitle(String str) {
        this.g = str;
    }

    public void updateList(List<ProductoRecalificacionItem> list, List<Leyenda> list2, int i2, int i3) {
        this.c.addAll(list);
        this.d.addAll(list2);
        this.e = i2;
        this.f = i3;
    }

    public void updateNotAssignedLimit(int i2) {
        this.e = i2;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i2) {
        switch (i2) {
            case 0:
                return new TitleHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.generic_view_title_layout, viewGroup, false));
            case 1:
                return new HeaderHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_recalificacion_distribucion_limites_header, viewGroup, false));
            case 2:
                return new ItemHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_recalificacion_distribucion_limites_producto, viewGroup, false), viewGroup.getContext());
            case 3:
                return new LegendHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recalificacion_comprobante_footer, viewGroup, false));
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
            ((ItemHolder) viewHolder).a((ProductoRecalificacionItem) this.c.get(i2 - 2));
        } else if (viewHolder instanceof LegendHolder) {
            ((LegendHolder) viewHolder).a((Leyenda) this.d.get(i2 - (this.c.size() + 2)));
        }
    }

    public int getItemCount() {
        return this.c.size() + 2 + this.d.size();
    }

    public int getItemViewType(int i2) {
        if (a(i2)) {
            return 0;
        }
        if (b(i2)) {
            return 1;
        }
        return c(i2) ? 3 : 2;
    }

    private boolean c(int i2) {
        return i2 > (this.c.size() + 2) - 1;
    }
}
