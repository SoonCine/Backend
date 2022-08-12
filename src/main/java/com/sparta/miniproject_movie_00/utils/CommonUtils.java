package com.sparta.miniproject_movie_00.utils;

public class CommonUtils {

    private static final String FILE_EXTENSION_SEPARATOR = ".";
    private static final String CATEGORY_PREFIX = "test";
    private static final String TIME_SEPARATOR = "/" ;

    public static String buildFileName(String originalFileName) {
        int fileExtensionIndex = originalFileName.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        String fileExtension = originalFileName.substring(fileExtensionIndex);
        String fileName = originalFileName.substring(0, fileExtensionIndex);
        String now = String.valueOf(System.currentTimeMillis());

        return CATEGORY_PREFIX + fileName + TIME_SEPARATOR + now + fileExtension;
    }

}
