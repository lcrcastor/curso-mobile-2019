package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.Model.ObjectDestinatarios;
import ar.com.santander.rio.mbanking.utils.UtilString;
import ar.com.santander.rio.mbanking.utils.UtilStyleCommons;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class AdapterDestinatarios extends ArrayAdapter<ObjectDestinatarios> {
    private Context a;
    private int b;
    private List<ObjectDestinatarios> c = null;
    private ArrayList<ObjectDestinatarios> d;
    private OnTouchListener e;
    private OnClickListener f;
    private OnClickListener g;

    public AdapterDestinatarios(Context context, int i, List<ObjectDestinatarios> list, OnTouchListener onTouchListener, OnClickListener onClickListener, OnClickListener onClickListener2) {
        super(context, i, list);
        this.b = i;
        this.a = context;
        this.c = list;
        this.d = new ArrayList<>();
        this.d.addAll(list);
        this.e = onTouchListener;
        this.f = onClickListener;
        this.g = onClickListener2;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = ((Activity) this.a).getLayoutInflater().inflate(this.b, viewGroup, false);
        }
        ObjectDestinatarios objectDestinatarios = (ObjectDestinatarios) this.c.get(i);
        ((TextView) view.findViewById(R.id.F12_06_lbl_data_nombreDestinatario)).setText(UtilString.capitalize(Html.fromHtml(objectDestinatarios.getNombre()).toString()));
        ((TextView) view.findViewById(R.id.F12_06_lbl_data_mailDestinatario)).setText(Html.fromHtml(objectDestinatarios.getMail()));
        ((RelativeLayout) view.findViewById(R.id.F12_06_rll_selectedItem)).setVisibility(objectDestinatarios.getSelected().booleanValue() ? 0 : 8);
        if (this.e == null) {
            ((RelativeLayout) view.findViewById(R.id.F12_06A_rll_botones_acciones)).setVisibility(8);
        } else {
            view.setOnTouchListener(this.e);
            Button button = (Button) view.findViewById(R.id.F12_06A_btn_destinatario_editar);
            button.setContentDescription(getContext().getString(R.string.description_button_editar));
            button.setOnClickListener(this.f);
            Button button2 = (Button) view.findViewById(R.id.F12_06A_btn_destinatario_eliminar);
            button2.setContentDescription(getContext().getString(R.string.description_button_eliminar));
            button2.setOnClickListener(this.g);
            view.findViewById(R.id.F12_06A_rll_botones_acciones).setVisibility(8);
            objectDestinatarios.setSwiped(Boolean.valueOf(false));
        }
        view.setTag(Integer.valueOf(i));
        applyContentDescription(view, objectDestinatarios);
        return view;
    }

    public void applyContentDescription(View view, ObjectDestinatarios objectDestinatarios) {
        CAccessibility.getInstance(this.a);
        TextView textView = (TextView) view.findViewById(R.id.F12_06_lbl_data_nombreDestinatario);
        textView.setContentDescription(String.format(this.a.getString(R.string.CONTENT_NOMBRE), new Object[]{textView.getText().toString()}));
        TextView textView2 = (TextView) view.findViewById(R.id.F12_06_lbl_data_mailDestinatario);
        textView2.setContentDescription(String.format(this.a.getString(R.string.CONTENT_EMAIL), new Object[]{textView2.getText().toString()}));
    }

    public int getFilteredDisplayedCount() {
        return this.c.size();
    }

    public int getTotalCount() {
        return this.d.size();
    }

    public void addItem(ObjectDestinatarios objectDestinatarios) {
        this.d.add(objectDestinatarios);
    }

    public void removeItem(ObjectDestinatarios objectDestinatarios) {
        this.d.remove(objectDestinatarios);
    }

    public void filter(String str) {
        String lowerCase = str.toLowerCase(Locale.getDefault());
        this.c.clear();
        if (lowerCase.length() == 0) {
            this.c.addAll(this.d);
        } else {
            Iterator it = this.d.iterator();
            while (it.hasNext()) {
                ObjectDestinatarios objectDestinatarios = (ObjectDestinatarios) it.next();
                if (objectDestinatarios.getNombre() != null) {
                    if (objectDestinatarios.getNombre().toLowerCase(Locale.getDefault()).contains(lowerCase)) {
                        this.c.add(objectDestinatarios);
                    } else if (objectDestinatarios.getMail() != null && objectDestinatarios.getMail().toLowerCase(Locale.getDefault()).contains(lowerCase)) {
                        this.c.add(objectDestinatarios);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public void applyLetterSpacing(View view) {
        UtilStyleCommons.setLetterSpacing(view.findViewById(R.id.F12_06_lbl_data_nombreDestinatario), -1.5f);
        UtilStyleCommons.setLetterSpacing(view.findViewById(R.id.F12_06_lbl_data_mailDestinatario), -1.5f);
    }
}
