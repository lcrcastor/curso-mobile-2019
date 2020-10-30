package android.support.v4.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.BaseSavedState;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;

public class FragmentTabHost extends TabHost implements OnTabChangeListener {
    private final ArrayList<TabInfo> a = new ArrayList<>();
    private FrameLayout b;
    private Context c;
    private FragmentManager d;
    private int e;
    private OnTabChangeListener f;
    private TabInfo g;
    private boolean h;

    static class DummyTabFactory implements TabContentFactory {
        private final Context a;

        public DummyTabFactory(Context context) {
            this.a = context;
        }

        public View createTabContent(String str) {
            View view = new View(this.a);
            view.setMinimumWidth(0);
            view.setMinimumHeight(0);
            return view;
        }
    }

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            /* renamed from: a */
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            /* renamed from: a */
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        String a;

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        SavedState(Parcel parcel) {
            super(parcel);
            this.a = parcel.readString();
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeString(this.a);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("FragmentTabHost.SavedState{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(" curTab=");
            sb.append(this.a);
            sb.append("}");
            return sb.toString();
        }
    }

    static final class TabInfo {
        @NonNull
        final String a;
        @NonNull
        final Class<?> b;
        @Nullable
        final Bundle c;
        Fragment d;

        TabInfo(@NonNull String str, @NonNull Class<?> cls, @Nullable Bundle bundle) {
            this.a = str;
            this.b = cls;
            this.c = bundle;
        }
    }

    public FragmentTabHost(Context context) {
        super(context, null);
        a(context, (AttributeSet) null);
    }

    public FragmentTabHost(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context, attributeSet);
    }

    private void a(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, new int[]{16842995}, 0, 0);
        this.e = obtainStyledAttributes.getResourceId(0, 0);
        obtainStyledAttributes.recycle();
        super.setOnTabChangedListener(this);
    }

    private void a(Context context) {
        if (findViewById(16908307) == null) {
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(1);
            addView(linearLayout, new LayoutParams(-1, -1));
            TabWidget tabWidget = new TabWidget(context);
            tabWidget.setId(16908307);
            tabWidget.setOrientation(0);
            linearLayout.addView(tabWidget, new LinearLayout.LayoutParams(-1, -2, BitmapDescriptorFactory.HUE_RED));
            FrameLayout frameLayout = new FrameLayout(context);
            frameLayout.setId(16908305);
            linearLayout.addView(frameLayout, new LinearLayout.LayoutParams(0, 0, BitmapDescriptorFactory.HUE_RED));
            FrameLayout frameLayout2 = new FrameLayout(context);
            this.b = frameLayout2;
            this.b.setId(this.e);
            linearLayout.addView(frameLayout2, new LinearLayout.LayoutParams(-1, 0, 1.0f));
        }
    }

    @Deprecated
    public void setup() {
        throw new IllegalStateException("Must call setup() that takes a Context and FragmentManager");
    }

    public void setup(Context context, FragmentManager fragmentManager) {
        a(context);
        super.setup();
        this.c = context;
        this.d = fragmentManager;
        a();
    }

    public void setup(Context context, FragmentManager fragmentManager, int i) {
        a(context);
        super.setup();
        this.c = context;
        this.d = fragmentManager;
        this.e = i;
        a();
        this.b.setId(i);
        if (getId() == -1) {
            setId(16908306);
        }
    }

    private void a() {
        if (this.b == null) {
            this.b = (FrameLayout) findViewById(this.e);
            if (this.b == null) {
                StringBuilder sb = new StringBuilder();
                sb.append("No tab content FrameLayout found for id ");
                sb.append(this.e);
                throw new IllegalStateException(sb.toString());
            }
        }
    }

    public void setOnTabChangedListener(OnTabChangeListener onTabChangeListener) {
        this.f = onTabChangeListener;
    }

    public void addTab(@NonNull TabSpec tabSpec, @NonNull Class<?> cls, @Nullable Bundle bundle) {
        tabSpec.setContent(new DummyTabFactory(this.c));
        String tag = tabSpec.getTag();
        TabInfo tabInfo = new TabInfo(tag, cls, bundle);
        if (this.h) {
            tabInfo.d = this.d.findFragmentByTag(tag);
            if (tabInfo.d != null && !tabInfo.d.isDetached()) {
                FragmentTransaction beginTransaction = this.d.beginTransaction();
                beginTransaction.detach(tabInfo.d);
                beginTransaction.commit();
            }
        }
        this.a.add(tabInfo);
        addTab(tabSpec);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        String currentTabTag = getCurrentTabTag();
        int size = this.a.size();
        FragmentTransaction fragmentTransaction = null;
        for (int i = 0; i < size; i++) {
            TabInfo tabInfo = (TabInfo) this.a.get(i);
            tabInfo.d = this.d.findFragmentByTag(tabInfo.a);
            if (tabInfo.d != null && !tabInfo.d.isDetached()) {
                if (tabInfo.a.equals(currentTabTag)) {
                    this.g = tabInfo;
                } else {
                    if (fragmentTransaction == null) {
                        fragmentTransaction = this.d.beginTransaction();
                    }
                    fragmentTransaction.detach(tabInfo.d);
                }
            }
        }
        this.h = true;
        FragmentTransaction a2 = a(currentTabTag, fragmentTransaction);
        if (a2 != null) {
            a2.commit();
            this.d.executePendingTransactions();
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.h = false;
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.a = getCurrentTabTag();
        return savedState;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setCurrentTabByTag(savedState.a);
    }

    public void onTabChanged(String str) {
        if (this.h) {
            FragmentTransaction a2 = a(str, (FragmentTransaction) null);
            if (a2 != null) {
                a2.commit();
            }
        }
        if (this.f != null) {
            this.f.onTabChanged(str);
        }
    }

    @Nullable
    private FragmentTransaction a(@Nullable String str, @Nullable FragmentTransaction fragmentTransaction) {
        TabInfo a2 = a(str);
        if (this.g != a2) {
            if (fragmentTransaction == null) {
                fragmentTransaction = this.d.beginTransaction();
            }
            if (!(this.g == null || this.g.d == null)) {
                fragmentTransaction.detach(this.g.d);
            }
            if (a2 != null) {
                if (a2.d == null) {
                    a2.d = Fragment.instantiate(this.c, a2.b.getName(), a2.c);
                    fragmentTransaction.add(this.e, a2.d, a2.a);
                } else {
                    fragmentTransaction.attach(a2.d);
                }
            }
            this.g = a2;
        }
        return fragmentTransaction;
    }

    @Nullable
    private TabInfo a(String str) {
        int size = this.a.size();
        for (int i = 0; i < size; i++) {
            TabInfo tabInfo = (TabInfo) this.a.get(i);
            if (tabInfo.a.equals(str)) {
                return tabInfo;
            }
        }
        return null;
    }
}
