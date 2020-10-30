package org.bouncycastle.util.test;

public class SimpleTestResult implements TestResult {
    private static final String a = System.getProperty("line.separator");
    private boolean b;
    private String c;
    private Throwable d;

    public SimpleTestResult(boolean z, String str) {
        this.b = z;
        this.c = str;
    }

    public SimpleTestResult(boolean z, String str, Throwable th) {
        this.b = z;
        this.c = str;
        this.d = th;
    }

    public static TestResult failed(Test test, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(test.getName());
        sb.append(": ");
        sb.append(str);
        return new SimpleTestResult(false, sb.toString());
    }

    public static TestResult failed(Test test, String str, Object obj, Object obj2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(a);
        sb.append("Expected: ");
        sb.append(obj);
        sb.append(a);
        sb.append("Found   : ");
        sb.append(obj2);
        return failed(test, sb.toString());
    }

    public static TestResult failed(Test test, String str, Throwable th) {
        StringBuilder sb = new StringBuilder();
        sb.append(test.getName());
        sb.append(": ");
        sb.append(str);
        return new SimpleTestResult(false, sb.toString(), th);
    }

    public static String failedMessage(String str, String str2, String str3, String str4) {
        StringBuffer stringBuffer = new StringBuffer(str);
        stringBuffer.append(" failing ");
        stringBuffer.append(str2);
        stringBuffer.append(a);
        stringBuffer.append("    expected: ");
        stringBuffer.append(str3);
        stringBuffer.append(a);
        stringBuffer.append("    got     : ");
        stringBuffer.append(str4);
        return stringBuffer.toString();
    }

    public static TestResult successful(Test test, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(test.getName());
        sb.append(": ");
        sb.append(str);
        return new SimpleTestResult(true, sb.toString());
    }

    public Throwable getException() {
        return this.d;
    }

    public boolean isSuccessful() {
        return this.b;
    }

    public String toString() {
        return this.c;
    }
}
