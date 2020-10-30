package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.CAmount;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAmountAcc;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CDateAcc;
import ar.com.santander.rio.mbanking.components.infinitescroll.InfiniteScrollAdapter;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TransactionResponseBean;
import ar.com.santander.rio.mbanking.utils.UtilCurrency;
import ar.com.santander.rio.mbanking.view.AmountView;
import ar.com.santander.rio.mbanking.view.DateView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import java.util.Collection;

public abstract class TransactionsAccountAdapter extends InfiniteScrollAdapter {
    private ArrayList<TransactionResponseBean> a = new ArrayList<>();

    static class ViewHolder {
        @InjectView(2131366314)
        AmountView tvAmount;
        @InjectView(2131366318)
        TextView tvConcept;
        @InjectView(2131366324)
        DateView tvDate;

        public ViewHolder(View view) {
            ButterKnife.inject((Object) this, view);
        }
    }

    public abstract void onItemClickTransactionAdapter(TransactionResponseBean transactionResponseBean);

    public TransactionsAccountAdapter(Context context) {
        super(context);
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

    public Object getRealItem(int i) {
        if (this.a != null) {
            return this.a.get(i);
        }
        return null;
    }

    public View getRealView(LayoutInflater layoutInflater, final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null || view.getId() != R.id.wrapperRow) {
            view = layoutInflater.inflate(R.layout.list_item_movimientos, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        a(viewHolder, (TransactionResponseBean) getRealItem(i));
        view.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                TransactionsAccountAdapter.this.onItemClickTransactionAdapter((TransactionResponseBean) TransactionsAccountAdapter.this.getRealItem(i));
            }
        });
        return view;
    }

    public View getLoadingView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        return layoutInflater.inflate(R.layout.list_loading, null);
    }

    private void a(ViewHolder viewHolder, TransactionResponseBean transactionResponseBean) {
        if (viewHolder != null) {
            if (viewHolder.tvDate != null) {
                viewHolder.tvDate.setStringDate(transactionResponseBean.fecha);
                viewHolder.tvDate.setCElementAcc(new CDateAcc());
            }
            if (viewHolder.tvConcept != null) {
                viewHolder.tvConcept.setText(transactionResponseBean.descripcionLarga);
                viewHolder.tvConcept.setContentDescription(viewHolder.tvConcept.getText().toString().toLowerCase());
            }
            if (viewHolder.tvAmount != null) {
                CAmount cAmount = new CAmount(transactionResponseBean.importe);
                cAmount.setSymbolCurrency(UtilCurrency.getSimbolCurrencyFromString(transactionResponseBean.moneda));
                viewHolder.tvAmount.setAmount(cAmount.getAmount());
                viewHolder.tvAmount.setCElementAcc(new CAmountAcc());
            }
        }
    }
}
