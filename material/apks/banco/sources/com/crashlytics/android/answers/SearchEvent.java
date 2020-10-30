package com.crashlytics.android.answers;

import com.google.android.gms.actions.SearchIntents;

public class SearchEvent extends PredefinedEvent<SearchEvent> {
    /* access modifiers changed from: 0000 */
    public String a() {
        return "search";
    }

    public SearchEvent putQuery(String str) {
        this.d.a(SearchIntents.EXTRA_QUERY, str);
        return this;
    }
}
