package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.imageloader.ImageLoader;
import ar.com.santander.rio.mbanking.app.commons.imageloader.ImageLoaderFactory;
import ar.com.santander.rio.mbanking.components.OnSingleClickListener;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuponSuperClubBean;
import ar.com.santander.rio.mbanking.utils.Utils;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import java.util.List;
import uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;

public class CouponsListSuperClubAdapter extends Adapter<ComodinesSuperClubComodinViewHolder> {
    private Context a;
    /* access modifiers changed from: private */
    public OnItemClickListener b;
    private List<CuponSuperClubBean> c;
    private ImageLoader d = ImageLoaderFactory.getImageLoader(this.a);

    public static class ComodinesSuperClubComodinViewHolder extends ViewHolder {
        private Context m;
        private ImageView n;
        private TextView o;

        public ComodinesSuperClubComodinViewHolder(View view, Context context) {
            super(view);
            this.m = context;
            this.n = (ImageView) view.findViewById(R.id.F20_01_IMG_COUPON);
            this.o = (TextView) view.findViewById(R.id.F20_01_LBL_COUPON);
        }

        public void bindItem(ImageLoader imageLoader, CuponSuperClubBean cuponSuperClubBean) {
            imageLoader.loadImage(cuponSuperClubBean.imagenDescuentoMedioDePago, this.n);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            String decimalGroupingFormat = Utils.getDecimalGroupingFormat(cuponSuperClubBean.puntos);
            spannableStringBuilder.append(decimalGroupingFormat).append(UtilsCuentas.SEPARAOR2).append(this.m.getString(R.string.ID3004_COMODINES_SUPER_CLUB_LBL_PUNTOS));
            spannableStringBuilder.setSpan(new CalligraphyTypefaceSpan(TypefaceUtils.load(this.m.getAssets(), "fonts/OpenSans-Bold.otf")), 0, decimalGroupingFormat.length(), 33);
            this.o.setText(spannableStringBuilder, BufferType.SPANNABLE);
            this.n.setContentDescription(cuponSuperClubBean.descripcion);
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(View view);
    }

    public CouponsListSuperClubAdapter(Context context, List<CuponSuperClubBean> list) {
        this.a = context;
        this.c = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.b = onItemClickListener;
    }

    public ComodinesSuperClubComodinViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.coupon_list_superclub, viewGroup, false);
        inflate.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View view) {
                if (CouponsListSuperClubAdapter.this.b != null) {
                    CouponsListSuperClubAdapter.this.b.OnItemClick(view);
                }
            }
        });
        return new ComodinesSuperClubComodinViewHolder(inflate, this.a);
    }

    public void onBindViewHolder(ComodinesSuperClubComodinViewHolder comodinesSuperClubComodinViewHolder, int i) {
        comodinesSuperClubComodinViewHolder.bindItem(this.d, (CuponSuperClubBean) this.c.get(i));
    }

    public int getItemCount() {
        return this.c.size();
    }
}
