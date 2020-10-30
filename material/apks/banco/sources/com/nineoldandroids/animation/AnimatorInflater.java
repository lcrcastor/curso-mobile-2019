package com.nineoldandroids.animation;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.util.Xml;
import android.view.animation.AnimationUtils;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;

public class AnimatorInflater {
    private static final int[] a = {16843490};
    private static final int[] b = {16843489};
    private static final int[] c = {16843073, 16843160, 16843198, 16843199, 16843200, 16843486, 16843487, 16843488};

    /* JADX WARNING: Removed duplicated region for block: B:23:0x005e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.nineoldandroids.animation.Animator loadAnimator(android.content.Context r4, int r5) {
        /*
            r0 = 0
            android.content.res.Resources r1 = r4.getResources()     // Catch:{ XmlPullParserException -> 0x003d, IOException -> 0x001e }
            android.content.res.XmlResourceParser r1 = r1.getAnimation(r5)     // Catch:{ XmlPullParserException -> 0x003d, IOException -> 0x001e }
            com.nineoldandroids.animation.Animator r4 = a(r4, r1)     // Catch:{ XmlPullParserException -> 0x0019, IOException -> 0x0016, all -> 0x0013 }
            if (r1 == 0) goto L_0x0012
            r1.close()
        L_0x0012:
            return r4
        L_0x0013:
            r4 = move-exception
            r0 = r1
            goto L_0x005c
        L_0x0016:
            r4 = move-exception
            r0 = r1
            goto L_0x001f
        L_0x0019:
            r4 = move-exception
            r0 = r1
            goto L_0x003e
        L_0x001c:
            r4 = move-exception
            goto L_0x005c
        L_0x001e:
            r4 = move-exception
        L_0x001f:
            android.content.res.Resources$NotFoundException r1 = new android.content.res.Resources$NotFoundException     // Catch:{ all -> 0x001c }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x001c }
            r2.<init>()     // Catch:{ all -> 0x001c }
            java.lang.String r3 = "Can't load animation resource ID #0x"
            r2.append(r3)     // Catch:{ all -> 0x001c }
            java.lang.String r5 = java.lang.Integer.toHexString(r5)     // Catch:{ all -> 0x001c }
            r2.append(r5)     // Catch:{ all -> 0x001c }
            java.lang.String r5 = r2.toString()     // Catch:{ all -> 0x001c }
            r1.<init>(r5)     // Catch:{ all -> 0x001c }
            r1.initCause(r4)     // Catch:{ all -> 0x001c }
            throw r1     // Catch:{ all -> 0x001c }
        L_0x003d:
            r4 = move-exception
        L_0x003e:
            android.content.res.Resources$NotFoundException r1 = new android.content.res.Resources$NotFoundException     // Catch:{ all -> 0x001c }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x001c }
            r2.<init>()     // Catch:{ all -> 0x001c }
            java.lang.String r3 = "Can't load animation resource ID #0x"
            r2.append(r3)     // Catch:{ all -> 0x001c }
            java.lang.String r5 = java.lang.Integer.toHexString(r5)     // Catch:{ all -> 0x001c }
            r2.append(r5)     // Catch:{ all -> 0x001c }
            java.lang.String r5 = r2.toString()     // Catch:{ all -> 0x001c }
            r1.<init>(r5)     // Catch:{ all -> 0x001c }
            r1.initCause(r4)     // Catch:{ all -> 0x001c }
            throw r1     // Catch:{ all -> 0x001c }
        L_0x005c:
            if (r0 == 0) goto L_0x0061
            r0.close()
        L_0x0061:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.nineoldandroids.animation.AnimatorInflater.loadAnimator(android.content.Context, int):com.nineoldandroids.animation.Animator");
    }

    private static Animator a(Context context, XmlPullParser xmlPullParser) {
        return a(context, xmlPullParser, Xml.asAttributeSet(xmlPullParser), null, 0);
    }

    private static Animator a(Context context, XmlPullParser xmlPullParser, AttributeSet attributeSet, AnimatorSet animatorSet, int i) {
        int i2;
        int depth = xmlPullParser.getDepth();
        ArrayList arrayList = null;
        Animator animator = null;
        while (true) {
            int next = xmlPullParser.next();
            i2 = 0;
            if ((next != 3 || xmlPullParser.getDepth() > depth) && next != 1) {
                if (next == 2) {
                    String name = xmlPullParser.getName();
                    if (name.equals("objectAnimator")) {
                        animator = a(context, attributeSet);
                    } else if (name.equals("animator")) {
                        animator = a(context, attributeSet, null);
                    } else if (name.equals("set")) {
                        animator = new AnimatorSet();
                        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, a);
                        TypedValue typedValue = new TypedValue();
                        obtainStyledAttributes.getValue(0, typedValue);
                        if (typedValue.type == 16) {
                            i2 = typedValue.data;
                        }
                        a(context, xmlPullParser, attributeSet, (AnimatorSet) animator, i2);
                        obtainStyledAttributes.recycle();
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Unknown animator name: ");
                        sb.append(xmlPullParser.getName());
                        throw new RuntimeException(sb.toString());
                    }
                    if (animatorSet != null) {
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                        }
                        arrayList.add(animator);
                    }
                }
            }
        }
        if (!(animatorSet == null || arrayList == null)) {
            Animator[] animatorArr = new Animator[arrayList.size()];
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                int i3 = i2 + 1;
                animatorArr[i2] = (Animator) it.next();
                i2 = i3;
            }
            if (i == 0) {
                animatorSet.playTogether(animatorArr);
            } else {
                animatorSet.playSequentially(animatorArr);
            }
        }
        return animator;
    }

    private static ObjectAnimator a(Context context, AttributeSet attributeSet) {
        ObjectAnimator objectAnimator = new ObjectAnimator();
        a(context, attributeSet, objectAnimator);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, b);
        objectAnimator.setPropertyName(obtainStyledAttributes.getString(0));
        obtainStyledAttributes.recycle();
        return objectAnimator;
    }

    private static ValueAnimator a(Context context, AttributeSet attributeSet, ValueAnimator valueAnimator) {
        int i;
        int i2;
        int i3;
        int i4;
        float f;
        float f2;
        float f3;
        Context context2 = context;
        TypedArray obtainStyledAttributes = context2.obtainStyledAttributes(attributeSet, c);
        long j = (long) obtainStyledAttributes.getInt(1, 0);
        long j2 = (long) obtainStyledAttributes.getInt(2, 0);
        int i5 = obtainStyledAttributes.getInt(7, 0);
        ValueAnimator valueAnimator2 = valueAnimator == null ? new ValueAnimator() : valueAnimator;
        boolean z = i5 == 0;
        TypedValue peekValue = obtainStyledAttributes.peekValue(5);
        boolean z2 = peekValue != null;
        int i6 = z2 ? peekValue.type : 0;
        TypedValue peekValue2 = obtainStyledAttributes.peekValue(6);
        boolean z3 = peekValue2 != null;
        int i7 = z3 ? peekValue2.type : 0;
        if ((z2 && i6 >= 28 && i6 <= 31) || (z3 && i7 >= 28 && i7 <= 31)) {
            valueAnimator2.setEvaluator(new ArgbEvaluator());
            z = false;
        }
        if (!z) {
            i = 0;
            if (z2) {
                if (i6 == 5) {
                    i3 = (int) obtainStyledAttributes.getDimension(5, BitmapDescriptorFactory.HUE_RED);
                } else if (i6 < 28 || i6 > 31) {
                    i3 = obtainStyledAttributes.getInt(5, 0);
                } else {
                    i3 = obtainStyledAttributes.getColor(5, 0);
                }
                if (z3) {
                    if (i7 == 5) {
                        i4 = (int) obtainStyledAttributes.getDimension(6, BitmapDescriptorFactory.HUE_RED);
                    } else if (i7 < 28 || i7 > 31) {
                        i4 = obtainStyledAttributes.getInt(6, 0);
                    } else {
                        i4 = obtainStyledAttributes.getColor(6, 0);
                    }
                    valueAnimator2.setIntValues(i3, i4);
                } else {
                    valueAnimator2.setIntValues(i3);
                }
            } else if (z3) {
                if (i7 == 5) {
                    i2 = (int) obtainStyledAttributes.getDimension(6, BitmapDescriptorFactory.HUE_RED);
                } else if (i7 < 28 || i7 > 31) {
                    i2 = obtainStyledAttributes.getInt(6, 0);
                } else {
                    i2 = obtainStyledAttributes.getColor(6, 0);
                }
                valueAnimator2.setIntValues(i2);
            }
        } else if (z2) {
            if (i6 == 5) {
                f2 = obtainStyledAttributes.getDimension(5, BitmapDescriptorFactory.HUE_RED);
            } else {
                f2 = obtainStyledAttributes.getFloat(5, BitmapDescriptorFactory.HUE_RED);
            }
            if (z3) {
                if (i7 == 5) {
                    f3 = obtainStyledAttributes.getDimension(6, BitmapDescriptorFactory.HUE_RED);
                } else {
                    f3 = obtainStyledAttributes.getFloat(6, BitmapDescriptorFactory.HUE_RED);
                }
                i = 0;
                valueAnimator2.setFloatValues(f2, f3);
            } else {
                i = 0;
                valueAnimator2.setFloatValues(f2);
            }
        } else {
            i = 0;
            if (i7 == 5) {
                f = obtainStyledAttributes.getDimension(6, BitmapDescriptorFactory.HUE_RED);
            } else {
                f = obtainStyledAttributes.getFloat(6, BitmapDescriptorFactory.HUE_RED);
            }
            valueAnimator2.setFloatValues(f);
        }
        valueAnimator2.setDuration(j);
        valueAnimator2.setStartDelay(j2);
        if (obtainStyledAttributes.hasValue(3)) {
            valueAnimator2.setRepeatCount(obtainStyledAttributes.getInt(3, i));
        }
        if (obtainStyledAttributes.hasValue(4)) {
            valueAnimator2.setRepeatMode(obtainStyledAttributes.getInt(4, 1));
        }
        int resourceId = obtainStyledAttributes.getResourceId(i, i);
        if (resourceId > 0) {
            valueAnimator2.setInterpolator(AnimationUtils.loadInterpolator(context2, resourceId));
        }
        obtainStyledAttributes.recycle();
        return valueAnimator2;
    }
}
