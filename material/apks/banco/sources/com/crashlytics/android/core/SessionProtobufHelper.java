package com.crashlytics.android.core;

import android.app.ActivityManager.RunningAppProcessInfo;
import android.os.Build.VERSION;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.IdManager.DeviceIdentifierType;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

class SessionProtobufHelper {
    private static final ByteString a = ByteString.a("0");
    private static final ByteString b = ByteString.a("Unity");

    private SessionProtobufHelper() {
    }

    public static void a(CodedOutputStream codedOutputStream, String str, String str2, long j) {
        codedOutputStream.a(1, ByteString.a(str2));
        codedOutputStream.a(2, ByteString.a(str));
        codedOutputStream.a(3, j);
    }

    public static void a(CodedOutputStream codedOutputStream, String str, String str2, String str3, String str4, String str5, int i, String str6) {
        ByteString a2 = ByteString.a(str);
        ByteString a3 = ByteString.a(str2);
        ByteString a4 = ByteString.a(str3);
        ByteString a5 = ByteString.a(str4);
        ByteString a6 = ByteString.a(str5);
        ByteString a7 = str6 != null ? ByteString.a(str6) : null;
        codedOutputStream.g(7, 2);
        codedOutputStream.k(a(a2, a3, a4, a5, a6, i, a7));
        codedOutputStream.a(1, a2);
        codedOutputStream.a(2, a4);
        codedOutputStream.a(3, a5);
        codedOutputStream.g(5, 2);
        codedOutputStream.k(a(a3));
        codedOutputStream.a(1, a3);
        codedOutputStream.a(6, a6);
        if (a7 != null) {
            codedOutputStream.a(8, b);
            codedOutputStream.a(9, a7);
        }
        codedOutputStream.b(10, i);
    }

    public static void a(CodedOutputStream codedOutputStream, boolean z) {
        ByteString a2 = ByteString.a(VERSION.RELEASE);
        ByteString a3 = ByteString.a(VERSION.CODENAME);
        codedOutputStream.g(8, 2);
        codedOutputStream.k(a(a2, a3, z));
        codedOutputStream.b(1, 3);
        codedOutputStream.a(2, a2);
        codedOutputStream.a(3, a3);
        codedOutputStream.a(4, z);
    }

    public static void a(CodedOutputStream codedOutputStream, String str, int i, String str2, int i2, long j, long j2, boolean z, Map<DeviceIdentifierType, String> map, int i3, String str3, String str4) {
        CodedOutputStream codedOutputStream2 = codedOutputStream;
        ByteString a2 = ByteString.a(str);
        ByteString a3 = a(str2);
        ByteString a4 = a(str4);
        ByteString a5 = a(str3);
        codedOutputStream2.g(9, 2);
        ByteString byteString = a5;
        ByteString byteString2 = a4;
        codedOutputStream2.k(a(i, a2, a3, i2, j, j2, z, map, i3, a5, a4));
        codedOutputStream2.a(1, a2);
        codedOutputStream2.b(3, i);
        codedOutputStream2.a(4, a3);
        codedOutputStream2.a(5, i2);
        codedOutputStream2.a(6, j);
        codedOutputStream2.a(7, j2);
        codedOutputStream2.a(10, z);
        for (Entry entry : map.entrySet()) {
            codedOutputStream2.g(11, 2);
            codedOutputStream2.k(a((DeviceIdentifierType) entry.getKey(), (String) entry.getValue()));
            codedOutputStream2.b(1, ((DeviceIdentifierType) entry.getKey()).protobufIndex);
            codedOutputStream2.a(2, ByteString.a((String) entry.getValue()));
        }
        codedOutputStream2.a(12, i3);
        ByteString byteString3 = byteString;
        if (byteString3 != null) {
            codedOutputStream2.a(13, byteString3);
        }
        ByteString byteString4 = byteString2;
        if (byteString4 != null) {
            codedOutputStream2.a(14, byteString4);
        }
    }

    public static void a(CodedOutputStream codedOutputStream, String str, String str2, String str3) {
        if (str == null) {
            str = "";
        }
        ByteString a2 = ByteString.a(str);
        ByteString a3 = a(str2);
        ByteString a4 = a(str3);
        int b2 = CodedOutputStream.b(1, a2) + 0;
        if (str2 != null) {
            b2 += CodedOutputStream.b(2, a3);
        }
        if (str3 != null) {
            b2 += CodedOutputStream.b(3, a4);
        }
        codedOutputStream.g(6, 2);
        codedOutputStream.k(b2);
        codedOutputStream.a(1, a2);
        if (str2 != null) {
            codedOutputStream.a(2, a3);
        }
        if (str3 != null) {
            codedOutputStream.a(3, a4);
        }
    }

