package ar.com.santander.rio.mbanking.app.ui.forms;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.AccessibilityDelegate;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.commons.imageloader.ImageLoaderFactory;
import ar.com.santander.rio.mbanking.app.ui.constants.MarcacionViajeConstants.Reintento;
import ar.com.santander.rio.mbanking.app.ui.constants.PagoTarjetasConstants;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.IFormCheckBox;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.IFormData;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.IFormDataButton;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.IFormDataDate;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.IFormDataHeader;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.IFormDataIntent;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.IFormDataLeyend;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.IFormDataSection;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.IFormDataSelection;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.IFormDataSelectionKeyValue;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.IFormDataSelectionMedioPago;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.IFormDataText;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.IFormDataTitle;
import ar.com.santander.rio.mbanking.app.ui.list_forms.ListAdapter.OnButtonClickListener;
import ar.com.santander.rio.mbanking.components.IsbanDatePickerDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDatePickerDialogFragment.IDateDialogListener;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.utils.itrsa.HtmlTextView;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso.Builder;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Target;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FormAdapter extends Adapter<ViewHolder> {
    AccessibilityDelegate a = new AccessibilityDelegate() {
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            accessibilityNodeInfo.setText(view.getContentDescription());
            if (view.getContentDescription() != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("adapter ");
                sb.append(view.getContentDescription().toString());
                Log.i("## accesibilidad", sb.toString());
            }
        }
    };
    /* access modifiers changed from: private */
    public Context b;
    /* access modifiers changed from: private */
    public List<IFormData> c;
    /* access modifiers changed from: private */
    public FragmentManager d;
    private OnChangeValuesListener e;
    /* access modifiers changed from: private */
    public OnButtonClickListener f;
    /* access modifiers changed from: private */
    public OnIntentListener g;
    /* access modifiers changed from: private */
    public OnValidateControl h;
    private IFormDataButton i;
    private boolean j = false;
    private boolean k = false;

    public static class ButtonMarginViewHolder extends ViewHolder {
        public Button btnRed;
        public IFormDataButton mData;

        ButtonMarginViewHolder(View view) {
            super(view);
            this.btnRed = (Button) view.findViewById(R.id.btn_rojo);
        }

        public void bindData(boolean z) {
            this.btnRed.setText(this.mData.getText());
            this.btnRed.setEnabled(z);
        }
    }

    public static class CheckBoxViewHolder extends ViewHolder {
        public ImageView checkBox;
        public boolean isCheck = false;
        public IFormCheckBox mData;
        public TextView mDescription;

        CheckBoxViewHolder(View view) {
            super(view);
            this.mDescription = (TextView) view.findViewById(R.id.tvTyCDescription);
            this.checkBox = (ImageView) view.findViewById(R.id.img_checkbox_tyc);
        }

        public void validateCheckBox(Context context) {
            if (this.checkBox.getTag().equals(String.valueOf(false))) {
                this.checkBox.setBackground(context.getResources().getDrawable(R.drawable.ic_checkbox_checked));
                this.checkBox.setTag(String.valueOf(true));
                this.isCheck = true;
                return;
            }
            this.checkBox.setBackground(context.getResources().getDrawable(R.drawable.ic_checkbox_off_gris));
            this.checkBox.setTag(String.valueOf(false));
            this.isCheck = false;
        }
    }

    public static class DatePickerInputViewHolder extends ViewHolder {
        public IFormData mData;
        public HtmlTextView mLabel;
        public ImageView mSelectorImage;
        public HtmlTextView mValue;
        public View mViewGoup;

        DatePickerInputViewHolder(View view) {
            super(view);
            this.mViewGoup = view;
            this.mLabel = (HtmlTextView) view.findViewById(R.id.label);
            this.mValue = (HtmlTextView) view.findViewById(R.id.value);
            this.mSelectorImage = (ImageView) view.findViewById(R.id.selector_arrow);
            t();
        }

        /* access modifiers changed from: 0000 */
        public void t() {
            AnonymousClass1 r0 = new AccessibilityDelegate() {
                public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                    super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                    accessibilityNodeInfo.setText(view.getContentDescription());
                    if (view.getContentDescription() != null) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("DatePickerInputViewHolder ");
                        sb.append(view.getContentDescription().toString());
                        Log.i("## accesibilidad", sb.toString());
                    }
                }
            };
            this.mLabel.setAccessibilityDelegate(r0);
            this.mValue.setAccessibilityDelegate(r0);
        }
    }

    public static class HeaderViewHolder extends ViewHolder {
        public ImageView icon;
        public IFormDataHeader mData;
        public TextView mText;
        public TextView mTvDescription;

        HeaderViewHolder(View view) {
            super(view);
            this.icon = (ImageView) view.findViewById(R.id.familia_icon);
            this.mText = (TextView) view.findViewById(R.id.familia_name);
            this.mTvDescription = (TextView) view.findViewById(R.id.familia_description);
        }

        public void bindData(Context context) {
            this.mText.setText(this.mData.getTitle());
            if (this.mData.getDescription() == null || this.mData.getDescription().isEmpty()) {
                this.mTvDescription.setVisibility(8);
            } else {
                this.mTvDescription.setVisibility(0);
                this.mTvDescription.setText(this.mData.getDescription());
            }
            ImageLoaderFactory.getImageLoader(context).loadImage(this.mData.getIconUrl(), this.icon);
            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setConnectTimeout(500, TimeUnit.MILLISECONDS);
            new Builder(context).downloader(new OkHttpDownloader(okHttpClient)).build();
            Picasso.with(context).load(this.mData.getIconUrl()).into((Target) new Target() {
                public void onPrepareLoad(Drawable drawable) {
                }

                public void onBitmapLoaded(Bitmap bitmap, LoadedFrom loadedFrom) {
                    HeaderViewHolder.this.icon.setImageBitmap(bitmap);
                    HeaderViewHolder.this.icon.setVisibility(0);
                }

                public void onBitmapFailed(Drawable drawable) {
                    HeaderViewHolder.this.icon.setImageResource(R.drawable.picture_g);
                    HeaderViewHolder.this.icon.setVisibility(0);
                }
            });
        }
    }

    public static class IntentValueViewHolder extends ViewHolder {
        public IFormDataIntent mData;
        public HtmlTextView mLabel;
        public ImageView mSelectorImage;
        public HtmlTextView mValue;
        public View mViewGoup;

        IntentValueViewHolder(View view) {
            super(view);
            this.mViewGoup = view;
            this.mLabel = (HtmlTextView) view.findViewById(R.id.label);
            this.mValue = (HtmlTextView) view.findViewById(R.id.value);
            this.mSelectorImage = (ImageView) view.findViewById(R.id.selector_arrow);
        }
    }

    public static class LeyendValueViewHolder extends ViewHolder {
        public IFormDataLeyend mData;
        public HtmlTextView mText;

        LeyendValueViewHolder(View view) {
            super(view);
            this.mText = (HtmlTextView) view;
        }
    }

    public interface OnChangeValuesListener {
        void isValidListener(ErrorMessage errorMessage);
    }

    public interface OnIntentListener {
        void startIntent(Class cls, Bundle bundle, int i);
    }

    public interface OnValidateControl {
        void validateControl(Boolean bool);
    }

    public static class SectionValueViewHolder extends ViewHolder {
        public IFormDataSection mData;
        public HtmlTextView mLabel;
        public View mViewGoup;

        SectionValueViewHolder(View view) {
            super(view);
            this.mViewGoup = view;
            this.mLabel = (HtmlTextView) view.findViewById(R.id.label);
        }
    }

    public static class SelectableInputKeyValueViewHolder extends SelectableInputViewHolder {
        public IFormDataSelectionKeyValue mData;
        public HtmlTextView mLabel;
        public ImageView mSelectorImage;
        public HtmlTextView mValue;
        public View mViewGoup;

        SelectableInputKeyValueViewHolder(View view) {
            super(view);
            this.mViewGoup = view;
            this.mLabel = (HtmlTextView) view.findViewById(R.id.label);
            this.mValue = (HtmlTextView) view.findViewById(R.id.value);
            this.mSelectorImage = (ImageView) view.findViewById(R.id.selector_arrow);
        }
    }

    public static class SelectableInputMedioPagoViewHolder extends SelectableInputViewHolder {
        public IFormDataSelectionMedioPago mData;
        public HtmlTextView mLabel;
        public ImageView mSelectorImage;
        public HtmlTextView mValue;
        public View mViewGoup;

        SelectableInputMedioPagoViewHolder(View view) {
            super(view);
            this.mViewGoup = view;
            this.mLabel = (HtmlTextView) view.findViewById(R.id.label);
            this.mValue = (HtmlTextView) view.findViewById(R.id.value);
            this.mSelectorImage = (ImageView) view.findViewById(R.id.selector_arrow);
        }
    }

    public static class SelectableInputViewHolder extends ViewHolder {
        public IFormDataSelection mData;
        public HtmlTextView mLabel;
        public ImageView mSelectorImage;
        public HtmlTextView mValue;
        public View mViewGoup;

        SelectableInputViewHolder(View view) {
            super(view);
            this.mViewGoup = view;
            this.mLabel = (HtmlTextView) view.findViewById(R.id.label);
            this.mValue = (HtmlTextView) view.findViewById(R.id.value);
            this.mSelectorImage = (ImageView) view.findViewById(R.id.selector_arrow);
        }
    }

    public static class SimpleInputViewHolder extends ViewHolder {
        public IFormDataText mData;
        public EditText mEtValue;
        public HtmlTextView mLabel;
        public HtmlTextView mTvValue;

        SimpleInputViewHolder(View view) {
            super(view);
            this.mLabel = (HtmlTextView) view.findViewById(R.id.label);
            this.mEtValue = (EditText) view.findViewById(R.id.value);
            this.mEtValue.setImeOptions(6);
            this.mTvValue = (HtmlTextView) view.findViewById(R.id.tvValue);
            t();
        }

        /* access modifiers changed from: 0000 */
        public void t() {
            AnonymousClass1 r0 = new AccessibilityDelegate() {
                public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                    super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                    accessibilityNodeInfo.setText(view.getContentDescription());
                    if (view.getContentDescription() != null) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("SimpleInputViewHolder ");
                        sb.append(view.getContentDescription().toString());
                        Log.i("## accesibilidad", sb.toString());
                    }
                }
            };
            this.mLabel.setAccessibilityDelegate(r0);
            this.mTvValue.setAccessibilityDelegate(r0);
        }
    }

    public static class TitleViewHolder extends ViewHolder implements ValueControls {
        public IFormDataTitle mData;
        public TextView mText;

        TitleViewHolder(View view) {
            super(view);
            this.mText = (TextView) view.findViewById(R.id.functionality_title);
        }

        public void bindData() {
            this.mText.setText(Html.fromHtml(this.mData.getTitle()));
            this.mText.setTypeface(null, 1);
        }
    }

    public interface ValueControls {
        void bindData();
    }

    public void setmOnIntentListener(OnIntentListener onIntentListener) {
        this.g = onIntentListener;
    }

    public void setmOnValidateControl(OnValidateControl onValidateControl) {
        this.h = onValidateControl;
    }

    public void setmOnChangeValuesListener(OnChangeValuesListener onChangeValuesListener) {
        this.e = onChangeValuesListener;
    }

    public FormAdapter(List<IFormData> list, FragmentManager fragmentManager, Context context) {
        this.c = list;
        this.d = fragmentManager;
        this.b = context;
    }

    public int getItemViewType(int i2) {
        return ((IFormData) this.c.get(i2)).getType();
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i2) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.generic_form_row, viewGroup, false);
        switch (i2) {
            case 1:
                return new SimpleInputViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.generic_form_row, viewGroup, false));
            case 2:
                return new SelectableInputViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.generic_string_list_form_row, viewGroup, false));
            case 3:
                return new DatePickerInputViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.generic_date_picker_form_row, viewGroup, false));
            case 4:
                return new SelectableInputKeyValueViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.generic_string_list_form_row, viewGroup, false));
            case 5:
                return new IntentValueViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.generic_string_list_form_row, viewGroup, false));
            case 6:
                return new SectionValueViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.generic_form_title_row, viewGroup, false));
            case 7:
                return new LeyendValueViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.generic_form_leyend_row, viewGroup, false));
            case 8:
                return new SelectableInputMedioPagoViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.generic_string_list_form_row, viewGroup, false));
            case 10:
                return new CheckBoxViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.generic_view_check_box, viewGroup, false));
            case 11:
                return new TitleViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.generic_view_title_layout, viewGroup, false));
            case 12:
                return new HeaderViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.generic_layout_familia_objeto_view, viewGroup, false));
            case 13:
                return new ButtonMarginViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.generic_button, viewGroup, false));
            default:
                return new SimpleInputViewHolder(inflate);
        }
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i2) {
        CAccessibility cAccessibility = new CAccessibility(this.b);
        if (viewHolder instanceof SimpleInputViewHolder) {
            final SimpleInputViewHolder simpleInputViewHolder = (SimpleInputViewHolder) viewHolder;
            if (simpleInputViewHolder.mData == null) {
                simpleInputViewHolder.mData = (IFormDataText) this.c.get(i2);
                simpleInputViewHolder.mData.setPosition(i2);
                ArrayList arrayList = new ArrayList(Arrays.asList(simpleInputViewHolder.mEtValue.getFilters()));
                if (simpleInputViewHolder.mData.getMaxOfCharacters().intValue() > 0) {
                    arrayList.add(new LengthFilter(simpleInputViewHolder.mData.getMaxOfCharacters().intValue()));
                }
                arrayList.add(new GenericInputFilter().setCharsetArray(simpleInputViewHolder.mData.getDigits()));
                simpleInputViewHolder.mEtValue.setFilters((InputFilter[]) arrayList.toArray(new InputFilter[arrayList.size()]));
            }
            simpleInputViewHolder.mData = (IFormDataText) this.c.get(i2);
            simpleInputViewHolder.mLabel.setText(((IFormData) this.c.get(i2)).getLabelText());
            if (((IFormData) this.c.get(i2)).getEditable().booleanValue()) {
                simpleInputViewHolder.mEtValue.setVisibility(0);
                simpleInputViewHolder.mTvValue.setVisibility(8);
                simpleInputViewHolder.mEtValue.setText(((IFormData) this.c.get(i2)).getValueText());
                simpleInputViewHolder.mEtValue.setHint(((IFormData) this.c.get(i2)).getHint());
                simpleInputViewHolder.mEtValue.setInputType(simpleInputViewHolder.mData.getKeyboardType());
                simpleInputViewHolder.mEtValue.addTextChangedListener(new TextWatcher() {
                    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    }

                    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    }

                    public void afterTextChanged(Editable editable) {
                        simpleInputViewHolder.mData.setValueText(editable.toString());
                        FormAdapter.this.a();
                    }
                });
                simpleInputViewHolder.mEtValue.setEnabled(true);
            } else {
                simpleInputViewHolder.mEtValue.setVisibility(8);
                simpleInputViewHolder.mTvValue.setVisibility(0);
                simpleInputViewHolder.mTvValue.setText(((IFormData) this.c.get(i2)).getValueText());
                simpleInputViewHolder.mEtValue.setEnabled(false);
            }
            try {
                simpleInputViewHolder.mLabel.setContentDescription(cAccessibility.applyFilterGeneral(Html.fromHtml(simpleInputViewHolder.mLabel.getText().toString()).toString()));
                simpleInputViewHolder.mTvValue.setContentDescription(cAccessibility.applyFilterGeneral(simpleInputViewHolder.mTvValue.getText().toString()));
                simpleInputViewHolder.mLabel.setAccessibilityDelegate(this.a);
                simpleInputViewHolder.mTvValue.setAccessibilityDelegate(this.a);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else if (viewHolder instanceof SelectableInputKeyValueViewHolder) {
            final SelectableInputKeyValueViewHolder selectableInputKeyValueViewHolder = (SelectableInputKeyValueViewHolder) viewHolder;
            selectableInputKeyValueViewHolder.mData = (IFormDataSelectionKeyValue) this.c.get(i2);
            selectableInputKeyValueViewHolder.mData.setPosition(i2);
            selectableInputKeyValueViewHolder.mLabel.setText(selectableInputKeyValueViewHolder.mData.getLabelText());
            if (selectableInputKeyValueViewHolder.mData.getEditable().booleanValue()) {
                if (!selectableInputKeyValueViewHolder.mData.getKeyValue().isEmpty()) {
                    selectableInputKeyValueViewHolder.mValue.setText(selectableInputKeyValueViewHolder.mData.getKeyValue().getText());
                } else {
                    selectableInputKeyValueViewHolder.mValue.setText(selectableInputKeyValueViewHolder.mData.getHint());
                }
                if (selectableInputKeyValueViewHolder.mData.getStringList().size() == 1) {
                    selectableInputKeyValueViewHolder.mData.setValueText((String) selectableInputKeyValueViewHolder.mData.getStringList().get(0));
                    selectableInputKeyValueViewHolder.mValue.setText((CharSequence) selectableInputKeyValueViewHolder.mData.getStringList().get(0));
                    selectableInputKeyValueViewHolder.mSelectorImage.setVisibility(8);
                } else if (selectableInputKeyValueViewHolder.mData.getStringList().size() > 1) {
                    selectableInputKeyValueViewHolder.mSelectorImage.setVisibility(0);
                    selectableInputKeyValueViewHolder.mViewGoup.setOnClickListener(new OnClickListener() {
                        public void onClick(View view) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(view.toString());
                            sb.append(i2);
                            IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(sb.toString(), ((IFormData) FormAdapter.this.c.get(i2)).getTitle(), null, ((IFormDataSelection) FormAdapter.this.c.get(i2)).getStringList(), PagoTarjetasConstants.ISBAN_DIALOG_CANCEL_BUTTON_TEXT, null, null, ((IFormDataSelectionKeyValue) FormAdapter.this.c.get(i2)).getKeyValue().getText(), ((IFormDataSelection) FormAdapter.this.c.get(i2)).getStringList());
                            newInstance.setDialogListener(new IDialogListener() {
                                public void onNegativeButton() {
                                }

                                public void onPositiveButton() {
                                }

                                public void onSimpleActionButton() {
                                }

                                public void onItemSelected(String str) {
                                    selectableInputKeyValueViewHolder.mData.setValueText(str);
                                    FormAdapter.this.notifyItemChanged(i2);
                                    FormAdapter.this.a();
                                }
                            });
                            FragmentManager c2 = FormAdapter.this.d;
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("FORM_DIALOG");
                            sb2.append(view.toString());
                            sb2.append(i2);
                            newInstance.show(c2, sb2.toString());
                        }
                    });
                }
            } else {
                if (selectableInputKeyValueViewHolder.mData.getKeyValue().getText().isEmpty()) {
                    selectableInputKeyValueViewHolder.mValue.setText(selectableInputKeyValueViewHolder.mData.getValueText());
                } else {
                    selectableInputKeyValueViewHolder.mValue.setText(selectableInputKeyValueViewHolder.mData.getKeyValue().getText());
                }
                selectableInputKeyValueViewHolder.mSelectorImage.setVisibility(8);
            }
            a(selectableInputKeyValueViewHolder, cAccessibility);
        } else if (viewHolder instanceof SelectableInputMedioPagoViewHolder) {
            final SelectableInputMedioPagoViewHolder selectableInputMedioPagoViewHolder = (SelectableInputMedioPagoViewHolder) viewHolder;
            selectableInputMedioPagoViewHolder.mData = (IFormDataSelectionMedioPago) this.c.get(i2);
            selectableInputMedioPagoViewHolder.mData.setPosition(i2);
            selectableInputMedioPagoViewHolder.mLabel.setText(selectableInputMedioPagoViewHolder.mData.getLabelText());
            if (selectableInputMedioPagoViewHolder.mData.getEditable().booleanValue()) {
                selectableInputMedioPagoViewHolder.mValue.setText(a(selectableInputMedioPagoViewHolder));
                if (selectableInputMedioPagoViewHolder.mData.getStringList().size() == 1) {
                    selectableInputMedioPagoViewHolder.mData.setValueText((String) selectableInputMedioPagoViewHolder.mData.getStringList().get(0));
                    selectableInputMedioPagoViewHolder.mValue.setText((CharSequence) selectableInputMedioPagoViewHolder.mData.getStringList().get(0));
                    selectableInputMedioPagoViewHolder.mSelectorImage.setVisibility(8);
                } else if (selectableInputMedioPagoViewHolder.mData.getStringList().size() > 1) {
                    selectableInputMedioPagoViewHolder.mSelectorImage.setVisibility(0);
                    selectableInputMedioPagoViewHolder.mViewGoup.setOnClickListener(new OnClickListener() {
                        public void onClick(View view) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(view.toString());
                            sb.append(i2);
                            IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(sb.toString(), ((IFormData) FormAdapter.this.c.get(i2)).getTitle(), null, ((IFormDataSelection) FormAdapter.this.c.get(i2)).getStringList(), PagoTarjetasConstants.ISBAN_DIALOG_CANCEL_BUTTON_TEXT, null, null, ((IFormDataSelectionMedioPago) FormAdapter.this.c.get(i2)).getMedioPagoValue().getDescripcion(), ((IFormDataSelection) FormAdapter.this.c.get(i2)).getStringList());
                            newInstance.setDialogListener(new IDialogListener() {
                                public void onNegativeButton() {
                                }

                                public void onPositiveButton() {
                                }

                                public void onSimpleActionButton() {
                                }

                                public void onItemSelected(String str) {
                                    selectableInputMedioPagoViewHolder.mData.setValueTextData(str);
                                    selectableInputMedioPagoViewHolder.mData.setValueText(str);
                                    FormAdapter.this.notifyItemChanged(i2);
                                    FormAdapter.this.a();
                                }
                            });
                            newInstance.show(FormAdapter.this.d, "FORM_DIALOG");
                        }
                    });
                }
            } else {
                selectableInputMedioPagoViewHolder.mValue.setText(selectableInputMedioPagoViewHolder.mData.getMedioPagoValue().getDescripcion());
                selectableInputMedioPagoViewHolder.mSelectorImage.setVisibility(8);
            }
            try {
                selectableInputMedioPagoViewHolder.mLabel.setContentDescription(selectableInputMedioPagoViewHolder.mLabel.getText().toString());
                selectableInputMedioPagoViewHolder.mValue.setContentDescription(cAccessibility.applyFilterAccount(selectableInputMedioPagoViewHolder.mValue.getText().toString()));
                selectableInputMedioPagoViewHolder.mLabel.setAccessibilityDelegate(this.a);
                selectableInputMedioPagoViewHolder.mValue.setAccessibilityDelegate(this.a);
            } catch (Exception e3) {
                e3.fillInStackTrace();
            }
        } else if (viewHolder instanceof SelectableInputViewHolder) {
            final SelectableInputViewHolder selectableInputViewHolder = (SelectableInputViewHolder) viewHolder;
            selectableInputViewHolder.mData = (IFormDataSelection) this.c.get(i2);
            selectableInputViewHolder.mData.setPosition(i2);
            selectableInputViewHolder.mLabel.setText(selectableInputViewHolder.mData.getLabelText());
            if (selectableInputViewHolder.mData.getEditable().booleanValue()) {
                if (!TextUtils.isEmpty(selectableInputViewHolder.mData.getValueText())) {
                    selectableInputViewHolder.mValue.setText(selectableInputViewHolder.mData.getValueText());
                } else {
                    selectableInputViewHolder.mValue.setText(selectableInputViewHolder.mData.getHint());
                }
                if (selectableInputViewHolder.mData.getStringList().size() == 1) {
                    selectableInputViewHolder.mData.setValueText((String) selectableInputViewHolder.mData.getStringList().get(0));
                    selectableInputViewHolder.mValue.setText((CharSequence) selectableInputViewHolder.mData.getStringList().get(0));
                    selectableInputViewHolder.mSelectorImage.setVisibility(8);
                } else if (selectableInputViewHolder.mData.getStringList().size() > 1) {
                    selectableInputViewHolder.mSelectorImage.setVisibility(0);
                    selectableInputViewHolder.mViewGoup.setOnClickListener(new OnClickListener() {
                        public void onClick(View view) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(view.toString());
                            sb.append(i2);
                            IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(sb.toString(), ((IFormData) FormAdapter.this.c.get(i2)).getTitle(), null, ((IFormDataSelection) FormAdapter.this.c.get(i2)).getStringList(), PagoTarjetasConstants.ISBAN_DIALOG_CANCEL_BUTTON_TEXT, null, null, ((IFormData) FormAdapter.this.c.get(i2)).getValueText(), ((IFormDataSelection) FormAdapter.this.c.get(i2)).getStringList());
                            newInstance.setDialogListener(new IDialogListener() {
                                public void onNegativeButton() {
                                }

                                public void onPositiveButton() {
                                }

                                public void onSimpleActionButton() {
                                }

                                public void onItemSelected(String str) {
                                    selectableInputViewHolder.mData.setValueText(str);
                                    FormAdapter.this.notifyItemChanged(i2);
                                    FormAdapter.this.a();
                                }
                            });
                            newInstance.show(FormAdapter.this.d, "FORM_DIALOG");
                        }
                    });
                }
            } else {
                selectableInputViewHolder.mValue.setText(selectableInputViewHolder.mData.getValueText());
                selectableInputViewHolder.mSelectorImage.setVisibility(8);
            }
            try {
                selectableInputViewHolder.mLabel.setContentDescription(cAccessibility.applyFilterGeneral(selectableInputViewHolder.mData.getLabelText()));
                selectableInputViewHolder.mValue.setContentDescription(cAccessibility.applyFilterGeneral(selectableInputViewHolder.mValue.getText().toString()));
                selectableInputViewHolder.mLabel.setAccessibilityDelegate(this.a);
                selectableInputViewHolder.mValue.setAccessibilityDelegate(this.a);
            } catch (Exception e4) {
                e4.printStackTrace();
            }
        } else if (viewHolder instanceof DatePickerInputViewHolder) {
            final DatePickerInputViewHolder datePickerInputViewHolder = (DatePickerInputViewHolder) viewHolder;
            datePickerInputViewHolder.mLabel.setText(((IFormData) this.c.get(i2)).getLabelText());
            datePickerInputViewHolder.mValue.setText(((IFormData) this.c.get(i2)).getValueText());
            datePickerInputViewHolder.mValue.setHint(((IFormData) this.c.get(i2)).getHint());
            datePickerInputViewHolder.mData = (IFormData) this.c.get(i2);
            datePickerInputViewHolder.mData.setPosition(i2);
            if (datePickerInputViewHolder.mData.getEditable().booleanValue()) {
                if (!TextUtils.isEmpty(datePickerInputViewHolder.mData.getValueText())) {
                    datePickerInputViewHolder.mValue.setText(datePickerInputViewHolder.mData.getValueText());
                } else {
                    datePickerInputViewHolder.mValue.setText(datePickerInputViewHolder.mData.getHint());
                }
                datePickerInputViewHolder.mViewGoup.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        IsbanDatePickerDialogFragment newInstance = IsbanDatePickerDialogFragment.newInstance(datePickerInputViewHolder.mData.getTitle(), datePickerInputViewHolder.mData.getValueText(), ((IFormDataDate) datePickerInputViewHolder.mData).getDateFormat());
                        newInstance.setDialogListener(new IDateDialogListener() {
                            public void onDateSelected(Date date) {
                                if (date != null) {
                                    String format = new SimpleDateFormat(((IFormDataDate) datePickerInputViewHolder.mData).getDateFormat()).format(date);
                                    datePickerInputViewHolder.mData.setValueText(format);
                                    datePickerInputViewHolder.mValue.setText(format);
                                    FormAdapter.this.notifyItemChanged(i2);
                                    FormAdapter.this.a();
                                }
                            }
                        });
                        newInstance.show(FormAdapter.this.d, "FORM_DATE_PICKER_DIALOG");
                    }
                });
            } else {
                datePickerInputViewHolder.mValue.setText(datePickerInputViewHolder.mData.getValueText());
                datePickerInputViewHolder.mSelectorImage.setVisibility(8);
            }
            try {
                datePickerInputViewHolder.mLabel.setContentDescription(cAccessibility.applyFilterGeneral(datePickerInputViewHolder.mLabel.getText().toString()));
                datePickerInputViewHolder.mValue.setContentDescription(cAccessibility.applyFilterDate(datePickerInputViewHolder.mValue.getText().toString()));
                datePickerInputViewHolder.mLabel.setAccessibilityDelegate(this.a);
                datePickerInputViewHolder.mValue.setAccessibilityDelegate(this.a);
            } catch (Exception e5) {
                e5.printStackTrace();
            }
        } else if (viewHolder instanceof IntentValueViewHolder) {
            final IntentValueViewHolder intentValueViewHolder = (IntentValueViewHolder) viewHolder;
            intentValueViewHolder.mData = (IFormDataIntent) this.c.get(i2);
            intentValueViewHolder.mData.setPosition(i2);
            intentValueViewHolder.mLabel.setText(intentValueViewHolder.mData.getLabelText());
            intentValueViewHolder.mValue.setText(intentValueViewHolder.mData.getValueText());
            intentValueViewHolder.mValue.setHint(intentValueViewHolder.mData.getHint());
            intentValueViewHolder.mSelectorImage.setVisibility(0);
            if (intentValueViewHolder.mData.getValue() != null) {
                intentValueViewHolder.mValue.setText(intentValueViewHolder.mData.getSelectedMessage());
            } else {
                intentValueViewHolder.mValue.setText(intentValueViewHolder.mData.getHint());
            }
            intentValueViewHolder.mViewGoup.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (FormAdapter.this.g != null && intentValueViewHolder.mData.getIntentClass() != null) {
                        FormAdapter.this.g.startIntent(intentValueViewHolder.mData.getIntentClass(), intentValueViewHolder.mData.getBundle(), intentValueViewHolder.mData.getRequestId().intValue());
                    }
                }
            });
        } else if (viewHolder instanceof SectionValueViewHolder) {
            SectionValueViewHolder sectionValueViewHolder = (SectionValueViewHolder) viewHolder;
            sectionValueViewHolder.mData = (IFormDataSection) this.c.get(i2);
            sectionValueViewHolder.mData.setPosition(i2);
            sectionValueViewHolder.mLabel.setText(sectionValueViewHolder.mData.getLabelText());
            try {
                sectionValueViewHolder.mLabel.setContentDescription(cAccessibility.applyFilterGeneral(sectionValueViewHolder.mLabel.getText().toString()));
                sectionValueViewHolder.mLabel.setAccessibilityDelegate(this.a);
            } catch (Exception e6) {
                e6.printStackTrace();
            }
        } else if (viewHolder instanceof LeyendValueViewHolder) {
            LeyendValueViewHolder leyendValueViewHolder = (LeyendValueViewHolder) viewHolder;
            leyendValueViewHolder.mData = (IFormDataLeyend) this.c.get(i2);
            leyendValueViewHolder.mData.setPosition(i2);
            leyendValueViewHolder.mText.setText(leyendValueViewHolder.mData.getValueText());
            leyendValueViewHolder.mText.setMovementMethod(LinkMovementMethod.getInstance());
            try {
                leyendValueViewHolder.mText.setContentDescription(cAccessibility.applyFilterGeneral(leyendValueViewHolder.mText.getText().toString()));
                leyendValueViewHolder.mText.setAccessibilityDelegate(this.a);
            } catch (Exception e7) {
                e7.printStackTrace();
            }
        } else if (viewHolder instanceof CheckBoxViewHolder) {
            final CheckBoxViewHolder checkBoxViewHolder = (CheckBoxViewHolder) viewHolder;
            checkBoxViewHolder.mData = (IFormCheckBox) this.c.get(i2);
            checkBoxViewHolder.mData.setPosition(i2);
            AnonymousClass8 r9 = new ClickableSpan() {
                public void onClick(View view) {
                    if (FormAdapter.this.g != null && checkBoxViewHolder.mData.getIntentClass() != null) {
                        FormAdapter.this.g.startIntent(checkBoxViewHolder.mData.getIntentClass(), checkBoxViewHolder.mData.getBundle(), checkBoxViewHolder.mData.getRequestId().intValue());
                    }
                }
            };
            SpannableString spannableString = new SpannableString(checkBoxViewHolder.mData.getLink());
            spannableString.setSpan(r9, 0, spannableString.length(), 33);
            checkBoxViewHolder.mDescription.setText(TextUtils.concat(new CharSequence[]{checkBoxViewHolder.mData.getDescription(), spannableString}));
            checkBoxViewHolder.mDescription.setMovementMethod(LinkMovementMethod.getInstance());
            checkBoxViewHolder.checkBox.setTag(Reintento.Reintento_Falso);
            checkBoxViewHolder.checkBox.setBackground(this.b.getResources().getDrawable(R.drawable.ic_checkbox_off_gris));
            checkBoxViewHolder.checkBox.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    checkBoxViewHolder.validateCheckBox(FormAdapter.this.b);
                    FormAdapter.this.h.validateControl(Boolean.valueOf(checkBoxViewHolder.isCheck));
                }
            });
        } else if (viewHolder instanceof TitleViewHolder) {
            TitleViewHolder titleViewHolder = (TitleViewHolder) viewHolder;
            titleViewHolder.mData = (IFormDataTitle) this.c.get(i2);
            titleViewHolder.bindData();
        } else if (viewHolder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) viewHolder;
            headerViewHolder.mData = (IFormDataHeader) this.c.get(i2);
            headerViewHolder.bindData(this.b);
        } else if (viewHolder instanceof ButtonMarginViewHolder) {
            ButtonMarginViewHolder buttonMarginViewHolder = (ButtonMarginViewHolder) viewHolder;
            buttonMarginViewHolder.mData = (IFormDataButton) this.c.get(i2);
            buttonMarginViewHolder.mData.setPosition(i2);
            this.i = buttonMarginViewHolder.mData;
            if (this.j) {
                buttonMarginViewHolder.mData.setEnableButton(this.k);
            }
            buttonMarginViewHolder.bindData(buttonMarginViewHolder.mData.isEnableButton());
            if (buttonMarginViewHolder.mData.isEnableButton()) {
                buttonMarginViewHolder.btnRed.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        FormAdapter.this.f.setActionButtton();
                    }
                });
            }
        }
    }

    private void a(SelectableInputKeyValueViewHolder selectableInputKeyValueViewHolder, CAccessibility cAccessibility) {
        try {
            selectableInputKeyValueViewHolder.mLabel.setContentDescription(cAccessibility.applyFilterGeneral(selectableInputKeyValueViewHolder.mData.getLabelText()));
            selectableInputKeyValueViewHolder.mValue.setContentDescription(cAccessibility.applyFilterGeneral(selectableInputKeyValueViewHolder.mValue.getText().toString()));
            selectableInputKeyValueViewHolder.mLabel.setAccessibilityDelegate(this.a);
            selectableInputKeyValueViewHolder.mValue.setAccessibilityDelegate(this.a);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private String a(SelectableInputMedioPagoViewHolder selectableInputMedioPagoViewHolder) {
        return !selectableInputMedioPagoViewHolder.mData.getMedioPagoValue().isEmpty() ? selectableInputMedioPagoViewHolder.mData.getMedioPagoValue().getDescripcion() : selectableInputMedioPagoViewHolder.mData.getHint();
    }

    public void setValueOfIntent(Bundle bundle, int i2) {
        if (bundle != null) {
            for (IFormData iFormData : this.c) {
                if (iFormData instanceof IFormDataIntent) {
                    IFormDataIntent iFormDataIntent = (IFormDataIntent) iFormData;
                    if (iFormDataIntent.getRequestId().equals(Integer.valueOf(i2))) {
                        iFormDataIntent.setValue(bundle);
                    }
                }
            }
            notifyDataSetChanged();
            a();
        }
    }

    public int getItemCount() {
        return this.c.size();
    }

    public ErrorMessage isValidForm() {
        ErrorMessage errorMessage = new ErrorMessage("", Boolean.valueOf(true));
        for (int i2 = 0; i2 < this.c.size(); i2++) {
            ErrorMessage isValid = ((IFormData) this.c.get(i2)).isValid();
            if (!isValid.getValid().booleanValue()) {
                return isValid;
            }
        }
        return errorMessage;
    }

    public void setButtonEnable(boolean z) {
        this.j = true;
        this.k = z;
        if (this.i != null) {
            this.i.setEnableButton(this.k);
            notifyItemChanged(this.i.getPosition());
        }
    }

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.f = onButtonClickListener;
    }

    /* access modifiers changed from: private */
    public void a() {
        if (this.e != null) {
            this.e.isValidListener(isValidForm());
        }
    }
}
