package ar.com.santander.rio.mbanking.app.ui.list_forms;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.imageloader.ImageLoaderFactory;
import ar.com.santander.rio.mbanking.app.ui.list_forms.fields.IAviso;
import ar.com.santander.rio.mbanking.app.ui.list_forms.fields.IButtonField;
import ar.com.santander.rio.mbanking.app.ui.list_forms.fields.IData;
import ar.com.santander.rio.mbanking.app.ui.list_forms.fields.IFooter;
import ar.com.santander.rio.mbanking.app.ui.list_forms.fields.IHeader;
import ar.com.santander.rio.mbanking.app.ui.list_forms.fields.IImageData;
import ar.com.santander.rio.mbanking.app.ui.list_forms.fields.ISubTitle;
import ar.com.santander.rio.mbanking.app.ui.list_forms.fields.ITitle;
import ar.com.santander.rio.mbanking.app.ui.utils.PicassoCache;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.utils.itrsa.HtmlTextView;
import com.squareup.picasso.Callback;
import java.util.List;

public class ListAdapter extends Adapter<ViewHolder> {
    private Context a;
    private List<IData> b;
    private FragmentManager c;
    private OnIntentListener d;
    private OnValidateControl e;
    /* access modifiers changed from: private */
    public OnButtonClickListener f;
    private SessionManager g;
    private OnItemClickListener h;

    public static class AvisoViewHolder extends ViewHolder implements ValueControls {
        private ConstraintLayout m;
        public IAviso mData;
        public TextView mText;
        public TextView mTitle;

        public void bindData() {
        }

        AvisoViewHolder(View view) {
            super(view);
            this.mText = (TextView) view.findViewById(R.id.text);
            this.mTitle = (TextView) view.findViewById(R.id.tittleAtencion);
            this.m = (ConstraintLayout) view.findViewById(R.id.layoutLeyendaLink);
        }

