package com.crashlytics.android.core;

import com.crashlytics.android.core.internal.models.BinaryImageData;
import com.crashlytics.android.core.internal.models.CustomAttributeData;
import com.crashlytics.android.core.internal.models.DeviceData;
import com.crashlytics.android.core.internal.models.SessionEventData;
import com.crashlytics.android.core.internal.models.SignalData;
import com.crashlytics.android.core.internal.models.ThreadData;
import com.crashlytics.android.core.internal.models.ThreadData.FrameData;
import io.fabric.sdk.android.Fabric;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

class NativeCrashWriter {
    private static final SignalData a = new SignalData("", "", 0);
    /* access modifiers changed from: private */
    public static final ProtobufMessage[] b = new ProtobufMessage[0];
    private static final ThreadMessage[] c = new ThreadMessage[0];
    private static final FrameMessage[] d = new FrameMessage[0];
    private static final BinaryImageMessage[] e = new BinaryImageMessage[0];
    private static final CustomAttributeMessage[] f = new CustomAttributeMessage[0];

    static final class ApplicationMessage extends ProtobufMessage {
        public ApplicationMessage(ExecutionMessage executionMessage, RepeatedMessage repeatedMessage) {
            super(3, executionMessage, repeatedMessage);
        }
    }

    static final class BinaryImageMessage extends ProtobufMessage {
        private final long a;
        private final long b;
        private final String c;
        private final String d;

        public BinaryImageMessage(BinaryImageData binaryImageData) {
            super(4, new ProtobufMessage[0]);
            this.a = binaryImageData.baseAddress;
            this.b = binaryImageData.size;
            this.c = binaryImageData.path;
            this.d = binaryImageData.f271id;
        }

        public int a() {
            int b2 = CodedOutputStream.b(1, this.a);
            return CodedOutputStream.b(3, ByteString.a(this.c)) + b2 + CodedOutputStream.b(2, this.b) + CodedOutputStream.b(4, ByteString.a(this.d));
        }

        public void a(CodedOutputStream codedOutputStream) {
            codedOutputStream.a(1, this.a);
            codedOutputStream.a(2, this.b);
            codedOutputStream.a(3, ByteString.a(this.c));
            codedOutputStream.a(4, ByteString.a(this.d));
        }
    }

    static final class CustomAttributeMessage extends ProtobufMessage {
        private final String a;
        private final String b;

        public CustomAttributeMessage(CustomAttributeData customAttributeData) {
            super(2, new ProtobufMessage[0]);
            this.a = customAttributeData.key;
            this.b = customAttributeData.value;
        }

        public int a() {
            return CodedOutputStream.b(1, ByteString.a(this.a)) + CodedOutputStream.b(2, ByteString.a(this.b == null ? "" : this.b));
        }

        public void a(CodedOutputStream codedOutputStream) {
            codedOutputStream.a(1, ByteString.a(this.a));
            codedOutputStream.a(2, ByteString.a(this.b == null ? "" : this.b));
        }
    }

    static final class DeviceMessage extends ProtobufMessage {
        private final float a;
        private final int b;
        private final boolean c;
        private final int d;
        private final long e;
        private final long f;

        public DeviceMessage(float f2, int i, boolean z, int i2, long j, long j2) {
            super(5, new ProtobufMessage[0]);
            this.a = f2;
            this.b = i;
            this.c = z;
            this.d = i2;
            this.e = j;
            this.f = j2;
        }

        public int a() {
            return CodedOutputStream.b(1, this.a) + 0 + CodedOutputStream.f(2, this.b) + CodedOutputStream.b(3, this.c) + CodedOutputStream.d(4, this.d) + CodedOutputStream.b(5, this.e) + CodedOutputStream.b(6, this.f);
        }

        public void a(CodedOutputStream codedOutputStream) {
            codedOutputStream.a(1, this.a);
            codedOutputStream.c(2, this.b);
            codedOutputStream.a(3, this.c);
            codedOutputStream.a(4, this.d);
            codedOutputStream.a(5, this.e);
            codedOutputStream.a(6, this.f);
        }
    }

    static final class EventMessage extends ProtobufMessage {
        private final long a;
        private final String b;

        public EventMessage(long j, String str, ProtobufMessage... protobufMessageArr) {
            super(10, protobufMessageArr);
            this.a = j;
            this.b = str;
        }

        public int a() {
            return CodedOutputStream.b(1, this.a) + CodedOutputStream.b(2, ByteString.a(this.b));
        }

        public void a(CodedOutputStream codedOutputStream) {
            codedOutputStream.a(1, this.a);
            codedOutputStream.a(2, ByteString.a(this.b));
        }
    }

