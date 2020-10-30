package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.ui.Model.Hyperlink;
import ar.com.santander.rio.mbanking.utils.Utils;
import java.util.List;

public class HyperlinkAdapter extends BaseAdapter {
    protected Context mContext;
    protected List<Hyperlink> mData;
    protected OnHyperlinkClickListener mHyperlinkClickListener;
    protected LayoutInflater mLayoutInflater = ((LayoutInflater) this.mContext.getSystemService("layout_inflater"));

    public interface OnHyperlinkClickListener {
        void onHyperlinkClick(Integer num);
    }

    class ViewHolder {
        TextView a;
        TextView b;

        private ViewHolder() {
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public HyperlinkAdapter(Context context, List<Hyperlink> list, OnHyperlinkClickListener onHyperlinkClickListener) {
        this.mContext = context;
        this.mData = list;
        this.mHyperlinkClickListener = onHyperlinkClickListener;
    }

    public int getCount() {
        return this.mData.size();
    }

    public Hyperlink getItem(int i) {
        return (Hyperlink) this.mData.get(i);
    }

    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = this.mLayoutInflater.inflate(R.layout.layout_hyperlink, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.a = (TextView) view.findViewById(R.id.info_activity_with_hyperlink_hipervinculo);
            viewHolder.b = (TextView) view.findViewById(R.id.info_activity_with_hyperlink_texto);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Hyperlink hyperlink = (Hyperlink) this.mData.get(i);
        viewHolder.a.setText(Html.fromHtml(Utils.formatIsbanHTMLCode(hyperlink.getTitle())));
        viewHolder.b.setText(Html.fromHtml(Utils.formatIsbanHTMLCode(hyperlink.getText())));
        viewHolder.a.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                HyperlinkAdapter.this.mHyperlinkClickListener.onHyperlinkClick(Integer.valueOf(i));
            }
        });
        return view;
    }
}
