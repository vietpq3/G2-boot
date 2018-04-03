package common;

import java.io.UnsupportedEncodingException;

public class CryptUtil {

    private static int LIMIT = 15;

    public static String encrypt(String input, String sessionId) throws UnsupportedEncodingException {
        int key = 0;
        int limit = LIMIT;
        while (key == 0) {
            key = sessionId.charAt(0) % limit++;
        }
        StringBuffer output = new StringBuffer("");

        for (char c : input.toCharArray()) {
            if ('A' <= c && c <= 'Z') {
                output.append((char) (c + key - ((c + key) > 90 ? 26 : 0)));
            } else if ('a' <= c && c <= 'z') {
                output.append((char) (c + key - ((c + key) > 122 ? 26 : 0)));
            } else if (' ' == c || 33 <= c && c <= 126) {
                output.append(c);
            } else {
                throw new UnsupportedEncodingException();
            }
        }
        return output.toString();
    }

    public static String decrypt(String input, String sessionId) throws UnsupportedEncodingException {
        int key = 0;
        int limit = LIMIT;
        while (key == 0) {
            key = sessionId.charAt(0) % limit++;
        }
        StringBuffer output = new StringBuffer("");

        for (char c : input.toCharArray()) {
            if ('A' <= c && c <= 'Z') {
                output.append((char) (c - key + ((c - key) < 65 ? 26 : 0)));
            } else if ('a' <= c && c <= 'z') {
                output.append((char) (c - key + ((c - key) < 97 ? 26 : 0)));
            } else if (' ' == c || 33 <= c && c <= 126) {
                output.append(c);
            } else {
                throw new UnsupportedEncodingException();
            }
        }
        return output.toString();
    }
}
