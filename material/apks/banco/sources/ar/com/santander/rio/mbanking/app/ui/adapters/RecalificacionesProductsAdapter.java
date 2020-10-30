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
import ar.com.santander.rio.mbanking.services.events.GetLimitesProductosEvent;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AvisoItemBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ProductoRecalificacionItem;
import ar.com.santander.rio.mbanking.view.AmountView;
import java.util.List;

public class RecalificacionesProductsAdapter extends Adapter<ViewHolder> {
    private final int a = 0;
    private final int b = 1;
    private final int c = 2;
    private final int d = 3;
    private final int e = 1;
    private final int f = 1;
    private int g;
    /* access modifiers changed from: private */
    public int h;
    /* access modifiers changed from: private */
    public Context i;
    private List<ProductoRecalificacionItem> j;
    /* access modifiers changed from: private */
    public AvisoItemBean k;
    /* access modifiers changed from: private */
    public AccessibilityDelegate l = new AccessibilityDelegate() {
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            accessibilityNodeInfo.setText(view.getContentDescription());
        }
    };

    class RecalificacionesAvisoHolder extends ViewHolder {
        private LinearLayout n;
        private TextView o;
        private TextView p;

        private RecalificacionesAvisoHolder(View view) {
            super(view);
            this.n = (LinearLayout) view.findViewById(R.id.llAvisoMessage);
            this.o = (TextView) view.findViewById(R.id.tvAvisoTitle);
            this.p = (TextView) view.findViewById(R.id.tvAvisoDescription);
        }

        /* access modifiers changed from: private */
        public void t() {
            if (RecalificacionesProductsAdapter.this.k == null || !RecalificacionesProductsAdapter.this.k.mostrarAviso.equalsIgnoreCase("S")) {
                this.n.setVisibility(8);
                return;
            }
            this.n.setVisibility(0);
            this.o.setText(Html.fromHtml(RecalificacionesProductsAdapter.this.k.msjTitulo));
            this.p.setText(Html.fromHtml(RecalificacionesProductsAdapter.this.k.msjDesc));
        }
    }

    class RecalificacionesHeaderHolder extends ViewHolder {
        private AmountView n;
        private TextView o;

        private RecalificacionesHeaderHolder(View view) {
            super(view);
            this.n = (AmountView) view.findViewById(R.id.avLimiteExtraccion);
            this.o = (TextView) view.findViewById(R.id.limiteActualView);
        }

        /* access modifiers changed from: private */
        public void t() {
            this.n.setText(CFormatterAmounts.getAmountAndCurrencyFromDouble((double) RecalificacionesProductsAdapter.this.h));
            this.o.setText(RecalificacionesProductsAdapter.this.i.getString(R.string.ID_4838_RECALIFICACION_LBL_LINEA_TOTAL_CREDITICIA, new Object[]{""}));
            try {
                this.n.setContentDescription(CAccessibility.getInstance(RecalificacionesProductsAdapter.this.i).applyFilterAmount(this.n.getText().toString()));
                this.n.setAccessibilityDelegate(RecalificacionesProductsAdapter.this.l);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class RecalificacionesProductsHolder extends ViewHolder {
        private TextView n;
        private TextView o;
        private TextView p;
        private TextView q;
        private LinearLayout r;
        private LinearLayout s;
        private final String t;
        private final String u;

        private RecalificacionesProductsHolder(View view) {
            super(view);
            this.t = "41";
            this.u = "03";
            this.n = (TextView) view.findViewById(R.id.tvRecalificacionLimiteProductoNombre);
            this.o = (TextView) view.findViewById(R.id.tvRecalificacionLimiteActualValue);
            this.p = (TextView) view.findViewById(R.id.tvRecalificacionLimiteDisponibleValue);
            this.q = (TextView) view.findViewById(R.id.tvMsjDesc);
            this.r = (LinearLayout) view.findViewById(R.id.llLimiteActual);
            this.s = (LinearLayout) view.findViewById(R.id.llLimiteDisponible);
            this.q = (TextView) view.findViewById(R.id.tvMsjDesc);
        }

        /* access modifiers changed from: private */
        public void a(ProductoRecalificacionItem productoRecalificacionItem) {
            this.n.setText(Html.fromHtml(productoRecalificacionItem.getNombreProducto()));
            this.q.setVisibility(8);
            this.r.setVisibility(8);
            this.s.setVisibility(8);
            if (productoRecalificacionItem.getLimiteActualProdNullOrValue() != null) {
                this.r.setVisibility(0);
                this.o.setText(CFormatterAmounts.getAmountAndCurrencyFromDouble((double) productoRecalificacionItem.getLimiteActualProd().intValue()));
                try {
                    this.o.setContentDescription(CAccessibility.getInstance(RecalificacionesProductsAdapter.this.i).applyFilterAmount(this.o.getText().toString()));
                    this.o.setAccessibilityDelegate(RecalificacionesProductsAdapter.this.l);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (productoRecalificacionItem.getDisponibleProdNullOrValue() != null) {
                this.s.setVisibility(0);
                this.p.setText(CFormatterAmounts.getAmountAndCurrencyFromDouble((double) productoRecalificacionItem.getDisponibleProd().intValue()));
                try {
                    this.p.setContentDescription(CAccessibility.getInstance(RecalificacionesProductsAdapter.this.i).applyFilterAmount(this.p.getText().toString()));
                    this.p.setAccessibilityDelegate(RecalificacionesProductsAdapter.this.l);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            if (productoRecalificacionItem.getMensajeLimite() != null && !productoRecalificacionItem.getMensajeLimite().msjDesc.isEmpty()) {
                this.q.setVisibility(0);
                this.q.setText(Html.fromHtml(productoRecalificacionItem.getMensajeLimite().msjDesc));
            }
        }
    }

    public class RecalificacionesTitleHolder extends ViewHolder {
        public TextView functionality_title;

        private RecalificacionesTitleHolder(View view) {
            super(view);
            this.functionality_title = (TextView) view.findViewById(R.id.functionality_title);
        }

        /* access modifiers changed from: private */
        public void t() {
            this.functionality_title.setText(R.string.ID_4837_RECALIFICACION_TIT_TUS_LIMITES);
        }
    }

    private boolean a(int i2) {
        return i2 == 0;
    }

    private boolean b(int i2) {
        return i2 == 1;
    }

    public RecalificacionesProductsAdapter(Context context, GetLimitesProductosEvent getLimitesProductosEvent, List<ProductoRecalificacionItem> list) {
        int i2 = 0;
        this.i = context;
        this.j = list;
        this.k = getLimitesProductosEvent.getLimitesProductosResponseBean.getLimitesProductosBodyResponseBean.aviso;
        this.h = getLimitesProductosEvent.getLimitesProductosResponseBean.getLimitesProductosBodyResponseBean.limiteTotalActual;
        if (this.k != null && this.k.mostrarAviso.equalsIgnoreCase("S")) {
            i2 = 1;
        }
        this.g = i2;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i2) {
        switch (i2) {
            case 0:
                return new RecalificacionesTitleHolder(LayoutInflater.from(this.i).inflate(R.layout.generic_view_title_layout, viewGroup, false));
            case 1:
                return new RecalificacionesHeaderHolder(LayoutInflater.from(this.i).inflate(R.layout.head_item_recalificacion_producto, viewGroup, false));
            case 2:
                return new RecalificacionesProductsHolder(LayoutInflater.from(this.i).inflate(R.layout.list_item_recalificacion_producto, viewGroup, false));
            case 3:
                return new RecalificacionesAvisoHolder(LayoutInflater.from(this.i).inflate(R.layout.type_info_footer_recalificacion_producto, viewGroup, false));
            default:
                return null;
        }
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i2) {
        if (viewHolder instanceof RecalificacionesTitleHolder) {
            ((RecalificacionesTitleHolder) viewHolder).t();
        } else if (viewHolder instanceof RecalificacionesHeaderHolder) {
            ((RecalificacionesHeaderHolder) viewHolder).t();
        } else if (viewHolder instanceof RecalificacionesProductsHolder) {
            ((RecalificacionesProductsHolder) viewHolder).a((ProductoRecalificacionItem) this.j.get(i2 - 2));
        } else if (viewHolder instanceof RecalificacionesAvisoHolder) {
            ((RecalificacionesAvisoHolder) viewHolder).t();
        }
    }

    public int getItemCount() {
        return this.j.size() + 2 + this.g;
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
        boolean z = false;
        if (this.k == null || !this.k.mostrarAviso.equalsIgnoreCase("S")) {
            return false;
        }
        if (i2 == getItemCount() - 1) {
            z = true;
        }
        return z;
    }
}
