package com.crashlytics.android.answers;

public class LevelStartEvent extends PredefinedEvent<LevelStartEvent> {
    /* access modifiers changed from: 0000 */
    public String a() {
        return "levelStart";
    }

    public LevelStartEvent putLevelName(String str) {
        this.d.a("levelName", str);
        return this;
    }
}
