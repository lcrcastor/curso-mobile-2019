package com.squareup.picasso;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.UriMatcher;
import android.net.Uri;
import android.os.Build.VERSION;
import android.provider.ContactsContract.Contacts;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.RequestHandler.Result;
import java.io.InputStream;

class ContactsPhotoRequestHandler extends RequestHandler {
    private static final UriMatcher a = new UriMatcher(-1);
    private final Context b;

    @TargetApi(14)
    static class ContactPhotoStreamIcs {
        private ContactPhotoStreamIcs() {
        }

        static InputStream a(ContentResolver contentResolver, Uri uri) {
            return Contacts.openContactPhotoInputStream(contentResolver, uri, true);
        }
    }

    static {
        a.addURI("com.android.contacts", "contacts/lookup/*/#", 1);
        a.addURI("com.android.contacts", "contacts/lookup/*", 1);
        a.addURI("com.android.contacts", "contacts/#/photo", 2);
        a.addURI("com.android.contacts", "contacts/#", 3);
        a.addURI("com.android.contacts", "display_photo/#", 4);
    }

    ContactsPhotoRequestHandler(Context context) {
        this.b = context;
    }

    public boolean canHandleRequest(Request request) {
        Uri uri = request.uri;
        return "content".equals(uri.getScheme()) && Contacts.CONTENT_URI.getHost().equals(uri.getHost()) && a.match(request.uri) != -1;
    }

    public Result load(Request request, int i) {
        InputStream a2 = a(request);
        if (a2 != null) {
            return new Result(a2, LoadedFrom.DISK);
        }
        return null;
    }

    private InputStream a(Request request) {
        ContentResolver contentResolver = this.b.getContentResolver();
        Uri uri = request.uri;
        switch (a.match(uri)) {
            case 1:
                uri = Contacts.lookupContact(contentResolver, uri);
                if (uri == null) {
                    return null;
                }
                break;
            case 2:
            case 4:
                return contentResolver.openInputStream(uri);
            case 3:
                break;
            default:
                StringBuilder sb = new StringBuilder();
                sb.append("Invalid uri: ");
                sb.append(uri);
                throw new IllegalStateException(sb.toString());
        }
        if (VERSION.SDK_INT < 14) {
            return Contacts.openContactPhotoInputStream(contentResolver, uri);
        }
        return ContactPhotoStreamIcs.a(contentResolver, uri);
    }
}
