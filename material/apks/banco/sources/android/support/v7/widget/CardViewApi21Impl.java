package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;

@RequiresApi(21)
class CardViewApi21Impl implements CardViewImpl {
    public void a() {
    }

    CardViewApi21Impl() {
    }

    public void a(CardViewDelegate cardViewDelegate, Context context, ColorStateList colorStateList, float f, float f2, float f3) {
        cardViewDelegate.a(new RoundRectDrawable(colorStateList, f));
        View d = cardViewDelegate.d();
        d.setClipToOutline(true);
        d.setElevation(f2);
        b(cardViewDelegate, f3);
    }

    public void a(CardViewDelegate cardViewDelegate, float f) {
        j(cardViewDelegate).a(f);
    }

    public void b(CardViewDelegate cardViewDelegate, float f) {
        j(cardViewDelegate).a(f, cardViewDelegate.a(), cardViewDelegate.b());
        f(cardViewDelegate);
    }

    public float a(CardViewDelegate cardViewDelegate) {
        return j(cardViewDelegate).a();
    }

    public float b(CardViewDelegate cardViewDelegate) {
        return d(cardViewDelegate) * 2.0f;
    }

    public float c(CardViewDelegate cardViewDelegate) {
        return d(cardViewDelegate) * 2.0f;
    }

    public float d(CardViewDelegate cardViewDelegate) {
        return j(cardViewDelegate).b();
    }

    public void c(CardViewDelegate cardViewDelegate, float f) {
        cardViewDelegate.d().setElevation(f);
    }

    public float e(CardViewDelegate cardViewDelegate) {
        return cardViewDelegate.d().getElevation();
    }

    public void f(CardViewDelegate cardViewDelegate) {
        if (!cardViewDelegate.a()) {
            cardViewDelegate.a(0, 0, 0, 0);
            return;
        }
        float a = a(cardViewDelegate);
        float d = d(cardViewDelegate);
        int ceil = (int) Math.ceil((double) RoundRectDrawableWithShadow.b(a, d, cardViewDelegate.b()));
        int ceil2 = (int) Math.ceil((double) RoundRectDrawableWithShadow.a(a, d, cardViewDelegate.b()));
        cardViewDelegate.a(ceil, ceil2, ceil, ceil2);
    }

    public void g(CardViewDelegate cardViewDelegate) {
        b(cardViewDelegate, a(cardViewDelegate));
    }

    public void h(CardViewDelegate cardViewDelegate) {
        b(cardViewDelegate, a(cardViewDelegate));
    }

    public void a(CardViewDelegate cardViewDelegate, @Nullable ColorStateList colorStateList) {
        j(cardViewDelegate).a(colorStateList);
    }

    public ColorStateList i(CardViewDelegate cardViewDelegate) {
        return j(cardViewDelegate).c();
    }

    private RoundRectDrawable j(CardViewDelegate cardViewDelegate) {
        return (RoundRectDrawable) cardViewDelegate.c();
    }
}
