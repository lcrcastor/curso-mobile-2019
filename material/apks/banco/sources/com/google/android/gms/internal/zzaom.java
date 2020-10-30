package com.google.android.gms.internal;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public final class zzaom {
    public zzaoh zza(Reader reader) {
        try {
            zzapy zzapy = new zzapy(reader);
            zzaoh zzh = zzh(zzapy);
            if (zzh.aV() || zzapy.bn() == zzapz.END_DOCUMENT) {
                return zzh;
            }
            throw new zzaoq("Did not consume the entire document.");
        } catch (zzaqb e) {
            throw new zzaoq((Throwable) e);
        } catch (IOException e2) {
            throw new zzaoi((Throwable) e2);
        } catch (NumberFormatException e3) {
            throw new zzaoq((Throwable) e3);
        }
    }

    public zzaoh zzh(zzapy zzapy) {
        boolean isLenient = zzapy.isLenient();
        zzapy.setLenient(true);
        try {
            zzaoh zzh = zzapi.zzh(zzapy);
            zzapy.setLenient(isLenient);
            return zzh;
        } catch (StackOverflowError e) {
            String valueOf = String.valueOf(zzapy);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 36);
            sb.append("Failed parsing JSON source: ");
            sb.append(valueOf);
            sb.append(" to Json");
            throw new zzaol(sb.toString(), e);
        } catch (OutOfMemoryError e2) {
            String valueOf2 = String.valueOf(zzapy);
            StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 36);
            sb2.append("Failed parsing JSON source: ");
            sb2.append(valueOf2);
            sb2.append(" to Json");
            throw new zzaol(sb2.toString(), e2);
        } catch (Throwable th) {
            zzapy.setLenient(isLenient);
            throw th;
        }
    }

    public zzaoh zzuq(String str) {
        return zza(new StringReader(str));
    }
}
