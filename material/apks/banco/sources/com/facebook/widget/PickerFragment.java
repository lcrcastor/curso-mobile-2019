package com.facebook.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.AlphaAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.facebook.FacebookException;
import com.facebook.Request;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.android.R;
import com.facebook.internal.SessionTracker;
import com.facebook.model.GraphObject;
import com.facebook.widget.GraphObjectAdapter.DataNeededListener;
import com.facebook.widget.GraphObjectAdapter.SectionAndItem;
import com.facebook.widget.GraphObjectAdapter.SectionAndItem.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class PickerFragment<T extends GraphObject> extends Fragment {
    public static final String DONE_BUTTON_TEXT_BUNDLE_KEY = "com.facebook.widget.PickerFragment.DoneButtonText";
    public static final String EXTRA_FIELDS_BUNDLE_KEY = "com.facebook.widget.PickerFragment.ExtraFields";
    public static final String SHOW_PICTURES_BUNDLE_KEY = "com.facebook.widget.PickerFragment.ShowPictures";
    public static final String SHOW_TITLE_BAR_BUNDLE_KEY = "com.facebook.widget.PickerFragment.ShowTitleBar";
    public static final String TITLE_TEXT_BUNDLE_KEY = "com.facebook.widget.PickerFragment.TitleText";
    HashSet<String> a = new HashSet<>();
    private boolean ad = true;
    private ListView ae;
    /* access modifiers changed from: private */
    public final Class<T> af;
    private LoadingStrategy ag;
    /* access modifiers changed from: private */
    public SelectionStrategy ah;
    private Set<String> ai;
    private ProgressBar aj;
    private SessionTracker ak;
    private String al;
    private String am;
    private TextView an;
    private Button ao;
    private Drawable ap;
    private Drawable aq;
    /* access modifiers changed from: private */

    /* renamed from: ar reason: collision with root package name */
    public boolean f272ar;
    private OnScrollListener as = new OnScrollListener() {
        public void onScrollStateChanged(AbsListView absListView, int i) {
        }

        public void onScroll(AbsListView absListView, int i, int i2, int i3) {
            PickerFragment.this.K();
        }
    };
    GraphObjectAdapter<T> b;
    private final int c;
    /* access modifiers changed from: private */
    public OnErrorListener d;
    private OnDataChangedListener e;
    private OnSelectionChangedListener f;
    /* access modifiers changed from: private */
    public OnDoneButtonClickedListener g;
    private GraphObjectFilter<T> h;
    private boolean i = true;

    public interface GraphObjectFilter<T> {
        boolean includeItem(T t);
    }

    abstract class LoadingStrategy {
        protected GraphObjectPagingLoader<T> b;
        protected GraphObjectAdapter<T> c;

        /* access modifiers changed from: protected */
        public boolean a() {
            return true;
        }

        LoadingStrategy() {
        }

        public void a(GraphObjectAdapter<T> graphObjectAdapter) {
            this.b = (GraphObjectPagingLoader) PickerFragment.this.getLoaderManager().initLoader(0, null, new LoaderCallbacks<SimpleGraphObjectCursor<T>>() {
                public Loader<SimpleGraphObjectCursor<T>> onCreateLoader(int i, Bundle bundle) {
                    return LoadingStrategy.this.e();
                }

                /* renamed from: a */
                public void onLoadFinished(Loader<SimpleGraphObjectCursor<T>> loader, SimpleGraphObjectCursor<T> simpleGraphObjectCursor) {
                    if (loader != LoadingStrategy.this.b) {
                        throw new FacebookException("Received callback for unknown loader.");
                    }
                    LoadingStrategy.this.a((GraphObjectPagingLoader) loader, simpleGraphObjectCursor);
                }

                public void onLoaderReset(Loader<SimpleGraphObjectCursor<T>> loader) {
                    if (loader != LoadingStrategy.this.b) {
                        throw new FacebookException("Received callback for unknown loader.");
                    }
                    LoadingStrategy.this.a((GraphObjectPagingLoader) loader);
                }
            });
            this.b.a((com.facebook.widget.GraphObjectPagingLoader.OnErrorListener) new com.facebook.widget.GraphObjectPagingLoader.OnErrorListener() {
                public void onError(FacebookException facebookException, GraphObjectPagingLoader<?> graphObjectPagingLoader) {
                    PickerFragment.this.H();
                    if (PickerFragment.this.d != null) {
                        PickerFragment.this.d.onError(PickerFragment.this, facebookException);
                    }
                }
            });
            this.c = graphObjectAdapter;
            this.c.a((GraphObjectCursor<T>) this.b.a());
            this.c.a((com.facebook.widget.GraphObjectAdapter.OnErrorListener) new com.facebook.widget.GraphObjectAdapter.OnErrorListener() {
                public void onError(GraphObjectAdapter<?> graphObjectAdapter, FacebookException facebookException) {
                    if (PickerFragment.this.d != null) {
                        PickerFragment.this.d.onError(PickerFragment.this, facebookException);
                    }
                }
            });
        }

        public void b() {
            this.c.a((DataNeededListener) null);
            this.c.a((com.facebook.widget.GraphObjectAdapter.OnErrorListener) null);
            this.b.a((com.facebook.widget.GraphObjectPagingLoader.OnErrorListener) null);
            this.b = null;
            this.c = null;
        }

        public void c() {
            if (this.b != null) {
                this.b.b();
            }
        }

        public void a(Request request) {
            if (this.b != null) {
                this.b.a(request, a());
                a(this.b, request);
            }
        }

        public boolean d() {
            return !this.c.isEmpty() || this.b.c();
        }

        /* access modifiers changed from: protected */
        public GraphObjectPagingLoader<T> e() {
            return new GraphObjectPagingLoader<>(PickerFragment.this.getActivity(), PickerFragment.this.af);
        }

        /* access modifiers changed from: protected */
        public void a(GraphObjectPagingLoader<T> graphObjectPagingLoader, Request request) {
            PickerFragment.this.F();
        }

        /* access modifiers changed from: protected */
        public void a(GraphObjectPagingLoader<T> graphObjectPagingLoader) {
            this.c.a(null);
        }

        /* access modifiers changed from: protected */
        public void a(GraphObjectPagingLoader<T> graphObjectPagingLoader, SimpleGraphObjectCursor<T> simpleGraphObjectCursor) {
            PickerFragment.this.a(simpleGraphObjectCursor);
        }
    }

    class MultiSelectionStrategy extends SelectionStrategy {
        private Set<String> c = new HashSet();

        /* access modifiers changed from: 0000 */
        public boolean d() {
            return true;
        }

        MultiSelectionStrategy() {
            super();
        }

        public Collection<String> a() {
            return this.c;
        }

        /* access modifiers changed from: 0000 */
        public boolean a(String str) {
            return str != null && this.c.contains(str);
        }

        /* access modifiers changed from: 0000 */
        public void b(String str) {
            if (str == null) {
                return;
            }
            if (this.c.contains(str)) {
                this.c.remove(str);
            } else {
                this.c.add(str);
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(Bundle bundle, String str) {
            if (!this.c.isEmpty()) {
                bundle.putString(str, TextUtils.join(",", this.c));
            }
        }

        /* access modifiers changed from: 0000 */
        public void b(Bundle bundle, String str) {
            if (bundle != null) {
                String string = bundle.getString(str);
                if (string != null) {
                    String[] split = TextUtils.split(string, ",");
                    this.c.clear();
                    Collections.addAll(this.c, split);
                }
            }
        }

        public void b() {
            this.c.clear();
        }

        /* access modifiers changed from: 0000 */
        public boolean c() {
            return this.c.isEmpty();
        }
    }

    public interface OnDataChangedListener {
        void onDataChanged(PickerFragment<?> pickerFragment);
    }

    public interface OnDoneButtonClickedListener {
        void onDoneButtonClicked(PickerFragment<?> pickerFragment);
    }

    public interface OnErrorListener {
        void onError(PickerFragment<?> pickerFragment, FacebookException facebookException);
    }

    public interface OnSelectionChangedListener {
        void onSelectionChanged(PickerFragment<?> pickerFragment);
    }

    abstract class PickerFragmentAdapter<U extends GraphObject> extends GraphObjectAdapter<T> {
        public PickerFragmentAdapter(Context context) {
            super(context);
        }

        /* access modifiers changed from: 0000 */
        public boolean b(String str) {
            return PickerFragment.this.ah.a(str);
        }

        /* access modifiers changed from: 0000 */
        public void a(CheckBox checkBox, boolean z) {
            checkBox.setChecked(z);
            checkBox.setVisibility((z || PickerFragment.this.ah.d()) ? 0 : 8);
        }
    }

    abstract class SelectionStrategy {
        /* access modifiers changed from: 0000 */
        public abstract Collection<String> a();

        /* access modifiers changed from: 0000 */
        public abstract void a(Bundle bundle, String str);

        /* access modifiers changed from: 0000 */
        public abstract boolean a(String str);

        /* access modifiers changed from: 0000 */
        public abstract void b();

        /* access modifiers changed from: 0000 */
        public abstract void b(Bundle bundle, String str);

        /* access modifiers changed from: 0000 */
        public abstract void b(String str);

        /* access modifiers changed from: 0000 */
        public abstract boolean c();

        /* access modifiers changed from: 0000 */
        public abstract boolean d();

        SelectionStrategy() {
        }
    }

    class SingleSelectionStrategy extends SelectionStrategy {
        private String c;

        /* access modifiers changed from: 0000 */
        public boolean d() {
            return false;
        }

        SingleSelectionStrategy() {
            super();
        }

        public Collection<String> a() {
            return Arrays.asList(new String[]{this.c});
        }

        /* access modifiers changed from: 0000 */
        public boolean a(String str) {
            return (this.c == null || str == null || !this.c.equals(str)) ? false : true;
        }

        /* access modifiers changed from: 0000 */
        public void b(String str) {
            if (this.c == null || !this.c.equals(str)) {
                this.c = str;
            } else {
                this.c = null;
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(Bundle bundle, String str) {
            if (!TextUtils.isEmpty(this.c)) {
                bundle.putString(str, this.c);
            }
        }

        /* access modifiers changed from: 0000 */
        public void b(Bundle bundle, String str) {
            if (bundle != null) {
                this.c = bundle.getString(str);
            }
        }

        public void b() {
            this.c = null;
        }

        /* access modifiers changed from: 0000 */
        public boolean c() {
            return this.c == null;
        }
    }

    /* access modifiers changed from: 0000 */
    public abstract SelectionStrategy A();

    /* access modifiers changed from: 0000 */
    public String B() {
        return null;
    }

    /* access modifiers changed from: 0000 */
    public void D() {
    }

    /* access modifiers changed from: 0000 */
    public abstract Request a(Session session);

    /* access modifiers changed from: 0000 */
    public void a(ViewGroup viewGroup) {
    }

    /* access modifiers changed from: 0000 */
    public void a(boolean z) {
    }

    /* access modifiers changed from: 0000 */
    public abstract PickerFragmentAdapter<T> y();

    /* access modifiers changed from: 0000 */
    public abstract LoadingStrategy z();

    PickerFragment(Class<T> cls, int i2, Bundle bundle) {
        this.af = cls;
        this.c = i2;
        h(bundle);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.b = y();
        this.b.a((Filter<T>) new Filter<T>() {
            public boolean a(T t) {
                return PickerFragment.this.a(t);
            }
        });
    }

    public void onInflate(Activity activity, AttributeSet attributeSet, Bundle bundle) {
        super.onInflate(activity, attributeSet, bundle);
        TypedArray obtainStyledAttributes = activity.obtainStyledAttributes(attributeSet, R.styleable.com_facebook_picker_fragment);
        setShowPictures(obtainStyledAttributes.getBoolean(0, this.i));
        String string = obtainStyledAttributes.getString(1);
        if (string != null) {
            setExtraFields(Arrays.asList(string.split(",")));
        }
        this.ad = obtainStyledAttributes.getBoolean(2, this.ad);
        this.al = obtainStyledAttributes.getString(3);
        this.am = obtainStyledAttributes.getString(4);
        this.ap = obtainStyledAttributes.getDrawable(5);
        this.aq = obtainStyledAttributes.getDrawable(6);
        obtainStyledAttributes.recycle();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        ViewGroup viewGroup2 = (ViewGroup) layoutInflater.inflate(this.c, viewGroup, false);
        this.ae = (ListView) viewGroup2.findViewById(R.id.com_facebook_picker_list_view);
        this.ae.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                PickerFragment.this.a((ListView) adapterView, view, i);
            }
        });
        this.ae.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View view) {
                return false;
            }
        });
        this.ae.setOnScrollListener(this.as);
        this.aj = (ProgressBar) viewGroup2.findViewById(R.id.com_facebook_picker_activity_circle);
        a(viewGroup2);
        this.ae.setAdapter(this.b);
        return viewGroup2;
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.ak = new SessionTracker(getActivity(), new StatusCallback() {
            public void call(Session session, SessionState sessionState, Exception exc) {
                if (!session.isOpened()) {
                    PickerFragment.this.J();
                }
            }
        });
        setSettingsFromBundle(bundle);
        this.ag = z();
        this.ag.a(this.b);
        this.ah = A();
        this.ah.b(bundle, "com.facebook.android.PickerFragment.Selection");
        if (this.ad) {
            b((ViewGroup) getView());
        }
        if (this.aj != null && bundle != null) {
            if (bundle.getBoolean("com.facebook.android.PickerFragment.ActivityCircleShown", false)) {
                F();
            } else {
                H();
            }
        }
    }

    public void onDetach() {
        super.onDetach();
        this.ae.setOnScrollListener(null);
        this.ae.setAdapter(null);
        this.ag.b();
        this.ak.stopTracking();
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        g(bundle);
        this.ah.a(bundle, "com.facebook.android.PickerFragment.Selection");
        if (this.aj != null) {
            bundle.putBoolean("com.facebook.android.PickerFragment.ActivityCircleShown", this.aj.getVisibility() == 0);
        }
    }

    public void onStop() {
        if (!this.f272ar) {
            a(false);
        }
        super.onStop();
    }

    public void setArguments(Bundle bundle) {
        super.setArguments(bundle);
        setSettingsFromBundle(bundle);
    }

    public OnDataChangedListener getOnDataChangedListener() {
        return this.e;
    }

    public void setOnDataChangedListener(OnDataChangedListener onDataChangedListener) {
        this.e = onDataChangedListener;
    }

    public OnSelectionChangedListener getOnSelectionChangedListener() {
        return this.f;
    }

    public void setOnSelectionChangedListener(OnSelectionChangedListener onSelectionChangedListener) {
        this.f = onSelectionChangedListener;
    }

    public OnDoneButtonClickedListener getOnDoneButtonClickedListener() {
        return this.g;
    }

    public void setOnDoneButtonClickedListener(OnDoneButtonClickedListener onDoneButtonClickedListener) {
        this.g = onDoneButtonClickedListener;
    }

    public OnErrorListener getOnErrorListener() {
        return this.d;
    }

    public void setOnErrorListener(OnErrorListener onErrorListener) {
        this.d = onErrorListener;
    }

    public GraphObjectFilter<T> getFilter() {
        return this.h;
    }

    public void setFilter(GraphObjectFilter<T> graphObjectFilter) {
        this.h = graphObjectFilter;
    }

    public Session getSession() {
        return this.ak.getSession();
    }

    public void setSession(Session session) {
        this.ak.setSession(session);
    }

    public boolean getShowPictures() {
        return this.i;
    }

    public void setShowPictures(boolean z) {
        this.i = z;
    }

    public Set<String> getExtraFields() {
        return new HashSet(this.a);
    }

    public void setExtraFields(Collection<String> collection) {
        this.a = new HashSet<>();
        if (collection != null) {
            this.a.addAll(collection);
        }
    }

    public void setShowTitleBar(boolean z) {
        this.ad = z;
    }

    public boolean getShowTitleBar() {
        return this.ad;
    }

    public void setTitleText(String str) {
        this.al = str;
    }

    public String getTitleText() {
        if (this.al == null) {
            this.al = B();
        }
        return this.al;
    }

    public void setDoneButtonText(String str) {
        this.am = str;
    }

    public String getDoneButtonText() {
        if (this.am == null) {
            this.am = E();
        }
        return this.am;
    }

    public void loadData(boolean z) {
        loadData(z, null);
    }

    public void loadData(boolean z, Set<String> set) {
        if (z || !this.ag.d()) {
            this.ai = set;
            I();
        }
    }

    public void setSettingsFromBundle(Bundle bundle) {
        h(bundle);
    }

    /* access modifiers changed from: 0000 */
    public boolean a(T t) {
        if (this.h != null) {
            return this.h.includeItem(t);
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public List<T> C() {
        return this.b.a(this.ah.a());
    }

    /* access modifiers changed from: 0000 */
    public void a(List<String> list) {
        for (String str : list) {
            if (!this.ah.a(str)) {
                this.ah.b(str);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void g(Bundle bundle) {
        bundle.putBoolean(SHOW_PICTURES_BUNDLE_KEY, this.i);
        if (!this.a.isEmpty()) {
            bundle.putString(EXTRA_FIELDS_BUNDLE_KEY, TextUtils.join(",", this.a));
        }
        bundle.putBoolean(SHOW_TITLE_BAR_BUNDLE_KEY, this.ad);
        bundle.putString(TITLE_TEXT_BUNDLE_KEY, this.al);
        bundle.putString(DONE_BUTTON_TEXT_BUNDLE_KEY, this.am);
    }

    /* access modifiers changed from: 0000 */
    public String E() {
        return getString(R.string.com_facebook_picker_done_button_text);
    }

    /* access modifiers changed from: 0000 */
    public void F() {
        if (this.aj != null) {
            G();
            this.aj.setVisibility(0);
        }
    }

    /* access modifiers changed from: 0000 */
    public void G() {
        a((View) this.aj, !this.b.isEmpty() ? 0.25f : 1.0f);
    }

    /* access modifiers changed from: 0000 */
    public void H() {
        if (this.aj != null) {
            this.aj.clearAnimation();
            this.aj.setVisibility(4);
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(SelectionStrategy selectionStrategy) {
        if (selectionStrategy != this.ah) {
            this.ah = selectionStrategy;
            if (this.b != null) {
                this.b.notifyDataSetChanged();
            }
        }
    }

    private static void a(View view, float f2) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(f2, f2);
        alphaAnimation.setDuration(0);
        alphaAnimation.setFillAfter(true);
        view.startAnimation(alphaAnimation);
    }

    private void h(Bundle bundle) {
        if (bundle != null) {
            this.i = bundle.getBoolean(SHOW_PICTURES_BUNDLE_KEY, this.i);
            String string = bundle.getString(EXTRA_FIELDS_BUNDLE_KEY);
            if (string != null) {
                setExtraFields(Arrays.asList(string.split(",")));
            }
            this.ad = bundle.getBoolean(SHOW_TITLE_BAR_BUNDLE_KEY, this.ad);
            String string2 = bundle.getString(TITLE_TEXT_BUNDLE_KEY);
            if (string2 != null) {
                this.al = string2;
                if (this.an != null) {
                    this.an.setText(this.al);
                }
            }
            String string3 = bundle.getString(DONE_BUTTON_TEXT_BUNDLE_KEY);
            if (string3 != null) {
                this.am = string3;
                if (this.ao != null) {
                    this.ao.setText(this.am);
                }
            }
        }
    }

    private void b(ViewGroup viewGroup) {
        ViewStub viewStub = (ViewStub) viewGroup.findViewById(R.id.com_facebook_picker_title_bar_stub);
        if (viewStub != null) {
            View inflate = viewStub.inflate();
            LayoutParams layoutParams = new LayoutParams(-1, -1);
            layoutParams.addRule(3, R.id.com_facebook_picker_title_bar);
            this.ae.setLayoutParams(layoutParams);
            if (this.ap != null) {
                inflate.setBackgroundDrawable(this.ap);
            }
            this.ao = (Button) viewGroup.findViewById(R.id.com_facebook_picker_done_button);
            if (this.ao != null) {
                this.ao.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        PickerFragment.this.a(true);
                        PickerFragment.this.f272ar = true;
                        if (PickerFragment.this.g != null) {
                            PickerFragment.this.g.onDoneButtonClicked(PickerFragment.this);
                        }
                    }
                });
                if (getDoneButtonText() != null) {
                    this.ao.setText(getDoneButtonText());
                }
                if (this.aq != null) {
                    this.ao.setBackgroundDrawable(this.aq);
                }
            }
            this.an = (TextView) viewGroup.findViewById(R.id.com_facebook_picker_title);
            if (this.an != null && getTitleText() != null) {
                this.an.setText(getTitleText());
            }
        }
    }

    /* access modifiers changed from: private */
    public void a(ListView listView, View view, int i2) {
        this.ah.b(this.b.g((GraphObject) listView.getItemAtPosition(i2)));
        this.b.notifyDataSetChanged();
        if (this.f != null) {
            this.f.onSelectionChanged(this);
        }
    }

    private void I() {
        J();
        Request a2 = a(getSession());
        if (a2 != null) {
            D();
            this.ag.a(a2);
        }
    }

    /* access modifiers changed from: private */
    public void J() {
        if (this.b != null) {
            boolean z = !this.ah.c();
            boolean z2 = !this.b.isEmpty();
            this.ag.c();
            this.ah.b();
            this.b.notifyDataSetChanged();
            if (z2 && this.e != null) {
                this.e.onDataChanged(this);
            }
            if (z && this.f != null) {
                this.f.onSelectionChanged(this);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(SimpleGraphObjectCursor<T> simpleGraphObjectCursor) {
        if (this.b != null) {
            View childAt = this.ae.getChildAt(1);
            int firstVisiblePosition = this.ae.getFirstVisiblePosition();
            if (firstVisiblePosition > 0) {
                firstVisiblePosition++;
            }
            SectionAndItem a2 = this.b.a(firstVisiblePosition);
            int top = (childAt == null || a2.getType() == Type.ACTIVITY_CIRCLE) ? 0 : childAt.getTop();
            boolean a3 = this.b.a((GraphObjectCursor<T>) simpleGraphObjectCursor);
            if (!(childAt == null || a2 == null)) {
                int a4 = this.b.a(a2.sectionKey, a2.graphObject);
                if (a4 != -1) {
                    this.ae.setSelectionFromTop(a4, top);
                }
            }
            if (a3 && this.e != null) {
                this.e.onDataChanged(this);
            }
            if (this.ai != null && !this.ai.isEmpty() && simpleGraphObjectCursor != null) {
                simpleGraphObjectCursor.c();
                boolean z = false;
                for (int i2 = 0; i2 < simpleGraphObjectCursor.b(); i2++) {
                    simpleGraphObjectCursor.a(i2);
                    GraphObject e2 = simpleGraphObjectCursor.e();
                    if (e2.asMap().containsKey("id")) {
                        Object property = e2.getProperty("id");
                        if (!(property instanceof String)) {
                            continue;
                        } else {
                            String str = (String) property;
                            if (this.ai.contains(str)) {
                                this.ah.b(str);
                                this.ai.remove(str);
                                z = true;
                            }
                            if (this.ai.isEmpty()) {
                                break;
                            }
                        }
                    }
                }
                if (this.f != null && z) {
                    this.f.onSelectionChanged(this);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void K() {
        int lastVisiblePosition = this.ae.getLastVisiblePosition();
        if (lastVisiblePosition >= 0) {
            this.b.a(this.ae.getFirstVisiblePosition(), lastVisiblePosition, 5);
        }
    }
}
