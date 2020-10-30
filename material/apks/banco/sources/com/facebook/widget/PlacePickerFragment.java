package com.facebook.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import com.facebook.AppEventsLogger;
import com.facebook.FacebookException;
import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.Session;
import com.facebook.android.R;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import com.facebook.model.GraphPlace;
import com.facebook.widget.GraphObjectAdapter.DataNeededListener;
import com.facebook.widget.PickerFragment.OnErrorListener;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class PlacePickerFragment extends PickerFragment<GraphPlace> {
    public static final int DEFAULT_RADIUS_IN_METERS = 1000;
    public static final int DEFAULT_RESULTS_LIMIT = 100;
    public static final String LOCATION_BUNDLE_KEY = "com.facebook.widget.PlacePickerFragment.Location";
    public static final String RADIUS_IN_METERS_BUNDLE_KEY = "com.facebook.widget.PlacePickerFragment.RadiusInMeters";
    public static final String RESULTS_LIMIT_BUNDLE_KEY = "com.facebook.widget.PlacePickerFragment.ResultsLimit";
    public static final String SEARCH_TEXT_BUNDLE_KEY = "com.facebook.widget.PlacePickerFragment.SearchText";
    public static final String SHOW_SEARCH_BOX_BUNDLE_KEY = "com.facebook.widget.PlacePickerFragment.ShowSearchBox";
    private EditText ad;
    private Location c;
    private int d;
    private int e;
    private String f;
    private Timer g;
    private boolean h;
    private boolean i;

    class AsNeededLoadingStrategy extends LoadingStrategy {
        private AsNeededLoadingStrategy() {
            super();
        }

        public void a(GraphObjectAdapter<GraphPlace> graphObjectAdapter) {
            super.a(graphObjectAdapter);
            this.c.a((DataNeededListener) new DataNeededListener() {
                public void onDataNeeded() {
                    if (!AsNeededLoadingStrategy.this.b.c()) {
                        AsNeededLoadingStrategy.this.b.d();
                    }
                }
            });
        }

        /* access modifiers changed from: protected */
        public void a(GraphObjectPagingLoader<GraphPlace> graphObjectPagingLoader, SimpleGraphObjectCursor<GraphPlace> simpleGraphObjectCursor) {
            super.a(graphObjectPagingLoader, simpleGraphObjectCursor);
            if (simpleGraphObjectCursor != null && !graphObjectPagingLoader.c()) {
                PlacePickerFragment.this.H();
                if (simpleGraphObjectCursor.g()) {
                    graphObjectPagingLoader.a(simpleGraphObjectCursor.a() ? 2000 : 0);
                }
            }
        }
    }

    class SearchTextWatcher implements TextWatcher {
        public void afterTextChanged(Editable editable) {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        private SearchTextWatcher() {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            PlacePickerFragment.this.onSearchBoxTextChanged(charSequence.toString(), false);
        }
    }

    public PlacePickerFragment() {
        this(null);
    }

    public PlacePickerFragment(Bundle bundle) {
        super(GraphPlace.class, R.layout.com_facebook_placepickerfragment, bundle);
        this.d = 1000;
        this.e = 100;
        this.i = true;
        h(bundle);
    }

    public Location getLocation() {
        return this.c;
    }

    public void setLocation(Location location) {
        this.c = location;
    }

    public int getRadiusInMeters() {
        return this.d;
    }

    public void setRadiusInMeters(int i2) {
        this.d = i2;
    }

    public int getResultsLimit() {
        return this.e;
    }

    public void setResultsLimit(int i2) {
        this.e = i2;
    }

    public String getSearchText() {
        return this.f;
    }

    public void setSearchText(String str) {
        if (TextUtils.isEmpty(str)) {
            str = null;
        }
        this.f = str;
        if (this.ad != null) {
            this.ad.setText(str);
        }
    }

    public void onSearchBoxTextChanged(String str, boolean z) {
        if (z || !Utility.stringsEqualOrEmpty(this.f, str)) {
            if (TextUtils.isEmpty(str)) {
                str = null;
            }
            this.f = str;
            this.h = true;
            if (this.g == null) {
                this.g = I();
            }
        }
    }

    public GraphPlace getSelection() {
        List C = C();
        if (C == null || C.isEmpty()) {
            return null;
        }
        return (GraphPlace) C.iterator().next();
    }

    public void setSettingsFromBundle(Bundle bundle) {
        super.setSettingsFromBundle(bundle);
        h(bundle);
    }

    public void onInflate(Activity activity, AttributeSet attributeSet, Bundle bundle) {
        super.onInflate(activity, attributeSet, bundle);
        TypedArray obtainStyledAttributes = activity.obtainStyledAttributes(attributeSet, R.styleable.com_facebook_place_picker_fragment);
        setRadiusInMeters(obtainStyledAttributes.getInt(0, this.d));
        setResultsLimit(obtainStyledAttributes.getInt(1, this.e));
        if (obtainStyledAttributes.hasValue(1)) {
            setSearchText(obtainStyledAttributes.getString(2));
        }
        this.i = obtainStyledAttributes.getBoolean(3, this.i);
        obtainStyledAttributes.recycle();
    }

    /* access modifiers changed from: 0000 */
    public void a(ViewGroup viewGroup) {
        if (this.i) {
            ListView listView = (ListView) viewGroup.findViewById(R.id.com_facebook_picker_list_view);
            listView.addHeaderView(getActivity().getLayoutInflater().inflate(R.layout.com_facebook_picker_search_box, listView, false), null, false);
            this.ad = (EditText) viewGroup.findViewById(R.id.com_facebook_picker_search_text);
            this.ad.addTextChangedListener(new SearchTextWatcher());
            if (!TextUtils.isEmpty(this.f)) {
                this.ad.setText(this.f);
            }
        }
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (this.ad != null) {
            ((InputMethodManager) getActivity().getSystemService("input_method")).showSoftInput(this.ad, 1);
        }
    }

    public void onDetach() {
        super.onDetach();
        if (this.ad != null) {
            ((InputMethodManager) getActivity().getSystemService("input_method")).hideSoftInputFromWindow(this.ad.getWindowToken(), 0);
        }
    }

    /* access modifiers changed from: 0000 */
    public void g(Bundle bundle) {
        super.g(bundle);
        bundle.putInt(RADIUS_IN_METERS_BUNDLE_KEY, this.d);
        bundle.putInt(RESULTS_LIMIT_BUNDLE_KEY, this.e);
        bundle.putString(SEARCH_TEXT_BUNDLE_KEY, this.f);
        bundle.putParcelable(LOCATION_BUNDLE_KEY, this.c);
        bundle.putBoolean(SHOW_SEARCH_BOX_BUNDLE_KEY, this.i);
    }

    /* access modifiers changed from: 0000 */
    public void D() {
        this.h = false;
    }

    /* access modifiers changed from: 0000 */
    public Request a(Session session) {
        return a(this.c, this.d, this.e, this.f, this.a, session);
    }

    /* access modifiers changed from: 0000 */
    public String B() {
        return getString(R.string.com_facebook_nearby);
    }

    /* access modifiers changed from: 0000 */
    public void a(boolean z) {
        AppEventsLogger newLogger = AppEventsLogger.newLogger((Context) getActivity(), getSession());
        Bundle bundle = new Bundle();
        bundle.putString(AnalyticsEvents.PARAMETER_DIALOG_OUTCOME, z ? AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_COMPLETED : AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN);
        bundle.putInt("num_places_picked", getSelection() != null ? 1 : 0);
        newLogger.logSdkEvent(AnalyticsEvents.EVENT_PLACE_PICKER_USAGE, null, bundle);
    }

    /* access modifiers changed from: 0000 */
    public PickerFragmentAdapter<GraphPlace> y() {
        AnonymousClass1 r0 = new PickerFragmentAdapter<GraphPlace>(getActivity()) {
            /* access modifiers changed from: protected */
            /* renamed from: a */
            public CharSequence d(GraphPlace graphPlace) {
                String category = graphPlace.getCategory();
                Integer num = (Integer) graphPlace.getProperty("were_here_count");
                if (category != null && num != null) {
                    return PlacePickerFragment.this.getString(R.string.com_facebook_placepicker_subtitle_format, category, num);
                } else if (category == null && num != null) {
                    return PlacePickerFragment.this.getString(R.string.com_facebook_placepicker_subtitle_were_here_only_format, num);
                } else if (category == null || num != null) {
                    return null;
                } else {
                    return PlacePickerFragment.this.getString(R.string.com_facebook_placepicker_subtitle_catetory_only_format, category);
                }
            }

            /* access modifiers changed from: protected */
            /* renamed from: b */
            public int a(GraphPlace graphPlace) {
                return R.layout.com_facebook_placepickerfragment_list_row;
            }

            /* access modifiers changed from: protected */
            public int a() {
                return R.drawable.com_facebook_place_default_icon;
            }
        };
        r0.b(false);
        r0.a(getShowPictures());
        return r0;
    }

    /* access modifiers changed from: 0000 */
    public LoadingStrategy z() {
        return new AsNeededLoadingStrategy();
    }

    /* access modifiers changed from: 0000 */
    public SelectionStrategy A() {
        return new SingleSelectionStrategy();
    }

    private Request a(Location location, int i2, int i3, String str, Set<String> set, Session session) {
        Request newPlacesSearchRequest = Request.newPlacesSearchRequest(session, location, i2, i3, str, null);
        HashSet hashSet = new HashSet(set);
        hashSet.addAll(Arrays.asList(new String[]{"id", "name", "location", "category", "were_here_count"}));
        String e2 = this.b.e();
        if (e2 != null) {
            hashSet.add(e2);
        }
        Bundle parameters = newPlacesSearchRequest.getParameters();
        parameters.putString("fields", TextUtils.join(",", hashSet));
        newPlacesSearchRequest.setParameters(parameters);
        return newPlacesSearchRequest;
    }

    private void h(Bundle bundle) {
        if (bundle != null) {
            setRadiusInMeters(bundle.getInt(RADIUS_IN_METERS_BUNDLE_KEY, this.d));
            setResultsLimit(bundle.getInt(RESULTS_LIMIT_BUNDLE_KEY, this.e));
            if (bundle.containsKey(SEARCH_TEXT_BUNDLE_KEY)) {
                setSearchText(bundle.getString(SEARCH_TEXT_BUNDLE_KEY));
            }
            if (bundle.containsKey(LOCATION_BUNDLE_KEY)) {
                setLocation((Location) bundle.getParcelable(LOCATION_BUNDLE_KEY));
            }
            this.i = bundle.getBoolean(SHOW_SEARCH_BOX_BUNDLE_KEY, this.i);
        }
    }

    private Timer I() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                PlacePickerFragment.this.J();
            }
        }, 0, 2000);
        return timer;
    }

    /* access modifiers changed from: private */
    public void J() {
        if (this.h) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    try {
                        PlacePickerFragment.this.loadData(true);
                    } catch (FacebookException e) {
                        if (e != null) {
                            OnErrorListener onErrorListener = PlacePickerFragment.this.getOnErrorListener();
                            if (onErrorListener != null) {
                                onErrorListener.onError(PlacePickerFragment.this, e);
                                return;
                            }
                            Logger.log(LoggingBehavior.REQUESTS, "PlacePickerFragment", "Error loading data : %s", e);
                        }
                    } catch (Exception e2) {
                        FacebookException facebookException = new FacebookException((Throwable) e2);
                        if (facebookException != null) {
                            OnErrorListener onErrorListener2 = PlacePickerFragment.this.getOnErrorListener();
                            if (onErrorListener2 != null) {
                                onErrorListener2.onError(PlacePickerFragment.this, facebookException);
                                return;
                            }
                            Logger.log(LoggingBehavior.REQUESTS, "PlacePickerFragment", "Error loading data : %s", facebookException);
                        }
                    }
                }
            });
            return;
        }
        this.g.cancel();
        this.g = null;
    }
}
