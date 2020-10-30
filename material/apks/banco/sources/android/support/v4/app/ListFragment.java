package android.support.v4.app;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ListFragment extends Fragment {
    ListAdapter a;
    private final Runnable ad = new Runnable() {
        public void run() {
            ListFragment.this.b.focusableViewAvailable(ListFragment.this.b);
        }
    };
    private final OnItemClickListener ae = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            ListFragment.this.onListItemClick((ListView) adapterView, view, i, j);
        }
    };
    ListView b;
    View c;
    TextView d;
    View e;
    View f;
    CharSequence g;
    boolean h;
    private final Handler i = new Handler();

    public void onListItemClick(ListView listView, View view, int i2, long j) {
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Context context = getContext();
        FrameLayout frameLayout = new FrameLayout(context);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setId(16711682);
        linearLayout.setOrientation(1);
        linearLayout.setVisibility(8);
        linearLayout.setGravity(17);
        linearLayout.addView(new ProgressBar(context, null, 16842874), new LayoutParams(-2, -2));
        frameLayout.addView(linearLayout, new LayoutParams(-1, -1));
        FrameLayout frameLayout2 = new FrameLayout(context);
        frameLayout2.setId(16711683);
        TextView textView = new TextView(context);
        textView.setId(16711681);
        textView.setGravity(17);
        frameLayout2.addView(textView, new LayoutParams(-1, -1));
        ListView listView = new ListView(context);
        listView.setId(16908298);
        listView.setDrawSelectorOnTop(false);
        frameLayout2.addView(listView, new LayoutParams(-1, -1));
        frameLayout.addView(frameLayout2, new LayoutParams(-1, -1));
        frameLayout.setLayoutParams(new LayoutParams(-1, -1));
        return frameLayout;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        y();
    }

    public void onDestroyView() {
        this.i.removeCallbacks(this.ad);
        this.b = null;
        this.h = false;
        this.f = null;
        this.e = null;
        this.c = null;
        this.d = null;
        super.onDestroyView();
    }

    public void setListAdapter(ListAdapter listAdapter) {
        boolean z = false;
        boolean z2 = this.a != null;
        this.a = listAdapter;
        if (this.b != null) {
            this.b.setAdapter(listAdapter);
            if (!this.h && !z2) {
                if (getView().getWindowToken() != null) {
                    z = true;
                }
                a(true, z);
            }
        }
    }

    public void setSelection(int i2) {
        y();
        this.b.setSelection(i2);
    }

    public int getSelectedItemPosition() {
        y();
        return this.b.getSelectedItemPosition();
    }

    public long getSelectedItemId() {
        y();
        return this.b.getSelectedItemId();
    }

    public ListView getListView() {
        y();
        return this.b;
    }

    public void setEmptyText(CharSequence charSequence) {
        y();
        if (this.d == null) {
            throw new IllegalStateException("Can't be used with a custom content view");
        }
        this.d.setText(charSequence);
        if (this.g == null) {
            this.b.setEmptyView(this.d);
        }
        this.g = charSequence;
    }

    public void setListShown(boolean z) {
        a(z, true);
    }

    public void setListShownNoAnimation(boolean z) {
        a(z, false);
    }

    private void a(boolean z, boolean z2) {
        y();
        if (this.e == null) {
            throw new IllegalStateException("Can't be used with a custom content view");
        } else if (this.h != z) {
            this.h = z;
            if (z) {
                if (z2) {
                    this.e.startAnimation(AnimationUtils.loadAnimation(getContext(), 17432577));
                    this.f.startAnimation(AnimationUtils.loadAnimation(getContext(), 17432576));
                } else {
                    this.e.clearAnimation();
                    this.f.clearAnimation();
                }
                this.e.setVisibility(8);
                this.f.setVisibility(0);
            } else {
                if (z2) {
                    this.e.startAnimation(AnimationUtils.loadAnimation(getContext(), 17432576));
                    this.f.startAnimation(AnimationUtils.loadAnimation(getContext(), 17432577));
                } else {
                    this.e.clearAnimation();
                    this.f.clearAnimation();
                }
                this.e.setVisibility(0);
                this.f.setVisibility(8);
            }
        }
    }

    public ListAdapter getListAdapter() {
        return this.a;
    }

    private void y() {
        if (this.b == null) {
            View view = getView();
            if (view == null) {
                throw new IllegalStateException("Content view not yet created");
            }
            if (view instanceof ListView) {
                this.b = (ListView) view;
            } else {
                this.d = (TextView) view.findViewById(16711681);
                if (this.d == null) {
                    this.c = view.findViewById(16908292);
                } else {
                    this.d.setVisibility(8);
                }
                this.e = view.findViewById(16711682);
                this.f = view.findViewById(16711683);
                View findViewById = view.findViewById(16908298);
                if (findViewById instanceof ListView) {
                    this.b = (ListView) findViewById;
                    if (this.c != null) {
                        this.b.setEmptyView(this.c);
                    } else if (this.g != null) {
                        this.d.setText(this.g);
                        this.b.setEmptyView(this.d);
                    }
                } else if (findViewById == null) {
                    throw new RuntimeException("Your content must have a ListView whose id attribute is 'android.R.id.list'");
                } else {
                    throw new RuntimeException("Content has view with id attribute 'android.R.id.list' that is not a ListView class");
                }
            }
            this.h = true;
            this.b.setOnItemClickListener(this.ae);
            if (this.a != null) {
                ListAdapter listAdapter = this.a;
                this.a = null;
                setListAdapter(listAdapter);
            } else if (this.e != null) {
                a(false, false);
            }
            this.i.post(this.ad);
        }
    }
}
