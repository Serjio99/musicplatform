package com.musicplatform.util;

public final class FileTypeUtils {

    private FileTypeUtils() {
    }

    public static boolean isImage(String contentType) {
        return contentType != null && contentType.startsWith("image/");
    }

    public static boolean isAudio(String contentType) {
        return contentType != null && contentType.startsWith("audio/");
    }

    public static boolean isPdf(String contentType) {
        return "application/pdf".equalsIgnoreCase(contentType);
    }
}