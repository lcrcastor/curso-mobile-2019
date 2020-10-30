package org.bouncycastle.util.test;

public class TestFailedException extends RuntimeException {
    private TestResult a;

    public TestFailedException(TestResult testResult) {
        this.a = testResult;
    }

    public TestResult getResult() {
        return this.a;
    }
}
