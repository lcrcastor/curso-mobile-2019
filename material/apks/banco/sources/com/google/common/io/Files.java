package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.TreeTraverser;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@GwtIncompatible
@Beta
public final class Files {
    private static final TreeTraverser<File> a = new TreeTraverser<File>() {
        public String toString() {
            return "Files.fileTreeTraverser()";
        }

        /* renamed from: a */
        public Iterable<File> children(File file) {
            if (file.isDirectory()) {
                File[] listFiles = file.listFiles();
                if (listFiles != null) {
                    return Collections.unmodifiableList(Arrays.asList(listFiles));
                }
            }
            return Collections.emptyList();
        }
    };

    static final class FileByteSink extends ByteSink {
        private final File a;
        private final ImmutableSet<FileWriteMode> b;

        private FileByteSink(File file, FileWriteMode... fileWriteModeArr) {
            this.a = (File) Preconditions.checkNotNull(file);
            this.b = ImmutableSet.copyOf((E[]) fileWriteModeArr);
        }

        /* renamed from: a */
        public FileOutputStream openStream() {
            return new FileOutputStream(this.a, this.b.contains(FileWriteMode.APPEND));
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Files.asByteSink(");
            sb.append(this.a);
            sb.append(", ");
            sb.append(this.b);
            sb.append(")");
            return sb.toString();
        }
    }

    static final class FileByteSource extends ByteSource {
        private final File a;

        private FileByteSource(File file) {
            this.a = (File) Preconditions.checkNotNull(file);
        }

        /* renamed from: a */
        public FileInputStream openStream() {
            return new FileInputStream(this.a);
        }

        public Optional<Long> sizeIfKnown() {
            if (this.a.isFile()) {
                return Optional.of(Long.valueOf(this.a.length()));
            }
            return Optional.absent();
        }

        public long size() {
            if (this.a.isFile()) {
                return this.a.length();
            }
            throw new FileNotFoundException(this.a.toString());
        }

