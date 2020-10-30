package android.support.v7.widget;

import android.app.PendingIntent;
import android.app.SearchableInfo;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.appcompat.R;
import android.support.v7.view.CollapsibleActionView;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.KeyEvent.DispatcherState;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewConfiguration;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.google.android.gms.actions.SearchIntents;
import java.lang.reflect.Method;
import java.util.WeakHashMap;
import org.bouncycastle.crypto.tls.CipherSuite;

public class SearchView extends LinearLayoutCompat implements CollapsibleActionView {
    static final AutoCompleteTextViewReflector i = new AutoCompleteTextViewReflector();
    private OnQueryTextListener A;
    private OnCloseListener B;
    private OnSuggestionListener C;
    private OnClickListener D;
    private boolean E;
    private boolean F;
    private boolean G;
    private CharSequence H;
    private boolean I;
    private boolean J;
    private int K;
    private boolean L;
    private CharSequence M;
    private CharSequence N;
    private boolean O;
    private int P;
    private Bundle Q;
    private final Runnable R;
    private Runnable S;
    private final WeakHashMap<String, ConstantState> T;
    private final OnClickListener U;
    private final OnEditorActionListener V;
    private final OnItemClickListener W;
    final SearchAutoComplete a;
    private final OnItemSelectedListener aa;
    private TextWatcher ab;
    final ImageView b;
    final ImageView c;
    final ImageView d;
    final ImageView e;
    OnFocusChangeListener f;
    CursorAdapter g;
    SearchableInfo h;
    OnKeyListener j;
    private final View k;
    private final View l;
    private final View m;
    private final View n;
    private UpdatableTouchDelegate o;
    private Rect p;
    private Rect q;
    private int[] r;
    private int[] s;
    private final ImageView t;
    private final Drawable u;
    private final int v;
    private final int w;
    private final Intent x;
    private final Intent y;
    private final CharSequence z;

    static class AutoCompleteTextViewReflector {
        private Method a;
        private Method b;
        private Method c;

