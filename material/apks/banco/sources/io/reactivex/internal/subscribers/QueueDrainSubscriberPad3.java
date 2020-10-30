package io.reactivex.internal.subscribers;

import java.util.concurrent.atomic.AtomicLong;

class QueueDrainSubscriberPad3 extends QueueDrainSubscriberPad2 {
    final AtomicLong n = new AtomicLong();

    QueueDrainSubscriberPad3() {
    }
}
