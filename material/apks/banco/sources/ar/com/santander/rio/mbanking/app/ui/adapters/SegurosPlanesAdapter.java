package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PlanSeguroBean;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import java.util.List;

public abstract class SegurosPlanesAdapter extends ArrayAdapter<PlanSeguroBean> {
    private int a;
    Context b;
    /* access modifiers changed from: private */
    public List<PlanSeguroBean> c;

    public abstract void checkboxChanged(String str, String str2);

    public SegurosPlanesAdapter(Context context, int i, List<PlanSeguroBean> list) {
        super(context, i, list);
        this.b = context;
        this.a = i;
        this.c = list;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = ((Activity) this.b).getLayoutInflater().inflate(this.a, viewGroup, false);
        }
        PlanSeguroBean planSeguroBean = (PlanSeguroBean) this.c.get(i);
        TextView textView = (TextView) view.findViewById(R.id.F27_31_LBL_DATA_PLAN);
        TextView textView2 = (TextView) view.findViewById(R.id.F27_31_LBL_DATA_DESCRIPCION);
        TextView textView3 = (TextView) view.findViewById(R.id.F27_31_LBL_DATA_DESCRIPCION2);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.F27_31_BTN_CHECK);
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.F27_31_RLAYOUT_ITEM_SEGUROS_PLAN);
        planSeguroBean.setListPosition(i);
        if (planSeguroBean.isChecked()) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }
        relativeLayout.setTag(String.valueOf(i));
        relativeLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                for (PlanSeguroBean planSeguroBean : SegurosPlanesAdapter.this.c) {
                    if (planSeguroBean.getListPosition() == Integer.valueOf(view.getTag().toString()).intValue()) {
                        planSeguroBean.setChecked(true);
                        SegurosPlanesAdapter.this.checkboxChanged(planSeguroBean.getSumaAsegurada1(), planSeguroBean.getCuota());
                    } else {
                        planSeguroBean.setChecked(false);
                    }
                }
                SegurosPlanesAdapter.this.notifyDataSetChanged();
            }
        });
        textView.setText(Html.fromHtml(planSeguroBean.getNombre()).toString());
        StringBuilder sb = new StringBuilder();
        sb.append(this.b.getString(R.string.ID_4029_SEGUROS_LBL_SUMA_ASEG));
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(Html.fromHtml(planSeguroBean.getDescripcionSuma1()).toString());
        sb.append(": ");
        sb.append(planSeguroBean.getSumaAsegurada1());
        textView2.setText(sb.toString());
        if ((planSeguroBean.getDescripcionSuma2() == null || planSeguroBean.getDescripcionSuma2().equals("")) && (planSeguroBean.getSumaAsegurada2() == null || planSeguroBean.getSumaAsegurada2().equals(""))) {
            textView3.setVisibility(8);
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.b.getString(R.string.ID_4029_SEGUROS_LBL_SUMA_ASEG));
            sb2.append(UtilsCuentas.SEPARAOR2);
            sb2.append(Html.fromHtml(planSeguroBean.getDescripcionSuma2()).toString());
            sb2.append(": ");
            sb2.append(planSeguroBean.getSumaAsegurada2());
            textView3.setText(sb2.toString());
            textView3.setVisibility(0);
        }
        try {
            textView2.setContentDescription(CAccessibility.getInstance(getContext()).applyFilterAmount(textView2.getText().toString()));
            if (textView3.getVisibility() != 8) {
                textView3.setContentDescription(CAccessibility.getInstance(getContext()).applyFilterAmount(textView3.getText().toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
}
