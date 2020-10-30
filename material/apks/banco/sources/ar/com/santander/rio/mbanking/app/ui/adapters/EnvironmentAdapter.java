package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.services.soap.environments.ManagerTypeEnvironment.Environment;
import java.util.List;

public class EnvironmentAdapter extends ArrayAdapter<Environment> {
    private Context a;
    private List<Environment> b;

    public long getItemId(int i) {
        return (long) i;
    }

    public EnvironmentAdapter(Context context, int i, List<Environment> list) {
        super(context, i, list);
        this.a = context;
        this.b = list;
    }

    public int getCount() {
        return this.b.size();
    }

    public Environment getItem(int i) {
        return (Environment) this.b.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView textView = new TextView(this.a);
        textView.setTextSize(20.0f);
        textView.setPadding(0, 5, 0, 5);
        textView.setText(getItem(i).name());
        textView.setTextColor(this.a.getResources().getColor(R.color.white));
        return textView;
    }

    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        return getView(i, view, viewGroup);
    }
}
