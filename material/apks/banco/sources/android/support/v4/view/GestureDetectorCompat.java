package android.support.v4.view;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

public final class GestureDetectorCompat {
    private final GestureDetectorCompatImpl a;

    interface GestureDetectorCompatImpl {
        void a(OnDoubleTapListener onDoubleTapListener);

        void a(boolean z);

        boolean a();

        boolean a(MotionEvent motionEvent);
    }

    static class GestureDetectorCompatImplBase implements GestureDetectorCompatImpl {
        private static final int j = ViewConfiguration.getLongPressTimeout();
        private static final int k = ViewConfiguration.getTapTimeout();
        private static final int l = ViewConfiguration.getDoubleTapTimeout();
        final OnGestureListener a;
        OnDoubleTapListener b;
        boolean c;
        boolean d;
        MotionEvent e;
        private int f;
        private int g;
        private int h;
        private int i;
        private final Handler m;
        private boolean n;
        private boolean o;
        private boolean p;
        private MotionEvent q;
        private boolean r;
        private float s;
        private float t;
        private float u;
        private float v;
        private boolean w;
        private VelocityTracker x;

        class GestureHandler extends Handler {
            GestureHandler() {
            }

            GestureHandler(Handler handler) {
                super(handler.getLooper());
            }

            public void handleMessage(Message message) {
                switch (message.what) {
                    case 1:
                        GestureDetectorCompatImplBase.this.a.onShowPress(GestureDetectorCompatImplBase.this.e);
                        return;
                    case 2:
                        GestureDetectorCompatImplBase.this.b();
                        return;
                    case 3:
                        if (GestureDetectorCompatImplBase.this.b == null) {
                            return;
                        }
                        if (!GestureDetectorCompatImplBase.this.c) {
                            GestureDetectorCompatImplBase.this.b.onSingleTapConfirmed(GestureDetectorCompatImplBase.this.e);
                            return;
                        } else {
                            GestureDetectorCompatImplBase.this.d = true;
                            return;
                        }
                    default:
                        StringBuilder sb = new StringBuilder();
                        sb.append("Unknown message ");
                        sb.append(message);
                        throw new RuntimeException(sb.toString());
                }
            }
        }

        GestureDetectorCompatImplBase(Context context, OnGestureListener onGestureListener, Handler handler) {
            if (handler != null) {
                this.m = new GestureHandler(handler);
            } else {
                this.m = new GestureHandler();
            }
            this.a = onGestureListener;
            if (onGestureListener instanceof OnDoubleTapListener) {
                a((OnDoubleTapListener) onGestureListener);
            }
            a(context);
        }

        private void a(Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null");
            } else if (this.a == null) {
                throw new IllegalArgumentException("OnGestureListener must not be null");
            } else {
                this.w = true;
                ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
                int scaledTouchSlop = viewConfiguration.getScaledTouchSlop();
                int scaledDoubleTapSlop = viewConfiguration.getScaledDoubleTapSlop();
                this.h = viewConfiguration.getScaledMinimumFlingVelocity();
                this.i = viewConfiguration.getScaledMaximumFlingVelocity();
                this.f = scaledTouchSlop * scaledTouchSlop;
                this.g = scaledDoubleTapSlop * scaledDoubleTapSlop;
            }
        }

        public void a(OnDoubleTapListener onDoubleTapListener) {
            this.b = onDoubleTapListener;
        }

        public void a(boolean z) {
            this.w = z;
        }

        public boolean a() {
            return this.w;
        }

