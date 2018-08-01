package com.forcelate.utils;

import java.util.UUID;

public class StringUtils {

    public static String randomId() {
        return UUID.randomUUID().toString();
    }
}
