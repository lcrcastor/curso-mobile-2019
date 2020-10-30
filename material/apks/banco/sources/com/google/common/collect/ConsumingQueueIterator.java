package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Queue;

@GwtCompatible
class ConsumingQueueIterator<T> extends AbstractIterator<T> {
    private final Queue<T> a;

    ConsumingQueueIterator(T... tArr) {
        this.a = new ArrayDeque(tArr.length);
        Collections.addAll(this.a, tArr);
    }

    ConsumingQueueIterator(Queue<T> queue) {
        this.a = (Queue) Preconditions.checkNotNull(queue);
    }

    public T computeNext() {
        return this.a.isEmpty() ? endOfData() : this.a.remove();
    }
}
