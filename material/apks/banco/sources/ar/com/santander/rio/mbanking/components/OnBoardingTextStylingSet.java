package ar.com.santander.rio.mbanking.components;

import android.text.Html;
import android.text.Spanned;
import java.util.HashMap;

public class OnBoardingTextStylingSet {
    private HashMap<Integer, Spanned> a;

    public OnBoardingTextStylingSet() {
        this.a = new HashMap<>();
    }

    public OnBoardingTextStylingSet(Integer num, Spanned spanned) {
        this.a = new HashMap<>();
        put(num, spanned);
    }

    public OnBoardingTextStylingSet(HashMap<Integer, Spanned> hashMap) {
        this.a = hashMap;
    }

    public HashMap<Integer, Spanned> get() {
        return this.a;
    }

    public Spanned get(Integer num) {
        return (Spanned) this.a.get(num);
    }

    public void put(Integer num, Spanned spanned) {
        try {
            this.a.put(num, spanned);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    public void put(Integer num, String str) {
        try {
            put(num, Html.fromHtml(str));
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    public void put(HashMap<Integer, Spanned> hashMap) {
        try {
            this.a.putAll(hashMap);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    public void remove(Integer num) {
        try {
            this.a.remove(num);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    public void clear() {
        try {
            this.a.clear();
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    public boolean isEmpty() {
        return this.a.isEmpty();
    }

    public boolean hasKey(Integer num) {
        return this.a.containsKey(num);
    }

    public boolean hasValue(Spanned spanned) {
        return this.a.containsValue(spanned);
    }
}
