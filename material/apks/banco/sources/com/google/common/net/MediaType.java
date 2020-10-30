package com.google.common.net;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Ascii;
import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Joiner.MapJoiner;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableListMultimap.Builder;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.errorprone.annotations.concurrent.LazyInit;
import cz.msebera.android.httpclient.entity.mime.MIME;
import cz.msebera.android.httpclient.message.TokenParser;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import org.bouncycastle.i18n.TextBundle;

@GwtCompatible
@Immutable
@Beta
public final class MediaType {
    public static final MediaType AAC_AUDIO = a("audio", "aac");
    public static final MediaType ANY_APPLICATION_TYPE = a("application", "*");
    public static final MediaType ANY_AUDIO_TYPE = a("audio", "*");
    public static final MediaType ANY_IMAGE_TYPE = a("image", "*");
    public static final MediaType ANY_TEXT_TYPE = a(TextBundle.TEXT_ENTRY, "*");
    public static final MediaType ANY_TYPE = a("*", "*");
    public static final MediaType ANY_VIDEO_TYPE = a("video", "*");
    public static final MediaType APPLE_MOBILE_CONFIG = a("application", "x-apple-aspen-config");
    public static final MediaType APPLE_PASSBOOK = a("application", "vnd.apple.pkpass");
    public static final MediaType APPLICATION_BINARY = a("application", MIME.ENC_BINARY);
    public static final MediaType APPLICATION_XML_UTF_8 = b("application", "xml");
    public static final MediaType ATOM_UTF_8 = b("application", "atom+xml");
    public static final MediaType BASIC_AUDIO = a("audio", "basic");
    public static final MediaType BMP = a("image", "bmp");
    public static final MediaType BZIP2 = a("application", "x-bzip2");
    public static final MediaType CACHE_MANIFEST_UTF_8 = b(TextBundle.TEXT_ENTRY, "cache-manifest");
    public static final MediaType CRW = a("image", "x-canon-crw");
    public static final MediaType CSS_UTF_8 = b(TextBundle.TEXT_ENTRY, "css");
    public static final MediaType CSV_UTF_8 = b(TextBundle.TEXT_ENTRY, "csv");
    public static final MediaType DART_UTF_8 = b("application", "dart");
    public static final MediaType EOT = a("application", "vnd.ms-fontobject");
    public static final MediaType EPUB = a("application", "epub+zip");
    public static final MediaType FLV_VIDEO = a("video", "x-flv");
    public static final MediaType FORM_DATA = a("application", "x-www-form-urlencoded");
    public static final MediaType GIF = a("image", "gif");
    public static final MediaType GZIP = a("application", "x-gzip");
    public static final MediaType HTML_UTF_8 = b(TextBundle.TEXT_ENTRY, "html");
    public static final MediaType ICO = a("image", "vnd.microsoft.icon");
    public static final MediaType I_CALENDAR_UTF_8 = b(TextBundle.TEXT_ENTRY, "calendar");
    public static final MediaType JAVASCRIPT_UTF_8 = b("application", "javascript");
    public static final MediaType JPEG = a("image", "jpeg");
    public static final MediaType JSON_UTF_8 = b("application", "json");
    public static final MediaType KEY_ARCHIVE = a("application", "pkcs12");
    public static final MediaType KML = a("application", "vnd.google-earth.kml+xml");
    public static final MediaType KMZ = a("application", "vnd.google-earth.kmz");
    public static final MediaType L24_AUDIO = a("audio", "l24");
    public static final MediaType MANIFEST_JSON_UTF_8 = b("application", "manifest+json");
    public static final MediaType MBOX = a("application", "mbox");
    public static final MediaType MICROSOFT_EXCEL = a("application", "vnd.ms-excel");
    public static final MediaType MICROSOFT_POWERPOINT = a("application", "vnd.ms-powerpoint");
    public static final MediaType MICROSOFT_WORD = a("application", "msword");
    public static final MediaType MP4_AUDIO = a("audio", "mp4");
    public static final MediaType MP4_VIDEO = a("video", "mp4");
    public static final MediaType MPEG_AUDIO = a("audio", "mpeg");
    public static final MediaType MPEG_VIDEO = a("video", "mpeg");
    public static final MediaType NACL_APPLICATION = a("application", "x-nacl");
    public static final MediaType NACL_PORTABLE_APPLICATION = a("application", "x-pnacl");
    public static final MediaType OCTET_STREAM = a("application", "octet-stream");
    public static final MediaType OGG_AUDIO = a("audio", "ogg");
    public static final MediaType OGG_CONTAINER = a("application", "ogg");
    public static final MediaType OGG_VIDEO = a("video", "ogg");
    public static final MediaType OOXML_DOCUMENT = a("application", "vnd.openxmlformats-officedocument.wordprocessingml.document");
    public static final MediaType OOXML_PRESENTATION = a("application", "vnd.openxmlformats-officedocument.presentationml.presentation");
    public static final MediaType OOXML_SHEET = a("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    public static final MediaType OPENDOCUMENT_GRAPHICS = a("application", "vnd.oasis.opendocument.graphics");
    public static final MediaType OPENDOCUMENT_PRESENTATION = a("application", "vnd.oasis.opendocument.presentation");
    public static final MediaType OPENDOCUMENT_SPREADSHEET = a("application", "vnd.oasis.opendocument.spreadsheet");
    public static final MediaType OPENDOCUMENT_TEXT = a("application", "vnd.oasis.opendocument.text");
    public static final MediaType PDF = a("application", "pdf");
    public static final MediaType PLAIN_TEXT_UTF_8 = b(TextBundle.TEXT_ENTRY, "plain");
    public static final MediaType PNG = a("image", "png");
    public static final MediaType POSTSCRIPT = a("application", "postscript");
    public static final MediaType PROTOBUF = a("application", "protobuf");
    public static final MediaType PSD = a("image", "vnd.adobe.photoshop");
    public static final MediaType QUICKTIME = a("video", "quicktime");
    public static final MediaType RDF_XML_UTF_8 = b("application", "rdf+xml");
    public static final MediaType RTF_UTF_8 = b("application", "rtf");
    public static final MediaType SFNT = a("application", "font-sfnt");
    public static final MediaType SHOCKWAVE_FLASH = a("application", "x-shockwave-flash");
    public static final MediaType SKETCHUP = a("application", "vnd.sketchup.skp");
    public static final MediaType SOAP_XML_UTF_8 = b("application", "soap+xml");
    public static final MediaType SVG_UTF_8 = b("image", "svg+xml");
    public static final MediaType TAR = a("application", "x-tar");
    public static final MediaType TEXT_JAVASCRIPT_UTF_8 = b(TextBundle.TEXT_ENTRY, "javascript");
    public static final MediaType THREE_GPP2_VIDEO = a("video", "3gpp2");
    public static final MediaType THREE_GPP_VIDEO = a("video", "3gpp");
    public static final MediaType TIFF = a("image", "tiff");
    public static final MediaType TSV_UTF_8 = b(TextBundle.TEXT_ENTRY, "tab-separated-values");
    public static final MediaType VCARD_UTF_8 = b(TextBundle.TEXT_ENTRY, "vcard");
    public static final MediaType VND_REAL_AUDIO = a("audio", "vnd.rn-realaudio");
    public static final MediaType VND_WAVE_AUDIO = a("audio", "vnd.wave");
    public static final MediaType VORBIS_AUDIO = a("audio", "vorbis");
    public static final MediaType VTT_UTF_8 = b(TextBundle.TEXT_ENTRY, "vtt");
    public static final MediaType WAX_AUDIO = a("audio", "x-ms-wax");
    public static final MediaType WEBM_AUDIO = a("audio", "webm");
    public static final MediaType WEBM_VIDEO = a("video", "webm");
    public static final MediaType WEBP = a("image", "webp");
    public static final MediaType WMA_AUDIO = a("audio", "x-ms-wma");
    public static final MediaType WML_UTF_8 = b(TextBundle.TEXT_ENTRY, "vnd.wap.wml");
    public static final MediaType WMV = a("video", "x-ms-wmv");
    public static final MediaType WOFF = a("application", "font-woff");
    public static final MediaType WOFF2 = a("application", "font-woff2");
    public static final MediaType XHTML_UTF_8 = b("application", "xhtml+xml");
    public static final MediaType XML_UTF_8 = b(TextBundle.TEXT_ENTRY, "xml");
    public static final MediaType XRD_UTF_8 = b("application", "xrd+xml");
    public static final MediaType ZIP = a("application", "zip");
    private static final ImmutableListMultimap<String, String> a = ImmutableListMultimap.of(HttpRequest.PARAM_CHARSET, Ascii.toLowerCase(Charsets.UTF_8.name()));
    /* access modifiers changed from: private */
    public static final CharMatcher b = CharMatcher.ascii().and(CharMatcher.javaIsoControl().negate()).and(CharMatcher.isNot(TokenParser.SP)).and(CharMatcher.noneOf("()<>@,;:\\\"/[]?="));
    private static final CharMatcher c = CharMatcher.ascii().and(CharMatcher.noneOf("\"\\\r"));
    private static final CharMatcher d = CharMatcher.anyOf(" \t\r\n");
    private static final Map<MediaType, MediaType> e = Maps.newHashMap();
    private static final MapJoiner k = Joiner.on("; ").withKeyValueSeparator("=");
    private final String f;
    private final String g;
    private final ImmutableListMultimap<String, String> h;
    @LazyInit
    private String i;
    @LazyInit
    private int j;

    static final class Tokenizer {
        final String a;
        int b = 0;

        Tokenizer(String str) {
            this.a = str;
        }

        /* access modifiers changed from: 0000 */
        public String a(CharMatcher charMatcher) {
            Preconditions.checkState(b());
            int i = this.b;
            this.b = charMatcher.negate().indexIn(this.a, i);
            return b() ? this.a.substring(i, this.b) : this.a.substring(i);
        }

        /* access modifiers changed from: 0000 */
        public String b(CharMatcher charMatcher) {
            int i = this.b;
            String a2 = a(charMatcher);
            Preconditions.checkState(this.b != i);
            return a2;
        }

        /* access modifiers changed from: 0000 */
        public char c(CharMatcher charMatcher) {
            Preconditions.checkState(b());
            char a2 = a();
            Preconditions.checkState(charMatcher.matches(a2));
            this.b++;
            return a2;
        }

        /* access modifiers changed from: 0000 */
        public char a(char c) {
            Preconditions.checkState(b());
            Preconditions.checkState(a() == c);
            this.b++;
            return c;
        }

        /* access modifiers changed from: 0000 */
        public char a() {
            Preconditions.checkState(b());
            return this.a.charAt(this.b);
        }

        /* access modifiers changed from: 0000 */
        public boolean b() {
            return this.b >= 0 && this.b < this.a.length();
        }
    }

    private static MediaType a(String str, String str2) {
        return a(new MediaType(str, str2, ImmutableListMultimap.of()));
    }

    private static MediaType b(String str, String str2) {
        return a(new MediaType(str, str2, a));
    }

    private static MediaType a(MediaType mediaType) {
        e.put(mediaType, mediaType);
        return mediaType;
    }

    private MediaType(String str, String str2, ImmutableListMultimap<String, String> immutableListMultimap) {
        this.f = str;
        this.g = str2;
        this.h = immutableListMultimap;
    }

    public String type() {
        return this.f;
    }

    public String subtype() {
        return this.g;
    }

    public ImmutableListMultimap<String, String> parameters() {
        return this.h;
    }

    private Map<String, ImmutableMultiset<String>> b() {
        return Maps.transformValues((Map<K, V1>) this.h.asMap(), (Function<? super V1, V2>) new Function<Collection<String>, ImmutableMultiset<String>>() {
            /* renamed from: a */
            public ImmutableMultiset<String> apply(Collection<String> collection) {
                return ImmutableMultiset.copyOf((Iterable<? extends E>) collection);
            }
        });
    }

    public Optional<Charset> charset() {
        ImmutableSet copyOf = ImmutableSet.copyOf((Collection<? extends E>) this.h.get((Object) HttpRequest.PARAM_CHARSET));
        switch (copyOf.size()) {
            case 0:
                return Optional.absent();
            case 1:
                return Optional.of(Charset.forName((String) Iterables.getOnlyElement(copyOf)));
            default:
                StringBuilder sb = new StringBuilder();
                sb.append("Multiple charset values defined: ");
                sb.append(copyOf);
                throw new IllegalStateException(sb.toString());
        }
    }

    public MediaType withoutParameters() {
        return this.h.isEmpty() ? this : create(this.f, this.g);
    }

    public MediaType withParameters(Multimap<String, String> multimap) {
        return a(this.f, this.g, multimap);
    }

    public MediaType withParameter(String str, String str2) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(str2);
        String b2 = b(str);
        Builder builder = ImmutableListMultimap.builder();
        Iterator it = this.h.entries().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            String str3 = (String) entry.getKey();
            if (!b2.equals(str3)) {
                builder.put(str3, entry.getValue());
            }
        }
        builder.put(b2, c(b2, str2));
        MediaType mediaType = new MediaType(this.f, this.g, builder.build());
        return (MediaType) MoreObjects.firstNonNull(e.get(mediaType), mediaType);
    }

    public MediaType withCharset(Charset charset) {
        Preconditions.checkNotNull(charset);
        return withParameter(HttpRequest.PARAM_CHARSET, charset.name());
    }

    public boolean hasWildcard() {
        return "*".equals(this.f) || "*".equals(this.g);
    }

    public boolean is(MediaType mediaType) {
        return (mediaType.f.equals("*") || mediaType.f.equals(this.f)) && (mediaType.g.equals("*") || mediaType.g.equals(this.g)) && this.h.entries().containsAll(mediaType.h.entries());
    }

    public static MediaType create(String str, String str2) {
        return a(str, str2, ImmutableListMultimap.of());
    }

    private static MediaType a(String str, String str2, Multimap<String, String> multimap) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(str2);
        Preconditions.checkNotNull(multimap);
        String b2 = b(str);
        String b3 = b(str2);
        Preconditions.checkArgument(!"*".equals(b2) || "*".equals(b3), "A wildcard type cannot be used with a non-wildcard subtype");
        Builder builder = ImmutableListMultimap.builder();
        for (Entry entry : multimap.entries()) {
            String b4 = b((String) entry.getKey());
            builder.put(b4, c(b4, (String) entry.getValue()));
        }
        MediaType mediaType = new MediaType(b2, b3, builder.build());
        return (MediaType) MoreObjects.firstNonNull(e.get(mediaType), mediaType);
    }

    private static String b(String str) {
        Preconditions.checkArgument(b.matchesAllOf(str));
        return Ascii.toLowerCase(str);
    }

    private static String c(String str, String str2) {
        return HttpRequest.PARAM_CHARSET.equals(str) ? Ascii.toLowerCase(str2) : str2;
    }

    public static MediaType parse(String str) {
        String str2;
        Preconditions.checkNotNull(str);
        Tokenizer tokenizer = new Tokenizer(str);
        try {
            String b2 = tokenizer.b(b);
            tokenizer.a('/');
            String b3 = tokenizer.b(b);
            Builder builder = ImmutableListMultimap.builder();
            while (tokenizer.b()) {
                tokenizer.a(d);
                tokenizer.a(';');
                tokenizer.a(d);
                String b4 = tokenizer.b(b);
                tokenizer.a('=');
                if ('\"' == tokenizer.a()) {
                    tokenizer.a((char) TokenParser.DQUOTE);
                    StringBuilder sb = new StringBuilder();
                    while ('\"' != tokenizer.a()) {
                        if ('\\' == tokenizer.a()) {
                            tokenizer.a((char) TokenParser.ESCAPE);
                            sb.append(tokenizer.c(CharMatcher.ascii()));
                        } else {
                            sb.append(tokenizer.b(c));
                        }
                    }
                    str2 = sb.toString();
                    tokenizer.a((char) TokenParser.DQUOTE);
                } else {
                    str2 = tokenizer.b(b);
                }
                builder.put(b4, str2);
            }
            return a(b2, b3, builder.build());
        } catch (IllegalStateException e2) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Could not parse '");
            sb2.append(str);
            sb2.append("'");
            throw new IllegalArgumentException(sb2.toString(), e2);
        }
    }

    public boolean equals(@Nullable Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MediaType)) {
            return false;
        }
        MediaType mediaType = (MediaType) obj;
        if (!this.f.equals(mediaType.f) || !this.g.equals(mediaType.g) || !b().equals(mediaType.b())) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        int i2 = this.j;
        if (i2 != 0) {
            return i2;
        }
        int hashCode = Objects.hashCode(this.f, this.g, b());
        this.j = hashCode;
        return hashCode;
    }

    public String toString() {
        String str = this.i;
        if (str != null) {
            return str;
        }
        String c2 = c();
        this.i = c2;
        return c2;
    }

    private String c() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.f);
        sb.append('/');
        sb.append(this.g);
        if (!this.h.isEmpty()) {
            sb.append("; ");
            k.appendTo(sb, (Iterable<? extends Entry<?, ?>>) Multimaps.transformValues((ListMultimap<K, V1>) this.h, (Function<? super V1, V2>) new Function<String, String>() {
                /* renamed from: a */
                public String apply(String str) {
                    return MediaType.b.matchesAllOf(str) ? str : MediaType.c(str);
                }
            }).entries());
        }
        return sb.toString();
    }

    /* access modifiers changed from: private */
    public static String c(String str) {
        StringBuilder sb = new StringBuilder(str.length() + 16);
        sb.append(TokenParser.DQUOTE);
        for (int i2 = 0; i2 < str.length(); i2++) {
            char charAt = str.charAt(i2);
            if (charAt == 13 || charAt == '\\' || charAt == '\"') {
                sb.append(TokenParser.ESCAPE);
            }
            sb.append(charAt);
        }
        sb.append(TokenParser.DQUOTE);
        return sb.toString();
    }
}
