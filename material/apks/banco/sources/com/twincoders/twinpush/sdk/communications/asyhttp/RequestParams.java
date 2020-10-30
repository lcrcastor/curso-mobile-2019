package com.twincoders.twinpush.sdk.communications.asyhttp;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.utils.URLEncodedUtils;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class RequestParams {
    private static String a = "UTF-8";
    protected ConcurrentHashMap<String, FileWrapper> fileParams;
    protected ConcurrentHashMap<String, String> urlParams;
    protected ConcurrentHashMap<String, ArrayList<String>> urlParamsWithArray;

    static class FileWrapper {
        public InputStream a;
        public String b;
        public String c;

        public FileWrapper(InputStream inputStream, String str, String str2) {
            this.a = inputStream;
            this.b = str;
            this.c = str2;
        }

        public String a() {
            return this.b != null ? this.b : "nofilename";
        }
    }

    public RequestParams() {
        a();
    }

    public RequestParams(Map<String, String> map) {
        a();
        for (Entry entry : map.entrySet()) {
            put((String) entry.getKey(), (String) entry.getValue());
        }
    }

    public RequestParams(String str, String str2) {
        a();
        put(str, str2);
    }

    public RequestParams(Object... objArr) {
        a();
        int length = objArr.length;
        if (length % 2 != 0) {
            throw new IllegalArgumentException("Supplied arguments must be even");
        }
        for (int i = 0; i < length; i += 2) {
            put(String.valueOf(objArr[i]), String.valueOf(objArr[i + 1]));
        }
    }

    public void put(String str, String str2) {
        if (str != null && str2 != null) {
            this.urlParams.put(str, str2);
        }
    }

    public void put(String str, File file) {
        put(str, new FileInputStream(file), file.getName());
    }

    public void put(String str, ArrayList<String> arrayList) {
        if (str != null && arrayList != null) {
            this.urlParamsWithArray.put(str, arrayList);
        }
    }

    public void put(String str, InputStream inputStream) {
        put(str, inputStream, null);
    }

    public void put(String str, InputStream inputStream, String str2) {
        put(str, inputStream, str2, null);
    }

    public void put(String str, InputStream inputStream, String str2, String str3) {
        if (str != null && inputStream != null) {
            this.fileParams.put(str, new FileWrapper(inputStream, str2, str3));
        }
    }

    public void remove(String str) {
        this.urlParams.remove(str);
        this.fileParams.remove(str);
        this.urlParamsWithArray.remove(str);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Entry entry : this.urlParams.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append((String) entry.getKey());
            sb.append("=");
            sb.append((String) entry.getValue());
        }
        for (Entry entry2 : this.fileParams.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append((String) entry2.getKey());
            sb.append("=");
            sb.append("FILE");
        }
        for (Entry entry3 : this.urlParamsWithArray.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            ArrayList arrayList = (ArrayList) entry3.getValue();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                if (arrayList.indexOf(str) != 0) {
                    sb.append("&");
                }
                sb.append((String) entry3.getKey());
                sb.append("=");
                sb.append(str);
            }
        }
        return sb.toString();
    }

    public HttpEntity getEntity() {
        if (!this.fileParams.isEmpty()) {
            SimpleMultipartEntity simpleMultipartEntity = new SimpleMultipartEntity();
            for (Entry entry : this.urlParams.entrySet()) {
                simpleMultipartEntity.a((String) entry.getKey(), (String) entry.getValue());
            }
            for (Entry entry2 : this.urlParamsWithArray.entrySet()) {
                Iterator it = ((ArrayList) entry2.getValue()).iterator();
                while (it.hasNext()) {
                    simpleMultipartEntity.a((String) entry2.getKey(), (String) it.next());
                }
            }
            int size = this.fileParams.entrySet().size() - 1;
            int i = 0;
            for (Entry entry3 : this.fileParams.entrySet()) {
                FileWrapper fileWrapper = (FileWrapper) entry3.getValue();
                if (fileWrapper.a != null) {
                    boolean z = i == size;
                    if (fileWrapper.c != null) {
                        simpleMultipartEntity.a((String) entry3.getKey(), fileWrapper.a(), fileWrapper.a, fileWrapper.c, z);
                    } else {
                        simpleMultipartEntity.a((String) entry3.getKey(), fileWrapper.a(), fileWrapper.a, z);
                    }
                }
                i++;
            }
            return simpleMultipartEntity;
        }
        try {
            return new UrlEncodedFormEntity(getParamsList(), a);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void a() {
        this.urlParams = new ConcurrentHashMap<>();
        this.fileParams = new ConcurrentHashMap<>();
        this.urlParamsWithArray = new ConcurrentHashMap<>();
    }

    /* access modifiers changed from: protected */
    public List<BasicNameValuePair> getParamsList() {
        LinkedList linkedList = new LinkedList();
        for (Entry entry : this.urlParams.entrySet()) {
            linkedList.add(new BasicNameValuePair((String) entry.getKey(), (String) entry.getValue()));
        }
        for (Entry entry2 : this.urlParamsWithArray.entrySet()) {
            Iterator it = ((ArrayList) entry2.getValue()).iterator();
            while (it.hasNext()) {
                linkedList.add(new BasicNameValuePair((String) entry2.getKey(), (String) it.next()));
            }
        }
        return linkedList;
    }

    /* access modifiers changed from: protected */
    public String getParamString() {
        return URLEncodedUtils.format(getParamsList(), a);
    }
}
