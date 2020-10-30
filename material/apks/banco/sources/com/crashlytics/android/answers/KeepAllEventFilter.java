package com.crashlytics.android.answers;

class KeepAllEventFilter implements EventFilter {
    public boolean a(SessionEvent sessionEvent) {
        return false;
    }

    KeepAllEventFilter() {
    }
}
