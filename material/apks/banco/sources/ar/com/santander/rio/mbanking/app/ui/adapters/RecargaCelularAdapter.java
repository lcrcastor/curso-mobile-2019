package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CElementAcc;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CPhoneNumberAcc;
import ar.com.santander.rio.mbanking.components.infinitescroll.InfiniteScrollAdapter;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import java.util.Collection;

public abstract class RecargaCelularAdapter extends InfiniteScrollAdapter implements BasePaymentAdapter {
    private ArrayList<DatosDeudaBean> a = new ArrayList<>();
    private CElementAcc b;

    static class ViewHolder {
        @InjectView(2131366316)
        TextView vCompDesc;
        @InjectView(2131366335)
        TextView vPhoneNumber;

        public ViewHolder(View view) {
            ButterKnife.inject((Object) this, view);
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

    public RecargaCelularAdapter(Context context) {
        super(context);
        addSectionHeader();
        setDoneLoading();
        this.b = new CPhoneNumberAcc();
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
                view = layoutInflater.inflate(R.layout.list_item_payments_celular, viewGroup, false);
                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            a(viewHolder, (DatosDeudaBean) getRealItem(i));
            view.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    RecargaCelularAdapter.this.onItemClickTransactionAdapter((DatosDeudaBean) RecargaCelularAdapter.this.getRealItem(i));
                }
            });
            return view;
        }
        View inflate = layoutInflater.inflate(R.layout.recargas_header_infinite_scroll, viewGroup, false);
        a(inflate);
        return inflate;
    }

    public View getLoadingView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        return layoutInflater.inflate(R.layout.list_loading, null);
    }

    private void a(ViewHolder viewHolder, DatosDeudaBean datosDeudaBean) {
        if (viewHolder != null) {
            try {
                if (viewHolder.vPhoneNumber != null) {
                    viewHolder.vPhoneNumber.setText(datosDeudaBean.identificacion);
                    this.b.applyFilter(viewHolder.vPhoneNumber, datosDeudaBean.identificacion);
                }
                if (viewHolder.vCompDesc != null) {
                    viewHolder.vCompDesc.setText(datosDeudaBean.empDescr);
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
                    textView.setText(this.mContext.getString(R.string.ID361_CELULAR_MAIN_LBL_RECHARGE));
                }
                TextView textView2 = (TextView) view.findViewById(R.id.vFirstCol);
                if (textView2 != null) {
                    textView2.setText(this.mContext.getString(R.string.ID375_CELULAR_DETAIL_LBL_NUMBERTORE));
                }
                TextView textView3 = (TextView) view.findViewById(R.id.vSecondCol);
                if (textView3 != null) {
                    textView3.setText(this.mContext.getString(R.string.ID376_CELULAR_DETAIL_LBL_COMPANY));
                }
                TextView textView4 = (TextView) view.findViewById(R.id.vThirdCol);
                if (textView4 != null) {
                    textView4.setVisibility(8);
                }
            } catch (Exception e) {
                Log.e("@dev", "Excepción al obtener el valor de:", e);
            }
        }
    }
}