        /* JADX WARNING: Removed duplicated region for block: B:93:0x0208  */
        /* JADX WARNING: Removed duplicated region for block: B:96:0x0221  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean a(android.view.MotionEvent r12) {
            /*
                r11 = this;
                int r0 = r12.getAction()
                android.view.VelocityTracker r1 = r11.x
                if (r1 != 0) goto L_0x000e
                android.view.VelocityTracker r1 = android.view.VelocityTracker.obtain()
                r11.x = r1
            L_0x000e:
                android.view.VelocityTracker r1 = r11.x
                r1.addMovement(r12)
                r0 = r0 & 255(0xff, float:3.57E-43)
                r1 = 6
                r2 = 1
                r3 = 0
                if (r0 != r1) goto L_0x001c
                r1 = 1
                goto L_0x001d
            L_0x001c:
                r1 = 0
            L_0x001d:
                if (r1 == 0) goto L_0x0024
                int r4 = r12.getActionIndex()
                goto L_0x0025
            L_0x0024:
                r4 = -1
            L_0x0025:
                int r5 = r12.getPointerCount()
                r6 = 0
                r7 = 0
                r8 = 0
                r9 = 0
            L_0x002d:
                if (r7 >= r5) goto L_0x003f
                if (r4 != r7) goto L_0x0032
                goto L_0x003c
            L_0x0032:
                float r10 = r12.getX(r7)
                float r8 = r8 + r10
                float r10 = r12.getY(r7)
                float r9 = r9 + r10
            L_0x003c:
                int r7 = r7 + 1
                goto L_0x002d
            L_0x003f:
                if (r1 == 0) goto L_0x0044
                int r1 = r5 + -1
                goto L_0x0045
            L_0x0044:
                r1 = r5
            L_0x0045:
                float r1 = (float) r1
                float r8 = r8 / r1
                float r9 = r9 / r1
                r1 = 1000(0x3e8, float:1.401E-42)
                r4 = 2
                r7 = 3
                switch(r0) {
                    case 0: goto L_0x01bb;
                    case 1: goto L_0x0127;
                    case 2: goto L_0x00b0;
                    case 3: goto L_0x00ab;
                    case 4: goto L_0x004f;
                    case 5: goto L_0x009e;
                    case 6: goto L_0x0051;
                    default: goto L_0x004f;
                }
            L_0x004f:
                goto L_0x0253
            L_0x0051:
                r11.s = r8
                r11.u = r8
                r11.t = r9
                r11.v = r9
                android.view.VelocityTracker r0 = r11.x
                int r2 = r11.i
                float r2 = (float) r2
                r0.computeCurrentVelocity(r1, r2)
                int r0 = r12.getActionIndex()
                int r1 = r12.getPointerId(r0)
                android.view.VelocityTracker r2 = r11.x
                float r2 = r2.getXVelocity(r1)
                android.view.VelocityTracker r4 = r11.x
                float r1 = r4.getYVelocity(r1)
                r4 = 0
            L_0x0076:
                if (r4 >= r5) goto L_0x0253
                if (r4 != r0) goto L_0x007b
                goto L_0x009b
            L_0x007b:
                int r7 = r12.getPointerId(r4)
                android.view.VelocityTracker r8 = r11.x
                float r8 = r8.getXVelocity(r7)
                float r8 = r8 * r2
                android.view.VelocityTracker r9 = r11.x
                float r7 = r9.getYVelocity(r7)
                float r7 = r7 * r1
                float r8 = r8 + r7
                int r7 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
                if (r7 >= 0) goto L_0x009b
                android.view.VelocityTracker r12 = r11.x
                r12.clear()
                goto L_0x0253
            L_0x009b:
                int r4 = r4 + 1
                goto L_0x0076
            L_0x009e:
                r11.s = r8
                r11.u = r8
                r11.t = r9
                r11.v = r9
                r11.d()
                goto L_0x0253
            L_0x00ab:
                r11.c()
                goto L_0x0253
            L_0x00b0:
                boolean r0 = r11.n
                if (r0 == 0) goto L_0x00b6
                goto L_0x0253
            L_0x00b6:
                float r0 = r11.s
                float r0 = r0 - r8
                float r1 = r11.t
                float r1 = r1 - r9
                boolean r5 = r11.r
                if (r5 == 0) goto L_0x00c9
                android.view.GestureDetector$OnDoubleTapListener r0 = r11.b
                boolean r12 = r0.onDoubleTapEvent(r12)
                r3 = r3 | r12
                goto L_0x0253
            L_0x00c9:
                boolean r5 = r11.o
                if (r5 == 0) goto L_0x0107
                float r5 = r11.u
                float r5 = r8 - r5
                int r5 = (int) r5
                float r6 = r11.v
                float r6 = r9 - r6
                int r6 = (int) r6
                int r5 = r5 * r5
                int r6 = r6 * r6
                int r5 = r5 + r6
                int r6 = r11.f
                if (r5 <= r6) goto L_0x00fe
                android.view.GestureDetector$OnGestureListener r6 = r11.a
                android.view.MotionEvent r10 = r11.e
                boolean r12 = r6.onScroll(r10, r12, r0, r1)
                r11.s = r8
                r11.t = r9
                r11.o = r3
                android.os.Handler r0 = r11.m
                r0.removeMessages(r7)
                android.os.Handler r0 = r11.m
                r0.removeMessages(r2)
                android.os.Handler r0 = r11.m
                r0.removeMessages(r4)
                goto L_0x00ff
            L_0x00fe:
                r12 = 0
            L_0x00ff:
                int r0 = r11.f
                if (r5 <= r0) goto L_0x01b8
                r11.p = r3
                goto L_0x01b8
            L_0x0107:
                float r2 = java.lang.Math.abs(r0)
                r4 = 1065353216(0x3f800000, float:1.0)
                int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r2 >= 0) goto L_0x0119
                float r2 = java.lang.Math.abs(r1)
                int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r2 < 0) goto L_0x0253
            L_0x0119:
                android.view.GestureDetector$OnGestureListener r2 = r11.a
                android.view.MotionEvent r3 = r11.e
                boolean r3 = r2.onScroll(r3, r12, r0, r1)
                r11.s = r8
                r11.t = r9
                goto L_0x0253
            L_0x0127:
                r11.c = r3
                android.view.MotionEvent r0 = android.view.MotionEvent.obtain(r12)
                boolean r5 = r11.r
                if (r5 == 0) goto L_0x0139
                android.view.GestureDetector$OnDoubleTapListener r1 = r11.b
                boolean r12 = r1.onDoubleTapEvent(r12)
                r12 = r12 | r3
                goto L_0x0193
            L_0x0139:
                boolean r5 = r11.n
                if (r5 == 0) goto L_0x0145
                android.os.Handler r12 = r11.m
                r12.removeMessages(r7)
                r11.n = r3
                goto L_0x0189
            L_0x0145:
                boolean r5 = r11.o
                if (r5 == 0) goto L_0x015e
                android.view.GestureDetector$OnGestureListener r1 = r11.a
                boolean r1 = r1.onSingleTapUp(r12)
                boolean r5 = r11.d
                if (r5 == 0) goto L_0x015c
                android.view.GestureDetector$OnDoubleTapListener r5 = r11.b
                if (r5 == 0) goto L_0x015c
                android.view.GestureDetector$OnDoubleTapListener r5 = r11.b
                r5.onSingleTapConfirmed(r12)
            L_0x015c:
                r12 = r1
                goto L_0x0193
            L_0x015e:
                android.view.VelocityTracker r5 = r11.x
                int r6 = r12.getPointerId(r3)
                int r7 = r11.i
                float r7 = (float) r7
                r5.computeCurrentVelocity(r1, r7)
                float r1 = r5.getYVelocity(r6)
                float r5 = r5.getXVelocity(r6)
                float r6 = java.lang.Math.abs(r1)
                int r7 = r11.h
                float r7 = (float) r7
                int r6 = (r6 > r7 ? 1 : (r6 == r7 ? 0 : -1))
                if (r6 > 0) goto L_0x018b
                float r6 = java.lang.Math.abs(r5)
                int r7 = r11.h
                float r7 = (float) r7
                int r6 = (r6 > r7 ? 1 : (r6 == r7 ? 0 : -1))
                if (r6 <= 0) goto L_0x0189
                goto L_0x018b
            L_0x0189:
                r12 = 0
                goto L_0x0193
            L_0x018b:
                android.view.GestureDetector$OnGestureListener r6 = r11.a
                android.view.MotionEvent r7 = r11.e
                boolean r12 = r6.onFling(r7, r12, r5, r1)
            L_0x0193:
                android.view.MotionEvent r1 = r11.q
                if (r1 == 0) goto L_0x019c
                android.view.MotionEvent r1 = r11.q
                r1.recycle()
            L_0x019c:
                r11.q = r0
                android.view.VelocityTracker r0 = r11.x
                if (r0 == 0) goto L_0x01aa
                android.view.VelocityTracker r0 = r11.x
                r0.recycle()
                r0 = 0
                r11.x = r0
            L_0x01aa:
                r11.r = r3
                r11.d = r3
                android.os.Handler r0 = r11.m
                r0.removeMessages(r2)
                android.os.Handler r0 = r11.m
                r0.removeMessages(r4)
            L_0x01b8:
                r3 = r12
                goto L_0x0253
            L_0x01bb:
                android.view.GestureDetector$OnDoubleTapListener r0 = r11.b
                if (r0 == 0) goto L_0x01fb
                android.os.Handler r0 = r11.m
                boolean r0 = r0.hasMessages(r7)
                if (r0 == 0) goto L_0x01cc
                android.os.Handler r1 = r11.m
                r1.removeMessages(r7)
            L_0x01cc:
                android.view.MotionEvent r1 = r11.e
                if (r1 == 0) goto L_0x01f3
                android.view.MotionEvent r1 = r11.q
                if (r1 == 0) goto L_0x01f3
                if (r0 == 0) goto L_0x01f3
                android.view.MotionEvent r0 = r11.e
                android.view.MotionEvent r1 = r11.q
                boolean r0 = r11.a(r0, r1, r12)
                if (r0 == 0) goto L_0x01f3
                r11.r = r2
                android.view.GestureDetector$OnDoubleTapListener r0 = r11.b
                android.view.MotionEvent r1 = r11.e
                boolean r0 = r0.onDoubleTap(r1)
                r0 = r0 | r3
                android.view.GestureDetector$OnDoubleTapListener r1 = r11.b
                boolean r1 = r1.onDoubleTapEvent(r12)
                r0 = r0 | r1
                goto L_0x01fc
            L_0x01f3:
                android.os.Handler r0 = r11.m
                int r1 = l
                long r5 = (long) r1
                r0.sendEmptyMessageDelayed(r7, r5)
            L_0x01fb:
                r0 = 0
            L_0x01fc:
                r11.s = r8
                r11.u = r8
                r11.t = r9
                r11.v = r9
                android.view.MotionEvent r1 = r11.e
                if (r1 == 0) goto L_0x020d
                android.view.MotionEvent r1 = r11.e
                r1.recycle()
            L_0x020d:
                android.view.MotionEvent r1 = android.view.MotionEvent.obtain(r12)
                r11.e = r1
                r11.o = r2
                r11.p = r2
                r11.c = r2
                r11.n = r3
                r11.d = r3
                boolean r1 = r11.w
                if (r1 == 0) goto L_0x023b
                android.os.Handler r1 = r11.m
                r1.removeMessages(r4)
                android.os.Handler r1 = r11.m
                android.view.MotionEvent r3 = r11.e
                long r5 = r3.getDownTime()
                int r3 = k
                long r7 = (long) r3
                long r9 = r5 + r7
                int r3 = j
                long r5 = (long) r3
                long r7 = r9 + r5
                r1.sendEmptyMessageAtTime(r4, r7)
            L_0x023b:
                android.os.Handler r1 = r11.m
                android.view.MotionEvent r3 = r11.e
                long r3 = r3.getDownTime()
                int r5 = k
                long r5 = (long) r5
                long r7 = r3 + r5
                r1.sendEmptyMessageAtTime(r2, r7)
                android.view.GestureDetector$OnGestureListener r1 = r11.a
                boolean r12 = r1.onDown(r12)
                r3 = r0 | r12
            L_0x0253:
                return r3
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v4.view.GestureDetectorCompat.GestureDetectorCompatImplBase.a(android.view.MotionEvent):boolean");
        }

        private void c() {
            this.m.removeMessages(1);
            this.m.removeMessages(2);
            this.m.removeMessages(3);
            this.x.recycle();
            this.x = null;
            this.r = false;
            this.c = false;
            this.o = false;
            this.p = false;
            this.d = false;
            if (this.n) {
                this.n = false;
            }
        }

        private void d() {
            this.m.removeMessages(1);
            this.m.removeMessages(2);
            this.m.removeMessages(3);
            this.r = false;
            this.o = false;
            this.p = false;
            this.d = false;
            if (this.n) {
                this.n = false;
            }
        }

        private boolean a(MotionEvent motionEvent, MotionEvent motionEvent2, MotionEvent motionEvent3) {
            boolean z = false;
            if (!this.p || motionEvent3.getEventTime() - motionEvent2.getEventTime() > ((long) l)) {
                return false;
            }
            int x2 = ((int) motionEvent.getX()) - ((int) motionEvent3.getX());
            int y = ((int) motionEvent.getY()) - ((int) motionEvent3.getY());
            if ((x2 * x2) + (y * y) < this.g) {
                z = true;
            }
            return z;
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            this.m.removeMessages(3);
            this.d = false;
            this.n = true;
            this.a.onLongPress(this.e);
        }
    }

    static class GestureDetectorCompatImplJellybeanMr2 implements GestureDetectorCompatImpl {
        private final GestureDetector a;

        GestureDetectorCompatImplJellybeanMr2(Context context, OnGestureListener onGestureListener, Handler handler) {
            this.a = new GestureDetector(context, onGestureListener, handler);
        }

        public boolean a() {
            return this.a.isLongpressEnabled();
        }

        public boolean a(MotionEvent motionEvent) {
            return this.a.onTouchEvent(motionEvent);
        }

        public void a(boolean z) {
            this.a.setIsLongpressEnabled(z);
        }

        public void a(OnDoubleTapListener onDoubleTapListener) {
            this.a.setOnDoubleTapListener(onDoubleTapListener);
        }
    }

    public GestureDetectorCompat(Context context, OnGestureListener onGestureListener) {
        this(context, onGestureListener, null);
    }

    public GestureDetectorCompat(Context context, OnGestureListener onGestureListener, Handler handler) {
        if (VERSION.SDK_INT > 17) {
            this.a = new GestureDetectorCompatImplJellybeanMr2(context, onGestureListener, handler);
        } else {
            this.a = new GestureDetectorCompatImplBase(context, onGestureListener, handler);
        }
    }

    public boolean isLongpressEnabled() {
        return this.a.a();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.a.a(motionEvent);
    }

    public void setIsLongpressEnabled(boolean z) {
        this.a.a(z);
    }

    public void setOnDoubleTapListener(OnDoubleTapListener onDoubleTapListener) {
        this.a.a(onDoubleTapListener);
    }
}
