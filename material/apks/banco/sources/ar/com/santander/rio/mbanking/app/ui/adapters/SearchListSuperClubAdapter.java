package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filter.FilterResults;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.components.OnSingleClickListener;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CategoriaSuperClubBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CategoriasSuperClubBean;
import java.util.ArrayList;
import java.util.List;

public class SearchListSuperClubAdapter extends Adapter<SearchListSuperClubViewHolder> implements Filterable {
    public static final int NO_MATCHES = 0;
    public static final int SEARCH_HAS_RESULTS = 1;
    /* access modifiers changed from: private */
    public Context a;
    /* access modifiers changed from: private */
    public OnSearchItemClickListener b;
    /* access modifiers changed from: private */
    public String c;
    /* access modifiers changed from: private */
    public int d = 0;
    public List<CategoriaSuperClubBean> mData;
    public List<CategoriaSuperClubBean> mFilteredData;

    public interface OnSearchItemClickListener {
        void onSearchItemClick(View view);

        void onSearchNoResultsItemClick();
    }

    static class SearchFilter extends Filter {
        private final SearchListSuperClubAdapter a;
        private final List<CategoriaSuperClubBean> b;
        private final List<CategoriaSuperClubBean> c;

        private SearchFilter(SearchListSuperClubAdapter searchListSuperClubAdapter, List<CategoriaSuperClubBean> list) {
            this.a = searchListSuperClubAdapter;
            this.b = list;
            this.c = new ArrayList();
        }

        /* access modifiers changed from: protected */
        public FilterResults performFiltering(CharSequence charSequence) {
            this.c.clear();
            FilterResults filterResults = new FilterResults();
            if (charSequence.length() == 0) {
                this.c.addAll(this.b);
            } else {
                this.a.d = 0;
                String trim = charSequence.toString().toLowerCase().trim();
                for (CategoriaSuperClubBean categoriaSuperClubBean : this.b) {
                    if (categoriaSuperClubBean.nombre.toLowerCase().contains(trim)) {
                        this.a.d = 1;
                        this.c.add(categoriaSuperClubBean);
                    }
                }
            }
            this.a.c = charSequence.toString();
            if (this.c.size() == 0) {
                List<CategoriaSuperClubBean> list = this.c;
                CategoriaSuperClubBean categoriaSuperClubBean2 = new CategoriaSuperClubBean("", "", this.a.a.getString(R.string.IDXX_COMODINES_SUPER_CLUB_BUSQ_SIN_RESULTADOS), "", "", "", new CategoriasSuperClubBean());
                list.add(categoriaSuperClubBean2);
            }
            filterResults.values = this.c;
            filterResults.count = this.c.size();
            return filterResults;
        }

        /* access modifiers changed from: protected */
        public void publishResults(CharSequence charSequence, FilterResults filterResults) {
            this.a.mFilteredData.clear();
            this.a.mFilteredData.addAll((List) filterResults.values);
            this.a.notifyDataSetChanged();
        }
    }

    public static class SearchListSuperClubViewHolder extends ViewHolder {
        private final SearchListSuperClubAdapter m;
        private TextView n;

        public SearchListSuperClubViewHolder(View view, SearchListSuperClubAdapter searchListSuperClubAdapter) {
            super(view);
            this.m = searchListSuperClubAdapter;
            this.n = (TextView) view.findViewById(R.id.F20_00_LBL_SEARCH_ITEM);
        }

        public void bindItem(CategoriaSuperClubBean categoriaSuperClubBean) {
            if (TextUtils.isEmpty(this.m.c)) {
                this.n.setText(Html.fromHtml(categoriaSuperClubBean.nombre));
                return;
            }
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            spannableStringBuilder.append(Html.fromHtml(categoriaSuperClubBean.nombre));
            ArrayList<Integer> arrayList = new ArrayList<>();
            int indexOf = categoriaSuperClubBean.nombre.toLowerCase().indexOf(this.m.c);
            while (indexOf >= 0) {
                arrayList.add(Integer.valueOf(indexOf));
                indexOf = categoriaSuperClubBean.nombre.indexOf(this.m.c, indexOf + 1);
            }
            int length = this.m.c.length();
            for (Integer num : arrayList) {
                spannableStringBuilder.setSpan(new ForegroundColorSpan(this.m.a.getResources().getColor(R.color.red_light)), num.intValue(), num.intValue() + length, 33);
            }
            this.n.setText(spannableStringBuilder, BufferType.SPANNABLE);
            this.n.setContentDescription(categoriaSuperClubBean.nombre);
        }
    }

    public SearchListSuperClubAdapter(Context context, List<CategoriaSuperClubBean> list) {
        this.a = context;
        this.mData = new ArrayList();
        for (CategoriaSuperClubBean categoriaSuperClubBean : list) {
            categoriaSuperClubBean.nombre = Html.fromHtml(categoriaSuperClubBean.nombre).toString();
            this.mData.add(categoriaSuperClubBean);
        }
        this.mFilteredData = new ArrayList();
        for (CategoriaSuperClubBean categoriaSuperClubBean2 : list) {
            categoriaSuperClubBean2.nombre = Html.fromHtml(categoriaSuperClubBean2.nombre).toString();
            this.mFilteredData.add(categoriaSuperClubBean2);
        }
    }

    public void setOnSearchItemClickListener(OnSearchItemClickListener onSearchItemClickListener) {
        this.b = onSearchItemClickListener;
    }

    public SearchListSuperClubViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_item_list_superclub, viewGroup, false);
        if (i == 1) {
            inflate.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View view) {
                    if (SearchListSuperClubAdapter.this.b != null) {
                        SearchListSuperClubAdapter.this.b.onSearchItemClick(view);
                    }
                }
            });
        } else {
            inflate.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View view) {
                    if (SearchListSuperClubAdapter.this.b != null) {
                        SearchListSuperClubAdapter.this.b.onSearchNoResultsItemClick();
                    }
                }
            });
        }
        return new SearchListSuperClubViewHolder(inflate, this);
    }

    public void onBindViewHolder(SearchListSuperClubViewHolder searchListSuperClubViewHolder, int i) {
        searchListSuperClubViewHolder.bindItem((CategoriaSuperClubBean) this.mFilteredData.get(i));
    }

    public int getItemViewType(int i) {
        return this.d;
    }

    public int getItemCount() {
        return this.mFilteredData.size();
    }

    public Filter getFilter() {
        return new SearchFilter(this.mData);
    }
}
