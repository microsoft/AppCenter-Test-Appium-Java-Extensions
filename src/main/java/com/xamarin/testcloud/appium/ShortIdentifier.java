package com.xamarin.testcloud.appium;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.regex.Pattern;

class ShortIdentifier {

    private final Pattern sanitizer = Pattern.compile("[^a-zA-Z0-9.:_()\\[\\]]");
    final int partMaxLength = 50;
    final int idMaxLength = 1024; // other 1024 reserved for leading path
    private final String shortId;

    public ShortIdentifier(String input) {
        if (input == null || input == "") {
            throw new IllegalArgumentException("input must be a string with contents");
        }
        this.shortId = shortId(input);
    }

    public String value() {
        return shortId;
    }

    private String shortId(String longId) {
        String[] parts = longId.split(":");
        StringBuilder sb = new StringBuilder(2 * parts.length + 2);
        for (String part : parts) {
            String sanitized = sanitizer.matcher(part).replaceAll("");
            sb.append(mostSignificantSubstring(sanitized, partMaxLength));
            sb.append("-");
        }
        sb.append(calcBase64Hash(longId));
        return mostSignificantSubstring(sb.toString(), idMaxLength);
    }

    private String mostSignificantSubstring(String input, int maxLength) {
        if (input.length() <= maxLength)
            return input;
        else
            return input.substring(0, maxLength - 2 - maxLength / 2) + ".." + input.substring(input.length() - maxLength / 2);
    }

    final int useBits = 64;
    private String calcBase64Hash(String input) {
        try {
            byte[] shortened = shortenHash(calcSha1(input), useBits);
            return Base64.getEncoder().encodeToString(shortened).replace('+', '_').replace('/', '-').replace("=","");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] calcSha1(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        return digest.digest(input.getBytes());
    }

    private byte[] shortenHash(byte[] digestBytes, int useBits) {
        return Arrays.copyOf(digestBytes, useBits / 8);
    }

}
