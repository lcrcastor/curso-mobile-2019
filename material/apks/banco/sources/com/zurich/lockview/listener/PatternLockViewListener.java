package com.zurich.lockview.listener;

import com.zurich.lockview.PatternLockView.Dot;
import java.util.List;

public interface PatternLockViewListener {
    void onCleared();

    void onComplete(List<Dot> list);

    void onProgress(List<Dot> list);

    void onStarted();

    void testFails();
}
