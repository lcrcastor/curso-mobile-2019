package ar.com.santander.rio.mbanking.services.model.general;

import java.util.List;
import java.util.Map;

public class EventTracker {
    private String action;
    private String category;
    private List<CustomDimenssion> customDimenssion;
    private Map<Integer, Integer> customMetrics;
    private String event;
    private String label;

    public EventTracker() {
    }

    public EventTracker(String str, String str2, String str3) {
        this.label = str;
        this.category = str2;
        this.action = str3;
    }

    public EventTracker(String str, String str2, String str3, List<CustomDimenssion> list) {
        this.label = str;
        this.category = str2;
        this.action = str3;
        this.customDimenssion = list;
    }

    public EventTracker(String str, String str2, String str3, String str4, List<CustomDimenssion> list) {
        this.event = str;
        this.label = str2;
        this.category = str3;
        this.action = str4;
        this.customDimenssion = list;
    }

    public EventTracker(String str, String str2, String str3, String str4, List<CustomDimenssion> list, Map<Integer, Integer> map) {
        this.event = str;
        this.label = str2;
        this.category = str3;
        this.action = str4;
        this.customDimenssion = list;
        this.customMetrics = map;
    }

    public Map<Integer, Integer> getCustomMetrics() {
        return this.customMetrics;
    }

    public void setCustomMetrics(Map<Integer, Integer> map) {
        this.customMetrics = map;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String str) {
        this.label = str;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String str) {
        this.category = str;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String str) {
        this.action = str;
    }

    public List<CustomDimenssion> getCustomDimenssion() {
        return this.customDimenssion;
    }

    public void setCustomDimenssion(List<CustomDimenssion> list) {
        this.customDimenssion = list;
    }

    public String getEvent() {
        return this.event;
    }

    public void setEvent(String str) {
        this.event = str;
    }
}