    public static void a(CodedOutputStream codedOutputStream, long j, String str, TrimmedThrowableData trimmedThrowableData, Thread thread, StackTraceElement[] stackTraceElementArr, Thread[] threadArr, List<StackTraceElement[]> list, Map<String, String> map, LogFileManager logFileManager, RunningAppProcessInfo runningAppProcessInfo, int i, String str2, String str3, Float f, int i2, boolean z, long j2, long j3) {
        ByteString a2;
        CodedOutputStream codedOutputStream2 = codedOutputStream;
        String str4 = str3;
        ByteString a3 = ByteString.a(str2);
        if (str4 == null) {
            a2 = null;
        } else {
            a2 = ByteString.a(str4.replace("-", ""));
        }
        ByteString byteString = a2;
        ByteString a4 = logFileManager.a();
        if (a4 == null) {
            Fabric.getLogger().d(CrashlyticsCore.TAG, "No log data to include with this event.");
        }
        logFileManager.b();
        codedOutputStream2.g(10, 2);
        codedOutputStream2.k(a(j, str, trimmedThrowableData, thread, stackTraceElementArr, threadArr, list, 8, map, runningAppProcessInfo, i, a3, byteString, f, i2, z, j2, j3, a4));
        codedOutputStream2.a(1, j);
        codedOutputStream2.a(2, ByteString.a(str));
        CodedOutputStream codedOutputStream3 = codedOutputStream2;
        ByteString byteString2 = a4;
        a(codedOutputStream3, trimmedThrowableData, thread, stackTraceElementArr, threadArr, list, 8, a3, byteString, map, runningAppProcessInfo, i);
        a(codedOutputStream3, f, i2, z, i, j2, j3);
        a(codedOutputStream2, byteString2);
    }

    private static void a(CodedOutputStream codedOutputStream, TrimmedThrowableData trimmedThrowableData, Thread thread, StackTraceElement[] stackTraceElementArr, Thread[] threadArr, List<StackTraceElement[]> list, int i, ByteString byteString, ByteString byteString2, Map<String, String> map, RunningAppProcessInfo runningAppProcessInfo, int i2) {
        codedOutputStream.g(3, 2);
        codedOutputStream.k(a(trimmedThrowableData, thread, stackTraceElementArr, threadArr, list, i, byteString, byteString2, map, runningAppProcessInfo, i2));
        a(codedOutputStream, trimmedThrowableData, thread, stackTraceElementArr, threadArr, list, i, byteString, byteString2);
        if (map != null && !map.isEmpty()) {
            a(codedOutputStream, map);
        }
        if (runningAppProcessInfo != null) {
            codedOutputStream.a(3, runningAppProcessInfo.importance != 100);
        }
        codedOutputStream.a(4, i2);
    }

