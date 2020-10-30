package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.PlazosFijoFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.TenenciaInversionesIntervinientesActivity;
import ar.com.santander.rio.mbanking.services.soap.beans.body.Cuenta;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PlazoFijo;
import ar.com.santander.rio.mbanking.services.soap.beans.body.Producto;
import java.util.ArrayList;
import java.util.List;

public class TenenciaInversionesCuentasAdapter extends Adapter<ViewHolder> {
    private final int a = 0;
    private final int b = 1;
    private List<Cuenta> c = new ArrayList();
    private PlazoFijo d;
    private ChildClickListener e;
    /* access modifiers changed from: private */
    public String f;

    public interface ChildClickListener {
        void onClick(View view, int i, Cuenta cuenta, Producto producto);
    }

    public class CuentaExpandViewHolder extends ViewHolder {
        private TextView A;
        private TextView B;
        /* access modifiers changed from: private */
        public HorizontalScrollView C;
        /* access modifiers changed from: private */
        public LinearLayout D;
        /* access modifiers changed from: private */
        public ChildClickListener E;
        private RelativeLayout n;
        private TextView o;
        private TextView p;
        private TextView q;
        private TextView r;
        /* access modifiers changed from: private */
        public Context s;
        /* access modifiers changed from: private */
        public ImageView t;
        private LinearLayout u;
        private LinearLayout v;
        private TextView w;
        private TextView x;
        private TextView y;
        private TextView z;

        public CuentaExpandViewHolder(View view, Context context, ChildClickListener childClickListener) {
            super(view);
            this.n = (RelativeLayout) view.findViewById(R.id.expandable_recyclerview_cuenta);
            this.o = (TextView) view.findViewById(R.id.tvNroDeCuenta);
            this.p = (TextView) view.findViewById(R.id.tvIntervinientesCuenta);
            this.q = (TextView) view.findViewById(R.id.tvTotalPesosCuenta);
            this.r = (TextView) view.findViewById(R.id.tvTotalDolaresCuenta);
            this.D = (LinearLayout) view.findViewById(R.id.llContenedor_productos);
            this.u = (LinearLayout) view.findViewById(R.id.layout_pesos);
            this.v = (LinearLayout) view.findViewById(R.id.layout_dolares);
            this.w = (TextView) view.findViewById(R.id.tv_custodia_tenencia_dolares_label_1);
            this.x = (TextView) view.findViewById(R.id.tv_custodia_tenencia_dolares_label_2);
            this.y = (TextView) view.findViewById(R.id.tv_custodia_tenencia_dolares_label_3);
            this.z = (TextView) view.findViewById(R.id.tv_custodia_tenencia_pesos_label_1);
            this.A = (TextView) view.findViewById(R.id.tv_custodia_tenencia_pesos_label_2);
            this.B = (TextView) view.findViewById(R.id.tv_custodia_tenencia_pesos_label_3);
            this.C = (HorizontalScrollView) view.findViewById(R.id.scroll_tenencias);
            this.t = (ImageView) view.findViewById(R.id.downArrow);
            this.s = context;
            this.E = childClickListener;
        }

