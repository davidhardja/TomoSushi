package com.example.david.sushi.Common;

/**
 * Created by David on 22/03/2017.
 */

public class StringHelper {
    public static int VALID_DATE_LENGTH = 25; // ex : 2015-12-19 23:18:54 -0500

    public static boolean isEmptyString(String text) {
        return (text == null || text.trim().equals("null") || text.trim()
                .length() <= 0);
    }

    public static String getCleanString(String text) {
        if (isEmptyString(text))
            return "";
        return text;
    }

    public static boolean isValidDate(String dateString) {
        return !(dateString == null || dateString.isEmpty() || dateString.equals("null")) && !(dateString.length() < VALID_DATE_LENGTH || dateString.length() > VALID_DATE_LENGTH);
    }
}