    private static void a(CodedOutputStream codedOutputStream, TrimmedThrowableData trimmedThrowableData, Thread thread, StackTraceElement[] stackTraceElementArr, Thread[] threadArr, List<StackTraceElement[]> list, int i, ByteString byteString, ByteString byteString2) {
        codedOutputStream.g(1, 2);
        codedOutputStream.k(a(trimmedThrowableData, thread, stackTraceElementArr, threadArr, list, i, byteString, byteString2));
        a(codedOutputStream, thread, stackTraceElementArr, 4, true);
        int length = threadArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            a(codedOutputStream, threadArr[i2], (StackTraceElement[]) list.get(i2), 0, false);
        }
        a(codedOutputStream, trimmedThrowableData, 1, i, 2);
        codedOutputStream.g(3, 2);
        codedOutputStream.k(a());
        codedOutputStream.a(1, a);
        codedOutputStream.a(2, a);
        codedOutputStream.a(3, 0);
        codedOutputStream.g(4, 2);
        codedOutputStream.k(a(byteString, byteString2));
        codedOutputStream.a(1, 0);
        codedOutputStream.a(2, 0);
        codedOutputStream.a(3, byteString);
        if (byteString2 != null) {
            codedOutputStream.a(4, byteString2);
        }
    }

    private static void a(CodedOutputStream codedOutputStream, Map<String, String> map) {
        for (Entry entry : map.entrySet()) {
            codedOutputStream.g(2, 2);
            codedOutputStream.k(a((String) entry.getKey(), (String) entry.getValue()));
            codedOutputStream.a(1, ByteString.a((String) entry.getKey()));
            String str = (String) entry.getValue();
            if (str == null) {
                str = "";
            }
            codedOutputStream.a(2, ByteString.a(str));
        }
    }

    private static void a(CodedOutputStream codedOutputStream, TrimmedThrowableData trimmedThrowableData, int i, int i2, int i3) {
        codedOutputStream.g(i3, 2);
        codedOutputStream.k(a(trimmedThrowableData, 1, i2));
        codedOutputStream.a(1, ByteString.a(trimmedThrowableData.b));
        String str = trimmedThrowableData.a;
        if (str != null) {
            codedOutputStream.a(3, ByteString.a(str));
        }
        int i4 = 0;
        for (StackTraceElement a2 : trimmedThrowableData.c) {
            a(codedOutputStream, 4, a2, true);
        }
        TrimmedThrowableData trimmedThrowableData2 = trimmedThrowableData.d;
        if (trimmedThrowableData2 == null) {
            return;
        }
        if (i < i2) {
            a(codedOutputStream, trimmedThrowableData2, i + 1, i2, 6);
            return;
        }
        while (trimmedThrowableData2 != null) {
            trimmedThrowableData2 = trimmedThrowableData2.d;
            i4++;
        }
        codedOutputStream.a(7, i4);
    }

    private static void a(CodedOutputStream codedOutputStream, Thread thread, StackTraceElement[] stackTraceElementArr, int i, boolean z) {
        codedOutputStream.g(1, 2);
        codedOutputStream.k(a(thread, stackTraceElementArr, i, z));
        codedOutputStream.a(1, ByteString.a(thread.getName()));
        codedOutputStream.a(2, i);
        for (StackTraceElement a2 : stackTraceElementArr) {
            a(codedOutputStream, 3, a2, z);
        }
    }

    private static void a(CodedOutputStream codedOutputStream, int i, StackTraceElement stackTraceElement, boolean z) {
        codedOutputStream.g(i, 2);
        codedOutputStream.k(a(stackTraceElement, z));
        if (stackTraceElement.isNativeMethod()) {
            codedOutputStream.a(1, (long) Math.max(stackTraceElement.getLineNumber(), 0));
        } else {
            codedOutputStream.a(1, 0);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(stackTraceElement.getClassName());
        sb.append(".");
        sb.append(stackTraceElement.getMethodName());
        codedOutputStream.a(2, ByteString.a(sb.toString()));
        if (stackTraceElement.getFileName() != null) {
            codedOutputStream.a(3, ByteString.a(stackTraceElement.getFileName()));
        }
        int i2 = 4;
        if (!stackTraceElement.isNativeMethod() && stackTraceElement.getLineNumber() > 0) {
            codedOutputStream.a(4, (long) stackTraceElement.getLineNumber());
        }
        if (!z) {
            i2 = 0;
        }
        codedOutputStream.a(5, i2);
    }

    private static void a(CodedOutputStream codedOutputStream, Float f, int i, boolean z, int i2, long j, long j2) {
        codedOutputStream.g(5, 2);
        codedOutputStream.k(a(f, i, z, i2, j, j2));
        if (f != null) {
            codedOutputStream.a(1, f.floatValue());
        }
        codedOutputStream.c(2, i);
        codedOutputStream.a(3, z);
        codedOutputStream.a(4, i2);
        codedOutputStream.a(5, j);
        codedOutputStream.a(6, j2);
    }

    private static void a(CodedOutputStream codedOutputStream, ByteString byteString) {
        if (byteString != null) {
            codedOutputStream.g(6, 2);
            codedOutputStream.k(b(byteString));
            codedOutputStream.a(1, byteString);
        }
    }

    private static int a(ByteString byteString, ByteString byteString2, ByteString byteString3, ByteString byteString4, ByteString byteString5, int i, ByteString byteString6) {
        int b2 = CodedOutputStream.b(1, byteString) + 0 + CodedOutputStream.b(2, byteString3) + CodedOutputStream.b(3, byteString4);
        int a2 = a(byteString2);
        int j = b2 + CodedOutputStream.j(5) + CodedOutputStream.l(a2) + a2 + CodedOutputStream.b(6, byteString5);
        if (byteString6 != null) {
            j = j + CodedOutputStream.b(8, b) + CodedOutputStream.b(9, byteString6);
        }
        return j + CodedOutputStream.e(10, i);
    }

    private static int a(ByteString byteString) {
        return CodedOutputStream.b(1, byteString) + 0;
    }

    private static int a(ByteString byteString, ByteString byteString2, boolean z) {
        return CodedOutputStream.e(1, 3) + 0 + CodedOutputStream.b(2, byteString) + CodedOutputStream.b(3, byteString2) + CodedOutputStream.b(4, z);
    }

    private static int a(DeviceIdentifierType deviceIdentifierType, String str) {
        return CodedOutputStream.e(1, deviceIdentifierType.protobufIndex) + CodedOutputStream.b(2, ByteString.a(str));
    }

    private static int a(int i, ByteString byteString, ByteString byteString2, int i2, long j, long j2, boolean z, Map<DeviceIdentifierType, String> map, int i3, ByteString byteString3, ByteString byteString4) {
        int i4;
        int i5;
        int i6 = 0;
        int b2 = CodedOutputStream.b(1, byteString) + 0 + CodedOutputStream.e(3, i);
        if (byteString2 == null) {
            i4 = 0;
        } else {
            i4 = CodedOutputStream.b(4, byteString2);
        }
        int d = b2 + i4 + CodedOutputStream.d(5, i2) + CodedOutputStream.b(6, j) + CodedOutputStream.b(7, j2) + CodedOutputStream.b(10, z);
        if (map != null) {
            for (Entry entry : map.entrySet()) {
                int a2 = a((DeviceIdentifierType) entry.getKey(), (String) entry.getValue());
                d += CodedOutputStream.j(11) + CodedOutputStream.l(a2) + a2;
            }
        }
        int d2 = d + CodedOutputStream.d(12, i3);
        if (byteString3 == null) {
            i5 = 0;
        } else {
            i5 = CodedOutputStream.b(13, byteString3);
        }
        int i7 = d2 + i5;
        if (byteString4 != null) {
            i6 = CodedOutputStream.b(14, byteString4);
        }
        return i7 + i6;
    }

    private static int a(ByteString byteString, ByteString byteString2) {
        int b2 = CodedOutputStream.b(1, 0) + 0 + CodedOutputStream.b(2, 0) + CodedOutputStream.b(3, byteString);
        return byteString2 != null ? b2 + CodedOutputStream.b(4, byteString2) : b2;
    }

    private static int a(long j, String str, TrimmedThrowableData trimmedThrowableData, Thread thread, StackTraceElement[] stackTraceElementArr, Thread[] threadArr, List<StackTraceElement[]> list, int i, Map<String, String> map, RunningAppProcessInfo runningAppProcessInfo, int i2, ByteString byteString, ByteString byteString2, Float f, int i3, boolean z, long j2, long j3, ByteString byteString3) {
        int b2 = CodedOutputStream.b(1, j) + 0 + CodedOutputStream.b(2, ByteString.a(str));
        int a2 = a(trimmedThrowableData, thread, stackTraceElementArr, threadArr, list, i, byteString, byteString2, map, runningAppProcessInfo, i2);
        int j4 = b2 + CodedOutputStream.j(3) + CodedOutputStream.l(a2) + a2;
        int a3 = a(f, i3, z, i2, j2, j3);
        int j5 = j4 + CodedOutputStream.j(5) + CodedOutputStream.l(a3) + a3;
        if (byteString3 == null) {
            return j5;
        }
        int b3 = b(byteString3);
        return j5 + CodedOutputStream.j(6) + CodedOutputStream.l(b3) + b3;
    }

    private static int a(TrimmedThrowableData trimmedThrowableData, Thread thread, StackTraceElement[] stackTraceElementArr, Thread[] threadArr, List<StackTraceElement[]> list, int i, ByteString byteString, ByteString byteString2, Map<String, String> map, RunningAppProcessInfo runningAppProcessInfo, int i2) {
        int a2 = a(trimmedThrowableData, thread, stackTraceElementArr, threadArr, list, i, byteString, byteString2);
        int j = CodedOutputStream.j(1) + CodedOutputStream.l(a2) + a2;
        boolean z = false;
        int i3 = j + 0;
        if (map != null) {
            for (Entry entry : map.entrySet()) {
                int a3 = a((String) entry.getKey(), (String) entry.getValue());
                i3 += CodedOutputStream.j(2) + CodedOutputStream.l(a3) + a3;
            }
        }
        if (runningAppProcessInfo != null) {
            if (runningAppProcessInfo.importance != 100) {
                z = true;
            }
            i3 += CodedOutputStream.b(3, z);
        }
        return i3 + CodedOutputStream.d(4, i2);
    }

    private static int a(TrimmedThrowableData trimmedThrowableData, Thread thread, StackTraceElement[] stackTraceElementArr, Thread[] threadArr, List<StackTraceElement[]> list, int i, ByteString byteString, ByteString byteString2) {
        int a2 = a(thread, stackTraceElementArr, 4, true);
        int j = CodedOutputStream.j(1) + CodedOutputStream.l(a2) + a2 + 0;
        int length = threadArr.length;
        int i2 = j;
        for (int i3 = 0; i3 < length; i3++) {
            int a3 = a(threadArr[i3], (StackTraceElement[]) list.get(i3), 0, false);
            i2 += CodedOutputStream.j(1) + CodedOutputStream.l(a3) + a3;
        }
        int a4 = a(trimmedThrowableData, 1, i);
        int j2 = i2 + CodedOutputStream.j(2) + CodedOutputStream.l(a4) + a4;
        int a5 = a();
        int j3 = j2 + CodedOutputStream.j(3) + CodedOutputStream.l(a5) + a5;
        int a6 = a(byteString, byteString2);
        return j3 + CodedOutputStream.j(3) + CodedOutputStream.l(a6) + a6;
    }

    private static int a(String str, String str2) {
        int b2 = CodedOutputStream.b(1, ByteString.a(str));
        if (str2 == null) {
            str2 = "";
        }
        return b2 + CodedOutputStream.b(2, ByteString.a(str2));
    }

    private static int a(Float f, int i, boolean z, int i2, long j, long j2) {
        int i3 = 0;
        if (f != null) {
            i3 = 0 + CodedOutputStream.b(1, f.floatValue());
        }
        return i3 + CodedOutputStream.f(2, i) + CodedOutputStream.b(3, z) + CodedOutputStream.d(4, i2) + CodedOutputStream.b(5, j) + CodedOutputStream.b(6, j2);
    }

    private static int b(ByteString byteString) {
        return CodedOutputStream.b(1, byteString);
    }

    private static int a(TrimmedThrowableData trimmedThrowableData, int i, int i2) {
        int i3 = 0;
        int b2 = CodedOutputStream.b(1, ByteString.a(trimmedThrowableData.b)) + 0;
        String str = trimmedThrowableData.a;
        if (str != null) {
            b2 += CodedOutputStream.b(3, ByteString.a(str));
        }
        int i4 = b2;
        for (StackTraceElement a2 : trimmedThrowableData.c) {
            int a3 = a(a2, true);
            i4 += CodedOutputStream.j(4) + CodedOutputStream.l(a3) + a3;
        }
        TrimmedThrowableData trimmedThrowableData2 = trimmedThrowableData.d;
        if (trimmedThrowableData2 == null) {
            return i4;
        }
        if (i < i2) {
            int a4 = a(trimmedThrowableData2, i + 1, i2);
            return i4 + CodedOutputStream.j(6) + CodedOutputStream.l(a4) + a4;
        }
        while (trimmedThrowableData2 != null) {
            trimmedThrowableData2 = trimmedThrowableData2.d;
            i3++;
        }
        return i4 + CodedOutputStream.d(7, i3);
    }

    private static int a() {
        return CodedOutputStream.b(1, a) + 0 + CodedOutputStream.b(2, a) + CodedOutputStream.b(3, 0);
    }

    private static int a(StackTraceElement stackTraceElement, boolean z) {
        int i;
        int i2 = 0;
        if (stackTraceElement.isNativeMethod()) {
            i = CodedOutputStream.b(1, (long) Math.max(stackTraceElement.getLineNumber(), 0)) + 0;
        } else {
            i = CodedOutputStream.b(1, 0) + 0;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(stackTraceElement.getClassName());
        sb.append(".");
        sb.append(stackTraceElement.getMethodName());
        int b2 = i + CodedOutputStream.b(2, ByteString.a(sb.toString()));
        if (stackTraceElement.getFileName() != null) {
            b2 += CodedOutputStream.b(3, ByteString.a(stackTraceElement.getFileName()));
        }
        if (!stackTraceElement.isNativeMethod() && stackTraceElement.getLineNumber() > 0) {
            b2 += CodedOutputStream.b(4, (long) stackTraceElement.getLineNumber());
        }
        if (z) {
            i2 = 2;
        }
        return b2 + CodedOutputStream.d(5, i2);
    }

    private static int a(Thread thread, StackTraceElement[] stackTraceElementArr, int i, boolean z) {
        int b2 = CodedOutputStream.b(1, ByteString.a(thread.getName())) + CodedOutputStream.d(2, i);
        for (StackTraceElement a2 : stackTraceElementArr) {
            int a3 = a(a2, z);
            b2 += CodedOutputStream.j(3) + CodedOutputStream.l(a3) + a3;
        }
        return b2;
    }

    private static ByteString a(String str) {
        if (str == null) {
            return null;
        }
        return ByteString.a(str);
    }
}