    static final class ExecutionMessage extends ProtobufMessage {
        public ExecutionMessage(SignalMessage signalMessage, RepeatedMessage repeatedMessage, RepeatedMessage repeatedMessage2) {
            super(1, repeatedMessage, signalMessage, repeatedMessage2);
        }
    }

    static final class FrameMessage extends ProtobufMessage {
        private final long a;
        private final String b;
        private final String c;
        private final long d;
        private final int e;

        public FrameMessage(FrameData frameData) {
            super(3, new ProtobufMessage[0]);
            this.a = frameData.address;
            this.b = frameData.symbol;
            this.c = frameData.file;
            this.d = frameData.offset;
            this.e = frameData.importance;
        }

        public int a() {
            return CodedOutputStream.b(1, this.a) + CodedOutputStream.b(2, ByteString.a(this.b)) + CodedOutputStream.b(3, ByteString.a(this.c)) + CodedOutputStream.b(4, this.d) + CodedOutputStream.d(5, this.e);
        }

        public void a(CodedOutputStream codedOutputStream) {
            codedOutputStream.a(1, this.a);
            codedOutputStream.a(2, ByteString.a(this.b));
            codedOutputStream.a(3, ByteString.a(this.c));
            codedOutputStream.a(4, this.d);
            codedOutputStream.a(5, this.e);
        }
    }

    static final class LogMessage extends ProtobufMessage {
        ByteString a;

        public LogMessage(ByteString byteString) {
            super(6, new ProtobufMessage[0]);
            this.a = byteString;
        }

        public int a() {
            return CodedOutputStream.b(1, this.a);
        }

        public void a(CodedOutputStream codedOutputStream) {
            codedOutputStream.a(1, this.a);
        }
    }

    static final class NullMessage extends ProtobufMessage {
        public int b() {
            return 0;
        }

        public void b(CodedOutputStream codedOutputStream) {
        }

        public NullMessage() {
            super(0, new ProtobufMessage[0]);
        }
    }

    static abstract class ProtobufMessage {
        private final int a;
        private final ProtobufMessage[] b;

        public int a() {
            return 0;
        }

        public void a(CodedOutputStream codedOutputStream) {
        }

        public ProtobufMessage(int i, ProtobufMessage... protobufMessageArr) {
            this.a = i;
            if (protobufMessageArr == null) {
                protobufMessageArr = NativeCrashWriter.b;
            }
            this.b = protobufMessageArr;
        }

        public int b() {
            int c = c();
            return c + CodedOutputStream.l(c) + CodedOutputStream.j(this.a);
        }

        public int c() {
            int a2 = a();
            for (ProtobufMessage b2 : this.b) {
                a2 += b2.b();
            }
            return a2;
        }

        public void b(CodedOutputStream codedOutputStream) {
            codedOutputStream.g(this.a, 2);
            codedOutputStream.k(c());
            a(codedOutputStream);
            for (ProtobufMessage b2 : this.b) {
                b2.b(codedOutputStream);
            }
        }
    }

    static final class RepeatedMessage extends ProtobufMessage {
        private final ProtobufMessage[] a;

        public RepeatedMessage(ProtobufMessage... protobufMessageArr) {
            super(0, new ProtobufMessage[0]);
            this.a = protobufMessageArr;
        }

        public void b(CodedOutputStream codedOutputStream) {
            for (ProtobufMessage b : this.a) {
                b.b(codedOutputStream);
            }
        }

        public int b() {
            int i = 0;
            for (ProtobufMessage b : this.a) {
                i += b.b();
            }
            return i;
        }
    }

    static final class SignalMessage extends ProtobufMessage {
        private final String a;
        private final String b;
        private final long c;

        public SignalMessage(SignalData signalData) {
            super(3, new ProtobufMessage[0]);
            this.a = signalData.name;
            this.b = signalData.code;
            this.c = signalData.faultAddress;
        }

        public int a() {
            return CodedOutputStream.b(1, ByteString.a(this.a)) + CodedOutputStream.b(2, ByteString.a(this.b)) + CodedOutputStream.b(3, this.c);
        }

        public void a(CodedOutputStream codedOutputStream) {
            codedOutputStream.a(1, ByteString.a(this.a));
            codedOutputStream.a(2, ByteString.a(this.b));
            codedOutputStream.a(3, this.c);
        }
    }

    static final class ThreadMessage extends ProtobufMessage {
        private final String a;
        private final int b;

        public ThreadMessage(ThreadData threadData, RepeatedMessage repeatedMessage) {
            super(1, repeatedMessage);
            this.a = threadData.name;
            this.b = threadData.importance;
        }

