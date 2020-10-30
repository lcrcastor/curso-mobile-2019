package com.google.android.gms.internal;

import com.google.android.gms.common.ConnectionResult;

public class zzqi extends zzqd {
    /* JADX WARNING: type inference failed for: r0v0, types: [com.google.android.gms.internal.zzqt, com.google.android.gms.common.util.zza] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v0, types: [com.google.android.gms.internal.zzqt, com.google.android.gms.common.util.zza]
      assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY]]
      uses: [com.google.android.gms.common.util.zza, com.google.android.gms.internal.zzqt]
      mth insns count: 11
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.util.ArrayList.forEach(ArrayList.java:1249)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.util.ArrayList.forEach(ArrayList.java:1249)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onStop() {
        /*
            r3 = this;
            super.onStop()
            r0 = 0
            java.util.Iterator r1 = r0.iterator()
        L_0x0008:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x0018
            java.lang.Object r2 = r1.next()
            com.google.android.gms.common.api.zzd r2 = (com.google.android.gms.common.api.zzd) r2
            r2.release()
            goto L_0x0008
        L_0x0018:
            r0.clear()
            r0.zza(r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzqi.onStop():void");
    }

    /* access modifiers changed from: protected */
    public void zza(ConnectionResult connectionResult, int i) {
        zzqt zzqt = null;
        zzqt.zza(connectionResult, i);
    }

    /* access modifiers changed from: protected */
    public void zzaqk() {
        zzqt zzqt = null;
        zzqt.zzaqk();
    }
}
