package com.crashlytics.android.core;

class CreateReportRequest {
    public final String a;
    public final Report b;

    public CreateReportRequest(String str, Report report) {
        this.a = str;
        this.b = report;
    }
}
