package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.services.soap.beans.ListaTarjetas;
import ar.com.santander.rio.mbanking.services.soap.beans.ListaUsuarios;
import ar.com.santander.rio.mbanking.services.soap.beans.Tarjeta;
import ar.com.santander.rio.mbanking.services.soap.beans.Usuario;
import ar.com.santander.rio.mbanking.view.AnimatedExpandableListView.AnimatedExpandableListAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProgramaWomenAdapter extends AnimatedExpandableListAdapter {
    private Context a;
    private List<String> b;
    /* access modifiers changed from: private */
    public HashMap<String, List<Tarjeta>> c;
    private String d;
    /* access modifiers changed from: private */
    public Boolean e = Boolean.valueOf(false);
    /* access modifiers changed from: private */
    public OnChangeSelectedStatusListener f;
    private boolean g = true;

    public interface OnChangeSelectedStatusListener {
        void onChangeSelectedStatus(boolean z);
    }

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

    public void setExpandible(boolean z) {
        this.g = z;
    }

    public boolean isExpandible() {
        return this.g;
    }

    public void setOnChangeSelectedStatusListener(OnChangeSelectedStatusListener onChangeSelectedStatusListener) {
        this.f = onChangeSelectedStatusListener;
    }

    public ProgramaWomenAdapter(Context context, List<String> list, HashMap<String, List<Tarjeta>> hashMap, String str) {
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
        View findViewById3 = view.findViewById(R.id.F26_04_BTN_EXPAND);
        if (!this.g) {
            findViewById3.setVisibility(8);
        }
        if (z) {
            findViewById3.setBackground(this.a.getResources().getDrawable(R.drawable.arrow_down));
        } else {
            findViewById3.setBackground(this.a.getResources().getDrawable(R.drawable.arrow_left));
        }
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
                    ProgramaWomenAdapter.this.e = Boolean.valueOf(true);
                    for (Tarjeta selected : (List) ProgramaWomenAdapter.this.c.get(str)) {
                        selected.setSelected(Boolean.valueOf(checkBox.isChecked()));
                    }
                    ProgramaWomenAdapter.this.notifyDataSetChanged();
                    if (ProgramaWomenAdapter.this.f == null) {
                        return;
                    }
                    if (ProgramaWomenAdapter.this.areSelectedChilds()) {
                        ProgramaWomenAdapter.this.f.onChangeSelectedStatus(true);
                    } else {
                        ProgramaWomenAdapter.this.f.onChangeSelectedStatus(false);
                    }
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
        if (!this.g) {
            ((ExpandableListView) viewGroup).expandGroup(i);
        }
        return view;
    }

    public int getRealChildrenCount(int i) {
        return ((List) this.c.get(this.b.get(i))).size();
    }

    public View getRealChildView(int i, int i2, boolean z, View view, ViewGroup viewGroup) {
        final Tarjeta tarjeta = (Tarjeta) getChild(i, i2);
        if (view == null) {
            view = ((LayoutInflater) this.a.getSystemService("layout_inflater")).inflate(R.layout.list_item_tarjeta_marcacion, null);
        }
        TextView textView = (TextView) view.findViewById(R.id.F26_04_LBL_DATA_TARJETA);
        TextView textView2 = (TextView) view.findViewById(R.id.F26_04_LBL_DATA_TIPO);
        textView.setText(tarjeta.getDescripcion());
        textView2.setText(tarjeta.getCondicion());
        try {
            textView.setContentDescription(CAccessibility.getInstance(this.a).applyFilterCreditCard(textView.getText().toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
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
                    ProgramaWomenAdapter.this.e = Boolean.valueOf(true);
                    tarjeta.setSelected(Boolean.valueOf(checkBox.isChecked()));
                    ProgramaWomenAdapter.this.notifyDataSetChanged();
                    if (ProgramaWomenAdapter.this.f == null) {
                        return;
                    }
                    if (ProgramaWomenAdapter.this.areSelectedChilds()) {
                        ProgramaWomenAdapter.this.f.onChangeSelectedStatus(true);
                    } else {
                        ProgramaWomenAdapter.this.f.onChangeSelectedStatus(false);
                    }
                }
            });
            checkBox.setChecked(tarjeta.isSelected().booleanValue());
        } else {
            checkBox.setVisibility(8);
        }
        return view;
    }

    private boolean a(int i) {
        Boolean valueOf = Boolean.valueOf(true);
        for (Tarjeta isSelected : (List) this.c.get((String) getGroup(i))) {
            if (!isSelected.isSelected().booleanValue()) {
                valueOf = Boolean.valueOf(false);
            }
        }
        return valueOf.booleanValue();
    }

    private boolean b(int i) {
        Boolean valueOf = Boolean.valueOf(true);
        for (Tarjeta isSelected : (List) this.c.get((String) getGroup(i))) {
            if (isSelected.isSelected().booleanValue()) {
                valueOf = Boolean.valueOf(false);
            }
        }
        return valueOf.booleanValue();
    }

    public boolean areSelectedChilds() {
        for (int i = 0; i < this.b.size(); i++) {
            if (!b(i)) {
                return true;
            }
        }
        return false;
    }

    public ListaUsuarios obtenerTarjetasActualizadas() {
        ListaUsuarios listaUsuarios = new ListaUsuarios();
        ArrayList arrayList = new ArrayList();
        for (String str : this.b) {
            ListaTarjetas listaTarjetas = new ListaTarjetas();
            ArrayList arrayList2 = new ArrayList();
            for (Tarjeta tarjeta : (List) this.c.get(str)) {
                tarjeta.setCondicion(str);
                arrayList2.add(tarjeta);
            }
            listaTarjetas.setTarjeta(arrayList2);
            arrayList.add(new Usuario(str, listaTarjetas));
        }
        listaUsuarios.setUsuario(arrayList);
        return listaUsuarios;
    }

    public Boolean getHuboCambios() {
        return this.e;
    }

    public void notifyGroupExpanded(int i) {
        super.notifyGroupExpanded(i);
    }
}
