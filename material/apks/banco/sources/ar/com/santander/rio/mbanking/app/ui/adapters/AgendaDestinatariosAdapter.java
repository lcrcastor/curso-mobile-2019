package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filter.FilterResults;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios;
import java.util.ArrayList;
import java.util.Iterator;

public class AgendaDestinatariosAdapter extends BaseAdapter implements Filterable {
    public static final String TAG_FRAGMENT_LOAD = "tag_fragment_load";
    private static final String a = "ar.com.santander.rio.mbanking.app.ui.adapters.AgendaDestinatariosAdapter";
    private static int b;
    private Context c;
    /* access modifiers changed from: private */
    public ArrayList<AgendaDestinatarios> d;
    /* access modifiers changed from: private */
    public ArrayList<AgendaDestinatarios> e;
    private LayoutInflater f = null;
    private ItemFilter g = new ItemFilter();
    private OnClickListener h;
    private AgendaDestinatarios i;
    private ViewHolder j;
    private OnTouchListener k;
    private OnClickListener l;
    private OnClickListener m;

    class ItemFilter extends Filter {
        private ItemFilter() {
        }

        /* access modifiers changed from: protected */
        public FilterResults performFiltering(CharSequence charSequence) {
            String lowerCase = charSequence.toString().toLowerCase();
            FilterResults filterResults = new FilterResults();
            ArrayList a2 = AgendaDestinatariosAdapter.this.d;
            int size = a2.size();
            ArrayList arrayList = new ArrayList(size);
            for (int i = 0; i < size; i++) {
                String titulo = ((AgendaDestinatarios) a2.get(i)).getTitulo();
                String nombre = ((AgendaDestinatarios) a2.get(i)).getNombre();
                if (titulo.toLowerCase().contains(lowerCase) || nombre.toLowerCase().contains(lowerCase)) {
                    arrayList.add(a2.get(i));
                }
            }
            filterResults.values = arrayList;
            filterResults.count = arrayList.size();
            return filterResults;
        }

        /* access modifiers changed from: protected */
        public void publishResults(CharSequence charSequence, FilterResults filterResults) {
            AgendaDestinatariosAdapter.this.e = (ArrayList) filterResults.values;
            AgendaDestinatariosAdapter.this.notifyDataSetChanged();
        }
    }

    public static class ViewHolder {
        View a;
        TextView b;
        TextView c;
        TextView d;
        TextView e;
        TextView f;
        ImageView g;
        RelativeLayout h;
        Button i;
        Button j;
        public int position;
    }

    public long getItemId(int i2) {
        return (long) i2;
    }

