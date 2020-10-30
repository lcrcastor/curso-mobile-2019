package com.twincoders.twinpush.sdk.communications;

import com.twincoders.twinpush.sdk.communications.TwinRequestParam.ParamType;
import java.util.List;

public class DefaultRequestParam implements TwinRequestParam {
    String a = null;
    Object b = null;
    List<TwinRequestParam> c = null;
    List<String> d = null;
    ParamType e;

    private DefaultRequestParam() {
    }

    DefaultRequestParam(String str, Object obj) {
        this.a = str;
        this.b = obj;
        this.e = ParamType.SIMPLE;
    }

    public static DefaultRequestParam simpleParam(String str, Object obj) {
        return new DefaultRequestParam(str, obj);
    }

    public static DefaultRequestParam complexParam(String str, List<TwinRequestParam> list) {
        DefaultRequestParam defaultRequestParam = new DefaultRequestParam();
        defaultRequestParam.a = str;
        defaultRequestParam.c = list;
        defaultRequestParam.e = ParamType.COMPLEX;
        return defaultRequestParam;
    }

    public static DefaultRequestParam arrayParam(String str, List<String> list) {
        DefaultRequestParam defaultRequestParam = new DefaultRequestParam();
        defaultRequestParam.a = str;
        defaultRequestParam.d = list;
        defaultRequestParam.e = ParamType.ARRAY;
        return defaultRequestParam;
    }

    public String getKey() {
        return this.a;
    }

    public Object getValue() {
        return this.b;
    }

    public List<String> getArrayValue() {
        return this.d;
    }

    public List<TwinRequestParam> getInnerParams() {
        return this.c;
    }

    public ParamType getParamType() {
        return this.e;
    }
}