        public void bindData(final OnItemClickListener onItemClickListener) {
            this.mText.setText(Html.fromHtml(this.mData.getLabel()));
            if (this.mData.getContentDescription() != null && !this.mData.getContentDescription().isEmpty()) {
                this.m.setContentDescription(this.mData.getContentDescription());
            }
            AnonymousClass1 r0 = new ClickableSpan() {
                public void onClick(View view) {
                    onItemClickListener.onFooterLinkClick();
                }
            };
            StringBuilder sb = new StringBuilder();
            sb.append("\n");
            sb.append(this.mData.getSpanable());
            SpannableString spannableString = new SpannableString(sb.toString());
            spannableString.setSpan(r0, 0, spannableString.length(), 33);
            if (this.mTitle != null) {
                this.mTitle.setText(this.mData.getTitle());
            }
            this.mText.setText(TextUtils.concat(new CharSequence[]{Html.fromHtml(this.mData.getLabel()), spannableString}));
            this.mText.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    public static class ButtonMarginViewHolder extends ViewHolder implements ValueControls {
        public Button btnRed;
        private LinearLayout m;
        public IButtonField mData;

        ButtonMarginViewHolder(View view) {
            super(view);
            this.btnRed = (Button) view.findViewById(R.id.btn_rojo);
            this.m = (LinearLayout) view.findViewById(R.id.layoutItemButton);
        }

        public void bindData() {
            this.btnRed.setText(this.mData.getText());
            LayoutParams layoutParams = new LayoutParams(-1, -2);
            layoutParams.setMargins(0, 40, 0, 0);
            this.m.setLayoutParams(layoutParams);
            if (this.mData.getContentDescription() != null && !this.mData.getContentDescription().isEmpty()) {
                this.btnRed.setContentDescription(this.mData.getContentDescription());
            }
        }
    }

    public static class ButtonViewHolder extends ViewHolder implements ValueControls {
        public Button btnRed;
        public IButtonField mData;

        ButtonViewHolder(View view) {
            super(view);
            this.btnRed = (Button) view.findViewById(R.id.btn_rojo);
        }

        public void bindData() {
            this.btnRed.setText(this.mData.getText());
            if (this.mData.getContentDescription() != null && !this.mData.getContentDescription().isEmpty()) {
                this.btnRed.setContentDescription(this.mData.getContentDescription());
            }
        }
    }

    public static class FooterViewHolder extends ViewHolder implements ValueControls {
        public IFooter mData;
        public TextView mText;

        FooterViewHolder(View view) {
            super(view);
            this.mText = (TextView) view.findViewById(R.id.text);
        }

        public void bindData() {
            this.mText.setText(Html.fromHtml(this.mData.getLabel()));
            if (this.mData.getContentDescription() != null && !this.mData.getContentDescription().isEmpty()) {
                this.mText.setContentDescription(this.mData.getContentDescription());
            }
        }
    }

    public static class HeaderViewHolder extends ViewHolder implements ValueControls {
        public IHeader mData;
        public HtmlTextView mText;
        public HtmlTextView mTextDown;
        public HtmlTextView mTvDescription;

        HeaderViewHolder(View view) {
            super(view);
            this.mText = (HtmlTextView) view.findViewById(R.id.textView1up);
            this.mTextDown = (HtmlTextView) view.findViewById(R.id.textView2down);
            this.mTvDescription = (HtmlTextView) view.findViewById(R.id.textView3down);
        }

        public void bindData() {
            this.mText.setText(this.mData.getTitle());
            this.mTextDown.setText(this.mData.getSubTitle());
            if (this.mData.getDescription() == null || this.mData.getDescription().isEmpty()) {
                this.mTvDescription.setVisibility(8);
            } else {
                this.mTvDescription.setVisibility(0);
                this.mTvDescription.setText(this.mData.getDescription());
            }
            if (this.mData.getContentDescription() != null && !this.mData.getContentDescription().isEmpty()) {
                this.mText.setContentDescription(this.mData.getContentDescription());
            }
            if (this.mData.getContentDescriptionSubTitle() != null && !this.mData.getContentDescriptionSubTitle().isEmpty()) {
                this.mTextDown.setContentDescription(this.mData.getContentDescriptionSubTitle());
            }
        }
    }

    public static class ItemViewHolder extends ViewHolder implements ValueControls {
        private HtmlTextView m;
        public IImageData mData;
        private HtmlTextView n;
        /* access modifiers changed from: private */
        public ImageView o;
        private LinearLayout p;
        private ConstraintLayout q;

        public void bindData() {
        }

        private ItemViewHolder(View view) {
            super(view);
            this.o = (ImageView) view.findViewById(R.id.ivItem);
            this.m = (HtmlTextView) view.findViewById(R.id.tvDescription);
            this.n = (HtmlTextView) view.findViewById(R.id.tvSubDescription);
            this.p = (LinearLayout) view.findViewById(R.id.layoutItemView);
            this.q = (ConstraintLayout) view.findViewById(R.id.layoutItemViewTurno);
        }

        public void bindData(Context context, SessionManager sessionManager, final OnItemClickListener onItemClickListener) {
            if (!TextUtils.isEmpty(this.mData.getLabel())) {
                this.m.setText(this.mData.getLabel());
                if (this.mData.getImageResourceURL() != null && !this.mData.getImageResourceURL().isEmpty()) {
                    ImageLoaderFactory.getImageLoader(context).loadImage(this.mData.getImageResourceURL(), this.o);
                    PicassoCache.getPicassoInstance(context, sessionManager).load(this.mData.getImageResourceURL()).into(this.o, new Callback() {
                        public void onError() {
                            ItemViewHolder.this.o.setImageResource(R.drawable.picture);
                            ItemViewHolder.this.o.setVisibility(0);
                        }

                        public void onSuccess() {
                            ItemViewHolder.this.o.setVisibility(0);
                        }
                    });
                } else if (this.mData.getImageResource() != 0) {
                    this.o.setBackgroundResource(this.mData.getImageResource());
                } else {
                    this.o.setVisibility(8);
                }
                this.o.setBackgroundResource(this.mData.getImageResource());
                String contentDescription = this.mData.getContentDescription();
                if (!TextUtils.isEmpty(this.mData.getDetail())) {
                    this.n.setText(this.mData.getDetail());
                } else if (this.n != null) {
                    this.n.setVisibility(8);
                }
                if (this.p != null) {
                    this.p.setContentDescription(contentDescription);
                } else if (this.q != null) {
                    this.q.setContentDescription(contentDescription);
                }
                this.itemView.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(ItemViewHolder.this.getItemViewType(), ItemViewHolder.this.mData.getPosition());
                        }
                    }
                });
            }
        }
    }

    public interface OnButtonClickListener {
        void setActionButtton();
    }

    public interface OnIntentListener {
        void startIntent(Class cls, Bundle bundle, int i);
    }

    public interface OnItemClickListener {
        void onFooterLinkClick();

        void onItemClick(int i, int i2);
    }

    public interface OnValidateControl {
        void validateControl(Boolean bool);
    }

    public static class SimpleInputViewHolder extends ViewHolder implements ValueControls {
        private ConstraintLayout m;
        public IData mData;
        public EditText mEtValue;
        public HtmlTextView mLabel;
        public HtmlTextView mTvValue;

        SimpleInputViewHolder(View view) {
            super(view);
            this.mLabel = (HtmlTextView) view.findViewById(R.id.label);
            this.mEtValue = (EditText) view.findViewById(R.id.value);
            this.mEtValue.setImeOptions(6);
            this.mTvValue = (HtmlTextView) view.findViewById(R.id.tvValue);
            this.m = (ConstraintLayout) view.findViewById(R.id.layoutItem);
        }

        public void bindData() {
            this.mLabel.setText(this.mData.getLabel());
            if (this.mData.getContentDescriptionLabel() != null && !this.mData.getContentDescriptionLabel().isEmpty()) {
                this.mLabel.setContentDescription(this.mData.getContentDescriptionLabel());
            }
            this.mEtValue.setVisibility(8);
            this.mTvValue.setVisibility(0);
            this.mTvValue.setText(this.mData.getDetail());
            if (this.mData.getContentDescription() != null && !this.mData.getContentDescription().isEmpty()) {
                this.m.setContentDescription(this.mData.getContentDescription());
            }
        }
    }

    public static class SubTitleViewHolder extends ViewHolder implements ValueControls {
        private RelativeLayout m;
        public ISubTitle mData;
        public TextView mText;

        SubTitleViewHolder(View view) {
            super(view);
            this.mText = (TextView) view.findViewById(R.id.section_title);
            this.m = (RelativeLayout) view.findViewById(R.id.section_subtitle);
        }

        public void bindData() {
            this.mText.setText(Html.fromHtml(this.mData.getSubTitle()));
            if (this.mData.getMargin() != null) {
                LayoutParams layoutParams = new LayoutParams(-1, -2);
                layoutParams.setMargins(this.mData.getMargin().getLeft(), this.mData.getMargin().getTop(), this.mData.getMargin().getRight(), this.mData.getMargin().getBottom());
                this.m.setLayoutParams(layoutParams);
            }
            if (this.mData.getContentDescription() != null && !this.mData.getContentDescription().isEmpty()) {
                this.mText.setContentDescription(this.mData.getContentDescription());
            }
        }
    }

    public static class TitleViewHolder extends ViewHolder implements ValueControls {
        public ITitle mData;
        public TextView mText;

        TitleViewHolder(View view) {
            super(view);
            this.mText = (TextView) view.findViewById(R.id.functionality_title);
        }

        public void bindData() {
            this.mText.setText(Html.fromHtml(this.mData.getTitle()));
            this.mText.setTypeface(null, 1);
            if (this.mData.getContentDescription() != null && !this.mData.getContentDescription().isEmpty()) {
                this.mText.setContentDescription(this.mData.getContentDescription());
            }
        }
    }

    public interface ValueControls {
        void bindData();
    }

    public ListAdapter(List<IData> list, FragmentManager fragmentManager) {
        this.b = list;
        this.c = fragmentManager;
    }

    public ListAdapter(List<IData> list, FragmentManager fragmentManager, Context context) {
        this.b = list;
        this.c = fragmentManager;
        this.a = context;
    }

    public ListAdapter(List<IData> list, FragmentManager fragmentManager, Context context, SessionManager sessionManager) {
        this.b = list;
        this.c = fragmentManager;
        this.a = context;
        this.g = sessionManager;
    }

    public int getItemViewType(int i) {
        return ((IData) this.b.get(i)).getType();
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.generic_form_row, viewGroup, false);
        switch (i) {
            case 1:
                return new TitleViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.generic_view_title_layout, viewGroup, false));
            case 2:
                return new SubTitleViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.generic_view_section_subtitle_layout, viewGroup, false));
            case 3:
                return new HeaderViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.generic_view_two_textviewcenter_with_margins, viewGroup, false));
            case 4:
                return new SimpleInputViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.generic_list_row, viewGroup, false));
            case 5:
                return new ItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.generic_view_image_with_description, viewGroup, false));
            case 6:
                return new ItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_solicitud_turno_caja, viewGroup, false));
            case 7:
                return new FooterViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.generic_form_leyend_row, viewGroup, false));
            case 8:
                return new AvisoViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.generic_leyenda_verde_layout, viewGroup, false));
            case 9:
                return new AvisoViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.generic_leyenda_roja_clara_layout, viewGroup, false));
            case 10:
                return new ButtonViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.generic_red_button, viewGroup, false));
            case 11:
                return new ButtonMarginViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.generic_button, viewGroup, false));
            default:
                return new SimpleInputViewHolder(inflate);
        }
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (viewHolder instanceof TitleViewHolder) {
            TitleViewHolder titleViewHolder = (TitleViewHolder) viewHolder;
            titleViewHolder.mData = (ITitle) this.b.get(i);
            titleViewHolder.bindData();
        } else if (viewHolder instanceof SubTitleViewHolder) {
            SubTitleViewHolder subTitleViewHolder = (SubTitleViewHolder) viewHolder;
            subTitleViewHolder.mData = (ISubTitle) this.b.get(i);
            subTitleViewHolder.bindData();
        } else if (viewHolder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) viewHolder;
            headerViewHolder.mData = (IHeader) this.b.get(i);
            headerViewHolder.bindData();
        } else if (viewHolder instanceof SimpleInputViewHolder) {
            SimpleInputViewHolder simpleInputViewHolder = (SimpleInputViewHolder) viewHolder;
            simpleInputViewHolder.mData = (IData) this.b.get(i);
            simpleInputViewHolder.bindData();
        } else if (viewHolder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
            itemViewHolder.mData = (IImageData) this.b.get(i);
            itemViewHolder.bindData(this.a, this.g, this.h);
        } else if (viewHolder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) viewHolder;
            footerViewHolder.mData = (IFooter) this.b.get(i);
            footerViewHolder.bindData();
        } else if (viewHolder instanceof AvisoViewHolder) {
            AvisoViewHolder avisoViewHolder = (AvisoViewHolder) viewHolder;
            avisoViewHolder.mData = (IAviso) this.b.get(i);
            avisoViewHolder.bindData(this.h);
        } else if (viewHolder instanceof ButtonViewHolder) {
            ButtonViewHolder buttonViewHolder = (ButtonViewHolder) viewHolder;
            buttonViewHolder.mData = (IButtonField) this.b.get(i);
            buttonViewHolder.bindData();
            buttonViewHolder.btnRed.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    ListAdapter.this.f.setActionButtton();
                }
            });
        } else if (viewHolder instanceof ButtonMarginViewHolder) {
            ButtonMarginViewHolder buttonMarginViewHolder = (ButtonMarginViewHolder) viewHolder;
            buttonMarginViewHolder.mData = (IButtonField) this.b.get(i);
            buttonMarginViewHolder.bindData();
            buttonMarginViewHolder.btnRed.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    ListAdapter.this.f.setActionButtton();
                }
            });
        }
    }

    public int getItemCount() {
        return this.b.size();
    }

    public void setmOnIntentListener(OnIntentListener onIntentListener) {
        this.d = onIntentListener;
    }

    public void setmOnValidateControl(OnValidateControl onValidateControl) {
        this.e = onValidateControl;
    }

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.f = onButtonClickListener;
    }

    public void setmOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.h = onItemClickListener;
    }
}
