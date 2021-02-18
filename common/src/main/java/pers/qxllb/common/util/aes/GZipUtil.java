package pers.qxllb.common.util.aes;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author
 */
@Slf4j
public class GZipUtil {

        public static final String GZIP_ENCODE_UTF_8 = "UTF-8";


        public static byte[] compress(String str) {
            return compress(str, GZIP_ENCODE_UTF_8);
        }

        public static byte[] compress(String str, String encoding) {
            if (str == null || str.length() == 0) {
                return null;
            }

            try (ByteArrayOutputStream out = new ByteArrayOutputStream()){
                try (GZIPOutputStream gzip = new GZIPOutputStream(out)){
                    gzip.write(str.getBytes(encoding));
                } catch (IOException e) {
                    log.error("Gzip compress`fail`msg=" + e.getMessage(), e);
                }

                return out.toByteArray();
            } catch (Exception e) {
                log.error("Gzip compress`fail`msg=" + e.getMessage(), e);
            }
            return null;
        }

        public static byte[] uncompress(byte[] bytes) {
            if (bytes == null || bytes.length == 0) {
                return null;
            }

            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                try (ByteArrayInputStream in = new ByteArrayInputStream(bytes)) {
                    GZIPInputStream ungzip = new GZIPInputStream(in);
                    byte[] buffer = new byte[256];
                    int n;
                    while ((n = ungzip.read(buffer)) >= 0) {
                        out.write(buffer, 0, n);
                    }
                } catch (IOException e) {
                    log.error("Gzip uncompress`fail`msg=" + e.getMessage(), e);
                }
                return out.toByteArray();
            } catch (IOException e) {
                log.error("Gzip uncompress`fail`msg=" + e.getMessage(), e);
            }

            return null;
        }

        public static String uncompressToString(byte[] bytes) {
            return uncompressToString(bytes, GZIP_ENCODE_UTF_8);
        }

        public static String uncompressToString(byte[] bytes, String encoding) {
            if (bytes == null || bytes.length == 0) {
                return null;
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            try {
                GZIPInputStream ungzip = new GZIPInputStream(in);
                byte[] buffer = new byte[256];
                int n;
                while ((n = ungzip.read(buffer)) >= 0) {
                    out.write(buffer, 0, n);
                }
                return out.toString(encoding);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

}