        public int a() {
            return CodedOutputStream.d(2, this.b) + (d() ? CodedOutputStream.b(1, ByteString.a(this.a)) : 0);
        }

        public void a(CodedOutputStream codedOutputStream) {
            if (d()) {
                codedOutputStream.a(1, ByteString.a(this.a));
            }
            codedOutputStream.a(2, this.b);
        }

        private boolean d() {
            return this.a != null && this.a.length() > 0;
        }
    }

    NativeCrashWriter() {
    }

    private static EventMessage a(SessionEventData sessionEventData, LogFileManager logFileManager, Map<String, String> map) {
        ApplicationMessage applicationMessage = new ApplicationMessage(new ExecutionMessage(new SignalMessage(sessionEventData.signal != null ? sessionEventData.signal : a), a(sessionEventData.threads), a(sessionEventData.binaryImages)), a(a(sessionEventData.customAttributes, map)));
        DeviceMessage a2 = a(sessionEventData.deviceData);
        ByteString a3 = logFileManager.a();
        if (a3 == null) {
            Fabric.getLogger().d(CrashlyticsCore.TAG, "No log data to include with this event.");
        }
        logFileManager.b();
        return new EventMessage(sessionEventData.timestamp, "ndk-crash", applicationMessage, a2, a3 != null ? new LogMessage(a3) : new NullMessage());
    }

    private static CustomAttributeData[] a(CustomAttributeData[] customAttributeDataArr, Map<String, String> map) {
        TreeMap treeMap = new TreeMap(map);
        if (customAttributeDataArr != null) {
            for (CustomAttributeData customAttributeData : customAttributeDataArr) {
                treeMap.put(customAttributeData.key, customAttributeData.value);
            }
        }
        Entry[] entryArr = (Entry[]) treeMap.entrySet().toArray(new Entry[treeMap.size()]);
        CustomAttributeData[] customAttributeDataArr2 = new CustomAttributeData[entryArr.length];
        for (int i = 0; i < customAttributeDataArr2.length; i++) {
            customAttributeDataArr2[i] = new CustomAttributeData((String) entryArr[i].getKey(), (String) entryArr[i].getValue());
        }
        return customAttributeDataArr2;
    }

    private static DeviceMessage a(DeviceData deviceData) {
        DeviceMessage deviceMessage = new DeviceMessage(((float) deviceData.batteryCapacity) / 100.0f, deviceData.batteryVelocity, deviceData.proximity, deviceData.orientation, deviceData.totalPhysicalMemory - deviceData.availablePhysicalMemory, deviceData.totalInternalStorage - deviceData.availableInternalStorage);
        return deviceMessage;
    }

    private static RepeatedMessage a(ThreadData[] threadDataArr) {
        ThreadMessage[] threadMessageArr = threadDataArr != null ? new ThreadMessage[threadDataArr.length] : c;
        for (int i = 0; i < threadMessageArr.length; i++) {
            ThreadData threadData = threadDataArr[i];
            threadMessageArr[i] = new ThreadMessage(threadData, a(threadData.frames));
        }
        return new RepeatedMessage(threadMessageArr);
    }

    private static RepeatedMessage a(FrameData[] frameDataArr) {
        FrameMessage[] frameMessageArr = frameDataArr != null ? new FrameMessage[frameDataArr.length] : d;
        for (int i = 0; i < frameMessageArr.length; i++) {
            frameMessageArr[i] = new FrameMessage(frameDataArr[i]);
        }
        return new RepeatedMessage(frameMessageArr);
    }

    private static RepeatedMessage a(BinaryImageData[] binaryImageDataArr) {
        BinaryImageMessage[] binaryImageMessageArr = binaryImageDataArr != null ? new BinaryImageMessage[binaryImageDataArr.length] : e;
        for (int i = 0; i < binaryImageMessageArr.length; i++) {
            binaryImageMessageArr[i] = new BinaryImageMessage(binaryImageDataArr[i]);
        }
        return new RepeatedMessage(binaryImageMessageArr);
    }

    private static RepeatedMessage a(CustomAttributeData[] customAttributeDataArr) {
        CustomAttributeMessage[] customAttributeMessageArr = customAttributeDataArr != null ? new CustomAttributeMessage[customAttributeDataArr.length] : f;
        for (int i = 0; i < customAttributeMessageArr.length; i++) {
            customAttributeMessageArr[i] = new CustomAttributeMessage(customAttributeDataArr[i]);
        }
        return new RepeatedMessage(customAttributeMessageArr);
    }

    public static void a(SessionEventData sessionEventData, LogFileManager logFileManager, Map<String, String> map, CodedOutputStream codedOutputStream) {
        a(sessionEventData, logFileManager, map).b(codedOutputStream);
    }
}
