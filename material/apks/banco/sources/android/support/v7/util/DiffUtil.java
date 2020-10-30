package android.support.v7.util;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.Adapter;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DiffUtil {
    private static final Comparator<Snake> a = new Comparator<Snake>() {
        /* renamed from: a */
        public int compare(Snake snake, Snake snake2) {
            int i = snake.a - snake2.a;
            return i == 0 ? snake.b - snake2.b : i;
        }
    };

    public static abstract class Callback {
        public abstract boolean areContentsTheSame(int i, int i2);

        public abstract boolean areItemsTheSame(int i, int i2);

        @Nullable
        public Object getChangePayload(int i, int i2) {
            return null;
        }

        public abstract int getNewListSize();

        public abstract int getOldListSize();
    }

    public static class DiffResult {
        private final List<Snake> a;
        private final int[] b;
        private final int[] c;
        private final Callback d;
        private final int e;
        private final int f;
        private final boolean g;

        DiffResult(Callback callback, List<Snake> list, int[] iArr, int[] iArr2, boolean z) {
            this.a = list;
            this.b = iArr;
            this.c = iArr2;
            Arrays.fill(this.b, 0);
            Arrays.fill(this.c, 0);
            this.d = callback;
            this.e = callback.getOldListSize();
            this.f = callback.getNewListSize();
            this.g = z;
            a();
            b();
        }

        private void a() {
            Snake snake = this.a.isEmpty() ? null : (Snake) this.a.get(0);
            if (snake == null || snake.a != 0 || snake.b != 0) {
                Snake snake2 = new Snake();
                snake2.a = 0;
                snake2.b = 0;
                snake2.d = false;
                snake2.c = 0;
                snake2.e = false;
                this.a.add(0, snake2);
            }
        }

        private void b() {
            int i = this.e;
            int i2 = this.f;
            for (int size = this.a.size() - 1; size >= 0; size--) {
                Snake snake = (Snake) this.a.get(size);
                int i3 = snake.a + snake.c;
                int i4 = snake.b + snake.c;
                if (this.g) {
                    while (i > i3) {
                        a(i, i2, size);
                        i--;
                    }
                    while (i2 > i4) {
                        b(i, i2, size);
                        i2--;
                    }
                }
                for (int i5 = 0; i5 < snake.c; i5++) {
                    int i6 = snake.a + i5;
                    int i7 = snake.b + i5;
                    int i8 = this.d.areContentsTheSame(i6, i7) ? 1 : 2;
                    this.b[i6] = (i7 << 5) | i8;
                    this.c[i7] = (i6 << 5) | i8;
                }
                i = snake.a;
                i2 = snake.b;
            }
        }

        private void a(int i, int i2, int i3) {
            if (this.b[i - 1] == 0) {
                a(i, i2, i3, false);
            }
        }

        private void b(int i, int i2, int i3) {
            if (this.c[i2 - 1] == 0) {
                a(i, i2, i3, true);
            }
        }

        private boolean a(int i, int i2, int i3, boolean z) {
            int i4;
            int i5;
            if (z) {
                i2--;
                i5 = i;
                i4 = i2;
            } else {
                i5 = i - 1;
                i4 = i5;
            }
            while (i3 >= 0) {
                Snake snake = (Snake) this.a.get(i3);
                int i6 = snake.a + snake.c;
                int i7 = snake.b + snake.c;
                int i8 = 4;
                if (z) {
                    for (int i9 = i5 - 1; i9 >= i6; i9--) {
                        if (this.d.areItemsTheSame(i9, i4)) {
                            if (this.d.areContentsTheSame(i9, i4)) {
                                i8 = 8;
                            }
                            this.c[i4] = (i9 << 5) | 16;
                            this.b[i9] = (i4 << 5) | i8;
                            return true;
                        }
                    }
                    continue;
                } else {
                    for (int i10 = i2 - 1; i10 >= i7; i10--) {
                        if (this.d.areItemsTheSame(i4, i10)) {
                            if (this.d.areContentsTheSame(i4, i10)) {
                                i8 = 8;
                            }
                            int i11 = i - 1;
                            this.b[i11] = (i10 << 5) | 16;
                            this.c[i10] = (i11 << 5) | i8;
                            return true;
                        }
                    }
                    continue;
                }
                i5 = snake.a;
                i2 = snake.b;
                i3--;
            }
            return false;
        }

        public void dispatchUpdatesTo(Adapter adapter) {
            dispatchUpdatesTo((ListUpdateCallback) new AdapterListUpdateCallback(adapter));
        }

        public void dispatchUpdatesTo(ListUpdateCallback listUpdateCallback) {
            BatchingListUpdateCallback batchingListUpdateCallback;
            if (listUpdateCallback instanceof BatchingListUpdateCallback) {
                batchingListUpdateCallback = (BatchingListUpdateCallback) listUpdateCallback;
            } else {
                batchingListUpdateCallback = new BatchingListUpdateCallback(listUpdateCallback);
            }
            ArrayList arrayList = new ArrayList();
            int i = this.e;
            int i2 = this.f;
            for (int size = this.a.size() - 1; size >= 0; size--) {
                Snake snake = (Snake) this.a.get(size);
                int i3 = snake.c;
                int i4 = snake.a + i3;
                int i5 = snake.b + i3;
                if (i4 < i) {
                    b(arrayList, batchingListUpdateCallback, i4, i - i4, i4);
                }
                if (i5 < i2) {
                    a(arrayList, batchingListUpdateCallback, i4, i2 - i5, i5);
                }
                for (int i6 = i3 - 1; i6 >= 0; i6--) {
                    if ((this.b[snake.a + i6] & 31) == 2) {
                        batchingListUpdateCallback.onChanged(snake.a + i6, 1, this.d.getChangePayload(snake.a + i6, snake.b + i6));
                    }
                }
                i = snake.a;
                i2 = snake.b;
            }
            batchingListUpdateCallback.dispatchLastEvent();
        }

        private static PostponedUpdate a(List<PostponedUpdate> list, int i, boolean z) {
            int size = list.size() - 1;
            while (size >= 0) {
                PostponedUpdate postponedUpdate = (PostponedUpdate) list.get(size);
                if (postponedUpdate.a == i && postponedUpdate.c == z) {
                    list.remove(size);
                    while (size < list.size()) {
                        PostponedUpdate postponedUpdate2 = (PostponedUpdate) list.get(size);
                        postponedUpdate2.b += z ? 1 : -1;
                        size++;
                    }
                    return postponedUpdate;
                }
                size--;
            }
            return null;
        }

        private void a(List<PostponedUpdate> list, ListUpdateCallback listUpdateCallback, int i, int i2, int i3) {
            if (!this.g) {
                listUpdateCallback.onInserted(i, i2);
                return;
            }
            for (int i4 = i2 - 1; i4 >= 0; i4--) {
                int i5 = i3 + i4;
                int i6 = this.c[i5] & 31;
                if (i6 == 0) {
                    listUpdateCallback.onInserted(i, 1);
                    for (PostponedUpdate postponedUpdate : list) {
                        postponedUpdate.b++;
                    }
                } else if (i6 == 4 || i6 == 8) {
                    int i7 = this.c[i5] >> 5;
                    listUpdateCallback.onMoved(a(list, i7, true).b, i);
                    if (i6 == 4) {
                        listUpdateCallback.onChanged(i, 1, this.d.getChangePayload(i7, i5));
                    }
                } else if (i6 != 16) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("unknown flag for pos ");
                    sb.append(i5);
                    sb.append(UtilsCuentas.SEPARAOR2);
                    sb.append(Long.toBinaryString((long) i6));
                    throw new IllegalStateException(sb.toString());
                } else {
                    list.add(new PostponedUpdate(i5, i, false));
                }
            }
        }

        private void b(List<PostponedUpdate> list, ListUpdateCallback listUpdateCallback, int i, int i2, int i3) {
            if (!this.g) {
                listUpdateCallback.onRemoved(i, i2);
                return;
            }
            for (int i4 = i2 - 1; i4 >= 0; i4--) {
                int i5 = i3 + i4;
                int i6 = this.b[i5] & 31;
                if (i6 == 0) {
                    listUpdateCallback.onRemoved(i + i4, 1);
                    for (PostponedUpdate postponedUpdate : list) {
                        postponedUpdate.b--;
                    }
                } else if (i6 == 4 || i6 == 8) {
                    int i7 = this.b[i5] >> 5;
                    PostponedUpdate a2 = a(list, i7, false);
                    listUpdateCallback.onMoved(i + i4, a2.b - 1);
                    if (i6 == 4) {
                        listUpdateCallback.onChanged(a2.b - 1, 1, this.d.getChangePayload(i5, i7));
                    }
                } else if (i6 != 16) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("unknown flag for pos ");
                    sb.append(i5);
                    sb.append(UtilsCuentas.SEPARAOR2);
                    sb.append(Long.toBinaryString((long) i6));
                    throw new IllegalStateException(sb.toString());
                } else {
                    list.add(new PostponedUpdate(i5, i + i4, true));
                }
            }
        }
    }

    public static abstract class ItemCallback<T> {
        public abstract boolean areContentsTheSame(T t, T t2);

        public abstract boolean areItemsTheSame(T t, T t2);

        public Object getChangePayload(T t, T t2) {
            return null;
        }
    }

    static class PostponedUpdate {
        int a;
        int b;
        boolean c;

        public PostponedUpdate(int i, int i2, boolean z) {
            this.a = i;
            this.b = i2;
            this.c = z;
        }
    }

    static class Range {
        int a;
        int b;
        int c;
        int d;

        public Range() {
        }

        public Range(int i, int i2, int i3, int i4) {
            this.a = i;
            this.b = i2;
            this.c = i3;
            this.d = i4;
        }
    }

    static class Snake {
        int a;
        int b;
        int c;
        boolean d;
        boolean e;

        Snake() {
        }
    }

    private DiffUtil() {
    }

    public static DiffResult calculateDiff(Callback callback) {
        return calculateDiff(callback, true);
    }

    public static DiffResult calculateDiff(Callback callback, boolean z) {
        int oldListSize = callback.getOldListSize();
        int newListSize = callback.getNewListSize();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new Range(0, oldListSize, 0, newListSize));
        int abs = Math.abs(oldListSize - newListSize) + oldListSize + newListSize;
        int i = abs * 2;
        int[] iArr = new int[i];
        int[] iArr2 = new int[i];
        ArrayList arrayList3 = new ArrayList();
        while (!arrayList2.isEmpty()) {
            Range range = (Range) arrayList2.remove(arrayList2.size() - 1);
            Snake a2 = a(callback, range.a, range.b, range.c, range.d, iArr, iArr2, abs);
            if (a2 != null) {
                if (a2.c > 0) {
                    arrayList.add(a2);
                }
                a2.a += range.a;
                a2.b += range.c;
                Range range2 = arrayList3.isEmpty() ? new Range() : (Range) arrayList3.remove(arrayList3.size() - 1);
                range2.a = range.a;
                range2.c = range.c;
                if (a2.e) {
                    range2.b = a2.a;
                    range2.d = a2.b;
                } else if (a2.d) {
                    range2.b = a2.a - 1;
                    range2.d = a2.b;
                } else {
                    range2.b = a2.a;
                    range2.d = a2.b - 1;
                }
                arrayList2.add(range2);
                if (!a2.e) {
                    range.a = a2.a + a2.c;
                    range.c = a2.b + a2.c;
                } else if (a2.d) {
                    range.a = a2.a + a2.c + 1;
                    range.c = a2.b + a2.c;
                } else {
                    range.a = a2.a + a2.c;
                    range.c = a2.b + a2.c + 1;
                }
                arrayList2.add(range);
            } else {
                arrayList3.add(range);
            }
        }
        Collections.sort(arrayList, a);
        DiffResult diffResult = new DiffResult(callback, arrayList, iArr, iArr2, z);
        return diffResult;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0042, code lost:
        if (r3[r15 - 1] < r3[r15 + r7]) goto L_0x0051;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00dd, code lost:
        if (r4[r9 - 1] < r4[r9 + 1]) goto L_0x00ee;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.support.v7.util.DiffUtil.Snake a(android.support.v7.util.DiffUtil.Callback r22, int r23, int r24, int r25, int r26, int[] r27, int[] r28, int r29) {
        /*
            r0 = r22
            r3 = r27
            r4 = r28
            int r5 = r24 - r23
            int r6 = r26 - r25
            r7 = 1
            if (r5 < r7) goto L_0x0156
            if (r6 >= r7) goto L_0x0011
            goto L_0x0156
        L_0x0011:
            int r8 = r5 - r6
            int r9 = r5 + r6
            int r9 = r9 + r7
            int r9 = r9 / 2
            int r10 = r29 - r9
            int r10 = r10 - r7
            int r11 = r29 + r9
            int r11 = r11 + r7
            r12 = 0
            java.util.Arrays.fill(r3, r10, r11, r12)
            int r10 = r10 + r8
            int r11 = r11 + r8
            java.util.Arrays.fill(r4, r10, r11, r5)
            int r10 = r8 % 2
            if (r10 == 0) goto L_0x002d
            r10 = 1
            goto L_0x002e
        L_0x002d:
            r10 = 0
        L_0x002e:
            r11 = 0
        L_0x002f:
            if (r11 > r9) goto L_0x014e
            int r13 = -r11
            r14 = r13
        L_0x0033:
            if (r14 > r11) goto L_0x00bd
            if (r14 == r13) goto L_0x0051
            if (r14 == r11) goto L_0x0045
            int r15 = r29 + r14
            int r16 = r15 + -1
            r12 = r3[r16]
            int r15 = r15 + r7
            r7 = r3[r15]
            if (r12 >= r7) goto L_0x0045
            goto L_0x0051
        L_0x0045:
            int r7 = r29 + r14
            r17 = 1
            int r7 = r7 + -1
            r7 = r3[r7]
            int r7 = r7 + 1
            r12 = 1
            goto L_0x005a
        L_0x0051:
            r17 = 1
            int r7 = r29 + r14
            int r7 = r7 + 1
            r7 = r3[r7]
            r12 = 0
        L_0x005a:
            int r15 = r7 - r14
            r18 = r9
            r9 = r15
        L_0x005f:
            if (r7 >= r5) goto L_0x007a
            if (r9 >= r6) goto L_0x007a
            r19 = r5
            int r5 = r23 + r7
            r20 = r6
            int r6 = r25 + r9
            boolean r5 = r0.areItemsTheSame(r5, r6)
            if (r5 == 0) goto L_0x007e
            int r7 = r7 + 1
            int r9 = r9 + 1
            r5 = r19
            r6 = r20
            goto L_0x005f
        L_0x007a:
            r19 = r5
            r20 = r6
        L_0x007e:
            int r5 = r29 + r14
            r3[r5] = r7
            if (r10 == 0) goto L_0x00b0
            int r6 = r8 - r11
            r7 = 1
            int r6 = r6 + r7
            if (r14 < r6) goto L_0x00b0
            int r6 = r8 + r11
            int r6 = r6 - r7
            if (r14 > r6) goto L_0x00b0
            r6 = r3[r5]
            r7 = r4[r5]
            if (r6 < r7) goto L_0x00b0
            android.support.v7.util.DiffUtil$Snake r0 = new android.support.v7.util.DiffUtil$Snake
            r0.<init>()
            r1 = r4[r5]
            r0.a = r1
            int r1 = r0.a
            int r1 = r1 - r14
            r0.b = r1
            r1 = r3[r5]
            r2 = r4[r5]
            int r1 = r1 - r2
            r0.c = r1
            r0.d = r12
            r5 = 0
            r0.e = r5
            return r0
        L_0x00b0:
            r5 = 0
            int r14 = r14 + 2
            r9 = r18
            r5 = r19
            r6 = r20
            r7 = 1
            r12 = 0
            goto L_0x0033
        L_0x00bd:
            r19 = r5
            r20 = r6
            r18 = r9
            r5 = 0
            r6 = r13
        L_0x00c5:
            if (r6 > r11) goto L_0x0141
            int r7 = r6 + r8
            int r9 = r11 + r8
            if (r7 == r9) goto L_0x00ec
            int r9 = r13 + r8
            if (r7 == r9) goto L_0x00e0
            int r9 = r29 + r7
            int r12 = r9 + -1
            r12 = r4[r12]
            r17 = 1
            int r9 = r9 + 1
            r9 = r4[r9]
            if (r12 >= r9) goto L_0x00e2
            goto L_0x00ee
        L_0x00e0:
            r17 = 1
        L_0x00e2:
            int r9 = r29 + r7
            int r9 = r9 + 1
            r9 = r4[r9]
            int r9 = r9 + -1
            r12 = 1
            goto L_0x00f5
        L_0x00ec:
            r17 = 1
        L_0x00ee:
            int r9 = r29 + r7
            int r9 = r9 + -1
            r9 = r4[r9]
            r12 = 0
        L_0x00f5:
            int r14 = r9 - r7
        L_0x00f7:
            if (r9 <= 0) goto L_0x0111
            if (r14 <= 0) goto L_0x0111
            int r15 = r23 + r9
            int r5 = r15 + -1
            int r15 = r25 + r14
            int r1 = r15 + -1
            boolean r1 = r0.areItemsTheSame(r5, r1)
            if (r1 == 0) goto L_0x0111
            int r9 = r9 + -1
            int r14 = r14 + -1
            r5 = 0
            r17 = 1
            goto L_0x00f7
        L_0x0111:
            int r1 = r29 + r7
            r4[r1] = r9
            if (r10 != 0) goto L_0x013c
            if (r7 < r13) goto L_0x013c
            if (r7 > r11) goto L_0x013c
            r5 = r3[r1]
            r9 = r4[r1]
            if (r5 < r9) goto L_0x013c
            android.support.v7.util.DiffUtil$Snake r0 = new android.support.v7.util.DiffUtil$Snake
            r0.<init>()
            r2 = r4[r1]
            r0.a = r2
            int r2 = r0.a
            int r2 = r2 - r7
            r0.b = r2
            r2 = r3[r1]
            r1 = r4[r1]
            int r2 = r2 - r1
            r0.c = r2
            r0.d = r12
            r1 = 1
            r0.e = r1
            return r0
        L_0x013c:
            r1 = 1
            int r6 = r6 + 2
            r5 = 0
            goto L_0x00c5
        L_0x0141:
            r1 = 1
            int r11 = r11 + 1
            r9 = r18
            r5 = r19
            r6 = r20
            r7 = 1
            r12 = 0
            goto L_0x002f
        L_0x014e:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "DiffUtil hit an unexpected case while trying to calculate the optimal path. Please make sure your data is not changing during the diff calculation."
            r0.<init>(r1)
            throw r0
        L_0x0156:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.util.DiffUtil.a(android.support.v7.util.DiffUtil$Callback, int, int, int, int, int[], int[], int):android.support.v7.util.DiffUtil$Snake");
    }
}
