package com.squareup.okhttp.internal;

import com.squareup.okhttp.Route;
import java.util.LinkedHashSet;
import java.util.Set;

public final class RouteDatabase {
    private final Set<Route> a = new LinkedHashSet();

    public synchronized void failed(Route route) {
        this.a.add(route);
    }

    public synchronized void connected(Route route) {
        this.a.remove(route);
    }

    public synchronized boolean shouldPostpone(Route route) {
        return this.a.contains(route);
    }

    public synchronized int failedRoutesCount() {
        return this.a.size();
    }
}
