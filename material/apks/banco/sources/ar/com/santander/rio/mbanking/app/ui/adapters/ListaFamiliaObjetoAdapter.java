package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.imageloader.ImageLoaderFactory;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FamiliaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LinkSugerencia;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.Callback;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso.Builder;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ListaFamiliaObjetoAdapter extends Adapter<ViewHolder> {
    /* access modifiers changed from: private */
    public List<Data> a;
    private Context b;
    /* access modifiers changed from: private */
    public OnItemClickListener c;

    public static class Data {
        protected FamiliaBean familiaBean;
        protected LinkSugerencia leyendaBean;

        public FamiliaBean getFamiliaBean() {
            return this.familiaBean;
        }

        public void setFamiliaBean(FamiliaBean familiaBean2) {
            this.familiaBean = familiaBean2;
        }

        public LinkSugerencia getLeyendaBean() {
            return this.leyendaBean;
        }

        public void setLeyendaBean(LinkSugerencia linkSugerencia) {
            this.leyendaBean = linkSugerencia;
        }

        public int getType() {
            if (this.familiaBean != null) {
                return 0;
            }
            return this.leyendaBean != null ? 1 : 99;
        }
    }

    public static class EmptyViewHolder extends ViewHolder {
        EmptyViewHolder(View view) {
            super(view);
        }
    }

    public static class FamiliaObjetoItemHolder extends ViewHolder {
        /* access modifiers changed from: private */
        public TextView m;
        /* access modifiers changed from: private */
        public ImageView n;

        private FamiliaObjetoItemHolder(View view) {
            super(view);
            this.n = (ImageView) view.findViewById(R.id.familia_icon);
            this.m = (TextView) view.findViewById(R.id.name);
        }
    }

    public static class LinkGreenViewHolder extends ViewHolder {
        public ImageView mCloseButton;
        public TextView mDescription;

        LinkGreenViewHolder(View view) {
            super(view);
            this.mDescription = (TextView) view.findViewById(R.id.text);
            this.mCloseButton = (ImageView) view.findViewById(R.id.close_button);
        }
    }

    public interface OnItemClickListener {
        void onFooterLinkClick();

        void onItemClick(FamiliaBean familiaBean);
    }

    public void setmOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.c = onItemClickListener;
    }

    public ListaFamiliaObjetoAdapter(Context context, List<Data> list) {
        this.a = list;
        this.b = context;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        switch (i) {
            case 0:
                return new FamiliaObjetoItemHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_familia_objeto, viewGroup, false));
            case 1:
                return new LinkGreenViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.generic_leyenda_gris_layout, viewGroup, false));
            default:
                return new EmptyViewHolder(new View(this.b));
        }
    }

    public int getItemViewType(int i) {
        return ((Data) this.a.get(i)).getType();
    }

    @RequiresApi(api = 24)
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        if (viewHolder instanceof FamiliaObjetoItemHolder) {
            final FamiliaObjetoItemHolder familiaObjetoItemHolder = (FamiliaObjetoItemHolder) viewHolder;
            if (!TextUtils.isEmpty(((Data) this.a.get(i)).familiaBean.getImagenLista())) {
                ImageLoaderFactory.getImageLoader(this.b).loadImage(((Data) this.a.get(i)).familiaBean.getImagenLista(), familiaObjetoItemHolder.n);
                OkHttpClient okHttpClient = new OkHttpClient();
                okHttpClient.setConnectTimeout(500, TimeUnit.MILLISECONDS);
                new Builder(this.b).downloader(new OkHttpDownloader(okHttpClient)).build().load(((Data) this.a.get(i)).familiaBean.getImagenLista()).into(familiaObjetoItemHolder.n, new Callback() {
                    public void onError() {
                        familiaObjetoItemHolder.n.setImageResource(R.drawable.picture);
                        familiaObjetoItemHolder.n.setVisibility(0);
                    }

                    public void onSuccess() {
                        familiaObjetoItemHolder.n.setVisibility(0);
                    }
                });
            } else {
                familiaObjetoItemHolder.n.setImageResource(R.drawable.picture);
                familiaObjetoItemHolder.n.setVisibility(0);
            }
            familiaObjetoItemHolder.m.setText(Html.fromHtml(((Data) this.a.get(i)).familiaBean.getNombreFamilia()));
            familiaObjetoItemHolder.itemView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (ListaFamiliaObjetoAdapter.this.c != null) {
                        ListaFamiliaObjetoAdapter.this.c.onItemClick(((Data) ListaFamiliaObjetoAdapter.this.a.get(i)).familiaBean);
                    }
                }
            });
        } else if (viewHolder instanceof LinkGreenViewHolder) {
            LinkGreenViewHolder linkGreenViewHolder = (LinkGreenViewHolder) viewHolder;
            AnonymousClass3 r0 = new ClickableSpan() {
                public void onClick(View view) {
                    ListaFamiliaObjetoAdapter.this.c.onFooterLinkClick();
                }
            };
            AnonymousClass4 r2 = new ClickableSpan() {
                public void onClick(View view) {
                    ListaFamiliaObjetoAdapter.this.c.onFooterLinkClick();
                }

                public void updateDrawState(TextPaint textPaint) {
                    super.updateDrawState(textPaint);
                    textPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
                    textPaint.setUnderlineText(false);
                }
            };
            StyleSpan styleSpan = new StyleSpan(1);
            SpannableString spannableString = new SpannableString(Html.fromHtml(((Data) this.a.get(i)).getLeyendaBean().getResDesc()));
            SpannableString spannableString2 = new SpannableString(this.b.getString(R.string.ID_4792_SEGUROS_BTN_INGRESA_AQUI));
            spannableString.setSpan(r2, 0, spannableString.length(), 33);
            spannableString.setSpan(styleSpan, 0, spannableString.length(), 33);
            spannableString2.setSpan(r0, 0, spannableString2.length(), 33);
            linkGreenViewHolder.mDescription.setText(TextUtils.concat(new CharSequence[]{spannableString, UtilsCuentas.SEPARAOR2, spannableString2}));
            linkGreenViewHolder.mDescription.setMovementMethod(LinkMovementMethod.getInstance());
            linkGreenViewHolder.mCloseButton.setVisibility(8);
        }
    }

    public int getItemCount() {
        return this.a.size();
    }
}
