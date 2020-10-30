package com.twincoders.twinpush.sdk.notifications;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PushNotification implements Serializable {
    private static final long serialVersionUID = 1;
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private Date f;
    private List<String> g = new ArrayList();
    private Map<String, String> h;

    public String getId() {
        return this.a;
    }

    public void setId(String str) {
        this.a = str;
    }

    public String getTitle() {
        return this.b;
    }

    public void setTitle(String str) {
        this.b = str;
    }

    public String getMessage() {
        return this.c;
    }

    public void setMessage(String str) {
        this.c = str;
    }

    public String getSound() {
        return this.d;
    }

    public void setSound(String str) {
        this.d = str;
    }

    public boolean isRichNotification() {
        return this.e != null && this.e.trim().length() > 0;
    }

    public String getRichURL() {
        return this.e;
    }

    public void setRichURL(String str) {
        this.e = str;
    }

    public Map<String, String> getCustomProperties() {
        return this.h;
    }

    public void setCustomProperties(Map<String, String> map) {
        this.h = map;
    }

    public Date getDate() {
        return this.f;
    }

    public void setDate(Date date) {
        this.f = date;
    }

    public List<String> getTags() {
        return this.g;
    }

    public void setTags(List<String> list) {
        this.g = list;
    }

    public boolean hasTitle() {
        return getTitle() != null && getTitle().trim().length() > 0;
    }
}
