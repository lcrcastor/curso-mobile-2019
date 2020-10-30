package io.fabric.sdk.android.services.common;

public enum DeliveryMechanism {
    DEVELOPER(1),
    USER_SIDELOAD(2),
    TEST_DISTRIBUTION(3),
    APP_STORE(4);
    
    public static final String BETA_APP_PACKAGE_NAME = "io.crash.air";
    private final int a;

    private DeliveryMechanism(int i) {
        this.a = i;
    }

    public int getId() {
        return this.a;
    }

    public String toString() {
        return Integer.toString(this.a);
    }

    public static DeliveryMechanism determineFrom(String str) {
        if (BETA_APP_PACKAGE_NAME.equals(str)) {
            return TEST_DISTRIBUTION;
        }
        if (str != null) {
            return APP_STORE;
        }
        return DEVELOPER;
    }
}