    public int getItemViewType(int i2) {
        return 0;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public AgendaDestinatariosAdapter(Context context, ArrayList<AgendaDestinatarios> arrayList, OnClickListener onClickListener, AgendaDestinatarios agendaDestinatarios, OnTouchListener onTouchListener, OnClickListener onClickListener2, OnClickListener onClickListener3) {
        this.c = context;
        Log.d(a, "Constructing CustomAdapter");
        this.e = arrayList;
        this.d = arrayList;
        this.h = onClickListener;
        this.i = agendaDestinatarios;
        this.f = LayoutInflater.from(context);
        this.k = onTouchListener;
        this.l = onClickListener2;
        this.m = onClickListener3;
    }

    public int getCount() {
        return this.e.size();
    }

    public Object getItem(int i2) {
        return this.e.get(i2);
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public View getView(int i2, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        AgendaDestinatarios agendaDestinatarios = (AgendaDestinatarios) getItem(i2);
        agendaDestinatarios.setPosition(i2);
        if (view == null) {
            view = this.f.inflate(R.layout.agenda_destinatarios_row, null);
            b++;
            String str = a;
            StringBuilder sb = new StringBuilder();
            sb.append(b);
            sb.append(" convertViews have been created");
            Log.d(str, sb.toString());
            viewHolder = new ViewHolder();
            viewHolder.a = view.findViewById(R.id.ItemAgendado);
            viewHolder.b = (TextView) view.findViewById(R.id.textViewNombreTitulo);
            viewHolder.c = (TextView) view.findViewById(R.id.textViewNombre);
            viewHolder.d = (TextView) view.findViewById(R.id.textViewInfo1);
            viewHolder.e = (TextView) view.findViewById(R.id.textViewInfo2);
            viewHolder.f = (TextView) view.findViewById(R.id.textViewAlias);
            viewHolder.g = (ImageView) view.findViewById(R.id.imageViewCheck);
            viewHolder.g.setOnClickListener(this.h);
            viewHolder.h = (RelativeLayout) view.findViewById(R.id.rllGroup_botones_acciones);
            viewHolder.i = (Button) view.findViewById(R.id.btn_destinatario_editar);
            viewHolder.j = (Button) view.findViewById(R.id.btn_destinatario_eliminar);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        CAccessibility cAccessibility = new CAccessibility(this.c);
        try {
            viewHolder.position = i2;
            viewHolder.g.setTag(agendaDestinatarios);
            viewHolder.b.setText(agendaDestinatarios.getTitulo());
            viewHolder.c.setText(agendaDestinatarios.getNombre());
            viewHolder.c.setContentDescription(CAccessibility.getInstance(this.c).applyFilterGeneral(viewHolder.c.getText().toString()));
            viewHolder.d.setText(Html.fromHtml(agendaDestinatarios.getInfo1()));
            viewHolder.e.setText(Html.fromHtml(agendaDestinatarios.getInfo2()));
            viewHolder.e.setContentDescription(cAccessibility.applyFilterAccount(viewHolder.e.getText().toString()));
            String alias = agendaDestinatarios.getAlias();
            if (alias == null || alias.isEmpty()) {
                viewHolder.f.setVisibility(8);
            } else {
                viewHolder.f.setVisibility(0);
                viewHolder.f.setText(Html.fromHtml(alias));
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(viewHolder.b.getText().toString());
            sb2.append(", ");
            sb2.append(viewHolder.c.getContentDescription());
            sb2.append(", ");
            sb2.append(viewHolder.d.getText().toString());
            sb2.append(", ");
            sb2.append(viewHolder.e.getContentDescription());
            sb2.append(", ");
            sb2.append(viewHolder.f.getText().toString());
            String sb3 = sb2.toString();
            if (this.i == null || !this.i.getInfo2().equals(agendaDestinatarios.getInfo2()) || !this.i.getChecked()) {
                updateCheckBox(viewHolder.g, false);
                viewHolder.g.setContentDescription(this.c.getString(R.string.BTN_OPTION_NOT_SELECTED, new Object[]{sb3}));
            } else {
                updateCheckBox(viewHolder.g, true);
                viewHolder.g.setContentDescription(this.c.getString(R.string.BTN_OPTION_SELECTED, new Object[]{sb3}));
                this.j = viewHolder;
            }
        } catch (Exception e2) {
            e2.fillInStackTrace();
        }
        if (this.k == null) {
            viewHolder.h.setVisibility(8);
            view.findViewById(R.id.rllGroup_selectedItem).setVisibility(0);
        } else {
            view.findViewById(R.id.rllGroup_selectedItem).setVisibility(8);
            view.setOnTouchListener(this.k);
            viewHolder.i.setOnClickListener(this.l);
            viewHolder.j.setOnClickListener(this.m);
            viewHolder.i.setContentDescription(this.c.getString(R.string.description_button_editar));
            viewHolder.j.setContentDescription(this.c.getString(R.string.description_button_eliminar));
            try {
                viewHolder.i.setContentDescription(CAccessibility.getInstance(this.c).applyFilterGeneral(this.c.getString(R.string.description_button_editar)));
                viewHolder.j.setContentDescription(CAccessibility.getInstance(this.c).applyFilterGeneral(this.c.getString(R.string.description_button_eliminar)));
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            viewHolder.h.setVisibility(8);
            agendaDestinatarios.setSwiped(Boolean.valueOf(false));
        }
        return view;
    }

    public void updateAdapterChecked() {
        a(false);
        if (this.j != null) {
            updateCheckBox(this.j.g, false);
        }
        if (this.i != null) {
            this.i.setChecked(false);
        }
    }

    public void updateCheckBox(ImageView imageView, boolean z) {
        if (imageView == null) {
            return;
        }
        if (z) {
            imageView.setImageDrawable(ContextCompat.getDrawable(this.c, R.drawable.icon_check_on));
        } else {
            imageView.setImageDrawable(ContextCompat.getDrawable(this.c, R.drawable.icon_check_off));
        }
    }

    private void a(boolean z) {
        Iterator it = this.e.iterator();
        while (it.hasNext()) {
            ((AgendaDestinatarios) it.next()).setChecked(z);
        }
    }

    public Filter getFilter() {
        return this.g;
    }

    public void updateDestinatarioSeleccionado(AgendaDestinatarios agendaDestinatarios) {
        try {
            this.i = agendaDestinatarios;
        } catch (Exception e2) {
            e2.fillInStackTrace();
        }
    }

    public void clearData() {
        this.d.clear();
        this.e.clear();
    }
}
