package ar.com.santander.rio.mbanking.inject;

public final class GraphRetriever {
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x002c, code lost:
        throw new java.lang.ClassCastException(r2.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:?, code lost:
        r2 = new java.lang.StringBuilder();
        r2.append(r3.getClass().getName());
        r2.append("  must implement GraphProvider interface!");
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:8:0x000e */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized dagger.ObjectGraph from(java.lang.Object r3) {
        /*
            java.lang.Class<ar.com.santander.rio.mbanking.inject.GraphRetriever> r0 = ar.com.santander.rio.mbanking.inject.GraphRetriever.class
            monitor-enter(r0)
            r1 = r3
            ar.com.santander.rio.mbanking.inject.GraphProvider r1 = (ar.com.santander.rio.mbanking.inject.GraphProvider) r1     // Catch:{ ClassCastException -> 0x000e }
            dagger.ObjectGraph r1 = r1.getGraph()     // Catch:{ ClassCastException -> 0x000e }
            monitor-exit(r0)
            return r1
        L_0x000c:
            r3 = move-exception
            goto L_0x002d
        L_0x000e:
            java.lang.ClassCastException r1 = new java.lang.ClassCastException     // Catch:{ all -> 0x000c }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x000c }
            r2.<init>()     // Catch:{ all -> 0x000c }
            java.lang.Class r3 = r3.getClass()     // Catch:{ all -> 0x000c }
            java.lang.String r3 = r3.getName()     // Catch:{ all -> 0x000c }
            r2.append(r3)     // Catch:{ all -> 0x000c }
            java.lang.String r3 = "  must implement GraphProvider interface!"
            r2.append(r3)     // Catch:{ all -> 0x000c }
            java.lang.String r3 = r2.toString()     // Catch:{ all -> 0x000c }
            r1.<init>(r3)     // Catch:{ all -> 0x000c }
            throw r1     // Catch:{ all -> 0x000c }
        L_0x002d:
            monitor-exit(r0)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.inject.GraphRetriever.from(java.lang.Object):dagger.ObjectGraph");
    }

    private GraphRetriever() {
    }
}
