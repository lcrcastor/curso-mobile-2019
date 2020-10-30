package com.crashlytics.android.core;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.common.ResponseParser;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.io.File;
import java.util.Map.Entry;

class DefaultCreateReportSpiCall extends AbstractSpiCall implements CreateReportSpiCall {
    public DefaultCreateReportSpiCall(Kit kit, String str, String str2, HttpRequestFactory httpRequestFactory) {
        super(kit, str, str2, httpRequestFactory, HttpMethod.POST);
    }

    public boolean a(CreateReportRequest createReportRequest) {
        HttpRequest a = a(a(getHttpRequest(), createReportRequest), createReportRequest.b);
        Logger logger = Fabric.getLogger();
        String str = CrashlyticsCore.TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("Sending report to: ");
        sb.append(getUrl());
        logger.d(str, sb.toString());
        int code = a.code();
        Logger logger2 = Fabric.getLogger();
        String str2 = CrashlyticsCore.TAG;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Create report request ID: ");
        sb2.append(a.header(AbstractSpiCall.HEADER_REQUEST_ID));
        logger2.d(str2, sb2.toString());
        Logger logger3 = Fabric.getLogger();
        String str3 = CrashlyticsCore.TAG;
        StringBuilder sb3 = new StringBuilder();
        sb3.append("Result was: ");
        sb3.append(code);
        logger3.d(str3, sb3.toString());
        return ResponseParser.parse(code) == 0;
    }

    private HttpRequest a(HttpRequest httpRequest, CreateReportRequest createReportRequest) {
        HttpRequest header = httpRequest.header(AbstractSpiCall.HEADER_API_KEY, createReportRequest.a).header(AbstractSpiCall.HEADER_CLIENT_TYPE, AbstractSpiCall.ANDROID_CLIENT_TYPE).header(AbstractSpiCall.HEADER_CLIENT_VERSION, this.kit.getVersion());
        for (Entry header2 : createReportRequest.b.e().entrySet()) {
            header = header.header(header2);
        }
        return header;
    }

    private HttpRequest a(HttpRequest httpRequest, Report report) {
        File[] d;
        httpRequest.part("report[identifier]", report.b());
        if (report.d().length == 1) {
            Logger logger = Fabric.getLogger();
            String str = CrashlyticsCore.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Adding single file ");
            sb.append(report.a());
            sb.append(" to report ");
            sb.append(report.b());
            logger.d(str, sb.toString());
            return httpRequest.part("report[file]", report.a(), "application/octet-stream", report.c());
        }
        int i = 0;
        for (File file : report.d()) {
            Logger logger2 = Fabric.getLogger();
            String str2 = CrashlyticsCore.TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Adding file ");
            sb2.append(file.getName());
            sb2.append(" to report ");
            sb2.append(report.b());
            logger2.d(str2, sb2.toString());
            StringBuilder sb3 = new StringBuilder();
            sb3.append("report[file");
            sb3.append(i);
            sb3.append("]");
            httpRequest.part(sb3.toString(), file.getName(), "application/octet-stream", file);
            i++;
        }
        return httpRequest;
    }
}
