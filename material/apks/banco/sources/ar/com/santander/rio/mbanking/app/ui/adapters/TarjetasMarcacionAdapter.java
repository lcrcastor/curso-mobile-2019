package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TarjetaMarcacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TarjetasMarcacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.UsuarioMarcacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.UsuariosMarcacionBean;
import ar.com.santander.rio.mbanking.view.AnimatedExpandableListView.AnimatedExpandableListAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TarjetasMarcacionAdapter extends AnimatedExpandableListAdapter {
    /* access modifiers changed from: private */
    public Context a;
    private List<String> b;
    /* access modifiers changed from: private */
    public HashMap<String, List<TarjetaMarcacionBean>> c;
    private String d;
    /* access modifiers changed from: private */
    public AnalyticsManager e;
    /* access modifiers changed from: private */
    public Boolean f = Boolean.valueOf(false);

    public long getChildId(int i, int i2) {
        return (long) i2;
    }

    public long getGroupId(int i) {
        return (long) i;
    }

    public boolean hasStableIds() {
        return false;
    }

    public boolean isChildSelectable(int i, int i2) {
        return true;
    }

    public TarjetasMarcacionAdapter(Context context, List<String> list, HashMap<String, List<TarjetaMarcacionBean>> hashMap, String str, AnalyticsManager analyticsManager) {
        this.a = context;
        this.b = list;
        this.c = hashMap;
        this.d = str;
        this.e = analyticsManager;
    }

    public TarjetasMarcacionAdapter(Context context, List<String> list, HashMap<String, List<TarjetaMarcacionBean>> hashMap, String str) {
        this.a = context;
        this.b = list;
        this.c = hashMap;
        this.d = str;
    }

    public int getGroupCount() {
        return this.b.size();
    }

    public Object getGroup(int i) {
        return this.b.get(i);
    }

    public Object getChild(int i, int i2) {
        return ((List) this.c.get(this.b.get(i))).get(i2);
    }

    public View getGroupView(int i, boolean z, View view, ViewGroup viewGroup) {
        final String str = (String) getGroup(i);
        if (view == null) {
            view = ((LayoutInflater) this.a.getSystemService("layout_inflater")).inflate(R.layout.list_header_tarjeta_marcacion, null);
        }
        ((TextView) view.findViewById(R.id.F26_04_LBL_DATA_TITULAR)).setText(Html.fromHtml(str));
        final CheckBox checkBox = (CheckBox) view.findViewById(R.id.F26_04_BTN_CHECK_ALL);
        View findViewById = view.findViewById(R.id.F26_04_GROUP_SEPARATOR_TOP);
        View findViewById2 = view.findViewById(R.id.F26_04_GROUP_SEPARATOR_BOTTOM);
        findViewById.setVisibility(0);
        if (z) {
            findViewById2.setVisibility(0);
        } else {
            findViewById2.setVisibility(8);
        }
        if (i == this.b.size() - 1) {
            findViewById2.setVisibility(0);
        }
        if (this.d.equals("1") || this.d.equals("3") || this.d.equals("4")) {
            checkBox.setVisibility(0);
            checkBox.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    TarjetasMarcacionAdapter.this.f = Boolean.valueOf(true);
                    TarjetasMarcacionAdapter.this.e.trackEvent(TarjetasMarcacionAdapter.this.a.getString(R.string.analytics_action_travels_habilitacion_tarjeta), TarjetasMarcacionAdapter.this.a.getString(R.string.analytics_action_travels_seleccionar), TarjetasMarcacionAdapter.this.a.getString(R.string.analytics_category_cards));
                    for (TarjetaMarcacionBean selected : (List) TarjetasMarcacionAdapter.this.c.get(str)) {
                        selected.setSelected(Boolean.valueOf(checkBox.isChecked()));
                    }
                    TarjetasMarcacionAdapter.this.notifyDataSetChanged();
                }
            });
            checkBox.setChecked(a(i));
            if (a(i) || b(i)) {
                checkBox.setButtonDrawable(this.a.getResources().getDrawable(R.drawable.custom_checkbox_selector));
            } else {
                checkBox.setButtonDrawable(this.a.getResources().getDrawable(R.drawable.custom_checkbox_selector_indeterminate));
            }
        } else {
            checkBox.setVisibility(8);
        }
        return view;
    }

    public int getRealChildrenCount(int i) {
        return ((List) this.c.get(this.b.get(i))).size();
    }

    public View getRealChildView(int i, int i2, boolean z, View view, ViewGroup viewGroup) {
        final TarjetaMarcacionBean tarjetaMarcacionBean = (TarjetaMarcacionBean) getChild(i, i2);
        if (view == null) {
            view = ((LayoutInflater) this.a.getSystemService("layout_inflater")).inflate(R.layout.list_item_tarjeta_marcacion, null);
        }
        TextView textView = (TextView) view.findViewById(R.id.F26_04_LBL_DATA_TIPO);
        ((TextView) view.findViewById(R.id.F26_04_LBL_DATA_TARJETA)).setText(tarjetaMarcacionBean.getDescripcion());
        textView.setText(tarjetaMarcacionBean.getCondicion());
        final CheckBox checkBox = (CheckBox) view.findViewById(R.id.F26_04_BTN_CHECK);
        View findViewById = view.findViewById(R.id.F26_04_ITEM_SEPARATOR_BOTTOM);
        if (!z || i == getGroupCount() - 1) {
            findViewById.setVisibility(0);
        } else {
            findViewById.setVisibility(8);
        }
        if (this.d.equals("1") || this.d.equals("3") || this.d.equals("4")) {
            checkBox.setVisibility(0);
            checkBox.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    TarjetasMarcacionAdapter.this.f = Boolean.valueOf(true);
                    TarjetasMarcacionAdapter.this.e.trackEvent(TarjetasMarcacionAdapter.this.a.getString(R.string.analytics_action_travels_habilitacion_tarjeta), TarjetasMarcacionAdapter.this.a.getString(R.string.analytics_action_travels_seleccionar), TarjetasMarcacionAdapter.this.a.getString(R.string.analytics_category_cards));
                    tarjetaMarcacionBean.setSelected(Boolean.valueOf(checkBox.isChecked()));
                    TarjetasMarcacionAdapter.this.notifyDataSetChanged();
                }
            });
            checkBox.setChecked(tarjetaMarcacionBean.isSelected().booleanValue());
        } else {
            checkBox.setVisibility(8);
        }
        return view;
    }

    private boolean a(int i) {
        Boolean valueOf = Boolean.valueOf(true);
        for (TarjetaMarcacionBean isSelected : (List) this.c.get((String) getGroup(i))) {
            if (!isSelected.isSelected().booleanValue()) {
                valueOf = Boolean.valueOf(false);
            }
        }
        return valueOf.booleanValue();
    }

    private boolean b(int i) {
        Boolean valueOf = Boolean.valueOf(true);
        for (TarjetaMarcacionBean isSelected : (List) this.c.get((String) getGroup(i))) {
            if (isSelected.isSelected().booleanValue()) {
                valueOf = Boolean.valueOf(false);
            }
        }
        return valueOf.booleanValue();
    }

    public UsuariosMarcacionBean obtenerTarjetasActualizadas() {
        UsuariosMarcacionBean usuariosMarcacionBean = new UsuariosMarcacionBean();
        ArrayList arrayList = new ArrayList();
        for (String str : this.b) {
            TarjetasMarcacionBean tarjetasMarcacionBean = new TarjetasMarcacionBean();
            ArrayList arrayList2 = new ArrayList();
            for (TarjetaMarcacionBean tarjetaMarcacionBean : (List) this.c.get(str)) {
                tarjetaMarcacionBean.setNombre(str);
                arrayList2.add(tarjetaMarcacionBean);
            }
            tarjetasMarcacionBean.setListaTarjetas(arrayList2);
            arrayList.add(new UsuarioMarcacionBean(str, tarjetasMarcacionBean));
        }
        usuariosMarcacionBean.setListaUsuarios(arrayList);
        return usuariosMarcacionBean;
    }

    public Boolean getHuboCambios() {
        return this.f;
    }

    public void notifyGroupExpanded(int i) {
        super.notifyGroupExpanded(i);
    }
}
