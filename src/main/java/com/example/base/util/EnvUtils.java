package com.example.base.util;

public final class EnvUtils {

    public static final String CLUSTER = env("CLUSTER_NAME", "");

    private static String env(String name, String def) {
        if (System.getenv(name) != null) {
            return System.getenv(name);
        }
        if (System.getProperty(name) != null) {
            return System.getProperty(name);
        }
        return def;
    }
}