        AutoCompleteTextViewReflector() {
            try {
                this.a = AutoCompleteTextView.class.getDeclaredMethod("doBeforeTextChanged", new Class[0]);
                this.a.setAccessible(true);
            } catch (NoSuchMethodException unused) {
            }
            try {
                this.b = AutoCompleteTextView.class.getDeclaredMethod("doAfterTextChanged", new Class[0]);
                this.b.setAccessible(true);
            } catch (NoSuchMethodException unused2) {
            }
            try {
                this.c = AutoCompleteTextView.class.getMethod("ensureImeVisible", new Class[]{Boolean.TYPE});
                this.c.setAccessible(true);
            } catch (NoSuchMethodException unused3) {
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(AutoCompleteTextView autoCompleteTextView) {
            if (this.a != null) {
                try {
                    this.a.invoke(autoCompleteTextView, new Object[0]);
                } catch (Exception unused) {
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void b(AutoCompleteTextView autoCompleteTextView) {
            if (this.b != null) {
                try {
                    this.b.invoke(autoCompleteTextView, new Object[0]);
                } catch (Exception unused) {
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(AutoCompleteTextView autoCompleteTextView, boolean z) {
            if (this.c != null) {
                try {
                    this.c.invoke(autoCompleteTextView, new Object[]{Boolean.valueOf(z)});
                } catch (Exception unused) {
                }
            }
        }
    }

    public interface OnCloseListener {
        boolean onClose();
    }

    public interface OnQueryTextListener {
        boolean onQueryTextChange(String str);

        boolean onQueryTextSubmit(String str);
    }

    public interface OnSuggestionListener {
        boolean onSuggestionClick(int i);

        boolean onSuggestionSelect(int i);
    }

    static class SavedState extends AbsSavedState {
        public static final Creator<SavedState> CREATOR = new ClassLoaderCreator<SavedState>() {
            /* renamed from: a */
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            /* renamed from: a */
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }

            /* renamed from: a */
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        boolean a;

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.a = ((Boolean) parcel.readValue(null)).booleanValue();
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeValue(Boolean.valueOf(this.a));
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("SearchView.SavedState{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(" isIconified=");
            sb.append(this.a);
            sb.append("}");
            return sb.toString();
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public static class SearchAutoComplete extends AppCompatAutoCompleteTextView {
        final Runnable a;
        private int b;
        private SearchView c;
        private boolean d;

        public void performCompletion() {
        }

        /* access modifiers changed from: protected */
        public void replaceText(CharSequence charSequence) {
        }

        public SearchAutoComplete(Context context) {
            this(context, null);
        }

        public SearchAutoComplete(Context context, AttributeSet attributeSet) {
            this(context, attributeSet, R.attr.autoCompleteTextViewStyle);
        }

        public SearchAutoComplete(Context context, AttributeSet attributeSet, int i) {
            super(context, attributeSet, i);
            this.a = new Runnable() {
                public void run() {
                    SearchAutoComplete.this.b();
                }
            };
            this.b = getThreshold();
        }

        /* access modifiers changed from: protected */
        public void onFinishInflate() {
            super.onFinishInflate();
            setMinWidth((int) TypedValue.applyDimension(1, (float) getSearchViewTextMinWidthDp(), getResources().getDisplayMetrics()));
        }

        /* access modifiers changed from: 0000 */
        public void setSearchView(SearchView searchView) {
            this.c = searchView;
        }

        public void setThreshold(int i) {
            super.setThreshold(i);
            this.b = i;
        }

        /* access modifiers changed from: private */
        public boolean a() {
            return TextUtils.getTrimmedLength(getText()) == 0;
        }

        public void onWindowFocusChanged(boolean z) {
            super.onWindowFocusChanged(z);
            if (z && this.c.hasFocus() && getVisibility() == 0) {
                this.d = true;
                if (SearchView.a(getContext())) {
                    SearchView.i.a(this, true);
                }
            }
        }

        /* access modifiers changed from: protected */
        public void onFocusChanged(boolean z, int i, Rect rect) {
            super.onFocusChanged(z, i, rect);
            this.c.f();
        }

        public boolean enoughToFilter() {
            return this.b <= 0 || super.enoughToFilter();
        }

        public boolean onKeyPreIme(int i, KeyEvent keyEvent) {
            if (i == 4) {
                if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                    DispatcherState keyDispatcherState = getKeyDispatcherState();
                    if (keyDispatcherState != null) {
                        keyDispatcherState.startTracking(keyEvent, this);
                    }
                    return true;
                } else if (keyEvent.getAction() == 1) {
                    DispatcherState keyDispatcherState2 = getKeyDispatcherState();
                    if (keyDispatcherState2 != null) {
                        keyDispatcherState2.handleUpEvent(keyEvent);
                    }
                    if (keyEvent.isTracking() && !keyEvent.isCanceled()) {
                        this.c.clearFocus();
                        setImeVisibility(false);
                        return true;
                    }
                }
            }
            return super.onKeyPreIme(i, keyEvent);
        }

        private int getSearchViewTextMinWidthDp() {
            Configuration configuration = getResources().getConfiguration();
            int i = configuration.screenWidthDp;
            int i2 = configuration.screenHeightDp;
            if (i >= 960 && i2 >= 720 && configuration.orientation == 2) {
                return 256;
            }
            if (i >= 600 || (i >= 640 && i2 >= 480)) {
                return 192;
            }
            return CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256;
        }

        public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
            InputConnection onCreateInputConnection = super.onCreateInputConnection(editorInfo);
            if (this.d) {
                removeCallbacks(this.a);
                post(this.a);
            }
            return onCreateInputConnection;
        }

        /* access modifiers changed from: private */
        public void b() {
            if (this.d) {
                ((InputMethodManager) getContext().getSystemService("input_method")).showSoftInput(this, 0);
                this.d = false;
            }
        }

        /* access modifiers changed from: private */
        public void setImeVisibility(boolean z) {
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService("input_method");
            if (!z) {
                this.d = false;
                removeCallbacks(this.a);
                inputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);
            } else if (inputMethodManager.isActive(this)) {
                this.d = false;
                removeCallbacks(this.a);
                inputMethodManager.showSoftInput(this, 0);
            } else {
                this.d = true;
            }
        }
    }

    static class UpdatableTouchDelegate extends TouchDelegate {
        private final View a;
        private final Rect b = new Rect();
        private final Rect c = new Rect();
        private final Rect d = new Rect();
        private final int e;
        private boolean f;

        public UpdatableTouchDelegate(Rect rect, Rect rect2, View view) {
            super(rect, view);
            this.e = ViewConfiguration.get(view.getContext()).getScaledTouchSlop();
            a(rect, rect2);
            this.a = view;
        }

        public void a(Rect rect, Rect rect2) {
            this.b.set(rect);
            this.d.set(rect);
            this.d.inset(-this.e, -this.e);
            this.c.set(rect2);
        }

        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTouchEvent(android.view.MotionEvent r7) {
            /*
                r6 = this;
                float r0 = r7.getX()
                int r0 = (int) r0
                float r1 = r7.getY()
                int r1 = (int) r1
                int r2 = r7.getAction()
                r3 = 1
                r4 = 0
                switch(r2) {
                    case 0: goto L_0x0027;
                    case 1: goto L_0x0019;
                    case 2: goto L_0x0019;
                    case 3: goto L_0x0014;
                    default: goto L_0x0013;
                }
            L_0x0013:
                goto L_0x0033
            L_0x0014:
                boolean r2 = r6.f
                r6.f = r4
                goto L_0x0034
            L_0x0019:
                boolean r2 = r6.f
                if (r2 == 0) goto L_0x0034
                android.graphics.Rect r5 = r6.d
                boolean r5 = r5.contains(r0, r1)
                if (r5 != 0) goto L_0x0034
                r3 = 0
                goto L_0x0034
            L_0x0027:
                android.graphics.Rect r2 = r6.b
                boolean r2 = r2.contains(r0, r1)
                if (r2 == 0) goto L_0x0033
                r6.f = r3
                r2 = 1
                goto L_0x0034
            L_0x0033:
                r2 = 0
            L_0x0034:
                if (r2 == 0) goto L_0x006b
                if (r3 == 0) goto L_0x0056
                android.graphics.Rect r2 = r6.c
                boolean r2 = r2.contains(r0, r1)
                if (r2 != 0) goto L_0x0056
                android.view.View r0 = r6.a
                int r0 = r0.getWidth()
                int r0 = r0 / 2
                float r0 = (float) r0
                android.view.View r1 = r6.a
                int r1 = r1.getHeight()
                int r1 = r1 / 2
                float r1 = (float) r1
                r7.setLocation(r0, r1)
                goto L_0x0065
            L_0x0056:
                android.graphics.Rect r2 = r6.c
                int r2 = r2.left
                int r0 = r0 - r2
                float r0 = (float) r0
                android.graphics.Rect r2 = r6.c
                int r2 = r2.top
                int r1 = r1 - r2
                float r1 = (float) r1
                r7.setLocation(r0, r1)
            L_0x0065:
                android.view.View r0 = r6.a
                boolean r4 = r0.dispatchTouchEvent(r7)
            L_0x006b:
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.SearchView.UpdatableTouchDelegate.onTouchEvent(android.view.MotionEvent):boolean");
        }
    }

    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.searchViewStyle);
    }

    public SearchView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.p = new Rect();
        this.q = new Rect();
        this.r = new int[2];
        this.s = new int[2];
        this.R = new Runnable() {
            public void run() {
                SearchView.this.a();
            }
        };
        this.S = new Runnable() {
            public void run() {
                if (SearchView.this.g != null && (SearchView.this.g instanceof SuggestionsAdapter)) {
                    SearchView.this.g.changeCursor(null);
                }
            }
        };
        this.T = new WeakHashMap<>();
        this.U = new OnClickListener() {
            public void onClick(View view) {
                if (view == SearchView.this.b) {
                    SearchView.this.d();
                } else if (view == SearchView.this.d) {
                    SearchView.this.c();
                } else if (view == SearchView.this.c) {
                    SearchView.this.b();
                } else if (view == SearchView.this.e) {
                    SearchView.this.e();
                } else if (view == SearchView.this.a) {
                    SearchView.this.h();
                }
            }
        };
        this.j = new OnKeyListener() {
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (SearchView.this.h == null) {
                    return false;
                }
                if (SearchView.this.a.isPopupShowing() && SearchView.this.a.getListSelection() != -1) {
                    return SearchView.this.a(view, i, keyEvent);
                }
                if (SearchView.this.a.a() || !keyEvent.hasNoModifiers() || keyEvent.getAction() != 1 || i != 66) {
                    return false;
                }
                view.cancelLongPress();
                SearchView.this.a(0, (String) null, SearchView.this.a.getText().toString());
                return true;
            }
        };
        this.V = new OnEditorActionListener() {
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                SearchView.this.b();
                return true;
            }
        };
        this.W = new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                SearchView.this.a(i, 0, (String) null);
            }
        };
        this.aa = new OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                SearchView.this.c(i);
            }
        };
        this.ab = new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                SearchView.this.b(charSequence);
            }
        };
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, R.styleable.SearchView, i2, 0);
        LayoutInflater.from(context).inflate(obtainStyledAttributes.getResourceId(R.styleable.SearchView_layout, R.layout.abc_search_view), this, true);
        this.a = (SearchAutoComplete) findViewById(R.id.search_src_text);
        this.a.setSearchView(this);
        this.k = findViewById(R.id.search_edit_frame);
        this.l = findViewById(R.id.search_plate);
        this.m = findViewById(R.id.submit_area);
        this.b = (ImageView) findViewById(R.id.search_button);
        this.c = (ImageView) findViewById(R.id.search_go_btn);
        this.d = (ImageView) findViewById(R.id.search_close_btn);
        this.e = (ImageView) findViewById(R.id.search_voice_btn);
        this.t = (ImageView) findViewById(R.id.search_mag_icon);
        ViewCompat.setBackground(this.l, obtainStyledAttributes.getDrawable(R.styleable.SearchView_queryBackground));
        ViewCompat.setBackground(this.m, obtainStyledAttributes.getDrawable(R.styleable.SearchView_submitBackground));
        this.b.setImageDrawable(obtainStyledAttributes.getDrawable(R.styleable.SearchView_searchIcon));
        this.c.setImageDrawable(obtainStyledAttributes.getDrawable(R.styleable.SearchView_goIcon));
        this.d.setImageDrawable(obtainStyledAttributes.getDrawable(R.styleable.SearchView_closeIcon));
        this.e.setImageDrawable(obtainStyledAttributes.getDrawable(R.styleable.SearchView_voiceIcon));
        this.t.setImageDrawable(obtainStyledAttributes.getDrawable(R.styleable.SearchView_searchIcon));
        this.u = obtainStyledAttributes.getDrawable(R.styleable.SearchView_searchHintIcon);
        TooltipCompat.setTooltipText(this.b, getResources().getString(R.string.abc_searchview_description_search));
        this.v = obtainStyledAttributes.getResourceId(R.styleable.SearchView_suggestionRowLayout, R.layout.abc_search_dropdown_item_icons_2line);
        this.w = obtainStyledAttributes.getResourceId(R.styleable.SearchView_commitIcon, 0);
        this.b.setOnClickListener(this.U);
        this.d.setOnClickListener(this.U);
        this.c.setOnClickListener(this.U);
        this.e.setOnClickListener(this.U);
        this.a.setOnClickListener(this.U);
        this.a.addTextChangedListener(this.ab);
        this.a.setOnEditorActionListener(this.V);
        this.a.setOnItemClickListener(this.W);
        this.a.setOnItemSelectedListener(this.aa);
        this.a.setOnKeyListener(this.j);
        this.a.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View view, boolean z) {
                if (SearchView.this.f != null) {
                    SearchView.this.f.onFocusChange(SearchView.this, z);
                }
            }
        });
        setIconifiedByDefault(obtainStyledAttributes.getBoolean(R.styleable.SearchView_iconifiedByDefault, true));
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.SearchView_android_maxWidth, -1);
        if (dimensionPixelSize != -1) {
            setMaxWidth(dimensionPixelSize);
        }
        this.z = obtainStyledAttributes.getText(R.styleable.SearchView_defaultQueryHint);
        this.H = obtainStyledAttributes.getText(R.styleable.SearchView_queryHint);
        int i3 = obtainStyledAttributes.getInt(R.styleable.SearchView_android_imeOptions, -1);
        if (i3 != -1) {
            setImeOptions(i3);
        }
        int i4 = obtainStyledAttributes.getInt(R.styleable.SearchView_android_inputType, -1);
        if (i4 != -1) {
            setInputType(i4);
        }
        setFocusable(obtainStyledAttributes.getBoolean(R.styleable.SearchView_android_focusable, true));
        obtainStyledAttributes.recycle();
        this.x = new Intent("android.speech.action.WEB_SEARCH");
        this.x.addFlags(268435456);
        this.x.putExtra("android.speech.extra.LANGUAGE_MODEL", "web_search");
        this.y = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        this.y.addFlags(268435456);
        this.n = findViewById(this.a.getDropDownAnchor());
        if (this.n != null) {
            this.n.addOnLayoutChangeListener(new OnLayoutChangeListener() {
                public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    SearchView.this.g();
                }
            });
        }
        a(this.E);
        n();
    }

    /* access modifiers changed from: 0000 */
    public int getSuggestionRowLayout() {
        return this.v;
    }

    /* access modifiers changed from: 0000 */
    public int getSuggestionCommitIconResId() {
        return this.w;
    }

    public void setSearchableInfo(SearchableInfo searchableInfo) {
        this.h = searchableInfo;
        if (this.h != null) {
            o();
            n();
        }
        this.L = i();
        if (this.L) {
            this.a.setPrivateImeOptions("nm");
        }
        a(isIconified());
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setAppSearchData(Bundle bundle) {
        this.Q = bundle;
    }

    public void setImeOptions(int i2) {
        this.a.setImeOptions(i2);
    }

    public int getImeOptions() {
        return this.a.getImeOptions();
    }

    public void setInputType(int i2) {
        this.a.setInputType(i2);
    }

    public int getInputType() {
        return this.a.getInputType();
    }

    public boolean requestFocus(int i2, Rect rect) {
        if (this.J || !isFocusable()) {
            return false;
        }
        if (isIconified()) {
            return super.requestFocus(i2, rect);
        }
        boolean requestFocus = this.a.requestFocus(i2, rect);
        if (requestFocus) {
            a(false);
        }
        return requestFocus;
    }

    public void clearFocus() {
        this.J = true;
        super.clearFocus();
        this.a.clearFocus();
        this.a.setImeVisibility(false);
        this.J = false;
    }

    public void setOnQueryTextListener(OnQueryTextListener onQueryTextListener) {
        this.A = onQueryTextListener;
    }

    public void setOnCloseListener(OnCloseListener onCloseListener) {
        this.B = onCloseListener;
    }

    public void setOnQueryTextFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        this.f = onFocusChangeListener;
    }

    public void setOnSuggestionListener(OnSuggestionListener onSuggestionListener) {
        this.C = onSuggestionListener;
    }

    public void setOnSearchClickListener(OnClickListener onClickListener) {
        this.D = onClickListener;
    }

    public CharSequence getQuery() {
        return this.a.getText();
    }

    public void setQuery(CharSequence charSequence, boolean z2) {
        this.a.setText(charSequence);
        if (charSequence != null) {
            this.a.setSelection(this.a.length());
            this.N = charSequence;
        }
        if (z2 && !TextUtils.isEmpty(charSequence)) {
            b();
        }
    }

    public void setQueryHint(@Nullable CharSequence charSequence) {
        this.H = charSequence;
        n();
    }

    @Nullable
    public CharSequence getQueryHint() {
        if (this.H != null) {
            return this.H;
        }
        if (this.h == null || this.h.getHintId() == 0) {
            return this.z;
        }
        return getContext().getText(this.h.getHintId());
    }

    public void setIconifiedByDefault(boolean z2) {
        if (this.E != z2) {
            this.E = z2;
            a(z2);
            n();
        }
    }

    public boolean isIconfiedByDefault() {
        return this.E;
    }

    public void setIconified(boolean z2) {
        if (z2) {
            c();
        } else {
            d();
        }
    }

    public boolean isIconified() {
        return this.F;
    }

    public void setSubmitButtonEnabled(boolean z2) {
        this.G = z2;
        a(isIconified());
    }

    public boolean isSubmitButtonEnabled() {
        return this.G;
    }

    public void setQueryRefinementEnabled(boolean z2) {
        this.I = z2;
        if (this.g instanceof SuggestionsAdapter) {
            ((SuggestionsAdapter) this.g).a(z2 ? 2 : 1);
        }
    }

    public boolean isQueryRefinementEnabled() {
        return this.I;
    }

    public void setSuggestionsAdapter(CursorAdapter cursorAdapter) {
        this.g = cursorAdapter;
        this.a.setAdapter(this.g);
    }

    public CursorAdapter getSuggestionsAdapter() {
        return this.g;
    }

    public void setMaxWidth(int i2) {
        this.K = i2;
        requestLayout();
    }

    public int getMaxWidth() {
        return this.K;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        if (isIconified()) {
            super.onMeasure(i2, i3);
            return;
        }
        int mode = MeasureSpec.getMode(i2);
        int size = MeasureSpec.getSize(i2);
        if (mode == Integer.MIN_VALUE) {
            size = this.K > 0 ? Math.min(this.K, size) : Math.min(getPreferredWidth(), size);
        } else if (mode == 0) {
            size = this.K > 0 ? this.K : getPreferredWidth();
        } else if (mode == 1073741824 && this.K > 0) {
            size = Math.min(this.K, size);
        }
        int mode2 = MeasureSpec.getMode(i3);
        int size2 = MeasureSpec.getSize(i3);
        if (mode2 == Integer.MIN_VALUE) {
            size2 = Math.min(getPreferredHeight(), size2);
        } else if (mode2 == 0) {
            size2 = getPreferredHeight();
        }
        super.onMeasure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec(size2, 1073741824));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
        if (z2) {
            a((View) this.a, this.p);
            this.q.set(this.p.left, 0, this.p.right, i5 - i3);
            if (this.o == null) {
                this.o = new UpdatableTouchDelegate(this.q, this.p, this.a);
                setTouchDelegate(this.o);
                return;
            }
            this.o.a(this.q, this.p);
        }
    }

    private void a(View view, Rect rect) {
        view.getLocationInWindow(this.r);
        getLocationInWindow(this.s);
        int i2 = this.r[1] - this.s[1];
        int i3 = this.r[0] - this.s[0];
        rect.set(i3, i2, view.getWidth() + i3, view.getHeight() + i2);
    }

    private int getPreferredWidth() {
        return getContext().getResources().getDimensionPixelSize(R.dimen.abc_search_view_preferred_width);
    }

    private int getPreferredHeight() {
        return getContext().getResources().getDimensionPixelSize(R.dimen.abc_search_view_preferred_height);
    }

    private void a(boolean z2) {
        this.F = z2;
        int i2 = 8;
        boolean z3 = false;
        boolean z4 = !TextUtils.isEmpty(this.a.getText());
        this.b.setVisibility(z2 ? 0 : 8);
        b(z4);
        this.k.setVisibility(z2 ? 8 : 0);
        if (this.t.getDrawable() != null && !this.E) {
            i2 = 0;
        }
        this.t.setVisibility(i2);
        l();
        if (!z4) {
            z3 = true;
        }
        c(z3);
        k();
    }

    private boolean i() {
        boolean z2 = false;
        if (this.h != null && this.h.getVoiceSearchEnabled()) {
            Intent intent = null;
            if (this.h.getVoiceSearchLaunchWebSearch()) {
                intent = this.x;
            } else if (this.h.getVoiceSearchLaunchRecognizer()) {
                intent = this.y;
            }
            if (intent != null) {
                if (getContext().getPackageManager().resolveActivity(intent, 65536) != null) {
                    z2 = true;
                }
                return z2;
            }
        }
        return false;
    }

    private boolean j() {
        return (this.G || this.L) && !isIconified();
    }

    private void b(boolean z2) {
        this.c.setVisibility((!this.G || !j() || !hasFocus() || (!z2 && this.L)) ? 8 : 0);
    }

    private void k() {
        this.m.setVisibility((!j() || !(this.c.getVisibility() == 0 || this.e.getVisibility() == 0)) ? 8 : 0);
    }

    private void l() {
        boolean z2 = true;
        boolean z3 = !TextUtils.isEmpty(this.a.getText());
        int i2 = 0;
        if (!z3 && (!this.E || this.O)) {
            z2 = false;
        }
        ImageView imageView = this.d;
        if (!z2) {
            i2 = 8;
        }
        imageView.setVisibility(i2);
        Drawable drawable = this.d.getDrawable();
        if (drawable != null) {
            drawable.setState(z3 ? ENABLED_STATE_SET : EMPTY_STATE_SET);
        }
    }

    private void m() {
        post(this.R);
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        int[] iArr = this.a.hasFocus() ? FOCUSED_STATE_SET : EMPTY_STATE_SET;
        Drawable background = this.l.getBackground();
        if (background != null) {
            background.setState(iArr);
        }
        Drawable background2 = this.m.getBackground();
        if (background2 != null) {
            background2.setState(iArr);
        }
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        removeCallbacks(this.R);
        post(this.S);
        super.onDetachedFromWindow();
    }

    /* access modifiers changed from: 0000 */
    public void a(CharSequence charSequence) {
        setQuery(charSequence);
    }

    /* access modifiers changed from: 0000 */
    public boolean a(View view, int i2, KeyEvent keyEvent) {
        int i3;
        if (this.h != null && this.g != null && keyEvent.getAction() == 0 && keyEvent.hasNoModifiers()) {
            if (i2 == 66 || i2 == 84 || i2 == 61) {
                return a(this.a.getListSelection(), 0, (String) null);
            }
            if (i2 == 21 || i2 == 22) {
                if (i2 == 21) {
                    i3 = 0;
                } else {
                    i3 = this.a.length();
                }
                this.a.setSelection(i3);
                this.a.setListSelection(0);
                this.a.clearListSelection();
                i.a(this.a, true);
                return true;
            } else if (i2 != 19 || this.a.getListSelection() == 0) {
                return false;
            }
        }
        return false;
    }

    private CharSequence c(CharSequence charSequence) {
        if (!this.E || this.u == null) {
            return charSequence;
        }
        int textSize = (int) (((double) this.a.getTextSize()) * 1.25d);
        this.u.setBounds(0, 0, textSize, textSize);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("   ");
        spannableStringBuilder.setSpan(new ImageSpan(this.u), 1, 2, 33);
        spannableStringBuilder.append(charSequence);
        return spannableStringBuilder;
    }

    private void n() {
        CharSequence queryHint = getQueryHint();
        SearchAutoComplete searchAutoComplete = this.a;
        if (queryHint == null) {
            queryHint = "";
        }
        searchAutoComplete.setHint(c(queryHint));
    }

    private void o() {
        this.a.setThreshold(this.h.getSuggestThreshold());
        this.a.setImeOptions(this.h.getImeOptions());
        int inputType = this.h.getInputType();
        int i2 = 1;
        if ((inputType & 15) == 1) {
            inputType &= -65537;
            if (this.h.getSuggestAuthority() != null) {
                inputType = inputType | 65536 | 524288;
            }
        }
        this.a.setInputType(inputType);
        if (this.g != null) {
            this.g.changeCursor(null);
        }
        if (this.h.getSuggestAuthority() != null) {
            this.g = new SuggestionsAdapter(getContext(), this, this.h, this.T);
            this.a.setAdapter(this.g);
            SuggestionsAdapter suggestionsAdapter = (SuggestionsAdapter) this.g;
            if (this.I) {
                i2 = 2;
            }
            suggestionsAdapter.a(i2);
        }
    }

    private void c(boolean z2) {
        int i2;
        if (!this.L || isIconified() || !z2) {
            i2 = 8;
        } else {
            i2 = 0;
            this.c.setVisibility(8);
        }
        this.e.setVisibility(i2);
    }

    /* access modifiers changed from: 0000 */
    public void b(CharSequence charSequence) {
        Editable text = this.a.getText();
        this.N = text;
        boolean z2 = true;
        boolean z3 = !TextUtils.isEmpty(text);
        b(z3);
        if (z3) {
            z2 = false;
        }
        c(z2);
        l();
        k();
        if (this.A != null && !TextUtils.equals(charSequence, this.M)) {
            this.A.onQueryTextChange(charSequence.toString());
        }
        this.M = charSequence.toString();
    }

    /* access modifiers changed from: 0000 */
    public void b() {
        Editable text = this.a.getText();
        if (text != null && TextUtils.getTrimmedLength(text) > 0) {
            if (this.A == null || !this.A.onQueryTextSubmit(text.toString())) {
                if (this.h != null) {
                    a(0, (String) null, text.toString());
                }
                this.a.setImeVisibility(false);
                p();
            }
        }
    }

    private void p() {
        this.a.dismissDropDown();
    }

    /* access modifiers changed from: 0000 */
    public void c() {
        if (!TextUtils.isEmpty(this.a.getText())) {
            this.a.setText("");
            this.a.requestFocus();
            this.a.setImeVisibility(true);
        } else if (!this.E) {
        } else {
            if (this.B == null || !this.B.onClose()) {
                clearFocus();
                a(true);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void d() {
        a(false);
        this.a.requestFocus();
        this.a.setImeVisibility(true);
        if (this.D != null) {
            this.D.onClick(this);
        }
    }

    /* access modifiers changed from: 0000 */
    public void e() {
        if (this.h != null) {
            SearchableInfo searchableInfo = this.h;
            try {
                if (searchableInfo.getVoiceSearchLaunchWebSearch()) {
                    getContext().startActivity(a(this.x, searchableInfo));
                } else if (searchableInfo.getVoiceSearchLaunchRecognizer()) {
                    getContext().startActivity(b(this.y, searchableInfo));
                }
            } catch (ActivityNotFoundException unused) {
                Log.w("SearchView", "Could not find voice search activity");
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void f() {
        a(isIconified());
        m();
        if (this.a.hasFocus()) {
            h();
        }
    }

    public void onWindowFocusChanged(boolean z2) {
        super.onWindowFocusChanged(z2);
        m();
    }

    public void onActionViewCollapsed() {
        setQuery("", false);
        clearFocus();
        a(true);
        this.a.setImeOptions(this.P);
        this.O = false;
    }

    public void onActionViewExpanded() {
        if (!this.O) {
            this.O = true;
            this.P = this.a.getImeOptions();
            this.a.setImeOptions(this.P | 33554432);
            this.a.setText("");
            setIconified(false);
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.a = isIconified();
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
        a(savedState.a);
        requestLayout();
    }

    /* access modifiers changed from: 0000 */
    public void g() {
        int i2;
        if (this.n.getWidth() > 1) {
            Resources resources = getContext().getResources();
            int paddingLeft = this.l.getPaddingLeft();
            Rect rect = new Rect();
            boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
            int dimensionPixelSize = this.E ? resources.getDimensionPixelSize(R.dimen.abc_dropdownitem_icon_width) + resources.getDimensionPixelSize(R.dimen.abc_dropdownitem_text_padding_left) : 0;
            this.a.getDropDownBackground().getPadding(rect);
            if (isLayoutRtl) {
                i2 = -rect.left;
            } else {
                i2 = paddingLeft - (rect.left + dimensionPixelSize);
            }
            this.a.setDropDownHorizontalOffset(i2);
            this.a.setDropDownWidth((((this.n.getWidth() + rect.left) + rect.right) + dimensionPixelSize) - paddingLeft);
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean a(int i2, int i3, String str) {
        if (this.C != null && this.C.onSuggestionClick(i2)) {
            return false;
        }
        b(i2, 0, null);
        this.a.setImeVisibility(false);
        p();
        return true;
    }

    /* access modifiers changed from: 0000 */
    public boolean c(int i2) {
        if (this.C != null && this.C.onSuggestionSelect(i2)) {
            return false;
        }
        d(i2);
        return true;
    }

    private void d(int i2) {
        Editable text = this.a.getText();
        Cursor cursor = this.g.getCursor();
        if (cursor != null) {
            if (cursor.moveToPosition(i2)) {
                CharSequence convertToString = this.g.convertToString(cursor);
                if (convertToString != null) {
                    setQuery(convertToString);
                } else {
                    setQuery(text);
                }
            } else {
                setQuery(text);
            }
        }
    }

    private boolean b(int i2, int i3, String str) {
        Cursor cursor = this.g.getCursor();
        if (cursor == null || !cursor.moveToPosition(i2)) {
            return false;
        }
        a(a(cursor, i3, str));
        return true;
    }

    private void a(Intent intent) {
        if (intent != null) {
            try {
                getContext().startActivity(intent);
            } catch (RuntimeException e2) {
                StringBuilder sb = new StringBuilder();
                sb.append("Failed launch activity: ");
                sb.append(intent);
                Log.e("SearchView", sb.toString(), e2);
            }
        }
    }

    private void setQuery(CharSequence charSequence) {
        this.a.setText(charSequence);
        this.a.setSelection(TextUtils.isEmpty(charSequence) ? 0 : charSequence.length());
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2, String str, String str2) {
        getContext().startActivity(a("android.intent.action.SEARCH", null, null, str2, i2, str));
    }

    private Intent a(String str, Uri uri, String str2, String str3, int i2, String str4) {
        Intent intent = new Intent(str);
        intent.addFlags(268435456);
        if (uri != null) {
            intent.setData(uri);
        }
        intent.putExtra("user_query", this.N);
        if (str3 != null) {
            intent.putExtra(SearchIntents.EXTRA_QUERY, str3);
        }
        if (str2 != null) {
            intent.putExtra("intent_extra_data_key", str2);
        }
        if (this.Q != null) {
            intent.putExtra("app_data", this.Q);
        }
        if (i2 != 0) {
            intent.putExtra("action_key", i2);
            intent.putExtra("action_msg", str4);
        }
        intent.setComponent(this.h.getSearchActivity());
        return intent;
    }

    private Intent a(Intent intent, SearchableInfo searchableInfo) {
        String str;
        Intent intent2 = new Intent(intent);
        ComponentName searchActivity = searchableInfo.getSearchActivity();
        String str2 = "calling_package";
        if (searchActivity == null) {
            str = null;
        } else {
            str = searchActivity.flattenToShortString();
        }
        intent2.putExtra(str2, str);
        return intent2;
    }

    private Intent b(Intent intent, SearchableInfo searchableInfo) {
        ComponentName searchActivity = searchableInfo.getSearchActivity();
        Intent intent2 = new Intent("android.intent.action.SEARCH");
        intent2.setComponent(searchActivity);
        PendingIntent activity = PendingIntent.getActivity(getContext(), 0, intent2, 1073741824);
        Bundle bundle = new Bundle();
        if (this.Q != null) {
            bundle.putParcelable("app_data", this.Q);
        }
        Intent intent3 = new Intent(intent);
        String str = "free_form";
        int i2 = 1;
        Resources resources = getResources();
        if (searchableInfo.getVoiceLanguageModeId() != 0) {
            str = resources.getString(searchableInfo.getVoiceLanguageModeId());
        }
        String str2 = null;
        String string = searchableInfo.getVoicePromptTextId() != 0 ? resources.getString(searchableInfo.getVoicePromptTextId()) : null;
        String string2 = searchableInfo.getVoiceLanguageId() != 0 ? resources.getString(searchableInfo.getVoiceLanguageId()) : null;
        if (searchableInfo.getVoiceMaxResults() != 0) {
            i2 = searchableInfo.getVoiceMaxResults();
        }
        intent3.putExtra("android.speech.extra.LANGUAGE_MODEL", str);
        intent3.putExtra("android.speech.extra.PROMPT", string);
        intent3.putExtra("android.speech.extra.LANGUAGE", string2);
        intent3.putExtra("android.speech.extra.MAX_RESULTS", i2);
        String str3 = "calling_package";
        if (searchActivity != null) {
            str2 = searchActivity.flattenToShortString();
        }
        intent3.putExtra(str3, str2);
        intent3.putExtra("android.speech.extra.RESULTS_PENDINGINTENT", activity);
        intent3.putExtra("android.speech.extra.RESULTS_PENDINGINTENT_BUNDLE", bundle);
        return intent3;
    }

    private Intent a(Cursor cursor, int i2, String str) {
        int i3;
        Uri uri;
        try {
            String a2 = SuggestionsAdapter.a(cursor, "suggest_intent_action");
            if (a2 == null) {
                a2 = this.h.getSuggestIntentAction();
            }
            if (a2 == null) {
                a2 = "android.intent.action.SEARCH";
            }
            String str2 = a2;
            String a3 = SuggestionsAdapter.a(cursor, "suggest_intent_data");
            if (a3 == null) {
                a3 = this.h.getSuggestIntentData();
            }
            if (a3 != null) {
                String a4 = SuggestionsAdapter.a(cursor, "suggest_intent_data_id");
                if (a4 != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(a3);
                    sb.append("/");
                    sb.append(Uri.encode(a4));
                    a3 = sb.toString();
                }
            }
            if (a3 == null) {
                uri = null;
            } else {
                uri = Uri.parse(a3);
            }
            String a5 = SuggestionsAdapter.a(cursor, "suggest_intent_query");
            return a(str2, uri, SuggestionsAdapter.a(cursor, "suggest_intent_extra_data"), a5, i2, str);
        } catch (RuntimeException e2) {
            try {
                i3 = cursor.getPosition();
            } catch (RuntimeException unused) {
                i3 = -1;
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Search suggestions cursor at row ");
            sb2.append(i3);
            sb2.append(" returned exception.");
            Log.w("SearchView", sb2.toString(), e2);
            return null;
        }
    }

    /* access modifiers changed from: 0000 */
    public void h() {
        i.a(this.a);
        i.b(this.a);
    }

    static boolean a(Context context) {
        return context.getResources().getConfiguration().orientation == 2;
    }
}
