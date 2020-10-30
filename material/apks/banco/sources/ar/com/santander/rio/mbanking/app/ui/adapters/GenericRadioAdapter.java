package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import java.util.List;

public class GenericRadioAdapter extends Adapter<ViewHolder> {
    public static Integer HDPI = Integer.valueOf(480);
    public static Integer XHDPI = Integer.valueOf(720);
    public static Integer XXHDPI = Integer.valueOf(1080);
    public static Integer XXXHDPI = Integer.valueOf(1400);
    private List<String> a;
    /* access modifiers changed from: private */
    public RadioButton b = null;
    /* access modifiers changed from: private */
    public String c;
    private int d;
    /* access modifiers changed from: private */
    public Event e;

    public interface Event {
        void onClickItem();
    }

    class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        RadioButton m;
        TextView n;
        LinearLayout o;

        public ViewHolder(View view) {
            super(view);
            this.o = (LinearLayout) view.findViewById(R.id.lLCalificacionRadioButton);
            this.m = (RadioButton) view.findViewById(R.id.rbRadio);
            this.n = (TextView) view.findViewById(R.id.tvText);
        }
    }

    public GenericRadioAdapter(List<String> list, int i) {
        this.a = list;
        this.d = i;
    }

    public void setOnClickOnRadioItem(Event event) {
        this.e = event;
    }

    public boolean isRbSelected() {
        return this.b != null;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.generic_radio_group, viewGroup, false));
    }

    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        viewHolder.n.setText((CharSequence) this.a.get(i));
        viewHolder.m.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (GenericRadioAdapter.this.b != null) {
                    GenericRadioAdapter.this.b.setChecked(false);
                }
                GenericRadioAdapter.this.b = viewHolder.m;
                GenericRadioAdapter.this.b.setChecked(true);
                GenericRadioAdapter.this.c = String.valueOf(i);
                GenericRadioAdapter.this.e.onClickItem();
            }
        });
        if (this.d >= XXXHDPI.intValue()) {
            viewHolder.o.setLayoutParams(new LayoutParams((this.d - 115) / getItemCount(), -2));
        } else if (this.d >= XXHDPI.intValue()) {
            viewHolder.o.setLayoutParams(new LayoutParams((this.d - 110) / getItemCount(), -2));
        } else if (this.d >= XHDPI.intValue()) {
            viewHolder.o.setLayoutParams(new LayoutParams((this.d - 105) / getItemCount(), -2));
        } else if (this.d >= HDPI.intValue()) {
            viewHolder.o.setLayoutParams(new LayoutParams((this.d - 63) / getItemCount(), -2));
        }
    }

    public int getItemCount() {
        return this.a.size();
    }

    public String getSelectedOption() {
        return this.c;
    }
}
