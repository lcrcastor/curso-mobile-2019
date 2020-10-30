package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.imageloader.ImageLoader;
import ar.com.santander.rio.mbanking.app.commons.imageloader.ImageLoaderFactory;
import ar.com.santander.rio.mbanking.components.OnSingleClickListener;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CategoriaSuperClubBean;
import java.util.List;

public class ListSuperClubAdapter extends Adapter<ComodinesSuperClubCategorySubcategoryViewHolder> {
    private Context a;
    /* access modifiers changed from: private */
    public OnItemClickListener b;
    private List<CategoriaSuperClubBean> c;
    private ImageLoader d = ImageLoaderFactory.getImageLoader(this.a);
    private int e;

    public static class ComodinesSuperClubCategorySubcategoryViewHolder extends ViewHolder {
        private ImageView m;
        private TextView n;

        public ComodinesSuperClubCategorySubcategoryViewHolder(View view) {
            super(view);
            this.m = (ImageView) view.findViewById(R.id.F20_00_IMG_COUPON);
            this.n = (TextView) view.findViewById(R.id.F20_00_LBL_COUPON);
        }

        public void bindItem(ImageLoader imageLoader, CategoriaSuperClubBean categoriaSuperClubBean) {
            imageLoader.loadImage(categoriaSuperClubBean.imagen, this.m);
            this.n.setText(Html.fromHtml(categoriaSuperClubBean.nombre).toString());
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(View view);
    }

    private ListSuperClubAdapter(Context context, List<CategoriaSuperClubBean> list, int i) {
        this.a = context;
        this.c = list;
        this.e = i;
    }

    public static ListSuperClubAdapter getCategoryAdapter(Context context, List<CategoriaSuperClubBean> list) {
        return new ListSuperClubAdapter(context, list, R.layout.item_category_superclub);
    }

    public static ListSuperClubAdapter getSubCategoryAdapter(Context context, List<CategoriaSuperClubBean> list) {
        return new ListSuperClubAdapter(context, list, R.layout.item_subcategory_list_superclub);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.b = onItemClickListener;
    }

    public ComodinesSuperClubCategorySubcategoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(this.e, viewGroup, false);
        inflate.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View view) {
                if (ListSuperClubAdapter.this.b != null) {
                    ListSuperClubAdapter.this.b.OnItemClick(view);
                }
            }
        });
        return new ComodinesSuperClubCategorySubcategoryViewHolder(inflate);
    }

    public void onBindViewHolder(ComodinesSuperClubCategorySubcategoryViewHolder comodinesSuperClubCategorySubcategoryViewHolder, int i) {
        comodinesSuperClubCategorySubcategoryViewHolder.bindItem(this.d, (CategoriaSuperClubBean) this.c.get(i));
    }

    public int getItemCount() {
        return this.c.size();
    }
}
