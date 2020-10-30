package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.CAmount;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAmountAcc;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CDateAcc;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.components.infinitescroll.InfiniteScrollAdapter;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean;
import ar.com.santander.rio.mbanking.utils.UtilCurrency;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import ar.com.santander.rio.mbanking.view.AmountView;
import ar.com.santander.rio.mbanking.view.DateView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import java.util.Collection;

public abstract class PaymentServicesAdapter extends InfiniteScrollAdapter implements BasePaymentAdapter {
    private ArrayList<DatosDeudaBean> a = new ArrayList<>();
    private CAccessibility b;

    static class ViewHolder {
        @InjectView(2131366314)
        AmountView tvAmount;
        @InjectView(2131366324)
        DateView tvDate;
        @InjectView(2131366316)
        TextView vCompDesc;
        @InjectView(2131366317)
        TextView vCompId;
        @InjectView(2131366442)
        View wrapperArrow;

        public ViewHolder(View view) {
            ButterKnife.inject((Object) this, view);
            if (this.wrapperArrow != null) {
                this.wrapperArrow.setVisibility(0);
            }
        }
    }

    public InfiniteScrollAdapter getAdapter() {
        return this;
    }

    public int getItemViewType(int i) {
        return i == 0 ? 0 : 1;
    }

    public int getViewTypeCount() {
        return 2;
    }

    public abstract void onItemClickTransactionAdapter(DatosDeudaBean datosDeudaBean);

    public PaymentServicesAdapter(Context context) {
        super(context);
        this.b = new CAccessibility(context);
        addSectionHeader();
        setDoneLoading();
    }

    public void addSectionHeader() {
        if (this.a != null) {
            this.a.add(0, new DatosDeudaBean());
        }
    }

    public ArrayList getItems() {
        return this.a;
    }

    public void addItems(Collection collection) {
        if (collection == null || collection.size() <= 0) {
            super.setDoneLoading();
        } else {
            this.a.addAll(collection);
        }
        notifyDataSetChanged();
    }

    public void cleanItems() {
        if (this.a != null) {
            this.a.clear();
            addSectionHeader();
        }
        setDoneLoading();
        notifyDataSetChanged();
    }

    public Object getRealItem(int i) {
        if (this.a != null) {
            return this.a.get(i);
        }
        return null;
    }

    public View getRealView(LayoutInflater layoutInflater, final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (getItemViewType(i) == 1) {
            if (view == null || view.getId() != R.id.wrapperRow) {
                view = layoutInflater.inflate(R.layout.list_item_payments, viewGroup, false);
                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            a(viewHolder, (DatosDeudaBean) getRealItem(i));
            view.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    PaymentServicesAdapter.this.onItemClickTransactionAdapter((DatosDeudaBean) PaymentServicesAdapter.this.getRealItem(i));
                }
            });
            return view;
        }
        View inflate = layoutInflater.inflate(R.layout.pagos_header_infinite_scroll, viewGroup, false);
        a(inflate);
        return inflate;
    }

    public View getLoadingView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        return layoutInflater.inflate(R.layout.list_loading, null);
    }

    private void a(ViewHolder viewHolder, DatosDeudaBean datosDeudaBean) {
        if (viewHolder != null) {
            try {
                if (viewHolder.tvDate != null) {
                    viewHolder.tvDate.setCElementAcc(new CDateAcc());
                    if (datosDeudaBean.vencimiento == null || !UtilDate.isDate(datosDeudaBean.vencimiento, Constants.FORMAT_DATE_WS_2)) {
                        viewHolder.tvDate.setDateStr(Constants.FORMAT_DATE_NULL);
                    } else {
                        viewHolder.tvDate.setDateStr(UtilDate.getDateFormat(datosDeudaBean.vencimiento, Constants.FORMAT_DATE_WS_2));
                    }
                }
                if (viewHolder.vCompDesc != null) {
                    viewHolder.vCompDesc.setText(datosDeudaBean.empDescr);
                }
                if (viewHolder.vCompId != null) {
                    TextView textView = viewHolder.vCompId;
                    StringBuilder sb = new StringBuilder();
                    sb.append(this.mContext.getString(R.string.TEXT_ID));
                    sb.append(UtilsCuentas.SEPARAOR2);
                    sb.append(datosDeudaBean.identificacion);
                    textView.setText(sb.toString());
                    TextView textView2 = viewHolder.vCompId;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(this.mContext.getString(R.string.TEXT_ID));
                    sb2.append(UtilsCuentas.SEPARAOR2);
                    sb2.append(CAccessibility.getInstance(this.mContext).applyFilterCharacterToCharacter(datosDeudaBean.identificacion));
                    textView2.setContentDescription(sb2.toString());
                }
                if (viewHolder.tvAmount != null) {
                    CAmount cAmount = new CAmount(datosDeudaBean.importe);
                    viewHolder.tvAmount.setCElementAcc(new CAmountAcc());
                    if (cAmount.getFormatDoubleAmount() > 0.0d) {
                        AmountView amountView = viewHolder.tvAmount;
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(UtilCurrency.getSimbolCurrencyFromString(datosDeudaBean.moneda));
                        sb3.append(CAmount.getInstance(datosDeudaBean.importe).getAmount());
                        amountView.setAmount(sb3.toString());
                        return;
                    }
                    viewHolder.tvAmount.setAmount("");
                }
            } catch (Exception e) {
                Log.e("@dev", "Excepción al obtener el valor de:", e);
            }
        }
    }

    private void a(View view) {
        if (view != null) {
            try {
                TextView textView = (TextView) view.findViewById(R.id.vTitle);
                if (textView != null) {
                    textView.setText(this.mContext.getString(R.string.ID173_PAYMENT_MAIN_LBL_TITLE));
                }
                TextView textView2 = (TextView) view.findViewById(R.id.vFirstCol);
                if (textView2 != null) {
                    textView2.setText(this.mContext.getString(R.string.ID174_PAYMENT_MAIN_LBL_ENDDATE));
                    textView2.setContentDescription(this.b.applyFilterGeneral(textView2.getText().toString()));
                }
                TextView textView3 = (TextView) view.findViewById(R.id.vSecondCol);
                if (textView3 != null) {
                    textView3.setText(this.mContext.getString(R.string.ID175_PAYMENT_MAIN_LBL_COMPANY));
                }
                TextView textView4 = (TextView) view.findViewById(R.id.vThirdCol);
                if (textView4 != null) {
                    textView4.setText(this.mContext.getString(R.string.ID176_PAYMENT_MAIN_LBL_PAYMENTAMOUNT));
                }
            } catch (Exception e) {
                Log.e("@dev", "Excepción al obtener el valor de:", e);
            }
        }
    }
}
