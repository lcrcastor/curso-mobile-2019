package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filter.FilterResults;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.services.model.general.PlaceMap;
import java.util.ArrayList;

public abstract class PlacesAutoCompleteAdapter extends ArrayAdapter<PlaceMap> implements Filterable {
    /* access modifiers changed from: private */
    public ArrayList<PlaceMap> a;

    public abstract ArrayList<PlaceMap> autocomplete(String str);

    public PlacesAutoCompleteAdapter(Context context, int i) {
        super(context, i);
    }

    public int getCount() {
        if (this.a == null) {
            return 0;
        }
        return this.a.size();
    }

    public PlaceMap getItem(int i) {
        return (PlaceMap) this.a.get(i);
    }

    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        return getCustomView(i, view, viewGroup);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        return getCustomView(i, view, viewGroup);
    }

    public View getCustomView(int i, View view, ViewGroup viewGroup) {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.list_item_autocomplete_places, null);
        if (this.a != null && i < this.a.size()) {
            PlaceMap placeMap = (PlaceMap) this.a.get(i);
            TextView textView = (TextView) inflate.findViewById(R.id.tvNamePlace);
            textView.setText(placeMap.getName());
            LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.wrapperItem);
            if (placeMap.isHeader) {
                linearLayout.setBackgroundColor(getContext().getResources().getColor(R.color.grey_light));
                textView.setTextColor(getContext().getResources().getColor(R.color.black));
                textView.setTypeface(null, 0);
            } else {
                linearLayout.setBackgroundColor(getContext().getResources().getColor(R.color.white));
                textView.setTextColor(getContext().getResources().getColor(R.color.grey_medium_light));
                textView.setTypeface(null, 1);
            }
        }
        return inflate;
    }

    public Filter getFilter() {
        return new Filter() {
            /* access modifiers changed from: protected */
            public FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                if (charSequence != null) {
                    try {
                        if (charSequence.length() >= PlacesAutoCompleteAdapter.this.getContext().getResources().getInteger(R.integer.autocomplete_characters)) {
                            filterResults.values = PlacesAutoCompleteAdapter.this.autocomplete(charSequence.toString());
                        }
                    } catch (Exception e) {
                        Log.e("@dev", "Error al obtener los valores del autocomplete en adapter", e);
                    }
                }
                if (filterResults.values != null) {
                    filterResults.count = ((ArrayList) filterResults.values).size();
                } else {
                    filterResults.count = 0;
                }
                return filterResults;
            }

            /* access modifiers changed from: protected */
            public void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (filterResults == null || filterResults.count <= 0) {
                    PlacesAutoCompleteAdapter.this.notifyDataSetInvalidated();
                    return;
                }
                PlacesAutoCompleteAdapter.this.a = (ArrayList) filterResults.values;
                PlacesAutoCompleteAdapter.this.notifyDataSetChanged();
            }
        };
    }

    public void resetPlaces(ArrayList<PlaceMap> arrayList) {
        this.a = new ArrayList<>();
        this.a.addAll(arrayList);
        notifyDataSetChanged();
    }
}
