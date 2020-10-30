package com.facebook.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import com.facebook.AppEventsLogger;
import com.facebook.FacebookException;
import com.facebook.Request;
import com.facebook.Session;
import com.facebook.android.R;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.model.GraphUser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FriendPickerFragment extends PickerFragment<GraphUser> {
    public static final String FRIEND_PICKER_TYPE_KEY = "com.facebook.widget.FriendPickerFragment.FriendPickerType";
    public static final String MULTI_SELECT_BUNDLE_KEY = "com.facebook.widget.FriendPickerFragment.MultiSelect";
    public static final String USER_ID_BUNDLE_KEY = "com.facebook.widget.FriendPickerFragment.UserId";
    private String c;
    private boolean d;
    /* access modifiers changed from: private */
    public FriendPickerType e;
    private List<String> f;

    public enum FriendPickerType {
        FRIENDS("/friends", true),
        TAGGABLE_FRIENDS("/taggable_friends", false),
        INVITABLE_FRIENDS("/invitable_friends", false);
        
        private final String a;
        private final boolean b;

        private FriendPickerType(String str, boolean z) {
            this.a = str;
            this.b = z;
        }

        /* access modifiers changed from: 0000 */
        public String a() {
            return this.a;
        }

        /* access modifiers changed from: 0000 */
        public boolean b() {
            return this.b;
        }
    }

    class ImmediateLoadingStrategy extends LoadingStrategy {
        private ImmediateLoadingStrategy() {
            super();
        }

        /* access modifiers changed from: protected */
        public void a(GraphObjectPagingLoader<GraphUser> graphObjectPagingLoader, SimpleGraphObjectCursor<GraphUser> simpleGraphObjectCursor) {
            super.a(graphObjectPagingLoader, simpleGraphObjectCursor);
            if (simpleGraphObjectCursor != null && !graphObjectPagingLoader.c()) {
                if (simpleGraphObjectCursor.a()) {
                    f();
                } else {
                    FriendPickerFragment.this.H();
                    if (simpleGraphObjectCursor.g()) {
                        graphObjectPagingLoader.a(simpleGraphObjectCursor.b() == 0 ? 2000 : 0);
                    }
                }
            }
        }

        /* access modifiers changed from: protected */
        public boolean a() {
            return FriendPickerFragment.this.e.b();
        }

        private void f() {
            FriendPickerFragment.this.F();
            this.b.d();
        }
    }

    public FriendPickerFragment() {
        this(null);
    }

    @SuppressLint({"ValidFragment"})
    public FriendPickerFragment(Bundle bundle) {
        super(GraphUser.class, R.layout.com_facebook_friendpickerfragment, bundle);
        this.d = true;
        this.e = FriendPickerType.FRIENDS;
        this.f = new ArrayList();
        h(bundle);
    }

    public String getUserId() {
        return this.c;
    }

    public void setUserId(String str) {
        this.c = str;
    }

    public boolean getMultiSelect() {
        return this.d;
    }

    public void setMultiSelect(boolean z) {
        if (this.d != z) {
            this.d = z;
            a(A());
        }
    }

    public void setFriendPickerType(FriendPickerType friendPickerType) {
        this.e = friendPickerType;
    }

    public void setSelectionByIds(List<String> list) {
        this.f.addAll(list);
    }

    public void setSelectionByIds(String... strArr) {
        setSelectionByIds(Arrays.asList(strArr));
    }

    public void setSelection(GraphUser... graphUserArr) {
        setSelection(Arrays.asList(graphUserArr));
    }

    public void setSelection(List<GraphUser> list) {
        ArrayList arrayList = new ArrayList();
        for (GraphUser id2 : list) {
            arrayList.add(id2.getId());
        }
        setSelectionByIds((List<String>) arrayList);
    }

    public List<GraphUser> getSelection() {
        return C();
    }

    public void onInflate(Activity activity, AttributeSet attributeSet, Bundle bundle) {
        super.onInflate(activity, attributeSet, bundle);
        TypedArray obtainStyledAttributes = activity.obtainStyledAttributes(attributeSet, R.styleable.com_facebook_friend_picker_fragment);
        setMultiSelect(obtainStyledAttributes.getBoolean(0, this.d));
        obtainStyledAttributes.recycle();
    }

    public void setSettingsFromBundle(Bundle bundle) {
        super.setSettingsFromBundle(bundle);
        h(bundle);
    }

    /* access modifiers changed from: 0000 */
    public void g(Bundle bundle) {
        super.g(bundle);
        bundle.putString(USER_ID_BUNDLE_KEY, this.c);
        bundle.putBoolean(MULTI_SELECT_BUNDLE_KEY, this.d);
    }

    /* access modifiers changed from: 0000 */
    public PickerFragmentAdapter<GraphUser> y() {
        AnonymousClass1 r0 = new PickerFragmentAdapter<GraphUser>(getActivity()) {
            /* access modifiers changed from: protected */
            public int a(GraphUser graphUser) {
                return R.layout.com_facebook_picker_list_row;
            }

            /* access modifiers changed from: protected */
            public int a() {
                return R.drawable.com_facebook_profile_default_icon;
            }
        };
        r0.b(true);
        r0.a(getShowPictures());
        r0.a(Arrays.asList(new String[]{"name"}));
        r0.a("name");
        return r0;
    }

    /* access modifiers changed from: 0000 */
    public LoadingStrategy z() {
        return new ImmediateLoadingStrategy();
    }

    /* access modifiers changed from: 0000 */
    public SelectionStrategy A() {
        return this.d ? new MultiSelectionStrategy() : new SingleSelectionStrategy();
    }

    /* access modifiers changed from: 0000 */
    public Request a(Session session) {
        if (this.b == null) {
            throw new FacebookException("Can't issue requests until Fragment has been created.");
        }
        return a(this.c != null ? this.c : "me", this.a, session);
    }

    /* access modifiers changed from: 0000 */
    public String B() {
        return getString(R.string.com_facebook_choose_friends);
    }

    /* access modifiers changed from: 0000 */
    public void a(boolean z) {
        AppEventsLogger newLogger = AppEventsLogger.newLogger((Context) getActivity(), getSession());
        Bundle bundle = new Bundle();
        bundle.putString(AnalyticsEvents.PARAMETER_DIALOG_OUTCOME, z ? AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_COMPLETED : AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN);
        bundle.putInt("num_friends_picked", getSelection().size());
        newLogger.logSdkEvent(AnalyticsEvents.EVENT_FRIEND_PICKER_USAGE, null, bundle);
    }

    public void loadData(boolean z) {
        super.loadData(z);
        a(this.f);
    }

    private Request a(String str, Set<String> set, Session session) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(this.e.a());
        Request newGraphPathRequest = Request.newGraphPathRequest(session, sb.toString(), null);
        HashSet hashSet = new HashSet(set);
        hashSet.addAll(Arrays.asList(new String[]{"id", "name"}));
        String e2 = this.b.e();
        if (e2 != null) {
            hashSet.add(e2);
        }
        Bundle parameters = newGraphPathRequest.getParameters();
        parameters.putString("fields", TextUtils.join(",", hashSet));
        newGraphPathRequest.setParameters(parameters);
        return newGraphPathRequest;
    }

    private void h(Bundle bundle) {
        if (bundle != null) {
            if (bundle.containsKey(USER_ID_BUNDLE_KEY)) {
                setUserId(bundle.getString(USER_ID_BUNDLE_KEY));
            }
            setMultiSelect(bundle.getBoolean(MULTI_SELECT_BUNDLE_KEY, this.d));
            if (bundle.containsKey(FRIEND_PICKER_TYPE_KEY)) {
                try {
                    this.e = FriendPickerType.valueOf(bundle.getString(FRIEND_PICKER_TYPE_KEY));
                } catch (Exception unused) {
                }
            }
        }
    }
}
