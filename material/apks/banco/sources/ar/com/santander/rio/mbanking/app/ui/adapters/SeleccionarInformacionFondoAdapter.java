package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filter.FilterResults;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.constants.FondosConstants;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CategoriaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FondoBean;
import ar.com.santander.rio.mbanking.utils.UtilCurrency;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import java.util.ArrayList;
import java.util.List;

public class SeleccionarInformacionFondoAdapter extends Adapter<ViewHolder> implements Filterable {
    /* access modifiers changed from: private */
    public List<Object> a = new ArrayList();
    /* access modifiers changed from: private */
    public List<Object> b = new ArrayList();
    /* access modifiers changed from: private */
    public OnFondoClicked c;

    class FondoViewHolder extends ViewHolder {
        TextView m;
        TextView n;
        ImageView o;
        RelativeLayout p;

        FondoViewHolder(View view) {
            super(view);
            this.m = (TextView) view.findViewById(R.id.F24_10_lbl_data_nombre_fondo);
            this.n = (TextView) view.findViewById(R.id.F24_10_lbl_data_importe_fondo);
            this.o = (ImageView) view.findViewById(R.id.F24_10_img_navigation);
            this.p = (RelativeLayout) view.findViewById(R.id.relative);
        }

        /* access modifiers changed from: 0000 */
        public void a(FondoBean fondoBean, OnClickListener onClickListener) {
            String str;
            Context context = this.p.getContext();
            this.m.setText(Html.fromHtml(fondoBean.getNombre()));
            this.m.setContentDescription(FondosConstants.applyAccesibilityFilterName(context, fondoBean.getNombre()));
            TextView textView = this.n;
            if (fondoBean.getImporte() == null || fondoBean.getImporte().equals("")) {
                str = "";
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(UtilCurrency.getSimbolCurrencyFromDescription(Html.fromHtml(fondoBean.getMoneda()).toString()));
                sb.append(UtilsCuentas.SEPARAOR2);
                sb.append(fondoBean.getImporte());
                str = sb.toString();
            }
            textView.setText(str);
            try {
                this.n.setContentDescription(CAccessibility.getInstance(context).applyFilterAmount(this.n.getText().toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.p.setOnClickListener(onClickListener);
        }
    }

    public interface OnFondoClicked {
        void onFondoSelected(FondoBean fondoBean);
    }

    public static class SectionViewHolder extends ViewHolder {
        TextView m;

        SectionViewHolder(View view) {
            super(view);
            this.m = (TextView) view.findViewById(R.id.section_text);
        }

        /* access modifiers changed from: 0000 */
        public void a(CategoriaFondosBean categoriaFondosBean) {
            this.m.setText(Html.fromHtml(categoriaFondosBean.getNombreCategoria()));
        }
    }

    public SeleccionarInformacionFondoAdapter(Context context, List<Object> list) {
        this.c = (OnFondoClicked) context;
        this.a.addAll(list);
        this.b.addAll(list);
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        switch (i) {
            case 0:
                return new SectionViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_section_header_fondos, viewGroup, false));
            case 1:
                return new FondoViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_seleccionar_informacion_fondos, viewGroup, false));
            default:
                return null;
        }
    }

    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        if (viewHolder instanceof SectionViewHolder) {
            ((SectionViewHolder) viewHolder).a((CategoriaFondosBean) this.b.get(viewHolder.getAdapterPosition()));
        } else if (viewHolder instanceof FondoViewHolder) {
            ((FondoViewHolder) viewHolder).a((FondoBean) this.b.get(viewHolder.getAdapterPosition()), new OnClickListener() {
                public void onClick(View view) {
                    try {
                        SeleccionarInformacionFondoAdapter.this.c.onFondoSelected((FondoBean) SeleccionarInformacionFondoAdapter.this.b.get(viewHolder.getAdapterPosition()));
                        view.setOnClickListener(null);
                    } catch (Exception e) {
                        e.fillInStackTrace();
                    }
                }
            });
        }
    }

    public int getItemCount() {
        return this.b.size();
    }

    public int getItemViewType(int i) {
        return a(i) ? 0 : 1;
    }

    private boolean a(int i) {
        return this.b.get(i) instanceof CategoriaFondosBean;
    }

    public Filter getFilter() {
        return new Filter() {
            /* access modifiers changed from: protected */
            public FilterResults performFiltering(CharSequence charSequence) {
                ArrayList arrayList = new ArrayList();
                FilterResults filterResults = new FilterResults();
                String trim = charSequence.toString().toLowerCase().trim();
                SeleccionarInformacionFondoAdapter.this.b.clear();
                if (trim.isEmpty()) {
                    SeleccionarInformacionFondoAdapter.this.b.addAll(SeleccionarInformacionFondoAdapter.this.a);
                } else {
                    for (Object next : SeleccionarInformacionFondoAdapter.this.a) {
                        if ((next instanceof CategoriaFondosBean) && Html.fromHtml(((CategoriaFondosBean) next).getNombreCategoria()).toString().toLowerCase().contains(trim.toLowerCase())) {
                            arrayList.add(next);
                        } else if ((next instanceof FondoBean) && Html.fromHtml(((FondoBean) next).getNombre()).toString().toLowerCase().contains(trim.toLowerCase())) {
                            arrayList.add(next);
                        }
                    }
                    SeleccionarInformacionFondoAdapter.this.b.addAll(arrayList);
                }
                return filterResults;
            }

            /* access modifiers changed from: protected */
            public void publishResults(CharSequence charSequence, FilterResults filterResults) {
                SeleccionarInformacionFondoAdapter.this.notifyDataSetChanged();
            }
        };
    }
}
