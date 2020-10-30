package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.AccessibilityDelegate;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LimiteExtraccionBean;
import java.util.List;

public class LimiteExtraccionModificacionAdapter extends Adapter<LimiteExtraccionHolder> {
    private Context a;
    private List<LimiteExtraccionBean> b;
    /* access modifiers changed from: private */
    public LimiteExtraccionBean c = null;
    public event event;

    public class LimiteExtraccionHolder extends ViewHolder {
        public ImageView ivCheck;
        public TextView tvLabel;

        LimiteExtraccionHolder(View view) {
            super(view);
            this.tvLabel = (TextView) view.findViewById(R.id.tvLabel);
            this.ivCheck = (ImageView) view.findViewById(R.id.ivCheck);
        }
    }

    public interface event {
        void onClickItem();
    }

    public LimiteExtraccionModificacionAdapter(Context context, event event2) {
        this.a = context;
        this.event = event2;
    }

    public void setList(List<LimiteExtraccionBean> list) {
        if (this.b != null) {
            this.c = null;
        }
        this.b = list;
        notifyDataSetChanged();
    }

    public LimiteExtraccionHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new LimiteExtraccionHolder(LayoutInflater.from(this.a).inflate(R.layout.generic_monoline_check_list_item, viewGroup, false));
    }

    public void onBindViewHolder(LimiteExtraccionHolder limiteExtraccionHolder, int i) {
        final LimiteExtraccionBean limiteExtraccionBean = (LimiteExtraccionBean) this.b.get(i);
        limiteExtraccionHolder.tvLabel.setText(limiteExtraccionBean.getImporte());
        limiteExtraccionHolder.ivCheck.setImageResource(limiteExtraccionBean == this.c ? R.drawable.ic_checkbox_checked : R.drawable.ic_checkbox_unchecked);
        limiteExtraccionHolder.ivCheck.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                LimiteExtraccionModificacionAdapter.this.c = limiteExtraccionBean;
                LimiteExtraccionModificacionAdapter.this.event.onClickItem();
                LimiteExtraccionModificacionAdapter.this.notifyDataSetChanged();
            }
        });
        try {
            limiteExtraccionHolder.tvLabel.setContentDescription(CAccessibility.getInstance(this.a).applyFilterAmount(limiteExtraccionBean.getImporte()));
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        limiteExtraccionHolder.tvLabel.setAccessibilityDelegate(new AccessibilityDelegate() {
            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.setText(view.getContentDescription());
            }
        });
    }

    public int getItemCount() {
        if (this.b != null) {
            return this.b.size();
        }
        return 0;
    }

    public LimiteExtraccionBean getLeSelectedItem() {
        return this.c;
    }
}