        public void bindData(final Cuenta cuenta) {
            if (cuenta.getNroCta() != null) {
                this.o.setText(Html.fromHtml(cuenta.getNroCta()));
                try {
                    this.o.setContentDescription(new CAccessibility(this.s).applyFilterAccount(this.o.getText().toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (cuenta.getIntervinientes() != null) {
                String[] split = cuenta.getIntervinientes().split("/");
                if (split.length == 1) {
                    this.p.setText(Html.fromHtml(cuenta.getIntervinientes()));
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append(split[0]);
                    sb.append(" ...ver todos");
                    SpannableString spannableString = new SpannableString(sb.toString());
                    spannableString.setSpan(new ClickableSpan() {
                        public void onClick(@NonNull View view) {
                        }
                    }, spannableString.toString().length() - 9, spannableString.toString().length(), 33);
                    this.p.setText(spannableString);
                    this.p.setOnClickListener(new OnClickListener() {
                        public void onClick(View view) {
                            CuentaExpandViewHolder.this.gotoIntegrantesInversion(cuenta.getIntervinientes(), cuenta.getNroCta());
                        }
                    });
                }
            }
            if (cuenta.getTotalPesos() != null) {
                this.q.setText(Html.fromHtml(cuenta.getTotalPesos()));
                try {
                    this.q.setContentDescription(new CAccessibility(this.s).applyFilterAmount(this.q.getText().toString()));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            if (cuenta.getTotalDolares() != null) {
                this.r.setText(Html.fromHtml(cuenta.getTotalDolares()));
                try {
                    this.r.setContentDescription(new CAccessibility(this.s).applyFilterAmount(this.r.getText().toString()));
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            if (cuenta.getProductos() != null) {
                this.D.removeAllViews();
                for (final int i = 0; i < cuenta.getProductos().getProductoList().size(); i++) {
                    final Producto producto = (Producto) cuenta.getProductos().getProductoList().get(i);
                    View inflate = LayoutInflater.from(this.s).inflate(R.layout.item_producto, null);
                    ((TextView) inflate.findViewById(R.id.tvdescripcionProducto)).setText(Html.fromHtml(producto.getDescripcion()));
                    ((TextView) inflate.findViewById(R.id.tvTotalPesos)).setText(Html.fromHtml(producto.getImportePesos()));
                    ((TextView) inflate.findViewById(R.id.tvTotalDolares)).setText(Html.fromHtml(producto.getImporteDolares()));
                    inflate.setOnClickListener(new OnClickListener() {
                        public void onClick(View view) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("Position: ");
                            sb.append(i);
                            sb.append(" CodProducto: ");
                            sb.append(producto.getCodProducto());
                            Log.d("Child:", sb.toString());
                            CuentaExpandViewHolder.this.E.onClick(view, CuentaExpandViewHolder.this.getAdapterPosition(), cuenta, producto);
                        }
                    });
                    this.D.addView(inflate);
                }
                this.n.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (CuentaExpandViewHolder.this.D.getVisibility() != 0) {
                            Animation loadAnimation = AnimationUtils.loadAnimation(CuentaExpandViewHolder.this.s, R.anim.rotate_row_image_right);
                            CuentaExpandViewHolder.this.t.startAnimation(loadAnimation);
                            loadAnimation.setFillAfter(true);
                            CuentaExpandViewHolder.this.D.setVisibility(0);
                            CuentaExpandViewHolder.this.C.setVisibility(0);
                            return;
                        }
                        Animation loadAnimation2 = AnimationUtils.loadAnimation(CuentaExpandViewHolder.this.s, R.anim.rotate_row_image_down);
                        CuentaExpandViewHolder.this.t.startAnimation(loadAnimation2);
                        loadAnimation2.setFillAfter(true);
                        CuentaExpandViewHolder.this.D.setVisibility(8);
                        CuentaExpandViewHolder.this.C.setVisibility(8);
                    }
                });
                this.w.setText(cuenta.getTenReexpresada().getPesos().getExpreDolares());
                this.x.setText(cuenta.getTenReexpresada().getPesos().getTotalDolares());
                this.y.setText(cuenta.getTenReexpresada().getPesos().getTotalExpreDolares());
                this.z.setText(cuenta.getTenReexpresada().getDolar().getExprePesos());
                this.A.setText(cuenta.getTenReexpresada().getDolar().getTotalPesos());
                this.B.setText(cuenta.getTenReexpresada().getDolar().getTotalExprePesos());
                return;
            }
            this.D.removeAllViews();
            View inflate2 = LayoutInflater.from(this.s).inflate(R.layout.generic_error_res4, null);
            if (cuenta.getMensajeCta() != null) {
                ((TextView) inflate2.findViewById(R.id.title_error)).setText(cuenta.getMensajeCta());
                ((ImageView) inflate2.findViewById(R.id.image_error)).setImageResource(R.drawable.error_continuacion);
            } else {
                ((TextView) inflate2.findViewById(R.id.title_error)).setText(R.string.ERROR_NO_HAY_TENENCIA);
                ((ImageView) inflate2.findViewById(R.id.image_error)).setImageResource(R.drawable.error_empty_fetch);
            }
            this.D.addView(inflate2);
            this.C.setVisibility(8);
            this.n.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (CuentaExpandViewHolder.this.D.getVisibility() != 0) {
                        Animation loadAnimation = AnimationUtils.loadAnimation(CuentaExpandViewHolder.this.s, R.anim.rotate_row_image_right);
                        CuentaExpandViewHolder.this.t.startAnimation(loadAnimation);
                        loadAnimation.setFillAfter(true);
                        CuentaExpandViewHolder.this.D.setVisibility(0);
                        return;
                    }
                    Animation loadAnimation2 = AnimationUtils.loadAnimation(CuentaExpandViewHolder.this.s, R.anim.rotate_row_image_down);
                    CuentaExpandViewHolder.this.t.startAnimation(loadAnimation2);
                    loadAnimation2.setFillAfter(true);
                    CuentaExpandViewHolder.this.D.setVisibility(8);
                }
            });
        }

        public void gotoIntegrantesInversion(String str, String str2) {
            Intent intent = new Intent(this.s, TenenciaInversionesIntervinientesActivity.class);
            intent.putExtra(TenenciaInversionesIntervinientesActivity.TAG_INTERVINIENTES, str);
            intent.putExtra("CUENTA", str2);
            intent.putExtra("TITLE", TenenciaInversionesCuentasAdapter.this.f);
            this.s.startActivity(intent);
        }
    }

