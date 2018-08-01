package com.forcelate.utils;

public class DriverUtils {

    public static void load() {
        System.setProperty("webdriver.chrome.driver", PathUtils.driverPath());
    }
}
