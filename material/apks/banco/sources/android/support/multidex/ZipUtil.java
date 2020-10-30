package android.support.multidex;

import android.support.v4.media.session.PlaybackStateCompat;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.zip.CRC32;
import java.util.zip.ZipException;

final class ZipUtil {

    static class CentralDirectory {
        long a;
        long b;

        CentralDirectory() {
        }
    }

    ZipUtil() {
    }

    static long a(File file) {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        try {
            return a(randomAccessFile, a(randomAccessFile));
        } finally {
            randomAccessFile.close();
        }
    }

    static CentralDirectory a(RandomAccessFile randomAccessFile) {
        long length = randomAccessFile.length() - 22;
        long j = 0;
        if (length < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("File too short to be a zip file: ");
            sb.append(randomAccessFile.length());
            throw new ZipException(sb.toString());
        }
        long j2 = length - PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH;
        if (j2 >= 0) {
            j = j2;
        }
        int reverseBytes = Integer.reverseBytes(101010256);
        while (true) {
            randomAccessFile.seek(length);
            if (randomAccessFile.readInt() == reverseBytes) {
                randomAccessFile.skipBytes(2);
                randomAccessFile.skipBytes(2);
                randomAccessFile.skipBytes(2);
                randomAccessFile.skipBytes(2);
                CentralDirectory centralDirectory = new CentralDirectory();
                centralDirectory.b = ((long) Integer.reverseBytes(randomAccessFile.readInt())) & 4294967295L;
                centralDirectory.a = ((long) Integer.reverseBytes(randomAccessFile.readInt())) & 4294967295L;
                return centralDirectory;
            }
            long j3 = length - 1;
            if (j3 < j) {
                throw new ZipException("End Of Central Directory signature not found");
            }
            length = j3;
        }
    }

    static long a(RandomAccessFile randomAccessFile, CentralDirectory centralDirectory) {
        CRC32 crc32 = new CRC32();
        long j = centralDirectory.b;
        randomAccessFile.seek(centralDirectory.a);
        byte[] bArr = new byte[16384];
        int read = randomAccessFile.read(bArr, 0, (int) Math.min(PlaybackStateCompat.ACTION_PREPARE, j));
        while (read != -1) {
            crc32.update(bArr, 0, read);
            long j2 = j - ((long) read);
            if (j2 == 0) {
                break;
            }
            read = randomAccessFile.read(bArr, 0, (int) Math.min(PlaybackStateCompat.ACTION_PREPARE, j2));
            j = j2;
        }
        return crc32.getValue();
    }
}
