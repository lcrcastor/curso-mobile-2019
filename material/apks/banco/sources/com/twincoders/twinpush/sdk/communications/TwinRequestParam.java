package com.twincoders.twinpush.sdk.communications;

import java.util.List;

public interface TwinRequestParam {

    public enum ParamType {
        SIMPLE,
        ARRAY,
        COMPLEX
    }

    List<String> getArrayValue();

    List<TwinRequestParam> getInnerParams();

    String getKey();

    ParamType getParamType();

    Object getValue();
}