        public byte[] read() {
            Closer create = Closer.create();
            try {
                FileInputStream fileInputStream = (FileInputStream) create.register(openStream());
                byte[] a2 = Files.a(fileInputStream, fileInputStream.getChannel().size());
                create.close();
                return a2;
            } catch (Throwable th) {
                create.close();
                throw th;
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Files.asByteSource(");
            sb.append(this.a);
            sb.append(")");
            return sb.toString();
        }
    }

    enum FilePredicate implements Predicate<File> {
        IS_DIRECTORY {
            public String toString() {
                return "Files.isDirectory()";
            }

            /* renamed from: a */
            public boolean apply(File file) {
                return file.isDirectory();
            }
        },
        IS_FILE {
            public String toString() {
                return "Files.isFile()";
            }

            /* renamed from: a */
            public boolean apply(File file) {
                return file.isFile();
            }
        }
    }

    private Files() {
    }

    public static BufferedReader newReader(File file, Charset charset) {
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(charset);
        return new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
    }

    public static BufferedWriter newWriter(File file, Charset charset) {
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(charset);
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
    }

    public static ByteSource asByteSource(File file) {
        return new FileByteSource(file);
    }

    static byte[] a(InputStream inputStream, long j) {
        if (j <= 2147483647L) {
            return j == 0 ? ByteStreams.toByteArray(inputStream) : ByteStreams.a(inputStream, (int) j);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("file is too large to fit in a byte array: ");
        sb.append(j);
        sb.append(" bytes");
        throw new OutOfMemoryError(sb.toString());
    }

    public static ByteSink asByteSink(File file, FileWriteMode... fileWriteModeArr) {
        return new FileByteSink(file, fileWriteModeArr);
    }

    public static CharSource asCharSource(File file, Charset charset) {
        return asByteSource(file).asCharSource(charset);
    }

    public static CharSink asCharSink(File file, Charset charset, FileWriteMode... fileWriteModeArr) {
        return asByteSink(file, fileWriteModeArr).asCharSink(charset);
    }

    private static FileWriteMode[] a(boolean z) {
        if (!z) {
            return new FileWriteMode[0];
        }
        return new FileWriteMode[]{FileWriteMode.APPEND};
    }

    public static byte[] toByteArray(File file) {
        return asByteSource(file).read();
    }

    public static String toString(File file, Charset charset) {
        return asCharSource(file, charset).read();
    }

    public static void write(byte[] bArr, File file) {
        asByteSink(file, new FileWriteMode[0]).write(bArr);
    }

    public static void copy(File file, OutputStream outputStream) {
        asByteSource(file).copyTo(outputStream);
    }

    public static void copy(File file, File file2) {
        Preconditions.checkArgument(!file.equals(file2), "Source %s and destination %s must be different", (Object) file, (Object) file2);
        asByteSource(file).copyTo(asByteSink(file2, new FileWriteMode[0]));
    }

    public static void write(CharSequence charSequence, File file, Charset charset) {
        asCharSink(file, charset, new FileWriteMode[0]).write(charSequence);
    }

    public static void append(CharSequence charSequence, File file, Charset charset) {
        a(charSequence, file, charset, true);
    }

    private static void a(CharSequence charSequence, File file, Charset charset, boolean z) {
        asCharSink(file, charset, a(z)).write(charSequence);
    }

    public static void copy(File file, Charset charset, Appendable appendable) {
        asCharSource(file, charset).copyTo(appendable);
    }

    public static boolean equal(File file, File file2) {
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(file2);
        if (file == file2 || file.equals(file2)) {
            return true;
        }
        long length = file.length();
        long length2 = file2.length();
        if (length == 0 || length2 == 0 || length == length2) {
            return asByteSource(file).contentEquals(asByteSource(file2));
        }
        return false;
    }

    public static File createTempDir() {
        File file = new File(System.getProperty("java.io.tmpdir"));
        StringBuilder sb = new StringBuilder();
        sb.append(System.currentTimeMillis());
        sb.append("-");
        String sb2 = sb.toString();
        for (int i = 0; i < 10000; i++) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(sb2);
            sb3.append(i);
            File file2 = new File(file, sb3.toString());
            if (file2.mkdir()) {
                return file2;
            }
        }
        StringBuilder sb4 = new StringBuilder();
        sb4.append("Failed to create directory within 10000 attempts (tried ");
        sb4.append(sb2);
        sb4.append("0 to ");
        sb4.append(sb2);
        sb4.append(9999);
        sb4.append(')');
        throw new IllegalStateException(sb4.toString());
    }

    public static void touch(File file) {
        Preconditions.checkNotNull(file);
        if (!file.createNewFile() && !file.setLastModified(System.currentTimeMillis())) {
            StringBuilder sb = new StringBuilder();
            sb.append("Unable to update modification time of ");
            sb.append(file);
            throw new IOException(sb.toString());
        }
    }

    public static void createParentDirs(File file) {
        Preconditions.checkNotNull(file);
        File parentFile = file.getCanonicalFile().getParentFile();
        if (parentFile != null) {
            parentFile.mkdirs();
            if (!parentFile.isDirectory()) {
                StringBuilder sb = new StringBuilder();
                sb.append("Unable to create parent directories of ");
                sb.append(file);
                throw new IOException(sb.toString());
            }
        }
    }

    public static void move(File file, File file2) {
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(file2);
        Preconditions.checkArgument(!file.equals(file2), "Source %s and destination %s must be different", (Object) file, (Object) file2);
        if (!file.renameTo(file2)) {
            copy(file, file2);
            if (file.delete()) {
                return;
            }
            if (!file2.delete()) {
                StringBuilder sb = new StringBuilder();
                sb.append("Unable to delete ");
                sb.append(file2);
                throw new IOException(sb.toString());
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Unable to delete ");
            sb2.append(file);
            throw new IOException(sb2.toString());
        }
    }

    public static String readFirstLine(File file, Charset charset) {
        return asCharSource(file, charset).readFirstLine();
    }

    public static List<String> readLines(File file, Charset charset) {
        return (List) readLines(file, charset, new LineProcessor<List<String>>() {
            final List<String> a = Lists.newArrayList();

            public boolean processLine(String str) {
                this.a.add(str);
                return true;
            }

            /* renamed from: a */
            public List<String> getResult() {
                return this.a;
            }
        });
    }

    @CanIgnoreReturnValue
    public static <T> T readLines(File file, Charset charset, LineProcessor<T> lineProcessor) {
        return asCharSource(file, charset).readLines(lineProcessor);
    }

    @CanIgnoreReturnValue
    public static <T> T readBytes(File file, ByteProcessor<T> byteProcessor) {
        return asByteSource(file).read(byteProcessor);
    }

    public static HashCode hash(File file, HashFunction hashFunction) {
        return asByteSource(file).hash(hashFunction);
    }

    public static MappedByteBuffer map(File file) {
        Preconditions.checkNotNull(file);
        return map(file, MapMode.READ_ONLY);
    }

    public static MappedByteBuffer map(File file, MapMode mapMode) {
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(mapMode);
        if (file.exists()) {
            return map(file, mapMode, file.length());
        }
        throw new FileNotFoundException(file.toString());
    }

    public static MappedByteBuffer map(File file, MapMode mapMode, long j) {
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(mapMode);
        Closer create = Closer.create();
        try {
            MappedByteBuffer a2 = a((RandomAccessFile) create.register(new RandomAccessFile(file, mapMode == MapMode.READ_ONLY ? "r" : "rw")), mapMode, j);
            create.close();
            return a2;
        } catch (Throwable th) {
            create.close();
            throw th;
        }
    }

    private static MappedByteBuffer a(RandomAccessFile randomAccessFile, MapMode mapMode, long j) {
        Closer create = Closer.create();
        try {
            MappedByteBuffer map = ((FileChannel) create.register(randomAccessFile.getChannel())).map(mapMode, 0, j);
            create.close();
            return map;
        } catch (Throwable th) {
            create.close();
            throw th;
        }
    }

    public static String simplifyPath(String str) {
        Preconditions.checkNotNull(str);
        if (str.length() == 0) {
            return ".";
        }
        Iterable<String> split = Splitter.on('/').omitEmptyStrings().split(str);
        ArrayList arrayList = new ArrayList();
        for (String str2 : split) {
            if (!str2.equals(".")) {
                if (!str2.equals("..")) {
                    arrayList.add(str2);
                } else if (arrayList.size() <= 0 || ((String) arrayList.get(arrayList.size() - 1)).equals("..")) {
                    arrayList.add("..");
                } else {
                    arrayList.remove(arrayList.size() - 1);
                }
            }
        }
        String join = Joiner.on('/').join((Iterable<?>) arrayList);
        if (str.charAt(0) == '/') {
            StringBuilder sb = new StringBuilder();
            sb.append("/");
            sb.append(join);
            join = sb.toString();
        }
        while (join.startsWith("/../")) {
            join = join.substring(3);
        }
        if (join.equals("/..")) {
            join = "/";
        } else if ("".equals(join)) {
            join = ".";
        }
        return join;
    }

    public static String getFileExtension(String str) {
        Preconditions.checkNotNull(str);
        String name = new File(str).getName();
        int lastIndexOf = name.lastIndexOf(46);
        if (lastIndexOf == -1) {
            return "";
        }
        return name.substring(lastIndexOf + 1);
    }

    public static String getNameWithoutExtension(String str) {
        Preconditions.checkNotNull(str);
        String name = new File(str).getName();
        int lastIndexOf = name.lastIndexOf(46);
        return lastIndexOf == -1 ? name : name.substring(0, lastIndexOf);
    }

    public static TreeTraverser<File> fileTreeTraverser() {
        return a;
    }

    public static Predicate<File> isDirectory() {
        return FilePredicate.IS_DIRECTORY;
    }

    public static Predicate<File> isFile() {
        return FilePredicate.IS_FILE;
    }
}