    public class PlazoFijoViewHolder extends ViewHolder {
        private TextView n;
        private TextView o;
        private TextView p;
        private Context q;

        public PlazoFijoViewHolder(View view, final Context context) {
            super(view);
            this.n = (TextView) view.findViewById(R.id.tvdescripcionPlazoFijo);
            this.o = (TextView) view.findViewById(R.id.tvTotalPesosPlazoFijo);
            this.p = (TextView) view.findViewById(R.id.tvTotalDolaresPlazoFijo);
            this.q = context;
            view.setOnClickListener(new OnClickListener(TenenciaInversionesCuentasAdapter.this) {
                public void onClick(View view) {
                    try {
                        ((SantanderRioMainActivity) context).changeFragmentAnimation(PlazosFijoFragment.newInstance("RTL", FragmentConstants.PLAZOS_FIJOS), R.anim.slide_in_right, R.anim.slide_out_left, R.anim.no_animation, R.anim.slide_out_right);
                    } catch (Exception unused) {
                        Log.d("Service", "Implementacion erronea");
                    }
                }
            });
        }

        /* access modifiers changed from: private */
        public void a(PlazoFijo plazoFijo) {
            if (plazoFijo.getImportePesos() != null) {
                this.o.setText(Html.fromHtml(plazoFijo.getImportePesos()));
                try {
                    this.o.setContentDescription(new CAccessibility(this.q).applyFilterAmount(this.o.getText().toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (plazoFijo.getImporteDolares() != null) {
                this.p.setText(Html.fromHtml(plazoFijo.getImporteDolares()));
                try {
                    this.p.setContentDescription(new CAccessibility(this.q).applyFilterAmount(this.p.getText().toString()));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public TenenciaInversionesCuentasAdapter(List<Cuenta> list, ChildClickListener childClickListener, String str) {
        this.c = list;
        this.e = childClickListener;
        this.f = str;
    }

    public TenenciaInversionesCuentasAdapter(List<Cuenta> list, PlazoFijo plazoFijo, ChildClickListener childClickListener, String str) {
        this.c = list;
        this.d = plazoFijo;
        this.e = childClickListener;
        this.f = str;
    }

    public void clear() {
        if (this.c != null) {
            this.c.clear();
        }
        notifyDataSetChanged();
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        switch (i) {
            case 0:
                return new PlazoFijoViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.plazo_fijo_item_view, viewGroup, false), viewGroup.getContext());
            case 1:
                return new CuentaExpandViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.expandable_recyclerview_cuenta, viewGroup, false), viewGroup.getContext(), this.e);
            default:
                return null;
        }
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Cuenta cuenta;
        if (viewHolder instanceof CuentaExpandViewHolder) {
            if (this.d != null) {
                cuenta = (Cuenta) this.c.get(i - 1);
            } else {
                cuenta = (Cuenta) this.c.get(i);
            }
            ((CuentaExpandViewHolder) viewHolder).bindData(cuenta);
        } else if (viewHolder instanceof PlazoFijoViewHolder) {
            ((PlazoFijoViewHolder) viewHolder).a(this.d);
        }
    }

    public int getItemCount() {
        if (this.d != null) {
            return this.c.size() + 1;
        }
        return this.c.size();
    }

    public int getItemViewType(int i) {
        return a(i) ? 0 : 1;
    }

    private boolean a(int i) {
        return this.d != null && i == 0;
    }
}
