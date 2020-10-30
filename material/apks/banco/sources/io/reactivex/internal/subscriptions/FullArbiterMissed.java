package io.reactivex.internal.subscriptions;

import java.util.concurrent.atomic.AtomicLong;

class FullArbiterMissed extends FullArbiterPad1 {
    final AtomicLong i = new AtomicLong();

    FullArbiterMissed() {
    }
}
